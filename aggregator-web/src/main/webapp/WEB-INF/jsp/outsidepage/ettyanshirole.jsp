<%@ page language="java" import="java.util.*" pageEncoding="Utf-8"%>
<%@page import="com.school.util.UtilTool"%>
<%
String outputHtml="";
    String proc_name=request.getHeader("x-etturl");
    if(proc_name==null){
        proc_name=request.getContextPath();
    }else{
        ///group1/1.jsp
        proc_name=proc_name.substring(0,proc_name.substring(1).indexOf("/")+1);
    }
    String ipStr=request.getServerName()+":"+request.getServerPort();
    //UtilTool.utilproperty.getProperty("PROC_NAME");
    String basePath = request.getScheme() + "://"
            + ipStr
            +"/"+proc_name + "/";
    session.setAttribute("IP_PROC_NAME",basePath);

    String jsoncallback=request.getParameter("jsoncallback");
String ettYsRole=UtilTool.utilproperty.getProperty("ETT_YS_ROLE");
int type=0;
if(ettYsRole==null||ettYsRole.trim().length()<1){
	outputHtml="异常错误，项目配置失败!code: no propert in 'sz_schoo > util.properties > ETT_YS_ROLE!'";
	type=1;
}else{
	String[] ettYsRoleArray=ettYsRole.split(",");
	if(ettYsRoleArray==null||ettYsRoleArray.length<1){
		outputHtml="异常错误，项目配置失败!code: sz_schoo > util.properties > ETT_YS_ROLE value is empty!";
		type=1;
	}else{
		for(String ysRole : ettYsRoleArray){
			if(ysRole!=null&&ysRole.length()>0){				
				String[] tmpArray=ysRole.split("\\|");
				if(tmpArray==null||tmpArray.length<1)continue;
				String rolename=tmpArray[0];
				String roletype=tmpArray[1];
				outputHtml+="<input type='radio' name='roletype' value='"+roletype+"' id='sz_rdo_rt_"+roletype+"'>";
				outputHtml+="<label for='sz_rdo_rt_"+roletype+"'>"+rolename+"</label>&nbsp;&nbsp;";
			}
		}
		outputHtml="<form action='"+basePath+"user' method='get'  target='_blank'><input type='hidden' name='m' value='ettlogin' />"+outputHtml;
		outputHtml+="<input type='submit' value='登录'/></form>";
		type=0;
	}
}
outputHtml=jsoncallback+"({\"type\":\""+type+"\",\"data\":\""+outputHtml+"\"})";
response.getWriter().write(outputHtml);
%>