
package  com.school.manager.inter.teachpaltform;

import com.school.entity.teachpaltform.TpTaskAllotInfo;
import com.school.manager.base.IBaseManager;

import java.util.List;
import java.util.Map;

public interface ITpTaskAllotManager  extends IBaseManager<TpTaskAllotInfo> {
    public List<TpTaskAllotInfo> getTaskByGroup(Long groupid);
    public List<Map<String,Object>> getCompleteNum(Long groupid,Long taskid);
    public List<Map<String,Object>> getNum(Long groupid,Long taskid);

    /**
     * 得到班级ID,通过 Courseid,taskid,userid
     * @param tallot
     * @return
     */
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
}
