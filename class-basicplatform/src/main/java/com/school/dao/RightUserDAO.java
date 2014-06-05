package  com.school.dao;


import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.school.dao.base.CommonDAO;
import com.school.dao.inter.IRightUserDAO;
import com.school.entity.RightUser;
import com.school.util.PageResult;

@Component  
public class RightUserDAO extends CommonDAO<RightUser> implements IRightUserDAO {

	public Boolean doDelete(RightUser obj) {
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

	public Boolean doSave(RightUser obj) {
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

	public Boolean doUpdate(RightUser obj) {
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

	public List<Object> getDeleteSql(RightUser obj, StringBuilder sqlbuilder) {
		if(obj==null||sqlbuilder==null)
			return null;
		sqlbuilder.append("{call j_right_user_proc_delete(");
		List<Object>objList = new ArrayList<Object>();
		if(obj.getUserinfo().getUserid()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getUserinfo().getUserid());
		}else
			sqlbuilder.append("NULL,");
		if(obj.getPagerightid()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getPagerightid());
		}else
			sqlbuilder.append("NULL,");
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<RightUser> getList(RightUser obj, PageResult presult) {
		StringBuilder sqlbuilder=new StringBuilder("{CALL rightuser_proc_search_split(");
		List<Object> objList=new ArrayList<Object>();
		if(obj==null)
			sqlbuilder.append("NULL,NULL,NULL,");
		else{
			if(obj.getUserinfo().getUserid()!=null){
				sqlbuilder.append("?,");
				objList.add(obj.getUserinfo().getUserid());
			}else
				sqlbuilder.append("NULL,");
			if(obj.getUserinfo().getUsername()!=null){
				sqlbuilder.append("?,");
				objList.add(obj.getUserinfo().getUsername());
			}else
				sqlbuilder.append("NULL,");
			if(obj.getPagerightid()!=null){
				sqlbuilder.append("?,");
				objList.add(obj.getPagerightid());
			}else
				sqlbuilder.append("NULL,");
			if(obj.getPagerightinfo().getPagerighttype()!=null){
				sqlbuilder.append("?,");
				objList.add(obj.getPagerightinfo().getPagerighttype());
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
		List<RightUser> roleList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, RightUser.class, objArray);
		if(presult!=null&&objArray[0]!=null&&objArray[0].toString().trim().length()>0)
			presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));				
		return roleList;	
	}


	public List<Object> getSaveSql(RightUser obj, StringBuilder sqlbuilder) {
		if(obj==null||sqlbuilder==null)
			return null;
		sqlbuilder.append("{call j_right_user_proc_add(");
		List<Object>objList = new ArrayList<Object>();
		if(obj.getUserinfo().getUserid()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getUserinfo().getUserid());
		}else
			sqlbuilder.append("NULL,");
		if(obj.getPagerightid()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getPagerightid()); 
		}else
			sqlbuilder.append("NULL,");
		sqlbuilder.append("?)}");
		return objList; 
	}

	public List<Object> getUpdateSql(RightUser obj, StringBuilder sqlbuilder) {
		if(obj==null||sqlbuilder==null)
			return null;
		sqlbuilder.append("{call role_proc_update(");
		List<Object>objList = new ArrayList<Object>();
		//Œ¥ π”√ 
		sqlbuilder.append("?)}");
		return null;  
	}

}
