package com.ste.enginestreamportal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ste.enginestreamportal.model.UserPasswords;

public interface UserPasswordsRepository  extends JpaRepository<UserPasswords, Long> {
	
	@Query("SELECT u FROM UserPasswords u where u.userId.id = :userId")
	public List<UserPasswords> getUserPasswordsByUserId(@Param("userId") Long userId);

	@Query("SELECT u FROM UserPasswords u where u.userId.id = :userId ORDER BY u.createdAt ASC")
	public List<UserPasswords> getUserPasswordsByDateOrder(@Param("userId") Long userId);
}
