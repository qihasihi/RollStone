package com.school.dao;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.school.dao.base.CommonDAO;
import com.school.dao.inter.ISubjectDAO;
import com.school.entity.SubjectInfo;
import com.school.util.PageResult;

@Component
public class SubjectDAO extends CommonDAO<SubjectInfo> implements ISubjectDAO{

	public Boolean doSave(SubjectInfo obj) {
		// TODO Auto-generated method stub
		if (obj == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getSaveSql(obj, sqlbuilder);
		Object afficeObj = executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}

	public Boolean doUpdate(SubjectInfo obj) {
		// TODO Auto-generated method stub
		if (obj == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getUpdateSql(obj, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}

	public Boolean doDelete(SubjectInfo obj) {
		// TODO Auto-generated method stub
		StringBuilder sqlbuilder=new StringBuilder();
		List<Object> objList=getDeleteSql(obj, sqlbuilder);
		Object afficeObj=this.executeSacle_PROC(sqlbuilder.toString(), objList.toArray());
		if(afficeObj!=null&&afficeObj.toString().trim().length()>0&&Integer.parseInt(afficeObj.toString())>0){
			return true;
		}return false;
	}

	public List<SubjectInfo> getList(SubjectInfo obj, PageResult presult) {
		// TODO Auto-generated method stub
		
		StringBuilder sqlbuilder=new StringBuilder("{CALL subject_info_proc_split(");
		List<Object> objList=new ArrayList<Object>();
		if(obj==null)
			sqlbuilder.append("NULL,NULL,NULL,");
		else{
            if (obj.getSubjectid() != null) {
                sqlbuilder.append("?,");
                objList.add(obj.getSubjectid());
            } else {
                sqlbuilder.append("NULL,");
            }
			if(obj.getSubjectname()!=null){
				sqlbuilder.append("?,");
				objList.add(obj.getSubjectname());
			}else
				sqlbuilder.append("NULL,");
            if(obj.getLzxsubjectid()!=null){
                sqlbuilder.append("?,");
                objList.add(obj.getLzxsubjectid());
            }else{
                sqlbuilder.append("NULL,");
            }
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
			objList.add(presult.getOrderBy().trim());
		}else
			sqlbuilder.append("NULL,");
		sqlbuilder.append("?)}");	
		List<Integer> types=new ArrayList<Integer>();
		types.add(Types.INTEGER);
		Object[] objArray=new Object[1];
		List<SubjectInfo> subjectInfoList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, SubjectInfo.class, objArray);
		if(presult!=null&&objArray[0]!=null&&objArray[0].toString().trim().length()>0)
			presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));
		return subjectInfoList;
	}

	public List<Object> getSaveSql(SubjectInfo obj, StringBuilder sqlbuilder) {
		// TODO Auto-generated method stub
		if (sqlbuilder == null)
			return null;
		sqlbuilder.append("{CALL subject_info_proc_add(");
		List<Object> objList = new ArrayList<Object>();
		if (obj != null) {
			if (obj.getSubjectname() != null) {
				sqlbuilder.append("?,");
				objList.add(obj.getSubjectname());
			} else {
				sqlbuilder.append("NULL,");
			}
			if(obj.getSubjecttype()!=null){
				sqlbuilder.append("?,");
				objList.add(obj.getSubjecttype());
			}else{
				sqlbuilder.append("NULL,");
			}
            if(obj.getLzxsubjectid()!=null){
                sqlbuilder.append("?,");
                objList.add(obj.getLzxsubjectid());
            }else{
                sqlbuilder.append("NULL,");
            }
		}
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getUpdateSql(SubjectInfo obj, StringBuilder sqlbuilder) {
		// TODO Auto-generated method stub
		sqlbuilder.append("{CALL subject_info_proc_update(");
		List<Object> objList = new ArrayList<Object>();
		if (obj != null) {
			if (obj.getSubjectid() != null) {
				sqlbuilder.append("?,");
				objList.add(obj.getSubjectid());
			} else
				sqlbuilder.append("NULL,");
			if (obj.getSubjectname() != null) {
				sqlbuilder.append("?,");
				objList.add(obj.getSubjectname());
			} else
				sqlbuilder.append("NULL,");

            if(obj.getLzxsubjectid()!=null){
                sqlbuilder.append("?,");
                objList.add(obj.getLzxsubjectid());
            }else{
                sqlbuilder.append("NULL,");
            }
		}
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getDeleteSql(SubjectInfo obj, StringBuilder sqlbuilder) {
		// TODO Auto-generated method stub
		if (sqlbuilder == null)
			return null;
		sqlbuilder.append("{CALL subject_info_proc_delete(");
		List<Object> objList = new ArrayList<Object>();
		if (obj == null) {
			sqlbuilder.append("NULL,");
		} else {
			if (obj.getSubjectid()!= null) {
				sqlbuilder.append("?,");
				objList.add(obj.getSubjectid());
			} else
				sqlbuilder.append("NULL,");
            if(obj.getSubjecttype()!=null){
                sqlbuilder.append("?,");
                objList.add(obj.getSubjecttype());
            }else
                sqlbuilder.append("NULL,");
		}
		sqlbuilder.append("?)}");
		return objList;
	}


    public List<SubjectInfo> getHavaCourseSubject(String termid, String userref, int userid) {
        StringBuilder sqlbuilder=new StringBuilder("{CALL subject_info_proc_havacourse(");
        List<Object> objList=new ArrayList<Object>();
        if(termid==null)
            return null;
        if(termid!=null){
            sqlbuilder.append("?,");
            objList.add(termid);
        }else{
            sqlbuilder.append("NULL,");
        }
        if(userref!=null){
            sqlbuilder.append("?,");
            objList.add(userref);
        }else{
            sqlbuilder.append("NULL,");
        }
        if(userid>0){
            sqlbuilder.append("?");
            objList.add(userid);
        }else{
            sqlbuilder.append("NULL");
        }
        sqlbuilder.append(")}");
        List<SubjectInfo> subjectList = this.executeResult_PROC(sqlbuilder.toString(),objList,null,SubjectInfo.class,null);
        return subjectList;
    }
}
