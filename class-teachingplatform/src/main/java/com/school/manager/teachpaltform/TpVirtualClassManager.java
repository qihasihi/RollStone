
package  com.school.manager.teachpaltform;

import java.util.List;
import java.util.Map;

import jxl.Sheet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.school.dao.inter.teachpaltform.ITpVirtualClassDAO;

import com.school.entity.teachpaltform.TpVirtualClassInfo;
import com.school.dao.base.ICommonDAO;
import com.school.manager.base.BaseManager;
import com.school.manager.inter.teachpaltform.ITpVirtualClassManager;
import com.school.util.PageResult;

@Service
public class  TpVirtualClassManager extends BaseManager<TpVirtualClassInfo> implements ITpVirtualClassManager  { 
	
	private ITpVirtualClassDAO tpvirtualclassdao;
	
	@Autowired
	@Qualifier("tpVirtualClassDAO")
	public void setTpvirtualclassdao(ITpVirtualClassDAO tpvirtualclassdao) {
		this.tpvirtualclassdao = tpvirtualclassdao;
	}
	
	public Boolean doSave(TpVirtualClassInfo tpvirtualclassinfo) {
		return this.tpvirtualclassdao.doSave(tpvirtualclassinfo);
	}
	
	public Boolean doDelete(TpVirtualClassInfo tpvirtualclassinfo) {
		return this.tpvirtualclassdao.doDelete(tpvirtualclassinfo);
	}

	public Boolean doUpdate(TpVirtualClassInfo tpvirtualclassinfo) {
		return this.tpvirtualclassdao.doUpdate(tpvirtualclassinfo);
	}
	
	public List<TpVirtualClassInfo> getList(TpVirtualClassInfo tpvirtualclassinfo, PageResult presult) {
		return this.tpvirtualclassdao.getList(tpvirtualclassinfo,presult);	
	}
	
	public List<Object> getSaveSql(TpVirtualClassInfo tpvirtualclassinfo, StringBuilder sqlbuilder) {
		return this.tpvirtualclassdao.getSaveSql(tpvirtualclassinfo,sqlbuilder);
	}

	public List<Object> getDeleteSql(TpVirtualClassInfo tpvirtualclassinfo, StringBuilder sqlbuilder) {
		return this.tpvirtualclassdao.getDeleteSql(tpvirtualclassinfo,sqlbuilder);
	}

	public List<Object> getUpdateSql(TpVirtualClassInfo tpvirtualclassinfo, StringBuilder sqlbuilder) {
		return this.tpvirtualclassdao.getUpdateSql(tpvirtualclassinfo,sqlbuilder);
	}

	public Boolean doExcetueArrayProc(List<String> sqlArrayList,
			List<List<Object>> objArrayList) {
		return this.tpvirtualclassdao.doExcetueArrayProc(sqlArrayList,objArrayList);
	}
	
	public TpVirtualClassInfo getOfExcel(Sheet rs, int cols, int d, String type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ICommonDAO<TpVirtualClassInfo> getBaseDAO() {
		// TODO Auto-generated method stub
		return tpvirtualclassdao;
	}

	@Override
	public String getNextId() {
		// TODO Auto-generated method stub
		return null;
	}

    public List<Map<String, Object>> getListBytch(String userid, String year) {
        return tpvirtualclassdao.getListBytch( userid, year);
    }
}

