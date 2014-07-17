
package  com.school.manager.inter.teachpaltform;

import com.school.entity.teachpaltform.TpCourseResource;
import com.school.manager.base.IBaseManager;
import com.school.util.PageResult;

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
    /**
     * ��ѯר����ع���ר���µ���Դ������Դ������
     * */
    public List<TpCourseResource> getResourceForRelatedCourse(TpCourseResource tpcourseresource, PageResult presult);
    /**
     * ģ����ѯ��ǰ���ѧ������Դ������Դ������
     * */
    public List<TpCourseResource> getLikeResource(Integer gradeid,Integer subjectid,String name,PageResult presult);

} 
