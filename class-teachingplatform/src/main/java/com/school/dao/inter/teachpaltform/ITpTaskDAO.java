
package com.school.dao.inter.teachpaltform;

import com.school.dao.base.ICommonDAO;
import com.school.entity.teachpaltform.TpTaskInfo;
import com.school.util.PageResult;

import java.util.List;

public interface ITpTaskDAO extends ICommonDAO<TpTaskInfo>{
    /**
     * 查询已发布的任务
     * @param t
     * @param presult
     * @return
     */
    List<TpTaskInfo> getTaskReleaseList(TpTaskInfo t,PageResult presult);

    /**
     * 获取学生任务
     * @param t
     * @param presult
     * @return
     */
    List<TpTaskInfo>getListbyStu(TpTaskInfo t,PageResult presult);
    /**
     * 得到同步的SQL
     * @param entity  对象实体
     * @param sqlbuilder  传出的SQL语句，必须实例化后
     * @return
     */
    public List<Object> getSynchroSql(TpTaskInfo entity,StringBuilder sqlbuilder);

    public Boolean doSaveTaskMsg(TpTaskInfo t);
}
