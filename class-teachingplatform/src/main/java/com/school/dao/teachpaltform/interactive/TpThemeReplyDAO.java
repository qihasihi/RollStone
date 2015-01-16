package com.school.dao.teachpaltform.interactive;

import com.school.dao.base.CommonDAO;
import com.school.dao.inter.teachpaltform.interactive.ITpThemeReplyDAO;
import com.school.entity.teachpaltform.interactive.TpThemeReplyInfo;
import com.school.util.PageResult;
import org.springframework.stereotype.Component;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

@Component
public class TpThemeReplyDAO extends CommonDAO<TpThemeReplyInfo> implements
		ITpThemeReplyDAO {

	public Boolean doSave(TpThemeReplyInfo tpthemereplyinfo) {
		if (tpthemereplyinfo == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getSaveSql(tpthemereplyinfo, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}

	public Boolean doDelete(TpThemeReplyInfo tpthemereplyinfo) {
		if (tpthemereplyinfo == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = getDeleteSql(tpthemereplyinfo, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}

	public Boolean doUpdate(TpThemeReplyInfo tpthemereplyinfo) {
		if (tpthemereplyinfo == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getUpdateSql(tpthemereplyinfo, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}
    public List<TpThemeReplyInfo> getListByThemeIdStr(final String themeidStr,final Integer searchType, PageResult presult){
        if(themeidStr==null)return null;
        StringBuilder sqlbuilder = new StringBuilder();
        sqlbuilder.append("{CALL tp_theme_reply_info_bythemeidstr_split(");
        List<Object> objList = new ArrayList<Object>();

        if (themeidStr != null) {
            sqlbuilder.append("?,");
            objList.add(themeidStr);
        } else
            sqlbuilder.append("null,");
        if (searchType != null) {
            sqlbuilder.append("?,");
            objList.add(searchType);
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
        types.add(Types.VARCHAR);
        Object[] objArray = new Object[1];
        List<TpThemeReplyInfo> tpthemereplyinfoList = this.executeResult_PROC(
                sqlbuilder.toString(), objList, types, TpThemeReplyInfo.class,
                objArray);
        if (presult != null && objArray[0] != null
                && objArray[0].toString().trim().length() > 0)
            presult.getList().add(objArray[0].toString().trim());
        return tpthemereplyinfoList;
    }

	public List<TpThemeReplyInfo> getList(TpThemeReplyInfo tpthemereplyinfo,
			PageResult presult) {
		StringBuilder sqlbuilder = new StringBuilder();
		sqlbuilder.append("{CALL tp_theme_reply_info_proc_split(");
		List<Object> objList = new ArrayList<Object>();
		
		if (tpthemereplyinfo.getThemeid() != null) {
			sqlbuilder.append("?,");
			objList.add(tpthemereplyinfo.getThemeid());
		} else
			sqlbuilder.append("null,");
		if (tpthemereplyinfo.getUserid() != null) {
			sqlbuilder.append("?,");
			objList.add(tpthemereplyinfo.getUserid());
		} else
			sqlbuilder.append("null,");	

		if (tpthemereplyinfo.getReplyid() != null) {
			sqlbuilder.append("?,");
			objList.add(tpthemereplyinfo.getReplyid());
		} else
			sqlbuilder.append("null,");
        if (tpthemereplyinfo.getTopicid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpthemereplyinfo.getTopicid());
        } else
            sqlbuilder.append("null,");
        if (tpthemereplyinfo.getToreplyid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpthemereplyinfo.getToreplyid());
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
		List<TpThemeReplyInfo> tpthemereplyinfoList = this.executeResult_PROC(
				sqlbuilder.toString(), objList, types, TpThemeReplyInfo.class,
				objArray);
		if (presult != null && objArray[0] != null
				&& objArray[0].toString().trim().length() > 0)
			presult
					.setRecTotal(Integer
							.parseInt(objArray[0].toString().trim()));
		return tpthemereplyinfoList;
	}

	public List<Object> getSaveSql(TpThemeReplyInfo tpthemereplyinfo,
			StringBuilder sqlbuilder) {
		if (tpthemereplyinfo == null || sqlbuilder == null)
			return null;
		sqlbuilder.append("{CALL tp_theme_reply_info_proc_add(");
		List<Object> objList = new ArrayList<Object>();
		if (tpthemereplyinfo.getThemeid() != null) {
			sqlbuilder.append("?,");
			objList.add(tpthemereplyinfo.getThemeid());
		} else
			sqlbuilder.append("null,");
		if (tpthemereplyinfo.getUserid() != null) {
			sqlbuilder.append("?,");
			objList.add(tpthemereplyinfo.getUserid());
		} else
			sqlbuilder.append("null,");
		if (tpthemereplyinfo.getReplyid() != null) {
			sqlbuilder.append("?,");
			objList.add(tpthemereplyinfo.getReplyid());
		} else
			sqlbuilder.append("null,");
        if (tpthemereplyinfo.getTopicid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpthemereplyinfo.getTopicid());
        } else
            sqlbuilder.append("null,");
        if (tpthemereplyinfo.getToreplyid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpthemereplyinfo.getToreplyid());
        } else
            sqlbuilder.append("null,");
        if (tpthemereplyinfo.getTorealname() != null) {
            sqlbuilder.append("?,");
            objList.add(tpthemereplyinfo.getTorealname());
        } else
            sqlbuilder.append("null,");

        if (tpthemereplyinfo.getCtimeString() != null) {
            sqlbuilder.append("?,");
            objList.add(tpthemereplyinfo.getCtimeString());
        } else
            sqlbuilder.append("null,");

        sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getDeleteSql(TpThemeReplyInfo tpthemereplyinfo,
			StringBuilder sqlbuilder) {
		if (tpthemereplyinfo == null || sqlbuilder == null)
			return null;
		sqlbuilder.append("{CALL tp_theme_reply_info_proc_delete(");
		List<Object> objList = new ArrayList<Object>();
		if (tpthemereplyinfo.getThemeid() != null) {
			sqlbuilder.append("?,");
			objList.add(tpthemereplyinfo.getThemeid());
		} else
			sqlbuilder.append("null,");
		if (tpthemereplyinfo.getUserid() != null) {
			sqlbuilder.append("?,");
			objList.add(tpthemereplyinfo.getUserid());
		} else
			sqlbuilder.append("null,");		

		if (tpthemereplyinfo.getReplyid() != null) {
			sqlbuilder.append("?,");
			objList.add(tpthemereplyinfo.getReplyid());
		} else
			sqlbuilder.append("null,");
		if (tpthemereplyinfo.getTopicid() != null) {
			sqlbuilder.append("?,");
			objList.add(tpthemereplyinfo.getTopicid());
		} else
			sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getUpdateSql(TpThemeReplyInfo tpthemereplyinfo,
			StringBuilder sqlbuilder) {
		if (tpthemereplyinfo == null || sqlbuilder == null)
			return null;
		sqlbuilder.append("{CALL tp_theme_reply_info_proc_update(");
		List<Object> objList = new ArrayList<Object>();
		
		if (tpthemereplyinfo.getCtime() != null) {
			sqlbuilder.append("?,");
			objList.add(tpthemereplyinfo.getCtime());
		} else
			sqlbuilder.append("null,");
		if (tpthemereplyinfo.getUserid() != null) {
			sqlbuilder.append("?,");
			objList.add(tpthemereplyinfo.getUserid());
		} else
			sqlbuilder.append("null,");
		if (tpthemereplyinfo.getThemeid() != null) {
			sqlbuilder.append("?,");
			objList.add(tpthemereplyinfo.getThemeid());
		} else
			sqlbuilder.append("null,");
		if (tpthemereplyinfo.getReplyid() != null) {
			sqlbuilder.append("?,");
			objList.add(tpthemereplyinfo.getReplyid());
		} else
			sqlbuilder.append("null,");
		if (tpthemereplyinfo.getTopicid() != null) {
			sqlbuilder.append("?,");
			objList.add(tpthemereplyinfo.getTopicid());
		} else
			sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList;
	}
}
