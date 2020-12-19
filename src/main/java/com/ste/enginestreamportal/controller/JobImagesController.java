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

import com.ste.enginestreamportal.model.JobImages;
import com.ste.enginestreamportal.payload.JobImagesPayload;
import com.ste.enginestreamportal.repository.JobDetailsRepository;
import com.ste.enginestreamportal.repository.JobImagesRepository;
import com.ste.enginestreamportal.util.Response;
import com.ste.enginestreamportal.util.Utils;

@RestController
@ResponseBody
@RequestMapping("/JobImages")
public class JobImagesController extends Utils{

	@Autowired
	JobImagesRepository jobImagesRepository;

	@Autowired
	JobDetailsRepository jobDetailsRepository;

	private Logger logger = LogManager.getLogger(JobImagesController.class);

	@GetMapping("/")
	public Response getAllJobImages() {
		Response response = new Response();
		try {
			response.setStatus(true);
			response.setError(null);
			response.setResponse(jobImagesRepository.findAll());
		} catch(Exception e) {
			response.setStatus(false);
			response.setError("Unable to get all JobImages");
			logger.error("Error : "+e);
			response.setResponse(null);
		}

		return response;
	}

	@GetMapping("/{Id}")
	public Response getJobImagesById(@PathVariable(value = "Id") Long id) {

		Response response = new Response();
		try {
			JobImages jobImages = jobImagesRepository.findById(id).get();
			response.setStatus(true);
			response.setError(null);
			response.setResponse(jobImages);
		} catch(NoSuchElementException ex) {
			response.setStatus(false);
			response.setError("Unable to find jobImages with id :"+id);
			logger.error("Error : "+ex);
			response.setResponse(null);
		} catch(Exception e) {
			response.setStatus(false);
			response.setError("Unable to get jobImages");
			logger.error("Error : "+e);
			response.setResponse(null);
		}

		return response;
	}

	@PostMapping("/")
	public Response createJobImages(@Valid @RequestBody JobImagesPayload jobImagesPayload) {

		Response response = new Response();
		response.setStatus(false);
		response.setResponse(null);
		try {
			if(null == jobImagesPayload) {
				response.setError("Payload cannot be null or empty");
			}
			else if(isZeroOrNull(jobImagesPayload.getJobId())) {			
				response.setError("Job Id cannot be null");
			}
			else if(isNullOrEmpty(jobImagesPayload.getImage())) {
				response.setError("Image cannot be null or empty");
			}
			else {
				JobImages jobImages = new JobImages();
				jobImages.setImage(jobImagesPayload.getImage());
				jobImages.setJobId(jobDetailsRepository.findById(jobImagesPayload.getJobId()).get());
				jobImages.setCreatedBy(jobImagesPayload.getCreatedBy());
				jobImages.setUpdatedBy(jobImagesPayload.getCreatedBy());
				jobImages = jobImagesRepository.save(jobImages);

				response.setStatus(true);
				response.setError(null);
				response.setResponse(jobImages);
			}

		} catch(Exception e) {
			response.setError("Unable to create jobImages");
			logger.error("Error : "+e);
		}

		return response;
	}

	@PutMapping("/")
	public Response updateJobImages(@Valid @RequestBody JobImagesPayload jobImagesPayload) {

		Response response = new Response();
		response.setStatus(false);
		response.setResponse(null);
		JobImages jobImages = null;
		try {
			if(null == jobImagesPayload) {
				response.setError("Payload cannot be null or empty");
			}
			else if(isZeroOrNull(jobImagesPayload.getJobId())) {			
				response.setError("Job Id cannot be null");
			}
			else if(isNullOrEmpty(jobImagesPayload.getImage())) {
				response.setError("Image cannot be null or empty");
			}
			else if(isZeroOrNull(jobImagesPayload.getId())) {
				response.setError("Id cannot be null or zero");
			}
			else {
				jobImages = jobImagesRepository.findById(jobImagesPayload.getId()).get();
				jobImages.setImage(jobImagesPayload.getImage());
				jobImages.setJobId(jobDetailsRepository.findById(jobImagesPayload.getJobId()).get());
				jobImages.setUpdatedBy(jobImagesPayload.getUpdatedBy());
				jobImages.setStatus(jobImagesPayload.getStatus());
				jobImagesRepository.save(jobImages);
				response.setStatus(true);
				response.setError(null);
				response.setResponse(jobImages);
			}
		} catch(Exception e) {
			response.setError("Unable to update jobImages");
			logger.error("Error : "+e);
		}

		return response;
	}
}
