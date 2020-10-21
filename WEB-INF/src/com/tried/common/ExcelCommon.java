package com.tried.common;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelCommon {
	
	public XSSFWorkbook  workbook=null;
	public Sheet sheet =null;
	public XSSFCellStyle titleStyle =null;//标题样式
	public XSSFCellStyle headStyle=null;//标题样式
	public Map<String,Integer> columnMap=new HashMap<String, Integer>(); //列坐标
	public XSSFCellStyle dataStyle=null;//数据样式
	public int dataStartRow=0;
	public String sheetTitle=null, worktitle=null;
	public Map<String,String> headTitle;
	public List<Map<String,String>> data;
	/**
	 * 
	 * @param sheetTitle 工作簿名称
	 * @param worktitle  报表名称
	 * @param headTitle  报表标题名称
	 * @param data        数据
	 * @throws Exception
	 */
	public String creatExcel( String sheetTitle,String worktitle,Map<String,String> headTitle,List<Map<String,String>> data) {
		this.sheetTitle=sheetTitle;
		this.worktitle=worktitle;
		this.headTitle=headTitle;
		this.data=data;
		FileOutputStream output=null;
		String fileName=null;
		try{
			fileName=ConfigUtils.getTempDirPath()+ConfigUtils.random32()+".xlsx";
			createWorkBook();
			dataInjection();
			output=new FileOutputStream(fileName);  
	    	workbook.write(output);  
	    	output.flush(); 
	    	output.close();
	    	return fileName;
	}catch(Exception e){
		e.printStackTrace();
	}finally{
		if(output!=null){
			try {
				output.close();
			} catch (IOException e) {
			}
		}
	}
	return fileName;
	} 
	/**
	 * 创建sheet标题，主标题，表头标题
	 * @param sheetTitle sheet标题
	 * @param worktitle   主标题
	 * @param headTitle   表头标题
	 * @throws Exception
	 */
	public void createWorkBook()throws Exception{
		   workbook=new XSSFWorkbook ();  
    	   sheet = workbook.createSheet(sheetTitle); 
    	 if(worktitle!=null&&!worktitle.isEmpty()){
    		 Row titleRow=sheet.createRow(0);
    		 Cell cell=titleRow.createCell(0); 
    		 cell.setCellValue(worktitle); 
    		 cell.setCellStyle(titleCellStyle());
    		 if(headTitle!=null&&headTitle.size()>0){
    			 sheet.addMergedRegion(new CellRangeAddress( 0,  0,  0,(headTitle.size()-1)));
    			 cell.setCellStyle(titleCellStyle());
    			 Row headRow=sheet.createRow(1);
    			 Iterator<String> itKey= headTitle.keySet().iterator();
    			 int i=0;
    			 while(itKey.hasNext()){
    				 String headKey=itKey.next();
    				 Cell headCell=headRow.createCell(i); 
    				 String headValue=(headTitle.get(headKey)!=null)?headTitle.get(headKey):"";
    				 headCell.setCellValue(headValue); 
    				 headCell.setCellStyle(headCellStyle()) ;
    				 columnMap.put(headKey, i);
    				 i++;
    			 }
    			 dataStartRow=2;
    		 }
    	 }else{
    		 if(headTitle!=null&&headTitle.size()>0){
    			 Row headRow=sheet.createRow(0);
    			 Iterator<String> itKey= headTitle.keySet().iterator();
    			 int i=0;
    			 while(itKey.hasNext()){
    				 String headKey=itKey.next();
    				 Cell headCell=headRow.createCell(i); 
    				 String headValue=(headTitle.get(headKey)!=null)?headTitle.get(headKey):"";
    				 headCell.setCellValue(headValue); 
    				 headCell.setCellStyle(headCellStyle()) ;
    				 columnMap.put(headKey, i);
    				 i++;
    			 }
    		 }
    		 dataStartRow=1;
    	 }
    	 
	}
	/**
	 * 数据注入
	 * @param data 数据
	 */
	public void dataInjection(){
	  
		for(int i=0;i<data.size();i++){
			 Row dataRow=sheet.createRow(dataStartRow);//创建数据行
			 Map<String,String> dataMap=data.get(i);
			 Iterator<String> itKey= headTitle.keySet().iterator();
			 while(itKey.hasNext()){  //遍历数据
				 String dataKey=itKey.next();
				 String dataValue=(dataMap.get(dataKey)!=null)?dataMap.get(dataKey):"";
				 int columnIndex=columnMap.get(dataKey);
				 Cell dataCell=dataRow.createCell(columnIndex); 
				 //if(ConfigUtils.isDigit(dataValue)){
				 if(StringUtils.isNumeric(dataValue)&&ConfigUtils.isDigit(dataValue)){
					 dataCell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
					 
					 dataCell.setCellValue( Double.valueOf(dataValue));
				 }else{
					 dataCell.setCellValue(dataValue); 
				 }
				
				 dataCell.setCellStyle(dataCellStyle()) ;
			 }
			 dataStartRow++;
		}
		
	}

	
	/**
	 * 标题样式
	 * @return
	 */
	public XSSFCellStyle titleCellStyle(){
		if(titleStyle!=null){
			return titleStyle;
		}
		if(workbook!=null){
			 titleStyle = (XSSFCellStyle)workbook.createCellStyle(); 
			 titleStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);   
			 titleStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);   
			 Font titleFont = workbook.createFont(); 
			 titleFont.setBoldweight((short) 1);
			 titleFont.setFontHeightInPoints((short)22);  
			 titleStyle.setFont(titleFont);  
			 titleStyle.setBorderBottom(CellStyle.BORDER_THIN);   
			 titleStyle.setBorderTop(CellStyle.BORDER_THIN);   
			 titleStyle.setBorderLeft(CellStyle.BORDER_THIN);   
			 titleStyle.setBorderRight(CellStyle.BORDER_THIN);  
		}
		return titleStyle;
	}
	
	/**
	 * 表头样式
	 * @return
	 */
	public XSSFCellStyle headCellStyle(){
		if(headStyle!=null){
			return headStyle;
		}
		if(workbook!=null){
			 headStyle = (XSSFCellStyle)workbook.createCellStyle(); 
			 headStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);   
			 headStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);   
			 Font headFont = workbook.createFont(); 
			 headFont.setBoldweight((short) 1);
			 headFont.setFontHeightInPoints((short)12);  
			 headStyle.setFont(headFont);   
			 headStyle.setBorderBottom(CellStyle.BORDER_THIN);   
			 headStyle.setBorderTop(CellStyle.BORDER_THIN);   
			 headStyle.setBorderLeft(CellStyle.BORDER_THIN);   
			 headStyle.setBorderRight(CellStyle.BORDER_THIN); 
		}
		return headStyle;
	}
	
	/**
	 * 数据样式
	 * @return
	 */
	public XSSFCellStyle dataCellStyle(){
		if(dataStyle!=null){
			return dataStyle;
		}
		if(workbook!=null){
			dataStyle = (XSSFCellStyle)workbook.createCellStyle(); 
			dataStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);   
			dataStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);   
			 Font titleFont = workbook.createFont(); 
			 titleFont.setFontHeightInPoints((short)12);  
			 dataStyle.setFont(titleFont);  
			 dataStyle.setBorderBottom(CellStyle.BORDER_THIN);   
			 dataStyle.setBorderTop(CellStyle.BORDER_THIN);   
			 dataStyle.setBorderLeft(CellStyle.BORDER_THIN);   
			 dataStyle.setBorderRight(CellStyle.BORDER_THIN);  
		}
		return dataStyle;
	}
    public static void main(String[] args) throws Exception{
    	Map<String,String> headMap=new LinkedHashMap<String, String>();
    	headMap.put("head1", "字段1");
    	headMap.put("head2", "字段2");
    	headMap.put("head3", "字段3");
    	headMap.put("head4", "字段4");
    	headMap.put("head5", "字段5");
    	headMap.put("head6", "字段6");
    
  
    	
    	List<Map<String,String>> list=new LinkedList<Map<String,String>>();
    	Map<String,String> map=new HashMap<String, String>();
    	map.put("head1", "1");
    	map.put("head2", "2");
    	map.put("head3", "3");
    	map.put("head4", "4");
    	map.put("head5", "5");
    	map.put("head6", "6");
    	list.add(map);
    	map=new HashMap<String, String>();
    	map.put("head1", "你猜");
     	list.add(map);
     	map=new HashMap<String, String>();
    	map.put("head5", "我哪知道");
    	list.add(map);
    	//String filePath= new ExcelCommon().creatExcel("测试sheet","测试",headMap,dataList);    	
    	//输出Excel文件  
    	 
    }
    
}
