package com.school.entity;

import com.school.util.UtilTool;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by yuechunyang on 14-2-22.
 */
public class UserModelTotalScoreInfo implements Serializable{
    private Integer ref;//主键
    private Integer modelId;//模块id
    private Integer userId;//用户id
    private Integer totalScore;//总分
    private Date ctime;//创建时间
    private Date mtime;//修改时间
    private String realname; //真实姓名
    private String headimage; //图像

    public String getHeadimage() {
        return headimage;
    }

    public void setHeadimage(String headimage) {
        this.headimage = headimage;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public Integer getRef() {
        return ref;
    }

    public void setRef(Integer ref) {
        this.ref = ref;
    }

    public Integer getModelId() {
        return modelId;
    }

    public void setModelId(Integer modelId) {
        this.modelId = modelId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Integer totalScore) {
        this.totalScore = totalScore;
    }

    public Date getCtime() {
        return ctime;
    }

    public String getCtimeString(){
        if(this.ctime==null)
            return null;
        return UtilTool.DateConvertToString(this.ctime, UtilTool.DateType.type1);
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    public Date getMtime() {
        return mtime;
    }

    public String getMtimeString(){
        if(this.mtime==null)
            return null;
        return UtilTool.DateConvertToString(this.mtime, UtilTool.DateType.type1);
    }

    public void setMtime(Date mtime) {
        this.mtime = mtime;
    }
}
