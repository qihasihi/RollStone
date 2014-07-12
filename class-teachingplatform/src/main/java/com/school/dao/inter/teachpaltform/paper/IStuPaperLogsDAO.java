
package com.school.dao.inter.teachpaltform.paper;

import com.school.dao.base.ICommonDAO;
import com.school.entity.teachpaltform.paper.StuPaperLogs;

import java.util.*;

public interface IStuPaperLogsDAO extends ICommonDAO<StuPaperLogs>{
    public List<StuPaperLogs> getMarkingLogs(Long paperid,Long quesid);
    public List<Map<String,Object>> getMarkingDetail(Long paperid,Long quesid);
    public List<Map<String,Object>> getMarkingNum(Long paperid,Long quesid);
    public List<Object>  getUpdateScoreSql(StuPaperLogs stupaperlogs, StringBuilder sqlbuilder);
    public Boolean doUpdateScore(StuPaperLogs stupaperlogs);
}
