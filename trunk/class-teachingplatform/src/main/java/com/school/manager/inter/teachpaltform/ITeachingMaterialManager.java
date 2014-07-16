
package  com.school.manager.inter.teachpaltform;

import com.school.entity.teachpaltform.TeachingMaterialInfo;
import com.school.manager.base.IBaseManager;

import java.util.List;
import java.util.Map;

public interface ITeachingMaterialManager  extends IBaseManager<TeachingMaterialInfo> {

    /**
     * �õ�ͬ����SQL
     * @param entity  ����ʵ��
     * @param sqlbuilder  ������SQL��䣬����ʵ������
     * @return
     */
    public List<Object> getSynchroSql(TeachingMaterialInfo entity,StringBuilder sqlbuilder);

    /**
     * ����residstr�õ��꼶ѧ������
     * @param residstr
     * @return
     */
    public List<Map<String,Object>> getTeachingMaterialGradeSubByResId(String residstr);
} 
