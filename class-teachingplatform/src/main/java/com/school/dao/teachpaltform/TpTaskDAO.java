package com.school.dao.teachpaltform;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.school.entity.teachpaltform.TpCourseResource;
import org.springframework.stereotype.Component;

import com.school.dao.base.CommonDAO;
import com.school.entity.teachpaltform.TpTaskInfo;
import com.school.dao.inter.teachpaltform.ITpTaskDAO;
import com.school.util.PageResult;

@Component  
public class TpTaskDAO extends CommonDAO<TpTaskInfo> implements ITpTaskDAO {

	public Boolean doSave(TpTaskInfo tptaskinfo) {
		if (tptaskinfo == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getSaveSql(tptaskinfo, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}
	
	public Boolean doDelete(TpTaskInfo tptaskinfo) {
		if(tptaskinfo==null)		
			return false;
		StringBuilder sqlbuilder=new StringBuilder();
		List<Object> objList=getDeleteSql(tptaskinfo, sqlbuilder);
		Object afficeObj=this.executeSacle_PROC(sqlbuilder.toString(), objList.toArray());
		if(afficeObj!=null&&afficeObj.toString().trim().length()>0&&Integer.parseInt(afficeObj.toString())>0){
			return true;
		}return false;
	}

	public Boolean doUpdate(TpTaskInfo tptaskinfo) {
		if (tptaskinfo == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getUpdateSql(tptaskinfo, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}
	
	public List<TpTaskInfo> getList(TpTaskInfo tptaskinfo, PageResult presult) {
		StringBuilder sqlbuilder = new StringBuilder();
		sqlbuilder.append("{CALL tp_task_info_proc_split(");
		List<Object> objList=new ArrayList<Object>();
        if (tptaskinfo.getTaskid() != null) {
            sqlbuilder.append("?,");
            objList.add(tptaskinfo.getTaskid());
        } else
            sqlbuilder.append("null,");
        if (tptaskinfo.getTaskname() != null) {
            sqlbuilder.append("?,");
            objList.add(tptaskinfo.getTaskname());
        } else
            sqlbuilder.append("null,");
        if (tptaskinfo.getTaskvalueid() != null) {
            sqlbuilder.append("?,");
            objList.add(tptaskinfo.getTaskvalueid());
        } else
            sqlbuilder.append("null,");
        if (tptaskinfo.getTasktype() != null) {
            sqlbuilder.append("?,");
            objList.add(tptaskinfo.getTasktype());
        } else
            sqlbuilder.append("null,");
        if (tptaskinfo.getCuserid() != null) {
            sqlbuilder.append("?,");
            objList.add(tptaskinfo.getCuserid());
        } else
            sqlbuilder.append("null,");
        if (tptaskinfo.getCourseid() != null) {
            sqlbuilder.append("?,");
            objList.add(tptaskinfo.getCourseid());
        } else
            sqlbuilder.append("null,");
        if (tptaskinfo.getCloudstatus() != null) {
            sqlbuilder.append("?,");
            objList.add(tptaskinfo.getCloudstatus());
        } else
            sqlbuilder.append("null,");
        if (tptaskinfo.getTaskremark() != null&&!tptaskinfo.getTaskremark().trim().equals("无")) {
            sqlbuilder.append("?,");
            objList.add(tptaskinfo.getTaskremark());
        } else
            sqlbuilder.append("null,");
        if (tptaskinfo.getQuestiontype() != null) {
            sqlbuilder.append("?,");
            objList.add(tptaskinfo.getQuestiontype());
        } else
            sqlbuilder.append("null,");
        if (tptaskinfo.getCloudtype() != null) {
            sqlbuilder.append("?,");
            objList.add(tptaskinfo.getCloudtype());
        } else
            sqlbuilder.append("null,");
        if (tptaskinfo.getSelecttype() != null) {
            sqlbuilder.append("?,");
            objList.add(tptaskinfo.getSelecttype());
        } else
            sqlbuilder.append("null,");
        if (tptaskinfo.getLoginuserid() != null) {
            sqlbuilder.append("?,");
            objList.add(tptaskinfo.getLoginuserid());
        } else
            sqlbuilder.append("null,");
        if (tptaskinfo.getStatus() != null) {
            sqlbuilder.append("?,");
            objList.add(tptaskinfo.getStatus());
        } else
            sqlbuilder.append("null,");
        if (tptaskinfo.getCriteria() != null) {
            sqlbuilder.append("?,");
            objList.add(tptaskinfo.getCriteria());
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
		List<TpTaskInfo> tptaskinfoList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, TpTaskInfo.class, objArray);
		if(presult!=null&&objArray[0]!=null&&objArray[0].toString().trim().length()>0)
			presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));				
		return tptaskinfoList;	
	}
	
	public List<Object> getSaveSql(TpTaskInfo tptaskinfo, StringBuilder sqlbuilder) {
		if(tptaskinfo==null || sqlbuilder==null)
			return null;
        /**
         *     p_task_id BIGINT,
         p_task_name VARCHAR(1000),
         p_task_value_id BIGINT,
         p_task_type INT,
         p_task_remark VARCHAR(1000),
         p_c_user_id VARCHAR(1000),
         p_course_id BIGINT,
         p_cloud_status INT,

         */
		sqlbuilder.append("{CALL tp_task_info_proc_add(");
		List<Object>objList = new ArrayList<Object>();
            if (tptaskinfo.getTaskid() != null) {
                sqlbuilder.append("?,");
                objList.add(tptaskinfo.getTaskid());
            } else
                sqlbuilder.append("null,");
            if (tptaskinfo.getTaskvalueid() != null) {
                sqlbuilder.append("?,");
                objList.add(tptaskinfo.getTaskvalueid());
            } else
                sqlbuilder.append("null,");
            if (tptaskinfo.getTasktype() != null) {
                sqlbuilder.append("?,");
                objList.add(tptaskinfo.getTasktype());
            } else
                sqlbuilder.append("null,");
			if (tptaskinfo.getTaskname() != null) {
				sqlbuilder.append("?,");
				objList.add(tptaskinfo.getTaskname());
			} else
				sqlbuilder.append("null,");
            if (tptaskinfo.getTaskremark() != null) {
                sqlbuilder.append("?,");
                objList.add(tptaskinfo.getTaskremark());
            } else
                sqlbuilder.append("null,");
            if (tptaskinfo.getCourseid() != null) {
                sqlbuilder.append("?,");
                objList.add(tptaskinfo.getCourseid());
            } else
                sqlbuilder.append("null,");
			if (tptaskinfo.getCuserid() != null) {
				sqlbuilder.append("?,");
				objList.add(tptaskinfo.getCuserid());
			} else
				sqlbuilder.append("null,");
			if (tptaskinfo.getCloudstatus() != null) {
				sqlbuilder.append("?,");
				objList.add(tptaskinfo.getCloudstatus());
			} else
				sqlbuilder.append("null,");
            if (tptaskinfo.getCriteria() != null) {
                sqlbuilder.append("?,");
                objList.add(tptaskinfo.getCriteria());
            } else
                sqlbuilder.append("null,");
            if (tptaskinfo.getOrderidx() != null) {
                sqlbuilder.append("?,");
                objList.add(tptaskinfo.getOrderidx());
            } else
                sqlbuilder.append("null,");
            if (tptaskinfo.getQuesnum() != null) {
                sqlbuilder.append("?,");
                objList.add(tptaskinfo.getQuesnum());
            } else
                sqlbuilder.append("null,");
            if (tptaskinfo.getResourcetype() != null) {
                sqlbuilder.append("?,");
                objList.add(tptaskinfo.getResourcetype());
            } else
                sqlbuilder.append("null,");
            if (tptaskinfo.getRemotetype() != null) {
                sqlbuilder.append("?,");
                objList.add(tptaskinfo.getRemotetype());
            } else
                sqlbuilder.append("null,");
            if (tptaskinfo.getResourcename() != null) {
                sqlbuilder.append("?,");
                objList.add(tptaskinfo.getResourcename());
            } else
                sqlbuilder.append("null,");
            if (tptaskinfo.getImtaskattach() != null) {
                sqlbuilder.append("?,");
                objList.add(tptaskinfo.getImtaskattach());
            } else
                sqlbuilder.append("null,");
            if (tptaskinfo.getImtaskcontent() != null) {
                sqlbuilder.append("?,");
                objList.add(tptaskinfo.getImtaskcontent());
            } else
                sqlbuilder.append("null,");
            if(tptaskinfo.getImtaskanalysis()!=null){
                sqlbuilder.append("?,");
                objList.add(tptaskinfo.getImtaskanalysis());
            }else
                sqlbuilder.append("null,");
            if(tptaskinfo.getImtaskattachtype()!=null){
                sqlbuilder.append("?,");
                objList.add(tptaskinfo.getImtaskattachtype());
            }else
                sqlbuilder.append("null,");
            if(tptaskinfo.getPaperid()!=null){
                sqlbuilder.append("?,");
                objList.add(tptaskinfo.getPaperid());
            }else
                sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList;
	}

    public List<Object> getDeleteSql(TpTaskInfo tptaskinfo, StringBuilder sqlbuilder) {
        if(tptaskinfo==null || sqlbuilder==null)
            return null;
        sqlbuilder.append("{CALL tp_task_info_proc_delete(");
        List<Object>objList = new ArrayList<Object>();
        if (tptaskinfo.getTaskid() != null) {
            sqlbuilder.append("?,");
            objList.add(tptaskinfo.getTaskid());
        } else
            sqlbuilder.append("null,");
        if (tptaskinfo.getCourseid() != null) {
            sqlbuilder.append("?,");
            objList.add(tptaskinfo.getCourseid());
        } else
            sqlbuilder.append("null,");
        sqlbuilder.append("?)}");
        return objList;
    }

    /**
     * 删除任务时，清除任务积分，删除完成记录
     * @param taskid
     * @param sqlbuilder
     * @return
     */
    public List<Object> getDelTpStuTaskScore(final Long taskid, StringBuilder sqlbuilder) {
        if(taskid==null || sqlbuilder==null)
            return null;
        sqlbuilder.append("{CALL del_tp_stu_score_task_award(?,?)}");
        List<Object> objList=new ArrayList<Object>();
        objList.add(taskid);
        return objList;
    }

    @Override
    public List<TpTaskInfo> getTaskRemindList(TpTaskInfo t, PageResult presult) {
        StringBuilder sqlbuilder = new StringBuilder();
        sqlbuilder.append("{CALL tp_task_info_remind_proc_split(");
        List<Object> objList=new ArrayList<Object>();

        if (t.getSelecttype() != null) {
            sqlbuilder.append("?,");
            objList.add(t.getSelecttype());
        } else
            sqlbuilder.append("null,");
        if (t.getTaskid() != null) {
            sqlbuilder.append("?,");
            objList.add(t.getTaskid());
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
        List<TpTaskInfo> tptaskinfoList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, TpTaskInfo.class, objArray);
        if(presult!=null&&objArray[0]!=null&&objArray[0].toString().trim().length()>0)
            presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));
        return tptaskinfoList;
    }


    public List<Object> getUpdateSql(TpTaskInfo tptaskinfo, StringBuilder sqlbuilder) {
		if(tptaskinfo==null||sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL tp_task_info_proc_update(");
		List<Object>objList = new ArrayList<Object>();
        if (tptaskinfo.getTaskid() != null) {
            sqlbuilder.append("?,");
            objList.add(tptaskinfo.getTaskid());
        } else
            sqlbuilder.append("null,");
        if (tptaskinfo.getTaskname() != null) {
            sqlbuilder.append("?,");
            objList.add(tptaskinfo.getTaskname());
        } else
            sqlbuilder.append("null,");
        if (tptaskinfo.getTaskvalueid() != null) {
            sqlbuilder.append("?,");
            objList.add(tptaskinfo.getTaskvalueid());
        } else
            sqlbuilder.append("null,");
        if (tptaskinfo.getTasktype() != null) {
            sqlbuilder.append("?,");
            objList.add(tptaskinfo.getTasktype());
        } else
            sqlbuilder.append("null,");
        if (tptaskinfo.getTaskremark() != null) {
            sqlbuilder.append("?,");
            objList.add(tptaskinfo.getTaskremark());
        } else
            sqlbuilder.append("null,");
        if (tptaskinfo.getCuserid() != null) {
            sqlbuilder.append("?,");
            objList.add(tptaskinfo.getCuserid());
        } else
            sqlbuilder.append("null,");
        if (tptaskinfo.getCourseid() != null) {
            sqlbuilder.append("?,");
            objList.add(tptaskinfo.getCourseid());
        } else
            sqlbuilder.append("null,");
        if (tptaskinfo.getCloudstatus() != null) {
            sqlbuilder.append("?,");
            objList.add(tptaskinfo.getCloudstatus());
        } else
            sqlbuilder.append("null,");
        if (tptaskinfo.getStatus() != null) {
            sqlbuilder.append("?,");
            objList.add(tptaskinfo.getStatus());
        } else
            sqlbuilder.append("null,");
        if (tptaskinfo.getOrderidx() != null) {
            sqlbuilder.append("?,");
            objList.add(tptaskinfo.getOrderidx());
        } else
            sqlbuilder.append("null,");
        if (tptaskinfo.getQuesnum() != null) {
            sqlbuilder.append("?,");
            objList.add(tptaskinfo.getQuesnum());
        } else
            sqlbuilder.append("null,");
        if (tptaskinfo.getResourcetype() != null) {
            sqlbuilder.append("?,");
            objList.add(tptaskinfo.getResourcetype());
        } else
            sqlbuilder.append("null,");
        if (tptaskinfo.getRemotetype() != null) {
            sqlbuilder.append("?,");
            objList.add(tptaskinfo.getRemotetype());
        } else
            sqlbuilder.append("null,");
        if (tptaskinfo.getResourcename() != null) {
            sqlbuilder.append("?,");
            objList.add(tptaskinfo.getResourcename());
        } else
            sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList; 
	}

	
	public String getNextId() {
		// TODO Auto-generated method stub
		return null;
	}

    public List<TpTaskInfo> getTaskReleaseList(TpTaskInfo tptaskinfo, PageResult presult) {
        StringBuilder sqlbuilder = new StringBuilder();
        sqlbuilder.append("{CALL tp_task_release_proc_split(");
        List<Object> objList=new ArrayList<Object>();
        if (tptaskinfo.getTaskid() != null) {
            sqlbuilder.append("?,");
            objList.add(tptaskinfo.getTaskid());
        } else
            sqlbuilder.append("null,");
        if (tptaskinfo.getTaskname() != null) {
            sqlbuilder.append("?,");
            objList.add(tptaskinfo.getTaskname());
        } else
            sqlbuilder.append("null,");
        if (tptaskinfo.getTaskvalueid() != null) {
            sqlbuilder.append("?,");
            objList.add(tptaskinfo.getTaskvalueid());
        } else
            sqlbuilder.append("null,");
        if (tptaskinfo.getTasktype() != null) {
            sqlbuilder.append("?,");
            objList.add(tptaskinfo.getTasktype());
        } else
            sqlbuilder.append("null,");
        if (tptaskinfo.getCuserid() != null) {
            sqlbuilder.append("?,");
            objList.add(tptaskinfo.getCuserid());
        } else
            sqlbuilder.append("null,");
        if (tptaskinfo.getCourseid() != null) {
            sqlbuilder.append("?,");
            objList.add(tptaskinfo.getCourseid());
        } else
            sqlbuilder.append("null,");
        if (tptaskinfo.getCloudstatus() != null) {
            sqlbuilder.append("?,");
            objList.add(tptaskinfo.getCloudstatus());
        } else
            sqlbuilder.append("null,");
        if (tptaskinfo.getSelecttype() != null) {
            sqlbuilder.append("?,");
            objList.add(tptaskinfo.getSelecttype());
        } else
            sqlbuilder.append("null,");
        if (tptaskinfo.getLoginuserid() != null) {
            sqlbuilder.append("?,");
            objList.add(tptaskinfo.getLoginuserid());
        } else
            sqlbuilder.append("null,");
        if (tptaskinfo.getStatus() != null) {
            sqlbuilder.append("?,");
            objList.add(tptaskinfo.getStatus());
        } else
            sqlbuilder.append("null,");
        if (tptaskinfo.getCriteria() != null) {
            sqlbuilder.append("?,");
            objList.add(tptaskinfo.getCriteria());
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
        List<TpTaskInfo> tptaskinfoList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, TpTaskInfo.class, objArray);
        if(presult!=null&&objArray[0]!=null&&objArray[0].toString().trim().length()>0)
            presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));
        return tptaskinfoList;
    }
   public List<TpTaskInfo> getListbyStu(TpTaskInfo tptaskinfo, PageResult presult) {
        StringBuilder sqlbuilder = new StringBuilder();
        if(tptaskinfo.getUserid()==null)
            return null;
        sqlbuilder.append("{CALL tp_get_task_by_stu(");
        List<Object> objList=new ArrayList<Object>();
        if (tptaskinfo.getCourseid() != null) {
            sqlbuilder.append("?,");
            objList.add(tptaskinfo.getCourseid());
        } else
            sqlbuilder.append("null,");
       if (tptaskinfo.getUserid() != null) {
           sqlbuilder.append("?,");
           objList.add(tptaskinfo.getUserid());
       } else
           sqlbuilder.append("null,");
       if (tptaskinfo.getTaskid() != null) {
           sqlbuilder.append("?,");
           objList.add(tptaskinfo.getTaskid());
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
        List<TpTaskInfo> tptaskinfoList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, TpTaskInfo.class, objArray);
        if(presult!=null&&objArray[0]!=null&&objArray[0].toString().trim().length()>0)
            presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));
        return tptaskinfoList;
    }

    @Override
    public List<TpTaskInfo> getUnionListbyStu(TpTaskInfo tptaskinfo, PageResult presult) {
        StringBuilder sqlbuilder = new StringBuilder();
        if(tptaskinfo.getUserid()==null)
            return null;
        sqlbuilder.append("{CALL tp_get_task_by_stu_union(");
        List<Object> objList=new ArrayList<Object>();
        if (tptaskinfo.getCourseid() != null) {
            sqlbuilder.append("?,");
            objList.add(tptaskinfo.getCourseid());
        } else
            sqlbuilder.append("null,");
        if (tptaskinfo.getUserid() != null) {
            sqlbuilder.append("?,");
            objList.add(tptaskinfo.getUserid());
        } else
            sqlbuilder.append("null,");
        if (tptaskinfo.getTaskid() != null) {
            sqlbuilder.append("?,");
            objList.add(tptaskinfo.getTaskid());
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
        List<TpTaskInfo> tptaskinfoList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, TpTaskInfo.class, objArray);
        if(presult!=null&&objArray[0]!=null&&objArray[0].toString().trim().length()>0)
            presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));
        return tptaskinfoList;
    }

    /**
     * 得到同步的SQL
     * @param entity  对象实体
     * @param sqlbuilder  传出的SQL语句，必须实例化后
     * @return
     */
    public List<Object> getSynchroSql(TpTaskInfo entity,StringBuilder sqlbuilder){
        if(entity==null||sqlbuilder==null)return null;
        sqlbuilder.append("{CALL tp_task_info_proc_synchro(");
        List<Object>objList = new ArrayList<Object>();
        if (entity.getTaskid() != null) {
            sqlbuilder.append("?,");
            objList.add(entity.getTaskid());
        } else
            sqlbuilder.append("null,");
        if (entity.getTaskvalueid() != null) {
            sqlbuilder.append("?,");
            objList.add(entity.getTaskvalueid());
        } else
            sqlbuilder.append("null,");
        if (entity.getTasktype() != null) {
            sqlbuilder.append("?,");
            objList.add(entity.getTasktype());
        } else
            sqlbuilder.append("null,");
        if (entity.getTaskname() != null) {
            sqlbuilder.append("?,");
            objList.add(entity.getTaskname());
        } else
            sqlbuilder.append("null,");
        if (entity.getTaskremark() != null) {
            sqlbuilder.append("?,");
            objList.add(entity.getTaskremark());
        } else
            sqlbuilder.append("null,");
        if (entity.getCourseid() != null) {
            sqlbuilder.append("?,");
            objList.add(entity.getCourseid());
        } else
            sqlbuilder.append("null,");
        if (entity.getCuserid() != null) {
            sqlbuilder.append("?,");
            objList.add(entity.getCuserid());
        } else
            sqlbuilder.append("null,");
        if (entity.getCloudstatus() != null) {
            sqlbuilder.append("?,");
            objList.add(entity.getCloudstatus());
        } else
            sqlbuilder.append("null,");
        if (entity.getCriteria() != null) {
            sqlbuilder.append("?,");
            objList.add(entity.getCriteria());
        } else
            sqlbuilder.append("null,");
        sqlbuilder.append("?)}");
        return objList;
    }

    @Override
    public Boolean doSaveTaskMsg(TpTaskInfo t) {
        if (t == null||t.getTaskid()==null||t.getCourseid()==null||t.getCuserid()==null)
            return false;
        StringBuilder sqlbuilder = new StringBuilder("{CALL tp_task_myinfo_add(?,?,?,?)}");
        List<Object> objList = new ArrayList<Object>();
        objList.add(t.getTaskid());
        objList.add(t.getCourseid());
        objList.add(t.getCuserid());
        Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
                objList.toArray());
        if (afficeObj != null && afficeObj.toString().trim().length() > 0
                && Integer.parseInt(afficeObj.toString()) > 0) {
            return true;
        }
        return false;
    }

    public List<TpTaskInfo> getDoTaskResourceId(TpTaskInfo obj) {
        if(obj==null)
            return null;
        StringBuilder sqlbuilder = new StringBuilder();
        sqlbuilder.append("{CALL tp_resource_remote_dotask(");
        List<Object> objList=new ArrayList<Object>();
        if(obj.getCourseid()!=null){
            sqlbuilder.append("?,");
            objList.add(obj.getCourseid());
        }else
            sqlbuilder.append("null,");
        if(obj.getRemotetype()!=null){
            sqlbuilder.append("?");
            objList.add(obj.getRemotetype());
        }else
            sqlbuilder.append("null");
        sqlbuilder.append(")}");
        List<TpTaskInfo> taskList=this.executeResult_PROC(sqlbuilder.toString(),objList,null,TpTaskInfo.class,null);
        return taskList;
    }
}
