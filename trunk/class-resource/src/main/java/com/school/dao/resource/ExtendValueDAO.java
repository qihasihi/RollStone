package com.school.dao.resource;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.school.dao.base.CommonDAO;
import com.school.entity.resource.ExtendValueInfo;
import com.school.dao.inter.resource.IExtendValueDAO;
import com.school.util.PageResult;

@Component
public class ExtendValueDAO extends CommonDAO<ExtendValueInfo> implements
		IExtendValueDAO {

	public Boolean doSave(ExtendValueInfo extendvalueinfo) {
		if (extendvalueinfo == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getSaveSql(extendvalueinfo, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}

	public Boolean doDelete(ExtendValueInfo extendvalueinfo) {
		if (extendvalueinfo == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = getDeleteSql(extendvalueinfo, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}

	public Boolean doUpdate(ExtendValueInfo extendvalueinfo) {
		if (extendvalueinfo == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getUpdateSql(extendvalueinfo, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}

	public List<ExtendValueInfo> getList(ExtendValueInfo extendvalueinfo,
			PageResult presult) {
		StringBuilder sqlbuilder = new StringBuilder();
		sqlbuilder.append("{CALL rs_extend_value_info_proc_split(");
		List<Object> objList = new ArrayList<Object>();
		
		if (extendvalueinfo.getValueid() != null) {
			sqlbuilder.append("?,");
			objList.add(extendvalueinfo.getValueid());
		} else
			sqlbuilder.append("null,");
		if (extendvalueinfo.getValuename() != null) {
			sqlbuilder.append("?,");
			objList.add(extendvalueinfo.getValuename());
		} else
			sqlbuilder.append("null,");
		if (extendvalueinfo.getExtendid() != null) {
			sqlbuilder.append("?,");
			objList.add(extendvalueinfo.getExtendid());
		} else
			sqlbuilder.append("null,");
		if (extendvalueinfo.getEnable() != null) {
			sqlbuilder.append("?,");
			objList.add(extendvalueinfo.getEnable());
		} else
			sqlbuilder.append("null,");
		if (extendvalueinfo.getOrdernumber() != null) {
			sqlbuilder.append("?,");
			objList.add(extendvalueinfo.getOrdernumber());
		} else
			sqlbuilder.append("null,");
		if (extendvalueinfo.getExtendname() != null) {
			sqlbuilder.append("?,");
			objList.add(extendvalueinfo.getExtendname());
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

		List<ExtendValueInfo> extendvalueinfoList = this.executeResult_PROC(
				sqlbuilder.toString(), objList, types, ExtendValueInfo.class,
				objArray);
		if (presult != null && objArray[0] != null
				&& objArray[0].toString().trim().length() > 0)
			presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));
		return extendvalueinfoList;
	}

	public List<ExtendValueInfo> getNotInList(String extendref,
			String valueList) {
		if(extendref==null||extendref.length()<1||valueList==null)
			return null;
		StringBuilder sqlbuilder = new StringBuilder();
		sqlbuilder.append("{CALL rs_extend_value_info_proc_notin_list(?,?,?)}");
		List<Object> objList = new ArrayList<Object>();
		
		objList.add(valueList);
		objList.add(extendref);
		List<Integer> types = new ArrayList<Integer>();
		types.add(Types.INTEGER);
		Object[] objArray = new Object[1];
		List<ExtendValueInfo> extendvalueinfoList = this.executeResult_PROC(
				sqlbuilder.toString(), objList, types, ExtendValueInfo.class,
				objArray);
		return extendvalueinfoList;
	}
	
	public List<Object> getSaveSql(ExtendValueInfo extendvalueinfo,
			StringBuilder sqlbuilder) {
		if (extendvalueinfo == null || sqlbuilder == null)
			return null;
		sqlbuilder.append("{CALL rs_extend_value_info_proc_add(");
		List<Object> objList = new ArrayList<Object>();

		if (extendvalueinfo.getValueid() != null) {
			sqlbuilder.append("?,");
			objList.add(extendvalueinfo.getValueid());
		} else
			sqlbuilder.append("null,");
		if (extendvalueinfo.getValuename() != null) {
			sqlbuilder.append("?,");
			objList.add(extendvalueinfo.getValuename());
		} else
			sqlbuilder.append("null,");
		if (extendvalueinfo.getExtendid() != null) {
			sqlbuilder.append("?,");
			objList.add(extendvalueinfo.getExtendid());
		} else
			sqlbuilder.append("null,");
		if (extendvalueinfo.getOrdernumber() != null) {
			sqlbuilder.append("?,");
			objList.add(extendvalueinfo.getOrdernumber());
		} else
			sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getDeleteSql(ExtendValueInfo extendvalueinfo,
			StringBuilder sqlbuilder) {
		if (extendvalueinfo == null || sqlbuilder == null)
			return null;
		sqlbuilder.append("{CALL rs_extend_value_info_proc_delete(");
		List<Object> objList = new ArrayList<Object>();
		if (extendvalueinfo.getValueid() != null) {
			sqlbuilder.append("?,");
			objList.add(extendvalueinfo.getValueid());
		} else
			sqlbuilder.append("null,");
		if (extendvalueinfo.getValuename() != null) {
			sqlbuilder.append("?,");
			objList.add(extendvalueinfo.getValuename());
		} else
			sqlbuilder.append("null,");
		if (extendvalueinfo.getExtendid() != null) {
			sqlbuilder.append("?,");
			objList.add(extendvalueinfo.getExtendid());
		} else
			sqlbuilder.append("null,");
		if (extendvalueinfo.getEnable() != null) {
			sqlbuilder.append("?,");
			objList.add(extendvalueinfo.getEnable());
		} else
			sqlbuilder.append("null,");
		if (extendvalueinfo.getOrdernumber() != null) {
			sqlbuilder.append("?,");
			objList.add(extendvalueinfo.getOrdernumber());
		} else
			sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList;
	}
	
	public List<Object> getUpdateSql(ExtendValueInfo extendvalueinfo,
			StringBuilder sqlbuilder) {
		if (extendvalueinfo == null || sqlbuilder == null)
			return null;
		sqlbuilder.append("{CALL rs_extend_value_info_proc_update(");
		List<Object> objList = new ArrayList<Object>();
		if (extendvalueinfo.getEnable() != null) {
			sqlbuilder.append("?,");
			objList.add(extendvalueinfo.getEnable());
		} else
			sqlbuilder.append("null,");
		if (extendvalueinfo.getCtime() != null) {
			sqlbuilder.append("?,");
			objList.add(extendvalueinfo.getCtime());
		} else
			sqlbuilder.append("null,");
		if (extendvalueinfo.getMtime() != null) {
			sqlbuilder.append("?,");
			objList.add(extendvalueinfo.getMtime());
		} else
			sqlbuilder.append("null,");
		if (extendvalueinfo.getValueid() != null) {
			sqlbuilder.append("?,");
			objList.add(extendvalueinfo.getValueid());
		} else
			sqlbuilder.append("null,");
		if (extendvalueinfo.getValuename() != null) {
			sqlbuilder.append("?,");
			objList.add(extendvalueinfo.getValuename());
		} else
			sqlbuilder.append("null,");
		if (extendvalueinfo.getExtendid() != null) {
			sqlbuilder.append("?,");
			objList.add(extendvalueinfo.getExtendid());
		} else
			sqlbuilder.append("null,");
		if (extendvalueinfo.getOrdernumber() != null) {
			sqlbuilder.append("?,");
			objList.add(extendvalueinfo.getOrdernumber());
		} else
			sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList;
	}
	
	public List<Object> getUpdateOrderBySql(ExtendValueInfo extendvalueinfo,
			StringBuilder sqlbuilder) {
		if (extendvalueinfo == null || sqlbuilder == null)
			return null;
		sqlbuilder.append("{CALL rs_extend_value_info_proc_update_orderby(");
		List<Object> objList = new ArrayList<Object>();
		if (extendvalueinfo.getValuename() != null) {
			sqlbuilder.append("?,");
			objList.add(extendvalueinfo.getValuename());
		} else
			sqlbuilder.append("null,");
		if (extendvalueinfo.getExtendid() != null) {
			sqlbuilder.append("?,");
			objList.add(extendvalueinfo.getExtendid());
		} else
			sqlbuilder.append("null,");
		if (extendvalueinfo.getOrdernumber() != null) {
			sqlbuilder.append("?,");
			objList.add(extendvalueinfo.getOrdernumber());
		} else
			sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList;
	}

}
