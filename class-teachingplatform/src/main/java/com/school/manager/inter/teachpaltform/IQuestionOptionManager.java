
package  com.school.manager.inter.teachpaltform;

import com.school.entity.teachpaltform.QuestionOption;
import com.school.manager.base.IBaseManager;

import java.util.List;

public interface IQuestionOptionManager  extends IBaseManager<QuestionOption> {
    /**
     * �õ�ͬ����SQL
     * @param entity  ����ʵ��
     * @param sqlbuilder  ������SQL��䣬����ʵ������
     * @return
     */
    public List<Object> getSynchroSql(QuestionOption entity,StringBuilder sqlbuilder);
} 
