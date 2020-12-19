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

import com.ste.enginestreamportal.model.BusinessType;
import com.ste.enginestreamportal.payload.BusinessTypePayload;
import com.ste.enginestreamportal.repository.BusinessTypeRepository;
import com.ste.enginestreamportal.services.BusinessTypeService;
import com.ste.enginestreamportal.util.Response;
import com.ste.enginestreamportal.util.Utils;

@RestController
@ResponseBody
@RequestMapping("/BusinessType")
public class BusinessTypeController extends Utils{

	@Autowired
	BusinessTypeRepository businessTypeRepository;
	
	@Autowired
	BusinessTypeService businessTypeService;
	
	private Logger logger = LogManager.getLogger(BusinessTypeController.class);

	@GetMapping("/pagination")
    public Response getAllBusinessTypesPagination(@RequestParam("showAll") Integer showAll, @RequestParam("pageIndex") int pageIndex,
            @RequestParam("sortType") String sortType, @RequestParam("searchWord") String searchWord,@RequestParam("statusType") String statusType) {
       
        Response response = new Response();
        Pageable pageable = null;
        Page<BusinessType> page = null;
        try {
            response.setStatus(true);
            response.setError(null);
            if(sortType != null && sortType.equalsIgnoreCase("asc")) {
                pageable = PageRequest.of(pageIndex, showAll, Sort.by("businessTypeName").ascending());
            } else {
                pageable = PageRequest.of(pageIndex, showAll, Sort.by("businessTypeName").descending());
            }
            page = businessTypeService.findAllBusinessType(pageable,sortType,searchWord,statusType);
           
            response.setResponse(page);
        } catch(Exception e) {
            response.setStatus(false);
            response.setError("Unable to get all businessTypes");
            logger.error("Error : "+e);
            response.setResponse(null);
        }
        return response;
    }

	@GetMapping("/{Id}")
	public Response getBusinessTypeById(@PathVariable(value = "Id") Long id) {

		Response response = new Response();
		response.setStatus(false);
		response.setResponse(null);
		try {
			BusinessType businessType = businessTypeRepository.findById(id).get();
			response.setStatus(true);
			response.setError(null);
			response.setResponse(businessType);
		}
		catch(NoSuchElementException ex) {
			response.setError("Unable to find BusinessType with id :"+id);
			logger.error("Error : "+ex);
		}
		catch(Exception e) {
			response.setError("Unable to get BusinessType");
			logger.error("Error : "+e);
		}
		return response;
	}

	@PostMapping("/")
	public Response createBusinessType(@Valid @RequestBody BusinessTypePayload businessTypePayload) {

		Response response = new Response();
		response.setStatus(false);
		response.setResponse(null);
		try {
			if(null == businessTypePayload) {
				response.setError("Payload cannot be null or empty");
			}else if(isNullOrEmpty(businessTypePayload.getBusinessTypeName())) {
				response.setError("Name cannot be null or empty");
			}
			else {
				BusinessType businessType = new BusinessType();
				businessType.setBusinessTypeName(businessTypePayload.getBusinessTypeName());
				businessType.setBusinessTypeDesc(businessTypePayload.getBusinessTypeDesc());
				businessType.setCreatedBy(businessTypePayload.getCreatedBy());
				businessType.setUpdatedBy(businessTypePayload.getCreatedBy());
				businessType = businessTypeRepository.save(businessType);
				response.setStatus(true);
				response.setError(null);
				response.setResponse(businessType);
			}
		} catch (Exception e) {
			response.setError("Could not create BusinessType");
			logger.error("Error : "+e);
		}
		return response;
	}

	@PutMapping("/")
	public Response updateBusinessType(@Valid @RequestBody BusinessTypePayload businessTypePayload) {

		Response response = new Response();
		response.setStatus(false);
		response.setResponse(null);
		try {
			if(null == businessTypePayload) {
				response.setError("Payload cannot be null or empty");
			}else if(isNullOrEmpty(businessTypePayload.getBusinessTypeName())) {
				response.setError("Name cannot be null or empty");
			}
			else if(isZeroOrNull(businessTypePayload.getId())) {
				response.setError("Id cannot zero or null");
			}
			else {
				BusinessType businessType = null;
				businessType = businessTypeRepository.findById(businessTypePayload.getId()).get();
				businessType.setBusinessTypeName(businessTypePayload.getBusinessTypeName());
				businessType.setBusinessTypeDesc(businessTypePayload.getBusinessTypeDesc());
                businessType.setUpdatedBy(businessTypePayload.getUpdatedBy());
				businessType.setStatus(businessTypePayload.getStatus());
				businessTypeRepository.save(businessType);
				response.setStatus(true);
				response.setError(null);
				response.setResponse(businessType);
			}
		} catch(Exception ex) {
			response.setError("Please pass valid BusinessType data to update.");
			logger.error("Error : "+ex);
		}
		return response;
	}
	
	@PutMapping("/{Id}/{Status}")
	public Response updateBusinessTypeStatus(@PathVariable(value = "Id") Long Id, @PathVariable(value="Status") Boolean status) {
		Response response = new Response();
		try {
			BusinessType businessType = businessTypeRepository.findById(Id).get();
			businessType.setStatus(status);
			businessTypeRepository.save(businessType);
			response.setStatus(true);
			response.setError(null);
			response.setResponse(businessType);
		} catch(NoSuchElementException ex) {
			response.setStatus(false);
			response.setError("Unable to find businessType with id :"+Id);
			logger.error("Error : "+ex);
			response.setResponse(null);
		} catch(Exception e) {
			response.setStatus(false);
			response.setError("Unable to update businessType");
			logger.error("Error : "+e);
			response.setResponse(null);
		}
		return response;
	}
	
	@GetMapping("/")
	public Response getAllBusinessTypes() {
		Response response = new Response();
		try {
			response.setStatus(true);
			response.setError(null);
			response.setResponse(businessTypeRepository.findAll());
		} catch(Exception e) {
			response.setStatus(false);
			response.setError("Unable to get all BusinessTypes");
			logger.error("Error : "+e);
			response.setResponse(null);
		}

		return response;
	}
}
