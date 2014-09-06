
package  com.school.manager.inter.teachpaltform.award;

import com.school.entity.teachpaltform.award.TpStuScoreLogs;
import com.school.manager.base.IBaseManager;

import java.util.List;
import java.util.Map;

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

    /**
     * ��ʦ�������β鿴ѧ������ͳ��ҳ��
     * @param termid
     * @param classid
     * @param subjectid
     * @param orderby
     * @return
     */
    public List<Map<String,Object>> getStuScoreTeachStatices(final String termid,final Integer classid,Integer subjectid,Integer orderby);
} 
