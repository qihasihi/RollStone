package com.school.entity;

import com.school.util.UtilTool;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by yuechunyang on 14-2-22.
 */
public class ScoreTypeInfo implements Serializable{
    private Integer ref;//����
    private Integer scoreTypeId;//��������id
    private String scoreTypeName;//������������
    private Integer score;//����
    private Integer maxnum;//��������
    private Date ctime;//����ʱ��

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
