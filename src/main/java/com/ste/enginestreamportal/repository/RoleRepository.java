package com.ste.enginestreamportal.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.ste.enginestreamportal.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long>,PagingAndSortingRepository<Role, Long>,JpaSpecificationExecutor<Role>{
	
	@Query("SELECT b FROM Role b WHERE b.id != 1")
	public List<Role> findnotsuperadmin();

	@Transactional
	@Modifying
	@Query("Update Role set Status = :status where id = :roleId")
	public void updateStatusForRole(@Param("status") Boolean status, @Param("roleId") Long roleId);
}
