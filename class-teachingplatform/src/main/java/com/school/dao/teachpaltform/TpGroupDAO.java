package com.school.dao.teachpaltform;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.school.dao.base.CommonDAO;
import com.school.entity.teachpaltform.TpGroupInfo;
import com.school.dao.inter.teachpaltform.ITpGroupDAO;
import com.school.util.PageResult;

@Component  
public class TpGroupDAO extends CommonDAO<TpGroupInfo> implements ITpGroupDAO {

	public Boolean doSave(TpGroupInfo tpgroupinfo) {
		if (tpgroupinfo == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getSaveSql(tpgroupinfo, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}
	
	public Boolean doDelete(TpGroupInfo tpgroupinfo) {
		if(tpgroupinfo==null || tpgroupinfo.getGroupid()==null)
			return false;
		StringBuilder sqlbuilder=new StringBuilder();
		List<Object> objList=getDeleteSql(tpgroupinfo, sqlbuilder);
		Object afficeObj=this.executeSacle_PROC(sqlbuilder.toString(), objList.toArray());
		if(afficeObj!=null&&afficeObj.toString().trim().length()>0&&Integer.parseInt(afficeObj.toString())>0){
			return true;
		}return false;
	}

	public Boolean doUpdate(TpGroupInfo tpgroupinfo) {
        if(tpgroupinfo==null || tpgroupinfo.getGroupid()==null)
            return false;

		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getUpdateSql(tpgroupinfo, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}
	
	public List<TpGroupInfo> getList(TpGroupInfo tpgroupinfo, PageResult presult) {
		StringBuilder sqlbuilder = new StringBuilder();
		sqlbuilder.append("{CALL tp_group_info_proc_split(");
		List<Object> objList=new ArrayList<Object>();
        if (tpgroupinfo.getGroupid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpgroupinfo.getGroupid());
        } else
            sqlbuilder.append("null,");
		if (tpgroupinfo.getClassid() != null) {
			sqlbuilder.append("?,");
			objList.add(tpgroupinfo.getClassid());
		} else
			sqlbuilder.append("null,");
        if (tpgroupinfo.getClasstype() != null) {
            sqlbuilder.append("?,");
            objList.add(tpgroupinfo.getClasstype());
        } else
            sqlbuilder.append("null,");
		if (tpgroupinfo.getCuserid() != null) {
			sqlbuilder.append("?,");
			objList.add(tpgroupinfo.getCuserid());
		} else
			sqlbuilder.append("null,");
		if (tpgroupinfo.getGroupname() != null) {
			sqlbuilder.append("?,");
			objList.add(tpgroupinfo.getGroupname());
		} else
			sqlbuilder.append("null,");
        if (tpgroupinfo.getTermid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpgroupinfo.getTermid());
        } else
            sqlbuilder.append("null,");
        if (tpgroupinfo.getUserid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpgroupinfo.getUserid());
        } else
            sqlbuilder.append("null,");
        if (tpgroupinfo.getSubjectid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpgroupinfo.getSubjectid());
        } else
            sqlbuilder.append("null,");
        if (tpgroupinfo.getTaskid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpgroupinfo.getTaskid());
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
		List<TpGroupInfo> tpgroupinfoList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, TpGroupInfo.class, objArray);
		if(presult!=null&&objArray[0]!=null&&objArray[0].toString().trim().length()>0)
			presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));				
		return tpgroupinfoList;	
	}
	
	public List<Object> getSaveSql(TpGroupInfo tpgroupinfo, StringBuilder sqlbuilder) {
		if(tpgroupinfo==null || sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL tp_group_info_proc_add(");
		List<Object>objList = new ArrayList<Object>();
        if (tpgroupinfo.getGroupid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpgroupinfo.getGroupid());
        } else
            sqlbuilder.append("null,");
        if (tpgroupinfo.getClassid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpgroupinfo.getClassid());
        } else
            sqlbuilder.append("null,");
        if (tpgroupinfo.getClasstype() != null) {
            sqlbuilder.append("?,");
            objList.add(tpgroupinfo.getClasstype());
        } else
            sqlbuilder.append("null,");
        if (tpgroupinfo.getCuserid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpgroupinfo.getCuserid());
        } else
            sqlbuilder.append("null,");
        if (tpgroupinfo.getTermid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpgroupinfo.getTermid());
        } else
            sqlbuilder.append("null,");
        if (tpgroupinfo.getGroupname() != null) {
            sqlbuilder.append("?,");
            objList.add(tpgroupinfo.getGroupname());
        } else
            sqlbuilder.append("null,");
        if (tpgroupinfo.getSubjectid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpgroupinfo.getSubjectid());
        } else
            sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getDeleteSql(TpGroupInfo tpgroupinfo, StringBuilder sqlbuilder) {
		if(tpgroupinfo==null || sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL tp_group_info_proc_delete(");
		List<Object>objList = new ArrayList<Object>();
        if (tpgroupinfo.getGroupid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpgroupinfo.getGroupid());
        } else
            sqlbuilder.append("null,");
        if (tpgroupinfo.getClassid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpgroupinfo.getClassid());
        } else
            sqlbuilder.append("null,");
        if (tpgroupinfo.getClasstype() != null) {
            sqlbuilder.append("?,");
            objList.add(tpgroupinfo.getClasstype());
        } else
            sqlbuilder.append("null,");
        if (tpgroupinfo.getCuserid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpgroupinfo.getCuserid());
        } else
            sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getUpdateSql(TpGroupInfo tpgroupinfo, StringBuilder sqlbuilder) {
		if(tpgroupinfo==null||sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL tp_group_info_proc_update(");
		List<Object>objList = new ArrayList<Object>();
        if (tpgroupinfo.getGroupid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpgroupinfo.getGroupid());
        } else
            sqlbuilder.append("null,");
        if (tpgroupinfo.getClassid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpgroupinfo.getClassid());
        } else
            sqlbuilder.append("null,");
        if (tpgroupinfo.getCuserid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpgroupinfo.getCuserid());
        } else
            sqlbuilder.append("null,");
        if (tpgroupinfo.getGroupname() != null) {
            sqlbuilder.append("?,");
            objList.add(tpgroupinfo.getGroupname());
        } else
            sqlbuilder.append("null,");
        if (tpgroupinfo.getSubjectid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpgroupinfo.getSubjectid());
        } else
            sqlbuilder.append("null,");

		sqlbuilder.append("?)}");
		return objList; 
	}

	public Boolean doExcetueArrayProc(List<String> sqlArrayList,
			List<List<Object>> objArrayList) {
		return this.executeArray_SQL(sqlArrayList, objArrayList);
	}
	
	public String getNextId() {
		// TODO Auto-generated method stub
		return null;
	}

    public boolean checkGroupName(TpGroupInfo t) {
        StringBuilder sqlbuilder = new StringBuilder(
                "{CALL tp_group_info_proc_check_groupname(");
        List<Object> objList = new ArrayList<Object>();
        if (t.getTermid() != null) {
            sqlbuilder.append("?,");
            objList.add(t.getTermid());
        } else
            sqlbuilder.append("null,");
        if (t.getCuserid() != null) {
            sqlbuilder.append("?,");
            objList.add(t.getCuserid());
        } else
            sqlbuilder.append("null,");
        if (t.getGroupid() != null) {
            sqlbuilder.append("?,");
            objList.add(t.getGroupid());
        } else
            sqlbuilder.append("null,");
        if (t.getGroupname() != null) {
            sqlbuilder.append("?,");
            objList.add(t.getGroupname());
        } else
            sqlbuilder.append("null,");
        if (t.getClassid() != null) {
            sqlbuilder.append("?,");
            objList.add(t.getClassid());
        } else
            sqlbuilder.append("null,");
        if (t.getClasstype() != null) {
            sqlbuilder.append("?,");
            objList.add(t.getClasstype());
        } else
            sqlbuilder.append("null,");
        if (t.getSubjectid() != null) {
            sqlbuilder.append("?,");
            objList.add(t.getSubjectid());
        } else
            sqlbuilder.append("null,");
        sqlbuilder.append("?)}");
        Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
                objList.toArray());
        if (afficeObj != null && afficeObj.toString().trim().length() > 0
                && Integer.parseInt(afficeObj.toString()) > 0) {
            return true;
        }
        return false;
    }

    public List<TpGroupInfo> getMyGroupList(Integer classid,Integer classtype, String termid, Integer tchid, Integer stuid,Integer subjectid) {
        StringBuilder sqlbuilder = new StringBuilder();
        sqlbuilder.append("{CALL tp_group_proc_getmygroup(");
        List<Object> objList=new ArrayList<Object>();
        if (classid != null) {
            sqlbuilder.append("?,");
            objList.add(classid);
        } else
            sqlbuilder.append("null,");
        if (classtype != null) {
            sqlbuilder.append("?,");
            objList.add(classtype);
        } else
            sqlbuilder.append("null,");
        if (termid != null) {
            sqlbuilder.append("?,");
            objList.add(termid);
        } else
            sqlbuilder.append("null,");
        if (tchid != null) {
            sqlbuilder.append("?,");
            objList.add(tchid);
        } else
            sqlbuilder.append("null,");
        if (stuid != null) {
            sqlbuilder.append("?,");
            objList.add(stuid);
        } else
            sqlbuilder.append("null,");
        if (subjectid != null) {
            sqlbuilder.append("?,");
            objList.add(subjectid);
        } else
            sqlbuilder.append("null,");
        sqlbuilder.append("?)}");
        List<Integer> types=new ArrayList<Integer>();
        types.add(Types.INTEGER);
        Object[] objArray=new Object[1];
        List<TpGroupInfo> tpgroupinfoList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, TpGroupInfo.class, objArray);
        return tpgroupinfoList;
    }

    public List<TpGroupInfo> getGroupBySubject(TpGroupInfo obj) {
        StringBuilder sqlbuilder = new StringBuilder();
        sqlbuilder.append("{CALL tp_group_info_proc_split_bysubject(");
        List<Object> objList=new ArrayList<Object>();
        if(obj==null)
            return null;
        if(obj.getTermid()!=null){
            sqlbuilder.append("?,");
            objList.add(obj.getTermid());
        }else{
            sqlbuilder.append("NULL,");
        }
        if(obj.getClassid()!=null){
            sqlbuilder.append("?,");
            objList.add(obj.getClassid());
        }else{
            sqlbuilder.append("NULL,");
        }
        if(obj.getSubjectid()!=null){
            sqlbuilder.append("?,");
            objList.add(obj.getSubjectid());
        }else{
            sqlbuilder.append("NULL,");
        }
        if(obj.getClasstype()!=null){
            sqlbuilder.append("?");
            objList.add(obj.getClasstype());
        }else{
            sqlbuilder.append("NULL");
        }
        sqlbuilder.append(")}");
        List<TpGroupInfo> groupList = this.executeResult_PROC(sqlbuilder.toString(),objList,null,TpGroupInfo.class,null);
        return groupList;
    }
}
