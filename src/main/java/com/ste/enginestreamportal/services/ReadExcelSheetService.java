package com.ste.enginestreamportal.services;

import java.io.File;
import java.io.FileInputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ste.enginestreamportal.model.CatalogueComponentDetails;
import com.ste.enginestreamportal.model.CatalogueComponentValues;
import com.ste.enginestreamportal.repository.DetailsRepo;
import com.ste.enginestreamportal.repository.ValuesRepo;

@Service
public class ReadExcelSheetService {
	@Autowired
	DetailsRepo detailsRepo;
	@Autowired
	ValuesRepo valuesRepo;

	/*
	 * public static void main(String[] args) throws Exception {
	 * //SpringApplication.run(ReadExcelSheet.class, args);
	 * ReadExcelSheet r = new ReadExcelSheet();
	 * r.ReadExcel(); 
	 * }
	 */
	public  void ReadExcel() throws Exception {
		//String fileLocation = "C:\\Users\\USER\\Downloads\\sample_excel_sheet\\Comp_Repair_CFM56-5B-2020_Catalog AUGUST 2020.xls";
		String fileLocation = ".\\src\\main\\resources\\excel\\Comp_Repair_CFM56-5B-2020_Catalog AUGUST 2020.xls";

		boolean insertIntoDetails = false;

		//fields of details
		String name = null;
		String componentCode = null;
		String componentParts= null;
		String repairSites= null;
		String ataRepairNumber= null;

		//fields of values
		String componentPartsValue= null;
		String ataRepairNumberValue= null;
		String repairDescription= null;
		String tatDays= null;
		String price= null;
		String repairSitesValue = null;

		CatalogueComponentDetails catalogueComponentDetails= null;
		CatalogueComponentValues catalogueComponentValues = null;
		
		/*
		 * valuesRepo.deleteAll();
		 * detailsRepo.deleteAll();
		 */

		File myFile = new File(fileLocation);
		FileInputStream fis = new FileInputStream(myFile);

		org.apache.poi.ss.usermodel.Workbook workbook = null;
		workbook = WorkbookFactory.create(fis);

		org.apache.poi.ss.usermodel.Sheet sheet = workbook.getSheetAt(0);

		System.out.println("no of sheets"+workbook.getNumberOfSheets());


		for(int i = 0; i < workbook.getNumberOfSheets(); i++) {
			sheet = workbook.getSheetAt(i);

			//getting name of the sheet i,e CFM56-5B
			name = sheet.getRow(0).getCell(2).getStringCellValue().split(" ")[0].split("\n")[0];

			for (int rn=3; rn<=sheet.getLastRowNum(); rn++) {
				Row row = sheet.getRow(rn);
				/*
				 * System.out.println("no of cells "+row.getPhysicalNumberOfCells());
				 * System.out.println("index "+rn);
				 */

				int detailsSno = 1;
				int valuesSno = 1;


				for (int cn=0; cn<row.getLastCellNum(); cn++) {
					Cell cell = row.getCell(cn);

					//details
					if(cn == 0 && cell != null && cell.getStringCellValue().length() > 1) {
						insertIntoDetails = true;

					}
					//values
					if(cn == 0 && cell == null) {
						insertIntoDetails = false;
					}
					if(insertIntoDetails) {
						//insert into details table
						switch (detailsSno++) {
						case 1:
							componentCode = cell != null ? cell.getStringCellValue() : null;
							break;
						case 2: 
							componentParts = cell != null ? cell.getStringCellValue() : null;
							break;
						case 3: 
							repairSites = cell != null ? cell.getStringCellValue() : null;
							break;
						case 4: 
							ataRepairNumber = cell != null ? cell.getStringCellValue() : null;
							break;
						default:
							String data = cell != null ? cell.getStringCellValue() : null;
							//System.out.println("hey..we got some unexpected data "+detailsSno+" with data "+ data);
						}

					}
					if(!insertIntoDetails) {
						//insert into values table
						switch (valuesSno++) {
						case 1: break;
						case 2: componentPartsValue = cell != null ? cell.getStringCellValue() : null;
						break;
						case 3:
							repairSitesValue = cell != null ? cell.getStringCellValue() : null;
							break;
						case 4:
							ataRepairNumberValue = cell != null ? cell.getStringCellValue() : null;
							break;
						case 5:
							repairDescription = cell != null ? cell.getStringCellValue() : null;
							break;
						case 6:
							tatDays = cell != null ? cell.getStringCellValue() : null;
							tatDays = tatDays != null ? tatDays.replace("+", "") : null; 
							break;
						case 7:
							price = cell != null ? cell.getStringCellValue() : null;
							price = price != null ? price.replace("+", "") : null; 
							break;
						default:
							//System.out.println("hey..we got some unexpected value "+valuesSno+ "with value "+cell.getStringCellValue());
						}


					}

				} //end of for


				if(insertIntoDetails) {
					//save into details
					catalogueComponentDetails = new CatalogueComponentDetails();
					catalogueComponentDetails.setName(name);
					catalogueComponentDetails.setComponentCode(componentCode);
					catalogueComponentDetails.setComponentParts(componentParts);
					catalogueComponentDetails.setRepairSites(repairSites);
					catalogueComponentDetails.setAtaRepairNumber(ataRepairNumber);

					detailsRepo.save(catalogueComponentDetails);

					componentCode = null;
					componentParts = null;
					repairSites = null;
					ataRepairNumber = null;

				} else {
					//save into values
					if(repairDescription != null && repairDescription.contains("*Note")) {
						componentPartsValue = null;
						ataRepairNumberValue = null;
						repairDescription = null;
						tatDays = null;
						price = null;
						repairSitesValue = null;

						continue;
					}

					catalogueComponentValues = new CatalogueComponentValues();
					catalogueComponentValues.setCatalogueComponentDetails(
							catalogueComponentDetails);
					catalogueComponentValues.setComponentPartsValue(componentPartsValue);
					catalogueComponentValues.setAtaRepairNumberValue(ataRepairNumberValue);
					catalogueComponentValues.setPrice(price);
					catalogueComponentValues.setRepairDescription(repairDescription);
					catalogueComponentValues.setRepairSitesValue(repairSitesValue);
					catalogueComponentValues.setTatDays(tatDays);

					String isEmpty = componentPartsValue + ataRepairNumberValue + price + repairDescription + repairSitesValue + tatDays;
					isEmpty = isEmpty.replace(" ", "");
					isEmpty = isEmpty.replace("null", "");
					if(isEmpty.equals("")) {
						componentPartsValue = null;
						ataRepairNumberValue = null;
						repairDescription = null;
						tatDays = null;
						price = null;
						repairSitesValue = null;

						continue;
					}

					valuesRepo.save(catalogueComponentValues);

					componentPartsValue = null;
					ataRepairNumberValue = null;
					repairDescription = null;
					tatDays = null;
					price = null;
					repairSitesValue = null;
				}
				//System.out.println();
			}

		}


	}
}

