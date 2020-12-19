package com.ste.enginestreamportal.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ste.enginestreamportal.model.Material;
import com.ste.enginestreamportal.repository.BatchRepository;
import com.ste.enginestreamportal.repository.MaterialRepository;
import com.ste.enginestreamportal.services.BatchService;
import com.ste.enginestreamportal.services.MailService;
import com.ste.enginestreamportal.services.MaterialService;
import com.ste.enginestreamportal.util.Response;

@RestController
@ResponseBody
@RequestMapping("/Material")
public class MaterialController {

	@Autowired
	MaterialRepository materialRepository;
	
	@Autowired
	BatchRepository batchRepository;

	@Autowired
	MailService mailService;

	@Autowired
	MaterialService materialService;

	@Autowired
	BatchService batchService;
	
	private Logger logger = LogManager.getLogger(MaterialController.class);

	@GetMapping("/")
    public Response getAllMaterials(@RequestParam("showAll") Integer showAll, @RequestParam("pageIndex") int pageIndex,
            @RequestParam("sortType") String sortType, @RequestParam("searchWord") String searchWord,@RequestParam("statusType") String statusType) {
       
        Response response = new Response();
        Pageable pageable = null;
        Page<Material> page = null;
        try {
            response.setStatus(true);
            response.setError(null);
            if(sortType != null && sortType.equalsIgnoreCase("asc")) {
                pageable = PageRequest.of(pageIndex, showAll, Sort.by("materialNumber").ascending());
            } else {
                pageable = PageRequest.of(pageIndex, showAll, Sort.by("materialNumber").descending());
            }
            page = materialService.findAllMaterial(pageable,sortType,searchWord,statusType);
            //List<User> usersList = userRepository.findAll();
            /*for(User user:usersList) {
                user.setPassword(PasswordUtils.generatedecryotPassword(user.getPassword()));
            }*/
            response.setResponse(page);
        } catch(Exception e) {
            response.setStatus(false);
            response.setError("Unable to get all Materials");
            logger.error("Error : "+e);
            response.setResponse(null);
        }
        return response;
    }
	
	@GetMapping("/plantandengine")
	public Response getAllMaterialDetailsWithEngineAndPlant() {
		
		Response response = new Response();
		try {
		List<Material> materialList = materialRepository.findAll();
		List<Map<String, Object>> materials = new ArrayList<Map<String,  Object>>();
		for(Material material:materialList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", material.getId());
			map.put("materialNumber", material.getMaterialNumber());
			map.put("materialDescription", material.getMaterialDescription());
			map.put("lastPOUnitPrice", material.getLastPOUnitPrice());
			map.put("last1stYearIssueQuantity", material.getLast1stYearIssueQuantity());
			map.put("last2ndYearIssueQuantity", material.getLast2ndYearIssueQuantity());
			map.put("last3rdYearIssueQuantity", material.getLast3rdYearIssueQuantity());
			map.put("batches", material.getBatches());
			map.put("plantName", material.getPlant().getPlantName());
			map.put("plantCode", material.getPlant().getPlantCode());
			map.put("engineType", material.getEngine().getEngineType());
            map.put("engineTypeDescription", material.getEngine().getEngineTypeDescription());
            materials.add(map);
            response.setStatus(true);
			response.setError(null);
			response.setResponse(materials);
		}
		}catch(Exception e) {
			response.setStatus(false);
			response.setError("Unable to get all materials");
			logger.error("Error : "+e);
			response.setResponse(null);
		}
		return response;
	}
	
	@GetMapping(path = "/convertMaterialToExcel")
	public void convertMaterialToExcel(HttpServletResponse response) throws IOException {
		Response r = new Response();
        try {
    		materialService.convertMaterialToExcel(response);  
    		r.setStatus(true);
			r.setError(null);
			r.setResponse("Successfully downloaded excel");
        } catch(Exception e) {
			r.setStatus(false);
			r.setError("Unable to download excel");
			logger.error("Error : "+e);
			r.setResponse(null);
		}
        //return r;
	}
	
	@GetMapping(value = "/sendemail/{id}")
	public Response sendEmail(@PathVariable Long id) throws Exception {
		Response response = new Response();
		response.setStatus(false);
		response.setResponse(null);
		try {
		Material material = this.getMaterialByBatchId(id);
		if	(material == null) {
			response.setError("material with batch id "+ id+" is not present in table");
			return response;
		}
		mailService.sendmail(material);
		response.setStatus(true);
		response.setError(null);
		response.setResponse("Email sent successfully");
		} catch(NoSuchElementException ex) {
			response.setError("Unable to sent mail with id :"+id);
			logger.error("Error : "+ex);
		} catch(Exception e) {
			response.setError("Unable to sent mail");
			logger.error("Error : "+e);
		}
		return response;
	}

	public Material getMaterialByBatchId(@PathVariable Long batchId) {
		return batchService.getMaterialByBatchIdUsingQuery(batchId);
	}
	@GetMapping("/getMaterialByBatchId/{batchId}")
	public Response getMaterialByBatchId2(@PathVariable Long batchId) {
		Response response = new Response();
		response.setStatus(false);
		response.setResponse(null);
		try {
			Material material =  batchService.getMaterialByBatchIdUsingQuery(batchId);
			response.setStatus(true);
			response.setError(null);
			response.setResponse(material);
		}
		catch(Exception e) {
			response.setError("Unable to get MaterialByBatchId");
			logger.error("Error : "+e);
		}
		return response;
	}
	@GetMapping("/getMaterialsByPagingId/{pageId}")
	public Response getMaterialsByPagingId(@PathVariable int pageId) {
		
		Response response = new Response();
		response.setStatus(false);
		response.setResponse(null);
		try {
			Pageable page = PageRequest.of(pageId, materialRepository.PAGE_SIZE);
		Page<Material> material =  materialRepository.findAll(page);
		response.setStatus(true);
		response.setError(null);
		response.setResponse(material);
		}catch(Exception e) {
			response.setError("Unable to get materialByPageId");
			logger.error("Error : "+e);
		}
		return response;
	}
	
	@GetMapping("/searchMaterial/")
	public Response searchMaterial(@RequestParam("data") String data, @RequestParam("pageSize") int pageSize, @RequestParam("pageIndex") int pageIndex) {
		Response response = new Response();
		response.setStatus(false);
		response.setResponse(null);
		try {
			System.out.println(data);
		Pageable pageable = PageRequest.of(pageIndex, pageSize);
		String pattern = "%"+data+"%";
		List<Material> materials = materialService.searchMaterial(pattern, pageable,false);
		response.setStatus(true);
		response.setError(null);
		response.setResponse(materials);
		}catch(Exception e) {
			response.setError("Unable to get materialByPageId");
			logger.error("Error : "+e);
		}
		return response;
	}
	
	@PutMapping("/{Id}/{Status}")
	public Response updateMaterialStatus(@PathVariable(value = "Id") Long Id, @PathVariable(value="Status") Boolean status) {
		Response response = new Response();
		try {
			Material material = materialRepository.findById(Id).get();
			material.setStatus(status);
			materialRepository.save(material);
			response.setStatus(true);
			response.setError(null);
			response.setResponse(material);
		} catch(NoSuchElementException ex) {
			response.setStatus(false);
			response.setError("Unable to find material with id :"+Id);
			logger.error("Error : "+ex);
			response.setResponse(null);
		} catch(Exception e) {
			response.setStatus(false);
			response.setError("Unable to update material");
			logger.error("Error : "+e);
			response.setResponse(null);
		}
		return response;
	}
}
