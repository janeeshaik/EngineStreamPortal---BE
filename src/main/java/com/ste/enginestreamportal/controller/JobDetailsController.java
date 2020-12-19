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
import org.springframework.web.server.ResponseStatusException;

import com.ste.enginestreamportal.model.JobDetails;
import com.ste.enginestreamportal.payload.JobDetailsPayload;
import com.ste.enginestreamportal.repository.JobDetailsRepository;
import com.ste.enginestreamportal.services.JobDetailsService;
import com.ste.enginestreamportal.util.Response;
import com.ste.enginestreamportal.util.Utils;

@RestController
@ResponseBody
@RequestMapping("/JobDetails")
public class JobDetailsController extends Utils {

	@Autowired
	JobDetailsRepository jobDetailsRepository;

	@Autowired
	JobDetailsService jobDetailsService;

	private Logger logger = LogManager.getLogger(JobDetailsController.class);

	@GetMapping("/")
    public Response getAllJobDetails(@RequestParam("showAll") Integer showAll, @RequestParam("pageIndex") int pageIndex,
            @RequestParam("sortType") String sortType, @RequestParam("searchWord") String searchWord,@RequestParam("statusType") String statusType) {
       
        Response response = new Response();
        Pageable pageable = null;
        Page<JobDetails> page = null;
        try {
            response.setStatus(true);
            response.setError(null);
            if(sortType != null && sortType.equalsIgnoreCase("asc")) {
                pageable = PageRequest.of(pageIndex, showAll, Sort.by("jobNo").ascending());
            } else {
                pageable = PageRequest.of(pageIndex, showAll, Sort.by("jobNo").descending());
            }
            page = jobDetailsService.findAllJobDetails(pageable,sortType,searchWord,statusType);
           
            response.setResponse(page);
        } catch(Exception e) {
            response.setStatus(false);
            response.setError("Unable to get all jobDetails");
            logger.error("Error : "+e);
            response.setResponse(null);
        }
        return response;
    }

	@GetMapping("/{Id}")
	public Response getJobDetailsById(@PathVariable(value = "Id") Long id) {

		Response response = new Response();
		try {
			JobDetails jobDetails = jobDetailsRepository.findById(id).get();
			response.setStatus(true);
			response.setError(null);
			response.setResponse(jobDetails);
		} catch(NoSuchElementException ex) {
			response.setStatus(false);
			response.setError("Unable to find JobDetails with id :"+id);
			logger.error("Error : "+ex);
			response.setResponse(null);
		} catch(Exception e) {
			response.setStatus(false);
			response.setError("Unable to get JobDetails");
			logger.error("Error : "+e);
			response.setResponse(null);
		}	
		return response;
	}

	@PostMapping("/")
	public Response createJobDetails(@Valid @RequestBody JobDetailsPayload jobDetailsPayload) {

		Response response = new Response();
		response.setStatus(false);
		response.setResponse(null);
		try {
			if(null == jobDetailsPayload) {
				response.setError("Payload cannot be null or empty");
			}
			else if(isNullOrEmpty(jobDetailsPayload.getJobCode())) {			
				response.setError("JobCode cannot be null or empty");
			}
			else if(isNullOrEmpty(jobDetailsPayload.getJobNo())) {
				response.setError("JobNo cannot be null or empty");
			}
			else {
				JobDetails jobDetails = new JobDetails();
				jobDetails = jobDetailsService.createJobDetails(jobDetailsPayload);
				response.setStatus(true);
				response.setError(null);
				response.setResponse(jobDetails);
			}
		} catch(ResponseStatusException re) {
			response.setStatus(false);
			response.setResponse(null);
			response.setError(re.getReason());
			logger.error("Error : "+re);
		}  catch(Exception e) {
			response.setError("Unable to create JobDetails");
			logger.error("Error : "+e);
		}
		return response;
	}

	@PutMapping("/")
	public Response updateJobDetails(@Valid @RequestBody JobDetailsPayload jobDetailsPayload) {

		Response response = new Response();
		response.setStatus(false);
		response.setResponse(null);
		try {
			if(null == jobDetailsPayload) {
				response.setError("Payload cannot be null or empty");
			}
			else if(isNullOrEmpty(jobDetailsPayload.getJobCode())) {			
				response.setError("JobCode cannot be null or empty");
			}
			else if(isNullOrEmpty(jobDetailsPayload.getJobNo())) {
				response.setError("JobNo cannot be null or empty");
			}
			else if(isZeroOrNull(jobDetailsPayload.getId())) {
				response.setError("Id cannot be Zero or null");
			}
			else {
				JobDetails jobDetails = jobDetailsService.updateJobDetails(jobDetailsPayload);
				response.setStatus(true);
				response.setError(null);
				response.setResponse(jobDetails);
			}
		}catch(ResponseStatusException re) {
			response.setStatus(false);
			response.setResponse(null);
			response.setError(re.getReason());
			logger.error("Error : "+re);
		}  catch(Exception e) {
			response.setError("Unable to update JobDetails");
			logger.error("Error : "+e);
		}

		return response;
	}
	
	@PutMapping("/{Id}/{Status}")
	public Response updateJobDetailsStatus(@PathVariable(value = "Id") Long Id, @PathVariable(value="Status") Boolean status) {
		Response response = new Response();
		try {
			JobDetails jobDetails = jobDetailsRepository.findById(Id).get();
			jobDetails.setStatus(status);
			jobDetailsRepository.save(jobDetails);
			response.setStatus(true);
			response.setError(null);
			response.setResponse(jobDetails);
		} catch(NoSuchElementException ex) {
			response.setStatus(false);
			response.setError("Unable to find jobDetails with id :"+Id);
			logger.error("Error : "+ex);
			response.setResponse(null);
		} catch(Exception e) {
			response.setStatus(false);
			response.setError("Unable to update jobDetails");
			logger.error("Error : "+e);
			response.setResponse(null);
		}
		return response;
	}
}
