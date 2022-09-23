package com.nixi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nixi.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {


	Role findByRoleName(String string);

}
