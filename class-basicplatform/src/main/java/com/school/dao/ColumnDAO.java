package com.school.dao;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.school.dao.base.CommonDAO;
import com.school.entity.ColumnInfo;
import com.school.dao.inter.IColumnDAO;
import com.school.util.PageResult;

@Component
public class ColumnDAO extends CommonDAO<ColumnInfo> implements IColumnDAO {

	public Boolean doSave(ColumnInfo columninfo) {
		if (columninfo == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getSaveSql(columninfo, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}

	public Boolean doDelete(ColumnInfo columninfo) {
		if (columninfo == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = getDeleteSql(columninfo, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}

	public Boolean doUpdate(ColumnInfo columninfo) {
		if (columninfo == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getUpdateSql(columninfo, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}

	public List<ColumnInfo> getList(ColumnInfo columninfo, PageResult presult) {
		StringBuilder sqlbuilder = new StringBuilder();
		sqlbuilder.append("{CALL column_info_proc_split(");
		List<Object> objList = new ArrayList<Object>();
		if (columninfo.getMuserid() != null) {
			sqlbuilder.append("?,");
			objList.add(columninfo.getMuserid());
		} else
			sqlbuilder.append("null,");
		if (columninfo.getColumnname() != null) {
			sqlbuilder.append("?,");
			objList.add(columninfo.getColumnname());
		} else
			sqlbuilder.append("null,");
		if (columninfo.getColumnid() != null) {
			sqlbuilder.append("?,");
			objList.add(columninfo.getColumnid());
		} else
			sqlbuilder.append("null,");
		if (columninfo.getPath() != null) {
			sqlbuilder.append("?,");
			objList.add(columninfo.getPath());
		} else
			sqlbuilder.append("null,");
		if (columninfo.getRef() != null) {
			sqlbuilder.append("?,");
			objList.add(columninfo.getRef());
		} else
			sqlbuilder.append("null,");
		if (columninfo.getStyleclassid() != null) {
			sqlbuilder.append("?,");
			objList.add(columninfo.getStyleclassid());
		} else
			sqlbuilder.append("null,");
		if (columninfo.getFnid() != null) {
			sqlbuilder.append("?,");
			objList.add(columninfo.getFnid());
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
		List<ColumnInfo> columninfoList = this.executeResult_PROC(sqlbuilder
				.toString(), objList, types, ColumnInfo.class, objArray);
		if (presult != null && objArray[0] != null
				&& objArray[0].toString().trim().length() > 0)
			presult
					.setRecTotal(Integer
							.parseInt(objArray[0].toString().trim()));
		return columninfoList;
	}

	public List<Object> getSaveSql(ColumnInfo columninfo,
			StringBuilder sqlbuilder) {
		if (columninfo == null || sqlbuilder == null)
			return null;
		sqlbuilder.append("{CALL column_info_proc_add(");
		List<Object> objList = new ArrayList<Object>();
		if (columninfo.getMuserid() != null) {
			sqlbuilder.append("?,");
			objList.add(columninfo.getMuserid());
		} else
			sqlbuilder.append("null,");
		if (columninfo.getColumnname() != null) {
			sqlbuilder.append("?,");
			objList.add(columninfo.getColumnname());
		} else
			sqlbuilder.append("null,");
		if (columninfo.getColumnid() != null) {
			sqlbuilder.append("?,");
			objList.add(columninfo.getColumnid());
		} else
			sqlbuilder.append("null,");
		if (columninfo.getPath() != null) {
			sqlbuilder.append("?,");
			objList.add(columninfo.getPath());
		} else
			sqlbuilder.append("null,");
		if (columninfo.getRef() != null) {
			sqlbuilder.append("?,");
			objList.add(columninfo.getRef());
		} else
			sqlbuilder.append("null,");
		if (columninfo.getStyleclassid() != null) {
			sqlbuilder.append("?,");
			objList.add(columninfo.getStyleclassid());
		} else
			sqlbuilder.append("null,");
		if (columninfo.getFnid() != null) {
			sqlbuilder.append("?,");
			objList.add(columninfo.getFnid());
		} else
			sqlbuilder.append("null,");
		if (columninfo.getRemark() != null) {
			sqlbuilder.append("?,");
			objList.add(columninfo.getRemark());
		} else
			sqlbuilder.append("null,");

		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getDeleteSql(ColumnInfo columninfo,
			StringBuilder sqlbuilder) {
		if (columninfo == null || sqlbuilder == null)
			return null;
		sqlbuilder.append("{CALL column_info_proc_delete(");
		List<Object> objList = new ArrayList<Object>();
		if (columninfo.getMuserid() != null) {
			sqlbuilder.append("?,");
			objList.add(columninfo.getMuserid());
		} else
			sqlbuilder.append("null,");
		if (columninfo.getColumnname() != null) {
			sqlbuilder.append("?,");
			objList.add(columninfo.getColumnname());
		} else
			sqlbuilder.append("null,");
		if (columninfo.getColumnid() != null) {
			sqlbuilder.append("?,");
			objList.add(columninfo.getColumnid());
		} else
			sqlbuilder.append("null,");
		if (columninfo.getPath() != null) {
			sqlbuilder.append("?,");
			objList.add(columninfo.getPath());
		} else
			sqlbuilder.append("null,");
		if (columninfo.getRef() != null) {
			sqlbuilder.append("?,");
			objList.add(columninfo.getRef());
		} else
			sqlbuilder.append("null,");
		if (columninfo.getStyleclassid() != null) {
			sqlbuilder.append("?,");
			objList.add(columninfo.getStyleclassid());
		} else
			sqlbuilder.append("null,");
		if (columninfo.getFnid() != null) {
			sqlbuilder.append("?,");
			objList.add(columninfo.getFnid());
		} else
			sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getUpdateSql(ColumnInfo columninfo,
			StringBuilder sqlbuilder) {
		if (columninfo == null || sqlbuilder == null)
			return null;
		sqlbuilder.append("{CALL column_info_proc_update(");
		List<Object> objList = new ArrayList<Object>();
		if (columninfo.getMuserid() != null) {
			sqlbuilder.append("?,");
			objList.add(columninfo.getMuserid());
		} else
			sqlbuilder.append("null,");
		if (columninfo.getColumnname() != null) {
			sqlbuilder.append("?,");
			objList.add(columninfo.getColumnname());
		} else
			sqlbuilder.append("null,");
		if (columninfo.getColumnid() != null) {
			sqlbuilder.append("?,");
			objList.add(columninfo.getColumnid());
		} else
			sqlbuilder.append("null,");
		if (columninfo.getPath() != null) {
			sqlbuilder.append("?,");
			objList.add(columninfo.getPath());
		} else
			sqlbuilder.append("null,");
		if (columninfo.getRef() != null) {
			sqlbuilder.append("?,");
			objList.add(columninfo.getRef());
		} else
			sqlbuilder.append("null,");
		if (columninfo.getStyleclassid() != null) {
			sqlbuilder.append("?,");
			objList.add(columninfo.getStyleclassid());
		} else
			sqlbuilder.append("null,");
		if (columninfo.getFnid() != null) {
			sqlbuilder.append("?,");
			objList.add(columninfo.getFnid());
		} else
			sqlbuilder.append("null,");
		if (columninfo.getRemark() != null) {
			sqlbuilder.append("?,");
			objList.add(columninfo.getRemark());
		} else
			sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList;
	}
	
	/**
	 * 得到用户的栏目访问权限
	 * @param userref
	 * @return
	 */
	public List<ColumnInfo> getUserColumnList(String userref) {
		StringBuilder sqlbuilder = new StringBuilder();		
		List<Object> objList = new ArrayList<Object>();
		sqlbuilder.append("{CALL user_column_list(?)}");
		List<Integer> types = new ArrayList<Integer>();
		//types.add(Types.INTEGER);
		Object[] objArray = new Object[0];
		objList.add(userref);
		List<ColumnInfo> columninfoList = this.executeResult_PROC(sqlbuilder
				.toString(), objList, types, ColumnInfo.class, objArray);
		return columninfoList;
	}
}
