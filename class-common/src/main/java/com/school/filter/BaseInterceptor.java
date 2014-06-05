package com.school.filter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.school.entity.DictionaryInfo;
import com.school.manager.DictionaryManager;
import com.school.manager.inter.IDictionaryManager;
import com.school.util.*;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import com.school.entity.PageRightInfo;
import com.school.entity.UserInfo;
import com.school.manager.PageRightManager;
import com.school.manager.inter.IPageRightManager;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * 过滤器
 */
public class BaseInterceptor implements HandlerInterceptor {
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
     * 加载资源文件的各种类型
     */
    public void writeUtilToolDocType(){
        IDictionaryManager dicManager=(DictionaryManager)SpringBeanUtil.getBean("dictionaryManager");
        List<DictionaryInfo> dicList=dicManager.getDictionaryByType("FILE_SUFFIX_TYPE");
        if(dicList!=null&&dicList.size()>0){
            for(DictionaryInfo dic:dicList){
                if(dic!=null&&dic.getDictionaryvalue()!=null){
                    if(Integer.parseInt(dic.getDictionaryvalue().trim())==3){ //音频
                        UtilTool._MP3_SUFFIX_TYPE_REGULAR="("+dic.getDictionaryname().replaceAll(",","\\|")+")$";
                    }else if(Integer.parseInt(dic.getDictionaryvalue().trim())==5){//动画
                        UtilTool._SWF_SUFFIX_TYPE_REGULAR="("+dic.getDictionaryname().replaceAll(",","\\|")+")$";
                    }else if(Integer.parseInt(dic.getDictionaryvalue().trim())==4){//图片
                        UtilTool._IMG_SUFFIX_TYPE_REGULAR="("+dic.getDictionaryname().replaceAll(",","\\|")+")$";
                    }else if(Integer.parseInt(dic.getDictionaryvalue().trim())==2){//视频
                        UtilTool._VIEW_SUFFIX_TYPE_REGULAR="("+dic.getDictionaryname().replaceAll(",","\\|")+")$";
                    }
                }
            }
        }
        UtilTool.isSynchroFileType=true;
    }

	/**
	 * 访问时拦截
	 */
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object arg2) throws Exception {

		// 字符设定
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
        // 设置TITLE
        if (request.getSession().getAttribute("_TITLE") == null)
            request.getSession().setAttribute("_TITLE",UtilTool.utilproperty.getProperty("CURRENT_PAGE_TITLE"));


//		if (request.getSession().getAttribute("CURRENT_TITLE") == null) {
//			String dbTitle = UtilTool._PAGE_TITLE;
//			request.getSession().setAttribute("CURRENT_TITLE", dbTitle);
//		}
		writeUtilProperties(request);	//写配置文件
        //加载资源文件的各种配置
        if(!UtilTool.isSynchroFileType)
            writeUtilToolDocType();

        // 设置结束
        Object sessionCuName=request.getSession().getAttribute("CURRENT_SCHOOL_NAME");
        if (sessionCuName == null||sessionCuName.toString().trim().length()<1) {
            String currentSchool = UtilTool.utilproperty.getProperty("CURRENT_SCHOOL_NAME");
            request.getSession().setAttribute("CURRENT_SCHOOL_NAME", currentSchool);
        }

		//将分校名称写入Session中
        String currentpath = request.getRequestURL().toString();
		if (request.getQueryString() != null
				&& request.getQueryString().trim().length() > 0)
			currentpath += "?" + request.getQueryString().split("&")[0];
		//System.out.println(currentpath);

		boolean returnType = false;
		String realpath = this.getLocationUrlMethod(request);
		// 未登录时用户访问以下链接不需要拦截
		//System.out.println(realpath);
		// 不需要拦截的链接
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
							break;
						}
					}
				}
			}
		}
		if (isfilterPath)
			return true;

		// 当用户访问内部资源时检查是否登陆，否则跳至登陆页面 开发时如果不想要可以把下面的全部注掉
		UserInfo user = (UserInfo)request.getSession().getAttribute("CURRENT_USER");
		if (user == null) {
			// 当拦截到未登录用户时，查询cookie是否保存用户信息如果保存将自动登录
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
			// 得到当前链接下的column_id
			//ApplicationContext context=WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());
			
		
			String data = getCurrentLocationColumnId(realpath);
			// 如果没有查到，则说明没有限制。
			if (data == null || data.trim().length() < 1
					|| data.trim().equals("ERROR"))
				returnType = true;
			else
				// 权限验证判断.
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

			// 加载导航文件，得到导航
		}
	}

	/**
	 * 得到当前的链接方法指向
	 * 
	 * @param request
	 * @return
	 */
	private String getLocationUrlMethod(HttpServletRequest request) {
		String queryPath = request.getQueryString();
//		String realpath = request.getRequestURI().substring(
//				request.getRequestURI().lastIndexOf("/") + 1);
		String realpath=request.getRequestURI().substring(
				request.getRequestURI().indexOf(request.getContextPath())+request.getContextPath().length()
				);
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
	 * 验证权限
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
	 * 得到当前链接下所属的栏目
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
	 * 写配置文件Util
	 * 
	 * @param request
	 */
	public static void writeUtilProperties(HttpServletRequest request) {
		//检测CURRENT_SCHOOL_ID
        String tmp="";
        //单例写
        if(iswrite)return;
        synchronized(tmp){
            Map<String,Object> writeMap=new HashMap<String,Object>();
            iswrite=true;
            String schoolId = UtilTool.utilproperty.getProperty("CURRENT_SCHOOL_ID");
            String schoolname=UtilTool.utilproperty.getProperty("CURRENT_SCHOOL_NAME");
         //   if(schoolId==null||schoolId.trim().length()<1||schoolname==null||schoolname.trim().length()<1){
                //得到文件中的KEY
                String fpath = request.getRealPath("/") + "school.txt";
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
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    return;
                } finally {
                    if (br != null)
                        try{
                        br.close();
                        }catch(Exception e){e.printStackTrace();}
                }
                //解析key  并写入schoolid,schoolname
                try {
                    DESPlus des = new DESPlus();
                    String schoolkeyfn = des.decrypt(content.toString());
                    //得到分校已经买的栏目
                    String[] schoolArray = schoolkeyfn.split("\\*");
                    if(schoolId==null||schoolId.length()<1){
                    String schoolid=schoolArray[0];

//                        WriteProperties.writeProperties(request.getRealPath("/")
//                                + "/WEB-INF/classes/properties/util.properties",
//                                "CURRENT_SCHOOL_ID", schoolid);
                        writeMap.put("CURRENT_SCHOOL_ID",schoolid);
                        UtilTool.utilproperty.setProperty("CURRENT_SCHOOL_ID",
                                schoolid);
                    }
                    //得到Schoolid后，得到名称,并赋值
                    if(schoolArray.length>3){
                        //对中文进行解码
                        String schoolnameChina=java.net.URLDecoder.decode(schoolArray[3],"UTF-8");
                        if(schoolname==null|| schoolname.trim().length()<1||!schoolnameChina.equals(schoolname)){
//                            WriteProperties.writeProperties(request.getRealPath("/")+ "/WEB-INF/classes/properties/util.properties","CURRENT_SCHOOL_NAME",schoolnameChina);
                            UtilTool.utilproperty.setProperty("CURRENT_SCHOOL_NAME",
                                    schoolnameChina);
                            writeMap.put("CURRENT_SCHOOL_NAME",schoolnameChina);
                        }
                    }
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            String ipAddress=UtilTool.utilproperty.getProperty("IP_ADDRESS");
            String localpath="http://"+ request.getServerName() + ":"+request.getServerPort();
            if(ipAddress!=null&&ipAddress.toString().trim().length()>0)
                localpath="http://"+ipAddress;


//                WriteProperties.writeProperties(request.getRealPath("/")
//                        + "/WEB-INF/classes/properties/util.properties",
//                        "RESOURCE_FILE_UPLOAD_HEAD", "http://"
//                                + request.getServerName() + ":"
//                                + request.getServerPort() + "/fileoperate/");

                writeMap.put("RESOURCE_FILE_UPLOAD_HEAD", localpath+ "/fileoperate/");


                UtilTool.utilproperty.setProperty("RESOURCE_FILE_UPLOAD_HEAD",
                       localpath + "/fileoperate/");


            String pt="http://" + request.getServerName() + ":"
                        + request.getServerPort() + "/fileoperate/questionimg/package/";
                writeMap.put("RESOURCE_QUESTION_IMG_PARENT_PATH",pt);
                UtilTool.utilproperty.setProperty("RESOURCE_QUESTION_IMG_PARENT_PATH",pt);


//                WriteProperties.writeProperties(request.getRealPath("/")
//                        + "/WEB-INF/classes/properties/util.properties",
//                        "RESOURCE_FILE_PATH_HEAD", "http://"
//                                + request.getServerName() + ":"
//                                + request.getServerPort()
//                                + "/fileoperate/uploadfile/");

                writeMap.put("RESOURCE_FILE_PATH_HEAD", localpath+ "/fileoperate/uploadfile/");

                UtilTool.utilproperty.setProperty("RESOURCE_FILE_PATH_HEAD",
                        localpath + "/fileoperate/uploadfile/");



            UtilTool.utilproperty.setProperty("RESOURCE_CLOUD_DOWN_DIRECTORY_HEAD",
                    localpath+ "/fileoperate/clouduploadfile/");

            writeMap.put("RESOURCE_CLOUD_DOWN_DIRECTORY_HEAD",localpath +"/fileoperate/clouduploadfile/");



            String bijiaoServerPath=loadServerUploadFile(request,"/fileoperate/uploadfile");
            if(bijiaoServerPath==null||bijiaoServerPath.toString().trim().length()<1||!new File(bijiaoServerPath).exists()){
                bijiaoServerPath = request.getRealPath("/").replaceAll("\\\\",
                        "/").substring(
                        0,
                        request.getRealPath("/").replaceAll("\\\\", "/").lastIndexOf(
                                "/"+UtilTool.utilproperty.getProperty("PROC_NAME")))
                        + "/fileoperate/uploadfile";
            }
            if (!new File(bijiaoServerPath).exists()) {
                bijiaoServerPath = System.getProperty("catalina.home").replaceAll(
                        "\\\\", "/")
                        + "/webapps/fileoperate/uploadfile";
            }
            writeMap.put("RESOURCE_SERVER_PATH", bijiaoServerPath);
            UtilTool.utilproperty.setProperty("RESOURCE_SERVER_PATH",
                    bijiaoServerPath);


            bijiaoServerPath=loadServerUploadFile(request,"/fileoperate/clouduploadfile");
            if(bijiaoServerPath==null||bijiaoServerPath.toString().trim().length()<1||!new File(bijiaoServerPath).exists()){
                bijiaoServerPath = request.getRealPath("/").replaceAll("\\\\",
                        "/").substring(
                        0,
                        request.getRealPath("/").replaceAll("\\\\", "/").lastIndexOf(
                                "/"+UtilTool.utilproperty.getProperty("PROC_NAME")))
                        + "/fileoperate/clouduploadfile";
            }
            if (!new File(bijiaoServerPath).exists()) {
                bijiaoServerPath = System.getProperty("catalina.home").replaceAll(
                        "\\\\", "/")
                        + "/webapps/fileoperate/clouduploadfile";
            }
            writeMap.put("RESOURCE_CLOUD_SERVER_PATH", bijiaoServerPath);
            UtilTool.utilproperty.setProperty("RESOURCE_CLOUD_SERVER_PATH",
                    bijiaoServerPath);
            //写入XML中
            WriteProperties.writeProperties(request.getRealPath("/") + "/WEB-INF/classes/properties/util.properties",writeMap);
         // System.out.println(UtilTool.utilproperty.getProperty("RESOURCE_SERVER_PATH").toString()+"           "+UtilTool.utilproperty.getProperty("RESOURCE_FILE_UPLOAD_HEAD").toString());
        }
	}

    /**
     * 得到Context中的docBase值
     * @param request
     * @return
     */
    private static String loadServerUploadFile(HttpServletRequest request,String eqStr){
        String serverPath=System.getProperty("catalina.home").replaceAll("\\\\", "/")+"/conf/server.xml";
        String returnVal=null;
        try{
            NodeList ndList=XmlReader.getChildNodeListElement(XmlReader.parse(serverPath).getDocumentElement(), "Context");
            if(ndList!=null&&ndList.getLength()>0){
                for (int i=0;i<ndList.getLength();i++){
                    Element em=(Element)ndList.item(i);
                    if( em.getAttributeNode("path")!=null&&em.getAttributeNode("path").getValue()!=null){
                        if(em.getAttributeNode("path").getValue().equals(eqStr)){
                            returnVal=em.getAttributeNode("docBase").getValue();
                            break;
                        }
                    }
                }
            }
        }catch(Exception e){e.printStackTrace();}
        return returnVal;
    }
}
