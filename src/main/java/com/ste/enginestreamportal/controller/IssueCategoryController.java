package com.ste.enginestreamportal.controller;

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

import com.ste.enginestreamportal.model.IssueCategory;

import com.ste.enginestreamportal.payload.IssueCategoryPayload;

import com.ste.enginestreamportal.repository.IssueCategoryRepository;
import com.ste.enginestreamportal.services.IssueCategoryService;
import com.ste.enginestreamportal.util.Response;
import com.ste.enginestreamportal.util.Utils;

@RestController
@ResponseBody
@RequestMapping("/IssueCategory")
public class IssueCategoryController extends Utils{

	@Autowired
	IssueCategoryRepository issueCategoryRepository;
	
	@Autowired
	IssueCategoryService issueCategoryService;
	
	private Logger logger = LogManager.getLogger(IssueCategory.class);
	
	@PostMapping("/")
	public Response createIssueCategory(@Valid @RequestBody IssueCategoryPayload issueCategoryPayload) {

		Response response = new Response();
		response.setStatus(false);
		response.setResponse(null);
		try {
			if(null == issueCategoryPayload) {
				response.setError("Payload cannot be null or empty");
			}else if(isNullOrEmpty(issueCategoryPayload.getIssueCategoryTypeName())) {
				response.setError("IssueCategoryTypeName cannot be null or empty");
			}
			else {
				IssueCategory issueCategory = new IssueCategory();
				issueCategory.setIssueCategoryTypeName(issueCategoryPayload.getIssueCategoryTypeName());
				issueCategory.setCreatedBy(issueCategoryPayload.getCreatedBy());
				issueCategory.setUpdatedBy(issueCategoryPayload.getCreatedBy());
				issueCategory = issueCategoryRepository.save(issueCategory);
				response.setStatus(true);
				response.setError(null);
				response.setResponse(issueCategory);
			}
		} catch (Exception e) {
			response.setError("Could not create issueCategory");
			logger.error("Error : "+e);
		}
		return response;
	}
	
	
	@GetMapping("/pagination")
    public Response getAllIssueCategoriesPagination(@RequestParam("showAll") Integer showAll, @RequestParam("pageIndex") int pageIndex,
            @RequestParam("sortType") String sortType, @RequestParam("searchWord") String searchWord,@RequestParam("statusType") String statusType) {
       
        Response response = new Response();
        Pageable pageable = null;
        Page<IssueCategory> page = null;
        try {
            response.setStatus(true);
            response.setError(null);
            if(sortType != null && sortType.equalsIgnoreCase("asc")) {
                pageable = PageRequest.of(pageIndex, showAll, Sort.by("issueCategoryTypeName").ascending());
            } else {
                pageable = PageRequest.of(pageIndex, showAll, Sort.by("issueCategoryTypeName").descending());
            }
            page = issueCategoryService.findAllIssueCategory(pageable,sortType,searchWord,statusType);
            response.setResponse(page);
        } catch(Exception e) {
            response.setStatus(false);
            response.setError("Unable to get all IssueCategory details");
            logger.error("Error : "+e);
            response.setResponse(null);
        }
        return response;
    }
	
	@GetMapping("/{Id}")
	public Response getIssueCategoryById(@PathVariable(value = "Id") Long id) {

		Response response = new Response();
		response.setStatus(false);
		response.setResponse(null);
		try {
			IssueCategory issueCategory = issueCategoryRepository.findById(id).get();
			response.setStatus(true);
			response.setError(null);
			response.setResponse(issueCategory);
		}
		catch(NoSuchElementException ex) {
			response.setError("Unable to find issueCategory with id :"+id);
			logger.error("Error : "+ex);
		}
		catch(Exception e) {
			response.setError("Unable to get issueCategory");
			logger.error("Error : "+e);
		}
		return response;
	}
	
	
	@PutMapping("/")
	public Response updateIssueCategory(@Valid @RequestBody IssueCategoryPayload issueCategoryPayload) {

		Response response = new Response();
		response.setStatus(false);
		response.setResponse(null);
		try {
			if(null == issueCategoryPayload) {
				response.setError("Payload cannot be null or empty");
			}else if(isNullOrEmpty(issueCategoryPayload.getIssueCategoryTypeName())) {
				response.setError("IssueCategoryTypeName cannot be null or empty");
			}
			else if(isZeroOrNull(issueCategoryPayload.getId())) {
				response.setError("Id cannot zero or null");
			}
			else {
				IssueCategory issueCategory = null;
				issueCategory = issueCategoryRepository.findById(issueCategoryPayload.getId()).get();
				issueCategory.setIssueCategoryTypeName(issueCategoryPayload.getIssueCategoryTypeName());
				issueCategory.setUpdatedBy(issueCategoryPayload.getUpdatedBy());
				issueCategory.setStatus(issueCategoryPayload.getStatus());
				issueCategoryRepository.save(issueCategory);
				response.setStatus(true);
				response.setError(null);
				response.setResponse(issueCategory);
			}
		} catch(Exception ex) {
			response.setError("Please pass valid issueCategory data to update.");
			logger.error("Error : "+ex);
		}
		return response;
	}

	@PutMapping("/{Id}/{Status}")
	public Response updateIssueCategoryStatus(@PathVariable(value = "Id") Long Id,@PathVariable(value="Status") Boolean status) {
		Response response = new Response();
		try {
			IssueCategory issueCategory = issueCategoryRepository.findById(Id).get();
			issueCategory.setStatus(status);
			issueCategoryRepository.save(issueCategory);
			response.setStatus(true);
			response.setError(null);
			response.setResponse(issueCategory);
		} catch(NoSuchElementException ex) {
			response.setStatus(false);
			response.setError("Unable to find issueCategory with id :"+Id);
			logger.error("Error : "+ex);
			response.setResponse(null);
		} catch(Exception e) {
			response.setStatus(false);
			response.setError("Unable to update issueCategory");
			logger.error("Error : "+e);
			response.setResponse(null);
		}
		return response;
	}
	
	@GetMapping("/")
	public Response getAllIssueCategory() {
		Response response = new Response();
		try {
			response.setStatus(true);
			response.setError(null);
			response.setResponse(issueCategoryRepository.findAll());
		} catch(Exception e) {
			response.setStatus(false);
			response.setError("Unable to get all IssueCategory");
			logger.error("Error : "+e);
			response.setResponse(null);
		}

		return response;
	}
}
