
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
     * �õ��༶ID,ͨ�� Courseid,taskid,userid
     * @param tallot
     * @return
     */
    public List<Map<String,Object>> getClassId(final TpTaskAllotInfo tallot);
    /**
     * �õ���Ч����������
     * @param entity
     * @return
     */
    public boolean getYXTkCount(TpTaskAllotInfo entity);
    /**
     * ����������ˣ���༶
     * @param taskid
     * @param userid
     * @return
     */
    public List<Map<String,Object>> getTaskAllotBLClassId(final Long taskid,final Integer userid);
}
