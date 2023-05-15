package com.example.demo.service;

import java.io.*;
import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.security.core.*;
import org.springframework.stereotype.*;
import org.springframework.web.multipart.*;

import com.example.demo.domain.*;
import com.example.demo.mapper.*;

public interface BoardService {

	public List<Board> listBoard();

	public Board getBoard(Integer id);

	public boolean update(Board board, List<String> removeFileNames, MultipartFile[] files) throws Exception;

	public boolean remove(Integer id);

	public boolean create(Board board, MultipartFile[] files) throws Exception;

	public Map<String, Object> listBoard(Integer page, String search, String type);

	public void removeByWriter(String id);

	public Integer getPrevId(Integer id);

	public Integer getNextId(Integer id);

	public Map<String, Object> like(Authentication authentication, Like like);
}
