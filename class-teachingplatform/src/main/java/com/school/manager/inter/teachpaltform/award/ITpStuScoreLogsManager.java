
package  com.school.manager.inter.teachpaltform.award;

import com.school.entity.teachpaltform.award.TpStuScoreLogs;
import com.school.manager.base.IBaseManager;

public interface ITpStuScoreLogsManager  extends IBaseManager<TpStuScoreLogs> {

    /**
     *  �����ӷ�
     */
    public Boolean awardStuScore(final Long courseid,final Long classid,final Long taskid,final Long userid,final String jid,final Integer type,final Integer dcshoolid);
    /**
     *�����ӷ�
     * @param courseid
     * @param classid
     * @param taskid
     * @param userid
     * @param jid
     * @param type
     * @param dcschool
     * @param awardType    �����ӷ�����  1:����÷�  2�����ۼӷ�
     * @return
     */
    public Boolean awardStuScore(final Long courseid,final Long classid,final Long taskid,final Long userid,final String jid,Integer type,Integer dcschool,Integer awardType);
} 
