package com.study.cmn;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageVO extends DTO{

	private String name;
	private String contents;
	private String whisper;
	
}
