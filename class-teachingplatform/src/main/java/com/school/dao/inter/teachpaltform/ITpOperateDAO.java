
package com.school.dao.inter.teachpaltform;

import com.school.dao.base.ICommonDAO;
import com.school.entity.teachpaltform.TpOperateInfo;
import com.school.util.PageResult;

import java.util.List;

public interface ITpOperateDAO extends ICommonDAO<TpOperateInfo>{
    /**
     * 得到本次需要更新的操作记录
     * @param ftime  上次更新的时间
     * @param presult 分页
     * @return
     */
    public List<TpOperateInfo> getSynchroList(String ftime,PageResult presult);
}
