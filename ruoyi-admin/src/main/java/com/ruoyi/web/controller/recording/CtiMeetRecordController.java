package com.ruoyi.web.controller.recording;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.recording.domain.CtiMeetRecord;
import com.ruoyi.recording.service.ICtiMeetRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.ArrayList;
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
            String path2 = "/home/ck/data/recordings/conference/meet_"+time+".zip ";

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
                return_url="/audio/conference/meet_"+time+".zip";
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
