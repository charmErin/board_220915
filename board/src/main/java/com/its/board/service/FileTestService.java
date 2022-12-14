package com.its.board.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.its.board.dto.FileTestDTO;
import com.its.board.repository.FileTestRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FileTestService {
	private final FileTestRepository fileTestRepository;

	public void save(FileTestDTO fileTestDTO) {
		fileTestRepository.save(fileTestDTO);
	}

	public List<FileTestDTO> findAll() {
		return fileTestRepository.findAll();
	}

	public FileTestDTO findById(Long id) {
		return fileTestRepository.findById(id);
	}

	public void save2(Map<String, Object> paramMap) {
		fileTestRepository.save2(paramMap);
	}

}
