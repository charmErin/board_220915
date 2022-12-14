package com.its.board.service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.its.board.dto.BoardDTO;
import com.its.board.dto.PageDTO;
import com.its.board.dto.SearchDTO;
import com.its.board.repository.BoardRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardService {
	private final BoardRepository boardRepository;

	public int save(BoardDTO boardDTO) {
		return boardRepository.save(boardDTO);
	}

	public List<BoardDTO> findAll(int categoryId) {
		return boardRepository.findAll(categoryId);
	}

	public BoardDTO findById(Long id) {
		return boardRepository.findById(id);
	}
	
	public BoardDTO detail(Long id) {
		return boardRepository.detail(id);
	}

	public void update(BoardDTO boardDTO) {
		boardRepository.update(boardDTO);
	}

	public void delete(Long id) {
		boardRepository.delete(id);
	}

	public List<BoardDTO> search(String searchType, String q) {
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("type", searchType);
		searchMap.put("q", q);
		return boardRepository.search(searchMap);
	}
	
	
	private static final int PAGE_LIMIT = 5;
	private static final int BLOCK_LIMIT = 3;

	public List<BoardDTO> pagingList(int categoryId, int page) {
		int pagingStart = (page-1) * PAGE_LIMIT;
		Map<String, Integer> pagingMap = new HashMap<String, Integer>();
		pagingMap.put("categoryId", categoryId);
		pagingMap.put("start", pagingStart);
		pagingMap.put("count", PAGE_LIMIT);
		return boardRepository.pagingList(pagingMap);
	}

	public PageDTO paging(int categoryId, int page) {
		Long boardCount = boardRepository.boardCount(categoryId);
		
        int maxPage = (int)(Math.ceil((double)boardCount / PAGE_LIMIT));
        int startPage = (((int)(Math.ceil((double)page / BLOCK_LIMIT))) - 1) * BLOCK_LIMIT + 1;
        int endPage = startPage + BLOCK_LIMIT - 1;
        
        if(endPage > maxPage) {
        	endPage = maxPage;        	
        }
        
        PageDTO pageDTO = new PageDTO();
        pageDTO.setPage(page);
        pageDTO.setStartPage(startPage);
        pageDTO.setEndPage(endPage);
        pageDTO.setMaxPage(maxPage);
        return pageDTO;
	}

	public List<BoardDTO> searchList(int page, String searchType, String q) {
		int pagingStart = (page-1) * PAGE_LIMIT;
		SearchDTO searchDTO = new SearchDTO();
		searchDTO.setSearchType(searchType);
		searchDTO.setQ(q);
		searchDTO.setStart(pagingStart);
		searchDTO.setCount(PAGE_LIMIT);
		return boardRepository.searchList(searchDTO);
	}

	public PageDTO searchPaging(int page, String searchType, String q) {
		SearchDTO searchDTO = new SearchDTO();
		searchDTO.setSearchType(searchType);
		searchDTO.setQ(q);
		Long boardCount = boardRepository.searchBoardCount(searchDTO);
		
        int maxPage = (int)(Math.ceil((double)boardCount / PAGE_LIMIT));
        int startPage = (((int)(Math.ceil((double)page / BLOCK_LIMIT))) - 1) * BLOCK_LIMIT + 1;
        int endPage = startPage + BLOCK_LIMIT - 1;
        
        if(endPage > maxPage) {
        	endPage = maxPage;        	
        }
        
        PageDTO pageDTO = new PageDTO();
        pageDTO.setPage(page);
        pageDTO.setStartPage(startPage);
        pageDTO.setEndPage(endPage);
        pageDTO.setMaxPage(maxPage);
        return pageDTO;
	}

	
}
