package com.ruoyi.gb.service;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.gb.domain.GbConfig;
import com.ruoyi.gb.domain.GbMonitor;
import com.ruoyi.gb.domain.GbSysConfig;

/**
 * GB28181服务器Service接口
 * 
 * @author ruoyi
 * @date 2020-12-18
 */
public interface  IGbConfigService extends IService<GbConfig>
{
    /**
     * 查询GB28181服务器
     * 
     * @param id GB28181服务器ID
     * @return GB28181服务器
     */
    public GbConfig selectGbConfigById(String id);

    /**
     * 查询GB28181服务器列表
     * 
     * @param gbConfig GB28181服务器
     * @return GB28181服务器集合
     */
    public List<GbConfig> selectGbConfigList(GbConfig gbConfig);

    /**
     * 新增GB28181服务器
     * 
     * @param gbConfig GB28181服务器
     * @return 结果
     */
    public int insertGbConfig(GbConfig gbConfig);

    /**
     * 修改GB28181服务器
     * 
     * @param gbConfig GB28181服务器
     * @return 结果
     */
    public int updateGbConfig(GbConfig gbConfig);

    /**
     * 批量删除GB28181服务器
     * 
     * @param ids 需要删除的GB28181服务器ID
     * @return 结果
     */
    public int deleteGbConfigByIds(String[] ids);

    /**
     * 删除GB28181服务器信息
     * 
     * @param id GB28181服务器ID
     * @return 结果
     */
    public int deleteGbConfigById(String id);
}
