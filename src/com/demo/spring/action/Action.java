package com.demo.spring.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.demo.spring.service.Service;

@Controller
@RequestMapping("/action")
public class Action {
	
	@Autowired
	Service service;
	
	@RequestMapping("/add.do")
	public void addUser() {
		try {
			service.addUser();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
