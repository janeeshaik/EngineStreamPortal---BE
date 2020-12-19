package com.ste.enginestreamportal.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ste.enginestreamportal.model.Quotation;
import com.ste.enginestreamportal.payload.QuotationPayload;
import com.ste.enginestreamportal.repository.CertificationRepository;
import com.ste.enginestreamportal.repository.JobDetailsRepository;
import com.ste.enginestreamportal.repository.QuotationRepository;
import com.ste.enginestreamportal.repository.ShipmentRepository;
import com.ste.enginestreamportal.repository.UserRepository;

@Service
public class QuotationService {

	@Autowired
	QuotationRepository quotationRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	CertificationRepository certificationRepository;
	
	@Autowired
	JobDetailsRepository jobDetailsRepository;
	
	@Autowired
	ShipmentRepository shipmentRepository;
	
	public Quotation createQuotation(QuotationPayload quotationPayload) {
		Quotation quotation = new Quotation();
		quotation = mapQuotationPayloadToQuotation(quotation, quotationPayload);
		try {
			quotation.setCreatedBy(quotationPayload.getCreatedBy());
			quotation.setUpdatedBy(quotationPayload.getCreatedBy());
			quotation = quotationRepository.save(quotation);
		} catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Could not create Quotation");
		}
		return quotation;
	}
	
	private Quotation mapQuotationPayloadToQuotation(Quotation quotation, QuotationPayload quotationPayload) {
		
		quotation.setApprovedBy(userRepository.findById(quotationPayload.getApprovedBy()).get());
		quotation.setAtaNumber(quotationPayload.getAtaNumber());
		quotation.setCertification(certificationRepository.findById(quotationPayload.getCertification()).get());
		quotation.setGstCharges(quotationPayload.getGstCharges());
		quotation.setJobId(jobDetailsRepository.findById(quotationPayload.getJobId()).get());
		quotation.setMaterialHandlingCharges(quotationPayload.getMaterialHandlingCharges());
		quotation.setOthers(quotationPayload.getOthers());
		quotation.setPreparedBy(userRepository.findById(quotationPayload.getPreparedBy()).get());
		quotation.setShipment(shipmentRepository.findById(quotationPayload.getShipping()).get());
		quotation.setTat(quotationPayload.getTat());
		quotation.setTermsAndConditions(quotationPayload.getTermsAndConditions());
		quotation.setTotalCharges(quotationPayload.getTotalCharges());
		quotation.setValidityFrom(quotationPayload.getValidityFrom());
		quotation.setValidityTo(quotationPayload.getValidityTo());
		return quotation;
	}
	
	public Quotation updateQuotation(QuotationPayload quotationPayload) {
		Quotation quotation = quotationRepository.findById(quotationPayload.getId()).get();
		quotation = mapQuotationPayloadToQuotation(quotation, quotationPayload);
		try {
			quotation.setUpdatedBy(quotationPayload.getUpdatedBy());
			quotation.setStatus(quotationPayload.getStatus());
			quotation = quotationRepository.save(quotation);
		} catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Could not update Quotation");
		}
		return quotation;
	}
}
