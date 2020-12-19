package com.ste.enginestreamportal.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ste.enginestreamportal.model.UserLog;
import com.ste.enginestreamportal.repository.UserLogRepository;
import com.ste.enginestreamportal.util.Response;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/UserLog")
@ResponseBody
public class UserLogController {
	
	@Autowired
	UserLogRepository userLogRepository;
	
	private Logger logger = LogManager.getLogger(UserLogController.class);
	
	@GetMapping("/")
	public Response getAllUserLogs() {
		
		Response response = new Response();
		try {
			response.setStatus(true);
			response.setError(null);
			List<UserLog> list = userLogRepository.findAll();
			response.setResponse(list);
		} catch(Exception e){
			response.setStatus(false);
			response.setError("Unable to get all user logs");
			logger.error("Error : "+e);
			response.setResponse(null);
		}
		return response;
	}

	@GetMapping("/(Id}")
	public Response getUserLogsById(@PathVariable(value = "Id") Long Id) {
		Response response = new Response();
		try {
			List<UserLog> list = null;
			list = userLogRepository.findUserLogsByUserId(Id);
			response.setStatus(true);
			response.setError(null);
			response.setResponse(list);
		}catch(Exception e) {
			response.setStatus(false);
			response.setError("Unable to get user logs");
			logger.error("Error : "+e);
			response.setResponse(null);
		}
		return response;
	}
}
