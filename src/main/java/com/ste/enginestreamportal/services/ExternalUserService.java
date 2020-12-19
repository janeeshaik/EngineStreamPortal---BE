package com.ste.enginestreamportal.services;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ste.enginestreamportal.model.ExternalUser;
import com.ste.enginestreamportal.model.User;
import com.ste.enginestreamportal.model.UserPasswords;
import com.ste.enginestreamportal.payload.ExternalUserPayload;
import com.ste.enginestreamportal.repository.ExternalUserRepo;
import com.ste.enginestreamportal.repository.RoleRepository;
import com.ste.enginestreamportal.repository.UserPasswordsRepository;
import com.ste.enginestreamportal.util.EmailCredentialsProperties;
import com.ste.enginestreamportal.util.PasswordUtils;

@Service
public class ExternalUserService {

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	ExternalUserRepo externalUserRepo;

	@Autowired
	UserPasswordsRepository userPasswordsRepository;

	@Autowired
	EmailCredentialsProperties emailCredentialsProperties;
	
	@Autowired
	MailService mailService;

	private Logger logger = LogManager.getLogger(ExternalUserService.class);

	public ExternalUserPayload createExternalUser(ExternalUserPayload userPayload) {
		ExternalUser externalUser = new ExternalUser();
		externalUser = mapUserPayloadToUser(externalUser, userPayload, false);
		externalUser.setInvalidAttemptsCount(0);
		try {
			
			externalUser = externalUserRepo.save(externalUser);
			externalUser.setCreatedBy(externalUser.getId().toString());
			externalUser.setUpdatedBy(externalUser.getId().toString());
			externalUser = externalUserRepo.save(externalUser);

		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e);
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Could not create User with duplicate User Id or Email Id");
		}

		mapUserToUserPayload(userPayload, externalUser);
		return userPayload;

	}

	private ExternalUser mapUserPayloadToUser(ExternalUser user, ExternalUserPayload userPayload, boolean isUpdate) {

		if (null != userPayload.getRoleId() && (0L != userPayload.getRoleId())) {
			user.setRoleId(roleRepository.findById(userPayload.getRoleId()).get());

		}

		else {
			user.setRoleId(null);
		}

		user.setUserid(userPayload.getUserid());
		user.setPhoneNo(userPayload.getPhoneNo());
		user.setEmailAddress(userPayload.getEmailAddress());
		user.setName(userPayload.getName());
		user.setBusinessTypes(userPayload.getBusinessTypes());
		if (isUpdate) {
			user.setPassword(PasswordUtils.encryptAES256(userPayload.getPassword()));
			user.setUpdatedBy(userPayload.getUserid());
		}
		else {
			user.setCreatedBy(userPayload.getUserid());
		}
		return user;
	}

	private ExternalUserPayload mapUserToUserPayload(ExternalUserPayload userPayload, ExternalUser user) {
		userPayload.setPhoneNo(user.getPhoneNo());
		userPayload.setId(user.getId());
		userPayload.setName(user.getName());
		userPayload.setEmailAddress(user.getEmailAddress());
		userPayload.setStatus(user.getStatus());
		userPayload.setCreatedAt(user.getCreatedAt());
		userPayload.setCreatedBy(user.getCreatedBy());
		userPayload.setUpdatedAt(user.getUpdatedAt());
		userPayload.setUpdatedBy(user.getUpdatedBy());
		if(user.getPassword() != null)
			userPayload.setPassword(PasswordUtils.decryptAES256(user.getPassword()));
		userPayload.setUserid(user.getUserid());
		if (null != user.getRoleId()) {
			userPayload.setRoleId(user.getRoleId().getId());
		} else
			userPayload.setRoleId(null);

		return userPayload;
	}

	public boolean validateEmailAddress(String emailAddress) {
		// TODO Auto-generated method stubfinal 
		Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
				Pattern.CASE_INSENSITIVE);
		java.util.regex.Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailAddress);
		return matcher.find();
	}

	public ExternalUserPayload updateExternalUserStatus(ExternalUserPayload userPayload) throws Exception {
		Optional<ExternalUser> externalUserOpt = externalUserRepo.findById(userPayload.getId());
		if (!externalUserOpt.isPresent()) {
			logger.error("user payload is null or there are no records with id ");
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"user payload is null or there are no records with id ");
		}
		ExternalUser externalUser = externalUserOpt.get();
		if(!userPayload.getStatus()) {
			//rejection mail
			String content = this.getRejectionMailContent();
			mailService.sendmail(content, externalUser.getEmailAddress(), "Request was Rejected");
			externalUser.setStatus(false);
			externalUserRepo.save(externalUser);
			return userPayload;
		}
		//update password
		String genaratedPassword = PasswordUtils.getRandomGenaratedPassword();
		userPayload.setPassword(genaratedPassword);
		externalUser = this.updateUser(userPayload, false);
		//setStatus and save
		externalUser.setStatus(true);
		externalUserRepo.save(externalUser);
		//send mail
		String content = this.getExternalUserStatusUpdateMessage(genaratedPassword,externalUser.getUserid());
		mailService.sendmail(content.toString(),externalUser.getEmailAddress(), "Account Password reset link");

		return userPayload;
	}

	private String getRejectionMailContent() {
		String content = "<div align=\"center\" style=\"text-align:left;border-top:4px solid #c82c35;box-shadow: 0px 0px 3px 1px #ddd8d8;margin:0px auto;width: 100%;overflow:auto;\">\r\n" + 
				"	<table align=\"center\" width=\"100%\">\r\n" + 
				"		<tr>\r\n" + 
				"			<td style=\"border-bottom:1px solid #e1e1e1;padding:5px 10px;\"><img src=\"st-engineering.png\" width=\"120\" alt=\"ENGINE STREAM PORTAL logo\" /></td>\r\n" + 
				"			<td style=\"border-bottom:1px solid #e1e1e1;padding:5px 10px;\" valign=\"middle\">\r\n" + 
				"				<div style=\"text-align: right;font-size: 16px;color:#333333;text-shadow: 1px 1px 2px #625f5f;\">ENGINE STREAM PORTAL</div>\r\n" + 
				"			</td>\r\n" + 
				"		</tr>\r\n" + 
				"	</table>\r\n" + 
				"	<table align=\"center\" width=\"100%\">\r\n" + 
				"		<tr>\r\n" + 
				"			<td colspan=\"2\" style=\"padding: 10px;font-size: 16px;color:#5e5d5d;line-height: 24px;letter-spacing: 0.03em;vertical-align: top;\">\r\n" + 
				"				<p>\r\n" + 
				"					Your Request was rejected\r\n" + 
				"\r\n" + 
				"				<p>Regards\r\n" + 
				"					<br/>Engine stream portal team</p>\r\n" + 
				"			</td>\r\n" + 
				"		</tr>\r\n" + 
				"	</table>\r\n" + 
				"	<table align=\"center\" width=\"100%\">\r\n" + 
				"		<tr>\r\n" + 
				"			<td colspan=\"2\" style=\"background-color: #363636;font-size:12px;font-weight:bold;padding: 10px;color:#ffffff;text-align: center;\">&copy;&nbsp;2020 ENGINE STREAM PORTAL</td>\r\n" + 
				"		</tr>\r\n" + 
				"	</table>\r\n" + 
				"</div>";
		return content;
		
	}

	public String getExternalUserStatusUpdateMessage(String genaratedPassword, String userId) {
		String content = "<div align=\"center\" style=\"text-align:left;border-top:4px solid #c82c35;box-shadow: 0px 0px 3px 1px #ddd8d8;margin:0px auto;width: 100%;overflow:auto;\">\r\n" + 
				"	<table align=\"center\" width=\"100%\">\r\n" + 
				"		<tr>\r\n" + 
				"			<td style=\"border-bottom:1px solid #e1e1e1;padding:5px 10px;\"><img src=\"st-engineering.png\" width=\"120\" alt=\"ENGINE STREAM PORTAL logo\" /></td>\r\n" + 
				"			<td style=\"border-bottom:1px solid #e1e1e1;padding:5px 10px;\" valign=\"middle\">\r\n" + 
				"				<div style=\"text-align: right;font-size: 16px;color:#333333;text-shadow: 1px 1px 2px #625f5f;\">ENGINE STREAM PORTAL</div>\r\n" + 
				"			</td>\r\n" + 
				"		</tr>\r\n" + 
				"	</table>\r\n" + 
				"	<table align=\"center\" width=\"100%\">\r\n" + 
				"		<tr>\r\n" + 
				"			<td colspan=\"2\" style=\"padding: 10px;font-size: 16px;color:#5e5d5d;line-height: 24px;letter-spacing: 0.03em;vertical-align: top;\">\r\n" + 
				"				<p>Hi,\r\n" + 
				"					<br>Please click on the below link to change your account password.\r\n" + 
				"					<br><a href = \"http://localhost:8080/ExternalUser/resetPassword/\">Password Reset Link</a>\r\n" + 
				" 					<br><b>User Id</b> = "+userId+
				"					<br><b>genarated Password</b> = "+genaratedPassword+			
				"					<br>For any queries, please reach out to our support team at enginestreamportal@gmail.com\r\n" + 
				"\r\n" + 
				"				<p>Regards\r\n" + 
				"					<br/>Engine stream portal team</p>\r\n" + 
				"			</td>\r\n" + 
				"		</tr>\r\n" + 
				"	</table>\r\n" + 
				"	<table align=\"center\" width=\"100%\">\r\n" + 
				"		<tr>\r\n" + 
				"			<td colspan=\"2\" style=\"background-color: #363636;font-size:12px;font-weight:bold;padding: 10px;color:#ffffff;text-align: center;\">&copy;&nbsp;2020 ENGINE STREAM PORTAL</td>\r\n" + 
				"		</tr>\r\n" + 
				"	</table>\r\n" + 
				"</div>";
		return content;
	}

	

	public ExternalUser updateUser(ExternalUserPayload userPayload, boolean isUpdate) throws IllegalAccessException, InvocationTargetException {

			Optional<ExternalUser> optUser = externalUserRepo.findById(userPayload.getId());
			String oldPassword = optUser.get().getPassword();
			ExternalUser extUser = optUser.get();
			if(isUpdate) {
			extUser = mapUserPayloadToUser(extUser, userPayload, true);
			}
			else {
				extUser.setPassword(PasswordUtils.encryptAES256(userPayload.getPassword()));
				extUser.setUpdatedBy(userPayload.getUpdatedBy());			
				}
			User user = new User();

			if (oldPassword == null || !oldPassword.equals(extUser.getPassword())) {
				extUser.setPasswordLastUpdatedAt(new java.sql.Date(System.currentTimeMillis()));
				extUser.setInvalidAttemptsCount(extUser.getInvalidAttemptsCount());
				UserPasswords userPassword = new UserPasswords();
				userPassword.setCreatedBy(userPayload.getUpdatedBy());
	            userPassword.setUpdatedBy(userPayload.getUpdatedBy());
				List<UserPasswords> userPasswordsList = new ArrayList<UserPasswords>();
				userPasswordsList = userPasswordsRepository.getUserPasswordsByUserId(extUser.getId());
				if (userPasswordsList.isEmpty()) {
					userPassword.setPassword(extUser.getPassword());
					BeanUtils.copyProperties(user, extUser);
					userPassword.setUserId(user);
					userPasswordsRepository.save(userPassword);
				} else {
					List<String> passwordsList = new ArrayList<String>();
					for (UserPasswords userPasswords : userPasswordsList) {
						passwordsList.add(userPasswords.getPassword());
					}
					if (passwordsList.contains(extUser.getPassword())) {
						throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
								"your password should not match with last 8 passwords");
					} else {
						if (passwordsList.size() < 8) {
							userPassword.setPassword(extUser.getPassword());
							BeanUtils.copyProperties(user, extUser);
							userPassword.setUserId(user);
							userPasswordsRepository.save(userPassword);
						} else {
							//get last updated record and reupdate it with new date and password or delete it and add new userPasswords
							List<UserPasswords> existingUserPasswords = new ArrayList<UserPasswords>();
							existingUserPasswords = userPasswordsRepository.getUserPasswordsByDateOrder(extUser.getId());
							userPasswordsRepository.delete(existingUserPasswords.get(0));
							userPassword.setPassword(extUser.getPassword());
							BeanUtils.copyProperties(user, extUser);
							userPassword.setUserId(user);
							userPasswordsRepository.save(userPassword);
						}
					}
				}
			} else {
				logger.error("Given password is same as previous password or password is null ");
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Given password is same as previous password or password is null ");
			}
			try {
				extUser.setUpdatedBy(userPayload.getUpdatedBy());
				extUser = externalUserRepo.save(extUser);
			} catch (Exception e) {
				// TODO: handle exception
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"Could not create ExternalUser with duplicate User Id or Email Id");
			}
			


		return extUser;

	}

	public ExternalUser resetExternalUserPassword(ExternalUserPayload userPayload)
			throws IllegalAccessException, InvocationTargetException {
		//validate given passsword
		if(userPayload.getConfirmPassword() == null  && !userPayload.getConfirmPassword().equals(userPayload.getPassword())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Password cannot be null or should match.");
		}
		boolean isValid = PasswordUtils.passwordFormatCheck(userPayload.getPassword());
		if(!isValid) {
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
		                    "Password should consist of at least 8 characters which should include upper and lower case characters and numbers.");
		        }
				return this.updateUser(userPayload, false);
	}

	public String getUserRegistrationMessage(ExternalUserPayload userPayLoad) {
		String content = "<div align=\"center\" style=\"text-align:left;border-top:4px solid #c82c35;box-shadow: 0px 0px 3px 1px #ddd8d8;margin:0px auto;width: 100%;overflow:auto;\">\r\n" + 
				"	<table align=\"center\" width=\"100%\">\r\n" + 
				"		<tr>\r\n" + 
				"			<td style=\"border-bottom:1px solid #e1e1e1;padding:5px 10px;\"><img src=\"st-engineering.png\" width=\"120\" alt=\"ENGINE STREAM PORTAL logo\" /></td>\r\n" + 
				"			<td style=\"border-bottom:1px solid #e1e1e1;padding:5px 10px;\" valign=\"middle\">\r\n" + 
				"				<div style=\"text-align: right;font-size: 16px;color:#333333;text-shadow: 1px 1px 2px #625f5f;\">ENGINE STREAM PORTAL</div>\r\n" + 
				"			</td>\r\n" + 
				"		</tr>\r\n" + 
				"	</table>\r\n" + 
				"	<table align=\"center\" width=\"100%\">\r\n" + 
				"		<tr>\r\n" + 
				"			<td colspan=\"2\" style=\"padding: 10px;font-size: 16px;color:#5e5d5d;line-height: 24px;letter-spacing: 0.03em;vertical-align: top;\">\r\n" + 
				"				<p>Hi,\r\n" + 
				"					<br>thank you for registering with engine stream portal.\r\n" + 
				"					<br>our customer report team will validate your details and update you.\r\n" + 
				"					<br>For any queries, please reach out to our support team at enginestreamportal@gmail.com\r\n" + 
				"				<p>Regards\r\n" + 
				"					<br/>Engine stream portal team</p>\r\n" + 
				"			</td>\r\n" + 
				"		</tr>\r\n" + 
				"	</table>\r\n" + 
				"	<table align=\"center\" width=\"100%\">\r\n" + 
				"		<tr>\r\n" + 
				"			<td colspan=\"2\" style=\"background-color: #363636;font-size:12px;font-weight:bold;padding: 10px;color:#ffffff;text-align: center;\">&copy;&nbsp;2020 ENGINE STREAM PORTAL</td>\r\n" + 
				"		</tr>\r\n" + 
				"	</table>\r\n" + 
				"</div>";
		// TODO Auto-generated method stub
		return content;
	}

	public String getForgotPasswordMessage(ExternalUser externalUser) {
        String content = "<div align=\"center\" style=\"text-align:left;border-top:4px solid #c82c35;box-shadow: 0px 0px 3px 1px #ddd8d8;margin:0px auto;width: 100%;overflow:auto;\">\r\n" +
                "    <table align=\"center\" width=\"100%\">\r\n" +
                "        <tr>\r\n" +
                "            <td style=\"border-bottom:1px solid #e1e1e1;padding:5px 10px;\"><img src=\"st-engineering.png\" width=\"120\" alt=\"ENGINE STREAM PORTAL logo\" /></td>\r\n" +
                "            <td style=\"border-bottom:1px solid #e1e1e1;padding:5px 10px;\" valign=\"middle\">\r\n" +
                "                <div style=\"text-align: right;font-size: 16px;color:#333333;text-shadow: 1px 1px 2px #625f5f;\">ENGINE STREAM PORTAL</div>\r\n" +
                "            </td>\r\n" +
                "        </tr>\r\n" +
                "    </table>\r\n" +
                "    <table align=\"center\" width=\"100%\">\r\n" +
                "        <tr>\r\n" +
                "            <td colspan=\"2\" style=\"padding: 10px;font-size: 16px;color:#5e5d5d;line-height: 24px;letter-spacing: 0.03em;vertical-align: top;\">\r\n" +
                "                <p>Hi,\r\n" +
                "                    <br>Please click on the below link to change your account password.\r\n" +
                "                    <br><a href = \"http://localhost:4200/updatePassword/"+externalUser.getUserid()+"\">Forgot password</a>\r\n" +
                "                    <br>For any queries, please reach out to our support team at enginestreamportal@gmail.com\r\n" +
                "\r\n" +
                "                <p>Regards\r\n" +
                "                    <br/>Engine stream portal team</p>\r\n" +
                "            </td>\r\n" +
                "        </tr>\r\n" +
                "    </table>\r\n" +
                "    <table align=\"center\" width=\"100%\">\r\n" +
                "        <tr>\r\n" +
                "            <td colspan=\"2\" style=\"background-color: #363636;font-size:12px;font-weight:bold;padding: 10px;color:#ffffff;text-align: center;\">&copy;&nbsp;2020 ENGINE STREAM PORTAL</td>\r\n" +
                "        </tr>\r\n" +
                "    </table>\r\n" +
                "</div>";
        return content;
    }

	public String getsaveForgotPassword(ExternalUser externalUser) {
		String content = "<div align=\"center\" style=\"text-align:left;border-top:4px solid #c82c35;box-shadow: 0px 0px 3px 1px #ddd8d8;margin:0px auto;width: 100%;overflow:auto;\">\r\n" + 
				"	<table align=\"center\" width=\"100%\">\r\n" + 
				"		<tr>\r\n" + 
				"			<td style=\"border-bottom:1px solid #e1e1e1;padding:5px 10px;\"><img src=\"st-engineering.png\" width=\"120\" alt=\"ENGINE STREAM PORTAL logo\" /></td>\r\n" + 
				"			<td style=\"border-bottom:1px solid #e1e1e1;padding:5px 10px;\" valign=\"middle\">\r\n" + 
				"				<div style=\"text-align: right;font-size: 16px;color:#333333;text-shadow: 1px 1px 2px #625f5f;\">ENGINE STREAM PORTAL</div>\r\n" + 
				"			</td>\r\n" + 
				"		</tr>\r\n" + 
				"	</table>\r\n" + 
				"	<table align=\"center\" width=\"100%\">\r\n" + 
				"		<tr>\r\n" + 
				"			<td colspan=\"2\" style=\"padding: 10px;font-size: 16px;color:#5e5d5d;line-height: 24px;letter-spacing: 0.03em;vertical-align: top;\">\r\n" + 
				"				<p>\r\n" + 
				"					Password changed successfully\r\n" + 
				"\r\n" + 
				"				<p>Regards\r\n" + 
				"					<br/>Engine stream portal team</p>\r\n" + 
				"			</td>\r\n" + 
				"		</tr>\r\n" + 
				"	</table>\r\n" + 
				"	<table align=\"center\" width=\"100%\">\r\n" + 
				"		<tr>\r\n" + 
				"			<td colspan=\"2\" style=\"background-color: #363636;font-size:12px;font-weight:bold;padding: 10px;color:#ffffff;text-align: center;\">&copy;&nbsp;2020 ENGINE STREAM PORTAL</td>\r\n" + 
				"		</tr>\r\n" + 
				"	</table>\r\n" + 
				"</div>";
		return content;
	}


}