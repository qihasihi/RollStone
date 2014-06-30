package com.school.dao.teachpaltform;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.school.entity.teachpaltform.TpCourseInfo;
import org.springframework.stereotype.Component;

import com.school.dao.base.CommonDAO;
import com.school.entity.teachpaltform.TpCourseQuestion;
import com.school.dao.inter.teachpaltform.ITpCourseQuestionDAO;
import com.school.util.PageResult;

@Component  
public class TpCourseQuestionDAO extends CommonDAO<TpCourseQuestion> implements ITpCourseQuestionDAO {

	public Boolean doSave(TpCourseQuestion tpcoursequestion) {
		if (tpcoursequestion == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getSaveSql(tpcoursequestion, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}
	
	public Boolean doDelete(TpCourseQuestion tpcoursequestion) {
		if(tpcoursequestion==null)		
			return false;
		StringBuilder sqlbuilder=new StringBuilder();
		List<Object> objList=getDeleteSql(tpcoursequestion, sqlbuilder);
		Object afficeObj=this.executeSacle_PROC(sqlbuilder.toString(), objList.toArray());
		if(afficeObj!=null&&afficeObj.toString().trim().length()>0&&Integer.parseInt(afficeObj.toString())>0){
			return true;
		}return false;
	}

	public Boolean doUpdate(TpCourseQuestion tpcoursequestion) {
		if (tpcoursequestion == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getUpdateSql(tpcoursequestion, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}
	
	public List<TpCourseQuestion> getList(TpCourseQuestion tpcoursequestion, PageResult presult) {
		StringBuilder sqlbuilder = new StringBuilder();
		sqlbuilder.append("{CALL tp_j_course_question_info_proc_split(");
		List<Object> objList=new ArrayList<Object>();
        if (tpcoursequestion.getRef() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcoursequestion.getRef());
        } else
            sqlbuilder.append("null,");
        if (tpcoursequestion.getUserid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcoursequestion.getUserid());
        } else
            sqlbuilder.append("null,");
		if (tpcoursequestion.getQuestionid() != null) {
			sqlbuilder.append("?,");
			objList.add(tpcoursequestion.getQuestionid());
		} else
			sqlbuilder.append("null,");
		if (tpcoursequestion.getCourseid() != null) {
			sqlbuilder.append("?,");
			objList.add(tpcoursequestion.getCourseid());
		} else
			sqlbuilder.append("null,");
        if (tpcoursequestion.getQuestiontype() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcoursequestion.getQuestiontype());
        } else
            sqlbuilder.append("null,");
        if (tpcoursequestion.getStatus() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcoursequestion.getStatus());
        } else
            sqlbuilder.append("null,");
        if (tpcoursequestion.getLocalstatus() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcoursequestion.getLocalstatus());
        } else
            sqlbuilder.append("null,");
        if (tpcoursequestion.getFlag() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcoursequestion.getFlag());
        } else
            sqlbuilder.append("null,");
        if (tpcoursequestion.getCoursename() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcoursequestion.getCoursename());
        } else
            sqlbuilder.append("null,");
        if (tpcoursequestion.getPaperid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcoursequestion.getPaperid());
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
		List<TpCourseQuestion> tpcoursequestionList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, TpCourseQuestion.class, objArray);
		if(presult!=null&&objArray[0]!=null&&objArray[0].toString().trim().length()>0)
			presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));				
		return tpcoursequestionList;	
	}
	
	public List<Object> getSaveSql(TpCourseQuestion tpcoursequestion, StringBuilder sqlbuilder) {
		if(tpcoursequestion==null || sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL tp_j_course_question_info_proc_add(");
		List<Object>objList = new ArrayList<Object>();

            if (tpcoursequestion.getRef() != null) {
                sqlbuilder.append("?,");
                objList.add(tpcoursequestion.getRef());
            } else
                sqlbuilder.append("null,");
			if (tpcoursequestion.getQuestionid() != null) {
				sqlbuilder.append("?,");
				objList.add(tpcoursequestion.getQuestionid());
			} else
				sqlbuilder.append("null,");
			if (tpcoursequestion.getCourseid() != null) {
				sqlbuilder.append("?,");
				objList.add(tpcoursequestion.getCourseid());
			} else
				sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getDeleteSql(TpCourseQuestion tpcoursequestion, StringBuilder sqlbuilder) {
		if(tpcoursequestion==null || sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL tp_j_course_question_info_proc_delete(");
		List<Object>objList = new ArrayList<Object>();
			if (tpcoursequestion.getQuestionid() != null) {
				sqlbuilder.append("?,");
				objList.add(tpcoursequestion.getQuestionid());
			} else
				sqlbuilder.append("null,");
			if (tpcoursequestion.getCourseid() != null) {
				sqlbuilder.append("?,");
				objList.add(tpcoursequestion.getCourseid());
			} else
				sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getUpdateSql(TpCourseQuestion tpcoursequestion, StringBuilder sqlbuilder) {
		if(tpcoursequestion==null||sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL tp_j_course_question_info_proc_update(");
		List<Object>objList = new ArrayList<Object>();
            if (tpcoursequestion.getRef() != null) {
                sqlbuilder.append("?,");
                objList.add(tpcoursequestion.getRef());
            } else
                sqlbuilder.append("null,");
			if (tpcoursequestion.getQuestionid() != null) {
				sqlbuilder.append("?,");
				objList.add(tpcoursequestion.getQuestionid());
			} else
				sqlbuilder.append("null,");

			if (tpcoursequestion.getCourseid() != null) {
				sqlbuilder.append("?,");
				objList.add(tpcoursequestion.getCourseid());
			} else
				sqlbuilder.append("null,");
            if (tpcoursequestion.getLocalstatus() != null) {
                sqlbuilder.append("?,");
                objList.add(tpcoursequestion.getLocalstatus());
            } else
                sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList; 
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
    public List<Object> getSynchroSql(TpCourseQuestion entity,StringBuilder sqlbuilder){
        if(entity==null||sqlbuilder==null)return null;
        List<Object> objList=new ArrayList<Object>();
        sqlbuilder.append("{CALL tp_j_course_question_info_proc_synchro(");
        if (entity.getQuestionid() != null) {
            sqlbuilder.append("?,");
            objList.add(entity.getQuestionid());
        } else
            sqlbuilder.append("null,");
        if (entity.getCourseid() != null) {
            sqlbuilder.append("?,");
            objList.add(entity.getCourseid());
        } else
            sqlbuilder.append("null,");
        sqlbuilder.append("?)}");

        return objList;
    }
}
