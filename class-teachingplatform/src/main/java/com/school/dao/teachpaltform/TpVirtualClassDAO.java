package com.school.dao.teachpaltform;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.school.dao.base.CommonDAO;
import com.school.entity.teachpaltform.TpVirtualClassInfo;
import com.school.dao.inter.teachpaltform.ITpVirtualClassDAO;
import com.school.util.PageResult;

@Component  
public class TpVirtualClassDAO extends CommonDAO<TpVirtualClassInfo> implements ITpVirtualClassDAO {

	public Boolean doSave(TpVirtualClassInfo tpvirtualclassinfo) {
		if (tpvirtualclassinfo == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getSaveSql(tpvirtualclassinfo, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}
	
	public Boolean doDelete(TpVirtualClassInfo tpvirtualclassinfo) {
		if(tpvirtualclassinfo==null)		
			return false;
		StringBuilder sqlbuilder=new StringBuilder();
		List<Object> objList=getDeleteSql(tpvirtualclassinfo, sqlbuilder);
		Object afficeObj=this.executeSacle_PROC(sqlbuilder.toString(), objList.toArray());
		if(afficeObj!=null&&afficeObj.toString().trim().length()>0&&Integer.parseInt(afficeObj.toString())>0){
			return true;
		}return false;
	}

	public Boolean doUpdate(TpVirtualClassInfo tpvirtualclassinfo) {
		if (tpvirtualclassinfo == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getUpdateSql(tpvirtualclassinfo, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}
	
	public List<TpVirtualClassInfo> getList(TpVirtualClassInfo tpvirtualclassinfo, PageResult presult) {
		StringBuilder sqlbuilder = new StringBuilder();
		sqlbuilder.append("{CALL tp_virtual_class_info_proc_split(");
		List<Object> objList=new ArrayList<Object>();
		if (tpvirtualclassinfo.getVirtualclassid() != null) {
			sqlbuilder.append("?,");
			objList.add(tpvirtualclassinfo.getVirtualclassid());
		} else
			sqlbuilder.append("null,");
		if (tpvirtualclassinfo.getVirtualclassname() != null) {
			sqlbuilder.append("?,");
			objList.add(tpvirtualclassinfo.getVirtualclassname());
		} else
			sqlbuilder.append("null,");
		if (tpvirtualclassinfo.getCuserid() != null) {
			sqlbuilder.append("?,");
			objList.add(tpvirtualclassinfo.getCuserid());
		} else
			sqlbuilder.append("null,");
        if (tpvirtualclassinfo.getStatus() != null) {
            sqlbuilder.append("?,");
            objList.add(tpvirtualclassinfo.getStatus());
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
		List<TpVirtualClassInfo> tpvirtualclassinfoList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, TpVirtualClassInfo.class, objArray);
		if(presult!=null&&objArray[0]!=null&&objArray[0].toString().trim().length()>0)
			presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));				
		return tpvirtualclassinfoList;	
	}

    //教学系统教师班级管理—获取教学班和虚拟班级信息
    public List<Map<String,Object>> getListBytch(String userid,String year) {
        if(userid == null || year==null){
            return null;
        }
        StringBuilder sqlbuilder = new StringBuilder();
        sqlbuilder.append("{CALL tp_virtual_class_info_proc_split_bytch(");
        List<Object> objList=new ArrayList<Object>();
        objList.add(userid);
        objList.add(year);
        sqlbuilder.append("?,?,?)}");
        List<Map<String,Object>> tpvirtualclassinfoList=this.executeResultListMap_PROC(sqlbuilder.toString(), objList);
        return tpvirtualclassinfoList;
    }
	
	public List<Object> getSaveSql(TpVirtualClassInfo tpvirtualclassinfo, StringBuilder sqlbuilder) {
		if(tpvirtualclassinfo==null || sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL tp_virtual_class_info_proc_add(");
		List<Object>objList = new ArrayList<Object>();
        if (tpvirtualclassinfo.getVirtualclassname() != null) {
            sqlbuilder.append("?,");
            objList.add(tpvirtualclassinfo.getVirtualclassname());
        } else
            sqlbuilder.append("null,");
        if (tpvirtualclassinfo.getCuserid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpvirtualclassinfo.getCuserid());
        } else
            sqlbuilder.append("null,");
        sqlbuilder.append("?,");
        objList.add(tpvirtualclassinfo.getDcschoolid());
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getDeleteSql(TpVirtualClassInfo tpvirtualclassinfo, StringBuilder sqlbuilder) {
		if(tpvirtualclassinfo==null || sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL tp_virtual_class_info_proc_delete(");
		List<Object>objList = new ArrayList<Object>();
			if (tpvirtualclassinfo.getVirtualclassid() != null) {
				sqlbuilder.append("?,");
				objList.add(tpvirtualclassinfo.getVirtualclassid());
			} else
				sqlbuilder.append("null,");
			if (tpvirtualclassinfo.getCtime() != null) {
				sqlbuilder.append("?,");
				objList.add(tpvirtualclassinfo.getCtime());
			} else
				sqlbuilder.append("null,");
			if (tpvirtualclassinfo.getVirtualclassname() != null) {
				sqlbuilder.append("?,");
				objList.add(tpvirtualclassinfo.getVirtualclassname());
			} else
				sqlbuilder.append("null,");
			if (tpvirtualclassinfo.getCuserid() != null) {
				sqlbuilder.append("?,");
				objList.add(tpvirtualclassinfo.getCuserid());
			} else
				sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getUpdateSql(TpVirtualClassInfo tpvirtualclassinfo, StringBuilder sqlbuilder) {
		if(tpvirtualclassinfo==null||sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL tp_virtual_class_info_proc_update(");
		List<Object>objList = new ArrayList<Object>();
        if (tpvirtualclassinfo.getVirtualclassid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpvirtualclassinfo.getVirtualclassid());
        } else
            sqlbuilder.append("null,");
        if (tpvirtualclassinfo.getVirtualclassname() != null) {
            sqlbuilder.append("?,");
            objList.add(tpvirtualclassinfo.getVirtualclassname());
        } else
            sqlbuilder.append("null,");
        if (tpvirtualclassinfo.getCuserid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpvirtualclassinfo.getCuserid());
        } else
            sqlbuilder.append("null,");
        if (tpvirtualclassinfo.getStatus() != null) {
            sqlbuilder.append("?,");
            objList.add(tpvirtualclassinfo.getStatus());
        } else
            sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList; 
	}

	public Boolean doExcetueArrayProc(List<String> sqlArrayList,
			List<List<Object>> objArrayList) {
		return this.executeArray_SQL(sqlArrayList, objArrayList);
	}
	
	public String getNextId() {
		// TODO Auto-generated method stub
		return null;
	}

}
