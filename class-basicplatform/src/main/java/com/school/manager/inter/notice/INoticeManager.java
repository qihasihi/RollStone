package com.school.manager.inter.notice;

import java.util.List;

import com.school.entity.notice.NoticeInfo;
import com.school.manager.base.IBaseManager;
import com.school.util.PageResult;

public interface INoticeManager extends IBaseManager<NoticeInfo> {
	public List<NoticeInfo> getUserList(NoticeInfo obj, PageResult presult);
	public Boolean noticeClick(String ref);
}
