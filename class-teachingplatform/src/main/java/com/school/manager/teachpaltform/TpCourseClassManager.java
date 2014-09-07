
package  com.school.manager.teachpaltform;

import java.util.List;
import java.util.Map;

import jxl.Sheet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.school.dao.inter.teachpaltform.ITpCourseClassDAO;

import com.school.entity.teachpaltform.TpCourseClass;
import com.school.dao.base.ICommonDAO;
import com.school.manager.base.BaseManager;
import com.school.manager.inter.teachpaltform.ITpCourseClassManager;
import com.school.util.PageResult;

@Service
public class  TpCourseClassManager extends BaseManager<TpCourseClass> implements ITpCourseClassManager  { 
	
	private ITpCourseClassDAO tpcourseclassdao;
	
	@Autowired
	@Qualifier("tpCourseClassDAO")
	public void setTpcourseclassdao(ITpCourseClassDAO tpcourseclassdao) {
		this.tpcourseclassdao = tpcourseclassdao;
	}
	
	public Boolean doSave(TpCourseClass tpcourseclass) {
		return this.tpcourseclassdao.doSave(tpcourseclass);
	}
	
	public Boolean doDelete(TpCourseClass tpcourseclass) {
		return this.tpcourseclassdao.doDelete(tpcourseclass);
	}

	public Boolean doUpdate(TpCourseClass tpcourseclass) {
		return this.tpcourseclassdao.doUpdate(tpcourseclass);
	}
	
	public List<TpCourseClass> getList(TpCourseClass tpcourseclass, PageResult presult) {
		return this.tpcourseclassdao.getList(tpcourseclass,presult);	
	}
	
	public List<Object> getSaveSql(TpCourseClass tpcourseclass, StringBuilder sqlbuilder) {
		return this.tpcourseclassdao.getSaveSql(tpcourseclass,sqlbuilder);
	}

	public List<Object> getDeleteSql(TpCourseClass tpcourseclass, StringBuilder sqlbuilder) {
		return this.tpcourseclassdao.getDeleteSql(tpcourseclass,sqlbuilder);
	}

	public List<Object> getUpdateSql(TpCourseClass tpcourseclass, StringBuilder sqlbuilder) {
		return this.tpcourseclassdao.getUpdateSql(tpcourseclass,sqlbuilder);
	}

	public Boolean doExcetueArrayProc(List<String> sqlArrayList,
			List<List<Object>> objArrayList) {
		return this.tpcourseclassdao.doExcetueArrayProc(sqlArrayList,objArrayList);
	}
	
	public TpCourseClass getOfExcel(Sheet rs, int cols, int d, String type) {
		// TODO Auto-generated method stub
		return null;
	}
    public List<Map<String,Object>> getTpClassCourse(Long courseid,Integer userid,String termid){
        return this.tpcourseclassdao.getTpClassCourse(courseid,userid,termid);
    }
    /**
     * 得到记录，根据班级ID,TERMID
     * 查询列  DISTINCT sub.subject_name,sub.lzx_subject_id,sub.subject_type
     * @param clsid
     * @param termid
     * @return
     */
    public List<TpCourseClass> getTpCourseClassByClsTermId(final Integer clsid,final String termid){
        return this.tpcourseclassdao.getTpCourseClassByClsTermId(clsid,termid);
    }

	@Override
	protected ICommonDAO<TpCourseClass> getBaseDAO() {
		// TODO Auto-generated method stub
		return tpcourseclassdao;
	}

	@Override
	public String getNextId() {
		// TODO Auto-generated method stub
		return null;
	}
}

