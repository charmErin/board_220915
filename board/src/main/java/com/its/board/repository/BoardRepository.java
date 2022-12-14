package com.its.board.repository;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.its.board.dto.BoardDTO;
import com.its.board.dto.SearchDTO;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class BoardRepository {
	private final SqlSessionTemplate sql;

	public int save(BoardDTO boardDTO) {
		return sql.insert("Board.save", boardDTO);
	}

	public List<BoardDTO> findAll(int categoryId) {
		return sql.selectList("Board.findAll", categoryId);
	}

	public BoardDTO detail(Long id) {
		sql.update("Board.updateHits", id);
		return sql.selectOne("Board.findById", id);
	}
	
	public BoardDTO findById(Long id) {
		return sql.selectOne("Board.findById", id);
	}

	public void update(BoardDTO boardDTO) {
		sql.update("Board.update", boardDTO);
	}

	public void delete(Long id) {
		sql.delete("Board.delete", id);
	}

	public List<BoardDTO> search(Map<String, String> searchMap) {
		return sql.selectList("Board.search", searchMap);
	}

	public List<BoardDTO> pagingList(Map<String, Integer> pagingMap) {
		return sql.selectList("Board.pagingList", pagingMap);
	}

	public Long boardCount(int categoryId) {
		return sql.selectOne("Board.count", categoryId);
	}

	public List<BoardDTO> searchList(SearchDTO searchDTO) {
		return sql.selectList("Board.searchList", searchDTO);
	}

	public Long searchBoardCount(SearchDTO searchDTO) {
		return sql.selectOne("Board.searchCount", searchDTO);
	}

	
	
	
}
