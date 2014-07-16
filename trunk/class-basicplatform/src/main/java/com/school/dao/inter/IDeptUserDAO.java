
package com.school.dao.inter;

import com.school.dao.base.ICommonDAO;
import com.school.entity.DeptUser;

import java.util.List;

public interface  IDeptUserDAO extends ICommonDAO<DeptUser>{
    /**
     * 正职务更新使用
     * @param obj
     * @param sqlbuilder
     * @return
     */
    public List<Object> getUpdateSqlLoyal(DeptUser obj, StringBuilder sqlbuilder);
}
