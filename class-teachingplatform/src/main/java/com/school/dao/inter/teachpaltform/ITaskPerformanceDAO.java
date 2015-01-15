
package com.school.dao.inter.teachpaltform;

import java.math.BigDecimal;
import java.util.*;

import com.school.dao.base.ICommonDAO;
import com.school.entity.teachpaltform.TaskPerformanceInfo;

public interface ITaskPerformanceDAO extends ICommonDAO<TaskPerformanceInfo>{
	/**
	 * 获取学生任务完成情况页
	 * @param t
	 * @return
	 */
	List<TaskPerformanceInfo>getPerformListByTaskid(TaskPerformanceInfo t, Long classid,Integer classtype);

    /**
     * 查询选择题答案分布
     * */

    public List<Map<String,Object>> getPerformanceOptionNum(Long taskid,Long classid);

    /**
     * 查询选择题答案分布(小组查询用)
     * */

    public List<Map<String,Object>> getPerformanceOptionNum2(Long taskid,Long classid);

    /**
     * 微视频统计
     * @param taskid
     * @param questionid
     * @return
     */
    public List<Map<String,Object>> getMicPerformanceOptionNum(Long taskid,Long questionid);

    /**
     * 查询答题完成率和正确率
     * */

    public List<Map<String,Object>> getPerformanceNum(Long taskid,Long classid,Integer type);

    /**
     * 查询答题完成率和正确率(小组查询用)
     * */

    public List<Map<String,Object>> getPerformanceNum2(Long taskid,Long classid);

	/**
	 * 查询回答论题的人数
	 * @param t
	 * @return QUERY_columns_PERFORMANCE
	 */
	List<TaskPerformanceInfo>getReplyColumsCount(TaskPerformanceInfo t);
	
	/**
	 * 获取学生任务完成状态
	 */
	List<TaskPerformanceInfo>getStuPerformanceStatus(TaskPerformanceInfo t);

    /**
     * 获取学生任务统计列表
     * */
    public List<Map<String,Object>> getStuSelfPerformance(Integer userid,Long courseid,Integer group,String termid,Integer subjectid);

    /**
     * 获取学生任务统计列表完成率
     * */
    public List<Map<String,Object>> getStuSelfPerformanceNum(Integer userid,String courseids,Integer group,String termid,Integer subjectid);

    /**
     * 教师查询学生成卷测试任务统计
     * */
    public List<List<String>> getCjTaskPerformance(Long taskid,Integer classid,Integer classtype,Integer subjectid);

    /**
     * 教师查询学生成卷测试任务统计
     * */
    public Boolean getCjTaskPerformanceBefor(Long taskid);
 }
