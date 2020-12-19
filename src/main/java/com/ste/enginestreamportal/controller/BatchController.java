package com.ste.enginestreamportal.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ste.enginestreamportal.model.Batch;
import com.ste.enginestreamportal.model.SurplusFlagHistory;
import com.ste.enginestreamportal.payload.BatchPayLoad;
import com.ste.enginestreamportal.repository.BatchRepository;
import com.ste.enginestreamportal.repository.SurplusFlagHistoryRepository;
import com.ste.enginestreamportal.services.BatchService;
import com.ste.enginestreamportal.util.Response;
import com.ste.enginestreamportal.util.Utils;

@RestController
@ResponseBody
@RequestMapping("/Batch")
public class BatchController extends Utils{

	@Autowired
	BatchRepository batchRepository;
	
	@Autowired
	BatchService batchService;
	
	@Autowired
	SurplusFlagHistoryRepository surplusFlagHistoryRepository;
	
	private Logger logger = LogManager.getLogger(BatchController.class);

	@GetMapping("/")
    public Response getAllBatches(@RequestParam("showAll") Integer showAll, @RequestParam("pageIndex") int pageIndex,
            @RequestParam("sortType") String sortType, @RequestParam("searchWord") String searchWord,@RequestParam("statusType") String statusType) {
       
        Response response = new Response();
        Pageable pageable = null;
        Page<Batch> page = null;
        try {
            response.setStatus(true);
            response.setError(null);
            if(sortType != null && sortType.equalsIgnoreCase("asc")) {
                pageable = PageRequest.of(pageIndex, showAll, Sort.by("batchNo").ascending());
            } else {
                pageable = PageRequest.of(pageIndex, showAll, Sort.by("batchNo").descending());
            }
            page = batchService.findAllBatches(pageable,sortType,searchWord,statusType);
            response.setResponse(page);
        } catch(Exception e) {
            response.setStatus(false);
            response.setError("Unable to get all Batches");
            logger.error("Error : "+e);
            response.setResponse(null);
        }
        return response;
    }
	
	@PutMapping("/flag")
	public Response changeSurplusFlag(@RequestBody String payload) {
		
		Response response = new Response();
		response.setStatus(false);
		response.setResponse(null);
		try {
			JSONObject json = new JSONObject(payload);
			Long batchId = json.getLong("id");
		    Optional<Batch> optBatch = batchRepository.findById(batchId);
		    if(!optBatch.isPresent()) {
		    	response.setError("Invalid id");
		    }
		    else {
		    Batch batch = optBatch.get();
			batch.setSurplusFlag(!batch.isSurplusFlag());
		    batchRepository.save(batch);
		    SurplusFlagHistory surplusFlagHistory= new SurplusFlagHistory();
		    surplusFlagHistory.setBatch(batch);
		    surplusFlagHistory.setFlagUpdated(batch.isSurplusFlag());
		    surplusFlagHistory.setReason(json.getString("reason"));
		    surplusFlagHistory.setUpdatedAt(new Date(System.currentTimeMillis()));
		    surplusFlagHistory.setUpdatedBy(json.getLong("employeeId"));
		    surplusFlagHistoryRepository.save(surplusFlagHistory);
		    response.setStatus(true);
			response.setError(null);
			response.setResponse(batch);
		    }
		} catch(Exception e) {
				response.setError("Unable to change Surplus Flag");
				logger.error("Error : "+e);
			}
			return response;
	}
	
	/*
	 * @GetMapping("/getEnabledSurplusFlagBatchRecordss")
	 * public List<Map<String, Object>>
	 * getAllBatchDetailsWithMaterialName(@RequestParam("pageSize") int
	 * pageSize, @RequestParam("pageIndex") int pageIndex) {
	 * System.out.println("getAllBatchDetailsWithMaterialName is called");
	 * 
	 * List<Map<String, Object>> mapList = new ArrayList<Map<String,Object>>();
	 * Pageable pageable = PageRequest.of(pageIndex, pageSize);
	 * List<Batch> batches =
	 * batchService.getEnabledSurplusFlagBatchRecords(pageable,false);
	 * 
	 * for(Batch batch : batches) {
	 * Map<String,Object> map = new LinkedHashMap<String, Object>();
	 * 
	 * map.put("id", batch.getId());
	 * map.put("bizUnit", batch.getBizUnit());
	 * map.put("bizUnitDescription",batch.getBizUnitDescription());
	 * map.put("batchNo", batch.getBatchNo());
	 * map.put("qiBatchNo", batch.getQiBatchNo());
	 * map.put("storageLocation", batch.getStorageLocation());
	 * map.put("storageBin", batch.getStorageBin());
	 * map.put("lastReceiptDate", batch.getLastReceiptDate());
	 * map.put("ageByDay", batch.getAgeByDay());
	 * map.put("ageByMonth", batch.getAgeByMonth());
	 * map.put("quantity", batch.getQuantity());
	 * map.put("uom", batch.getUom());
	 * map.put("vendorName", batch.getVendorName());
	 * map.put("reasonPurchaseDescription",batch.getReasonPurchaseDescription());
	 * map.put("valueInUSD", batch.getValueInUSD());
	 * map.put("nbvInUSD", batch.getNbvInUSD());
	 * map.put("totalNBVUSD", batch.getTotalNBVUSD());
	 * map.put("tsn", batch.getTsn());
	 * map.put("csn", batch.getCsn());
	 * map.put("condition", batch.getCondition());
	 * map.put("materialSerialNumber", batch.getMaterialSerialNumber());
	 * map.put("materialCharacteristic", batch.getMaterialCharacteristic());
	 * map.put("surplusFlag", batch.isSurplusFlag());
	 * map.put("materialDescription", batch.getMaterial() != null ?
	 * batch.getMaterial().getMaterialDescription() : null);
	 * 
	 * mapList.add(map);
	 * }
	 * return mapList;
	 * }
	 */
	@GetMapping("/getEnabledSurplusFlagBatchRecords")
	public Response getEnabledSurplusFlagBatchRecords(@RequestParam("pageSize") int pageSize, @RequestParam("pageIndex") int pageIndex) throws Exception {
		Response response = new Response();
		response.setStatus(false);
		response.setResponse(null);
		try {
		List<Batch> batches = null;
		List<BatchPayLoad> batchPayloads = new ArrayList<BatchPayLoad>();
		BatchPayLoad batchPayload;
		Pageable pageable = PageRequest.of(pageIndex, pageSize);
		batches = batchService.getEnabledSurplusFlagBatchRecords(pageable,false);
		for(Batch b : batches) {
			batchPayload = new BatchPayLoad();
			BeanUtils.copyProperties(batchPayload, b);
			
			if(b.getMaterial() != null && b.getMaterial().getMaterialDescription() != null)
				batchPayload.setMaterialDescription((b.getMaterial().getMaterialDescription()));
			
			batchPayloads.add(batchPayload);
		}
		response.setStatus(true);
		response.setError(null);
		response.setResponse(batchPayloads);
		}catch(Exception e) {
			response.setError("Unable to get Shipment");
			logger.error("Error : "+e);
		}
		return response;
		
	}
	

	@PutMapping("/{Id}/{Status}")
	public Response updateBatchStatus(@PathVariable(value = "Id") Long Id, @PathVariable(value="Status") Boolean status) {
		Response response = new Response();
		try {
			Batch batch = batchRepository.findById(Id).get();
			batch.setStatus(status);
			batchRepository.save(batch);
			response.setStatus(true);
			response.setError(null);
			response.setResponse(batch);
		} catch(NoSuchElementException ex) {
			response.setStatus(false);
			response.setError("Unable to find batch with id :"+Id);
			logger.error("Error : "+ex);
			response.setResponse(null);
		} catch(Exception e) {
			response.setStatus(false);
			response.setError("Unable to update batch");
			logger.error("Error : "+e);
			response.setResponse(null);
		}
		return response;
	}
}
