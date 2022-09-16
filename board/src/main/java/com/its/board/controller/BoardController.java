package com.its.board.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.its.board.dto.BoardDTO;
import com.its.board.dto.CommentDTO;
import com.its.board.service.BoardService;
import com.its.board.service.CommentService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {
	private final BoardService boardService;
	private final CommentService commentService;
	
	@GetMapping("/save-form")
	public String saveForm() {
		return "board/save";
	}
	
	@PostMapping("/save")
	public String save(@ModelAttribute BoardDTO boardDTO) throws IllegalStateException, IOException {
		int result = boardService.save(boardDTO);
		if (result > 0) {
			return "redirect:/board/" + boardDTO.getCategoryId();
		} else {
			System.out.println("글 저장실패");
			return "index";
		}
	}
	
	@GetMapping("/{categoryId}")
	public String findAll(@PathVariable int categoryId, Model model) {
		model.addAttribute("boardList", boardService.findAll(categoryId));
		model.addAttribute("category", categoryId);
		return "board/list";
	}
	
	@GetMapping("/detail/{id}")
	public String findById(@PathVariable Long id, Model model) {
		BoardDTO boardDTO = boardService.detail(id);
		model.addAttribute("board", boardDTO);
		
		List<CommentDTO> commentDTOList = commentService.findAll(id);
		model.addAttribute("commentList", commentDTOList);
		return "board/detail";
	}
	
	@GetMapping("/update-form")
	public String updateForm(@RequestParam Long id, Model model) {
		BoardDTO boardDTO = boardService.findById(id);
		model.addAttribute("board", boardDTO);
		return "board/update";
	}
	
	@PostMapping("/update")
	public String update(@ModelAttribute BoardDTO boardDTO) throws IllegalStateException, IOException {
		boardService.update(boardDTO);
		return "redirect:/board/" + boardDTO.getCategoryId();
	}
	
	@GetMapping("/delete")
	public String delete(@RequestParam Long id) {
		int categoryId = boardService.findById(id).getCategoryId();
		boardService.delete(id);
		return "redirect:/board/" + categoryId;
	}
	
	@GetMapping("/search")
	public String search(@RequestParam String searchType, @RequestParam String q,
						Model model) {
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("type", searchType);
		searchMap.put("q", q);
		List<BoardDTO> boardDTOList = boardService.search(searchMap);
		model.addAttribute("boardList", boardDTOList);
		return "board/list";
	}
	
	
	
}
