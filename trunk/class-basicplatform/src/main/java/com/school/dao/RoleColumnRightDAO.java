package com.school.dao;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.school.dao.base.CommonDAO;
import com.school.entity.RoleColumnRightInfo;
import com.school.dao.inter.IRoleColumnRightDAO;
import com.school.util.PageResult;

@Component
public class RoleColumnRightDAO extends CommonDAO<RoleColumnRightInfo>
		implements IRoleColumnRightDAO {

	public Boolean doSave(RoleColumnRightInfo rolecolumnrightinfo) {
		if (rolecolumnrightinfo == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getSaveSql(rolecolumnrightinfo, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}

	public Boolean doDelete(RoleColumnRightInfo rolecolumnrightinfo) {
		if (rolecolumnrightinfo == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = getDeleteSql(rolecolumnrightinfo, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}

	public Boolean doUpdate(RoleColumnRightInfo rolecolumnrightinfo) {
		if (rolecolumnrightinfo == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getUpdateSql(rolecolumnrightinfo,
				sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}

	public List<RoleColumnRightInfo> getList(
			RoleColumnRightInfo rolecolumnrightinfo, PageResult presult) {
		StringBuilder sqlbuilder = new StringBuilder();
		sqlbuilder.append("{CALL j_role_columnright_info_proc_split(");
		List<Object> objList = new ArrayList<Object>();
		if (rolecolumnrightinfo.getMuserid() != null) {
			sqlbuilder.append("?,");
			objList.add(rolecolumnrightinfo.getMuserid());
		} else
			sqlbuilder.append("null,");
		if (rolecolumnrightinfo.getRoleid() != null) {
			sqlbuilder.append("?,");
			objList.add(rolecolumnrightinfo.getRoleid());
		} else
			sqlbuilder.append("null,");
		if (rolecolumnrightinfo.getRef() != null) {
			sqlbuilder.append("?,");
			objList.add(rolecolumnrightinfo.getRef());
		} else
			sqlbuilder.append("null,");
		if (rolecolumnrightinfo.getColumnrightid() != null) {
			sqlbuilder.append("?,");
			objList.add(rolecolumnrightinfo.getColumnrightid());
		} else
			sqlbuilder.append("null,");
		if (rolecolumnrightinfo.getRolecolumnrightid() != null) {
			sqlbuilder.append("?,");
			objList.add(rolecolumnrightinfo.getRolecolumnrightid());
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
		List<RoleColumnRightInfo> rolecolumnrightinfoList = this
				.executeResult_PROC(sqlbuilder.toString(), objList, types,
						RoleColumnRightInfo.class, objArray);
		if (presult != null && objArray[0] != null
				&& objArray[0].toString().trim().length() > 0)
			presult
					.setRecTotal(Integer
							.parseInt(objArray[0].toString().trim()));
		return rolecolumnrightinfoList;
	}

	public List<Object> getSaveSql(RoleColumnRightInfo rolecolumnrightinfo,
			StringBuilder sqlbuilder) {
		if (rolecolumnrightinfo == null || sqlbuilder == null)
			return null;
		sqlbuilder.append("{CALL j_role_columnright_info_proc_add(");
		List<Object> objList = new ArrayList<Object>();
		if (rolecolumnrightinfo.getMuserid() != null) {
			sqlbuilder.append("?,");
			objList.add(rolecolumnrightinfo.getMuserid());
		} else
			sqlbuilder.append("null,");
		if (rolecolumnrightinfo.getRoleid() != null) {
			sqlbuilder.append("?,");
			objList.add(rolecolumnrightinfo.getRoleid());
		} else
			sqlbuilder.append("null,");
		if (rolecolumnrightinfo.getRef() != null) {
			sqlbuilder.append("?,");
			objList.add(rolecolumnrightinfo.getRef());
		} else
			sqlbuilder.append("null,");
		if (rolecolumnrightinfo.getColumnrightid() != null) {
			sqlbuilder.append("?,");
			objList.add(rolecolumnrightinfo.getColumnrightid());
		} else
			sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getDeleteSql(RoleColumnRightInfo rolecolumnrightinfo,
			StringBuilder sqlbuilder) {
		if (rolecolumnrightinfo == null || sqlbuilder == null)
			return null;
		sqlbuilder.append("{CALL j_role_columnright_info_proc_delete(");
		List<Object> objList = new ArrayList<Object>();
		if (rolecolumnrightinfo.getMuserid() != null) {
			sqlbuilder.append("?,");
			objList.add(rolecolumnrightinfo.getMuserid());
		} else
			sqlbuilder.append("null,");
		if (rolecolumnrightinfo.getRoleid() != null) {
			sqlbuilder.append("?,");
			objList.add(rolecolumnrightinfo.getRoleid());
		} else
			sqlbuilder.append("null,");
		if (rolecolumnrightinfo.getRef() != null) {
			sqlbuilder.append("?,");
			objList.add(rolecolumnrightinfo.getRef());
		} else
			sqlbuilder.append("null,");
		if (rolecolumnrightinfo.getColumnrightid() != null) {
			sqlbuilder.append("?,");
			objList.add(rolecolumnrightinfo.getColumnrightid());
		} else
			sqlbuilder.append("null,");
		if (rolecolumnrightinfo.getRolecolumnrightid() != null) {
			sqlbuilder.append("?,");
			objList.add(rolecolumnrightinfo.getRolecolumnrightid());
		} else
			sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getUpdateSql(RoleColumnRightInfo rolecolumnrightinfo,
			StringBuilder sqlbuilder) {
		if (rolecolumnrightinfo == null || sqlbuilder == null)
			return null;
		sqlbuilder.append("{CALL j_role_columnright_info_proc_update(");
		List<Object> objList = new ArrayList<Object>();
		if (rolecolumnrightinfo.getMuserid() != null) {
			sqlbuilder.append("?,");
			objList.add(rolecolumnrightinfo.getMuserid());
		} else
			sqlbuilder.append("null,");
		if (rolecolumnrightinfo.getRoleid() != null) {
			sqlbuilder.append("?,");
			objList.add(rolecolumnrightinfo.getRoleid());
		} else
			sqlbuilder.append("null,");
		if (rolecolumnrightinfo.getRef() != null) {
			sqlbuilder.append("?,");
			objList.add(rolecolumnrightinfo.getRef());
		} else
			sqlbuilder.append("null,");
		if (rolecolumnrightinfo.getColumnrightid() != null) {
			sqlbuilder.append("?,");
			objList.add(rolecolumnrightinfo.getColumnrightid());
		} else
			sqlbuilder.append("null,");
		if (rolecolumnrightinfo.getRolecolumnrightid() != null) {
			sqlbuilder.append("?,");
			objList.add(rolecolumnrightinfo.getRolecolumnrightid());
		} else
			sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList;
	}
}
