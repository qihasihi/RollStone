
package com.school.dao.inter.teachpaltform;

import com.school.dao.base.ICommonDAO;
import com.school.entity.teachpaltform.TpGroupInfo;

import java.util.List;

public interface ITpGroupDAO extends ICommonDAO<TpGroupInfo>{

    public boolean checkGroupName(TpGroupInfo t);

    public List<TpGroupInfo> getMyGroupList(Integer classid,Integer classtype, String termid, Integer tchid, Integer stuid,Integer subjectid);

}
