package com.ruoyi.recording.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.annotation.Excel;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

/**
 * @Author 马小姐
 * @ClassName CtiMeetRecord
 * @Date 2021-02-04 09:59
 * @Version 1.0
 * @Description:
 */
@TableName("cti_meet_record")
public class CtiMeetRecord implements Serializable {
    private static final long serialVersionUID = 1L;


    private String id;


    private String meetname;


    private String meetnum;


    private Long starttime;


    private Long endtime;


    private String uuid;

    private String video;


    private String meetmeber;


    private String url;


    private String type;


    private String name;


    private String phorex;

    private String startDate;

    private String endDate;


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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "CtiMeetRecord{" +
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
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                '}';
    }
}
