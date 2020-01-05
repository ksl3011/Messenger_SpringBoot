package com.study.cmn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import com.study.messenger.web.HandshakeInterceptorImpl;
import com.study.messenger.web.StompHandler;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfigure implements WebSocketMessageBrokerConfigurer {

	@Autowired
	private StompHandler sh;
	@Autowired
	private HandshakeInterceptorImpl hs;
	
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		//WebSocket을 사용할 수없는 경우 대체 전송을 사용할 수 있도록 SockJS 폴백 옵션을 활성화
		registry.addEndpoint("/this_is_endpoint").addInterceptors(hs).withSockJS();
	}

	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		registry.enableSimpleBroker("/brokerA");	//브로커가 해당주소의 클라에게 메시지전달
		//registry.setApplicationDestinationPrefixes("/");//전송할 목적지의 prefix
	}

	@Override
	public void configureClientInboundChannel(ChannelRegistration registration) {
		registration.interceptors(sh);
	}

	@Override
	public void configureClientOutboundChannel(ChannelRegistration registration) {
		registration.interceptors(sh);
	}

	

}
