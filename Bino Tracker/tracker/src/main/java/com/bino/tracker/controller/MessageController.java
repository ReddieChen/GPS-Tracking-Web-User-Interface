package com.bino.tracker.controller;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.bino.tracker.service.PositionService;
import com.bino.tracker.service.PositionSimulator;
import com.bino.tracker.vo.Message;

@Controller
public class MessageController {
	static ScheduledExecutorService statusTimerExecutor;

	@Autowired
	public PositionService positionService;
	
	@Autowired
	public PositionSimulator positionSimulator;

	@PostConstruct
	public void init() {
		if (statusTimerExecutor == null) {
			positionSimulator.startThread();
			statusTimerExecutor = Executors.newSingleThreadScheduledExecutor();
			statusTimerExecutor.scheduleAtFixedRate(new Runnable() {
				@Override
				public void run() {
					positionService.boradcast();
				}
			}, 5000, 3000, TimeUnit.MILLISECONDS);
		}
	}

	// @RequestMapping(value = "/", method = RequestMethod.GET)
	// public String index() {
	// return "home";
	// }

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView home() {
		ModelAndView model = new ModelAndView();
		model.addObject("positions", positionSimulator.getPositions());
		model.addObject("drivers", positionSimulator.getDrivers());
		model.addObject("units", positionSimulator.getUnits());
		model.addObject("pois", positionSimulator.getPois());
		model.addObject("violationDetails1", positionSimulator.getViolationDetails1());
		model.addObject("violationDetails2", positionSimulator.getViolationDetails2());
		model.addObject("violationDetails3", positionSimulator.getViolationDetails3());
		model.addObject("violationDetails4", positionSimulator.getViolationDetails4());
		model.addObject("violationDetails5", positionSimulator.getViolationDetails5());
		model.setViewName("home");
		return model;
	}

	// @MessageMapping("/hello")
	// @SendTo("/topic/greetings")
	// public OutputMessage sendMessage(Message message) {
	// System.out.println("sendMessage()");
	// message.setMessage(message.getMessage() + " from server");
	// return new OutputMessage(message, new Date());
	// }
}
