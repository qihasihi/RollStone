package com.school.dao.teachpaltform;

import com.school.dao.base.CommonDAO;
import com.school.dao.inter.teachpaltform.ITpCourseClassDAO;
import com.school.entity.teachpaltform.TpCourseClass;
import com.school.util.PageResult;
import org.springframework.stereotype.Component;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

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

	public Boolean doExcetueArrayProc(List<String> sqlArrayList,
			List<List<Object>> objArrayList) {
		return this.executeArray_SQL(sqlArrayList, objArrayList);
	}
	
	public String getNextId() {
		// TODO Auto-generated method stub
		return null;
	}

}
