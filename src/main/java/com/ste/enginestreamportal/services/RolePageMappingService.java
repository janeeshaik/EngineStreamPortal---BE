package com.ste.enginestreamportal.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ste.enginestreamportal.model.RolePageMapping;
import com.ste.enginestreamportal.payload.RolePageMappingPayload;
import com.ste.enginestreamportal.repository.PagesRepository;
import com.ste.enginestreamportal.repository.RolePageMappingRepository;
import com.ste.enginestreamportal.repository.RoleRepository;

@Service
public class RolePageMappingService {
	
	private Logger logger = LogManager.getLogger(RolePageMappingService.class);


	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private RolePageMappingRepository rolePageMappingRepository;

	@Autowired
	private PagesRepository pagesRepository;
	
	public List<RolePageMappingPayload> getRolePageMappingPayloads(Long roleId) {
		if (null == roleId) {
			logger.error("The roleId is empty.");
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "RoleId is empty.");
		}
		logger.info("Fetching the role to page mapping entries for role - " + roleId);
		List<RolePageMappingPayload> rolePageMappingPayloads = new ArrayList<RolePageMappingPayload>();
		List<RolePageMapping> rolePageMappings = rolePageMappingRepository.findByRoleId(roleId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.OK,
						"No RoleToPageMapping entries found for role with ID = " + roleId));
		for(RolePageMapping rolePageMapping : rolePageMappings) {
			rolePageMappingPayloads.add(mapRolePageMappingToRolePageMappingPayload(rolePageMapping));
		}
		return (rolePageMappingPayloads);
	}
	public List<RolePageMappingPayload> getAllRolePageMappingPayloads() {
		logger.info("Fetching all role to page mapping entries.");
		List<RolePageMappingPayload> rolePageMappingPayloads = new ArrayList<RolePageMappingPayload>();
		List<RolePageMapping> rolePageMappings = rolePageMappingRepository.findAll();
		if(null == rolePageMappings || rolePageMappings.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No role to page mapping entries found.");
		}
		for(RolePageMapping rolePageMapping : rolePageMappings) {
			rolePageMappingPayloads.add(mapRolePageMappingToRolePageMappingPayload(rolePageMapping));
		}
		return (rolePageMappingPayloads);
	}
	
	public List<RolePageMappingPayload> createRolePageMapping(List<RolePageMappingPayload> rolePageMappingPayloads) {
		if (null == rolePageMappingPayloads) {
			logger.error("Cannot create role page mapping entries. The page mapping entries don't exist");
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The role page mapping entries don't exist.");
		}
				
		rolePageMappingRepository.removeByRoleId(rolePageMappingPayloads.get(0).getRoleId());
		List<RolePageMapping> rolePageMappings = new ArrayList<RolePageMapping>();

		for (RolePageMappingPayload rolePageMappingPayload : rolePageMappingPayloads) {
			logger.info("Saving role page mapping entry with RoleId = " + rolePageMappingPayload.getRoleId()
					+ " PageId = " + rolePageMappingPayload.getPageId());
			rolePageMappings.add(mapRolePageMappingPayloadToRolePageMapping(rolePageMappingPayload));
		}

		List<RolePageMapping> savedRolePageMapping = rolePageMappingRepository.saveAll(rolePageMappings);
		List<RolePageMappingPayload> savedRolePageMappingPayloads = new ArrayList<RolePageMappingPayload>(
				savedRolePageMapping.size());
		for (RolePageMapping rolePageMapping : savedRolePageMapping) {
			savedRolePageMappingPayloads.add(mapRolePageMappingToRolePageMappingPayload(rolePageMapping));
		}

		return (savedRolePageMappingPayloads);
	}

	private RolePageMappingPayload mapRolePageMappingToRolePageMappingPayload(RolePageMapping rolePageMapping) {
		RolePageMappingPayload rolePageMappingPayload = new RolePageMappingPayload();
		rolePageMappingPayload.setId(rolePageMapping.getId());
		rolePageMappingPayload.setCreate(rolePageMapping.getCreates());
		rolePageMappingPayload.setEdit(rolePageMapping.getUpdates());
		rolePageMappingPayload.setDelete(rolePageMapping.getDeletes());
		rolePageMappingPayload.setView(rolePageMapping.getViews());
		rolePageMappingPayload.setFinance(rolePageMapping.getFinance());
		rolePageMappingPayload.setRoleId(rolePageMapping.getRoleId());
		rolePageMappingPayload.setPageId(rolePageMapping.getPageId().getId());
		rolePageMappingPayload.setCreatedAt(rolePageMapping.getCreatedAt());
		rolePageMappingPayload.setCreatedBy(rolePageMapping.getCreatedBy());
		rolePageMappingPayload.setUpdatedAt(rolePageMapping.getUpdatedAt());
		rolePageMappingPayload.setUpdatedBy(rolePageMapping.getUpdatedBy());

		return rolePageMappingPayload;
	}

	private RolePageMapping mapRolePageMappingPayloadToRolePageMapping(RolePageMappingPayload rolePageMappingPayload) {
		RolePageMapping rolePageMapping = new RolePageMapping();
		rolePageMapping.setPageId(pagesRepository.findById(rolePageMappingPayload.getPageId())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"Page with Id - " + rolePageMappingPayload.getPageId())));
		rolePageMapping.setRoleId(rolePageMappingPayload.getRoleId());
		rolePageMapping.setCreates(rolePageMappingPayload.getCreate());
		rolePageMapping.setDeletes(rolePageMappingPayload.getDelete());
		rolePageMapping.setViews(rolePageMappingPayload.getView());
		rolePageMapping.setUpdates(rolePageMappingPayload.getEdit());
		rolePageMapping.setFinance(rolePageMappingPayload.getFinance());
		rolePageMapping.setCreatedBy(rolePageMappingPayload.getCreatedBy());
		rolePageMapping.setUpdatedBy(rolePageMapping.getUpdatedBy());
		return rolePageMapping;
	}

	public List<RolePageMappingPayload> updateRolePageMapping(List<RolePageMappingPayload> rolePageMappingPayloads) {
		if (null == rolePageMappingPayloads) {
			logger.error("Cannot update role page mapping entries. The page mapping entries don't exist");
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The role page mapping entries are empty.");
		}
		for (RolePageMappingPayload rolePageMappingPayload : rolePageMappingPayloads) {
			Optional<RolePageMapping> optRolePageMapping = rolePageMappingRepository
					.findById(rolePageMappingPayload.getId());
			if (!optRolePageMapping.isPresent()) {
				logger.error("Cannot fetch role to page mapping with Id " + rolePageMappingPayload.getId());
				continue;
			}
			RolePageMapping roleToPageMapping = optRolePageMapping.get();
			roleToPageMapping.setFinance(rolePageMappingPayload.getFinance());
			roleToPageMapping.setCreates(rolePageMappingPayload.getCreate());
			roleToPageMapping.setDeletes(rolePageMappingPayload.getDelete());
			roleToPageMapping.setUpdates(rolePageMappingPayload.getEdit());
			roleToPageMapping.setViews(rolePageMappingPayload.getView());
            roleToPageMapping.setUpdatedBy(rolePageMappingPayload.getUpdatedBy());
            roleToPageMapping.setStatus(rolePageMappingPayload.getStatus());
			rolePageMappingRepository.save(roleToPageMapping);
		}

		return (rolePageMappingPayloads);
	}
	
}
