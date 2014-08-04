
package com.school.dao.inter.teachpaltform.paper;

import com.school.dao.base.ICommonDAO;
import com.school.entity.teachpaltform.paper.StuPaperLogs;

import java.util.*;

public interface IStuPaperLogsDAO extends ICommonDAO<StuPaperLogs>{
    public List<StuPaperLogs> getMarkingLogs(Long paperid,Long quesid);
    public List<Map<String,Object>> getMarkingDetail(Long paperid,Long questionid,Long quesid,Integer ismark,Integer classid,Integer classtype);
    public List<Map<String,Object>> getMarkingNum(Long paperid,Long quesid,Integer classid,Integer classtype);
    public List<Map<String,Object>> getPaperPercentNum(Long paperid,int bignum,int smallnum);
    public List<Map<String,Object>> getPaperPercentNum2(Long paperid,int bignum,int smallnum);
    public List<Object>  getUpdateScoreSql(StuPaperLogs stupaperlogs, StringBuilder sqlbuilder);
    public Boolean doUpdateScore(StuPaperLogs stupaperlogs);

    /**
     * 根据学生ID,试卷ID,得到学生得分
     * @param userid
     * @param paperid
     * @return
     */
    public List<Map<String,Object>> getStuPaperSumScore(Integer userid,Long paperid);
}
