package com.ste.enginestreamportal.controller;

import java.util.NoSuchElementException;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ste.enginestreamportal.model.Shipment;
import com.ste.enginestreamportal.payload.ShipmentPayload;
import com.ste.enginestreamportal.repository.ShipmentRepository;
import com.ste.enginestreamportal.util.Response;
import com.ste.enginestreamportal.util.Utils;

@RestController
@ResponseBody
@RequestMapping("/Shipment")
public class ShipmentController extends Utils{

	@Autowired
	ShipmentRepository shipmentRepository;

	private Logger logger = LogManager.getLogger(UserController.class);

	@GetMapping("/")
	public Response getAllShipment() {
		Response response = new Response();
		try {
			response.setStatus(true);
			response.setError(null);
			response.setResponse(shipmentRepository.findAll());
		} catch(Exception e) {
			response.setStatus(false);
			response.setError("Unable to get all Shipment");
			logger.error("Error : "+e);
			response.setResponse(null);
		}

		return response;
	}

	@GetMapping("/{Id}")
	public Response getShipmentById(@PathVariable(value = "Id") Long id) {

		Response response = new Response();
		response.setStatus(false);
		response.setResponse(null);
		try {
			Shipment shipment = shipmentRepository.findById(id).get();
			response.setStatus(true);
			response.setError(null);
			response.setResponse(shipment);
		}
		catch(NoSuchElementException ex) {
			response.setError("Unable to find Shipment with id :"+id);
			logger.error("Error : "+ex);
		}
		catch(Exception e) {
			response.setError("Unable to get Shipment");
			logger.error("Error : "+e);
		}
		return response;
	}

	@PostMapping("/")
	public Response createShipment(@Valid @RequestBody ShipmentPayload shipmentPayload) {

		Response response = new Response();
		response.setStatus(false);
		response.setResponse(null);
		try {
			if(null == shipmentPayload) {
				response.setError("Payload cannot be null or empty");
			}else if(isNullOrEmpty(shipmentPayload.getName())) {
				response.setError("Name cannot be null or empty");
			}
			else {
				Shipment shipment = new Shipment();
				shipment.setName(shipmentPayload.getName());
				shipment.setCreatedBy(shipmentPayload.getCreatedBy());
				shipment.setUpdatedBy(shipmentPayload.getCreatedBy());
				shipment = shipmentRepository.save(shipment);
				response.setStatus(true);
				response.setError(null);
				response.setResponse(shipment);
			}
		} catch (Exception e) {
			response.setError("Could not create shipment");
			logger.error("Error : "+e);
		}
		return response;
	}

	@PutMapping("/")
	public Response updateShipment(@Valid @RequestBody ShipmentPayload shipmentPayload) {

		Response response = new Response();
		response.setStatus(false);
		response.setResponse(null);
		try {
			if(null == shipmentPayload) {
				response.setError("Payload cannot be null or empty");
			}else if(isNullOrEmpty(shipmentPayload.getName())) {
				response.setError("Name cannot be null or empty");
			}
			else if(isZeroOrNull(shipmentPayload.getId())) {
				response.setError("Id cannot zero or null");
			}
			else {
				Shipment shipment = null;
				shipment = shipmentRepository.findById(shipmentPayload.getId()).get();
				shipment.setName(shipmentPayload.getName());
				shipment.setUpdatedBy(shipmentPayload.getUpdatedBy());
				shipment.setStatus(shipmentPayload.getStatus());
				shipmentRepository.save(shipment);
				response.setStatus(true);
				response.setError(null);
				response.setResponse(shipment);
			}
		} catch(Exception ex) {
			response.setError("Please pass valid Shipment data to update.");
			logger.error("Error : "+ex);
		}
		return response;
	}

}
