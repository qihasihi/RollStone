
package com.school.dao.inter;

import com.school.dao.base.ICommonDAO;
import com.school.entity.DeptUser;

import java.util.List;

public interface  IDeptUserDAO extends ICommonDAO<DeptUser>{
    /**
     * ��ְ�����ʹ��
     * @param obj
     * @param sqlbuilder
     * @return
     */
    public List<Object> getUpdateSqlLoyal(DeptUser obj, StringBuilder sqlbuilder);
}
