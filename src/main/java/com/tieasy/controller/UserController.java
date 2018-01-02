package com.tieasy.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RestController;

import com.tieasy.service.impl.UserServiceImpl;

@RestController
public class UserController {

	@Resource
	private UserServiceImpl userService;
	
}
