package  com.school.dao;


import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.school.dao.base.CommonDAO;
import com.school.dao.inter.IDeptDAO;
import com.school.entity.DeptInfo;
import com.school.entity.DeptUser;
import com.school.util.PageResult;

@Component  
public class DeptDAO extends CommonDAO<DeptInfo> implements IDeptDAO {

	public Boolean doDelete(DeptInfo obj) {
		StringBuilder sqlbuilder=new StringBuilder();
		List<Object> objList=getDeleteSql(obj, sqlbuilder);
		Object afficeObj=this.executeSacle_PROC(sqlbuilder.toString(), objList.toArray());
		if(afficeObj!=null&&afficeObj.toString().trim().length()>0&&Integer.parseInt(afficeObj.toString())>0){
			return true;
		}return false;
	}

	public Boolean doExcetueArrayProc(List<String> sqlArrayList,
			List<List<Object>> objArrayList) {
		return this.executeArray_SQL(sqlArrayList, objArrayList);
	}

	public Boolean doSave(DeptInfo obj) {
		if (obj == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getSaveSql(obj, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}

	public Boolean doUpdate(DeptInfo obj) {
		if (obj == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getUpdateSql(obj, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}
	/**
	 * ¼¶ÁªÉ¾³ý 
	 */
	public List<Object> getDeleteSql(DeptInfo obj, StringBuilder sqlbuilder) {
		if(obj==null||sqlbuilder==null)
			return null;
		sqlbuilder.append("{call dept_proc_delete(");
		List<Object>objList = new ArrayList<Object>();
		if(obj.getDeptid()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getDeptid());
		}else 
			sqlbuilder.append("NULL,");
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<DeptInfo> getList(DeptInfo obj, PageResult presult) {
		StringBuilder sqlbuilder=new StringBuilder("{CALL dept_proc_search_split(");
		List<Object> objList=new ArrayList<Object>();
		if(obj==null)
			sqlbuilder.append("NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,");
		else{
			if(obj.getDeptid()!=null){
				sqlbuilder.append("?,");
				objList.add(obj.getDeptid());
			}else
				sqlbuilder.append("NULL,");
			if(obj.getParentdeptid()!=null){
				sqlbuilder.append("?,");
				objList.add(obj.getParentdeptid());
			}else
				sqlbuilder.append("NULL,");
			if(obj.getDeptname()!=null){
				sqlbuilder.append("?,");
				objList.add(obj.getDeptname());
			}else
				sqlbuilder.append("NULL,");
			if(obj.getGrade()!=null){
				sqlbuilder.append("?,");
				objList.add(obj.getGrade());
			}else
				sqlbuilder.append("NULL,");
			if(obj.getSubjectid()!=null){
				sqlbuilder.append("?,");
				objList.add(obj.getSubjectid());
			}else
				sqlbuilder.append("NULL,");
			if(obj.getStudyperiods()!=null){
				sqlbuilder.append("?,");
				objList.add(obj.getStudyperiods()); 
			}else
				sqlbuilder.append("NULL,");
			if(obj.getUserinfo().getUserid()!=null){
				sqlbuilder.append("?,");
				objList.add(obj.getUserinfo().getUserid());
			}else
				sqlbuilder.append("NULL,"); 
			if(obj.getTypeid()!=null){
				sqlbuilder.append("?,");
				objList.add(obj.getTypeid());
			}else
				sqlbuilder.append("NULL,"); 
		} 
		if(presult!=null&&presult.getPageNo()>0&&presult.getPageSize()>0){
			sqlbuilder.append("?,?,");
			objList.add(presult.getPageNo());
			objList.add(presult.getPageSize());
		}else{
			sqlbuilder.append("NULL,NULL,");
		}
		if(presult!=null&&presult.getOrderBy()!=null&&presult.getOrderBy().trim().length()>0){
			sqlbuilder.append("?,");
			objList.add(presult.getOrderBy());
		}else{
			sqlbuilder.append("NULL,");
		}
		sqlbuilder.append("?)}");	
		List<Integer> types=new ArrayList<Integer>();
		types.add(Types.INTEGER);
		Object[] objArray=new Object[1];
		List<DeptInfo> roleList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, DeptInfo.class, objArray);
		if(presult!=null&&objArray[0]!=null&&objArray[0].toString().trim().length()>0) 
			presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));				
		return roleList;	
	}

	public List<Object> getSaveSql(DeptInfo obj, StringBuilder sqlbuilder) {
		if(obj==null||sqlbuilder==null) 
			return null;
		sqlbuilder.append("{call dept_proc_add("); 
		List<Object>objList = new ArrayList<Object>();
		if(obj.getTypeid()!=null){
			sqlbuilder.append("?,"); 
			objList.add(obj.getTypeid()); 
		}else
			sqlbuilder.append("NULL,");
		if(obj.getDeptname()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getDeptname());
		}else
			sqlbuilder.append("NULL,");
		if(obj.getParentdeptid()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getParentdeptid());
		}else
			sqlbuilder.append("NULL,");
		if(obj.getUserinfo().getUserid()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getUserinfo().getUserid()); 
		}else
			sqlbuilder.append("NULL,");
		if(obj.getGrade()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getGrade()); 
		}else
			sqlbuilder.append("NULL,");
		if(obj.getSubjectid()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getSubjectid()); 
		}else
			sqlbuilder.append("NULL,");
		if(obj.getStudyperiods()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getStudyperiods());
		}else
			sqlbuilder.append("NULL,");
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getUpdateSql(DeptInfo obj, StringBuilder sqlbuilder) {
		if(obj==null||sqlbuilder==null)
			return null;
		sqlbuilder.append("{call dept_proc_update(");
		List<Object>objList = new ArrayList<Object>();
		if(obj.getDeptid()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getDeptid());
		}else
			sqlbuilder.append("NULL,");
		if(obj.getTypeid()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getTypeid());
		}else
			sqlbuilder.append("NULL,");
		if(obj.getDeptname()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getDeptname());
		}else
			sqlbuilder.append("NULL,");
		if(obj.getParentdeptid()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getParentdeptid());
		}else
			sqlbuilder.append("NULL,");
		if(obj.getUserinfo().getUserid()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getUserinfo().getUserid()); 
		}else
			sqlbuilder.append("NULL,");
		if(obj.getGrade()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getGrade()); 
		}else
			sqlbuilder.append("NULL,");
		if(obj.getSubjectid()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getSubjectid());  
		}else
			sqlbuilder.append("NULL,");
		if(obj.getStudyperiods()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getStudyperiods());
		}else
			sqlbuilder.append("NULL,");
		sqlbuilder.append("?)}");
		return objList;   
	}

	public List<DeptUser> getNotInDeptUser(Integer roleid,String typeid,String nameid) {
		StringBuilder sqlbuilder=new StringBuilder("{CALL dept_proc_notin_dept_user(");
		List<Object> objList=new ArrayList<Object>();
		if(roleid!=null){
			sqlbuilder.append("?,");
			objList.add(roleid);
		}else
			sqlbuilder.append("NULL,");
		if(typeid!=null){
			sqlbuilder.append("?,");
			objList.add(typeid);  
		}else
			sqlbuilder.append("NULL,");
		if(nameid!=null){
			sqlbuilder.append("?");
			objList.add(nameid);  
		}else
			sqlbuilder.append("NULL");
		sqlbuilder.append(")}");    
		List<Integer> types=new ArrayList<Integer>();
		List<DeptUser> roleList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, DeptUser.class, null);
		return roleList;	
	} 

}
