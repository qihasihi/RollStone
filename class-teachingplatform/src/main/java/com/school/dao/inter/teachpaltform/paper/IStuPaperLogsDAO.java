
package com.school.dao.inter.teachpaltform.paper;

import com.school.dao.base.ICommonDAO;
import com.school.entity.teachpaltform.paper.StuPaperLogs;

import java.util.*;

public interface IStuPaperLogsDAO extends ICommonDAO<StuPaperLogs>{
    public List<StuPaperLogs> getMarkingLogs(final Long paperid,final  Long quesid,final Integer classid,final Long taskid);
    public List<Map<String,Object>> getMarkingDetail(final Long paperid,final Long questionid,final Long quesid,final Integer ismark,final Integer classid,final Integer classtype,final Long taskid);
    public List<Map<String,Object>> getMarkingNum(final Long paperid,final Long quesid,final Integer classid,final Integer classtype);
    public List<Map<String, Object>> getPaperPercentNum(Long paperid, int bignum, int smallnum,Integer classid);
    public List<Map<String, Object>> getPaperPercentNum2(Long paperid, int bignum, int smallnum,Integer classid);
    public List<Object>  getUpdateScoreSql(final StuPaperLogs stupaperlogs, StringBuilder sqlbuilder);
    public Boolean doUpdateScore(final StuPaperLogs stupaperlogs);



    /**
     * ����ѧ��ID,�Ծ�ID,�õ�ѧ���÷�
     * @param userid
     * @param paperid
     * @return
     */
    public List<Map<String,Object>> getStuPaperSumScore(final Integer userid,final Long paperid);
}
