package com.school.filter;
import com.school.entity.DictionaryInfo;
import com.school.entity.PageRightInfo;
import com.school.entity.UserInfo;
import com.school.manager.DictionaryManager;
import com.school.manager.PageRightManager;
import com.school.manager.inter.IDictionaryManager;
import com.school.manager.inter.IPageRightManager;
import com.school.util.JsonEntity;
import com.school.util.SpringBeanUtil;
import com.school.util.UtilTool;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ������
 */
public class BaseInterceptor implements HandlerInterceptor {
    //��¼Log4J
    private Logger logger = Logger.getLogger(this.getClass());
    private static boolean iswrite=false;
    public void afterCompletion(HttpServletRequest arg0,
                                HttpServletResponse arg1, Object arg2, Exception arg3)
            throws Exception {
        // TODO Auto-generated method stub

    }

    public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
                           Object arg2, ModelAndView arg3) throws Exception {
        // TODO Auto-generated method stub

    }

    /**
     * ������Դ�ļ��ĸ�������
     */
    public void writeUtilToolDocType(){
        IDictionaryManager dicManager=(DictionaryManager)SpringBeanUtil.getBean("dictionaryManager");
        List<DictionaryInfo> dicList=dicManager.getDictionaryByType("FILE_SUFFIX_TYPE");
        if(dicList!=null&&dicList.size()>0){
            for(DictionaryInfo dic:dicList){
                if(dic!=null&&dic.getDictionaryvalue()!=null){
                    if(Integer.parseInt(dic.getDictionaryvalue().trim())==3){ //��Ƶ
                        UtilTool._MP3_SUFFIX_TYPE_REGULAR="("+dic.getDictionaryname().replaceAll(",","\\|")+")$";
                    }else if(Integer.parseInt(dic.getDictionaryvalue().trim())==5){//����
                        UtilTool._SWF_SUFFIX_TYPE_REGULAR="("+dic.getDictionaryname().replaceAll(",","\\|")+")$";
                    }else if(Integer.parseInt(dic.getDictionaryvalue().trim())==4){//ͼƬ
                        UtilTool._IMG_SUFFIX_TYPE_REGULAR="("+dic.getDictionaryname().replaceAll(",","\\|")+")$";
                    }else if(Integer.parseInt(dic.getDictionaryvalue().trim())==2){//��Ƶ
                        UtilTool._VIEW_SUFFIX_TYPE_REGULAR="("+dic.getDictionaryname().replaceAll(",","\\|")+")$";
                    }
                }
            }
        }
        UtilTool.isSynchroFileType=true;
    }

    /**
     * ��֤��׺��
     * @param str
     * @return
     */
    private static boolean validateLastName(String str){
        //ppt,rar,zip,xls,xlsx,doc,docx,ppt,pptx,wps,js,jpg,png,gif,bmp,css,htm
        Pattern pattern = Pattern.compile("(jpg|png|gif|bmg|ppt|rar|zip|xls|xlsx|doc|docx|ppt|pptx|wps|js|bmp|css|htm)$");
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }
    public static void main(String[] args){
        System.out.println(validateLastName("����ԭ���Ի���ԭ����dsafasdfasdfjpg"));
    }

    /**
     * ������Ŀ��̬����
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

        //���õ��ļ���������Ŀ����
        String publicFileSystemIpPort=new StringBuilder(basePath).append("/").toString();
        //��Ŀ
        String fileSystemIpPort=publicFileSystemIpPort+UtilTool.utilproperty.getProperty("RESOURCE_FILE_UPLOAD_HEAD")+"/";
        //String fileSystemIpPort=request.getScheme() + "://"+ ipStr+"/"+UtilTool.utilproperty.getProperty("RESOURCE_FILE_UPLOAD_HEAD")+"/";
        if(request.getSession().getAttribute("FILE_SYSTEM_IP_PORT")==null||!request.getSession().getAttribute("FILE_SYSTEM_IP_PORT").toString().equals(fileSystemIpPort))
          request.getSession().setAttribute("FILE_SYSTEM_IP_PORT", fileSystemIpPort);
        // ���ص��ļ�
        String fileSystemUpload=publicFileSystemIpPort+UtilTool.utilproperty.getProperty("RESOURCE_FILE_PATH_HEAD")+"/";
         if(request.getSession().getAttribute("RESOURCE_FILE_PATH_HEAD")==null||!request.getSession().getAttribute("RESOURCE_FILE_PATH_HEAD").toString().equals(fileSystemUpload))
            request.getSession().setAttribute("RESOURCE_FILE_PATH_HEAD", fileSystemUpload);
        // �ƶ˵��ļ�
        String fileSystemCloudUpload=publicFileSystemIpPort+UtilTool.utilproperty.getProperty("RESOURCE_CLOUD_DOWN_DIRECTORY_HEAD")+"/";
        if(request.getSession().getAttribute("RESOURCE_CLOUD_DOWN_DIRECTORY_HEAD")==null||!request.getSession().getAttribute("RESOURCE_CLOUD_DOWN_DIRECTORY_HEAD").toString().equals(fileSystemCloudUpload))
             request.getSession().setAttribute("RESOURCE_CLOUD_DOWN_DIRECTORY_HEAD", fileSystemCloudUpload);
    }

    /**
     * ����ʱ����
     */
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object arg2) throws Exception {

        // �ַ��趨
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        // ����TITLE
        if (request.getSession().getAttribute("_TITLE") == null)
            request.getSession().setAttribute("_TITLE",UtilTool.utilproperty.getProperty("CURRENT_PAGE_TITLE"));

        //�����ļ�������������������Ŀ���ʵ�·������
        setProjectValidate(request);
        writeUtilProperties(request);	//д�����ļ�
        //������Դ�ļ��ĸ�������
        if(!UtilTool.isSynchroFileType)
            writeUtilToolDocType();

        // ���ý���
//        Object sessionCuName=request.getSession().getAttribute("CURRENT_SCHOOL_NAME");
//        if (sessionCuName == null||sessionCuName.toString().trim().length()<1) {
//            String currentSchool = UtilTool.utilproperty.getProperty("CURRENT_SCHOOL_NAME");
//            request.getSession().setAttribute("CURRENT_SCHOOL_NAME", currentSchool);
//        }

        //����У����д��Session��
        String currentpath = request.getRequestURL().toString();
        if (request.getQueryString() != null
                && request.getQueryString().trim().length() > 0)
            currentpath += "?" + request.getQueryString().split("&")[0];
        //System.out.println(currentpath);

        boolean returnType = false;
        //������û��ϴ�������Ŀ¼����ֱ��pass
        if(request.getRequestURI().indexOf("/uploadfile/")!=-1||request.getRequestURI().indexOf("/uploadfile/")!=-1){
            return true;
        }
        String realpath = this.getLocationUrlMethod(request);
        // δ��¼ʱ�û������������Ӳ���Ҫ����
        //System.out.println(realpath);
        // ����Ҫ���ص�����
        String passpath = UtilTool.utilproperty.getProperty("INTERCEPTOR_PASS_PATH");
        boolean isfilterPath = false;
        if (passpath != null) {
            Object[] passPathArray = passpath.trim().split(",");
            if (passPathArray != null && passPathArray.length > 0) {
                for (Object pathObj : passPathArray) {
                    if (pathObj != null
                            && pathObj.toString().trim().length() > 0) {
                        String pathObjStr = pathObj.toString().toLowerCase();
                        // System.out.println(pathObjStr + "     " + passpath);
                        // String
                        // suffix=pathObjStr.indexOf(".")!=-1?pathObjStr.substring(pathObjStr.indexOf(".")+1):pathObjStr;
                        if (realpath.toLowerCase().indexOf(pathObjStr) != -1) {
                            isfilterPath = true;
                            String lastname=pathObjStr;
                            if(lastname.indexOf(".")!=-1){
                                lastname=lastname.substring(lastname.lastIndexOf(".")+1);
                            }
                            if(!validateLastName(lastname)){
                                StringBuilder paramStr=new StringBuilder();
                                Map<String,Object> objMap=request.getParameterMap();
                                if(objMap!=null&&objMap.size()>0){
                                    Iterator<String> iteKey=objMap.keySet().iterator();
                                    while(iteKey.hasNext()){
                                        String key=iteKey.next();
                                        if(key.trim().equals("m"))
                                            continue;
                                        if(paramStr.toString().trim().length()>0)
                                            paramStr.append("&");
                                        paramStr.append(key).append("=").append(request.getParameter(key));
                                    }
                                    String method=request.getParameter("m");
                                    if(method!=null&&method.trim().length()>0){
                                        String pmstr=paramStr.toString();
                                        paramStr=new StringBuilder("m="+method);
                                        if(pmstr.trim().length()>0){
                                            paramStr.append("&").append(pmstr);
                                        }
                                    }
                                }
                                logger.warn("----------------\n----No Login active:  reallocalpth:"+request.getRequestURL().toString()+"?"+paramStr.toString()+"");
                                logger.warn("----------------\n----No Login active:  proclocalpath:"+request.getSession().getAttribute("FILE_SYSTEM_IP_PORT")+"?"+paramStr.toString()+"");
                            }
                            break;
                        }
                    }
                }
            }
        }
        if (isfilterPath)
            return true;

        // ���û������ڲ���Դʱ����Ƿ��½������������½ҳ�� ����ʱ�������Ҫ���԰������ȫ��ע��
        UserInfo user = (UserInfo)request.getSession().getAttribute("CURRENT_USER");
        if (user == null) {
            // �����ص�δ��¼�û�ʱ����ѯcookie�Ƿ񱣴��û���Ϣ������潫�Զ���¼
			/*
			 * String username = ""; int i = 0; Cookie[] cookie =
			 * request.getCookies(); if (cookie != null) { for (; i <
			 * cookie.length; i++) { if
			 * (cookie[i].getValue().split("|")[0].equals
			 * ("szs_username")&&cookie[i].getValue().split("|").length>1) {
			 * username = cookie[i].getName(); break; } } } if
			 * (!username.equals(
			 * "")&&cookie[i].getValue().split("|")[1].equals("auto")) {
			 * request.setAttribute("username", username);
			 * request.setAttribute("path", currentpath);
			 * request.getRequestDispatcher
			 * ("/user?m=autologin").forward(request, response); return false; }
			 * else {
			 */
            if(UtilTool._IS_SIMPLE_RESOURCE!=2){

                request.setAttribute("state", "no_login");
                request.getRequestDispatcher("/login.jsp").forward(request,response);
            }else{
                response.sendRedirect("user?m=simpleResLogin");
            }
            return false;
			/*
			 * } return true;
			 */
        } else {
            //System.out.println(6);
            UserInfo u = (UserInfo) request.getSession().getAttribute(
                    "CURRENT_USER");
            JsonEntity je = new JsonEntity();
            if (request.getSession().getAttribute("currentUserRoleList") == null)
                request.getSession().setAttribute("currentUserRoleList",
                        u.getCjJRoleUsers());
            // �õ���ǰ�����µ�column_id
            //ApplicationContext context=WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());


            String data = getCurrentLocationColumnId(realpath);
            // ���û�в鵽����˵��û�����ơ�
            if (data == null || data.trim().length() < 1
                    || data.trim().equals("ERROR"))
                returnType = true;
            else
                // Ȩ����֤�ж�.
                //returnType =  validatePageRight(data.trim(), user.getRef(),realpath);
                returnType=true;

            if (!returnType) {
                je.setMsg(UtilTool.msgproperty.getProperty("NO_PAGE_RIGHT"));
                response.getWriter().println(je.getAlertMsg());
                response
                        .getWriter()
                        .print(
                                "<script type='text/javascript'>history.go(-1);</script>");
            }

            return returnType;
            //return true;

            // ���ص����ļ����õ�����
        }
    }

    /**
     * �õ���ǰ�����ӷ���ָ��
     *
     * @param request
     * @return
     */
    private String getLocationUrlMethod(HttpServletRequest request) {
        String queryPath = request.getQueryString();
//		String realpath = request.getRequestURI().substring(
//				request.getRequestURI().lastIndexOf("/") + 1);
        String pcname=request.getHeader("x-etturl");
        if(pcname==null){
            pcname=request.getContextPath();
        }else{
            ///group1/1.jsp
            pcname=pcname.substring(0,pcname.substring(1).indexOf("/"));
        }
        String realpath="";
        if(request.getRequestURI().indexOf(pcname)!=-1){
            realpath=request.getRequestURI().substring(
                    request.getRequestURI().indexOf(pcname)+pcname.length()
            );
        }else{
            String realProcName="/sz_school";
            realpath=request.getRequestURI().substring(
                    request.getRequestURI().indexOf(realProcName)+realProcName.length()
            );
        }
        if(realpath.substring(0,1).equals("/"))
            realpath=realpath.substring(1);
        if(realpath.lastIndexOf("/")!=-1&&realpath.substring(realpath.lastIndexOf("/")).equals("/"))
            realpath=realpath.substring(0,realpath.lastIndexOf("/"));
        if (queryPath != null) {
            boolean isflag = false;
            String queryParamMethod = UtilTool.utilproperty
                    .getProperty("QUERY_PARAM_METHOD");
            Object[] queryParamMethodArray = queryParamMethod.trim().split(",");
            if (queryParamMethod != null
                    && queryParamMethod.trim().length() > 0) {
                for (Object tmp : queryParamMethodArray) {
                    if (tmp != null && tmp.toString().trim().length() > 0) {
                        String obj = request.getParameter(tmp.toString());
                        if (obj != null && obj.trim().length() > 0) {
                            realpath = realpath + "?" + tmp + "=" + obj;
                            isflag = true;
                            break;
                        }
                    }
                }
            }
            if (isflag == false && queryPath != null && queryPath.length() > 0) {
                if (queryPath.indexOf("&") != -1) {
                    queryPath = queryPath.substring(0, queryPath.indexOf("&"));
                    realpath += "?" + queryPath;
                }
            }
        }
        return realpath;
    }

    /**
     * ��֤Ȩ��
     *
     * @param columnid
     * @param userref
     * @return
     */
    private boolean validatePageRight(String columnid, String userref,
                                      String realpath) {
        IPageRightManager pageRightManager = (PageRightManager) SpringBeanUtil
                .getBean("pageRightManager");
        List<PageRightInfo> prList = pageRightManager.getUserColumnList(
                columnid, userref);
        boolean returnType = true;
        if (prList != null && prList.size() > 0) {
            for (PageRightInfo prtmp : prList) {
                if (prtmp != null) {
                    if (realpath.indexOf(prtmp.getPagevalue().trim()) != -1) {
                        returnType = false;
                        break;
                    }
                }
            }
        }
        return returnType;
    }

    /**
     * �õ���ǰ��������������Ŀ
     *
     * @param path
     * @return
     */
    private String getCurrentLocationColumnId(String path) {
        IPageRightManager crprManager = (PageRightManager) SpringBeanUtil.getBean("pageRightManager");
        PageRightInfo crprEntity = new PageRightInfo();
        crprEntity.setPagevalue(path);
        List<PageRightInfo> crprList = crprManager.getList(
                crprEntity, null);
        if (crprList != null && crprList.size() > 0)
            return crprList.get(0).getColumnid();
        return null;
    }

    /**
     * д�����ļ�Util
     *
     * @param request
     */
    public static void writeUtilProperties(HttpServletRequest request) {
        //���CURRENT_SCHOOL_ID
        String tmp="";
        //����д
        if(iswrite)return;
        synchronized(tmp){
//            Map<String,Object> writeMap=new HashMap<String,Object>();
//            iswrite=true;
//            String schoolId = UtilTool.utilproperty.getProperty("CURRENT_SCHOOL_ID");
//            String schoolname=UtilTool.utilproperty.getProperty("CURRENT_SCHOOL_NAME");
//            //   if(schoolId==null||schoolId.trim().length()<1||schoolname==null||schoolname.trim().length()<1){
//            //�õ��ļ��е�KEY
//            String fpath = request.getRealPath("/") + "school.txt";
//            BufferedReader br = null;
//            StringBuilder content = null;
//            try {
//                br = new BufferedReader(new FileReader(fpath.trim()));
//                String lineContent = null;
//                while ((lineContent = br.readLine()) != null) {
//                    if (content == null)
//                        content = new StringBuilder(lineContent);
//                    else
//                        content.append("\n" + lineContent);
//                }
//            } catch (Exception e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//                return;
//            } finally {
//                if (br != null)
//                    try{
//                        br.close();
//                    }catch(Exception e){e.printStackTrace();}
//            }
//            //����key  ��д��schoolid,schoolname
//            try {
//                DESPlus des = new DESPlus();
//                String schoolkeyfn = des.decrypt(content.toString());
//                //�õ���У�Ѿ������Ŀ
//                String[] schoolArray = schoolkeyfn.split("\\*");
//                if(schoolId==null||schoolId.length()<1){
//                    String schoolid=schoolArray[0];
//
////                        WriteProperties.writeProperties(request.getRealPath("/")
////                                + "/WEB-INF/classes/properties/util.properties",
////                                "CURRENT_SCHOOL_ID", schoolid);
//                    writeMap.put("CURRENT_SCHOOL_ID",schoolid);
//                    UtilTool.utilproperty.setProperty("CURRENT_SCHOOL_ID",
//                            schoolid);
//                }
//                //�õ�Schoolid�󣬵õ�����,����ֵ
//                if(schoolArray.length>3){
//                    //�����Ľ��н���
//                    String schoolnameChina=java.net.URLDecoder.decode(schoolArray[3],"UTF-8");
//                    if(schoolname==null|| schoolname.trim().length()<1||!schoolnameChina.equals(schoolname)){
////                            WriteProperties.writeProperties(request.getRealPath("/")+ "/WEB-INF/classes/properties/util.properties","CURRENT_SCHOOL_NAME",schoolnameChina);
//                        UtilTool.utilproperty.setProperty("CURRENT_SCHOOL_NAME",
//                                schoolnameChina);
//                        writeMap.put("CURRENT_SCHOOL_NAME",schoolnameChina);
//                    }
//                }
//            } catch (Exception e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }

//            if(ipAddress!=null&&ipAddress.toString().trim().length()>0)
//                localpath="http://"+ipAddress;


//                WriteProperties.writeProperties(request.getRealPath("/")
//                        + "/WEB-INF/classes/properties/util.properties",
//                        "RESOURCE_FILE_UPLOAD_HEAD", "http://"
//                                + request.getServerName() + ":"
//                                + request.getServerPort() + "/fileoperate/");

         //   writeMap.put("RESOURCE_FILE_UPLOAD_HEAD", localpath+ "/"+UtilTool.utilproperty.getProperty("FILEOPERATE_PROJECT")+"/");


//            UtilTool.utilproperty.setProperty("RESOURCE_FILE_UPLOAD_HEAD",
//                    localpath+ "/"+UtilTool.utilproperty.getProperty("FILEOPERATE_PROJECT")+"/");
//
//
//            String pt=localpath+ "/"+UtilTool.utilproperty.getProperty("FILEOPERATE_PROJECT")+"/"+"/questionimg/package/";
//            writeMap.put("RESOURCE_QUESTION_IMG_PARENT_PATH",pt);
//            UtilTool.utilproperty.setProperty("RESOURCE_QUESTION_IMG_PARENT_PATH",pt);


//                WriteProperties.writeProperties(request.getRealPath("/")
//                        + "/WEB-INF/classes/properties/util.properties",
//                        "RESOURCE_FILE_PATH_HEAD", "http://"
//                                + request.getServerName() + ":"
//                                + request.getServerPort()
//                                + "/fileoperate/uploadfile/");

//            writeMap.put("RESOURCE_FILE_PATH_HEAD", localpath+ "/"+UtilTool.utilproperty.getProperty("FILEOPERATE_PROJECT")+"/uploadfile/");
//
//            UtilTool.utilproperty.setProperty("RESOURCE_FILE_PATH_HEAD",
//                    localpath + "/"+UtilTool.utilproperty.getProperty("FILEOPERATE_PROJECT")+"/uploadfile/");
//
//
//
//            UtilTool.utilproperty.setProperty("RESOURCE_CLOUD_DOWN_DIRECTORY_HEAD",
//                    localpath+ "/"+UtilTool.utilproperty.getProperty("FILEOPERATE_PROJECT")+"/clouduploadfile/");
//
//            writeMap.put("RESOURCE_CLOUD_DOWN_DIRECTORY_HEAD",localpath +"/"+UtilTool.utilproperty.getProperty("FILEOPERATE_PROJECT")+"/clouduploadfile/");



//            String bijiaoServerPath=loadServerUploadFile(request,"/fileoperate/uploadfile");
//            //  System.out.println("localrealpath:"+bijiaoServerPath);
//            if(bijiaoServerPath==null||bijiaoServerPath.toString().trim().length()<1||!new File(bijiaoServerPath).exists()){
//                bijiaoServerPath = request.getRealPath("/").replaceAll("\\\\",
//                        "/").substring(
//                        0,
//                        request.getRealPath("/").replaceAll("\\\\", "/").lastIndexOf(
//                                "/"+UtilTool.utilproperty.getProperty("PROC_NAME")))
//                        + "/fileoperate/uploadfile";
//            }
//            if (!new File(bijiaoServerPath).exists()) {
//                bijiaoServerPath = System.getProperty("catalina.home").replaceAll(
//                        "\\\\", "/")
//                        + "/webapps/fileoperate/uploadfile";
//            }
//            writeMap.put("RESOURCE_SERVER_PATH", bijiaoServerPath);
//            UtilTool.utilproperty.setProperty("RESOURCE_SERVER_PATH",
//                    bijiaoServerPath);


//            bijiaoServerPath=loadServerUploadFile(request,"/fileoperate/clouduploadfile");
//            // System.out.println("cloudrealpath:"+bijiaoServerPath);
//            if(bijiaoServerPath==null||bijiaoServerPath.toString().trim().length()<1||!new File(bijiaoServerPath).exists()){
//                bijiaoServerPath = request.getRealPath("/").replaceAll("\\\\",
//                        "/").substring(
//                        0,
//                        request.getRealPath("/").replaceAll("\\\\", "/").lastIndexOf(
//                                "/"+UtilTool.utilproperty.getProperty("PROC_NAME")))
//                        + "/fileoperate/clouduploadfile";
//            }
//            if (!new File(bijiaoServerPath).exists()) {
//                bijiaoServerPath = System.getProperty("catalina.home").replaceAll(
//                        "\\\\", "/")
//                        + "/webapps/fileoperate/clouduploadfile";
//            }
//            writeMap.put("RESOURCE_CLOUD_SERVER_PATH", bijiaoServerPath);
//            UtilTool.utilproperty.setProperty("RESOURCE_CLOUD_SERVER_PATH",
//                    bijiaoServerPath);
            //д��XML��
//            WriteProperties.writeProperties(request.getRealPath("/") + "/WEB-INF/classes/properties/util.properties",writeMap);
            // System.out.println(UtilTool.utilproperty.getProperty("RESOURCE_SERVER_PATH").toString()+"           "+UtilTool.utilproperty.getProperty("RESOURCE_FILE_UPLOAD_HEAD").toString());
        }
    }

    /**
     * �õ�Context�е�docBaseֵ
     * @param request
     * @return
     */
//    private static String loadServerUploadFile(HttpServletRequest request,String eqStr){
//        String serverPath=System.getProperty("catalina.home").replaceAll("\\\\", "/")+"/conf/server.xml";
//        String returnVal=null;
//        try{
//            NodeList ndList=XmlReader.getChildNodeListElement(XmlReader.parse(serverPath).getDocumentElement(), "Context");
//            if(ndList!=null&&ndList.getLength()>0){
//                for (int i=0;i<ndList.getLength();i++){
//                    Element em=(Element)ndList.item(i);
//                    if( em.getAttributeNode("path")!=null&&em.getAttributeNode("path").getValue()!=null){
//                        if(em.getAttributeNode("path").getValue().equals(eqStr)){
//                            returnVal=em.getAttributeNode("docBase").getValue();
//                            break;
//                        }
//                    }
//                }
//            }
//        }catch(Exception e){e.printStackTrace();}
//        return returnVal;
//    }
}
