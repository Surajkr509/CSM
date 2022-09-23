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

import com.nixi.model.MainBanner;
import com.nixi.repository.MainBannerRepository;
import com.nixi.service.MainBannerService;
import com.nixi.utilis.Constants;



@RestController
@RequestMapping("/api/auth")
public class MainBannerController {
	 
	
	@Autowired
	MainBannerService mainBannerService;
	
	@Autowired
	MainBannerRepository mainBannerRepository;
	
	@PostMapping("/addImage")
	public String addImage(@ModelAttribute MainBanner slideShow, @RequestParam("file") MultipartFile multipartFile) {
		System.out.println("::MainBannerController.addImage::::");
		try {
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			System.out.println("FileName:::"+fileName);
			slideShow.setPhotos(fileName);
			slideShow.setCreatedAt(Constants.getDateAndTime());
			slideShow.setUpdatedAt(Constants.getDateAndTime());
			mainBannerService.add(slideShow, multipartFile);
			
			return "Image uploaded successfully!";

		} catch (Exception e) {
			return "Failed to upload Image!";
		}
	}
	
	@GetMapping("/imageList")
	public List<MainBanner> imageList() {
		System.out.println("::MainBannerController.imageList::::");
		List<MainBanner> imageData =mainBannerRepository.findAll();
		return imageData;
	}
	@GetMapping("/imageList/{id}")
	public Object imageListById(@PathVariable Long id) {
		System.out.println("::MainBannerController.imageListById::::");
		if(id!=null) {
			Optional<MainBanner> imageData =mainBannerRepository.findById(id);
			return imageData;
		}
		else {
			return "Invalid ID!";
		}
	}
	
	@PostMapping("/updateImageById/{id}")
	public String updateImage(@PathVariable Long id,@ModelAttribute MainBanner slideshow,RedirectAttributes rA,@RequestParam("file") MultipartFile multipartFile ) throws IOException {
		System.out.println("::MainBannerController.updateImageById::::");
		try {
			Optional<MainBanner> existSlideShow = mainBannerRepository.findById(id);
			if(existSlideShow.isPresent()) {
				mainBannerService.update(id,slideshow,multipartFile);
				return "Image Uploaded Successfully!";
			} else {
				return "*Please provide valid ID!";
			}
		} catch (Exception e) {
			return "Invalid ID!";
		}
		
	}
	
	@DeleteMapping("/deleteImageById/{id}")
	public String delete (@PathVariable Long id) {
		System.out.println("::MainBannerController.deleteImageById::::");
		try {
			Optional<MainBanner> existSlideShow = mainBannerRepository.findById(id);
			if(existSlideShow.isPresent()) {
				mainBannerService.delete(id);
				return "Image Deleted Successfully";
			} else {
				return "Invalid ID";
			}
		} catch (Exception e) {
			return "Failed to deleted Image!";
		}
		
	}

	
}
