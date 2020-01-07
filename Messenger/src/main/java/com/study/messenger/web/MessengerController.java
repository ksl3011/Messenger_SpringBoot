package com.study.messenger.web;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.study.cmn.MessageVO;

@RestController
public class MessengerController {

	@Autowired
	private StompHandler sh;
	
	/**
	 * 구독
	 * @param msg
	 * @return
	 */
	@MessageMapping(value = "/m/brokerA/subscribe")//맵핑
	@SendTo("/brokerA")//이 주소로 구독중인 stompClient로 전송
	public MessageVO subscribe(SimpMessageHeaderAccessor a) {
		Map<String, String> map = (Map<String, String>) a.getHeader("simpSessionAttributes");
		String id = map.get("USER_ID");
		System.out.println("brokerA/subscribe << " + id);
		MessageVO vo = new MessageVO("Server", "Connect : "+ id, "", date());
		return vo;
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
		MessageVO vo = new MessageVO("Server", sh.list.toString(), "", date());
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
	public MessageVO out(SimpMessageHeaderAccessor a) {
		Map<String, String> map = (Map<String, String>) a.getHeader("simpSessionAttributes");
		String id = map.get("USER_ID");
		System.out.println("brokerA/out << " + id);
		MessageVO vo = new MessageVO("Server", "Connect : "+ id, "", date());
		return vo;
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
		vo.setDate(date());
		return vo;
	}
	
	private String date() {
		Calendar c = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/M/d hh:mm:ss", Locale.KOREA);
		return sdf.format(c.getTime());
	}
}
