package com.ste.enginestreamportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ste.enginestreamportal.model.Quotation;

public interface QuotationRepository extends JpaRepository<Quotation, Long>{

}
