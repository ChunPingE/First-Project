package com.example.demo.service;

import java.io.*;
import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.security.core.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;
import org.springframework.web.multipart.*;

import com.example.demo.domain.*;
import com.example.demo.mapper.*;

import software.amazon.awssdk.core.sync.*;
import software.amazon.awssdk.services.s3.*;
import software.amazon.awssdk.services.s3.model.*;

@Service
public class BoardServiceImpl implements BoardService {

	@Value("${aws.s3.bucketName}")
	private String bucketName;

	@Autowired
	private S3Client s3;

	@Autowired
	private BoardMapper mapper;

	@Autowired
	private BoardLikeMapper likeMapper;

	public List<Board> listBoard() {
		List<Board> list = mapper.selectAll();
		return list;
	}

	@Override
	public Map<String, Object> listBoard(Integer page, String search, String type) {
		Integer rowPerPage = 10;
		Integer startIndex = (page - 1) * rowPerPage;

		// 페이지 네이션이 필요한 정보
		// 전체 레코드 수
		Integer numOfRecords = mapper.countAll(search, type);

		// 마지막 페이지 번호 (총 글개수 - 1) / rowPerPage + 1
		Integer lastPageNumber = (numOfRecords - 1) / rowPerPage + 1;

		// 페이지네이션 왼쪽 번호
		Integer leftPageNumber = page - 5;
		leftPageNumber = Math.max(leftPageNumber, 1);

		// 페이지네이션 오른쪽 번호
		Integer rightPageNumber = leftPageNumber + 9;
		rightPageNumber = Math.min(rightPageNumber, lastPageNumber);

		// 현재페이지
		Integer currentPageNumber = page;

		// 이전 페이지 다음페이지
		Integer prevPageNumber = currentPageNumber - 1;

		Integer nextPageNumber = currentPageNumber + 1;

		Map<String, Object> pageInfo = new HashMap<>();
		pageInfo.put("lastPageNumber", lastPageNumber);
		pageInfo.put("leftPageNumber", leftPageNumber);
		pageInfo.put("rightPageNumber", rightPageNumber);
		pageInfo.put("currentPageNumber", currentPageNumber);
		pageInfo.put("prevPageNumber", prevPageNumber);
		pageInfo.put("nextPageNumber", nextPageNumber);

		// 게시물 목록
		List<BoardView> list = mapper.selectAllPaging(startIndex, rowPerPage, search, type);

		return Map.of("pageInfo", pageInfo, "boardList", list);
	}

	@Override
	public Board getBoard(Integer id, Authentication authentication) {
		Board board = mapper.selectById(id);
		//현재 로그인하란 사람이 이 게시물에 좋아요 했는지?
		//첫번째 글
		Integer firstBoardId = mapper.selectFirstBoardId();
		//마지막글
		Integer lastBoardId = mapper.selectLastBoardId();
		
		if (id.equals(firstBoardId)) {
		board.setPrevId(firstBoardId);
		} else {
			board.setPrevId(mapper.selectPrevId(id));
		}
		
		if (id.equals(lastBoardId)) {
			board.setPrevId(lastBoardId);
		} else {
			board.setNextId(mapper.selectNextId(id));
		}
		
		if (authentication != null) {
			Like like = likeMapper.select(id, authentication.getName());
			if (like != null) {
				board.setLiked(true);
			}
		}
		return board;
	}
	
	@Override
	public Object getBoard(Integer id) {
		return getBoard(id, null);
	}

	@Transactional(rollbackFor = Exception.class)
	public boolean update(Board board, List<String> removeFileNames, MultipartFile[] files)
			throws Exception {
		if (removeFileNames != null && !removeFileNames.isEmpty()) {
			for (String fileName : removeFileNames) {
				// 파일 삭제
				String objectKey = "board/" + board.getId() + "/" + fileName;
				DeleteObjectRequest dor = DeleteObjectRequest.builder()
						.bucket(bucketName)
						.key(objectKey)
						.build();

				s3.deleteObject(dor);

				// FileName 테이블의 데이터 삭제
				mapper.deleteFileNameByBoardIdAndFileName(board.getId(), fileName);
				/*
				 * // 하드 디스크에서 삭제 String path = "F:\\study\\upload\\" + board.getId() +
				 * File.separator + fileName; File file = new File(path); if (file.exists()) {
				 * file.delete(); }
				 * 
				 * String path2 = "F:\\study\\upload\\" + board.getId(); File file2 = new
				 * File(path2); if (file2.exists()) { file2.delete(); }
				 */
			}
		}

		// 게시물(Board) 테이블 수정
		int cnt = mapper.update(board);

		for (MultipartFile file : files) {
			if (file.getSize() > 0) {
				/*
				 * // 폴더만들기 String folder = "F:\\study\\upload\\" + board.getId(); File
				 * targetFolder = new File(folder); if (!targetFolder.exists()) {
				 * targetFolder.mkdirs(); } // 파일 저장 String path = folder + File.separator +
				 * file.getOriginalFilename(); File target = new File(path);
				 * file.transferTo(target);
				 */
				String objectKey = "board/" + board.getId() + "/" + file.getOriginalFilename();

				// s3에 파일(객체) 업로드
				PutObjectRequest por = PutObjectRequest.builder()
						.bucket(bucketName)
						.acl(ObjectCannedACL.PUBLIC_READ)
						.key(objectKey)
						.build();
				RequestBody rb = RequestBody.fromInputStream(file.getInputStream(), file.getSize());

				s3.putObject(por, rb);

				// db에 관련정보저장 (insert)
				mapper.insertFileName(board.getId(), file.getOriginalFilename());
			}
		}
		return cnt == 1;
	}

	@Transactional(rollbackFor = Exception.class)
	public boolean remove(Integer id) {
		// 파일명 조회
		List<String> fileNames = mapper.selectFileNamesByBoardId(id);

		// FileName 테이블의 데이터 지우기
		mapper.deleteFileNameByBoardId(id);

		// 하드디스크의 파일 지우기
		// s3파일 지우기
		for (String fileName : fileNames) {
			/*
			 * String path = "F:\\study\\upload\\" + id + File.separator + fileName; File
			 * file = new File(path); if (file.exists()) { file.delete(); }
			 */
			String objectKey = "board/" + id + "/" + fileName;
			DeleteObjectRequest dor = DeleteObjectRequest.builder()
					.bucket(bucketName)
					.key(objectKey)
					.build();

			s3.deleteObject(dor);
		}
		// 좋아요 테이블의 데이터 지우기
		likeMapper.deleteByBoardId(id);

		// 게시물 테이블의 데이터 지우기
		int cnt = mapper.deleteById(id);
		return cnt == 1;
	}

	@Transactional(rollbackFor = Exception.class)
	public boolean create(Board board, MultipartFile[] files) throws Exception {
		// 게시물 insert
		int cnt = mapper.insert(board);

		for (MultipartFile file : files) {
			if (file.getSize() > 0) {
				// System.out.println(file.getOriginalFilename());
				// System.out.println(file.getSize());
				// 파일 저장 (파일 시스템 하드디스크에 저장)
				// 폴더만들기
				/*
				 * String folder = "F:\\study\\upload\\" + board.getId(); File targetFolder =
				 * new File(folder); if (!targetFolder.exists()) { targetFolder.mkdirs(); } //
				 * 파일 저장 String path = folder + File.separator + file.getOriginalFilename();
				 * File target = new File(path); file.transferTo(target);
				 */

				String objectKey = "board/" + board.getId() + "/" + file.getOriginalFilename();

				// s3에 파일 업로드
				PutObjectRequest por = PutObjectRequest.builder()
						.bucket(bucketName)
						.acl(ObjectCannedACL.PUBLIC_READ)
						.key(objectKey)
						.build();
				RequestBody rb = RequestBody.fromInputStream(file.getInputStream(), file.getSize());

				s3.putObject(por, rb);

				// db에 관련정보저장 (insert)
				mapper.insertFileName(board.getId(), file.getOriginalFilename());
			}
		}
		return cnt == 1;
	}

	@Override
	public void removeByWriter(String writer) {
		// 파일명 조회
		List<Integer> idList = mapper.selectIdByWriter(writer);

		for (Integer id : idList) {
			remove(id);
		}
	}

	@Override
	public Map<String, Object> like(Authentication authentication, Like like) {
		Map<String, Object> result = new HashMap<>();

		result.put("like", false);

		like.setMemberId(authentication.getName());
		Integer deleteCnt = likeMapper.delete(like);

		if (deleteCnt != 1) {
			Integer insertCnt = likeMapper.insert(like);
			result.put("like", true);
		}
		
		Integer count = likeMapper.countByBoardId(like.getBoardId());
		result.put("count", count);

		return result;
	}



}
