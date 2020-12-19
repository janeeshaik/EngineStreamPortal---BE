package com.ste.enginestreamportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ste.enginestreamportal.model.JobDetails;

public interface JobDetailsRepository extends JpaRepository<JobDetails, Long>,PagingAndSortingRepository<JobDetails, Long>,JpaSpecificationExecutor<JobDetails>{

}
