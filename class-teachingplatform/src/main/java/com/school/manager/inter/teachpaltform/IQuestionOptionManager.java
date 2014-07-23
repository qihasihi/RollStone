
package  com.school.manager.inter.teachpaltform;

import com.school.entity.teachpaltform.QuestionOption;
import com.school.entity.teachpaltform.TpCourseQuestion;
import com.school.manager.base.IBaseManager;
import com.school.util.PageResult;

import java.util.List;

public interface IQuestionOptionManager  extends IBaseManager<QuestionOption> {
    /**
     * 得到同步的SQL
     * @param entity  对象实体
     * @param sqlbuilder  传出的SQL语句，必须实例化后
     * @return
     */
    public List<Object> getSynchroSql(QuestionOption entity,StringBuilder sqlbuilder);

    public List<QuestionOption>getPaperQuesOptionList(QuestionOption questionOption,PageResult presult);
    public List<QuestionOption>getCourseQuesOptionList(TpCourseQuestion courseQuestion,PageResult presult);
} 
