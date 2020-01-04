package com.study.messenger.web;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.socket.TextMessage;

import com.google.gson.Gson;
import com.study.cmn.MessageVO;
import com.study.user.UserVO;

@Controller
public class MessengerController {
	Set<String> userList = new HashSet<String>(); 
	
	/**
	 * 구독
	 * @param msg
	 * @return
	 */
	@MessageMapping(value = "/m/brokerA/subscribe")//맵핑
	//@SendTo("/brokerA")//이 주소로 구독중인 stompClient로 전송
	public void subscribe(String user) {
		System.out.println("brokerA/subscribe << " + user);
		userList.add(user);
	}
	
	/**
	 * 유저리스트
	 * @param msg
	 * @return
	 */
	@ResponseBody
	@MessageMapping(value = "/m/brokerA/userList")
	@SendTo("/brokerA")
	public MessageVO userList() {
		MessageVO vo = new MessageVO("Server", userList.toString(), "");
		return vo;
	}
	
	/**
	 * disconnect
	 * @param msg
	 * @return
	 */
	@ResponseBody
	@MessageMapping(value = "/m/brokerA/out")
	@SendTo("/brokerA")
	public void out(String user, HttpServletRequest req) {
		System.out.println("brokerA/out << " + user);
		userList.remove(user);
		HttpSession s = req.getSession();
		s.invalidate();
	}
	
	/**
	 * 메시지
	 * @param vo
	 * @return
	 */
	@ResponseBody
	@MessageMapping(value = "/m/brokerA/msg")
	@SendTo("/brokerA")
	public MessageVO message(MessageVO vo) {
		System.out.println("brokerA서버/받은메시지 : " + vo);
		return vo;
	}
	
}
