package com.ruoyi.recording.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.annotation.Excel;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

@TableName("cti_call_record")
public class CtiCallRecord  implements Serializable {
    private static final long serialVersionUID = 1L;


    private String id;


    private String sessionid;


    private String caller;


    private String callerid;



    private String callee;



    private String calleeid;


    private Long channelcreatetime;


    private Long channelanswertime;

    private String startTime;

    private String endTime;


    private Long channelhanguptime;


    private String type;



    private String duration;

    private String ringing;

    private String recordingaddress;



    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSessionid() {
        return sessionid;
    }

    public void setSessionid(String sessionid) {
        this.sessionid = sessionid;
    }

    public String getCaller() {
        return caller;
    }

    public void setCaller(String caller) {
        this.caller = caller;
    }

    public String getCallerid() {
        return callerid;
    }

    public void setCallerid(String callerid) {
        this.callerid = callerid;
    }

    public String getCallee() {
        return callee;
    }

    public void setCallee(String callee) {
        this.callee = callee;
    }

    public String getCalleeid() {
        return calleeid;
    }

    public void setCalleeid(String calleeid) {
        this.calleeid = calleeid;
    }

    public Long getChannelcreatetime() {
        return channelcreatetime;
    }

    public void setChannelcreatetime(Long channelcreatetime) {
        this.channelcreatetime = channelcreatetime;
    }

    public Long getChannelanswertime() {
        return channelanswertime;
    }

    public void setChannelanswertime(Long channelanswertime) {
        this.channelanswertime = channelanswertime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Long getChannelhanguptime() {
        return channelhanguptime;
    }

    public void setChannelhanguptime(Long channelhanguptime) {
        this.channelhanguptime = channelhanguptime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getRinging() {
        return ringing;
    }

    public void setRinging(String ringing) {
        this.ringing = ringing;
    }

    public String getRecordingaddress() {
        return recordingaddress;
    }

    public void setRecordingaddress(String recordingaddress) {
        this.recordingaddress = recordingaddress;
    }


    @Override
    public String toString() {
        return "CtiCallRecord{" +
                "id='" + id + '\'' +
                ", sessionid='" + sessionid + '\'' +
                ", caller='" + caller + '\'' +
                ", callerid='" + callerid + '\'' +
                ", callee='" + callee + '\'' +
                ", calleeid='" + calleeid + '\'' +
                ", channelcreatetime=" + channelcreatetime +
                ", channelanswertime=" + channelanswertime +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", channelhanguptime=" + channelhanguptime +
                ", type='" + type + '\'' +
                ", duration='" + duration + '\'' +
                ", ringing='" + ringing + '\'' +
                ", recordingaddress='" + recordingaddress + '\'' +
                '}';
    }
}
