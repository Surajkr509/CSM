package com.nixi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nixi.model.CMSMasterMenu;

@Repository
public interface CMSMasterMenuRepository extends JpaRepository<CMSMasterMenu, Long> {

	boolean existsByName(String name);

}
