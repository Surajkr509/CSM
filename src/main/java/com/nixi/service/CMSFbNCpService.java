package com.nixi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nixi.model.CMSFeedbackNComplaints;
import com.nixi.repository.CMSFbNCpRepository;
import com.nixi.utilis.Constants;

@Service
public class CMSFbNCpService {
	
	@Autowired
	private CMSFbNCpRepository cMSFbNCpRepository;

	public Object addFeedback(CMSFeedbackNComplaints userData) {
		userData.setCreatedAt(Constants.getDateAndTime());
		return cMSFbNCpRepository.save(userData);
	}

	public void deleteFeedbackById(Long id) {
		cMSFbNCpRepository.findById(id);
	}

}
