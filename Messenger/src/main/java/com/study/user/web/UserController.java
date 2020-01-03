package com.study.user.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.study.cmn.JsonVO;
import com.study.user.UserService;
import com.study.user.UserVO;

@Controller
public class UserController {

	@Autowired
	private UserService u;
	
	@ResponseBody
	@RequestMapping(value = "/join", method = RequestMethod.POST)
	public String join(UserVO vo) {
		int flag = u.save(vo);
		JsonVO json = new JsonVO(flag, ((flag==1)?"ok":"no") );
		Gson gson = new Gson();
		String gsonString = gson.toJson(json);
		return gsonString;
	}
	
	@ResponseBody
	@RequestMapping(value = "/validation", method = RequestMethod.POST)
	public String validation(UserVO vo) {
		UserVO outvo = (UserVO) u.selectOne(vo);
		boolean flag = (outvo==null);
		JsonVO json = new JsonVO( (flag?1:0), (flag?"ok":"no") );
		Gson gson = new Gson();
		String gsonString = gson.toJson(json);
		return gsonString;
	}
}
