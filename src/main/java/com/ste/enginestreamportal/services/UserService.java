package com.ste.enginestreamportal.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ste.enginestreamportal.model.User;
import com.ste.enginestreamportal.model.UserPasswords;
import com.ste.enginestreamportal.payload.UserPayload;
import com.ste.enginestreamportal.repository.DepartmentRepository;
import com.ste.enginestreamportal.repository.RoleRepository;
import com.ste.enginestreamportal.repository.UserPasswordsRepository;
import com.ste.enginestreamportal.repository.UserRepository;
import com.ste.enginestreamportal.util.PasswordUtils;

@Service
public class UserService {
	
	@Autowired
	DepartmentRepository departmentRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	UserPasswordsRepository userPasswordsRepository;
	
	@Autowired
	MailService mailService;
	
	@Autowired
	ExternalUserService externalUserService;
	
	public UserPayload createUser(UserPayload userPayload) {
		User user = new User();
		UserPasswords userPasswords = new UserPasswords();
		user = mapUserPayloadToUser(user, userPayload);
		user.setPasswordLastUpdatedAt(new java.sql.Date(System.currentTimeMillis()));
		user.setInvalidAttemptsCount(0);
		try {
			user.setCreatedBy(userPayload.getCreatedBy());
			user.setUpdatedBy(userPayload.getCreatedBy());
			/*if(PasswordUtils.passwordFormatCheck(userPayload.getPassword())) {
	            user.setPassword(PasswordUtils.encryptAES256(userPayload.getPassword()));
			}else {
	        	throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
	                    "Password should consist of at least 8 characters which should include upper and lower case characters and numbers.");
	        }*/
			String genaratedPassword = PasswordUtils.getRandomGenaratedPassword();
			user.setPassword(PasswordUtils.encryptAES256(genaratedPassword));
			user = userRepository.save(user);
			userPasswords.setUserId(user);
			userPasswords.setPassword(user.getPassword());
			userPasswords.setCreatedBy(userPayload.getCreatedBy());
			userPasswords.setUpdatedBy(userPayload.getCreatedBy());
			userPasswords = userPasswordsRepository.save(userPasswords);
			String content = externalUserService.getExternalUserStatusUpdateMessage(genaratedPassword,user.getUserid());
			mailService.sendmail(content.toString(), user.getEmailAddress(), "Account Password reset link");
			/**/
		} catch (Exception e) {
			// TODO: handle exception
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Could not create User with duplicate User Id or Email Id");
		}

		
		mapUserToUserPayload(userPayload, user);
		return userPayload;

	}

	private User mapUserPayloadToUser(User user, UserPayload userPayload) {
		if (null != userPayload.getRoleId() && (0L != userPayload.getRoleId())) {
			user.setRoleId(roleRepository.findById(userPayload.getRoleId()).get());

		}
		else {
			user.setRoleId(null);
		}
		if (null != userPayload.getDepartmentId() && (0L != userPayload.getDepartmentId())) {
			user.setDepartmentId(departmentRepository.findById(userPayload.getDepartmentId()).get());
		} else {
			user.setDepartmentId(null);
		}
		user.setUserid(userPayload.getUserid());
		user.setPhoneNo(userPayload.getPhoneNo());
		user.setEmailAddress(userPayload.getEmailAddress());
		user.setName(userPayload.getName());
		user.setBusinessTypes(userPayload.getBusinessTypes());
		
		return user;
	}


	public UserPayload updateUser(UserPayload userPayload) {
		Optional<User> optUser = userRepository.findById(userPayload.getId());
		//final String oldPassword = optUser.get().getPassword();
		User user = optUser.get();
		user = mapUserPayloadToUser(user, userPayload);
		/*List<String> passwordsList = new ArrayList<String>();
		UserPasswords userPassword = new UserPasswords();

		if(!oldPassword.equals(user.getPassword())) {
			user.setPasswordLastUpdatedAt(new java.sql.Date(System.currentTimeMillis()));
			user.setInvalidAttemptsCount(user.getInvalidAttemptsCount());
			List<UserPasswords> userPasswordsList = new ArrayList<UserPasswords>();
		    userPasswordsList = userPasswordsRepository.getUserPasswordsByUserId(user.getId());
			if(userPasswordsList.isEmpty()) {
			}
			else {
				for(UserPasswords userPasswords : userPasswordsList) {
					passwordsList.add(userPasswords.getPassword());
				}
				if(passwordsList.contains(user.getPassword())) {
					
					 * Response response = new Response():
					 * resposne.setStatus(false);
					 * response.setResponse(null);
					 * response.setError("Cannot change to your previous password please use a different password");
					 * return response;
					 
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
							"Cannot change to your previous password please use a different password");
				}
				else {
					
				}
			}
		}*/
		try {
			user.setUpdatedBy(userPayload.getUpdatedBy());
			user.setStatus(userPayload.getStatus());
			user = userRepository.save(user);
			/*if(!oldPassword.equals(user.getPassword())) {
			if(passwordsList.size() < 8) {
				
			}
			else {
				List<UserPasswords> existingUserPasswords = new ArrayList<UserPasswords>();
				existingUserPasswords = userPasswordsRepository.getUserPasswordsByDateOrder(user.getId());
				userPasswordsRepository.delete(existingUserPasswords.get(0));
			}
			userPassword.setPassword(user.getPassword());
			userPassword.setUserId(user);
			userPassword.setCreatedBy(user.getUpdatedBy());
			userPassword.setUpdatedBy(user.getUpdatedBy());
			userPasswordsRepository.save(userPassword);
			}*/
		
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Could not update User with duplicate User Id or Email Id");
		}
		UserPayload updatedPayload = new UserPayload();
		updatedPayload = mapUserToUserPayload(updatedPayload, user);


		return updatedPayload;
	
	}
	
	

	private UserPayload mapUserToUserPayload(UserPayload userPayload, User user) {
		userPayload.setPhoneNo(user.getPhoneNo());
		userPayload.setId(user.getId());
		userPayload.setName(user.getName());
		userPayload.setEmailAddress(user.getEmailAddress());
		userPayload.setStatus(user.getStatus());
		userPayload.setCreatedAt(user.getCreatedAt());
		userPayload.setCreatedBy(user.getCreatedBy());
		userPayload.setUpdatedAt(user.getUpdatedAt());
		userPayload.setUpdatedBy(user.getUpdatedBy());
		userPayload.setBusinessTypes(user.getBusinessTypes());
		userPayload.setDepartmentId(user.getDepartmentId().getId());
		// userPayload.setPassword(user.getPassword());
		userPayload.setUserid(user.getUserid());
		if (null != user.getRoleId()) {
			userPayload.setRoleId(user.getRoleId().getId());
		} else
			userPayload.setRoleId(null);

		return userPayload;
	}

	public Page<User> findAllUsers(Pageable pageable, String sortType, String searchWord, String statusType) {
        Page<User> page = userRepository.findAll(new Specification<User>() {
            /**
             * 
             */
            private static final long serialVersionUID = 1L;

 

            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if("active".equals(statusType)) {
                    predicates.add(cb.equal(root.get("status").as(String.class), "1"));
                } else if("inactive".equals(statusType)) {
                    predicates.add(cb.equal(root.get("status").as(String.class), "0"));
                } else {
                    
                }
                if (searchWord != null && !"".equals(searchWord)) {
                    String pattern = "%" + searchWord + "%";
                    predicates.add(cb.or(cb.like(root.get("name").as(String.class), pattern),
                            cb.like(root.get("userid").as(String.class), pattern),
                            cb.like(root.get("emailAddress").as(String.class), pattern),
                            cb.like(root.get("phoneNo").as(String.class), pattern)));
                }

 

                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        }, pageable);
        System.out.println(page.getContent().size() + " " + " " + pageable.getPageSize());

 

        return page;
    }

 

    /*public List<User> getAllActiveUsers(Pageable pageable, String sortType, String searchWord) {
        Page<User> page =  userRepository.findAll(new Specification<User>() {
            *//**
             * 
             *//*
            private static final long serialVersionUID = 1L;

 

            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                predicates.add(cb.equal(root.get("status").as(String.class), "1"));
                if(searchWord != null && !"".equals(searchWord)) {
                    String pattern = "%"+searchWord+"%";
                    predicates.add(
                                    cb.or(
                                            cb.like(root.get("name").as(String.class), pattern),
                                            cb.like(root.get("userid").as(String.class), pattern),
                                            cb.like(root.get("emailAddress").as(String.class), pattern),
                                            cb.like(root.get("phoneNo").as(String.class), pattern)
                                            )
                                    );
                }

 

                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        }, pageable);
        System.out.println(page.getContent().size() + " "+ " "+ pageable.getPageSize());

 


        return page.getContent();
    }
    */
    public void genarateUsersExcelSheet(HttpServletResponse httpResponse, Integer showAll, int pageIndex,
            String sortType, String searchWord, String statusType) throws IOException {

 

        httpResponse.setContentType("application/octet-stream");
        String headerValue = null;
        Pageable pageable = null;
        String headerKey = "Content-Disposition";
        List<User> usersList = null;
        Page<User> page = null;

 

        if (sortType != null && sortType.equalsIgnoreCase("asc")) {
            pageable = PageRequest.of(pageIndex, showAll, Sort.by("name").ascending());
        } else {
            pageable = PageRequest.of(pageIndex, showAll, Sort.by("name").descending());
        }

 

        if ("inactive".equals(statusType)) {
            headerValue = "inline; filename=InactiveUsers" + ".xlsx";
        } else if("active".equals(statusType)){
            headerValue = "inline; filename=ActiveUsers" + ".xlsx";
        } else {
            headerValue = "inline; filename=AllUsers" + ".xlsx";
        }
        
        page = this.findAllUsers(pageable, sortType, searchWord,statusType);
        usersList = page.getContent();
        UsersExcelService excelExporter = new UsersExcelService(usersList, statusType);

 

        httpResponse.setHeader(headerKey, headerValue);
        excelExporter.export(httpResponse);
    }
}
