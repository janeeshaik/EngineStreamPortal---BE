package com.ste.enginestreamportal.services;

import java.sql.Date;
import java.text.DecimalFormat;
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

import com.ste.enginestreamportal.model.Issue;
import com.ste.enginestreamportal.model.State;
import com.ste.enginestreamportal.model.User;
import com.ste.enginestreamportal.payload.IssuePayload;
import com.ste.enginestreamportal.repository.DepartmentRepository;
import com.ste.enginestreamportal.repository.IssueCategoryRepository;
import com.ste.enginestreamportal.repository.IssueRepository;
import com.ste.enginestreamportal.repository.IssueSubCategoryRepository;
import com.ste.enginestreamportal.repository.StateRepository;
import com.ste.enginestreamportal.repository.UserRepository;
import com.ste.enginestreamportal.util.Utils;

@Service
public class IssueService extends Utils{


	@Autowired
	IssueRepository issueRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	IssueCategoryRepository issueCategoryRepository;

	@Autowired
	IssueSubCategoryRepository issueSubCategoryRepository;

	@Autowired
	DepartmentRepository departmentRepository;
	
	@Autowired
	StateRepository stateRepository;
	
	public Page<Issue> findAllIssues(Pageable pageable, String sortType, String searchWord, String stateType) {
		Page<Issue> page = issueRepository.findAll(new Specification<Issue>() {

			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<Issue> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<>();
				if("open".equals(stateType)) {
					predicates.add(cb.equal(root.get("state").as(State.class), stateRepository.findById(1L).get()));
				} else if("closed".equals(stateType)) {
					predicates.add(cb.equal(root.get("state").as(State.class), stateRepository.findById(2L).get()));
				} else if("inProgress".equals(stateType)) {
					predicates.add(cb.equal(root.get("state").as(State.class), stateRepository.findById(3L).get()));
				}
				else {

				}
				if (searchWord != null && !"".equals(searchWord)) {
					String pattern = "%" + searchWord + "%";
					predicates.add(cb.or(cb.like(root.get("issueNumber").as(String.class), pattern),
							cb.like(root.get("description").as(String.class), pattern),
							cb.like(root.get("engineSerialNumber").as(String.class), pattern),
							cb.like(root.get("subject").as(String.class), pattern)));
				}


				return cb.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		}, pageable);
		System.out.println(page.getContent().size() + " " + " " + pageable.getPageSize());

		return page;
	}

	public Issue createIssue(IssuePayload issuePayload) {

		Issue issue = new Issue();
		issue = mapIssuePayloadToIssue(issue, issuePayload);
		issue.setCreatedBy(issuePayload.getCreatedBy());
		issue.setUpdatedBy(issuePayload.getCreatedBy());
		try {
		issue = issueRepository.save(issue);
		DecimalFormat df = new DecimalFormat("00000");
		String issueNumber = "STEA"+df.format(issue.getId());
		issue.setIssueNumber(issueNumber);
		issue = issueRepository.save(issue);
		} catch(Exception ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Could not create Issue");
		}
		
		return issue;
	}

	private Issue mapIssuePayloadToIssue(Issue issue, IssuePayload issuePayload) {
		User raisedByUser = userRepository.findById(issuePayload.getRaisedBy()).get();
		//if(raisedByUser.getRoleId().getRoleName().equalsIgnoreCase("Customer") || raisedByUser.getRoleId().getRoleName().contains("Customer")) {
		if(raisedByUser.getRoleId().getId() == 4) {
            //externalCustomer issue creation
			issue.setCustomerId(raisedByUser);
			issue.setRaisedForDepartmentId(departmentRepository.findById(14L).get());
		}
		else {
			//internalUser issue creation
			issue.setCustomerId(userRepository.findById(issuePayload.getCustomerId()).get());
		    issue.setRaisedForDepartmentId(departmentRepository.findById(issuePayload.getRaisedForDepartmentId()).get());
		    if(!isZeroOrNull(issuePayload.getRaisedFor())){
		    		issue.setRaisedFor(userRepository.findById(issuePayload.getRaisedFor()).get());
		    }
		    issue.setResponseRequiredDate(issuePayload.getResponseRequiredDate());
		    issue.setEngineSerialNumber(issuePayload.getEngineSerialNumber());
		}
		issue.setState(stateRepository.findById(1L).get());
		issue.setRaisedBy(raisedByUser);
		issue.setRaisedDate(new Date(System.currentTimeMillis()));
		issue.setIssueCategoryId(issueCategoryRepository.findById(issuePayload.getIssueCategoryId()).get());
		issue.setSubject(issuePayload.getSubject());
		issue.setDescription(issuePayload.getDescription());
		issue.setExcusableDelay(0);
		if(!isZeroOrNull(issuePayload.getIssueSubCategoryId())) {
			issue.setIssueSubCategoryId(issueSubCategoryRepository.findById(issuePayload.getIssueSubCategoryId()).get());
		}
		
		return issue;
	}
	
	public Issue updateIssue(IssuePayload issuePayload) {
		Issue issue = issueRepository.findById(issuePayload.getId()).get();
		
		issue.setState(stateRepository.findById(issuePayload.getState()).get());
		issue.setIssueCategoryId(issueCategoryRepository.findById(issuePayload.getIssueCategoryId()).get());
		issue.setSubject(issuePayload.getSubject());
		issue.setDescription(issuePayload.getDescription());
		issue.setExcusableDelay(issuePayload.getExcusableDelay());
		if(!isZeroOrNull(issuePayload.getIssueSubCategoryId())) {
			issue.setIssueSubCategoryId(issueSubCategoryRepository.findById(issuePayload.getIssueSubCategoryId()).get());
		}
		User updatingUser = userRepository.findById(Long.parseLong(issuePayload.getUpdatedBy())).get();
		if(updatingUser.getRoleId().getId() != 4) {
			if(isZeroOrNull(issuePayload.getRaisedForDepartmentId())) {
				issue.setRaisedForDepartmentId(null);
			}
			else {
		issue.setRaisedForDepartmentId(departmentRepository.findById(issuePayload.getRaisedForDepartmentId()).get());
		}
		}
		if(!isZeroOrNull(issuePayload.getRaisedFor())){
	    		issue.setRaisedFor(userRepository.findById(issuePayload.getRaisedFor()).get());
	    }
	    issue.setResponseRequiredDate(issuePayload.getResponseRequiredDate());
	    issue.setEngineSerialNumber(issuePayload.getEngineSerialNumber());
		issue.setUpdatedBy(issuePayload.getUpdatedBy());
		issue.setStatus(issuePayload.getStatus());
        issueRepository.save(issue);
        
        return issue;
	}
}
