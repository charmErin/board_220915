package com.its.board.dto;

import lombok.Data;

@Data
public class PageDTO {
	private int page;
	private int startPage;
	private int endPage;
	private int maxPage;
}
