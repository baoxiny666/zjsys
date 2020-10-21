package com.tried.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;

//import com.test.ReadDirector;

public class FileCommonUtils {
	private static Logger logger = Logger.getLogger(FileCommonUtils.class);
	/**
	 * 复制单个文件 ,可以对目标文件进行覆盖或内容追加
	 * @param srcFileName 源文件名
	 * @param destFileName 目标文件名
	 * @param overlay 如果目标文件存在，是否覆盖
	 * @return
	 */
	public static boolean copyFile(String srcFileName,String destFileName,boolean overlay){
		 
		File srcFile = new File(srcFileName);
		//判断源文件是否存在
		if(!srcFile.exists()){
			return false;
		}else if(!srcFile.isFile()){
			return false;
		}
		File destFile = new File(destFileName);
		if(destFile.exists()){
			if(overlay){
				new File(destFileName).delete();
			}	
		}else{
			if(!destFile.getParentFile().exists()){
				if(!destFile.getParentFile().mkdirs()){
					return false;
				}
			}
		}
		//复制文件
		int byteread = 0;
		InputStream in = null;
		OutputStream out = null;
		try{
			in = new FileInputStream(srcFile);
			out = new FileOutputStream(destFile);
			byte[] buffer = new byte[1024];
			while((byteread = in.read(buffer))!=-1){
				out.write(buffer,0,byteread);
			}
			return true;
		}catch(FileNotFoundException e){
			return false;
		}catch(IOException e){
			return false;
		}finally{
			try{
				if(out != null)
					out.close();
				if(in != null)
					in.close();
			}catch(IOException e){
				e.printStackTrace();
			}
		}
	}
	
	/**
	 *  快速复制文件，适应于模板复制，无文件追加功能，默认直接覆盖
		 * @Description 
		 * @author liuxd
		 * @date 2019-2-24 上午10:22:36
		 * @version V1.0
	 */
	public static void copyFile(String source, String dest) throws IOException {
		File sourceFile=new File(source);
		File destFile=new File(dest);
		 if(!sourceFile.exists()){
			 return ;
		 }
		if(!destFile.getParentFile().exists()){
			 destFile.getParentFile().mkdirs();
		 }
		FileChannel inputChannel = null;
		FileChannel outputChannel = null;
		try {
			inputChannel = new FileInputStream(sourceFile).getChannel();
			outputChannel = new FileOutputStream(destFile).getChannel();
			outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
		}catch(Exception e){
			logger.error(e.getMessage());	
		} 
		finally {
			inputChannel.close();
			outputChannel.close();
		}
	}
	
	/**
	 * 复制某个目录及其目录下的所有子目录和文件到新文件夹
	 * @param oldPath
	 * @param newPath
	 * @author lyw
	 */
	public static void copyFolder(String oldPath,String newPath){
		try{
			File file = new File(oldPath);
			if(file.isFile()){//如果是文件直接复制
				copyFile(oldPath,newPath);
			}else{
				file.mkdirs();
				File fileList = new File(oldPath);
				String[] files = fileList.list();
				File temp = null;
				for(int i = 0 ; i<files.length;i++){
					if(oldPath.endsWith(File.separator)){
						temp = new File(oldPath + files[i]);
					}else{
						temp = new File(oldPath + File.separator + files[i]);
					}
					if(temp.isFile()){
						FileInputStream input = new FileInputStream(temp);
						FileOutputStream output = new FileOutputStream(newPath 
								+"/"+(temp.getName()).toString());
						byte[] bufferarry = new byte[1024*64];
						int prereadlength;
						while((prereadlength = input.read(bufferarry))!=-1){
							output.write(bufferarry, 0, prereadlength);
						}
						output.flush();
						output.close();
						input.close();
					}
					if(temp.isDirectory()){
						copyFolder(oldPath + "/" + files[i],newPath +"/"+files[i]);
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 打包zip文件
	 * @param fileList
	 * @param destFileName
	 * @author lyw
	 */
	public static void zipPack(List<File> fileList,String destFileName){
		byte[] buffer = new byte[1024];
		ZipOutputStream out = null;
		try{
			out = new ZipOutputStream(new FileOutputStream(destFileName));
			for(File fileTemp : fileList){
				FileInputStream fis = new FileInputStream(fileTemp);
				out.putNextEntry(new ZipEntry(fileTemp.getName()));
				out.setEncoding("gbk");
				int len;
				while ((len=fis.read(buffer))>0){
					out.write(buffer,0,len);
				}
				fis.close();
			}
			out.closeEntry();
			out.flush();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(out!=null)
					out.close();
			}catch(IOException e){
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 剪切文件
	 * @param srcFile
	 * @param destFile
	 * @author lyw
	 */
	public static void cutTo(String srcFile,String destFile) throws Exception{
		copyFolder(srcFile,destFile);
		deleteDir(new File(srcFile));
	}
	
	/**
	 * 删除文件及其子文件
	 * @param dir
	 * @return
	 * @author lyw
	 */
	public static boolean deleteDir(File dir){
		if(dir.isDirectory()){
			String[] children = dir.list();
			for(int i=0;i<children.length;i++){
				boolean isDelete = deleteDir(new File(dir,children[i]));
				if(!isDelete){
					return false;
				}
			}
		}
		return dir.delete();
	}
	
	/**
	 * 快速移动文件 父目录必须保持一致
	 * @param srcFile
	 * @param destFile
	 * @author lyw
	 */
	public static void removeTo(String srcFile,String destFile){
		editFile(srcFile, destFile);
	}
	
	/**
	 * 修改文件或文件夹名称
	 * @param oleFile
	 * @param newFile
	 * @author lyw
	 */
	public static void editFile(String oldFile,String newFile){
		if(!oldFile.equals(newFile)){
			File oFile = new File(oldFile);
			File nFile = new File(newFile);
			oFile.renameTo(nFile);
		}
	}
	
	
	public static void main(String[] args) throws Exception{
		String o1 = "D:\\root1\\project\\newProject\\1.txt";
		System.out.println(new File(o1).isFile());
		String o2 = "D:\\root1\\project\\oldProject\\1.txt";
		//cutTo(o1, o2);
		//System.out.println(o1.substring(0,o1.lastIndexOf("\\")));
	}
	 
	
	
	 /**
	  * 读取目录文件
	  * @param dirname 目录名称
	  * @return list集合
	  */
	 public static List getFiles(String dirname)throws Exception{
	    List file_names=null;
	    File dir=new File(dirname);
	    if(dir.exists()){
	     file_names=new ArrayList();
	     File []files=dir.listFiles();     
	     //排序 按最后修改时间倒序排序
	     Arrays.sort(files, new FileCommonUtils.CompratorByLastModified());   
	     for(int i=0;i<files.length;i++){
	   //获取当文件最后修改时间 files[i].lastModified()
	      if(files[i].isHidden()){//判断是隐藏文件
	          System.out.println("隐藏文件："+files[i].getName());
	      }else if(files[i].isDirectory()){//判断是目录
	    	  System.out.println("文件夹："+files[i].getName());
	      }else{//普通文件
	          file_names.add(files[i].getName());
	      }
	     }
	    }else{
	     System.out.println("该目录没有任何文件信息！");    
	    }   
	    return file_names;
	 }
	
	
	
	/**
	 * 按时间倒序排序文件
	 * @author SUNLUNAN
	 *
	 */
	private static class CompratorByLastModified implements Comparator<File>{   
		  public int compare(File f1, File f2) {   
		   long diff = f1.lastModified()-f2.lastModified();   
		       if(diff>0)   
		         return -1;   
		       else if(diff==0)   
		         return 0;   
		       else  
		         return 1;   
		       }   
		  public boolean equals(Object obj){   
		   return true;   
		  }   
		   } 
		
		
	


}
