package com.its.board.dto;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class CommentDTO {
	private Long id;
	private Long boardId;
	private Long memberId;
	private String commentWriter;
	private String commentContents;
	private Timestamp createdTime;
}
