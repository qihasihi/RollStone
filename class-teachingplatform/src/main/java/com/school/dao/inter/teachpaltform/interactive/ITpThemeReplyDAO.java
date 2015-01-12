
package com.school.dao.inter.teachpaltform.interactive;

import com.school.dao.base.ICommonDAO;
import com.school.entity.teachpaltform.interactive.TpThemeReplyInfo;
import com.school.util.PageResult;

import java.util.List;

public interface ITpThemeReplyDAO extends ICommonDAO<TpThemeReplyInfo>{

    public List<TpThemeReplyInfo> getListByThemeIdStr(final String themeidStr,final Integer searchType, PageResult presult);
}
