package com.school.dao;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.school.dao.base.CommonDAO;
import com.school.entity.ColumnRightPageRightInfo;
import com.school.dao.inter.IColumnRightPageRightDAO;
import com.school.util.PageResult;

@Component
public class ColumnRightPageRightDAO extends
		CommonDAO<ColumnRightPageRightInfo> implements IColumnRightPageRightDAO {

	public Boolean doSave(ColumnRightPageRightInfo columnrightpagerightinfo) {
		if (columnrightpagerightinfo == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getSaveSql(columnrightpagerightinfo,
				sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}

	public Boolean doDelete(ColumnRightPageRightInfo columnrightpagerightinfo) {
		if (columnrightpagerightinfo == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = getDeleteSql(columnrightpagerightinfo,
				sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}

	public Boolean doUpdate(ColumnRightPageRightInfo columnrightpagerightinfo) {
		if (columnrightpagerightinfo == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getUpdateSql(columnrightpagerightinfo,
				sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}

	public List<ColumnRightPageRightInfo> getList(
			ColumnRightPageRightInfo columnrightpagerightinfo,
			PageResult presult) {
		StringBuilder sqlbuilder = new StringBuilder();
		sqlbuilder.append("{CALL j_columnright_pageright_info_proc_split(");
		List<Object> objList = new ArrayList<Object>();
		if (columnrightpagerightinfo.getMuserid() != null) {
			sqlbuilder.append("?,");
			objList.add(columnrightpagerightinfo.getMuserid());
		} else
			sqlbuilder.append("null,");
		if (columnrightpagerightinfo.getColumnid() != null) {
			sqlbuilder.append("?,");
			objList.add(columnrightpagerightinfo.getColumnid());
		} else
			sqlbuilder.append("null,");
		if (columnrightpagerightinfo.getRef() != null) {
			sqlbuilder.append("?,");
			objList.add(columnrightpagerightinfo.getRef());
		} else
			sqlbuilder.append("null,");
		if (columnrightpagerightinfo.getPagerightid() != null) {
			sqlbuilder.append("?,");
			objList.add(columnrightpagerightinfo.getPagerightid());
		} else
			sqlbuilder.append("null,");
		if (columnrightpagerightinfo.getPagevalue() != null) {
			sqlbuilder.append("?,");
			objList.add(columnrightpagerightinfo.getPagevalue());
		} else
			sqlbuilder.append("null,");
		if (columnrightpagerightinfo.getColumnrightid() != null) {
			sqlbuilder.append("?,");
			objList.add(columnrightpagerightinfo.getColumnrightid());
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
		List<ColumnRightPageRightInfo> columnrightpagerightinfoList = this
				.executeResult_PROC(sqlbuilder.toString(), objList, types,
						ColumnRightPageRightInfo.class, objArray);
		if (presult != null && objArray[0] != null
				&& objArray[0].toString().trim().length() > 0)
			presult
					.setRecTotal(Integer
							.parseInt(objArray[0].toString().trim()));
		return columnrightpagerightinfoList;
	}

	public List<Object> getSaveSql(
			ColumnRightPageRightInfo columnrightpagerightinfo,
			StringBuilder sqlbuilder) {
		if (columnrightpagerightinfo == null || sqlbuilder == null)
			return null;
		sqlbuilder.append("{CALL j_columnright_pageright_info_proc_add(");
		List<Object> objList = new ArrayList<Object>();
		if (columnrightpagerightinfo.getMuserid() != null) {
			sqlbuilder.append("?,");
			objList.add(columnrightpagerightinfo.getMuserid());
		} else
			sqlbuilder.append("null,");
		if (columnrightpagerightinfo.getColumnid() != null) {
			sqlbuilder.append("?,");
			objList.add(columnrightpagerightinfo.getColumnid());
		} else
			sqlbuilder.append("null,");
		if (columnrightpagerightinfo.getRef() != null) {
			sqlbuilder.append("?,");
			objList.add(columnrightpagerightinfo.getRef());
		} else
			sqlbuilder.append("null,");
		if (columnrightpagerightinfo.getPagerightid() != null) {
			sqlbuilder.append("?,");
			objList.add(columnrightpagerightinfo.getPagerightid());
		} else
			sqlbuilder.append("null,");
		if (columnrightpagerightinfo.getColumnrightid() != null) {
			sqlbuilder.append("?,");
			objList.add(columnrightpagerightinfo.getColumnrightid());
		} else
			sqlbuilder.append("null,");

		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getDeleteSql(
			ColumnRightPageRightInfo columnrightpagerightinfo,
			StringBuilder sqlbuilder) {
		if (columnrightpagerightinfo == null || sqlbuilder == null)
			return null;
		sqlbuilder.append("{CALL j_columnright_pageright_info_proc_delete(");
		List<Object> objList = new ArrayList<Object>();
		if (columnrightpagerightinfo.getMuserid() != null) {
			sqlbuilder.append("?,");
			objList.add(columnrightpagerightinfo.getMuserid());
		} else
			sqlbuilder.append("null,");
		if (columnrightpagerightinfo.getColumnid() != null) {
			sqlbuilder.append("?,");
			objList.add(columnrightpagerightinfo.getColumnid());
		} else
			sqlbuilder.append("null,");
		if (columnrightpagerightinfo.getRef() != null) {
			sqlbuilder.append("?,");
			objList.add(columnrightpagerightinfo.getRef());
		} else
			sqlbuilder.append("null,");
		if (columnrightpagerightinfo.getPagerightid() != null) {
			sqlbuilder.append("?,");
			objList.add(columnrightpagerightinfo.getPagerightid());
		} else
			sqlbuilder.append("null,");
		if (columnrightpagerightinfo.getColumnrightid() != null) {
			sqlbuilder.append("?,");
			objList.add(columnrightpagerightinfo.getColumnrightid());
		} else
			sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getUpdateSql(
			ColumnRightPageRightInfo columnrightpagerightinfo,
			StringBuilder sqlbuilder) {
		if (columnrightpagerightinfo == null || sqlbuilder == null)
			return null;
		sqlbuilder.append("{CALL j_columnright_pageright_info_proc_update(");
		List<Object> objList = new ArrayList<Object>();
		if (columnrightpagerightinfo.getMuserid() != null) {
			sqlbuilder.append("?,");
			objList.add(columnrightpagerightinfo.getMuserid());
		} else
			sqlbuilder.append("null,");
		if (columnrightpagerightinfo.getColumnid() != null) {
			sqlbuilder.append("?,");
			objList.add(columnrightpagerightinfo.getColumnid());
		} else
			sqlbuilder.append("null,");
		if (columnrightpagerightinfo.getRef() != null) {
			sqlbuilder.append("?,");
			objList.add(columnrightpagerightinfo.getRef());
		} else
			sqlbuilder.append("null,");
		if (columnrightpagerightinfo.getPagerightid() != null) {
			sqlbuilder.append("?,");
			objList.add(columnrightpagerightinfo.getPagerightid());
		} else
			sqlbuilder.append("null,");
		if (columnrightpagerightinfo.getColumnrightid() != null) {
			sqlbuilder.append("?,");
			objList.add(columnrightpagerightinfo.getColumnrightid());
		} else
			sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList;
	}
}
