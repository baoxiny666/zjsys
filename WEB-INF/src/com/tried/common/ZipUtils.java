package com.tried.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 文件压缩工具类
 * @author lyw
 * @date 2019-10-22
 *
 */
public class ZipUtils {
	private static final int BUFFER_SISE = 2*1024;
	
	/**
	 * 
	 * @param srcDir 压缩文件夹路径
	 * @param out 压缩文件输出流
	 * @param KeepDirStructure 是否保留原来目录结构 
	 * @throws RuntimeException
	 */
	public static void toZip(String srcDir,OutputStream out ,boolean KeepDirStructure)
			throws RuntimeException{
		ZipOutputStream zos = null;
		try{
			zos = new ZipOutputStream(out);
			File sourceFile = new File(srcDir);
			compress(sourceFile,zos,sourceFile.getName(),KeepDirStructure);
		}catch(Exception e){
			throw new RuntimeException("zip error from ZipUtils",e);
		}finally{
			if(zos!=null){
				try{
					zos.close();
				}catch(IOException e){
					e.printStackTrace();
				}
			}
		}
		
	}

	/**
	 * 
	 * @param srcFiles 需要压缩的文件列表
	 * @param out 压缩文件输出流
	 * @throws RuntimeException
	 */
	public static void toZip(List<File> srcFiles,OutputStream out) throws RuntimeException{
		ZipOutputStream zos = null;
		try{
			zos = new ZipOutputStream(out);
			for(File srcFile : srcFiles){
				byte[] buf = new byte[BUFFER_SISE];
				zos.putNextEntry(new ZipEntry(srcFile.getName()));
				int len;
				FileInputStream in = new FileInputStream(srcFile);
				while((len = in.read(buf))!=-1){
					zos.write(buf,0,len);
				}
				zos.closeEntry();
				in.close();
			}
		}catch(Exception e){
			throw new RuntimeException("zip error from ZipUtils",e);
		}finally{
			if(zos != null){
				try{
					zos.close();
				}catch(IOException e){
					e.printStackTrace();
				}
			}
		}
	}
	
	
	/**
	 * 递归压缩方法
	 * @param sourceFile 源文件
	 * @param zos zip输出流
	 * @param name 压缩后的名称
	 * @param keepDirStructure 是否保留原有文件结构
	 * @throws Exception
	 */
	private static void compress(File sourceFile, ZipOutputStream zos,
			String name, boolean keepDirStructure) throws Exception{
		byte[] buf = new byte[BUFFER_SISE];
		if(sourceFile.isFile()){
			zos.putNextEntry(new ZipEntry(name));
			int len;
			FileInputStream in = new FileInputStream(sourceFile);
			while((len = in.read(buf))!= -1){
				zos.write(buf,0,len);
			}
			zos.closeEntry();
			in.close();
			
		}else{
			File[] listFiles = sourceFile.listFiles();
			if(listFiles == null || listFiles.length == 0){
				if(keepDirStructure){
					zos.putNextEntry(new ZipEntry(name + "/"));
					zos.closeEntry();
				}
			}else{
				for(File file : listFiles){
					if(keepDirStructure){
						compress(file,zos,name+"/"+file.getName(),keepDirStructure);
					}else{
						compress(file,zos,file.getName(),keepDirStructure);
					}
				}
			}
		}
	}
	
	public static void main(String[] args) throws Exception{
		 FileOutputStream fos1 = new FileOutputStream(new File("e:/01.zip"));
		 ZipOutputStream zos =new ZipOutputStream(fos1);
		 zos.putNextEntry(new ZipEntry("aaa/aaa/"));
			zos.closeEntry();
			zos.close();
	}
	

}
