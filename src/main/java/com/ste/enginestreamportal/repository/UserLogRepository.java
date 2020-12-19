package com.ste.enginestreamportal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ste.enginestreamportal.model.UserLog;

public interface UserLogRepository extends JpaRepository<UserLog, Long>{

	@Query("SELECT u FROM UserLog u where u.userId.id = :Id ORDER BY loginDate")
	public List<UserLog> findUserLogsByUserId(@Param("Id") Long Id);
}
