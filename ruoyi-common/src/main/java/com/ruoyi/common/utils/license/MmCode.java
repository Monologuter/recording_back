package com.ruoyi.common.utils.license;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.alibaba.fastjson.JSONObject;
public class MmCode
{
	private static final Log logger = LogFactory.getLog(MmCode.class);
	private static String OS = System.getProperty("os.name").toLowerCase();
	
	public static String getCode()
	{
		String code = "";
		try
		{
			File file = new File("/usr/bin/sbfck");
			if (file.exists())
			{
				code = new String(IoUtil.readBinaryFile(file), "utf-8");
			}
			else
			{
				code = get_code();
				code = Base64Util.base64(MD5.md5(code));
				IoUtil.write(code.getBytes("utf-8"), "/usr/bin/sbfck");
			}
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
		}
		return code;
	}

	private static String get_code()
	{
		return getCPUSerial() + getMotherboardSN() + getHardDiskSN("C") + getMac();
	}

	/**
	 * 获取cpu序列号
	 * 
	 * @return
	 */
	public static String getCPUSerial()
	{
		String result = "";
		try
		{
			if (isWindows())
			{
				try
				{
					File file = File.createTempFile("tmp", ".vbs");
					file.deleteOnExit();
					FileWriter fw = new java.io.FileWriter(file);
					String vbs = "Set objWMIService = GetObject(\"winmgmts:\\\\.\\root\\cimv2\")\n"
							+ "Set colItems = objWMIService.ExecQuery _ \n"
							+ "   (\"Select * from Win32_Processor\") \n" + "For Each objItem in colItems \n"
							+ "    Wscript.Echo objItem.ProcessorId \n" + "    exit for  ' do the first cpu only! \n"
							+ "Next \n";

					// + "    exit for  \r\n" + "Next";
					fw.write(vbs);
					fw.close();
					Process p = Runtime.getRuntime().exec("cscript //NoLogo " + file.getPath());
					BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
					String line;
					while ((line = input.readLine()) != null)
					{
						result += line;
					}
					input.close();
					file.delete();
				}
				catch (Exception e)
				{
				}
			}
			else
			{
				result = exeCmd("dmidecode -t processor | grep 'ID'");
			}
		}
		catch (Exception e)
		{
			logger.error("获取CPU序列号失败");
		}
		logger.info("CPU序列号: " + result + "\r\n\r\n");
		return result == null ? "" : result.trim();
	}

	/**
	 * 获取主板序列号
	 * 
	 * @return
	 */
	public static String getMotherboardSN()
	{
		String result = "";
		try
		{

			if (isWindows())
			{
				File file = File.createTempFile("realhowto", ".vbs");
				file.deleteOnExit();
				FileWriter fw = new java.io.FileWriter(file);

				String vbs = "Set objWMIService = GetObject(\"winmgmts:\\\\.\\root\\cimv2\")\n"
						+ "Set colItems = objWMIService.ExecQuery _ \n" + "   (\"Select * from Win32_BaseBoard\") \n"
						+ "For Each objItem in colItems \n" + "    Wscript.Echo objItem.SerialNumber \n"
						+ "    exit for  ' do the first cpu only! \n" + "Next \n";

				fw.write(vbs);
				fw.close();
				Process p = Runtime.getRuntime().exec("cscript //NoLogo " + file.getPath());
				BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
				String line;
				while ((line = input.readLine()) != null)
				{
					result += line;
				}
				input.close();
			}
			else
			{
				result = exeCmd("dmidecode |grep 'Serial Number'");
			}
		}
		catch (Exception e)
		{
		}
		logger.info("主板序列号: " + result + "\r\n\r\n");
		return result == null ? "" : result.trim();
	}

	/**
	 * 获取硬盘序列号
	 * 
	 * @return
	 */
	public static String getHardDiskSN(String drive)
	{
		String result = "";
		try
		{
			if (isWindows())
			{
				File file = File.createTempFile("realhowto", ".vbs");
				file.deleteOnExit();
				FileWriter fw = new java.io.FileWriter(file);

				String vbs = "Set objFSO = CreateObject(\"Scripting.FileSystemObject\")\n"
						+ "Set colDrives = objFSO.Drives\n" + "Set objDrive = colDrives.item(\"" + drive + "\")\n"
						+ "Wscript.Echo objDrive.SerialNumber"; // see note
				fw.write(vbs);
				fw.close();
				Process p = Runtime.getRuntime().exec("cscript //NoLogo " + file.getPath());
				BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
				String line;
				while ((line = input.readLine()) != null)
				{
					result += line;
				}
				input.close();
			}
			else
			{
				// 查看系统序列号
				result = exeCmd("dmidecode -s system-serial-number");
			}
		}
		catch (Exception e)
		{
			logger.error("获取硬盘序列号失败");
		}
		logger.info("硬盘序列号: " + result + "\r\n\r\n");
		return result == null ? "" : result.trim();
	}

	/**
	 * 获取MAC地址
	 */
	public static String getMac()
	{

		String result = "";
		try
		{
			if (isWindows())
			{
				Process process = Runtime.getRuntime().exec("ipconfig /all");

				InputStreamReader ir = new InputStreamReader(process.getInputStream());

				LineNumberReader input = new LineNumberReader(ir);

				String line;

				while ((line = input.readLine()) != null)

					if (line.indexOf("Physical Address") > 0)
					{

						String MACAddr = line.substring(line.indexOf("-") - 2);
						result = MACAddr;
					}
			}
			else
			{
				try
				{
					Process p = new ProcessBuilder("ifconfig").start();
					BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
					String line;
					while ((line = br.readLine()) != null)
					{
						Pattern pat = Pattern.compile("\\b\\w+:\\w+:\\w+:\\w+:\\w+:\\w+\\b");
						Matcher mat = pat.matcher(line);
						if (mat.find())
						{
							result = mat.group(0);
						}
					}
					br.close();
				}
				catch (Exception e)
				{
				}
			}
		}
		catch (java.io.IOException e)
		{
		}
		logger.info("MAC: " + result + "\r\n\r\n");
		return result == null ? "" : result.trim();
	}

	public static String exeCmd(String commandStr)
	{
		StringBuilder sb = new StringBuilder();
		BufferedReader br = null;
		try
		{
			Process p = null;
			if (isWindows())
			{
				p = Runtime.getRuntime().exec(commandStr);
			}
			else
			{
				String[] cmds = new String[3];
				cmds[0] = "/bin/sh";
				cmds[1] = "-c";
				cmds[2] = commandStr;
				p = Runtime.getRuntime().exec(cmds);
			}
			br = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line = null;
			while ((line = br.readLine()) != null)
			{
				sb.append(line);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (br != null)
			{
				try
				{
					br.close();
				}
				catch (Exception e)
				{
				}
			}
		}
		return sb.toString();
	}

	public static boolean isWindows()
	{
		return OS.indexOf("win") >= 0;
	}

}