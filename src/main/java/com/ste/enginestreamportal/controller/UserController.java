package com.ste.enginestreamportal.controller;

import java.util.List;
import java.util.NoSuchElementException;

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
import org.springframework.web.server.ResponseStatusException;

import com.ste.enginestreamportal.exceptions.ResourceNotFoundException;
import com.ste.enginestreamportal.model.RolePageMapping;
import com.ste.enginestreamportal.model.User;
import com.ste.enginestreamportal.payload.UserPayload;
import com.ste.enginestreamportal.repository.UserRepository;
import com.ste.enginestreamportal.services.UserService;
import com.ste.enginestreamportal.util.PasswordUtils;
import com.ste.enginestreamportal.util.Response;
import com.ste.enginestreamportal.util.Utils;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/User")
@ResponseBody
public class UserController extends Utils{

	@Autowired
	UserRepository userRepository;

	@Autowired
	private UserService userService;

	private Logger logger = LogManager.getLogger(UserController.class);

	@GetMapping("/")
    public Response getAllUsers(@RequestParam("showAll") Integer showAll, @RequestParam("pageIndex") int pageIndex,
            @RequestParam("sortType") String sortType, @RequestParam("searchWord") String searchWord,@RequestParam("statusType") String statusType) {
       
        Response response = new Response();
        Pageable pageable = null;
        Page<User> page = null;
        try {
            response.setStatus(true);
            response.setError(null);
            if(sortType != null && sortType.equalsIgnoreCase("asc")) {
                pageable = PageRequest.of(pageIndex, showAll, Sort.by("name").ascending());
            } else {
                pageable = PageRequest.of(pageIndex, showAll, Sort.by("name").descending());
            }
            page = userService.findAllUsers(pageable,sortType,searchWord,statusType);
            //List<User> usersList = userRepository.findAll();
            /*for(User user:usersList) {
                user.setPassword(PasswordUtils.generatedecryotPassword(user.getPassword()));
            }*/
            response.setResponse(page);
        } catch(Exception e) {
            response.setStatus(false);
            response.setError("Unable to get all users");
            logger.error("Error : "+e);
            response.setResponse(null);
        }
        return response;
    }

	@GetMapping("/{Id}")
	public Response getAllUsersById(@PathVariable(value = "Id") Long Id) {
		Response response = new Response();
		try {
			User usernames = userRepository.findById(Id).get();
			//usernames.setPassword(PasswordUtils.generatedecryotPassword(usernames.getPassword()));
			response.setStatus(true);
			response.setError(null);
			response.setResponse(usernames);
		} catch(NoSuchElementException ex) {
			response.setStatus(false);
			response.setError("Unable to find user with id :"+Id);
			logger.error("Error : "+ex);
			response.setResponse(null);
		} catch(Exception e) {
			response.setStatus(false);
			response.setError("Unable to get user");
			logger.error("Error : "+e);
			response.setResponse(null);
		}
		return response;
	}

	@GetMapping("/getAllUsersByIdParent")
	public Response getAllUsersByIdParent(@RequestParam(value = "Id") Long Id) {
		Response response = new Response();
		try {
			response.setStatus(true);
			response.setError(null);
			response.setResponse(userRepository.findByIdParent(Id));
		} catch(Exception e) {
			response.setStatus(false);
			response.setError("Unable to get all users");
			logger.error("Error : "+e);
			response.setResponse(null);
		}
		return response;
	}


	@GetMapping("/getUserWithRolePage")
	public Response getUserWithRolePage(@RequestParam(value = "Id") Long id,
			@RequestParam(value = "PageId") Long pageId) {
		Response response = new Response();
		try {
			response.setStatus(true);
			response.setError(null);
		List<RolePageMapping> usersRolePageMapping = userRepository.findUsersForRole(id, pageId);
		if (null == usersRolePageMapping) {
			throw new ResourceNotFoundException("User", "Id", id);
		}
		for (RolePageMapping rolePageMapping : usersRolePageMapping) {
			if (rolePageMapping.getPageIdAsLong() == pageId) {
				response.setResponse(rolePageMapping);
				return response;
			}

		}
		response.setResponse(usersRolePageMapping.get(0));
		}catch(Exception e) {
			response.setStatus(false);
			response.setError("Unable to get all users");
			logger.error("Error : "+e);
			response.setResponse(null);
		}
		return response;
	}


	@PostMapping("/")
	public Response createUSer(@Valid @RequestBody UserPayload userPayload) {

		Response response = new Response();
		response.setStatus(false);
		response.setResponse(null);
		try {
			if(null == userPayload) {
				response.setError("Payload cannot be null or empty");
			}
		else if(userPayload.getRoleId()==null) {			
			response.setError("Role Id cannot be null");
			}
		else if(isNullOrEmpty(userPayload.getEmailAddress())) {
			response.setError("EmailAddress cannot be null or empty");
		}
		else if(emailAddressNotMatchesFormat(userPayload.getEmailAddress())) {
			response.setError("EmailAddress is not in correct format");
		}
		else if(isNullOrEmpty(userPayload.getUserid())) {
			response.setError("Userid cannot be null or empty");
		}
		else if(isNullOrEmpty(userPayload.getName())) {
			response.setError("Name cannot be null or empty");
		}
		else {
			UserPayload savedUser = userService.createUser(userPayload);
			response.setStatus(true);
			response.setError(null);
			response.setResponse(savedUser);
		}
		
		} catch(ResponseStatusException re) {
			response.setError(re.getReason());
			logger.error("Error : "+re);
		} catch(Exception e) {
			response.setError("Unable to create User");
			logger.error("Error : "+e);
		}
		return response;
	}

	@PutMapping("/")
	public Response updateUser(@Valid @RequestBody UserPayload userPayloadList) {
		
		Response response = new Response();
		response.setStatus(false);
		response.setResponse(null);
		try {
		if (null == userPayloadList) {
			response.setError("Payload cannot be empty or null");
		}
		else if(userPayloadList.getId() == null) {
			response.setError("Id cannot be null or empty");
		}
		else if(userPayloadList.getRoleId()==null) {			
			response.setError("Role Id cannot be null");
			}
		else if(isNullOrEmpty(userPayloadList.getEmailAddress())) {
			response.setError("EmailAddress cannot be null or empty");
		}
		else if(emailAddressNotMatchesFormat(userPayloadList.getEmailAddress())) {
			response.setError("EmailAddress is not in correct format");
		}
		else if(isNullOrEmpty(userPayloadList.getUserid())) {
			response.setError("Userid cannot be null or empty");
		}
		else if(isNullOrEmpty(userPayloadList.getName())) {
			response.setError("Name cannot be null or empty");
		}
		else {
		UserPayload updatedPayload = userService.updateUser(userPayloadList);
		response.setStatus(true);
		response.setError(null);
		response.setResponse(updatedPayload);
		}
		} catch(ResponseStatusException re) {
			response.setError(re.getReason());
			logger.error("Error : "+re);
		} catch(Exception e) {
			response.setError("Unable to update User");
			logger.error("Error : "+e);
		}
		return response;
	}

	/*@GetMapping("/activeUsers")
    public Response getAllActiveUsers(@RequestParam("showAll") Integer showAll, @RequestParam("pageIndex") int pageIndex,
            @RequestParam("sortType") String sortType, @RequestParam("searchWord") String searchWord) {
       
        Response response = new Response();
        response.setStatus(false);
        response.setResponse(null);
        Pageable pageable = null;
        try {
            if(sortType != null && sortType.equalsIgnoreCase("asc")) {
                pageable = PageRequest.of(pageIndex, showAll, Sort.by("name").ascending());
            } else {
                pageable = PageRequest.of(pageIndex, showAll, Sort.by("name").descending());
            }
            List<User> list = userService.getAllActiveUsers(pageable,sortType,searchWord);
            //List<User> list = userRepository.getAllActiveUsers();
            if(list.isEmpty()) {
                response.setError("Could not find any active users");
            }
            else {
                response.setStatus(true);
                response.setError(null);
                response.setResponse(list);
            }
        } catch(Exception e) {
            response.setError("Could not get active users");
            logger.error("Error : "+e);
        }
        return response;
    }*/
	
	@PutMapping("/{Id}/{Status}")
	public Response updateUserStatus(@PathVariable(value = "Id") Long Id, @PathVariable(value="Status") Boolean status) {
		Response response = new Response();
		try {
			User user = userRepository.findById(Id).get();
			user.setStatus(status);
			userRepository.save(user);
			response.setStatus(true);
			response.setError(null);
			response.setResponse(user);
		} catch(NoSuchElementException ex) {
			response.setStatus(false);
			response.setError("Unable to find user with id :"+Id);
			logger.error("Error : "+ex);
			response.setResponse(null);
		} catch(Exception e) {
			response.setStatus(false);
			response.setError("Unable to update user");
			logger.error("Error : "+e);
			response.setResponse(null);
		}
		return response;
	}

	@PutMapping("/updateInvalidAttemptsCount/{Id}")
	public Response updateInvalidAttemptsCount(@PathVariable(value = "Id") Long Id) {
		Response response = new Response();
		response.setStatus(false);
		response.setResponse(null);
		try {
			User user = userRepository.findById(Id).get();
			user.setInvalidAttemptsCount(0);
			userRepository.save(user);
			response.setStatus(true);
			response.setError(null);
			response.setResponse(user);
		} catch(NoSuchElementException ex) {
			response.setError("Unable to find user with id :"+Id);
			logger.error("Error : "+ex);
		} catch(Exception e) {
			response.setError("Unable to update InvalidAttemptsCount for the user");
			logger.error("Error : "+e);
		}
		return response;
	}
	
	@GetMapping("/genarateUsersExcelSheet")
    public void genarateUsersExcelSheet(HttpServletResponse httpResponse,@RequestParam("showAll") Integer showAll, @RequestParam("pageIndex") int pageIndex,
            @RequestParam("sortType") String sortType, @RequestParam("searchWord") String searchWord, @RequestParam("statusType") String statusType) {
        Response response = new Response();
        response.setStatus(false);
        response.setResponse(null);
        try {
            logger.info("showAll "+showAll+" pageIndex "+pageIndex+" sortType "+sortType+" searchWord "+searchWord+" statusType "+statusType );
            userService.genarateUsersExcelSheet(httpResponse, showAll, pageIndex, sortType, searchWord, statusType);
            response.setResponse("excel sheet successfully genaraated");
            logger.info("excel sheet successfully generated");
            response.setStatus(true);
            response.setError(null);
           
        } catch(Exception e) {
            response.setError("unable to genarate excel sheet");
            logger.error("unable to genarate excel sheet");
        }
        //return response;
    }
	
	@GetMapping("/decryptPassword/{Id}")
	public String decryptUserPassword(@PathVariable(value = "Id") Long Id) {
		String decryptedPassword = "";
		try {
		User user = userRepository.findById(Id).get();
		decryptedPassword = PasswordUtils.decryptAES256(user.getPassword());
		} catch(Exception ex) {
			logger.error("decrypt Password error "+ex);
		}
		return decryptedPassword;
	}
}
 