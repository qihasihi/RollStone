
package com.school.dao.inter.teachpaltform;

import com.school.dao.base.ICommonDAO;
import com.school.entity.teachpaltform.TpVirtualClassInfo;

import java.util.List;
import java.util.Map;

public interface ITpVirtualClassDAO extends ICommonDAO<TpVirtualClassInfo>{

    public List<Map<String,Object>> getListBytch(String userid,String year) ;
}
