package com.school.entity.teachpaltform;

import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Date;

/**
 * 课堂纪实实体类
 * Created by zhengzhou on 14-8-19.
 */
@Entity
public class TpRecordInfo  implements  Serializable{
    private Integer ref;
    private Long courseId;
    private Long userId;
    private String content;
    private String imgUrl;
    private Long classId;
    private Date ctime;
    private Long dcSchoolId;

    public Integer getRef() {
        return ref;
    }

    public void setRef(Integer ref) {
        this.ref = ref;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    public Long getDcSchoolId() {
        return dcSchoolId;
    }
    public void setDcSchoolId(Long schoolid) {
        this.dcSchoolId=schoolid;
    }
}
