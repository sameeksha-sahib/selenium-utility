package testScripts;

import org.testng.annotations.Test;

import utility.ExcelExportAndFileIO;

public class TestExcel {

	@Test 
	public void testXLSX() throws Exception {
		
		String sheetName = "Sheet1";		
		String[] dataToWriteXLSX = {"XLSX new1", "XLSX new2"};			
		String fileNameXLSX = "TestXLSX.xlsx";				
				
		ExcelExportAndFileIO.writeExcel( fileNameXLSX, sheetName, dataToWriteXLSX);
		ExcelExportAndFileIO.readExcel( fileNameXLSX, sheetName);

	}
	
	@Test 
	public void testXLS() throws Exception {
		
		String sheetName = "Sheet1";
		String[] dataToWriteXLS = {"XLS new1", "XLS new2"};
		String fileNameXLS = "TestXLS.xls";
		
		ExcelExportAndFileIO.writeExcel( fileNameXLS, sheetName, dataToWriteXLS);
		ExcelExportAndFileIO.readExcel( fileNameXLS, sheetName);

	}
	
	@Test // this should fail
	public void testAnyOher() throws Exception {
		
		String sheetName = "Sheet1";		
		String[] dataToWrite = {"fail", "fail"};
		String fileNameXLSM = "TestAnyOther.xlsm";
		
		ExcelExportAndFileIO.writeExcel( fileNameXLSM, sheetName, dataToWrite);
		ExcelExportAndFileIO.readExcel( fileNameXLSM, sheetName);

	}
}
