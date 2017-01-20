package db.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import jxl.Cell;
import jxl.Sheet;
import jxl.WorkbookSettings;
import jxl.common.Assert;
import jxl.read.biff.BiffException;
import jxl.read.biff.WorkbookParser;

public class ExcelReader extends WorkbookParser {
	
	protected ExcelReader(jxl.read.biff.File file, WorkbookSettings ws) throws BiffException, FileNotFoundException, IOException {
		super(file, ws);
	}
	
	public static ExcelReader getExcelReader(String file){
		ExcelReader excelReader = null;
		File f = new File(file);
		FileInputStream fis = null;
		jxl.read.biff.File datafile = null;
		WorkbookSettings ws = new WorkbookSettings();
		try {
			fis = new FileInputStream(f);
			datafile = new jxl.read.biff.File(fis, ws);
			excelReader = new ExcelReader(datafile, ws);
			excelReader.parse();
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(fis != null)
					fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return excelReader;
	}
	
	public static Cell findCellByContent(Cell[] cells, String content){
		Cell cell = null;
		for(Cell c:cells){
			if(c.getContents().trim().equals(content)){
				cell = c;
				break;
			}else{
				continue;
			}
		}
		return cell;
	}
	
	public static int getRow(Cell[] cells, String content){
		return findCellByContent(cells, content).getRow();
	}
	
	public static int getColumn(Cell[] cells, String content){
		return findCellByContent(cells, content).getColumn();
	}
	
	public static void main(String[] args) {
		ExcelReader book = getExcelReader("src/data1.xls");
		System.out.println("book:" + book.getNumberOfSheets());
		Assert.verify(book.getNumberOfSheets() > 0, "book has not sheet...");
		Sheet sheet = book.getSheet(0);
		Cell[] cells = sheet.getColumn(1);
		Cell cell = null;
		for(Cell c:cells){
			if(c.getContents().trim().equals("GET")){
				cell = c;
				break;
			}else{
				continue;
			}
		}
		System.out.println("column:" + cell.getColumn() + "; row:" + cell.getRow() + "; content:" + cell.getContents());
	}

}
