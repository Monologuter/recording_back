package com.ruoyi.web.controller.recording;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;

import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.recording.domain.CtiMeetRecord;
import com.ruoyi.recording.service.ICtiMeetRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recording/meet")
public class CtiMeetRecordController extends BaseController
{
    @Autowired
    private ICtiMeetRecordService ctiMeetRecordService;

    /**
     * 查询会议记录列表
     */
    @PreAuthorize("@ss.hasPermi('recording:meet:list')")
    @GetMapping("/list")
    public TableDataInfo list(CtiMeetRecord ctiMeetRecord)
    {
        startPage();
        List<CtiMeetRecord> list = ctiMeetRecordService.selectCtiMeetRecordList(ctiMeetRecord);

        return getDataTable(list);
    }

    /**
     * 导出会议记录列表
     */
    @PreAuthorize("@ss.hasPermi('recording:meet:export')")
    @Log(title = "会议记录", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(CtiMeetRecord ctiMeetRecord)
    {
        List<CtiMeetRecord> list = ctiMeetRecordService.selectCtiMeetRecordList(ctiMeetRecord);
        ExcelUtil<CtiMeetRecord> util = new ExcelUtil<CtiMeetRecord>(CtiMeetRecord.class);
        return util.exportExcel(list, "meet");
    }

    /**
     * 获取会议记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('recording:meet:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return AjaxResult.success(ctiMeetRecordService.selectCtiMeetRecordById(id));
    }

    /**
     * 新增会议记录
     */
    @PreAuthorize("@ss.hasPermi('recording:meet:add')")
    @Log(title = "会议记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CtiMeetRecord ctiMeetRecord)
    {
        return toAjax(ctiMeetRecordService.insertCtiMeetRecord(ctiMeetRecord));
    }

    /**
     * 修改会议记录
     */
    @PreAuthorize("@ss.hasPermi('recording:meet:edit')")
    @Log(title = "会议记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CtiMeetRecord ctiMeetRecord)
    {
        return toAjax(ctiMeetRecordService.updateCtiMeetRecord(ctiMeetRecord));
    }

    /**
     * 删除会议记录
     */
    @PreAuthorize("@ss.hasPermi('recording:meet:remove')")
    @Log(title = "会议记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(ctiMeetRecordService.deleteCtiMeetRecordByIds(ids));
    }
}
