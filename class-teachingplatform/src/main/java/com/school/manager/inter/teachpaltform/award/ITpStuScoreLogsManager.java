
package  com.school.manager.inter.teachpaltform.award;

import com.school.entity.teachpaltform.award.TpStuScoreLogs;
import com.school.manager.base.IBaseManager;

import java.util.List;
import java.util.Map;

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

    /**
     * 老师，班主任查看学生积分统计页面
     * @param termid
     * @param classid
     * @param subjectid
     * @param orderby
     * @return
     */
    public List<Map<String,Object>> getStuScoreTeachStatices(final String termid,final Integer classid,Integer subjectid,Integer orderby);
} 
