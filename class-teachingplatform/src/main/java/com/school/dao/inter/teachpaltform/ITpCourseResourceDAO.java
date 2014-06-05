
package com.school.dao.inter.teachpaltform;

import com.school.dao.base.ICommonDAO;
import com.school.entity.teachpaltform.TpCourseResource;

import java.util.List;

public interface ITpCourseResourceDAO extends ICommonDAO<TpCourseResource>{
    /**
     * 得到同步的SQL
     * @param entity  对象实体
     * @param sqlbuilder  传出的SQL语句，必须实例化后
     * @return
     */
    public List<Object> getSynchroSql(TpCourseResource entity,StringBuilder sqlbuilder);
    public Boolean doAddDynamic(TpCourseResource courseres);
}
