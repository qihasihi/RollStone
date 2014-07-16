package com.school.entity;

import com.school.util.UtilTool;

import javax.persistence.Entity;
import java.util.Date;

/**
 * Created by yuechunyang on 14-2-22.
 */
@Entity
public class UserModelScoreLogsInfo  implements java.io.Serializable {
    private Integer ref;//����
    private Integer scoreTypeId;//��������id
    private Integer modelId;//ģ��id
    private Integer userId;//�û�id
    private Integer score;//����
    private Long referenceId;//�ο�id����Դ����ר��id��
    private Date ctime;//����ʱ��
    private String selmonth;
    private String resname;

    public String getResname() {
        return resname;
    }

    public void setResname(String resname) {
        this.resname = resname;
    }

    public String getSelmonth() {
        return selmonth;
    }

    public void setSelmonth(String selmonth) {
        this.selmonth = selmonth;
    }

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

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Long getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(Long referenceId) {
        this.referenceId = referenceId;
    }

    public Date getCtime() {
        return ctime;
    }

    public String getCtimeString(){
        if(ctime==null)
            return null;
        return UtilTool.DateConvertToString(ctime, UtilTool.DateType.type1);
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }


}
