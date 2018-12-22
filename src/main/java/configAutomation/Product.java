package configAutomation;

import java.util.ArrayList;

public class Product {
	ArrayList<ProductOption> optionsConfiguration;
	String sheetName;
	String productName;
	String productNameParsed;
	
	//TODO: Product should make use of a 2D ArrayList
	public Product(String targetSheet, String targetProduct) {
		optionsConfiguration = new ArrayList<ProductOption>(); 
		sheetName = targetSheet;
		productName = targetProduct;//may need to parse out characters that can't be used in a filename 
		productNameParsed = productName.replace('/','_')
										.replace('\\','_')
										.replace(':','_')
										.replace('*','_')
										.replace('?','_')
										.replace('\"','_')
										.replace('>','_')
										.replace('<','_')
										.replace('|','_');
	}
	
	public int getOptionCount() {
		System.out.println("optionsConfiguration.size(): "+optionsConfiguration.size());
		return optionsConfiguration.size();
	}
	
	public void extractTaggedOptions(int rowCount, ArrayList<ProductOption> completeOptionsList, ExcelUtilities excelUtilities) {
		//find column# from productName
		int columnId = getColumnId(excelUtilities);
		String tempData = "";
		//start with one to skip the header
		System.out.println("rowCount: " + rowCount);
		for(int i=0; i<rowCount; i++) {			
			try {
				tempData = excelUtilities.getCellData(i, columnId, sheetName);
				System.out.println("extractTaggedOption["+columnId+"]["+i+"]: " + tempData);
			} catch (Exception e) {
				tempData = "";
				e.printStackTrace();
			}
			
			if(tempData.equals("x")) {
				//then get equivalent from completeOptionsList
				ProductOption optionFromCompleteList = completeOptionsList.get(i-1);
				System.out.println("Add to options: " + optionFromCompleteList.combinedString);
				//add to optionsConfiguration
//				System.out.println("Adding "+optionFromCompleteList.originalString+" to optionsConfiguration");				
				optionsConfiguration.add(optionFromCompleteList);
			}
		}
		
		for(int i=0; i<optionsConfiguration.size(); i++) {
			System.out.println("[config] " + i + ": " +optionsConfiguration.get(i).combinedString);
		}
	}
	
	public ProductOption getOption(int index) {
		return optionsConfiguration.get(index);
	}
	/**
	 * Gets the columnId corresponding to this product's name
	 * @param excelUtilities
	 * @return
	 */
	private int getColumnId(ExcelUtilities excelUtilities) {
		int columnId = -1;
		int columnCount;
		try {
			columnCount = excelUtilities.getColumnCount(sheetName);
		} catch (Exception e1) {
			System.out.println("getColumnId: columnCount error");
			columnCount=0;
			e1.printStackTrace();
		}
		String tempColumnName = "";
		for(int i=0; i<columnCount; i++ ) {
			try {
				tempColumnName = excelUtilities.getCellData(0, i, sheetName);
			} catch (Exception e) {
				tempColumnName = "";
				e.printStackTrace();
			}
			System.out.println(i + ": " + productName + "=? " +tempColumnName);
			if(tempColumnName.equals(productName)) {
				columnId = i;
				break;
			}
			
			
		}
		return columnId;
	}
	
}
