package com.school.dao;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.school.dao.base.CommonDAO;
import com.school.entity.IdentityInfo;
import com.school.dao.inter.IIdentityDAO;
import com.school.util.PageResult;

@Component
public class IdentityDAO extends CommonDAO<IdentityInfo> implements
		IIdentityDAO {

	public Boolean doSave(IdentityInfo identityinfo) {
		if (identityinfo == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getSaveSql(identityinfo, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}

	public Boolean doDelete(IdentityInfo identityinfo) {
		if (identityinfo == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = getDeleteSql(identityinfo, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}

	public Boolean doUpdate(IdentityInfo identityinfo) {
		if (identityinfo == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getUpdateSql(identityinfo, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}

	public List<IdentityInfo> getList(IdentityInfo identityinfo,
			PageResult presult) {
		StringBuilder sqlbuilder = new StringBuilder();
		sqlbuilder.append("{CALL identity_info_proc_split(");
		List<Object> objList = new ArrayList<Object>();
		if (identityinfo.getMuserid() != null) {
			sqlbuilder.append("?,");
			objList.add(identityinfo.getMuserid());
		} else
			sqlbuilder.append("null,");
		if (identityinfo.getRoleid() != null) {
			sqlbuilder.append("?,");
			objList.add(identityinfo.getRoleid());
		} else
			sqlbuilder.append("null,");
		if (identityinfo.getIdentityname() != null) {
			sqlbuilder.append("?,");
			objList.add(identityinfo.getIdentityname());
		} else
			sqlbuilder.append("null,");

		if (identityinfo.getRef() != null) {
			sqlbuilder.append("?,");
			objList.add(identityinfo.getRef());
		} else
			sqlbuilder.append("null,");
		if (identityinfo.getIsadmin()!= null) {
			sqlbuilder.append("?,");
			objList.add(identityinfo.getIsadmin());
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
		List<IdentityInfo> identityinfoList = this.executeResult_PROC(
				sqlbuilder.toString(), objList, types, IdentityInfo.class,
				objArray);
		if (presult != null && objArray[0] != null
				&& objArray[0].toString().trim().length() > 0)
			presult
					.setRecTotal(Integer
							.parseInt(objArray[0].toString().trim()));
		return identityinfoList;
	}

	public List<Object> getSaveSql(IdentityInfo identityinfo,
			StringBuilder sqlbuilder) {
		if (identityinfo == null || sqlbuilder == null)
			return null;
		sqlbuilder.append("{CALL identity_info_proc_add(");
		List<Object> objList = new ArrayList<Object>();
		if (identityinfo.getMuserid() != null) {
			sqlbuilder.append("?,");
			objList.add(identityinfo.getMuserid());
		} else
			sqlbuilder.append("null,");
		if (identityinfo.getRoleid() != null) {
			sqlbuilder.append("?,");
			objList.add(identityinfo.getRoleid());
		} else
			sqlbuilder.append("null,");
		if (identityinfo.getIdentityname() != null) {
			sqlbuilder.append("?,");
			objList.add(identityinfo.getIdentityname());
		} else
			sqlbuilder.append("null,");

		if (identityinfo.getRef() != null) {
			sqlbuilder.append("?,");
			objList.add(identityinfo.getRef());
		} else
			sqlbuilder.append("null,");

		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getDeleteSql(IdentityInfo identityinfo,
			StringBuilder sqlbuilder) {
		if (identityinfo == null || sqlbuilder == null)
			return null;
		sqlbuilder.append("{CALL identity_info_proc_delete(");
		List<Object> objList = new ArrayList<Object>();
		if (identityinfo.getMuserid() != null) {
			sqlbuilder.append("?,");
			objList.add(identityinfo.getMuserid());
		} else
			sqlbuilder.append("null,");
		if (identityinfo.getRoleid() != null) {
			sqlbuilder.append("?,");
			objList.add(identityinfo.getRoleid());
		} else
			sqlbuilder.append("null,");
		if (identityinfo.getIdentityname() != null) {
			sqlbuilder.append("?,");
			objList.add(identityinfo.getIdentityname());
		} else
			sqlbuilder.append("null,");

		if (identityinfo.getRef() != null) {
			sqlbuilder.append("?,");
			objList.add(identityinfo.getRef());
		} else
			sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getUpdateSql(IdentityInfo identityinfo,
			StringBuilder sqlbuilder) {
		if (identityinfo == null || sqlbuilder == null)
			return null;
		sqlbuilder.append("{CALL identity_info_proc_update(");
		List<Object> objList = new ArrayList<Object>();
		if (identityinfo.getMuserid() != null) {
			sqlbuilder.append("?,");
			objList.add(identityinfo.getMuserid());
		} else
			sqlbuilder.append("null,");
		if (identityinfo.getRoleid() != null) {
			sqlbuilder.append("?,");
			objList.add(identityinfo.getRoleid());
		} else
			sqlbuilder.append("null,");
		if (identityinfo.getIdentityname() != null) {
			sqlbuilder.append("?,");
			objList.add(identityinfo.getIdentityname());
		} else
			sqlbuilder.append("null,");

		if (identityinfo.getRef() != null) {
			sqlbuilder.append("?,");
			objList.add(identityinfo.getRef());
		} else
			sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList;
	}
}
