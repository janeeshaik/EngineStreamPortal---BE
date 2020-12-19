package com.ste.enginestreamportal.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ste.enginestreamportal.model.CostApprovalForm;
import com.ste.enginestreamportal.payload.CostApprovalFormPayload;
import com.ste.enginestreamportal.repository.CostApprovalFormRepository;
import com.ste.enginestreamportal.repository.JobDetailsRepository;
import com.ste.enginestreamportal.repository.UserRepository;

@Service
public class CostApprovalFormService {

	@Autowired
	CostApprovalFormRepository costApprovalFormRepository;
	
	@Autowired
	JobDetailsRepository jobDetailsRepository;
	
	@Autowired
	UserRepository userRepository;
	
	public CostApprovalForm createCostApprovalForm(CostApprovalFormPayload costApprovalFormPayload) {
		CostApprovalForm costApprovalForm = new CostApprovalForm();
		costApprovalForm = mapCostApprovalFormPayloadToCostApprovalForm(costApprovalForm, costApprovalFormPayload);
		try {
			costApprovalForm.setCreatedBy(costApprovalFormPayload.getCreatedBy());
			costApprovalForm.setUpdatedBy(costApprovalFormPayload.getCreatedBy());
			costApprovalForm = costApprovalFormRepository.save(costApprovalForm);
		} catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Could not create Cost Approval Form");
		}
		return costApprovalForm;
	}
	
	private CostApprovalForm mapCostApprovalFormPayloadToCostApprovalForm(CostApprovalForm costApprovalForm, CostApprovalFormPayload costApprovalFormPayload) {
		
		costApprovalForm.setApprover1(userRepository.findById(costApprovalFormPayload.getApprover1()).get());
		costApprovalForm.setApprover2(userRepository.findById(costApprovalFormPayload.getApprover2()).get());
		costApprovalForm.setCurrency(costApprovalFormPayload.getCurrency());
		costApprovalForm.setEstimatedPrice(costApprovalFormPayload.getEstimatedPrice());
		costApprovalForm.setItemDescription(costApprovalFormPayload.getItemDecription());
		costApprovalForm.setJobId(jobDetailsRepository.findById(costApprovalFormPayload.getJobId()).get());
		costApprovalForm.setRaisedBy(userRepository.findById(costApprovalFormPayload.getRaisedBy()).get());
		costApprovalForm.setExpiryDate(costApprovalFormPayload.getExpiryDate());
		return costApprovalForm;
	}
	
	public CostApprovalForm updateCostApprovalForm(CostApprovalFormPayload costApprovalFormPayload) {
		
		CostApprovalForm costApprovalForm = costApprovalFormRepository.findById(costApprovalFormPayload.getId()).get();
		costApprovalForm = mapCostApprovalFormPayloadToCostApprovalForm(costApprovalForm, costApprovalFormPayload);
		try {
			costApprovalForm.setUpdatedBy(costApprovalFormPayload.getUpdatedBy());
			costApprovalForm.setStatus(costApprovalFormPayload.getStatus());
			costApprovalForm = costApprovalFormRepository.save(costApprovalForm);
		} catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Could not update Cost Approval Form");
		}
		return costApprovalForm;
	}
}