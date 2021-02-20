package com.ruoyi.recording.service.impl;

import com.ruoyi.recording.domain.CtiMeetRecord;
import com.ruoyi.recording.mapper.CtiMeetRecordMapper;
import com.ruoyi.recording.service.ICtiMeetRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 会议记录Service业务层处理
 * 
 * @author ruoyi
 * @date 2021-02-04
 */
@Service
public class CtiMeetRecordServiceImpl implements ICtiMeetRecordService
{
    @Autowired
    private CtiMeetRecordMapper ctiMeetRecordMapper;

    /**
     * 查询会议记录
     * 
     * @param id 会议记录ID
     * @return 会议记录
     */
    @Override
    public CtiMeetRecord selectCtiMeetRecordById(String id)
    {
        return ctiMeetRecordMapper.selectCtiMeetRecordById(id);
    }

    /**
     * 查询会议记录列表
     * 
     * @param ctiMeetRecord 会议记录
     * @return 会议记录
     */
    @Override
    public List<CtiMeetRecord> selectCtiMeetRecordList(CtiMeetRecord ctiMeetRecord)
    {
        return ctiMeetRecordMapper.selectCtiMeetRecordList(ctiMeetRecord);
    }

    /**
     * 新增会议记录
     * 
     * @param ctiMeetRecord 会议记录
     * @return 结果
     */
    @Override
    public int insertCtiMeetRecord(CtiMeetRecord ctiMeetRecord)
    {
        return ctiMeetRecordMapper.insertCtiMeetRecord(ctiMeetRecord);
    }

    /**
     * 修改会议记录
     * 
     * @param ctiMeetRecord 会议记录
     * @return 结果
     */
    @Override
    public int updateCtiMeetRecord(CtiMeetRecord ctiMeetRecord)
    {
        return ctiMeetRecordMapper.updateCtiMeetRecord(ctiMeetRecord);
    }

    /**
     * 批量删除会议记录
     * 
     * @param ids 需要删除的会议记录ID
     * @return 结果
     */
    @Override
    public int deleteCtiMeetRecordByIds(String[] ids)
    {
        return ctiMeetRecordMapper.deleteCtiMeetRecordByIds(ids);
    }

    /**
     * 删除会议记录信息
     * 
     * @param id 会议记录ID
     * @return 结果
     */
    @Override
    public int deleteCtiMeetRecordById(String id)
    {
        return ctiMeetRecordMapper.deleteCtiMeetRecordById(id);
    }
}
