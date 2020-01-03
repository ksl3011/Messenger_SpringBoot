package com.study.messenger.web;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.study.cmn.MessageVO;

@Controller
public class MessengerController {
	
	/**
	 * 구독
	 * @param msg
	 * @return
	 */
	@ResponseBody
	@MessageMapping(value = "/m/brokerA/subscribe")//맵핑
	@SendTo("/brokerA")//이 주소로 구독중인 stompClient로 전송
	public String subscribe(String msg) {
		System.out.println("brokerA/subscribe << " + msg);
		Gson gson = new Gson();
		String gsonMsg = gson.toJson(msg);
		return gsonMsg;
	}
	
	/**
	 * disconnect
	 * @param msg
	 * @return
	 */
	@ResponseBody
	@MessageMapping(value = "/m/brokerA/out")
	@SendTo("/brokerA")
	public void out(String msg) {
		System.out.println("brokerA/out << " + msg);
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
