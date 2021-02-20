/**
 *
 * @dispatch2020
 * @com.ck.core.service.gb28181
 * @UpdateAddressBookService.java
 * @author liubaoping
 * @date 2020年11月12日 下午2:12:01
 * @TODO
 */
package com.ruoyi.gb.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.util.StringUtil;
import com.ruoyi.common.core.domain.ApiRsp;
import com.ruoyi.common.utils.CodeDefine;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.GbCodeDefine;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.gb.domain.GbAddress;
import com.ruoyi.gb.domain.GbAlarm;
import com.ruoyi.gb.domain.GbConfig;
import com.ruoyi.gb.domain.GbMonitor;
import com.ruoyi.gb.service.ICatalogService;
import com.ruoyi.gb.service.IGBNoticeService;
import com.ruoyi.gb.service.IGbAddressService;
import com.ruoyi.gb.service.IGbAlarmService;
import com.ruoyi.gb.service.IGbConfigService;
import com.ruoyi.gb.service.IGbMonitorService;
import com.ruoyi.gb.service.XmlTemplateService;

import cn.hutool.core.lang.UUID;
import cn.hutool.http.HttpRequest;

/**
 *
 * @dispatch2020
 * @com.ck.core.service.gb28181
 * @UpdateAddressBookService.java
 * @author liubaoping
 * @date 2020年11月12日 下午2:12:01
 * @TODO @
 */
@Service
public class CatalogServiceImpl implements ICatalogService {
	private static Logger logger = Logger.getLogger(CatalogServiceImpl.class);
	@Autowired
	private XmlTemplateService xmlTemplateService;
	@Autowired
	private IGbAddressService iGbAddressService;
	@Autowired
	private IGbMonitorService iGbMonitorService;
	@Autowired
	private IGbConfigService iGbConfigService;
	@Autowired
	private IGBNoticeService gBNoticeService;
	@Autowired
	private IGbAlarmService gbAlarmService;

	// GB28181服务器心跳
	public Map<String, Map<String, Object>> Keepalive = new HashMap<String, Map<String, Object>>();
	@Autowired
	@Qualifier("sqlSessionTemplate")
	SqlSessionTemplate sqlSessionTemplate;
	ScheduledExecutorService ses = Executors.newScheduledThreadPool(5);

	/**
	 * 初始化方法
	 */
	@PostConstruct
	public void init() {
		ses.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				for (Map<String, Object> gb : Keepalive.values()) {
					long time = Long.valueOf(String.valueOf(gb.get("time")));
					if (System.currentTimeMillis() - time > 100) {
						gb.put("status", "no");
						gb.put("time", System.currentTimeMillis());
						// 修改数据库
						GbConfig gbConfig = new GbConfig();
						gbConfig.setConnectType(GbCodeDefine.IS_NOT_CONNECT);
						gbConfig.setUpdateTime(System.currentTimeMillis());
						iGbConfigService.update(gbConfig,
								new QueryWrapper<GbConfig>().eq("device_id", gb.get("deviceID").toString()));
					}
				}
			}
		}, 30, 60, TimeUnit.SECONDS);
	}

	/**
	 *
	 * @project_name dispatch2020
	 * @package_name com.ck.core.service.monitor.gb28181
	 * @file_name CatalogService.java
	 * @enclosing_method update_gb28181_Catalog
	 * @return_type ApiRsp
	 * @author liubaoping
	 * @date 2020年11月27日 下午4:17:02
	 * @TODO
	 * @@param jsonobj
	 * @@return
	 */
	public String update_gb28181_Catalog(String deviceID) {
		try {
			String xml = xmlTemplateService.Catalog(deviceID);
			String url = xmlTemplateService.getUrl(deviceID);
			HttpRequest hreq = HttpRequest.post(url);
			return hreq.body(xml).timeout(5000).execute().body();
		} catch (Exception e) {
			e.printStackTrace();
			return e.toString();
		}
	}

	/**
	 *
	 * @project_name dispatch2020
	 * @package_name com.ck.core.service.monitor.gb28181
	 * @file_name CatalogService.java
	 * @enclosing_method Catalog
	 * @return_type void
	 * @author liubaoping
	 * @date 2020年11月27日 下午4:17:06
	 * @TODO
	 * @@param doc
	 * @@param ParentDeviceID
	 */
	public synchronized void Catalog(final Document doc, final String ParentDeviceID) {

		try {
			Element item = (Element) doc.selectSingleNode("Response/DeviceList/Item");
			String deviceID = item.elementText("DeviceID");
			String name = item.elementText("Name");
			String parentID = item.elementText("ParentID");
			String longitude = item.elementText("Longitude");
			String latitude = item.elementText("Latitude");
			String iPAddress = item.elementText("IPAddress");
			if (StringUtil.isEmpty(latitude) && StringUtil.isEmpty(longitude) && StringUtil.isEmpty(iPAddress)) { // 通讯录
				logger.info("目录：" + item.asXML());
				GbAddress org = new GbAddress();
				org.setType(GbCodeDefine.IS_GB);
				org.setDomainId(ParentDeviceID);
				org.setId(ParentDeviceID);
				org.setName(name);
				// 当ParentDeviceID等于parentID时 顶级 否则就是下级
				if (!ParentDeviceID.equals(parentID)) {
					org.setPid(parentID);
				}
				List<GbAddress> parent1 = iGbAddressService
						.list(new QueryWrapper<GbAddress>().eq("id", ParentDeviceID));
				// 不存在就新增
				if (parent1.size() == 0) {
					iGbAddressService.save(org);
				} else {
					iGbAddressService.update(org, new QueryWrapper<GbAddress>().eq("id", ParentDeviceID));
				}
			} else { // 监控
				logger.info("监控：" + item.asXML());
				GbMonitor user = new GbMonitor();
				user.setId(UUID.randomUUID().toString());
				user.setName(name);
				parentID = StringUtils.isEmpty(parentID)?ParentDeviceID:parentID;
				user.setAddressId(parentID);
				user.setDomainId(ParentDeviceID);
				user.setDeviceId(deviceID);
				user.setLatitude(latitude);
				user.setLongitude(longitude);
				user.setIpAddress(iPAddress);
				// 根据parentID 去通讯录查询是否有该通讯录 有就不做操作，没有就新增一个一级通讯录
				GbAddress gbAddress = iGbAddressService.getOne(new QueryWrapper<GbAddress>().eq("id", parentID));
				if (gbAddress == null) {
					GbAddress org = new GbAddress();
					org.setId(ParentDeviceID);
					org.setType(GbCodeDefine.IS_GB);
					org.setName(ParentDeviceID);
					org.setDomainId(ParentDeviceID);
					iGbAddressService.save(org);
				}
				List<GbMonitor> list = iGbMonitorService.list(new QueryWrapper<GbMonitor>().eq("id", deviceID));
				if (list.size() == 0) {
					user.setCreatetime(DateUtils.getTime());
					iGbMonitorService.save(user);
				} else {
					iGbMonitorService.update(user, new QueryWrapper<GbMonitor>().eq("id", deviceID));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("【GB28181消息推送】 " + doc.asXML());
		}

	}

	// gb28181预警信息保存
	@Override
	public void saveAlarm(Document doc, String parentDeviceID) {
		try {

			Element item = (Element) doc.selectSingleNode("Notify");
			String deviceID = item.elementText("DeviceID");
			String alarmPriority = item.elementText("AlarmPriority");
			String alarmMethod = item.elementText("AlarmMethod");
			String alarmTime = item.elementText("AlarmTime");
			String alarmDescription = item.elementText("AlarmDescription");
			String alarmInfo = item.elementText("AlarmInfo");
			String longitude = item.elementText("Longitude");
			String latitude = item.elementText("Latitude");
			// 信息
			Element info = (Element) item.selectSingleNode("Info");
			String alarmType = info.elementText("AlarmType");

			Element alarmTypeParam = (Element) info.selectSingleNode("AlarmTypeParam");
			String eventType = null;
			if (alarmTypeParam != null) {
				eventType = alarmTypeParam.elementText("EventType");
			}
			GbMonitor gbMonitor = iGbMonitorService.getOne(new QueryWrapper<GbMonitor>().eq("device_id", deviceID));
			GbAlarm gbAlarm = new GbAlarm(UUID.randomUUID().toString(), deviceID, alarmPriority, alarmMethod, alarmTime,
					alarmDescription, alarmInfo, alarmType, eventType, longitude, latitude, gbMonitor.getIpAddress(),
					gbMonitor.getDomainId());
			if (gbAlarmService.saveGbAlarm(gbAlarm) > 0) {
				gBNoticeService.notice("alarm", JSON.toJSONString(gbAlarm));
			}
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}

	/**
	 *
	 */
	public void Keepalive(Document doc, String parentDeviceID) {
		Element item = (Element) doc.selectSingleNode("Notify");
		String deviceID = item.elementText("DeviceID");
		String status = item.elementText("Status");
		Map<String, Object> KeepaliveMap = Keepalive.get(deviceID);
		if (KeepaliveMap == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("deviceID", deviceID);
			map.put("status", "ok");
			map.put("time", System.currentTimeMillis());
			Keepalive.put(deviceID, map);
			gBNoticeService.notice("Keepalive", Keepalive.toString());
			// 修改数据库
			GbConfig gbConfig = new GbConfig();
			gbConfig.setConnectType(GbCodeDefine.IS_CONNECT);
			gbConfig.setUpdateTime(System.currentTimeMillis());
			iGbConfigService.update(gbConfig, new QueryWrapper<GbConfig>().eq("device_id", deviceID));
		}
	}

	public ApiRsp PresetQuery(JSONObject jsonobj) {
		String deviceID = jsonobj.getString("deviceID");
		if (StringUtils.isEmpty(deviceID)) {
			return ApiRsp.error();
		}
		try {
			String xml = xmlTemplateService.PresetQuery(deviceID);
			String url = xmlTemplateService.getUrl(deviceID);
			HttpRequest hreq = HttpRequest.post(url);
			String result2 = hreq.body(xml).timeout(5000).execute().body();
			if (result2.contains("200")) {
				return ApiRsp.success();
			}
			return ApiRsp.error();
		} catch (Exception e) {
			return ApiRsp.success(e.toString());
		}
	}
}
