
package  com.school.manager;

import java.util.List;
import java.util.Map;

import jxl.Sheet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.school.dao.base.ICommonDAO;
import com.school.dao.inter.IClassUserDAO;
import com.school.util.PageResult;
import com.school.entity.ClassUser;
import com.school.manager.base.BaseManager;
import com.school.manager.inter.IClassUserManager;

@Service
public class  ClassUserManager extends BaseManager<ClassUser> implements IClassUserManager  {
	private IClassUserDAO classuserdao ;
	 
	@Autowired
	@Qualifier("classUserDAO")
	
	
	public void setClassuserdao(IClassUserDAO classuserdao) {
		this.classuserdao = classuserdao;
	}

	@Override
	public Boolean doDelete(ClassUser obj) {
		return this.classuserdao.doDelete(obj);
	}

	@Override
	public Boolean doSave(ClassUser obj) {
		return this.classuserdao.doSave(obj);
	}

	@Override
	public Boolean doUpdate(ClassUser obj) {
		return this.classuserdao.doUpdate(obj);
	}

	@Override
	protected ICommonDAO<ClassUser> getBaseDAO() {
		return classuserdao;
	}

	@Override
	public List<ClassUser> getList(ClassUser obj, PageResult presult) {
		return this.classuserdao.getList(obj, presult);
	}


	@Override
	public String getNextId() {
		// TODO Auto-generated method stub
		return this.classuserdao.getNextId();
	}

	public List<Object> getDeleteSql(ClassUser obj, StringBuilder sqlbuilder) {
		return this.classuserdao.getDeleteSql(obj, sqlbuilder);
	}

	public List<Object> getSaveSql(ClassUser obj, StringBuilder sqlbuilder) { 
		return this.classuserdao.getSaveSql(obj, sqlbuilder);
	} 

	public List<Object> getUpdateSql(ClassUser obj, StringBuilder sqlbuilder) {
		return this.classuserdao.getUpdateSql(obj, sqlbuilder);  
	}

	public ClassUser getOfExcel(Sheet rs, int cols, int d, String type) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<ClassUser> getClassUserByTchAndStu(String tchUserRef,
			String stuUserRef, String year,Integer clsisflag) {
		// TODO Auto-generated method stub
		return this.classuserdao.getClassUserByTchAndStu(tchUserRef, stuUserRef, year,clsisflag);
	} 
	/**
	 * 得到论题下的学生
	 * @param clsUser
	 * @param presult
	 * @return
	 */
	public List<ClassUser> getThemeList(ClassUser clsUser,PageResult presult){
		return this.classuserdao.getThemeList(clsUser,presult);
	}
	/**
	 * 调班查询学生。
	 * @param relationType
	 * @param year
	 * @param clsid
	 * @return
	 */
	public List<Map<String,Object>> getClassUserWithTiaoban(String relationType,String year,String clsid){
		return this.classuserdao.getClassUserWithTiaoban(relationType, year, clsid);
	}

    public List<ClassUser> getListByTchYear(String userid, String year) {
        return this.classuserdao.getListByTchYear(userid,year);
    }
    /**
     * 资源上传，得到Grade，subjectid
     * @param userref
     * @param relationType
     * @param year
     * @return
     */
    public List<Map<String,Object>>  getClassUserTeacherBy(String userref,String relationType,String year,Integer gradeid){
        return this.classuserdao.getClassUserTeacherBy(userref,relationType,year,gradeid);
    }

    @Override
    public Integer isTeachingBanZhuRen(String userid, Integer classid,String termid,String gradevalue) {
        return this.classuserdao.isTeachingBanZhuRen(userid,classid,termid,gradevalue);
    }
}

