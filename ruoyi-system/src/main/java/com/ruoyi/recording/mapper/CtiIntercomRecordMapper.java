package com.ruoyi.recording.mapper;


import com.ruoyi.recording.domain.CtiIntercomRecord;

import java.util.List;

/**
 * @Author 马小姐
 * @ClassName CtiIntercomRecordMapper
 * @Date 2021-02-04 17:30
 * @Version 1.0
 * @Description:
 */
public interface CtiIntercomRecordMapper {

    /**
     * 查询对讲记录
     *
     * @param id 对讲记录ID
     * @return 对讲记录
     */
    public CtiIntercomRecord selectCtiIntercomRecordById(String id);

    /**
     * 查询对讲记录列表
     *
     * @param
     * @return 对讲记录集合
     */
    public List<CtiIntercomRecord> selectCtiIntercomRecordList(CtiIntercomRecord ctiIntercomRecord);

    /**
     * 新增对讲记录
     *
     * @param
     * @return 结果
     */
    public int insertCtiIntercomRecord(CtiIntercomRecord ctiIntercomRecord);

    /**
     * 修改对讲记录
     *
     * @param
     * @return 结果
     */
    public int updateCtiIntercomRecord(CtiIntercomRecord ctiIntercomRecord);

    /**
     * 删除对讲记录
     *
     * @param id 对讲记录ID
     * @return 结果
     */
    public int deleteCtiIntercomRecordById(String id);

    /**
     * 批量删除对讲记录
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteCtiIntercomRecordByIds(String[] ids);
}
