package com.ruoyi.gb.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.gb.domain.GbMonitor;

/**
 * 监控Mapper接口
 * 
 * @author ruoyi
 * @date 2020-12-24
 */
public interface GbMonitorMapper extends BaseMapper<GbMonitor>
{
    /**
     * 查询监控
     * 
     * @param id 监控ID
     * @return 监控
     */
    public GbMonitor selectGbMonitorById(String id);

    /**
     * 查询监控列表
     * 
     * @param gbMonitor 监控
     * @return 监控集合
     */
    public List<GbMonitor> selectGbMonitorList(GbMonitor gbMonitor);

    /**
     * 新增监控
     * 
     * @param gbMonitor 监控
     * @return 结果
     */
    public int insertGbMonitor(GbMonitor gbMonitor);

    /**
     * 修改监控
     * 
     * @param gbMonitor 监控
     * @return 结果
     */
    public int updateGbMonitor(GbMonitor gbMonitor);

    /**
     * 删除监控
     * 
     * @param id 监控ID
     * @return 结果
     */
    public int deleteGbMonitorById(String id);

    /**
     * 批量删除监控
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteGbMonitorByIds(String[] ids);

	public List<GbMonitor> listMonitorToAddress(String id);
}
