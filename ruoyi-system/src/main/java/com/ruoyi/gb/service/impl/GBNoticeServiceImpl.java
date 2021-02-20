package com.ruoyi.gb.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.gb.domain.GbSysConfig;
import com.ruoyi.gb.service.IGBNoticeService;
import com.ruoyi.gb.service.IGbConfigService;
import com.ruoyi.gb.service.IGbSysConfigService;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;

@Service
public class GBNoticeServiceImpl implements IGBNoticeService {

	@Autowired
	private IGbConfigService gbConfigService;
	@Autowired
	private IGbSysConfigService gbSysConfigService;

	public void notice(String type, String body) {
		String noticeUrl = gbSysConfigService.getOne(new QueryWrapper<GbSysConfig>().eq("param_name", "notice_url"))
				.getParamValue();
		String noticeType = gbSysConfigService.getOne(new QueryWrapper<GbSysConfig>().eq("param_name", "notice_type"))
				.getParamValue();
		try {
			if (StringUtils.isNotEmpty(noticeUrl)) {
				String[] urlArray = noticeUrl.split(",");
				for (String urlItem : urlArray) {
					if (noticeType.contains(type)) {
						HttpRequest hreq = HttpRequest.post(urlItem);
						JSONObject query = new JSONObject();
						query.put("data", body);
						query.put("type", type);
						System.out.println(query.toString());
						String result2 = hreq.body(query.toString(), "application/json;charset=utf-8")// 表单内容
								.timeout(10000)// 超时，毫秒
								.execute().body();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
