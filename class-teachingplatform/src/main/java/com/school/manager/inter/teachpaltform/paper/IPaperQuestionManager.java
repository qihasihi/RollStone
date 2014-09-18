
package  com.school.manager.inter.teachpaltform.paper;

import com.school.entity.teachpaltform.paper.PaperQuestion;
import com.school.manager.base.IBaseManager;
import com.school.util.PageResult;

import java.util.List;
import java.util.Map;

public interface IPaperQuestionManager  extends IBaseManager<PaperQuestion> { 
    Float getSumScore(PaperQuestion paperQuestion);

    /**
     * ��ѯ�Ծ������������������������Ӽ���
     * @param paperid
     * @return
     */
    Integer paperQuesCount(Long paperid);

    Boolean updateQuesTeamScore(PaperQuestion paperQuestion);
    public List<PaperQuestion> getQuestionByPaper(Long paperid,Integer classid,Integer classtype,Long taskid);

    public List<PaperQuestion> getPaperTeamQuestionList(PaperQuestion p,PageResult pageResult);

    /**
     * �õ�ͬ��SQL���
     * @param paperQuestion
     * @param sqlbuilder
     * @return
     */
    public List<Object> getSynchroSql(PaperQuestion paperQuestion, StringBuilder sqlbuilder);

    public List<Map<String,Object>> getPaperQuesAllId(Long paperid);

    /**
     * �õ��Ծ��µ����з�����ĳ�����
     * @param paperid
     * @param quesid
     * @return
     */
    public List<Map<String,Object>> getPaperQuesAllScore(Long paperid,Long quesid,Long courseid);

    /**
     * �õ�һ���Ծ��������������
     * @param paperid
     * @return
     */
    public List<Map<String,Object>> getZGTCount(final Long paperid);


    /**
     * �õ���ǰ�༶�£���ǰ���⣬�Ծ��µ���ȷ��
     * @param paperid
     * @param quesid
     * @return
     */
    public List<Map<String,Object>> getClsPaperQuesZQLV(Long paperid,Long quesid,Integer classid,Long taskid);

    /**
     * �õ���ǰ�༶�£���ǰ���⣬�Ծ��µ���ȷ��
     * @param paperid
     * @param quesid
     * @return
     */
    public List<Map<String,Object>> getClsPaperQuesOptTJ(Long paperid,Long quesid,Integer classid,Long taskid);

    /**
     * �õ��û��ܷ�
     * @param paperid
     * @param userid
     * @param taskid
     * @return
     */
    public List<Map<String,Object>> getPaperScoreByUser(final long paperid,final Integer userid,final long taskid);
}
