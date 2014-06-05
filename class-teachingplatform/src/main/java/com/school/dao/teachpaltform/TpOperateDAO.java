package com.school.dao.teachpaltform;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.school.dao.base.CommonDAO;
import com.school.entity.teachpaltform.TpOperateInfo;
import com.school.dao.inter.teachpaltform.ITpOperateDAO;
import com.school.util.PageResult;

@Component  
public class TpOperateDAO extends CommonDAO<TpOperateInfo> implements ITpOperateDAO {

	public Boolean doSave(TpOperateInfo tpoperateinfo) {
		if (tpoperateinfo == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getSaveSql(tpoperateinfo, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}
	
	public Boolean doDelete(TpOperateInfo tpoperateinfo) {
		if(tpoperateinfo==null)		
			return false;
		StringBuilder sqlbuilder=new StringBuilder();
		List<Object> objList=getDeleteSql(tpoperateinfo, sqlbuilder);
		Object afficeObj=this.executeSacle_PROC(sqlbuilder.toString(), objList.toArray());
		if(afficeObj!=null&&afficeObj.toString().trim().length()>0&&Integer.parseInt(afficeObj.toString())>0){
			return true;
		}return false;
	}

	public Boolean doUpdate(TpOperateInfo tpoperateinfo) {
		if (tpoperateinfo == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getUpdateSql(tpoperateinfo, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}
	
	public List<TpOperateInfo> getList(TpOperateInfo tpoperateinfo, PageResult presult) {
		StringBuilder sqlbuilder = new StringBuilder();
		sqlbuilder.append("{CALL tp_operate_info_proc_split(");
		List<Object> objList=new ArrayList<Object>();
		if (tpoperateinfo.getOperatetype() != null) {
			sqlbuilder.append("?,");
			objList.add(tpoperateinfo.getOperatetype());
		} else
			sqlbuilder.append("null,");
		if (tpoperateinfo.getCourseid() != null) {
			sqlbuilder.append("?,");
			objList.add(tpoperateinfo.getCourseid());
		} else
			sqlbuilder.append("null,");
		if (tpoperateinfo.getRef() != null) {
			sqlbuilder.append("?,");
			objList.add(tpoperateinfo.getRef());
		} else
			sqlbuilder.append("null,");
		if (tpoperateinfo.getDatatype() != null) {
			sqlbuilder.append("?,");
			objList.add(tpoperateinfo.getDatatype());
		} else
			sqlbuilder.append("null,");
		if (tpoperateinfo.getCuserid() != null) {
			sqlbuilder.append("?,");
			objList.add(tpoperateinfo.getCuserid());
		} else
			sqlbuilder.append("null,");
		if (tpoperateinfo.getTargetid() != null) {
			sqlbuilder.append("?,");
			objList.add(tpoperateinfo.getTargetid());
		} else
			sqlbuilder.append("null,");

		if(presult!=null&&presult.getPageNo()>0&&presult.getPageSize()>0){
			sqlbuilder.append("?,?,");
			objList.add(presult.getPageNo());
			objList.add(presult.getPageSize());
		}else{
			sqlbuilder.append("NULL,NULL,");
		}
		if(presult!=null&&presult.getOrderBy()!=null&&presult.getOrderBy().trim().length()>0){
			sqlbuilder.append("?,");
			objList.add(presult.getOrderBy());
		}else{
			sqlbuilder.append("NULL,");
		}
		sqlbuilder.append("?)}");	
		List<Integer> types=new ArrayList<Integer>();
		types.add(Types.INTEGER);
		Object[] objArray=new Object[1];
		List<TpOperateInfo> tpoperateinfoList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, TpOperateInfo.class, objArray);
		if(presult!=null&&objArray[0]!=null&&objArray[0].toString().trim().length()>0)
			presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));				
		return tpoperateinfoList;	
	}

    /**
     * 得到本次需要更新的操作记录
     * @param ftime  上次更新的时间
     * @param presult 分页
     * @return
     */
    public List<TpOperateInfo> getSynchroList(String ftime,PageResult presult){
        StringBuilder sqlbuilder = new StringBuilder();
        sqlbuilder.append("{CALL tp_operate_info_proc_synchro_search(");
        List<Object> objList=new ArrayList<Object>();
        if (ftime!= null) {
            sqlbuilder.append("?,");
            objList.add(ftime);
        } else
            sqlbuilder.append("null,");

        if(presult!=null&&presult.getPageNo()>0&&presult.getPageSize()>0){
            sqlbuilder.append("?,?,");
            objList.add(presult.getPageNo());
            objList.add(presult.getPageSize());
        }else{
            sqlbuilder.append("NULL,NULL,");
        }
        if(presult!=null&&presult.getOrderBy()!=null&&presult.getOrderBy().trim().length()>0){
            sqlbuilder.append("?,");
            objList.add(presult.getOrderBy());
        }else{
            sqlbuilder.append("NULL,");
        }
        sqlbuilder.append("?)}");
        List<Integer> types=new ArrayList<Integer>();
        types.add(Types.INTEGER);
        Object[] objArray=new Object[1];
        List<TpOperateInfo> tpoperateinfoList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, TpOperateInfo.class, objArray);
        if(presult!=null&&objArray[0]!=null&&objArray[0].toString().trim().length()>0)
            presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));
        return tpoperateinfoList;
    }
	
	public List<Object> getSaveSql(TpOperateInfo tpoperateinfo, StringBuilder sqlbuilder) {
		if(tpoperateinfo==null || sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL tp_operate_info_proc_add(");
		List<Object>objList = new ArrayList<Object>();
        if (tpoperateinfo.getOperatetype() != null) {
            sqlbuilder.append("?,");
            objList.add(tpoperateinfo.getOperatetype());
        } else
            sqlbuilder.append("null,");
        if (tpoperateinfo.getCourseid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpoperateinfo.getCourseid());
        } else
            sqlbuilder.append("null,");
        if (tpoperateinfo.getRef() != null) {
            sqlbuilder.append("?,");
            objList.add(tpoperateinfo.getRef());
        } else
            sqlbuilder.append("null,");
        if (tpoperateinfo.getDatatype() != null) {
            sqlbuilder.append("?,");
            objList.add(tpoperateinfo.getDatatype());
        } else
            sqlbuilder.append("null,");
        if (tpoperateinfo.getCuserid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpoperateinfo.getCuserid());
        } else
            sqlbuilder.append("null,");
        if (tpoperateinfo.getTargetid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpoperateinfo.getTargetid());
        } else
            sqlbuilder.append("null,");
        if (tpoperateinfo.getReferenceid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpoperateinfo.getReferenceid());
        } else
            sqlbuilder.append("null,");

        sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getDeleteSql(TpOperateInfo tpoperateinfo, StringBuilder sqlbuilder) {
		if(tpoperateinfo==null || sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL tp_operate_info_proc_delete(");
		List<Object>objList = new ArrayList<Object>();
        if (tpoperateinfo.getOperatetype() != null) {
            sqlbuilder.append("?,");
            objList.add(tpoperateinfo.getOperatetype());
        } else
            sqlbuilder.append("null,");
        if (tpoperateinfo.getCourseid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpoperateinfo.getCourseid());
        } else
            sqlbuilder.append("null,");
        if (tpoperateinfo.getRef() != null) {
            sqlbuilder.append("?,");
            objList.add(tpoperateinfo.getRef());
        } else
            sqlbuilder.append("null,");
        if (tpoperateinfo.getDatatype() != null) {
            sqlbuilder.append("?,");
            objList.add(tpoperateinfo.getDatatype());
        } else
            sqlbuilder.append("null,");
        if (tpoperateinfo.getCuserid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpoperateinfo.getCuserid());
        } else
            sqlbuilder.append("null,");
        if (tpoperateinfo.getTargetid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpoperateinfo.getTargetid());
        } else
            sqlbuilder.append("null,");

        sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getUpdateSql(TpOperateInfo tpoperateinfo, StringBuilder sqlbuilder) {
		if(tpoperateinfo==null||sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL tp_operate_info_proc_update(");
		List<Object>objList = new ArrayList<Object>();
        if (tpoperateinfo.getOperatetype() != null) {
            sqlbuilder.append("?,");
            objList.add(tpoperateinfo.getOperatetype());
        } else
            sqlbuilder.append("null,");
        if (tpoperateinfo.getCourseid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpoperateinfo.getCourseid());
        } else
            sqlbuilder.append("null,");
        if (tpoperateinfo.getRef() != null) {
            sqlbuilder.append("?,");
            objList.add(tpoperateinfo.getRef());
        } else
            sqlbuilder.append("null,");
        if (tpoperateinfo.getDatatype() != null) {
            sqlbuilder.append("?,");
            objList.add(tpoperateinfo.getDatatype());
        } else
            sqlbuilder.append("null,");
        if (tpoperateinfo.getCuserid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpoperateinfo.getCuserid());
        } else
            sqlbuilder.append("null,");
        if (tpoperateinfo.getTargetid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpoperateinfo.getTargetid());
        } else
            sqlbuilder.append("null,");

        sqlbuilder.append("?)}");
		return objList; 
	}

	public Boolean doExcetueArrayProc(List<String> sqlArrayList,
			List<List<Object>> objArrayList) {
		return this.executeArray_SQL(sqlArrayList, objArrayList);
	}

}
