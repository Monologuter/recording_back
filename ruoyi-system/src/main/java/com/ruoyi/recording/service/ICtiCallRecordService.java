package com.ruoyi.recording.service;



import com.ruoyi.recording.domain.CtiCallRecord;

import java.util.List;

/**
 * 通话记录Service接口
 * 
 * @author ruoyi
 * @date 2021-02-04
 */
public interface ICtiCallRecordService 
{
    /**
     * 查询通话记录
     * 
     * @param id 通话记录ID
     * @return 通话记录
     */
    public CtiCallRecord selectCtiCallRecordById(String id);

    /**
     * 查询通话记录列表
     * 
     * @param ctiCallRecord 通话记录
     * @return 通话记录集合
     */
    public List<CtiCallRecord> selectCtiCallRecordList(CtiCallRecord ctiCallRecord);

    /**
     * 新增通话记录
     * 
     * @param ctiCallRecord 通话记录
     * @return 结果
     */
    public int insertCtiCallRecord(CtiCallRecord ctiCallRecord);

    /**
     * 修改通话记录
     * 
     * @param ctiCallRecord 通话记录
     * @return 结果
     */
    public int updateCtiCallRecord(CtiCallRecord ctiCallRecord);

    /**
     * 批量删除通话记录
     * 
     * @param ids 需要删除的通话记录ID
     * @return 结果
     */
    public int deleteCtiCallRecordByIds(String[] ids);

    /**
     * 删除通话记录信息
     * 
     * @param id 通话记录ID
     * @return 结果
     */
    public int deleteCtiCallRecordById(String id);
}
