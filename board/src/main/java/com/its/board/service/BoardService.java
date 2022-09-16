package com.its.board.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.its.board.dto.BoardDTO;
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
	
	
}
