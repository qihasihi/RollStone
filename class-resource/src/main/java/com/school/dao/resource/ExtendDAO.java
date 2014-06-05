package com.school.dao.resource;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.school.dao.base.CommonDAO;
import com.school.entity.resource.ExtendInfo;
import com.school.dao.inter.resource.IExtendDAO;
import com.school.util.PageResult;

@Component
public class ExtendDAO extends CommonDAO<ExtendInfo> implements IExtendDAO {

	public Boolean doSave(ExtendInfo extendinfo) {
		if (extendinfo == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getSaveSql(extendinfo, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}

	public Integer doSaveGetId(ExtendInfo extendinfo) {
		if (extendinfo == null)
			return 0;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getSaveSql(extendinfo, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return Integer.parseInt(afficeObj.toString());
		}
		return 0;
	}

	public Boolean doDelete(ExtendInfo extendinfo) {
		if (extendinfo == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = getDeleteSql(extendinfo, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}

	public Boolean doUpdate(ExtendInfo extendinfo) {
		if (extendinfo == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getUpdateSql(extendinfo, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}

	public List<ExtendInfo> getList(ExtendInfo extendinfo, PageResult presult) {
		StringBuilder sqlbuilder = new StringBuilder();
		sqlbuilder.append("{CALL rs_extend_info_proc_split(");
		List<Object> objList = new ArrayList<Object>();
		if (extendinfo == null)
			extendinfo = new ExtendInfo();
		if (extendinfo.getExtendid() != null) {
			sqlbuilder.append("?,");
			objList.add(extendinfo.getExtendid());
		} else
			sqlbuilder.append("null,");
		if (extendinfo.getEnable() != null) {
			sqlbuilder.append("?,");
			objList.add(extendinfo.getEnable());
		} else
			sqlbuilder.append("null,");
		if (extendinfo.getExtendname() != null) {
			sqlbuilder.append("?,");
			objList.add(extendinfo.getExtendname());
		} else
			sqlbuilder.append("null,");
		if (extendinfo.getDependextendid() != null) {
			sqlbuilder.append("?,");
			objList.add(extendinfo.getDependextendid());
		} else
			sqlbuilder.append("null,");
		if (extendinfo.getDependextendvalueid() != null) {
			sqlbuilder.append("?,");
			objList.add(extendinfo.getDependextendvalueid());
		} else
			sqlbuilder.append("null,");
		if (extendinfo.getIsquery() != null) {
			sqlbuilder.append("?,");
			objList.add(extendinfo.getIsquery());
		} else
			sqlbuilder.append("null,");
		if (extendinfo.getOrdernumber() != null) {
			sqlbuilder.append("?,");
			objList.add(extendinfo.getOrdernumber());
		} else
			sqlbuilder.append("null,");
		if (extendinfo.getCheck() != null) {
			sqlbuilder.append("?,");
			objList.add(extendinfo.getCheck());
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
		List<ExtendInfo> extendinfoList = this.executeResult_PROC(
				sqlbuilder.toString(), objList, types, ExtendInfo.class,
				objArray);
		if (presult != null && objArray[0] != null
				&& objArray[0].toString().trim().length() > 0)
			presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));
		return extendinfoList;
	}

	public List<Object> getSaveSql(ExtendInfo extendinfo,
			StringBuilder sqlbuilder) {
		if (extendinfo == null || sqlbuilder == null)
			return null;
		sqlbuilder.append("{CALL rs_extend_info_proc_add(");
		List<Object> objList = new ArrayList<Object>();
		if (extendinfo.getExtendname() != null) {
			sqlbuilder.append("?,");
			objList.add(extendinfo.getExtendname());
		} else
			sqlbuilder.append("null,");
		if (extendinfo.getDependextendid() != null) {
			sqlbuilder.append("?,");
			objList.add(extendinfo.getDependextendid());
		} else
			sqlbuilder.append("null,");
		if (extendinfo.getExtendid() != null) {
			sqlbuilder.append("?,");
			objList.add(extendinfo.getExtendid());
		} else
			sqlbuilder.append("null,");
		if (extendinfo.getDependextendvalueid() != null) {
			sqlbuilder.append("?,");
			objList.add(extendinfo.getDependextendvalueid());
		} else
			sqlbuilder.append("null,");
		if (extendinfo.getEnable() != null) {
			sqlbuilder.append("?,");
			objList.add(extendinfo.getEnable());
		} else
			sqlbuilder.append("null,");
		if (extendinfo.getIsquery() != null) {
			sqlbuilder.append("?,");
			objList.add(extendinfo.getIsquery());
		} else
			sqlbuilder.append("null,");
		if (extendinfo.getOrdernumber() != null) {
			sqlbuilder.append("?,");
			objList.add(extendinfo.getOrdernumber());
		} else
			sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getDeleteSql(ExtendInfo extendinfo,
			StringBuilder sqlbuilder) {
		if (extendinfo == null || sqlbuilder == null)
			return null;
		sqlbuilder.append("{CALL rs_extend_info_proc_delete(");
		List<Object> objList = new ArrayList<Object>();

		if (extendinfo.getExtendid() != null) {
			sqlbuilder.append("?,");
			objList.add(extendinfo.getExtendid());
		} else
			sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList;
	}
	/**
	 * 查找所有的树节点
	 * @param rootid
	 * @return
	 */
	public List<Map<String, Object>> getExtendCheckShu(String rootid){
		if (rootid == null || rootid.trim().length()<1)
			return null;
		StringBuilder sqlbuilder=new StringBuilder();
		sqlbuilder.append("{CALL rs_extend_check_proc_shu(");
		List<Object> objList = new ArrayList<Object>();
		if(rootid!= null) {
			sqlbuilder.append("?");
			objList.add(rootid); 
		}else
			sqlbuilder.append("null");
		sqlbuilder.append(")}");
		return this.executeResultListMap_PROC(sqlbuilder.toString(), objList);
	}

	public List<Object> getUpdateSql(ExtendInfo extendinfo,
			StringBuilder sqlbuilder) {
		if (extendinfo == null || sqlbuilder == null)
			return null;
		sqlbuilder.append("{CALL rs_extend_info_proc_update(");
		List<Object> objList = new ArrayList<Object>();
		if (extendinfo.getExtendid() != null) {
			sqlbuilder.append("?,");
			objList.add(extendinfo.getExtendid());
		} else
			sqlbuilder.append("null,");
		if (extendinfo.getExtendname() != null) {
			sqlbuilder.append("?,");
			objList.add(extendinfo.getExtendname());
		} else
			sqlbuilder.append("null,");
		if (extendinfo.getDependextendid() != null) {
			sqlbuilder.append("?,");
			objList.add(extendinfo.getDependextendid());
		} else
			sqlbuilder.append("null,");
		if (extendinfo.getDependextendvalueid() != null) {
			sqlbuilder.append("?,");
			objList.add(extendinfo.getDependextendvalueid());
		} else
			sqlbuilder.append("null,");
		if (extendinfo.getIsquery() != null) {
			sqlbuilder.append("?,");
			objList.add(extendinfo.getIsquery());
		} else
			sqlbuilder.append("null,");
		if (extendinfo.getOrdernumber() != null) {
			sqlbuilder.append("?,");
			objList.add(extendinfo.getOrdernumber());
		} else
			sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList;
	}

}
