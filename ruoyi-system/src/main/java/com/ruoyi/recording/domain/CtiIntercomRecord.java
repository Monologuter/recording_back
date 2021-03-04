package com.ruoyi.recording.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.annotation.Excel;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @Author 马小姐
 * @ClassName CtiMeetRecord
 * @Date 2021-02-04 09:59
 * @Version 1.0
 * @Description:
 */
@TableName("cti_meet_record")
public class CtiIntercomRecord implements Serializable {
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private String id;

    /** 会议名称 */
    @Excel(name = "会议名称")
    private String meetname;

    /** 会议号 */
    @Excel(name = "会议号")
    private String meetnum;

    /** 开始时间 */
    @Excel(name = "开始时间")
    private Long starttime;

    /** 结束时间 */
    @Excel(name = "结束时间")
    private Long endtime;

    /** 文件名 */
    @Excel(name = "文件名")
    private String uuid;

    /** 是否视频呼叫 ，取值范围【true：是，false：】 默认为false' */
    @Excel(name = "是否视频呼叫 ，取值范围【true：是，false：】 默认为false'")
    private String video;

    /** 会议成员号 */
    @Excel(name = "会议成员号")
    private String meetmeber;

    /** 会议地址 */
    @Excel(name = "会议地址")
    private String url;

    /** 类型，1：语音会议，2：视屏会议，3：对讲 */
    @Excel(name = "类型，1：语音会议，2：视屏会议，3：对讲")
    private String type;

    /** 讲话人名称 */
    @Excel(name = "讲话人名称")
    private String name;

    /** 讲话人 */
    @Excel(name = "讲话人")
    private String phorex;


    private String start;

    private String end;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMeetname() {
        return meetname;
    }

    public void setMeetname(String meetname) {
        this.meetname = meetname;
    }

    public String getMeetnum() {
        return meetnum;
    }

    public void setMeetnum(String meetnum) {
        this.meetnum = meetnum;
    }

    public Long getStarttime() {
        return starttime;
    }

    public void setStarttime(Long starttime) {
        this.starttime = starttime;
    }

    public Long getEndtime() {
        return endtime;
    }

    public void setEndtime(Long endtime) {
        this.endtime = endtime;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getMeetmeber() {
        return meetmeber;
    }

    public void setMeetmeber(String meetmeber) {
        this.meetmeber = meetmeber;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhorex() {
        return phorex;
    }

    public void setPhorex(String phorex) {
        this.phorex = phorex;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return "CtiIntercomRecord{" +
                "id='" + id + '\'' +
                ", meetname='" + meetname + '\'' +
                ", meetnum='" + meetnum + '\'' +
                ", starttime=" + starttime +
                ", endtime=" + endtime +
                ", uuid='" + uuid + '\'' +
                ", video='" + video + '\'' +
                ", meetmeber='" + meetmeber + '\'' +
                ", url='" + url + '\'' +
                ", type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", phorex='" + phorex + '\'' +
                ", start='" + start + '\'' +
                ", end='" + end + '\'' +
                '}';
    }
}
