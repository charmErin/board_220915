package com.its.board.dto;

import lombok.Data;

@Data
public class SearchDTO {
	private String searchType;
	private String q;
	private int start;
	private int count;
}
