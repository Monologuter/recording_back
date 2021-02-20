package com.ruoyi.gb.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.gb.mapper.GbConfigMapper;
import com.ruoyi.gb.mapper.GbMonitorMapper;
import com.ruoyi.gb.domain.GbConfig;
import com.ruoyi.gb.domain.GbMonitor;
import com.ruoyi.gb.domain.GbSysConfig;
import com.ruoyi.gb.service.IGbConfigService;

/**
 * GB28181服务器Service业务层处理
 * 
 * @author ruoyi
 * @date 2020-12-18
 */
@Service
public class GbConfigServiceImpl extends ServiceImpl<GbConfigMapper, GbConfig> implements IGbConfigService 
{
    @Autowired
    private GbConfigMapper gbConfigMapper;

    /**
     * 查询GB28181服务器
     * 
     * @param id GB28181服务器ID
     * @return GB28181服务器
     */
    @Override
    public GbConfig selectGbConfigById(String id)
    {
        return gbConfigMapper.selectGbConfigById(id);
    }

    /**
     * 查询GB28181服务器列表
     * 
     * @param gbConfig GB28181服务器
     * @return GB28181服务器
     */
    @Override
    public List<GbConfig> selectGbConfigList(GbConfig gbConfig)
    {
        return gbConfigMapper.selectGbConfigList(gbConfig);
    }

    /**
     * 新增GB28181服务器
     * 
     * @param gbConfig GB28181服务器
     * @return 结果
     */
    @Override
    public int insertGbConfig(GbConfig gbConfig)
    {
        gbConfig.setCreateTime(System.currentTimeMillis());
        return gbConfigMapper.insertGbConfig(gbConfig);
    }

    /**
     * 修改GB28181服务器
     * 
     * @param gbConfig GB28181服务器
     * @return 结果
     */
    @Override
    public int updateGbConfig(GbConfig gbConfig)
    {
        return gbConfigMapper.updateGbConfig(gbConfig);
    }

    /**
     * 批量删除GB28181服务器
     * 
     * @param ids 需要删除的GB28181服务器ID
     * @return 结果
     */
    @Override
    public int deleteGbConfigByIds(String[] ids)
    {
        return gbConfigMapper.deleteGbConfigByIds(ids);
    }

    /**
     * 删除GB28181服务器信息
     * 
     * @param id GB28181服务器ID
     * @return 结果
     */
    @Override
    public int deleteGbConfigById(String id)
    {
        return gbConfigMapper.deleteGbConfigById(id);
    }
}
