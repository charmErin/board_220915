package com.its.board.repository;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.its.board.dto.MemberDTO;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

	private final SqlSessionTemplate sql;
	
	// 회원 가입
	public int save(MemberDTO memberDTO) {
		return sql.insert("Member.save", memberDTO);
	}

	// 로그인
	public MemberDTO login(MemberDTO memberDTO) {
		return sql.selectOne("Member.login", memberDTO);
	}

	// 회원 목록
	public List<MemberDTO> findAll() {
		return sql.selectList("Member.findAll");
	}
	
	// 회원 상세 정보
	public MemberDTO findById(Long id) {
		return sql.selectOne("Member.findById", id);
	}

	// 회원 정보 수정
	public void update(MemberDTO memberDTO) {
		sql.update("Member.update", memberDTO);
	}

	// 회원 삭제 (탈퇴)
	public void delete(Long id) {
		sql.delete("Member.delete", id);
	}

	public MemberDTO dupCheck(String loginId) {
		return sql.selectOne("Member.dupCheck", loginId);
	}

}
