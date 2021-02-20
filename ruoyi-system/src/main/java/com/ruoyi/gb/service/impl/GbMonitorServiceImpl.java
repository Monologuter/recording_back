package com.ruoyi.gb.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.GbCodeDefine;
import com.ruoyi.common.utils.gb.FileUtils;
import com.ruoyi.common.utils.gb.PoiUtil;
import com.ruoyi.gb.domain.GbAddress;
import com.ruoyi.gb.domain.GbMonitor;
import com.ruoyi.gb.domain.GbSysConfig;
import com.ruoyi.gb.mapper.GbAddressMapper;
import com.ruoyi.gb.mapper.GbConfigMapper;
import com.ruoyi.gb.mapper.GbMonitorMapper;
import com.ruoyi.gb.mapper.GbSysConfigMapper;
import com.ruoyi.gb.service.IGBNoticeService;
import com.ruoyi.gb.service.IGbMonitorService;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;

/**
 * 监控Service业务层处理
 * 
 * @author ruoyi
 * @date 2020-12-24
 */
@Service
public class GbMonitorServiceImpl extends ServiceImpl<GbMonitorMapper, GbMonitor> implements IGbMonitorService {
	@Autowired
	private GbMonitorMapper gbMonitorMapper;
	@Autowired
	private GbSysConfigMapper gbSysConfigMapper;
	@Autowired
	private GbAddressMapper gbAddressMapper;

	@Autowired
	private IGBNoticeService gBNoticeService;

	private final Log logger = LogFactory.getLog(GbMonitorServiceImpl.class);

	@Value("${monitor_camera_url}")
	private String monitor_camera_url;

	/**
	 * 查询监控
	 * 
	 * @param id 监控ID
	 * @return 监控
	 */
	@Override
	public GbMonitor selectGbMonitorById(String id) {
		return gbMonitorMapper.selectGbMonitorById(id);
	}

	/**
	 * 查询监控列表
	 * 
	 * @param gbMonitor 监控
	 * @return 监控
	 */
	@Override
	public List<GbMonitor> selectGbMonitorList(GbMonitor gbMonitor) {
		return gbMonitorMapper.selectGbMonitorList(gbMonitor);
	}

	/**
	 * 新增监控
	 * 
	 * @param gbMonitor 监控
	 * @return 结果
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public int insertGbMonitor(GbMonitor gbMonitor) {
		try {
			this.addXml(gbMonitor);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return gbMonitorMapper.insertGbMonitor(gbMonitor);
	}

	/**
	 * 修改监控
	 * 
	 * @param gbMonitor 监控
	 * @return 结果
	 */
	@Override
	public int updateGbMonitor(GbMonitor gbMonitor) {
		this.updateXml(gbMonitor);
		return gbMonitorMapper.updateGbMonitor(gbMonitor);
	}

	/**
	 * 批量删除监控
	 * 
	 * @param ids 需要删除的监控ID
	 * @return 结果
	 */
	@Override
	public int deleteGbMonitorByIds(String[] ids) {
		return gbMonitorMapper.deleteGbMonitorByIds(ids);
	}

	/**
	 * 删除监控信息
	 * 
	 * @param id 监控ID
	 * @return 结果
	 */
	@Override
	public int deleteGbMonitorById(String id) {
		GbMonitor gbMonitor = gbMonitorMapper.selectById(id);
		this.deleteXml(gbMonitor.getDeviceId());
		return gbMonitorMapper.deleteGbMonitorById(id);
	}

	@Override
	public List<GbMonitor> listMonitorToAddress(String id) {
		return gbMonitorMapper.listMonitorToAddress(id);
	}

	public void addXml(GbMonitor gbMonitor) {

		try {
			if (new File(monitor_camera_url).exists()) {
				SAXReader sas = new SAXReader();
				Document doc = sas.read(new File(monitor_camera_url));
				Element e1 = (Element) doc.selectSingleNode("/ROOT/item/item");
				Element e1_clone = e1.addElement("item");
				e1_clone.addAttribute("text", gbMonitor.getName());
				e1_clone.addAttribute("key", gbMonitor.getDeviceId());
				e1_clone.addAttribute("PlayURL", gbMonitor.getCameraUrl());
				e1_clone.addAttribute("username", gbMonitor.getCameraAccount());
				e1_clone.addAttribute("passwd", gbMonitor.getCameraPasswd());
				e1_clone.addAttribute("Head", gbMonitor.getCameraHead());
				e1_clone.addAttribute("type", "49");
				e1_clone.addAttribute("channleid", "1");
				e1_clone.addAttribute("monitorType", "3");
				FileUtils.writeFile(monitor_camera_url, doc.asXML());
			}
			monitorNotice("addMonitor", gbMonitor);
			refresh();
		} catch (

		Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
	}

	public void updateXml(GbMonitor gbMonitor) {
		try {
			if (new File(monitor_camera_url).exists()) {
				SAXReader sas = new SAXReader();
				Document doc = sas.read(new File(monitor_camera_url));

				Element e1 = (Element) doc.selectSingleNode("//item[@key='" + gbMonitor.getDeviceId() + "']");
				if (null != e1) {
					e1.setAttributeValue("text", gbMonitor.getName());
					e1.setAttributeValue("key", gbMonitor.getDeviceId());
					e1.setAttributeValue("PlayURL", gbMonitor.getCameraUrl());
					e1.setAttributeValue("username", gbMonitor.getCameraAccount());
					e1.setAttributeValue("passwd", gbMonitor.getCameraPasswd());
					e1.setAttributeValue("Head", gbMonitor.getCameraHead());

				}
				FileUtils.writeFile(monitor_camera_url, doc.asXML());
				monitorNotice("updateMonitor", gbMonitor);
				refresh();
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
	}

	public void deleteXml(String key) {
		try {
			if (new File(monitor_camera_url).exists()) {
				SAXReader sas = new SAXReader();
				Document doc = sas.read(new File(monitor_camera_url));
				Element e1 = (Element) doc.selectSingleNode("/ROOT/item/item/item[@key='" + key + "']");
				e1.getParent().remove(e1);
				FileUtils.writeFile(monitor_camera_url, doc.asXML());

				JSONObject body = new JSONObject();
				body.put("deviceId", key);
				gBNoticeService.notice("deleteMonitor", body.toJSONString());
				refresh();
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
	}

	// 普通监控操作消息发送
	public void monitorNotice(String type, GbMonitor gbMonitor) {
		try {
			JSONObject body = new JSONObject();
			body.put("name", gbMonitor.getName());
			body.put("deviceId", gbMonitor.getDeviceId());
			body.put("longitude", gbMonitor.getLongitude());
			body.put("latitude", gbMonitor.getLatitude());
			body.put("ip", gbMonitor.getIpAddress());
			body.put("org", gbMonitor.getAddressId());
			body.put("id", gbMonitor.getDeviceId());
			body.put("account", gbMonitor.getDeviceId());
			body.put("scope", "3");
			body.put("kind", "3");
			gBNoticeService.notice(type, body.toJSONString());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void refresh() {
		String os = System.getProperty("os.name");
		if (!os.toLowerCase().contains("win")) {
			String monitor_camera_port = gbSysConfigMapper
					.selectOne(new QueryWrapper<GbSysConfig>().eq("param_name", "monitor_camera_port")).getParamValue();
			String monitor_camera_ip = gbSysConfigMapper
					.selectOne(new QueryWrapper<GbSysConfig>().eq("param_name", "monitor_camera_ip")).getParamValue();
			if (null != monitor_camera_port && null != monitor_camera_ip) {
				String url = monitor_camera_ip + monitor_camera_port + "/sip/camerupdate";
				HttpUtil.get(url);
			}
		}
	}

	// 错误集合
	List<HashMap<String, Object>> listError = new ArrayList<HashMap<String, Object>>();

	// 全部过一遍 把问题全清出来 没问题在导入
	@Override
	public AjaxResult importMonitorExcel(List<String[]> list, String addressId) {
		List<GbMonitor> gbMonitors = new ArrayList<GbMonitor>();
		Map<String, Object> data = new HashMap<>();
		listError.clear();

		// deviceIds 唯一判断 表格内也要去重
		List<String> deviceIds = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			String[] strs = list.get(i);

			GbMonitor gbMonitor = this.checkImportData(strs, deviceIds, addressId);
			if (gbMonitor != null) {
				gbMonitors.add(gbMonitor);
				gbMonitorMapper.insert(gbMonitor);
				this.addXml(gbMonitor);
			}
		}
		if (listError.size() > 0) {
			data.put("success", false);
			return AjaxResult.success("导入结束，导入失败" + listError.size() + "条,是否导出错误信息?", data);
		}
		data.put("success", true);
		return AjaxResult.success("导入成功", data);
	}

	static final int MONITOR_NAME = 0;
	static final int DEVICE_ID = 1;
	static final int CAMERA_URL = 2;
	static final int CAMERA_ACCOUNT = 3;
	static final int CAMERA_PASSWD = 4;
	static final int CAMERA_HEAD = 5;
	static final int ADDRESS_NAME = 6;

	public GbMonitor checkImportData(String[] data, List<String> deviceIds, String addressId) {
		// 内容完整
		if (StrUtil.hasBlank(data)) {
			this.addErrorMonitorImport(listError, data, "信息不完整");
			return null;
		}
		// 设备编号/监控名称校验
		if (data[DEVICE_ID].length() < 10) {
			this.addErrorMonitorImport(listError, data, "设备编号长度不得低于10位");
		}
		GbMonitor getGbMonitor = gbMonitorMapper
				.selectOne(new QueryWrapper<GbMonitor>().eq("device_id", data[DEVICE_ID]));
		if (getGbMonitor != null) {
			this.addErrorMonitorImport(listError, data, "设备编号 数据库中重复");
			return null;
		} else if (deviceIds.contains(data[DEVICE_ID])) {
			this.addErrorMonitorImport(listError, data, "设备编号 Excel中重复");
			return null;
		}

		if (!("y".equals(data[CAMERA_HEAD]) || "n".equals(data[CAMERA_HEAD]))) {
			this.addErrorMonitorImport(listError, data, "发送心跳必须是y 或 n");
			return null;
		}

		if (data[CAMERA_PASSWD].length() > 0 && data[CAMERA_PASSWD].length() < 5 && data[CAMERA_PASSWD].length() > 25) {
			this.addErrorMonitorImport(listError, data, "监控密码请在5-25位之间");
			return null;
		}

		// 非国标通讯录校验
//		GbAddress getGbAddress = gbAddressMapper.selectOne(new QueryWrapper<GbAddress>()
//				.eq("name", data[ADDRESS_NAME]).eq("type", GbCodeDefine.IS_NOT_GB));
//		if(getGbAddress==null) {
//			this.addErrorMonitorImport(listError, data, "监控名称错误");
//			return null;
//		}
		deviceIds.add(data[DEVICE_ID]);
		GbMonitor gbMonitor = new GbMonitor();
		gbMonitor.setId(UUID.randomUUID().toString());
		gbMonitor.setCreatetime(DateUtils.dateTimeNow());
		gbMonitor.setName(data[MONITOR_NAME]);
		gbMonitor.setDeviceId(data[DEVICE_ID]);
		gbMonitor.setCameraUrl(data[CAMERA_URL]);
		gbMonitor.setCameraAccount(data[CAMERA_ACCOUNT]);
		gbMonitor.setCameraPasswd(data[CAMERA_PASSWD]);
		gbMonitor.setCameraHead("y".equals(data[CAMERA_HEAD]) ? "1" : "0");
		gbMonitor.setAddressId(addressId);
		return gbMonitor;
	}

	/**
	 * 导入错误 添加到集合中
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<HashMap<String, Object>> addErrorMonitorImport(List<HashMap<String, Object>> listError, String[] data,
			String errorMes) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("name", data[MONITOR_NAME]);
		map.put("deviceId", data[DEVICE_ID]);
		map.put("cameraUrl", data[CAMERA_URL]);
		map.put("cameraAccount", data[CAMERA_ACCOUNT]);
		map.put("cameraPasswd", data[CAMERA_PASSWD]);
		map.put("cameraHead", data[CAMERA_HEAD]);
		map.put("errorMes", errorMes);
		listError.add(map);
		return listError;
	}

	/**
	 * 
	 * 导入错误客户信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public void saveErrorImport(HttpServletRequest request, HttpServletResponse response) throws IOException {
		List<String[]> exportList = new ArrayList<>();
		String[] titles = new String[] { "监控名称", "设备编号", "摄像头地址", "监控帐号", "监控密码", "监控心跳", "通讯录ID", "错误信息" };
		exportList.add(titles);
		for (int i = 0; i < listError.size(); i++) {
			String[] strs = new String[] { (String) listError.get(i).get("name"),
					(String) listError.get(i).get("deviceId"), (String) listError.get(i).get("cameraUrl"),
					(String) listError.get(i).get("cameraAccount"), (String) listError.get(i).get("cameraPasswd"),
					(String) listError.get(i).get("cameraHead"), (String) listError.get(i).get("addressId"),
					(String) listError.get(i).get("errorMes"), };
			exportList.add(strs);
		}
		listError.clear();
		PoiUtil.exportData(exportList, "错误信息", response);
	}

}
