package com.ste.enginestreamportal.services;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.ste.enginestreamportal.exceptions.InvalidImageNameException;
import com.ste.enginestreamportal.model.HomePageContentEntity;
import com.ste.enginestreamportal.repository.HomePageContentRepo;
import com.ste.enginestreamportal.util.CommonMethods;
import com.ste.enginestreamportal.util.Constants;
import com.ste.enginestreamportal.util.FileUploadProperties;


@Service
public class HomePageContentService {
	@Autowired
	HomePageContentRepo homePageContentRepo;

	@Autowired
	FileUploadProperties fileUploadProperties;

	private Logger logger = LogManager.getLogger(HomePageContentService.class);
	// when getting data in the form of json object
	/*
	 * public HomePageContentEntity saveHomePageContent(String jsonString) {
	 * HomePageContentEntity homePageContentEntity = null;
	 * try {
	 * Gson gson = new Gson();
	 * homePageContentEntity = gson.fromJson(jsonString,
	 * HomePageContentEntity.class);
	 * 
	 * homePageContentEntity.setCreatedBy(1);
	 * homePageContentRepo.save(homePageContentEntity);
	 * } catch(Exception e) {
	 * logger.error(e);
	 * }
	 * return homePageContentRepo.findById(homePageContentEntity.getId()).get();
	 * }
	 */


	// when getting data in the form of request params
	public HomePageContentEntity saveHomePageContentRp(String title, String content, MultipartFile file) {
		HomePageContentEntity homePageContentEntity = null;
		try {

			if(!CommonMethods.isValidImageName(file.getOriginalFilename())) {
				System.out.println("not a valid file type given");
				throw new InvalidImageNameException("not a valid image");
			}
			String fileNameParts[] = file.getOriginalFilename().split("[.]");
			String fileName = fileNameParts[0]+"_"+CommonMethods.getFormattedCurrentDateTime()+"."+fileNameParts[1];
			fileName = fileName.replaceAll("[ ]+", "_");

			homePageContentEntity = new HomePageContentEntity();
			homePageContentEntity.setTitle(title);
			homePageContentEntity.setContent(content);
			
			homePageContentEntity.setCreatedBy("1");
			homePageContentEntity.setImage(fileName);
			homePageContentEntity = homePageContentRepo.save(homePageContentEntity);

			//file.transferTo(new File("C:\\projects\\fileUpload\\src\\main\\resources\\uploaded_images\\"+fileName));
			File directory = new File("./");
			String imageFilePath =directory.getAbsolutePath();
			file.transferTo(new File(imageFilePath+Constants.homePageContentUpload+fileName));


		} catch(Exception e) {
			logger.error(e);
		}

		return homePageContentRepo.findById(homePageContentEntity.getId()).get();
	}


	public List<HomePageContentEntity> getAllHomePageContent() {
		// TODO Auto-generated method stub
		return homePageContentRepo.findAll();
	}


	public Optional<HomePageContentEntity> getHomePageContentById(long id) {
		return homePageContentRepo.findById(id);
	}


	public HomePageContentEntity updateHomePageContentTitle(long id, String title) {
		HomePageContentEntity homePage = null;
		try {
			Optional<HomePageContentEntity> homePageContentEntity = homePageContentRepo.findById(id);
			if(!homePageContentEntity.isPresent()) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "no record with id "+id);
			}
			homePage = homePageContentEntity.get();
			homePage.setTitle(title);
			homePage.setUpdatedBy("1");
			homePageContentRepo.save(homePage);

		} catch(Exception e) {
			logger.error(e);
		}
		return homePage;
	}


	public HomePageContentEntity updateHomePageContentContent(int id, String content) {
		HomePageContentEntity homePage = null;
		try {
			Optional<HomePageContentEntity> homePageContentEntity = homePageContentRepo.findById((long) id);
			if(!homePageContentEntity.isPresent()) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "no record with id "+id);
			}
			homePage = homePageContentEntity.get();
			homePage.setContent(content);
			homePage.setUpdatedBy("1");
			homePageContentRepo.save(homePage);

		} catch(Exception e) {
			logger.error(e);
		}
		return homePage;
	}

	public HomePageContentEntity updateHomePageContentImage(long id, MultipartFile file) throws IllegalStateException, IOException {
		HomePageContentEntity image = null;
		try {
			Optional<HomePageContentEntity> homePageContentEntity = homePageContentRepo.findById(id);
			if(!homePageContentEntity.isPresent()) {
				logger.error("there are no records with given id");
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "there are no records with id "+id);
			}
			image = homePageContentEntity.get();
			String oldImageName = image.getImage();

			if(!CommonMethods.isValidImageName(file.getOriginalFilename())) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "not a valid image name");
			}
			String fileNameParts[] = file.getOriginalFilename().split("[.]");
			String fileName = fileNameParts[0]+"_"+CommonMethods.getFormattedCurrentDateTime()+"."+fileNameParts[1];
			fileName = fileName.replaceAll("[ ]+", "_");
			image.setImage(fileName);
			image.setUpdatedBy("1");

			File directory = new File("./");
			String imageFilePath =directory.getAbsolutePath();
			file.transferTo(new File(imageFilePath+Constants.homePageContentUpload+fileName));

			homePageContentRepo.save(image);
			//delete the previous image
			File oldImage = new File(imageFilePath+Constants.homePageContentUpload+oldImageName); 
			if(oldImage.delete())
				System.out.println("old image deleted");
			else
				System.out.println("failed to delete old image");
		} catch(Exception e) {
			logger.error(e);
		}
		return image;
	}



}