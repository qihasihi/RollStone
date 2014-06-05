
package  com.school.manager.inter.teachpaltform;

import com.school.entity.teachpaltform.TpCourseQuestion;
import com.school.manager.base.IBaseManager;

import java.util.List;

public interface ITpCourseQuestionManager  extends IBaseManager<TpCourseQuestion> {
    /**
     * 得到同步的SQL
     * @param entity  对象实体
     * @param sqlbuilder  传出的SQL语句，必须实例化后
     * @return
     */
    public List<Object> getSynchroSql(TpCourseQuestion entity,StringBuilder sqlbuilder);
} 
