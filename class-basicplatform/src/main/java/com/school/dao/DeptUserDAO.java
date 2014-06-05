package  com.school.dao;


import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.school.dao.base.CommonDAO;
import com.school.dao.inter.IDeptUserDAO;
import com.school.entity.DeptUser;
import com.school.util.PageResult;

@Component  
public class DeptUserDAO extends CommonDAO<DeptUser> implements IDeptUserDAO {

	public Boolean doDelete(DeptUser obj) {
		StringBuilder sqlbuilder=new StringBuilder();
		List<Object> objList=getDeleteSql(obj, sqlbuilder);
		Object afficeObj=this.executeSacle_PROC(sqlbuilder.toString(), objList.toArray());
		if(afficeObj!=null&&afficeObj.toString().trim().length()>0&&Integer.parseInt(afficeObj.toString())>0){
			return true;
		}return false;
	}


 
	public Boolean doSave(DeptUser obj) {
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

	public Boolean doUpdate(DeptUser obj) {
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

	public List<Object> getDeleteSql(DeptUser obj, StringBuilder sqlbuilder) {
		if(obj==null||sqlbuilder==null)
			return null;
		sqlbuilder.append("{call j_dept_user_proc_delete(");
		List<Object>objList = new ArrayList<Object>();
		if(obj.getRef()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getRef());
		}else
			sqlbuilder.append("NULL,");
	
		if(obj.getDeptinfo().getDeptid()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getDeptinfo().getDeptid()); 
		}else
			sqlbuilder.append("NULL,"); 
		if(obj.getRoleid()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getRoleid());
		}else 
			sqlbuilder.append("NULL,");
			
		if(obj.getUserinfo().getRef()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getUserinfo().getRef());
		}else 
			sqlbuilder.append("NULL,");
		sqlbuilder.append("?)}"); 
		return objList;
	}

	public List<DeptUser> getList(DeptUser obj, PageResult presult) {
		StringBuilder sqlbuilder=new StringBuilder("{CALL j_dept_user_proc_split("); 
		List<Object> objList=new ArrayList<Object>();
		if(obj==null)
			sqlbuilder.append("NULL,NULL,NULL,NULL,");
		else{ 
			if(obj.getRef()!=null){
				sqlbuilder.append("?,");
				objList.add(obj.getRef());
			}else
				sqlbuilder.append("NULL,");
		
			if(obj.getDeptinfo().getDeptid()!=null){
				sqlbuilder.append("?,");
				objList.add(obj.getDeptinfo().getDeptid()); 
			}else
				sqlbuilder.append("NULL,"); 
			if(obj.getRoleid()!=null){
				sqlbuilder.append("?,");
				objList.add(obj.getRoleid());
			}else 
				sqlbuilder.append("NULL,");
				
			if(obj.getUserinfo().getUserid()!=null){
				sqlbuilder.append("?,");
				objList.add(obj.getUserinfo().getUserid());
			}else 
				sqlbuilder.append("NULL,");
			if(obj.getUserref()!=null){
				sqlbuilder.append("?,");
				objList.add(obj.getUserref());
			}else 
				sqlbuilder.append("NULL,");
			if(obj.getDeptinfo().getTypeid()!=null){
				sqlbuilder.append("?,");
				objList.add(obj.getDeptinfo().getTypeid());
			}else 
				sqlbuilder.append("NULL,");	
			if(obj.getOtheruserref()!=null){
				sqlbuilder.append("?,");
				objList.add(obj.getOtheruserref());
			}else
				sqlbuilder.append("NULL,");
			if(obj.getRoleflag()!=null){
				sqlbuilder.append("?,");
				objList.add(obj.getRoleflag());
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
		List<DeptUser> deptUserList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, DeptUser.class, objArray);
		if(presult!=null&&objArray[0]!=null&&objArray[0].toString().trim().length()>0)
			presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));				
		return deptUserList; 	
	}

	public List<Object> getSaveSql(DeptUser obj, StringBuilder sqlbuilder) {
		if(obj==null||sqlbuilder==null)
			return null;
		sqlbuilder.append("{call j_dept_user_proc_add("); 
		List<Object>objList = new ArrayList<Object>();
		if(obj.getRef()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getRef());
		}else
			sqlbuilder.append("NULL,");
	
		if(obj.getDeptinfo().getDeptid()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getDeptinfo().getDeptid()); 
		}else
			sqlbuilder.append("NULL,"); 
		if(obj.getRoleid()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getRoleid());
		}else 
			sqlbuilder.append("NULL,");
			
		if(obj.getUserref()!=null){
			sqlbuilder.append("?,");  
			objList.add(obj.getUserref());
		}else 
			sqlbuilder.append("NULL,");
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getUpdateSql(DeptUser obj, StringBuilder sqlbuilder) {
		if(obj==null||sqlbuilder==null)
			return null;
		sqlbuilder.append("{call j_dept_user_proc_update("); 
		List<Object>objList = new ArrayList<Object>();
		if(obj.getRef()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getRef());
		}else
			sqlbuilder.append("NULL,");
	
		if(obj.getDeptinfo().getDeptid()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getDeptinfo().getDeptid()); 
		}else
			sqlbuilder.append("NULL,"); 
		if(obj.getRoleid()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getRoleid());
		}else 
			sqlbuilder.append("NULL,");
			
		if(obj.getUserref()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getUserref());
		}else 
			sqlbuilder.append("NULL,");
		if(obj.getDeptinfo().getTypeid()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getDeptinfo().getTypeid());
		}else   
			sqlbuilder.append("NULL,");
		sqlbuilder.append("?)}");
		return objList;
	}

    @Override
    public List<Object> getUpdateSqlLoyal(DeptUser obj, StringBuilder sqlbuilder) {
        if(obj==null||sqlbuilder==null)
            return null;
        sqlbuilder.append("{call j_dept_user_proc_update_loyal(");
        List<Object>objList = new ArrayList<Object>();

        if(obj.getDeptinfo().getDeptid()!=null){
            sqlbuilder.append("?,");
            objList.add(obj.getDeptinfo().getDeptid());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getRoleid()!=null){
            sqlbuilder.append("?,");
            objList.add(obj.getRoleid());
        }else
            sqlbuilder.append("NULL,");
        sqlbuilder.append("?)}");
        return objList;
    }
}
