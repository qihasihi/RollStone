
package  com.school.manager.inter.teachpaltform.paper;

import com.school.entity.teachpaltform.paper.PaperQuestion;
import com.school.manager.base.IBaseManager;

import java.util.List;

public interface IPaperQuestionManager  extends IBaseManager<PaperQuestion> { 
    Float getSumScore(PaperQuestion paperQuestion);
    public List<PaperQuestion> getQuestionByPaper(Integer paperid);
} 
