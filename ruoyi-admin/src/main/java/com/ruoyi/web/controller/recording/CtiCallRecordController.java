package com.ruoyi.web.controller.recording;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.recording.domain.CtiCallRecord;
import com.ruoyi.recording.service.ICtiCallRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recording/record")
public class CtiCallRecordController extends BaseController
{
    @Autowired
    private ICtiCallRecordService ctiCallRecordService;

    /**
     * 查询通话记录列表
     */
    @PreAuthorize("@ss.hasPermi('recording:record:list')")
    @GetMapping("/list")
    public TableDataInfo list(CtiCallRecord ctiCallRecord)
    {
        startPage();
        List<CtiCallRecord> list = ctiCallRecordService.selectCtiCallRecordList(ctiCallRecord);
        return getDataTable(list);
    }

    /**
     * 导出通话记录列表
     */
    @PreAuthorize("@ss.hasPermi('recording:record:export')")
    @Log(title = "通话记录", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(CtiCallRecord ctiCallRecord)
    {
        List<CtiCallRecord> list = ctiCallRecordService.selectCtiCallRecordList(ctiCallRecord);
        ExcelUtil<CtiCallRecord> util = new ExcelUtil<CtiCallRecord>(CtiCallRecord.class);
        return util.exportExcel(list, "record");
    }

    /**
     * 获取通话记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('recording:record:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return AjaxResult.success(ctiCallRecordService.selectCtiCallRecordById(id));
    }

    /**
     * 新增通话记录
     */
    @PreAuthorize("@ss.hasPermi('recording:record:add')")
    @Log(title = "通话记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CtiCallRecord ctiCallRecord)
    {
        return toAjax(ctiCallRecordService.insertCtiCallRecord(ctiCallRecord));
    }

    /**
     * 修改通话记录
     */
    @PreAuthorize("@ss.hasPermi('recording:record:edit')")
    @Log(title = "通话记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CtiCallRecord ctiCallRecord)
    {
        return toAjax(ctiCallRecordService.updateCtiCallRecord(ctiCallRecord));
    }

    /**
     * 删除通话记录
     */
    @PreAuthorize("@ss.hasPermi('recording:record:remove')")
    @Log(title = "通话记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(ctiCallRecordService.deleteCtiCallRecordByIds(ids));
    }
}
