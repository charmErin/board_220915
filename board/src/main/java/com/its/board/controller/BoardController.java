package com.its.board.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.its.board.dto.BoardDTO;
import com.its.board.dto.CommentDTO;
import com.its.board.dto.FileDTO;
import com.its.board.dto.FileTestDTO;
import com.its.board.dto.PageDTO;
import com.its.board.service.BoardService;
import com.its.board.service.CommentService;
import com.its.board.service.FileService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {
	private final BoardService boardService;
	private final FileService fileService;
	private final CommentService commentService;
	
	// 글 저장 페이지 요청
	@GetMapping("/save-form")
	public String saveForm() {
		return "board/save";
	}
	
	
	// 글 저장 처리
//	@PostMapping("/save")
//	public String save(@ModelAttribute BoardDTO boardDTO) throws IllegalStateException, IOException {
//		int result = boardService.save(boardDTO);
//		if (result > 0) {
//			return "redirect:/board/detail/" + boardDTO.getCategoryId() + "?page=1&id=" + boardDTO.getId();
//		} else {
//			System.out.println("글 저장실패");
//			return "index";
//		}
//	}
	
	@PostMapping("/save")
	public String save(@ModelAttribute BoardDTO boardDTO,
						MultipartFile[] boardFile) throws IllegalStateException, IOException {
		int result = boardService.save(boardDTO);
		if (result > 0) {
			for	(MultipartFile m: boardFile) {
				if (!m.isEmpty()) {
					String fileName = m.getOriginalFilename();
					File f = new File("D:\\image", fileName);
					ByteArrayOutputStream bos = new ByteArrayOutputStream();
					FileInputStream fis = new FileInputStream(f);
					
					int readCount = 0;
					byte[] buffer = new byte[1024];
					while((readCount = fis.read(buffer)) != -1) {
						bos.write(buffer, 0, readCount);
					}
					
					byte[] bis = bos.toByteArray();
					
					FileDTO fileDTO= new FileDTO();
					fileDTO.setBoardId(boardDTO.getId());
					fileDTO.setBoardFile(bis);
					fileDTO.setBoardFileName(fileName);
					fileDTO.setBoardFileSize(m.getSize());
					
					fileService.save(fileDTO);
				}
			}
			return "redirect:/board/detail/" + boardDTO.getCategoryId() + "?page=1&id=" + boardDTO.getId();
		} else {
			System.out.println("글 저장실패");
			return "index";
		}
	}
	
	
	
	// 글 페이징 목록
	@GetMapping("/{categoryId}")
	public String paging(@PathVariable int categoryId,
						@RequestParam(value="page", required=false, defaultValue="1") int page,
						Model model) {
		List<BoardDTO> boardDTOList = boardService.pagingList(categoryId, page);
		PageDTO pageDTO = boardService.paging(categoryId, page);
		model.addAttribute("boardList", boardDTOList);
		model.addAttribute("pageDTO", pageDTO);
		model.addAttribute("category", categoryId);
		return "board/list";
	}
	
	
	// 글 상세 페이지
	@GetMapping("/detail/{categoryId}")
	public String findById(@PathVariable int categoryId,
							@RequestParam(value="page", required=false, defaultValue="1") int page,
							@RequestParam Long id, Model model) {
		BoardDTO boardDTO = boardService.detail(id);
		boardDTO.setBoardFileList(fileService.findAll(id));
		model.addAttribute("board", boardDTO);
		
		List<CommentDTO> commentDTOList = commentService.findAll(id);
		model.addAttribute("commentList", commentDTOList);
		
		model.addAttribute("categoryId", categoryId);
		model.addAttribute("page", page);
		return "board/detail";
	}
	
	
	// 글 수정 페이지 요청
	@GetMapping("/update-form")
	public String updateForm(@RequestParam(value="page", required=false, defaultValue="1") int page,
							@RequestParam Long id, Model model) {
		BoardDTO boardDTO = boardService.findById(id);
		boardDTO.setBoardFileList(fileService.findAll(id));
		model.addAttribute("board", boardDTO);
		model.addAttribute("page", page);
		return "board/update";
	}
	
	
	// 글 수정 처리
	@PostMapping("/update")
	public String update(@ModelAttribute BoardDTO boardDTO, MultipartFile[] boardFile,
						@RequestParam int page) throws IllegalStateException, IOException {
		boardService.update(boardDTO);
		for	(MultipartFile m: boardFile) {
			if (!m.isEmpty()) {
				String fileName = m.getOriginalFilename();
				File f = new File("D:\\image", fileName);
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				FileInputStream fis = new FileInputStream(f);
				
				int readCount = 0;
				byte[] buffer = new byte[1024];
				while((readCount = fis.read(buffer)) != -1) {
					bos.write(buffer, 0, readCount);
				}
				
				byte[] bis = bos.toByteArray();
				
				FileDTO fileDTO= new FileDTO();
				fileDTO.setBoardId(boardDTO.getId());
				fileDTO.setBoardFile(bis);
				fileDTO.setBoardFileName(fileName);
				fileDTO.setBoardFileSize(m.getSize());
				
				fileService.save(fileDTO);
			}
		}
		return "redirect:/board/detail/" + boardDTO.getCategoryId() + "?page=" + page + "&id=" + boardDTO.getId();
	}
	
	
	// 글 삭제 처리
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable Long id) {
		int categoryId = boardService.findById(id).getCategoryId();
		boardService.delete(id);
		return "redirect:/board/" + categoryId;
	}
	
	
	// 글 검색 목록
//	@GetMapping("/search")
//	public String search(@RequestParam String searchType, @RequestParam String q,
//						Model model) {
//		List<BoardDTO> boardDTOList = boardService.search(searchType, q);
//		model.addAttribute("boardList", boardDTOList);
//		return "board/list";
//	}
	
	
	// 글 검색 페이징 목록
	@GetMapping("/search")
	public String searchList(@RequestParam(value="page", required=false, defaultValue="1") int page,
							@RequestParam String searchType,
							@RequestParam String q, Model model) {
		List<BoardDTO> searchList = boardService.searchList(page, searchType, q);
		PageDTO pageDTO = boardService.searchPaging(page, searchType, q);
		model.addAttribute("searchList", searchList);
		model.addAttribute("pageDTO", pageDTO);
		model.addAttribute("searchType", searchType);
		model.addAttribute("q", q);
		return "board/search";
	}
	
	
	// 검색글 상세 페이지
	@GetMapping("/search-detail")
	public String searchDetail(@RequestParam(value="page", required=false, defaultValue="1") int page,
							@RequestParam String searchType,
							@RequestParam String q,
							@RequestParam Long id, Model model) {
		BoardDTO boardDTO = boardService.detail(id);
		boardDTO.setBoardFileList(fileService.findAll(id));
		model.addAttribute("board", boardDTO);
		
		List<CommentDTO> commentDTOList = commentService.findAll(id);
		model.addAttribute("commentList", commentDTOList);
		
		model.addAttribute("page", page);
		model.addAttribute("searchType", searchType);
		model.addAttribute("q", q);
		return "board/searchDetail";
	}

	
	// 검색글 상세페이지 수정 요청
	@GetMapping("/search/update-form")
	public String searchUpdateForm(@RequestParam String searchType, @RequestParam String q,
								@RequestParam(value="page", required=false, defaultValue="1") int page,
								@RequestParam Long id, Model model) {
		BoardDTO boardDTO = boardService.findById(id);
		boardDTO.setBoardFileList(fileService.findAll(id));
		model.addAttribute("board", boardDTO);
		model.addAttribute("page", page);
		model.addAttribute("searchType", searchType);
		model.addAttribute("q", q);
		return "board/searchUpdate";
	}
	
	
	// 검색글 수정 처리
	@PostMapping("/search/update")
	public String searchUpdate(@RequestParam String searchType, @RequestParam String q,
							@ModelAttribute BoardDTO boardDTO, MultipartFile[] boardFile,
							@RequestParam int page) throws IllegalStateException, IOException {
		String encodedParam = URLEncoder.encode(q, "UTF-8");
		boardService.update(boardDTO);
		for	(MultipartFile m: boardFile) {
			if (!m.isEmpty()) {
				String fileName = m.getOriginalFilename();
				File f = new File("D:\\image", fileName);
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				FileInputStream fis = new FileInputStream(f);
				
				int readCount = 0;
				byte[] buffer = new byte[1024];
				while((readCount = fis.read(buffer)) != -1) {
					bos.write(buffer, 0, readCount);
				}
				
				byte[] bis = bos.toByteArray();
				
				FileDTO fileDTO= new FileDTO();
				fileDTO.setBoardId(boardDTO.getId());
				fileDTO.setBoardFile(bis);
				fileDTO.setBoardFileName(fileName);
				fileDTO.setBoardFileSize(m.getSize());
				
				fileService.save(fileDTO);
			}
		}
		return "redirect:/board/search-detail?searchType=" + searchType + "&q=" + encodedParam + "&page=" + page + "&id=" + boardDTO.getId();
	}
	
	
	// 파일 다운로드
	@GetMapping("/download/{id}")
	public void download3(HttpServletResponse response, @PathVariable Long id) throws IOException {
		FileDTO fileDTO = fileService.findById(id);
		String fileName = fileDTO.getBoardFileName();
		byte[] bytes = fileDTO.getBoardFile();
		ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
		OutputStream os = response.getOutputStream();
		
		response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\";");
		response.setContentType("application/octer-stream");
		response.setHeader("Content-Transfer-Encoding", "binary");
		response.setHeader("Pragma", "no-cache;");
		response.setHeader("Expires", "-1;");
		
		byte[] buffer = new byte[1024*8];
		while(true) {
			int x = bis.read(buffer);
			if (x == -1) {
				break;
			}
			os.write(buffer, 0, x);
		}
		os.close();
		bis.close();
	}
	
	// 파일 삭제
	@GetMapping("/file-delete")
	public @ResponseBody List<FileDTO> fileDelete(@RequestParam Long id, @RequestParam Long boardId) {
		fileService.delete(id);
		return fileService.findAll(boardId);
	}
	
}
