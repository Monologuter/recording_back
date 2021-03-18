package com.ruoyi.common.utils;

import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by cnpc on 2016/12/9. e-mail:jrn1012@petrochina.com.cn qq:475572229
 */
public class FileUtils {

	/**
	 * @param file     //文件对象
	 * @param filePath //上传路径
	 * @param fileName //文件名
	 * @return 文件名
	 */
	public static String fileUp(MultipartFile file, String filePath, String fileName) {
		System.out.println("2222222222222222222222222222222222222222222222" + filePath + "999999999999999999999999");
		String extName = ""; // 扩展名格式：
		try {
			if (file.getOriginalFilename().lastIndexOf(".") >= 0) {
				extName = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
			}
			copyFile(file.getInputStream(), filePath, fileName + extName).replaceAll("-", "");
			/*
			 * String os = System.getProperty("os.name"); if
			 * (!os.toLowerCase().contains("win")) {
			 * Runtime.getRuntime().exec("chmod 777 -R /home/ck/data"); }
			 */

		} catch (IOException e) {
			System.out.println(e);
		}
		return fileName + extName;
	}

	/**
	 * 写文件到当前目录的upload目录中
	 * 
	 * @param in
	 * @param fileName
	 * @throws IOException
	 */
	private static String copyFile(InputStream in, String dir, String realName) throws IOException {
		File file = new File(dir, realName);
		if (!file.exists()) {
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			file.createNewFile();
		}
		copyInputStreamToFile(in, file);
		return realName;
	}

	/**
	 * 删除文件夹里面的所有文件
	 * 
	 * @param path 文件夹路径 如 c:/fqf
	 */
	public static void delAllFile(String path) {
		File file = new File(path);
		if (!file.exists()) {
			return;
		}
		if (!file.isDirectory()) {
			return;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
				delFolder(path + "/" + tempList[i]);// 再删除空文件夹
			}
		}
	}

	/**
	 * 删除文件夹
	 * 
	 * @param folderPath 文件夹路径及名称 如c:/fqf
	 */
	public static void delFolder(String folderPath) {
		try {
			delAllFile(folderPath); // 删除完里面所有内容
			String filePath = folderPath;
			filePath = filePath.toString();
			File myFilePath = new File(filePath);
			myFilePath.delete(); // 删除空文件夹

		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	/**
	 * 复制单个文件
	 * 
	 * @param oldPath 源文件路径
	 * @param newPath 复制后路径
	 * @return 文件大小
	 */
	public static int copyFile(String oldPath, String newPath) {
		try {
			int bytesum = 0;
			int byteread = 0;
			File oldfile = new File(oldPath);
			if (oldfile.exists()) {
				InputStream inStream = new FileInputStream(oldPath); // 读入原文件
				FileOutputStream fs = new FileOutputStream(newPath);
				byte[] buffer = new byte[1444];
				int length;
				while ((byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread; // 字节数 文件大小
					System.out.println(bytesum);
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
				fs.close();
			}
			return bytesum;
		} catch (Exception e) {
			System.out.println("复制单个文件操作出错");
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * 复制文件流到新的文件
	 * 
	 * @param inStream 文件流
	 * @param file     新文件
	 * @return 是否复制成功
	 */
	public static boolean copyInputStreamToFile(final InputStream inStream, File file) throws IOException {
		int bytesum = 0;
		int byteread = 0;
		byte[] buffer = new byte[1024];
		FileOutputStream fs = new FileOutputStream(file);
		while ((byteread = inStream.read(buffer)) != -1) {
			bytesum += byteread; // 字节数 文件大小
			fs.write(buffer, 0, byteread);
		}
		inStream.close();
		fs.close();
		return true;
	}

	/**
	 * 删除指定路径下的文件
	 * 
	 * @param filePathAndName 文件路径
	 */
	public static void delFile(String filePathAndName) {
		try {
			String filePath = filePathAndName;
			filePath = filePath.toString();
			File myDelFile = new File(filePath);
			myDelFile.delete();

		} catch (Exception e) {
			System.out.println("删除文件操作出错");
			e.printStackTrace();
		}

	}

	/**
	 * 判断文件是否是图像文件
	 */
	public static boolean isImage(String name) {
		boolean valid = true;
		try {
			Image image = ImageIO.read(new File(name));
			if (image == null) {
				valid = false;
				System.out.println("The file" + name + "could not be opened , it is not an image");
			}
		} catch (IOException ex) {
			valid = false;
			System.out.println("The file" + name + "could not be opened , an error occurred.");
		}
		return valid;
	}

	public static String generateZipFile(String basePath, String zipFileName, String... fileNames) {
		byte[] buffer = new byte[1024];
		String strZipName = basePath + zipFileName;
		try {
			ZipOutputStream out = new ZipOutputStream(new FileOutputStream(strZipName));
			for (String fileName : fileNames) {
				File file = new File(basePath + fileName);
				FileInputStream fis = new FileInputStream(file);
				out.putNextEntry(new ZipEntry(file.getName()));
				int len;
				// 读入需要下载的文件的内容，打包到zip文件
				while ((len = fis.read(buffer)) > 0) {
					out.write(buffer, 0, len);
				}
				out.closeEntry();
				fis.close();
			}
			out.close();
			return strZipName;
		} catch (IOException ex) {
			return null;
		}
	}

	/**
	 *
	 * @project_name dm
	 * @package_name com.wf.ew.common.utils
	 * @file_name FileUtils.java
	 * @enclosing_method writeFile
	 * @return_type boolean
	 * @author liubaoping
	 * @date 2020年10月10日 下午4:56:19
	 * @TODO
	 * @@param destFileName
	 * @@param str
	 * @@return
	 */
	public static boolean writeFile(String destFileName, String str) {
		boolean success = false;
		byte[] bt = new byte[1024];
		try {
			bt = str.getBytes("UTF-8");
			File file = new File(destFileName);
			if (!file.exists()) {
				CreateFile(destFileName);
			}
			FileOutputStream in = new FileOutputStream(destFileName);
			try {
				in.write(bt, 0, bt.length);
				in.close();
				success = true;
				System.out.println("写入文件成功");
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return success;
	}

	/**
	 *
	 * @project_name dm
	 * @package_name com.wf.ew.common.utils
	 * @file_name FileUtils.java
	 * @enclosing_method CreateFile
	 * @return_type boolean
	 * @author liubaoping
	 * @date 2020年10月10日 下午4:56:12
	 * @TODO
	 * @@param destFileName
	 * @@return
	 */
	public static boolean CreateFile(String destFileName) {
		File file = new File(destFileName);
		if (file.exists()) {
			System.out.println("创建单个文件" + destFileName + "失败，目标文件已存在！");
			return false;
		}
		if (destFileName.endsWith(File.separator)) {
			System.out.println("创建单个文件" + destFileName + "失败，目标不能是目录！");
			return false;
		}
		if (!file.getParentFile().exists()) {
			System.out.println("目标文件所在路径不存在，准备创建。。。");
			if (!file.getParentFile().mkdirs()) {
				System.out.println("创建目录文件所在的目录失败！");
				return false;
			}
		}
		try {
			if (file.createNewFile()) {
				System.out.println("创建单个文件" + destFileName + "成功！");
				return true;
			}
			System.out.println("创建单个文件" + destFileName + "失败！");
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("创建单个文件" + destFileName + "失败！");
		}
		return false;
	}
}
