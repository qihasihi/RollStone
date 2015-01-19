package com.school.im1_1_3;
import com.etiantian.unite.utils.UrlSigUtil;
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
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)

@ContextConfiguration(locations ={"classpath:spring-jdbc-config-tran.xml", "classpath:spring-config-junit.xml"})

@TransactionConfiguration(defaultRollback = true)
@WebAppConfiguration
@Transactional
public class ImInterFaceControllerTest {
    //    @Autowired
//    private UserController userController;
// request,response
    MockHttpServletRequest request;
    MockHttpServletResponse response;
    @Autowired
    private ImInterFaceController imInterFaceController;

    @Autowired
    protected WebApplicationContext wac;
    private MockMvc mockMvc;

    @Before
    public void before() throws Exception {
        //init spring
        this.mockMvc = webAppContextSetup(this.wac).build();
        //init session config
        request=mockMvc.perform(MockMvcRequestBuilders.post("/user?m=dologin").param("username","tea").param("password","111111"))
                .andExpect(status().isOk()).andReturn().getRequest();


        request.removeParameter("username");
        request.removeParameter("password");
        response=new MockHttpServletResponse();
    }
    @After
    public void after() throws Exception {
    }
    /**
     *
     * Method: teacherJoinClass(HttpServletRequest request, HttpServletResponse response)
     *     test main method
     */
  /*  @Test
    public void testTeacherJoinClass() throws Exception {
        String time=System.currentTimeMillis()+"";
        HashMap<String,String> mp=new HashMap<String, String>();
        mp.put("jid","2470528");
        mp.put("classCode", "515264");  //加入的班级邀请码
        mp.put("time", time);
        mp.put("isAdmin", "1");         //是否是班主任 1:是班主任
        mp.put("subjectId", "");        //如果不是班主任，则是任课教师，则需要传入学科 例：1,2,3,4,5,7
        String sign= UrlSigUtil.makeSigSimple("teacherJoinClass.do", mp);
        mp.put("sign", sign);
        MvcResult mvcResult=mockMvc.perform(MockMvcRequestBuilders.post("/im1.1.3?m=teacherJoinClass.do")
                .param("jid",mp.get("jid"))
                .param("classCode",mp.get("classCode"))
                .param("time",mp.get("time"))
                .param("isAdmin",mp.get("isAdmin"))
                .param("subjectId",mp.get("subjectId"))
                .param("sign",mp.get("sign")))
                .andExpect(status().isOk()).andReturn();
        String msg=mvcResult.getResponse().getContentAsString();
        System.out.println(msg);
        boolean isflag=false;
        if(msg!=null&&msg.trim().length()>0){
            JSONObject jo=JSONObject.fromObject(msg);
            if(jo!=null&&jo.get("result")!=null&&jo.get("result").toString().trim().equals("1")){
                isflag=true;
            }
        }
//        Assert.assertTrue(isflag);
    }
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

}
