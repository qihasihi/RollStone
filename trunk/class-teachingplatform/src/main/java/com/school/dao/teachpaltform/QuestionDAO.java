package com.school.dao.teachpaltform;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.school.entity.teachpaltform.TpTaskInfo;
import org.springframework.stereotype.Component;

import com.school.dao.base.CommonDAO;
import com.school.entity.teachpaltform.QuestionInfo;
import com.school.dao.inter.teachpaltform.IQuestionDAO;
import com.school.util.PageResult;

@Component  
public class QuestionDAO extends CommonDAO<QuestionInfo> implements IQuestionDAO {

	public Boolean doSave(QuestionInfo questioninfo) {
		if (questioninfo == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getSaveSql(questioninfo, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}
	
	public Boolean doDelete(QuestionInfo questioninfo) {
		if(questioninfo==null)		
			return false;
		StringBuilder sqlbuilder=new StringBuilder();
		List<Object> objList=getDeleteSql(questioninfo, sqlbuilder);
		Object afficeObj=this.executeSacle_PROC(sqlbuilder.toString(), objList.toArray());
		if(afficeObj!=null&&afficeObj.toString().trim().length()>0&&Integer.parseInt(afficeObj.toString())>0){
			return true;
		}return false;
	}

	public Boolean doUpdate(QuestionInfo questioninfo) {
		if (questioninfo == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getUpdateSql(questioninfo, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}
	
	public List<QuestionInfo> getList(QuestionInfo questioninfo, PageResult presult) {
		StringBuilder sqlbuilder = new StringBuilder();
		sqlbuilder.append("{CALL question_info_proc_split(");
		List<Object> objList=new ArrayList<Object>();
		if (questioninfo.getQuestionid() != null) {
			sqlbuilder.append("?,");
			objList.add(questioninfo.getQuestionid());
		} else
			sqlbuilder.append("null,");
        if (questioninfo.getQuestionlevel() != null) {
            sqlbuilder.append("?,");
            objList.add(questioninfo.getQuestionlevel());
        } else
            sqlbuilder.append("null,");

        if (questioninfo.getQuestiontype() != null) {
            sqlbuilder.append("?,");
            objList.add(questioninfo.getQuestiontype());
        } else
            sqlbuilder.append("null,");
        if (questioninfo.getPapertypeid() != null) {
            sqlbuilder.append("?,");
            objList.add(questioninfo.getPapertypeid());
        } else
            sqlbuilder.append("null,");
        if (questioninfo.getCuserid() != null) {
            sqlbuilder.append("?,");
            objList.add(questioninfo.getCuserid());
        } else
            sqlbuilder.append("null,");

        if (questioninfo.getCloudstatus() != null) {
            sqlbuilder.append("?,");
            objList.add(questioninfo.getCloudstatus());
        } else
            sqlbuilder.append("null,");
		if (questioninfo.getStatus() != null) {
			sqlbuilder.append("?,");
			objList.add(questioninfo.getStatus());
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
		List<QuestionInfo> questioninfoList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, QuestionInfo.class, objArray);
		if(presult!=null&&objArray[0]!=null&&objArray[0].toString().trim().length()>0)
			presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));				
		return questioninfoList;	
	}
	
	public List<Object> getSaveSql(QuestionInfo questioninfo, StringBuilder sqlbuilder) {
		if(questioninfo==null || sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL question_info_proc_add(");
		List<Object>objList = new ArrayList<Object>();
            if (questioninfo.getQuestionid() != null) {
                sqlbuilder.append("?,");
                objList.add(questioninfo.getQuestionid());
            } else
                sqlbuilder.append("null,");
            if (questioninfo.getQuestiontype() != null) {
                sqlbuilder.append("?,");
                objList.add(questioninfo.getQuestiontype());
            } else
                sqlbuilder.append("null,");
            if (questioninfo.getPapertypeid() != null) {
                sqlbuilder.append("?,");
                objList.add(questioninfo.getPapertypeid());
            } else
                sqlbuilder.append("null,");
            if (questioninfo.getQuestionlevel() != null) {
                sqlbuilder.append("?,");
                objList.add(questioninfo.getQuestionlevel());
            } else
                sqlbuilder.append("null,");
            if (questioninfo.getStatus() != null) {
                sqlbuilder.append("?,");
                objList.add(questioninfo.getStatus());
            } else
                sqlbuilder.append("null,");
            if (questioninfo.getCloudstatus() != null) {
                sqlbuilder.append("?,");
                objList.add(questioninfo.getCloudstatus());
            } else
                sqlbuilder.append("null,");
			if (questioninfo.getSaveContent() != null) {
				sqlbuilder.append("?,");
				objList.add(questioninfo.getSaveContent());
			} else
				sqlbuilder.append("null,");
            if (questioninfo.getSaveAnalysis() != null) {
                sqlbuilder.append("?,");
                objList.add(questioninfo.getSaveAnalysis());
            } else
                sqlbuilder.append("null,");
			if (questioninfo.getExamtype() != null) {
				sqlbuilder.append("?,");
				objList.add(questioninfo.getExamtype());
			} else
				sqlbuilder.append("null,");
            if (questioninfo.getExamsubjecttype() != null) {
                sqlbuilder.append("?,");
                objList.add(questioninfo.getExamsubjecttype());
            } else
                sqlbuilder.append("null,");
			if (questioninfo.getExamyear() != null) {
				sqlbuilder.append("?,");
				objList.add(questioninfo.getExamyear());
			} else
				sqlbuilder.append("null,");
            if (questioninfo.getAxamarea() != null) {
                sqlbuilder.append("?,");
                objList.add(questioninfo.getAxamarea());
            } else
                sqlbuilder.append("null,");
            if (questioninfo.getProvince() != null) {
                sqlbuilder.append("?,");
                objList.add(questioninfo.getProvince());
            } else
                sqlbuilder.append("null,");
            if (questioninfo.getCity() != null) {
                sqlbuilder.append("?,");
                objList.add(questioninfo.getCity());
            } else
                sqlbuilder.append("null,");
			if (questioninfo.getGrade() != null) {
				sqlbuilder.append("?,");
				objList.add(questioninfo.getGrade());
			} else
				sqlbuilder.append("null,");
            if (questioninfo.getUsecount() != null) {
                sqlbuilder.append("?,");
                objList.add(questioninfo.getUsecount());
            } else
                sqlbuilder.append("null,");
			if (questioninfo.getCuserid() != null) {
				sqlbuilder.append("?,");
				objList.add(questioninfo.getCuserid());
			} else
				sqlbuilder.append("null,");

			if (questioninfo.getCusername() != null) {
				sqlbuilder.append("?,");
				objList.add(questioninfo.getCusername());
			} else
				sqlbuilder.append("null,");
            if(questioninfo.getCorrectanswer()!=null){
                sqlbuilder.append("?,");
                objList.add(questioninfo.getCorrectanswer());
            }else
                sqlbuilder.append("null,");

		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getDeleteSql(QuestionInfo questioninfo, StringBuilder sqlbuilder) {
		if(questioninfo==null || sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL question_info_proc_delete(");
		List<Object>objList = new ArrayList<Object>();

			if (questioninfo.getQuestionid() != null) {
				sqlbuilder.append("?,");
				objList.add(questioninfo.getQuestionid());
			} else
				sqlbuilder.append("null,");
			if (questioninfo.getPapertypeid() != null) {
				sqlbuilder.append("?,");
				objList.add(questioninfo.getPapertypeid());
			} else
				sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getUpdateSql(QuestionInfo questioninfo, StringBuilder sqlbuilder) {
		if(questioninfo==null||sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL question_info_proc_update(");
		List<Object>objList = new ArrayList<Object>();
        if (questioninfo.getQuestionid() != null) {
            sqlbuilder.append("?,");
            objList.add(questioninfo.getQuestionid());
        } else
            sqlbuilder.append("null,");
        if (questioninfo.getQuestiontype() != null) {
            sqlbuilder.append("?,");
            objList.add(questioninfo.getQuestiontype());
        } else
            sqlbuilder.append("null,");
        if (questioninfo.getPapertypeid() != null) {
            sqlbuilder.append("?,");
            objList.add(questioninfo.getPapertypeid());
        } else
            sqlbuilder.append("null,");
        if (questioninfo.getQuestionlevel() != null) {
            sqlbuilder.append("?,");
            objList.add(questioninfo.getQuestionlevel());
        } else
            sqlbuilder.append("null,");
        if (questioninfo.getStatus() != null) {
            sqlbuilder.append("?,");
            objList.add(questioninfo.getStatus());
        } else
            sqlbuilder.append("null,");
        if (questioninfo.getCloudstatus() != null) {
            sqlbuilder.append("?,");
            objList.add(questioninfo.getCloudstatus());
        } else
            sqlbuilder.append("null,");
        if (questioninfo.getSaveContent() != null) {
            sqlbuilder.append("?,");
            objList.add(questioninfo.getSaveContent());
        } else
            sqlbuilder.append("null,");
        if (questioninfo.getSaveAnalysis() != null) {
            sqlbuilder.append("?,");
            objList.add(questioninfo.getSaveAnalysis());
        } else
            sqlbuilder.append("null,");
        if (questioninfo.getExamtype() != null) {
            sqlbuilder.append("?,");
            objList.add(questioninfo.getExamtype());
        } else
            sqlbuilder.append("null,");
        if (questioninfo.getExamsubjecttype() != null) {
            sqlbuilder.append("?,");
            objList.add(questioninfo.getExamsubjecttype());
        } else
            sqlbuilder.append("null,");
        if (questioninfo.getExamyear() != null) {
            sqlbuilder.append("?,");
            objList.add(questioninfo.getExamyear());
        } else
            sqlbuilder.append("null,");
        if (questioninfo.getAxamarea() != null) {
            sqlbuilder.append("?,");
            objList.add(questioninfo.getAxamarea());
        } else
            sqlbuilder.append("null,");
        if (questioninfo.getProvince() != null) {
            sqlbuilder.append("?,");
            objList.add(questioninfo.getProvince());
        } else
            sqlbuilder.append("null,");
        if (questioninfo.getCity() != null) {
            sqlbuilder.append("?,");
            objList.add(questioninfo.getCity());
        } else
            sqlbuilder.append("null,");
        if (questioninfo.getGrade() != null) {
            sqlbuilder.append("?,");
            objList.add(questioninfo.getGrade());
        } else
            sqlbuilder.append("null,");
        if (questioninfo.getUsecount() != null) {
            sqlbuilder.append("?,");
            objList.add(questioninfo.getUsecount());
        } else
            sqlbuilder.append("null,");
        if (questioninfo.getCuserid() != null) {
            sqlbuilder.append("?,");
            objList.add(questioninfo.getCuserid());
        } else
            sqlbuilder.append("null,");

        if (questioninfo.getCusername() != null) {
            sqlbuilder.append("?,");
            objList.add(questioninfo.getCusername());
        } else
            sqlbuilder.append("null,");
        if(questioninfo.getCorrectanswer()!=null){
            sqlbuilder.append("?,");
            objList.add(questioninfo.getCorrectanswer());
        }else
            sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList; 
	}

    public String getNextId(){
		// TODO Auto-generated method stub
		return null;
	}

    /**
     * 得到同步的SQL
     * @param entity  对象实体
     * @param sqlbuilder  传出的SQL语句，必须实例化后
     * @return
     */
    public List<Object> getSynchroSql(QuestionInfo entity,StringBuilder sqlbuilder){
        if(entity==null||sqlbuilder==null)return null;
        sqlbuilder.append("{CALL question_info_proc_synchro(");
        List<Object>objList = new ArrayList<Object>();
        if (entity.getQuestionid() != null) {
            sqlbuilder.append("?,");
            objList.add(entity.getQuestionid());
        } else
            sqlbuilder.append("null,");
        if (entity.getQuestiontype() != null) {
            sqlbuilder.append("?,");
            objList.add(entity.getQuestiontype());
        } else
            sqlbuilder.append("null,");
        if (entity.getPapertypeid() != null) {
            sqlbuilder.append("?,");
            objList.add(entity.getPapertypeid());
        } else
            sqlbuilder.append("null,");
        if (entity.getQuestionlevel() != null) {
            sqlbuilder.append("?,");
            objList.add(entity.getQuestionlevel());
        } else
            sqlbuilder.append("null,");
        if (entity.getStatus() != null) {
            sqlbuilder.append("?,");
            objList.add(entity.getStatus());
        } else
            sqlbuilder.append("null,");
        if (entity.getCloudstatus() != null) {
            sqlbuilder.append("?,");
            objList.add(entity.getCloudstatus());
        } else
            sqlbuilder.append("null,");
        if (entity.getSaveContent() != null) {
            sqlbuilder.append("?,");
            objList.add(entity.getSaveContent());
        } else
            sqlbuilder.append("null,");
        if (entity.getSaveAnalysis() != null) {
            sqlbuilder.append("?,");
            objList.add(entity.getSaveAnalysis());
        } else
            sqlbuilder.append("null,");
        if (entity.getExamtype() != null) {
            sqlbuilder.append("?,");
            objList.add(entity.getExamtype());
        } else
            sqlbuilder.append("null,");
        if (entity.getExamsubjecttype() != null) {
            sqlbuilder.append("?,");
            objList.add(entity.getExamsubjecttype());
        } else
            sqlbuilder.append("null,");
        if (entity.getExamyear() != null) {
            sqlbuilder.append("?,");
            objList.add(entity.getExamyear());
        } else
            sqlbuilder.append("null,");
        if (entity.getAxamarea() != null) {
            sqlbuilder.append("?,");
            objList.add(entity.getAxamarea());
        } else
            sqlbuilder.append("null,");
        if (entity.getProvince() != null) {
            sqlbuilder.append("?,");
            objList.add(entity.getProvince());
        } else
            sqlbuilder.append("null,");
        if (entity.getCity() != null) {
            sqlbuilder.append("?,");
            objList.add(entity.getCity());
        } else
            sqlbuilder.append("null,");
        if (entity.getGrade() != null) {
            sqlbuilder.append("?,");
            objList.add(entity.getGrade());
        } else
            sqlbuilder.append("null,");
        if (entity.getUsecount() != null) {
            sqlbuilder.append("?,");
            objList.add(entity.getUsecount());
        } else
            sqlbuilder.append("null,");
        if (entity.getCuserid() != null) {
            sqlbuilder.append("?,");
            objList.add(entity.getCuserid());
        } else
            sqlbuilder.append("null,");

        if (entity.getCusername() != null) {
            sqlbuilder.append("?,");
            objList.add(entity.getCusername());
        } else
            sqlbuilder.append("null,");
        if(entity.getCorrectanswer()!=null){
            sqlbuilder.append("?,");
            objList.add(entity.getCorrectanswer());
        }else
            sqlbuilder.append("null,");
        sqlbuilder.append("?)}");
        return objList;
    }

}
