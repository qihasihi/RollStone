package com.school.dao.teachpaltform.paper;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.school.dao.base.CommonDAO;
import com.school.entity.teachpaltform.paper.PaperInfo;
import com.school.dao.inter.teachpaltform.paper.IPaperDAO;
import com.school.util.PageResult;

@Component  
public class PaperDAO extends CommonDAO<PaperInfo> implements IPaperDAO {

	public Boolean doSave(PaperInfo paperinfo) {
		if (paperinfo == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getSaveSql(paperinfo, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}
	
	public Boolean doDelete(PaperInfo paperinfo) {
		if(paperinfo==null)		
			return false;
		StringBuilder sqlbuilder=new StringBuilder();
		List<Object> objList=getDeleteSql(paperinfo, sqlbuilder);
		Object afficeObj=this.executeSacle_PROC(sqlbuilder.toString(), objList.toArray());
		if(afficeObj!=null&&afficeObj.toString().trim().length()>0&&Integer.parseInt(afficeObj.toString())>0){
			return true;
		}return false;
	}

	public Boolean doUpdate(PaperInfo paperinfo) {
		if (paperinfo == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getUpdateSql(paperinfo, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}
	
	public List<PaperInfo> getList(PaperInfo paperinfo, PageResult presult) {
		StringBuilder sqlbuilder = new StringBuilder();
		sqlbuilder.append("{CALL paper_info_proc_split(");
		List<Object> objList=new ArrayList<Object>();
        if (paperinfo.getRef() != null) {
            sqlbuilder.append("?,");
            objList.add(paperinfo.getRef());
        } else
            sqlbuilder.append("null,");
        if (paperinfo.getPaperid() != null) {
            sqlbuilder.append("?,");
            objList.add(paperinfo.getPaperid());
        } else
            sqlbuilder.append("null,");
        if (paperinfo.getPapername() != null) {
            sqlbuilder.append("?,");
            objList.add(paperinfo.getPapername());
        } else
            sqlbuilder.append("null,");
		if (paperinfo.getCuserid() != null) {
			sqlbuilder.append("?,");
			objList.add(paperinfo.getCuserid());
		} else
			sqlbuilder.append("null,");
        if (paperinfo.getScore() != null) {
            sqlbuilder.append("?,");
            objList.add(paperinfo.getScore());
        } else
            sqlbuilder.append("null,");

		if (paperinfo.getPapertype() != null) {
			sqlbuilder.append("?,");
			objList.add(paperinfo.getPapertype());
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
		List<PaperInfo> paperinfoList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, PaperInfo.class, objArray);
		if(presult!=null&&objArray[0]!=null&&objArray[0].toString().trim().length()>0)
			presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));				
		return paperinfoList;	
	}
	
	public List<Object> getSaveSql(PaperInfo paperinfo, StringBuilder sqlbuilder) {
		if(paperinfo==null || sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL paper_info_proc_add(");
		List<Object>objList = new ArrayList<Object>();
			if (paperinfo.getPaperid() != null) {
				sqlbuilder.append("?,");
				objList.add(paperinfo.getPaperid());
			} else
				sqlbuilder.append("null,");
            if (paperinfo.getPapername() != null) {
                sqlbuilder.append("?,");
                objList.add(paperinfo.getPapername());
            } else
                sqlbuilder.append("null,");
            if (paperinfo.getCuserid() != null) {
                sqlbuilder.append("?,");
                objList.add(paperinfo.getCuserid());
            } else
                sqlbuilder.append("null,");
            if (paperinfo.getScore() != null) {
                sqlbuilder.append("?,");
                objList.add(paperinfo.getScore());
            } else
                sqlbuilder.append("null,");
			if (paperinfo.getPapertype() != null) {
				sqlbuilder.append("?,");
				objList.add(paperinfo.getPapertype());
			} else
				sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getDeleteSql(PaperInfo paperinfo, StringBuilder sqlbuilder) {
		if(paperinfo==null || sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL paper_info_proc_delete(");
		List<Object>objList = new ArrayList<Object>();
            if (paperinfo.getRef() != null) {
                sqlbuilder.append("?,");
                objList.add(paperinfo.getRef());
            } else
                sqlbuilder.append("null,");
			if (paperinfo.getPaperid() != null) {
				sqlbuilder.append("?,");
				objList.add(paperinfo.getPaperid());
			} else
				sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getUpdateSql(PaperInfo paperinfo, StringBuilder sqlbuilder) {
		if(paperinfo==null||sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL paper_info_proc_update(");
		List<Object>objList = new ArrayList<Object>();
            if (paperinfo.getRef() != null) {
                sqlbuilder.append("?,");
                objList.add(paperinfo.getRef());
            } else
                sqlbuilder.append("null,");
            if (paperinfo.getPaperid() != null) {
                sqlbuilder.append("?,");
                objList.add(paperinfo.getPaperid());
            } else
                sqlbuilder.append("null,");
            if (paperinfo.getPapername() != null) {
                sqlbuilder.append("?,");
                objList.add(paperinfo.getPapername());
            } else
                sqlbuilder.append("null,");
			if (paperinfo.getCuserid() != null) {
				sqlbuilder.append("?,");
				objList.add(paperinfo.getCuserid());
			} else
				sqlbuilder.append("null,");
            if (paperinfo.getScore() != null) {
                sqlbuilder.append("?,");
                objList.add(paperinfo.getScore());
            } else
                sqlbuilder.append("null,");
			if (paperinfo.getPapertype() != null) {
				sqlbuilder.append("?,");
				objList.add(paperinfo.getPapertype());
			} else
				sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList; 
	}

    /**
     * 生成自主测试试卷
     * @param taskid
     * @param userid
     * @return
     */
    public Boolean doGenderZiZhuPaper(Long taskid,Integer userid) {
        if (taskid == null||userid==null)
            return false;
        StringBuilder sqlbuilder = new StringBuilder("{CALL j_paper_question_zizhuzujuan(?,?,?)}");
        List<Object> objList=new ArrayList<Object>();
        objList.add(taskid);
        objList.add(userid);
        Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
                objList.toArray());
        if (afficeObj != null && afficeObj.toString().trim().length() > 0
                && Integer.parseInt(afficeObj.toString()) > 0) {
            return true;
        }
        return false;
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
