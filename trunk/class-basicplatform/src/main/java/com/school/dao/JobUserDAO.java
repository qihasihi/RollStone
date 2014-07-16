package  com.school.dao;


import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.school.dao.base.CommonDAO;
import com.school.dao.inter.IJobUserDAO;
import com.school.entity.JobUser;
import com.school.util.PageResult;

@Component  
public class JobUserDAO extends CommonDAO<JobUser> implements IJobUserDAO {

	public Boolean doDelete(JobUser obj) {
		StringBuilder sqlbuilder=new StringBuilder();
		List<Object> objList=getDeleteSql(obj, sqlbuilder);
		Object afficeObj=this.executeSacle_PROC(sqlbuilder.toString(), objList.toArray());
		if(afficeObj!=null&&afficeObj.toString().trim().length()>0&&Integer.parseInt(afficeObj.toString())>0){
			return true;
		}return false;
	}

	public Boolean doExcetueArrayProc(List<String> sqlArrayList,
			List<List<Object>> objArrayList) {
		return this.executeArrayQuery_PROC(sqlArrayList, objArrayList);
	}
 
	public Boolean doSave(JobUser obj) {
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

	public Boolean doUpdate(JobUser obj) {
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

	public List<Object> getDeleteSql(JobUser obj, StringBuilder sqlbuilder) {
		if(obj==null||sqlbuilder==null)
			return null;
		sqlbuilder.append("{call jobuser_proc_delete(");
		List<Object>objList = new ArrayList<Object>();
		if(obj.getRef()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getRef());
		}else
			sqlbuilder.append("NULL,");
		if(obj.getUserid()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getUserid());
		}else
			sqlbuilder.append("NULL,");
		if(obj.getJobid()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getJobid());
		}else
			sqlbuilder.append("NULL,");
		sqlbuilder.append("?)}");
		return objList; 
	} 

	public List<JobUser> getList(JobUser obj, PageResult presult) {
		StringBuilder sqlbuilder=new StringBuilder("{CALL jobuser_proc_search_split(");
		List<Object> objList=new ArrayList<Object>();
		if(obj==null)
			sqlbuilder.append("NULL,NULL,NULL");
		else{
			if(obj.getJobid()!=null){
				sqlbuilder.append("?,");
				objList.add(obj.getJobid()); 
			}else
				sqlbuilder.append("NULL,");
			if(obj.getUserid()!=null){
				sqlbuilder.append("?,");
				objList.add(obj.getUserid());
			}else
				sqlbuilder.append("NULL,");
			if(obj.getJobname()!=null){
				sqlbuilder.append("?,");
				objList.add(obj.getJobname()); 
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
		List<JobUser> jobList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, JobUser.class, objArray);
		if(presult!=null&&objArray[0]!=null&&objArray[0].toString().trim().length()>0)
			presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));				
		return jobList; 	
	}


	public List<Object> getSaveSql(JobUser obj, StringBuilder sqlbuilder) {
		if(obj==null||sqlbuilder==null)
			return null;
		sqlbuilder.append("{call jobuser_proc_add(");
		List<Object>objList = new ArrayList<Object>();
		if(obj.getUserid()!=null){ 
			sqlbuilder.append("?,");
			objList.add(obj.getUserid());
		}else
			sqlbuilder.append("NULL,");
		if(obj.getJobid()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getJobid()); 
		}else
			sqlbuilder.append("NULL,");
		sqlbuilder.append("?)}"); 
		return objList;
	}

	public List<Object> getUpdateSql(JobUser obj, StringBuilder sqlbuilder) {
		if(obj==null||sqlbuilder==null)
			return null;
		sqlbuilder.append("{call jobuser_proc_update("); 
		List<Object>objList = new ArrayList<Object>();
//		if(obj.!=null){
//			sqlbuilder.append("?,");
//			objList.add(obj.);
//		}else
//			sqlbuilder.append("NULL,");
//		
		sqlbuilder.append("?)}");
		return null;   
	}

}
