
package com.school.dao.inter.teachpaltform;

import java.math.BigDecimal;
import java.util.*;

import com.school.dao.base.ICommonDAO;
import com.school.entity.teachpaltform.TaskPerformanceInfo;

public interface ITaskPerformanceDAO extends ICommonDAO<TaskPerformanceInfo>{
	/**
	 * ��ȡѧ������������ҳ
	 * @param t
	 * @return
	 */
	List<TaskPerformanceInfo>getPerformListByTaskid(TaskPerformanceInfo t, Long classid,Integer classtype);

    /**
     * ��ѯѡ����𰸷ֲ�
     * */

    public List<Map<String,Object>> getPerformanceOptionNum(Long taskid,Long classid);

    /**
     * ��ѯѡ����𰸷ֲ�(С���ѯ��)
     * */

    public List<Map<String,Object>> getPerformanceOptionNum2(Long taskid,Long classid);

    /**
     * ΢��Ƶͳ��
     * @param taskid
     * @param questionid
     * @return
     */
    public List<Map<String,Object>> getMicPerformanceOptionNum(Long taskid,Long questionid);

    /**
     * ��ѯ��������ʺ���ȷ��
     * */

    public List<Map<String,Object>> getPerformanceNum(Long taskid,Long classid,Integer type);

    /**
     * ��ѯ��������ʺ���ȷ��(С���ѯ��)
     * */

    public List<Map<String,Object>> getPerformanceNum2(Long taskid,Long classid);

	/**
	 * ��ѯ�ش����������
	 * @param t
	 * @return QUERY_columns_PERFORMANCE
	 */
	List<TaskPerformanceInfo>getReplyColumsCount(TaskPerformanceInfo t);
	
	/**
	 * ��ȡѧ���������״̬
	 */
	List<TaskPerformanceInfo>getStuPerformanceStatus(TaskPerformanceInfo t);

    /**
     * ��ȡѧ������ͳ���б�
     * */
    public List<Map<String,Object>> getStuSelfPerformance(Integer userid,Long courseid,Integer group,String termid,Integer subjectid);

    /**
     * ��ȡѧ������ͳ���б������
     * */
    public List<Map<String,Object>> getStuSelfPerformanceNum(Integer userid,String courseids,Integer group,String termid,Integer subjectid);

    /**
     * ��ʦ��ѯѧ���ɾ��������ͳ��
     * */
    public List<List<String>> getCjTaskPerformance(Long taskid,Integer classid,Integer classtype,Integer subjectid);

    /**
     * ��ʦ��ѯѧ���ɾ��������ͳ��
     * */
    public Boolean getCjTaskPerformanceBefor(Long taskid);
 }
