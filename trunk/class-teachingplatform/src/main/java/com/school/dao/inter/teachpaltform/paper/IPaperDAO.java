
package com.school.dao.inter.teachpaltform.paper;

import com.school.dao.base.ICommonDAO;
import com.school.entity.teachpaltform.paper.PaperInfo;

public interface IPaperDAO extends ICommonDAO<PaperInfo>{
    /**
     * �������������Ծ�
     * @param taskid
     * @param userid
     * @return
     */
    public Boolean doGenderZiZhuPaper(Long taskid,Integer userid);
}
