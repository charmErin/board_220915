package com.its.board.dto;

import lombok.Data;

@Data
public class FileDTO {
	private Long id;
	private Long boardId;
	private String uuid;
	private String uploadPath;
	private String fileName;
	private boolean fileType;
}
