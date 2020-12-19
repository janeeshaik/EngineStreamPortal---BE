package com.ste.enginestreamportal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.ste.enginestreamportal.model.Responses;

public interface ResponsesRepository extends JpaRepository<Responses, Long>,PagingAndSortingRepository<Responses, Long>,JpaSpecificationExecutor<Responses>{

	@Query("SELECT r FROM Responses r where r.issueId.id = :issueId")
	public List<Responses> findResponsesbyIssueId(@Param("issueId") Long issueId);

}
