package com.ste.enginestreamportal.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ste.enginestreamportal.model.ExternalUser;

public interface ExternalUserRepo extends JpaRepository<ExternalUser, Long>{

	@Query("SELECT u FROM ExternalUser u where u.userid = :userId")
	public Optional<ExternalUser> findByUserIddetails(String userId);

}
