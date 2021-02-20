package com.ruoyi.gb.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.gb.domain.GbAddress;
import com.ruoyi.gb.domain.GbMonitor;

/**
 * 监控Service接口
 * 
 * @author ruoyi
 * @date 2020-12-24
 */
public interface IGbMonitorService extends IService<GbMonitor>
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
     * 批量删除监控
     * 
     * @param ids 需要删除的监控ID
     * @return 结果
     */
    public int deleteGbMonitorByIds(String[] ids);

    /**
     * 删除监控信息
     * 
     * @param id 监控ID
     * @return 结果
     */
    public int deleteGbMonitorById(String id);

	public List<GbMonitor> listMonitorToAddress(String id);

	public AjaxResult importMonitorExcel(List<String[]> list, String orgid);

	public void saveErrorImport(HttpServletRequest req, HttpServletResponse res) throws IOException;
}
