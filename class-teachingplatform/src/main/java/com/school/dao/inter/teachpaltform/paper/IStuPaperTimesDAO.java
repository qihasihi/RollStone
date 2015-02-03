package com.school.dao.inter.teachpaltform.paper;

import com.school.dao.base.ICommonDAO;
import com.school.entity.teachpaltform.paper.StuPaperTimesInfo;

import java.util.List;

/**
 * Created by zhengzhou on 15-2-2.
 */
public interface IStuPaperTimesDAO extends ICommonDAO<StuPaperTimesInfo>{
    /**
     * �õ�ѧ�����Ծ��Ѿ���ʼ����δ�ύ�Ծ�ļ�¼
     * @param entity
     * @return
     */
    public List<StuPaperTimesInfo> getStuPaperNoCommitUser(final StuPaperTimesInfo entity);
}
