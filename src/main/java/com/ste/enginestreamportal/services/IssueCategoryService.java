package com.ste.enginestreamportal.services;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.ste.enginestreamportal.model.IssueCategory;

import com.ste.enginestreamportal.repository.IssueCategoryRepository;

@Service
public class IssueCategoryService {

	@Autowired
	 IssueCategoryRepository IssueCategoryRepository;
	
	public Page<IssueCategory> findAllIssueCategory(Pageable pageable, String sortType, String searchWord, String statusType) {
        Page<IssueCategory> page = IssueCategoryRepository.findAll(new Specification<IssueCategory>() {

			
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<IssueCategory> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<>();
                if("active".equals(statusType)) {
                    predicates.add(cb.equal(root.get("status").as(String.class), "1"));
                } else if("inactive".equals(statusType)) {
                    predicates.add(cb.equal(root.get("status").as(String.class), "0"));
                } else {
                    
                }
                if (searchWord != null && !"".equals(searchWord)) {
                    String pattern = "%" + searchWord + "%";
                    predicates.add(cb.or(cb.like(root.get("issueCategoryTypeName").as(String.class), pattern)));
                }


                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        }, pageable);
        System.out.println(page.getContent().size() + " " + " " + pageable.getPageSize());

        return page;
			}

        }

