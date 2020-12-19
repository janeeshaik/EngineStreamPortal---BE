package com.ste.enginestreamportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ste.enginestreamportal.model.BusinessType;

public interface BusinessTypeRepository extends JpaRepository<BusinessType, Long>,PagingAndSortingRepository<BusinessType, Long>,JpaSpecificationExecutor<BusinessType> {
	
	@Query("select a from BusinessType a where a.businessTypeName =:businessTypeName")
    public  BusinessType findBusinessTypeIdByName(String  businessTypeName);

}
