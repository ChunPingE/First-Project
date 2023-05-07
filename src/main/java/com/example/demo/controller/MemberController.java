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
@RequestMapping("member")
public class MemberController {

	@Autowired
	private MemberService service;

	@GetMapping("signup")
	public void signupForm() {
	}

	@PostMapping("signup")
	public String signupProcess(Member member, RedirectAttributes rttr) {

		try {
			service.signUp(member);
			rttr.addFlashAttribute("message", "회원가입에 성공했습니다.");
			return "redirect:/list";

		} catch (Exception e) {
			rttr.addFlashAttribute("member", member);
			rttr.addFlashAttribute("message", "회원가입 중 문제가 발생했습니다.");
			return "redirect:/member/signup";
		}
	}
	
	@GetMapping("list")
	public void memberList(Model model) {
		List<Member> list = service.listMember();
		model.addAttribute("memberList", list);
	}
	
	// 경로: /member/info?id=
	@GetMapping("info")
	public void memberList(String id, Model model) {
		Member member = service.getInfo(id);
		model.addAttribute("member", member);
	}
}
