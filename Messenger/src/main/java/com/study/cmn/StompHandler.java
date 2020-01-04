package com.study.cmn;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;


@Component
public class StompHandler implements ChannelInterceptor {

	@Override
	public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
		StompHeaderAccessor s = StompHeaderAccessor.wrap(message);
		String command = (s.getCommand()==null)?"":s.getCommand().name();
		if(command.equals(StompCommand.CONNECT.toString())) {
			System.out.println("Connect");
			System.out.println(s.getSessionId().toString());
		}else if(command.equals(StompCommand.DISCONNECT.toString())) {
			System.out.println("disConnect");
			System.out.println(s.getSessionId().toString());
		}
	}
}


/*
public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
StompHeaderAccessor s = StompHeaderAccessor.wrap(message);
		System.out.println(message);
		System.out.println(channel);
		System.out.println(sent);
		System.out.println(s.toString());

StompHeaderAccessor -> simpMessageType
						stompCommand 
						
stompCommand ->
					구독 SUBSCRIBE
					연결 CONNECT DISCONNECT
					메시지보낼때 SEND
					받을때 null
simpMessageType ->
					구독 SUBSCRIBE
					연결 CONNECT DISCONNECT
					메시지 MESSAGE
		
메시지보냈을때

GenericMessage [payload=byte[28]
				, headers={simpMessageType=MESSAGE
							, stompCommand=SEND
							, nativeHeaders={destination=[/m/brokerA/msg]
											, content-length=[28]
											}
							, simpSessionAttributes={}
							, simpHeartbeat=[J@633b57bb
											, simpSessionId=oq44ri1d
											, simpDestination=/m/brokerA/msg
							}
				]
ExecutorSubscribableChannel[clientInboundChannel]
true
StompHeaderAccessor [headers={simpMessageType=MESSAGE
							, stompCommand=SEND
							, nativeHeaders={destination=[/m/brokerA/msg]
											, content-length=[28]
											}
							, simpSessionAttributes={}
							, simpHeartbeat=[J@633b57bb
											, simpSessionId=oq44ri1d
											, simpDestination=/m/brokerA/msg
							}
					]

brokerA서버/받은메시지 : MessageVO(name=t1, contents=G, whisper=null)

GenericMessage [payload=byte[43]
				, headers={simpMessageType=MESSAGE
							, simpSubscriptionId=sub-0
							, conversionHint=method 'message' parameter -1
							, contentType=application/json
							, simpSessionId=oq44ri1d
							, simpDestination=/brokerA
							}
				]
ExecutorSubscribableChannel[clientOutboundChannel]
true
StompHeaderAccessor [headers={simpMessageType=MESSAGE
							, nativeHeaders={destination=[/brokerA]
											, content-type=[application/json]
											}
							, simpSubscriptionId=sub-0
							, conversionHint=method 'message' parameter -1
							, contentType=application/json
							, simpSessionId=oq44ri1d
							, simpDestination=/brokerA
							}
					]


*/