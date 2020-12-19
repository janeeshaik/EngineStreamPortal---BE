package com.ste.enginestreamportal.controller;

import java.util.NoSuchElementException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ste.enginestreamportal.model.Batch;
import com.ste.enginestreamportal.model.Material;
import com.ste.enginestreamportal.services.BatchService;
import com.ste.enginestreamportal.services.MailService;
import com.ste.enginestreamportal.services.MaterialService;
import com.ste.enginestreamportal.util.Constants;
import com.ste.enginestreamportal.util.Response;
import com.ste.enginestreamportal.util.Utils;

/**
 * @author Jani Shaik on 16-Dec-2020
 */

@RestController
public class InventoryAndAssetManagementController extends Utils{

	private final Logger logger = LogManager.getLogger(InventoryAndAssetManagementController.class);

	@Autowired
	MaterialService materialService;

	@Autowired
	BatchService batchService;

	@Autowired
	MailService mailService;

	/**
	 * To get list of all materials
	 * @return Lis of all materials
	 */
	@GetMapping("/materials")
	public Response getAllMaterials() {
		Response response = new Response();
		try {
			response.setStatus(true);
			response.setError(null);
			response.setResponse(materialService.getMaterials());
			//response.setResponse(materialService.getAllMaterials());
		} catch(Exception e) {
			response.setStatus(false);
			response.setError(Constants.MATERIAL_DETAILS_ERROR);
			logger.error("Error : "+e);
			response.setResponse(null);
		}
		return response;
	}

	/**
	 * To get material details
	 * @param materialId Material Id
	 * @return Material details
	 */
	@GetMapping("/materials/{materialId}")
	public Response getMaterialById(@PathVariable Long materialId) {
		Response response = new Response();
		try {
			response.setStatus(true);
			response.setError(null);
			response.setResponse(materialService.getMaterialById(materialId));
		} catch (Exception e) {
			response.setStatus(false);
			response.setError(Constants.MATERIAL_DETAILS_ERROR);
			logger.error("Error : "+e);
			response.setResponse(null);
		}
		return response;
	}

	/**
	 * To get batch details by Id
	 * @param batchId Batch Id
	 * @return Batch details
	 */
	@GetMapping("/materials/batches/{batchId}")
	public Response getBatchById(@PathVariable Long batchId) {
		Response response = new Response();
		try {
			response.setStatus(true);
			response.setError(null);
			response.setResponse(batchService.getBatchById(batchId));
		} catch (Exception e) {
			response.setStatus(false);
			response.setError(Constants.MATERIAL_BATCH_ERROR);
			logger.error("Error : "+e);
			response.setResponse(null);
		}
		return response;
	}

	/**
	 * To get batches
	 * @return Batch details
	 */
	@GetMapping("/batches")
	public Response getBatches() {
		Response response = new Response();
		try {
			response.setStatus(true);
			response.setError(null);
			response.setResponse(batchService.findAll());
		} catch (Exception e) {
			response.setStatus(false);
			response.setError(Constants.BATCH_DETAILS_ERROR);
			logger.error("Error : "+e);
			response.setResponse(null);
		}
		return response;
	}

	/**
	 * To get surplus inventory
	 * @return Surplus Inventory
	 */
	@GetMapping("/surplusInventory")
	public Response getSurplusInventory() {
		Response response = new Response();
		try {
			response.setStatus(true);
			response.setError(null);
			response.setResponse(batchService.getSurplusInventory(Constants.IS_SURPLUS_FLAG));
		} catch (Exception e) {
			response.setStatus(false);
			response.setError(Constants.SURPLUS_INVENTORY_ERROR);
			logger.error("Error : "+e);
			response.setResponse(null);
		}
		return response;
	}

	/**
	 * To update batch surplus inventory status
	 * @param batchId BatchId
	 * @param flag Flag
	 * @return Updated status
	 */
	@PutMapping("/batches/flag/{batchId}/{flag}")
	public Response flagSurplusInventory(@PathVariable Long batchId, @PathVariable Boolean flag) {
		Response response = new Response();
		try {
			response.setStatus(true);
			response.setError(null);
			batchService.updateBatchSurplusFlag(batchId, flag);
			response.setResponse(Constants.BATCH_DETAILS_UPDATE);
		} catch (Exception e) {
			response.setStatus(true);
			response.setError(Constants.SURPLUS_INVENTORY_UPDATE_FLAG_ERROR);
			response.setResponse(null);
		}
		return response;
	}

	@GetMapping(value = "/sendBatchEmail/{id}/{emailAddress}")
	public Response sendEmail(@PathVariable Long id, @PathVariable String emailAddress) throws Exception {
		Response response = new Response();
		response.setStatus(false);
		response.setResponse(null);
		try {
			if(emailAddressNotMatchesFormat(emailAddress)) {
				response.setError("EmailAddress is not in correct format");
				return response;
			}
			Batch batch = batchService.getBatchById(id);
			Material material = batch.getMaterial();
			if	(material == null) {
				response.setError("material with batch id "+ id+" is not present in table");
				return response;
			}
			String content = this.batchEmailContent(batch);
			mailService.sendmail(content, emailAddress, "Batch Details for : "+batch.getBatchNo());
			response.setStatus(true);
			response.setError(null);
			response.setResponse("Email sent successfully");
		} catch(NoSuchElementException ex) {
			response.setError("Unable to batch with id :"+id);
			logger.error("Error : "+ex);
		} catch(Exception e) {
			response.setError("Unable to sent mail");
			logger.error("Error : "+e);
		}
		return response;
	}
	
	public String batchEmailContent(Batch batch) {
		String content = "<div align=\"center\" style=\"text-align:left;border-top:4px solid #c82c35;box-shadow: 0px 0px 3px 1px #ddd8d8;margin:0px auto;width: 100%;overflow:auto;\">\r\n" + 
				"<style>\r\n" + 
				"table2 {\r\n" + 
				"  font-family: arial, sans-serif;\r\n" + 
				"  border-collapse: collapse;\r\n" + 
				"  width: 100%;\r\n" + 
				"}\r\n" + 
				"</style>\r\n" + 
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
				"					<br>Please check the below details of batch.\r\n" + 
				"					<br>For any queries, please reach out to our support team at enginestreamportal@gmail.com\r\n" + 
				"                <table2>\r\n" + 
				"                <tr>\r\n" + 
				"                <th>Batch Number</th>\r\n" + 
				"                <td>"+batch.getBatchNo()+"</td>\r\n" + 
				"                </tr>\r\n" + 
				"                <tr>\r\n" + 
				"                <th>Quantity</th>\r\n" + 
				"                <td>"+batch.getQuantity()+"</td>\r\n" + 
				"                </tr>\r\n" + 
				"                <tr>\r\n" + 
				"                <th>Material Number</th>\r\n" + 
				"                <td>"+batch.getMaterial().getMaterialNumber()+"</td>\r\n" + 
				"                </tr>\r\n" + 
				"                <tr>\r\n" + 
				"                <th>Material Serial Number</th>\r\n" + 
				"                <td>"+batch.getMaterialSerialNumber()+"</td>\r\n" + 
				"                </tr>\r\n" + 
				"                <tr>\r\n" + 
				"                <th>Engine Type</th>\r\n" + 
				"                <td>"+batch.getMaterial().getEngine().getEngineType()+"</td>\r\n" + 
				"                </tr>\r\n" + 
				"                <tr>\r\n" + 
				"                <th>Material Description</th>\r\n" + 
				"                <td>"+batch.getMaterial().getMaterialDescription()+"</td>\r\n" + 
				"                </tr>\r\n" + 
				"                <tr>\r\n" + 
				"                <th>Plant Name</th>\r\n" + 
				"                <td>"+batch.getMaterial().getPlant().getPlantName()+"</td>\r\n" + 
				"                </tr>\r\n" + 
				"                <tr>\r\n" + 
				"                <th>Condition</th>\r\n" + 
				"                <td>"+batch.getCondition()+"</td>\r\n" + 
				"                </tr>\r\n" + 
				"                <tr>\r\n" + 
				"                <th>VendorName</th>\r\n" + 
				"                <td>"+batch.getVendorName()+"</td>\r\n" + 
				"                </tr>\r\n" + 
				"                </table2>\r\n" +
				"			</td>\r\n" + 
				"		</tr>\r\n" + 
				"	</table>\r\n" + 
				"	<table align=\"center\" width=\"100%\">\r\n" + 
				"		<tr>\r\n" + 
				"			<td colspan=\"2\" style=\"background-color: #363636;font-size:12px;font-weight:bold;padding: 10px;color:#ffffff;text-align: center;\">&copy;&nbsp;2020 ENGINE STREAM PORTAL</td>\r\n" + 
				"		</tr>\r\n" + 
				"                <p>Regards\r\n" + 
				"					<br/>Engine stream portal team</p>\r\n" +  
				"	</table>\r\n" + 
				"</div>";
		return content;
	}
}
