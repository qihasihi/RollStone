
package  com.school.manager.teachpaltform;

import java.util.List;
import java.util.Map;

import jxl.Sheet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.school.dao.inter.teachpaltform.ITpGroupStudentDAO;

import com.school.entity.teachpaltform.TpGroupStudent;
import com.school.dao.base.ICommonDAO;
import com.school.manager.base.BaseManager;
import com.school.manager.inter.teachpaltform.ITpGroupStudentManager;
import com.school.util.PageResult;

@Service
public class  TpGroupStudentManager extends BaseManager<TpGroupStudent> implements ITpGroupStudentManager  { 
	
	private ITpGroupStudentDAO tpgroupstudentdao;
	
	@Autowired
	@Qualifier("tpGroupStudentDAO")
	public void setTpgroupstudentdao(ITpGroupStudentDAO tpgroupstudentdao) {
		this.tpgroupstudentdao = tpgroupstudentdao;
	}
	
	public Boolean doSave(TpGroupStudent tpgroupstudent) {
		return this.tpgroupstudentdao.doSave(tpgroupstudent);
	}
	
	public Boolean doDelete(TpGroupStudent tpgroupstudent) {
		return this.tpgroupstudentdao.doDelete(tpgroupstudent);
	}

	public Boolean doUpdate(TpGroupStudent tpgroupstudent) {
		return this.tpgroupstudentdao.doUpdate(tpgroupstudent);
	}
	
	public List<TpGroupStudent> getList(TpGroupStudent tpgroupstudent, PageResult presult) {
		return this.tpgroupstudentdao.getList(tpgroupstudent,presult);	
	}
	
	public List<Object> getSaveSql(TpGroupStudent tpgroupstudent, StringBuilder sqlbuilder) {
		return this.tpgroupstudentdao.getSaveSql(tpgroupstudent,sqlbuilder);
	}

	public List<Object> getDeleteSql(TpGroupStudent tpgroupstudent, StringBuilder sqlbuilder) {
		return this.tpgroupstudentdao.getDeleteSql(tpgroupstudent,sqlbuilder);
	}

	public List<Object> getUpdateSql(TpGroupStudent tpgroupstudent, StringBuilder sqlbuilder) {
		return this.tpgroupstudentdao.getUpdateSql(tpgroupstudent,sqlbuilder);
	}

	public Boolean doExcetueArrayProc(List<String> sqlArrayList,
			List<List<Object>> objArrayList) {
		return this.tpgroupstudentdao.doExcetueArrayProc(sqlArrayList,objArrayList);
	}
	
	public TpGroupStudent getOfExcel(Sheet rs, int cols, int d, String type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ICommonDAO<TpGroupStudent> getBaseDAO() {
		// TODO Auto-generated method stub
		return tpgroupstudentdao;
	}

	@Override
	public String getNextId() {
		// TODO Auto-generated method stub
		return null;
	}

    // 获取班级未分配小组的学生
    public List<Map<String,Object>> getNoGroupStudentList(Integer classid,Integer classtype,Integer userid,Integer subjectid,String termid){
        return tpgroupstudentdao.getNoGroupStudentList(classid,classtype,userid,subjectid,termid);
    }

    public List<TpGroupStudent> getGroupStudentByClass(TpGroupStudent gs, PageResult presult) {
        return tpgroupstudentdao.getGroupStudentByClass(gs,presult);
    }
}

