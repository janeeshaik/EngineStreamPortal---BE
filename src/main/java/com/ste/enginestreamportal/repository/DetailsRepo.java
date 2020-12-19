  
package com.ste.enginestreamportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ste.enginestreamportal.model.CatalogueComponentDetails;

public interface DetailsRepo extends JpaRepository<CatalogueComponentDetails, Long>{

}