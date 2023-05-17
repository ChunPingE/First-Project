package com.example.demo.mapper;

import org.apache.ibatis.annotations.*;

import com.example.demo.domain.*;

@Mapper
public interface BoardLikeMapper {

	Integer insert(Like like);
	
	Integer delete(Like like);
	
	Integer countByBoardId(Integer boardId);

	Like select(Integer boardId, String memberId);
	
	void removeByMemberId(String memberId);

	void deleteByBoardId(Integer boardId);
}
