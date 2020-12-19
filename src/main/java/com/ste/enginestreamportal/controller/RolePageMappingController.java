package com.ste.enginestreamportal.controller;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
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

import com.ste.enginestreamportal.model.RolePageMapping;
import com.ste.enginestreamportal.payload.RolePageMappingPayload;
import com.ste.enginestreamportal.repository.RolePageMappingRepository;
import com.ste.enginestreamportal.services.RolePageMappingService;
import com.ste.enginestreamportal.util.Response;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/RolePageMapping")
@ResponseBody
public class RolePageMappingController {

	@Autowired
	RolePageMappingRepository rolePageMappingRepository;

	@Autowired
	RolePageMappingService rolePageMappingService;

	private Logger logger = LogManager.getLogger(RolePageMappingController.class);

	@GetMapping("/")
	public Response getAllRolePage() {
		Response response = new Response();
		try {
			response.setStatus(true);
			response.setError(null);
			response.setResponse(rolePageMappingService.getAllRolePageMappingPayloads());
		} catch(ResponseStatusException re) {
			response.setStatus(false);
			response.setResponse(null);
			response.setError(re.getReason());
			logger.error("Error : "+re);
		}  catch(Exception e) {
			response.setStatus(false);
			response.setError("Unable to get all RolePageMapping");
			logger.error("Error : "+e);
			response.setResponse(null);
		}
		return response;
	}

	@GetMapping("/{Id}")
	public Response getAllRolePageMappingById(@PathVariable(value = "Id") Long id) {

		Response response = new Response();
		response.setStatus(false);
		response.setResponse(null);
		try {
			logger.debug(String.format("Fetching role %d.", id));
			Optional<RolePageMapping> rolePageMapping = rolePageMappingRepository.findById(id);
			response.setResponse(rolePageMapping.get());
			response.setStatus(true);
			response.setError(null);
		}
		catch(NoSuchElementException ex) {
			response.setError("Unable to find RolePageMapping with id :"+id);
			logger.error("Error : "+ex);
		}
		catch(ResponseStatusException re) {
			response.setStatus(false);
			response.setResponse(null);
			response.setError(re.getReason());
			logger.error("Error : "+re);
		} catch(Exception e) {
			response.setError("Unable to get RolePageMapping");
			logger.error("Error : "+e);
		}

		return response;
	}


	@GetMapping("/getRolePageMappingsForRole")
	public Response getAllRolePageByRoleId(@RequestParam(value = "RoleId") Long roleId) {

		Response response = new Response();
		try {
			logger.debug(String.format("Fetching all role to page mapping entries for role %d.", roleId));
			response.setStatus(true);
			response.setError(null);
			response.setResponse(rolePageMappingService.getRolePageMappingPayloads(roleId));
		}catch(ResponseStatusException re) {
			response.setStatus(false);
			response.setResponse(null);
			response.setError(re.getReason());
			logger.error("Error : "+re);
		} catch(Exception e) {
			response.setStatus(false);
			response.setError("Unable to get all RolePage By RoleId");
			logger.error("Error : "+e);
			response.setResponse(null);
		}
		return response;
	}

	@PutMapping("/")
	public Response updateRolePageMapping(@Valid @RequestBody List<RolePageMappingPayload> rolePageMappingPayloads) {

		Response response = new Response();
		try {
			response.setStatus(true);
			response.setError(null);
			rolePageMappingService.updateRolePageMapping(rolePageMappingPayloads);
			response.setResponse("Successfully updated role page mappings");
		}catch(ResponseStatusException re) {
			response.setStatus(false);
			response.setResponse(null);
			response.setError(re.getReason());
			logger.error("Error : "+re);
		} catch(Exception e) {
			response.setStatus(false);
			response.setError("Unable to update RolePages");
			logger.error("Error : "+e);
			response.setResponse(null);
		}
		return response;
	}

	@PostMapping("/")
	public Response createRolePageMappings(@Valid @RequestBody List<RolePageMappingPayload> rolePageMappingPayloads) {

		Response response = new Response();
		try {
			response.setStatus(true);
			response.setError(null);
			rolePageMappingService.createRolePageMapping(rolePageMappingPayloads);
			response.setResponse("Successfully created role page mappings");
		}catch(ResponseStatusException re) {
			response.setStatus(false);
			response.setResponse(null);
			response.setError(re.getReason());
			logger.error("Error : "+re);
		} catch(Exception e) {
			response.setStatus(false);
			response.setError("Unable to create RolePages");
			logger.error("Error : "+e);
			response.setResponse(null);
		}
		return response;
	}

}
