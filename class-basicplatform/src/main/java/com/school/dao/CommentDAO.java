package com.school.dao;

import com.school.dao.base.CommonDAO;
import com.school.dao.inter.ICommentDAO;
import com.school.entity.CommentInfo;
import com.school.util.PageResult;
import org.springframework.stereotype.Component;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component  
public class CommentDAO extends CommonDAO<CommentInfo> implements ICommentDAO {

	public Boolean doSave(CommentInfo commentinfo) {
		if (commentinfo == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getSaveSql(commentinfo, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}
	
	public Boolean doDelete(CommentInfo commentinfo) {
		if(commentinfo==null)		
			return false;
		StringBuilder sqlbuilder=new StringBuilder();
		List<Object> objList=getDeleteSql(commentinfo, sqlbuilder);
		Object afficeObj=this.executeSacle_PROC(sqlbuilder.toString(), objList.toArray());
		if(afficeObj!=null&&afficeObj.toString().trim().length()>0&&Integer.parseInt(afficeObj.toString())>0){
			return true;
		}return false;
	}

	public Boolean doUpdate(CommentInfo commentinfo) {
		if (commentinfo == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getUpdateSql(commentinfo, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}
	
	public List<CommentInfo> getList(CommentInfo commentinfo, PageResult presult) {
		StringBuilder sqlbuilder = new StringBuilder();
		sqlbuilder.append("{CALL comment_info_proc_split(");
		List<Object> objList=new ArrayList<Object>();
		if (commentinfo.getCommentid() != null) {
			sqlbuilder.append("?,");
			objList.add(commentinfo.getCommentid());
		} else
			sqlbuilder.append("null,");
		if (commentinfo.getCommenttype() != null) {
			sqlbuilder.append("?,");
			objList.add(commentinfo.getCommenttype());
		} else
			sqlbuilder.append("null,");
		if (commentinfo.getCommentobjectid() != null) {
			sqlbuilder.append("?,");
			objList.add(commentinfo.getCommentobjectid());
		} else
			sqlbuilder.append("null,");
		if (commentinfo.getCommentcontext() != null) {
			sqlbuilder.append("?,");
			objList.add(commentinfo.getCommentcontext());
		} else
			sqlbuilder.append("null,");
		if (commentinfo.getCommentuserid() != null) {
			sqlbuilder.append("?,");
			objList.add(commentinfo.getCommentuserid());
		} else
			sqlbuilder.append("null,");
		if (commentinfo.getScore() != null) {
			sqlbuilder.append("?,");
			objList.add(commentinfo.getScore());
		} else
			sqlbuilder.append("null,");
		if (commentinfo.getAnonymous() != null) {
			sqlbuilder.append("?,");
			objList.add(commentinfo.getAnonymous());
		} else
			sqlbuilder.append("null,");
		if (commentinfo.getReportuserid() != null) {
			sqlbuilder.append("?,");
			objList.add(commentinfo.getReportuserid());
		} else
			sqlbuilder.append("null,");
		if (commentinfo.getReportcontext() != null) {
			sqlbuilder.append("?,");
			objList.add(commentinfo.getReportcontext());
		} else
			sqlbuilder.append("null,");
        if(commentinfo.getCurrentLoginRef()!=null){
            sqlbuilder.append("?,");
            objList.add(commentinfo.getCurrentLoginRef());
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
		List<CommentInfo> commentinfoList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, CommentInfo.class, objArray);
		if(presult!=null&&objArray[0]!=null&&objArray[0].toString().trim().length()>0)
			presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));				
		return commentinfoList;	
	}
	
	public List<Map<String,Object>> getAvgByClass(Long courseid,Integer classid,Integer classtype,Integer tchid) {
        StringBuilder sqlbuilder = new StringBuilder();
        sqlbuilder.append("{CALL comment_info_proc_split_course_class_avg(?,?,?,?,?)}");
        List<Object> objList=new ArrayList<Object>();
        objList.add(courseid);
        objList.add(classid);
        objList.add(classtype);
        objList.add(tchid);
		List<Map<String,Object>> commentinfoList=this.executeResultListMap_PROC(sqlbuilder.toString(), objList);
		return commentinfoList;	
	}
	
	public List<CommentInfo> getListByClass(Long courseid,Integer classid,Integer classtype,Integer tchid,PageResult presult) {
        if(classid==null || tchid==null)
            return null;
		StringBuilder sqlbuilder = new StringBuilder();
		sqlbuilder.append("{CALL comment_info_proc_split_course_class(?,?,?,?,");
		List<Object> objList=new ArrayList<Object>();
        objList.add(courseid);
        objList.add(classid);
        objList.add(classtype);
        objList.add(tchid);
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

		List<CommentInfo> commentinfoList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, CommentInfo.class, objArray);
		if(presult!=null&&objArray[0]!=null&&objArray[0].toString().trim().length()>0)
			presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));				
		return commentinfoList;	
	}
	
	public List<Object> getSaveSql(CommentInfo commentinfo, StringBuilder sqlbuilder) {
		if(commentinfo==null || sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL comment_info_proc_add(");
		List<Object>objList = new ArrayList<Object>();
			
			if (commentinfo.getCommentid() != null) {
				sqlbuilder.append("?,");
				objList.add(commentinfo.getCommentid());
			} else
				sqlbuilder.append("null,");
			if (commentinfo.getCommenttype() != null) {
				sqlbuilder.append("?,");
				objList.add(commentinfo.getCommenttype());
			} else
				sqlbuilder.append("null,");
			if (commentinfo.getCommentobjectid() != null) {
				sqlbuilder.append("?,");
				objList.add(commentinfo.getCommentobjectid());
			} else
				sqlbuilder.append("null,");
			if (commentinfo.getCommentcontext() != null) {
				sqlbuilder.append("?,");
				objList.add(commentinfo.getCommentcontext());
			} else
				sqlbuilder.append("null,");
			if (commentinfo.getScore() != null) {
				sqlbuilder.append("?,");
				objList.add(commentinfo.getScore());
			} else
				sqlbuilder.append("null,");
			if (commentinfo.getCommentuserid() != null) {
				sqlbuilder.append("?,");
				objList.add(commentinfo.getCommentuserid());
			} else
				sqlbuilder.append("null,");
			if (commentinfo.getAnonymous() != null) {
				sqlbuilder.append("?,");
				objList.add(commentinfo.getAnonymous());
			} else
				sqlbuilder.append("null,");
            if (commentinfo.getCommentparentid() != null) {
                sqlbuilder.append("?,");
                objList.add(commentinfo.getCommentparentid());
            } else
                sqlbuilder.append("null,");
            if (commentinfo.getTouserid() != null) {
                sqlbuilder.append("?,");
                objList.add(commentinfo.getTouserid());
            } else
                sqlbuilder.append("null,");
            if (commentinfo.getTorealname() != null) {
                sqlbuilder.append("?,");
                objList.add(commentinfo.getTorealname());
            } else
                sqlbuilder.append("null,");
            if (commentinfo.getReportuserid() != null) {
                sqlbuilder.append("?,");
                objList.add(commentinfo.getReportuserid());
            } else
                sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getDeleteSql(CommentInfo commentinfo, StringBuilder sqlbuilder) {
		if(commentinfo==null || sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL comment_info_proc_delete(");
		List<Object>objList = new ArrayList<Object>();
			if (commentinfo.getReportuserid() != null) {
				sqlbuilder.append("?,");
				objList.add(commentinfo.getReportuserid());
			} else
				sqlbuilder.append("null,");
			if (commentinfo.getCommentcontext() != null) {
				sqlbuilder.append("?,");
				objList.add(commentinfo.getCommentcontext());
			} else
				sqlbuilder.append("null,");
			if (commentinfo.getCommenttype() != null) {
				sqlbuilder.append("?,");
				objList.add(commentinfo.getCommenttype());
			} else
				sqlbuilder.append("null,");
			if (commentinfo.getCommentid() != null) {
				sqlbuilder.append("?,");
				objList.add(commentinfo.getCommentid());
			} else
				sqlbuilder.append("null,");
			if (commentinfo.getCommentobjectid() != null) {
				sqlbuilder.append("?,");
				objList.add(commentinfo.getCommentobjectid());
			} else
				sqlbuilder.append("null,");
			if (commentinfo.getReportcontext() != null) {
				sqlbuilder.append("?,");
				objList.add(commentinfo.getReportcontext());
			} else
				sqlbuilder.append("null,");
			if (commentinfo.getScore() != null) {
				sqlbuilder.append("?,");
				objList.add(commentinfo.getScore());
			} else
				sqlbuilder.append("null,");
			if (commentinfo.getCommentuserid() != null) {
				sqlbuilder.append("?,");
				objList.add(commentinfo.getCommentuserid());
			} else
				sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getUpdateSql(CommentInfo commentinfo, StringBuilder sqlbuilder) {
		if(commentinfo==null||sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL comment_info_proc_update(");
		List<Object>objList = new ArrayList<Object>();
			if (commentinfo.getCommentid() != null) {
				sqlbuilder.append("?,");
				objList.add(commentinfo.getCommentid());
			} else
				sqlbuilder.append("null,");
			if (commentinfo.getCommenttype() != null) {
				sqlbuilder.append("?,");
				objList.add(commentinfo.getCommenttype());
			} else
				sqlbuilder.append("null,");
			if (commentinfo.getCommentobjectid() != null) {
				sqlbuilder.append("?,");
				objList.add(commentinfo.getCommentobjectid());
			} else
				sqlbuilder.append("null,");
			if (commentinfo.getCommentcontext() != null) {
				sqlbuilder.append("?,");
				objList.add(commentinfo.getCommentcontext());
			} else
				sqlbuilder.append("null,");
			if (commentinfo.getCommentuserid() != null) {
				sqlbuilder.append("?,");
				objList.add(commentinfo.getCommentuserid());
			} else
				sqlbuilder.append("null,");
			if (commentinfo.getAnonymous() != null) {
				sqlbuilder.append("?,");
				objList.add(commentinfo.getAnonymous());
			} else
				sqlbuilder.append("null,");
			if (commentinfo.getReportuserid() != null) {
				sqlbuilder.append("?,");
				objList.add(commentinfo.getReportuserid());
			} else
				sqlbuilder.append("null,");
			if (commentinfo.getReportcontext() != null) {
				sqlbuilder.append("?,");
				objList.add(commentinfo.getReportcontext());
			} else
				sqlbuilder.append("null,");
			if (commentinfo.getSupport() != null) {
				sqlbuilder.append("?,");
				objList.add(commentinfo.getSupport());
			} else
				sqlbuilder.append("null,");
			if (commentinfo.getOppose() != null) {
				sqlbuilder.append("?,");
				objList.add(commentinfo.getOppose());
			} else
				sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList; 
	}

	public boolean doSupportOrOppose(String comment_id, boolean type) {
		// TODO Auto-generated method stub
		if(comment_id==null)
			return false;
		String sql="{call comment_info_proc_support_or_oppose(?,?,?)}";
		List<Object>objList = new ArrayList<Object>();
		objList.add(comment_id);
		objList.add(type);
		Object afficeObj = this.executeSacle_PROC(sql,
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}

    public List<CommentInfo> getResouceCommentList(CommentInfo commentinfo, PageResult presult) {
        StringBuilder sqlbuilder = new StringBuilder();
        sqlbuilder.append("{CALL comment_info_res_proc_split(");
        List<Object> objList=new ArrayList<Object>();
        if (commentinfo.getCommentid() != null) {
            sqlbuilder.append("?,");
            objList.add(commentinfo.getCommentid());
        } else
            sqlbuilder.append("null,");
        if (commentinfo.getCommenttype() != null) {
            sqlbuilder.append("?,");
            objList.add(commentinfo.getCommenttype());
        } else
            sqlbuilder.append("null,");
        if (commentinfo.getCommentobjectid() != null) {
            sqlbuilder.append("?,");
            objList.add(commentinfo.getCommentobjectid());
        } else
            sqlbuilder.append("null,");
        if (commentinfo.getCommentuserid() != null) {
            sqlbuilder.append("?,");
            objList.add(commentinfo.getCommentuserid());
        } else
            sqlbuilder.append("null,");
        if (commentinfo.getReportuserid() != null) {
            sqlbuilder.append("?,");
            objList.add(commentinfo.getReportuserid());
        } else
            sqlbuilder.append("null,");
        if (commentinfo.getCommentparentid() != null) {
            sqlbuilder.append("?,");
            objList.add(commentinfo.getCommentparentid());
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
        List<CommentInfo> commentinfoList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, CommentInfo.class, objArray);
        if(presult!=null&&objArray[0]!=null&&objArray[0].toString().trim().length()>0)
            presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));
        return commentinfoList;
    }

    public List<CommentInfo> getResouceCommentTreeList(CommentInfo commentinfo, PageResult presult) {
        StringBuilder sqlbuilder = new StringBuilder();
        sqlbuilder.append("{CALL tp_qry_res_comment_tree(");
        List<Object> objList=new ArrayList<Object>();
        if (commentinfo.getCommentobjectid()!= null) {
            sqlbuilder.append("?,");
            objList.add(commentinfo.getCommentobjectid());
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
        List<CommentInfo> commentinfoList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, CommentInfo.class, null);
        return commentinfoList;
    }


    public List<Map<String, Object>> getClassList(Long courseid,Integer teacherid,Integer type) {
        StringBuilder sqlbuilder = new StringBuilder();
        sqlbuilder.append("{CALL tp_j_course_class_proc_getclass(?,?,?");
        List<Object> objList=new ArrayList<Object>();
        objList.add(courseid);
        objList.add(teacherid);
        objList.add(type);
        sqlbuilder.append(")}");
        List<Map<String,Object>> classList = this.executeResultListMap_PROC(sqlbuilder.toString(),objList);
        return classList;
    }

    public List<Map<String, Object>> getVirClassList(Long courseid,Integer teacherid,Integer type) {
        StringBuilder sqlbuilder = new StringBuilder();
        sqlbuilder.append("{CALL tp_j_course_class_proc_getvirclass(?,?,?");
        List<Object> objList=new ArrayList<Object>();
        objList.add(courseid);
        objList.add(teacherid);
        objList.add(type);
        sqlbuilder.append(")}");
        List<Map<String,Object>> classList = this.executeResultListMap_PROC(sqlbuilder.toString(),objList);
        return classList;
    }

}