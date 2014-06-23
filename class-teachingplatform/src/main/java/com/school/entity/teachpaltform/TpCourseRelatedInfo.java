package com.school.entity.teachpaltform;

import java.io.Serializable;

/**
 * Created by yuechunyang on 14-6-19.
 */
public class TpCourseRelatedInfo implements Serializable {
    private Integer ref;
    private Long courseid;
    private Long relatedcourseid;

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
