package com.nixi.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nixi.model.Navbar;
import com.nixi.repository.NavbarRepository;
import com.nixi.utilis.Constants;

@RestController
@RequestMapping("/api/auth")
public class NavbarController {
	
	@Autowired
	NavbarRepository navbarRepository;
	
	
	@PostMapping("addNavbar")
	public String addNavbar(@Valid @ModelAttribute Navbar nav) {
		System.err.println("::: AdminController.addNavbar :::");
		Navbar navbar = navbarRepository.findByName(nav.getName());
	//	if()
		nav.setCreatedAt(Constants.getDateAndTime());
		nav.setUpdatedAt(Constants.getDateAndTime());
		navbarRepository.save(nav);
		return "Navbar added successfully";
	}

	

}
