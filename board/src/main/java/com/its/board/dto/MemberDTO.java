package com.its.board.dto;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberDTO {
	private Long id;
	private String loginId;
	private String memberPassword;
	private String memberName;
	private String memberMobile;
	
}
