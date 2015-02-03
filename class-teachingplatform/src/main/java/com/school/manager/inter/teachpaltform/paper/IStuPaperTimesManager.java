package com.school.manager.inter.teachpaltform.paper;

import com.school.entity.teachpaltform.paper.StuPaperTimesInfo;
import com.school.manager.base.IBaseManager;

import java.util.List;

/**
 * Created by zhengzhou on 15-2-2.
 */
public interface IStuPaperTimesManager extends IBaseManager<StuPaperTimesInfo>{
    /**
     * �õ�ѧ�����Ծ��Ѿ���ʼ����δ�ύ�Ծ�ļ�¼
     * @param entity
     * @return
     */
    public List<StuPaperTimesInfo> getStuPaperNoCommitUser(final StuPaperTimesInfo entity);
}
