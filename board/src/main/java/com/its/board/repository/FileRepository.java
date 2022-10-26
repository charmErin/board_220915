package com.its.board.repository;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.its.board.dto.FileDTO;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class FileRepository {
	private final SqlSessionTemplate sql;

	public void save(FileDTO fileDTO) {
		sql.insert("File.save", fileDTO);
	}

	public List<FileDTO> findAll(Long id) {
		return sql.selectList("File.findAll", id);
	}

	public FileDTO findById(Long id) {
		return sql.selectOne("File.findById", id);
	}

	public void delete(Long id) {
		sql.delete("File.delete", id);
	}
	
	

}
