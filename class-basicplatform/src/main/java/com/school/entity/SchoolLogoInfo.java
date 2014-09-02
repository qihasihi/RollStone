package com.school.entity;

import java.io.Serializable;

/**
 * Created by yuechunyang on 14-9-2.
 */
public class SchoolLogoInfo implements Serializable {
    private Integer ref;
    private Integer schoolid;
    private String logosrc;

    public Integer getRef() {
        return ref;
    }

    public void setRef(Integer ref) {
        this.ref = ref;
    }

    public Integer getSchoolid() {
        return schoolid;
    }

    public void setSchoolid(Integer schoolid) {
        this.schoolid = schoolid;
    }

    public String getLogosrc() {
        return logosrc;
    }

    public void setLogosrc(String logosrc) {
        this.logosrc = logosrc;
    }
}
