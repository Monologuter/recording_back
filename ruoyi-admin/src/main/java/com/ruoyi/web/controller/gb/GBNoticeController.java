/**
 *
 * @dispatch2020
 * @com.ck.core.httpapi
 * @GBNoticeController.java
 * @author liubaoping
 * @date 2020年11月9日 下午3:49:24
 * @TODO
 */
package com.ruoyi.web.controller.gb;

import com.ruoyi.common.core.domain.ApiRsp;
import com.ruoyi.common.utils.CodeDefine;
import com.ruoyi.common.utils.RequestBodyUtil;
import com.ruoyi.gb.service.ICatalogService;
import com.ruoyi.gb.service.IGBNoticeService;
import com.ruoyi.gb.service.impl.CatalogServiceImpl;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.InputStream;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @dispatch2020
 * @com.ck.core.httpapi
 * @GBNoticeController.java
 * @author liubaoping
 * @date 2020年11月9日 下午3:49:24
 * @TODO @
 */
@CrossOrigin
@RestController
@RequestMapping("/gbNotice")
public class GBNoticeController {
	// 日志问题
	private static Logger logger = Logger.getLogger(GBNoticeController.class);
	@Autowired
	private ICatalogService catalogService;
	
	@Autowired
	private IGBNoticeService gBNoticeService;

	@RequestMapping(value = "")
	@ResponseBody
	public Object api(HttpServletRequest request) {
		try {
			String reqbody = RequestBodyUtil.getReqBody(request);
			Document doc = DocumentHelper.parseText(reqbody);
			System.out.println(reqbody);
			if (reqbody.contains(CodeDefine.CATALOG)) {
				String ParentDeviceID = ParseXML(doc, "Response");
				catalogService.Catalog(doc, ParentDeviceID);
				logger.info("dispatch接收到GB28181通知消息>>>Catalog=" + reqbody);
			} else if (reqbody.contains(CodeDefine.PRESETQUERY)) {
				logger.info("dispatch接收到GB28181通知消息>>>PresetQuery=" + reqbody);
			} else if (reqbody.contains(CodeDefine.DEVICESTATUS)) {
				logger.info("dispatch接收到GB28181通知消息>>>DeviceStatus=" + reqbody);
			} else if (reqbody.contains(CodeDefine.KEEPALIVE)) {
				String parentDeviceID = ParseXML(doc, "Notify");
				catalogService.Keepalive(doc, parentDeviceID);
				logger.info("dispatch接收到GB28181通知消息>>>keepalive=" + reqbody);
			} else if (reqbody.contains(CodeDefine.ALARM)) {
				String parentDeviceID = ParseXML(doc, "Notify");
				catalogService.saveAlarm(doc, parentDeviceID);
				logger.info("dispatch接收到GB28181通知消息>>>alarm=" + reqbody);
			}
			return ApiRsp.success();
		} catch (Exception e) { 
			e.printStackTrace();
			return ApiRsp.error();
		}
	}

	public String ParseXML(Document doc, String response) {
		Element e1 = (Element) doc.selectSingleNode(response);
		String ParentDeviceID = e1.elementText("DeviceID");
		return ParentDeviceID;
	}
}
