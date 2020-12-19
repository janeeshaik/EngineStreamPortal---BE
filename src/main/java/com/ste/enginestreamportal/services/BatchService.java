package com.ste.enginestreamportal.services;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.ste.enginestreamportal.model.Batch;
import com.ste.enginestreamportal.model.Material;
import com.ste.enginestreamportal.repository.BatchRepository;

@Service
public class BatchService {

	@Autowired
	BatchRepository batchRepo;

	public List<Batch> findAll() {
		return batchRepo.findAll();
	}

	public List<Batch> getSurplusInventory(Boolean surplusFlag) {
		return batchRepo.getSurplusInventory(surplusFlag);
	}
	
	public Material getMaterialByBatchIdUsingQuery(Long batchId) {
		return batchRepo.getMaterialByBatchIdUsingQuery(batchId);
	}

	public void updateBatchSurplusFlag(Long batchId, Boolean flag) {
		batchRepo.updateBatchSurplusFlag(batchId, flag);
		//return batchRepo.updateBatchSurplusFlag(batchId, flag);
	}

	public Batch getBatchById(Long batchId) {
		return batchRepo.getBatchById(batchId);
	}
	
	public List<Batch> getEnabledSurplusFlagBatchRecords(Pageable pageable, boolean visited) {

		Page<Batch> page =  batchRepo.findAll(new Specification<Batch>() {
			@Override
			public Predicate toPredicate(Root<Batch> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<>();
				predicates.add(
						cb.equal(root.get("surplusFlag").as(String.class), "1")
						);

				return cb.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		}, pageable);
		System.out.println(page.getContent().size() + " "+ visited+ " "+ pageable.getPageSize());

		if(page.getContent().size() == 0 && !visited) {
			int pageIndex = 0;
			System.out.println("if is called");
			Pageable p = PageRequest.of(pageIndex, pageable.getPageSize());
			visited = true;
			return getEnabledSurplusFlagBatchRecords(p,visited);
		}
		return page.getContent();
	}
	
	public Page<Batch> findAllBatches(Pageable pageable, String sortType, String searchWord, String statusType) {
        Page<Batch> page = batchRepo.findAll(new Specification<Batch>() {

        	private static final long serialVersionUID = 1L;

        	
			@Override
			public Predicate toPredicate(Root<Batch> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				 List<Predicate> predicates = new ArrayList<>();
	                if("active".equals(statusType)) {
	                    predicates.add(cb.equal(root.get("status").as(String.class), "1"));
	                } else if("inactive".equals(statusType)) {
	                    predicates.add(cb.equal(root.get("status").as(String.class), "0"));
	                } else {
	                    
	                }
	                if (searchWord != null && !"".equals(searchWord)) {
	                    String pattern = "%" + searchWord + "%";
	                    predicates.add(cb.or(cb.like(root.get("bizUnit").as(String.class), pattern),
	                    		 cb.like(root.get("bizUnitDescription").as(String.class), pattern),
		                            cb.like(root.get("storageLocation").as(String.class), pattern),
		                            cb.like(root.get("vendorName").as(String.class), pattern),
		                    		 cb.like(root.get("reasonPurchaseDescription").as(String.class), pattern),
			                            cb.like(root.get("condition").as(String.class), pattern),
			                            cb.like(root.get("materialSerialNumber").as(String.class), pattern),
			                            cb.like(root.get("batchNo").as(String.class), pattern),
			                            cb.like(root.get("qiBatchNo").as(String.class), pattern),
			                            cb.like(root.get("materialCharacteristic").as(String.class), pattern)));
	                }

	                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
	            }
	        }, pageable);
	        System.out.println(page.getContent().size() + " " + " " + pageable.getPageSize());

	        return page;
			}
	
}
