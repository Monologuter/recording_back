package com.ruoyi.web.controller.gb;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.util.StringUtil;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.gb.PoiUtil;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.utils.uuid.UUID;
import com.ruoyi.gb.domain.GbAddress;
import com.ruoyi.gb.domain.GbMonitor;
import com.ruoyi.gb.service.IGbAddressService;
import com.ruoyi.gb.service.IGbMonitorService;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 监控Controller
 * 
 * @author ruoyi
 * @date 2020-12-24
 */
@Controller
@RequestMapping("/gb/monitor")
public class GbMonitorController extends BaseController
{
    @Autowired
    private IGbMonitorService gbMonitorService;
    
    @Autowired
	private IGbAddressService gbAddressService;
    
    /**
     * 查询监控列表
     */
    @ResponseBody
    @GetMapping("/list")
    public TableDataInfo list(GbMonitor gbMonitor)
    {
        startPage();
        List<GbMonitor> list = gbMonitorService.selectGbMonitorList(gbMonitor);
        return getDataTable(list);
    }
    
    @ResponseBody
	@GetMapping("/queryTree")
    public AjaxResult queryTree(String id,String name)
    {
		List<Map<String, Object>> treeList = new ArrayList<>();
		List<GbAddress> addressList = new ArrayList<>();
		List<GbMonitor> monitorList = new ArrayList<>();
		
		Map<String, Object> icon = new HashMap<>();
		icon.put("icon", "camera");
		Map<String, Object> style = new HashMap<>();
		style.put("color", "green");
		if(StringUtil.isEmpty(id)) {
			addressList = gbAddressService.list(new QueryWrapper<GbAddress>().isNull("pid"));
		}else {
			addressList = gbAddressService.list(new QueryWrapper<GbAddress>().eq("pid", id));
			monitorList = gbMonitorService.list(new QueryWrapper<GbMonitor>().eq("address_id", id));
		}
		if(StringUtil.isNotEmpty(name)) {
			addressList = gbAddressService.list(new QueryWrapper<GbAddress>().like("name", name));
			monitorList = gbMonitorService.list(new QueryWrapper<GbMonitor>().like("name", name));
		}
        for (GbAddress item : addressList) {
        	Map<String,Object> map = new HashMap<>();
        	map.put("key", item.getId());
        	map.put("value", item.getId());
        	map.put("title", item.getName());
        	map.put("type", "address");
        	map.put("pid", id);
        	map.put("data", item);
        	treeList.add(map);
		}
        for (GbMonitor item : monitorList) {
        	Map<String,Object> map = new HashMap<>();
        	map.put("key", item.getId());
        	map.put("value", item.getId());
        	map.put("title", item.getName());
        	map.put("pid", id);
        	map.put("isLeaf", true);
        	map.put("type", "monitor");
        	map.put("slots", icon);
        	map.put("stype", style);
        	map.put("data", item);
        	treeList.add(map);
		}
        return AjaxResult.success(treeList);
    }

    /**
     * 导出监控列表
     */
    @ResponseBody
    @Log(title = "监控", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(GbMonitor gbMonitor)
    {
        List<GbMonitor> list = gbMonitorService.selectGbMonitorList(gbMonitor);
        ExcelUtil<GbMonitor> util = new ExcelUtil<GbMonitor>(GbMonitor.class);
        return util.exportExcel(list, "monitor");
    }

    /**
     * 获取监控详细信息
     */
    @ResponseBody
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return AjaxResult.success(gbMonitorService.selectGbMonitorById(id));
    }

    /**
     * 新增监控
     */
    @ResponseBody
    @Log(title = "监控", businessType = BusinessType.INSERT)
    @PostMapping("/save")
    public AjaxResult add(@RequestBody GbMonitor gbMonitor)
    {
    	GbMonitor getByDeviceId = gbMonitorService.getOne(new QueryWrapper<GbMonitor>().eq("device_id", gbMonitor.getDeviceId()));
    	if(getByDeviceId != null) {
    		return AjaxResult.error("设备编号已存在");
    	}
    	gbMonitor.setCameraNo(gbMonitor.getDeviceId());
    	gbMonitor.setId(UUID.randomUUID().toString());
        return toAjax(gbMonitorService.insertGbMonitor(gbMonitor));
    }

    /**
     * 修改监控
     */
    @ResponseBody
    @Log(title = "监控", businessType = BusinessType.UPDATE)
    @PutMapping("/edit")
    public AjaxResult edit(@RequestBody GbMonitor gbMonitor)
    {
    	GbMonitor getByDeviceId = gbMonitorService.getOne(new QueryWrapper<GbMonitor>().eq("device_id", gbMonitor.getDeviceId()));
    	if(getByDeviceId != null && !gbMonitor.getId().equals(getByDeviceId.getId())) {
    		return AjaxResult.error("设备编号已存在");
    	}
        return toAjax(gbMonitorService.updateGbMonitor(gbMonitor));
    }

    /**
     * 删除监控
     */
    @ResponseBody
    @Log(title = "监控", businessType = BusinessType.DELETE)
	@DeleteMapping("/delete")
    public AjaxResult remove(String id)
    {
        return toAjax(gbMonitorService.deleteGbMonitorById(id));
    }
    
	/**
	 * 导入监控
	 */
    @ResponseBody
	@PostMapping("/monitorImport")
	public AjaxResult monitorImport(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("file")MultipartFile file, @RequestParam("addressId")String addressId) throws IOException {
		if(StringUtils.isEmpty(addressId)) {
			return AjaxResult.error("通讯录为空!");
		}
		List<String[]> list = PoiUtil.importData(file.getInputStream(), 1, 0);
		return gbMonitorService.importMonitorExcel(list, addressId);
	}
	
	/**
	 * 导入错误信息
	 */
	@GetMapping("/errerExport")
	public void errerExport(HttpServletRequest req, HttpServletResponse res) throws Exception {
			gbMonitorService.saveErrorImport(req, res);
	}
	
}
