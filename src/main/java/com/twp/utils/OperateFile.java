package com.twp.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OperateFile {
	private static final String CONFIG_FILE = "upload.properties";
	
	//练习题目提交后显示加<br>换行
	public String readTiJiao(String path) // 从文本文件中读取内容
	{
		String readStr = "";
		try {
			File file = new File(path);
			FileReader fileread = new FileReader(file);
			BufferedReader bufread = new BufferedReader(fileread);
			String read = null;
			while ((read = bufread.readLine()) != null) {
				readStr = readStr+ read+"</br>";
			}
		} catch (Exception d) {
			System.out.println(d.getMessage());
		}
		return readStr; // 返回从文本文件中读取内容
	}

	public String readfile(String path) // 从文本文件中读取内容
	{
		String readStr = "";
		try {
			File file = new File(path);
			FileReader fileread = new FileReader(file);
			BufferedReader bufread = new BufferedReader(fileread);
			String read = null;
			while ((read = bufread.readLine()) != null) {
				readStr = readStr+ read+'\n';
			}
		} catch (Exception d) {
			System.out.println(d.getMessage());
		}
		return readStr; // 返回从文本文件中读取内容
	}
	
	public  void  copyFolder(String  oldPath,  String  newPath)  {    
		System.out.println("oldPath"+oldPath);
		System.out.println("newPath"+newPath);
		   
	       try  {    
	           (new  File(newPath)).mkdirs();  //如果文件夹不存在  则建立新文件夹    
	           File  a=new  File(oldPath);    
	           String[]  file=a.list();    
	           File  temp=null;    
	           for  (int  i  =  0;  i  <  file.length;  i++)  {    
	               if(oldPath.endsWith(File.separator)){    
	                   temp=new  File(oldPath+file[i]);    
	               }    
	               else{    
	                   temp=new  File(oldPath+File.separator+file[i]);    
	               }    
	   
	               if(temp.isFile()){    
	                   FileInputStream  input  =  new  FileInputStream(temp);    
	                   FileOutputStream  output  =  new  FileOutputStream(newPath  +  File.separator  +   
	                           (temp.getName()).toString());    
	                   byte[]  b  =  new  byte[1024  *  5];    
	                   int  len;    
	                   while  (  (len  =  input.read(b))  !=  -1)  {    
	                       output.write(b,  0,  len);    
	                   }    
	                   output.flush();    
	                   output.close();    
	                   input.close();    
	               }    
	               if(temp.isDirectory()){//如果是子文件夹    
	                   copyFolder(oldPath+File.separator+file[i],newPath+File.separator+file[i]);    
	               }    
	           }    
	       }    
	       catch  (Exception  e)  {    
	           System.out.println("复制整个文件夹内容操作出错");    
	           e.printStackTrace();    
	       }    
	   
	   }    
	

	// Linux 写文件
	// 向文本文件中写入内容
	public String writeFile(String content,Integer ItemId,String name, boolean append) throws IOException,
			InterruptedException {

		// 获取练习题临时保存路径
		String fileName = null;//n2s(Fn.time());
		String practicePath = getConfig("PRCATICE") + File.separator+ fileName;
		String name_1 = name+".v";
		String name_2 = name+"_tb.v";
		File writefile = new File(practicePath +File.separator+"demo"+File.separator+name_1);
		System.out.println(practicePath);
		String workPath = getConfig("DEMO");
		System.out.println("practicePath"+practicePath +File.separator+"demo"+File.separator+name_1);

		writefile.setExecutable(true);// 设置可执行权限
		writefile.setReadable(true);// 设置可读权限
		writefile.setWritable(true);// 设置可写权限
		if (!writefile.getParentFile().exists()) {
			writefile.getParentFile().mkdirs();
			System.out.println("21"+writefile.getParentFile().exists());
		}
		boolean addStr = append;
		FileWriter filewriter = new FileWriter(writefile, addStr);
		BufferedWriter bufwriter = new BufferedWriter(filewriter);
		filewriter.write(content);
		filewriter.flush();
		filewriter.close();
		System.out.println("workPath"+workPath);
		System.out.println("practicePath + File.separator+"+practicePath + File.separator+"demo");
		new OperateFile().copyFolder(workPath, practicePath + File.separator+"demo");

		
		String ItemPath = UploadUtils.getConfig("TI_KU")+File.separator+ItemId+File.separator+name_2;
		try {
            Process process = Runtime.getRuntime().exec(new String[] { "/bin/sh", "-c", "cp "+ItemPath+" "+practicePath+File.separator+"demo" });
            int exitCode = process.waitFor();
            File file = new File(ItemPath);
            file.setExecutable(true);// 设置可执行权限
			file.setReadable(true);// 设置可读权限
			file.setWritable(true);// 设置可写权限
			System.out.println("ItemPath  : "+ItemPath);
        } catch (java.lang.NullPointerException e) {
            System.err.println("NullPointerException " + e.getMessage());
        }

		return practicePath + File.separator+"demo";
	}


	/**
	 * 把10位时间戳数字转成字符串(混合大小写)
	 * 
	 * @param num
	 * @return
	 */
	private static String n2s(int num) {
		String str = "";
		char _char;
		String numStr = String.valueOf(num);
		for (int i = 0; i < numStr.length(); i++) {
			int n = Integer.parseInt(numStr.substring(i, i + 1));
			if (n <= 2) { // 这里最后一组只保留xyz不转换，用来做分隔符
				_char = (char) (n + 65 + rand(0, 3) * 10 + rand(0, 2) * 32);
			} else {
				_char = (char) (n + 65 + rand(0, 2) * 10 + rand(0, 2) * 32);
			}
			str += _char;
		}
		return str;
	}

	/**
	 * 产生随机整数,范围[m,n)
	 * 
	 * @return
	 */
	private static int rand(int m, int n) {
		return (int) (Math.random() * (n - m) + m);
	}

	/**
	 * 获取配置文件的值
	 * 
	 * @param key
	 * @return
	 */
	public static String getConfig(String key) {
		ClassLoader loader = OperateFile.class.getClassLoader();
		InputStream in = loader.getResourceAsStream(CONFIG_FILE);
		Properties properties = new Properties();
		try {
			properties.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return properties.getProperty(key);
	}

}