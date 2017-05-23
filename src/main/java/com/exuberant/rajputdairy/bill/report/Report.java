/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.exuberant.rajputdairy.bill.report;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.exuberant.rajputdairy.bill.agency.Customer;
import com.exuberant.rajputdairy.bill.agency.Headers;


import com.lowagie.text.rtf.RtfWriter2;
import com.lowagie.text.rtf.style.RtfFont;

import com.exuberant.rajputdairy.bill.fileOperations.OperateExcel;
import com.exuberant.rajputdairy.bill.fileOperations.OperateTxt;

public class Report {

	private static String folderPath = "C:\\Data\\Rakesh\\JavaWorkSpace\\RajputDairyBills\\Test_Files\\";
	private static String excelName = "Bills.xls";
	private static String reportName = "Bills";
	private String txtFileName = "Jul-09.txt";
	private String txtFilePathWithName = folderPath + txtFileName;
	private static int currentRecord = 0;
	private static int totalRecorProcessed = 0;

	// private String rtfFilePathWithName = folderPath + rtfFileName;
	public static void main(String args[]) {
		try {
			//InputStream myxls = new FileInputStream(folderPath + Utility.getMonth()+".xls");
			ClassLoader classloader = Thread.currentThread().getContextClassLoader();
			InputStream myxls = classloader.getResourceAsStream("Aug-15.xls");
			Workbook wb = new HSSFWorkbook(myxls);
			List<String> sheets = Arrays.asList("O.T.","Santosh_Nagar","TP");
			Document document = new Document();
			String fileName = System.getProperty("user.dir") + "\\" + reportName + "_"
					+ Utility.getMonth() + ".rtf";
			System.out.println(fileName);
			RtfWriter2.getInstance(document, new FileOutputStream(fileName));
			document.open();
			for (String sheetName : sheets) {
				currentRecord = 0;
				System.err.println(sheetName);
				Sheet sheet = wb.getSheet(sheetName);
				Iterator<Row> rowIter = sheet.rowIterator();
				Customer c;
				int count = 1;
				while (rowIter.hasNext()) {
					Row row = rowIter.next();										
					if (count<3) {						
						++count;
						continue;
					}
					if (row.getCell(Customer.indexes.get(Headers.NAME)) == null) {
						System.out.println("break");
						break;
					}
					c = new Customer(row);
					currentRecord++;
					totalRecorProcessed++;
					writeReportInWord(document, c);
					
				}

			}
			document.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void generateBills(Iterator<Row> rowIter, Document document) {
		/*
		 * Algorithm Read the Rows From Excel For Each Row modify the Bill File
		 */

		try {
			String fileName = folderPath + excelName + "_"
					+ Utility.getCurrentMonth() + ".rtf";
			RtfWriter2.getInstance(document, new FileOutputStream(fileName));
			document.open();
		} catch (FileNotFoundException ex) {
			Logger.getLogger(Report.class.getName())
					.log(Level.SEVERE, null, ex);
		}
		OperateExcel oe = new OperateExcel();
		String query = "select * from [Sheet1$] where date is not null";
        ResultSet rs = oe.getExcelDataByQuery(query, excelName);
        processRecords(document, rs);
        document.close();
	}

	public void generateBills(String excelName) {
		/*
		 * Algorithm Read the Rows From Excel For Each Row modify the Bill File
		 */
		Document document = new Document();
		try {
			String fileName = folderPath + excelName + "_"
					+ Utility.getLastMonth() + ".rtf";
			RtfWriter2.getInstance(document, new FileOutputStream(fileName));
			document.open();
		} catch (FileNotFoundException ex) {
			Logger.getLogger(Report.class.getName())
					.log(Level.SEVERE, null, ex);
		}
		OperateExcel oe = new OperateExcel();
		String query = "select * from [Sheet1$] where date is not null";
        ResultSet rs = oe.getExcelDataByQuery(query, excelName);
        processRecords(document, rs);
        document.close();
	}

    private void processRecords(Document document, ResultSet rs) {
        if (rs != null) {
            try {
                while (rs.next()) {
                    Customer c = new Customer(rs);
                    // writeReportInTxt(c);
                    writeReportInWord(document, c);
                }
            } catch (SQLException ex) {
                Logger.getLogger(Report.class.getName()).log(Level.SEVERE,
                        null, ex);
            }

        } else {
            System.out.println("No data returned From Excel File");
        }
    }

    public void writeReportInTxt(Customer cust) {
		OperateTxt ot = new OperateTxt();
		String content = "";
		content = content + Utility.addCharacter(" ", 32);
		content += "Rajput Milk Agency";
		ot.appendTextFile(this.txtFilePathWithName, content);
		ot.addNewLine(this.txtFilePathWithName, 1);
		String cust_name = Utility.addCharacter(" ", 7)
				+ "Customers Name:  EaI/EaImatI" + cust.cust_name;
		ot.appendTextFile(this.txtFilePathWithName, cust_name);
		content = "Bill Date: " + cust.bill_date;
		ot.addNewLine(this.txtFilePathWithName, 1);
		ot.appendTextFile(this.txtFilePathWithName, content);
		content = "Total Milk  " + cust.total_milk + " Liters" + "					"
				+ cust.amount;
		ot.addNewLine(this.txtFilePathWithName, 1);
		ot.appendTextFile(this.txtFilePathWithName, content);
		ot.addNewLine(this.txtFilePathWithName, 1);
		content = "Balance							" + cust.balance;
		ot.appendTextFile(this.txtFilePathWithName, content);
		content = "-------------------------------------------------------------";
		ot.appendTextFile(this.txtFilePathWithName, content);
		content = "Total Bill                                                     "
				+ cust.total_bill;
		ot.appendTextFile(this.txtFilePathWithName, content);
		ot.addNewLine(this.txtFilePathWithName, 5);
		content = "------------------------------------------------------------------------";
		ot.appendTextFile(this.txtFilePathWithName, content);
		content = "------------------------------------------------------------------------";
		ot.appendTextFile(this.txtFilePathWithName, content);
	}

	public static void writeReportInWord(Document doc, Customer cust) {

		String fontName = "Shivaji01";
		float fontSize = 20;
		RtfFont font = new RtfFont(fontName, fontSize, Font.BOLD);
		// OperateRTF objRTF = new OperateRTF();
		String content = "";
		// Image jpeg = null;
		try {
			content = content + Utility.addCharacter(" ", 18);
			content = content + "rajapUt dUQa DoArI";			
			doc.add(new Paragraph(content, font));
			font = new RtfFont(fontName, 16, Font.BOLD);
			content = "ibala idnaaMk: " + cust.getBill_date()
					+ Utility.addCharacter(" ", 8) + "faona: 9767189054�8856024226";
			doc.add(new Paragraph(content, font));
			// doc.add(jpeg);
			content = Utility.addCharacter(" ", 19) + "g`aahkacao naava: "
					+ cust.getCust_name();
			doc.add(new Paragraph(content, font));
			content = Utility.addCharacter(" ", 13) + "ekUNa dUQa:  "
					+ cust.getTotal_milk() + "  ilaTr    " + cust.getAmount()
					+ "  $";
			doc.add(new Paragraph(content, font));
			// String s = cust.getBalance();
			// System.out.println(s);
			// System.out.println(Float.valueOf(s).floatValue());
			// System.out.println(cust.getBalance());

            addBalance(doc, cust, font);
			content = "-------------------------------------------------------------------------------------------------------------";
			doc.add(new Paragraph(content, new RtfFont("Verdana", 10, Font.BOLD)));
			content = Utility.addCharacter(" ", 13) + "ekUNa ibala: "
					+ Utility.addCharacter(" ", 17) + cust.getTotal_bill()
					+ "  $";
			doc.add(new Paragraph(content, font));
			doc.add(new Paragraph("", font));
			if(totalRecorProcessed%4!=0){
				doc.add(new Paragraph("", font));
			}
			
			//For Wishes comment Following two line
			/*doc.add(new Paragraph("", font));
			doc.add(new Paragraph(Integer.toString(currentRecord), font));*/
			// doc.add(new Paragraph("", font));
			//For Wishes uncomment Following two lines
			//doc.add(new Paragraph(Utility.addCharacter(" ", 10)+"|| navavaYaa-iBanaMdna ||", new RtfFont("Shivaji01", 28, Font.BOLD)));
			//doc.add(new Paragraph(Utility.addCharacter(" ", 8)+"!!! Happy Holi !!!", new RtfFont("Verdana", 23, Font.BOLD)));
			//doc.add(new Paragraph("", font));
			//doc.add(new Paragraph(Utility.addCharacter(" ", 10)+"|| SauBa idpavalaI ||", new RtfFont("Shivaji01", 28, Font.BOLD)));
			//doc.add(new Paragraph(Utility.addCharacter(" ", 5)+"�� Axaya tRtIyaacyaa haid-k SauBaocCa ��", new RtfFont("Shivaji01", 28, Font.BOLD)));
			doc.add(new Paragraph(Utility.addCharacter(" ", 5)+"�� svatM~ta idnaacyaa haid-k SauBaocCa ��", new RtfFont("Shivaji01", 28, Font.BOLD)));
//			doc.add(new Paragraph(Utility.addCharacter(" ", 5)+"�� mahara YT idnaacyaa haid-k SauBaocCa ��", new RtfFont("Shivaji01", 28, Font.BOLD)));
			//doc.add(new Paragraph(Utility.addCharacter(" ", 5)+"�� dsa�yaacyaa haid-k SauBaocCa ��", new RtfFont("Shivaji01", 28, Font.BOLD)));
			//doc.add(new Paragraph(Utility.addCharacter(" ", 5)+"|| dsa�yaacyaa haid-k SauBaocCa ||", new RtfFont("Shivaji01", 28, Font.BOLD)));
			doc.add(new Paragraph(
					"-------------------------------------------------------------------------------------------------------------",
					new RtfFont("Verdana", 10, Font.BOLD)));
		} catch (DocumentException ex) {
			Logger.getLogger(Report.class.getName())
					.log(Level.SEVERE, null, ex);
		}

	}

	public void BigFontwriteReportInWord(Document doc, Customer cust) {

		String fontName = "Shivaji01";
		float fontSize = 24;
		RtfFont font;
		// OperateRTF objRTF = new OperateRTF();
		String content = "";
		// Image jpeg = null;
		try {
			content = content + Utility.addCharacter(" ", 27);
			content = content + "rajapUt dUQa DoArI";
			font = new RtfFont(fontName, 25, Font.BOLD);
			// RtfImage rtfImg = new
			// RtfImage("C:\\Documents and Settings\\13411\\Desktop\\Genie_Admin_Error.JPG");
			// Image img =
			// Image.getInstance("C:\\Documents and Settings\\13411\\Desktop\\Genie_Admin_Error.JPG");
			/*
			 * try { jpeg = Image.getInstance(
			 * "E:\\Learn\\Java_Test\\Excel Operation\\ExcelOperations\\Test_Files\\phone1.jpg"
			 * ); } catch (BadElementException ex) {
			 * Logger.getLogger(report.class.getName()).log(Level.SEVERE, null,
			 * ex); } catch (MalformedURLException ex) {
			 * Logger.getLogger(report.class.getName()).log(Level.SEVERE, null,
			 * ex); } catch (IOException ex) {
			 * Logger.getLogger(report.class.getName()).log(Level.SEVERE, null,
			 * ex); }
			 */
			doc.add(new Paragraph(content, font));
			font = new RtfFont(fontName, 24, Font.BOLD);
			content = "ibala idnaaMk: " + cust.getBill_date()
					+ Utility.addCharacter(" ", 27) + "faona: 9767904695";
			doc.add(new Paragraph(content, font));
			// doc.add(jpeg);
			content = Utility.addCharacter(" ", 23) + "g`aahkacao naava: EaI "
					+ cust.getCust_name();
			doc.add(new Paragraph(content, font));
			content = Utility.addCharacter(" ", 13) + "ekUNa dUQa:  "
					+ cust.getTotal_milk() + "  ilaTr    " + cust.getAmount()
					+ "  $";
			doc.add(new Paragraph(content, font));
			// String s = cust.getBalance();
			// System.out.println(s);
			// System.out.println(Float.valueOf(s).floatValue());
			// System.out.println(cust.getBalance());
            addBalance(doc, cust, font);
            content = "-------------------------------------------------------------------------------------------------------------";
			doc.add(new Paragraph(content, new RtfFont("Verdana", 10, Font.BOLD)));
			content = Utility.addCharacter(" ", 13) + "ekUNa ibala: "
					+ Utility.addCharacter(" ", 17) + cust.getTotal_bill()
					+ "  $";
			doc.add(new Paragraph(content, font));
			doc.add(new Paragraph("", font));
			doc.add(new Paragraph("", font));
			doc.add(new Paragraph("", font));
			doc.add(new Paragraph("", font));
			doc.add(new Paragraph("", font));
			doc.add(new Paragraph(
					"-------------------------------------------------------------------------------------------------------------",
					new RtfFont("Verdana", 10, Font.BOLD)));
		} catch (DocumentException ex) {
			Logger.getLogger(Report.class.getName())
					.log(Level.SEVERE, null, ex);
		}

	}

    private static void addBalance(Document doc, Customer cust, RtfFont font) throws DocumentException {
        String content;
        if (Float.valueOf(cust.getBalance()) != 0) {
            content = Utility.addCharacter(" ", 13) + "maagaIla baakI: "
                    + Utility.addCharacter(" ", 16) + cust.getBalance()
                    + "  $";
            doc.add(new Paragraph(content, font));
        } else {
            content = " ";
            doc.add(new Paragraph(content, font));
        }
    }
}

