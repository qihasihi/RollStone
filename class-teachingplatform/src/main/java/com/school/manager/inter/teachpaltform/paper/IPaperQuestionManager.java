
package  com.school.manager.inter.teachpaltform.paper;

import com.school.entity.teachpaltform.paper.PaperQuestion;
import com.school.manager.base.IBaseManager;
import com.school.util.PageResult;

import java.util.List;
import java.util.Map;

public interface IPaperQuestionManager  extends IBaseManager<PaperQuestion> { 
    Float getSumScore(PaperQuestion paperQuestion);

    /**
     * 查询试卷下试题数量（包含组试题子集）
     * @param paperid
     * @return
     */
    Integer paperQuesCount(Long paperid);

    Boolean updateQuesTeamScore(PaperQuestion paperQuestion);
    public List<PaperQuestion> getQuestionByPaper(Long paperid,Integer classid,Integer classtype,Long taskid);

    public List<PaperQuestion> getPaperTeamQuestionList(PaperQuestion p,PageResult pageResult);

    /**
     * 得到同步SQL语句
     * @param paperQuestion
     * @param sqlbuilder
     * @return
     */
    public List<Object> getSynchroSql(PaperQuestion paperQuestion, StringBuilder sqlbuilder);

    public List<Map<String,Object>> getPaperQuesAllId(Long paperid);

    /**
     * 得到试卷下的所有分数或某题分数
     * @param paperid
     * @param quesid
     * @return
     */
    public List<Map<String,Object>> getPaperQuesAllScore(Long paperid,Long quesid,Long courseid);

    /**
     * 得到一个试卷下主观题的数量
     * @param paperid
     * @return
     */
    public List<Map<String,Object>> getZGTCount(final Long paperid);


    /**
     * 得到当前班级下，当前试题，试卷下的正确率
     * @param paperid
     * @param quesid
     * @return
     */
    public List<Map<String,Object>> getClsPaperQuesZQLV(Long paperid,Long quesid,Integer classid,Long taskid);

    /**
     * 得到当前班级下，当前试题，试卷下的正确率
     * @param paperid
     * @param quesid
     * @return
     */
    public List<Map<String,Object>> getClsPaperQuesOptTJ(Long paperid,Long quesid,Integer classid,Long taskid);

    /**
     * 得到用户总分
     * @param paperid
     * @param userid
     * @param taskid
     * @return
     */
    public List<Map<String,Object>> getPaperScoreByUser(final long paperid,final Integer userid,final long taskid);
}
