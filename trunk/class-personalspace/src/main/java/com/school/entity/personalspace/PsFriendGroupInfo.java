package com.school.entity.personalspace;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by qihaishi on 14-6-4.
 */
public class PsFriendGroupInfo implements  Serializable {
    private Long groupid;
    private String groupname;
    private Date ctime;
    private Date mtime;
    private Integer cuserid;

    public Long getGroupid() {
        return groupid;
    }

    public void setGroupid(Long groupid) {
        this.groupid = groupid;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    public Date getMtime() {
        return mtime;
    }

    public void setMtime(Date mtime) {
        this.mtime = mtime;
    }

    public Integer getCuserid() {
        return cuserid;
    }

    public void setCuserid(Integer cuserid) {
        this.cuserid = cuserid;
    }
}
