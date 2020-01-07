package com.study.user.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
		vo.setPw(null);
		UserVO outvo = (UserVO) u.selectOne(vo);
		boolean flag = (outvo==null);
		JsonVO json = new JsonVO( (flag?1:0), (flag?"ok":"no") );
		Gson gson = new Gson();
		String gsonString = gson.toJson(json);
		return gsonString;
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(UserVO vo, HttpServletRequest req) throws Exception {
		UserVO outvo = (UserVO) u.selectOne(vo);
		if(outvo!=null) {
			HttpSession s = req.getSession();
			s.setAttribute("userId", vo.getUserId());
			return "redirect:messenger_A.jsp";
		}else {
			throw new Exception();
		}
	}
}
