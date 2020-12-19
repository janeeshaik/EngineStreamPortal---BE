package com.ste.enginestreamportal.services;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.ste.enginestreamportal.model.Material;
 
 
public class MaterialOpenPdfService {
    private List<Material> listMaterials;
     
    public MaterialOpenPdfService(List<Material> listMaterials) {
        this.listMaterials = listMaterials;
    }
 
    private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        //cell.setBackgroundColor(Color.BLUE);
        cell.setPadding(5);
         
        Font font = FontFactory.getFont(FontFactory.HELVETICA);
       // font.setColor(Color.WHITE);
         
      //S.No, Material Number, Material Description, Last PO Unit Price, Last 1st Year Issue Quantity, Last 2nd Year Issue Quantity, Last 3rd Year Issue Quantity

        cell.setPhrase(new Phrase("S.no", font));
        table.addCell(cell);
        
        cell.setPhrase(new Phrase("Material Number", font));
        table.addCell(cell);
        
        cell.setPhrase(new Phrase("Material Description", font));
        table.addCell(cell);
         
        cell.setPhrase(new Phrase("Last PO Unit Price", font));
        table.addCell(cell);
         
        cell.setPhrase(new Phrase("Last 1st Year Issue Quantity", font));
        table.addCell(cell);
         
        cell.setPhrase(new Phrase("Last 2nd Year Issue Quantity", font));
        table.addCell(cell);
         
        cell.setPhrase(new Phrase(" Last 3rd Year Issue Quantity", font));
        table.addCell(cell);
         
    }
     
    private void writeTableData(PdfPTable table) {
        //S.No, Material Number, Material Description, Last PO Unit Price, Last 1st Year Issue Quantity, Last 2nd Year Issue Quantity, Last 3rd Year Issue Quantity
    	int sno = 1;
        for (Material material : listMaterials) {
            table.addCell(sno++ + "");
            table.addCell(getPhraseData(material.getMaterialNumber()));
            table.addCell(getPhraseData(material.getMaterialDescription()));
            table.addCell(getPhraseData(material.getLastPOUnitPrice()));
            table.addCell(getPhraseData(material.getLast1stYearIssueQuantity()));
            table.addCell(getPhraseData(material.getLast2ndYearIssueQuantity()));
            table.addCell(getPhraseData(material.getLast3rdYearIssueQuantity()));
          
        }
    }
    
    public String getPhraseData(Object obj) {
		return obj == null ? "null" :  obj.toString();
	}
    
    public void export(HttpServletResponse response) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());
         
        document.open();
        
        //header containing Quotation , Quote to, rectangle box with data
        
        //material data with corresponding batch data
        
        
        
        
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
       // font.setColor(Color.BLUE);
         
        Paragraph p = new Paragraph("List of Materials", font);
        p.setAlignment(Paragraph.ALIGN_CENTER);
         
        document.add(p);
        
        HeaderFooter header = new HeaderFooter(new Phrase("This is a header."), false);
        HeaderFooter footer = new HeaderFooter(new Phrase("This is page "), new Phrase("."));
        document.setHeader(header);
        document.setFooter(footer);

         
        PdfPTable table = new PdfPTable(7);
        table.setWidthPercentage(100f);
        table.setWidths(new float[] {1.5f, 3.5f, 3.0f, 3.0f, 1.5f,3.0f,3.0f});
        table.setSpacingBefore(10);
        
        
        writeTableHeader(table);
        writeTableData(table);
         
        document.add(table);
         
        document.close();
         
    }
}