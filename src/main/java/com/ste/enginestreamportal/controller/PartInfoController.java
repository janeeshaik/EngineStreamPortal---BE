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

import com.ste.enginestreamportal.model.PartInfo;
import com.ste.enginestreamportal.payload.PartInfoPayload;
import com.ste.enginestreamportal.repository.PartInfoRepository;
import com.ste.enginestreamportal.util.Response;
import com.ste.enginestreamportal.util.Utils;

@RestController
@ResponseBody
@RequestMapping("/PartInfo")
public class PartInfoController extends Utils{

	@Autowired
	PartInfoRepository partInfoRepository;
	
	private Logger logger = LogManager.getLogger(PartInfoController.class);

	@GetMapping("/")
	public Response getAllPartInfo() {
		
		Response response = new Response();
		response.setStatus(false);
		response.setResponse(null);
		try {
		List<PartInfo> list = null;
		list = partInfoRepository.findAll();
		response.setStatus(true);
		response.setError(null);
		response.setResponse(list);
		} catch(Exception e) {
			response.setError("Unable to get all Pages");
			logger.error("Error : "+e);
		}
		return response;
	}
	
	@GetMapping("/{Id}")
	public Response getPartInfoById(@PathVariable(value = "Id") Long id) {

		Response response = new Response();
		response.setStatus(false);
		response.setResponse(null);
		try {
		PartInfo partInfo = partInfoRepository.findById(id).get();
		response.setStatus(true);
		response.setError(null);
		response.setResponse(partInfo);
		}
		catch(NoSuchElementException ex) {
			response.setError("Unable to find PartInfo with id :"+id);
			logger.error("Error : "+ex);
		}
		catch(Exception e) {
			response.setError("Unable to get PartInfo");
			logger.error("Error : "+e);
		}
		return response;
	}
	
	@PostMapping("/")
	public Response createPartInfo(@Valid @RequestBody PartInfoPayload partInfoPayload) {
		
		Response response = new Response();
		response.setStatus(false);
		response.setResponse(null);
		try {
			if(null == partInfoPayload) {
				response.setError("Payload cannot be null or empty");
			}else if(isNullOrEmpty(partInfoPayload.getPartNo())) {
				response.setError("Part Number cannot be null or empty");
			}
			else {
		PartInfo partInfo = new PartInfo();
		partInfo.setDescription(partInfoPayload.getDescription());
		partInfo.setPartNo(partInfoPayload.getPartNo());
		partInfo.setPrice(partInfoPayload.getPrice());
		partInfo.setTat(partInfoPayload.getTat());
		partInfo.setCreatedBy(partInfoPayload.getCreatedBy());
		partInfo.setUpdatedBy(partInfoPayload.getCreatedBy());
		partInfo = partInfoRepository.save(partInfo);
		response.setStatus(true);
		response.setError(null);
		response.setResponse(partInfo);
			}
		} catch (Exception e) {
			response.setError("Could not create partInfo");
			logger.error("Error : "+e);
		}
		return response;
	}
	
	@PutMapping("/")
	public Response updatePartInfo(@Valid @RequestBody PartInfoPayload partInfoPayload) {
		
		Response response = new Response();
		response.setStatus(false);
		response.setResponse(null);
		try {
			if(null == partInfoPayload) {
				response.setError("Payload cannot be null or empty");
			}else if(isNullOrEmpty(partInfoPayload.getPartNo())) {
				response.setError("Part Number cannot be null or empty");
			} else if(isZeroOrNull(partInfoPayload.getId())) {
				response.setError("Part Info Id cannot zero or null");
			}
			else {
		    PartInfo partInfo = null;
			partInfo = partInfoRepository.findById(partInfoPayload.getId()).get();
			partInfo.setDescription(partInfoPayload.getDescription());
			partInfo.setPartNo(partInfoPayload.getPartNo());
			partInfo.setPrice(partInfoPayload.getPrice());
			partInfo.setTat(partInfoPayload.getTat());
			partInfo.setUpdatedBy(partInfoPayload.getUpdatedBy());
			partInfo.setStatus(partInfoPayload.getStatus());
			partInfoRepository.save(partInfo);
			response.setStatus(true);
			response.setError(null);
			response.setResponse(partInfo);
			}
		} catch(Exception ex) {
			response.setError("Please pass valid PartInfoPayload data to update.");
			logger.error("Error : "+ex);
		}
		return response;
	}
}
