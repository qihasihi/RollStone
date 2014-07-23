
package  com.school.manager.inter.teachpaltform;

import com.school.entity.teachpaltform.TpCourseQuestion;
import com.school.manager.base.IBaseManager;
import com.school.util.PageResult;

import java.util.List;

public interface ITpCourseQuestionManager  extends IBaseManager<TpCourseQuestion> {
    /**
     * 得到同步的SQL
     * @param entity  对象实体
     * @param sqlbuilder  传出的SQL语句，必须实例化后
     * @return
     */
    public List<Object> getSynchroSql(TpCourseQuestion entity,StringBuilder sqlbuilder);

    List<TpCourseQuestion>getQuestionTeamList(TpCourseQuestion tpCourseQuestion,PageResult pageResult);

    /**
     * 获取专题下客观题数量
     * @param tpCourseQuestion
     * @return
     */
    Integer getObjectiveQuesCount(TpCourseQuestion tpCourseQuestion);
} 
