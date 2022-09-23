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

import com.nixi.model.CMSPhotoGallery;
import com.nixi.repository.CMSPhotoGalleryRepository;
import com.nixi.service.CMSPhotoGalleryService;
import com.nixi.utilis.Constants;

@RestController
@RequestMapping("/api/auth")
public class CMSPhotoGalleryController {

	@Autowired
	private CMSPhotoGalleryRepository  cMSPhotoGalleryRepository;
	@Autowired
	private CMSPhotoGalleryService cmsPhotoGalleryService;
	
	@PostMapping("/addPhoto")
	public String addImage(@ModelAttribute CMSPhotoGallery photoGallery, @RequestParam("file") MultipartFile multipartFile) {
		System.out.println("::CMSPhotoGalleryController.addPhoto::::");
		try {
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			System.out.println("FileName:::"+fileName);
			photoGallery.setPhoto(fileName);
			photoGallery.setCreatedAt(Constants.getDateAndTime());
			photoGallery.setUpdatedAt(Constants.getDateAndTime());
			cmsPhotoGalleryService.add(photoGallery, multipartFile);
			
			return "Photo uploaded successfully!";

		} catch (Exception e) {
			return "Failed to upload photo!";
		}
	}
	
	@GetMapping("/photoGalleryList")
	public List<CMSPhotoGallery> photoGalleryList() {
		System.out.println("::CMSPhotoGalleryController.photoGalleryList::::");
		List<CMSPhotoGallery> imageData =cMSPhotoGalleryRepository.findAll();
		return imageData;
	}
	
	@GetMapping("/photoGalleryById/{id}")
	public Object photoGalleryById(@PathVariable Long id){
		System.out.println("::CMSPhotoGalleryController.photoGalleryById::::");
		Optional<CMSPhotoGallery> photoGallery =cMSPhotoGalleryRepository.findById(id);
		if(photoGallery.isPresent()) {
		return photoGallery;
	} else {
		return "Oops invalid ID!";
	}
	}
	
	@PostMapping("/updatePhotoGalleryById/{id}")
	public String updatePhotoGalleryById(@PathVariable Long id,@ModelAttribute CMSPhotoGallery pGallery,RedirectAttributes rA,@RequestParam("file") MultipartFile multipartFile ) throws IOException {
		System.out.println("::CMSPhotoGalleryController.updatePhotoGalleryById::::");
		try {
			Optional<CMSPhotoGallery> existData = cMSPhotoGalleryRepository.findById(id);
			if(existData.isPresent()) {
				cmsPhotoGalleryService.update(id,pGallery,existData,multipartFile);
				return "Photo Details Updated Successfully!";
			} else {
				return "*Please provide valid ID!";
			}
		} catch (Exception e) {
			return "Invalid ID!";
		}
		
	}
	@DeleteMapping("/deletePhotoGalleryById/{id}")
	public String deletePhotoGalleryById (@PathVariable Long id) {
		System.out.println("::CMSPhotoGalleryController.deletePhotoGalleryById::::");
		Optional<CMSPhotoGallery> existData = cMSPhotoGalleryRepository.findById(id);
		if(existData.isPresent()) {
			cmsPhotoGalleryService.delete(id);
			return "Deleted Successfully";
		} else {
			return "Oops invalid ID!";
		}
	}
}
