package com.nixi.service;

import java.io.IOException;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.nixi.bean.FileUploadUtil;
import com.nixi.model.MainBanner;
import com.nixi.repository.MainBannerRepository;
import com.nixi.utilis.Constants;


@Service
public class MainBannerService {
	
	@Autowired
	MainBannerRepository mainBannerRepository;
	

	public void add(@Valid MainBanner slideShow, MultipartFile multipartFile) throws IOException {
		MainBanner slideData=mainBannerRepository.save(slideShow);
		String uploadDir = "src/main/resources/static/images/" + slideData.getId();
		FileUploadUtil.saveFile(uploadDir, slideShow.getPhotos(), multipartFile);
	}


	public void update(Long id,MainBanner slideshow, MultipartFile multipartFile) throws IOException {
		System.err.println("Update:::");
		Optional<MainBanner> existSlideShow = mainBannerRepository.findById(id);
		System.out.println("Image ID" + slideshow);
		if (existSlideShow.isPresent()) {
		if(!multipartFile.isEmpty()) {
			MainBanner data = existSlideShow.get();
			
			String uploadDir2 = "src/main/resources/static/images/" + data.getId()+"/";
			
			Constants.deleteMultiPartFile(uploadDir2, data.getPhotos());
			data.setBannerName(data.getBannerName());
			data.setUpdatedAt(Constants.getDateAndTime());
			System.out.println("::::"+data);
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			System.out.println("Image:::"+fileName);
			data.setPhotos(fileName);
			System.out.println("Image:::"+fileName);
			mainBannerRepository.save(data);
			String uploadDir = "src/main/resources/static/images/" + data.getId();
			FileUploadUtil.saveFile(uploadDir, data.getPhotos(), multipartFile);
		}
		}
	}


	public void delete(Long id) {
		mainBannerRepository.deleteById(id);
	}



	

}
