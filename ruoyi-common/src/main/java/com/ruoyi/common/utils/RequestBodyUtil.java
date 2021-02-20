package com.ruoyi.common.utils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilderFactory;

import org.jdom.output.XMLOutputter;

public class RequestBodyUtil {
	
	public static String getReqBody(HttpServletRequest req) throws Exception {
	  BufferedReader in = new BufferedReader(new InputStreamReader(req.getInputStream(), "utf-8"));
	  StringBuffer bankXmlBuffer = new StringBuffer();
	  String inputLine;
	  while ((inputLine = in.readLine()) != null) {
	   bankXmlBuffer.append(inputLine);
	  }
	  in.close();
	  return bankXmlBuffer.toString();
	 }
	
	public static String getReqBodyGBK(HttpServletRequest req) throws Exception {
	  BufferedReader in = new BufferedReader(new InputStreamReader(req.getInputStream(), "GBK"));
	  StringBuffer bankXmlBuffer = new StringBuffer();
	  String inputLine;
	  while ((inputLine = in.readLine()) != null) {
	   bankXmlBuffer.append(inputLine);
	  }
	  in.close();
	  return bankXmlBuffer.toString();
	 }
}
