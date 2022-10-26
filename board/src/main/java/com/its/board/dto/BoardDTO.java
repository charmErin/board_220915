package com.its.board.dto;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class BoardDTO {
	private Long id;
	private Integer categoryId;
	private Long memberId;
	private String boardTitle;
	private String boardWriter;
	private String boardContents;
	private Timestamp createdTime;
	private int boardHits;
	
	private List<FileDTO> boardFileList;
}
