package com.ruoyi.common.utils.license;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Base64Util {
	private static final Log logger = LogFactory.getLog(Base64Util.class);

	/**
	 * 进行Base64编码，按UTF-8编码方式
	 * 
	 * @param src 源数据
	 * @return 编码后结果
	 */
	public static String base64(String src) {
		return base64(src, "UTF-8");
	}

	/**
	 * 进行Base64编码
	 * 
	 * @param src     源数据
	 * @param charset 字符集
	 * @return 编码后结果
	 */
	public static String base64(String src, String charset) {
		String result = "";
		if (src == null) {
			return result;
		}
		try {
			result = new String(Base64.encodeBase64(src.getBytes(charset)), charset);
		} catch (Exception e) {
			logger.error("base64编码异常!", e);
			return src;
		}
		return result;
	}

	public static String base64(byte[] src, String charset) {
		String result = "";
		if (src == null) {
			return result;
		}
		try {
			result = new String(Base64.encodeBase64(src), charset);
		} catch (Exception e) {
			logger.error("base64编码异常!", e);
			return result;
		}
		return result;
	}

	/**
	 * base64解码
	 * 
	 * @param src 源数据
	 * @return 解码后结果
	 */
	public static String deBase64(String src) {
		return deBase64(src, "UTF-8");
	}

	/**
	 * base64解码
	 * 
	 * @param src     源数据
	 * @param charset 字符集
	 * @return 解码后结果
	 */
	public static String deBase64(String src, String charset) {
		String result = "";
		if (src == null) {
			return result;
		}
		try {
			result = new String(Base64.decodeBase64(src.getBytes(charset)), charset);
		} catch (Exception e) {
			logger.error("base64解码异常!", e);
		}
		return result;
	}
}