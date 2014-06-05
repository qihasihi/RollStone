
package com.school.dao.inter;

import com.school.dao.base.ICommonDAO;
import com.school.entity.GradeInfo;

import java.util.List;

public interface  IGradeDAO extends ICommonDAO<GradeInfo>{
    public List<GradeInfo> getTchGradeList(Integer userid,String year);

}
