
package  com.school.manager.inter.teachpaltform;

import com.school.entity.teachpaltform.TpGroupInfo;
import com.school.manager.base.IBaseManager;

import java.util.List;

public interface ITpGroupManager  extends IBaseManager<TpGroupInfo> {

    public boolean checkGroupName(TpGroupInfo t);

    public List<TpGroupInfo> getMyGroupList(Integer classid,Integer classtype, String termid, Integer tchid, Integer stuid,Integer subjectid);

    public List<TpGroupInfo> getGroupBySubject(TpGroupInfo obj);

} 
