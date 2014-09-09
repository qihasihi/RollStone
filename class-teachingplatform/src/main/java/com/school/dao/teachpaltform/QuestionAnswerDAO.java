package com.school.dao.teachpaltform;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.school.dao.base.CommonDAO;
import com.school.entity.teachpaltform.QuestionAnswer;
import com.school.dao.inter.teachpaltform.IQuestionAnswerDAO;
import com.school.util.PageResult;

@Component  
public class QuestionAnswerDAO extends CommonDAO<QuestionAnswer> implements IQuestionAnswerDAO {

	public Boolean doSave(QuestionAnswer questionanswer) {
		if (questionanswer == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getSaveSql(questionanswer, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}
	
	public Boolean doDelete(QuestionAnswer questionanswer) {
		if(questionanswer==null)		
			return false;
		StringBuilder sqlbuilder=new StringBuilder();
		List<Object> objList=getDeleteSql(questionanswer, sqlbuilder);
		Object afficeObj=this.executeSacle_PROC(sqlbuilder.toString(), objList.toArray());
		if(afficeObj!=null&&afficeObj.toString().trim().length()>0&&Integer.parseInt(afficeObj.toString())>0){
			return true;
		}return false;
	}

	public Boolean doUpdate(QuestionAnswer questionanswer) {
		if (questionanswer == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getUpdateSql(questionanswer, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}
	
	public List<QuestionAnswer> getList(QuestionAnswer questionanswer, PageResult presult) {
		StringBuilder sqlbuilder = new StringBuilder();
		sqlbuilder.append("{CALL tp_ques_answer_record_proc_split(");
		List<Object> objList=new ArrayList<Object>();
		if (questionanswer.getRef() != null) {
			sqlbuilder.append("?,");
			objList.add(questionanswer.getRef());
		} else
			sqlbuilder.append("null,");
		if (questionanswer.getQuesid()!= null) {
			sqlbuilder.append("?,");
			objList.add(questionanswer.getQuesid());
		} else
			sqlbuilder.append("null,");
		if (questionanswer.getQuesparentid() != null) {
			sqlbuilder.append("?,");
			objList.add(questionanswer.getQuesparentid());
		} else
			sqlbuilder.append("null,");
		if (questionanswer.getUserid() != null) {
			sqlbuilder.append("?,");
			objList.add(questionanswer.getUserid());
		} else
			sqlbuilder.append("null,");
		if (questionanswer.getTaskid() != null) {
			sqlbuilder.append("?,");
			objList.add(questionanswer.getTaskid());
		} else
			sqlbuilder.append("null,");
		if (questionanswer.getCourseid() != null) {
			sqlbuilder.append("?,");
			objList.add(questionanswer.getCourseid());
		} else
			sqlbuilder.append("null,");
		if (questionanswer.getGroupid() != null) {
			sqlbuilder.append("?,");
			objList.add(questionanswer.getGroupid());
		} else
			sqlbuilder.append("null,");
		if (questionanswer.getTasktype() != null) {
			sqlbuilder.append("?,");
			objList.add(questionanswer.getTasktype());
		} else
			sqlbuilder.append("null,");
        if (questionanswer.getRightanswer() != null) {
            sqlbuilder.append("?,");
            objList.add(questionanswer.getRightanswer());
        } else
            sqlbuilder.append("null,");
        if (questionanswer.getClassid() != null) {
            sqlbuilder.append("?,");
            objList.add(questionanswer.getClassid());
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
		List<QuestionAnswer> questionanswerList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, QuestionAnswer.class, objArray);
		if(presult!=null&&objArray[0]!=null&&objArray[0].toString().trim().length()>0)
			presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));				
		return questionanswerList;	
	}
	
	public List<Object> getSaveSql(QuestionAnswer questionanswer, StringBuilder sqlbuilder) {
		if(questionanswer==null || sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL tp_ques_answer_record_proc_add(");
		List<Object>objList = new ArrayList<Object>();
		if (questionanswer.getQuesid()!= null) {
			sqlbuilder.append("?,");
			objList.add(questionanswer.getQuesid());
		} else
			sqlbuilder.append("null,");
		if (questionanswer.getQuesparentid() != null) {
			sqlbuilder.append("?,");
			objList.add(questionanswer.getQuesparentid());
		} else
			sqlbuilder.append("null,");
		if (questionanswer.getUserid() != null) {
			sqlbuilder.append("?,");
			objList.add(questionanswer.getUserid());
		} else
			sqlbuilder.append("null,");
		if (questionanswer.getTaskid() != null) {
			sqlbuilder.append("?,");
			objList.add(questionanswer.getTaskid());
		} else
			sqlbuilder.append("null,");
		if (questionanswer.getCourseid() != null) {
			sqlbuilder.append("?,");
			objList.add(questionanswer.getCourseid());
		} else
			sqlbuilder.append("null,");
		if (questionanswer.getGroupid() != null) {
			sqlbuilder.append("?,");
			objList.add(questionanswer.getGroupid());
		} else
			sqlbuilder.append("null,");
		if (questionanswer.getTasktype() != null) {
			sqlbuilder.append("?,");
			objList.add(questionanswer.getTasktype());
		} else
			sqlbuilder.append("null,");
		if (questionanswer.getRightanswer() != null) {
			sqlbuilder.append("?,");
			objList.add(questionanswer.getRightanswer());
		} else
			sqlbuilder.append("null,");
		if (questionanswer.getAnswercontent() != null) {
			sqlbuilder.append("?,");
			objList.add(questionanswer.getAnswercontent());
		} else
			sqlbuilder.append("null,");
        if (questionanswer.getTouserid() != null) {
            sqlbuilder.append("?,");
            objList.add(questionanswer.getTouserid());
        } else
            sqlbuilder.append("null,");
        if (questionanswer.getTorealname() != null) {
            sqlbuilder.append("?,");
            objList.add(questionanswer.getTorealname());
        } else
            sqlbuilder.append("null,");
        if (questionanswer.getReplyuserid() != null) {
            sqlbuilder.append("?,");
            objList.add(questionanswer.getReplyuserid());
        } else
            sqlbuilder.append("null,");
        if (questionanswer.getReplyattach() != null) {
            sqlbuilder.append("?,");
            objList.add(questionanswer.getReplyattach());
        } else
            sqlbuilder.append("null,");
        if (questionanswer.getReplyattachtype() != null) {
            sqlbuilder.append("?,");
            objList.add(questionanswer.getReplyattachtype());
        } else
            sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getDeleteSql(QuestionAnswer questionanswer, StringBuilder sqlbuilder) {
		if(questionanswer==null || sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL tp_ques_answer_record_proc_delete(");
		List<Object>objList = new ArrayList<Object>();
			if (questionanswer.getRef() != null) {
				sqlbuilder.append("?,");
				objList.add(questionanswer.getRef());
			} else
				sqlbuilder.append("null,");
			if (questionanswer.getQuesid() != null) {
				sqlbuilder.append("?,");
				objList.add(questionanswer.getQuesid());
			} else
				sqlbuilder.append("null,");
			if (questionanswer.getQuesparentid() != null) {
				sqlbuilder.append("?,");
				objList.add(questionanswer.getQuesparentid());
			} else
				sqlbuilder.append("null,");
			if (questionanswer.getRightanswer() != null) {
				sqlbuilder.append("?,");
				objList.add(questionanswer.getRightanswer());
			} else
				sqlbuilder.append("null,");
			if (questionanswer.getUserid() != null) {
				sqlbuilder.append("?,");
				objList.add(questionanswer.getUserid());
			} else
				sqlbuilder.append("null,");
			if (questionanswer.getTaskid() != null) {
				sqlbuilder.append("?,");
				objList.add(questionanswer.getTaskid());
			} else
				sqlbuilder.append("null,");
			if (questionanswer.getCourseid() != null) {
				sqlbuilder.append("?,");
				objList.add(questionanswer.getCourseid());
			} else
				sqlbuilder.append("null,");
			if (questionanswer.getTasktype() != null) {
				sqlbuilder.append("?,");
				objList.add(questionanswer.getTasktype());
			} else
				sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getUpdateSql(QuestionAnswer questionanswer, StringBuilder sqlbuilder) {
		if(questionanswer==null||sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL tp_ques_answer_record_proc_update(");
		List<Object>objList = new ArrayList<Object>();
			if (questionanswer.getRef() != null) {
				sqlbuilder.append("?,");
				objList.add(questionanswer.getRef());
			} else
				sqlbuilder.append("null,");
			if (questionanswer.getQuesid() != null) {
				sqlbuilder.append("?,");
				objList.add(questionanswer.getQuesid());
			} else
				sqlbuilder.append("null,");
			if (questionanswer.getQuesparentid() != null) {
				sqlbuilder.append("?,");
				objList.add(questionanswer.getQuesparentid());
			} else
				sqlbuilder.append("null,");
			if (questionanswer.getAnswercontent() != null) {
				sqlbuilder.append("?,");
				objList.add(questionanswer.getAnswercontent());
			} else
				sqlbuilder.append("null,");
			if (questionanswer.getRightanswer() != null) {
				sqlbuilder.append("?,");
				objList.add(questionanswer.getRightanswer());
			} else
				sqlbuilder.append("null,");
			if (questionanswer.getUserid() != null) {
				sqlbuilder.append("?,");
				objList.add(questionanswer.getUserid());
			} else
				sqlbuilder.append("null,");
			if (questionanswer.getTaskid() != null) {
				sqlbuilder.append("?,");
				objList.add(questionanswer.getTaskid());
			} else
				sqlbuilder.append("null,");
			if (questionanswer.getCourseid() != null) {
				sqlbuilder.append("?,");
				objList.add(questionanswer.getCourseid());
			} else
				sqlbuilder.append("null,");
			if (questionanswer.getTasktype() != null) {
				sqlbuilder.append("?,");
				objList.add(questionanswer.getTasktype());
			} else
				sqlbuilder.append("null,");
			if (questionanswer.getGroupid() != null) {
				sqlbuilder.append("?,");
				objList.add(questionanswer.getGroupid());
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

	public List<QuestionAnswer> getListNoRepeat(QuestionAnswer t) {
		if(t.getTaskid()==null||t.getGroupid()==null)
			return null;
		StringBuilder sqlbuilder = new StringBuilder();
		sqlbuilder.append("{CALL tp_qry_answer_record_no_repeat(");
		List<Object> objList=new ArrayList<Object>();
		objList.add(t.getTaskid());
		objList.add(t.getGroupid());   
		sqlbuilder.append("?,?)}");
		List<Integer> types=new ArrayList<Integer>();
		List<QuestionAnswer> questionanswerList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, QuestionAnswer.class, null);
		return questionanswerList;	
	}

    public List<QuestionAnswer> getResouceStuNoteList(QuestionAnswer questionanswer, PageResult presult) {
        StringBuilder sqlbuilder = new StringBuilder();
        sqlbuilder.append("{CALL tp_ques_answer_stunote_split(");
        List<Object> objList=new ArrayList<Object>();

        if (questionanswer.getQuesid()!= null) {
            sqlbuilder.append("?,");
            objList.add(questionanswer.getQuesid());
        } else
            sqlbuilder.append("null,");
        if (questionanswer.getQuesparentid() != null) {
            sqlbuilder.append("?,");
            objList.add(questionanswer.getQuesparentid());
        } else
            sqlbuilder.append("null,");
        if (questionanswer.getUserid() != null) {
            sqlbuilder.append("?,");
            objList.add(questionanswer.getUserid());
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
        List<QuestionAnswer> questionanswerList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, QuestionAnswer.class, objArray);
        if(presult!=null&&objArray[0]!=null&&objArray[0].toString().trim().length()>0)
            presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));
        return questionanswerList;
    }

    public List<QuestionAnswer> getResouceStuNoteTreeList(QuestionAnswer q, PageResult presult) {
        StringBuilder sqlbuilder = new StringBuilder();
        sqlbuilder.append("{CALL tp_qry_res_stunote_tree(");
        List<Object> objList=new ArrayList<Object>();
        if (q.getQuesparentid()!= null) {
            sqlbuilder.append("?,");
            objList.add(q.getQuesparentid());
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
            sqlbuilder.append("?");
            objList.add(presult.getOrderBy());
        }else{
            sqlbuilder.append("NULL");
        }
        sqlbuilder.append(")}");
        List<Integer> types=new ArrayList<Integer>();
        List<QuestionAnswer> commentinfoList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, QuestionAnswer.class, null);
        return commentinfoList;
    }

}
