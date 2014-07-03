package com.school.dao.teachpaltform.paper;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.school.dao.base.CommonDAO;
import com.school.entity.teachpaltform.paper.PaperQuestion;
import com.school.dao.inter.teachpaltform.paper.IPaperQuestionDAO;
import com.school.util.PageResult;

@Component  
public class PaperQuestionDAO extends CommonDAO<PaperQuestion> implements IPaperQuestionDAO {

	public Boolean doSave(PaperQuestion paperquestion) {
		if (paperquestion == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getSaveSql(paperquestion, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}
	
	public Boolean doDelete(PaperQuestion paperquestion) {
		if(paperquestion==null)		
			return false;
		StringBuilder sqlbuilder=new StringBuilder();
		List<Object> objList=getDeleteSql(paperquestion, sqlbuilder);
		Object afficeObj=this.executeSacle_PROC(sqlbuilder.toString(), objList.toArray());
		if(afficeObj!=null&&afficeObj.toString().trim().length()>0&&Integer.parseInt(afficeObj.toString())>0){
			return true;
		}return false;
	}

	public Boolean doUpdate(PaperQuestion paperquestion) {
		if (paperquestion == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getUpdateSql(paperquestion, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}
	
	public List<PaperQuestion> getList(PaperQuestion paperquestion, PageResult presult) {
		StringBuilder sqlbuilder = new StringBuilder();
		sqlbuilder.append("{CALL j_paper_question_proc_split(");
		List<Object> objList=new ArrayList<Object>();
        if (paperquestion.getRef() != null) {
            sqlbuilder.append("?,");
            objList.add(paperquestion.getRef());
        } else
            sqlbuilder.append("null,");
		if (paperquestion.getPaperid() != null) {
			sqlbuilder.append("?,");
			objList.add(paperquestion.getPaperid());
		} else
			sqlbuilder.append("null,");
		if (paperquestion.getQuestionid() != null) {
			sqlbuilder.append("?,");
			objList.add(paperquestion.getQuestionid());
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
		List<PaperQuestion> paperquestionList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, PaperQuestion.class, objArray);
		if(presult!=null&&objArray[0]!=null&&objArray[0].toString().trim().length()>0)
			presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));				
		return paperquestionList;	
	}
	
	public List<Object> getSaveSql(PaperQuestion paperquestion, StringBuilder sqlbuilder) {
		if(paperquestion==null || sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL j_paper_question_proc_add(");
		List<Object>objList = new ArrayList<Object>();
			if (paperquestion.getPaperid() != null) {
				sqlbuilder.append("?,");
				objList.add(paperquestion.getPaperid());
			} else
				sqlbuilder.append("null,");
            if (paperquestion.getQuestionid() != null) {
                sqlbuilder.append("?,");
                objList.add(paperquestion.getQuestionid());
            } else
                sqlbuilder.append("null,");
			if (paperquestion.getOrderidx() != null) {
				sqlbuilder.append("?,");
				objList.add(paperquestion.getOrderidx());
			} else
				sqlbuilder.append("null,");
			if (paperquestion.getScore() != null) {
				sqlbuilder.append("?,");
				objList.add(paperquestion.getScore());
			} else
				sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getDeleteSql(PaperQuestion paperquestion, StringBuilder sqlbuilder) {
		if(paperquestion==null || sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL j_paper_question_proc_delete(");
		List<Object>objList = new ArrayList<Object>();
            if (paperquestion.getRef() != null) {
                sqlbuilder.append("?,");
                objList.add(paperquestion.getRef());
            } else
                sqlbuilder.append("null,");
			if (paperquestion.getPaperid() != null) {
				sqlbuilder.append("?,");
				objList.add(paperquestion.getPaperid());
			} else
				sqlbuilder.append("null,");
			if (paperquestion.getQuestionid() != null) {
				sqlbuilder.append("?,");
				objList.add(paperquestion.getQuestionid());
			} else
				sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getUpdateSql(PaperQuestion paperquestion, StringBuilder sqlbuilder) {
		if(paperquestion==null||sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL j_paper_question_proc_update(");
		List<Object>objList = new ArrayList<Object>();
            if (paperquestion.getRef() != null) {
                sqlbuilder.append("?,");
                objList.add(paperquestion.getRef());
            } else
                sqlbuilder.append("null,");
			if (paperquestion.getPaperid() != null) {
				sqlbuilder.append("?,");
				objList.add(paperquestion.getPaperid());
			} else
				sqlbuilder.append("null,");

			if (paperquestion.getQuestionid() != null) {
				sqlbuilder.append("?,");
				objList.add(paperquestion.getQuestionid());
			} else
				sqlbuilder.append("null,");
            if (paperquestion.getOrderidx() != null) {
                sqlbuilder.append("?,");
                objList.add(paperquestion.getOrderidx());
            } else
                sqlbuilder.append("null,");
			if (paperquestion.getScore() != null) {
				sqlbuilder.append("?,");
				objList.add(paperquestion.getScore());
			} else
				sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList; 
	}

	
	public String getNextId() {
		// TODO Auto-generated method stub
		return null;
	}

    @Override
    public Float getSumScore(PaperQuestion paperQuestion) {
        if (paperQuestion == null)
            return null;
        StringBuilder sqlbuilder = new StringBuilder("{CALL j_course_paper_sumscore(?,?)}");

        List<Object> objList = new ArrayList<Object>();
        objList.add(paperQuestion.getPaperid());
        Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
                objList.toArray());
        if (afficeObj != null && afficeObj.toString().trim().length() > 0) {
            return  Float.parseFloat(afficeObj.toString());
        }
        return null;
    }

    public List<PaperQuestion> getQuestionByPaper(Integer paperid) {
        StringBuilder sqlbuilder = new StringBuilder();
        sqlbuilder.append("{CALL tp_paper_marking_proc_list(");
        List<Object> objList=new ArrayList<Object>();
        if(paperid!=null){
            sqlbuilder.append("?");
            objList.add(paperid);
        }else{
            sqlbuilder.append("NULL");
        }
        sqlbuilder.append(")}");
        List<PaperQuestion> questionList = this.executeResult_PROC(sqlbuilder.toString(),objList,null,PaperQuestion.class,null);
        return questionList;
    }
}
