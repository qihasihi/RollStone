package com.school.manager.notice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import jxl.Sheet;

import com.school.dao.base.ICommonDAO;
import com.school.dao.inter.IClassDAO;
import com.school.dao.inter.notice.INoticeDAO;
import com.school.entity.notice.NoticeInfo;
import com.school.manager.base.BaseManager;
import com.school.manager.inter.notice.INoticeManager;
import com.school.util.PageResult;
/**
 * @author 岳春阳
 * @date 2013-05-22
 * @description 通知公告manager 
 */
@Service
public class NoticeManager extends BaseManager<NoticeInfo> implements
		INoticeManager {
	private INoticeDAO noticedao;
	@Autowired
	@Qualifier("noticeDAO")	
	
	public void setNoticedao(INoticeDAO noticedao) {
		this.noticedao = noticedao;
	}
	@Override
	public Boolean doDelete(NoticeInfo obj) {
		// TODO Auto-generated method stub
		return this.noticedao.doDelete(obj);
	}

	@Override
	public Boolean doSave(NoticeInfo obj) {
		// TODO Auto-generated method stub
        return this.noticedao.doSave(obj);
	}

	@Override
	public Boolean doUpdate(NoticeInfo obj) {
		// TODO Auto-generated method stub
		return this.noticedao.doUpdate(obj);
	}

	@Override
	protected ICommonDAO<NoticeInfo> getBaseDAO() {
		// TODO Auto-generated method stub
		return this.noticedao;
	}

	@Override
	public List<NoticeInfo> getList(NoticeInfo obj, PageResult presult) {
		// TODO Auto-generated method stub
		return this.noticedao.getList(obj, presult);
	}

	@Override
	public String getNextId() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Object> getDeleteSql(NoticeInfo obj, StringBuilder sqlbuilder) {
		// TODO Auto-generated method stub
		return this.noticedao.getDeleteSql(obj, sqlbuilder);
	}

	public NoticeInfo getOfExcel(Sheet rs, int cols, int d, String type) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Object> getSaveSql(NoticeInfo obj, StringBuilder sqlbuilder) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Object> getUpdateSql(NoticeInfo obj, StringBuilder sqlbuilder) {
		// TODO Auto-generated method stub
		return null;
	}
	public List<NoticeInfo> getUserList(NoticeInfo obj, PageResult presult) {
		// TODO Auto-generated method stub
		return this.noticedao.getUserList(obj, presult);
	}
	public Boolean noticeClick(String ref) {
		// TODO Auto-generated method stub
		return this.noticedao.noticeClick(ref);
	}

	
}
