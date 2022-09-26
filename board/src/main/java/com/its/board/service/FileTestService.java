package com.its.board.service;

import java.util.List;

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
}