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

import com.ste.enginestreamportal.model.Issue;
import com.ste.enginestreamportal.payload.IssuePayload;
import com.ste.enginestreamportal.repository.IssueRepository;
import com.ste.enginestreamportal.repository.StateRepository;
import com.ste.enginestreamportal.services.IssueService;
import com.ste.enginestreamportal.util.Response;
import com.ste.enginestreamportal.util.Utils;

@RestController
@ResponseBody
@RequestMapping("/Issue")
public class IssueController extends Utils{

	private Logger logger = LogManager.getLogger(IssueController.class);

	@Autowired
	IssueRepository issueRepository;
	
	@Autowired
	StateRepository stateRepository;
	
	@Autowired
	IssueService issueService;
	
	@GetMapping("/")
    public Response getAllIssues(@RequestParam("showAll") Integer showAll, @RequestParam("pageIndex") int pageIndex,
            @RequestParam("sortType") String sortType, @RequestParam("searchWord") String searchWord,@RequestParam("stateType") String stateType) {
       
        Response response = new Response();
        Pageable pageable = null;
        Page<Issue> page = null;
        try {
            response.setStatus(true);
            response.setError(null);
            if(sortType != null && sortType.equalsIgnoreCase("asc")) {
                pageable = PageRequest.of(pageIndex, showAll, Sort.by("issueNumber").ascending());
            } else {
                pageable = PageRequest.of(pageIndex, showAll, Sort.by("issueNumber").descending());
            }
            page = issueService.findAllIssues(pageable,sortType,searchWord,stateType);
            response.setResponse(page);
        } catch(Exception e) {
            response.setStatus(false);
            response.setError("Unable to get all issues");
            logger.error("Error : "+e);
            response.setResponse(null);
        }
        return response;
    }
	
	@GetMapping("/{Id}")
	public Response getIssueById(@PathVariable(value = "Id") Long id) {

		Response response = new Response();
		response.setStatus(false);
		response.setResponse(null);
		try {
			Issue issue = issueRepository.findById(id).get();
			response.setStatus(true);
			response.setError(null);
			response.setResponse(issue);
		}
		catch(NoSuchElementException ex) {
			response.setError("Unable to find issue with id :"+id);
			logger.error("Error : "+ex);
		}
		catch(Exception e) {
			response.setError("Unable to get issue");
			logger.error("Error : "+e);
		}
		return response;
	}
	
	@PutMapping("/{Id}/{Status}")
	public Response updateIssueStatus(@PathVariable(value = "Id") Long Id,@PathVariable(value="Status") Boolean status) {
		Response response = new Response();
		try {
			Issue issue = issueRepository.findById(Id).get();
            issue.setStatus(status);
			issueRepository.save(issue);
			response.setStatus(true);
			response.setError(null);
			response.setResponse(issue);
		} catch(NoSuchElementException ex) {
			response.setStatus(false);
			response.setError("Unable to find issue with id :"+Id);
			logger.error("Error : "+ex);
			response.setResponse(null);
		} catch(Exception e) {
			response.setStatus(false);
			response.setError("Unable to update issue");
			logger.error("Error : "+e);
			response.setResponse(null);
		}
		return response;
	}
	
	@PostMapping("/")
	public Response createIssue(@Valid @RequestBody IssuePayload issuePayload) {
		
		Response response = new Response();
		response.setStatus(false);
		response.setResponse(null);
		try {
			if(null == issuePayload) {
				response.setError("Payload cannot be null or empty");
			} else if(isNullOrEmpty(issuePayload.getSubject())){
				response.setError("Subject cannot be empty or null");
			} else if(isNullOrEmpty(issuePayload.getDescription())) {
				response.setError("Description cannot be empty or null");
			} else if(isZeroOrNull(issuePayload.getIssueCategoryId())) {
				response.setError("Issue Category cannot be zero or null");
			}
			else {
				Issue issue = new Issue();
				issue = issueService.createIssue(issuePayload);
				response.setStatus(true);
				response.setError(null);
				response.setResponse(issue);
			}
		} catch(ResponseStatusException re) {
			response.setError(re.getReason());
			logger.error("Error : "+re);
		} catch(Exception e) {
			response.setError("Unable to create Issue");
			logger.error("Error : "+e);
		}
		return response;
	}
	
	@PutMapping("/{Id}/State/{State}")
	public Response updateStateOfIssue(@PathVariable(value = "Id") Long Id, @PathVariable(value = "State") Long State) {
		Response response = new Response();
		try {
			Issue issue = issueRepository.findById(Id).get();
			issue.setState(stateRepository.findById(State).get());
			issueRepository.save(issue);
			response.setStatus(true);
			response.setError(null);
			response.setResponse(issue);
		} catch(NoSuchElementException ex) {
			response.setStatus(false);
			response.setError("Unable to find issue with id :"+Id);
			logger.error("Error : "+ex);
			response.setResponse(null);
		} catch(Exception e) {
			response.setStatus(false);
			response.setError("Unable to update issue");
			logger.error("Error : "+e);
			response.setResponse(null);
		}
		return response;
	}
	
	@PutMapping("/")
	public Response udpateIssue(@Valid @RequestBody IssuePayload issuePayload) {
		
		Response response = new Response();
		response.setStatus(false);
		response.setResponse(null);
		try {
			if(null == issuePayload) {
				response.setError("Payload cannot be null or empty");
			} else if(isNullOrEmpty(issuePayload.getSubject())){
				response.setError("Subject cannot be empty or null");
			} else if(isNullOrEmpty(issuePayload.getDescription())) {
				response.setError("Description cannot be empty or null");
			} else if(isZeroOrNull(issuePayload.getIssueCategoryId())) {
				response.setError("Issue Category cannot be zero or null");
			} else if(isZeroOrNull(issuePayload.getId())) {
				response.setError("Id cannot be zero or null");
			}
			else {
				Issue issue = issueService.updateIssue(issuePayload);
				response.setStatus(true);
				response.setError(null);
				response.setResponse(issue);
			}
		} catch(ResponseStatusException re) {
			response.setError(re.getReason());
			logger.error("Error : "+re);
		} catch(Exception e) {
			response.setError("Unable to update Issue");
			logger.error("Error : "+e);
		}
		return response;
	}
	
}
