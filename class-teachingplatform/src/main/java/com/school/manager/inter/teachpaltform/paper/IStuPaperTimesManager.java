package com.school.manager.inter.teachpaltform.paper;

import com.school.entity.teachpaltform.paper.StuPaperTimesInfo;
import com.school.manager.base.IBaseManager;

import java.util.List;

/**
 * Created by zhengzhou on 15-2-2.
 */
public interface IStuPaperTimesManager extends IBaseManager<StuPaperTimesInfo>{
    /**
     * 得到学生做试卷已经开始，但未提交试卷的记录
     * @param entity
     * @return
     */
    public List<StuPaperTimesInfo> getStuPaperNoCommitUser(final StuPaperTimesInfo entity);
}
