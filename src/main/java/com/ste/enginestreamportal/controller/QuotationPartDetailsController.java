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

import com.ste.enginestreamportal.model.QuotationPartDetails;
import com.ste.enginestreamportal.payload.QuotationPartDetailsPayload;
import com.ste.enginestreamportal.repository.ConditionReportPartDetailsRepository;
import com.ste.enginestreamportal.repository.QuotationPartDetailsRepository;
import com.ste.enginestreamportal.repository.QuotationRepository;
import com.ste.enginestreamportal.util.Response;
import com.ste.enginestreamportal.util.Utils;

@RestController
@ResponseBody
@RequestMapping("/QuotationPartDetails")
public class QuotationPartDetailsController extends Utils{

	@Autowired
	QuotationPartDetailsRepository quotationPartDetailsRepository;

	@Autowired
	ConditionReportPartDetailsRepository conditionReportPartDetailsRepository;

	@Autowired
	QuotationRepository quotationRepository;

	private Logger logger = LogManager.getLogger(QuotationPartDetailsController.class);

	@GetMapping("/")
	public Response getAllQuotationPartDetails() {

		Response response = new Response();
		response.setStatus(false);
		response.setResponse(null);
		try {
			List<QuotationPartDetails> list = null;
			list = quotationPartDetailsRepository.findAll();
			response.setStatus(true);
			response.setError(null);
			response.setResponse(list);
		} catch(Exception e) {
			response.setError("Unable to get all QuotationPartDetails");
			logger.error("Error : "+e);
		}
		return response;
	}

	@GetMapping("/{Id}")
	public Response getQuotationPartDetailsById(@PathVariable(value = "Id") Long id) {

		Response response = new Response();
		response.setStatus(false);
		response.setResponse(null);
		try {
			QuotationPartDetails quotationPartDetails = quotationPartDetailsRepository.findById(id).get();
			response.setStatus(true);
			response.setError(null);
			response.setResponse(quotationPartDetails);
		}
		catch(NoSuchElementException ex) {
			response.setError("Unable to find QuotationPartDetails with id :"+id);
			logger.error("Error : "+ex);
		}
		catch(Exception e) {
			response.setError("Unable to get QuotationPartDetails");
			logger.error("Error : "+e);
		}
		return response;
	}

	@PostMapping("/")
	public Response createQuotationPartDetails(@Valid @RequestBody QuotationPartDetailsPayload quotationPartDetailsPayload) {

		Response response = new Response();
		response.setStatus(false);
		response.setResponse(null);
		try {
			if(null == quotationPartDetailsPayload) {
				response.setError("Payload cannot be null or empty");
			}else if(isZeroOrNull(quotationPartDetailsPayload.getQuotationId())) {
				response.setError("quotationId cannot be null or zero");
			}else if(isZeroOrNull(quotationPartDetailsPayload.getConditionReportPartId())) {
				response.setError("condition report part id cannot be null or zero");
			}
			else {
				QuotationPartDetails quotationPartDetails = new QuotationPartDetails();
				quotationPartDetails.setConditionReportPartId(conditionReportPartDetailsRepository.findById(quotationPartDetailsPayload.getConditionReportPartId()).get());
				quotationPartDetails.setExpectedPrice(quotationPartDetailsPayload.getExpectedPrice());
				quotationPartDetails.setQuotationId(quotationRepository.findById(quotationPartDetailsPayload.getQuotationId()).get());
				quotationPartDetails.setCreatedBy(quotationPartDetailsPayload.getCreatedBy());
				quotationPartDetails.setUpdatedBy(quotationPartDetailsPayload.getCreatedBy());
				quotationPartDetails = quotationPartDetailsRepository.save(quotationPartDetails);
				response.setStatus(true);
				response.setError(null);
				response.setResponse(quotationPartDetails);
			}
		} catch(Exception ex) {
			response.setError("Please pass valid QuotationPartDetailsPayload data to update.");
			logger.error("Error : "+ex);
		}
		return response;
	}

	@PutMapping("/")
	public Response updateQuotationPartDetails(@Valid @RequestBody QuotationPartDetailsPayload quotationPartDetailsPayload) {

		Response response = new Response();
		response.setStatus(false);
		response.setResponse(null);
		try {
			if(null == quotationPartDetailsPayload) {
				response.setError("Payload cannot be null or empty");
			}else if(isZeroOrNull(quotationPartDetailsPayload.getQuotationId())) {
				response.setError("quotationId cannot be null or zero");
			}else if(isZeroOrNull(quotationPartDetailsPayload.getConditionReportPartId())) {
				response.setError("condition report part id cannot be null or zero");
			}else if(isZeroOrNull(quotationPartDetailsPayload.getId())) {
				response.setError("Id cannot zero or null");
			}
			else {
				QuotationPartDetails quotationPartDetails = null;
				quotationPartDetails = quotationPartDetailsRepository.findById(quotationPartDetailsPayload.getId()).get();
				quotationPartDetails.setConditionReportPartId(conditionReportPartDetailsRepository.findById(quotationPartDetailsPayload.getConditionReportPartId()).get());
				quotationPartDetails.setExpectedPrice(quotationPartDetailsPayload.getExpectedPrice());
				quotationPartDetails.setQuotationId(quotationRepository.findById(quotationPartDetailsPayload.getQuotationId()).get());
				quotationPartDetails.setUpdatedBy(quotationPartDetailsPayload.getUpdatedBy());
				quotationPartDetails.setStatus(quotationPartDetailsPayload.getStatus());
				quotationPartDetailsRepository.save(quotationPartDetails);
				response.setStatus(true);
				response.setError(null);
				response.setResponse(quotationPartDetails);
			}
		} catch(Exception ex) {
			response.setError("Please pass valid QuotationPartDetailsPayload data to update.");
			logger.error("Error : "+ex);
		}
		return response;
	}
}
