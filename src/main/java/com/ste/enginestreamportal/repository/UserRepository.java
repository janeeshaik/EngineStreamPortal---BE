package com.ste.enginestreamportal.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.ste.enginestreamportal.model.RolePageMapping;
import com.ste.enginestreamportal.model.User;
import com.ste.enginestreamportal.payload.UserPayload;

public interface UserRepository extends JpaRepository<User, Long>,PagingAndSortingRepository<User, Long>,JpaSpecificationExecutor<User> {
	
	@Query("SELECT u FROM User u where u.userid = :userName")
	public Optional<User> findByUserIddetails(String userName);

	@Query("SELECT u.roleId.rolePageMapping FROM User u INNER JOIN u.roleId.rolePageMapping rp "
			+ "WHERE u.id = :userId AND rp.pageIdAsLong = :pageId")
	public List<RolePageMapping> findUsersForRole(@Param("userId") Long userId, @Param("pageId") Long pageId);

	
	@Query("SELECT u FROM User u where u.id = :Id")
	public UserPayload findbyids(@Param("Id") Long Id);

	@Query("SELECT u FROM User u INNER JOIN u.roleId.rolePageMapping rp " + "WHERE u.id = :Id and rp.pageId.parentId=1")
	public List<User> findByIdParent(@Param("Id") Long Id);
	
	@Query("SELECT u FROM User u where u.status = true")
	public List<User> getAllActiveUsers();
	
	@Query("SELECT u FROM User u where u.departmentId.id = :deptId")
	public List<User> findbydeptid(@Param("deptId") Long deptId);

	
}