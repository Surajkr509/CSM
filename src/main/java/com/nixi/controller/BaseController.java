package com.nixi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;

import com.nixi.configuration.jwt.JwtUtils;
import com.nixi.repository.RoleRepository;
import com.nixi.repository.UserInfoRepository;
import com.nixi.service.UserInfoService;
import com.nixi.utilis.BeanValidator;

public abstract class BaseController {
	
		@Autowired
		protected BeanValidator beanValidator;
		
		@Autowired
		protected UserInfoRepository  userInfoRepository;
		
		@Autowired
		protected AuthenticationManager authenticationManager;
		
		@Autowired
		protected RoleRepository roleRepository;
		
		@Autowired
		protected JwtUtils jwtUtils;
		
		@Autowired
		protected UserInfoService userInfoService;

}
