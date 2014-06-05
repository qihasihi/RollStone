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
     * 得到我管理的列表
     * @param resourceinfo
     * @param presult
     * @return
     */
    public List<ResourceInfo> getCheckListByExtendValue(ResourceInfo resourceinfo, PageResult presult);
	/**
	 * 查询，（带权限）
	 * 
	 * @param resourceinfo
	 * @param presult
	 * @return
	 */
	public List<ResourceInfo> getList(ResourceInfo resourceinfo,PageResult presult);
	
	public boolean updateResScore(String resid);

    /**
     * 同步数据修改netsharestatus 云端状态
     * @param up_netsharestatus  修改后的云端反馈
     * @param sharestatus   条件:分享类型(1:本地共享 2:云端共享 3:不共享)
     * @param resdegree     条件：资源等级（1:标准 2:共享 3:本地）
     * @param netsharestatus 条件：网校分享类型(-1：未同步  0:待审核 1:未通过 2:已删除 3:共享 4:标准)
     * @return
     */
     public boolean doUpdateShareNetShareStatus(int up_netsharestatus, int sharestatus, int resdegree, int netsharestatus);

    /**
     * 查询用户
     * @param usernamelike
     * @param userid
     * @param presult
     * @return
     */
    public List<ResourceInfo> getListByUser(String usernamelike,Integer userid,
                                        PageResult presult);

    public List<ResourceInfo> getListBySchoolName(String schoolnameLike,Integer iseq,PageResult presult);
    /**
     * 得到同步的SQL
     * @param entity  对象实体
     * @param sqlbuilder  传出的SQL语句，必须实例化后
     * @return
     */
    public List<Object> getSynchroSql(ResourceInfo entity,StringBuilder sqlbuilder);

    /**
     * 根据ResId得到知识点集合
     * @param resid
     * @return
     */
    public List<Map<String,Object>> getResourceCourseList(String resid);

    List<ResourceInfo> getMyResList(ResourceInfo entity, PageResult presult);

    List<Object> getUpdateResNum(ResourceInfo res, StringBuilder sqlbuilder);

    /**
     * 得到我的资源排行榜
     * @param entity
     * @param presult
     * @return
     */
    public List<ResourceInfo> getMyResStaticesRank(ResourceInfo entity, PageResult presult);
    /**
     * 得到首页排行
     * @param entity
     * @param pagesize
     * @return
     */
    public List<Map<String,Object>> getResourceIdxViewRank(ResourceInfo entity,Integer pagesize);

    /**
     * 得到首页排行下载排行
     * @param entity
     * @param pagesize
     * @return
     */
    public List<Map<String,Object>> getResourceIdxDownRank(ResourceInfo entity,Integer pagesize);

    /**
     * 得到浏览量排行
     * @param rs
     * @param presult
     * @return
     */
    public List<ResourceInfo> getResourceIdxViewRankPage(ResourceInfo rs, PageResult presult);

    /**
     * 得到排行榜的添加SQL语句
     * @param entity
     * @param sqlbuilder
     * @return
     */
    public List<Object> getRsHotRankSaveSql(RsHotRankInfo entity,StringBuilder sqlbuilder);
    /**
     * 得到排行榜的删除SQL语句
     * @param entity
     * @param sqlbuilder
     * @return
     */
    public List<Object> getRsHotRankDelSql(RsHotRankInfo entity,StringBuilder sqlbuilder);

    List<ResourceInfo> getResourceIdxDownRankPage(ResourceInfo rs, PageResult presult);

    /**
     * 得到云端消息消息
     * @param entity
     * @param sqlbuilder
     * @return
     */
    public List<Object> getMyInfoCloudSaveSql(MyInfoCloudInfo entity,StringBuilder sqlbuilder);

    /**
     * 查询云端相关动态
     * @param rs
     * @param presult
     * @return
     */
    public List<MyInfoCloudInfo> getMyInfoCloudList(MyInfoCloudInfo rs, PageResult presult);
}
