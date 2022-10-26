package com.its.board.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.its.board.dto.FileDTO;
import com.its.board.repository.FileRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FileService {
	private final FileRepository fileRepository;

	public void save(FileDTO fileDTO) {
		fileRepository.save(fileDTO);
	}

	public List<FileDTO> findAll(Long id) {
		return fileRepository.findAll(id);
	}

	public FileDTO findById(Long id) {
		return fileRepository.findById(id);
	}

	public void delete(Long id) {
		fileRepository.delete(id);
	}
	
	
}
