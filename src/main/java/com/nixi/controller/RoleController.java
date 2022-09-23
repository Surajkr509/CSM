package com.nixi.controller;


import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nixi.model.Role;
import com.nixi.repository.RoleRepository;
import com.nixi.service.RoleService;


@RestController
@RequestMapping("/api/auth")
public class RoleController {
	
	@Autowired
	RoleRepository  roleRepository;
	
	@Autowired
	RoleService  roleService;
	
	@PostMapping(value = "/addRole")
	public String addRole(@Valid @ModelAttribute Role role, Authentication auth) {
		System.err.println("::: AdminController.addRole :::");
			Role data = roleRepository.findByRoleName(role.getRoleName());
			if (data != null) {
				//bindingResult.rejectValue("roleName", "error.role", "Role already exists");
				return "Role Already Exist!";
			}
			 else {
				 roleService.saveRole(role);
				 return "Role added successfully!";
			}
	}
	
	@GetMapping({ "/roleList" })
	public List<Role> roleList() {
		System.err.println("::: AdminController.roleList :::");
		return roleService.getRoleList();
	}

	@GetMapping({ "/roleById/{id}" })
	public Optional<Role> roleById(@PathVariable Long id) {
		System.err.println("::: AdminController.roleById :::");
		return roleService.getRoleById(id);
	}
	
	@PostMapping({ "/updateRoleById/{id}" })
	public String updateRoleById(@PathVariable Long id,@ModelAttribute Role role) {
		System.err.println("::: AdminController.updateRoleById :::" + role.getId());
		try {
		if (id!=null) {
			role.setId(id);
			roleService.updateRoleById(id,role);
			return "Role Updated Successfully";
		} else {
			return "Invalid ID!!";
		}
	} catch (Exception e) {
		return "Invalid ID!";
	}
	}
	@DeleteMapping("/deleteRoleById/{id}")
	public String deleteRoleById(@PathVariable Long id) {
		System.err.println("::: AdminController.deleteRoleById :::" +id);
		try {
		if (id!=null) {
			roleService.deleteRoleById(id);
			return "Role Deleted Successfully";
		} else {
			return "Invalid ID!";
		}
	} catch (Exception e) {
		return "Invalid ID!";
	}
	}
		
}
