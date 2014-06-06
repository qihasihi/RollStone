
package  com.school.manager.resource;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.school.entity.resource.MyInfoCloudInfo;
import com.school.entity.resource.RsHotRankInfo;
import jxl.Sheet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.school.dao.inter.resource.IResourceDAO;

import com.school.entity.UserInfo;
import com.school.entity.resource.ResourceInfo;
import com.school.dao.base.ICommonDAO;
import com.school.manager.base.BaseManager;
import com.school.manager.inter.resource.IResourceManager;
import com.school.util.PageResult;

@Service
public class  ResourceManager extends BaseManager<ResourceInfo> implements IResourceManager  {

    private IResourceDAO resourcedao;

    @Autowired
    @Qualifier("resourceDAO")
    public void setResourcedao(IResourceDAO resourcedao) {
        this.resourcedao = resourcedao;
    }

    public Boolean doSave(ResourceInfo resourceinfo) {
        return resourcedao.doSave(resourceinfo);
    }

    public Boolean doDelete(ResourceInfo resourceinfo) {
        return resourcedao.doDelete(resourceinfo);
    }

    public Boolean doUpdate(ResourceInfo resourceinfo) {
        return resourcedao.doUpdate(resourceinfo);
    }

    public List<ResourceInfo> getList(ResourceInfo resourceinfo, PageResult presult) {
        return resourcedao.getList(resourceinfo,presult);
    }

    public List<Object> getSaveSql(ResourceInfo resourceinfo, StringBuilder sqlbuilder) {
        return resourcedao.getSaveSql(resourceinfo,sqlbuilder);
    }

    public List<Object> getDeleteSql(ResourceInfo resourceinfo, StringBuilder sqlbuilder) {

        return resourcedao.getDeleteSql(resourceinfo,sqlbuilder);
    }

    public List<Object> getUpdateSql(ResourceInfo resourceinfo, StringBuilder sqlbuilder) {
        return resourcedao.getUpdateSql(resourceinfo,sqlbuilder);
    }

    public Boolean doExcetueArrayProc(List<String> sqlArrayList,
                                      List<List<Object>> objArrayList) {
        return resourcedao.doExcetueArrayProc(sqlArrayList,objArrayList);
    }
    /**
     * �õ�ͬ����SQL
     * @param entity  ����ʵ��
     * @param sqlbuilder  ������SQL��䣬����ʵ������
     * @return
     */
    public List<Object> getSynchroSql(ResourceInfo entity,StringBuilder sqlbuilder){
        return this.resourcedao.getSynchroSql(entity,sqlbuilder);
    }

    public ResourceInfo getOfExcel(Sheet rs, int cols, int d, String type) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected ICommonDAO<ResourceInfo> getBaseDAO() {
        // TODO Auto-generated method stub
        return resourcedao;
    }

    @Override
    public String getNextId() {
        // TODO Auto-generated method stub
        return UUID.randomUUID().toString();
    }

    public Boolean addClicks(String resId) {
        return resourcedao.addClicks(resId);
    }
    public Boolean addDowns(String resId) {
        return resourcedao.addDowns(resId);
    }

    public List<ResourceInfo> getListOfExcellentResource(ResourceInfo resourceinfo, PageResult presult) {
        // TODO Auto-generated method stub
        return resourcedao.getListOfExcellentResource(resourceinfo, presult);
    }

    public List<UserInfo> getListByUserSort(
            ResourceInfo resourceinfo, PageResult presult) {
        // TODO Auto-generated method stub
        return resourcedao.getListByUserSort(resourceinfo, presult);
    }

    public List<ResourceInfo> getListByExtendValue(ResourceInfo resourceinfo,PageResult presult) {
        // TODO Auto-generated method stub
        return resourcedao.getListByExtendValue(resourceinfo, presult);
    }

    public boolean updateResScore(String resid) {
        // TODO Auto-generated method stub
        return resourcedao.updateResScore(resid);
    }

    public boolean doUpdateShareNetShareStatus(int up_netsharestatus, int sharestatus, int resdegree, int netsharestatus) {
        return resourcedao.doUpdateShareNetShareStatus(up_netsharestatus,sharestatus,resdegree,netsharestatus);
    }

    /**
     * ��ѯ�û�
     * @param usernamelike
     * @param userid
     * @param presult
     * @return
     */
    public List<ResourceInfo> getListByUser(String usernamelike,Integer userid,
                                            PageResult presult){
        return resourcedao.getListByUser(usernamelike,userid,presult);
    }
    public List<ResourceInfo> getListBySchoolName(String schoolnameLike,Integer iseq,PageResult presult){
        return resourcedao.getListBySchoolName(schoolnameLike,iseq,presult);
    }

    /**
     * ����ResId�õ�֪ʶ�㼯��
     * @param resid
     * @return
     */
    public List<Map<String,Object>> getResourceCourseList(String resid){
        return this.resourcedao.getResourceCourseList(resid);
    }
    public List<ResourceInfo> getMyResList(ResourceInfo entity,PageResult presult){
        return this.resourcedao.getMyResList(entity,presult);
    }
    /**
     * �õ��ҹ�����б�
     * @param resourceinfo
     * @param presult
     * @return
     */
    public List<ResourceInfo> getCheckListByExtendValue(ResourceInfo resourceinfo, PageResult presult){
        return this.resourcedao.getCheckListByExtendValue(resourceinfo,presult);
    }
    public  List<Object> getUpdateResNum(ResourceInfo res, StringBuilder sqlbuilder){
        return this.resourcedao.getUpdateResNum(res,sqlbuilder);
    }
    /**
     * �õ��ҵ���Դ���а�
     * @param entity
     * @param presult
     * @return
     */
    public List<ResourceInfo> getMyResStaticesRank(ResourceInfo entity, PageResult presult){
        return this.resourcedao.getMyResStaticesRank(entity,presult);
    }
    /**
     * �õ���ҳ����
     * @param entity
     * @param pagesize
     * @return
     */
    public List<Map<String,Object>> getResourceIdxViewRank(ResourceInfo entity,Integer pagesize){
        return this.resourcedao.getResourceIdxViewRank(entity,pagesize);
    }
    /**
     * �õ���ҳ������������
     * @param entity
     * @param pagesize
     * @return
     */
    public List<Map<String,Object>> getResourceIdxDownRank(ResourceInfo entity,Integer pagesize){
        return this.resourcedao.getResourceIdxDownRank(entity,pagesize);
    }
    /**
     * �õ����������
     * @param rs
     * @param presult
     * @return
     */
    public List<ResourceInfo> getResourceIdxViewRankPage(ResourceInfo rs, PageResult presult){
        return this.resourcedao.getResourceIdxViewRankPage(rs, presult);
    }
    /**
     * �õ����а�����SQL���
     * @param entity
     * @param sqlbuilder
     * @return
     */
    public List<Object> getRsHotRankSaveSql(RsHotRankInfo entity,StringBuilder sqlbuilder){
        return this.resourcedao.getRsHotRankSaveSql(entity,sqlbuilder);
    }
    /**
     * �õ����а��ɾ��SQL���
     * @param entity
     * @param sqlbuilder
     * @return
     */
    public List<Object> getRsHotRankDelSql(RsHotRankInfo entity,StringBuilder sqlbuilder){
        return this.resourcedao.getRsHotRankDelSql(entity,sqlbuilder);
    }

    /**
     * �õ���������
     * @param rs
     * @param presult
     * @return
     */
    public List<ResourceInfo> getResourceIdxDownRankPage(ResourceInfo rs, PageResult presult){
        return this.resourcedao.getResourceIdxDownRankPage(rs,presult);
    }
    /**
     * �õ��ƶ���Ϣ��Ϣ
     * @param entity
     * @param sqlbuilder
     * @return
     */
    public List<Object> getMyInfoCloudSaveSql(MyInfoCloudInfo entity,StringBuilder sqlbuilder){
        return this.resourcedao.getMyInfoCloudSaveSql(entity,sqlbuilder);
    }
    /**
     * ��ѯ�ƶ���ض�̬
     * @param rs
     * @param presult
     * @return
     */
    public List<MyInfoCloudInfo> getMyInfoCloudList(MyInfoCloudInfo rs, PageResult presult){
        return this.resourcedao.getMyInfoCloudList(rs,presult);
    }

    /**
     * ��ѯ������̬�ƶ���ض�̬
     * @param rs
     * @param presult
     * @return
     */
    public List<MyInfoCloudInfo> getMyInfoCloudOtherList(MyInfoCloudInfo rs, PageResult presult){
        return this.resourcedao.getMyInfoCloudOtherList(rs,presult);
    }
}

