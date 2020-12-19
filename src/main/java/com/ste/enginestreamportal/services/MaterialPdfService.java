package com.ste.enginestreamportal.services;

/*
 * import java.io.ByteArrayInputStream;
 * import java.io.ByteArrayOutputStream;
 * import java.util.List;
 * 
 * import com.itextpdf.text.Document;
 * import com.itextpdf.text.DocumentException;
 * import com.itextpdf.text.Element;
 * import com.itextpdf.text.Font;
 * import com.itextpdf.text.FontFactory;
 * import com.itextpdf.text.Phrase;
 * import com.itextpdf.text.log.Logger;
 * import com.itextpdf.text.log.LoggerFactory;
 * import com.itextpdf.text.pdf.PdfPCell;
 * import com.itextpdf.text.pdf.PdfPTable;
 * import com.itextpdf.text.pdf.PdfWriter;
 * import com.ste.inventorymanagement.model.Material;
 * 
 * public class MaterialPdfService {
 * 
 * public ByteArrayInputStream getMaterialPdf(List<Material> materials) {
 * final Logger logger = LoggerFactory.getLogger(MaterialPdfService.class);
 * 
 * Document document = new Document();
 * ByteArrayOutputStream out = new ByteArrayOutputStream();
 * 
 * 
 * try {
 * 
 * PdfPTable table = new PdfPTable(7);
 * table.setWidthPercentage(100);
 * table.setWidths(new int[]{3, 3, 3, 3, 3, 3, 3});
 * 
 * Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
 * //S.No, Material Number, Material Description, Last PO Unit Price, Last 1st
 * Year Issue Quantity, Last 2nd Year Issue Quantity, Last 3rd Year Issue
 * Quantity
 * 
 * PdfPCell hcell;
 * hcell = new PdfPCell(new Phrase("S.No", headFont));
 * hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
 * table.addCell(hcell);
 * 
 * hcell = new PdfPCell(new Phrase("Material Number", headFont));
 * hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
 * table.addCell(hcell);
 * 
 * hcell = new PdfPCell(new Phrase("Material Description", headFont));
 * hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
 * table.addCell(hcell);
 * 
 * hcell = new PdfPCell(new Phrase("Last PO Unit Price", headFont));
 * hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
 * table.addCell(hcell);
 * 
 * hcell = new PdfPCell(new Phrase("Last 1st Year Issue Quantity", headFont));
 * hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
 * table.addCell(hcell);
 * 
 * hcell = new PdfPCell(new Phrase("Last 2nd Year Issue Quantity", headFont));
 * hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
 * table.addCell(hcell);
 * 
 * hcell = new PdfPCell(new Phrase("Last 3rd Year Issue Quantity", headFont));
 * hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
 * table.addCell(hcell);
 * 
 * int sno = 1;
 * for (Material material : materials) {
 * PdfPCell cell;
 * //S.No, Material Number, Material Description, Last PO Unit Price, Last 1st
 * Year Issue Quantity, Last 2nd Year Issue Quantity, Last 3rd Year Issue
 * Quantity
 * 
 * 
 * cell = new PdfPCell(new Phrase((sno++)+""));
 * cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
 * cell.setHorizontalAlignment(Element.ALIGN_CENTER);
 * table.addCell(cell);
 * 
 * cell = new PdfPCell(new
 * Phrase(getPhraseData((material.getMaterialNumber()))));
 * cell.setPaddingLeft(5);
 * cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
 * cell.setHorizontalAlignment(Element.ALIGN_LEFT);
 * table.addCell(cell);
 * 
 * cell = new PdfPCell(new
 * Phrase(getPhraseData(material.getMaterialDescription())));
 * cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
 * cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
 * cell.setPaddingRight(5);
 * table.addCell(cell);
 * 
 * cell = new PdfPCell(new
 * Phrase(getPhraseData(material.getLastPOUnitPrice().toString())));
 * cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
 * cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
 * cell.setPaddingRight(5);
 * table.addCell(cell);
 * 
 * cell = new PdfPCell(new
 * Phrase(getPhraseData(material.getLast1stYearIssueQuantity())));
 * cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
 * cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
 * cell.setPaddingRight(5);
 * table.addCell(cell);
 * 
 * cell = new PdfPCell(new
 * Phrase(getPhraseData(material.getLast2ndYearIssueQuantity())));
 * cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
 * cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
 * cell.setPaddingRight(5);
 * table.addCell(cell);
 * 
 * cell = new PdfPCell(new
 * Phrase(getPhraseData(material.getLast3rdYearIssueQuantity())));
 * cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
 * cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
 * cell.setPaddingRight(5);
 * table.addCell(cell);
 * 
 * 
 * }
 * 
 * PdfWriter.getInstance(document, out);
 * document.open();
 * document.add(table);
 * 
 * document.close();
 * 
 * } catch (DocumentException ex) {
 * 
 * logger.error("Error occurred: {0}", ex);
 * }
 * 
 * return new ByteArrayInputStream(out.toByteArray());
 * }
 * 
 * public String getPhraseData(Object obj) {
 * return obj == null ? "null" : obj.toString();
 * }
 * 
 * }
 */