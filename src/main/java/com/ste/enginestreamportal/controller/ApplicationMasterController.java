package com.ste.enginestreamportal.controller;

import java.util.NoSuchElementException;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.ste.enginestreamportal.model.ApplicationMaster;
import com.ste.enginestreamportal.payload.ApplicationMasterPayload;
import com.ste.enginestreamportal.repository.ApplicationMasterRepository;
import com.ste.enginestreamportal.services.ApplicationMasterService;
import com.ste.enginestreamportal.util.Response;
import com.ste.enginestreamportal.util.Utils;



@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/ApplicationMaster")
@ResponseBody
public class ApplicationMasterController extends Utils{

	@Autowired
	ApplicationMasterRepository applicationMasterRepository;

	@Autowired
	ApplicationMasterService applicationMasterService;

	private Logger logger = LogManager.getLogger(ApplicationMasterController.class);

	@GetMapping("/")
	public Response getAllApplicationMasterDetails() {
		Response response = new Response();
		try {
			response.setStatus(true);
			response.setError(null);
			response.setResponse(applicationMasterRepository.findAll());
		}
		catch(Exception e) {
			response.setStatus(false);
			response.setError("Unable to get all ApplicationMasterDetails");
			logger.error("Error : "+e);
			response.setResponse(null);
		}
		return response;
	}

	@GetMapping("/{Id}")
	public Response getApplicationMasterById(@PathVariable(value = "Id") Long Id) {
		Response response = new Response();
		try {
			ApplicationMaster applicationMaster = applicationMasterRepository.findById(Id).get();
			response.setStatus(true);
			response.setError(null);
			response.setResponse(applicationMaster);
		}
		catch(NoSuchElementException ex) {
			response.setStatus(false);
			response.setError("Unable to find ApplicationMasterDetails with id :"+Id);
			logger.error("Error : "+ex);
			response.setResponse(null);
		}
		catch(Exception e) {
			response.setStatus(false);
			response.setError("Unable to get ApplicationMasterDetails");
			logger.error("Error : "+e);
			response.setResponse(null);
		}
		return response;			
	}


	@PostMapping("/")
	public Response createApplicationMaster(@Valid @RequestBody ApplicationMasterPayload applicationMasterPayload) {

		Response response = new Response();
		response.setStatus(false);
		response.setResponse(null);
		try {
			if(null == applicationMasterPayload) {
				response.setError("Payload cannot be null or empty");
			}
			else if(isNullOrEmpty(applicationMasterPayload.getName())) {
				response.setError("Name cannot be null or empty");
			}
			else {
				ApplicationMaster applicationMaster = applicationMasterService.createApplicationMaster(applicationMasterPayload);
				response.setStatus(true);
				response.setError(null);
				response.setResponse(applicationMaster);
			}
		} catch(ResponseStatusException re) {
			response.setError(re.getReason());
			logger.error("Error : "+re);
		} catch(Exception e) {
			response.setError("Unable to create ApplicationMaster");
			logger.error("Error : "+e);
		}
		return response;
	}

	@PutMapping("/")
	public Response updateUser(@Valid @RequestBody ApplicationMasterPayload applicationMasterPayload) {
		Response response = new Response();
		response.setStatus(false);
		response.setResponse(null);
		try {
			if (null == applicationMasterPayload) {
				response.setError("Payload cannot be empty or null");
			}
			else if(isZeroOrNull(applicationMasterPayload.getId())) {
				response.setError("Id cannot be null or empty");
			}
			else if(isNullOrEmpty(applicationMasterPayload.getName())) {
				response.setError("Name cannot be null or empty");
			}
			else {
				ApplicationMaster applicationMaster = applicationMasterService.updateApplicationMaster(applicationMasterPayload);
				response.setStatus(true);
				response.setError(null);
				response.setResponse(applicationMaster);
			}
		} catch(ResponseStatusException re) {
			response.setError(re.getReason());
			logger.error("Error : "+re);
		} catch(Exception e) {
			response.setError("Unable to update ApplicationMaster");
			logger.error("Error : "+e);
		}

		return response;
	}

}
