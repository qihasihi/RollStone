
package  com.school.manager.teachpaltform;

import java.util.List;
import jxl.Sheet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.school.dao.inter.teachpaltform.IClassCadresDAO;

import com.school.entity.teachpaltform.ClassCadresInfo;
import com.school.dao.base.ICommonDAO;
import com.school.manager.base.BaseManager;
import com.school.manager.inter.teachpaltform.IClassCadresManager;
import com.school.util.PageResult;

@Service
public class  ClassCadresManager extends BaseManager<ClassCadresInfo> implements IClassCadresManager  { 
	
	private IClassCadresDAO classcadresdao;
	
	@Autowired
	@Qualifier("classCadresDAO")
	public void setClasscadresdao(IClassCadresDAO classcadresdao) {
		this.classcadresdao = classcadresdao;
	}
	
	public Boolean doSave(ClassCadresInfo classcadresinfo) {
		return this.classcadresdao.doSave(classcadresinfo);
	}
	
	public Boolean doDelete(ClassCadresInfo classcadresinfo) {
		return this.classcadresdao.doDelete(classcadresinfo);
	}

	public Boolean doUpdate(ClassCadresInfo classcadresinfo) {
		return this.classcadresdao.doUpdate(classcadresinfo);
	}
	
	public List<ClassCadresInfo> getList(ClassCadresInfo classcadresinfo, PageResult presult) {
		return this.classcadresdao.getList(classcadresinfo,presult);	
	}
	
	public List<Object> getSaveSql(ClassCadresInfo classcadresinfo, StringBuilder sqlbuilder) {
		return this.classcadresdao.getSaveSql(classcadresinfo,sqlbuilder);
	}

	public List<Object> getDeleteSql(ClassCadresInfo classcadresinfo, StringBuilder sqlbuilder) {
		return this.classcadresdao.getDeleteSql(classcadresinfo,sqlbuilder);
	}

	public List<Object> getUpdateSql(ClassCadresInfo classcadresinfo, StringBuilder sqlbuilder) {
		return this.classcadresdao.getUpdateSql(classcadresinfo,sqlbuilder);
	}

	public Boolean doExcetueArrayProc(List<String> sqlArrayList,
			List<List<Object>> objArrayList) {
		return this.classcadresdao.doExcetueArrayProc(sqlArrayList,objArrayList);
	}
	
	public ClassCadresInfo getOfExcel(Sheet rs, int cols, int d, String type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ICommonDAO<ClassCadresInfo> getBaseDAO() {
		// TODO Auto-generated method stub
		return classcadresdao;
	}

	@Override
	public String getNextId() {
		// TODO Auto-generated method stub
		return null;
	}
}

