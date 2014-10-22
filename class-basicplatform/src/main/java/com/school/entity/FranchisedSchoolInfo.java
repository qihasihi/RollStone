package com.school.entity;

import java.io.Serializable;

public class FranchisedSchoolInfo implements Serializable{
	private Integer schoolId;//ѧУid
    private Integer totalClass;
    private Integer classYearId;

    public Integer getTotalClass() {
        return totalClass;
    }

    public void setTotalClass(Integer totalClass) {
        this.totalClass = totalClass;
    }

    public Integer getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    public Integer getClassYearId() {
        return classYearId;
    }

    public void setClassYearId(Integer classYearId) {
        this.classYearId = classYearId;
    }
}
