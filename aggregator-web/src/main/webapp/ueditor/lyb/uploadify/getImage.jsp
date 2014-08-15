<%@ page import="java.io.*" %>
<%@ page import="java.awt.*" %>
<%@ page import="java.awt.image.BufferedImage" %>
<%@ page import="com.sun.image.codec.jpeg.JPEGImageEncoder" %>
<%@ page import="com.sun.image.codec.jpeg.JPEGCodec" %>
<%@ page import="com.school.util.UtilTool" %>
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
    if(!new File(path).exists()){
        return; //提示文件不存在
    }
    //将图片缩放
    String writepath=ImgResize(path,250,250);
    //将缩略图输出
    UtilTool.writeImage(response, writepath);
%>

<%!
    /**
     * 强制压缩/放大图片到固定的大小
     * @param  path String 原始图片路径(绝对完整路径)
     * @param w int 新宽度
     * @param h int 新高度
     * @throws IOException
     */
    public String ImgResize(String path,int w, int h) throws IOException {
        File _file = new File(path);
        _file.setReadOnly();
        File    srcFile  = _file;
        String  fileSuffix = _file.getName().substring(
                (_file.getName().lastIndexOf(".") + 1),
                (_file.getName().length()));
        File    destFile    = new File(srcFile.getPath().substring(0,
                (srcFile.getPath().lastIndexOf(".")))
                + srcFile.getName().substring(0,srcFile.getName().lastIndexOf(".")) + "_sm." + fileSuffix);
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
%>