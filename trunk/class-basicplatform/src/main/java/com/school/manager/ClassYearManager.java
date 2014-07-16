
package  com.school.manager;

import java.util.List;
import java.util.Map;

import jxl.Sheet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.school.dao.base.ICommonDAO;
import com.school.dao.inter.IClassYearDAO;
import com.school.util.PageResult;
import com.school.entity.ClassYearInfo;
import com.school.manager.base.BaseManager;
import com.school.manager.inter.IClassYearManager;

@Service
public class  ClassYearManager extends BaseManager<ClassYearInfo> implements IClassYearManager  {
	private IClassYearDAO classyeardao ;
	 
	@Autowired
	@Qualifier("classYearDAO")
	public void setClassYeardao(IClassYearDAO classyeardao) {
		this.classyeardao = classyeardao;
	}

	@Override
	public Boolean doDelete(ClassYearInfo obj) {
		return this.classyeardao.doDelete(obj);
	}

	@Override
	public Boolean doSave(ClassYearInfo obj) {
		return this.classyeardao.doSave(obj);
	}

	@Override
	public Boolean doUpdate(ClassYearInfo obj) {
		return this.classyeardao.doUpdate(obj);
	}

	@Override
	protected ICommonDAO<ClassYearInfo> getBaseDAO() {
		return classyeardao;
	}

	@Override
	public List<ClassYearInfo> getList(ClassYearInfo obj, PageResult presult) {
		return this.classyeardao.getList(obj, presult);
	}


	@Override
	public String getNextId() {
		// TODO Auto-generated method stub
		return this.classyeardao.getNextId();
	}
	public List<Object> getDeleteSql(ClassYearInfo obj, StringBuilder sqlbuilder) {
		return this.classyeardao.getDeleteSql(obj, sqlbuilder);
	}

	public List<Object> getSaveSql(ClassYearInfo obj, StringBuilder sqlbuilder) { 
		return this.classyeardao.getSaveSql(obj, sqlbuilder);
	} 

	public List<Object> getUpdateSql(ClassYearInfo obj, StringBuilder sqlbuilder) {
		return this.classyeardao.getUpdateSql(obj, sqlbuilder);  
	}

	public ClassYearInfo getOfExcel(Sheet rs, int cols, int d, String type) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<ClassYearInfo> getCurrentYearList(String flag) {
		// TODO Auto-generated method stub
		return this.classyeardao.getCurrentYearList(flag);
	} 
	/**
	 * 得到过去的年份
	 * @param cyear
	 * @return
	 */
	public List<Map<String,Object>> getClassYearPree(ClassYearInfo cyear){
		return this.classyeardao.getClassYearPree(cyear);
	}
	
	
}

