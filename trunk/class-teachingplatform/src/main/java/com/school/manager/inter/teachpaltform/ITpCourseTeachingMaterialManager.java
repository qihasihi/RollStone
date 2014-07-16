
package com.school.manager.inter.teachpaltform;

import com.school.entity.teachpaltform.TpCourseTeachingMaterial;
import com.school.manager.base.IBaseManager;

import java.util.List;

public interface ITpCourseTeachingMaterialManager  extends IBaseManager<TpCourseTeachingMaterial> {
    /**
     * 得到同步的SQL语句
     * @param entity
     * @param sqlbuilder
     * @return
     */
    public List<Object> getSynchroSql(TpCourseTeachingMaterial entity,StringBuilder sqlbuilder);
} 
