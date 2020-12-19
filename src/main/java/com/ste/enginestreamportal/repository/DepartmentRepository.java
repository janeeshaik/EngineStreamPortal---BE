package com.ste.enginestreamportal.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ste.enginestreamportal.model.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long>,PagingAndSortingRepository<Department, Long>,JpaSpecificationExecutor<Department> {

	@Query("SELECT d FROM Department d where d.departmentName = :departmentName")
	public Optional<Department> findDepartmentByName(String departmentName);
}
