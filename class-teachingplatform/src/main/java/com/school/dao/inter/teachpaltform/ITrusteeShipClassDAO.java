
package com.school.dao.inter.teachpaltform;

import com.school.dao.base.ICommonDAO;
import com.school.entity.teachpaltform.TrusteeShipClass;

import java.util.List;
import java.util.Map;

public interface ITrusteeShipClassDAO extends ICommonDAO<TrusteeShipClass>{
    public List<Map<String,Object>> getTrusteeShipTchs(String subject,String grade,String year ,String tchname);
    public List<Map<String,Object>> getTsClassList(TrusteeShipClass trusteeshipclass);
}
