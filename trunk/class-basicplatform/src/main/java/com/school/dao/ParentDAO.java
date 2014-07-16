package  com.school.dao;


import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.school.dao.base.CommonDAO;
import com.school.dao.inter.IParentDAO;
import com.school.entity.ParentInfo;
import com.school.util.PageResult;

@Component  
public class ParentDAO extends CommonDAO<ParentInfo> implements IParentDAO {

	public Boolean doDelete(ParentInfo obj) {
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
 
	public Boolean doSave(ParentInfo obj) {
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

	public Boolean doUpdate(ParentInfo obj) {
		if (obj == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getUpdateSql(obj, sqlbuilder);
		System.out.println(sqlbuilder.toString());
		System.out.println(objList.size());
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}

	public List<Object> getDeleteSql(ParentInfo obj, StringBuilder sqlbuilder) {
		if(obj==null||sqlbuilder==null)
			return null;
		sqlbuilder.append("{call parent_proc_delete(");
		List<Object>objList = new ArrayList<Object>();
		if(obj.getParentid()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getParentid());
		}else
			sqlbuilder.append("NULL,");
		if(obj.getParentname()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getParentname());
		}else
			sqlbuilder.append("NULL,");
		if(obj.getParentsex()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getParentsex());
		}else
			sqlbuilder.append("NULL,");
		if(obj.getUserid()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getUserid());
		}else
			sqlbuilder.append("NULL,");
		if(obj.getCusername()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getCusername());
		}else
			sqlbuilder.append("NULL,");
		sqlbuilder.append("?)}");
		return objList;
	} 

	public List<ParentInfo> getList(ParentInfo obj, PageResult presult) {
		StringBuilder sqlbuilder=new StringBuilder("{CALL parent_proc_search_split(");
		List<Object> objList=new ArrayList<Object>();
		if(obj==null)
			sqlbuilder.append("NULL,NULL,NULL,NULL,NULL,NULL,NULL,");
		else{ 
			if(obj.getParentid()!=null){
				sqlbuilder.append("?,"); 
				objList.add(obj.getParentid());
			}else
				sqlbuilder.append("NULL,");
			if(obj.getParentname()!=null){
				sqlbuilder.append("?,");
				objList.add(obj.getParentname());
			}else
				sqlbuilder.append("NULL,");
			if(obj.getParentsex()!=null){
				sqlbuilder.append("?,");
				objList.add(obj.getParentsex()); 
			}else
				sqlbuilder.append("NULL,");
			if(obj.getCusername()!=null){
				sqlbuilder.append("?,");
				objList.add(obj.getCusername());
			}else
				sqlbuilder.append("NULL,");
			if(obj.getUserinfo().getUserid()!=null){
				sqlbuilder.append("?,");
				objList.add(obj.getUserinfo().getUserid());
			}else
				sqlbuilder.append("NULL,");
			if(obj.getUserid()!=null){
				sqlbuilder.append("?,");
				objList.add(obj.getUserid());
			}else
				sqlbuilder.append("NULL,");
			if(obj.getUsername()!=null){
				sqlbuilder.append("?,");
				objList.add(obj.getUsername()); 
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
		List<ParentInfo> roleList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, ParentInfo.class, objArray);
		if(presult!=null&&objArray[0]!=null&&objArray[0].toString().trim().length()>0)
			presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));				
		return roleList;	
	}


	public List<Object> getSaveSql(ParentInfo obj, StringBuilder sqlbuilder) {
		if(obj==null||sqlbuilder==null)
			return null;
		sqlbuilder.append("{call parent_proc_add(");
		List<Object>objList = new ArrayList<Object>();
		if(obj.getRef()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getRef());
		}else
			sqlbuilder.append("NULL,");
		if(obj.getParentname()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getParentname());
		}else
			sqlbuilder.append("NULL,");
		if(obj.getParentsex()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getParentsex());
		}else
			sqlbuilder.append("NULL,");
		if(obj.getParentphone()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getParentphone());
		}else
			sqlbuilder.append("NULL,");
		if(obj.getUserid()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getUserid());
		}else
			sqlbuilder.append("NULL,");
		if(obj.getCusername()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getCusername());
		}else
			sqlbuilder.append("NULL,");
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getUpdateSql(ParentInfo obj, StringBuilder sqlbuilder) {
		if(obj==null||sqlbuilder==null)
			return null;
		sqlbuilder.append("{call parent_proc_update(");
		List<Object>objList = new ArrayList<Object>();
		if(obj.getRef()!=null){
			sqlbuilder.append("?,"); 
			objList.add(obj.getRef());
		}else
			sqlbuilder.append("NULL,");
		if(obj.getParentid()!=null){
			sqlbuilder.append("?,"); 
			objList.add(obj.getParentid());
		}else
			sqlbuilder.append("NULL,");
		if(obj.getParentname()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getParentname());
		}else
			sqlbuilder.append("NULL,");
		if(obj.getParentsex()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getParentsex()); 
		}else
			sqlbuilder.append("NULL,");
		if(obj.getParentphone()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getParentphone()); 
		}else
			sqlbuilder.append("NULL,"); 
		if(obj.getUserid()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getUserid());
		}else 
			sqlbuilder.append("NULL,");
		if(obj.getCusername()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getCusername()); 
		}else 
			sqlbuilder.append("NULL,"); 
		sqlbuilder.append("?)}"); 
		return objList; 
	}

}
