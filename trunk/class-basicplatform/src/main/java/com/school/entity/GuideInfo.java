package com.school.entity;

import javax.persistence.Entity;
import java.util.Date;

/**
 * 向导表
 * Created by zhengzhou on 14-5-5.
 */
@Entity
public class GuideInfo  implements java.io.Serializable {
    private Long ref;
    private String optable;
    private UserInfo opUserInfo;
    private Integer optype;  //1:代表是初始进入

    private Date ctime;

    public UserInfo getOpUserInfo() {
        return (opUserInfo==null?opUserInfo=new UserInfo():opUserInfo);
    }

    public void setOpUserInfo(UserInfo opUser) {
        this.opUserInfo = opUser;
    }

    public Long getRef() {
        return ref;
    }

    public void setRef(Long ref) {
        this.ref = ref;
    }

    public String getOptable() {
        return optable;
    }

    public void setOptable(String optable) {
        this.optable = optable;
    }

    public Integer getOpuser() {
        return getOpUserInfo().getUserid();
    }

    public void setOpuser(Integer opuser) {
        this.getOpUserInfo().setUserid(opuser);
    }

    public Integer getOptype() {
        return optype;
    }

    public void setOptype(Integer optype) {
        this.optype = optype;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }
}
