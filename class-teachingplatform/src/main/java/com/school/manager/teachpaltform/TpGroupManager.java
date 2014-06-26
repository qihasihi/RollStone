
package  com.school.manager.teachpaltform;

import java.util.List;
import jxl.Sheet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.school.dao.inter.teachpaltform.ITpGroupDAO;

import com.school.entity.teachpaltform.TpGroupInfo;
import com.school.dao.base.ICommonDAO;
import com.school.manager.base.BaseManager;
import com.school.manager.inter.teachpaltform.ITpGroupManager;
import com.school.util.PageResult;

@Service
public class  TpGroupManager extends BaseManager<TpGroupInfo> implements ITpGroupManager  { 
	
	private ITpGroupDAO tpgroupdao;
	
	@Autowired
	@Qualifier("tpGroupDAO")
	public void setTpgroupdao(ITpGroupDAO tpgroupdao) {
		this.tpgroupdao = tpgroupdao;
	}
	
	public Boolean doSave(TpGroupInfo tpgroupinfo) {
		return this.tpgroupdao.doSave(tpgroupinfo);
	}
	
	public Boolean doDelete(TpGroupInfo tpgroupinfo) {
		return this.tpgroupdao.doDelete(tpgroupinfo);
	}

	public Boolean doUpdate(TpGroupInfo tpgroupinfo) {
		return this.tpgroupdao.doUpdate(tpgroupinfo);
	}
	
	public List<TpGroupInfo> getList(TpGroupInfo tpgroupinfo, PageResult presult) {
		return this.tpgroupdao.getList(tpgroupinfo,presult);	
	}
	
	public List<Object> getSaveSql(TpGroupInfo tpgroupinfo, StringBuilder sqlbuilder) {
		return this.tpgroupdao.getSaveSql(tpgroupinfo,sqlbuilder);
	}

	public List<Object> getDeleteSql(TpGroupInfo tpgroupinfo, StringBuilder sqlbuilder) {
		return this.tpgroupdao.getDeleteSql(tpgroupinfo,sqlbuilder);
	}

	public List<Object> getUpdateSql(TpGroupInfo tpgroupinfo, StringBuilder sqlbuilder) {
		return this.tpgroupdao.getUpdateSql(tpgroupinfo,sqlbuilder);
	}

	public Boolean doExcetueArrayProc(List<String> sqlArrayList,
			List<List<Object>> objArrayList) {
		return this.tpgroupdao.doExcetueArrayProc(sqlArrayList,objArrayList);
	}
	
	public TpGroupInfo getOfExcel(Sheet rs, int cols, int d, String type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ICommonDAO<TpGroupInfo> getBaseDAO() {
		// TODO Auto-generated method stub
		return tpgroupdao;
	}

	@Override
	public String getNextId() {
		// TODO Auto-generated method stub
		return null;
	}

    public boolean checkGroupName(TpGroupInfo t) {
        return this.tpgroupdao.checkGroupName(t);
    }

    public List<TpGroupInfo> getMyGroupList(Integer classid, Integer classtype, String termid, Integer tchid, Integer stuid,Integer subjectid) {
        return this.tpgroupdao.getMyGroupList(classid, classtype, termid, tchid, stuid,subjectid);
    }

    public List<TpGroupInfo> getGroupBySubject(TpGroupInfo obj) {
        return this.tpgroupdao.getGroupBySubject(obj);
    }

}

