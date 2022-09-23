package com.nixi.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nixi.model.CMSMasterMenu;
import com.nixi.repository.CMSMasterMenuRepository;
import com.nixi.utilis.Constants;

@Service
public class CMSMasterMenuService {
	
	@Autowired
	CMSMasterMenuRepository cmsMasterMenuRepository;

	public Object addMasterMenu(CMSMasterMenu mMData) {
		mMData.setCreatedAt(Constants.getDateAndTime());
		mMData.setUpdatedAt(Constants.getDateAndTime());
		mMData.setStatus(true);
		return cmsMasterMenuRepository.save(mMData);
	}

	public void updateMasterMenuById(Long id, CMSMasterMenu cmsMasterMenu, Optional<CMSMasterMenu> existData) {
		CMSMasterMenu masterMenuData = existData.get();
		masterMenuData.setUpdatedAt(Constants.getDateAndTime());
		masterMenuData.setStatus(true);
		masterMenuData.setName(cmsMasterMenu.getName());
		masterMenuData.setUrl(cmsMasterMenu.getUrl());
		cmsMasterMenuRepository.save(masterMenuData);
	}

	public void deleteMasterMenuById(Long id) {
		cmsMasterMenuRepository.deleteById(id);
	}

}
