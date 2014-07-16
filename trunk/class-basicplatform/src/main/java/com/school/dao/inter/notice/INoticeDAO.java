package com.school.dao.inter.notice;

import java.util.List;

import com.school.dao.base.ICommonDAO;
import com.school.entity.notice.NoticeInfo;
import com.school.util.PageResult;

public interface INoticeDAO extends ICommonDAO<NoticeInfo> {
	public List<NoticeInfo> getUserList(NoticeInfo obj, PageResult presult);
	public Boolean noticeClick(String ref);
}
