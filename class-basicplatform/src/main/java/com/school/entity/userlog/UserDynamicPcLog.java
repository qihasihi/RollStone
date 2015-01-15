package com.school.entity.userlog;

import java.util.Date;

/**
 * Created by qihaishi on 15-1-14.
 */
public class UserDynamicPcLog {

    private Integer ref;
    private Integer userid;
    private Integer ettuserid;
    private Integer usertype;
    private Integer dynamicid;
    private Integer source;
    private Date ctime;

    public Integer getRef() {
        return ref;
    }

    public void setRef(Integer ref) {
        this.ref = ref;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getEttuserid() {
        return ettuserid;
    }

    public void setEttuserid(Integer ettuserid) {
        this.ettuserid = ettuserid;
    }

    public Integer getUsertype() {
        return usertype;
    }

    public void setUsertype(Integer usertype) {
        this.usertype = usertype;
    }

    public Integer getDynamicid() {
        return dynamicid;
    }

    public void setDynamicid(Integer dynamicid) {
        this.dynamicid = dynamicid;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }
}
