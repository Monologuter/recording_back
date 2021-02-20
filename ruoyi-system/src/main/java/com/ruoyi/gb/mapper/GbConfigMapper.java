package com.ruoyi.gb.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.gb.domain.GbConfig;
import com.ruoyi.gb.domain.GbSysConfig;

/**
 * GB28181服务器Mapper接口
 * 
 * @author ruoyi
 * @date 2020-12-18
 */
public interface GbConfigMapper extends BaseMapper<GbConfig>
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
     * 删除GB28181服务器
     * 
     * @param id GB28181服务器ID
     * @return 结果
     */
    public int deleteGbConfigById(String id);

    /**
     * 批量删除GB28181服务器
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteGbConfigByIds(String[] ids);
}
