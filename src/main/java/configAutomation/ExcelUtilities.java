package configAutomation;

//import java.awt.List;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtilities {
	
	public XSSFWorkbook excelWBook;

	public ExcelUtilities(String filename) {
		initialize(filename);
		System.out.println("ExcelUtilities Initializing: " + filename);
	}
	//need to figure out when to stop this (maybe using the options column?)
	public int getRowCount(String targetSheet) {
		XSSFSheet excelWSheet = excelWBook.getSheet(targetSheet);
		System.out.println("opening sheet: " + targetSheet + " in "+excelWSheet.toString());		
		int count=0;
		try {
			String cellData;
			do {
				cellData = this.getCellData(count,1, excelWSheet);
				System.out.println("getting cell data: " + cellData + " ["+cellData.length()+"]");
				if(cellData.length()>0) {
					count++;
				}
			}while(cellData.length()>0);
		}catch(Exception e) {
			System.out.println("getRowCount failed");
		}
		System.out.println("rowCount: " + count);
		return count;
	}
	public void initialize(String filename) {	
		FileInputStream inputStream;
		try {
			inputStream = new FileInputStream(filename);
			excelWBook = new XSSFWorkbook(inputStream);
		} catch (FileNotFoundException e1) {
			System.out.println("Couldn't open FileInputStream: "+ filename);
			e1.printStackTrace();
		} catch (IOException e) {
			System.out.println("Couldn't create XSSFWorkbook: " + filename);
			e.printStackTrace();
		}
	}
	
	/*
	 * Goes through the rows of the target sheet
	 * Looks for the value on the top leftmost cell and cascades the value down until a new value is found
	 * As this goes down each row, it also pulls the value on the second column
	 * It combines both values into a productoption that would get pulled at a later time
	 */
	public ArrayList<ProductOption> getInitialOptionsList(int rowCount, String targetSheet) {
		ArrayList<ProductOption> optionList = new ArrayList<ProductOption>();
		XSSFSheet excelWSheet = excelWBook.getSheet(targetSheet);
		for(int i=1; i<rowCount; i++) {
			String cellDataMenu="";
			String cellDataOption="";
			try {				
				cellDataMenu = this.getCellData(i, 0, excelWSheet);
				cellDataOption = this.getCellData(i, 1, excelWSheet);
				ProductOption newProductOption = new ProductOption(cellDataMenu,cellDataOption);
//				System.out.println(i + ": " + cellDataMenu + ": " + cellDataOption);
//				System.out.println(i + ": " + newProductOption.originalString + ": " + newProductOption.selection);
				optionList.add(newProductOption);
				
			} catch (Exception e) {
				System.out.println(i+ ": " +e);
			}
		}
//		System.out.println("optionList.size(): " + optionList.size() + " | "+ "tempCount: " + tempCount);
		for(int i=0; i<optionList.size(); i++) {
			System.out.println("[option] " + i + ": " +optionList.get(i).combinedString);
		}
		return optionList;
	}
	
//	public ArrayList<ProductOption> getOptionsList(int column, int rowCount, String targetSheet){
//		ArrayList<ProductOption> optionList = new ArrayList<ProductOption>();
//		XSSFSheet excelWSheet = excelWBook.getSheet(targetSheet);
//		for(int i=0; i<rowCount; i++) {
//			String cellData;
//			try {
//				cellData = this.getCellData(i, column, excelWSheet);			
//				ProductOption tempOption = new ProductOption(cellData);
//				optionList.add(tempOption);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//		return optionList;
//	}
		
	public String getCellData(int RowNum, int ColNum, XSSFSheet excelWSheet) throws Exception{		
		try{
			XSSFCell cell = excelWSheet.getRow(RowNum).getCell(ColNum);
			String cellData = cell.getStringCellValue();
			return cellData;
		}catch (Exception e){
			return"";	
		}	
	}
	
	public String getCellData(int RowNum, int ColNum, String sheetName) throws Exception{
		try {
			XSSFSheet excelWSheet = excelWBook.getSheet(sheetName);
			String cellData = getCellData(RowNum, ColNum,excelWSheet);
			return cellData;
		}catch(Exception e) {
			return"";
		}		
	}
	
	public String getColumnHeader(int colNum, XSSFSheet excelWSheet) throws Exception{
		try {
			String colHeader = getCellData(0, colNum, excelWSheet);
			return colHeader;
		}catch(Exception e) {
			return "";
		}
	}
	
	public int getColumnCount(String sheetName) throws Exception{
		int count=0;
		try {
			XSSFSheet excelWSheet = excelWBook.getSheet(sheetName);
			XSSFCell cell;
			while(true) {
				cell  = excelWSheet.getRow(0).getCell(count);
				if(cell.equals("")) {
					break;
				}else {
					count++;
				}
			}
		}catch(Exception e) {
			System.out.println("getColumnCount ended: " + e);
		}

		return count;
	}
}
