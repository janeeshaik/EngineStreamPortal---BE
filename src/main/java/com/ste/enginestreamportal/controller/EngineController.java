package com.ste.enginestreamportal.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ste.enginestreamportal.repository.EngineRepository;
import com.ste.enginestreamportal.util.Response;

@RestController
@ResponseBody
@RequestMapping("/Engine")
public class EngineController {

	@Autowired
	EngineRepository engineRepository;

	private Logger logger = LogManager.getLogger(EngineController.class);

	@GetMapping("/")
	public Response getAllEngineDetails() {
		Response response = new Response();
		try {
			response.setStatus(true);
			response.setError(null);
			response.setResponse(engineRepository.findAll());
		}catch(Exception e) {
			response.setStatus(false);
			response.setError("Unable to get all Engines");
			logger.error("Error : "+e);
			response.setResponse(null);
		}

		return response;
	}
}
