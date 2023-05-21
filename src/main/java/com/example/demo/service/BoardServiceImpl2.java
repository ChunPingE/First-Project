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
public class BoardServiceImpl2 implements BoardService {

	@Value("${aws.s3.bucketName}")
	private String bucketName;

	private final S3Client s3;

	private final BoardMapper mapper;

	private final BoardLikeMapper likeMapper;

	@Autowired
	public BoardServiceImpl2(S3Client s3, BoardMapper mapper, BoardLikeMapper likeMapper) {
		this.s3 = s3;
		this.mapper = mapper;
		this.likeMapper = likeMapper;
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
	public List<Board> listBoard() {
		return null;
	}

	@Override
	public Board getBoard(Integer id, Authentication authentication) {
		return null;
	}

	@Override
	public boolean update(Board board, List<String> removeFileNames, MultipartFile[] files) throws Exception {
		return false;
	}

	@Override
	public boolean remove(Integer id) {
		return false;
	}

	@Override
	public boolean create(Board board, MultipartFile[] files) throws Exception {
		return false;
	}

	@Override
	public void removeByWriter(String id) {
	}

	@Override
	public Map<String, Object> like(Authentication authentication, Like like) {
		return null;
	}

	@Override
	public Object getBoard(Integer id) {
		return null;
	}

	
}
