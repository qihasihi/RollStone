package com.school.dao;

import com.school.dao.base.CommonDAO;

import com.school.dao.inter.IZTestUserInfoDAO;
import com.school.entity.UserInfo;
import com.school.entity.ZTestUserInfo;
import com.school.util.PageResult;
import org.springframework.stereotype.Component;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

@Component
public class ZTestUserInfoDAO extends CommonDAO<ZTestUserInfo> implements IZTestUserInfoDAO {

    public List<ZTestUserInfo> getList(ZTestUserInfo obj, PageResult presult) {

        List<ZTestUserInfo> userList =new ArrayList<ZTestUserInfo>();
        return userList;
    }
	public Boolean doSave(ZTestUserInfo obj) {
		// TODO Auto-generated method stub
//		if (obj == null)
//			return false;
//		StringBuilder sqlbuilder = new StringBuilder();
//		List<Object> objList = this.getSaveSql(obj, sqlbuilder);
//		Object afficeObj = executeSacle_PROC(sqlbuilder.toString(),
//				objList.toArray());
//		if (afficeObj != null && afficeObj.toString().trim().length() > 0
//				&& Integer.parseInt(afficeObj.toString()) > 0) {
//			return true;
//		}
		return false;
	}

	public Boolean doUpdate(ZTestUserInfo obj) {
		// TODO Auto-generated method stub
//		if (obj == null)
//			return false;
//		StringBuilder sqlbuilder = new StringBuilder();
//		List<Object> objList = this.getUpdateSql(obj, sqlbuilder);
//		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
//				objList.toArray());
//		if (afficeObj != null && afficeObj.toString().trim().length() > 0
//				&& Integer.parseInt(afficeObj.toString()) > 0) {
//			return true;
//		}
		return false;
	}

	public Boolean doDelete(ZTestUserInfo obj) {
//		// TODO Auto-generated method stub
//		StringBuilder sqlbuilder=new StringBuilder();
//		List<Object> objList=getDeleteSql(obj, sqlbuilder);
//		Object afficeObj=this.executeSacle_PROC(sqlbuilder.toString(), objList.toArray());
//		if(afficeObj!=null&&afficeObj.toString().trim().length()>0&&Integer.parseInt(afficeObj.toString())>0){
//			return true;
//		}
      return false;
	}
    @Override
    public String getNextId() {
        return super.getNextId();    //To change body of overridden methods use File | Settings | File Templates.
    }
	public List<ZTestUserInfo> getList() {
		// TODO Auto-generated method stub
		
        //先不用数据库的
        List<ZTestUserInfo> zTestUserInfoList=new ArrayList<ZTestUserInfo>();
        ZTestUserInfo zTestUserInfo=new   ZTestUserInfo();
        zTestUserInfo.setId(1);
        zTestUserInfo.setName("liu");
        zTestUserInfoList.add(zTestUserInfo);
        zTestUserInfo.setId(2);
        zTestUserInfo.setName("姚笛");
        zTestUserInfoList.add(zTestUserInfo);
		return zTestUserInfoList;
		
	}

	public List<Object> getSaveSql(ZTestUserInfo obj, StringBuilder sqlbuilder) {
		// TODO Auto-generated method stub

		List<Object> objList = new ArrayList<Object>();

		return objList;
	}

	public List<Object> getUpdateSql(ZTestUserInfo obj, StringBuilder sqlbuilder) {
		// TODO Auto-generated method stub

		List<Object> objList = new ArrayList<Object>();
		return objList;
	}

	public List<Object> getDeleteSql(ZTestUserInfo obj, StringBuilder sqlbuilder) {
		// TODO Auto-generated method stub

		List<Object> objList = new ArrayList<Object>();

		return objList;
	}

//	public Boolean doExcetueArrayProc(List<String> sqlArrayList,
//			List<List<Object>> objArrayList) {
//		// TODO Auto-generated method stub
//		return null;
//	}

	
}
