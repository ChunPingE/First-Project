package com.example.demo.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.*;

import com.example.demo.domain.*;
import com.example.demo.service.*;

@Controller
@RequestMapping("/")
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	//게시물 목록
	// 경로 : http://localhost:8080
	// 경로 : http://localhost:8080/list
	//@RequestMapping({"/", "list"}, method = RequestMethod.GET)
	@GetMapping({"/", "list"})
	public String list(Model model) {
		// 1. request param 수집가공
		// 2. business logic 처리
		List<Board> list = boardService.listBoard();
		// 3. add attribute
		model.addAttribute("boardList", list);
		// 4. fowrard/redirect
		return "list";
	}
	
	@GetMapping("/detail/{id}")
	public String detail(@PathVariable("id") Integer id,  Model model) {
		// 1. request param 수집가공
		// 2. business logic 처리
		Board board = boardService.getBoard(id);
		// 3. add attribute
		model.addAttribute("board", board);
		// 4. fowrard/redirect
		return "detail";
	}
	
	@GetMapping("/update/{id}")
	public String update(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("board", boardService.getBoard(id));
		return "update";
	}
	
	//@RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
	@PostMapping("/update/{id}")
	public String updateProcess(Board board, RedirectAttributes rttr) {
		
		boolean ok = boardService.update(board);
		if (ok) {
			//해당게시물 보기로 리디렉션
			rttr.addFlashAttribute("success", "modifySuccess");
			return "redirect:/detail/" + board.getId();
		} else {
			//수정폼으로 리디렉션
			rttr.addFlashAttribute("fail", "modifyFail");
			return "redirect:/update/" + board.getId();
		}
	}
	
	@PostMapping("remove")
	public String update(Integer id, RedirectAttributes rttr) {
		boolean ok = boardService.remove(id);
		if (ok) {
			rttr.addFlashAttribute("success", "removeScucess");
			return "redirect:/list";
		} else {
			rttr.addFlashAttribute("fail", "removeFail");
			return "redirect:/detail/" + id;
		}
	}
}
