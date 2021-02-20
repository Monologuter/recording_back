package com.ruoyi.web.controller.gb;

import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.ApiRsp;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.gb.domain.GbConfig;
import com.ruoyi.gb.service.ICatalogService;
import com.ruoyi.gb.service.IGbConfigService;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.utils.uuid.UUID;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * GB28181服务器Controller
 * 
 * @author ruoyi
 * @date 2020-12-18
 */
@RestController
@RequestMapping("/gb/config")
public class GbConfigController extends BaseController
{
    @Autowired
    private IGbConfigService gbConfigService;
    
    @Autowired
    private ICatalogService catalogService;

    /**
     * 查询GB28181服务器列表
     */
    @PreAuthorize("@ss.hasPermi('gb:config:list')")
    @GetMapping("/list")
    public TableDataInfo list(GbConfig gbConfig)
    {
        startPage();
        List<GbConfig> list = gbConfigService.selectGbConfigList(gbConfig);
        return getDataTable(list);
    }
    
    /**
     * 查询GB28181服务器列表 不分页
     */
    @GetMapping("/listNoPage")
    public AjaxResult listNoPage(GbConfig gbConfig)
    {
        List<GbConfig> list = gbConfigService.selectGbConfigList(gbConfig);
        return AjaxResult.success(list);
    }

    /**
     * 获取GB28181服务器详细信息
     */
    @PreAuthorize("@ss.hasPermi('gb:config:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return AjaxResult.success(gbConfigService.selectGbConfigById(id));
    }

    /**
     * 新增GB28181服务器
     */
    @PreAuthorize("@ss.hasPermi('gb:config:add')")
    @Log(title = "GB28181服务器", businessType = BusinessType.INSERT)
    @PostMapping("/save")
    public AjaxResult add(@RequestBody GbConfig gbConfig)
    {
    	GbConfig searchResult = gbConfigService.getOne(new QueryWrapper<GbConfig>().eq("name", gbConfig.getName()));
    	if(searchResult!=null) {
    		return AjaxResult.error("名称 "+gbConfig.getName()+"已存在!");
    	}
    	searchResult = gbConfigService.getOne(new QueryWrapper<GbConfig>().eq("device_id", gbConfig.getDeviceId()));
    	if(searchResult!=null) {
    		return AjaxResult.error("域标识 "+gbConfig.getDeviceId()+"已存在!");
    	}
    	
    	gbConfig.setId(UUID.randomUUID().toString());
    	gbConfig.setCreateTime(System.currentTimeMillis());
        return toAjax(gbConfigService.insertGbConfig(gbConfig));
    }

    /**
     * 修改GB28181服务器
     */
    @PreAuthorize("@ss.hasPermi('gb:config:edit')")
    @Log(title = "GB28181服务器", businessType = BusinessType.UPDATE)
    @PutMapping("/edit")
    public AjaxResult edit(@RequestBody GbConfig gbConfig)
    {
        return toAjax(gbConfigService.updateGbConfig(gbConfig));
    }

    /**
     * 删除GB28181服务器
     */
    @PreAuthorize("@ss.hasPermi('gb:config:remove')")
    @Log(title = "GB28181服务器", businessType = BusinessType.DELETE)
	@DeleteMapping("/delete")
    public AjaxResult remove(String id)
    {
        return toAjax(gbConfigService.deleteGbConfigById(id));
    }
    
    /**
     * 更新数据
     */
    @Log(title = "GB28181服务器", businessType = BusinessType.UPDATE)
	@GetMapping("/update")
    public AjaxResult update(String deviceId)
	{
    	if(StringUtils.isEmpty(deviceId) ) {
    		return AjaxResult.error("域标识为空");
    	}
		String result = catalogService.update_gb28181_Catalog(deviceId);
		if (result.contains("200")) {
			return AjaxResult.success();
		}
		return AjaxResult.error(result);
	}
    
    
}
