package com.ste.enginestreamportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ste.enginestreamportal.model.CatalogueComponentValues;

public interface ValuesRepo extends JpaRepository<CatalogueComponentValues, Long>{

}