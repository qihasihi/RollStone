package com.school.dao.teachpaltform.paper;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.school.dao.base.CommonDAO;
import com.school.entity.teachpaltform.paper.TpCoursePaper;
import com.school.dao.inter.teachpaltform.paper.ITpCoursePaperDAO;
import com.school.util.PageResult;

@Component  
public class TpCoursePaperDAO extends CommonDAO<TpCoursePaper> implements ITpCoursePaperDAO {

	public Boolean doSave(TpCoursePaper tpcoursepaper) {
		if (tpcoursepaper == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getSaveSql(tpcoursepaper, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}
	
	public Boolean doDelete(TpCoursePaper tpcoursepaper) {
		if(tpcoursepaper==null)		
			return false;
		StringBuilder sqlbuilder=new StringBuilder();
		List<Object> objList=getDeleteSql(tpcoursepaper, sqlbuilder);
		Object afficeObj=this.executeSacle_PROC(sqlbuilder.toString(), objList.toArray());
		if(afficeObj!=null&&afficeObj.toString().trim().length()>0&&Integer.parseInt(afficeObj.toString())>0){
			return true;
		}return false;
	}

	public Boolean doUpdate(TpCoursePaper tpcoursepaper) {
		if (tpcoursepaper == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getUpdateSql(tpcoursepaper, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}
	
	public List<TpCoursePaper> getList(TpCoursePaper tpcoursepaper, PageResult presult) {
		StringBuilder sqlbuilder = new StringBuilder();
		sqlbuilder.append("{CALL tp_j_course_paper_proc_split(");
		List<Object> objList=new ArrayList<Object>();
        if (tpcoursepaper.getRef() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcoursepaper.getRef());
        } else
            sqlbuilder.append("null,");
		if (tpcoursepaper.getPaperid() != null) {
			sqlbuilder.append("?,");
			objList.add(tpcoursepaper.getPaperid());
		} else
			sqlbuilder.append("null,");

		if (tpcoursepaper.getCourseid() != null) {
			sqlbuilder.append("?,");
			objList.add(tpcoursepaper.getCourseid());
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
		List<TpCoursePaper> tpcoursepaperList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, TpCoursePaper.class, objArray);
		if(presult!=null&&objArray[0]!=null&&objArray[0].toString().trim().length()>0)
			presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));				
		return tpcoursepaperList;	
	}
	
	public List<Object> getSaveSql(TpCoursePaper tpcoursepaper, StringBuilder sqlbuilder) {
		if(tpcoursepaper==null || sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL tp_j_course_paper_proc_add(");
		List<Object>objList = new ArrayList<Object>();
			if (tpcoursepaper.getPaperid() != null) {
				sqlbuilder.append("?,");
				objList.add(tpcoursepaper.getPaperid());
			} else
				sqlbuilder.append("null,");

			if (tpcoursepaper.getCourseid() != null) {
				sqlbuilder.append("?,");
				objList.add(tpcoursepaper.getCourseid());
			} else
				sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getDeleteSql(TpCoursePaper tpcoursepaper, StringBuilder sqlbuilder) {
		if(tpcoursepaper==null || sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL tp_j_course_paper_proc_delete(");
		List<Object>objList = new ArrayList<Object>();
            if (tpcoursepaper.getRef() != null) {
                sqlbuilder.append("?,");
                objList.add(tpcoursepaper.getRef());
            } else
                sqlbuilder.append("null,");
			if (tpcoursepaper.getPaperid() != null) {
				sqlbuilder.append("?,");
				objList.add(tpcoursepaper.getPaperid());
			} else
				sqlbuilder.append("null,");

			if (tpcoursepaper.getCourseid() != null) {
				sqlbuilder.append("?,");
				objList.add(tpcoursepaper.getCourseid());
			} else
				sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getUpdateSql(TpCoursePaper tpcoursepaper, StringBuilder sqlbuilder) {
		if(tpcoursepaper==null||sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL tp_j_course_paper_proc_update(");
		List<Object>objList = new ArrayList<Object>();
            if (tpcoursepaper.getRef() != null) {
                sqlbuilder.append("?,");
                objList.add(tpcoursepaper.getRef());
            } else
                sqlbuilder.append("null,");
			if (tpcoursepaper.getPaperid() != null) {
				sqlbuilder.append("?,");
				objList.add(tpcoursepaper.getPaperid());
			} else
				sqlbuilder.append("null,");

			if (tpcoursepaper.getCourseid() != null) {
				sqlbuilder.append("?,");
				objList.add(tpcoursepaper.getCourseid());
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
