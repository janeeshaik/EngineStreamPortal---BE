package com.ste.enginestreamportal.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ste.enginestreamportal.model.State;

public interface StateRepository extends JpaRepository<State, Long>,PagingAndSortingRepository<State, Long>,JpaSpecificationExecutor<State> {

	@Query("SELECT d FROM State d where d.stateName = :stateName")
	public Optional<State> findStateByName(String stateName);
}
