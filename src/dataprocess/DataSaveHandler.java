package dataprocess;

import java.io.File;
import java.io.IOException;

import org.apache.http.util.Asserts;

import dao.HttpDataBean;
import db.excel.ExcelWriter;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class DataSaveHandler {
	private static String file;
	
	private static void initFile(String url){
		Asserts.notEmpty(url, "url");
		String fileName = "";
		if(url.contains("?")){
			fileName = url.substring(url.lastIndexOf("/"), url.indexOf("?")).trim();
		} else if(url.trim().endsWith("/")){
			String temp = url.substring(0, url.lastIndexOf("/"));
			fileName = temp.substring(temp.lastIndexOf("/")).trim();
		} else {
			fileName = url.substring(url.lastIndexOf("/")).trim();
		}
		file = "data" + fileName.replace(":", "-") + ".xls";
	}
	
	public static void saveToExcel(HttpDataBean data) {
		HttpDataBean httpData = ((HttpDataBean) data);
		if(null != httpData.getUrl())
			initFile(httpData.getUrl());
		try {
			writeToExcel(httpData);
		} catch (RowsExceededException e) {
			e.printStackTrace();
		} catch (WriteException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static synchronized void writeToExcel(HttpDataBean httpData) throws RowsExceededException, WriteException, IOException {
		File srcFile = new File(file);
		ExcelWriter book = ExcelWriter.getBook(srcFile);
		WritableSheet sheet = null;
		if(book.getNumberOfSheets() < 1){
			sheet = book.createSheet("data", 0);
		} else {
			sheet = book.getSheet(0);
		}
		int rowCount = sheet.getRows();
		if(rowCount < 1 || !"ID".equals(sheet.getCell(0, 0).getContents())){
			String[] titles = {"ID", "URL","Method","ReqHeader","ReqParams","RspCode","RspMsg","RspHeader","RspBody"};
			for(int i = 0; i < titles.length; i++){
				Label label = new Label(i, 0, titles[i]);
				sheet.addCell(label);
			}
			rowCount = rowCount + 1;
		}
		String id = String.valueOf(httpData.getId());
		sheet.addCell(new Label(0, rowCount, id)); //ExcelReader.getColumn(titles, "ID")
		sheet.addCell(new Label(1, rowCount, httpData.getUrl())); //ExcelReader.getColumn(titles, "URL")
		String method = httpData.getMethod();
		sheet.addCell(new Label(2, rowCount, method)); //ExcelReader.getColumn(titles, "Method")
		sheet.addCell(new Label(3, rowCount, httpData.getRequestHeader())); //ExcelReader.getColumn(titles, "ReqHeader")
		sheet.addCell(new Label(4, rowCount, httpData.getRequestParamsStr())); //ExcelReader.getColumn(titles, "ReqParams")
		sheet.addCell(new Label(5, rowCount, String.valueOf(httpData.getResponseCode()))); //ExcelReader.getColumn(titles, "RespStatus")
		sheet.addCell(new Label(6, rowCount, httpData.getResponseMsg())); //ExcelReader.getColumn(titles, "RespMsg")
		sheet.addCell(new Label(7, rowCount, httpData.getResponseHeader())); //ExcelReader.getColumn(titles, "RespHeader")
		sheet.addCell(new Label(8, rowCount, httpData.getResponseBody())); //ExcelReader.getColumn(titles, "RespBody")
		book.write();
		book.close();
		ExcelWriter.deleteTempFile(srcFile);
	}
	
	public static void main(String[] args) {
		String url = "http://172.23.27.210:8888?";
		initFile(url);
		System.out.println(file);
	}
}
