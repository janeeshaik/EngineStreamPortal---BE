package com.ste.enginestreamportal.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ste.enginestreamportal.model.ApplicationMaster;
import com.ste.enginestreamportal.model.Role;
import com.ste.enginestreamportal.repository.ApplicationMasterRepository;
import com.ste.enginestreamportal.repository.RoleRepository;
import com.ste.enginestreamportal.services.RoleService;
import com.ste.enginestreamportal.util.Response;
import com.ste.enginestreamportal.util.Utils;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/Role")
@ResponseBody
public class RoleController extends Utils{

	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	RoleService roleService;

	@Autowired
	ApplicationMasterRepository applicationMasterRepository;
	
	private Logger logger = LogManager.getLogger(RoleController.class);

	@GetMapping("/pagination")
    public Response getAllRolesPagination(@RequestParam("showAll") Integer showAll, @RequestParam("pageIndex") int pageIndex, 
            @RequestParam("sortType") String sortType, @RequestParam("searchWord") String searchWord,@RequestParam("statusType") String statusType) {

 

        Response response = new Response();
        Pageable pageable = null;
        try {
            response.setStatus(true);
            response.setError(null);
            if("asc".equalsIgnoreCase(sortType))
                pageable = PageRequest.of(pageIndex, showAll, Sort.by("roleName").ascending());
            else
                pageable = PageRequest.of(pageIndex, showAll, Sort.by("roleName").descending());
            Page<Role> page = roleService.getAllRolePage(pageable,sortType,searchWord,statusType);
            response.setResponse(page);
        }catch(Exception e) {
            response.setStatus(false);
            response.setError("Unable to get all Roles");
            logger.error("Error : "+e);
            response.setResponse(null);
        }
        return response;
    }

	@GetMapping("/{Id}")
	public Response getAllRolePageByRoleId(@PathVariable(value = "Id") Long id) {

		Response response = new Response();
		response.setStatus(false);
		response.setResponse(null);
		try {
			Optional<Role> role = roleRepository.findById(id);
			response.setResponse(role.get());
			response.setStatus(true);
			response.setError(null);
		}
		catch(NoSuchElementException ex) {
			response.setError("Unable to find Role with id :"+id);
			logger.error("Error : "+ex);
		}
		catch(Exception e) {
			response.setError("Unable to get Role");
			logger.error("Error : "+e);
		}
		return response;
	}


	@PostMapping("/")
	public Response createRole(@Valid @RequestBody Role role) {
		Response response = new Response();
		response.setStatus(false);
		response.setResponse(null);
		try {
			if (null == role) {
				response.setError("The role cannot be empty.");
			}
			else if(isNullOrEmpty(role.getRoleName())) {
				response.setError("RoleName cannot be null or empty");
			}
			else {
				Role createdRole = roleRepository.save(role);
				response.setStatus(true);
				response.setError(null);
				response.setResponse(createdRole);
			}
		} catch (Exception e) {
			response.setError("Could not create role");
			logger.error("Error : "+e);
		}
		return response;
	}

	@PutMapping("/{Id}")
	public Response updateRole(@PathVariable(value = "Id") Long id, @Valid @RequestBody Role role) {

		Response response = new Response();
		response.setStatus(false);
		response.setResponse(null);
		try {
			Optional<Role> optRole = roleRepository.findById(id);
			if (!optRole.isPresent()) {
				response.setError("Could not find a role with ID = " + id);
				return response;
			}else if(isNullOrEmpty(role.getRoleName())) {
				response.setError("RoleName cannot be null or empty");
			}
			else {
				Role roleToUpdate = optRole.get();

				roleToUpdate.setRoleDescription(role.getRoleDescription());
				roleToUpdate.setRoleName(role.getRoleName());
				roleToUpdate.setUpdatedBy(role.getUpdatedBy());
				roleToUpdate.setApplications(role.getApplications());
				roleToUpdate.setStatus(role.getStatus());
				roleRepository.save(roleToUpdate);
				response.setStatus(true);
				response.setError(null);
				response.setResponse(roleToUpdate);
			}
		}catch(Exception e) {
			response.setError("Unable to update Role");
			logger.error("Error : "+e);
		}
		return response;
	}


	@GetMapping("/getAllRoleexcludeSuperAdmin")
	public Response getAllRoleexcludeSuperAdmin() {

		Response response = new Response();
		try {
			List<Role> rolesname = roleRepository.findnotsuperadmin();
			response.setStatus(true);
			response.setError(null);
			response.setResponse(rolesname);
		}catch(Exception e) {
			response.setStatus(false);
			response.setError("Unable to get all Roles");
			logger.error("Error : "+e);
			response.setResponse(null);
		}
		return response;
	}


	@PutMapping("/updateAllRoles")
	public Response updateAllRoles(@Valid @RequestBody List<Role> roles) {

		Response response = new Response();
		response.setStatus(false);
		response.setResponse(null);
		if (null == roles || roles.isEmpty()) {
			response.setError("Please pass valid roles to update.");
			return response;
		}
		List<Role> updatedRoles = new ArrayList<Role>(roles.size());
		List<Long> roleIdsList = new ArrayList<Long>(roles.size());
		roles.forEach(r -> roleIdsList.add(r.getId()));
		List<Role> rolesFromStorage = roleRepository.findAllById(roleIdsList);
		for (Role roleInRestPayload : roles) {
			Role roleInStorage = rolesFromStorage.stream()
					.filter(role -> roleInRestPayload.getId().equals(role.getId())).findFirst().orElse(new Role());
			if (roleInStorage.getId() == 0) {
				logger.error("Could not save role with ID = " + roleInRestPayload.getId());
				continue;
			}
			roleInStorage.setRoleName(roleInRestPayload.getRoleName());
			roleInStorage.setRoleDescription(roleInRestPayload.getRoleDescription());
			roleInStorage.setUpdatedBy(roleInRestPayload.getUpdatedBy());
			roleInStorage.setApplications(roleInRestPayload.getApplications());
			updatedRoles.add(roleInStorage);
		}
		response.setStatus(true);
		response.setError(null);
		response.setResponse(updatedRoles);
		if ((null != updatedRoles) && !updatedRoles.isEmpty()) {
			try {
				updatedRoles = roleRepository.saveAll(updatedRoles);
				response.setResponse(updatedRoles);
			} catch (Exception e) {
				response.setError("Could not Update Role with duplicate Role Name");
				response.setStatus(false);
				response.setResponse(null);
			}
		}
		return response;
	}

	@PutMapping("/{Id}/{Status}")
	public Response updateRoleStatus(@PathVariable(value = "Id") Long Id, @PathVariable(value="Status") Boolean status) {
		Response response = new Response();
		try {
			roleRepository.updateStatusForRole(status, Id);
			response.setStatus(true);
			response.setError(null);
			response.setResponse("Updated Status for the Role");
		} catch(NoSuchElementException ex) {
			response.setStatus(false);
			response.setError("Unable to find Role with id :"+Id);
			logger.error("Error : "+ex);
			response.setResponse(null);
		} catch(Exception e) {
			response.setStatus(false);
			response.setError("Unable to update Role");
			logger.error("Error : "+e);
			response.setResponse(null);
		}
		return response;
	}
	
	@GetMapping("/genarateRolesExcelSheet")
    public void genarateUsersExcelSheet(HttpServletResponse httpResponse,@RequestParam("showAll") Integer showAll, @RequestParam("pageIndex") int pageIndex,
            @RequestParam("sortType") String sortType, @RequestParam("searchWord") String searchWord, @RequestParam("statusType") String statusType) {
        Response response = new Response();
        response.setStatus(false);
        response.setResponse(null);
        try {
            logger.info("showAll "+showAll+" pageIndex "+pageIndex+" sortType "+sortType+" searchWord "+searchWord+" statusType "+statusType );
            roleService.genarateRolesExcelSheet(httpResponse, showAll, pageIndex, sortType, searchWord, statusType);
            response.setResponse("excel sheet successfully genaraated");
            response.setStatus(true);
            response.setError(null);
           
        } catch(Exception e) {
            response.setError("unable to genarate excel sheet");
            logger.error("unable to genarate excel sheet "+e);
        }
        //return response;
    }
	
	@GetMapping("/")
	public Response getAllRoles() {
		Response response = new Response();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			List<Role> rolesList = roleRepository.findAll();
			for(Role role:rolesList) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", role.getId());
				map.put("status", role.getStatus());
				map.put("createdAt", role.getCreatedAt());
				map.put("createdBy", role.getCreatedBy());
				map.put("updatedAt", role.getUpdatedAt());
				map.put("updatedBy", role.getUpdatedBy());
				map.put("roleName", role.getRoleName());
				map.put("roleDescription", role.getRoleDescription());
				map.put("applications", role.getApplications());
				List<ApplicationMaster> applicationsList = new ArrayList<ApplicationMaster>();
				if(!isNullOrEmpty(role.getApplications())) {
					String[] appIds = role.getApplications().split(",");
					for(String appId : appIds) {
						ApplicationMaster applicationMaster = applicationMasterRepository.findById(Long.parseLong(appId)).get();
						applicationsList.add(applicationMaster);
					}
				}
				map.put("applicationsObjects", applicationsList);
				list.add(map);
			}
			response.setStatus(true);
			response.setError(null);
			response.setResponse(list);
		} catch(Exception e) {
			response.setStatus(false);
			response.setError("Unable to get all Roles");
			logger.error("Error : "+e);
			response.setResponse(null);
		}

		return response;
	}
}
