
package  com.school.manager.inter.teachpaltform;

import com.school.entity.teachpaltform.TeachVersionInfo;
import com.school.manager.base.IBaseManager;

import java.util.List;

public interface ITeachVersionManager  extends IBaseManager<TeachVersionInfo> { 

    public List<Object> getSynchroSql(TeachVersionInfo entity,StringBuilder sqlbuilder);
} 
