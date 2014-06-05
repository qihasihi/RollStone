
package  com.school.manager.inter.teachpaltform;

import com.school.entity.teachpaltform.TeachingMaterialInfo;
import com.school.manager.base.IBaseManager;

import java.util.List;
import java.util.Map;

public interface ITeachingMaterialManager  extends IBaseManager<TeachingMaterialInfo> {

    /**
     * 得到同步的SQL
     * @param entity  对象实体
     * @param sqlbuilder  传出的SQL语句，必须实例化后
     * @return
     */
    public List<Object> getSynchroSql(TeachingMaterialInfo entity,StringBuilder sqlbuilder);

    /**
     * 根据residstr得到年级学科属性
     * @param residstr
     * @return
     */
    public List<Map<String,Object>> getTeachingMaterialGradeSubByResId(String residstr);
} 
