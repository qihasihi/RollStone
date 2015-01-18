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
    private String country;
    private String province;
    private String city;
    private Integer isactive;

    public Integer getIsactive() {
        return isactive;
    }

    public void setIsactive(Integer isactive) {
        this.isactive = isactive;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    private Integer enable;

    public Integer getEnable() {
        return enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }

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
