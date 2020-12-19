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

import com.ste.enginestreamportal.model.Batch;
import com.ste.enginestreamportal.model.Material;

public class MaterialExcelExporterService {
	private XSSFWorkbook workbook;
	private XSSFSheet sheet;
	private List<Material> materials;
	int rowCount = 1;
	int headerRow = 0;
	int materialSno = 1;

	public MaterialExcelExporterService(List<Material> materials) {
		this.materials = materials;
		workbook = new XSSFWorkbook();
		sheet = workbook.createSheet("Materials");
	}


	private void writeMaterialHeaderLine() {
		
		int column = 0;
		Row row = sheet.createRow(headerRow);

		CellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setBold(true);
		font.setFontHeight(14);
		style.setFont(font);

		createCell(row, column++, "S.No", style);
		createCell(row, column++, "Material Number", style);
		createCell(row, column++, "Material Description", style);
		createCell(row, column++, "Last PO Unit Price", style);
		createCell(row, column++, "Last 1st Year Issue Quantity", style);
		
		createCell(row, column++, "Last 2nd Year Issue Quantity", style);
		createCell(row, column++, "Last 3rd Year Issue Quantity", style);

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

	private void writeMaterialDataLines(Material material) {

		CellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setFontHeight(12);
		style.setFont(font);

		Row row = sheet.createRow(rowCount);
		int columnCount = 0;

		createCell(row, columnCount++, materialSno++, style);
		createCell(row, columnCount++, material.getMaterialNumber(), style);
		createCell(row, columnCount++, material.getMaterialDescription(), style);
		createCell(row, columnCount++, material.getLastPOUnitPrice(), style);
		createCell(row, columnCount++, material.getLast1stYearIssueQuantity(), style);
		createCell(row, columnCount++, material.getLast2ndYearIssueQuantity(), style);
		createCell(row, columnCount++, material.getLast3rdYearIssueQuantity(), style);
		
		headerRow = ++rowCount;
		rowCount++;

	}
	
	private void writeBatchHeaderLine() {

		Row row = sheet.createRow(headerRow);
		int column = 1;

		CellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setBold(true);
		font.setFontHeight(14);
		style.setFont(font);

		createCell(row, column++, "S.No", style);
		createCell(row, column++, "Biz Unit", style);
		
		createCell(row, column++, "Biz Unit Description", style);
		createCell(row, column++, "Batch No", style);
		createCell(row, column++, "Qi Batch No", style);
		createCell(row, column++, "Storage Location", style);
		createCell(row, column++, "Storage Bin", style);
		createCell(row, column++, "Last Receipt Date", style);
		createCell(row, column++, "Age By Day", style);
		createCell(row, column++, "Age By Month", style);
		createCell(row, column++, "Quantity", style);
		createCell(row, column++, "Uom", style);
		createCell(row, column++, "Vendor Name", style);
		createCell(row, column++, "Reason Purchase Description", style);
		createCell(row, column++, "Value In USD", style);
		createCell(row, column++, "Nbv In USD", style);
		createCell(row, column++, "Total Nbv USD", style);
		createCell(row, column++, "Tsn", style);
		createCell(row, column++, "Csn", style);
		createCell(row, column++, "Condition", style);
		createCell(row, column++, "Material Serial Number", style);
		createCell(row, column++, "Material Characteristic", style);
		

	}
	private void writeBatchDataLines(List<Batch> batches) {

		CellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setFontHeight(12);
		style.setFont(font);
		int sno = 1;

		
		int columnCount = 1;
		for(Batch batch : batches) {
			Row row = sheet.createRow(rowCount++);
			createCell(row, columnCount++,sno++, style);
			createCell(row, columnCount++, batch.getBizUnit(), style);
			createCell(row, columnCount++, batch.getBizUnitDescription(), style);
			createCell(row, columnCount++, batch.getBatchNo(), style);
			createCell(row, columnCount++, batch.getQiBatchNo(), style);
			createCell(row, columnCount++, batch.getStorageLocation(), style);
			createCell(row, columnCount++, batch.getStorageBin(), style);
			createCell(row, columnCount++, batch.getLastReceiptDate(), style);
			createCell(row, columnCount++, batch.getAgeByDay(), style);
			createCell(row, columnCount++, batch.getAgeByMonth(), style);
			createCell(row, columnCount++, batch.getQuantity(), style);
			createCell(row, columnCount++, batch.getUom(), style);
			createCell(row, columnCount++, batch.getVendorName(), style);
			createCell(row, columnCount++, batch.getReasonPurchaseDescription(), style);
			createCell(row, columnCount++, batch.getValueInUSD(), style);
			createCell(row, columnCount++, batch.getNbvInUSD(), style);
			createCell(row, columnCount++, batch.getTotalNBVUSD(), style);
			createCell(row, columnCount++, batch.getTsn(), style);
			createCell(row, columnCount++, batch.getCsn(), style);
			createCell(row, columnCount++, batch.getCondition(), style);
			createCell(row, columnCount++, batch.getMaterialSerialNumber(), style);
			createCell(row, columnCount++, batch.getMaterialCharacteristic(), style);
			columnCount = 1;
			
		}
		
		headerRow = ++rowCount;
		rowCount++;

	}

	public void export(HttpServletResponse response) throws IOException {
		
		for(Material material : materials) {
			writeMaterialHeaderLine();
			writeMaterialDataLines(material);
			writeBatchHeaderLine();
			writeBatchDataLines(material.getBatches());
		}
		

		ServletOutputStream outputStream = response.getOutputStream();
		workbook.write(outputStream);
		workbook.close();

		outputStream.close();

	}
}