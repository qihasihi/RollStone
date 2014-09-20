package com.school.dao;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.school.entity.EttColumnInfo;
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

    /**
     * 同步ETT栏目信息
     * @param entity
     * @param sqlbuilder
     * @return
     */
    public List<Object> getEttColumnSynchro(final EttColumnInfo entity,StringBuilder sqlbuilder){
        if(entity==null||sqlbuilder==null)return null;
        sqlbuilder.append("{CALL ett_column_info_proc_synchro(");
        List<Object> returnObj=new ArrayList<Object>();
        if(entity.getEttcolumnid()!=null){
            returnObj.add(entity.getEttcolumnid());
            sqlbuilder.append("?,");
        }else
            sqlbuilder.append("NULL,");
        if(entity.getEttcolumnname()!=null){
            returnObj.add(entity.getEttcolumnname());
            sqlbuilder.append("?,");
        }else
            sqlbuilder.append("NULL,");
        if(entity.getEttcolumnurl()!=null){
            returnObj.add(entity.getEttcolumnurl());
            sqlbuilder.append("?,");
        }else
            sqlbuilder.append("NULL,");
        if(entity.getStatus()!=null){
            returnObj.add(entity.getStatus());
            sqlbuilder.append("?,");
        }else
            sqlbuilder.append("NULL,");
        if(entity.getStyle()!=null){
            returnObj.add(entity.getStyle());
        }else
            returnObj.add("");
        sqlbuilder.append("?,");

        if(entity.getRoletype()!=null){
            returnObj.add(entity.getRoletype());
            sqlbuilder.append("?,");
        }else
            sqlbuilder.append("NULL,");
        if(entity.getIsShow()!=null){
            returnObj.add(entity.getIsShow());
            sqlbuilder.append("?,");
        }else
            sqlbuilder.append("NULL,");
        if(entity.getSchoolid()!=null){
            returnObj.add(entity.getSchoolid());
            sqlbuilder.append("?,");
        }else
            sqlbuilder.append("NULL,");
        sqlbuilder.append("?)}");
        return returnObj;
    }
    public List<Object> getEttDeleteSql(final EttColumnInfo entity, StringBuilder sqlbuilder){
        if(entity==null||sqlbuilder==null)return null;
        sqlbuilder.append("{CALL ett_column_info_proc_delete_synchro(");
        List<Object> returnObj=new ArrayList<Object>();
        if(entity.getEttcolumnid()!=null){
            returnObj.add(entity.getEttcolumnid());
            sqlbuilder.append("?,");
        }else
            sqlbuilder.append("NULL,");
        if(entity.getStatus()!=null){
            returnObj.add(entity.getStatus());
            sqlbuilder.append("?,");
        }else
            sqlbuilder.append("NULL,");
        if(entity.getRoletype()!=null){
            returnObj.add(entity.getRoletype());
            sqlbuilder.append("?,");
        }else
            sqlbuilder.append("NULL,");
        sqlbuilder.append("?)}");
        return returnObj;
    }



    /**
     * 查询ETT栏目信息
     * @param entity
     * @param presult
     * @return
     */
    public List<EttColumnInfo> getEttColumnSplit(final EttColumnInfo entity,PageResult presult){
       StringBuilder sqlbuilder=new StringBuilder("{CALL ett_column_info_proc_split(");
        List<Object> objList=new ArrayList<Object>();
        if(entity==null){
            sqlbuilder.append("NULL,NULL,NULL,NULL,NULL,NULL,");
        }else{
            if(entity.getRef()!=null){
                objList.add(entity.getRef());
                sqlbuilder.append("?,");
            }else
                sqlbuilder.append("NULL,");
            if(entity.getEttcolumnid()!=null){
                objList.add(entity.getEttcolumnid());
                sqlbuilder.append("?,");
            }else
                sqlbuilder.append("NULL,");
            if(entity.getEttcolumnname()!=null){
                objList.add(entity.getEttcolumnname());
                sqlbuilder.append("?,");
            }else
                sqlbuilder.append("NULL,");
            if(entity.getStatus()!=null){
                objList.add(entity.getStatus());
                sqlbuilder.append("?,");
            }else
                sqlbuilder.append("NULL,");
            if(entity.getRoletype()!=null){
                objList.add(entity.getRoletype());
                sqlbuilder.append("?,");
            }else
                sqlbuilder.append("NULL,");
            if(entity.getSchoolid()!=null){
                objList.add(entity.getSchoolid());
                sqlbuilder.append("?,");
            }else
                sqlbuilder.append("NULL,");
        }
        if (presult != null && presult.getPageNo() > 0
                && presult.getPageSize() > 0) {
            sqlbuilder.append("?,?,");
            objList.add(presult.getPageNo());
            objList.add(presult.getPageSize());
        } else {
            sqlbuilder.append("NULL,NULL,");
        }
        sqlbuilder.append("?)}");
        List<Integer> types = new ArrayList<Integer>();
        types.add(Types.INTEGER);
        Object[] objArray = new Object[1];
        List<EttColumnInfo> ettColumnList = this.executeResult_PROC(sqlbuilder
                .toString(), objList, types, EttColumnInfo.class, objArray);
        if (presult != null && objArray[0] != null
                && objArray[0].toString().trim().length() > 0)
            presult
                    .setRecTotal(Integer
                            .parseInt(objArray[0].toString().trim()));
        return ettColumnList;
    }
}
