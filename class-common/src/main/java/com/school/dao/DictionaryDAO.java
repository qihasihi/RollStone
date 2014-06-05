package com.school.dao;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.school.dao.base.CommonDAO;
import com.school.entity.DictionaryInfo;
import com.school.dao.inter.IDictionaryDAO;
import com.school.util.PageResult;

@Component
public class DictionaryDAO extends CommonDAO<DictionaryInfo> implements
		IDictionaryDAO {

	public Boolean doSave(DictionaryInfo dictionaryinfo) {//
		if (dictionaryinfo == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getSaveSql(dictionaryinfo, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}

	public Boolean doDelete(DictionaryInfo dictionaryinfo) {
		if (dictionaryinfo == null||dictionaryinfo.getRef() == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = getDeleteSql(dictionaryinfo, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}

	public Boolean doUpdate(DictionaryInfo dictionaryinfo) {
		if (dictionaryinfo == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getUpdateSql(dictionaryinfo, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}

	public List<DictionaryInfo> getList(DictionaryInfo dictionaryinfo,
			PageResult presult) {
		StringBuilder sqlbuilder = new StringBuilder();
		sqlbuilder.append("{CALL dictionary_info_proc_split(");
		List<Object> objList = new ArrayList<Object>();
		if (dictionaryinfo.getRef() != null) {
			sqlbuilder.append("?,");
			objList.add(dictionaryinfo.getRef());
		} else
			sqlbuilder.append("null,");
		if (dictionaryinfo.getDictionaryname() != null) {
			sqlbuilder.append("?,");
			objList.add(dictionaryinfo.getDictionaryname());
		} else
			sqlbuilder.append("null,");
		if (dictionaryinfo.getDictionaryvalue() != null) {
			sqlbuilder.append("?,");
			objList.add(dictionaryinfo.getDictionaryvalue());
		} else
			sqlbuilder.append("null,");
		if (dictionaryinfo.getDictionarytype() != null) {
			sqlbuilder.append("?,");
			objList.add(dictionaryinfo.getDictionarytype());
		} else
			sqlbuilder.append("null,");
		if (dictionaryinfo.getOrderidx() != null) {
			sqlbuilder.append("?,");
			objList.add(dictionaryinfo.getOrderidx());
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
//		if (presult != null && presult.getOrderBy() != null
//				&& presult.getOrderBy().trim().length() > 0) {
//			sqlbuilder.append("?,");
//			objList.add(presult.getOrderBy());
//		} else {
//			sqlbuilder.append("NULL,");
//		}
		sqlbuilder.append("?)}");
		List<Integer> types = new ArrayList<Integer>();
		types.add(Types.INTEGER);
		Object[] objArray = new Object[1];
		List<DictionaryInfo> dictionaryinfoList = this.executeResult_PROC(
				sqlbuilder.toString(), objList, types, DictionaryInfo.class,
				objArray);
		if (presult != null && objArray[0] != null
				&& objArray[0].toString().trim().length() > 0)
			presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));
		return dictionaryinfoList;
	}

	public List<Object> getSaveSql(DictionaryInfo dictionaryinfo,
			StringBuilder sqlbuilder) {
		if (dictionaryinfo == null || sqlbuilder == null)
			return null;
		sqlbuilder.append("{CALL dictionary_info_proc_add(");
		List<Object> objList = new ArrayList<Object>();
        sqlbuilder.append("?,");
		if (dictionaryinfo.getRef() != null) {
			objList.add(dictionaryinfo.getRef());
		} else{
			objList.add(UUID.randomUUID().toString());
        }
		if (dictionaryinfo.getDictionaryname() != null) {
			sqlbuilder.append("?,");
			objList.add(dictionaryinfo.getDictionaryname());
		} else
			sqlbuilder.append("null,");
		if (dictionaryinfo.getDictionaryvalue() != null) {
			sqlbuilder.append("?,");
			objList.add(dictionaryinfo.getDictionaryvalue());
		} else
			sqlbuilder.append("null,");
		if (dictionaryinfo.getDictionarytype() != null) {
			sqlbuilder.append("?,");
			objList.add(dictionaryinfo.getDictionarytype());
		} else
			sqlbuilder.append("null,");
		if (dictionaryinfo.getDictionarydescription() != null) {
			sqlbuilder.append("?,");
			objList.add(dictionaryinfo.getDictionarydescription());
		} else
			sqlbuilder.append("null,");
		if (dictionaryinfo.getOrderidx() != null) {
			sqlbuilder.append("?,");
			objList.add(dictionaryinfo.getOrderidx());
		} else
			sqlbuilder.append("null,");

		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getDeleteSql(DictionaryInfo dictionaryinfo,
			StringBuilder sqlbuilder) {
		if (dictionaryinfo == null || sqlbuilder == null)
			return null;
		sqlbuilder.append("{CALL dictionary_info_proc_delete(");
		List<Object> objList = new ArrayList<Object>();
		if (dictionaryinfo.getRef() != null) {
			sqlbuilder.append("?,");
			objList.add(dictionaryinfo.getRef());
		} else
			sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getUpdateSql(DictionaryInfo dictionaryinfo,
			StringBuilder sqlbuilder) {
		if (dictionaryinfo == null || sqlbuilder == null)
			return null;
		sqlbuilder.append("{CALL dictionary_info_proc_update(");
		List<Object> objList = new ArrayList<Object>();
		if (dictionaryinfo.getRef() != null) {
			sqlbuilder.append("?,");
			objList.add(dictionaryinfo.getRef());
		} else
			sqlbuilder.append("null,");
		if (dictionaryinfo.getDictionaryname() != null) {
			sqlbuilder.append("?,");
			objList.add(dictionaryinfo.getDictionaryname());
		} else
			sqlbuilder.append("null,");		
		if (dictionaryinfo.getDictionaryvalue() != null) {
			sqlbuilder.append("?,");
			objList.add(dictionaryinfo.getDictionaryvalue());
		} else
			sqlbuilder.append("null,");
		if (dictionaryinfo.getDictionarytype() != null) {
			sqlbuilder.append("?,");
			objList.add(dictionaryinfo.getDictionarytype());
		} else
			sqlbuilder.append("null,");
		if (dictionaryinfo.getDictionarydescription() != null) {
			sqlbuilder.append("?,");
			objList.add(dictionaryinfo.getDictionarydescription());
		} else
			sqlbuilder.append("null,");
		if (dictionaryinfo.getOrderidx() != null) {
			sqlbuilder.append("?,");
			objList.add(dictionaryinfo.getOrderidx());
		} else
			sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList;
	}

}
