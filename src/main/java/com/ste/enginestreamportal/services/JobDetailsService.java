package com.ste.enginestreamportal.services;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ste.enginestreamportal.model.JobDetails;
import com.ste.enginestreamportal.payload.JobDetailsPayload;
import com.ste.enginestreamportal.repository.JobDetailsRepository;
import com.ste.enginestreamportal.repository.UserRepository;

@Service
public class JobDetailsService {
	
	@Autowired
	JobDetailsRepository jobDetailsRepository;
	
	@Autowired
	UserRepository userRepository;
	
	public JobDetails createJobDetails(JobDetailsPayload jobDetailsPayload) {
		
		JobDetails jobDetails = new JobDetails();
		jobDetails = mapJobDetailsPayloadToJobDetails(jobDetails, jobDetailsPayload);
		try {
			jobDetails.setCreatedBy(jobDetailsPayload.getCreatedBy());
			jobDetails.setUpdatedBy(jobDetailsPayload.getCreatedBy());
			jobDetails = jobDetailsRepository.save(jobDetails);
		} catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Could not create Job Details");
		}
		return jobDetails;
	}
	
	private JobDetails mapJobDetailsPayloadToJobDetails(JobDetails jobDetails, JobDetailsPayload jobDetailsPayload) {
		
		jobDetails.setApprover1(userRepository.findById(jobDetailsPayload.getApprover1()).get());
		jobDetails.setApprover2(userRepository.findById(jobDetailsPayload.getApprover2()).get());
		jobDetails.setApprover3(userRepository.findById(jobDetailsPayload.getApprover3()).get());
		jobDetails.setAssignedTo(userRepository.findById(jobDetailsPayload.getAssignedTo()).get());
		jobDetails.setCondition(jobDetailsPayload.getCondition());
		jobDetails.setConditionReportStatus(jobDetailsPayload.getConditionReportStatus());
		jobDetails.setCsn(jobDetailsPayload.getCsn());
		jobDetails.setCustomer(jobDetailsPayload.getCustomer());
		jobDetails.setCustomerPO(jobDetailsPayload.getCustomerPO());
		jobDetails.setCustomerType(jobDetailsPayload.getCustomerType());
		jobDetails.setEffectiveDate(jobDetailsPayload.getEffectiveDate());
		jobDetails.setFindings(jobDetailsPayload.getFindings());
		jobDetails.setFormNo(jobDetailsPayload.getFormNo());
		jobDetails.setJobCode(jobDetailsPayload.getJobCode());
		jobDetails.setJobNo(jobDetailsPayload.getJobNo());
		jobDetails.setOthers(jobDetailsPayload.getOthers());
		jobDetails.setPartDescription(jobDetailsPayload.getPartDescription());
		jobDetails.setPartReceivedDate(jobDetailsPayload.getPartReceivedDate());
		jobDetails.setReason(jobDetailsPayload.getReason());
		jobDetails.setRecommendation(jobDetailsPayload.getRecommendation());
		jobDetails.setReportNo(jobDetailsPayload.getReportNo());
		jobDetails.setTsn(jobDetailsPayload.getTsn());
		jobDetails.setWork(jobDetailsPayload.getWork());
		return jobDetails;		
	}
	
	public JobDetails updateJobDetails(JobDetailsPayload jobDetailsPayload) {
		
		JobDetails jobDetails = jobDetailsRepository.findById(jobDetailsPayload.getId()).get();
		jobDetails = mapJobDetailsPayloadToJobDetails(jobDetails, jobDetailsPayload);
		try {
			jobDetails.setUpdatedBy(jobDetailsPayload.getUpdatedBy());
			jobDetails.setStatus(jobDetailsPayload.getStatus());
            jobDetails = jobDetailsRepository.save(jobDetails);
		} catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Could not update Job Details");
		}
		return jobDetails;
	}
	
	public Page<JobDetails> findAllJobDetails(Pageable pageable, String sortType, String searchWord, String statusType) {
        Page<JobDetails> page = jobDetailsRepository.findAll(new Specification<JobDetails>() {

			
				 private static final long serialVersionUID = 1L;


		            @Override
		            public Predicate toPredicate(Root<JobDetails> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		                List<Predicate> predicates = new ArrayList<>();
		                if("active".equals(statusType)) {
		                    predicates.add(cb.equal(root.get("status").as(String.class), "1"));
		                } else if("inactive".equals(statusType)) {
		                    predicates.add(cb.equal(root.get("status").as(String.class), "0"));
		                } else {
		                    
		                }
		                if (searchWord != null && !"".equals(searchWord)) {
		                    String pattern = "%" + searchWord + "%";
		                    predicates.add(cb.or(cb.like(root.get("customer").as(String.class), pattern),
		                            cb.like(root.get("partDescription").as(String.class), pattern),
		                            cb.like(root.get("formNo").as(String.class), pattern),
		                            cb.like(root.get("reportNo").as(String.class), pattern),
		                            cb.like(root.get("reason").as(String.class), pattern),
		                            cb.like(root.get("condition").as(String.class), pattern),
		                            cb.like(root.get("jobNo").as(String.class), pattern),
		                            cb.like(root.get("jobCode").as(String.class), pattern)));
		                }

		                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
		            }
		        }, pageable);
		        System.out.println(page.getContent().size() + " " + " " + pageable.getPageSize());

		        return page;
			
        }

}
