
package com.school.dao.inter.teachpaltform;

import com.school.dao.base.ICommonDAO;
import com.school.entity.teachpaltform.TpCourseResource;
import com.school.util.PageResult;

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

    /**
     * 查询专题相关关联专题下的资源，发资源任务用
     * */
    public List<TpCourseResource> getResourceForRelatedCourse(TpCourseResource tpcourseresource, PageResult presult);

    /**
     * 模糊查询当前年纪学科下资源，发资源任务用
     * */
    public List<TpCourseResource> getLikeResource(Integer gradeid,Integer subjectid,String name,PageResult presult);
 }
