package com.tried.common;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections.IteratorUtils;
import org.apache.log4j.Logger;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFFooter;
import org.apache.poi.xwpf.usermodel.XWPFHeader;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.xmlbeans.XmlOptions;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBody;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

/**
 * 适用于word 2007 poi 版本 3.7
 */
public class WordUtil {
	private static Logger logger = Logger.getLogger(WordUtil.class);
	private static final int wdFormatPDF = 17;
	private static final int xlTypePDF = 0;
	private static final int ppSaveAsPDF = 32;
	private static final int msoTrue = -1;
	private static final int msofalse = 0;

	public static byte[] inputStream2ByteArray(InputStream in, boolean isClose) {
		byte[] byteArray = null;
		try {
			int total = in.available();
			byteArray = new byte[total];
			in.read(byteArray);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (isClose) {
				try {
					in.close();
				} catch (Exception e2) {
					System.out.println("关闭流失败");
				}
			}
		}
		return byteArray;
	}

	/**
	 * 根据指定的参数值、模板，生成 word 文档
	 * @title: generateWord
	 * @author: lyw
	 * @date : 2019-1-31 上午9:49:58
	 * @version: v1.0
	 * @param param  需要替换的变量
	 * @param template  模板
	 */
	public static CustomXWPFDocument generateWord(Map<String, Object> param,
			String template) {
		CustomXWPFDocument doc = null;
		try {
			OPCPackage pack = POIXMLDocument.openPackage(template);
			doc = new CustomXWPFDocument(pack);
			if (param != null && param.size() > 0) {
				//处理页眉
				proccessHeader(doc,param);
				//处理正文段落
			 	proccessBodyParagraph(doc,param);
				//处理正文表格
				proccessBodyTable(doc,param);
				//处理页脚
				proccessFooter(doc,param);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return doc;
	}
	/**
	 * 处理页眉
	 * @title: proccessHeader
	 * @author: lyw
	 * @date : 2019-1-31 上午9:33:51
	 * @version: v1.0
	 * @param doc  文档
	 * @param param  需要替换的变量
	 */
	public static void proccessHeader(CustomXWPFDocument doc,Map<String, Object> param){
		List<XWPFHeader> pageHeaders = doc.getHeaderList();
		for(int i=0;i<pageHeaders.size();i++){
			List<XWPFParagraph> headerPara = pageHeaders.get(i).getParagraphs();
			handleParagraphs(headerPara, param, doc);
			
			List<XWPFTable> headTables = pageHeaders.get(i).getTables(); 
			handleTable(doc,headTables,param);
		}
	}
	
	/**
	 * 处理正文-段落
	 * @title: proccessBodyParagraph
	 * @author: lyw
	 * @date : 2019-1-31 上午9:37:18
	 * @version: v1.0
	 * @param doc  文档
	 * @param param  需要替换的变量
	 */
	public static void proccessBodyParagraph(CustomXWPFDocument doc,Map<String, Object> param){
		List<XWPFParagraph> paragraphList = doc.getParagraphs();
		handleParagraphs(paragraphList, param, doc);
	}
	
	/**
	 * 处理正文-table
	 * @title: proccessBodyTable
	 * @author: lyw
	 * @date : 2019-1-31 上午9:37:53
	 * @version: v1.0
	 * @param doc  文档
	 * @param param  需要替换的变量
	 */
	public static void proccessBodyTable(CustomXWPFDocument doc,Map<String, Object> param){
		Iterator<XWPFTable> it = doc.getTablesIterator();
		List<XWPFTable> tables = IteratorUtils.toList(it);
		handleTable(doc,tables,param);
	}

	/**
	 * 处理页脚
	 * @title: proccessFooter
	 * @author: lyw
	 * @date : 2019-1-31 上午9:56:26
	 * @version: v1.0
	 * @param doc  文档
	 * @param param  需要替换的变量
	 */
	public static void proccessFooter(CustomXWPFDocument doc,Map<String, Object> param){
		List<XWPFFooter> pageFooters = doc.getFooterList();
		for(int i=0;i<pageFooters.size();i++){
			List<XWPFParagraph> footerPara = pageFooters.get(i).getParagraphs();
			handleParagraphs(footerPara, param, doc);
			
			List<XWPFTable> headTables = pageFooters.get(i).getTables(); 
			handleTable(doc,headTables,param);
		}
	}
	
	/**
	 * 处理table
	 * @title: handleTable
	 * @author: lyw
	 * @date : 2019-1-31 上午9:44:39
	 * @version: v1.0
	 */
	public static void handleTable(CustomXWPFDocument doc,List<XWPFTable> tables,Map<String, Object> param){
		for(XWPFTable ht:tables){
			List<XWPFTableRow> rows = ht.getRows();
			for (XWPFTableRow row : rows) {
				List<XWPFTableCell> cells = row.getTableCells();
				for (XWPFTableCell cell : cells) {
					List<XWPFParagraph> paragraphListTable = cell.getParagraphs();
					handleParagraphs(paragraphListTable, param, doc);
					//处理表格嵌套问题	start	
					List<XWPFTable> secTables=cell.getTables();
					for(XWPFTable secTable:secTables){
						List<XWPFTableRow> secrows =secTable.getRows();
						for (XWPFTableRow secrow : secrows) {
							List<XWPFTableCell> seccells = secrow.getTableCells();
							for (XWPFTableCell seccell : seccells) {
								List<XWPFParagraph> escparagraphListTable = seccell.getParagraphs();
								handleParagraphs(escparagraphListTable, param, doc);
							}
						}
					}//处理表格嵌套问题	end	
				}
			}
		}
	}
	
	/**
	 * 处理段落
	 * @title: handleParagraphs
	 * @author: lyw
	 * @date : 2019-1-31 上午9:49:37
	 * @version: v1.0
	 */
    static int i=0;
	public static void handleParagraphs(List<XWPFParagraph> paragraphList,
			Map<String, Object> param, CustomXWPFDocument doc) {
		if (paragraphList != null && paragraphList.size() > 0) {
			for (XWPFParagraph paragraph : paragraphList) {
				List<XWPFRun> runs = paragraph.getRuns();
				for (XWPFRun run : runs) {
					String text = run.getText(0);
					if (text != null) {
						boolean isSetText = false;
						for (Entry<String, Object> entry : param.entrySet()) {
							String key = entry.getKey();
							if (text.indexOf(key) != -1) {
								isSetText = true;
								Object value = entry.getValue();
								if (value instanceof String) {// 文本替换
									text = text.replace(key, value.toString());
								} else if (value instanceof Map) {// 图片替换
									text = text.replace(key, "");
									
									Map pic = (Map) value;
									int width = Integer.parseInt(pic.get( "width").toString());
									int height = Integer.parseInt(pic.get( "height").toString());
									int picType = getPictureType(pic .get("type").toString());
									byte[] byteArray = (byte[]) pic .get("content");
									ByteArrayInputStream byteInputStream = new ByteArrayInputStream(byteArray);
									try {
										int ind = doc.addPicture(byteInputStream, picType);
										String blipId =doc.getAllPictures().get(ind).getPackageRelationship().getId();  
										
										int id=doc.getNextPicNameNumber(getPictureType(pic .get("type").toString()));
										  if(i!=0){
												doc.createPicture(blipId,i, width, height,paragraph);
											  i++;
										  }else{
												doc.createPicture(blipId,id, width, height,paragraph);
											  i=id+1;
										  }
									
										
									} catch (Exception e) {
										e.printStackTrace();
									}
								}
							}
						}
						if (isSetText) {
							run.setText(text, 0);
						}
					}
				}
			}
		}
	}

	/**
	 * 根据图片类型，取得对应的图片类型代码
	 * @title: getPictureType
	 * @author: lyw
	 * @date : 2019-1-31 上午9:49:25
	 * @version: v1.0
	 */
	private static int getPictureType(String picType) {
		int res = CustomXWPFDocument.PICTURE_TYPE_PICT;
		if (picType != null) {
			if (picType.equalsIgnoreCase("png")) {
				res = CustomXWPFDocument.PICTURE_TYPE_PNG;
			} else if (picType.equalsIgnoreCase("dib")) {
				res = CustomXWPFDocument.PICTURE_TYPE_DIB;
			} else if (picType.equalsIgnoreCase("emf")) {
				res = CustomXWPFDocument.PICTURE_TYPE_EMF;
			} else if (picType.equalsIgnoreCase("jpg")
					|| picType.equalsIgnoreCase("jpeg")) {
				res = CustomXWPFDocument.PICTURE_TYPE_JPEG;
			} else if (picType.equalsIgnoreCase("wmf")) {
				res = CustomXWPFDocument.PICTURE_TYPE_WMF;
			}
		}
		return res;
	}
	/**
	 * @title: conver2PDF
	 * @author: lyw
	 * @date : 2019-1-31 上午9:49:00
	 * @version: v1.0
	 */
	public static boolean conver2PDF(String inputFile, String pdfFile) {
		String suffix = getFileSufix(inputFile);
		File file = new File(inputFile);
		if (!file.exists()) {
			System.out.println("文件不存在!");
			return false;
		}
		if (suffix.equals("pdf")) {
			System.out.println("PDF not need to convert!");
			return false;
		}
		if (suffix.equals("doc") || suffix.equals("docx")
				|| suffix.equals("txt")) {
			return word2PDF(inputFile, pdfFile);
		} else if (suffix.equals("ppt") || suffix.equals("pptx")) {
			return ppt2PDF(inputFile, pdfFile);
		} else if (suffix.equals("xls") || suffix.equals("xlsx")) {
			return excel2PDF(inputFile, pdfFile);
		} else {
			System.out.println("文件格式不支持转换!");
			return false;
		}
	}

	/**
	 * Word 转 PDF
	 * @title: word2PDF
	 * @author: lyw
	 * @date : 2019-1-31 上午9:48:41
	 * @version: v1.0
	 */
	public static boolean word2PDF(String inputFile, String pdfFile) {
		ComThread.InitSTA();
		ActiveXComponent app = null;
		Dispatch doc = null;
		try {
			app = new ActiveXComponent("Word.Application");
			app.setProperty("Visible", new Variant(false));
			Dispatch docs = app.getProperty("Documents").toDispatch();

			doc = Dispatch.call(docs, "open", inputFile, false, true)
					.toDispatch();
			Dispatch.call(doc, "ExportAsFixedFormat", pdfFile, wdFormatPDF);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}finally{
			Dispatch.call(doc, "Close", false);
			if(app!=null){
				app.invoke("Quit", 0);	
			}
			ComThread.Release();
			ComThread.quitMainSTA();
		}
	}
	/**
	 * Excel 转 PDF
	 * @title: excel2PDF
	 * @author: lyw
	 * @date : 2019-1-31 上午9:48:30
	 * @version: v1.0
	 */
	public static boolean excel2PDF(String inputFile, String pdfFile) {
		ComThread.InitSTA();
		
		ActiveXComponent app = null;
		Dispatch excel = null;
		try {
			app = new ActiveXComponent("Excel.Application");
			app.setProperty("Visible", new Variant(false));
			Dispatch excels = app.getProperty("Workbooks").toDispatch();
			excel = Dispatch.call(excels, "Open", inputFile, false,
					true).toDispatch();
			Dispatch.call(excel, "ExportAsFixedFormat", xlTypePDF, pdfFile);
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}finally{
			Dispatch.call(excel, "Close", false);
			if(app!=null){
				app.invoke("Quit");
			}
			ComThread.Release();
			ComThread.quitMainSTA();
		}
	}
	/**
	 * PPT 转 PDF
	 * @title: ppt2PDF
	 * @author: lyw
	 * @date : 2019-1-31 上午9:48:18
	 * @version: v1.0
	 */
	public static boolean ppt2PDF(String inputFile, String pdfFile) {
		ComThread.InitSTA();
		ActiveXComponent app  = null;
		Dispatch ppt = null;
		try {
			app = new ActiveXComponent("PowerPoint.Application");
			Dispatch ppts = app.getProperty("Presentations").toDispatch();

			ppt = Dispatch.call(ppts, "Open", inputFile, true,// ReadOnly
					true,// Untitled指定文件是否有标题
					false// WithWindow指定文件是否可见
					).toDispatch();

			Dispatch.call(ppt, "SaveAs", pdfFile, ppSaveAsPDF);
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}finally{
			Dispatch.call(ppt, "Close");
			if(app!=null){
				app.invoke("Quit");
			}
			ComThread.Release();
			ComThread.quitMainSTA();
		}
	}

	public static String getFileSufix(String fileName) {
		int splitIndex = fileName.lastIndexOf(".");
		return fileName.substring(splitIndex + 1);
	}

	/**
	 * @Description 替换word文档中指定位置的对象
	 * @author liuxd
	 * @date 2016-12-14 上午10:05:36
	 * @version V1.0
	 */
	public static void replaceWordObject(Map<String, Object> param, String srcFilePath, String outFilePath)  {
		
		FileOutputStream fopts = null;
		CustomXWPFDocument doc = null;
		try {
			doc = generateWord(param, srcFilePath);
			fopts = new FileOutputStream(outFilePath);
			doc.write(fopts);
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			if (fopts != null) {
				try {
					fopts.close();
				} catch (IOException ie) {
					logger.error(ie.getMessage());
				}
			}
			
		}
		
	}
	/**
	 * 合并多个word文档
	 * @title: mergeDoc
	 * @author: lyw
	 * @date : 2019-1-30 上午11:02:25
	 * @version: v1.0
	 */
	public static void mergeDoc(String[] srcDocxs,String destDocx){
		OutputStream dest = null;
		List<OPCPackage> opcpList = new ArrayList<OPCPackage>();
		int length = null == srcDocxs ? 0 : srcDocxs.length;
		//循环获取每个docx文件的OPCPackage对象
		for (int i = 0; i < length; i++) {
			String doc = srcDocxs[i];
			OPCPackage srcPackage =  null;
			try {
				srcPackage = OPCPackage.open(doc);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(null != srcPackage){
				opcpList.add(srcPackage);
			}
		}
		int opcpSize = opcpList.size();
		if(opcpSize > 0){//获取的OPCPackage对象大于0时，执行合并操作
			try {
				dest = new FileOutputStream(destDocx);
				XWPFDocument src1Document = new XWPFDocument(opcpList.get(0));
				CTBody src1Body = src1Document.getDocument().getBody();//OPCPackage大于1的部分执行合并操作
				if(opcpSize > 1){
					for (int i = 1; i < opcpSize; i++) {
						OPCPackage src2Package = opcpList.get(i);
						XWPFDocument src2Document = new XWPFDocument(src2Package);
						CTBody src2Body = src2Document.getDocument().getBody();
						appendBody(src1Body, src2Body);
					}
				}//将合并的文档写入目标文件中
				src1Document.write(dest);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				try {
					dest.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 合并文档内容
	 * @param src 目标文档
	 * @param append 要合并的文档
	 * @throws Exception
	 */
	private static void appendBody(CTBody src, CTBody append) throws Exception {
		XmlOptions optionsOuter = new XmlOptions();
		optionsOuter.setSaveOuter();
		String appendString = append.xmlText(optionsOuter);
		String srcString = src.xmlText();
		String prefix = srcString.substring(0, srcString.indexOf(">") + 1);
		String mainPart = srcString.substring(srcString.indexOf(">") + 1,
				srcString.lastIndexOf("<"));
		String sufix = srcString.substring(srcString.lastIndexOf("<"));
		String addPart = appendString.substring(appendString.indexOf(">") + 1,
				appendString.lastIndexOf("<"));
		CTBody makeBody = CTBody.Factory.parse(prefix + mainPart + addPart
				+ sufix);
		src.set(makeBody);
	}
	
	/**
	  * @Description 测试
	  * @author liuxd
	  * @date 2016-12-14 下午4:20:41
	  * @version V1.0
	 * @throws Exception 
	 */
	
	public static void main(String[] args) throws Exception {
		
		/* word文档合并测试
		String[] srcDocxs = {"d:\\a\\布线、操作性能和功能.docx","d:\\a\\冲击耐受电压试验.docx"};
		String destDocx = "d:\\a\\test.docx";
		mergeDoc(srcDocxs, destDocx);*/
		
		  //long startTime = System.currentTimeMillis();
		   //String  srcFile="D:\\project\\dg-workspace\\FileDataBase\\4d15b33b5d14404ba30e6fa03cf4edfd.docx";
		   String  srcFile="C:\\Users\\SUNLUNAN\\Desktop\\电工院模板\\m1\\13W20190039-东莞建工-着火.docx";
	       String targetFile="C:\\Users\\SUNLUNAN\\Desktop\\电工院模板\\m2\\13W20190039-东莞建工-着火.docx";
	       String pdfFile = "C:\\Users\\SUNLUNAN\\Desktop\\电工院模板\\m2\\9fa45724a6c240b6be5c67904ef11067.pdf";
	       
	        Map<String,Object> param = new HashMap<String, Object>();
	        Map<String,Object>  hyHeader = new HashMap<String, Object>();  
	        hyHeader.put("width", 80);  
	        hyHeader.put("height", 35);  
	        hyHeader.put("type", "png");  
	        hyHeader.put("content", WordUtil.inputStream2ByteArray(new FileInputStream("C:\\Users\\SUNLUNAN\\Desktop\\zsf.png"), true));  
	        param.put("${ZJP}",hyHeader);
	        param.put("${SHP}",hyHeader);
	        param.put("${QFP}",hyHeader);
	      
	        replaceWordObject(param,srcFile,targetFile);
	    	//conver2PDF(targetFile,pdfFile);
 
	     //   File file=new File(targetFile);
	      //  if(file.exists()){
	     //   	file.delete();
	     //   }
		  //  	Map<String,Object> param = new HashMap<String, Object>(); 
				//param.put("${sampleName}", "样品名称：1121313 \n 样品类别 \n");
			//	param.put("${sampleList}", "样品名称：1121313 \r\n样品类别\r\n hjh");
//				param.put("${proxyPerson}", "张三");
//				param.put("${testYj}", "GB/T 7251.12-2013");
//				param.put("${testType}", "3C申请");
//				param.put("${reportNum}", "A2019-CCC-001");
 			//param.put("${sampleName}", "电机");
//				param.put("${sampleGuige}", "3000瓦");
//				param.put("${cardDate}", "2019-02-18");
//				param.put("${cardPerson}", "王小青");
//				param.put("${cardCheckPerson}", "张金水"); 
// 	         
//		        Map<String,Object>  hyHeader = new HashMap<String, Object>();  
//		        hyHeader.put("width", 80);  
//		        hyHeader.put("height", 35);  
//		        hyHeader.put("type", "png");  
//		        hyHeader.put("content", WordUtil.inputStream2ByteArray(new FileInputStream("E:\\资料\\电子签名\\fubaoxin.png"), true));  
//		       
//		        param.put("${cardPerson}",hyHeader);
//		        param.put("${cardCheckPerson}",hyHeader);
		    //	param.put("${checkTcNum}", "4");
		     //   new WordUtil().replaceWordObject(param,srcFile,targetFile);
		       // String qrcodeImg="C:\\Users\\angel\\Desktop\\报告\\reportpng.png";
		      //  ConfigUtils.getPropertyByName(path, name)
		      //  new JacobUtil().copyQrcode(targetFile, qrcodeImg, 480, 10);
		     //   long endTime = System.currentTimeMillis();
			//	System.out.println("执行时间为：" + (endTime - startTime) / 1000F);  
				//111111111111
				
	            //	XSSFWorkbook xwb = new XSSFWorkbook(); 
		    	//	XSSFSheet sheet0 = xwb.createSheet("分析报告图表"); ;
		    		//设置列宽、行高
		    		//sheet0.setDefaultColumnWidth(16); 
		    		//sheet0.setDefaultRowHeightInPoints(20);
		            
		            
					
		    	  
				
	}
	
	

	
	  /** 
     * 导出excel表格样式定义
     */  
    public static  Map<String, XSSFCellStyle> createStyles(XSSFWorkbook wb) {  
        Map<String, XSSFCellStyle> styles = new HashMap<String, XSSFCellStyle>();        
        XSSFCellStyle style;
        //字体12号
        XSSFFont font1=wb.createFont(); 
        font1.setFontHeightInPoints((short)11);
        font1.setFontName("宋体");
       /// font1.setBoldweight( XSSFFont.BOLDWEIGHT_BOLD); 
        XSSFFont font2=wb.createFont(); 
        font2.setFontHeightInPoints((short)10);
        font2.setFontName("仿宋");
        XSSFFont font3=wb.createFont(); 
        font3.setFontHeightInPoints((short)11);
        font3.setBold(true);
        font3.setFontName("仿宋");
        
        XSSFFont font14=wb.createFont(); 
        font14.setFontHeightInPoints((short)15);
        font14.setFontName("宋体");
        
        XSSFFont Tahoma=wb.createFont(); 
        Tahoma.setFontHeightInPoints((short)11);
        Tahoma.setFontName("Tahoma");
        
        
        style = wb.createCellStyle(); 
        style.setAlignment(XSSFCellStyle.ALIGN_LEFT);  
        style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);  
        style.setWrapText(true);  
        style.setFont(font14);
        styles.put("font14", style);  
        //单元格样式居中对齐
        style = wb.createCellStyle();  
        style.setAlignment(XSSFCellStyle.ALIGN_CENTER);  
        style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);  
        style.setWrapText(true);  
        style.setBorderRight(XSSFCellStyle.BORDER_THIN);  
        style.setFont(font1);
        style.setRightBorderColor(new XSSFColor( new Color(0, 0, 0)));  
        style.setBorderLeft(XSSFCellStyle.BORDER_THIN);  
        style.setLeftBorderColor(new XSSFColor( new Color(0, 0, 0)));  
        style.setBorderTop(XSSFCellStyle.BORDER_THIN);  
        style.setTopBorderColor(new XSSFColor( new Color(0, 0, 0)));  
        style.setBorderBottom(XSSFCellStyle.BORDER_THIN);  
        style.setBottomBorderColor(new XSSFColor( new Color(0, 0, 0)));  
        styles.put("cellCenter", style);        
        
        //单元格样式右对齐
        style = wb.createCellStyle();  
        style.setAlignment(XSSFCellStyle.ALIGN_RIGHT);  
        style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);  
        style.setWrapText(true);  
        style.setFont(font1);
        style.setBorderRight(XSSFCellStyle.BORDER_THIN);  
        style.setRightBorderColor(new XSSFColor( new Color(0, 0, 0)));  
        style.setBorderLeft(XSSFCellStyle.BORDER_THIN);  
        style.setLeftBorderColor(new XSSFColor( new Color(0, 0, 0)));  
        style.setBorderTop(XSSFCellStyle.BORDER_THIN);  
        style.setTopBorderColor(new XSSFColor( new Color(0, 0, 0)));  
        style.setBorderBottom(XSSFCellStyle.BORDER_THIN);  
        style.setBottomBorderColor(new XSSFColor( new Color(0, 0, 0)));  
        styles.put("cellRight", style);  
        //单元格样式左对齐
        style = wb.createCellStyle();  
        style.setAlignment(XSSFCellStyle.ALIGN_LEFT);  
        style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);  
        style.setWrapText(true);  
        style.setFont(font1);
        style.setBorderRight(XSSFCellStyle.BORDER_THIN);  
        style.setRightBorderColor(new XSSFColor( new Color(0, 0, 0)));  
        style.setBorderLeft(XSSFCellStyle.BORDER_THIN);  
        style.setLeftBorderColor(new XSSFColor( new Color(0, 0, 0)));  
        style.setBorderTop(XSSFCellStyle.BORDER_THIN);  
        style.setTopBorderColor(new XSSFColor( new Color(0, 0, 0)));  
        style.setBorderBottom(XSSFCellStyle.BORDER_THIN);  
        style.setBottomBorderColor(new XSSFColor( new Color(0, 0, 0)));  
        styles.put("cellLeft", style);
        
        

        style = wb.createCellStyle();  
        style.setAlignment(XSSFCellStyle.ALIGN_LEFT);  
        style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);  
        style.setWrapText(true); 
        style.setFont(Tahoma);
        style.setBorderRight(XSSFCellStyle.BORDER_THIN);  
        style.setRightBorderColor(new XSSFColor( new Color(0, 0, 0)));  
        style.setBorderLeft(XSSFCellStyle.BORDER_THIN);  
        style.setLeftBorderColor(new XSSFColor( new Color(0, 0, 0)));  
        style.setBorderTop(XSSFCellStyle.BORDER_THIN);  
        style.setTopBorderColor(new XSSFColor( new Color(0, 0, 0)));  
        style.setBorderBottom(XSSFCellStyle.BORDER_THIN);  
        style.setBottomBorderColor(new XSSFColor( new Color(0, 0, 0)));  
        styles.put("cellBlodLeft", style); 
        //左上
        style = wb.createCellStyle();  
        style.setAlignment(XSSFCellStyle.ALIGN_LEFT);  
        style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);  
        style.setWrapText(true);  
        style.setFont(font1);    
        style.setBorderLeft(XSSFCellStyle.BORDER_THIN);  
        style.setLeftBorderColor(new XSSFColor( new Color(0, 0, 0)));  
        style.setBorderTop(XSSFCellStyle.BORDER_THIN);  
        style.setTopBorderColor(new XSSFColor( new Color(0, 0, 0)));       
        styles.put("zs", style);
        //左
        style = wb.createCellStyle();  
        style.setAlignment(XSSFCellStyle.ALIGN_LEFT);  
        style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);  
        style.setWrapText(true);  
        style.setFont(font1);    
        style.setBorderLeft(XSSFCellStyle.BORDER_THIN);  
        style.setLeftBorderColor(new XSSFColor( new Color(0, 0, 0)));       
        styles.put("zz", style);
        //左下
        style = wb.createCellStyle();  
        style.setAlignment(XSSFCellStyle.ALIGN_LEFT);  
        style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);  
        style.setWrapText(true);  
        style.setFont(font1);    
        style.setBorderLeft(XSSFCellStyle.BORDER_THIN);  
        style.setLeftBorderColor(new XSSFColor( new Color(0, 0, 0)));  
        style.setBorderBottom(XSSFCellStyle.BORDER_THIN);  
        style.setBottomBorderColor(new XSSFColor( new Color(0, 0, 0)));       
        styles.put("zx", style);
       
        //中上
        style = wb.createCellStyle();  
        style.setAlignment(XSSFCellStyle.ALIGN_LEFT);  
        style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);  
        style.setWrapText(true);  
        style.setFont(font1);    
        style.setBorderTop(XSSFCellStyle.BORDER_THIN);  
        style.setTopBorderColor(new XSSFColor( new Color(0, 0, 0)));      
        styles.put("cs", style);
        
        //中下
        style = wb.createCellStyle();  
        style.setAlignment(XSSFCellStyle.ALIGN_LEFT);  
        style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);  
        style.setWrapText(true);  
        style.setFont(font1);    
        style.setBorderBottom(XSSFCellStyle.BORDER_THIN);  
        style.setBottomBorderColor(new XSSFColor( new Color(0, 0, 0)));        
        styles.put("cx", style);
        //右上
        style = wb.createCellStyle();  
        style.setAlignment(XSSFCellStyle.ALIGN_LEFT);  
        style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);  
        style.setWrapText(true);  
        style.setFont(font1);
        style.setBorderRight(XSSFCellStyle.BORDER_THIN);  
        style.setRightBorderColor(new XSSFColor( new Color(0, 0, 0)));          
        style.setBorderTop(XSSFCellStyle.BORDER_THIN);  
        style.setTopBorderColor(new XSSFColor( new Color(0, 0, 0)));        
        styles.put("ys", style); 
        //右右
        style = wb.createCellStyle();  
        style.setAlignment(XSSFCellStyle.ALIGN_LEFT);  
        style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);  
        style.setWrapText(true);  
        style.setFont(font1);
        style.setBorderRight(XSSFCellStyle.BORDER_THIN);  
        style.setRightBorderColor(new XSSFColor( new Color(0, 0, 0)));                 
        styles.put("yy", style); 
      //右下
        style = wb.createCellStyle();  
        style.setAlignment(XSSFCellStyle.ALIGN_LEFT);  
        style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);  
        style.setWrapText(true);  
        style.setFont(font1);
        style.setBorderRight(XSSFCellStyle.BORDER_THIN);  
        style.setRightBorderColor(new XSSFColor( new Color(0, 0, 0)));      
        style.setBorderBottom(XSSFCellStyle.BORDER_THIN);  
        style.setBottomBorderColor(new XSSFColor( new Color(0, 0, 0)));  
        styles.put("yx", style); 
        
        //最左边
        style = wb.createCellStyle();  
        style.setAlignment(XSSFCellStyle.ALIGN_RIGHT);
        style.setWrapText(true);
        style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);  
        style.setBorderLeft(XSSFCellStyle.BORDER_THIN);  
        style.setBorderBottom(XSSFCellStyle.BORDER_THIN);  
        style.setBottomBorderColor(new XSSFColor( new Color(0, 0, 0)));  
        style.setLeftBorderColor(new XSSFColor( new Color(0, 0, 0)));  
        styles.put("tableLeft", style);  
        //最中间
        style = wb.createCellStyle();  
        style.setAlignment(XSSFCellStyle.ALIGN_CENTER);   
        style.setWrapText(true);
        style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);  
        style.setBorderBottom(XSSFCellStyle.BORDER_THIN);  
        style.setBottomBorderColor(new XSSFColor( new Color(0, 0, 0)));  
        styles.put("tableCenter", style);  
        //最右边
        style = wb.createCellStyle();  
        style.setAlignment(XSSFCellStyle.ALIGN_RIGHT);  
        style.setWrapText(true);
        style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);  
        style.setBorderRight(XSSFCellStyle.BORDER_THIN);  
        style.setRightBorderColor(new XSSFColor( new Color(0, 0, 0))); 
        styles.put("tableCenter", style);  
        
        
        
        //
        
        //最左上边边框加粗
        style = wb.createCellStyle();  
        style.setAlignment(XSSFCellStyle.ALIGN_CENTER);  
        style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);  
        style.setWrapText(true);  
        style.setBorderRight(XSSFCellStyle.BORDER_THIN);  
        style.setFont(font3);
        style.setRightBorderColor(new XSSFColor( new Color(0, 0, 0)));  
        style.setBorderLeft(XSSFCellStyle.BORDER_MEDIUM );  
        style.setLeftBorderColor(new XSSFColor( new Color(0, 0, 0)));  
        style.setBorderTop(XSSFCellStyle.BORDER_MEDIUM);  
        style.setTopBorderColor(new XSSFColor( new Color(0, 0, 0)));  
        style.setBorderBottom(XSSFCellStyle.BORDER_THIN);  
        style.setBottomBorderColor(new XSSFColor( new Color(0, 0, 0)));  
        styles.put("cellLeftTopLineBold", style); 
        //最上边边框加粗
        style = wb.createCellStyle();  
        style.setAlignment(XSSFCellStyle.ALIGN_LEFT);  
        style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);  
        style.setWrapText(true);  
        style.setBorderRight(XSSFCellStyle.BORDER_THIN);  
        style.setFont(font1);
        style.setRightBorderColor(new XSSFColor( new Color(0, 0, 0)));  
        style.setBorderLeft(XSSFCellStyle.BORDER_THIN);  
        style.setLeftBorderColor(new XSSFColor( new Color(0, 0, 0)));  
        style.setBorderTop(XSSFCellStyle.BORDER_MEDIUM);  
        style.setTopBorderColor(new XSSFColor( new Color(0, 0, 0)));  
        style.setBorderBottom(XSSFCellStyle.BORDER_THIN);  
        style.setBottomBorderColor(new XSSFColor( new Color(0, 0, 0)));  
        styles.put("cellTopLineBold", style); 
      //最左下边边框加粗
        style = wb.createCellStyle();  
        style.setAlignment(XSSFCellStyle.ALIGN_LEFT);  
        style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);  
        style.setWrapText(true);  
        style.setBorderRight(XSSFCellStyle.BORDER_THIN);  
        style.setFont(font1);
        style.setRightBorderColor(new XSSFColor( new Color(0, 0, 0)));  
        style.setBorderLeft(XSSFCellStyle.BORDER_MEDIUM);  
        style.setLeftBorderColor(new XSSFColor( new Color(0, 0, 0)));  
        style.setBorderTop(XSSFCellStyle.BORDER_THIN);  
        style.setTopBorderColor(new XSSFColor( new Color(0, 0, 0)));  
        style.setBorderBottom(XSSFCellStyle.BORDER_MEDIUM);  
        style.setBottomBorderColor(new XSSFColor( new Color(0, 0, 0)));  
        styles.put("cellLeftBottomLineBold", style); 
        
        //最下边边框加粗
        style = wb.createCellStyle();  
        style.setAlignment(XSSFCellStyle.ALIGN_LEFT);  
        style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);  
        style.setWrapText(true);  
        style.setBorderRight(XSSFCellStyle.BORDER_THIN);  
        style.setFont(font1);
        style.setRightBorderColor(new XSSFColor( new Color(0, 0, 0)));  
        style.setBorderLeft(XSSFCellStyle.BORDER_THIN);  
        style.setLeftBorderColor(new XSSFColor( new Color(0, 0, 0)));  
        style.setBorderTop(XSSFCellStyle.BORDER_THIN);  
        style.setTopBorderColor(new XSSFColor( new Color(0, 0, 0)));  
        style.setBorderBottom(XSSFCellStyle.BORDER_MEDIUM);  
        style.setBottomBorderColor(new XSSFColor( new Color(0, 0, 0)));  
        styles.put("cellBottomLineBold", style); 
        
        //最右边边框加粗
        style = wb.createCellStyle();  
        style.setAlignment(XSSFCellStyle.ALIGN_LEFT);  
        style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);  
        style.setWrapText(true);  
        style.setBorderRight(XSSFCellStyle.BORDER_MEDIUM);  
        style.setFont(font1);
        style.setRightBorderColor(new XSSFColor( new Color(0, 0, 0)));  
        style.setBorderLeft(XSSFCellStyle.BORDER_THIN);  
        style.setLeftBorderColor(new XSSFColor( new Color(0, 0, 0)));  
        style.setBorderTop(XSSFCellStyle.BORDER_THIN);  
        style.setTopBorderColor(new XSSFColor( new Color(0, 0, 0)));  
        style.setBorderBottom(XSSFCellStyle.BORDER_THIN);  
        style.setBottomBorderColor(new XSSFColor( new Color(0, 0, 0)));  
        styles.put("cellRightLineBold", style); 
        
        //最左边边框加粗
        style = wb.createCellStyle();  
        style.setAlignment(XSSFCellStyle.ALIGN_LEFT);  
        style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);  
        style.setWrapText(true);  
        style.setBorderRight(XSSFCellStyle.BORDER_THIN);  
        style.setFont(font1);
        style.setRightBorderColor(new XSSFColor( new Color(0, 0, 0)));  
        style.setBorderLeft(XSSFCellStyle.BORDER_MEDIUM);  
        style.setLeftBorderColor(new XSSFColor( new Color(0, 0, 0)));  
        style.setBorderTop(XSSFCellStyle.BORDER_THIN);  
        style.setTopBorderColor(new XSSFColor( new Color(0, 0, 0)));  
        style.setBorderBottom(XSSFCellStyle.BORDER_THIN);  
        style.setBottomBorderColor(new XSSFColor( new Color(0, 0, 0)));  
        styles.put("cellLeftLineBold", style); 
        //最右上边边框加粗
        style = wb.createCellStyle();  
        style.setAlignment(XSSFCellStyle.ALIGN_LEFT);  
        style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);  
        style.setWrapText(true);  
        style.setBorderRight(XSSFCellStyle.BORDER_MEDIUM);  
        style.setFont(font1);
        style.setRightBorderColor(new XSSFColor( new Color(0, 0, 0)));  
        style.setBorderLeft(XSSFCellStyle.BORDER_THIN);  
        style.setLeftBorderColor(new XSSFColor( new Color(0, 0, 0)));  
        style.setBorderTop(XSSFCellStyle.BORDER_MEDIUM);  
        style.setTopBorderColor(new XSSFColor( new Color(0, 0, 0)));  
        style.setBorderBottom(XSSFCellStyle.BORDER_THIN);  
        style.setBottomBorderColor(new XSSFColor( new Color(0, 0, 0)));  
        styles.put("cellRightTopLineBold", style);        
      
      //中间
        style = wb.createCellStyle();  
        style.setAlignment(XSSFCellStyle.ALIGN_LEFT);  
        style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);  
        style.setWrapText(true);  
        style.setBorderRight(XSSFCellStyle.BORDER_THIN);  
        style.setFont(font1);
        style.setRightBorderColor(new XSSFColor( new Color(0, 0, 0)));  
        style.setBorderLeft(XSSFCellStyle.BORDER_THIN);  
        style.setLeftBorderColor(new XSSFColor( new Color(0, 0, 0)));  
        style.setBorderTop(XSSFCellStyle.BORDER_THIN);  
        style.setTopBorderColor(new XSSFColor( new Color(0, 0, 0)));  
        style.setBorderBottom(XSSFCellStyle.BORDER_THIN);  
        style.setBottomBorderColor(new XSSFColor( new Color(0, 0, 0)));  
        styles.put("cellCenterLineBold", style);        
        //右下边框
        style = wb.createCellStyle();  
        style.setAlignment(XSSFCellStyle.ALIGN_LEFT);  
        style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);  
        style.setWrapText(true);  
        style.setBorderRight(XSSFCellStyle.BORDER_MEDIUM);  
        style.setFont(font1);
        style.setRightBorderColor(new XSSFColor( new Color(0, 0, 0)));  
        style.setBorderLeft(XSSFCellStyle.BORDER_THIN);  
        style.setLeftBorderColor(new XSSFColor( new Color(0, 0, 0)));  
        style.setBorderTop(XSSFCellStyle.BORDER_THIN);  
        style.setTopBorderColor(new XSSFColor( new Color(0, 0, 0)));  
        style.setBorderBottom(XSSFCellStyle.BORDER_MEDIUM);  
        style.setBottomBorderColor(new XSSFColor( new Color(0, 0, 0)));  
        styles.put("cellRightBottomLineBold", style);
        
        
        //无边框
      //单元格样式右对齐
        style = wb.createCellStyle();  
        style.setAlignment(XSSFCellStyle.ALIGN_RIGHT);
        style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);  
        style.setFont(font1);
        style.setWrapText(true);
        styles.put("right", style);  
      //单元格样式左对齐
        style = wb.createCellStyle();  
        style.setAlignment(XSSFCellStyle.ALIGN_LEFT);  
        style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);  
        style.setFont(font1);
        style.setWrapText(true);
        styles.put("left", style);  
      //单元格样式左对齐
        style = wb.createCellStyle();  
        style.setAlignment(XSSFCellStyle.ALIGN_CENTER); 
        style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);  
        style.setFont(font1);
        style.setWrapText(true);
        styles.put("center", style);  
        
        style = wb.createCellStyle();  
        style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);  
        style.setFont(font1);
        style.setWrapText(true);
        styles.put("noStyleNormal", style);  
        
        
       //fangsong12
        style = wb.createCellStyle(); 
        style.setFont(font2);
        style.setAlignment(XSSFCellStyle.ALIGN_LEFT); 
        style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);  
        style.setWrapText(true);
        styles.put("fangsong12", style);  
        
        
        
        //标题样式
        XSSFFont  titleFont = wb.createFont();  
        titleFont.setFontHeightInPoints((short) 13);  
        titleFont.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);  
        titleFont.setFontName("宋体");
        style = wb.createCellStyle();  
        style.setAlignment(XSSFCellStyle.ALIGN_CENTER);  
        style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER); 
        style.setWrapText(true);//设置自动换行
        style.setFont(titleFont);  
        styles.put("title", style);  
        
        //两端对齐
        //字体
        XSSFFont font_ld=wb.createFont();
        font_ld.setFontHeightInPoints((short)11);
        font_ld.setFontName("仿宋");
        style = wb.createCellStyle();  
        style.setAlignment(HorizontalAlignment.DISTRIBUTED); 
        style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER); 
        style.setWrapText(true);//设置自动换行
        style.setFont(font_ld);  
        styles.put("ldFontStyle", style);  
     

        
        return styles;  
    }	 

    
    
 public static XSSFCellStyle generateRowAndCell(XSSFSheet sheet,  int rowIndex, int cellIndex) {
	// 创建row
	XSSFRow row = sheet.getRow(rowIndex);
	XSSFCell cell = row.getCell(cellIndex);
	XSSFCellStyle cellStyle =cell.getCellStyle();  
	return  cellStyle;
}

    
    
    
    
    
}
