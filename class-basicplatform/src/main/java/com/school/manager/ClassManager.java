
package  com.school.manager;

import java.util.List;

import jxl.Sheet;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.school.dao.base.ICommonDAO;
import com.school.dao.inter.IClassDAO;
import com.school.util.PageResult;
import com.school.entity.ClassInfo;
import com.school.manager.base.BaseManager;
import com.school.manager.inter.IClassManager;

@Service
public class  ClassManager extends BaseManager<ClassInfo> implements IClassManager  {
	private IClassDAO classdao ;
	 
	@Autowired
	@Qualifier("classDAO")	
	
	public void setClassdao(IClassDAO classdao) {
		this.classdao = classdao;
	}

	@Override
	public Boolean doDelete(ClassInfo obj) {
		return this.classdao.doDelete(obj);
	}

	@Override
	public Boolean doSave(ClassInfo obj) {
		return this.classdao.doSave(obj);
	}

	@Override
	public Boolean doUpdate(ClassInfo obj) {
		return this.classdao.doUpdate(obj);
	}

	@Override
	protected ICommonDAO<ClassInfo> getBaseDAO() {
		return classdao;
	}

	@Override
	public List<ClassInfo> getList(ClassInfo obj, PageResult presult) {
        return this.classdao.getList(obj, presult);
	}


	@Override
	public String getNextId() {
		// TODO Auto-generated method stub
		return this.classdao.getNextId();
	}
	public List<Object> getDeleteSql(ClassInfo obj, StringBuilder sqlbuilder) {
		return this.classdao.getDeleteSql(obj, sqlbuilder);
	}

	public List<Object> getSaveSql(ClassInfo obj, StringBuilder sqlbuilder) { 
		return this.classdao.getSaveSql(obj, sqlbuilder);
	} 

	public List<Object> getUpdateSql(ClassInfo obj, StringBuilder sqlbuilder) {
		return this.classdao.getUpdateSql(obj, sqlbuilder);  
	}
	/**
	 * 根据UserRef,YEAR,PAttern得到班级
	 * @param userref
	 * @param year
	 * @param pattern
	 * @return
	 */
	public List<ClassInfo> getClassByUserYearPattern(String userref,String year,String pattern){
		return this.classdao.getClassByUserYearPattern(userref, year, pattern);
	}
	public ClassInfo getOfExcel(Sheet rs, int cols, int d, String type) {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * 升级操作
	 * @param year
	 * @return
	 */
	public Boolean doClassLevelUp(String year){
		return this.classdao.doClassLevelUp(year);
	}
    /**
     * 得到更新或添加的SQL
     * @param obj;
     * @param sqlbuilder
     * @return
     */
    public List<Object> getSaveOrUpdateSql(ClassInfo obj, StringBuilder sqlbuilder){
        return this.classdao.getSaveOrUpdateSql(obj,sqlbuilder);
    }
	
	
	
}

