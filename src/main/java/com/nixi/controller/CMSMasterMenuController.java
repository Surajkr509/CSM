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
import com.nixi.model.CMSMasterMenu;
import com.nixi.repository.CMSMasterMenuRepository;
import com.nixi.service.CMSMasterMenuService;
import com.nixi.utilis.Constants;

@RestController
@RequestMapping("/api/auth")
public class CMSMasterMenuController {

	@Autowired
	CMSMasterMenuRepository cmsMasterMenuRepository;
	
	@Autowired
	CMSMasterMenuService cmsMasterMenuService;
	
	@PostMapping("/addMasterMenu")
	public ResponseEntity<?> addMasterMenu(@ModelAttribute CMSMasterMenu mMData) {
		System.err.println(" ::: CMSMasterMenuController.addMasterMenu ::: ");
		ResultDTO<?> responsePacket = null;
		try {
			
			if(mMData!=null) {
			if (cmsMasterMenuRepository.existsByName(mMData.getName())) {
				responsePacket = new ResultDTO<>(false, null, "Master Menu Name Already Exist!");
				return new ResponseEntity<>(responsePacket, HttpStatus.BAD_REQUEST);
				
				
			} else {
				responsePacket = new ResultDTO<>(true, cmsMasterMenuService.addMasterMenu(mMData),Constants.requestSuccess);
				return new ResponseEntity<>(responsePacket, HttpStatus.OK);
			}
			} else {
				responsePacket = new ResultDTO<>(false, null, "MasterMenu Data is Null!");
				return new ResponseEntity<>(responsePacket, HttpStatus.BAD_REQUEST);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			responsePacket = new ResultDTO<>(false, null, e.getMessage());
			return new ResponseEntity<>(responsePacket, HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/masterMenuList")
	public List<CMSMasterMenu> masterMenuList(){
		System.err.println(" ::: CMSMasterMenuController.masterMenuList ::: ");
		 List<CMSMasterMenu> allData = cmsMasterMenuRepository.findAll();
		 return allData;
	}
	
	@GetMapping("/masterMenuById/{id}")
	public Object masterMenuById(@PathVariable Long id) {
		System.err.println(" ::: CMSMasterMenuController.masterMenuById ::: ");
		 Optional<CMSMasterMenu> existData = cmsMasterMenuRepository.findById(id);
		 if(existData.isPresent()) {
			 return existData;
		 } else {
			 return "Oops Invalid ID!";
		 }
		
	}
	
	@PostMapping("/updateMasterMenuById/{id}")
	public Object updateMasterMenuById(@PathVariable Long id,@ModelAttribute CMSMasterMenu cmsMasterMenu) {
		System.err.println(" ::: CMSMasterMenuController.updateMasterMenuById ::: ");
		 Optional<CMSMasterMenu> existData = cmsMasterMenuRepository.findById(id);
		 if(existData.isPresent()) {
			 cmsMasterMenuService.updateMasterMenuById(id,cmsMasterMenu,existData);
			 return "Master Menu updated Successfully";
		 } else {
			 return "Oops Invalid ID!";
		 }
	}
	
	@DeleteMapping("/deleteMasterMenuById/{id}")
	public Object deleteMasterMenuById(@PathVariable Long id) {
		System.err.println(" ::: CMSMasterMenuController.deleteMasterMenuById ::: ");
		 Optional<CMSMasterMenu> existData = cmsMasterMenuRepository.findById(id);
		 if(existData.isPresent()) {
			 cmsMasterMenuService.deleteMasterMenuById(id);
			 return "MasterMenu Deleted Successfully!";
		 } else {
			 return "Oops Invalid ID!";
		 }
		
	}
	
}
