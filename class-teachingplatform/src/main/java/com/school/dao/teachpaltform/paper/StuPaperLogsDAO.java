package com.school.dao.teachpaltform.paper;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.school.dao.base.CommonDAO;
import com.school.entity.teachpaltform.paper.StuPaperLogs;
import com.school.dao.inter.teachpaltform.paper.IStuPaperLogsDAO;
import com.school.util.PageResult;

@Component  
public class StuPaperLogsDAO extends CommonDAO<StuPaperLogs> implements IStuPaperLogsDAO {

	public Boolean doSave(StuPaperLogs stupaperlogs) {
		if (stupaperlogs == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getSaveSql(stupaperlogs, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}
	
	public Boolean doDelete(StuPaperLogs stupaperlogs) {
		if(stupaperlogs==null)		
			return false;
		StringBuilder sqlbuilder=new StringBuilder();
		List<Object> objList=getDeleteSql(stupaperlogs, sqlbuilder);
		Object afficeObj=this.executeSacle_PROC(sqlbuilder.toString(), objList.toArray());
		if(afficeObj!=null&&afficeObj.toString().trim().length()>0&&Integer.parseInt(afficeObj.toString())>0){
			return true;
		}return false;
	}

	public Boolean doUpdate(StuPaperLogs stupaperlogs) {
		if (stupaperlogs == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getUpdateSql(stupaperlogs, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}
	
	public List<StuPaperLogs> getList(StuPaperLogs stupaperlogs, PageResult presult) {
		StringBuilder sqlbuilder = new StringBuilder();
		sqlbuilder.append("{CALL stu_paper_logs_proc_split(");
		List<Object> objList=new ArrayList<Object>();
        if (stupaperlogs.getRef() != null) {
            sqlbuilder.append("?,");
            objList.add(stupaperlogs.getRef());
        } else
            sqlbuilder.append("null,");
		if (stupaperlogs.getPaperid() != null) {
			sqlbuilder.append("?,");
			objList.add(stupaperlogs.getPaperid());
		} else
			sqlbuilder.append("null,");

        if (stupaperlogs.getUserid() != null) {
            sqlbuilder.append("?,");
            objList.add(stupaperlogs.getUserid());
        } else
            sqlbuilder.append("null,");
        if (stupaperlogs.getIsinpaper() != null) {
            sqlbuilder.append("?,");
            objList.add(stupaperlogs.getIsinpaper());
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
		List<StuPaperLogs> stupaperlogsList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, StuPaperLogs.class, objArray);
		if(presult!=null&&objArray[0]!=null&&objArray[0].toString().trim().length()>0)
			presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));				
		return stupaperlogsList;	
	}
	
	public List<Object> getSaveSql(StuPaperLogs stupaperlogs, StringBuilder sqlbuilder) {
		if(stupaperlogs==null || sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL stu_paper_logs_proc_add(");
		List<Object>objList = new ArrayList<Object>();
			if (stupaperlogs.getPaperid() != null) {
				sqlbuilder.append("?,");
				objList.add(stupaperlogs.getPaperid());
			} else
				sqlbuilder.append("null,");
			if (stupaperlogs.getUserid() != null) {
				sqlbuilder.append("?,");
				objList.add(stupaperlogs.getUserid());
			} else
				sqlbuilder.append("null,");
			if (stupaperlogs.getScore() != null) {
				sqlbuilder.append("?,");
				objList.add(stupaperlogs.getScore());
			} else
				sqlbuilder.append("null,");
            if (stupaperlogs.getIsinpaper() != null) {
                sqlbuilder.append("?,");
                objList.add(stupaperlogs.getIsinpaper());
            } else
                sqlbuilder.append("null,");
            if (stupaperlogs.getIsmarking() != null) {
                sqlbuilder.append("?,");
                objList.add(stupaperlogs.getIsmarking());
            } else
                sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getDeleteSql(StuPaperLogs stupaperlogs, StringBuilder sqlbuilder) {
		if(stupaperlogs==null || sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL stu_paper_logs_proc_delete(");
		List<Object>objList = new ArrayList<Object>();
			if (stupaperlogs.getRef() != null) {
				sqlbuilder.append("?,");
				objList.add(stupaperlogs.getRef());
			} else
				sqlbuilder.append("null,");
            if (stupaperlogs.getPaperid() != null) {
                sqlbuilder.append("?,");
                objList.add(stupaperlogs.getPaperid());
            } else
                sqlbuilder.append("null,");

            if (stupaperlogs.getUserid() != null) {
                sqlbuilder.append("?,");
                objList.add(stupaperlogs.getUserid());
            } else
                sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getUpdateSql(StuPaperLogs stupaperlogs, StringBuilder sqlbuilder) {
		if(stupaperlogs==null||sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL stu_paper_logs_proc_update(");
		List<Object>objList = new ArrayList<Object>();
			if (stupaperlogs.getRef() != null) {
				sqlbuilder.append("?,");
				objList.add(stupaperlogs.getRef());
			} else
				sqlbuilder.append("null,");
            if (stupaperlogs.getUserid() != null) {
                sqlbuilder.append("?,");
                objList.add(stupaperlogs.getUserid());
            } else
                sqlbuilder.append("null,");
            if (stupaperlogs.getPaperid() != null) {
                sqlbuilder.append("?,");
                objList.add(stupaperlogs.getPaperid());
            } else
                sqlbuilder.append("null,");
			if (stupaperlogs.getScore() != null) {
				sqlbuilder.append("?,");
				objList.add(stupaperlogs.getScore());
			} else
				sqlbuilder.append("null,");
            if (stupaperlogs.getIsinpaper() != null) {
                sqlbuilder.append("?,");
                objList.add(stupaperlogs.getIsinpaper());
            } else
                sqlbuilder.append("null,");
            if (stupaperlogs.getIsmarking() != null) {
                sqlbuilder.append("?,");
                objList.add(stupaperlogs.getIsmarking());
            } else
                sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList; 
	}

//	public Boolean doExcetueArrayProc(List<String> sqlArrayList,
//			List<List<Object>> objArrayList) {
//		return this.executeArray_SQL(sqlArrayList, objArrayList);
//	}

	public String getNextId() {
		// TODO Auto-generated method stub
		return null;
	}

    public List<StuPaperLogs> getMarkingLogs(Long paperid, Long quesid) {
        StringBuilder sqlbuilder = new StringBuilder();
        sqlbuilder.append("{CALL tp_paper_marking_logs_proc_list(");
        List<Object> objList=new ArrayList<Object>();
        if(paperid!=null){
            sqlbuilder.append("?,");
            objList.add(paperid);
        }else{
            sqlbuilder.append("NULL,");
        }
        if(quesid!=null){
            sqlbuilder.append("?");
            objList.add(quesid);
        }else{
            sqlbuilder.append("NULL");
        }
        sqlbuilder.append(")}");
        List<StuPaperLogs> logsList = this.executeResult_PROC(sqlbuilder.toString(),objList,null,StuPaperLogs.class,null);
        return logsList;
    }

    /**
     * 根据学生ID,试卷ID,得到学生得分
     * @param userid
     * @param paperid
     * @return
     */
    public List<Map<String,Object>> getStuPaperSumScore(Integer userid,Long paperid) {
        StringBuilder sqlbuilder = new StringBuilder();
        sqlbuilder.append("{CALL stu_paper_logs_scoresum(");
        List<Object> objList=new ArrayList<Object>();
        if(userid!=null){
            sqlbuilder.append("?,");
            objList.add(userid);
        }else{
            sqlbuilder.append("NULL,");
        }
        if(paperid!=null){
            sqlbuilder.append("?");
            objList.add(paperid);
        }else{
            sqlbuilder.append("NULL");
        }
        sqlbuilder.append(")}");
        return  this.executeResultListMap_PROC(sqlbuilder.toString(),objList);
    }

    public List<Map<String, Object>> getMarkingDetail(Long paperid, Long questionid,Long quesid,Integer ismark,Integer classid,Integer classtype) {
        StringBuilder sqlbuilder = new StringBuilder();
        sqlbuilder.append("{CALL tp_paper_marking_proc_getdetail(");
        List<Object> objList=new ArrayList<Object>();
        if(paperid!=null){
            sqlbuilder.append("?,");
            objList.add(paperid);
        }else{
            sqlbuilder.append("NULL,");
        }
        if(questionid!=null){
            sqlbuilder.append("?,");
            objList.add(questionid);
        }else{
            sqlbuilder.append("NULL,");
        }
        if(quesid!=null){
            sqlbuilder.append("?,");
            objList.add(quesid);
        }else{
            sqlbuilder.append("NULL,");
        }
        if(ismark!=null){
            sqlbuilder.append("?,");
            objList.add(ismark);
        }else{
            sqlbuilder.append("NULL,");
        }
        if(classid!=null){
            sqlbuilder.append("?,");
            objList.add(classid);
        }else{
            sqlbuilder.append("NULL,");
        }
        if(classtype!=null){
            sqlbuilder.append("?");
            objList.add(classtype);
        }else{
            sqlbuilder.append("NULL");
        }
        sqlbuilder.append(")}");
        List<Map<String,Object>> list = this.executeResultListMap_PROC(sqlbuilder.toString(),objList);
        return list;
    }

    public List<Map<String, Object>> getMarkingNum(Long paperid, Long quesid,Integer classid,Integer classtype) {
        StringBuilder sqlbuilder = new StringBuilder();
        sqlbuilder.append("{CALL tp_paper_marking_proc_getlogs(");
        List<Object> objList=new ArrayList<Object>();
        if(paperid!=null){
            sqlbuilder.append("?,");
            objList.add(paperid);
        }else{
            sqlbuilder.append("NULL,");
        }
        if(quesid!=null){
            sqlbuilder.append("?,");
            objList.add(quesid);
        }else{
            sqlbuilder.append("NULL,");
        }
        if(classid!=null){
            sqlbuilder.append("?,");
            objList.add(classid);
        }else{
            sqlbuilder.append("NULL,");
        }
        if(classtype!=null){
            sqlbuilder.append("?");
            objList.add(classtype);
        }else{
            sqlbuilder.append("NULL");
        }
        sqlbuilder.append(")}");
        List<Map<String,Object>> list = this.executeResultListMap_PROC(sqlbuilder.toString(),objList);
        return list;
    }

    public List<Map<String, Object>> getPaperPercentNum(Long paperid, int bignum, int smallnum) {
        StringBuilder sqlbuilder = new StringBuilder();
        sqlbuilder.append("{CALL tp_paper_marking_percent_proc_logs(");
        List<Object> objList=new ArrayList<Object>();
        if(paperid!=null){
            sqlbuilder.append("?,");
            objList.add(paperid);
        }else{
            sqlbuilder.append("NULL,");
        }
        if(bignum>0){
            sqlbuilder.append("?,");
            objList.add(bignum);
        }else{
            sqlbuilder.append("NULL,");
        }
        sqlbuilder.append("?");
        objList.add(smallnum);
        sqlbuilder.append(")}");
        List<Map<String,Object>> list = this.executeResultListMap_PROC(sqlbuilder.toString(),objList);
        return list;
    }
    public List<Map<String, Object>> getPaperPercentNum2(Long paperid, int bignum, int smallnum) {
        StringBuilder sqlbuilder = new StringBuilder();
        sqlbuilder.append("{CALL tp_paper_marking_percent_proc_logs2(");
        List<Object> objList=new ArrayList<Object>();
        if(paperid!=null){
            sqlbuilder.append("?,");
            objList.add(paperid);
        }else{
            sqlbuilder.append("NULL,");
        }
        if(bignum>0){
            sqlbuilder.append("?,");
            objList.add(bignum);
        }else{
            sqlbuilder.append("NULL,");
        }
        sqlbuilder.append("?");
        objList.add(smallnum);
        sqlbuilder.append(")}");
        List<Map<String,Object>> list = this.executeResultListMap_PROC(sqlbuilder.toString(),objList);
        return list;
    }

    public List<Object> getUpdateScoreSql(StuPaperLogs stupaperlogs, StringBuilder sqlbuilder) {
        if(stupaperlogs==null){
            return null;
        }
        sqlbuilder.append("{CALL stu_paper_logs_proc_update_score(");
        List<Object>objList = new ArrayList<Object>();
        if (stupaperlogs.getUserid()!= null) {
            sqlbuilder.append("?,");
            objList.add(stupaperlogs.getUserid());
        } else
            sqlbuilder.append("null,");
        if (stupaperlogs.getPaperid() != null) {
            sqlbuilder.append("?,");
            objList.add(stupaperlogs.getPaperid());
        } else
            sqlbuilder.append("null,");

        if (stupaperlogs.getScore() != null) {
            sqlbuilder.append("?,");
            objList.add(stupaperlogs.getScore());
        } else
            sqlbuilder.append("null,");

        if (stupaperlogs.getIsmarking() != null) {
            sqlbuilder.append("?,");
            objList.add(stupaperlogs.getIsmarking());
        } else
            sqlbuilder.append("null,");
        sqlbuilder.append("?)}");
        return objList;
    }

    public Boolean doUpdateScore(StuPaperLogs stupaperlogs) {
        if (stupaperlogs == null)
            return false;
        StringBuilder sqlbuilder = new StringBuilder();
        List<Object> objList = this.getUpdateScoreSql(stupaperlogs, sqlbuilder);
        Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
                objList.toArray());
        if (afficeObj != null && afficeObj.toString().trim().length() > 0
                && Integer.parseInt(afficeObj.toString()) > 0) {
            return true;
        }
        return false;
    }
}
