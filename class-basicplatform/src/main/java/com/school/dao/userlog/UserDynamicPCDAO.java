package com.school.dao.userlog;

import com.school.dao.base.CommonDAO;
import com.school.dao.inter.teachpaltform.paper.IPaperDAO;
import com.school.dao.inter.userlog.IUserDynamicPCDAO;
import com.school.entity.userlog.UserDynamicPcLog;
import com.school.util.PageResult;
import org.springframework.stereotype.Component;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

@Component  
public class UserDynamicPCDAO extends CommonDAO<UserDynamicPcLog> implements IUserDynamicPCDAO {

	public Boolean doSave(UserDynamicPcLog t) {
		if (t == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getSaveSql(t, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}
	
	public Boolean doDelete(UserDynamicPcLog t) {
		if(t==null)		
			return false;
		StringBuilder sqlbuilder=new StringBuilder();
		List<Object> objList=getDeleteSql(t, sqlbuilder);
		Object afficeObj=this.executeSacle_PROC(sqlbuilder.toString(), objList.toArray());
		if(afficeObj!=null&&afficeObj.toString().trim().length()>0&&Integer.parseInt(afficeObj.toString())>0){
			return true;
		}return false;
	}

	public Boolean doUpdate(UserDynamicPcLog t) {
		if (t == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getUpdateSql(t, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}
	
	public List<UserDynamicPcLog> getList(UserDynamicPcLog t, PageResult presult) {
		StringBuilder sqlbuilder = new StringBuilder();
		sqlbuilder.append("{CALL paper_info_proc_split(");
		List<Object> objList=new ArrayList<Object>();
        if (t.getRef() != null) {
            sqlbuilder.append("?,");
            objList.add(t.getRef());
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
		List<UserDynamicPcLog> tList=this.executeResult_PROC(sqlbuilder.toString(), objList, types,UserDynamicPcLog.class, objArray);
		if(presult!=null&&objArray[0]!=null&&objArray[0].toString().trim().length()>0)
			presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));				
		return tList;	
	}
	
	public List<Object> getSaveSql(UserDynamicPcLog t, StringBuilder sqlbuilder) {
		if(t==null || sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL user_dynamic_pc_proc_add(");
		List<Object>objList = new ArrayList<Object>();

        if (t.getUserid() != null) {
            sqlbuilder.append("?,");
            objList.add(t.getUserid());
        } else
            sqlbuilder.append("null,");
        if (t.getEttuserid() != null) {
            sqlbuilder.append("?,");
            objList.add(t.getEttuserid());
        } else
            sqlbuilder.append("null,");
        if (t.getUsertype() != null) {
            sqlbuilder.append("?,");
            objList.add(t.getUsertype());
        } else
            sqlbuilder.append("null,");
        if (t.getDynamicid() != null) {
            sqlbuilder.append("?,");
            objList.add(t.getDynamicid());
        } else
            sqlbuilder.append("null,");
        if (t.getSource() != null) {
            sqlbuilder.append("?,");
            objList.add(t.getSource());
        } else
            sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getDeleteSql(UserDynamicPcLog t, StringBuilder sqlbuilder) {
		if(t==null || sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL paper_info_proc_delete(");
		List<Object>objList = new ArrayList<Object>();
            if (t.getRef() != null) {
                sqlbuilder.append("?,");
                objList.add(t.getRef());
            } else
                sqlbuilder.append("null,");


		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getUpdateSql(UserDynamicPcLog t, StringBuilder sqlbuilder) {
		if(t==null||sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL paper_info_proc_update(");
		List<Object>objList = new ArrayList<Object>();
            if (t.getRef() != null) {
                sqlbuilder.append("?,");
                objList.add(t.getRef());
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



//	public Boolean doExcetueArrayProc(List<String> sqlArrayList,
//			List<List<Object>> objArrayList) {
//		return this.executeArray_SQL(sqlArrayList, objArrayList);
//	}
	
	public String getNextId() {
		// TODO Auto-generated method stub
		return null;
	}

}
