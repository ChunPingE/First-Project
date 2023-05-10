package com.example.demo.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.security.access.prepost.*;
import org.springframework.security.core.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.*;

import com.example.demo.domain.*;
import com.example.demo.service.*;

import jakarta.servlet.*;
import jakarta.servlet.http.*;

@Controller
@RequestMapping("member")
public class MemberController {

	@Autowired
	private MemberService service;

	@GetMapping("signup")
	@PreAuthorize("isAnonymous()")
	public void signupForm() {
	}

	@GetMapping("login")
	public void loginForm() {
	}

	@PostMapping("signup")
	@PreAuthorize("isAnonymous()")
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
	@PreAuthorize("hasAuthority('admin')")
	public void memberList(Model model) {
		List<Member> list = service.listMember();
		model.addAttribute("memberList", list);
	}

	// 경로: /member/info?id=
	@GetMapping("info")
	@PreAuthorize("isAuthenticated() and (authentication.name eq #id) or hasAuthority('admin')")
	public void memberList(String id, Model model) {
		Member member = service.getInfo(id);
		model.addAttribute("member", member);
	}
	
	@PostMapping("remove")
	@PreAuthorize("isAuthenticated() and (authentication.name eq #member.id)")
	public String remove(Member member, RedirectAttributes rttr, HttpServletRequest request) throws ServletException {
		boolean ok = service.remove(member);
		if (ok) {
			rttr.addFlashAttribute("message", "회원탈퇴하였습니다.");
			//로그아웃
			request.logout();
			return "redirect:/list";
		} else {
			rttr.addFlashAttribute("message", "회원탈퇴시 문제가 발생했습니다..");
			return "redirect:/member/info?id=" + member.getId();
		}
	}

	@GetMapping("update")
	@PreAuthorize("isAuthenticated() and (authentication.name eq #id)")
	public void updateForm(String id, Model model, Authentication authentication) {
		Member member = service.getInfo(id);
		model.addAttribute("member", member);
	}
	
	@PostMapping("update")
	@PreAuthorize("isAuthenticated() and (authentication.name eq #member.id)")
	public String updatePorc(Member member, String inputPassword, RedirectAttributes rttr) {
		boolean ok = service.modify(member, inputPassword);

		if (ok) {
			rttr.addFlashAttribute("message", "회원 정보가 수정되었습니다");
			return "redirect:/member/info?id=" + member.getId();
		} else {
			rttr.addFlashAttribute("message", "회원 정보 수정에 실패하였습니다.");
			return "redirect:/member/update?id=" + member.getId();
		}
	}
}
