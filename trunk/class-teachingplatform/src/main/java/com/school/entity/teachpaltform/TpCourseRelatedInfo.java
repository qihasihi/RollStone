package com.school.entity.teachpaltform;

import java.io.Serializable;

/**
 * Created by yuechunyang on 14-6-19.
 */
public class TpCourseRelatedInfo implements Serializable {
    private Integer ref;
    private Long courseid;
    private Long relatedcourseid;
    private String coursename;

    public String getCoursename() {
        return coursename;
    }

    public void setCoursename(String coursename) {
        this.coursename = coursename;
    }

    public Integer getRef() {
        return ref;
    }

    public void setRef(Integer ref) {
        this.ref = ref;
    }

    public Long getCourseid() {
        return courseid;
    }

    public void setCourseid(Long courseid) {
        this.courseid = courseid;
    }

    public Long getRelatedcourseid() {
        return relatedcourseid;
    }

    public void setRelatedcourseid(Long relatedcourseid) {
        this.relatedcourseid = relatedcourseid;
    }
}
