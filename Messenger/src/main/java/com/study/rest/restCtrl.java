package com.study.rest;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController						//responseBody 자동적용
@RequestMapping(value = "/test")	//기본url을 http://localhost:8080/Messenger/a 로
public class restCtrl {
	
	@RequestMapping(value = "/a", method = RequestMethod.GET)
	public String normal(String s) {
		return s;
	}
	
	@RequestMapping(value = "/{msg}")// PathVariable : {}안의 이름을 자동으로 매칭
	public String pathVariable(@PathVariable String msg) {
		return msg;
	}
	

}
