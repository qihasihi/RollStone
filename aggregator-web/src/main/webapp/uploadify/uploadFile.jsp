<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page
	import="java.sql.*,java.io.*,java.util.zip.*,com.school.entity.resource.*,java.util.*,com.school.entity.*,com.school.manager.inter.franchisedschool.*,com.school.manager.franchisedschool.*,com.school.entity.franchisedschool.*,com.school.manager.resource.*"%>
<%@page import="com.school.manager.inter.IUserManager"%>
<%@page import="com.school.manager.UserManager"%>
<%@page import="com.school.dao.base.CommonDAO"%>
<%@page import="com.school.dao.base.ICommonDAO"%>
<%@page import="com.school.util.UtilTool"%>
<%@page import="com.school.dao.resource.ExtendResourceDAO"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@page
	import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="com.school.util.PageResult"%>
<%@page import="com.school.manager.inter.resource.IResourceManager"%>
<%@page import="com.school.manager.inter.resource.IResourceFileManager"%>
<%@page import="com.school.dao.resource.ResourceFileDAO"%>
<%@page import="com.school.dao.inter.resource.IResourceDAO"%>
<%@page import="com.school.dao.inter.resource.IResourceFileDAO"%>
<%@page import="com.school.dao.inter.resource.IExtendResourceDAO"%>
<%@page import="com.school.util.JsonEntity"%>
<%@page import="org.apache.commons.fileupload.disk.DiskFileItemFactory"%>
<%@page import="org.apache.commons.fileupload.servlet.ServletFileUpload"%>
<%@page import="org.apache.commons.fileupload.FileUploadException"%>
<%@page import="org.apache.commons.fileupload.FileItem"%>
<%@page import="com.school.control.base.BaseController"%>
<%
	response.setHeader("Cache-Control", "no-store");
	response.setDateHeader("Expires", 0);
%>
<%

	
	response.setCharacterEncoding("utf-8");
	JsonEntity je = new JsonEntity();

	String savepath = UtilTool.utilproperty.getProperty("RESOURCE_SERVER_PATH");
	DiskFileItemFactory fac = new DiskFileItemFactory();
	ServletFileUpload upload = new ServletFileUpload(fac);
	upload.setHeaderEncoding("utf-8");
	//upload.setFileSizeMax(1024*1024*1024*8);
	List fileList = null;

	try {
		fileList = upload.parseRequest(request);
	} catch (FileUploadException ex) {
		ex.printStackTrace();
	}
	Iterator<FileItem> it = fileList.iterator();
    String resid="";
	String name = "";
	String extName = "";
	String typeid = "";
	String path = "";
	boolean flag = false;
	String msg = "";
	while (it.hasNext()) {
		FileItem item = it.next();
		if (item.isFormField()) {
            if ("resid".equals(item.getFieldName())) {
                resid = item.getString("utf-8");
                if (resid == null || resid.trim().length() < 1) {
                    return;
                }
                path=UtilTool.getResourceMd5Directory(resid);
            }
		} else if (!item.isFormField()) {
			name = item.getName();
			if (name == null || name.trim().equals("")) {
				continue;
			}
			// 扩展名格式：
			if (name.lastIndexOf(".") >= 0) {
				extName = name.substring(name.lastIndexOf("."));
                if(extName!=null&&UtilTool.matchingText(UtilTool._VIEW_SUFFIX_TYPE_REGULAR,extName.toLowerCase())){
                    if(!extName.toLowerCase().equals(".mp4")){
                        je.setMsg("仅限MP4格式的视频，请转换后再上传!");
                        response.getWriter().print(je.toJSON());
                        return;
                    }
                }
			}else
                return;
			savepath = savepath +"/"+ path + "/";
			File f1 = new File(savepath);
			if (!f1.exists()) {
				f1.mkdirs();
			}
			File saveFile = new File(savepath + "001" + extName);
			try {
				item.write(saveFile);
				flag = true;
			} catch (Exception e) {
				e.printStackTrace();
				flag = false;
				msg = e.getMessage();
			}
		}
	}
	
%>
