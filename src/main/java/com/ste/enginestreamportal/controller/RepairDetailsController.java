package com.ste.enginestreamportal.controller;

import java.util.List;
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

import com.ste.enginestreamportal.model.RepairDetails;
import com.ste.enginestreamportal.payload.RepairDetailsPayload;
import com.ste.enginestreamportal.repository.QuotationRepository;
import com.ste.enginestreamportal.repository.RepairDetailsRepository;
import com.ste.enginestreamportal.util.Response;
import com.ste.enginestreamportal.util.Utils;

@RestController
@ResponseBody
@RequestMapping("/RepairDetails")
public class RepairDetailsController extends Utils{

	@Autowired
	RepairDetailsRepository repairDetailsRepository;

	@Autowired
	QuotationRepository quotationRepository;

	private Logger logger = LogManager.getLogger(RepairDetailsController.class);

	@GetMapping("/")
	public Response getAllRepairDetails() {

		Response response = new Response();
		response.setStatus(false);
		response.setResponse(null);
		try {
			List<RepairDetails> list = null;
			list = repairDetailsRepository.findAll();
			response.setStatus(true);
			response.setError(null);
			response.setResponse(list);
		} catch(Exception e) {
			response.setError("Unable to get all RepairDetails");
			logger.error("Error : "+e);
		}
		return response;
	}

	@GetMapping("/{Id}")
	public Response getRepairDetailsById(@PathVariable(value = "Id") Long id) {

		Response response = new Response();
		response.setStatus(false);
		response.setResponse(null);
		try {
			RepairDetails repairDetails = repairDetailsRepository.findById(id).get();
			response.setStatus(true);
			response.setError(null);
			response.setResponse(repairDetails);
		}
		catch(NoSuchElementException ex) {
			response.setError("Unable to find RepairDetails with id :"+id);
			logger.error("Error : "+ex);
		}
		catch(Exception e) {
			response.setError("Unable to get RepairDetails");
			logger.error("Error : "+e);
		}
		return response;
	}

	@PostMapping("/")
	public Response createRepairDetails(@Valid @RequestBody RepairDetailsPayload repairDetailsPayload) {

		Response response = new Response();
		response.setStatus(false);
		response.setResponse(null);
		try {
			if(null == repairDetailsPayload) {
				response.setError("Payload cannot be null or empty");
			}else if(isNullOrEmpty(repairDetailsPayload.getRepairNo())) {
				response.setError("Repair Number cannot be null or empty");
			}
			else {
				RepairDetails repairDetails = new RepairDetails();
				repairDetails.setPrice(repairDetailsPayload.getPrice());
				repairDetails.setRepairDescription(repairDetailsPayload.getRepairDescription());
				repairDetails.setRepairNo(repairDetailsPayload.getRepairNo());
				repairDetails.setQuotationId(quotationRepository.findById(repairDetailsPayload.getQuotationId()).get());
				repairDetails.setCreatedBy(repairDetailsPayload.getCreatedBy());
				repairDetails.setUpdatedBy(repairDetailsPayload.getCreatedBy());
				repairDetails = repairDetailsRepository.save(repairDetails);
				response.setStatus(true);
				response.setError(null);
				response.setResponse(repairDetails);
			}
		} catch (Exception e) {
			response.setError("Could not create RepairDetails");
			logger.error("Error : "+e);
		}
		return response;
	}

	@PutMapping("/")
	public Response updateRepairDetails(@Valid @RequestBody RepairDetailsPayload repairDetailsPayload) {

		Response response = new Response();
		response.setStatus(false);
		response.setResponse(null);
		try {
			if(null == repairDetailsPayload) {
				response.setError("Payload cannot be null or empty");
			}else if(isNullOrEmpty(repairDetailsPayload.getRepairNo())) {
				response.setError("Repair Number cannot be null or empty");
			}else if(isZeroOrNull(repairDetailsPayload.getId())) {
				response.setError("Id cannot zero or null");
			}
			else {
				RepairDetails repairDetails = null;
				repairDetails = repairDetailsRepository.findById(repairDetailsPayload.getId()).get();
				repairDetails.setPrice(repairDetailsPayload.getPrice());
				repairDetails.setRepairDescription(repairDetailsPayload.getRepairDescription());
				repairDetails.setRepairNo(repairDetailsPayload.getRepairNo());
				repairDetails.setQuotationId(quotationRepository.findById(repairDetailsPayload.getQuotationId()).get());
				repairDetails.setUpdatedBy(repairDetailsPayload.getUpdatedBy());
				repairDetailsRepository.save(repairDetails);
				response.setStatus(true);
				response.setError(null);
				response.setResponse(repairDetails);
			}
		} catch(Exception ex) {
			response.setError("Please pass valid RepairDetailsPayload data to update.");
			logger.error("Error : "+ex);
		}
		return response;
	}
}
