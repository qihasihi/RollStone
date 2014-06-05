package com.school.entity;

import com.school.util.UtilTool;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by yuechunyang on 14-2-22.
 */
public class ScoreTypeInfo implements Serializable{
    private Integer ref;//主键
    private Integer scoreTypeId;//分数类型id
    private String scoreTypeName;//分数类型名称
    private Integer score;//分数
    private Integer maxnum;//操作次数
    private Date ctime;//创建时间

    public Integer getRef() {
        return ref;
    }

    public void setRef(Integer ref) {
        this.ref = ref;
    }

    public Integer getScoreTypeId() {
        return scoreTypeId;
    }

    public void setScoreTypeId(Integer scoreTypeId) {
        this.scoreTypeId = scoreTypeId;
    }

    public String getScoreTypeName() {
        return scoreTypeName;
    }

    public void setScoreTypeName(String scoreTypeName) {
        this.scoreTypeName = scoreTypeName;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getMaxnum() {
        return maxnum;
    }

    public void setMaxnum(Integer maxnum) {
        this.maxnum = maxnum;
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
}
