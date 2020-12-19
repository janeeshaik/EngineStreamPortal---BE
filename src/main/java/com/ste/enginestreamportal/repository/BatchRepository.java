package com.ste.enginestreamportal.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ste.enginestreamportal.model.Batch;
import com.ste.enginestreamportal.model.Material;

public interface BatchRepository extends JpaRepository<Batch, Long>,PagingAndSortingRepository<Batch, Long>,JpaSpecificationExecutor<Batch>{
	
	@Query("select b.material from Batch b where b.id = :batchId")
	public Material getMaterialByBatchIdUsingQuery(Long batchId);
	
	@Modifying
	@Transactional
	@Query("update Batch b set b.surplusFlag = 1 where b.ageByMonth > 24")
	public void updateBatchSurplusFlagJob();

	@Query("select b from Batch b where b.id = :batchId")
	public Batch getBatchById(Long batchId);

	@Query("select b from Batch b where b.surplusFlag = :surplusFlag")
	public List<Batch> getSurplusInventory(Boolean surplusFlag);

	@Modifying
	@Transactional
	@Query("update Batch b set b.surplusFlag = :flag where b.id = :batchId")
	public void updateBatchSurplusFlag(Long batchId, Boolean flag);
}