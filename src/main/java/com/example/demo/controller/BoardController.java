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
	private BoardService service;

	// 게시물 목록
	// 경로 : http://localhost:8080?page=3
	// 경로 : http://localhost:8080/list?page=5
	// @RequestMapping({"/", "list"}, method = RequestMethod.GET)
	@GetMapping({ "/", "list" })
	public String list(Model model, @RequestParam(value = "page", defaultValue = "1") Integer page) {
		// 1. request param 수집가공
		// 2. business logic 처리
		// List<Board> list = service.listBoard(); //페이지 처리전
		Map<String, Object> result = service.listBoard(page); // 페이지 처리후
		// 3. add attribute
		//model.addAttribute("boardList", result.get("list"));
		//model.addAttribute("pageInfo", result.get("pageInfo"));
		model.addAllAttributes(result);
		// 4. fowrard/redirect
		return "list";
	}

	@GetMapping("/detail/{id}")
	public String detail(@PathVariable("id") Integer id, Model model) {
		// 1. request param 수집가공
		// 2. business logic 처리
		Board board = service.getBoard(id);
		// 3. add attribute
		model.addAttribute("board", board);
		// 4. fowrard/redirect
		return "detail";
	}

	@GetMapping("/update/{id}")
	public String update(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("board", service.getBoard(id));
		return "update";
	}

	// @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
	@PostMapping("/update/{id}")
	public String updateProcess(Board board, RedirectAttributes rttr) {

		boolean ok = service.update(board);
		if (ok) {
			// 해당게시물 보기로 리디렉션
			rttr.addFlashAttribute("success", "modifySuccess");
			rttr.addFlashAttribute("message", board.getId() + "번 게시물이 수정되었습니다.");
			return "redirect:/detail/" + board.getId();
		} else {
			// 수정폼으로 리디렉션
			rttr.addFlashAttribute("fail", "modifyFail");
			rttr.addFlashAttribute("message", "게시물이 수정되지 않았습니다.");
			return "redirect:/update/" + board.getId();
		}
	}

	@PostMapping("remove")
	public String update(Integer id, RedirectAttributes rttr) {
		boolean ok = service.remove(id);
		if (ok) {
			rttr.addFlashAttribute("success", "removeSucess");
			rttr.addFlashAttribute("message", id + "번 게시물이 삭제되었습니다.");
			return "redirect:/list";
		} else {
			rttr.addFlashAttribute("fail", "removeFail");
			return "redirect:/detail/" + id;
		}
	}

	// 인서트 기능 내맘대로 추가
	// 연습해보기
	@GetMapping("add")
	public String addForm() {
		// 게시물 작성 form(view)로 포워드
		return "add";
	}

	@PostMapping("add")
	public String addProcess(Board board, RedirectAttributes rttr) {
		// 새 게시물 db에 추가
		boolean ok = service.create(board);

		if (ok) {
			rttr.addFlashAttribute("success", "insertScucess");
			rttr.addFlashAttribute("message", "게시물이 등록되었습니다.");
			// return "redirect:/list";
			return "redirect:/detail/" + board.getId();
		} else {
			rttr.addFlashAttribute("fail", "insertFail");
			rttr.addFlashAttribute("message", "게시물 등록에 실패했습니다. 다시입력해주세요");
			rttr.addFlashAttribute("board", board);
			return "redirect:/add";
		}
	}
}
