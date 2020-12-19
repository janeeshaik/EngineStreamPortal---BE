package com.ste.enginestreamportal.controller;

import java.util.NoSuchElementException;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ste.enginestreamportal.model.DollarConversions;
import com.ste.enginestreamportal.payload.DollarConversionsPayload;
import com.ste.enginestreamportal.repository.DollarConversionsRepository;
import com.ste.enginestreamportal.util.Response;
import com.ste.enginestreamportal.util.Utils;

@RestController
@ResponseBody
@RequestMapping("/DollarConversions")
public class DollarConversionsController extends Utils {

	@Autowired
	DollarConversionsRepository dollarConversionsRepository;

	private Logger logger = LogManager.getLogger(DollarConversionsController.class);

	@GetMapping("/")
	public Response getAllDollarConversions() {
		Response response = new Response();
		try {
			response.setStatus(true);
			response.setError(null);
			response.setResponse(dollarConversionsRepository.findAll());
		} catch(Exception e) {
			response.setStatus(false);
			response.setError("Unable to get all DollarConversions");
			logger.error("Error : "+e);
			response.setResponse(null);
		}

		return response;
	}

	@GetMapping("/{Id}")
	public Response getDollarConversionsById(@PathVariable(value = "Id") Long id) {
		Response response = new Response();
		try {
			DollarConversions dollarConversions = dollarConversionsRepository.findById(id).get();
			response.setStatus(true);
			response.setError(null);
			response.setResponse(dollarConversions);
		} catch(NoSuchElementException ex) {
			response.setStatus(false);
			response.setError("Unable to find dollarConversions with id :"+id);
			logger.error("Error : "+ex);
			response.setResponse(null);
		} catch(Exception e) {
			response.setStatus(false);
			response.setError("Unable to get dollarConversions");
			logger.error("Error : "+e);
			response.setResponse(null);
		}
		return response;
	}

	@PostMapping("/")
	public Response createDollarConversion(@Valid @RequestBody DollarConversionsPayload dollarConversionsPayload) {

		Response response = new Response();
		response.setStatus(false);
		response.setResponse(null);
		try {
			if(null == dollarConversionsPayload) {
				response.setError("Payload cannot be null or empty");
			}
			else if(isZeroOrNull(dollarConversionsPayload.getSingaporeDollar())) {
				response.setError("SingaporeDollar cannot be null or empty");
			}
			else if(isZeroOrNull(dollarConversionsPayload.getUsDollar())) {
				response.setError("UsDollar cannot be null or empty");
			}
			else {
				DollarConversions dollarConversions = new DollarConversions();
				dollarConversions.setSingaporeDollar(dollarConversionsPayload.getSingaporeDollar());
				dollarConversions.setUsDollar(dollarConversionsPayload.getUsDollar());
				dollarConversions.setCreatedBy(dollarConversionsPayload.getCreatedBy());
				dollarConversions.setUpdatedBy(dollarConversionsPayload.getCreatedBy());
				dollarConversions = dollarConversionsRepository.save(dollarConversions);

				response.setStatus(true);
				response.setError(null);
				response.setResponse(dollarConversions);
			}
		} catch(Exception e) {
			response.setError("Unable to create dollarConversions");
			logger.error("Error : "+e);
		}
		return response;
	}

	@PutMapping("/")
	public Response updateDollarConversion(@Valid @RequestBody DollarConversionsPayload dollarConversionsPayload) {

		Response response = new Response();
		response.setStatus(false);
		response.setResponse(null);
		DollarConversions dollarConversions = null;
		try {
			if (null == dollarConversionsPayload) {
				response.setError("Payload cannot be empty or null");
			}
			else if(isZeroOrNull(dollarConversionsPayload.getId())) {
				response.setError("Id cannot be null or empty");
			}
			else if(isZeroOrNull(dollarConversionsPayload.getSingaporeDollar())) {
				response.setError("SingaporeDollar cannot be null or empty");
			}
			else if(isZeroOrNull(dollarConversionsPayload.getUsDollar())) {
				response.setError("UsDollar cannot be null or empty");
			}
			else {
				dollarConversions = dollarConversionsRepository.findById(dollarConversionsPayload.getId()).get();
				dollarConversions.setSingaporeDollar(dollarConversionsPayload.getSingaporeDollar());
				dollarConversions.setUsDollar(dollarConversionsPayload.getUsDollar());
				dollarConversions.setUpdatedBy(dollarConversionsPayload.getUpdatedBy());
				dollarConversions.setStatus(dollarConversionsPayload.getStatus());
				dollarConversionsRepository.save(dollarConversions);
				response.setStatus(true);
				response.setError(null);
				response.setResponse(dollarConversions);
			}
		} catch(Exception ex) {
			response.setError("Unable to update dollarConversions");
			logger.error("Error : "+ex);
		}

		return response;
	}
}
