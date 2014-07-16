
package  com.school.manager.inter.teachpaltform;

import com.school.entity.teachpaltform.TpVirtualClassInfo;
import com.school.manager.base.IBaseManager;

import java.util.List;
import java.util.Map;

public interface ITpVirtualClassManager  extends IBaseManager<TpVirtualClassInfo> {

    public List<Map<String,Object>> getListBytch(String userid,String year) ;
} 
