package com.school.dao.teachpaltform;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.school.util.UtilTool;
import org.springframework.stereotype.Component;

import com.school.dao.base.CommonDAO;
import com.school.entity.teachpaltform.TpTaskAllotInfo;
import com.school.dao.inter.teachpaltform.ITpTaskAllotDAO;
import com.school.util.PageResult;

@Component  
public class TpTaskAllotDAO extends CommonDAO<TpTaskAllotInfo> implements ITpTaskAllotDAO {

	public Boolean doSave(TpTaskAllotInfo tptaskallotinfo) {
		if (tptaskallotinfo == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getSaveSql(tptaskallotinfo, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}
	
	public Boolean doDelete(TpTaskAllotInfo tptaskallotinfo) {
		if(tptaskallotinfo==null)		
			return false;
		StringBuilder sqlbuilder=new StringBuilder();
		List<Object> objList=getDeleteSql(tptaskallotinfo, sqlbuilder);
		Object afficeObj=this.executeSacle_PROC(sqlbuilder.toString(), objList.toArray());
		if(afficeObj!=null&&afficeObj.toString().trim().length()>0&&Integer.parseInt(afficeObj.toString())>0){
			return true;
		}return false;
	}

	public Boolean doUpdate(TpTaskAllotInfo tptaskallotinfo) {
		if (tptaskallotinfo == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getUpdateSql(tptaskallotinfo, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}

    /**
     * 得到有效的任务数量
     * @param entity
     * @return
     */
    public boolean getYXTkCount(TpTaskAllotInfo entity){
        StringBuilder sqlbuilder=new StringBuilder();
        sqlbuilder.append("{call tp_task_allot_yx_by_tkid_stuid(");
        List<Object> objList=new ArrayList<Object>();
        if(entity==null){
            sqlbuilder.append("?,?,");
        }else{
            if(entity.getTaskid()!=null){
                sqlbuilder.append("?,");
                objList.add(entity.getTaskid());
            }else
                sqlbuilder.append("NULL,");
            if(entity.getUserinfo().getUserid()!=null){
                sqlbuilder.append("?");
                objList.add(entity.getUserinfo().getUserid());
            }else
                sqlbuilder.append("NULL");
        }
        sqlbuilder.append(")}");
       List<Map<String,Object>> mapList=this.executeResultListMap_PROC(sqlbuilder.toString(),objList);
        if(mapList==null||!mapList.get(0).containsKey("TKCOUNT")||
                mapList.get(0)==null||mapList.get(0).get("TKCOUNT")==null
                ||Integer.parseInt(mapList.get(0).get("TKCOUNT").toString())<1)
            return false;
        return true;
    }
	
	public List<TpTaskAllotInfo> getList(TpTaskAllotInfo tptaskallotinfo, PageResult presult) {
		StringBuilder sqlbuilder = new StringBuilder();
		sqlbuilder.append("{CALL tp_task_allot_info_proc_split(");
		List<Object> objList=new ArrayList<Object>();
        if (tptaskallotinfo.getTaskid() != null) {
            sqlbuilder.append("?,");
            objList.add(tptaskallotinfo.getTaskid());
        } else
            sqlbuilder.append("null,");
        if (tptaskallotinfo.getCourseid() != null) {
            sqlbuilder.append("?,");
            objList.add(tptaskallotinfo.getCourseid());
        } else
            sqlbuilder.append("null,");
		if (tptaskallotinfo.getUsertype() != null) {
			sqlbuilder.append("?,");
			objList.add(tptaskallotinfo.getUsertype());
		} else
			sqlbuilder.append("null,");
        if (tptaskallotinfo.getUsertypeid() != null) {
            sqlbuilder.append("?,");
            objList.add(tptaskallotinfo.getUsertypeid());
        } else
            sqlbuilder.append("null,");
		if (tptaskallotinfo.getCuserid() != null) {
			sqlbuilder.append("?,");
			objList.add(tptaskallotinfo.getCuserid());
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
		List<TpTaskAllotInfo> tptaskallotinfoList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, TpTaskAllotInfo.class, objArray);
		if(presult!=null&&objArray[0]!=null&&objArray[0].toString().trim().length()>0)
			presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));				
		return tptaskallotinfoList;	
	}
	
	public List<Object> getSaveSql(TpTaskAllotInfo tptaskallotinfo, StringBuilder sqlbuilder) {
		if(tptaskallotinfo==null || sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL tp_task_allot_info_proc_add(");
		List<Object>objList = new ArrayList<Object>();
            if (tptaskallotinfo.getTaskid() != null) {
                sqlbuilder.append("?,");
                objList.add(tptaskallotinfo.getTaskid());
            } else
                sqlbuilder.append("null,");
            if (tptaskallotinfo.getCourseid() != null) {
                sqlbuilder.append("?,");
                objList.add(tptaskallotinfo.getCourseid());
            } else
                sqlbuilder.append("null,");
            if (tptaskallotinfo.getUsertype() != null) {
                sqlbuilder.append("?,");
                objList.add(tptaskallotinfo.getUsertype());
            } else
                sqlbuilder.append("null,");
			if (tptaskallotinfo.getUsertypeid() != null) {
				sqlbuilder.append("?,");
				objList.add(tptaskallotinfo.getUsertypeid());
			} else
				sqlbuilder.append("null,");
			if (tptaskallotinfo.getBtime() != null) {
				sqlbuilder.append("?,");
				objList.add(UtilTool.DateConvertToString(tptaskallotinfo.getBtime(), UtilTool.DateType.type1));
			} else
				sqlbuilder.append("null,");
            if (tptaskallotinfo.getEtime() != null) {
                sqlbuilder.append("?,");
                objList.add(UtilTool.DateConvertToString(tptaskallotinfo.getEtime(), UtilTool.DateType.type1));
            } else
                sqlbuilder.append("null,");
			if (tptaskallotinfo.getCuserid() != null) {
				sqlbuilder.append("?,");
				objList.add(tptaskallotinfo.getCuserid());
			} else
				sqlbuilder.append("null,");
			if (tptaskallotinfo.getCriteria() != null) {
				sqlbuilder.append("?,");
				objList.add(tptaskallotinfo.getCriteria());
			} else
				sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList;
	}

    public List<Object> getDeleteSql(TpTaskAllotInfo tptaskallotinfo, StringBuilder sqlbuilder) {
        if(tptaskallotinfo==null || sqlbuilder==null)
            return null;
        sqlbuilder.append("{CALL tp_task_allot_info_proc_delete(");
        List<Object>objList = new ArrayList<Object>();
        if (tptaskallotinfo.getTaskid() != null) {
            sqlbuilder.append("?,");
            objList.add(tptaskallotinfo.getTaskid());
        } else
            sqlbuilder.append("null,");
        if (tptaskallotinfo.getCourseid() != null) {
            sqlbuilder.append("?,");
            objList.add(tptaskallotinfo.getCourseid());
        } else
            sqlbuilder.append("null,");
        sqlbuilder.append("?)}");
        return objList;
    }

    /**
     * 根据任务和人，查班级
     * @param taskid
     * @param userid
     * @return
     */
    public List<Map<String,Object>> getTaskAllotBLClassId(final Long taskid,final Integer userid) {
        if(taskid==null || userid==null)
            return null;
        String sql="{CALL tp_task_allot_gl_classid(?,?)}";
        List<Object> objList=new ArrayList<Object>();
        objList.add(taskid);
        objList.add(userid);
        return this.executeResultListMap_PROC(sql,objList);
    }

    @Override
    public List<TpTaskAllotInfo> getTaskRemindObjList(TpTaskAllotInfo tptaskallotinfo, PageResult presult) {
        StringBuilder sqlbuilder = new StringBuilder();
        sqlbuilder.append("{CALL tp_task_remind_allotobj_split(");
        List<Object> objList=new ArrayList<Object>();
        if (tptaskallotinfo.getTaskid() != null) {
            sqlbuilder.append("?,");
            objList.add(tptaskallotinfo.getTaskid());
        } else
            sqlbuilder.append("null,");
        if (tptaskallotinfo.getCourseid() != null) {
            sqlbuilder.append("?,");
            objList.add(tptaskallotinfo.getCourseid());
        } else
            sqlbuilder.append("null,");
        if (tptaskallotinfo.getUsertype() != null) {
            sqlbuilder.append("?,");
            objList.add(tptaskallotinfo.getUsertype());
        } else
            sqlbuilder.append("null,");
        if (tptaskallotinfo.getUsertypeid() != null) {
            sqlbuilder.append("?,");
            objList.add(tptaskallotinfo.getUsertypeid());
        } else
            sqlbuilder.append("null,");
        if (tptaskallotinfo.getCuserid() != null) {
            sqlbuilder.append("?,");
            objList.add(tptaskallotinfo.getCuserid());
        } else
            sqlbuilder.append("null,");
        if (tptaskallotinfo.getSeltype() != null) {
            sqlbuilder.append("?,");
            objList.add(tptaskallotinfo.getSeltype());
        } else
            sqlbuilder.append("null,");
        if (tptaskallotinfo.getRemindstatus() != null) {
            sqlbuilder.append("?,");
            objList.add(tptaskallotinfo.getRemindstatus());
        } else
            sqlbuilder.append("null,");
        if (tptaskallotinfo.getBtime() != null) {
            sqlbuilder.append("?,");
            objList.add(UtilTool.DateConvertToString(tptaskallotinfo.getBtime(), UtilTool.DateType.type1));
        } else
            sqlbuilder.append("null,");
        if (tptaskallotinfo.getEtime() != null) {
            sqlbuilder.append("?,");
            objList.add(UtilTool.DateConvertToString(tptaskallotinfo.getEtime(), UtilTool.DateType.type1));
        } else
            sqlbuilder.append("null,");
        if (tptaskallotinfo.getTasktype() != null) {
            sqlbuilder.append("?,");
            objList.add(tptaskallotinfo.getTasktype());
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
        List<TpTaskAllotInfo> tptaskallotinfoList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, TpTaskAllotInfo.class, objArray);
        if(presult!=null&&objArray[0]!=null&&objArray[0].toString().trim().length()>0)
            presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));
        return tptaskallotinfoList;
    }

    public List<Object> getUpdateSql(TpTaskAllotInfo tptaskallotinfo, StringBuilder sqlbuilder) {
		if(tptaskallotinfo==null||sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL tp_task_allot_info_proc_update(");
		List<Object>objList = new ArrayList<Object>();
			if (tptaskallotinfo.getRef() != null) {
				sqlbuilder.append("?,");
				objList.add(tptaskallotinfo.getRef());
			} else
				sqlbuilder.append("null,");
			if (tptaskallotinfo.getRemindstatus() != null) {
				sqlbuilder.append("?,");
				objList.add(tptaskallotinfo.getRemindstatus());
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

    public List<TpTaskAllotInfo> getTaskByGroup(Long groupid) {
        if(groupid==null)
            return null;
        StringBuilder sqlbuilder = new StringBuilder();
        sqlbuilder.append("{CALL tp_qry_group_for_performance(");
        List<Object>objList = new ArrayList<Object>();
        if(groupid!=null){
            sqlbuilder.append("?");
            objList.add(groupid);
        }else{
            sqlbuilder.append("NULL");
        }
        sqlbuilder.append(")}");
        List<TpTaskAllotInfo> taskList = this.executeResult_PROC(sqlbuilder.toString(),objList,null,TpTaskAllotInfo.class,null);
        return taskList;
    }

    public List<Map<String,Object>> getCompleteNum(Long groupid, Long taskid) {
        if(groupid==null||taskid==null){
            return null;
        }
        StringBuilder sqlbuilder = new StringBuilder();
        sqlbuilder.append("{CALL tp_qry_completenum_for_taskpeople(");
        List<Object>objList = new ArrayList<Object>();
        if(groupid!=null){
            sqlbuilder.append("?,");
            objList.add(groupid);
        }else{
            sqlbuilder.append("NULL,");
        }
        if(taskid!=null){
            sqlbuilder.append("?");
            objList.add(taskid);
        }else{
            sqlbuilder.append("NULL");
        }
        sqlbuilder.append(")}");
        List<Map<String,Object>> list = this.executeResultListMap_PROC(sqlbuilder.toString(),objList);
        return list;
    }

    public List<Map<String,Object>> getNum(Long groupid, Long taskid) {
        if(groupid==null||taskid==null){
            return null;
        }
        StringBuilder sqlbuilder = new StringBuilder();
        sqlbuilder.append("{CALL tp_qry_num_for_taskpeople(");
        List<Object>objList = new ArrayList<Object>();
        if(groupid!=null){
            sqlbuilder.append("?,");
            objList.add(groupid);
        }else{
            sqlbuilder.append("NULL,");
        }
        if(taskid!=null){
            sqlbuilder.append("?");
            objList.add(taskid);
        }else{
            sqlbuilder.append("NULL");
        }
        sqlbuilder.append(")}");
        List<Map<String,Object>> list = this.executeResultListMap_PROC(sqlbuilder.toString(),objList);
        return list;
    }

    /**
     * 根据Userid,Taskid得到当前学生所在的班级
     * @param tk
     * @return
     */
    public List<Map<String,Object>> getClassId(final TpTaskAllotInfo tk) {
        if(tk==null||tk.getUserinfo().getUserid()==null||tk.getTaskid()==null){
            return null;
        }
        StringBuilder sqlbuilder = new StringBuilder();
        sqlbuilder.append("{CALL tp_task_allot_info_proc_searchClsid(");
        List<Object>objList = new ArrayList<Object>();
        if(tk.getUserinfo().getUserid()!=null){
            sqlbuilder.append("?,");
            objList.add(tk.getUserinfo().getUserid());
        }else{
            sqlbuilder.append("NULL,");
        }
        if(tk.getTaskid()!=null){
            sqlbuilder.append("?");
            objList.add(tk.getTaskid());
        }else{
            sqlbuilder.append("NULL");
        }
        sqlbuilder.append(")}");
        return this.executeResultListMap_PROC(sqlbuilder.toString(),objList);

    }


}
