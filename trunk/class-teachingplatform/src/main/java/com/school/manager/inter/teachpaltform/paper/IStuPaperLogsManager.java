
package  com.school.manager.inter.teachpaltform.paper;

import com.school.entity.teachpaltform.paper.StuPaperLogs;
import com.school.manager.base.IBaseManager;

import java.util.List;
import java.util.Map;

public interface IStuPaperLogsManager  extends IBaseManager<StuPaperLogs> {
    public List<StuPaperLogs> getMarkingLogs(Long paperid,Long quesid);
    public List<Map<String,Object>> getMarkingDetail(Long paperid,Long quesid);
    public List<Map<String,Object>> getMarkingNum(Long paperid,Long quesid);
    public List<Object>  getUpdateScoreSql(StuPaperLogs stupaperlogs, StringBuilder sqlbuilder);
    public Boolean doUpdateScore(StuPaperLogs stupaperlogs);

    /**
     * ����ѧ��ID,�Ծ�ID,�õ�ѧ���÷�
     * @param userid
     * @param paperid
     * @return
     */
    public List<Map<String,Object>> getStuPaperSumScore(Integer userid,Long paperid);
} 
