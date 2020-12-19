package com.ste.enginestreamportal.controller;

import java.util.List;
import java.util.NoSuchElementException;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ste.enginestreamportal.model.Responses;
import com.ste.enginestreamportal.payload.ResponsesPayload;
import com.ste.enginestreamportal.repository.IssueRepository;
import com.ste.enginestreamportal.repository.ResponsesRepository;
import com.ste.enginestreamportal.services.ResponsesService;
import com.ste.enginestreamportal.util.Response;
import com.ste.enginestreamportal.util.Utils;

@RestController
@ResponseBody
@RequestMapping("/Response")
public class ResponseController extends Utils{

	@Autowired
	ResponsesService responsesService;
	
	@Autowired
	ResponsesRepository responsesRepository;
	
	@Autowired
	IssueRepository issueRepository;
	
	private Logger logger = LogManager.getLogger(ResponseController.class);

	@GetMapping("/")
    public Response getAllResponses(@RequestParam("showAll") Integer showAll, @RequestParam("pageIndex") int pageIndex, 
            @RequestParam("sortType") String sortType, @RequestParam("searchWord") String searchWord,@RequestParam("statusType") String statusType) {

 

        Response response = new Response();
        Pageable pageable = null;
        try {
            response.setStatus(true);
            response.setError(null);
            if("asc".equalsIgnoreCase(sortType))
                pageable = PageRequest.of(pageIndex, showAll, Sort.by("response").ascending());
            else
                pageable = PageRequest.of(pageIndex, showAll, Sort.by("response").descending());
            Page<Responses> page = responsesService.getAllResponses(pageable,sortType,searchWord,statusType);
            response.setResponse(page);
        }catch(Exception e) {
            response.setStatus(false);
            response.setError("Unable to get all Responses");
            logger.error("Error : "+e);
            response.setResponse(null);
        }
        return response;
    }
	
	@PostMapping("/")
	public Response createResponse(@Valid @RequestBody ResponsesPayload responsesPayload) {

		Response response = new Response();
		response.setStatus(false);
		response.setResponse(null);
		try {
			if(null == responsesPayload) {
				response.setError("Payload cannot be null or empty");
			}else if(isNullOrEmpty(responsesPayload.getResponse())) {
				response.setError("Response cannot be null or empty");
			}
			else if(isZeroOrNull(responsesPayload.getIssueId())) {
				response.setError("Issue id cannot be zero or null");
			}
			else {
				Responses responses = new Responses();
				responses.setResponse(responsesPayload.getResponse());
				responses.setIssueId(issueRepository.findById(responsesPayload.getIssueId()).get());
				responses.setCreatedBy(responsesPayload.getCreatedBy());
				responses.setUpdatedBy(responsesPayload.getCreatedBy());
				responses = responsesRepository.save(responses);
				response.setStatus(true);
				response.setError(null);
				response.setResponse(responses);
			}
		} catch (Exception e) {
			response.setError("Could not create response");
			logger.error("Error : "+e);
		}
		return response;
	}
	
	@GetMapping("/{Id}")
	public Response getResponsesById(@PathVariable(value = "Id") Long id) {

		Response response = new Response();
		response.setStatus(false);
		response.setResponse(null);
		try {
			Responses responses = responsesRepository.findById(id).get();
			response.setStatus(true);
			response.setError(null);
			response.setResponse(responses);
		}
		catch(NoSuchElementException ex) {
			response.setError("Unable to find Response with id :"+id);
			logger.error("Error : "+ex);
		}
		catch(Exception e) {
			response.setError("Unable to get Response");
			logger.error("Error : "+e);
		}
		return response;
	}
	
	@GetMapping("/Issue/{Id}")
	public Response getResponsesByIssueId(@PathVariable(value = "Id") Long id) {

		Response response = new Response();
		response.setStatus(false);
		response.setResponse(null);
		try {
			List<Responses> responses = responsesRepository.findResponsesbyIssueId(id);
			response.setStatus(true);
			response.setError(null);
			response.setResponse(responses);
		}
		catch(Exception e) {
			response.setError("Unable to get Responses");
			logger.error("Error : "+e);
		}
		return response;
	}
	

	@PutMapping("/")
	public Response updateResponses(@Valid @RequestBody ResponsesPayload responsesPayload) {

		Response response = new Response();
		response.setStatus(false);
		response.setResponse(null);
		try {
			if(null == responsesPayload) {
				response.setError("Payload cannot be null or empty");
			}else if(isNullOrEmpty(responsesPayload.getResponse())) {
				response.setError("Response cannot be null or empty");
			}
			else if(isZeroOrNull(responsesPayload.getId())) {
				response.setError("Id cannot zero or null");
			}
			else {
				Responses responses = null;
				responses = responsesRepository.findById(responsesPayload.getId()).get();
				responses.setResponse(responsesPayload.getResponse());
				responses.setUpdatedBy(responsesPayload.getUpdatedBy());
				responses.setStatus(responsesPayload.getStatus());
				responsesRepository.save(responses);
				response.setStatus(true);
				response.setError(null);
				response.setResponse(responses);
			}
		} catch(Exception ex) {
			response.setError("Please pass valid response data to update.");
			logger.error("Error : "+ex);
		}
		return response;
	}
}
