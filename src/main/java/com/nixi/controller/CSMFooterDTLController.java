package com.nixi.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.nixi.model.CSMFooterDTL;
import com.nixi.repository.CSMFooterDTLRepository;
import com.nixi.service.CSMFooterDTLService;
import com.nixi.utilis.Constants;

@RestController
@RequestMapping("/api/auth")
public class CSMFooterDTLController {
	
	@Autowired
	private  CSMFooterDTLService csmFooterDTLService;
	
	@Autowired
	private  CSMFooterDTLRepository csmFooterDTLRepository;
	
	@PostMapping("/footerAddDTL")
	public String addImage(@ModelAttribute CSMFooterDTL slideShow, @RequestParam("file") MultipartFile multipartFile) {
		System.out.println("::CSMFooterDTLController.footerAddDTL::::");
		try {
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			System.out.println("FileName:::"+fileName);
			slideShow.setLogoImage(fileName);
			slideShow.setCreatedAt(Constants.getDateAndTime());
			slideShow.setUpdatedAt(Constants.getDateAndTime());
			csmFooterDTLService.add(slideShow, multipartFile);
			
			return "Footer Details uploaded successfully!";

		} catch (Exception e) {
			return "Failed to upload Image!";
		}
	}
	@GetMapping("/footerDTLList")
	public List<CSMFooterDTL> footerImageList() {
		System.out.println("::MainBannerController.footerDTLList::::");
		List<CSMFooterDTL> imageData =csmFooterDTLRepository.findAll();
		return imageData;
	}
	
	@GetMapping("/footerDTLListById/{id}")
	public Object imageListById(@PathVariable Long id) {
		System.out.println("::MainBannerController.footerDTLListById::::");
		Optional<CSMFooterDTL> existCSMFooterDTL = csmFooterDTLRepository.findById(id);
		if(existCSMFooterDTL.isPresent()) {
			return existCSMFooterDTL;
		}
		else {
			return "Oops Invalid ID!";
		}
	}
	@PostMapping("/updateFooterDTLById/{id}")
	public String updateImage(@PathVariable Long id,@ModelAttribute CSMFooterDTL slideshow,RedirectAttributes rA,@RequestParam("file") MultipartFile multipartFile ) throws IOException {
		System.out.println("::MainBannerController.updateFooterDTLById::::");
		try {
			Optional<CSMFooterDTL> existSlideShow = csmFooterDTLRepository.findById(id);
			if(existSlideShow.isPresent()) {
				csmFooterDTLService.update(id,slideshow,multipartFile);
				return "Footer Details Updated Successfully!";
			} else {
				return "*Please provide valid ID!";
			}
		} catch (Exception e) {
			return "Invalid ID!";
		}
		
	}
	@DeleteMapping("/deleteFooterDTLById/{id}")
	public String delete (@PathVariable Long id) {
		System.out.println("::MainBannerController.deleteFooterDTLById::::");
		try {
			Optional<CSMFooterDTL> existFooterDTL = csmFooterDTLRepository.findById(id);
			if(existFooterDTL.isPresent()) {
				csmFooterDTLService.deleteFooterDTLById(id);
				return "Image Deleted Successfully";
			} else {
				return "Invalid ID";
			}
		} catch (Exception e) {
			return "Failed to deleted Image!";
		}
		
	}

}
