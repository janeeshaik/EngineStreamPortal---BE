package com.ste.enginestreamportal.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
import org.springframework.stereotype.Service;

import com.ste.enginestreamportal.model.Role;
import com.ste.enginestreamportal.repository.ApplicationMasterRepository;
import com.ste.enginestreamportal.repository.RoleRepository;

@Service
public class RoleService {

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	ApplicationMasterRepository applicationMasterRepository;
	
public Page<Role> getAllRolePage(Pageable pageable, String sortType, String searchWord, String statusType) {
        
        Page<Role> page = roleRepository.findAll(new Specification<Role>() {
            /**
             * 
             */
            private static final long serialVersionUID = 1L;

 

            @Override
            public Predicate toPredicate(Root<Role> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if("active".equals(statusType)) {
                    predicates.add(cb.equal(root.get("status").as(String.class), "1"));
                } else if("inactive".equals(statusType)) {
                    predicates.add(cb.equal(root.get("status").as(String.class), "0"));
                } else {
                    //all
                }
                if (searchWord != null && !"".equals(searchWord)) {
                    String pattern = "%" + searchWord + "%";
                    predicates.add(cb.or(
                            cb.like(root.get("roleName").as(String.class), pattern),
                            cb.like(root.get("roleDescription").as(String.class), pattern)
                            ));
                }

 

                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        }, pageable);
        System.out.println(page.getContent().size() + " " + " " + pageable.getPageSize());
        return page;
    }
	
public void genarateRolesExcelSheet(HttpServletResponse httpResponse, Integer showAll, int pageIndex,
        String sortType, String searchWord, String statusType) throws IOException {



    httpResponse.setContentType("application/octet-stream");
    String headerValue = null;
    Pageable pageable = null;
    String headerKey = "Content-Disposition";
    List<Role> roleList = null;



    if (sortType != null && sortType.equalsIgnoreCase("asc")) {
        pageable = PageRequest.of(pageIndex, showAll, Sort.by("roleName").ascending());
    } else {
        pageable = PageRequest.of(pageIndex, showAll, Sort.by("roleName").descending());
    }



    if ("inactive".equals(statusType)) {
        headerValue = "attachment; filename=InactiveRoles" + ".xlsx";
    } else if("active".equals(statusType)){
        headerValue = "attachment; filename=ActiveRoles" + ".xlsx";
    } else {
        headerValue = "attachment; filename=AllRoles" + ".xlsx";
    }
    
    roleList = this.getAllRolePage(pageable, sortType, searchWord,statusType).getContent();
    RolesExcelService excelExporter = new RolesExcelService(roleList, statusType,applicationMasterRepository);



    httpResponse.setHeader(headerKey, headerValue);
    excelExporter.export(httpResponse);
}
}
