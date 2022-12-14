package com.its.board.repository;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.its.board.dto.FileTestDTO;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class FileTestRepository {
	private final SqlSessionTemplate sql;

	public void save(FileTestDTO fileTestDTO) {
		sql.insert("FileTest.save", fileTestDTO);
	}

	public List<FileTestDTO> findAll() {
		return sql.selectList("FileTest.findAll");
	}

	public FileTestDTO findById(Long id) {
		return sql.selectOne("FileTest.findById", id);
	}

	public void save2(Map<String, Object> paramMap) {
		sql.insert("FileTest.save2", paramMap);
	}
	
	
}
