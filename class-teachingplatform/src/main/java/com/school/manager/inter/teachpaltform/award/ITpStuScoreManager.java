package com.school.manager.inter.teachpaltform.award;

import com.school.entity.teachpaltform.award.TpGroupScore;
import com.school.entity.teachpaltform.award.TpStuScore;
import com.school.manager.base.IBaseManager;

import java.util.List;
import java.util.Map;

/**
 * Created by zhengzhou on 14-6-24.
 */
public interface ITpStuScoreManager extends IBaseManager<TpStuScore>{
    boolean AddOrUpdate(final TpStuScore entity);
    /**
     * �õ�ҳ���ϵĲ�ѯ
     * @param courseid
     * @param classid
     * @param classtype
     * @return
     */
    public List<Map<String,Object>> getPageDataList(final Long courseid,final Long classid,
                                                    final Integer classtype,final Integer subjectid,
                                                    final String groupidStr,final Integer uid);

    /**
     * ��ʦ�鳤�ύʱ����ʼ���������
     * @param obj
     * @return
     */
    public boolean stuScoreLastInit(final TpStuScore obj,final String groupidArr);


    /**
     * ��ӻ��޸Ļ���
     * @param obj
     * @return
     */
    public List<Object> getAddOrUpdateColScore(final TpStuScore obj,StringBuilder sqlbuilder);

    /**
     * ����ͳ��
     * @param subjectid
     * @param classid
     * @return
     */
    public List<Map<String,Object>> getScoreStatices(final Integer subjectid,final Integer classid,final String sort);
    /**
     * ����ͳ��С�����
     * @param taskid
     * @param classid
     * @param userid
     * @param courseid
     * @param dcschoolid
     * @param sqlbuilder
     * @return
     */
    public List<Object> getUpdateStaticesGroupScore(final Long taskid,final Integer classid
            ,final Integer userid,final Long courseid,final Integer dcschoolid,StringBuilder sqlbuilder);
    /**
     * ��������ִ��ͳ��
     * ���༶����==��¼���ύ������
     * @return
     */
    public boolean tpStuScoreCkAllComplateInput(final Long courseid,final Integer classid,final Integer subjectid,final Integer dcschoolid);
    /**
     * ��֤TpStuScore�Ƿ��ڱ�ר����¼�������
     * @param userid
     * @param courseid
     * @return
     */
    public Integer getTpScoreCourseIsInput(final Integer userid,final Long courseid);
}
