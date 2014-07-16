
package  com.school.manager.resource;

import java.util.List;
import java.util.UUID;

import jxl.Sheet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.school.dao.inter.resource.IExtendValueDAO;

import com.school.entity.resource.ExtendValueInfo;
import com.school.dao.base.ICommonDAO;
import com.school.manager.base.BaseManager;
import com.school.manager.inter.resource.IExtendValueManager;
import com.school.util.PageResult;

@Service
public class  ExtendValueManager extends BaseManager<ExtendValueInfo> implements IExtendValueManager  { 
	
	private IExtendValueDAO extendvaluedao;
	
	@Autowired
	@Qualifier("extendValueDAO")
	public void setExtendvaluedao(IExtendValueDAO extendvaluedao) {
		this.extendvaluedao = extendvaluedao;
	}
	
	public Boolean doSave(ExtendValueInfo extendvalueinfo) {
		return extendvaluedao.doSave(extendvalueinfo);
	}
	
	public Boolean doDelete(ExtendValueInfo extendvalueinfo) {
		return extendvaluedao.doDelete(extendvalueinfo);
	}

	public Boolean doUpdate(ExtendValueInfo extendvalueinfo) {
		return extendvaluedao.doUpdate(extendvalueinfo);
	}
	
	public List<ExtendValueInfo> getList(ExtendValueInfo extendvalueinfo, PageResult presult) {
		return extendvaluedao.getList(extendvalueinfo,presult);	
	}
	
	public List<Object> getSaveSql(ExtendValueInfo extendvalueinfo, StringBuilder sqlbuilder) {
		return extendvaluedao.getSaveSql(extendvalueinfo,sqlbuilder);
	}

	public List<Object> getDeleteSql(ExtendValueInfo extendvalueinfo, StringBuilder sqlbuilder) {
		return extendvaluedao.getDeleteSql(extendvalueinfo,sqlbuilder);
	}

	public List<Object> getUpdateSql(ExtendValueInfo extendvalueinfo, StringBuilder sqlbuilder) {
		return extendvaluedao.getUpdateSql(extendvalueinfo,sqlbuilder);
	}

	public Boolean doExcetueArrayProc(List<String> sqlArrayList,
			List<List<Object>> objArrayList) {
		return extendvaluedao.doExcetueArrayProc(sqlArrayList,objArrayList);
	}
	
	public ExtendValueInfo getOfExcel(Sheet rs, int cols, int d, String type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ICommonDAO<ExtendValueInfo> getBaseDAO() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String getNextId() {
		// TODO Auto-generated method stub
		return UUID.randomUUID().toString();
	}

	public List<ExtendValueInfo> getNotInList(String extendid, String valueList) {
		// TODO Auto-generated method stub
		return this.extendvaluedao.getNotInList(extendid, valueList);
	}

	public List<Object> getUpdateOrderBySql(ExtendValueInfo extendvalueinfo,
			StringBuilder sqlbuilder) {
		// TODO Auto-generated method stub
		return this.extendvaluedao.getUpdateOrderBySql(extendvalueinfo, sqlbuilder);
	}
}

