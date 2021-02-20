package com.ruoyi.common.utils.license;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author hxc
 *
 */
public class IoUtil {

	private static final Log Log = LogFactory.getLog(IoUtil.class);
	
	/**
	 * 写文件
	 * @param filePath
	 * @param bodys
	 */
	public static void writeFileByLines(String filePath, List<String> bodys) {
		BufferedWriter bw = null;
		File file = new File(filePath);
		if (!file.getParentFile().exists()) {
			file.mkdirs();
		}
		if (file.exists()) {
			file.delete();
		}
		try {
			FileOutputStream fos = new FileOutputStream(filePath);
			bw = new BufferedWriter(new OutputStreamWriter(fos,"utf-8"));
			if (null != bodys) {
				for (String str : bodys) {
					bw.write(str);
					bw.newLine();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bw != null) {
				try {
					bw.close();
				} catch (IOException e1) {
				}
			}
		}
	}

	/**
	 * 
	 * 以行为单位读取文件，常用于读面向行的格式化文件
	 * 
	 * @param fileName
	 *            文件名
	 */
	public static List<String> readFileByLines(String fileName) {
		List<String> list = new ArrayList<String>();
		File file = new File(fileName);
		if(!file.exists())
		{
			return list;
		}
		BufferedReader reader = null;
		try {
			InputStreamReader isr = new InputStreamReader(new FileInputStream(file),"utf-8");
			reader = new BufferedReader(isr);
			String tempString = null;
			while ((tempString = reader.readLine()) != null) {
				if (!tempString.trim().isEmpty()) {
					list.add(tempString);
				}
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
		return list;
	}
	
	/**
	 * 封装好的文件拷贝方法
	 */
	public static void copy(String from, String to) {
		try {
			InputStream in = new FileInputStream(from);
			OutputStream out = new FileOutputStream(to);
			byte[] buff = new byte[1024];
			int len = 0;
			while ((len = in.read(buff)) != -1) {
				out.write(buff, 0, len);
			}
			in.close();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}  
	}
	
	
	/**
	 * 文件删除
	 * @param filepath
	 */
	public static void  deleteFile(String filepath)
	{
		new File(filepath).delete();
	}

	public static void main(String[] args)
	{
		List<String> bodys = new ArrayList<String>();
		bodys.add("fff");
		writeFileByLines("d:/t.txt", bodys);
	}
	
	
	/**
	 * 将内容写到目标文件中
	 * 
	 * @param body
	 *            字节内容
	 * @param target
	 *            目标地址
	 */
	public static void write(InputStream is, String target)
	{
		FileOutputStream fos = null;
		try
		{
			fos = new FileOutputStream(target);
			byte[] b = null;
			do
			{
				b = new byte[is.available() > 1024 ? 1024 : is.available()];
				is.read(b);
				fos.write(b);
			}
			while (is.available() > 0);
		}
		catch (Exception e)
		{
			Log.error( e.getMessage(), e);
		}
		finally
		{
			if (is != null)
			{
				try
				{
					is.close();
				}
				catch (IOException e)
				{
					Log.error( e.getMessage(), e);
				}
			}
			if (fos != null)
			{
				try
				{
					fos.close();
				}
				catch (IOException e)
				{
					Log.error( e.getMessage(), e);
				}
			}
		}
	}

	/**
	 * 将内容写到目标文件中
	 * 
	 * @param body
	 *            字节内容
	 * @param target
	 *            目标地址
	 */
	public static void write(byte[] body, String target)
	{
		try
		{
			File file = new File(target);
			if (!file.getParentFile().exists())
			{
				file.getParentFile().mkdirs();
			}
			FileOutputStream fos = new FileOutputStream(target);
			fos.write(body);
			fos.close();
		}
		catch (Exception e)
		{
			Log.error( e.getMessage(), e);
		}
	}
	
	
	public static void writeAppend(byte[] body, String target)
	{
		try
		{
			File file = new File(target);
			if (!file.getParentFile().exists())
			{
				file.getParentFile().mkdirs();
			}
			FileOutputStream fos = new FileOutputStream(target, true);
			fos.write(body);
			fos.close();
		}
		catch (Exception e)
		{
			Log.error( e.getMessage(), e);
		}
	}

	/**
	 * 删除当前文件及他的父目录 在父目录下只有当前文件的情况.
	 * 
	 * @param filepath
	 *            待删除文件
	 * @param pdir
	 *            父目录层数
	 */
	public static void delFileAndParent(String filepath, int pdir)
	{
		File file = new File(filepath);
		file.delete();
		File pfile = file.getParentFile();
		List<File> list = new ArrayList<File>();
		for (int i = 0; i < pdir; i++)
		{
			if (pfile.exists())
			{
				list.add(pfile);
				pfile = pfile.getParentFile();
			}
		}
		for (int i = 0; i < list.size(); i++)
		{
			String[] files = list.get(i).list(new FilenameFilter()
			{
				@Override
				public boolean accept(File dir, String name)
				{
					if (!(name.equals(".") || name.equals("..")))
					{
						return true;
					}
					return false;
				}
			});
			if (files.length == 0)
			{
				list.get(i).delete();
			}
		}
	}

	/**
	 * 读二进制文件，包括文本，图片等 文件大小不能超出50M
	 * 
	 * @param filepath
	 *            文件路径
	 * @return byte[] 文件内容
	 */
	public static byte[] readBinaryFile(String filepath)
	{
		return readBinaryFile(new File(filepath));
	}

	/**
	 * 读二进制文件，包括文本，图片等 文件大小不能超出50M
	 * 
	 * @param f
	 *            待读文件
	 * @return byte[] 文件的byte[]
	 */
	public static byte[] readBinaryFile(File f)
	{
		byte[] b = null;
		RandomAccessFile raf = null;
		try
		{
			raf = new RandomAccessFile(f, "r");
			b = new byte[(int) raf.length()];
			raf.read(b);
		}
		catch (FileNotFoundException e)
		{
			Log.error( e.getMessage(), e);
			return null;
		}
		catch (Exception e)
		{
			Log.error( e.getMessage(), e);
			return null;
		}
		finally
		{
			if (raf != null)
			{
				try
				{
					raf.close();
				}
				catch (IOException e)
				{
					Log.error( e.getMessage(), e);
				}
			}
		}
		return b;
	}

	/**
	 * 行写
	 * @param list
	 * @param filepath
	 * @throws IOException
	 */
	public static void writeLineFile(List<String> list, String filepath) 
	{
		BufferedWriter bw = null;
		try
		{
			// 写入中文字符时解决中文乱码问题
			FileOutputStream fos = new FileOutputStream(new File(filepath));
			OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
			bw = new BufferedWriter(osw);
			for (String arr : list)
			{
				bw.write(arr + "\t\n");
			}
			bw.close();
		}
		catch (Exception e)
		{
			Log.error( e.getMessage(), e);
		}
		finally
		{
			if (bw != null)
			{
				try
				{
					bw.close();
				}
				catch (IOException e)
				{
					Log.error( e.getMessage(), e);
				}
			}
		}
	}

}
