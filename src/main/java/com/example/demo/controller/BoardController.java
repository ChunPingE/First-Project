package com.example.demo.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.security.access.prepost.*;
import org.springframework.security.core.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.*;
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
	public String list(Model model,
			@RequestParam(value = "page", defaultValue = "1") Integer page,
			@RequestParam(value = "search", defaultValue = "") String search,
			@RequestParam(value = "type", required = false) String type) {
		// 1. request param 수집가공
		// 2. business logic 처리
		// List<Board> list = service.listBoard(); //페이지 처리전
		Map<String, Object> result = service.listBoard(page, search, type); // 페이지 처리후
		// 3. add attribute
		// model.addAttribute("boardList", result.get("list"));
		// model.addAttribute("pageInfo", result.get("pageInfo"));
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
	@PreAuthorize("isAuthenticated() and @customSecurityChecker.checkBoardWriter(authentication, #id)")
	public String update(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("board", service.getBoard(id));
		return "update";
	}

	// @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
	@PostMapping("/update/{id}")
	@PreAuthorize("isAuthenticated() and @customSecurityChecker.checkBoardWriter(authentication, #board.id)")
	//수정하려는 게시물 id : board.id를 전달하던지 board를 전달
	public String updateProcess(Board board,
			@RequestParam(value = "removeFiles", required = false) List<String> removeFileNames,
			@RequestParam(value = "files", required = false) MultipartFile[] files,
			RedirectAttributes rttr) {
		try {
			boolean ok = service.update(board, removeFileNames, files);
			if (ok) {
				// 해당게시물 보기로 리디렉션
				rttr.addFlashAttribute("message", board.getId() + "번 게시물이 수정되었습니다.");
				return "redirect:/detail/" + board.getId();
			} else {
				// 수정폼으로 리디렉션
				rttr.addFlashAttribute("message", "게시물이 수정되지 않았습니다.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/update/" + board.getId();
	}

	@PostMapping("remove")
	@PreAuthorize("isAuthenticated() and @customSecurityChecker.checkBoardWriter(authentication, #id)")
	public String update(Integer id, RedirectAttributes rttr) {
		boolean ok = service.remove(id);
		if (ok) {
			rttr.addFlashAttribute("message", id + "번 게시물이 삭제되었습니다.");
			return "redirect:/list";
		} else {
			rttr.addFlashAttribute("message", "게시물이 삭제되지 않았습니다.");
			return "redirect:/detail/" + id;
		}
	}

	// 인서트 기능 내맘대로 추가
	// 연습해보기
	@GetMapping("add")
	@PreAuthorize("isAuthenticated()")
	public String addForm() {
		// 게시물 작성 form(view)로 포워드
		return "add";
	}

	@PostMapping("add")
	@PreAuthorize("isAuthenticated()")
	public String addProcess(
			@RequestParam("files") MultipartFile[] files,
			Board board,
			RedirectAttributes rttr,
			Authentication authentication) {
		// 새 게시물 db에 추가
		try {
			board.setWriter(authentication.getName());
			boolean ok = service.create(board, files);
			if (ok) {
				rttr.addFlashAttribute("message", "게시물이 등록되었습니다.");
				// return "redirect:/list";
				return "redirect:/detail/" + board.getId();
			} else {
				rttr.addFlashAttribute("message", "게시물 등록에 실패했습니다. 다시입력해주세요");
				rttr.addFlashAttribute("board", board);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/add";
	}
}
