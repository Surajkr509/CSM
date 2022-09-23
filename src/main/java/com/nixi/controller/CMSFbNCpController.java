package com.nixi.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nixi.bean.ResultDTO;
import com.nixi.model.CMSFeedbackNComplaints;
import com.nixi.repository.CMSFbNCpRepository;
import com.nixi.service.CMSFbNCpService;
import com.nixi.utilis.Constants;

@RestController
@RequestMapping("/api/auth")
public class CMSFbNCpController {
	
	@Autowired
	private CMSFbNCpRepository cMSFbNCpRepository;
	
	@Autowired
	private CMSFbNCpService cMSFbNCpService;
	
	@PostMapping("/addFeedback&Complain")
	public ResponseEntity<?> addFeedback(@ModelAttribute CMSFeedbackNComplaints userData) {
		System.err.println(" ::: CMSFbNCpController.addFeedback&Complain ::: ");
		ResultDTO<?> responsePacket = null;
		try {
			if(userData!=null) {
				responsePacket = new ResultDTO<>(true, cMSFbNCpService.addFeedback(userData),Constants.requestSuccess);
				return new ResponseEntity<>(responsePacket, HttpStatus.OK);
			} else {
				responsePacket = new ResultDTO<>(false, "Please Fill the Information");
				return new ResponseEntity<>(responsePacket, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			e.printStackTrace();
			responsePacket = new ResultDTO<>(false, null, e.getMessage());
			return new ResponseEntity<>(responsePacket, HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/feedback&ComplainList")
	public List<CMSFeedbackNComplaints>feedbackNComplainList(){
		System.err.println(" ::: CMSFbNCpController.feedback&ComplainList ::: ");
		return cMSFbNCpRepository.findAll();
	}
	
	@GetMapping("/feedback&ComplainById/{id}")
	public Object feedbackNComplainById(@PathVariable Long id) {
		System.err.println(" ::: CMSFbNCpController.feedback&ComplainById:: ");
		Optional<CMSFeedbackNComplaints> existData =  cMSFbNCpRepository.findById(id);
		if(existData.isPresent()) {
			return existData;
		} else {
			return "Oops Invalid ID!";
		}
	}
	
	@DeleteMapping("/deleteFeedbackById/{id}")
	public String deleteFeedbackById(@PathVariable Long id) {
		System.err.println(" ::: CMSFbNCpController.deleteFeedbackById:: ");
		Optional<CMSFeedbackNComplaints> existData =  cMSFbNCpRepository.findById(id);
		if(existData.isPresent()) {
			 cMSFbNCpService.deleteFeedbackById(id);
			 return "Feedback&Complaint Deleted Successfully";
		} else {
			return "Oops Invalid ID!";
		}
	}

}
