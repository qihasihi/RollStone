package com.school.dao.resource;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.school.dao.base.CommonDAO;
import com.school.entity.resource.AccessInfo;
import com.school.dao.inter.resource.IAccessDAO;
import com.school.util.PageResult;

@Component
public class AccessDAO extends CommonDAO<AccessInfo> implements IAccessDAO {

	public Boolean doSave(AccessInfo accessinfo) {
		if (accessinfo == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getSaveSql(accessinfo, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}
	
	public Boolean doSaveOrUpdate(AccessInfo accessinfo) {
		if (accessinfo == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getSaveOrUpdateSql(accessinfo, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}

	public Boolean doDelete(AccessInfo accessinfo) {
		if (accessinfo == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = getDeleteSql(accessinfo, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}

	public Boolean doUpdate(AccessInfo accessinfo) {
		if (accessinfo == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getUpdateSql(accessinfo, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}

	public List<AccessInfo> getList(AccessInfo accessinfo, PageResult presult) {
		StringBuilder sqlbuilder = new StringBuilder();
		sqlbuilder.append("{CALL rs_access_info_proc_split(");
		List<Object> objList = new ArrayList<Object>();
		if (accessinfo.getRef() != null) {
			sqlbuilder.append("?,");
			objList.add(accessinfo.getRef());
		} else
			sqlbuilder.append("null,");
		if (accessinfo.getResid() != null) {
			sqlbuilder.append("?,");
			objList.add(accessinfo.getResid());
		} else
			sqlbuilder.append("null,");
		if (accessinfo.getUserid() != null) {
			sqlbuilder.append("?,");
			objList.add(accessinfo.getUserid());
		} else
			sqlbuilder.append("null,");
		if (accessinfo.getEnable() != null) {
			sqlbuilder.append("?,");
			objList.add(accessinfo.getEnable());
		} else
			sqlbuilder.append("null,");
		if (presult != null && presult.getPageNo() > 0
				&& presult.getPageSize() > 0) {
			sqlbuilder.append("?,?,");
			objList.add(presult.getPageNo());
			objList.add(presult.getPageSize());
		} else {
			sqlbuilder.append("NULL,NULL,");
		}
		if (presult != null && presult.getOrderBy() != null
				&& presult.getOrderBy().trim().length() > 0) {
			sqlbuilder.append("?,");
			objList.add(presult.getOrderBy());
		} else {
			sqlbuilder.append("NULL,");
		}
		sqlbuilder.append("?)}");
		List<Integer> types = new ArrayList<Integer>();
		types.add(Types.INTEGER);
		Object[] objArray = new Object[1];
		List<AccessInfo> accessinfoList = this.executeResult_PROC(
				sqlbuilder.toString(), objList, types, AccessInfo.class,
				objArray);
		if (presult != null && objArray[0] != null
				&& objArray[0].toString().trim().length() > 0)
			presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));
		return accessinfoList;
	}

	public List<Object> getSaveSql(AccessInfo accessinfo,
			StringBuilder sqlbuilder) {
		if (accessinfo == null || sqlbuilder == null)
			return null;
		sqlbuilder.append("{CALL rs_access_info_proc_add(");
		List<Object> objList = new ArrayList<Object>();
		if (accessinfo.getRef() != null) {
			sqlbuilder.append("?,");
			objList.add(accessinfo.getRef());
		} else
			sqlbuilder.append("null,");
		if (accessinfo.getResid() != null) {
			sqlbuilder.append("?,");
			objList.add(accessinfo.getResid());
		} else
			sqlbuilder.append("null,");
		if (accessinfo.getUserid() != null) {
			sqlbuilder.append("?,");
			objList.add(accessinfo.getUserid());
		} else
			sqlbuilder.append("null,");
        if (accessinfo.getEnable() != null) {
            sqlbuilder.append("?,");
            objList.add(accessinfo.getEnable());
        } else
            sqlbuilder.append("null,");
        if (accessinfo.getIsFromRank() != null) {
            sqlbuilder.append("?,");
            objList.add(accessinfo.getIsFromRank());
        } else
            sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList;
	}
	
	public List<Object> getSaveOrUpdateSql(AccessInfo accessinfo,
			StringBuilder sqlbuilder) {
		if (accessinfo == null || sqlbuilder == null)
			return null;
		sqlbuilder.append("{CALL rs_access_info_proc_addorupdate(");
		List<Object> objList = new ArrayList<Object>();
		if (accessinfo.getRef() != null) {
			sqlbuilder.append("?,");
			objList.add(accessinfo.getRef());
		} else
			sqlbuilder.append("null,");
		if (accessinfo.getResid() != null) {
			sqlbuilder.append("?,");
			objList.add(accessinfo.getResid());
		} else
			sqlbuilder.append("null,");
		if (accessinfo.getUserid() != null) {
			sqlbuilder.append("?,");
			objList.add(accessinfo.getUserid());
		} else
			sqlbuilder.append("null,");
        if (accessinfo.getEnable() != null) {
            sqlbuilder.append("?,");
            objList.add(accessinfo.getEnable());
        } else
            sqlbuilder.append("null,");
        if (accessinfo.getIsFromRank() != null) {
            sqlbuilder.append("?,");
            objList.add(accessinfo.getIsFromRank());
        } else
            sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getDeleteSql(AccessInfo accessinfo,
			StringBuilder sqlbuilder) {
		if (accessinfo == null || sqlbuilder == null)
			return null;
		sqlbuilder.append("{CALL rs_access_info_proc_delete(");
		List<Object> objList = new ArrayList<Object>();
		if (accessinfo.getUserid() != null) {
			sqlbuilder.append("?,");
			objList.add(accessinfo.getUserid());
		} else
			sqlbuilder.append("null,");
		if (accessinfo.getEnable() != null) {
			sqlbuilder.append("?,");
			objList.add(accessinfo.getEnable());
		} else
			sqlbuilder.append("null,");

		if (accessinfo.getRef() != null) {
			sqlbuilder.append("?,");
			objList.add(accessinfo.getRef());
		} else
			sqlbuilder.append("null,");
		if (accessinfo.getResid() != null) {
			sqlbuilder.append("?,");
			objList.add(accessinfo.getResid());
		} else
			sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getUpdateSql(AccessInfo accessinfo,
			StringBuilder sqlbuilder) {
		if (accessinfo == null || sqlbuilder == null)
			return null;
		sqlbuilder.append("{CALL rs_access_info_proc_update(");
		List<Object> objList = new ArrayList<Object>();
		if (accessinfo.getUserid() != null) {
			sqlbuilder.append("?,");
			objList.add(accessinfo.getUserid());
		} else
			sqlbuilder.append("null,");
		if (accessinfo.getEnable() != null) {
			sqlbuilder.append("?,");
			objList.add(accessinfo.getEnable());
		} else
			sqlbuilder.append("null,");

		if (accessinfo.getRef() != null) {
			sqlbuilder.append("?,");
			objList.add(accessinfo.getRef());
		} else
			sqlbuilder.append("null,");
		if (accessinfo.getResid() != null) {
			sqlbuilder.append("?,");
			objList.add(accessinfo.getResid());
		} else
			sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList;
	}

}
