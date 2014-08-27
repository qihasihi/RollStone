
package  com.school.manager.inter.teachpaltform.award;

import com.school.entity.teachpaltform.award.TpStuScoreLogs;
import com.school.manager.base.IBaseManager;

public interface ITpStuScoreLogsManager  extends IBaseManager<TpStuScoreLogs> {

    /**
     *  ½±Àø¼Ó·Ö
     */
    public Boolean awardStuScore(final Long courseid,final Long classid,final Long taskid,final Long userid,final String jid,final Integer type);
} 
