package com.ste.enginestreamportal.controller;

import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.ste.enginestreamportal.model.ImageUploadEntity;
import com.ste.enginestreamportal.services.FileUploadService;
import com.ste.enginestreamportal.util.FileUploadProperties;
import com.ste.enginestreamportal.util.Response;


@RestController
@RequestMapping("/fileUpload")
public class FileUploadController {
	
	@Autowired
	FileUploadService fileUploadService;
	
	@Autowired
	FileUploadProperties fileUploadProperties;
	
	private Logger logger = LogManager.getLogger(FileUploadController.class);
	
	@PostMapping("/uploadFile")
	public Response uploadFile(@RequestParam("image") MultipartFile file,
			@RequestParam("title") String title, @RequestParam("text") String text, @RequestParam("description") String description, @RequestParam("status") boolean status,
			@RequestParam("position") Integer position
			) throws NumberFormatException, Exception {
		Response response = new Response();
		try {
			response.setStatus(true);
			response.setError(null);
			response.setResponse(fileUploadService.uploadFile(file, title, text, description, status, position));
		} catch(ResponseStatusException re) {
			response.setStatus(false);
			response.setResponse(null);
			response.setError(re.getReason());
			logger.error("Error : "+re);
		}  catch(Exception e) {
			response.setStatus(false);
			response.setError("Unable to upload image");
			logger.error("Error : "+e);
			response.setResponse(null);
		}
		return response;
		
	}
	
	
	@GetMapping("/getAllImages")
	public Response getAllImages() {
		Response response = new Response();
		try {
			response.setStatus(true);
			response.setError(null);
			response.setResponse(fileUploadService.getAllImages());
		} catch(ResponseStatusException re) {
			response.setStatus(false);
			response.setResponse(null);
			response.setError(re.getReason());
			logger.error("Error : "+re);
		} catch(Exception e) {
			logger.error("Error : "+e);
			response.setStatus(false);
			response.setError("Unable to get all users");
			response.setResponse(null);
		}
		return response;
	}
	
	@GetMapping("/getImageById/{id}")
	public Response getImageById(@PathVariable int id) {
		Response response = new Response();
		try {
			response.setStatus(true);
			response.setError(null);
			response.setResponse(fileUploadService.getImageById(id));
		} catch(ResponseStatusException re) {
			response.setStatus(false);
			response.setResponse(null);
			response.setError(re.getReason());
			logger.error("Error : "+re);
		} catch(Exception e) {
			response.setStatus(false);
			response.setError("Unable to get all images");
			logger.error("Error : "+e);
			response.setResponse(null);
		}
		return response;
		
	}
	
	@PutMapping("/updateStatus/{id}")
	public Response updateStatus(@PathVariable int id, @RequestParam("imageStatus") boolean imageStatus) {
		Response response = new Response();
		try {
			response.setStatus(true);
			response.setError(null);
			response.setResponse(fileUploadService.updateStatus(id,imageStatus));
		} catch(ResponseStatusException re) {
			response.setStatus(false);
			response.setResponse(null);
			response.setError(re.getReason());
			logger.error("Error : "+re);
		} catch(Exception e) {
			response.setStatus(false);
			response.setError("Unable to update status");
			logger.error("Error : "+e);
			response.setResponse(null);
		}
		return response;
	}

	@PutMapping("/updatePosition/{id}")
	public Response updatePosition(@PathVariable int id, @RequestParam("position") Integer position) {
		Response response = new Response();
		try {
			response.setStatus(true);
			response.setError(null);
			response.setResponse(fileUploadService.updatePosition(id,position));
		} catch(ResponseStatusException re) {
			response.setStatus(false);
			response.setResponse(null);
			response.setError(re.getReason());
			logger.error("Error : "+re);
		} catch(Exception e) {
			response.setStatus(false);
			response.setError("Unable to update position");
			logger.error("Error : "+e);
			response.setResponse(null);
		}
		return response;
	}
	
	@PutMapping("/updateImage/{id}")
	public Response updateImage(@PathVariable int id, @RequestParam("image") MultipartFile image) throws IllegalStateException, IOException {
		Response response = new Response();
		try {
			response.setStatus(true);
			response.setError(null);
			response.setResponse(fileUploadService.updateImage(id,image));
		} catch(ResponseStatusException re) {
			response.setStatus(false);
			response.setResponse(null);
			response.setError(re.getReason());
			logger.error("Error : "+re);
		} catch(Exception e) {
			response.setStatus(false);
			response.setError("Unable to update image");
			logger.error("Error : "+e);
			response.setResponse(null);
		}
		return response;
	}
	
	@PutMapping("/updateTitle/{id}")
	public Response updateTitle(@PathVariable int id, @RequestParam("title") String title) {
		Response response = new Response();
		try {
			response.setStatus(true);
			response.setError(null);
			response.setResponse(fileUploadService.updateTitle(id,title));
		} catch(ResponseStatusException re) {
			response.setStatus(false);
			response.setResponse(null);
			response.setError(re.getReason());
			logger.error("Error : "+re);
		} catch(Exception e) {
			response.setStatus(false);
			response.setError("Unable to update title");
			logger.error("Error : "+e);
			response.setResponse(null);
		}
		return response;
	}
	
	@PutMapping("/updateDescription/{id}")
	public Response updateDescription(@PathVariable int id, @RequestParam("description") String description) {
		Response response = new Response();
		try {
			response.setStatus(true);
			response.setError(null);
			response.setResponse(fileUploadService.updateDescription(id,description));
		} catch(ResponseStatusException re) {
			response.setStatus(false);
			response.setResponse(null);
			response.setError(re.getReason());
			logger.error("Error : "+re);
		} catch(Exception e) {
			response.setStatus(false);
			response.setError("Unable to update description");
			logger.error("Error : "+e);
			response.setResponse(null);
		}
		return response;
	}
	
}
