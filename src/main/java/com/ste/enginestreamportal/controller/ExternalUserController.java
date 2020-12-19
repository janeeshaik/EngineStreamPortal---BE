package com.ste.enginestreamportal.controller;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.ste.enginestreamportal.model.ExternalUser;
import com.ste.enginestreamportal.payload.ExternalUserPayload;
import com.ste.enginestreamportal.repository.ExternalUserRepo;
import com.ste.enginestreamportal.repository.UserRepository;
import com.ste.enginestreamportal.services.ExternalUserService;
import com.ste.enginestreamportal.services.MailService;
import com.ste.enginestreamportal.util.PasswordUtils;
import com.ste.enginestreamportal.util.Response;
import com.ste.enginestreamportal.util.Utils;

@RestController
@RequestMapping("/ExternalUser")
@ResponseBody
public class ExternalUserController extends Utils{
	
	private Logger logger = LogManager.getLogger(ExternalUserController.class);
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ExternalUserService externalUserService;
	
	@Autowired
	ExternalUserRepo externalUserRepo;
	
	@Autowired
	MailService mailService;
	
	@PostMapping("/")
	public Response saveExternalUser(@RequestBody ExternalUserPayload userPayLoad) {
		Response response = new Response();
		response.setStatus(false);
		response.setResponse(null);
		try {
			//validate email
			if(!externalUserService.validateEmailAddress(userPayLoad.getEmailAddress())) {
				response.setError("enter a valid emai address");
				return response;
			}
			ExternalUserPayload savedUser = externalUserService.createExternalUser(userPayLoad);
			
			String content = externalUserService.getUserRegistrationMessage(userPayLoad);
			//send mail
			mailService.sendmail(content.toString(), userPayLoad.getEmailAddress(), "Engine Stream Portal Registration");
			response.setStatus(true);
			response.setError(null);
			response.setResponse(savedUser);
			
		} catch(ResponseStatusException re) {
			response.setError(re.getReason());
			logger.error("Error : "+re);
		} catch(Exception e) {
			response.setError("Unable to create External User");
			logger.error("Error : "+e);
			
		}
		return response;
	}
	
	@PutMapping("/")
	public Response updateExternalUserStatus(@RequestBody ExternalUserPayload userPayload) {
		Response response = new Response();
		response.setStatus(false);
		response.setResponse(null);
		try {
			//send mail with username and password genarated and reset password link
			userPayload = externalUserService.updateExternalUserStatus(userPayload);
			response.setError(null);
			response.setStatus(true);
			response.setResponse("Status Updated!");//updated
			/*
			 * Object object = gson.fromJson(gson.toJson(userPayload), Object.class);
			 * response.setResponse(object);
			 */
		} catch(ResponseStatusException re) {
			response.setError(re.getReason());
			logger.error("Error : "+re);
		} catch(Exception e) {
			logger.error(e);
			response.setError(e.toString());
		}
		return response;
	}
	
	@PutMapping("/resetPassword")
	public Response resetExternalUserPassword(@RequestBody ExternalUserPayload userPayload) {
		Response response = new Response();
		response.setStatus(false);
		response.setResponse(null);
		try {
			Optional<ExternalUser> externalUser = externalUserRepo.findById(userPayload.getId());
			if(userPayload.getOldPassword() != null && externalUser.isPresent()) {
				String passwordFromDb = PasswordUtils.decryptAES256((externalUser.get().getPassword()));
				if(userPayload.getOldPassword().equals(passwordFromDb))
					externalUserService.resetExternalUserPassword(userPayload);
				else {
					logger.error("wrong old password was given");
					response.setError("wrong old password was given");
					return response;
				}
			} else {
				logger.error("old password is null or given id is not present in db");
				response.setError("old password is null or given id is not present in db");
				return response;
			}
			
			String content = externalUserService.getsaveForgotPassword(new ExternalUser());
			mailService.sendmail(content.toString(), externalUser.get().getEmailAddress(), "PASSWORD RESET SUCCESSFUL");
			response.setError(null);
			response.setStatus(true);
			response.setResponse("Password reset successful!");
		} catch(ResponseStatusException re) {
			response.setError(re.getReason());
			logger.error("Error : "+re);
		} catch (Exception e) {
			logger.error(e);
			response.setError(e.toString());
		}
		
		return response;
	}
	@PostMapping("/sendForgotPasswordLinkToMail")
	public Response sendForgotPasswordLinkToMail(@RequestBody ExternalUserPayload userPayload) {
		Response response = new Response();
		response.setStatus(false);
		response.setResponse(null);
		try {
			//ExternalUser externalUser = externalUserRepo.findById(userPayload.getId()).get();
			
			ExternalUser externalUser = externalUserRepo.findByUserIddetails(userPayload.getUserid()).get();
			
			String contant = externalUserService.getForgotPasswordMessage(externalUser);
			mailService.sendmail(contant, externalUser.getEmailAddress(), "FORGOT PASSWORD");
			response.setStatus(true);
			response.setError(null);
			response.setResponse("A mail has been sent to the registered mail.");
		} catch(NoSuchElementException e) {
			response.setError("Could not find any user with userId : "+userPayload.getUserid());
			logger.error(e);
		} catch(ResponseStatusException re) {
			response.setError(re.getReason());
			logger.error("Error : "+re);
		} catch(Exception e) {
			logger.error(e);
			response.setError(e.toString());
		}
		return response;
		
	}
	@PostMapping("/saveForgotPassword")
	public Response saveForgotPassword(@RequestBody ExternalUserPayload userPayload) {
		Response response = new Response();
		response.setStatus(false);
		response.setResponse(null);
		try {
			if(userPayload.getPassword() == null || userPayload.getConfirmPassword() == null || !userPayload.getPassword().equals(userPayload.getConfirmPassword())) {
				response.setError("password and confirm password did not matched");
				return response;
			}
			if(userPayload.getUserid() == null || !userRepository.findByUserIddetails(userPayload.getUserid()).isPresent()) {
				response.setError("no records with given UserId "+userPayload.getUserid());
				return response;
			}
			//validate given passsword
			boolean isValid = PasswordUtils.passwordFormatCheck(userPayload.getPassword());
			if(!isValid) {
				response.setError("Password should consist of at least 8 characters which should include upper and lower case characters and numbers.");
				return response;
			}
			ExternalUser externalUser = externalUserRepo.findByUserIddetails(userPayload.getUserid()).get();
            userPayload.setId(externalUser.getId());
            userPayload.setUpdatedBy(externalUser.getId().toString());
			externalUserService.updateUser(userPayload, false); //added boolean for password updating
			String contant = externalUserService.getsaveForgotPassword(externalUser);
			mailService.sendmail(contant, externalUser.getEmailAddress(), "FORGOT PASSWORD CHANGE SUCCESSFUL");
			response.setStatus(true);
			response.setError(null);
			response.setResponse("Successfully Updated your password!");
		} catch(NoSuchElementException e) {
			response.setError("Could not find any user with userId : "+userPayload.getUserid());
			logger.error(e);
		} catch(ResponseStatusException re) {
			response.setError(re.getReason());
			logger.error("Error : "+re);
		} catch(Exception e) {
			logger.error(e);
			response.setError(e.toString());
		}
		return response;
		
	}

}