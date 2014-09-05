
package  com.school.manager.inter.teachpaltform.award;

import com.school.entity.teachpaltform.award.TpStuScoreLogs;
import com.school.manager.base.IBaseManager;

public interface ITpStuScoreLogsManager  extends IBaseManager<TpStuScoreLogs> {

    /**
     *  奖励加分
     */
    public Boolean awardStuScore(final Long courseid,final Long classid,final Long taskid,final Long userid,final String jid,final Integer type,final Integer dcshoolid);
    /**
     *奖励加分
     * @param courseid
     * @param classid
     * @param taskid
     * @param userid
     * @param jid
     * @param type
     * @param dcschool
     * @param awardType    奖励加分类型  1:任务得分  2：评论加分
     * @return
     */
    public Boolean awardStuScore(final Long courseid,final Long classid,final Long taskid,final Long userid,final String jid,Integer type,Integer dcschool,Integer awardType);
} 
