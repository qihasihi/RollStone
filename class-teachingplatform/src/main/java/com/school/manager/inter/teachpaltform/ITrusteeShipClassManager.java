
package  com.school.manager.inter.teachpaltform;

import com.school.entity.teachpaltform.TrusteeShipClass;
import com.school.manager.base.IBaseManager;

import java.util.List;
import java.util.Map;

public interface ITrusteeShipClassManager  extends IBaseManager<TrusteeShipClass> {

    public List<Map<String,Object>> getTrusteeShipTchs(String subject,String grade,String year ,String tchname);

    public List<Map<String,Object>> getTsClassList(TrusteeShipClass trusteeshipclass);
} 
