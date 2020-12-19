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

import com.ste.enginestreamportal.model.ImageUploadEntity;
import com.ste.enginestreamportal.repository.FileUploadRepo;
import com.ste.enginestreamportal.util.CommonMethods;
import com.ste.enginestreamportal.util.Constants;
import com.ste.enginestreamportal.util.FileUploadProperties;

@Service
public class FileUploadService {
	
	@Autowired
	FileUploadRepo fileUploadRepo;
	
	@Autowired
	FileUploadProperties fileUploadProperties;
	
	private Logger logger = LogManager.getLogger(FileUploadService.class);
	
	public ImageUploadEntity uploadFile(MultipartFile file, String title, String text, String description, boolean status,
			Integer position) {
		ImageUploadEntity imageUploadEntity = new ImageUploadEntity();

		try {
			if(!CommonMethods.isValidImageName(file.getOriginalFilename())) {
				logger.error("not a valid file type given");
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"not a valid file type given");
			}
			String fileNameParts[] = file.getOriginalFilename().split("[.]");
			String fileName = fileNameParts[0]+"_"+CommonMethods.getFormattedCurrentDateTime()+"."+fileNameParts[1];
			fileName = fileName.replaceAll("[ ]+", "_");

			imageUploadEntity.setCreatedBy("1");
			imageUploadEntity.setDescription(description);
			imageUploadEntity.setPosition(position);
			imageUploadEntity.setImageStatus(status);
			imageUploadEntity.setText(text);
			imageUploadEntity.setTitle(title);

			imageUploadEntity.setImage(fileName);

			fileUploadRepo.save(imageUploadEntity);
			
			File directory = new File("./");
			String imageFilePath =directory.getAbsolutePath();
			file.transferTo(new File(imageFilePath+Constants.uploadDir+fileName));
		} catch(Exception e) {
			logger.error(e);
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"not a valid file type given");
		}

		return imageUploadEntity;

	}
	
	public List<ImageUploadEntity> getAllImages() {
		return fileUploadRepo.findByPositionAsc();
	}

	public Optional<ImageUploadEntity> getImageById(int id) {
		return fileUploadRepo.findById(id);
	}

	public ImageUploadEntity updateStatus(int id, boolean status) {
		Optional<ImageUploadEntity> imageUploadEntity = fileUploadRepo.findById(id);
		if(!imageUploadEntity.isPresent()) {
			logger.debug("no elements with id "+id);
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "no element with id "+id);
		}
		ImageUploadEntity image = imageUploadEntity.get();
		image.setImageStatus(status);
		
		image.setUpdatedBy("1");
		fileUploadRepo.save(image);
		return image;
	}

	public ImageUploadEntity updatePosition(int id, Integer position) {
		Optional<ImageUploadEntity> imageUploadEntity = fileUploadRepo.findById(id);
		if(!imageUploadEntity.isPresent()) {
			logger.debug("no elements with id "+id);
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "no element with id "+id);
		}
		ImageUploadEntity image = imageUploadEntity.get();
		image.setPosition(position);
		image.setUpdatedBy("1");
		fileUploadRepo.save(image);
		return image;
	}

	public ImageUploadEntity updateImage(int id, MultipartFile file) throws IllegalStateException, IOException {
		Optional<ImageUploadEntity> imageUploadEntity = fileUploadRepo.findById(id);
		if(!imageUploadEntity.isPresent()) {
			logger.debug("no elements with id "+id);
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "no element with id "+id);
		}
		ImageUploadEntity image = imageUploadEntity.get();
		String oldImageName = image.getImage();
		
		if(!CommonMethods.isValidImageName(file.getOriginalFilename())) {
			logger.debug("not a valid image "+id);
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "not a valid image name "+file.getOriginalFilename());
		}
		String fileNameParts[] = file.getOriginalFilename().split("[.]");
		String fileName = fileNameParts[0]+"_"+CommonMethods.getFormattedCurrentDateTime()+"."+fileNameParts[1];
		fileName = fileName.replaceAll("[ ]+", "_");
		image.setImage(fileName);
		image.setUpdatedBy("1");
		
		File directory = new File("./");
		String imageFilePath =directory.getAbsolutePath();
		
		file.transferTo(new File(imageFilePath+Constants.uploadDir+fileName));
		
		fileUploadRepo.save(image);
		//delete the previous image
		File oldImage = new File(imageFilePath+Constants.uploadDir+oldImageName); 
		if(oldImage.delete()) {
			System.out.println("old image deleted");
			logger.debug("old image deleted");
		}
		else {
			System.out.println("failed to delete old image");
			logger.debug("failed to delete old image");
		}
		return image;
	}

	public ImageUploadEntity updateTitle(int id, String title) {
		Optional<ImageUploadEntity> imageUploadEntity = fileUploadRepo.findById(id);
		if(!imageUploadEntity.isPresent()) {
			logger.debug("no elements with id "+id);
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "no element with id "+id);
		}
		ImageUploadEntity image = imageUploadEntity.get();
		image.setTitle(title);
		
		image.setUpdatedBy("1");
		fileUploadRepo.save(image);
		return image;
	}

	public ImageUploadEntity updateDescription(int id, String description) {
		Optional<ImageUploadEntity> imageUploadEntity = fileUploadRepo.findById(id);
		if(!imageUploadEntity.isPresent()) {
			logger.debug("no elements with id "+id);
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "no element with id "+id);
		}
		ImageUploadEntity image = imageUploadEntity.get();
		image.setDescription(description);
		
		image.setUpdatedBy("1");
		fileUploadRepo.save(image);
		return image;
	}

}