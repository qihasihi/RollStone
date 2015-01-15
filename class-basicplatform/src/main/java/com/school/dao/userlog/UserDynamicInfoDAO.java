package com.school.dao.userlog;

import com.school.dao.base.CommonDAO;
import com.school.dao.inter.userlog.IUserDynamicInfoDAO;
import com.school.entity.userlog.UserDynamicInfo;
import com.school.util.PageResult;
import org.springframework.stereotype.Component;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component  
public class UserDynamicInfoDAO extends CommonDAO<UserDynamicInfo> implements IUserDynamicInfoDAO {

	public Boolean doSave(UserDynamicInfo t) {
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
	
	public Boolean doDelete(UserDynamicInfo t) {
		if(t==null)		
			return false;
		StringBuilder sqlbuilder=new StringBuilder();
		List<Object> objList=getDeleteSql(t, sqlbuilder);
		Object afficeObj=this.executeSacle_PROC(sqlbuilder.toString(), objList.toArray());
		if(afficeObj!=null&&afficeObj.toString().trim().length()>0&&Integer.parseInt(afficeObj.toString())>0){
			return true;
		}return false;
	}

	public Boolean doUpdate(UserDynamicInfo t) {
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
	
	public List<UserDynamicInfo> getList(UserDynamicInfo t, PageResult presult) {
		StringBuilder sqlbuilder = new StringBuilder();
		sqlbuilder.append("{CALL paper_info_proc_split(");
		List<Object> objList=new ArrayList<Object>();

        if (t.getDynamicid() != null) {
            sqlbuilder.append("?,");
            objList.add(t.getDynamicid());
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
		List<UserDynamicInfo> tList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, UserDynamicInfo.class, objArray);
		if(presult!=null&&objArray[0]!=null&&objArray[0].toString().trim().length()>0)
			presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));				
		return tList;	
	}
	
	public List<Object> getSaveSql(UserDynamicInfo t, StringBuilder sqlbuilder) {
		if(t==null || sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL paper_info_proc_add(");
		List<Object>objList = new ArrayList<Object>();

			if (t.getDynamicid() != null) {
				sqlbuilder.append("?,");
				objList.add(t.getDynamicid());
			} else
				sqlbuilder.append("null,");

		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getDeleteSql(UserDynamicInfo t, StringBuilder sqlbuilder) {
		if(t==null || sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL paper_info_proc_delete(");
		List<Object>objList = new ArrayList<Object>();
            if (t.getDynamicid() != null) {
                sqlbuilder.append("?,");
                objList.add(t.getDynamicid());
            } else
                sqlbuilder.append("null,");

		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getUpdateSql(UserDynamicInfo t, StringBuilder sqlbuilder) {
		if(t==null||sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL paper_info_proc_update(");
		List<Object>objList = new ArrayList<Object>();
            if (t.getDynamicid() != null) {
                sqlbuilder.append("?,");
                objList.add(t.getDynamicid());
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


	
	public String getNextId() {
		// TODO Auto-generated method stub
		return UUID.randomUUID().toString();
	}

}
