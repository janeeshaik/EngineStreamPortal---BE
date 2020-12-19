package com.ste.enginestreamportal.services;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.ste.enginestreamportal.model.ApplicationMaster;
import com.ste.enginestreamportal.model.Role;
import com.ste.enginestreamportal.repository.ApplicationMasterRepository;


public class RolesExcelService {
	
	 ApplicationMasterRepository applicationMasterRepository;
	
	private XSSFWorkbook workbook = new XSSFWorkbook();
	private XSSFSheet sheet =  workbook.createSheet("Roles");
	private List<Role> Roles;
	int rowCount = 1;
	int headerRow = 0;
	int RoleSno = 1;
	String statusType;
	
	
	public RolesExcelService(List<Role> roles,String statusType, ApplicationMasterRepository applicationMasterRepository) {
		this.applicationMasterRepository = applicationMasterRepository;
		this.statusType = statusType;
		this.Roles = roles;
		workbook = new XSSFWorkbook();
		sheet = workbook.createSheet("Roles");
	}
	 

	private void writeRoleHeaderLine() {

		int column = 0;
		Row row = sheet.createRow(headerRow);

		CellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setBold(true);
		font.setFontHeight(14);
		style.setFont(font);

		createCell(row, column++, "S.No", style);
		createCell(row, column++, "Role Name", style);
		createCell(row, column++, "Role Description", style);
		createCell(row, column++, "Application Name", style);

		if("all".equals(statusType))
			createCell(row, column++, "Status", style);

	}




	private void createCell(Row row, int columnCount, Object value, CellStyle style) {
		sheet.autoSizeColumn(columnCount);
		Cell cell = row.createCell(columnCount);
		if (value instanceof Integer) {
			cell.setCellValue((Integer) value);
		} else if (value instanceof Boolean) {
			cell.setCellValue((Boolean) value);
		}else {
			if(value != null)
				cell.setCellValue( value.toString());
			else
				cell.setCellValue("null");
		}
		cell.setCellStyle(style);
	}

	private void writeRoleDataLines(Role role) {

		CellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setFontHeight(12);
		style.setFont(font);
		style.setWrapText(true);
		String apllicationNames = "";

		Row row = sheet.createRow(rowCount);
		int columnCount = 0;

		createCell(row, columnCount++, RoleSno++, style);
		createCell(row, columnCount++, role.getRoleName(), style);
		createCell(row, columnCount++, role.getRoleDescription(), style);
		
		if(role.getApplications() != null) {
			 apllicationNames = this.getApplicationNames(role.getApplications());
		}
		createCell(row, columnCount++,apllicationNames.replaceAll(",", ",\n"), style);
		if("all".equals(statusType))
			createCell(row, columnCount++, role.getStatus(), style);
		//headerRow = ++rowCount;
		rowCount++;

	}


	private String getApplicationNames(String applicationIds) {
		String[] appIds = applicationIds.split(",");
		String appName = "";
		for(String appId : appIds) {
			//Optional<ApplicationMaster> applicationName = applicationMasterRepository.findById(Long.parseLong(appId));
			String value = applicationMasterRepository.getApplicationNamesById(Long.parseLong(appId));
			if(value == null)
				continue;
			 appName = appName + applicationMasterRepository.getApplicationNamesById(Long.parseLong(appId))+",";
		}
			//removing extra comma at the end
		if(appName.length() != 0) {
			appName = appName.substring(0, appName.length()-1);
		}
		return appName;
	}


	public void export(HttpServletResponse response) throws IOException {

		writeRoleHeaderLine();
		for(Role Role : Roles) {
			writeRoleDataLines(Role);
		}

		ServletOutputStream outputStream = response.getOutputStream();
		workbook.write(outputStream);
		workbook.close();

		outputStream.close();

	}
}
