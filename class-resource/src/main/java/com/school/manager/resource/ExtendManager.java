
package  com.school.manager.resource;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import jxl.Sheet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.school.dao.inter.resource.IExtendDAO;

import com.school.entity.resource.ExtendInfo;
import com.school.dao.base.ICommonDAO;
import com.school.manager.base.BaseManager;
import com.school.manager.inter.resource.IExtendManager;
import com.school.util.PageResult;

@Service
public class  ExtendManager extends BaseManager<ExtendInfo> implements IExtendManager  { 
	
	private IExtendDAO extenddao;
	
	@Autowired
	@Qualifier("extendDAO")
	public void setExtenddao(IExtendDAO extenddao) {
		this.extenddao = extenddao;
	}
	
	public Boolean doSave(ExtendInfo extendinfo) {
		return extenddao.doSave(extendinfo);
	}
	
	public Boolean doDelete(ExtendInfo extendinfo) {
		return extenddao.doDelete(extendinfo);
	}

	public Boolean doUpdate(ExtendInfo extendinfo) {
		return extenddao.doUpdate(extendinfo);
	}
	
	public List<ExtendInfo> getList(ExtendInfo extendinfo, PageResult presult) {
		return extenddao.getList(extendinfo,presult);	
	}
	
	public List<Object> getSaveSql(ExtendInfo extendinfo, StringBuilder sqlbuilder) {
		return extenddao.getSaveSql(extendinfo,sqlbuilder);
	}

	public List<Object> getDeleteSql(ExtendInfo extendinfo, StringBuilder sqlbuilder) {
		return extenddao.getDeleteSql(extendinfo,sqlbuilder);
	}

	public List<Object> getUpdateSql(ExtendInfo extendinfo, StringBuilder sqlbuilder) {
		return extenddao.getUpdateSql(extendinfo,sqlbuilder);
	}

	public Boolean doExcetueArrayProc(List<String> sqlArrayList,
			List<List<Object>> objArrayList) {
		return extenddao.doExcetueArrayProc(sqlArrayList,objArrayList);
	}
	
	public ExtendInfo getOfExcel(Sheet rs, int cols, int d, String type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ICommonDAO<ExtendInfo> getBaseDAO() {
		// TODO Auto-generated method stub
		return extenddao;
	}
	
	/**
	 * 查找所有的树节点
	 * @param rootid
	 * @return
	 */
	public List<Map<String, Object>> getExtendCheckShu(String rootid){
		return extenddao.getExtendCheckShu(rootid);
	}

	@Override
	public String getNextId() {
		// TODO Auto-generated method stub
		return UUID.randomUUID().toString();
	}

	public Integer doSaveGetId(ExtendInfo extendinfo) {
		// TODO Auto-generated method stub
		return this.extenddao.doSaveGetId(extendinfo);
	}
}

