package com.school.dao.resource;

import com.school.dao.base.CommonDAO;
import com.school.dao.inter.resource.IStoreDAO;
import com.school.entity.resource.StoreInfo;
import com.school.util.PageResult;
import org.springframework.stereotype.Component;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

@Component  
public class StoreDAO extends CommonDAO<StoreInfo> implements IStoreDAO {

	public Boolean doSave(StoreInfo storeinfo) {
		if (storeinfo == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getSaveSql(storeinfo, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}
	
	public Boolean doDelete(StoreInfo storeinfo) {
		if(storeinfo==null)		
			return false;
		StringBuilder sqlbuilder=new StringBuilder();
		List<Object> objList=getDeleteSql(storeinfo, sqlbuilder);
		Object afficeObj=this.executeSacle_PROC(sqlbuilder.toString(), objList.toArray());
		if(afficeObj!=null&&afficeObj.toString().trim().length()>0&&Integer.parseInt(afficeObj.toString())>0){
			return true;
		}return false;
	}

	public Boolean doUpdate(StoreInfo storeinfo) {
		if (storeinfo == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getUpdateSql(storeinfo, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}
	
	public List<StoreInfo> getList(StoreInfo storeinfo, PageResult presult) {
		StringBuilder sqlbuilder = new StringBuilder();
		sqlbuilder.append("{CALL rs_store_info_proc_split(");
		List<Object> objList=new ArrayList<Object>();
		if (storeinfo.getRef() != null) {
			sqlbuilder.append("?,");
			objList.add(storeinfo.getRef());
		} else
			sqlbuilder.append("null,");
		if (storeinfo.getResid() != null) {
			sqlbuilder.append("?,");
			objList.add(storeinfo.getResid());
		} else
			sqlbuilder.append("null,");
		if (storeinfo.getUserid() != null) {
			sqlbuilder.append("?,");
			objList.add(storeinfo.getUserid());
		} else
			sqlbuilder.append("null,");
        if (storeinfo.getResourceInfo().getResname() != null) {
            sqlbuilder.append("?,");
            objList.add(storeinfo.getResourceInfo().getResname());
        } else
            sqlbuilder.append("null,");
        if (storeinfo.getResourceInfo().getSubject() != null) {
            sqlbuilder.append("?,");
            objList.add(storeinfo.getResourceInfo().getSubject());
        } else
            sqlbuilder.append("null,");
        if (storeinfo.getResourceInfo().getGrade() != null) {
            sqlbuilder.append("?,");
            objList.add(storeinfo.getResourceInfo().getGrade());
        } else
            sqlbuilder.append("null,");
        if (storeinfo.getResourceInfo().getRestype() != null) {
            sqlbuilder.append("?,");
            objList.add(storeinfo.getResourceInfo().getRestype());
        } else
            sqlbuilder.append("null,");
        if (storeinfo.getResourceInfo().getFiletype() != null) {
            sqlbuilder.append("?,");
            objList.add(storeinfo.getResourceInfo().getFiletype());
        } else
            sqlbuilder.append("null,");
        if (storeinfo.getSchoolname() != null) {
            sqlbuilder.append("?,");
            objList.add(storeinfo.getSchoolname());
        } else
            sqlbuilder.append("null,");
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
		List<StoreInfo> storeinfoList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, StoreInfo.class, objArray);
		if(presult!=null&&objArray[0]!=null&&objArray[0].toString().trim().length()>0)
			presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));				
		return storeinfoList;	
	}
	
	public List<Object> getSaveSql(StoreInfo storeinfo, StringBuilder sqlbuilder) {
		if(storeinfo==null || sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL rs_store_info_proc_add(");
		List<Object>objList = new ArrayList<Object>();
			
			if (storeinfo.getUserid() != null) {
				sqlbuilder.append("?,");
				objList.add(storeinfo.getUserid());
			} else
				sqlbuilder.append("null,");
			if (storeinfo.getRef() != null) {
				sqlbuilder.append("?,");
				objList.add(storeinfo.getRef());
			} else
				sqlbuilder.append("null,");
			if (storeinfo.getResid() != null) {
				sqlbuilder.append("?,");
				objList.add(storeinfo.getResid());
			} else
				sqlbuilder.append("null,");
		
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getDeleteSql(StoreInfo storeinfo, StringBuilder sqlbuilder) {
		if(storeinfo==null || sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL rs_store_info_proc_delete(");
		List<Object>objList = new ArrayList<Object>();
		if (storeinfo.getUserid() != null) {
			sqlbuilder.append("?,");
			objList.add(storeinfo.getUserid());
		} else
			sqlbuilder.append("null,");
		if (storeinfo.getRef() != null) {
			sqlbuilder.append("?,");
			objList.add(storeinfo.getRef());
		} else
			sqlbuilder.append("null,");
		if (storeinfo.getResid() != null) {
			sqlbuilder.append("?,");
			objList.add(storeinfo.getResid());
		} else
			sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getUpdateSql(StoreInfo storeinfo, StringBuilder sqlbuilder) {
		if(storeinfo==null||sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL rs_store_info_proc_update(");
		List<Object>objList = new ArrayList<Object>();
		if (storeinfo.getUserid() != null) {
			sqlbuilder.append("?,");
			objList.add(storeinfo.getUserid());
		} else
			sqlbuilder.append("null,");
		if (storeinfo.getRef() != null) {
			sqlbuilder.append("?,");
			objList.add(storeinfo.getRef());
		} else
			sqlbuilder.append("null,");
		if (storeinfo.getResid() != null) {
			sqlbuilder.append("?,");
			objList.add(storeinfo.getResid());
		} else
			sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList; 
	}

}
