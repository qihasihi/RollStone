
package  com.school.manager.inter.teachpaltform.paper;

import com.school.entity.teachpaltform.paper.PaperInfo;
import com.school.manager.base.IBaseManager;

public interface IPaperManager  extends IBaseManager<PaperInfo> {
    /**
     * �������������Ծ�
     * @param taskid
     * @param userid
     * @return
     */
    public Boolean doGenderZiZhuPaper(Long taskid,Integer userid);
} 
