
package  com.school.manager;

import java.util.List;

import jxl.Sheet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.school.dao.base.ICommonDAO;
import com.school.dao.inter.ITeacherDAO;
import com.school.util.PageResult;
import com.school.entity.TeacherInfo;
import com.school.manager.base.BaseManager;
import com.school.manager.inter.ITeacherManager;

@Service
public class  TeacherManager extends BaseManager<TeacherInfo> implements ITeacherManager  {
	private ITeacherDAO teacherdao ;
	 
	@Autowired
	@Qualifier("teacherDAO")
	
	
	public void setteacherdao(ITeacherDAO teacherdao) {
		this.teacherdao = teacherdao;
	}

	@Override
	public Boolean doDelete(TeacherInfo obj) {
		return this.teacherdao.doDelete(obj);
	}

	@Override
	public Boolean doSave(TeacherInfo obj) {
		return this.teacherdao.doSave(obj);
	}

	@Override
	public Boolean doUpdate(TeacherInfo obj) {
		return this.teacherdao.doUpdate(obj);
	}

	@Override
	protected ICommonDAO<TeacherInfo> getBaseDAO() {
		return teacherdao;
	}

	@Override
	public List<TeacherInfo> getList(TeacherInfo obj, PageResult presult) {
		return this.teacherdao.getList(obj, presult);
	}


	@Override
	public String getNextId() {
		// TODO Auto-generated method stub
		return this.teacherdao.getNextId();
	}

	public List<Object> getDeleteSql(TeacherInfo obj, StringBuilder sqlbuilder) {
		return this.teacherdao.getDeleteSql(obj, sqlbuilder);
	}

	public List<Object> getSaveSql(TeacherInfo obj, StringBuilder sqlbuilder) { 
		return this.teacherdao.getSaveSql(obj, sqlbuilder);
	} 

	public List<Object> getUpdateSql(TeacherInfo obj, StringBuilder sqlbuilder) {
		return this.teacherdao.getUpdateSql(obj, sqlbuilder);  
	}

	public TeacherInfo getOfExcel(Sheet rs, int cols, int d, String type) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<TeacherInfo> getTeacherListByUserId(String userid,String year) {
		// TODO Auto-generated method stub
		return teacherdao.getTeacherListByUserId(userid,year);
	} 
	
	public TeacherInfo loadBy(String userRef){
		TeacherInfo t = new TeacherInfo();
		t.setUserid(userRef);
		List<TeacherInfo> tl = this.getList(t, null);
		if(tl!=null&&tl.size()>0)
			return tl.get(0);
		else
			return  null;
	}

	public List<TeacherInfo> getListByTchnameOrUsername(TeacherInfo obj,
			PageResult presult) {
		// TODO Auto-generated method stub
		return teacherdao.getListByTchnameOrUsername(obj, presult);
	}
	
}

