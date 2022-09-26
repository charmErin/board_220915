package com.its.board.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.Blob;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.its.board.dto.FileTestDTO;
import com.its.board.service.FileTestService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/file-test")
public class FileTestController {
	private final FileTestService fileTestService;
	
	@GetMapping("/save-form")
	public String saveForm() {
		return "test/save";
	}
	
	@PostMapping("/save")
	public String save(MultipartFile[] blobFile, Model model) throws IOException {
		for (MultipartFile multipartFile: blobFile) {
			FileTestDTO fileTestDTO = new FileTestDTO();
			fileTestDTO.setFileName(multipartFile.getOriginalFilename());
			fileTestDTO.setBlobFile(multipartFile.getBytes());
			fileTestDTO.setFileSize(multipartFile.getSize());
			
			fileTestService.save(fileTestDTO);
		}
		return "redirect:/file-test/findAll";
	}
	
	@GetMapping("/findAll")
	public String findAll(Model model) throws IOException {
		List<FileTestDTO> fileTestDTOList = fileTestService.findAll();
		model.addAttribute("fileList", fileTestDTOList);
		return "test/list";
	}
	
	
}