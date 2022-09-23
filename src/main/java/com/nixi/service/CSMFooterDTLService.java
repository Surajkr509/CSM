package com.nixi.service;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.nixi.bean.FileUploadUtil;
import com.nixi.model.CSMFooterDTL;
import com.nixi.repository.CSMFooterDTLRepository;
import com.nixi.utilis.Constants;

@Service
public class CSMFooterDTLService {
	
	@Autowired
	private  CSMFooterDTLRepository csmFooterDTLRepository;

	public void add(CSMFooterDTL slideShow, MultipartFile multipartFile) throws IOException {
		CSMFooterDTL slideData=csmFooterDTLRepository.save(slideShow);
		String uploadDir = "src/main/resources/static/images/" + slideData.getId();
		FileUploadUtil.saveFile(uploadDir, slideShow.getLogoImage(), multipartFile);
	}

	public void update(Long id, CSMFooterDTL slideshow, MultipartFile multipartFile) throws IOException {
		System.err.println("UpdateCSMFooterDTL:::");
		Optional<CSMFooterDTL> existSlideShow = csmFooterDTLRepository.findById(id);
		System.out.println("Image ID" + slideshow);
		if (existSlideShow.isPresent()) {
		if(!multipartFile.isEmpty()) {
			CSMFooterDTL data = existSlideShow.get();
			
			String uploadDir2 = "src/main/resources/static/images/" + data.getId()+"/";
			
			Constants.deleteMultiPartFile(uploadDir2, data.getLogoImage());
			data.setLogoName(slideshow.getLogoName());
			data.setUpdatedAt(Constants.getDateAndTime());
			data.setUrl(slideshow.getUrl());
			System.out.println("::::"+data);
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			System.out.println("LogoImage:::"+fileName);
			data.setLogoImage(fileName);
			System.out.println("LogoImage:::"+fileName);
			csmFooterDTLRepository.save(data);
			String uploadDir = "src/main/resources/static/images/" + data.getId();
			FileUploadUtil.saveFile(uploadDir, data.getLogoImage(), multipartFile);
		}
		}
	}

	public void deleteFooterDTLById(Long id) {
		csmFooterDTLRepository.deleteById(id);
	}

}
