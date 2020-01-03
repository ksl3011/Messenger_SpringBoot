package com.study.cmn;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JsonVO extends DTO {
	
	private int id;
	private String msg;
	
}
