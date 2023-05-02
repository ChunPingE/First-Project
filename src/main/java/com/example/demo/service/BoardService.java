package com.example.demo.service;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.multipart.*;

import com.example.demo.domain.*;
import com.example.demo.mapper.*;

public interface BoardService {

	public List<Board> listBoard();
	
	public Board getBoard(Integer id);

	public boolean update(Board board);

	public boolean remove(Integer id);

	public boolean create(Board board, MultipartFile[] files) throws Exception;

	public Map<String, Object> listBoard(Integer page, String search, String type);
}
