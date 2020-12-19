package com.ste.enginestreamportal.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ste.enginestreamportal.model.RolePageMapping;

public interface RolePageMappingRepository extends JpaRepository<RolePageMapping, Long>{

public Optional<List<RolePageMapping>> findByRoleId(Long roleId);
	

	@Transactional
	@Modifying
	@Query("delete from RolePageMapping rp where rp.roleId= :roleId")
	public void removeByRoleId(Long roleId);

	
}
