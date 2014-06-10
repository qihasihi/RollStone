<%@ page import="java.io.*" %>
<%@ page import="java.awt.*" %>
<%@ page import="java.awt.image.BufferedImage" %>
<%@ page import="com.sun.image.codec.jpeg.JPEGImageEncoder" %>
<%@ page import="com.sun.image.codec.jpeg.JPEGCodec" %>
<%@ page import="java.util.Properties" %>
<%--
  Created by IntelliJ IDEA.
  User: zhengzhou
  Date: 14-5-7
  Time: 下午7:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String url=request.getParameter("path");
    if(url==null||url.trim().length()<1){
        return;
    }

    String issmail=request.getParameter("issmail");//是否是小图  1:是  2 OR NULL：否
    String currentPath = request.getRequestURI().replace( request.getContextPath(), "" );
    //加载配置文件
    currentPath=request.getRealPath(currentPath.substring(0,currentPath.lastIndexOf("/")));
    String propertiesPath = currentPath + "/config.properties";
    Properties properties = new Properties();
    try {
        properties.load( new FileInputStream( propertiesPath ) );
    } catch ( Exception e ) {
        //加载失败的处理
        e.printStackTrace();
    }

    String path =currentPath+"/"+properties.getProperty("savePath")+"/"+url.substring(0,url.lastIndexOf(".")).replaceAll("\\.","")+url.substring(url.lastIndexOf("."));
        if(!new File(path).exists()){
            return; //提示文件不存在
    }

    String writepath=path;
    if(issmail!=null&&issmail.trim().equals("1")){//是否缩放小图
        //将图片缩放
        writepath=ImgResize(path,51,51,1);
    }else
        writepath=ImgResize(path,1024,768,2);
    //将缩略图输出
    loadImage(response,writepath);
%>

<%!
    public void loadImage(HttpServletResponse response,String path) throws Exception {
        response.reset();
        ServletOutputStream output = response.getOutputStream();
        InputStream in = new FileInputStream(path);
        byte tmp[] = new byte[256];
        int i=0;
        while ((i = in.read(tmp)) != -1) {
            output.write(tmp, 0, i);
        }
        in.close();
        output.flush(); //强制清出缓冲区
        output.close();
    }
%>
<%!
    /**
     * 强制压缩/放大图片到固定的大小
     * @param  path String 原始图片路径(绝对完整路径)
     * @param w int 新宽度
     * @param h int 新高度
     * @throws IOException
     */
    public String ImgResize(String path,int w, int h,int type1) throws IOException {
        File _file = new File(path);
        _file.setReadOnly();
        File    srcFile  = _file;
        String  fileSuffix = _file.getName().substring(
                (_file.getName().lastIndexOf(".") + 1),
                (_file.getName().length()));
        String fpath=srcFile.getPath().substring(0,
                (srcFile.getPath().lastIndexOf(".")))
                + srcFile.getName().substring(0,srcFile.getName().lastIndexOf(".")) + "_sm." + fileSuffix;
        if(type1==2)
            fpath=srcFile.getPath().substring(0,
                    (srcFile.getPath().lastIndexOf(".")))
                    + srcFile.getName().substring(0,srcFile.getName().lastIndexOf(".")) + "_bg." + fileSuffix;
        File    destFile    = new File(fpath);
        if(!destFile.exists()){
            Image srcImage  = javax.imageio.ImageIO.read(_file);
            //得到图片的原始大小， 以便按比例压缩。
            int  imageWidth = srcImage.getWidth(null);
            int  imageHeight = srcImage.getHeight(null);
    //        System.out.println("width: " + imageWidth);
    //        System.out.println("height: " + imageHeight);
            //得到合适的压缩大小，按比例。
            if (imageWidth >= imageHeight)
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