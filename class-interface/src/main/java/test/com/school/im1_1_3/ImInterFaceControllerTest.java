package test.com.school.im1_1_3; 

import com.etiantian.unite.utils.UrlSigUtil;
import com.school.im1_1_3.ImInterFaceController;
import com.school.util.UtilTool;
import net.sf.json.JSONObject;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@RunWith(SpringJUnit4ClassRunner.class)

@ContextConfiguration(locations ={"classpath:spring-jdbc-config-tran.xml", "classpath:spring-config-junit.xml"})

@TransactionConfiguration(defaultRollback = true)
@Transactional
public class ImInterFaceControllerTest { 
//    @Autowired
//    private UserController userController;
    @Autowired
    private ImInterFaceController imInterFaceController;
    
    // request,response
    MockHttpServletRequest request;
    MockHttpServletResponse response;
    
    @Before
    public void before() throws Exception {
        //init spring
        request =  new MockHttpServletRequest();
        response =  new MockHttpServletResponse();
        
        //init session config
        setProjectValidate(request);
        //request.setRequestURI("/user");
//        request.addParameter("m", "doLogin");
//        request.addParameter("username","tea");
//        request.addParameter("password","111111");
//        request.setMethod("POST");
//
//        this.userController.doLogin(request,response);
//        String redirectedUrl=response.getRedirectedUrl();
//
//        System.out.println(redirectedUrl);
//        System.out.println(response.getContentAsString());
//
//        request.removeParameter("username");
//        request.removeParameter("password");

//        response =  new MockHttpServletResponse();
    }
// In the execute test  after execute
    @After
    public void after() throws Exception { 
        
    } 
/**
* 
* Method: teacherJoinClass(HttpServletRequest request, HttpServletResponse response) 
*     test main method
*/ 
    @Test
    public void testTeacherJoinClass() throws Exception { 
        //TODO: Test goes here...
        String time=System.currentTimeMillis()+"";
        HashMap<String,String> mp=new HashMap<String, String>();
        mp.put("jid","2579214");
        mp.put("classCode", "515264");  //加入的班级邀请码
        mp.put("time", time);
        mp.put("isAdmin", "1");         //是否是班主任 1:是班主任
        mp.put("subjectId", "");        //如果不是班主任，则是任课教师，则需要传入学科 例：1,2,3,4,5,7
        String sign=UrlSigUtil.makeSigSimple("teacherJoinClass.do",mp);
        mp.put("sign", sign);
        request.addParameters(mp);
        imInterFaceController.teacherJoinClass(request,response);
        String msg=response.getContentAsString();
        System.out.println(msg);
        boolean isflag=false;
        if(msg!=null&&msg.trim().length()>0){
            JSONObject jo=JSONObject.fromObject(msg);
            if(jo!=null&&jo.get("result")!=null&&jo.get("result").toString().trim().equals("1")){
                isflag=true;
            }
        }
        Assert.assertTrue(isflag);
    } 

/** 
*
* Method: classDetailInfo(HttpServletRequest request, HttpServletResponse response) 
*     test main method
*/ 
    @Test
    public void testClassDetailInfo() throws Exception { 
        //TODO: Test goes here...
        //验证必填参数
        String time=System.currentTimeMillis()+"";
        HashMap<String,String> mp=new HashMap<String, String>();
        mp.put("jid","2579255");
        mp.put("schoolId", "51013");
        mp.put("classId", "67018");
        mp.put("time", time);
        String sign=UrlSigUtil.makeSigSimple("classDetailInfo.do",mp);
        mp.put("sign", sign);
        request.addParameters(mp);
        imInterFaceController.classDetailInfo(request, response);
        String msg=response.getContentAsString();
        System.out.println(msg);
        boolean isflag=false;
        if(msg!=null&&msg.trim().length()>0){
            JSONObject jo=JSONObject.fromObject(msg);
            if(jo!=null&&jo.get("result")!=null&&jo.get("result").toString().trim().equals("1")){
                isflag=true;
            }
        }
        Assert.assertTrue(isflag);
    }

    @Test
    public void testSaveClassTimePoint() throws Exception {
        //TODO: Test goes here...
        //验证必填参数
        String time=System.currentTimeMillis()+"";
        HashMap<String,String> mp=new HashMap<String, String>();
        mp.put("jid","2470767");
        mp.put("schoolId", "51005");
        mp.put("classId", "399973");
        mp.put("time", time);
        String sign=UrlSigUtil.makeSigSimple("saveClassTimePoint.do",mp);
        mp.put("sign", sign);
        request.addParameters(mp);
        imInterFaceController.saveClassTimePoint(request, response);
        String msg=response.getContentAsString();
        System.out.println(msg);
        boolean isflag=false;
        if(msg!=null&&msg.trim().length()>0){
            JSONObject jo=JSONObject.fromObject(msg);
            if(jo!=null&&jo.get("result")!=null&&jo.get("result").toString().trim().equals("1")){
                isflag=true;
            }
        }
        Assert.assertTrue(isflag);
    }


    /**
     * init  proc config 
     * @param request
     */
    private void setProjectValidate(HttpServletRequest request){
        String ipStr=request.getServerName();
        if(request.getServerPort()!=80){
            ipStr+=":"+request.getServerPort();
        }
        String proc_name=request.getHeader("x-etturl");
        if(proc_name==null){
            proc_name=request.getContextPath().replaceAll("/","");
        }else{
            if(proc_name.indexOf("/")!=-1)
                proc_name+="/";
            ///group1/1.jsp
            proc_name=proc_name.substring(0,proc_name.substring(1).indexOf("/")+1).replaceAll("/","");
        }
        //UtilTool.utilproperty.getProperty("PROC_NAME");
        String basePath = request.getScheme() + "://"
                + ipStr
                +"/"+proc_name + "/";
        if(request.getSession().getAttribute("IP_PROC_NAME")==null||!request.getSession().getAttribute("IP_PROC_NAME").toString().equals(basePath))
        request.getSession().setAttribute("IP_PROC_NAME", basePath);

        //public 
        String publicFileSystemIpPort=new StringBuilder(basePath).append("/").toString();
        //proc
        String fileSystemIpPort=publicFileSystemIpPort+UtilTool.utilproperty.getProperty("RESOURCE_FILE_UPLOAD_HEAD")+"/";
        //String fileSystemIpPort=request.getScheme() + "://"+ ipStr+"/"+UtilTool.utilproperty.getProperty("RESOURCE_FILE_UPLOAD_HEAD")+"/";
        if(request.getSession().getAttribute("FILE_SYSTEM_IP_PORT")==null||!request.getSession().getAttribute("FILE_SYSTEM_IP_PORT").toString().equals(fileSystemIpPort))
          request.getSession().setAttribute("FILE_SYSTEM_IP_PORT", fileSystemIpPort);
        // loca file
        String fileSystemUpload=publicFileSystemIpPort+UtilTool.utilproperty.getProperty("RESOURCE_FILE_PATH_HEAD")+"/";
         if(request.getSession().getAttribute("RESOURCE_FILE_PATH_HEAD")==null||!request.getSession().getAttribute("RESOURCE_FILE_PATH_HEAD").toString().equals(fileSystemUpload))
            request.getSession().setAttribute("RESOURCE_FILE_PATH_HEAD", fileSystemUpload);
        // cloud file
        String fileSystemCloudUpload=publicFileSystemIpPort+UtilTool.utilproperty.getProperty("RESOURCE_CLOUD_DOWN_DIRECTORY_HEAD")+"/";
        if(request.getSession().getAttribute("RESOURCE_CLOUD_DOWN_DIRECTORY_HEAD")==null||!request.getSession().getAttribute("RESOURCE_CLOUD_DOWN_DIRECTORY_HEAD").toString().equals(fileSystemCloudUpload))
             request.getSession().setAttribute("RESOURCE_CLOUD_DOWN_DIRECTORY_HEAD", fileSystemCloudUpload);
    }
} 
