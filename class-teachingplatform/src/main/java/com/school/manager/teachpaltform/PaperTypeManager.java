
package  com.school.manager.teachpaltform;

import java.util.List;
import jxl.Sheet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.school.dao.inter.teachpaltform.IPaperTypeDAO;

import com.school.entity.teachpaltform.PaperTypeInfo;
import com.school.dao.base.ICommonDAO;
import com.school.manager.base.BaseManager;
import com.school.manager.inter.teachpaltform.IPaperTypeManager;
import com.school.util.PageResult;

@Service
public class  PaperTypeManager extends BaseManager<PaperTypeInfo> implements IPaperTypeManager  { 
	
	private IPaperTypeDAO papertypedao;
	
	@Autowired
	@Qualifier("paperTypeDAO")
	public void setPapertypedao(IPaperTypeDAO papertypedao) {
		this.papertypedao = papertypedao;
	}
	
	public Boolean doSave(PaperTypeInfo papertypeinfo) {
		return this.papertypedao.doSave(papertypeinfo);
	}
	
	public Boolean doDelete(PaperTypeInfo papertypeinfo) {
		return this.papertypedao.doDelete(papertypeinfo);
	}

	public Boolean doUpdate(PaperTypeInfo papertypeinfo) {
		return this.papertypedao.doUpdate(papertypeinfo);
	}
	
	public List<PaperTypeInfo> getList(PaperTypeInfo papertypeinfo, PageResult presult) {
		return this.papertypedao.getList(papertypeinfo,presult);	
	}
	
	public List<Object> getSaveSql(PaperTypeInfo papertypeinfo, StringBuilder sqlbuilder) {
		return this.papertypedao.getSaveSql(papertypeinfo,sqlbuilder);
	}

	public List<Object> getDeleteSql(PaperTypeInfo papertypeinfo, StringBuilder sqlbuilder) {
		return this.papertypedao.getDeleteSql(papertypeinfo,sqlbuilder);
	}

	public List<Object> getUpdateSql(PaperTypeInfo papertypeinfo, StringBuilder sqlbuilder) {
		return this.papertypedao.getUpdateSql(papertypeinfo,sqlbuilder);
	}

	public Boolean doExcetueArrayProc(List<String> sqlArrayList,
			List<List<Object>> objArrayList) {
		return this.papertypedao.doExcetueArrayProc(sqlArrayList,objArrayList);
	}
	
	public PaperTypeInfo getOfExcel(Sheet rs, int cols, int d, String type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ICommonDAO<PaperTypeInfo> getBaseDAO() {
		// TODO Auto-generated method stub
		return papertypedao;
	}

	@Override
	public String getNextId() {
		// TODO Auto-generated method stub
		return null;
	}
}

