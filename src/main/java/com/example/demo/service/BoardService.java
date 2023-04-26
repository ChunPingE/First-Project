package com.example.demo.service;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import com.example.demo.domain.*;
import com.example.demo.mapper.*;

public interface BoardService {

	public List<Board> listBoard();
	public Board getBoard(Integer id);
	public boolean update(Board board);
	public boolean remove(Integer id);
	public boolean create(Board board);
}
