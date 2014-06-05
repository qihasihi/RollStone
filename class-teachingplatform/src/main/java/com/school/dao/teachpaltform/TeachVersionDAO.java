package com.school.dao.teachpaltform;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.school.dao.base.CommonDAO;
import com.school.entity.teachpaltform.TeachVersionInfo;
import com.school.dao.inter.teachpaltform.ITeachVersionDAO;
import com.school.util.PageResult;

@Component  
public class TeachVersionDAO extends CommonDAO<TeachVersionInfo> implements ITeachVersionDAO {

	public Boolean doSave(TeachVersionInfo teachversioninfo) {
		if (teachversioninfo == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getSaveSql(teachversioninfo, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}
	
	public Boolean doDelete(TeachVersionInfo teachversioninfo) {
		if(teachversioninfo==null)		
			return false;
		StringBuilder sqlbuilder=new StringBuilder();
		List<Object> objList=getDeleteSql(teachversioninfo, sqlbuilder);
		Object afficeObj=this.executeSacle_PROC(sqlbuilder.toString(), objList.toArray());
		if(afficeObj!=null&&afficeObj.toString().trim().length()>0&&Integer.parseInt(afficeObj.toString())>0){
			return true;
		}return false;
	}

	public Boolean doUpdate(TeachVersionInfo teachversioninfo) {
		if (teachversioninfo == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getUpdateSql(teachversioninfo, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}
	
	public List<TeachVersionInfo> getList(TeachVersionInfo teachversioninfo, PageResult presult) {
		StringBuilder sqlbuilder = new StringBuilder();
		sqlbuilder.append("{CALL teach_version_info_proc_split(");
		List<Object> objList=new ArrayList<Object>();
		if (teachversioninfo.getCtime() != null) {
			sqlbuilder.append("?,");
			objList.add(teachversioninfo.getCtime());
		} else
			sqlbuilder.append("null,");
		if (teachversioninfo.getVersionname() != null) {
			sqlbuilder.append("?,");
			objList.add(teachversioninfo.getVersionname());
		} else
			sqlbuilder.append("null,");
		if (teachversioninfo.getVersionid() != null) {
			sqlbuilder.append("?,");
			objList.add(teachversioninfo.getVersionid());
		} else
			sqlbuilder.append("null,");
		if (teachversioninfo.getCuserid() != null) {
			sqlbuilder.append("?,");
			objList.add(teachversioninfo.getCuserid());
		} else
			sqlbuilder.append("null,");
		if (teachversioninfo.getRemark() != null) {
			sqlbuilder.append("?,");
			objList.add(teachversioninfo.getRemark());
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
		List<TeachVersionInfo> teachversioninfoList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, TeachVersionInfo.class, objArray);
		if(presult!=null&&objArray[0]!=null&&objArray[0].toString().trim().length()>0)
			presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));				
		return teachversioninfoList;	
	}
	
	public List<Object> getSaveSql(TeachVersionInfo teachversioninfo, StringBuilder sqlbuilder) {
		if(teachversioninfo==null || sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL teach_version_info_proc_add(");
		List<Object>objList = new ArrayList<Object>();
			if (teachversioninfo.getCtime() != null) {
				sqlbuilder.append("?,");
				objList.add(teachversioninfo.getCtime());
			} else
				sqlbuilder.append("null,");
			if (teachversioninfo.getVersionname() != null) {
				sqlbuilder.append("?,");
				objList.add(teachversioninfo.getVersionname());
			} else
				sqlbuilder.append("null,");
			if (teachversioninfo.getVersionid() != null) {
				sqlbuilder.append("?,");
				objList.add(teachversioninfo.getVersionid());
			} else
				sqlbuilder.append("null,");
			if (teachversioninfo.getCuserid() != null) {
				sqlbuilder.append("?,");
				objList.add(teachversioninfo.getCuserid());
			} else
				sqlbuilder.append("null,");
			if (teachversioninfo.getRemark() != null) {
				sqlbuilder.append("?,");
				objList.add(teachversioninfo.getRemark());
			} else
				sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getDeleteSql(TeachVersionInfo teachversioninfo, StringBuilder sqlbuilder) {
		if(teachversioninfo==null || sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL teach_version_info_proc_delete(");
		List<Object>objList = new ArrayList<Object>();
			if (teachversioninfo.getCtime() != null) {
				sqlbuilder.append("?,");
				objList.add(teachversioninfo.getCtime());
			} else
				sqlbuilder.append("null,");
			if (teachversioninfo.getVersionname() != null) {
				sqlbuilder.append("?,");
				objList.add(teachversioninfo.getVersionname());
			} else
				sqlbuilder.append("null,");
			if (teachversioninfo.getVersionid() != null) {
				sqlbuilder.append("?,");
				objList.add(teachversioninfo.getVersionid());
			} else
				sqlbuilder.append("null,");
			if (teachversioninfo.getCuserid() != null) {
				sqlbuilder.append("?,");
				objList.add(teachversioninfo.getCuserid());
			} else
				sqlbuilder.append("null,");
			if (teachversioninfo.getRemark() != null) {
				sqlbuilder.append("?,");
				objList.add(teachversioninfo.getRemark());
			} else
				sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getUpdateSql(TeachVersionInfo teachversioninfo, StringBuilder sqlbuilder) {
		if(teachversioninfo==null||sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL teach_version_info_proc_update(");
		List<Object>objList = new ArrayList<Object>();
			if (teachversioninfo.getCtime() != null) {
				sqlbuilder.append("?,");
				objList.add(teachversioninfo.getCtime());
			} else
				sqlbuilder.append("null,");
			if (teachversioninfo.getVersionname() != null) {
				sqlbuilder.append("?,");
				objList.add(teachversioninfo.getVersionname());
			} else
				sqlbuilder.append("null,");
			if (teachversioninfo.getVersionid() != null) {
				sqlbuilder.append("?,");
				objList.add(teachversioninfo.getVersionid());
			} else
				sqlbuilder.append("null,");
			if (teachversioninfo.getCuserid() != null) {
				sqlbuilder.append("?,");
				objList.add(teachversioninfo.getCuserid());
			} else
				sqlbuilder.append("null,");
			if (teachversioninfo.getRemark() != null) {
				sqlbuilder.append("?,");
				objList.add(teachversioninfo.getRemark());
			} else
				sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList; 
	}

	
	public String getNextId() {
		// TODO Auto-generated method stub
		return null;
	}

    public List<Object> getSynchroSql(TeachVersionInfo entity, StringBuilder sqlbuilder) {
        if(entity==null || sqlbuilder==null)
            return null;
        sqlbuilder.append("{CALL teach_version_info_proc_synchro(");
        List<Object>objList = new ArrayList<Object>();
        if (entity.getVersionname() != null) {
            sqlbuilder.append("?,");
            objList.add(entity.getVersionname());
        } else
            sqlbuilder.append("null,");
        if (entity.getVersionid() != null) {
            sqlbuilder.append("?,");
            objList.add(entity.getVersionid());
        } else
            sqlbuilder.append("null,");
        if (entity.getRemark() != null) {
            sqlbuilder.append("?,");
            objList.add(entity.getRemark());
        } else
            sqlbuilder.append("null,");
        if (entity.getCuserid() != null) {
            sqlbuilder.append("?,");
            objList.add(entity.getCuserid());
        } else
            sqlbuilder.append("null,");

        sqlbuilder.append("?)}");
        return objList;
    }
}
