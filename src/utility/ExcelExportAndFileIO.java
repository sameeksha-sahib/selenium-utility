package utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Assert;

public class ExcelExportAndFileIO {

	private static String EXCEL_FOLDER_PATH = System.getProperty("user.dir") + "/Excel/";

	public static void writeExcel(String fileName, String sheetName, String[] dataToWrite) throws Exception {
		// Create file input stream
		File file = new File(EXCEL_FOLDER_PATH + fileName);
		FileInputStream inputStream = new FileInputStream(file);

		// Get extension of excel file
		String fileExt = fileName.substring(fileName.indexOf("."));

		// initialize workbook on the basis of excel file extension
		Workbook workbook = null;
		switch (fileExt) {
		case ".xlsx":
			workbook = new XSSFWorkbook(inputStream);
			break;
		case ".xls":
			workbook = new HSSFWorkbook(inputStream);
			break;
		default:
			Assert.fail("Invalid file extension: " + fileExt);
		}

		// Initialize sheet
		Sheet sheet = workbook.getSheet(sheetName);

		// Get number of rows
		int rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();

		// Create new row to write data
		Row newRow = sheet.createRow(rowCount + 1);

		// Create cells and write data
		for (int i = 0; i < dataToWrite.length; ++i) {
			Cell cell = newRow.createCell(i);
			cell.setCellValue(dataToWrite[i]);
		}

		inputStream.close(); // close input stream

		// Create output stream and write in file
		FileOutputStream outputStream = new FileOutputStream(file);
		workbook.write(outputStream);

		outputStream.close(); // close output stream
	}

	public static void readExcel(String fileName, String sheetName) throws Exception {
		// Create file input stream
		File file = new File(EXCEL_FOLDER_PATH + fileName);
		FileInputStream inputStream = new FileInputStream(file);

		// Get extension of excel file
		String fileExt = fileName.substring(fileName.indexOf("."));

		// initialize workbook on the basis of excel file extension
		Workbook workbook = null;
		switch (fileExt) {
		case ".xlsx":
			workbook = new XSSFWorkbook(inputStream);
			break;
		case ".xls":
			workbook = new HSSFWorkbook(inputStream);
			break;
		default:
			Assert.fail("Invalid file extension: " + fileExt);

		}

		// Initialize sheet
		Sheet sheet = workbook.getSheet(sheetName);

		// Get number of rows
		int physicalRowCount = sheet.getPhysicalNumberOfRows();

		// Read data and print on console
		for (int i = 0; i < physicalRowCount; ++i) {
			Row row = sheet.getRow(i);
			for (int j = 0; j < row.getLastCellNum(); ++j) {
				System.out.print(row.getCell(j).getStringCellValue() + "||");
			}

			System.out.println();
		}
	}
}
