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

import com.ste.enginestreamportal.model.IssueSubCategory;

import com.ste.enginestreamportal.payload.IssueSubCategoryPayload;
import com.ste.enginestreamportal.repository.IssueSubCategoryRepository;
import com.ste.enginestreamportal.services.IssueSubCategoryService;
import com.ste.enginestreamportal.util.Response;
import com.ste.enginestreamportal.util.Utils;

@RestController
@ResponseBody
@RequestMapping("/IssueSubCategory")
public class IssueSubCategoryController extends Utils {

	@Autowired
	IssueSubCategoryRepository issueSubCategoryRepository;
	
	@Autowired
	IssueSubCategoryService issueSubCategoryService;
	
     private Logger logger = LogManager.getLogger(IssueSubCategory.class);
	
	@PostMapping("/")
	public Response createIssueSubCategory(@Valid @RequestBody IssueSubCategoryPayload issueSubCategoryPayload) {

		Response response = new Response();
		response.setStatus(false);
		response.setResponse(null);
		try {
			if(null == issueSubCategoryPayload) {
				response.setError("Payload cannot be null or empty");
			}else if(isNullOrEmpty(issueSubCategoryPayload.getSubCategoryName())) {
				response.setError("SubCategoryName cannot be null or empty");
			}
			else {
				IssueSubCategory issueSubCategory = new IssueSubCategory();
				issueSubCategory.setSubCategoryName(issueSubCategoryPayload.getSubCategoryName());
				issueSubCategory.setCreatedBy(issueSubCategoryPayload.getCreatedBy());
				issueSubCategory.setUpdatedBy(issueSubCategoryPayload.getCreatedBy());
				issueSubCategory = issueSubCategoryRepository.save(issueSubCategory);
				response.setStatus(true);
				response.setError(null);
				response.setResponse(issueSubCategory);
			}
		} catch (Exception e) {
			response.setError("Could not create issueSubCategory");
			logger.error("Error : "+e);
		}
		return response;
	}
	
	@GetMapping("/pagination")
    public Response getAllIssueSubCategoriesPagination(@RequestParam("showAll") Integer showAll, @RequestParam("pageIndex") int pageIndex,
            @RequestParam("sortType") String sortType, @RequestParam("searchWord") String searchWord,@RequestParam("statusType") String statusType) {
       
        Response response = new Response();
        Pageable pageable = null;
        Page<IssueSubCategory> page = null;
        try {
            response.setStatus(true);
            response.setError(null);
            if(sortType != null && sortType.equalsIgnoreCase("asc")) {
                pageable = PageRequest.of(pageIndex, showAll, Sort.by("subCategoryName").ascending());
            } else {
                pageable = PageRequest.of(pageIndex, showAll, Sort.by("subCategoryName").descending());
            }
            page = issueSubCategoryService.findAllIssueSubCategory(pageable,sortType,searchWord,statusType);
            response.setResponse(page);
        } catch(Exception e) {
            response.setStatus(false);
            response.setError("Unable to get all IssueSubCategory details");
            logger.error("Error : "+e);
            response.setResponse(null);
        }
        return response;
    }
	
	@GetMapping("/{Id}")
	public Response getIssueSubCategoryById(@PathVariable(value = "Id") Long id) {

		Response response = new Response();
		response.setStatus(false);
		response.setResponse(null);
		try {
			IssueSubCategory issueSubCategory = issueSubCategoryRepository.findById(id).get();
			response.setStatus(true);
			response.setError(null);
			response.setResponse(issueSubCategory);
		}
		catch(NoSuchElementException ex) {
			response.setError("Unable to find issueSubCategory with id :"+id);
			logger.error("Error : "+ex);
		}
		catch(Exception e) {
			response.setError("Unable to get issueSubCategory");
			logger.error("Error : "+e);
		}
		return response;
	}
	
	
	@PutMapping("/")
	public Response updateIssueSubCategory(@Valid @RequestBody IssueSubCategoryPayload issueSubCategoryPayload) {

		Response response = new Response();
		response.setStatus(false);
		response.setResponse(null);
		try {
			if(null == issueSubCategoryPayload) {
				response.setError("Payload cannot be null or empty");
			}else if(isNullOrEmpty(issueSubCategoryPayload.getSubCategoryName())) {
				response.setError("SubCategoryName cannot be null or empty");
			}
			else if(isZeroOrNull(issueSubCategoryPayload.getId())) {
				response.setError("Id cannot zero or null");
			}
			else {
				IssueSubCategory issueSubCategory = null;
				issueSubCategory = issueSubCategoryRepository.findById(issueSubCategoryPayload.getId()).get();
				issueSubCategory.setSubCategoryName(issueSubCategoryPayload.getSubCategoryName());
				issueSubCategory.setUpdatedBy(issueSubCategoryPayload.getUpdatedBy());
				issueSubCategory.setStatus(issueSubCategoryPayload.getStatus());
				issueSubCategoryRepository.save(issueSubCategory);
				response.setStatus(true);
				response.setError(null);
				response.setResponse(issueSubCategory);
			}
		} catch(Exception ex) {
			response.setError("Please pass valid issueSubCategory data to update.");
			logger.error("Error : "+ex);
		}
		return response;
	}

	@PutMapping("/{Id}/{Status}")
	public Response updateIssueSubCategoryStatus(@PathVariable(value = "Id") Long Id,@PathVariable(value="Status") Boolean status) {
		Response response = new Response();
		try {
			IssueSubCategory issueSubCategory = issueSubCategoryRepository.findById(Id).get();
			issueSubCategory.setStatus(status);
			issueSubCategoryRepository.save(issueSubCategory);
			response.setStatus(true);
			response.setError(null);
			response.setResponse(issueSubCategory);
		} catch(NoSuchElementException ex) {
			response.setStatus(false);
			response.setError("Unable to find issueSubCategory with id :"+Id);
			logger.error("Error : "+ex);
			response.setResponse(null);
		} catch(Exception e) {
			response.setStatus(false);
			response.setError("Unable to update issueSubCategory");
			logger.error("Error : "+e);
			response.setResponse(null);
		}
		return response;
	}
	
	@GetMapping("/")
	public Response getAllIssueSubCategories() {
		Response response = new Response();
		try {
			response.setStatus(true);
			response.setError(null);
			response.setResponse(issueSubCategoryRepository.findAll());
		} catch(Exception e) {
			response.setStatus(false);
			response.setError("Unable to get all IssueSubCategories");
			logger.error("Error : "+e);
			response.setResponse(null);
		}

		return response;
	}
	
}
