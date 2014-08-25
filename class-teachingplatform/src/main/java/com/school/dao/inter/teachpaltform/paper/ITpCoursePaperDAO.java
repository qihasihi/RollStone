
package com.school.dao.inter.teachpaltform.paper;

import com.school.dao.base.ICommonDAO;
import com.school.entity.teachpaltform.paper.TpCoursePaper;
import com.school.util.PageResult;

import java.util.List;

public interface ITpCoursePaperDAO extends ICommonDAO<TpCoursePaper>{
    List<TpCoursePaper> getRelateCoursePaPerList(TpCoursePaper tpcoursepaper, PageResult presult);

    /**
     * ��ѯ�Ƿ����AB��
     * @param tpCoursePaper
     * @param presult
     * @return
     */
    public List<TpCoursePaper> getABSynchroList(TpCoursePaper tpCoursePaper,PageResult presult);
}
