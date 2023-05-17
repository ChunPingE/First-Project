package com.example.demo.mapper;

import org.apache.ibatis.session.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import com.example.demo.domain.*;

public class BoardLikeDAO implements BoardLikeMapper{

    private BoardLikeMapper mapper;
    
    @Autowired
    public BoardLikeDAO(SqlSession sqlSession) {
        mapper = sqlSession.getMapper(BoardLikeMapper.class);
    }

	@Override
	public Integer insert(Like like) {
		return mapper.insert(like);
	}

	@Override
	public Integer delete(Like like) {
		return mapper.delete(like);
	}

	@Override
	public Integer countByBoardId(Integer boardId) {
		return mapper.countByBoardId(boardId);
	}

	@Override
	public Like select(Integer boardId, String memberId) {
		return mapper.select(boardId, memberId);
	}

	@Override
	public void removeByMemberId(String memberId) {
	}

	@Override
	public void deleteByBoardId(Integer boardId) {
	}
}
