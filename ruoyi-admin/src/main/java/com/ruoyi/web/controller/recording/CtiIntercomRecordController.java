package com.ruoyi.web.controller.recording;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.recording.domain.CtiIntercomRecord;
import com.ruoyi.recording.service.ICtIntercomRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recording/intercom")
public class CtiIntercomRecordController extends BaseController
{
    @Autowired
    private ICtIntercomRecordService ctiIntercomRecordService;

    /**
     * 查询会议记录列表
     */
    @PreAuthorize("@ss.hasPermi('recording:Intercom:list')")
    @GetMapping("/list")
    public TableDataInfo list(CtiIntercomRecord ctiIntercomRecord)
    {
        startPage();
        List<CtiIntercomRecord> list = ctiIntercomRecordService.selectCtiIntercomRecordList(ctiIntercomRecord);
        return getDataTable(list);
    }

    /**
     * 导出会议记录列表
     */
    @PreAuthorize("@ss.hasPermi('recording:Intercom:export')")
    @Log(title = "会议记录", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(CtiIntercomRecord ctiIntercomRecord)
    {
        List<CtiIntercomRecord> list = ctiIntercomRecordService.selectCtiIntercomRecordList(ctiIntercomRecord);
        ExcelUtil<CtiIntercomRecord> util = new ExcelUtil<CtiIntercomRecord>(CtiIntercomRecord.class);
        return util.exportExcel(list, "Intercom");
    }

    /**
     * 获取会议记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('recording:Intercom:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return AjaxResult.success(ctiIntercomRecordService.selectCtiIntercomRecordById(id));
    }

    /**
     * 新增会议记录
     */
    @PreAuthorize("@ss.hasPermi('recording:Intercom:add')")
    @Log(title = "会议记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CtiIntercomRecord ctiIntercomRecord)
    {
        return toAjax(ctiIntercomRecordService.insertCtiIntercomRecord(ctiIntercomRecord));
    }

    /**
     * 修改会议记录
     */
    @PreAuthorize("@ss.hasPermi('recording:Intercom:edit')")
    @Log(title = "会议记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CtiIntercomRecord ctiIntercomRecord)
    {
        return toAjax(ctiIntercomRecordService.updateCtiIntercomRecord(ctiIntercomRecord));
    }

    /**
     * 删除会议记录
     */
    @PreAuthorize("@ss.hasPermi('recording:Intercom:remove')")
    @Log(title = "会议记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(ctiIntercomRecordService.deleteCtiIntercomRecordByIds(ids));
    }
}
