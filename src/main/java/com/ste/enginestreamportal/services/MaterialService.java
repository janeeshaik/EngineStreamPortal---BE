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
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.ste.enginestreamportal.model.Material;
import com.ste.enginestreamportal.repository.MaterialRepository;

@Service
public class MaterialService {

	@Autowired
	MaterialRepository materialRepo;

	public List<Material> getMaterials() {
		return materialRepo.findAll();
	}
	
	public List<Object[]> getAllMaterials() {
		return materialRepo.findAllMaterials();
	}

	public Material getMaterialById(Long materialId) {
		return materialRepo.findMaterialById(materialId);
	}
	
	public void convertMaterialToExcel(HttpServletResponse response) throws IOException {
		response.setContentType("application/octet-stream");

		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=materials" + ".xlsx";
		response.setHeader(headerKey, headerValue);

		List<Material> materials = this.getMaterials();

		MaterialExcelExporterService excelExporter = new MaterialExcelExporterService(materials);

		excelExporter.export(response);
	}

	public List<Material> getMaterialDataUsingQyery() {
		return materialRepo.getMaterialDataUsingQyery();
	}

	public Optional<Material> getMaterialByBatchId(int id) {
		Optional<Material> material = materialRepo.findById((long) id);
		return material;
	}

	/*
	 * public List<Material> searchMaterial1(String pattern) {
	 * 
	 * String query = "select m "
	 * + "from Material m ";
	 * if(data != "null") {
	 * query +=
	 * "where m.materialNumber like :data or "
	 * + "m.materialDescription like :data or "
	 * + "m.lastPOUnitPrice like :data or "
	 * + "m.last1stYearIssueQuantity like :data or "
	 * + "m.last2ndYearIssueQuantity like :data or "
	 * + "m.last3rdYearIssueQuantity like :data ";
	 * }
	 * 
	 * 
	 * List<Material> materials = materialRepo.searchMaterial(pattern);
	 * System.out.println(pattern);
	 * return materials;
	 * }
	 */

	public List<Material> searchMaterial(String pattern, Pageable pageable, boolean visited) {

		Page<Material> page = materialRepo.findAll(new Specification<Material>() {
			@Override
			public Predicate toPredicate(Root<Material> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();
				if (pattern != null) {
					predicates.add(criteriaBuilder.or(criteriaBuilder.like(root.get("materialNumber"), pattern),
							criteriaBuilder.like(root.get("materialDescription"), pattern),
							criteriaBuilder.like(root.get("lastPOUnitPrice").as(String.class), pattern),
							criteriaBuilder.like(root.get("last1stYearIssueQuantity").as(String.class), pattern),
							criteriaBuilder.like(root.get("last2ndYearIssueQuantity").as(String.class), pattern),
							criteriaBuilder.like(root.get("last3rdYearIssueQuantity").as(String.class), pattern)));
				}

				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		}, pageable);
		System.out.println(page.getContent().size() + " " + visited + " " + pageable.getPageSize());

		if (page.getContent().size() == 0 && !visited) {
			int pageIndex = 0;
			System.out.println("if is called");
			Pageable p = PageRequest.of(pageIndex, pageable.getPageSize());
			visited = true;
			return searchMaterial(pattern, p, visited);
		}
		return page.getContent();
	}

	/*
	 * public ResponseEntity<InputStreamResource> getMaterialPdf(HttpServletResponse
	 * response) {
	 * response.setContentType("application/octet-stream");
	 * 
	 * String headerKey = "Content-Disposition";
	 * String headerValue = "inline; filename=MaterialsReport.pdf";
	 * response.setHeader(headerKey, headerValue);
	 * 
	 * MaterialPdfService materialPdfService = new MaterialPdfService();
	 * List<Material> materials = this.getMaterials();
	 * ByteArrayInputStream bis = materialPdfService.getMaterialPdf(materials);
	 * 
	 * return ResponseEntity
	 * .ok()
	 * //.headers(headers)
	 * .contentType(MediaType.APPLICATION_PDF)
	 * .body(new InputStreamResource(bis));
	 * }
	 */

	
	public Page<Material> findAllMaterial(Pageable pageable, String sortType, String searchWord, String statusType) {
        Page<Material> page = materialRepo.findAll(new Specification<Material>() {


				@Override
				public Predicate toPredicate(Root<Material> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
					 List<Predicate> predicates = new ArrayList<>();
		                if("active".equals(statusType)) {
		                    predicates.add(cb.equal(root.get("status").as(String.class), "1"));
		                } else if("inactive".equals(statusType)) {
		                    predicates.add(cb.equal(root.get("status").as(String.class), "0"));
		                } else {
		                    
		                }
		                if (searchWord != null && !"".equals(searchWord)) {
		                    String pattern = "%" + searchWord + "%";
		                    predicates.add(cb.or(cb.like(root.get("materialNumber").as(String.class), pattern),
		                            cb.like(root.get("materialDescription").as(String.class), pattern)));
		                }

		                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
		            }
		        }, pageable);
		        System.out.println(page.getContent().size() + " " + " " + pageable.getPageSize());

		        return page;
        }
}
