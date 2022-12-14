package com.its.board.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.its.board.dto.CommentDTO;
import com.its.board.repository.CommentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentService {
	private final CommentRepository commentRepository;

	public void save(CommentDTO commentDTO) {
		commentRepository.save(commentDTO);
	}

	public List<CommentDTO> findAll(Long boardId) {
		return commentRepository.findAll(boardId);
	}

	public void delete(Long id) {
		commentRepository.delete(id);
	}

	public void update(CommentDTO commentDTO) {
		commentRepository.update(commentDTO);
	}
}
