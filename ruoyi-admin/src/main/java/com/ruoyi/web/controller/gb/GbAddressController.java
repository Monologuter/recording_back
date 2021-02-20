package com.ruoyi.web.controller.gb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.uuid.UUID;
import com.ruoyi.gb.domain.GbAddress;
import com.ruoyi.gb.domain.GbConfig;
import com.ruoyi.gb.domain.GbMonitor;
import com.ruoyi.gb.service.IGbAddressService;
import com.ruoyi.gb.service.IGbMonitorService;
import com.ruoyi.gb.service.impl.GbAddressServiceImpl;

@RestController
@RequestMapping("/gb/address")
public class GbAddressController extends BaseController{
	@Autowired
	private IGbAddressService gbAddressService;
	
	@Autowired
	private IGbMonitorService gbMonitorService;
	
	@GetMapping("/list")
    public AjaxResult list(GbAddress gbAddress)
    {
        List<GbAddress> list = gbAddressService.selectGbAddressList(gbAddress);
        return AjaxResult.success(list);
    }
	
	@GetMapping("/queryTree")
    public AjaxResult queryTree(GbAddress gbAddress)
    {
		List<Map<String, Object>> treeList = new ArrayList<>();
        List<GbAddress> list = gbAddressService.selectGbAddressList(gbAddress);
        for (GbAddress item : list) {
        	Map<String,Object> map = new HashMap<>();
        	map.put("key", item.getId());
        	map.put("value", item.getId());
        	map.put("title", item.getName());
        	map.put("pid", item.getPid());
        	map.put("data", item);
        	treeList.add(map);
		}
        return AjaxResult.success(treeList);
    }
	
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return AjaxResult.success(gbAddressService.selectGbAddressById(id));
    }
    
    /**
     * 新增通讯录
     */
    @Log(title = "GB28181通讯录", businessType = BusinessType.INSERT)
    @PostMapping("/save")
    public AjaxResult add(@RequestBody GbAddress gbAddress)
    {
    	GbAddress searchResult = gbAddressService.getOne(new QueryWrapper<GbAddress>().eq("name", gbAddress.getName()));
    	if(searchResult!=null) {
    		return AjaxResult.error("名称 "+gbAddress.getName()+"已存在!");
    	}
    	gbAddress.setId(UUID.randomUUID().toString());
        return toAjax(gbAddressService.insertGbAddress(gbAddress));
    }

    /**
     * 修改通讯录
     */
    @Log(title = "GB28181通讯录", businessType = BusinessType.UPDATE)
    @PutMapping("/edit")
    public AjaxResult edit(@RequestBody GbAddress gbAddress)
    {
    	GbAddress searchResult = gbAddressService.getOne(new QueryWrapper<GbAddress>().eq("name", gbAddress.getName()));
    	if(searchResult!=null&&!gbAddress.getId().equals(searchResult.getId())) {
    		return AjaxResult.error("名称 "+gbAddress.getName()+"已存在!");
    	}
        return toAjax(gbAddressService.updateGbAddress(gbAddress));
    }

    /**
     * 删除通讯录
     */
    @DeleteMapping("/delete")
    public AjaxResult remove(String id)
    {
    	List<GbAddress> gbAddressList = gbAddressService.list(new QueryWrapper<GbAddress>().eq("pid", id));
    	if(gbAddressList.size()>0) {
    		return AjaxResult.error("存在下级通讯录，请先删除");
    	}
    	List<GbMonitor> gbMonitorList = gbMonitorService.list(new QueryWrapper<GbMonitor>().eq("address_id", id));
    	if(gbMonitorList.size()>0) {
    		return AjaxResult.error("通讯录下存在设备，请先删除");
    	}
        return toAjax(gbAddressService.deleteGbAddressById(id));
    }
}
