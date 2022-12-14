package com.its.board.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.its.board.dto.CommentDTO;
import com.its.board.service.CommentService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {
	private final CommentService commentService;
	
	@PostMapping("/save")
	public @ResponseBody List<CommentDTO> save(@ModelAttribute CommentDTO commentDTO) {
		commentService.save(commentDTO);
		return commentService.findAll(commentDTO.getBoardId());
	}
	
	@PostMapping
	public @ResponseBody List<CommentDTO> delete(@RequestParam Long id,
												@RequestParam Long boardId) {
		commentService.delete(id);
		return commentService.findAll(boardId);
	}
	
	@PostMapping("/update")
	public @ResponseBody List<CommentDTO> update(@ModelAttribute CommentDTO commentDTO) {
		commentService.update(commentDTO);
		return commentService.findAll(commentDTO.getBoardId());
	}
	
}
