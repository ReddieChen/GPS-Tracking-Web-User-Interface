package com.bino.tracker.interceptor;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;

public class PresenceChannelInterceptor extends ChannelInterceptorAdapter {
	
	@Override
	public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
		StompHeaderAccessor sha = StompHeaderAccessor.wrap(message);
		if (sha.getCommand() == null) {
			return;
		}
		String sessionId = sha.getSessionId();
		switch (sha.getCommand()) {
		case CONNECT:
			// System.out.println("STOMP Connect [sessionId: " + sessionId + "]");
			break;
		case CONNECTED:
			// System.out.println("STOMP Connected [sessionId: " + sessionId + "]");
			break;
		case DISCONNECT:
			// System.out.println("STOMP Disconnect [sessionId: " + sessionId + "]");
			break;
		default:
			break;

		}
	}
}
