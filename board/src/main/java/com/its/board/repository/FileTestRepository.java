package com.its.board.repository;

import java.util.List;

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
	
	
}
