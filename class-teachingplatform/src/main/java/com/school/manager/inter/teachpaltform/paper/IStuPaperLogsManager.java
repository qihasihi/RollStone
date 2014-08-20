
package  com.school.manager.inter.teachpaltform.paper;

import com.school.entity.teachpaltform.paper.StuPaperLogs;
import com.school.manager.base.IBaseManager;

import java.util.List;
import java.util.Map;

public interface IStuPaperLogsManager  extends IBaseManager<StuPaperLogs> {
    public List<StuPaperLogs> getMarkingLogs(final Long paperid,final Long quesid,final Integer classid,final Long taskid);
    public List<Map<String,Object>> getMarkingDetail(final Long paperid,final Long questionid,final Long quesid,final Integer ismark,final Integer classid,final Integer classtype,final Long taskid);
    public List<Map<String,Object>> getMarkingNum(final Long paperid,final Long quesid,final Integer classid,final Integer classtype);
    public List<Map<String, Object>> getPaperPercentNum(Long paperid, int bignum, int smallnum,Integer classid);
    public List<Map<String, Object>> getPaperPercentNum2(final Long paperid,final  int bignum, final int smallnum);
    public List<Object>  getUpdateScoreSql(final StuPaperLogs stupaperlogs, StringBuilder sqlbuilder);
    public Boolean doUpdateScore(final StuPaperLogs stupaperlogs);

    /**
     * 根据学生ID,试卷ID,得到学生得分
     * @param userid
     * @param paperid
     * @return
     */
    public List<Map<String,Object>> getStuPaperSumScore(final Integer userid,final Long paperid);
} 
