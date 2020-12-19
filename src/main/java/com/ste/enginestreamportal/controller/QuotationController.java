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
import org.springframework.web.server.ResponseStatusException;

import com.ste.enginestreamportal.model.Quotation;
import com.ste.enginestreamportal.payload.QuotationPayload;
import com.ste.enginestreamportal.repository.QuotationRepository;
import com.ste.enginestreamportal.services.QuotationService;
import com.ste.enginestreamportal.util.Response;
import com.ste.enginestreamportal.util.Utils;

@RestController
@ResponseBody
@RequestMapping("/Quotation")
public class QuotationController extends Utils{

	@Autowired
	QuotationRepository quotationRepository;
	
	@Autowired
	QuotationService quotationService;
	
	private Logger logger = LogManager.getLogger(QuotationController.class);

	@GetMapping("/")
	public Response getAllQuotation() {
		
		Response response = new Response();
		try {
		List<Quotation> list = null;
		list = quotationRepository.findAll();
		response.setStatus(true);
		response.setError(null);
		response.setResponse(list);
		}catch(Exception e) {
			response.setStatus(false);
			response.setError("Unable to get all Quotations");
			logger.error("Error : "+e);
			response.setResponse(null);
		}
		return response;
	}
	
	@GetMapping("/{Id}")
	public Response getQuotationById(@PathVariable(value = "Id") Long id) {
		
		Response response = new Response();
		response.setStatus(false);
		response.setResponse(null);
		try {
		Quotation quotation = quotationRepository.findById(id).get();
		response.setStatus(true);
		response.setError(null);
		response.setResponse(quotation);
		} 
		catch(NoSuchElementException ex) {
			response.setError("Unable to find Quotation with id :"+id);
			logger.error("Error : "+ex);
		}
		catch(Exception e) {
			response.setError("Unable to get Quotation");
			logger.error("Error : "+e);
		}
		return response;
	}
	
	@PostMapping("/")
	public Response createQuotation(@Valid @RequestBody QuotationPayload quotationPayload) {
		
		Response response = new Response();
		response.setStatus(false);
		response.setResponse(null);
		try {
			if(null == quotationPayload) {
				response.setError("Payload cannot be null or empty");
			}else if(isZeroOrNull(quotationPayload.getJobId())) {
				response.setError("Job Id cannot be null or zero");
			}
			else {
		Quotation quotation = new Quotation();
		quotation = quotationService.createQuotation(quotationPayload);
		response.setStatus(true);
		response.setError(null);
		response.setResponse(quotation);
			}
		} catch(ResponseStatusException re) {
			response.setStatus(false);
			response.setResponse(null);
			response.setError(re.getReason());
			logger.error("Error : "+re);
		}  catch (Exception e) {
			response.setError("Could not create Quotation");
			logger.error("Error : "+e);
		}
		return response;
	}
	
	@PutMapping("/")
	public Response updateQuotation(@Valid @RequestBody QuotationPayload quotationPayload) {
		
		Response response = new Response();
		response.setStatus(false);
		response.setResponse(null);
		try {
			if(null == quotationPayload) {
				response.setError("Payload cannot be null or empty");
			}else if(isZeroOrNull(quotationPayload.getJobId())) {
				response.setError("Job Id cannot be null or zero");
			}
			else if(isZeroOrNull(quotationPayload.getId())) {
				response.setError("Id cannot be zero or null");
			}
			else {
				Quotation quotation = new Quotation();
				quotation = quotationService.updateQuotation(quotationPayload);
				response.setStatus(true);
				response.setError(null);
				response.setResponse(quotation);
			}
		} catch(ResponseStatusException re) {
			response.setStatus(false);
			response.setResponse(null);
			response.setError(re.getReason());
			logger.error("Error : "+re);
		}  catch(Exception ex) {
			response.setError("Please pass valid QuotationPayload data to update.");
			logger.error("Error : "+ex);
		}
		return response;
	}
}
