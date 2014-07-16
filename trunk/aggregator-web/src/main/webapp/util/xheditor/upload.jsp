<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="java.io.File"%>
<%@page import="java.io.DataInputStream"%>
<%@page import="java.io.FileOutputStream"%>
<%@page import="org.apache.commons.fileupload.FileItem"%>
<%@page import="org.apache.commons.fileupload.DiskFileUpload"%>
<%@page import="java.io.InputStream"%>
<%@page import="java.io.OutputStream"%>
<%@page import="java.io.BufferedInputStream"%>
<%@page import="java.io.FileInputStream"%>
<%@page import="java.io.BufferedOutputStream"%>

<%@ page import="com.school.util.UtilTool" %>
<%
	String path = request.getContextPath();
	String basePath = "http://"+UtilTool.utilproperty.getProperty("IP_ADDRESS")+"/"
            + UtilTool.utilproperty.getProperty("PROC_NAME") + "/";
    //String basePath="http://"+request.getServerName()+":"+request.getLocalPort()+request.getContextPath();
	response.setContentType("text/html;charset=utf-8");
	response.setCharacterEncoding("UTF-8");
	request.setCharacterEncoding("UTF-8");
	//文件大小限制			
	double MAX_SIZE = 2; //2GB
	InputStream ins = null;
	OutputStream outs = null;
	String baseDir = request.getRealPath("/") + "/userUploadFile/";
	String tempPath = baseDir + "tmp";
	File f = new File(baseDir);
	List<String> urlPath = new ArrayList<String>();
	if (!f.exists())
		f.mkdir();
	try {
	//HTML5上传
		if ("application/octet-stream".equals(request.getContentType())) {
			String dispoString = request
					.getHeader("Content-Disposition");

			int iFindStart = dispoString.indexOf("name=\"") + 6;

			int iFindEnd = dispoString.indexOf("\"", iFindStart);

			iFindStart = dispoString.indexOf("filename=\"") + 10;

			iFindEnd = dispoString.indexOf("\"", iFindStart);

			String sFileName = dispoString.substring(iFindStart,
					iFindEnd);
			int i = request.getContentLength();

			byte buffer[] = new byte[i];

			int j = 0;

			//获取表单的上传文件

			while (j < i) {

				int k = request.getInputStream().read(buffer, j, i - j);

				j += k;

			}

			//文件是否为空

			if (buffer.length == 0) {

				response
						.getWriter()
						.print(
								"{\"err\":\"上传文件不能为空!\",\"msg\":{\"type\":\"error\",\"msg\":\"上传文件不能为空!\"}}");

				return;

			}

			//检查文件大小
			double fisize = buffer.length / 1024 / 1024 / 1024;
			if (MAX_SIZE > 0 && fisize > MAX_SIZE) {

				String responseT = "{\"err\":\"\",\"msg\":{\"type\":\"error\",\"msg\":\"上传文件的大小不能大于"
						+ (MAX_SIZE / 1024 / 1024) + "GB!\"}}";
				response.getWriter().print(responseT);
				return;

			}

			String base = request.getParameter("base");

			String id = request.getParameter("id");

			//获取文件扩展名 

			String extensionName = sFileName.substring(sFileName
					.lastIndexOf(".") + 1);

			//重命名文件

			String fileName = new Date().getTime() + "";

			String filePathString = baseDir + fileName + "."
					+ extensionName;

			String saveFile = filePathString;

			System.out.println("saveFile:" + saveFile);

			OutputStream oute = new BufferedOutputStream(
					new FileOutputStream(saveFile, true));

			oute.write(buffer);

			oute.close();

			urlPath.add(basePath + "/userUploadFile/" + fileName + "."
					+ extensionName);

		} else {

			DiskFileUpload fu = new DiskFileUpload();
			// 设置最大文件尺寸，这里是4MB
			//	fu.setSizeMax(4194304);
			// 设置缓冲区大小，这里是4kb
			fu.setSizeThreshold(4096);
			// 设置临时目录：
			fu.setRepositoryPath(tempPath);

			// 得到所有的文件：
			List fileItems = fu.parseRequest(request);
			Iterator i = fileItems.iterator();
			// 依次处理每一个文件：
			while (i.hasNext()) {
				FileItem fi = (FileItem) i.next();
				System.out.println(fi.getSize() + "  " + MAX_SIZE);
				double fisize = fi.getSize() / 1024 / 1024 / 1024;
				if (fisize > MAX_SIZE) {
					response.getWriter().print(
							"{\"error\":\"上传文件的字节数不能大于" + MAX_SIZE
									+ "GB!\",\"msg\":\"上传文件的大小不能大于"
									+ (MAX_SIZE / 1024 / 1024)
									+ "GB !\"}");
					return;
				}
				String filename = new Date().getTime() + "";
				if (fi.getName().indexOf(".") != -1)
					filename += fi.getName().substring(
							fi.getName().lastIndexOf("."));

				try {
					//开始文件上传
					File tpFile = new File(baseDir + filename);
					if (tpFile.exists())
						tpFile.delete();
					fi.write(tpFile);

					fi.delete();
					urlPath.add(basePath + "/userUploadFile/"
							+ filename);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	} catch (Exception e) {
		// 可以跳转出错页面
		e.printStackTrace();
	} finally {
		out.clear();
		//out = pageContext.pushBody();
	}
	String responseT = null;
	if (urlPath == null || urlPath.size() < 1) {
		responseT = "{\"err\":\"\",\"msg\":{\"type\":\"error\",\"msg\":\"错误!未知错误!\"}}";
	} else {
		responseT = "{\"err\":\"\",\"msg\":\""
				+ urlPath.get(0).replace("\\", "/") + "\"}";
	}
	//System.out.println(responseT);
	response.getWriter().print(responseT);
%>
