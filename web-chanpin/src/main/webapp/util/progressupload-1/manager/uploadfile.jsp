<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="java.io.IOException"%>
<%@page import="org.apache.commons.fileupload.disk.DiskFileItemFactory"%>
<%@page import="org.apache.commons.fileupload.servlet.ServletFileUpload"%>
<%@page import="org.apache.commons.fileupload.FileItem"%>
<%@page import="java.io.File"%>
<%@page import="java.io.InputStream"%>
<%@page import="java.io.FileOutputStream"%>
<%@page import="org.apache.commons.fileupload.ProgressListener"%>
<%@page import="com.school.util.ProgressUtil"%>
<%
  try {  
  			request.setCharacterEncoding("UTF-8");
            upload(request, response);  
        } catch (IOException e) {  
            System.out.println("上传文件发生异常,错误原因 : " + e.getMessage());  
        }  

%>

<%! public void upload(HttpServletRequest request,  
                HttpServletResponse response) throws IOException {  
            System.out.println("客户端提交类型: " + request.getContentType());  
            if (request.getContentType() == null) {  
                throw new IOException(  
                        "the request doesn't contain a multipart/form-data stream");  
            }
            
            String key = request.getParameter("key");  
           // Object sessionAttrObj=request.getSession().getAttribute(key);  
          //  System.out.println(sessionAttrObj);
            ProgressUtil p=null;
            if(request.getSession().getAttribute(key)==null){
            	p=new ProgressUtil();
            	request.setAttribute(key,p);
            }else
            	p=(ProgressUtil)request.getSession().getAttribute(key);
            // 设置上传文件总大小  
            p.setLength(request.getContentLength());  
             System.out.println("上传文件大小为 : " + p.getLength());  
            // 上传临时路径  
            String path = request.getSession().getServletContext().getRealPath("/")+"/userUploadFile/";  
             System.out.println("上传临时路径 : " + path);  
            // 设置上传工厂  
            DiskFileItemFactory factory = new DiskFileItemFactory();  
            factory.setRepository(new File(path));  
            // 阀值,超过这个值才会写到临时目录  
            factory.setSizeThreshold(1024 * 1024 * 10);  
            ServletFileUpload upload = new ServletFileUpload(factory);  
            // 最大上传限制  
            upload.setSizeMax(1024 * 1024 *1024* 2);  
            // 设置监听器监听上传进度  
            upload.setProgressListener(p);  
            try {  
                 System.out.println("解析上传文件....");  
                List<FileItem> items = upload.parseRequest(request);  
                  
                 System.out.println("上传数据...");  
                for (FileItem item : items) {  
                      
                    // 非表单域  
                    if (!item.isFormField()) {  
                         System.out.println("上传路径  : " + path + item.getName());  
                        FileOutputStream fos = new FileOutputStream(path + item.getFieldName());  
                        // 文件全在内存中  
                        if (item.isInMemory()) {  
                            fos.write(item.get());  
                            p.setComplete(true);  
                        } else {  
                            InputStream is = item.getInputStream();  
                            byte[] buffer = new byte[1024];  
                            int len;  
                            while ((len = is.read(buffer)) > 0) {  
                                fos.write(buffer, 0, len);  
                            }  
                            is.close();  
                        }  
                        fos.close();  
                         System.out.println("完成上传文件!");  
                          
                        item.delete();  
                         System.out.println("删除临时文件!");  
                          
                        p.setComplete(true);  
                         System.out.println("更新progress对象状态为完成状态!");  
                         
                    }  
                }  
            } catch (Exception e) {  
                 System.out.println("上传文件出现异常, 错误原因 : " + e.getMessage());  
                // 发生错误,进度信息对象设置为完成状态  
                p.setComplete(true);  
                
            }finally{
            	request.getSession().removeAttribute(key);  
           		request.getSession().removeAttribute("f_currentLength");
            }
        }   %>

