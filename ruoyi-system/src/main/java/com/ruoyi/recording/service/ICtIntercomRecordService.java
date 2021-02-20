package com.ruoyi.recording.service;


import com.ruoyi.recording.domain.CtiIntercomRecord;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 会议记录Service接口
 * 
 * @author ruoyi
 * @date 2021-02-04
 */

@Repository
public interface ICtIntercomRecordService 
{
    /**
     * 查询会议记录
     * 
     * @param id 会议记录ID
     * @return 会议记录
     */
    public CtiIntercomRecord selectCtiIntercomRecordById(String id);

    /**
     * 查询会议记录列表
     * 
     * @param ctiIntercomRecord 会议记录
     * @return 会议记录集合
     */
    public List<CtiIntercomRecord> selectCtiIntercomRecordList(CtiIntercomRecord ctiIntercomRecord);

    /**
     * 新增会议记录
     * 
     * @param ctiIntercomRecord 会议记录
     * @return 结果
     */
    public int insertCtiIntercomRecord(CtiIntercomRecord ctiIntercomRecord);

    /**
     * 修改会议记录
     * 
     * @param ctiIntercomRecord 会议记录
     * @return 结果
     */
    public int updateCtiIntercomRecord(CtiIntercomRecord ctiIntercomRecord);

    /**
     * 批量删除会议记录
     * 
     * @param ids 需要删除的会议记录ID
     * @return 结果
     */
    public int deleteCtiIntercomRecordByIds(String[] ids);

    /**
     * 删除会议记录信息
     * 
     * @param id 会议记录ID
     * @return 结果
     */
    public int deleteCtiIntercomRecordById(String id);
}
