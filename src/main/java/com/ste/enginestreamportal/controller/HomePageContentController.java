package com.ste.enginestreamportal.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

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

import com.ste.enginestreamportal.model.HomePageContentEntity;
import com.ste.enginestreamportal.services.HomePageContentService;
import com.ste.enginestreamportal.util.Response;


@RestController
@RequestMapping("/HomePageContent")
public class HomePageContentController {
	
	@Autowired
	HomePageContentService homePageContentService;
	
	private Logger logger = LogManager.getLogger(HomePageContentController.class);
	
	@PostMapping("/saveHomePageContentRp")
	public Response saveHomePageContentRp(@RequestParam("title") String title, @RequestParam("content") String content,@RequestParam("image") MultipartFile file) {
		Response response = new Response();
		try {
			response.setStatus(true);
			response.setError(null);
			response.setResponse(homePageContentService.saveHomePageContentRp(title, content, file));
		} catch(ResponseStatusException re) {
			response.setStatus(false);
			response.setResponse(null);
			response.setError(re.getReason());
			logger.error("Error : "+re);
		}  catch(Exception e) {
			response.setStatus(false);
			response.setError("Unable to save homepage");
			logger.error("Error : "+e);
			response.setResponse(null);
		}
		
		return response;
		
	}
	
	@GetMapping("/getAllHomePageContent")
	public Response getAllHomePageContent() {
		Response response = new Response();
		try {
			response.setStatus(true);
			response.setError(null);
			response.setResponse(homePageContentService.getAllHomePageContent());
		} catch(ResponseStatusException re) {
			response.setStatus(false);
			response.setResponse(null);
			response.setError(re.getReason());
			logger.error("Error : "+re);
		}  catch(Exception e) {
			response.setStatus(false);
			response.setError("Unable to get all homePageContent");
			logger.error("Error : "+e);
			response.setResponse(null);
		}
		return response;
	}
	
	@GetMapping("/getHomePageContentById/{id}")
	public Response getHomePageContentById(@PathVariable int id) {
		Response response = new Response();
		try {
			response.setStatus(true);
			response.setError(null);
			response.setResponse(homePageContentService.getHomePageContentById(id));
		} catch(ResponseStatusException re) {
			response.setStatus(false);
			response.setResponse(null);
			response.setError(re.getReason());
			logger.error("Error : "+re);
		}  catch(Exception e) {
			response.setStatus(false);
			response.setError("Unable to get home Page Content with id "+id);
			logger.error("Error : "+e);
			response.setResponse(null);
		}
		return response;
	}
	
	@PutMapping("/updateHomePageContentTitle/{id}")
	public Response updateHomePageContentTitle(@PathVariable int id, @RequestParam("title") String title) {
		Response response = new Response();
		try {
			response.setStatus(true);
			response.setError(null);
			response.setResponse(homePageContentService.updateHomePageContentTitle(id, title));
		} catch(ResponseStatusException re) {
			response.setStatus(false);
			response.setResponse(null);
			response.setError(re.getReason());
			logger.error("Error : "+re);
		}  catch(Exception e) {
			response.setStatus(false);
			response.setError("Unable to get all users");
			logger.error("Error : "+e);
			response.setResponse(null);
		}
		return response;
	}
	
	@PutMapping("/updateHomePageContentContent/{id}")
	public Response updateHomePageContentContent(@PathVariable int id, @RequestParam("content") String content) {
		Response response = new Response();
		try {
			response.setStatus(true);
			response.setError(null);
			response.setResponse(homePageContentService.updateHomePageContentContent(id, content));
		} catch(ResponseStatusException re) {
			response.setStatus(false);
			response.setResponse(null);
			response.setError(re.getReason());
			logger.error("Error : "+re);
		}  catch(Exception e) {
			response.setStatus(false);
			response.setError("Unable to get all users");
			logger.error("Error : "+e);
			response.setResponse(null);
		}
		return response;
	}
	
	@PutMapping("/updateHomePageContentImage/{id}")
	public Response updateHomePageContentImage(@PathVariable int id, @RequestParam("image") MultipartFile image) throws IllegalStateException, IOException {
		Response response = new Response();
		try {
			response.setStatus(true);
			response.setError(null);
			response.setResponse(homePageContentService.updateHomePageContentImage(id, image));
		} catch(ResponseStatusException re) {
			response.setStatus(false);
			response.setResponse(null);
			response.setError(re.getReason());
			logger.error("Error : "+re);
		}  catch(Exception e) {
			response.setStatus(false);
			response.setError("Unable to get all users");
			logger.error("Error : "+e);
			response.setResponse(null);
		}
		return response;
	}
	
	
}
