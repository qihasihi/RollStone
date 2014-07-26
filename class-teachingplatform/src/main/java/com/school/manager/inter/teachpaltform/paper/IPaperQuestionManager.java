
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
    public List<PaperQuestion> getQuestionByPaper(Long paperid);

    public List<PaperQuestion> getPaperTeamQuestionList(PaperQuestion p,PageResult pageResult);

    /**
     * 得到同步SQL语句
     * @param paperQuestion
     * @param sqlbuilder
     * @return
     */
    public List<Object> getSynchroSql(PaperQuestion paperQuestion, StringBuilder sqlbuilder);

    public List<Map<String,Object>> getPaperQuesAllId(Long paperid);
}
