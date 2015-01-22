package com.school.im1_1.control._interface;

import com.school.control.UserController;
import com.school.util.UtilTool;
import org.junit.After;
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

@RunWith(SpringJUnit4ClassRunner.class)

/**
 * get request,response 
 * has request.setParameter value.
 */
/*
target spring config xml(applicationContext.xml) file.
@ContextConfiguration
two properties???
locations???spring config file???
inheritLocations???extends parent test class Spring config file.default value :true
*/
@ContextConfiguration(locations ={"classpath:spring-jdbc-config-tran.xml","classpath:spring-config-junit.xml"})

/** 
* ImUtilTool Tester. 
* 
* @author <Authors name> 
* @since <pre>Ò»ÔÂ 14, 2015</pre> 
* @version 1.0 
*/ 
/*@TransactionConfiguration.
 param 1:transactionManager IS applicationContext.xml path
    OR in bean.xml's transaction bean id;
 param 2:defaultRollback IS after test rollback transaction.default true
*/
@TransactionConfiguration(defaultRollback = true)
@Transactional
public class ImUtilToolTest { 
    @Autowired
    private UserController userController;
    
    // request,response
    MockHttpServletRequest request;
    MockHttpServletResponse response;
    
    @Before
    public void before() throws Exception {
        //init spring
        request =  new MockHttpServletRequest();
        response =  new MockHttpServletResponse();
        
      /*  //init session config
        setProjectValidate(request);
        //request.setRequestURI("/user");
        request.addParameter("m", "doLogin");
        request.addParameter("username","tea");
        request.addParameter("password","111111");
        request.setMethod("POST");

      //  this.userController.doLogin(request,response);
        String redirectedUrl=response.getRedirectedUrl();

        System.out.println(redirectedUrl);
        System.out.println(response.getContentAsString()); 

        response =  new MockHttpServletResponse();*/
    }
// In the execute test  after execute
    @After
    public void after() throws Exception { 
        
    } 

/** 
* 
* Method: ValidateRequestParam(HttpServletRequest request) 
*     test main method
*/ 
    @Test
    public void testValidateRequestParam() throws Exception { 
        //TODO: Test goes here... 
    } 

/** 
* 
* Method: getRequestParam(HttpServletRequest request) 
*     test main method
*/ 
    @Test
    public void testGetRequestParam() throws Exception { 
        //TODO: Test goes here... 
    } 

/** 
* 
* Method: getUserType(String usertype) 
*     test main method
*/ 
    @Test
    public void testGetUserType() throws Exception { 
        //TODO: Test goes here... 
    } 

/** 
* 
* Method: getEttPhoneAndRealNmae(String jidstr, String schoolid, String userid) 
*     test main method
*/ 
    @Test
    public void testGetEttPhoneAndRealNmae() throws Exception { 
        //TODO: Test goes here... 
    } 

/** 
* 
* Method: main(String[] args) 
*     test main method
*/ 
    @Test
    public void testMain() throws Exception { 
        //TODO: Test goes here... 
    } 

/** 
* 
* Method: getTaskOvertime(String tasktime) 
*     test main method
*/ 
    @Test
    public void testGetTaskOvertime() throws Exception { 
        //TODO: Test goes here... 
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
