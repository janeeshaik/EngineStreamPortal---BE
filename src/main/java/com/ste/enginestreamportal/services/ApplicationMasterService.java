package com.ste.enginestreamportal.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ste.enginestreamportal.model.ApplicationMaster;
import com.ste.enginestreamportal.payload.ApplicationMasterPayload;
import com.ste.enginestreamportal.repository.ApplicationMasterRepository;
import com.ste.enginestreamportal.repository.RoleRepository;

@Service
public class ApplicationMasterService {

	@Autowired
	ApplicationMasterRepository applicationMasterRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	public ApplicationMaster createApplicationMaster(ApplicationMasterPayload applicationMasterPayload) {
		
		ApplicationMaster applicationMaster = new ApplicationMaster();
		applicationMaster = mapApplicationMasterPayload(applicationMaster, applicationMasterPayload);
	    try {
	    	applicationMaster.setCreatedBy(applicationMasterPayload.getCreatedBy());
	    	applicationMaster.setUpdatedBy(applicationMasterPayload.getCreatedBy());
	    	applicationMaster = applicationMasterRepository.save(applicationMaster);
	    }catch (Exception e) {
			// TODO: handle exception
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Could not create ApplicationMaster with duplicate ApplicationMaster Id");
		}
	    return applicationMaster;
	}
	
	private ApplicationMaster mapApplicationMasterPayload(ApplicationMaster applicationMaster, ApplicationMasterPayload applicationMasterPayload) {
		
		applicationMaster.setName(applicationMasterPayload.getName());
		applicationMaster.setDescription(applicationMasterPayload.getDescription());
	    return applicationMaster;
	}
	
	public ApplicationMaster updateApplicationMaster(ApplicationMasterPayload applicationMasterPayload) {
		Optional<ApplicationMaster> optApplicationMaster = applicationMasterRepository.findById(applicationMasterPayload.getId());
		ApplicationMaster applicationMaster = optApplicationMaster.get();
		applicationMaster = mapApplicationMasterPayload(applicationMaster, applicationMasterPayload);
		applicationMaster.setUpdatedBy(applicationMasterPayload.getUpdatedBy());
		applicationMaster.setStatus(applicationMasterPayload.getStatus());
    	applicationMaster = applicationMasterRepository.save(applicationMaster);
        return applicationMaster;
	}
}
