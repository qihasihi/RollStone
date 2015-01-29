
package com.school.im1_1.control._interface;

import com.etiantian.unite.utils.UrlSigUtil;
import com.school.control.UserController;
import com.school.im1_1.control._interface.ImInterfaceController;
import com.school.util.UtilTool;
import net.sf.json.JSONObject;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)


/**
 * get request,response 
 * has request.setParameter value.
 */

@ContextConfiguration(locations ={"classpath:spring-jdbc-config-tran.xml","classpath:spring-config-junit.xml"})

@TransactionConfiguration(defaultRollback = true)
@Transactional
public class ImInterfaceControllerTest { 
    @Autowired
    private UserController userController;
    @Autowired
    private ImInterfaceController imInterfaceController;
    
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
//        response =  new MockHttpServletResponse();
    }
// In the execute test  after execute
    @After
    public void after() throws Exception { 
        
    }

    @Test
    public void testGetTaskPaperQuestion() throws Exception {
        String time=System.currentTimeMillis()+"";
        HashMap<String,String>map=new HashMap<String, String>();
        map.put("taskId","-2008379040121");
        map.put("classId","58872");
        map.put("groupId","-6482062037777");
        map.put("jid","2470492");
        map.put("schoolId","50002");
        map.put("userType","2");
        map.put("time",time);
        String sign=UrlSigUtil.makeSigSimple("getTaskPaperQuestion",map);
        map.put("sign",sign);
        request.addParameters(map);
        imInterfaceController.getTaskPaperQuestion(request,response);
        String returnMsg=response.getContentAsString();
        boolean result=false;
        if(returnMsg!=null&&returnMsg.length()>0){
            JSONObject jsonObject=JSONObject.fromObject(returnMsg);
            if(jsonObject!=null&&jsonObject.containsKey("result")&&jsonObject.get("result").toString().equals("1")){
                result=true;
            }
        }
        Assert.assertTrue(result);
    }



 /*   @Test
        public void testGetLiveAddress() throws Exception {
        String time=System.currentTimeMillis()+"";
        HashMap<String,String>map=new HashMap<String, String>();
        map.put("taskId","-3188141132755");
        map.put("courseId","-9978058759128");
        map.put("schoolId","50040");
        map.put("jid","1963076");
        map.put("time",time);
        String sign=UrlSigUtil.makeSigSimple("getLiveAddress",map);
        map.put("sign",sign);
        request.addParameters(map);
        imInterfaceController.getLiveAddress(request,response);
        String returnMsg=response.getContentAsString();
        boolean result=false;
        if(returnMsg!=null&&returnMsg.length()>0){
            JSONObject jsonObject=JSONObject.fromObject(returnMsg);
            if(jsonObject!=null&&jsonObject.containsKey("result")&&jsonObject.get("result").toString().equals("1")){
                result=true;
            }
        }
        Assert.assertTrue(result);
    }*/
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

