package com.school.control.base;


import FTsafe.Ry4S;
import com.school.entity.RoleUser;
import com.school.entity.UserInfo;
import com.school.util.JsonEntity;
import com.school.util.PageResult;
import com.school.util.SpringBeanUtil;
import com.school.util.UtilTool;
import com.school.util.jqgrid.JQGridConditionEntity;
import com.school.util.jqgrid.JQGridDetial;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.ConvertUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.*;
import java.util.List;

/**
 * 控制器基类
 *
 * @author zhengzhou
 */
@Controller
@Scope("prototype")
public class BaseController<T extends java.io.Serializable> {




    /**
     * 得到Manager  以Class为例  getManager(ClassManager.getClass());
     * @param cls    要转换的实体类名称
     * @param <T>
     * @return
     *
     */
    public <T> T getManager(Class<T> cls){
        return  SpringBeanUtil.getBean(cls.getSimpleName(), cls);
    }
    public BaseController(){};
//    /**初始化向导*/
//    @Autowired
//    @Qualifier("initWizardManager")
//    public IInitWizardManager initWizardManager;
//
//	/********业务逻辑**********/
//	@Autowired
//	@Qualifier("termManager")
//	public ITermManager termManager;
//
//
//	@Autowired
//	@Qualifier("operateExcelManager")
//	public IOperateExcelManager operaterexcelmanager;
//
//	@Autowired
//	@Qualifier("smsManager")
//	public ISmsManager smsManager;
//
//	@Autowired
//	@Qualifier("smsReceiverManager")
//	public ISmsReceiverManager smsReceiverManager;
//	/**
//	 * paggrightmanager
//	 */
//	@Autowired
//	@Qualifier("pageRightManager")
//	public IPageRightManager pageRightManager;
//
//
//	/**
//	 * rolemanager
//	 */
//	@Autowired
//	@Qualifier("roleManager")
//	public IRoleManager roleManager;
//
//	/**
//	 * usermanager
//	 */
//	@Autowired
//	@Qualifier("userManager")
//	public IUserManager userManager;
//
//	@Autowired
//	@Qualifier("jobManager")
//	public IJobManager jobManager;
//
//	@Autowired
//	@Qualifier("subjectManager")
//	public ISubjectManager subjectManager;
//
//	/**
//	 * classyearmanager 学年
//	 */
//	@Autowired
//	@Qualifier("classYearManager")
//	public IClassYearManager classYearManager;
//
//	/**
//	 * grademanager 年级
//	 */
//	@Autowired
//	@Qualifier("gradeManager")
//	public IGradeManager gradeManager;
//
//	/**
//	 *班级
//	 */
//	@Autowired
//	@Qualifier("classManager")
//	public IClassManager classManager;
//
//	/**
//	 * 学生
//	 */
//	@Autowired
//	@Qualifier("studentManager")
//	public IStudentManager studentManager;
//
//	/**
//	 * 教师
//	 */
//	@Autowired
//	@Qualifier("teacherManager")
//	public ITeacherManager teacherManager;
//
//	/**
//	 * 家长
//	 */
//	@Autowired
//	@Qualifier("parentManager")
//	public IParentManager parentManager;
//
//	/**
//	 * 角色与用户
//	 */
//	@Autowired
//	@Qualifier("roleUserManager")
//	public IRoleUserManager roleUserManager;
//
//	/**
//	 * 角色与用户
//	 */
//	@Autowired
//	@Qualifier("dictionaryManager")
//	public IDictionaryManager dictionaryManager;
//
//	/**
//	 * 班级与用户
//	 */
//	@Autowired
//	@Qualifier("classUserManager")
//	public IClassUserManager classUserManager;
//
//	/**
//	 * 部门
//	 */
//	@Autowired
//	@Qualifier("deptManager")
//	public IDeptManager deptManager;
//
//	/**
//	 * 部门与用户
//	 */
//	@Autowired
//	@Qualifier("deptUserManager")
//	public IDeptUserManager deptUserManager;
//
//	/**
//	 * 用户与权限
//	 */
//	@Autowired
//	@Qualifier("rightUserManager")
//	public IRightUserManager rightUserManager;
//
//    /**
//     * 学科与用户
//     */
//    @Autowired
//    @Qualifier("subjectUserManager")
//    public ISubjectUserManager subjectUserManager;
//
//    /**
//     * 学科与用户
//     */
//    @Autowired
//    @Qualifier("guideManager")
//    public IGuideManager guideManager;
//
//	/**
//	 * 职务与用户
//	 */
//	@Autowired
//	@Qualifier("jobUserManager")
//	public IJobUserManager jobUserManager;
//
//	/**
//	 * 评论
//	 */
//	@Autowired
//	@Qualifier("commentManager")
//	public ICommentManager commentManager;
//
//	/**
//	 * 打分
//	 */
//	@Autowired
//	@Qualifier("scoreManager")
//	public IScoreManager scoreManager;
//
//
//    /**
//     * 测试用户
//     */
//    @Autowired
//    @Qualifier("ZTestUserInfoManager")
//    public IZTestUserInfoManager testUserManager;
//
//
//
//    /********************************互动空间************************************/
//    /**
//     * 专题论题
//     */
//    @Autowired
//    @Qualifier("tpTopicManager")
//    public ITpTopicManager tpTopicManager;
//
//    @Autowired
//    @Qualifier("schoolManager")
//    public ISchoolManager schoolManager;
//    /**
//     *专题 论题主题
//     */
//    @Autowired
//    @Qualifier("tpTopicThemeManager")
//    public ITpTopicThemeManager tpTopicThemeManager;
//
//    /**
//     *专题 主题评论
//     */
//    @Autowired
//    @Qualifier("tpThemeReplyManager")
//    public ITpThemeReplyManager tpThemeReplyManager;
//
//    /**
//     * 操作元素表
//     */
//    @Autowired
//    @Qualifier("tpOperateManager")
//    public ITpOperateManager tpOperateManager;
//
//
//    /******************************** 评教系统***********************************/
//	/**
//	 * 评教时间管理
//	 */
//	@Autowired
//	@Qualifier("timeStepManager")
//	public ITimeStepManager timeStepManager;
//
//	/**
//	 * 评教项管理
//	*/
//	@Autowired
//	@Qualifier("appraiseItemManager")
//	public IAppraiseItemManager appraiseItemManager;
//
//	/**
//	 * 评教过程日志
//	*/
//	@Autowired
//	@Qualifier("appraiseLogsManager")
//	public IAppraiseLogsManager appraiseLogsManager;
//
//	/**
//	 * 首页与用户消息
//	*/
//	@Autowired
//	@Qualifier("myInfoUserManager")
//	public IMyInfoUserManager myInfoUserManager;
//
//	/**
//	 * 模版
//	*/
//	@Autowired
//	@Qualifier("myInfoTemplateManager")
//	public IMyInfoTemplateManager myInfoTemplateManager;
//
//
//	/******************************** 活动管理***********************************/
//
//	/**
//	 * 活动
//	 */
//	@Autowired
//	@Qualifier("activityManager")
//	public IActivityManager activityManager;
//
//	/**
//	 * 活动场地
//	 */
//	@Autowired
//	@Qualifier("siteManager")
//	public ISiteManager siteManager;
//
//	/**
//	 * 活动场地关系
//	 */
//	@Autowired
//	@Qualifier("activitySiteManager")
//	public IActivitySiteManager activitySiteManager;
//
//	/**
//	 * 活动用户关系
//	 */
//	@Autowired
//	@Qualifier("activityUserManager")
//	public IActivityUserManager activityUserManager;
//
//	/******************************** 校风评比***********************************/
//
//	/**
//	 * 学生校风
//	 */
//	@Autowired
//	@Qualifier("stuEthosManager")
//	public IStuEthosManager stuEthosManager;
//
//	/**
//	 * 班级校风
//	 */
//	@Autowired
//	@Qualifier("classEthosManager")
//	public IClassEthosManager classEthosManager;
//
//	/**
//	 * 班级校风
//	 */
//	@Autowired
//	@Qualifier("weekManager")
//	public IWeekManager weekManager;
//
//
//	/********************************通知公告***********************************/
//
//	/**
//	 * 通知公告
//	 */
//	@Autowired
//	@Qualifier("noticeManager")
//	public INoticeManager noticeManager;
//
//    /******************************** 教学系统***********************************/
//    /**
//     * 教材
//     */
//    @Autowired
//    @Qualifier("teachingMaterialManager")
//    public ITeachingMaterialManager teachingMaterialManager;
//
//    /**
//     * 教材与专题
//     */
//    @Autowired
//    @Qualifier("tpCourseTeachingMaterialManager")
//    public ITpCourseTeachingMaterialManager tpCourseTeachingMaterialManager;
//
//    /**
//     * 教师与教材，年级，学科对应关系表
//     */
//    @Autowired
//    @Qualifier("tpTeacherTeachMaterialManager")
//    public ITpTeacherTeachMaterialManager tpTeacherTeachMaterialManager;
//
//
//
//    /**
//     * 专题
//     */
//    @Autowired
//    @Qualifier("tpCourseManager")
//    public ITpCourseManager tpCourseManager;
//
//    /**
//     * 专题与班级
//     */
//    @Autowired
//    @Qualifier("tpCourseClassManager")
//    public ITpCourseClassManager tpCourseClassManager;
//
//    /**
//     * 小组
//     */
//    @Autowired
//    @Qualifier("tpGroupManager")
//    public ITpGroupManager tpGroupManager;
//
//    /**
//     * 小组与学生
//     */
//    @Autowired
//    @Qualifier("tpGroupStudentManager")
//    public ITpGroupStudentManager tpGroupStudentManager;
//
//    /**
//     * 虚拟班级
//     */
//    @Autowired
//    @Qualifier("tpVirtualClassManager")
//    public ITpVirtualClassManager tpVirtualClassManager;
//
//    /**
//     * 虚拟班级与学生
//     */
//    @Autowired
//    @Qualifier("tpVirtualClassStudentManager")
//    public ITpVirtualClassStudentManager tpVirtualClassStudentManager;
//
//    /**
//     * 班级托管
//     */
//    @Autowired
//    @Qualifier("trusteeShipClassManager")
//    public ITrusteeShipClassManager trusteeShipClassManager;
//
//    /**
//     * 试题
//     */
//    @Autowired
//    @Qualifier("questionManager")
//    public IQuestionManager questionManager;
//
//    /**
//     * 试题选项
//     */
//    @Autowired
//    @Qualifier("questionOptionManager")
//    public IQuestionOptionManager questionOptionManager;
//
//    /**
//     * 试题与专题
//     */
//    @Autowired
//    @Qualifier("tpCourseQuestionManager")
//    public ITpCourseQuestionManager tpCourseQuestionManager;
//
//    /**
//     * 专题资源
//     */
//    @Autowired
//    @Qualifier("tpCourseResourceManager")
//    public ITpCourseResourceManager tpCourseResourceManager;
//
//    /**
//     * 任务
//     */
//    @Autowired
//    @Qualifier("tpTaskManager")
//    public ITpTaskManager tpTaskManager;
//
//
//    /**
//     * 任务回答表
//     */
//    @Autowired
//    @Qualifier("questionAnswerManager")
//    public IQuestionAnswerManager questionAnswerManager;
//
//
//
//    /**
//     *学生建议
//     */
//    @Autowired
//    @Qualifier("taskSuggestManager")
//    public ITaskSuggestManager taskSuggestManager;
//
//
//
//    /**
//     * 完成情况
//     */
//    @Autowired
//    @Qualifier("taskPerformanceManager")
//    public ITaskPerformanceManager taskPerformanceManager;
//
//
//    /**
//     * 任务分配
//     */
//    @Autowired
//    @Qualifier("tpTaskAllotManager")
//    public ITpTaskAllotManager tpTaskAllotManager;
//
//
//    /**
//     * 专题资源浏览记录
//     */
//    @Autowired
//    @Qualifier("tpResourceViewManager")
//    public ITpResourceViewManager tpResourceViewManager;
//
//
//
//    /**
//     * 专题资源收藏记录
//     */
//    @Autowired
//    @Qualifier("tpResourceCollectManager")
//    public ITpResourceCollectManager tpResourceCollectManager;
//
////    /**
////     * 虚拟班级
////     * */
////    @Autowired
////    @Qualifier("tpVirtualClassManager")
////    public ITpVirtualClassManager tpVirtualClassManager;
//
//
//    /******************************** 资源系统***********************************/
//
//	/**
//	 * 扩展属性类
//	 */
//	@Autowired
//	@Qualifier("extendManager")
//	public IExtendManager extendManager;
//
//
//	/**
//	 * 扩展属性值
//	 */
//	@Autowired
//	@Qualifier("extendValueManager")
//	public IExtendValueManager extendValueManager;
//
//	/**
//	 * 资源信息
//	 */
//	@Autowired
//	@Qualifier("resourceManager")
//	public IResourceManager resourceManager;
//
//	/**
//	 * 资源权限
//	 */
//	@Autowired
//	@Qualifier("resourceRightManager")
//	public IResourceRightManager resourceRightManager;
//
//
//
//	/**
//	 * 资源文件
//	 */
//	@Autowired
//	@Qualifier("resourceFileManager")
//	public IResourceFileManager resourceFileManager;
//
//	/**
//	 * 资源文件
//	 */
//	@Autowired
//	@Qualifier("extendResourceManager")
//	public IExtendResourceManager extendResourceManager;
//
//	/**
//	 * 资源收藏
//	 */
//	@Autowired
//	@Qualifier("storeManager")
//	public IStoreManager storeManager;
//
//	/**
//	 * 资源推荐
//	 */
//	@Autowired
//	@Qualifier("resourceRecommendManager")
//	public IResourceRecommendManager resourceRecommendManager;
//	/**
//	 * 资源浏览
//	 */
//	@Autowired
//	@Qualifier("accessManager")
//	public IAccessManager accessManager;
//
//	/**
//	 * 资源下载情况
//	 */
//	@Autowired
//	@Qualifier("downloadManager")
//	public IDownloadManager downloadManager;
//
//	/**
//	 * 资源特殊操作
//	 */
//	@Autowired
//	@Qualifier("operateRecordManager")
//	public IOperateRecordManager operateRecordManager;
//
//	/**
//	 * 资源举报操作
//	 */
//	@Autowired
//	@Qualifier("resourceReportManager")
//	public IResourceReportManager resourceReportManager;
//
//
//	@Autowired
//	@Qualifier("checkManager")
//	public ICheckManager checkManager;
//
//	/**************验证功能权限****************/
//	@Autowired
//	@Qualifier("fnClickManager")
//	public IFnClickManager fnclickManager;
//
//	/**************同行评价****************/
//	/**
//	 * 主题设置
//	 */
//	@Autowired
//	@Qualifier("pjPeerBaseManager")
//	public IPjPeerBaseManager pjPeerBaseManager;
//
//	/**
//	 * 评价项设置
//	 */
//	@Autowired
//	@Qualifier("pjPeerItemManager")
//	public IPjPeerItemManager pjPeerItemManager;
//
//	/**
//	 * 参评用户
//	 */
//	@Autowired
//	@Qualifier("pjPeerUserManager")
//	public IPjPeerUserManager pjPeerUserManager;
//
//	/**
//	 * 评价数据
//	 */
//	@Autowired
//	@Qualifier("pjPeerLogManager")
//	public IPjPeerLogManager pjPeerLogManager;
//
//	/**
//	 * 视频转换时间设置
//	 */
//	@Autowired
//	@Qualifier("videoTimeManager")
//	public IVideoTimeManager videoTimeManager;
//
//
//	/**
//	 * 栏目
//	 */
//
//	@Autowired
//	@Qualifier("columnManager")
//	public IColumnManager columnManager;
//
//	/**
//	 * 栏目权限
//	 */
//	@Autowired
//	@Qualifier("columnRightManager")
//	public IColumnRightManager columnRightManager;
//
//
//	/**
//	 * 栏目权限与页面权限
//	 */
//	@Autowired
//	@Qualifier("columnRightPageRightManager")
//	public IColumnRightPageRightManager columnRightPageRightManager;
//
//
//	/**
//	 * 身份
//	 */
//	@Autowired
//	@Qualifier("identityManager")
//	public IIdentityManager identityManager;
//
//	/**
//	 * 角色与栏目权限
//	 */
//	@Autowired
//	@Qualifier("roleColumnRightManager")
//	public IRoleColumnRightManager roleColumnRightManager;
//
//	/**
//	 * 用户与栏目权限
//	 */
//	@Autowired
//	@Qualifier("userColumnRightManager")
//	public IUserColumnRightManager userColumnRightManager;
//
//	/**
//	 * 用户与身份
//	 */
//	@Autowired
//	@Qualifier("userIdentityManager")
//	public IUserIdentityManager userIdentityManager;
//
//    /******************************** 用户积分详情 ***********************************/
//    /**
//     * 积分类型
//     * */
//	@Autowired
//    @Qualifier("scoreTypeManager")
//    public IScoreTypeManager scoreTypeManager;
//    /**
//     * 用户总分
//     * */
//    @Autowired
//    @Qualifier("userModelTotalScoreManager")
//    public IUserModelTotalScoreManager userModelTotalScoreManager;
//    /**
//     * 用户积分记录
//     * */
//    @Autowired
//    @Qualifier("userModelScoreLogsManager")
//    public IUserModelScoreLogsManager userModelScoreLogsManager;
//

    /******************************** 公共方法区域 ***********************************/


    /**
     * 将参数转换成实体成型
     * @param request
     * @param clazz
     */
    public T getParameter(HttpServletRequest request,
                          Class<? extends Object> clazz) throws Exception {
        Object obj = null;

        if (request == null || clazz == null)
            return null;
        Enumeration<String> paraNameStrArr = request.getParameterNames();
        if (paraNameStrArr != null) {
            obj = clazz.newInstance();
            while (paraNameStrArr.hasMoreElements()) {

                String paraNameStr = paraNameStrArr.nextElement();

                Object paraVal = request.getParameter(paraNameStr);
                // 进行封装成Entity
                if (clazz != null && obj != null&&paraVal!=null) {
                    Method[] methods = clazz.getMethods(); // 获取
                    // 该类的所有方法
                    // 查找所有方法中的set方法 利用反射把取出的字段通过set方法对对象中的字段赋值
                    String colName = paraNameStr;
                    // 获取Set方法名字
                    String methodName = "set"
                            + colName.substring(0, 1).toUpperCase() // 第1个大写
                            + colName.substring(1).replace("_", "");

                    // _empty适用于jgrid的添加
                    if (paraVal != null && !paraVal.equals("_empty"))
                        for (Method m : methods)
                            if (methodName.equals(m.getName())) {
                                Object val=paraVal;
                                //  System.out.println(m.getName()+"  "+val);
                                if(m.getParameterTypes()!=null&&m.getParameterTypes().length>0&&val!=null&&
                                        !m.getParameterTypes()[0].getSimpleName().toLowerCase().equals("string"))
                                    val= ConvertUtils.convert(val.toString(), m.getParameterTypes()[0]);

                                m.invoke(obj, val); // obj:被实例化的类
                                break;
                            }
                }
            }
        }
        return (T) obj;
    }

    /**
     * 验证当前用户的角色
     *
     * @param rid
     */
    public boolean validateRole(HttpServletRequest request,Integer rid) {
        boolean returnVal = false;
        UserInfo cuuser = request.getAttribute("tmpUser")==null?this.logined(request):(UserInfo)request.getAttribute("tmpUser");
        if (cuuser != null && cuuser.getCjJRoleUsers().size() > 0) {
            for (Object ruObj : cuuser.getCjJRoleUsers()) {
                RoleUser ru = (RoleUser) ruObj;
                if (ru.getRoleinfo().getRoleid()!= null
                        && ru.getRoleinfo().getRoleid().intValue() == rid.intValue()) {
                    returnVal = true;
                    break;
                }
            }
        }
        return returnVal;
    }



    /**
     * 获取分页参数的所有信息
     */
    public PageResult getPageResultParameter(HttpServletRequest request) {
        // 获取排序组件信息
        PageResult pageresult = new PageResult();
        String pageNo = request.getParameter("pageResult.pageNo");
        String pageSize = request.getParameter("pageResult.pageSize");
        String orderBy = request.getParameter("pageResult.orderBy");
        String sort = request.getParameter("pageResult.sort");
        String objList=request.getParameter("pageResult.objList");
        if (pageNo != null && pageNo.trim().length() > 0) {
            pageresult.setPageNo(Integer.parseInt(pageNo.trim()));
        }
        if (pageSize != null && pageSize.trim().length() > 0) {
            pageresult.setPageSize(Integer.parseInt(pageSize.trim()));
        }
        if (orderBy != null && orderBy.trim().length() > 0) {
            pageresult.setOrderBy(orderBy.trim());
        }
        if (sort != null && sort.trim().length() > 0) {
            pageresult.setSort(sort);
        }
        //将objList转化成List<object>
        if(objList!=null&&objList.length()>0){
            Map<String, Class> m=new HashMap<String, Class>();
            m.put("rules", JQGridDetial.class);
            JSONObject filt=JSONObject.fromObject(objList);
            JQGridConditionEntity pg=(JQGridConditionEntity)JSONObject.toBean(filt, JQGridConditionEntity.class,m);
            pageresult.setJqgridcondition(pg);
        }
        return pageresult;
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        System.out.println(UtilTool.gender_MD5("1123一五六数学课堂（王秋嬛）.mp4"));
    }
    /**
     * 文件上传用
     */
    private static final long serialVersionUID = 572146812454l;
    private static final int BUFFER_SIZE = 16 * 1024;

    /**
     * 用String数组封装多个文件名称
     */
    private String[]uploadFileName;
    public MultipartFile myFile;
    private String fname;
    private List<String>fileNameList=new ArrayList<String>();

    public MultipartFile[] getUpload(HttpServletRequest request){
        MultipartFile[]fileArray=null;
        MultipartHttpServletRequest multipartrequest = (MultipartHttpServletRequest)request;
        Map<String,MultipartFile>fileMaps =multipartrequest.getFileMap();
        if(fileMaps!=null&&fileMaps.size()>0){
            fileArray=new MultipartFile[fileMaps.size()];
            uploadFileName=new String[fileMaps.size()];
            int idx=0;
            for(Map.Entry<String, MultipartFile>entity:fileMaps.entrySet()){
                fileArray[idx]=entity.getValue();
                MultipartFile file = entity.getValue();
                uploadFileName[idx]=file.getOriginalFilename();
                idx+=1;
            }
        }
        return fileArray;
    }

    /**
     * 根据上传文件名  组成生成文件名
     * @param fnameSize
     * @return
     */
    public List<String>getFileArrayName(int fnameSize){
        fileNameList.clear();
        for(int i=0;i<fnameSize;i++){
            fileNameList.add(new Date().getTime()
                    + ""
                    + i
                    + "."
                    + this.getUploadFileName()[i].substring(this
                    .getUploadFileName()[i].lastIndexOf(".")+1));
        }
        return fileNameList;
    }

    /**
     * 文件上传
     * @return
     */
    public boolean fileupLoad(HttpServletRequest request)throws Exception{
        try {
            if(this.getFname()==null){
                this.setFname(this.getFileNameList().get(0));
            }
            String filename = this.getFname();
            File imageFile = new File(request.getRealPath("/")
                    + "uploadfile/"+ filename);
            if(myFile==null)
                myFile=this.getUpload(request)[0];
            copy(myFile.getInputStream(),imageFile);
            myFile=null;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * 多文件上传
     */
    public boolean fileupLoad(HttpServletRequest request,boolean ismulti){
        boolean flag = true;
        int i=0;
        if(this.getUpload(request)!=null&&this.getUpload(request).length>0){
            try {
                for(;i<this.getUpload(request).length;i++){
                    File f=new File(request.getRealPath("/")+"uploadfile/"+this.getFileNameList().get(i));
                    copy(this.getUpload(request)[i].getInputStream(),f);
                }
            }catch (Exception e) {
                System.out.println(e.getMessage());
                flag=false;
            }
            if(!flag){
                for(int j=0;j<i;j++){
                    File f=new File(request.getRealPath("/")+"uploadfile/"+this.getFileNameList().get(i));
                    if(f.exists())
                        f.delete();
                }
            }
        }
        return flag;
    }

    public List<String> getFileNameList() {
        return fileNameList;
    }
    public void setFileNameList(List<String> fileNameList) {
        this.fileNameList = fileNameList;
    }

    public String[] getUploadFileName() {
        return uploadFileName;
    }
    public void setUploadFileName(String[] uploadFileName) {
        this.uploadFileName = uploadFileName;
    }
    public MultipartFile getMyFile() {
        return myFile;
    }
    public void setMyFile(MultipartFile myFile) {
        this.myFile = myFile;
    }
    public String getFname() {
        return fname;
    }
    public void setFname(String fname) {
        this.fname = fname;
    }

    /**
     * 文件复制 以文件流的形式
     *
     * @param src
     * @param dst
     * @throws Exception
     */
    public static void copy(InputStream src, File dst) throws Exception {

        InputStream in = null;
        OutputStream out = null;
        try {
            in = new BufferedInputStream(src, BUFFER_SIZE);
            out = new BufferedOutputStream(new FileOutputStream(dst),
                    BUFFER_SIZE);
            byte[] buffer = new byte[BUFFER_SIZE];
            while (in.read(buffer) > 0) {
                out.write(buffer);
            }
        } finally {
            if (null != in) {
                in.close();
            }
            if (null != out) {
                out.close();
            }
        }

    }

    /**
     * java 文件下载
     */
    public void doloadLocalFile(HttpServletRequest request,HttpServletResponse response,String fname)
            throws Exception{
        JsonEntity je = new JsonEntity();
        String filename=request.getRealPath("/")+"uploadfile/"+fname;
        File f=new File(filename);
        if(!f.exists()){
            je.setMsg("系统中未发现该文件!请联系管理员审查!");
            je.getAlertMsgAndBack();
            response.getWriter().print(je.toJSON());
            return;
        }
        //读到流中
        InputStream in = new BufferedInputStream(new FileInputStream(f));

        //清除头部空白行
        response.reset();
        //设置输出格式
        response.setContentType("bin");
        response.addHeader("Content-Disposition", "attchment; filename=\"" + filename + "\"");
        //取出流中数据
        byte[] b = new byte[100];
        int len;
        try {
            while( (len=in.read(b)) >0){
                response.getOutputStream().write(b, 0, len);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    /**
     * 存入Session 中。
     *
     * @param request
     *            HttpServletRequest对象
     * @param key
     *            存入Session的key
     * @param val
     *            存入Session的value（值）
     */
    protected void setAttributeSession(HttpServletRequest request, String key,
                                       Object val) {
        if (key == null)
            return;
        request.getSession().setAttribute(key, val);
    }


    /**
     * 是否登录 操作
     *
     * @return
     */
    protected UserInfo logined(HttpServletRequest request) {
        UserInfo usr = (UserInfo) request.getSession().getAttribute("CURRENT_USER");
        return usr;
    }

    public static boolean isNum(String str){
        if(str==null||str.trim().length()==0)
            return false;
        return str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
    }



    /**
     * 验证客户端功能权限
     * @param fnid
     * @return
     * @throws Exception
     */
    public Boolean validateFn(HttpServletRequest request,Integer fnid) throws Exception{
        if (request == null)
            return null;
	/*	String fpath = request.getRealPath("/")+"school.txt";
		BufferedReader br = null;
		StringBuilder content = null;
		try {
			br = new BufferedReader(new FileReader(fpath.trim()));
			String lineContent = null;
			while ((lineContent = br.readLine()) != null) {
				if (content == null)
					content = new StringBuilder(lineContent);
				else
					content.append("\n" + lineContent);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} finally {
			if (br != null)
				br.close();
		}
		DESPlus des = new DESPlus();
		String schoolkeyfn = des.decrypt(content.toString());
		String[] fn = schoolkeyfn.split("\\*");
		String[] fnids = fn[2].split(",");
		String schoolkey = this.validateRy4S().replace("*", "");
		Boolean b = false;
		if(schoolkey.length()>1){		
			String schoolkey2 = schoolkeyfn.split("\\*")[0]+"*"+schoolkeyfn.split("\\*")[1];
			if(schoolkey.trim().equals(schoolkey2.replace("*", "").trim())){
				if(fnid!=null){
					FnClickInfo obj = new FnClickInfo();
					obj.setFnid(fnid);
					List<FnClickInfo> list = this.fnclickManager.getList(obj, null);
					if(list.size()<1){					
						for(int k = 0;k<fnids.length;k++){
							if(Integer.parseInt(fnids[k])==fnid){
								b=true;
							}
						}
						if(b){
							obj.setClickcount(1);
							Boolean arg = this.fnclickManager.doSave(obj);
						}
					}else{
						int count = list.get(0).getClickcount();
						if(count==10){
							b = true;
						}else{
							for(int k = 0;k<fnids.length;k++){
								if(Integer.parseInt(fnids[k])==fnid){
									b=true;
								}
							}
							if(b){
								obj.setClickcount(count+1);
								Boolean arg = this.fnclickManager.doUpdate(obj);
							}
						}
					}
					
				}
			}
		}*/
        return true;
    }

    /**
     * 验证加密狗，功能权限
     * @param ：schoolid*fnid，fnid，fnid。。。。。。
     * @return boolean true or false
     * @author 岳春阳
     * @throws Exception
     * @date  2013-06-18 14:02
     */
    public String validateRy4S() throws Exception{

        //验证有没有权限
        short[] handle=new short[1];
        short[] handleAll=new short[1024];
        int[] lp1=new int[1];
        int[] lp2=new int[2];
        short[] p1=new short[1];
        short[] p2=new short[1];
        short[] p3=new short[1];
        short[] p4=new short[1];
        byte[]  buffer=new byte[1024];
        short	retval;

        Ry4S rockey=new Ry4S();

        p1[0] = -(0xffff-0xc44c)-1;
        p2[0] = -(0xffff-0xc8f8)-1;
        p3[0] = 	0x0799;
        p4[0] = -(0xffff-0xc43b)-1;

        retval=rockey.Rockey(rockey.RY_FIND,handle,lp1,lp2,p1,p2,p3,p4,buffer);//查询狗
        if(retval!=rockey.ERR_SUCCESS)
        {
            return "1";
        }
        System.out.println("Find Rockey4Smart:"+lp1[0]);
        retval=rockey.Rockey(rockey.RY_OPEN,handle,lp1,lp2,p1,p2,p3,p4,buffer);//打开狗
        if(retval!=rockey.ERR_SUCCESS)
        {
            return "2";
        }
        int i=1;



        p1[0]=3;
        p2[0]=499;
        for(int m=0;m<1024;m++)buffer[m]=0;
        retval = rockey.Rockey(rockey.RY_READ,handle,lp1,lp2,p1,p2,p3,p4,buffer);//读锁
        if(retval!=rockey.ERR_SUCCESS)
        {
            return "3";
        }
        return new String(buffer);
    }


    /*****************************资源工具**********************************/
    /**
     * 输出图片
     * @param response
     * @param path
     * @throws Exception
     */
    protected void writeImage(HttpServletResponse response,String path) throws Exception {
        response.reset();
        ServletOutputStream output = response.getOutputStream();
        InputStream in = new FileInputStream(path);
        byte tmp[] = new byte[256];
        int i=0;
        while ((i = in.read(tmp)) != -1) {
            output.write(tmp, 0, i);
        }
        in.close();
        output.flush(); //强制清出缓冲区
        output.close();
    }

    /**
     * 强制压缩/放大图片到固定的大小
     * @param  path String 原始图片路径(绝对完整路径)
     * @param w int 新宽度
     * @param h int 新高度
     * @throws IOException
     */
    protected String ImgResize(String path,int w, int h) throws IOException {
        File _file = new File(path);
        _file.setReadOnly();
        File    srcFile  = _file;
        String  fileSuffix = _file.getName().substring(
                (_file.getName().lastIndexOf(".") + 1),
                (_file.getName().length()));
        String fname=UtilTool.utilproperty.getProperty("RESOURCE_SERVER_PATH").toString()+"/tmp/imgCache/"+srcFile.getParentFile().getName()+"/";
        File parF=new File(fname);
        if(!parF.exists())
            parF.mkdirs();
        fname+="001"+(w+h)/100+"."+fileSuffix;
//        String fname=srcFile.getPath().substring(0,
//                (srcFile.getPath().lastIndexOf(".")))
//                + srcFile.getName().substring(0,srcFile.getName().lastIndexOf(".")) + "_"+(w+h)/100+"." + fileSuffix;
        File    destFile    = new File(fname);
        if(!destFile.exists()){
            Image srcImage  = javax.imageio.ImageIO.read(_file);
            //得到图片的原始大小， 以便按比例压缩。
            int  imageWidth = srcImage.getWidth(null);
            int  imageHeight = srcImage.getHeight(null);
            //        System.out.println("width: " + imageWidth);
            //        System.out.println("height: " + imageHeight);
            //得到合适的压缩大小，按比例。
            if ( imageWidth >= imageHeight)
                h = (int)Math.round((imageHeight * w * 1.0 / imageWidth));
            else
                w = (int)Math.round((imageWidth * h * 1.0 / imageHeight));

            //构建图片对象
            BufferedImage _image = new BufferedImage(w, h,
                    BufferedImage.TYPE_INT_RGB);
            //绘制缩小后的图
            _image.getGraphics().drawImage(srcImage, 0, 0, w, h, null);
            //输出到文件流
            FileOutputStream out = new FileOutputStream(destFile);
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
            encoder.encode(_image);
            out.flush();
            out.close();
        }
        return destFile.getPath();
    }

}
