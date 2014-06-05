package  com.school.dao;


import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.school.dao.base.CommonDAO;
import com.school.dao.inter.ISubjectUserDAO;
import com.school.entity.SubjectUser;
import com.school.util.PageResult;

@Component  
public class SubjectUserDAO extends CommonDAO<SubjectUser> implements ISubjectUserDAO {

	public Boolean doDelete(SubjectUser obj) {
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
 
	public Boolean doSave(SubjectUser obj) {
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

	public Boolean doUpdate(SubjectUser obj) {
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

	public List<Object> getDeleteSql(SubjectUser obj, StringBuilder sqlbuilder) {
		if(obj==null||sqlbuilder==null)
			return null;
		sqlbuilder.append("{call subject_user_proc_delete(");
		List<Object>objList = new ArrayList<Object>();
		if(obj.getRef()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getRef());
		}else
			sqlbuilder.append("NULL,");
		if(obj.getUserinfo().getRef()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getUserinfo().getRef());
		}else
			sqlbuilder.append("NULL,");
		if(obj.getSubjectinfo().getSubjectid()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getSubjectinfo().getSubjectid()); 
		}else
			sqlbuilder.append("NULL,");
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<SubjectUser> getList(SubjectUser obj, PageResult presult) {
		StringBuilder sqlbuilder=new StringBuilder("{CALL subject_user_proc_search_split(");
		List<Object> objList=new ArrayList<Object>();
		if(obj==null)
			sqlbuilder.append("NULL,NULL,NULL,");
		else{
			if(obj.getSubjectinfo().getSubjectid()!=null){
				sqlbuilder.append("?,");
				objList.add(obj.getSubjectinfo().getSubjectid());
			}else
				sqlbuilder.append("NULL,");
			if(obj.getUserinfo().getRef()!=null){
				sqlbuilder.append("?,");
				objList.add(obj.getUserinfo().getRef());
			}else 
				sqlbuilder.append("NULL,");
            if(obj.getSubjectinfo().getSubjectname()!=null){
                sqlbuilder.append("?,");
                objList.add(obj.getSubjectinfo().getSubjectname());
            }else
                sqlbuilder.append("NULL,");
            if(obj.getIsmajor()!=null){
                sqlbuilder.append("?,");
                objList.add(obj.getIsmajor());
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
		List<SubjectUser> subjectUserList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, SubjectUser.class, objArray);
		if(presult!=null&&objArray[0]!=null&&objArray[0].toString().trim().length()>0)
			presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));				
		return subjectUserList;	 
	} 

	public List<Object> getSaveSql(SubjectUser obj, StringBuilder sqlbuilder) {
		if(obj==null||sqlbuilder==null)
			return null;
		sqlbuilder.append("{call subject_user_proc_add(");
		List<Object>objList = new ArrayList<Object>();
		if(obj.getUserinfo().getRef()!=null){ 
			sqlbuilder.append("?,");
			objList.add(obj.getUserinfo().getRef());
		}else
			sqlbuilder.append("NULL,");
		if(obj.getSubjectinfo().getSubjectid()!=null){ 
			sqlbuilder.append("?,");
			objList.add(obj.getSubjectinfo().getSubjectid());
		}else 
			sqlbuilder.append("NULL,"); 
		if(obj.getIsmajor()!=null){ 
			sqlbuilder.append("?,"); 
			objList.add(obj.getIsmajor());
		}else 
			sqlbuilder.append("NULL,");   
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getUpdateSql(SubjectUser obj, StringBuilder sqlbuilder) {
		if(obj==null||sqlbuilder==null)
			return null;
		sqlbuilder.append("{call role_proc_update(");
		List<Object>objList = new ArrayList<Object>();
//		if(obj.!=null){
//			sqlbuilder.append("?,");
//			objList.add(obj.);
//		}else
//			sqlbuilder.append("NULL,");
//		
//		sqlbuilder.append("?)}");
		return null;  
	}

}
