package com.school.manager;

import com.school.dao.base.ICommonDAO;
import com.school.dao.inter.IZTestUserInfoDAO;
import com.school.entity.SubjectInfo;
import com.school.entity.UserInfo;
import com.school.entity.ZTestUserInfo;
import com.school.manager.base.BaseManager;
import com.school.manager.inter.IZTestUserInfoManager;
import com.school.util.PageResult;
import jxl.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ZTestUserInfoManager extends BaseManager<ZTestUserInfo> implements IZTestUserInfoManager  {
    @Autowired
    @Qualifier("ZTestUserInfoDAO")
	private IZTestUserInfoDAO testUserDAO;

    public void setTestUserDAO(IZTestUserInfoDAO testUserDAO) {
        this.testUserDAO = testUserDAO;
    }


    @Autowired


    public  String getNextId()
    {
        return "" ;
    };

    public List<Object> getSaveSql(ZTestUserInfo obj, StringBuilder sqlbuilder) {
        // TODO Auto-generated method stub
        return null;
    }


    public List<Object> getUpdateSql(ZTestUserInfo obj, StringBuilder sqlbuilder) {
        // TODO Auto-generated method stub
        return null;
    }


    public List<Object> getDeleteSql(ZTestUserInfo obj, StringBuilder sqlbuilder) {
        // TODO Auto-generated method stub
        return null;
    }


	@Override
	public Boolean doDelete(ZTestUserInfo obj) {
		//return this.studentdao.doDelete(obj);
        return false;
	}

	@Override
	public Boolean doSave(ZTestUserInfo obj) {
//		return this.studentdao.doSave(obj);
        return false;
	}

	@Override
	public Boolean doUpdate(ZTestUserInfo obj) {
//		return this.studentdao.doUpdate(obj);
        return false;
	}

	@Override
	protected ICommonDAO<ZTestUserInfo> getBaseDAO() {
		return testUserDAO;
	}

	public List<ZTestUserInfo> getList() {
		return this.testUserDAO.getList();
	}




    public List<ZTestUserInfo> getList(ZTestUserInfo obj, PageResult presult) {
        return null;
    }

    public ZTestUserInfo getOfExcel(Sheet rs, int cols, int d, String type) {
        // TODO Auto-generated method stub
        return null;
    }





	
}

