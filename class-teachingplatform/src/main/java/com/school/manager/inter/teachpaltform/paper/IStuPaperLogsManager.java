
package  com.school.manager.inter.teachpaltform.paper;

import com.school.entity.teachpaltform.paper.StuPaperLogs;
import com.school.manager.base.IBaseManager;

import java.util.List;

public interface IStuPaperLogsManager  extends IBaseManager<StuPaperLogs> {
    public List<StuPaperLogs> getMarkingLogs(Integer paperid,Integer quesid);
} 
