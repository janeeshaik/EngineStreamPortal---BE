package com.ste.enginestreamportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ste.enginestreamportal.model.Issue;

public interface IssueRepository extends JpaRepository<Issue, Long>,PagingAndSortingRepository<Issue, Long>,JpaSpecificationExecutor<Issue>{

}
