package com.its.board.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.its.board.dto.MemberDTO;
import com.its.board.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {
	
	private final MemberRepository memberRepository;
	
	public boolean save(MemberDTO memberDTO) {
		int result = memberRepository.save(memberDTO);
		if (result > 0) {
			return true;
		} else {
			return false;
		}
	}

	public MemberDTO login(MemberDTO memberDTO) {
		return memberRepository.login(memberDTO);
	}

	public List<MemberDTO> findAll() {
		return memberRepository.findAll();
	}

	public MemberDTO findById(Long id) {
		return memberRepository.findById(id);
	}

	public void update(MemberDTO memberDTO) {
		memberRepository.update(memberDTO);
	}

	public void delete(Long id) {
		memberRepository.delete(id);
	}

	public MemberDTO dupCheck(String loginId) {
		return memberRepository.dupCheck(loginId);
	}

}
