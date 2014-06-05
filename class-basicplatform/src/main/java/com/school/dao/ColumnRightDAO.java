package com.school.dao;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.school.dao.base.CommonDAO;
import com.school.entity.ColumnRightInfo;
import com.school.dao.inter.IColumnRightDAO;
import com.school.util.PageResult;

@Component
public class ColumnRightDAO extends CommonDAO<ColumnRightInfo> implements
		IColumnRightDAO {

	public Boolean doSave(ColumnRightInfo columnrightinfo) {
		if (columnrightinfo == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getSaveSql(columnrightinfo, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}

	public Boolean doDelete(ColumnRightInfo columnrightinfo) {
		if (columnrightinfo == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = getDeleteSql(columnrightinfo, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}

	public Boolean doUpdate(ColumnRightInfo columnrightinfo) {
		if (columnrightinfo == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getUpdateSql(columnrightinfo, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}

	public List<ColumnRightInfo> getList(ColumnRightInfo columnrightinfo,
			PageResult presult) {
		StringBuilder sqlbuilder = new StringBuilder();
		sqlbuilder.append("{CALL columnright_info_proc_split(");
		List<Object> objList = new ArrayList<Object>();

		if (columnrightinfo.getMuserid() != null) {
			sqlbuilder.append("?,");
			objList.add(columnrightinfo.getMuserid());
		} else
			sqlbuilder.append("null,");
		if (columnrightinfo.getColumnid() != null) {
			sqlbuilder.append("?,");
			objList.add(columnrightinfo.getColumnid());
		} else
			sqlbuilder.append("null,");
		if (columnrightinfo.getColumnrightname() != null) {
			sqlbuilder.append("?,");
			objList.add(columnrightinfo.getColumnrightname());
		} else
			sqlbuilder.append("null,");

		if (columnrightinfo.getRef() != null) {
			sqlbuilder.append("?,");
			objList.add(columnrightinfo.getRef());
		} else
			sqlbuilder.append("null,");

		if (columnrightinfo.getColumnrightid() != null) {
			sqlbuilder.append("?,");
			objList.add(columnrightinfo.getColumnrightid());
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
		List<ColumnRightInfo> columnrightinfoList = this.executeResult_PROC(
				sqlbuilder.toString(), objList, types, ColumnRightInfo.class,
				objArray);
		if (presult != null && objArray[0] != null
				&& objArray[0].toString().trim().length() > 0)
			presult
					.setRecTotal(Integer
							.parseInt(objArray[0].toString().trim()));
		return columnrightinfoList;
	}

	public List<Object> getSaveSql(ColumnRightInfo columnrightinfo,
			StringBuilder sqlbuilder) {
		if (columnrightinfo == null || sqlbuilder == null)
			return null;
		sqlbuilder.append("{CALL columnright_info_proc_add(");
		List<Object> objList = new ArrayList<Object>();

		if (columnrightinfo.getMuserid() != null) {
			sqlbuilder.append("?,");
			objList.add(columnrightinfo.getMuserid());
		} else
			sqlbuilder.append("null,");
		if (columnrightinfo.getColumnid() != null) {
			sqlbuilder.append("?,");
			objList.add(columnrightinfo.getColumnid());
		} else
			sqlbuilder.append("null,");
		if (columnrightinfo.getColumnrightname() != null) {
			sqlbuilder.append("?,");
			objList.add(columnrightinfo.getColumnrightname());
		} else
			sqlbuilder.append("null,");

		if (columnrightinfo.getRef() != null) {
			sqlbuilder.append("?,");
			objList.add(columnrightinfo.getRef());
		} else
			sqlbuilder.append("null,");

		if (columnrightinfo.getColumnrightid() != null) {
			sqlbuilder.append("?,");
			objList.add(columnrightinfo.getColumnrightid());
		} else
			sqlbuilder.append("null,");

		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getDeleteSql(ColumnRightInfo columnrightinfo,
			StringBuilder sqlbuilder) {
		if (columnrightinfo == null || sqlbuilder == null)
			return null;
		sqlbuilder.append("{CALL columnright_info_proc_delete(");
		List<Object> objList = new ArrayList<Object>();

		if (columnrightinfo.getMuserid() != null) {
			sqlbuilder.append("?,");
			objList.add(columnrightinfo.getMuserid());
		} else
			sqlbuilder.append("null,");
		if (columnrightinfo.getColumnid() != null) {
			sqlbuilder.append("?,");
			objList.add(columnrightinfo.getColumnid());
		} else
			sqlbuilder.append("null,");
		if (columnrightinfo.getColumnrightname() != null) {
			sqlbuilder.append("?,");
			objList.add(columnrightinfo.getColumnrightname());
		} else
			sqlbuilder.append("null,");

		if (columnrightinfo.getRef() != null) {
			sqlbuilder.append("?,");
			objList.add(columnrightinfo.getRef());
		} else
			sqlbuilder.append("null,");

		if (columnrightinfo.getColumnrightid() != null) {
			sqlbuilder.append("?,");
			objList.add(columnrightinfo.getColumnrightid());
		} else
			sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getUpdateSql(ColumnRightInfo columnrightinfo,
			StringBuilder sqlbuilder) {
		if (columnrightinfo == null || sqlbuilder == null)
			return null;
		sqlbuilder.append("{CALL columnright_info_proc_update(");
		List<Object> objList = new ArrayList<Object>();

		if (columnrightinfo.getMuserid() != null) {
			sqlbuilder.append("?,");
			objList.add(columnrightinfo.getMuserid());
		} else
			sqlbuilder.append("null,");
		if (columnrightinfo.getColumnid() != null) {
			sqlbuilder.append("?,");
			objList.add(columnrightinfo.getColumnid());
		} else
			sqlbuilder.append("null,");
		if (columnrightinfo.getColumnrightname() != null) {
			sqlbuilder.append("?,");
			objList.add(columnrightinfo.getColumnrightname());
		} else
			sqlbuilder.append("null,");

		if (columnrightinfo.getRef() != null) {
			sqlbuilder.append("?,");
			objList.add(columnrightinfo.getRef());
		} else
			sqlbuilder.append("null,");

		if (columnrightinfo.getColumnrightid() != null) {
			sqlbuilder.append("?,");
			objList.add(columnrightinfo.getColumnrightid());
		} else
			sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList;
	}
	
	
}
