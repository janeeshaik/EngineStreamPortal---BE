package com.ste.enginestreamportal.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ste.enginestreamportal.model.Department;
import com.ste.enginestreamportal.payload.DepartmentPayload;
import com.ste.enginestreamportal.repository.DepartmentRepository;
import com.ste.enginestreamportal.repository.UserRepository;
import com.ste.enginestreamportal.services.DepartmentService;
import com.ste.enginestreamportal.util.Response;
import com.ste.enginestreamportal.util.Utils;

@RestController
@ResponseBody
@RequestMapping("/Department")
public class DepartmentController extends Utils{

	@Autowired
	DepartmentRepository departmentRepository;
	
	@Autowired
	UserRepository userRepository;

	@Autowired
	DepartmentService departmentService;

	private Logger logger = LogManager.getLogger(DepartmentController.class);

	@GetMapping("/pagination")
	public Response getAllDepartmentsPagination(@RequestParam("showAll") Integer showAll, @RequestParam("pageIndex") int pageIndex,
			@RequestParam("sortType") String sortType, @RequestParam("searchWord") String searchWord,@RequestParam("statusType") String statusType) {

		Response response = new Response();
		Pageable pageable = null;
		Page<Department> page = null;
		try {
			response.setStatus(true);
			response.setError(null);
			if(sortType != null && sortType.equalsIgnoreCase("asc")) {
				pageable = PageRequest.of(pageIndex, showAll, Sort.by("departmentName").ascending());
			} else {
				pageable = PageRequest.of(pageIndex, showAll, Sort.by("departmentName").descending());
			}
			page = departmentService.findAllDepartment(pageable,sortType,searchWord,statusType);

			response.setResponse(page);
		} catch(Exception e) {
			response.setStatus(false);
			response.setError("Unable to get all departments");
			logger.error("Error : "+e);
			response.setResponse(null);
		}
		return response;
	}

	@GetMapping("/{Id}")
	public Response getDepartmentById(@PathVariable(value = "Id") Long id) {

		Response response = new Response();
		response.setStatus(false);
		response.setResponse(null);
		try {
			Department department = departmentRepository.findById(id).get();
			response.setStatus(true);
			response.setError(null);
			response.setResponse(department);
		}
		catch(NoSuchElementException ex) {
			response.setError("Unable to find Department with id :"+id);
			logger.error("Error : "+ex);
		}
		catch(Exception e) {
			response.setError("Unable to get Department");
			logger.error("Error : "+e);
		}
		return response;
	}

	@PostMapping("/")
	public Response createDepartment(@Valid @RequestBody DepartmentPayload departmentPayload) {

		Response response = new Response();
		response.setStatus(false);
		response.setResponse(null);
		try {
			if(null == departmentPayload) {
				response.setError("Payload cannot be null or empty");
			}else if(isNullOrEmpty(departmentPayload.getDepartmentName())) {
				response.setError("Name cannot be null or empty");
			}
			else {
				Department department = new Department();
				department.setDepartmentName(departmentPayload.getDepartmentName());
				department.setCreatedBy(departmentPayload.getCreatedBy());
				department.setUpdatedBy(departmentPayload.getCreatedBy());
				department = departmentRepository.save(department);
				response.setStatus(true);
				response.setError(null);
				response.setResponse(department);
			}
		} catch (Exception e) {
			response.setError("Could not create Department");
			logger.error("Error : "+e);
		}
		return response;
	}

	@PutMapping("/")
	public Response updateDepartment(@Valid @RequestBody DepartmentPayload departmentPayload) {

		Response response = new Response();
		response.setStatus(false);
		response.setResponse(null);
		try {
			if(null == departmentPayload) {
				response.setError("Payload cannot be null or empty");
			}else if(isNullOrEmpty(departmentPayload.getDepartmentName())) {
				response.setError("Name cannot be null or empty");
			}
			else if(isZeroOrNull(departmentPayload.getId())) {
				response.setError("Id cannot zero or null");
			}
			else {
				Department department = null;
				department = departmentRepository.findById(departmentPayload.getId()).get();
				department.setDepartmentName(departmentPayload.getDepartmentName());
				department.setUpdatedBy(departmentPayload.getUpdatedBy());
				department.setStatus(departmentPayload.getStatus());
				departmentRepository.save(department);
				response.setStatus(true);
				response.setError(null);
				response.setResponse(department);
			}
		} catch(Exception ex) {
			response.setError("Please pass valid Department data to update.");
			logger.error("Error : "+ex);
		}
		return response;
	}

	@PutMapping("/{Id}/{Status}")
	public Response updateDepartmentStatus(@PathVariable(value = "Id") Long Id, @PathVariable(value="Status") Boolean status) {
		Response response = new Response();
		try {
			Department department = departmentRepository.findById(Id).get();
			department.setStatus(status);
			departmentRepository.save(department);
			response.setStatus(true);
			response.setError(null);
			response.setResponse(department);
		} catch(NoSuchElementException ex) {
			response.setStatus(false);
			response.setError("Unable to find department with id :"+Id);
			logger.error("Error : "+ex);
			response.setResponse(null);
		} catch(Exception e) {
			response.setStatus(false);
			response.setError("Unable to update department");
			logger.error("Error : "+e);
			response.setResponse(null);
		}
		return response;
	}

	@GetMapping("/")
	public Response getAllDepartments() {
		Response response = new Response();
		try {
			response.setStatus(true);
			response.setError(null);
			response.setResponse(departmentRepository.findAll());
		} catch(Exception e) {
			response.setStatus(false);
			response.setError("Unable to get all Departments");
			logger.error("Error : "+e);
			response.setResponse(null);
		}

		return response;
	}

	@GetMapping("/usersList")
	public Response getAllDepartmentsAndUsers() {
		Response response = new Response();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			List<Department> departmentsList = departmentRepository.findAll();
			for(Department department : departmentsList) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", department.getId());
				map.put("status", department.getStatus());
				map.put("createdAt", department.getCreatedAt());
				map.put("createdBy", department.getCreatedBy());
				map.put("updatedAt", department.getUpdatedAt());
				map.put("updatedBy", department.getUpdatedBy());
				map.put("departmentName", department.getDepartmentName());
				map.put("usersList", userRepository.findbydeptid(department.getId()));
                list.add(map);
			}
			response.setStatus(true);
			response.setError(null);
			response.setResponse(list);
			} catch(Exception e) {
				response.setStatus(false);
				response.setError("Unable to get all Departments and usersList");
				logger.error("Error : "+e);
				response.setResponse(null);
			}
		return response;
	}
}
