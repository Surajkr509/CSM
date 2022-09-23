package com.nixi.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nixi.model.Role;
import com.nixi.repository.RoleRepository;
import com.nixi.utilis.Constants;

@Service
public class RoleService {

	@Autowired
	RoleRepository  roleRepository;

	public void saveRole(@Valid Role role) {
		role.setCreatedAt(Constants.getDateAndTime());
		role.setUpdatedAt(Constants.getDateAndTime());
		role.setActive(true);
		roleRepository.save(role);
	}

	public List<Role> getRoleList() {
		List<Role> data = roleRepository.findAll();
		return data;
		
	}

	public Optional<Role> getRoleById(Long id) {
		Optional<Role> roleData = roleRepository.findById(id);
		return roleData;
	}

	public void updateRoleById(Long id, Role role) {
		Optional<Role> roleData = roleRepository.findById(id);
		Role roleChange =roleData.get();
		roleChange.setUpdatedAt(Constants.getDateAndTime());
		roleChange.setRoleName(role.getRoleName());
		roleChange.setActive(true);
		roleRepository.save(roleChange);
	}

	public void deleteRoleById(Long id) {
		roleRepository.deleteById(id);
	}

}
