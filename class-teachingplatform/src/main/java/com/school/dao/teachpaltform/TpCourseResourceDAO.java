package com.school.dao.teachpaltform;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.school.entity.teachpaltform.TpCourseQuestion;
import org.springframework.stereotype.Component;

import com.school.dao.base.CommonDAO;
import com.school.entity.teachpaltform.TpCourseResource;
import com.school.dao.inter.teachpaltform.ITpCourseResourceDAO;
import com.school.util.PageResult;

@Component  
public class TpCourseResourceDAO extends CommonDAO<TpCourseResource> implements ITpCourseResourceDAO {

	public Boolean doSave(TpCourseResource tpcourseresource) {
		if (tpcourseresource == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getSaveSql(tpcourseresource, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}
	
	public Boolean doDelete(TpCourseResource tpcourseresource) {
		if(tpcourseresource==null)		
			return false;
		StringBuilder sqlbuilder=new StringBuilder();
		List<Object> objList=getDeleteSql(tpcourseresource, sqlbuilder);
		Object afficeObj=this.executeSacle_PROC(sqlbuilder.toString(), objList.toArray());
		if(afficeObj!=null&&afficeObj.toString().trim().length()>0&&Integer.parseInt(afficeObj.toString())>0){
			return true;
		}return false;
	}

	public Boolean doUpdate(TpCourseResource tpcourseresource) {
		if (tpcourseresource == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getUpdateSql(tpcourseresource, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}
	
	public List<TpCourseResource> getList(TpCourseResource tpcourseresource, PageResult presult) {
		StringBuilder sqlbuilder = new StringBuilder();
		sqlbuilder.append("{CALL tp_j_course_resource_info_proc_split(");
		List<Object> objList=new ArrayList<Object>();
		if (tpcourseresource.getRef() != null) {
			sqlbuilder.append("?,");
			objList.add(tpcourseresource.getRef());
		} else
			sqlbuilder.append("null,");
		if (tpcourseresource.getResid() != null) {
			sqlbuilder.append("?,");
			objList.add(tpcourseresource.getResid());
		} else
			sqlbuilder.append("null,");
		if (tpcourseresource.getCourseid() != null) {
			sqlbuilder.append("?,");
			objList.add(tpcourseresource.getCourseid());
		} else
			sqlbuilder.append("null,");
        if (tpcourseresource.getResourcetype() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseresource.getResourcetype());
        } else
            sqlbuilder.append("null,");
        if (tpcourseresource.getResstatus() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseresource.getResstatus());
        } else
            sqlbuilder.append("null,");
        if (tpcourseresource.getUsertype() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseresource.getUsertype());
        } else
            sqlbuilder.append("null,");
        if (tpcourseresource.getUserid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseresource.getUserid());
        } else
            sqlbuilder.append("null,");
        if (tpcourseresource.getLocalstatus() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseresource.getLocalstatus());
        } else
            sqlbuilder.append("null,");
        if (tpcourseresource.getTaskflag() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseresource.getTaskflag());
        } else
            sqlbuilder.append("null,");
        if (tpcourseresource.getCurrentcourseid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseresource.getCurrentcourseid());
        } else
            sqlbuilder.append("null,");
        if (tpcourseresource.getDifftype() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseresource.getDifftype());
        } else
            sqlbuilder.append("null,");
        if (tpcourseresource.getSeldatetype() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseresource.getSeldatetype());
        } else
            sqlbuilder.append("null,");
        if (tpcourseresource.getHaspaper() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseresource.getHaspaper());
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
		List<TpCourseResource> tpcourseresourceList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, TpCourseResource.class, objArray);
		if(presult!=null&&objArray[0]!=null&&objArray[0].toString().trim().length()>0)
			presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));				
		return tpcourseresourceList;	
	}
	
	public List<Object> getSaveSql(TpCourseResource tpcourseresource, StringBuilder sqlbuilder) {
		if(tpcourseresource==null || sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL tp_j_course_resource_info_proc_add(");
		List<Object>objList = new ArrayList<Object>();

        if (tpcourseresource.getRef() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseresource.getRef());
        } else
            sqlbuilder.append("null,");
        if (tpcourseresource.getResid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseresource.getResid());
        } else
            sqlbuilder.append("null,");
        if (tpcourseresource.getCourseid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseresource.getCourseid());
        } else
            sqlbuilder.append("null,");
        if (tpcourseresource.getResourcetype()!= null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseresource.getResourcetype());
        } else
            sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getDeleteSql(TpCourseResource tpcourseresource, StringBuilder sqlbuilder) {
		if(tpcourseresource==null || sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL tp_j_course_resource_info_proc_delete(");
		List<Object>objList = new ArrayList<Object>();
            if (tpcourseresource.getRef() != null) {
                sqlbuilder.append("?,");
                objList.add(tpcourseresource.getRef());
            } else
                sqlbuilder.append("null,");
			if (tpcourseresource.getResid() != null) {
				sqlbuilder.append("?,");
				objList.add(tpcourseresource.getResid());
			} else
				sqlbuilder.append("null,");
			if (tpcourseresource.getCourseid() != null) {
				sqlbuilder.append("?,");
				objList.add(tpcourseresource.getCourseid());
			} else
				sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getUpdateSql(TpCourseResource tpcourseresource, StringBuilder sqlbuilder) {
		if(tpcourseresource==null||sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL tp_j_course_resource_info_proc_update(");
		List<Object>objList = new ArrayList<Object>();
            if (tpcourseresource.getRef() != null) {
                sqlbuilder.append("?,");
                objList.add(tpcourseresource.getRef());
            } else
                sqlbuilder.append("null,");
			if (tpcourseresource.getResid() != null) {
				sqlbuilder.append("?,");
				objList.add(tpcourseresource.getResid());
			} else
				sqlbuilder.append("null,");
			if (tpcourseresource.getCourseid() != null) {
				sqlbuilder.append("?,");
				objList.add(tpcourseresource.getCourseid());
			} else
				sqlbuilder.append("null,");
            if (tpcourseresource.getResourcetype() != null) {
                sqlbuilder.append("?,");
                objList.add(tpcourseresource.getResourcetype());
            } else
                sqlbuilder.append("null,");
            if (tpcourseresource.getLocalstatus() != null) {
                sqlbuilder.append("?,");
                objList.add(tpcourseresource.getLocalstatus());
            } else
                sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList; 
	}
    /**
     * 得到同步的SQL
     * @param entity  对象实体
     * @param sqlbuilder  传出的SQL语句，必须实例化后
     * @return
     */
    public List<Object> getSynchroSql(TpCourseResource entity,StringBuilder sqlbuilder){
        if(entity==null||sqlbuilder==null)return null;
        sqlbuilder.append("{CALL tp_j_course_resource_info_proc_synchro(");
        List<Object>objList = new ArrayList<Object>();
        if (entity.getRef() != null) {
            sqlbuilder.append("?,");
            objList.add(entity.getRef());
        } else
            sqlbuilder.append("null,");
        if (entity.getResid() != null) {
            sqlbuilder.append("?,");
            objList.add(entity.getResid());
        } else
            sqlbuilder.append("null,");
        if (entity.getCourseid() != null) {
            sqlbuilder.append("?,");
            objList.add(entity.getCourseid());
        } else
            sqlbuilder.append("null,");
        if (entity.getResourcetype()!= null) {
            sqlbuilder.append("?,");
            objList.add(entity.getResourcetype());
        } else
            sqlbuilder.append("null,");
        sqlbuilder.append("?)}");
        return objList;
    }

    @Override
    public Boolean doAddDynamic(TpCourseResource courseres) {
        if(courseres==null||courseres.getCourseid()==null||courseres.getResid()==null)
            return null;
        StringBuilder sqlbuilder = new StringBuilder();
        sqlbuilder.append("{CALL j_myinfo_proc_add_tpres_dynamic(?,?,?)}");
        List<Object> objList=new ArrayList<Object>();
        objList.add(courseres.getCourseid());
        objList.add(courseres.getResid());
        List<Integer> types=new ArrayList<Integer>();
        types.add(Types.INTEGER);
        Object obj=this.executeSacle_PROC(sqlbuilder.toString(), objList.toArray());
        if(obj!=null&&obj.toString().trim().length()>0&&Integer.parseInt(obj.toString())>0)
            return true;
        return false;
    }

    public List<TpCourseResource> getResourceForRelatedCourse(TpCourseResource tpcourseresource, PageResult presult) {
        StringBuilder sqlbuilder = new StringBuilder();
        sqlbuilder.append("{CALL tp_j_course_resource_info_proc_forrelated(");
        List<Object> objList=new ArrayList<Object>();
        if (tpcourseresource.getRef() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseresource.getRef());
        } else
            sqlbuilder.append("null,");
        if (tpcourseresource.getResid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseresource.getResid());
        } else
            sqlbuilder.append("null,");
        if (tpcourseresource.getCourseids() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseresource.getCourseids());
        } else
            sqlbuilder.append("null,");
        if (tpcourseresource.getTaskCourseid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseresource.getTaskCourseid());
        } else
            sqlbuilder.append("null,");
        if (tpcourseresource.getResourcetype() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseresource.getResourcetype());
        } else
            sqlbuilder.append("null,");
        if (tpcourseresource.getResstatus() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseresource.getResstatus());
        } else
            sqlbuilder.append("null,");
        if (tpcourseresource.getUsertype() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseresource.getUsertype());
        } else
            sqlbuilder.append("null,");
        if (tpcourseresource.getUserid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseresource.getUserid());
        } else
            sqlbuilder.append("null,");
        if (tpcourseresource.getLocalstatus() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseresource.getLocalstatus());
        } else
            sqlbuilder.append("null,");
        if (tpcourseresource.getTaskflag() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseresource.getTaskflag());
        } else
            sqlbuilder.append("null,");
        if (tpcourseresource.getCurrentcourseid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseresource.getCurrentcourseid());
        } else
            sqlbuilder.append("null,");
        if (tpcourseresource.getDifftype() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseresource.getDifftype());
        } else
            sqlbuilder.append("null,");
        if (tpcourseresource.getHaspaper() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseresource.getHaspaper());
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
        List<TpCourseResource> tpcourseresourceList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, TpCourseResource.class, objArray);
        if(presult!=null&&objArray[0]!=null&&objArray[0].toString().trim().length()>0)
            presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));
        return tpcourseresourceList;
    }

    public List<TpCourseResource> getLikeResource(Integer gradeid, Integer subjectid, String name, PageResult presult) {
        StringBuilder sqlbuilder = new StringBuilder();
        sqlbuilder.append("{CALL tp_task_resource_by_values(");
        List<Object> objList=new ArrayList<Object>();
        if(gradeid!=null){
            sqlbuilder.append("?,");
            objList.add(gradeid);
        }else
            sqlbuilder.append("null,");
        if(subjectid!=null){
            sqlbuilder.append("?,");
            objList.add(subjectid);
        }else
            sqlbuilder.append("null,");
        if(name!=null){
            sqlbuilder.append("?,");
            objList.add(name);
        }else
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
        List<TpCourseResource> tpcourseresourceList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, TpCourseResource.class, objArray);
        if(presult!=null&&objArray[0]!=null&&objArray[0].toString().trim().length()>0)
            presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));
        return tpcourseresourceList;
    }

    public List<TpCourseResource> getResourceUnion(TpCourseResource tpcourseresource, PageResult presult) {
        StringBuilder sqlbuilder = new StringBuilder();
        sqlbuilder.append("{CALL tp_j_course_resource_info_proc_forrelated(");
        List<Object> objList=new ArrayList<Object>();
        if(tpcourseresource.getCourseid()!=null){
            sqlbuilder.append("?,");
            objList.add(tpcourseresource.getCourseid());
        }else{
            sqlbuilder.append("null,");
        }
        if(tpcourseresource.getTaskflag()!=null){
            sqlbuilder.append("?,");
            objList.add(tpcourseresource.getTaskflag());
        }else{
            sqlbuilder.append("null,");
        }

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
        List<TpCourseResource> tpcourseresourceList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, TpCourseResource.class, objArray);
        if(presult!=null&&objArray[0]!=null&&objArray[0].toString().trim().length()>0)
            presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));
        return tpcourseresourceList;
    }

    public String getNextId() {
		// TODO Auto-generated method stub
		return null;
	}

}
