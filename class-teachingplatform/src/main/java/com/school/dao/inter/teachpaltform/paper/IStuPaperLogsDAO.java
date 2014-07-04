
package com.school.dao.inter.teachpaltform.paper;

import com.school.dao.base.ICommonDAO;
import com.school.entity.teachpaltform.paper.StuPaperLogs;

import java.util.*;

public interface IStuPaperLogsDAO extends ICommonDAO<StuPaperLogs>{
    public List<StuPaperLogs> getMarkingLogs(Integer paperid,Integer quesid);
    public List<Map<String,Object>> getMarkingDetail(Integer paperid,Integer quesid);
    public List<Map<String,Object>> getMarkingNum(Integer paperid,Integer quesid);
}
