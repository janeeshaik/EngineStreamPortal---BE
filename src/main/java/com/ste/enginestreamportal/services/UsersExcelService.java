package com.ste.enginestreamportal.services;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.ste.enginestreamportal.model.User;

public class UsersExcelService {

	private XSSFWorkbook workbook;
	private XSSFSheet sheet;
	private List<User> Users;
	int rowCount = 1;
	int headerRow = 0;
	int UserSno = 1;
	String statusType;

	public UsersExcelService(List<User> users,String statusType) {
		this.statusType = statusType;
		this.Users = users;
		workbook = new XSSFWorkbook();
		sheet = workbook.createSheet("Users");
	}


	private void writeUserHeaderLine() {

		int column = 0;
		Row row = sheet.createRow(headerRow);

		CellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setBold(true);
		font.setFontHeight(14);
		style.setFont(font);

		createCell(row, column++, "S.No", style);
		createCell(row, column++, "Role Name", style);
		createCell(row, column++, "Name", style);
		createCell(row, column++, "Email address", style);
		createCell(row, column++, "User Id", style);

		createCell(row, column++, "Phone Number", style);
		createCell(row, column++, "Password Last Updated At", style);
		createCell(row, column++, "Invalid Attempts Count", style);
		createCell(row, column++, "Department Name", style);
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

	private void writeUserDataLines(User user) {

		CellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setFontHeight(12);
		style.setFont(font);

		Row row = sheet.createRow(rowCount);
		int columnCount = 0;

		createCell(row, columnCount++, UserSno++, style);
		createCell(row, columnCount++, user.getRoleId() != null ? user.getRoleId().getRoleName() : "", style);
		createCell(row, columnCount++, user.getName(), style);
		createCell(row, columnCount++, user.getEmailAddress(), style);
		createCell(row, columnCount++, user.getUserid(), style);

		createCell(row, columnCount++, user.getPhoneNo(), style);
		createCell(row, columnCount++, user.getPasswordLastUpdatedAt(), style);
		createCell(row, columnCount++, user.getInvalidAttemptsCount(), style);
		createCell(row, columnCount++, user.getDepartmentId() != null ? user.getDepartmentId().getDepartmentName() : "", style);
		if("all".equals(statusType))
			createCell(row, columnCount++, user.getStatus(), style);
		//headerRow = ++rowCount;
		rowCount++;

	}


	public void export(HttpServletResponse response) throws IOException {

		writeUserHeaderLine();
		for(User User : Users) {
			writeUserDataLines(User);
		}

		ServletOutputStream outputStream = response.getOutputStream();
		workbook.write(outputStream);
		workbook.close();

		outputStream.close();

	}
}