package com.school.dao.teachpaltform.paper;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.school.dao.base.CommonDAO;
import com.school.entity.teachpaltform.paper.StuPaperQuesLogs;
import com.school.dao.inter.teachpaltform.paper.IStuPaperQuesLogsDAO;
import com.school.util.PageResult;

@Component  
public class StuPaperQuesLogsDAO extends CommonDAO<StuPaperQuesLogs> implements IStuPaperQuesLogsDAO {

	public Boolean doSave(StuPaperQuesLogs stupaperqueslogs) {
		if (stupaperqueslogs == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getSaveSql(stupaperqueslogs, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}
	
	public Boolean doDelete(StuPaperQuesLogs stupaperqueslogs) {
		if(stupaperqueslogs==null)		
			return false;
		StringBuilder sqlbuilder=new StringBuilder();
		List<Object> objList=getDeleteSql(stupaperqueslogs, sqlbuilder);
		Object afficeObj=this.executeSacle_PROC(sqlbuilder.toString(), objList.toArray());
		if(afficeObj!=null&&afficeObj.toString().trim().length()>0&&Integer.parseInt(afficeObj.toString())>0){
			return true;
		}return false;
	}

	public Boolean doUpdate(StuPaperQuesLogs stupaperqueslogs) {
		if (stupaperqueslogs == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getUpdateSql(stupaperqueslogs, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}
	
	public List<StuPaperQuesLogs> getList(StuPaperQuesLogs stupaperqueslogs, PageResult presult) {
		StringBuilder sqlbuilder = new StringBuilder();
		sqlbuilder.append("{CALL stu_paper_ques_logs_proc_split(");
		List<Object> objList=new ArrayList<Object>();

		if (stupaperqueslogs.getRef() != null) {
			sqlbuilder.append("?,");
			objList.add(stupaperqueslogs.getRef());
		} else
			sqlbuilder.append("null,");
        if (stupaperqueslogs.getPaperid() != null) {
            sqlbuilder.append("?,");
            objList.add(stupaperqueslogs.getPaperid());
        } else
            sqlbuilder.append("null,");
        if (stupaperqueslogs.getUserid() != null) {
            sqlbuilder.append("?,");
            objList.add(stupaperqueslogs.getUserid());
        } else
            sqlbuilder.append("null,");
		if (stupaperqueslogs.getQuesid() != null) {
			sqlbuilder.append("?,");
			objList.add(stupaperqueslogs.getQuesid());
		} else
			sqlbuilder.append("null,");
        if (stupaperqueslogs.getIsright() != null) {
            sqlbuilder.append("?,");
            objList.add(stupaperqueslogs.getIsright());
        } else
            sqlbuilder.append("null,");

        if (stupaperqueslogs.getAnswer() != null) {
            sqlbuilder.append("?,");
            objList.add(stupaperqueslogs.getAnswer());
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
		List<StuPaperQuesLogs> stupaperqueslogsList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, StuPaperQuesLogs.class, objArray);
		if(presult!=null&&objArray[0]!=null&&objArray[0].toString().trim().length()>0)
			presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));				
		return stupaperqueslogsList;	
	}
	
	public List<Object> getSaveSql(StuPaperQuesLogs stupaperqueslogs, StringBuilder sqlbuilder) {
		if(stupaperqueslogs==null || sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL stu_paper_ques_logs_proc_add(");
		List<Object>objList = new ArrayList<Object>();

        if (stupaperqueslogs.getPaperid() != null) {
            sqlbuilder.append("?,");
            objList.add(stupaperqueslogs.getPaperid());
        } else
            sqlbuilder.append("null,");
        if (stupaperqueslogs.getQuesid() != null) {
            sqlbuilder.append("?,");
            objList.add(stupaperqueslogs.getQuesid());
        } else
            sqlbuilder.append("null,");
        if (stupaperqueslogs.getUserid() != null) {
            sqlbuilder.append("?,");
            objList.add(stupaperqueslogs.getUserid());
        } else
            sqlbuilder.append("null,");
        if (stupaperqueslogs.getScore() != null) {
            sqlbuilder.append("?,");
            objList.add(stupaperqueslogs.getScore());
        } else
            sqlbuilder.append("null,");

        if (stupaperqueslogs.getIsright() != null) {
            sqlbuilder.append("?,");
            objList.add(stupaperqueslogs.getIsright());
        } else
            sqlbuilder.append("null,");

        if (stupaperqueslogs.getAnswer() != null) {
            sqlbuilder.append("?,");
            objList.add(stupaperqueslogs.getAnswer());
        } else
            sqlbuilder.append("null,");

        if (stupaperqueslogs.getIsmarking() != null) {
            sqlbuilder.append("?,");
            objList.add(stupaperqueslogs.getIsmarking());
        } else
            sqlbuilder.append("null,");

		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getDeleteSql(StuPaperQuesLogs stupaperqueslogs, StringBuilder sqlbuilder) {
		if(stupaperqueslogs==null || sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL stu_paper_ques_logs_proc_delete(");
		List<Object>objList = new ArrayList<Object>();

        if (stupaperqueslogs.getRef() != null) {
            sqlbuilder.append("?,");
            objList.add(stupaperqueslogs.getRef());
        } else
            sqlbuilder.append("null,");
        if (stupaperqueslogs.getPaperid() != null) {
            sqlbuilder.append("?,");
            objList.add(stupaperqueslogs.getPaperid());
        } else
            sqlbuilder.append("null,");
        if (stupaperqueslogs.getUserid() != null) {
            sqlbuilder.append("?,");
            objList.add(stupaperqueslogs.getUserid());
        } else
            sqlbuilder.append("null,");
        if (stupaperqueslogs.getQuesid() != null) {
            sqlbuilder.append("?,");
            objList.add(stupaperqueslogs.getQuesid());
        } else
            sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getUpdateSql(StuPaperQuesLogs stupaperqueslogs, StringBuilder sqlbuilder) {
		if(stupaperqueslogs==null||sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL stu_paper_ques_logs_proc_update(");
		List<Object>objList = new ArrayList<Object>();
        if (stupaperqueslogs.getRef() != null) {
            sqlbuilder.append("?,");
            objList.add(stupaperqueslogs.getRef());
        } else
            sqlbuilder.append("null,");
        if (stupaperqueslogs.getPaperid() != null) {
            sqlbuilder.append("?,");
            objList.add(stupaperqueslogs.getPaperid());
        } else
            sqlbuilder.append("null,");
        if (stupaperqueslogs.getQuesid() != null) {
            sqlbuilder.append("?,");
            objList.add(stupaperqueslogs.getQuesid());
        } else
            sqlbuilder.append("null,");
        if (stupaperqueslogs.getUserid() != null) {
            sqlbuilder.append("?,");
            objList.add(stupaperqueslogs.getUserid());
        } else
            sqlbuilder.append("null,");
        if (stupaperqueslogs.getScore() != null) {
            sqlbuilder.append("?,");
            objList.add(stupaperqueslogs.getScore());
        } else
            sqlbuilder.append("null,");
        if (stupaperqueslogs.getAnswer() != null) {
            sqlbuilder.append("?,");
            objList.add(stupaperqueslogs.getAnswer());
        } else
            sqlbuilder.append("null,");
        if (stupaperqueslogs.getIsright() != null) {
            sqlbuilder.append("?,");
            objList.add(stupaperqueslogs.getIsright());
        } else
            sqlbuilder.append("null,");
        if (stupaperqueslogs.getIsmarking() != null) {
            sqlbuilder.append("?,");
            objList.add(stupaperqueslogs.getIsmarking());
        } else
            sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList; 
	}


	
	public String getNextId() {
		// TODO Auto-generated method stub
		return null;
	}

}
