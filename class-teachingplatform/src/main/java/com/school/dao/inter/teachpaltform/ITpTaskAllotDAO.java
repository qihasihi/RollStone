
package com.school.dao.inter.teachpaltform;

import com.school.dao.base.ICommonDAO;
import com.school.entity.teachpaltform.TpTaskAllotInfo;
import com.school.util.PageResult;

import java.util.List;
import java.util.Map;

public interface ITpTaskAllotDAO extends ICommonDAO<TpTaskAllotInfo>{
    public List<TpTaskAllotInfo> getTaskByGroup(Long groupid);
    public List<Map<String,Object>> getCompleteNum(Long groupid,Long taskid);
    public List<Map<String,Object>> getNum(Long groupid,Long taskid);

    public List<Map<String,Object>> getClassId(final TpTaskAllotInfo tallot);

    /**
     * 得到有效的任务数量
     * @param entity
     * @return
     */
    public boolean getYXTkCount(TpTaskAllotInfo entity);
    /**
     * 根据任务和人，查班级
     * @param taskid
     * @param userid
     * @return
     */
    public List<Map<String,Object>> getTaskAllotBLClassId(final Long taskid,final Integer userid);

    /**
     * 获取任务提醒对象
     * @param tallot
     * @param pageResult
     * @return
     */
    public List<TpTaskAllotInfo>getTaskRemindObjList(TpTaskAllotInfo tallot,PageResult pageResult);
}
