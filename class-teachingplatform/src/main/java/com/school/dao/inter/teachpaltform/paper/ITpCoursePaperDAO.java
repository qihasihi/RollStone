
package com.school.dao.inter.teachpaltform.paper;

import com.school.dao.base.ICommonDAO;
import com.school.entity.teachpaltform.paper.TpCoursePaper;
import com.school.util.PageResult;

import java.util.List;

public interface ITpCoursePaperDAO extends ICommonDAO<TpCoursePaper>{
    List<TpCoursePaper> getRelateCoursePaPerList(TpCoursePaper tpcoursepaper, PageResult presult);

    /**
     * 查询是否存在AB卷
     * @param tpCoursePaper
     * @param presult
     * @return
     */
    public List<TpCoursePaper> getABSynchroList(TpCoursePaper tpCoursePaper,PageResult presult);

    /**
     * 添加关联试卷
     * @param tpcoursepaper
     * @param presult
     * @return
     */
    List<TpCoursePaper> getSelRelatePaPerList(TpCoursePaper tpcoursepaper, PageResult presult);
}
