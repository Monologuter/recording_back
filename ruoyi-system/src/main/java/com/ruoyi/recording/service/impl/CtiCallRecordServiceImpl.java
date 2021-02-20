package com.ruoyi.recording.service.impl;

import com.ruoyi.recording.domain.CtiCallRecord;
import com.ruoyi.recording.mapper.CtiCallRecordMapper;
import com.ruoyi.recording.service.ICtiCallRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 通话记录Service业务层处理
 * 
 * @author ruoyi
 * @date 2021-02-04
 */
@Service
public class CtiCallRecordServiceImpl implements ICtiCallRecordService
{
    @Autowired
    private CtiCallRecordMapper ctiCallRecordMapper;

    /**
     * 查询通话记录
     * 
     * @param id 通话记录ID
     * @return 通话记录
     */
    @Override
    public CtiCallRecord selectCtiCallRecordById(String id)
    {
        return ctiCallRecordMapper.selectCtiCallRecordById(id);
    }

    /**
     * 查询通话记录列表
     * 
     * @param ctiCallRecord 通话记录
     * @return 通话记录
     */
    @Override
    public List<CtiCallRecord> selectCtiCallRecordList(CtiCallRecord ctiCallRecord)
    {
        return ctiCallRecordMapper.selectCtiCallRecordList(ctiCallRecord);
    }

    /**
     * 新增通话记录
     * 
     * @param ctiCallRecord 通话记录
     * @return 结果
     */
    @Override
    public int insertCtiCallRecord(CtiCallRecord ctiCallRecord)
    {
        return ctiCallRecordMapper.insertCtiCallRecord(ctiCallRecord);
    }

    /**
     * 修改通话记录
     * 
     * @param ctiCallRecord 通话记录
     * @return 结果
     */
    @Override
    public int updateCtiCallRecord(CtiCallRecord ctiCallRecord)
    {
        return ctiCallRecordMapper.updateCtiCallRecord(ctiCallRecord);
    }

    /**
     * 批量删除通话记录
     * 
     * @param ids 需要删除的通话记录ID
     * @return 结果
     */
    @Override
    public int deleteCtiCallRecordByIds(String[] ids)
    {
        return ctiCallRecordMapper.deleteCtiCallRecordByIds(ids);
    }

    /**
     * 删除通话记录信息
     * 
     * @param id 通话记录ID
     * @return 结果
     */
    @Override
    public int deleteCtiCallRecordById(String id)
    {
        return ctiCallRecordMapper.deleteCtiCallRecordById(id);
    }
}
