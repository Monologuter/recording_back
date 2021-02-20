package com.ruoyi.recording.mapper;

import com.ruoyi.recording.domain.CtiMeetRecord;

import java.util.List;


public interface CtiMeetRecordMapper
{
    /**
     * 查询会议记录
     * 
     * @param id 会议记录ID
     * @return 会议记录
     */
    public CtiMeetRecord selectCtiMeetRecordById(String id);

    /**
     * 查询会议记录列表
     * 
     * @param ctiMeetRecord 会议记录
     * @return 会议记录集合
     */
    public List<CtiMeetRecord> selectCtiMeetRecordList(CtiMeetRecord ctiMeetRecord);

    /**
     * 新增会议记录
     * 
     * @param ctiMeetRecord 会议记录
     * @return 结果
     */
    public int insertCtiMeetRecord(CtiMeetRecord ctiMeetRecord);

    /**
     * 修改会议记录
     * 
     * @param ctiMeetRecord 会议记录
     * @return 结果
     */
    public int updateCtiMeetRecord(CtiMeetRecord ctiMeetRecord);

    /**
     * 删除会议记录
     * 
     * @param id 会议记录ID
     * @return 结果
     */
    public int deleteCtiMeetRecordById(String id);

    /**
     * 批量删除会议记录
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteCtiMeetRecordByIds(String[] ids);
}
