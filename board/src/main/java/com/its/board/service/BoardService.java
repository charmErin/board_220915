package com.its.board.service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.its.board.dto.BoardDTO;
import com.its.board.dto.PageDTO;
import com.its.board.repository.BoardRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardService {
	private final BoardRepository boardRepository;

	public int save(BoardDTO boardDTO) throws IllegalStateException, IOException {
		MultipartFile boardFile = boardDTO.getBoardFile();

		if (!boardFile.isEmpty()) {
			String boardFileName = boardFile.getOriginalFilename();
			boardFileName = System.currentTimeMillis() + "_" + boardFileName;
			String savePath = "D:\\eclipse_file\\" + boardFileName;
			boardFile.transferTo(new File(savePath));
			boardDTO.setBoardFileName(boardFileName);
		}
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

	public void update(BoardDTO boardDTO) throws IllegalStateException, IOException {
		MultipartFile boardFile = boardDTO.getBoardFile();

		if (!boardFile.isEmpty()) {
			String boardFileName = boardFile.getOriginalFilename();
			boardFileName = System.currentTimeMillis() + "_" + boardFileName;
			String savePath = "D:\\eclipse_img\\" + boardFileName;
			boardFile.transferTo(new File(savePath));
			boardDTO.setBoardFileName(boardFileName);
		}
		boardRepository.update(boardDTO);
	}

	public void delete(Long id) {
		boardRepository.delete(id);
	}

	public List<BoardDTO> search(Map<String, String> searchMap) {
		return boardRepository.search(searchMap);
	}
	
	
	private static final int PAGE_LIMIT = 3;
	private static final int BLOCK_LIMIT = 5;

	public List<BoardDTO> pagingList(int page, int categoryId) {
		int pagingStart = (page-1) * PAGE_LIMIT;
		Map<String, Integer> pagingMap = new HashMap<String, Integer>();
		pagingMap.put("categoryId", categoryId);
		pagingMap.put("start", pagingStart);
		pagingMap.put("count", PAGE_LIMIT);
		return boardRepository.pagingList(pagingMap);
	}

	public PageDTO paging(int page, int categoryId) {
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

	
}
