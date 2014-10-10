package com.school.entity;

import com.school.util.UtilTool;

import java.util.Date;

/**
 * 分校信息表
 * Created by zhengzhou on 14-4-7.
 */
public class SchoolInfo {
    /**
     * school_id long
     * name  varchar(100)
     * c_time date
     */
    private Long schoolid;
    private String name;
    private Date ctime;
    private String ip;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Long getSchoolid() {
        return schoolid;
    }

    public void setSchoolid(Long schoolid) {
        this.schoolid = schoolid;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }
    public String getCtimeString(){
        return (this.ctime==null?"": UtilTool.DateConvertToString(this.ctime, UtilTool.DateType.type1));
    }
    public String getNameString(){
        return this.getName();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
