package db.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import common.util.LogUtil;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableSheet;
import jxl.write.WriteException;
import jxl.write.biff.WritableWorkbookImpl;

public class ExcelWriter extends WritableWorkbookImpl{
	private static Class<?> cl = ExcelWriter.class;
	
	protected ExcelWriter(OutputStream os, boolean cs, WorkbookSettings ws) throws IOException {
		super(os, cs, ws);
	}
	
	protected ExcelWriter(OutputStream os, Workbook w, boolean cs, WorkbookSettings ws) throws IOException{
		super(os, w, cs, ws);
	}

	public static ExcelWriter getBook(File srcFile){
		File tarFile = getTempFile(srcFile);
		return getBook(srcFile, tarFile);
	}
	
	public static ExcelWriter getBook(File sourcefile, File targetFile){
		LogUtil.debug(cl, "sourceFile:" + sourcefile + "; targetFile:" + targetFile);
		if(sourcefile.exists()){
			return copyBook(sourcefile, targetFile);
		} else {
			return createBook(sourcefile);
		}
	}
	
	public static ExcelWriter createBook(File file){
		ExcelWriter excelWriter = null;
		WorkbookSettings ws = new WorkbookSettings();
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
			excelWriter = new ExcelWriter(fos, true, ws);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return excelWriter;
	}
	
	public static ExcelWriter copyBook(File sourcefile, File targetFile){
		ExcelWriter excelWriter = null;
		WorkbookSettings ws = new WorkbookSettings();
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(targetFile);
			FileInputStream in = new FileInputStream(sourcefile); 
			Workbook wb = Workbook.getWorkbook(in); // 获得原始文档  
//			ExcelReader wb = ExcelReader.getExcelReader(sourcefile.getPath());
			excelWriter = new ExcelWriter(fos, wb, true, ws); // 创建一个可读写的副本  
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return excelWriter;
	}
	
	public static File getTempFile(File srcFile){
		String fPath = srcFile.getAbsolutePath();
		String prefix = fPath.substring(0, fPath.lastIndexOf("."));
		String suffix = fPath.substring(fPath.lastIndexOf("."));
		return new File(prefix + "-temp" + suffix);
	}
	
	public static void deleteTempFile(File srcFile){
		File tempFile = getTempFile(srcFile);
		if(tempFile.exists()){
			boolean isDel = srcFile.delete();
			boolean isSucc = tempFile.renameTo(srcFile);
			LogUtil.debug(cl, "is delete sourceFile:" + isDel + "; is rename ok:" + isSucc);
		}
	}

	public static void main(String[] args) throws IOException, WriteException {
		File srcFile = new File("src/data1.xls");
		ExcelWriter book = getBook(srcFile);
		WritableSheet sheet = null;
		if(book.getNumberOfSheets() < 1){
			sheet = book.createSheet("data", 0);
		} else {
			sheet = book.getSheet(0);
		}
		if(sheet.getRows() < 1){
			String[] titles = {"URL","Method","RequestHeader","RequestBody","RequestParameter","ResponseStatus","ResponseMessage","ResponseHeader","ResponseBody"};
			for(int i = 0; i < titles.length; i++){
				Label label = new Label(i, 0, titles[i]);
				sheet.addCell(label);
			}
		}
		WritableCell cell = sheet.getWritableCell(0, 1);
		Label label01 = new Label(0, 1, cell.getContents() + ".baidu.com");
		Label label11 = new Label(1, 1, "GET");
		sheet.addCell(label01);
		sheet.addCell(label11);
		book.write();
		book.close();
		deleteTempFile(srcFile);
	}
}
