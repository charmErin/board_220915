package com.its.board.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.its.board.dto.MemberDTO;
import com.its.board.service.MemberService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
	private final MemberService memberService;
	
	// 회원가입 페이지 요청
	@GetMapping("/save-form")
	public String saveForm() {
		return "member/save";
	}
	
	// 아이디 중복 체크
	@PostMapping("/dup-check")
	public @ResponseBody String dupCheck(@RequestParam String loginId) {
		MemberDTO findDTO = memberService.dupCheck(loginId);
		if (findDTO == null) {
			return "ok";
		} else {
			return "no";
		}
	}
	
	// 회원가입 처리
	@PostMapping("/save")
	public String save(@ModelAttribute MemberDTO memberDTO) {
		boolean result = memberService.save(memberDTO);
		if (result) {
			return "member/login";
		} else {
			System.out.println("회원가입 실패");
			return "index";
		}
	}
	
	// 로그인 페이지 요청
	@GetMapping("/login-form")
	public String loginForm() {
		return "member/login";
	}
	
	// 로그인 처리
	@PostMapping("/login")
	public String login(@ModelAttribute MemberDTO memberDTO,
						HttpSession session) {
		MemberDTO loginDTO = memberService.login(memberDTO);
		if (loginDTO != null) {
			session.setAttribute("id", loginDTO.getId());
			session.setAttribute("loginId", loginDTO.getLoginId());
			session.setAttribute("memberName", loginDTO.getMemberName());
			return "index";
		} else {
			System.out.println("로그인 실패");
			return "member/login";
		}
	}
	
	// 로그아웃 처리
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
	
	// 회원 목록 조회
	@GetMapping("/findAll")
	public String findAll(Model model) {
		List<MemberDTO> memberDTOList = memberService.findAll();
		model.addAttribute("memberList", memberDTOList);
		return "member/list";
	}
	
	// 회원 상세 조회
	@GetMapping("/detail/{id}")
	public String findById(@PathVariable Long id, Model model) {
		MemberDTO memberDTO = memberService.findById(id);
		model.addAttribute("member", memberDTO);
		return "member/detail";
	}
	
	// 회원 정보 수정 페이지 요청
	@GetMapping("/update-form/{id}")
	public String updateForm(@PathVariable Long id, Model model) {
		MemberDTO memberDTO = memberService.findById(id);
		model.addAttribute("member", memberDTO);
		return "member/update";
	}
	
	// 회원 정보 수정 처리
	@PostMapping("/update")
	public String update(@ModelAttribute MemberDTO memberDTO) {
		memberService.update(memberDTO);
		return "redirect:/member/detail/" + memberDTO.getId();
	}
	
	// 회원 삭제(탈퇴) 처리
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable Long id, HttpSession session) {
		memberService.delete(id);
		String loginId = (String) session.getAttribute("loginId");
		if (loginId.equals("admin")) {
			return "redirect:/member/findAll";
		} else {
			session.invalidate();
			return "redirect:/";
		}
	}

}
