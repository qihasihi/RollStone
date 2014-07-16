    <%@ page language="java" contentType="text/html; charset=utf-8"
        pageEncoding="utf-8"%>
        <%@ page import="sun.misc.BASE64Decoder" %>
        <%@ page import="java.io.BufferedReader"%>
    <%@ page import="java.io.IOException"%>
    <%@ page import="java.io.InputStream"%>
    <%@ page import="java.io.InputStreamReader"%>
    <%@ page import="java.io.OutputStream"%>
    <%@ page import="java.io.File"%>
    <%@ page import="java.io.FileOutputStream"%>
    <%@ page import="java.util.Date"%>
    <%@ page import="com.school.util.ueditor.Uploader" %>
    <%@ page import="com.school.util.MD5_NEW" %>

    <%
    request.setCharacterEncoding("utf-8");
	response.setCharacterEncoding("utf-8");
	
	String param = request.getParameter("action");
    Uploader up = new Uploader(request);

   String dataid=request.getParameter("dataid");
   String t_path="/";
   if(dataid!=null&&dataid.trim().length()>0)
       t_path+= MD5_NEW.getMD5Result(dataid);
        //仅做示例用，请自行修改
   String path = "/../../uploadfile"+t_path;

    up.setSavePath(path);
    String[] fileType = {".gif" , ".png" , ".jpg" , ".jpeg" , ".bmp"};
    up.setAllowFiles(fileType);
    up.setMaxSize(10000); //单位KB
    
    if(param!=null && param.equals("tmpImg")){
    	up.upload();
    	out.print("<script>parent.ue_callback('" + up.getUrl() + "','" + up.getState() + "')</script>");
    }else{
    	up.uploadBase64("content");
    	response.getWriter().print("{'url':'" + up.getUrl()+"',state:'"+up.getState()+"'}");
    }
    
    %>
