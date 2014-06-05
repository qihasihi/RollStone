
package  com.school.manager.inter.teachpaltform;

import com.school.entity.teachpaltform.TpCourseResource;
import com.school.manager.base.IBaseManager;

import java.util.List;

public interface ITpCourseResourceManager  extends IBaseManager<TpCourseResource> {
    /**
     * �õ�ͬ����SQL
     * @param entity  ����ʵ��
     * @param sqlbuilder  ������SQL��䣬����ʵ������
     * @return
     */
    public List<Object> getSynchroSql(TpCourseResource entity,StringBuilder sqlbuilder);

    public Boolean doAddDynamic(TpCourseResource courseres);

} 
