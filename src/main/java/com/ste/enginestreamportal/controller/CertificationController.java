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

import com.ste.enginestreamportal.model.Certification;
import com.ste.enginestreamportal.payload.CertificationPayload;
import com.ste.enginestreamportal.repository.CertificationRepository;
import com.ste.enginestreamportal.util.Response;
import com.ste.enginestreamportal.util.Utils;

@RestController
@ResponseBody
@RequestMapping("/Certification")
public class CertificationController extends Utils{

	@Autowired
	CertificationRepository certificationRepository;
	
	private Logger logger = LogManager.getLogger(CertificationController.class);
	
	@GetMapping("/")
	public Response getAllCertifications() {
		Response response = new Response();
		try {
			response.setStatus(true);
			response.setError(null);
			response.setResponse(certificationRepository.findAll());
		} catch(Exception e) {
			response.setStatus(false);
			response.setError("Unable to get all Certifications");
			logger.error("Error : "+e);
			response.setResponse(null);
		}

		return response;
	}
	
	@GetMapping("/{Id}")
	public Response getCertificationById(@PathVariable(value = "Id") Long id) {

		Response response = new Response();
		try {
			Certification certification = certificationRepository.findById(id).get();
			response.setStatus(true);
			response.setError(null);
			response.setResponse(certification);
		}
		 catch(NoSuchElementException ex) {
			 response.setStatus(false);
				response.setError("Unable to find Certification with id :"+id);
				logger.error("Error : "+ex);
				response.setResponse(null);
		 }
		catch(Exception e) {
			response.setStatus(false);
			response.setError("Unable to get Certification");
			logger.error("Error : "+e);
			response.setResponse(null);
		}

		return response;
	}
	
	@PostMapping("/")
	public Response createCertification(@Valid @RequestBody CertificationPayload certificationPayload) {
		
		Response response = new Response();
		response.setStatus(false);
		response.setResponse(null);
		try {
			if(null == certificationPayload) {
				response.setError("Payload cannot be null or empty");
			}else if(isNullOrEmpty(certificationPayload.getName())) {
				response.setError("Name cannot be null or empty");
			}
			
			else {
				Certification certification = new Certification();
				certification.setName(certificationPayload.getName());
				certification.setCreatedBy(certificationPayload.getCreatedBy());
				certification.setUpdatedBy(certification.getCreatedBy());
				certification = certificationRepository.save(certification);
				response.setStatus(true);
				response.setError(null);
				response.setResponse(certification);
			}
		} catch(Exception e) {
			response.setError("Unable to create certification");
			logger.error("Error : "+e);
		}
		
		return response;
	}
	
	@PutMapping("/")
	public Response updateCertification(@Valid @RequestBody CertificationPayload certificationPayload) {
		
		Response response = new Response();
		response.setStatus(false);
		response.setResponse(null);
		Certification certification = null;
		try {
				if (null == certificationPayload) {
					response.setError("Payload cannot be empty or null");
				}else if(isNullOrEmpty(certificationPayload.getName())) {
					response.setError("Name cannot be null or empty");
				}else if(isZeroOrNull(certificationPayload.getId())) {
					response.setError("Certification Id cannot zero or null");
				}
				else {
					certification = certificationRepository.findById(certificationPayload.getId()).get();
					certification.setName(certificationPayload.getName());
					certification.setUpdatedBy(certification.getUpdatedBy());
					certification.setStatus(certificationPayload.getStatus());
					certificationRepository.save(certification);
					response.setStatus(true);
					response.setError(null);
					response.setResponse(certification);
				}
		} catch(Exception ex) {
			response.setError("Unable to update  Certification");
			logger.error("Error : "+ex);
		}
		return response;
	}
}
