package com.app.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class TestController {
	public TestController() {
		log.info("in ctor of "+getClass());
	}
	@GetMapping("/home")
	public String showHome()
	{
		return "in home page...";//@RespBody 
	}
	
	@GetMapping("/user")
	public String showUser()
	{
		return "in user page...";//@RespBody 
	}
	
	@GetMapping("/admin")
	public String showAdmin()
	{
		return "in admin page...";//@RespBody 
	}
	
	//add req handling method to add new topic : under admin access : dummy  usage(actual in CMS)
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/add/topic")
	public String addTopic()
	{
		return "in add topic";//@RespBody 
	}
	
	//add req handling method to view  topic : anyone should be able to view the topic , after authentication
	
		@GetMapping("/view/topic")
		public String viewTopic()
		{
			return "in view topic";//@RespBody 
		}

}
