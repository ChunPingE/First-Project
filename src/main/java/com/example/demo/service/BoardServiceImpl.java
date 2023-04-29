package com.example.demo.service;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import com.example.demo.domain.*;
import com.example.demo.mapper.*;

@Service
public class BoardServiceImpl implements BoardService {

	@Autowired
	private BoardMapper mapper;

	public List<Board> listBoard() {
		List<Board> list = mapper.selectAll();
		return list;
	}

	@Override
	public Map<String, Object> listBoard(Integer page) {
		Integer rowPerPage = 10;
		Integer startIndex = (page - 1) * rowPerPage;

		// 페이지 네이션이 필요한 정보
		// 전체 레코드 수
		Integer numOfRecords = mapper.countAll();

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
		List<Board> list = mapper.selectAllPaging(startIndex, rowPerPage);

		return Map.of("pageInfo", pageInfo, "boardList", list);
	}

	public Board getBoard(Integer id) {
		return mapper.selectById(id);
	}

	public boolean update(Board board) {
		int cnt = mapper.update(board);
		return cnt == 1;
	}

	public boolean remove(Integer id) {
		int cnt = mapper.deleteById(id);
		return cnt == 1;
	}

	public boolean create(Board board) {
		int cnt = mapper.insert(board);
		return cnt == 1;
	}

}
