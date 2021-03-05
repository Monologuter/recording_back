package com.ruoyi.web.controller.recording;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.recording.domain.CtiIntercomRecord;
import com.ruoyi.recording.service.ICtIntercomRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.*;

@RestController
@RequestMapping("/recording/intercom")
public class CtiIntercomRecordController extends BaseController {
    @Autowired
    private ICtIntercomRecordService ctiIntercomRecordService;

    /**
     * 查询对讲记录列表
     */
    @PreAuthorize("@ss.hasPermi('recording:Intercom:list')")
    @GetMapping("/list")
    public TableDataInfo list(CtiIntercomRecord ctiIntercomRecord) {
        startPage();
        List<CtiIntercomRecord> list = ctiIntercomRecordService.selectCtiIntercomRecordList(ctiIntercomRecord);
        return getDataTable(list);
    }

    /**
     * 导出对讲记录列表
     */
    @PreAuthorize("@ss.hasPermi('recording:Intercom:export')")
    @Log(title = "对讲记录", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(CtiIntercomRecord ctiIntercomRecord) {
        List<CtiIntercomRecord> list = ctiIntercomRecordService.selectCtiIntercomRecordList(ctiIntercomRecord);
        ExcelUtil<CtiIntercomRecord> util = new ExcelUtil<CtiIntercomRecord>(CtiIntercomRecord.class);
        return util.exportExcel(list, "Intercom");
    }

    /**
     * 获取对讲记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('recording:Intercom:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id) {
        return AjaxResult.success(ctiIntercomRecordService.selectCtiIntercomRecordById(id));
    }

    /**
     * 新增对讲记录
     */
    @PreAuthorize("@ss.hasPermi('recording:Intercom:add')")
    @Log(title = "对讲记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CtiIntercomRecord ctiIntercomRecord) {
        return toAjax(ctiIntercomRecordService.insertCtiIntercomRecord(ctiIntercomRecord));
    }

    /**
     * 修改对讲记录
     */
    @PreAuthorize("@ss.hasPermi('recording:Intercom:edit')")
    @Log(title = "对讲记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CtiIntercomRecord ctiIntercomRecord) {
        return toAjax(ctiIntercomRecordService.updateCtiIntercomRecord(ctiIntercomRecord));
    }

    /**
     * 删除对讲记录
     */
    @PreAuthorize("@ss.hasPermi('recording:Intercom:remove')")
    @Log(title = "对讲记录", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids) {
        return toAjax(ctiIntercomRecordService.deleteCtiIntercomRecordByIds(ids));
    }

    /**
     * 对讲记录批量下载
     * 首先从前端获取传递地址的json对象
     * 将json对象转化为数组
     * 将地址每个都拼接起来 拷贝到一个空文件夹中
     * 然后将这个文件夹打包
     * 将这个压缩包的地址返回到前端
     * 前端就相当于下载单个文件
     */
    @RequestMapping(value = "/downloadRecord",method = RequestMethod.POST)
    public AjaxResult downloadSelected(@RequestBody JSONObject jsonObject) throws IOException {


        InputStream is = null;
        String time=DateUtils.dateTimeNow();

        String[] rmPath = new String[]{"find ", "/home/ck/data/recordings/conference ", "-mtime  +1   ", "-name ", "*.zip ", "-exec rm -rf {} ", "\\" ," ;"};
        StringBuffer sbRmPath = new StringBuffer();
        for (int i = 0; i < rmPath.length; i++) {
            sbRmPath.append(rmPath[i]);
        }

        String path = "/home/ck/data/recordings/conference/Temp_"+time;
        //        保存压缩包的文件目录
        String path2 = "/home/ck/data/recordings/conference/intercom_"+time+".zip ";

        String return_url="";

        try{
        System.out.println(sbRmPath.toString());
        Runtime.getRuntime().exec(sbRmPath.toString());
        JSONArray jsonArray = jsonObject.getJSONArray("urls");
        //将其转成一个地址数组
        System.out.println("jsonArray = " + jsonArray);
        //创建一个临时文件夹 将文件拷贝到这个文件夹中 等打包完成之后需要将其删除
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        for (int i = 0; i <jsonArray.size();i++) {
            String path1 = "/home/ck/data/recordings/conference"+jsonArray.get(i);
            String cmd= "cp "+ path1+" "+path;
                  //执行copy命令
            Runtime.getRuntime().exec(cmd);

        }

        String cmd1 = "zip -q -r " +path2 + path;
        //执行压缩命令
            Process ps = Runtime.getRuntime().exec(cmd1);
            is = ps.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = br.readLine()) != null) {
                // 执行结果加上回车
                sb.append(line).append("\n");
            }
        Runtime.getRuntime().exec("rm -rf "+path);
             return_url="/audio/conference/intercom_"+time+".zip";
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if (null != is) {
                try {
                    is.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        System.out.printf(return_url);
        return AjaxResult.success("成功",return_url);
    }
}