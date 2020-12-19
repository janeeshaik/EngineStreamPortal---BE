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
import org.springframework.web.server.ResponseStatusException;

import com.ste.enginestreamportal.model.CostApprovalForm;
import com.ste.enginestreamportal.payload.CostApprovalFormPayload;
import com.ste.enginestreamportal.repository.CostApprovalFormRepository;
import com.ste.enginestreamportal.services.CostApprovalFormService;
import com.ste.enginestreamportal.util.Response;
import com.ste.enginestreamportal.util.Utils;

@RestController
@ResponseBody
@RequestMapping("/CostApprovalForm")
public class CostApprovalFormController extends Utils{

	@Autowired
	CostApprovalFormRepository costApprovalFormRepository;
	
	@Autowired
	CostApprovalFormService costApprovalFormService;
	
	private Logger logger = LogManager.getLogger(CostApprovalFormController.class);

	@GetMapping("/")
	public Response getAllCostApprovalForms() {
		Response response = new Response();
		try {
			response.setStatus(true);
			response.setError(null);
			response.setResponse(costApprovalFormRepository.findAll());
		} catch(Exception e) {
			response.setStatus(false);
			response.setError("Unable to get all CostApprovalForms");
			logger.error("Error : "+e);
			response.setResponse(null);
		}
		return response;
	}
	
	@GetMapping("/{Id}")
	public Response getCostApprovalFormById(@PathVariable(value = "Id") Long id) {
		
		Response response = new Response();
		try {
			CostApprovalForm costApprovalForm = costApprovalFormRepository.findById(id).get();
			
			response.setStatus(true);
			response.setError(null);
			response.setResponse(costApprovalForm);
		} catch(NoSuchElementException ex) {
			response.setStatus(false);
			response.setError("Unable to find costApprovalForm with id :"+id);
			logger.error("Error : "+ex);
			response.setResponse(null);
		} catch(Exception e) {
			response.setStatus(false);
			response.setError("Unable to get costApprovalForm");
			logger.error("Error : "+e);
			response.setResponse(null);
		}
		return response;
		
	}
	
	@PostMapping("/")
	public Response createCostApprovalForm(@Valid @RequestBody CostApprovalFormPayload costApprovalFormPayload) {

		Response response = new Response();
		response.setStatus(false);
		response.setResponse(null);
		try {
			if(null == costApprovalFormPayload) {
				response.setError("Payload cannot be null or empty");
			}
		else if(isZeroOrNull(costApprovalFormPayload.getJobId())) {			
			response.setError("job Id cannot be null or Zero");
			}
		else {
			CostApprovalForm costApprovalForm =  costApprovalFormService.createCostApprovalForm(costApprovalFormPayload);
			response.setStatus(true);
			response.setError(null);
			response.setResponse(costApprovalForm);
		}
		
		} catch(ResponseStatusException re) {
			response.setError(re.getReason());
			logger.error("Error : "+re);
		} catch(Exception e) {
			response.setError("Unable to create costApprovalForm");
			logger.error("Error : "+e);
		}
		return response;
	}
	
	@PutMapping("/")
	public Response updateCostApprovalForm(@Valid @RequestBody CostApprovalFormPayload costApprovalFormPayload) {
		
		Response response = new Response();
		response.setStatus(false);
		response.setResponse(null);
		try {
			if(null == costApprovalFormPayload) {
				response.setError("Payload cannot be null or empty");
			}
		else if(isZeroOrNull(costApprovalFormPayload.getJobId())) {			
			response.setError("job Id cannot be null or Zero");
			}
		else if(isZeroOrNull(costApprovalFormPayload.getId())) {
			response.setError("Id cannot be null or zero");
		}
		else {
		CostApprovalForm costApprovalForm = costApprovalFormService.updateCostApprovalForm(costApprovalFormPayload);
		response.setStatus(true);
		response.setError(null);
		response.setResponse(costApprovalForm);
		}
		} catch(ResponseStatusException re) {
			response.setError(re.getReason());
			logger.error("Error : "+re);
		} catch(Exception e) {
			response.setError("Unable to update costApprovalForm");
			logger.error("Error : "+e);
		}
		return response;
	}
}
