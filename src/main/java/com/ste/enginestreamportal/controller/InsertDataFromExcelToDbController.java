package com.ste.enginestreamportal.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ste.enginestreamportal.services.ReadExcelSheetService;
import com.ste.enginestreamportal.util.Response;

@Controller
@RequestMapping("/insert")
public class InsertDataFromExcelToDbController {
	
	@Autowired
	ReadExcelSheetService readExcelSheetService;
	
	private Logger logger = LogManager.getLogger(InsertDataFromExcelToDbController.class);

	@PostMapping("/insertDataFromExcelToDb")
	@ResponseBody
	public Response insertDataFromExcelToDb() throws Exception {
		
		Response response = new Response();
		try {
			readExcelSheetService.ReadExcel();
			response.setStatus(true);
			response.setError(null);
			response.setResponse("data is inserted successfully");
		} catch(Exception e) {
			response.setStatus(false);
			response.setError("Unable to insert data");
			logger.error("Error : "+e);
			response.setResponse(null);			
		}
		return response;
	}

}
