package com.school.dao.inter.resource;

import java.util.List;
import java.util.Map;

import com.school.dao.base.ICommonDAO;
import com.school.entity.UserInfo;
import com.school.entity.resource.MyInfoCloudInfo;
import com.school.entity.resource.ResourceInfo;
import com.school.entity.resource.RsHotRankInfo;
import com.school.util.PageResult;

public interface IResourceDAO extends ICommonDAO<ResourceInfo> {

	public List<ResourceInfo> getListByExtendValue(ResourceInfo resourceinfo,
			PageResult presult);

	public List<ResourceInfo> getListOfExcellentResource(ResourceInfo resourceinfo, PageResult presult);

	public Boolean addClicks(String resId);
	
	public Boolean addDowns(String resId) ;

	public List<UserInfo> getListByUserSort(ResourceInfo resourceinfo,
			PageResult presult);
    /**
     * �õ��ҹ�����б�
     * @param resourceinfo
     * @param presult
     * @return
     */
    public List<ResourceInfo> getCheckListByExtendValue(ResourceInfo resourceinfo, PageResult presult);
	/**
	 * ��ѯ������Ȩ�ޣ�
	 * 
	 * @param resourceinfo
	 * @param presult
	 * @return
	 */
	public List<ResourceInfo> getList(ResourceInfo resourceinfo,PageResult presult);
	
	public boolean updateResScore(String resid);

    /**
     * ͬ�������޸�netsharestatus �ƶ�״̬
     * @param up_netsharestatus  �޸ĺ���ƶ˷���
     * @param sharestatus   ����:��������(1:���ع��� 2:�ƶ˹��� 3:������)
     * @param resdegree     ��������Դ�ȼ���1:��׼ 2:���� 3:���أ�
     * @param netsharestatus ��������У��������(-1��δͬ��  0:����� 1:δͨ�� 2:��ɾ�� 3:���� 4:��׼)
     * @return
     */
     public boolean doUpdateShareNetShareStatus(int up_netsharestatus, int sharestatus, int resdegree, int netsharestatus);

    /**
     * ��ѯ�û�
     * @param usernamelike
     * @param userid
     * @param presult
     * @return
     */
    public List<ResourceInfo> getListByUser(String usernamelike,Integer userid,
                                        PageResult presult);

    public List<ResourceInfo> getListBySchoolName(String schoolnameLike,Integer iseq,PageResult presult);
    /**
     * �õ�ͬ����SQL
     * @param entity  ����ʵ��
     * @param sqlbuilder  ������SQL��䣬����ʵ������
     * @return
     */
    public List<Object> getSynchroSql(ResourceInfo entity,StringBuilder sqlbuilder);

    /**
     * ����ResId�õ�֪ʶ�㼯��
     * @param resid
     * @return
     */
    public List<Map<String,Object>> getResourceCourseList(String resid);

    List<ResourceInfo> getMyResList(ResourceInfo entity, PageResult presult);

    List<Object> getUpdateResNum(ResourceInfo res, StringBuilder sqlbuilder);

    /**
     * �õ��ҵ���Դ���а�
     * @param entity
     * @param presult
     * @return
     */
    public List<ResourceInfo> getMyResStaticesRank(ResourceInfo entity, PageResult presult);
    /**
     * �õ���ҳ����
     * @param entity
     * @param pagesize
     * @return
     */
    public List<Map<String,Object>> getResourceIdxViewRank(ResourceInfo entity,Integer pagesize);

    /**
     * �õ���ҳ������������
     * @param entity
     * @param pagesize
     * @return
     */
    public List<Map<String,Object>> getResourceIdxDownRank(ResourceInfo entity,Integer pagesize);

    /**
     * �õ����������
     * @param rs
     * @param presult
     * @return
     */
    public List<ResourceInfo> getResourceIdxViewRankPage(ResourceInfo rs, PageResult presult);

    /**
     * �õ����а�����SQL���
     * @param entity
     * @param sqlbuilder
     * @return
     */
    public List<Object> getRsHotRankSaveSql(RsHotRankInfo entity,StringBuilder sqlbuilder);
    /**
     * �õ����а��ɾ��SQL���
     * @param entity
     * @param sqlbuilder
     * @return
     */
    public List<Object> getRsHotRankDelSql(RsHotRankInfo entity,StringBuilder sqlbuilder);

    List<ResourceInfo> getResourceIdxDownRankPage(ResourceInfo rs, PageResult presult);

    /**
     * �õ��ƶ���Ϣ��Ϣ
     * @param entity
     * @param sqlbuilder
     * @return
     */
    public List<Object> getMyInfoCloudSaveSql(MyInfoCloudInfo entity,StringBuilder sqlbuilder);

    /**
     * ��ѯ�ƶ���ض�̬
     * @param rs
     * @param presult
     * @return
     */
    public List<MyInfoCloudInfo> getMyInfoCloudList(MyInfoCloudInfo rs, PageResult presult);
}
