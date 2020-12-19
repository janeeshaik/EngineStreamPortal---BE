package com.ste.enginestreamportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ste.enginestreamportal.model.IssueSubCategory;

public interface IssueSubCategoryRepository extends JpaRepository<IssueSubCategory, Long>,PagingAndSortingRepository<IssueSubCategory, Long>,JpaSpecificationExecutor<IssueSubCategory> {

}
