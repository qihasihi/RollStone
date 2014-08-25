
package  com.school.manager.inter.teachpaltform.paper;

import com.school.entity.teachpaltform.paper.TpCoursePaper;
import com.school.manager.base.IBaseManager;
import com.school.util.PageResult;

import java.util.List;

public interface ITpCoursePaperManager  extends IBaseManager<TpCoursePaper> {
    List<TpCoursePaper> getRelateCoursePaPerList(TpCoursePaper tpcoursepaper, PageResult presult);

    /**
     * ²éÑ¯ÊÇ·ñ´æÔÚAB¾í
     * @param tpCoursePaper
     * @param presult
     * @return
     */
    public List<TpCoursePaper> getABSynchroList(TpCoursePaper tpCoursePaper,PageResult presult);
} 
