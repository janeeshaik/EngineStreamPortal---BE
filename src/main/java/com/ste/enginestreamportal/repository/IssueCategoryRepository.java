package com.ste.enginestreamportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ste.enginestreamportal.model.IssueCategory;


public interface IssueCategoryRepository extends JpaRepository<IssueCategory, Long>,PagingAndSortingRepository<IssueCategory, Long>,JpaSpecificationExecutor<IssueCategory>{

	
}
