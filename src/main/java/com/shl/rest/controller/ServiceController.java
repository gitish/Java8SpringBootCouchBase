package com.shl.rest.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServiceController {
	
	@RequestMapping("/test")
	public String getInfo() {
		return "Shail welcomes you!!";
	}
}
