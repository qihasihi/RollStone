
package  com.school.manager.inter.teachpaltform;

import com.school.entity.teachpaltform.QuestionInfo;
import com.school.manager.base.IBaseManager;

import java.util.List;

public interface IQuestionManager  extends IBaseManager<QuestionInfo> {
    /**
     * �õ�ͬ����SQL
     * @param entity  ����ʵ��
     * @param sqlbuilder  ������SQL��䣬����ʵ������
     * @return
     */
    public List<Object> getSynchroSql(QuestionInfo entity,StringBuilder sqlbuilder);
} 
