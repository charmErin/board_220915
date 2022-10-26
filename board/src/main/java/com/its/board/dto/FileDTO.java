package com.its.board.dto;

import lombok.Data;

@Data
public class FileDTO {
	private Long id;
	private Long boardId;
	private byte[] boardFile;
	private String boardFileName;
	private long boardFileSize;
}
