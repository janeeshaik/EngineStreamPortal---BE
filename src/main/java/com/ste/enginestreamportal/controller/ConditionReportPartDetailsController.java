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

import com.ste.enginestreamportal.model.ConditionReportPartDetails;
import com.ste.enginestreamportal.payload.ConditionReportPartDetailsPayload;
import com.ste.enginestreamportal.repository.ConditionReportPartDetailsRepository;
import com.ste.enginestreamportal.repository.JobDetailsRepository;
import com.ste.enginestreamportal.repository.PartInfoRepository;
import com.ste.enginestreamportal.util.Response;
import com.ste.enginestreamportal.util.Utils;

@RestController
@ResponseBody
@RequestMapping("/ConditionReportPartDetails")
public class ConditionReportPartDetailsController extends Utils{

	@Autowired
	ConditionReportPartDetailsRepository conditionReportPartDetailsRepository;

	@Autowired
	JobDetailsRepository jobDetailsRepository;

	@Autowired
	PartInfoRepository partInfoRepository;

	private Logger logger = LogManager.getLogger(ConditionReportPartDetailsController.class);

	@GetMapping("/")
	public Response getAllConditionReportPartDetails() {

		Response response = new Response();
		try {
			response.setStatus(true);
			response.setError(null);
			response.setResponse(conditionReportPartDetailsRepository.findAll());
		} catch(Exception e) {
			response.setStatus(false);
			response.setError("Unable to get all conditionReportPartDetails");
			logger.error("Error : "+e);
			response.setResponse(null);
		}
		return response;
	}

	@GetMapping("/{Id}")
	public Response getConditionReportPartDetailsById(@PathVariable(value = "Id") Long id) {

		Response response = new Response();
		try {
			ConditionReportPartDetails conditionReportPartDetails = conditionReportPartDetailsRepository.findById(id).get();
			response.setStatus(true);
			response.setError(null);
			response.setResponse(conditionReportPartDetails);
		} 
		catch(NoSuchElementException ex) {
			response.setStatus(false);
			response.setError("Unable to find conditionReportPartDetails with id :"+id);
			logger.error("Error : "+ex);
			response.setResponse(null);
		} catch(Exception e) {
			response.setStatus(false);
			response.setError("Unable to get conditionReportPartDetails");
			logger.error("Error : "+e);
			response.setResponse(null);
		}
		return response;
	}

	@PostMapping("/")
	public Response createConditionReportPartDetails(@Valid @RequestBody ConditionReportPartDetailsPayload conditionReportPartDetailsPayload) {

		Response response = new Response();
		response.setStatus(false);
		response.setResponse(null);
		try {
			if(null == conditionReportPartDetailsPayload) {
				response.setError("Payload cannot be null or empty");
			}
			else if(isZeroOrNull(conditionReportPartDetailsPayload.getJobId())) {			
				response.setError("jobId cannot be null or Zero");
			}

			else if(isZeroOrNull(conditionReportPartDetailsPayload.getPartId())) {
				response.setError("PartId cannot be null or Zero");
			}
			else {
				ConditionReportPartDetails conditionReportPartDetails = new ConditionReportPartDetails();
				conditionReportPartDetails.setQuantity(conditionReportPartDetailsPayload.getQuantity());
				conditionReportPartDetails.setJobId(jobDetailsRepository.findById(conditionReportPartDetailsPayload.getJobId()).get());
				conditionReportPartDetails.setPartId(partInfoRepository.findById(conditionReportPartDetailsPayload.getPartId()).get());
				conditionReportPartDetails.setCreatedBy(conditionReportPartDetailsPayload.getCreatedBy());
				conditionReportPartDetails.setUpdatedBy(conditionReportPartDetailsPayload.getCreatedBy());
				conditionReportPartDetails = conditionReportPartDetailsRepository.save(conditionReportPartDetails);
				response.setStatus(true);
				response.setError(null);
				response.setResponse(conditionReportPartDetails);
			}
		}catch(Exception e) {
			response.setError("Unable to create conditionReportPartDetails");
			logger.error("Error : "+e);
		}
		return response;
	}

	@PutMapping("/")
	public Response updateConditionReportPartDetails(@Valid @RequestBody ConditionReportPartDetailsPayload conditionReportPartDetailsPayload) {

		Response response = new Response();
		response.setStatus(false);
		response.setResponse(null);
		ConditionReportPartDetails conditionReportPartDetails = null;
		try {
			if(null == conditionReportPartDetailsPayload) {
				response.setError("Payload cannot be null or empty");
			}
			else if(isZeroOrNull(conditionReportPartDetailsPayload.getJobId())) {			
				response.setError("jobId cannot be null or Zero");
			}

			else if(isZeroOrNull(conditionReportPartDetailsPayload.getPartId())) {
				response.setError("PartId cannot be null or Zero");
			}
			else if(isZeroOrNull(conditionReportPartDetailsPayload.getId())) {
				response.setError("Id cannot be null or Zero");
			}
			else {
				conditionReportPartDetails = conditionReportPartDetailsRepository.findById(conditionReportPartDetailsPayload.getId()).get();
				conditionReportPartDetails.setQuantity(conditionReportPartDetailsPayload.getQuantity());
				conditionReportPartDetails.setJobId(jobDetailsRepository.findById(conditionReportPartDetailsPayload.getJobId()).get());
				conditionReportPartDetails.setPartId(partInfoRepository.findById(conditionReportPartDetailsPayload.getPartId()).get());
				conditionReportPartDetails.setUpdatedBy(conditionReportPartDetailsPayload.getUpdatedBy());
				conditionReportPartDetails.setStatus(conditionReportPartDetailsPayload.getStatus());
				conditionReportPartDetails = conditionReportPartDetailsRepository.save(conditionReportPartDetails);

				response.setStatus(true);
				response.setError(null);
				response.setResponse(conditionReportPartDetails);
			} 
		}catch(Exception e) {
			response.setError("Unable to update conditionReportPartDetails");
			logger.error("Error : "+e);
		}
		return response;
	}
}
