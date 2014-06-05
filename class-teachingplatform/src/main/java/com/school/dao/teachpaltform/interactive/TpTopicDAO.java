package com.school.dao.teachpaltform.interactive;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.school.dao.base.CommonDAO;
import com.school.entity.teachpaltform.interactive.TpTopicInfo;
import com.school.dao.inter.teachpaltform.interactive.ITpTopicDAO;
import com.school.util.PageResult;

@Component
public class TpTopicDAO extends CommonDAO<TpTopicInfo> implements ITpTopicDAO {

	public Boolean doSave(TpTopicInfo tptopicinfo) {
		if (tptopicinfo == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getSaveSql(tptopicinfo, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}

	public Boolean doDelete(TpTopicInfo tptopicinfo) {
		if(tptopicinfo==null)
			return false;
		StringBuilder sqlbuilder=new StringBuilder();
		List<Object> objList=getDeleteSql(tptopicinfo, sqlbuilder);
		Object afficeObj=this.executeSacle_PROC(sqlbuilder.toString(), objList.toArray());
		if(afficeObj!=null&&afficeObj.toString().trim().length()>0&&Integer.parseInt(afficeObj.toString())>0){
			return true;
		}return false;
	}

	public Boolean doUpdate(TpTopicInfo tptopicinfo) {
		if (tptopicinfo == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getUpdateSql(tptopicinfo, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}

	public List<TpTopicInfo> getList(TpTopicInfo tptopicinfo, PageResult presult) {
		StringBuilder sqlbuilder = new StringBuilder();
		sqlbuilder.append("{CALL tp_topic_info_proc_split(");
		List<Object> objList=new ArrayList<Object>();
		if (tptopicinfo.getOrderidx() != null) {
			sqlbuilder.append("?,");
			objList.add(tptopicinfo.getOrderidx());
		} else
			sqlbuilder.append("null,");
		if (tptopicinfo.getTopickeyword() != null) {
			sqlbuilder.append("?,");
			objList.add(tptopicinfo.getTopickeyword());
		} else
			sqlbuilder.append("null,");

		if (tptopicinfo.getStatus() != null) {
			sqlbuilder.append("?,");
			objList.add(tptopicinfo.getStatus());
		} else
			sqlbuilder.append("null,");
		if (tptopicinfo.getTopictitle() != null) {
			sqlbuilder.append("?,");
			objList.add(tptopicinfo.getTopictitle());
		} else
			sqlbuilder.append("null,");
		if (tptopicinfo.getCuserid() != null) {
			sqlbuilder.append("?,");
			objList.add(tptopicinfo.getCuserid());
		} else
			sqlbuilder.append("null,");
		if (tptopicinfo.getTopiccontent() != null) {
			sqlbuilder.append("?,");
			objList.add(tptopicinfo.getTopiccontent());
		} else
			sqlbuilder.append("null,");
		if (tptopicinfo.getCourseid() != null) {
			sqlbuilder.append("?,");
			objList.add(tptopicinfo.getCourseid());
		} else
			sqlbuilder.append("null,");
		if (tptopicinfo.getCloudstatus() != null) {
			sqlbuilder.append("?,");
			objList.add(tptopicinfo.getCloudstatus());
		} else
			sqlbuilder.append("null,");
        if (tptopicinfo.getTopicid() != null) {
            sqlbuilder.append("?,");
            objList.add(tptopicinfo.getTopicid());
        } else
            sqlbuilder.append("null,");
        if (tptopicinfo.getLoginuserid() != null) {
            sqlbuilder.append("?,");
            objList.add(tptopicinfo.getLoginuserid());
        } else
            sqlbuilder.append("null,");
        if (tptopicinfo.getSelectType() != null) {
            sqlbuilder.append("?,");
            objList.add(tptopicinfo.getSelectType());
        } else
            sqlbuilder.append("null,");
        if (tptopicinfo.getQuoteid() != null) {
			sqlbuilder.append("?,");
			objList.add(tptopicinfo.getQuoteid());
		} else
			sqlbuilder.append("null,");
        if (tptopicinfo.getFlag() != null) {
            sqlbuilder.append("?,");
            objList.add(tptopicinfo.getFlag());
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
		List<TpTopicInfo> tptopicinfoList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, TpTopicInfo.class, objArray);
		if(presult!=null&&objArray[0]!=null&&objArray[0].toString().trim().length()>0)
			presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));
		return tptopicinfoList;
	}

	public List<Object>  getSaveSql(TpTopicInfo tptopicinfo, StringBuilder sqlbuilder) {
		if(tptopicinfo==null || sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL tp_topic_info_proc_add(");
		List<Object>objList = new ArrayList<Object>();
			if (tptopicinfo.getOrderidx() != null) {
				sqlbuilder.append("?,");
				objList.add(tptopicinfo.getOrderidx());
			} else
				sqlbuilder.append("null,");
			if (tptopicinfo.getTopickeyword() != null) {
				sqlbuilder.append("?,");
				objList.add(tptopicinfo.getTopickeyword());
			} else
				sqlbuilder.append("null,");

			if (tptopicinfo.getStatus() != null) {
				sqlbuilder.append("?,");
				objList.add(tptopicinfo.getStatus());
			} else
				sqlbuilder.append("null,");
			if (tptopicinfo.getTopictitle() != null) {
				sqlbuilder.append("?,");
				objList.add(tptopicinfo.getTopictitle());
			} else
				sqlbuilder.append("null,");
			if (tptopicinfo.getCuserid() != null) {
				sqlbuilder.append("?,");
				objList.add(tptopicinfo.getCuserid());
			} else
				sqlbuilder.append("null,");
			if (tptopicinfo.getTopiccontent() != null) {
				sqlbuilder.append("?,");
				objList.add(tptopicinfo.getTopiccontent());
			} else
				sqlbuilder.append("null,");
			if (tptopicinfo.getCourseid() != null) {
				sqlbuilder.append("?,");
				objList.add(tptopicinfo.getCourseid());
			} else
				sqlbuilder.append("null,");
			if (tptopicinfo.getCloudstatus() != null) {
				sqlbuilder.append("?,");
				objList.add(tptopicinfo.getCloudstatus());
			} else
				sqlbuilder.append("null,");
			if (tptopicinfo.getTopicid() != null) {
				sqlbuilder.append("?,");
				objList.add(tptopicinfo.getTopicid());
			} else
				sqlbuilder.append("null,");
			if (tptopicinfo.getQuoteid() != null) {
				sqlbuilder.append("?,");
				objList.add(tptopicinfo.getQuoteid());
			} else
				sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getDeleteSql(TpTopicInfo tptopicinfo, StringBuilder sqlbuilder) {
		if(tptopicinfo==null || sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL tp_topic_info_proc_delete(");
		List<Object>objList = new ArrayList<Object>();
        if (tptopicinfo.getOrderidx() != null) {
            sqlbuilder.append("?,");
            objList.add(tptopicinfo.getOrderidx());
        } else
            sqlbuilder.append("null,");
        if (tptopicinfo.getTopickeyword() != null) {
            sqlbuilder.append("?,");
            objList.add(tptopicinfo.getTopickeyword());
        } else
            sqlbuilder.append("null,");

        if (tptopicinfo.getStatus() != null) {
            sqlbuilder.append("?,");
            objList.add(tptopicinfo.getStatus());
        } else
            sqlbuilder.append("null,");
        if (tptopicinfo.getTopictitle() != null) {
            sqlbuilder.append("?,");
            objList.add(tptopicinfo.getTopictitle());
        } else
            sqlbuilder.append("null,");
        if (tptopicinfo.getCuserid() != null) {
            sqlbuilder.append("?,");
            objList.add(tptopicinfo.getCuserid());
        } else
            sqlbuilder.append("null,");
        if (tptopicinfo.getTopiccontent() != null) {
            sqlbuilder.append("?,");
            objList.add(tptopicinfo.getTopiccontent());
        } else
            sqlbuilder.append("null,");
        if (tptopicinfo.getCourseid() != null) {
            sqlbuilder.append("?,");
            objList.add(tptopicinfo.getCourseid());
        } else
            sqlbuilder.append("null,");
        if (tptopicinfo.getCloudstatus() != null) {
            sqlbuilder.append("?,");
            objList.add(tptopicinfo.getCloudstatus());
        } else
            sqlbuilder.append("null,");
        if (tptopicinfo.getTopicid() != null) {
            sqlbuilder.append("?,");
            objList.add(tptopicinfo.getTopicid());
        } else
            sqlbuilder.append("null,");
        if (tptopicinfo.getQuoteid() != null) {
			sqlbuilder.append("?,");
			objList.add(tptopicinfo.getQuoteid());
		} else
			sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getUpdateSql(TpTopicInfo tptopicinfo, StringBuilder sqlbuilder) {
		if(tptopicinfo==null||sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL tp_topic_info_proc_update(");
		List<Object>objList = new ArrayList<Object>();
        if (tptopicinfo.getOrderidx() != null) {
            sqlbuilder.append("?,");
            objList.add(tptopicinfo.getOrderidx());
        } else
            sqlbuilder.append("null,");
        if (tptopicinfo.getTopickeyword() != null) {
            sqlbuilder.append("?,");
            objList.add(tptopicinfo.getTopickeyword());
        } else
            sqlbuilder.append("null,");

        if (tptopicinfo.getStatus() != null) {
            sqlbuilder.append("?,");
            objList.add(tptopicinfo.getStatus());
        } else
            sqlbuilder.append("null,");
        if (tptopicinfo.getTopictitle() != null) {
            sqlbuilder.append("?,");
            objList.add(tptopicinfo.getTopictitle());
        } else
            sqlbuilder.append("null,");
        if (tptopicinfo.getCuserid() != null) {
            sqlbuilder.append("?,");
            objList.add(tptopicinfo.getCuserid());
        } else
            sqlbuilder.append("null,");
        if (tptopicinfo.getTopiccontent() != null) {
            sqlbuilder.append("?,");
            objList.add(tptopicinfo.getTopiccontent());
        } else
            sqlbuilder.append("null,");
        if (tptopicinfo.getCourseid() != null) {
            sqlbuilder.append("?,");
            objList.add(tptopicinfo.getCourseid());
        } else
            sqlbuilder.append("null,");
        if (tptopicinfo.getCloudstatus() != null) {
            sqlbuilder.append("?,");
            objList.add(tptopicinfo.getCloudstatus());
        } else
            sqlbuilder.append("null,");
        if (tptopicinfo.getTopicid() != null) {
            sqlbuilder.append("?,");
            objList.add(tptopicinfo.getTopicid());
        } else
            sqlbuilder.append("null,");
        if (tptopicinfo.getQuoteid() != null) {
			sqlbuilder.append("?,");
			objList.add(tptopicinfo.getQuoteid());
		} else
			sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList;
	}

    /**
     * 得到互动空间首页显示的专题栏目
     * @param userref
     * @param termid
     * @return
     */
    public List<Map<String,Object>> getListTopicIndex(String userref,String termid){
        StringBuilder sqlbuilder=new StringBuilder("{CALL tp_course_info_proc_tpcidx(");
        List<Object> objList=new ArrayList<Object>();
        if(userref==null||userref.trim().length()<1)
            sqlbuilder.append("NULL,");
        else{
            sqlbuilder.append("?,");
            objList.add(userref);
        }
        if(termid==null)
            sqlbuilder.append("NULL,");
        else{
            sqlbuilder.append("?,");
            objList.add(termid);
        }
       sqlbuilder.append(")}");
        return this.executeResultListMap_PROC(sqlbuilder.toString(),objList);
    }

	public String getNextId() {
		// TODO Auto-generated method stub
		return null;
	}


    /**
     * 得到同步的SQL
     * @param entity  对象实体
     * @param sqlbuilder  传出的SQL语句，必须实例化后
     * @return
     */
    public List<Object> getSynchroSql(TpTopicInfo entity,StringBuilder sqlbuilder){
        if(entity==null||sqlbuilder==null)return null;
        sqlbuilder.append("{CALL tp_topic_info_proc_synchro(");
        List<Object>objList = new ArrayList<Object>();
        if (entity.getOrderidx() != null) {
            sqlbuilder.append("?,");
            objList.add(entity.getOrderidx());
        } else
            sqlbuilder.append("null,");
        if (entity.getTopickeyword() != null) {
            sqlbuilder.append("?,");
            objList.add(entity.getTopickeyword());
        } else
            sqlbuilder.append("null,");

        if (entity.getStatus() != null) {
            sqlbuilder.append("?,");
            objList.add(entity.getStatus());
        } else
            sqlbuilder.append("null,");
        if (entity.getTopictitle() != null) {
            sqlbuilder.append("?,");
            objList.add(entity.getTopictitle());
        } else
            sqlbuilder.append("null,");
        if (entity.getCuserid() != null) {
            sqlbuilder.append("?,");
            objList.add(entity.getCuserid());
        } else
            sqlbuilder.append("null,");
        if (entity.getTopiccontent() != null) {
            sqlbuilder.append("?,");
            objList.add(entity.getTopiccontent());
        } else
            sqlbuilder.append("null,");
        if (entity.getCourseid() != null) {
            sqlbuilder.append("?,");
            objList.add(entity.getCourseid());
        } else
            sqlbuilder.append("null,");
        if (entity.getCloudstatus() != null) {
            sqlbuilder.append("?,");
            objList.add(entity.getCloudstatus());
        } else
            sqlbuilder.append("null,");
        if (entity.getTopicid() != null) {
            sqlbuilder.append("?,");
            objList.add(entity.getTopicid());
        } else
            sqlbuilder.append("null,");
        sqlbuilder.append("?)}");
        return objList;
    }
    /**
     * 根据CourseId得到互动空间的统计
     * @param courseid
     * @return
     */    
    public List<Map<String,Object>> getTopicStaticesPageIdx(Long courseid,String loginref,Integer roleInt){
    	if(courseid==null)return null;
    	StringBuilder sqlbuilder=new StringBuilder("{CALL interactive_statices_pageidx(?,?,?)}");
    	List<Object> objlist=new ArrayList<Object>();
        objlist.add(courseid);
        objlist.add(loginref);
        objlist.add(roleInt);
    	return this.executeResultListMap_PROC(sqlbuilder.toString(), objlist);
    	
    }

    /**
     *得到论题统计页面数据
     * @param topicid
     * @param clsid
     * @return
     */
    public List<Map<String,Object>> getTopicStatices(Long topicid,Integer clsid,Integer clstype){
        if(topicid==null||clsid==null)return null;
        StringBuilder sqlbuilder=new StringBuilder("{CALL tp_topic_info_statices(?,?,?)}");
        List<Object> objlist=new ArrayList<Object>();
        objlist.add(clsid);
        objlist.add(topicid);
        objlist.add(clstype);
        return this.executeResultListMap_PROC(sqlbuilder.toString(), objlist);
    }

    /**
     *得到互动空间班级信息
     * @param courseid
     * @param userid
     * @param roletype
     * @return
     */
    public List<Map<String,Object>> getInteractiveClass(Long courseid,Integer userid,Integer roletype){
        if(userid==null||courseid==null||roletype==null)return null;
        StringBuilder sqlbuilder=new StringBuilder("{CALL tp_interactive_getclass(?,?,?)}");
        List<Object> objlist=new ArrayList<Object>();
        objlist.add(courseid);
        objlist.add(userid);
        objlist.add(roletype);
        return this.executeResultListMap_PROC(sqlbuilder.toString(), objlist);
    }
}
