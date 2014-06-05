
package  com.school.manager.inter;

import com.school.entity.DeptUser;
import com.school.manager.base.IBaseManager;

import java.util.List;

public interface IDeptUserManager  extends IBaseManager<DeptUser> {
    /**
     * 正职务更新使用
     * @param obj
     * @param sqlbuilder
     * @return
     */
    public List<Object> getUpdateSqlLoyal(DeptUser obj, StringBuilder sqlbuilder);
} 
