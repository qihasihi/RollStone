
package  com.school.manager.inter.teachpaltform;

import com.school.entity.teachpaltform.TpCourseQuestion;
import com.school.manager.base.IBaseManager;

import java.util.List;

public interface ITpCourseQuestionManager  extends IBaseManager<TpCourseQuestion> {
    /**
     * �õ�ͬ����SQL
     * @param entity  ����ʵ��
     * @param sqlbuilder  ������SQL��䣬����ʵ������
     * @return
     */
    public List<Object> getSynchroSql(TpCourseQuestion entity,StringBuilder sqlbuilder);
} 
