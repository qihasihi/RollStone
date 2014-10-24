package com.school.dao.notice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.school.dao.base.CommonDAO;
import com.school.dao.inter.notice.INoticeDAO;
import com.school.entity.activity.ActivityInfo;
import com.school.entity.ethosaraisal.StuEthosInfo;
import com.school.entity.notice.NoticeInfo;
import com.school.util.PageResult;
/**
 * @author 岳春阳
 * @date 2013-05-22
 * @description 通知公告数据访问类 
 */
/**
 * @author admin
 *
 */
/**
 * @author admin
 *
 */
/**
 * @author admin
 *
 */
/**
 * @author admin
 *
 */
/**
 * @author admin
 *
 */
@Component
public class NoticeDAO extends CommonDAO<NoticeInfo> implements INoticeDAO {

	public Boolean doDelete(NoticeInfo obj) {
		// TODO Auto-generated method stub
		if(obj==null||obj.getRef()==null){
			return false;
		}
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objlist = getDeleteSql(obj, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(), objlist.toArray());
		if(afficeObj!=null&&afficeObj.toString().trim().length()>0&&Integer.parseInt(afficeObj.toString())>0){
			return true;
		}
		return false;
	}
	
	public Boolean doSave(NoticeInfo obj) {
		// TODO Auto-generated method stub
		if(obj==null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objlist = getSaveSql(obj, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(), objlist.toArray());
		if(afficeObj != null && afficeObj.toString().trim().length()>0 
				&& Integer.parseInt(afficeObj.toString())>0){
			return true;
		}		
		return false;
	}

	public Boolean doUpdate(NoticeInfo obj) {
		// TODO Auto-generated method stub
		if(obj==null)
			return null;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objlist = getUpdateSql(obj, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(), objlist.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}

	

	public List<Object> getDeleteSql(NoticeInfo obj, StringBuilder sqlbuilder) {
		// TODO Auto-generated method stub
		if(obj==null||obj.getRef()==null){
			return null;
		}
		sqlbuilder.append("{call notice_proc_delete(");
		List<Object> objlist = new ArrayList<Object>();
		if(obj.getRef()!=null&&obj.getRef()!=""){
			sqlbuilder.append("?,");
			objlist.add(obj.getRef());
		}else{
			sqlbuilder.append("NULL");
		}
		sqlbuilder.append("?)}");
		return objlist;
	}

	public List<NoticeInfo> getList(NoticeInfo obj, PageResult presult) {
		// TODO Auto-generated method stub
		if(obj==null)
			return null;
		StringBuilder sqlbuilder = new StringBuilder("{call notice_proc_list(");
		List<Object> objlist = new ArrayList<Object>();
		if(obj.getRef()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getRef());
		}else{
			sqlbuilder.append("NULL,");
		}

        sqlbuilder.append("?,");
        objlist.add(obj.getDcschoolid() );

		if(presult!=null&&presult.getPageNo()>0&&presult.getPageSize()>0){
			sqlbuilder.append("?,?,");
			objlist.add(presult.getPageNo());
			objlist.add(presult.getPageSize());
		}else{
			sqlbuilder.append("NULL,NULL,");
		}
		if(presult!=null&&presult.getOrderBy()!=null&&presult.getOrderBy().trim().length()>0){
			sqlbuilder.append("?,");
			objlist.add(presult.getOrderBy());
		}else{
			sqlbuilder.append("NULL,");
		}
		sqlbuilder.append("?)}");	
		List<Integer> types=new ArrayList<Integer>();
		types.add(Types.INTEGER);
		Object[] objArray=new Object[1];
		List<NoticeInfo> list = this.executeResult_PROC(sqlbuilder.toString(), objlist, types, NoticeInfo.class, objArray);
		if(presult!=null&&objArray[0]!=null&&objArray[0].toString().trim().length()>0)
			presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));	
		return list;
	}

	public List<Object> getSaveSql(NoticeInfo obj, StringBuilder sqlbuilder) {
		// TODO Auto-generated method stub
		List<Object> objlist = new ArrayList<Object>();
		if(obj==null){
			return null;
		}else{		
			sqlbuilder.append("{call notice_proc_add(");
			if(obj.getRef()!=null){
				sqlbuilder.append("?,");
				objlist.add(obj.getRef());
			}else{
				sqlbuilder.append("NULL,");
			}
			if(obj.getNoticetitle()!=null){
				sqlbuilder.append("?,");
				objlist.add(obj.getNoticetitle());
			}else{
				sqlbuilder.append("NULL,");
			}
			if(obj.getNoticecontent()!=null){
//				sqlbuilder.append("?,");
//				objlist.add(obj.getNoticecontent());
                sqlbuilder.append("NULL,");
			}else{
				sqlbuilder.append("NULL,");
			}
			if(obj.getNoticetype()!=null){
				sqlbuilder.append("?,");
				objlist.add(obj.getNoticetype());
			}else{
				sqlbuilder.append("NULL,");
			}
			if(obj.getNoticerole()!=null){
				sqlbuilder.append("?,");
				objlist.add(obj.getNoticerole());
			}else{
				sqlbuilder.append("NULL,");
			}
			if(obj.getNoticegrade()!=null){
				sqlbuilder.append("?,");
				objlist.add(obj.getNoticegrade());
			}else{
				sqlbuilder.append("NULL,");
			}
			if(obj.getIstop()!=null){
				sqlbuilder.append("?,");
				objlist.add(obj.getIstop());
			}else{
				sqlbuilder.append("NULL,");
			}
			if(obj.getClickcount()!=null){
				sqlbuilder.append("?,");
				objlist.add(obj.getClickcount());
			}else{
				sqlbuilder.append("NULL,");
			}
			if(obj.getBegintime()!=null){
				sqlbuilder.append("?,");
				objlist.add(obj.getBegintime());
			}else{
				sqlbuilder.append("NULL,");
			}
			if(obj.getEndtime()!=null){
				sqlbuilder.append("?,");
				objlist.add(obj.getEndtime());
			}else{
				sqlbuilder.append("NULL,");
			}
			if(obj.getCuserid()!=null){
				sqlbuilder.append("?,");
				objlist.add(obj.getCuserid());
			}else{
				sqlbuilder.append("NULL,");
			}
			if(obj.getTitlelink()!=null){
				sqlbuilder.append("?,");
				objlist.add(obj.getTitlelink());
			}else{
				sqlbuilder.append("NULL,");
			}
            if(obj.getIstime()!=null){
                sqlbuilder.append("?,");
                objlist.add(obj.getIstime());
            }else{
                sqlbuilder.append("NULL,");
            }
            sqlbuilder.append("?,");
            objlist.add(obj.getDcschoolid());
			sqlbuilder.append("?)}");
		}
		return objlist;
	}

	public List<Object> getUpdateSql(NoticeInfo obj, StringBuilder sqlbuilder) {
		// TODO Auto-generated method stub
		List<Object> objlist = new ArrayList<Object>();
		if(obj==null){
			return null;
		}else{		
			sqlbuilder.append("{call notice_proc_update(");
			if(obj.getRef()!=null){
				sqlbuilder.append("?,");
				objlist.add(obj.getRef());
			}else{
				sqlbuilder.append("NULL,");
			}
			if(obj.getNoticetitle()!=null){
				sqlbuilder.append("?,");
				objlist.add(obj.getNoticetitle());
			}else{
				sqlbuilder.append("NULL,");
			}
			if(obj.getNoticecontent()!=null){
				sqlbuilder.append("?,");
				objlist.add("");
			}else{
				sqlbuilder.append("NULL,");
			}
			if(obj.getNoticetype()!=null){
				sqlbuilder.append("?,");
				objlist.add(obj.getNoticetype());
			}else{
				sqlbuilder.append("NULL,");
			}
			if(obj.getNoticerole()!=null){
				sqlbuilder.append("?,");
				objlist.add(obj.getNoticerole());
			}else{
				sqlbuilder.append("NULL,");
			}
			if(obj.getNoticegrade()!=null){
				sqlbuilder.append("?,");
				objlist.add(obj.getNoticegrade());
			}else{
				sqlbuilder.append("NULL,");
			}
			if(obj.getIstop()!=null){
				sqlbuilder.append("?,");
				objlist.add(obj.getIstop());
			}else{
				sqlbuilder.append("NULL,");
			}
			if(obj.getClickcount()!=null){
				sqlbuilder.append("?,");
				objlist.add(obj.getClickcount());
			}else{
				sqlbuilder.append("NULL,");
			}
			if(obj.getBegintime()!=null){
				sqlbuilder.append("?,");
				objlist.add(obj.getBegintime());
			}else{
				sqlbuilder.append("NULL,");
			}
			if(obj.getEndtime()!=null){
				sqlbuilder.append("?,");
				objlist.add(obj.getEndtime());
			}else{
				sqlbuilder.append("NULL,");
			}
			if(obj.getCuserid()!=null){
				sqlbuilder.append("?,");
				objlist.add(obj.getCuserid());
			}else{
				sqlbuilder.append("NULL,");
			}
			if(obj.getTitlelink()!=null){
				sqlbuilder.append("?,");
				objlist.add(obj.getTitlelink());
			}else{
				sqlbuilder.append("NULL,");
			}
            if(obj.getIstime()!=null){
                sqlbuilder.append("?,");
                objlist.add(obj.getIstime());
            }else{
                sqlbuilder.append("NULL,");
            }
			sqlbuilder.append("?)}");
		}
		return objlist;
	}
	/**
	 * 获取用户列表
	 */
	public List<NoticeInfo> getUserList(NoticeInfo obj, PageResult presult) {
		// TODO Auto-generated method stub
		if(obj==null)
			return null;
		StringBuilder sqlbuilder = new StringBuilder("{call notice_user_proc_list(");
		List<Object> objlist = new ArrayList<Object>();
		if(obj.getCuserid()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getCuserid());
		}else{
			sqlbuilder.append("NULL,");
		}
		if(obj.getNoticerole()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getNoticerole());
		}else{
			sqlbuilder.append("NULL,");
		}
		if(obj.getNoticegrade()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getNoticegrade());
		}else{
			sqlbuilder.append("NULL,");
		}
		if(obj.getNoticetype()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getNoticetype());
		}else{
			sqlbuilder.append("NULL,");
		}
        sqlbuilder.append("?,");
        objlist.add(obj.getDcschoolid());
		if(presult!=null&&presult.getPageNo()>0&&presult.getPageSize()>0){
			sqlbuilder.append("?,?,");
			objlist.add(presult.getPageNo());
			objlist.add(presult.getPageSize());
		}else{
			sqlbuilder.append("NULL,NULL,");
		}
		if(presult!=null&&presult.getOrderBy()!=null&&presult.getOrderBy().trim().length()>0){
			sqlbuilder.append("?,");
			objlist.add(presult.getOrderBy());
		}else{
			sqlbuilder.append("NULL,");
		}
		sqlbuilder.append("?)}");	
		List<Integer> types=new ArrayList<Integer>();
		types.add(Types.INTEGER);
		Object[] objArray=new Object[1];
		List<NoticeInfo> list = this.executeResult_PROC(sqlbuilder.toString(), objlist, types, NoticeInfo.class, objArray);
		if(presult!=null&&objArray[0]!=null&&objArray[0].toString().trim().length()>0)
			presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));	
		return list;
	}
	/**
	 * 点击浏览次数操作
	 */
	public Boolean noticeClick(String ref) {
		// TODO Auto-generated method stub
		if(ref==null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objlist = new ArrayList<Object>();
		sqlbuilder.append("{call notice_proc_click(");
		sqlbuilder.append("?,");
		objlist.add(ref);
		sqlbuilder.append("?)}");
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(), objlist.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}


}
