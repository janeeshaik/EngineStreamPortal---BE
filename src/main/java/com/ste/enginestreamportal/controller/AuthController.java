package com.ste.enginestreamportal.controller;

import java.sql.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.ste.enginestreamportal.exceptions.AuthFailException;
import com.ste.enginestreamportal.model.Login;
import com.ste.enginestreamportal.model.User;
import com.ste.enginestreamportal.model.UserLog;
import com.ste.enginestreamportal.repository.UserLogRepository;
import com.ste.enginestreamportal.repository.UserRepository;
import com.ste.enginestreamportal.util.PasswordUtils;
import com.ste.enginestreamportal.util.Utils;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/auth")
public class AuthController extends Utils{

	@Autowired
	UserRepository userRepository;

	@Autowired
	UserLogRepository userLogRepository;
	
	@PostMapping("/login")
	public User validateUser(@Valid @RequestBody Login user) {
		String userName = user.getUserName();
		String providedPassword = user.getPassword();
        if(isNullOrEmpty(providedPassword)) {
        	throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Password cannot be empty");
        }
		User tmp_user = userRepository.findByUserIddetails(userName)
				.orElseThrow(() -> new AuthFailException("User", "UserName", userName));

		String securePassword = tmp_user.getPassword();

		boolean passwordMatch = PasswordUtils.verifyUserPassword(providedPassword, securePassword);
		if (!passwordMatch) {
			Integer count = tmp_user.getInvalidAttemptsCount();
			int attemptsLeft = 5 - count;
			if(attemptsLeft != 0) {
			tmp_user.setInvalidAttemptsCount(count+1);			
			userRepository.save(tmp_user);
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You have "+attemptsLeft+" attempts left to login");
			}
			else {
				throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You have 0 attempts left to login, Please contact administrator");
			}
		}
		if(tmp_user.getInvalidAttemptsCount() == 5) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You have 0 attempts left to login, Please contact administrator");
		}
		tmp_user.setInvalidAttemptsCount(0);			
		userRepository.save(tmp_user);
		Date currentDate = new Date(System.currentTimeMillis());
		Date passwordLastUpdatedDate = tmp_user.getPasswordLastUpdatedAt();
		long currentDateDays =currentDate.getTime()/(1000*60*60*24);
		long passwordLastUpdatedDateDays = passwordLastUpdatedDate.getTime()/(1000*60*60*24);
		if(currentDateDays-passwordLastUpdatedDateDays>90) {
			System.out.println("++++++++++++++++++++++ change Password");
		}
		UserLog userLog = new UserLog();
		userLog.setLoginDate(new java.sql.Timestamp(System.currentTimeMillis()));
		userLog.setUserId(tmp_user);
		userLogRepository.save(userLog);
		return tmp_user;

	}
}
