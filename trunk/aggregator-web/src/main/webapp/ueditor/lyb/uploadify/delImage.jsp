<%@ page import="java.io.*" %>
<%@ page import="java.awt.*" %>
<%@ page import="java.awt.image.BufferedImage" %>
<%@ page import="com.sun.image.codec.jpeg.JPEGImageEncoder" %>
<%@ page import="com.sun.image.codec.jpeg.JPEGCodec" %>
<%--
  Created by IntelliJ IDEA.
  User: zhengzhou
  Date: 14-5-7
  Time: 下午7:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String url=request.getParameter("url");
    if(url==null||url.trim().length()<1){
        return;
    }
    String path = request.getSession().getServletContext().getRealPath("/")+url.substring(0,url.lastIndexOf(".")).replaceAll("\\.","")+url.substring(url.lastIndexOf("."));
    if(new File(path).exists()){
        File _file = new File(path);
        File    srcFile  = _file;
        if(srcFile.exists()){
            //将图片缩放
            String  fileSuffix = _file.getName().substring(
                    (_file.getName().lastIndexOf(".") + 1),
                    (_file.getName().length()));
            File    destFile    = new File(srcFile.getPath().substring(0,
                    (srcFile.getPath().lastIndexOf(".")))
                    + srcFile.getName().substring(0,srcFile.getName().lastIndexOf(".")) + "_sm." + fileSuffix);
            if(destFile.exists()){ //删除缩略图
                System.gc();
                destFile.delete();
            }
            System.gc();
            _file.delete();//删除原始图片
        }
    }
    //文件上传完毕后，将文件转成
    response.getWriter().print("{\"title\":\"del OK!\",\"state\":\"SUCCESS\"}");
%>
