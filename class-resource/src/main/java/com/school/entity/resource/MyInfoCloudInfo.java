package com.school.entity.resource;

import com.school.util.UtilTool;

import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by zhengzhou on 14-6-4.
 */
@Entity
public class MyInfoCloudInfo implements  Serializable{
    private Long ref;
    private Long targetid;
    private Integer type;
    private String data;
    private Long userid;
    private Date ctime;
    //查询字段
    private String targetName;

    public String getTargetName() {
        return targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    public Long getRef() {
        return ref;
    }

    public void setRef(Long ref) {
        this.ref = ref;
    }

    public Long getTargetid() {
        return targetid;
    }

    public void setTargetid(Long targetid) {
        this.targetid = targetid;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    public String getCtimeString(){
        if(ctime!=null){
            return UtilTool.DateConvertToString(this.ctime, UtilTool.DateType.type1);
        }
        return "";
    }

    /**
     * 显示的值
     * @return
     */
    public String getDataMsg(){
        String returnVal=this.data;
        String splitChar="#ETIANTIAN_SPLIT#";
        if(this.getType()!=null&&this.data!=null){
            if(this.data.indexOf(splitChar)!=-1){
                returnVal=this.data.replace("#ETIANTIAN_SPLIT#",targetName+"");
            }
        }
        return returnVal;
    }
}
