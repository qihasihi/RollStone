package com.school.entity.teachpaltform;

import com.school.util.UtilTool;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by qihaishi on 14-12-25.
 */
public class TaskRemindInfo implements Serializable {
    private Date ctime;
    private Integer ref;
    private Integer seltype;

    public Integer getSeltype() {
        return seltype;
    }

    public void setSeltype(Integer seltype) {
        this.seltype = seltype;
    }

    public String getCtimeString(){
        if(ctime==null)
            return null;
        return UtilTool.DateConvertToString(ctime, UtilTool.DateType.type1);
    }

    public Integer getRef() {
        return ref;
    }

    public void setRef(Integer ref) {
        this.ref = ref;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    public static enum QUERY_TYPE{
        Hour{public  Integer getValue(){return 1;}};

        public abstract Integer getValue();
    }
}
