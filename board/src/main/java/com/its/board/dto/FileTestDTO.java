package com.its.board.dto;

import lombok.Data;

@Data
public class FileTestDTO {
	private Long id;
	private byte[] blobFile;
	private String fileName;
	private long fileSize;
}
