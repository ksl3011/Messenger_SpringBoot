package com.study.user;

import com.study.cmn.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserVO extends DTO {

	private String userId;
	private String pw;

}
