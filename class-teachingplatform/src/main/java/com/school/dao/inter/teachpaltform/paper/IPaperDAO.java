
package com.school.dao.inter.teachpaltform.paper;

import com.school.dao.base.ICommonDAO;
import com.school.entity.teachpaltform.paper.PaperInfo;

public interface IPaperDAO extends ICommonDAO<PaperInfo>{
    /**
     * 生成自主测试试卷
     * @param taskid
     * @param userid
     * @return
     */
    public Boolean doGenderZiZhuPaper(Long taskid,Integer userid);
}
