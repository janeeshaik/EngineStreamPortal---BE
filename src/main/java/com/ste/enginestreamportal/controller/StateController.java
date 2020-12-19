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

import com.ste.enginestreamportal.model.State;

import com.ste.enginestreamportal.payload.StatePayload;
import com.ste.enginestreamportal.repository.StateRepository;
import com.ste.enginestreamportal.services.StateService;
import com.ste.enginestreamportal.util.Response;
import com.ste.enginestreamportal.util.Utils;

@RestController
@ResponseBody
@RequestMapping("/State")
public class StateController extends Utils {
	
	private Logger logger = LogManager.getLogger(StateController.class);
	
	@Autowired
	 StateRepository stateRepository;
	
	@Autowired
	StateService stateService;
	
	

	@PostMapping("/")
	public Response createState(@Valid @RequestBody StatePayload statePayload) {

		Response response = new Response();
		response.setStatus(false);
		response.setResponse(null);
		try {
			if(null == statePayload) {
				response.setError("Payload cannot be null or empty");
			}else if(isNullOrEmpty(statePayload.getStateName())) {
				response.setError("StateName cannot be null or empty");
			}
			else {
				State state = new State();
				state.setStateName(statePayload.getStateName());
				state.setCreatedBy(statePayload.getCreatedBy());
				state.setUpdatedBy(statePayload.getCreatedBy());
				state = stateRepository.save(state);
				response.setStatus(true);
				response.setError(null);
				response.setResponse(state);
			}
		} catch (Exception e) {
			response.setError("Could not create state");
			logger.error("Error : "+e);
		}
		return response;
	}
	
	@GetMapping("/pagination")
    public Response getAllStatesPagination(@RequestParam("showAll") Integer showAll, @RequestParam("pageIndex") int pageIndex,
            @RequestParam("sortType") String sortType, @RequestParam("searchWord") String searchWord,@RequestParam("statusType") String statusType) {
       
        Response response = new Response();
        Pageable pageable = null;
        Page<State> page = null;
        try {
            response.setStatus(true);
            response.setError(null);
            if(sortType != null && sortType.equalsIgnoreCase("asc")) {
                pageable = PageRequest.of(pageIndex, showAll, Sort.by("stateName").ascending());
            } else {
                pageable = PageRequest.of(pageIndex, showAll, Sort.by("stateName").descending());
            }
            page = stateService.findAllState(pageable,sortType,searchWord,statusType);
            response.setResponse(page);
        } catch(Exception e) {
            response.setStatus(false);
            response.setError("Unable to get all states");
            logger.error("Error : "+e);
            response.setResponse(null);
        }
        return response;
    }
	
	
	@GetMapping("/{Id}")
	public Response getStateById(@PathVariable(value = "Id") Long id) {

		Response response = new Response();
		response.setStatus(false);
		response.setResponse(null);
		try {
			State state = stateRepository.findById(id).get();
			response.setStatus(true);
			response.setError(null);
			response.setResponse(state);
		}
		catch(NoSuchElementException ex) {
			response.setError("Unable to find state with id :"+id);
			logger.error("Error : "+ex);
		}
		catch(Exception e) {
			response.setError("Unable to get state");
			logger.error("Error : "+e);
		}
		return response;
	}
	
	@PutMapping("/")
	public Response updateState(@Valid @RequestBody StatePayload statePayload) {

		Response response = new Response();
		response.setStatus(false);
		response.setResponse(null);
		try {
			if(null == statePayload) {
				response.setError("Payload cannot be null or empty");
			}else if(isNullOrEmpty(statePayload.getStateName())) {
				response.setError("StateName cannot be null or empty");
			}
			else if(isZeroOrNull(statePayload.getId())) {
				response.setError("Id cannot zero or null");
			}
			else {
				State state = null;
				state = stateRepository.findById(statePayload.getId()).get();
				state.setStateName(statePayload.getStateName());
				state.setUpdatedBy(statePayload.getUpdatedBy());
				state.setStatus(statePayload.getStatus());
				stateRepository.save(state);
				response.setStatus(true);
				response.setError(null);
				response.setResponse(state);
			}
		} catch(Exception ex) {
			response.setError("Please pass valid state data to update.");
			logger.error("Error : "+ex);
		}
		return response;
	}

	
	@PutMapping("/{Id}/{Status}")
	public Response updateStateStatus(@PathVariable(value = "Id") Long Id,@PathVariable(value="Status") Boolean status) {
		Response response = new Response();
		try {
			State state = stateRepository.findById(Id).get();
			state.setStatus(status);
			stateRepository.save(state);
			response.setStatus(true);
			response.setError(null);
			response.setResponse(state);
		} catch(NoSuchElementException ex) {
			response.setStatus(false);
			response.setError("Unable to find state with id :"+Id);
			logger.error("Error : "+ex);
			response.setResponse(null);
		} catch(Exception e) {
			response.setStatus(false);
			response.setError("Unable to update state");
			logger.error("Error : "+e);
			response.setResponse(null);
		}
		return response;
	}
	
	@GetMapping("/")
	public Response getAllStates() {
		Response response = new Response();
		try {
			response.setStatus(true);
			response.setError(null);
			response.setResponse(stateRepository.findAll());
		} catch(Exception e) {
			response.setStatus(false);
			response.setError("Unable to get all States");
			logger.error("Error : "+e);
			response.setResponse(null);
		}

		return response;
	}
}
