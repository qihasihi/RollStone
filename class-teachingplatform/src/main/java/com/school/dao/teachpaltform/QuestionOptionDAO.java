package com.school.dao.teachpaltform;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.school.entity.teachpaltform.QuestionInfo;
import org.springframework.stereotype.Component;

import com.school.dao.base.CommonDAO;
import com.school.entity.teachpaltform.QuestionOption;
import com.school.dao.inter.teachpaltform.IQuestionOptionDAO;
import com.school.util.PageResult;

@Component  
public class QuestionOptionDAO extends CommonDAO<QuestionOption> implements IQuestionOptionDAO {

	public Boolean doSave(QuestionOption questionoption) {
		if (questionoption == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getSaveSql(questionoption, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}
	
	public Boolean doDelete(QuestionOption questionoption) {
		if(questionoption==null)		
			return false;
		StringBuilder sqlbuilder=new StringBuilder();
		List<Object> objList=getDeleteSql(questionoption, sqlbuilder);
		Object afficeObj=this.executeSacle_PROC(sqlbuilder.toString(), objList.toArray());
		if(afficeObj!=null&&afficeObj.toString().trim().length()>0&&Integer.parseInt(afficeObj.toString())>0){
			return true;
		}return false;
	}

	public Boolean doUpdate(QuestionOption questionoption) {
		if (questionoption == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getUpdateSql(questionoption, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}
	
	public List<QuestionOption> getList(QuestionOption questionoption, PageResult presult) {
		StringBuilder sqlbuilder = new StringBuilder();
		sqlbuilder.append("{CALL j_question_option_proc_split(");
		List<Object> objList=new ArrayList<Object>();
        if (questionoption.getRef() != null) {
            sqlbuilder.append("?,");
            objList.add(questionoption.getRef());
        } else
            sqlbuilder.append("null,");
		if (questionoption.getQuestionid() != null) {
			sqlbuilder.append("?,");
			objList.add(questionoption.getQuestionid());
		} else
			sqlbuilder.append("null,");
		if (questionoption.getOptiontype() != null) {
			sqlbuilder.append("?,");
			objList.add(questionoption.getOptiontype());
		} else
			sqlbuilder.append("null,");
        if (questionoption.getIsright() != null) {
            sqlbuilder.append("?,");
            objList.add(questionoption.getIsright());
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
		List<QuestionOption> questionoptionList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, QuestionOption.class, objArray);
		if(presult!=null&&objArray[0]!=null&&objArray[0].toString().trim().length()>0)
			presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));				
		return questionoptionList;	
	}
	
	public List<Object> getSaveSql(QuestionOption questionoption, StringBuilder sqlbuilder) {
		if(questionoption==null || sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL j_question_option_proc_add(");
		List<Object>objList = new ArrayList<Object>();
			if (questionoption.getQuestionid() != null) {
				sqlbuilder.append("?,");
				objList.add(questionoption.getQuestionid());
			} else
				sqlbuilder.append("null,");
            if (questionoption.getScore() != null) {
                sqlbuilder.append("?,");
                objList.add(questionoption.getScore());
            } else
                sqlbuilder.append("null,");
            if (questionoption.getSaveContent() != null) {
                sqlbuilder.append("?,");
                objList.add(questionoption.getSaveContent());
            } else
                sqlbuilder.append("null,");
			if (questionoption.getIsright() != null) {
				sqlbuilder.append("?,");
				objList.add(questionoption.getIsright());
			} else
				sqlbuilder.append("null,");
			if (questionoption.getOptiontype() != null) {
				sqlbuilder.append("?,");
				objList.add(questionoption.getOptiontype());
			} else
				sqlbuilder.append("null,");

		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getDeleteSql(QuestionOption questionoption, StringBuilder sqlbuilder) {
		if(questionoption==null || sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL j_question_option_proc_delete(");
		List<Object>objList = new ArrayList<Object>();
			if (questionoption.getQuestionid() != null) {
				sqlbuilder.append("?,");
				objList.add(questionoption.getQuestionid());
			} else
				sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getUpdateSql(QuestionOption questionoption, StringBuilder sqlbuilder) {
		if(questionoption==null||sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL j_question_option_proc_update(");
		List<Object>objList = new ArrayList<Object>();
        if (questionoption.getQuestionid() != null) {
            sqlbuilder.append("?,");
            objList.add(questionoption.getQuestionid());
        } else
            sqlbuilder.append("null,");
        if (questionoption.getScore() != null) {
            sqlbuilder.append("?,");
            objList.add(questionoption.getScore());
        } else
            sqlbuilder.append("null,");
        if (questionoption.getSaveContent() != null) {
            sqlbuilder.append("?,");
            objList.add(questionoption.getSaveContent());
        } else
            sqlbuilder.append("null,");
        if (questionoption.getIsright() != null) {
            sqlbuilder.append("?,");
            objList.add(questionoption.getIsright());
        } else
            sqlbuilder.append("null,");
        if (questionoption.getOptiontype() != null) {
            sqlbuilder.append("?,");
            objList.add(questionoption.getOptiontype());
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
    public List<QuestionOption> getPaperQuesOptionList(QuestionOption questionoption, PageResult presult) {
        StringBuilder sqlbuilder = new StringBuilder();
        sqlbuilder.append("{CALL j_paper_question_option_proc_split(");
        List<Object> objList=new ArrayList<Object>();
        if (questionoption.getRef() != null) {
            sqlbuilder.append("?,");
            objList.add(questionoption.getRef());
        } else
            sqlbuilder.append("null,");
        if (questionoption.getQuestionid() != null) {
            sqlbuilder.append("?,");
            objList.add(questionoption.getQuestionid());
        } else
            sqlbuilder.append("null,");
        if (questionoption.getOptiontype() != null) {
            sqlbuilder.append("?,");
            objList.add(questionoption.getOptiontype());
        } else
            sqlbuilder.append("null,");
        if (questionoption.getIsright() != null) {
            sqlbuilder.append("?,");
            objList.add(questionoption.getIsright());
        } else
            sqlbuilder.append("null,");
        if (questionoption.getPaperid() != null) {
            sqlbuilder.append("?,");
            objList.add(questionoption.getPaperid());
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
        List<QuestionOption> questionoptionList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, QuestionOption.class, objArray);
        if(presult!=null&&objArray[0]!=null&&objArray[0].toString().trim().length()>0)
            presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));
        return questionoptionList;
    }

    /**
     * 得到同步的SQL
     * @param entity  对象实体
     * @param sqlbuilder  传出的SQL语句，必须实例化后
     * @return
     */
    public List<Object> getSynchroSql(QuestionOption entity,StringBuilder sqlbuilder){
        if(entity==null||sqlbuilder==null)return null;

        sqlbuilder.append("{CALL j_question_option_proc_synchro(");
        List<Object>objList = new ArrayList<Object>();
        if (entity.getQuestionid() != null) {
            sqlbuilder.append("?,");
            objList.add(entity.getQuestionid());
        } else
            sqlbuilder.append("null,");

        /*   p_question_id BIGINT,
				            p_score INT,
				            p_content VARCHAR(4000),
				            p_is_right INT,
				            p_option_type VARCHAR(1000),*/
        if (entity.getScore() != null) {
            sqlbuilder.append("?,");
            objList.add(entity.getScore());
        } else
            sqlbuilder.append("null,");
        if (entity.getSaveContent() != null) {
            sqlbuilder.append("?,");
            objList.add(entity.getSaveContent());
        } else
            sqlbuilder.append("null,");
        if (entity.getIsright() != null) {
            sqlbuilder.append("?,");
            objList.add(entity.getIsright());
        } else
            sqlbuilder.append("null,");
        if (entity.getOptiontype() != null) {
            sqlbuilder.append("?,");
            objList.add(entity.getOptiontype());
        } else
            sqlbuilder.append("null,");
        sqlbuilder.append("?)}");
        return objList;
    }

}
