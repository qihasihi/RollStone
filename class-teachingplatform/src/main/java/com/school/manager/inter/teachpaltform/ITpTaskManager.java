
package  com.school.manager.inter.teachpaltform;

import com.school.entity.teachpaltform.TpTaskInfo;
import com.school.manager.base.IBaseManager;
import com.school.util.PageResult;

import java.util.List;

public interface ITpTaskManager  extends IBaseManager<TpTaskInfo> {
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

    public List<TpTaskInfo> getDoTaskResourceId(TpTaskInfo obj);
    /**
     * 删除任务时，清除任务积分，删除完成记录
     * @param taskid
     * @param sqlbuilder
     * @return
     */
    public List<Object> getDelTpStuTaskScore(final Long taskid, StringBuilder sqlbuilder);

} 
