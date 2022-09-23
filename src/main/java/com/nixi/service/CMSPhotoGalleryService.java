package com.nixi.service;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.nixi.bean.FileUploadUtil;
import com.nixi.model.CMSPhotoGallery;
import com.nixi.repository.CMSPhotoGalleryRepository;
import com.nixi.utilis.Constants;

@Service
public class CMSPhotoGalleryService {
	
	@Autowired
	private CMSPhotoGalleryRepository  cMSPhotoGalleryRepository;

	public void add(CMSPhotoGallery photoGallery, MultipartFile multipartFile) throws IOException {
		photoGallery.setStatus(true);
		CMSPhotoGallery slideData=cMSPhotoGalleryRepository.save(photoGallery);
		String uploadDir = "src/main/resources/static/images/" + slideData.getId();
		FileUploadUtil.saveFile(uploadDir, photoGallery.getPhoto(), multipartFile);
	}


	public void update(Long id, CMSPhotoGallery pGallery, Optional<CMSPhotoGallery> existData,
			MultipartFile multipartFile) throws IOException {
		System.out.println("Image ID" + pGallery);
		if(!multipartFile.isEmpty()) {
			CMSPhotoGallery data = existData.get();
			
			String uploadDir2 = "src/main/resources/static/images/" + data.getId()+"/";
			
			Constants.deleteMultiPartFile(uploadDir2, data.getPhoto());
			data.setPhotoName(pGallery.getPhotoName());
			data.setUpdatedAt(Constants.getDateAndTime());
			data.setUrl(pGallery.getUrl());
			data.setStatus(true);
			System.out.println("::::"+data);
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			System.out.println("LogoImage:::"+fileName);
			data.setPhoto(fileName);
			System.out.println("LogoImage:::"+fileName);
			cMSPhotoGalleryRepository.save(data);
			String uploadDir = "src/main/resources/static/images/" + data.getId();
			FileUploadUtil.saveFile(uploadDir, data.getPhoto(), multipartFile);
		}
		
	}


	public void delete(Long id) {
		cMSPhotoGalleryRepository.deleteById(id);
	}

	}


