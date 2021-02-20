package com.ruoyi.recording.service.impl;

import com.ruoyi.recording.domain.CtiIntercomRecord;
import com.ruoyi.recording.mapper.CtiIntercomRecordMapper;
import com.ruoyi.recording.service.ICtIntercomRecordService;
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
public class CtiIntercomRecordServiceImpl implements ICtIntercomRecordService {
    @Autowired
    private CtiIntercomRecordMapper ctiIntercomRecordMapper;

    @Override
    public CtiIntercomRecord selectCtiIntercomRecordById(String id) {
        return ctiIntercomRecordMapper.selectCtiIntercomRecordById(id);
    }

    @Override
    public List<CtiIntercomRecord> selectCtiIntercomRecordList(CtiIntercomRecord ctiIntercomRecord) {
        return ctiIntercomRecordMapper.selectCtiIntercomRecordList(ctiIntercomRecord);
    }

    @Override
    public int insertCtiIntercomRecord(CtiIntercomRecord ctiIntercomRecord) {
        return ctiIntercomRecordMapper.insertCtiIntercomRecord(ctiIntercomRecord);
    }

    @Override
    public int updateCtiIntercomRecord(CtiIntercomRecord ctiIntercomRecord) {
        return ctiIntercomRecordMapper.updateCtiIntercomRecord(ctiIntercomRecord);
    }

    @Override
    public int deleteCtiIntercomRecordByIds(String[] ids) {
        return ctiIntercomRecordMapper.deleteCtiIntercomRecordByIds(ids);
    }

    @Override
    public int deleteCtiIntercomRecordById(String id) {
        return ctiIntercomRecordMapper.deleteCtiIntercomRecordById(id);
    }


}
