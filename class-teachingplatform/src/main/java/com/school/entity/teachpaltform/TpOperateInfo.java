package  com.school.entity.teachpaltform;

import java.util.Date;

public class TpOperateInfo {


    private Integer operatetype;
    private Long courseid;
    private Long ref;
    private Long targetid;
    private Integer datatype;
    private Integer cuserid;
    private Date ctime;
    private Long referenceid;

    public Long getReferenceid() {
        return referenceid;
    }

    public void setReferenceid(Long referenceid) {
        this.referenceid = referenceid;
    }

    public Integer getOperatetype() {
        return operatetype;
    }

    public void setOperatetype(Integer operatetype) {
        this.operatetype = operatetype;
    }

    public Long getCourseid() {
        return courseid;
    }

    public void setCourseid(Long courseid) {
        this.courseid = courseid;
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

    public Integer getDatatype() {
        return datatype;
    }

    public void setDatatype(Integer datatype) {
        this.datatype = datatype;
    }

    public Integer getCuserid() {
        return cuserid;
    }

    public void setCuserid(Integer cuserid) {
        this.cuserid = cuserid;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    /**
     * data_type枚举
     */
    public static enum OPERATE_TYPE{
        /**
         * 专题
         */
        COURSE{public Integer getValue(){return 1;}},
        /**
         * 专题任务
         */
        COURSE_TASK{public Integer getValue(){return 2;}},
        /**
         * 专题资源
         */
        COURSE_RESOURCE{public Integer getValue(){return 3;}},
        /**
         * 专题论题
         */
        COURSE_TOPIC{public Integer getValue(){return 4;}},
        /**
         * 专题主题
         */
        COURSE_TOPIC_THEME{public Integer getValue(){return 5;}},
        /**
         * 专题试题
         */
        COURSE_QUESTION{public Integer getValue(){return 6;}};

        public abstract Integer getValue();
    }
}
