package com.nixi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nixi.model.Navbar;

public interface NavbarRepository extends JpaRepository<Navbar, Long> {

	Navbar findByName(String name);

}
