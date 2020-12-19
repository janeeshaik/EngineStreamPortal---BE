package com.ste.enginestreamportal.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ste.enginestreamportal.repository.PlantRepository;
import com.ste.enginestreamportal.util.Response;

@RestController
@ResponseBody
@RequestMapping("/Plant")
public class PlantController {

	@Autowired
	PlantRepository plantRepository;
	
	private Logger logger = LogManager.getLogger(PlantController.class);

	@GetMapping("/")
	public Response getAllPlantDetails() {
		Response response = new Response();
		try {
			logger.debug("Fetching all Pages.");
			response.setResponse(plantRepository.findAll());
			response.setStatus(true);
			response.setError(null);
		}catch(Exception e) {
			response.setStatus(false);
			response.setError("Unable to get all PlantDetails");
			logger.error("Error : "+e);
			response.setResponse(null);
		}
		return response;
	}
}
