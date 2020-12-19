package com.ste.enginestreamportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ste.enginestreamportal.model.ApplicationMaster;

public interface ApplicationMasterRepository extends JpaRepository<ApplicationMaster, Long>{

	@Query("select am.name from ApplicationMaster am where id = :id")
	public String getApplicationNamesById(Long id);
}
