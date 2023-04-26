package com.example.demo.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;

import com.example.demo.domain.*;
import com.example.demo.mapper.*;

@Controller
@RequestMapping("/")
public class BoardController {
	
	@Autowired
	private BoardMapper mapper;
	
	//게시물 목록
	// 경로 : http://localhost:8080
	// 경로 : http://localhost:8080/list
	//@RequestMapping({"/", "list"}, method = RequestMethod.GET)
	@GetMapping({"/", "list"})
	public String list(Model model) {
		// 1. request param 수집가공
		// 2. business logic 처리
		List<Board> list = mapper.selectAll();
		// 3. add attribute
		model.addAttribute("boardList", list);
		// 4. fowrard/redirect
		return "list";
	}
}
