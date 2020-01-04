package com.study.cmn;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MessageVO extends DTO{

	private String name;
	private String contents;
	private String whisper;
	
}
