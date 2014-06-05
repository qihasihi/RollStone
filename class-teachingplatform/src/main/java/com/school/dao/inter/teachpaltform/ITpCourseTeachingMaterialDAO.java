
package com.school.dao.inter.teachpaltform;

import com.school.dao.base.ICommonDAO;
import com.school.entity.teachpaltform.TpCourseTeachingMaterial;

import java.util.List;

public interface ITpCourseTeachingMaterialDAO extends ICommonDAO<TpCourseTeachingMaterial>{
    /**
     * 得到同步的SQL语句
     * @param entity
     * @param sqlbuilder
     * @return
     */
    public List<Object> getSynchroSql(TpCourseTeachingMaterial entity,StringBuilder sqlbuilder);
}
