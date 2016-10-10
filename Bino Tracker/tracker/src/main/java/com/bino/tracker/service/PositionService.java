package com.bino.tracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import com.bino.tracker.vo.Message;

@Service
public class PositionService {
	@Autowired
	public SimpMessageSendingOperations messagingTemplate;
	
	@Autowired
	public PositionSimulator positionSimulator;

	public void boradcast() {
		Message message = new Message();
		message.setCommand("update");
		message.setData1(positionSimulator.getPositions());
		message.setData2(positionSimulator.getPositionEvents());
		messagingTemplate.convertAndSend("/topic/position", message);
		positionSimulator.clearPositionEvents();
	}
}
