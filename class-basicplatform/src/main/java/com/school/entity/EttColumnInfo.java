package com.school.entity;

import javax.persistence.Entity;
import java.io.Serializable;

/**
 * Created by zhengzhou on 14-7-2.
 */
@Entity
public class EttColumnInfo implements  Serializable{
    private Integer ettcolumnid;
    private String ettcolumnname;
    private String ettcolumnurl;
    private Integer status;
    private Integer ref;

    public Integer getEttcolumnid() {
        return ettcolumnid;
    }

    public void setEttcolumnid(Integer ettcolumnid) {
        this.ettcolumnid = ettcolumnid;
    }

    public String getEttcolumnname() {
        return ettcolumnname;
    }

    public void setEttcolumnname(String ettcolumnname) {
        this.ettcolumnname = ettcolumnname;
    }

    public String getEttcolumnurl() {
        return ettcolumnurl;
    }

    public void setEttcolumnurl(String ettcolumnurl) {
        this.ettcolumnurl = ettcolumnurl;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getRef() {
        return ref;
    }

    public void setRef(Integer ref) {
        this.ref = ref;
    }
}
