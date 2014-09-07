package com.school.dao.teachpaltform;

import com.school.dao.base.CommonDAO;
import com.school.dao.inter.teachpaltform.ITpCourseClassDAO;
import com.school.entity.teachpaltform.TpCourseClass;
import com.school.util.PageResult;
import org.springframework.stereotype.Component;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component  
public class TpCourseClassDAO extends CommonDAO<TpCourseClass> implements ITpCourseClassDAO {

	public Boolean doSave(TpCourseClass tpcourseclass) {
		if (tpcourseclass == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getSaveSql(tpcourseclass, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}
	
	public Boolean doDelete(TpCourseClass tpcourseclass) {
		if(tpcourseclass==null)		
			return false;
		StringBuilder sqlbuilder=new StringBuilder();
		List<Object> objList=getDeleteSql(tpcourseclass, sqlbuilder);
		Object afficeObj=this.executeSacle_PROC(sqlbuilder.toString(), objList.toArray());
		if(afficeObj!=null&&afficeObj.toString().trim().length()>0&&Integer.parseInt(afficeObj.toString())>0){
			return true;
		}return false;
	}

	public Boolean doUpdate(TpCourseClass tpcourseclass) {
		if (tpcourseclass == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getUpdateSql(tpcourseclass, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}
	
	public List<TpCourseClass> getList(TpCourseClass tpcourseclass, PageResult presult) {
		StringBuilder sqlbuilder = new StringBuilder();
		sqlbuilder.append("{CALL tp_j_course_class_proc_split(");
		List<Object> objList=new ArrayList<Object>();
		if (tpcourseclass.getRef() != null) {
			sqlbuilder.append("?,");
			objList.add(tpcourseclass.getRef());
		} else
			sqlbuilder.append("null,");
        if (tpcourseclass.getCourseid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseclass.getCourseid());
        } else
            sqlbuilder.append("null,");
		if (tpcourseclass.getClassid() != null) {
			sqlbuilder.append("?,");
			objList.add(tpcourseclass.getClassid());
		} else
			sqlbuilder.append("null,");
        if (tpcourseclass.getTermid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseclass.getTermid());
        } else
            sqlbuilder.append("null,");
		if (tpcourseclass.getClasstype() != null) {
			sqlbuilder.append("?,");
			objList.add(tpcourseclass.getClasstype());
		} else
			sqlbuilder.append("null,");
        if (tpcourseclass.getBegintime() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseclass.getBegintime());
        } else
            sqlbuilder.append("null,");
		if (tpcourseclass.getUserid() != null) {
			sqlbuilder.append("?,");
			objList.add(tpcourseclass.getUserid());
		} else
			sqlbuilder.append("null,");
        if (tpcourseclass.getSubjectid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseclass.getSubjectid());
        } else
            sqlbuilder.append("null,");
        if (tpcourseclass.getGradeid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseclass.getGradeid());
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
        System.out.println(sqlbuilder.toString());
        for(Object o : objList)
            System.out.println(o.toString());
		List<TpCourseClass> tpcourseclassList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, TpCourseClass.class, objArray);
		if(presult!=null&&objArray[0]!=null&&objArray[0].toString().trim().length()>0)
			presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));				
		return tpcourseclassList;	
	}

    public List<TpCourseClass> getStuList(TpCourseClass tpcourseclass, PageResult presult) {
        StringBuilder sqlbuilder = new StringBuilder();
        sqlbuilder.append("{CALL tp_j_course_class_proc_split(");
        List<Object> objList=new ArrayList<Object>();
        if (tpcourseclass.getRef() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseclass.getRef());
        } else
            sqlbuilder.append("null,");
        if (tpcourseclass.getCourseid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseclass.getCourseid());
        } else
            sqlbuilder.append("null,");
        if (tpcourseclass.getClassid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseclass.getClassid());
        } else
            sqlbuilder.append("null,");
        if (tpcourseclass.getClasstype() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseclass.getClasstype());
        } else
            sqlbuilder.append("null,");
        if (tpcourseclass.getBegintime() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseclass.getBegintime());
        } else
            sqlbuilder.append("null,");
        if (tpcourseclass.getUserid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseclass.getUserid());
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
        List<TpCourseClass> tpcourseclassList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, TpCourseClass.class, objArray);
        if(presult!=null&&objArray[0]!=null&&objArray[0].toString().trim().length()>0)
            presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));
        return tpcourseclassList;
    }

    /**
     * 得到记录，根据班级ID,TERMID
     * 查询列  DISTINCT sub.subject_name,sub.lzx_subject_id,sub.subject_type
     * @param clsid
     * @param termid
     * @return
     *  @author zhengzhou
     */
    public List<TpCourseClass> getTpCourseClassByClsTermId(final Integer clsid,final String termid){
        if(clsid==null||termid==null)return null;
        StringBuilder sqlbuilder=new StringBuilder("{CALL tp_j_course_class_getsub_byclstermid(?,?)}");

        List<Object> objList=new ArrayList<Object>();
        objList.add(clsid);
        objList.add(termid);

        List<Integer> types=new ArrayList<Integer>();
        List<TpCourseClass> tpcourseclassList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, TpCourseClass.class, null);

        return tpcourseclassList;
    }
    /**
     * 得到班级记录，根据userid,TERMID,subjectid
     * 查询列  DISTINCT cls.class_grade,cls.year,cls.class_name
     * @param subjectid
     * @param termid
     * @return
     *  @author zhengzhou
     */
    public List<TpCourseClass> getTpClsEntityByUserSubTermId(final Integer subjectid,final Integer userid,final String termid){
        if(subjectid==null||termid==null||userid==null)return null;
        StringBuilder sqlbuilder=new StringBuilder("{CALL tp_j_course_class_getcls_bysubtermuserid(?,?,?)}");

        List<Object> objList=new ArrayList<Object>();
        objList.add(subjectid);
        objList.add(userid);
        objList.add(termid);

        List<Integer> types=new ArrayList<Integer>();
        List<TpCourseClass> tpcourseclassList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, TpCourseClass.class, null);
        return tpcourseclassList;
    }

	public List<Object> getSaveSql(TpCourseClass tpcourseclass, StringBuilder sqlbuilder) {
		if(tpcourseclass==null || sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL tp_j_course_class_proc_add(");
		List<Object>objList = new ArrayList<Object>();

        if (tpcourseclass.getCourseid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseclass.getCourseid());
        } else
            sqlbuilder.append("null,");
        if (tpcourseclass.getClassid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseclass.getClassid());
        } else
            sqlbuilder.append("null,");
        if (tpcourseclass.getSubjectid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseclass.getSubjectid());
        } else
            sqlbuilder.append("null,");
        if (tpcourseclass.getGradeid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseclass.getGradeid());
        } else
            sqlbuilder.append("null,");
        if (tpcourseclass.getTermid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseclass.getTermid());
        } else
            sqlbuilder.append("null,");
        if (tpcourseclass.getBegintime() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseclass.getBegintimeString());
        } else
            sqlbuilder.append("null,");
        if (tpcourseclass.getEndtime() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseclass.getEndtimeString());
        } else
            sqlbuilder.append("null,");
        if (tpcourseclass.getClasstype() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseclass.getClasstype());
        } else
            sqlbuilder.append("null,");
        if (tpcourseclass.getUserid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseclass.getUserid());
        } else
            sqlbuilder.append("null,");

		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getDeleteSql(TpCourseClass tpcourseclass, StringBuilder sqlbuilder) {
		if(tpcourseclass==null || sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL tp_j_course_class_proc_delete(");
		List<Object>objList = new ArrayList<Object>();
        if (tpcourseclass.getRef() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseclass.getRef());
        } else
            sqlbuilder.append("null,");
        if (tpcourseclass.getCourseid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseclass.getCourseid());
        } else
            sqlbuilder.append("null,");
        if (tpcourseclass.getClassid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseclass.getClassid());
        } else
            sqlbuilder.append("null,");
        if (tpcourseclass.getClasstype() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseclass.getClasstype());
        } else
            sqlbuilder.append("null,");
        if (tpcourseclass.getTermid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseclass.getTermid());
        } else
            sqlbuilder.append("null,");
        if (tpcourseclass.getUserid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseclass.getUserid());
        } else
            sqlbuilder.append("null,");
        if (tpcourseclass.getGradeid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseclass.getGradeid());
        } else
            sqlbuilder.append("null,");
        if (tpcourseclass.getSubjectid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseclass.getSubjectid());
        } else
            sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getUpdateSql(TpCourseClass tpcourseclass, StringBuilder sqlbuilder) {
		if(tpcourseclass==null||sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL tp_j_course_class_proc_update(");
		List<Object>objList = new ArrayList<Object>();
			if (tpcourseclass.getBegintime() != null) {
				sqlbuilder.append("?,");
				objList.add(tpcourseclass.getBegintime());
			} else
				sqlbuilder.append("null,");
			if (tpcourseclass.getRef() != null) {
				sqlbuilder.append("?,");
				objList.add(tpcourseclass.getRef());
			} else
				sqlbuilder.append("null,");
			if (tpcourseclass.getCtime() != null) {
				sqlbuilder.append("?,");
				objList.add(tpcourseclass.getCtime());
			} else
				sqlbuilder.append("null,");
			if (tpcourseclass.getClassid() != null) {
				sqlbuilder.append("?,");
				objList.add(tpcourseclass.getClassid());
			} else
				sqlbuilder.append("null,");
			if (tpcourseclass.getClasstype() != null) {
				sqlbuilder.append("?,");
				objList.add(tpcourseclass.getClasstype());
			} else
				sqlbuilder.append("null,");
			if (tpcourseclass.getUserid() != null) {
				sqlbuilder.append("?,");
				objList.add(tpcourseclass.getUserid());
			} else
				sqlbuilder.append("null,");
			if (tpcourseclass.getCourseid() != null) {
				sqlbuilder.append("?,");
				objList.add(tpcourseclass.getCourseid());
			} else
				sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList; 
	}

    public List<Map<String,Object>> getTpClassCourse(Long courseid,Integer userid,String termid){
        if(courseid==null || userid==null)
            return null;
        StringBuilder sqlbuilder=new StringBuilder("{CALL tp_j_course_class_getclassByusercourseid(");
        List<Object>objList = new ArrayList<Object>();

        if (courseid != null) {
            sqlbuilder.append("?,");
            objList.add(courseid);
        } else
            sqlbuilder.append("null,");
        if (userid!= null) {
            sqlbuilder.append("?,");
            objList.add(userid);
        } else
            sqlbuilder.append("null,");
        if (termid!= null) {
            sqlbuilder.append("?");
            objList.add(termid);
        } else
            sqlbuilder.append("null");

        sqlbuilder.append(")}");
        return this.executeResultListMap_PROC(sqlbuilder.toString(),objList);
    }
	public Boolean doExcetueArrayProc(List<String> sqlArrayList,
			List<List<Object>> objArrayList) {
		return this.executeArray_SQL(sqlArrayList, objArrayList);
	}

	public String getNextId() {
		// TODO Auto-generated method stub
		return null;
	}

}
