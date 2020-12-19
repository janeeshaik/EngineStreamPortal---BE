package com.ste.enginestreamportal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ste.enginestreamportal.model.Pages;


public interface PagesRepository extends JpaRepository<Pages, Long>,PagingAndSortingRepository<Pages, Long>,JpaSpecificationExecutor<Pages>{

	@Query("SELECT p from Pages p inner join RolePageMapping rp on p.id=rp.pageId.id where p.parentId=1  and rp.roleId=:roleid"
			+ " and rp.views=1 ")
	public List<Object> getParentPageDetailswithroleid(Long roleid);
	
	@Query("SELECT p from Pages p inner join RolePageMapping rp on p.id=rp.pageId.id where p.parentId=1 and rp.roleId=:roleid"
			+ " and rp.views=1 ")
	public List<Object> getParentPageDetailswithroleidonly(Long roleid);
	
	@Query("SELECT p from Pages p  where p.parentId=1")
	public List<Object> getParentPageDetails();
	
	@Query("SELECT p from Pages p")
	public List<Pages> getAllParentPageDetails();
	
	@Query("SELECT p, rp from Pages p inner join RolePageMapping rp on p.id=rp.pageId.id where rp.roleId=:roleid")
	public List<Pages> getAllParentPageDetailswithroleid(Long roleid);


	


	
}
