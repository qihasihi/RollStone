
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
     * 查询任务详情(学校名称，学科)
     * @param t
     * @param presult
     * @return
     */
    List<TpTaskInfo> getTaskDetailList(TpTaskInfo t,PageResult presult);

    /**
     * 获取学生任务
     * @param t
     * @param presult
     * @return
     */
    List<TpTaskInfo>getListbyStu(TpTaskInfo t,PageResult presult);
    /**
     * 获取学生任务，包含调班调组后已完成的任务
     * @param t
     * @param presult
     * @return
     */
    List<TpTaskInfo>getUnionListbyStu(TpTaskInfo t,PageResult presult);
    /**
     * 得到同步的SQL
     * @param entity  对象实体
     * @param sqlbuilder  传出的SQL语句，必须实例化后
     * @return
     */
    public List<Object> getSynchroSql(TpTaskInfo entity,StringBuilder sqlbuilder);

    public Boolean doSaveTaskMsg(TpTaskInfo t);

    /**
     * 查询已发布任务的资源id
     * */
    public List<TpTaskInfo> getDoTaskResourceId(TpTaskInfo obj);

    /**
     * 删除任务时，清除任务积分，删除完成记录
     * @param taskid
     * @param sqlbuilder
     * @return
     */
    public List<Object> getDelTpStuTaskScore(final Long taskid, StringBuilder sqlbuilder);


    /**
     * 获取需要发提醒的任务
     * @return
     */
    public List<TpTaskInfo> getTaskRemindList(TpTaskInfo t,PageResult presult);



    /**
     * 获取班级任务统计
     * @param t
     * @return
     */
    public List<TpTaskInfo> getTaskColumnByClass(TpTaskInfo t);

    /**
     * 获取班级任务统计
     * @param t
     * @return
     */
    public List<List<String>>  getTaskStatByClass(TpTaskInfo t);

    /**
     * 获取小组任务统计
     * @param t
     * @return
     */
    public List<List<String>>  getTaskStatByGroup(TpTaskInfo t);

    /**
     * 获取未分配小组任务统计
     * @param t
     * @return
     */
    public List<List<String>>  getTaskStatByNoGroup(TpTaskInfo t);

}
