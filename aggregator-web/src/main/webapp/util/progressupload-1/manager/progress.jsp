<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="java.io.PrintWriter"%>
<%@page import="java.io.IOException"%>
<%@page import="com.school.util.ProgressUtil"%>
<%
	//		String callback1 = request.getParameter("callback1");  
	//       String callback2 = request.getParameter("callback2");  
	// 缓存progress对象的key值  
	String key = request.getParameter("key");
	//String key = Integer.toString(request.hashCode());  
	// 新建当前上传文件的进度信息对象  
	ProgressUtil p = null;
	if (request.getSession().getAttribute(key) != null)
		p = (ProgressUtil) request.getSession().getAttribute(key);
	else {
		p = new ProgressUtil();
		request.getSession().setAttribute(key, p);
	}

	// 缓存progress对象        
	request.setCharacterEncoding("UTF-8");
	/*清楚缓存 */
	response.setContentType("text/html;charset=UTF-8");
	response.setHeader("pragma", "no-cache");
	response.setHeader("cache-control", "no-cache");
	response.setHeader("expires", "0");
	try {
		// execClientScript(response, callback1 + "(" + key + ")");  
		int threadSize = 0;		
		while(!p.isComplete()){
			if(p.getCurrentLength()>0){				
				response.getWriter().print(
						p.getCurrentLength() + "|" + p.getLength());
				break;
			}
		}
	} catch (Exception e) {
		System.out.println("调用客户端脚本错误,原因 ：" + e.getMessage());
		request.getSession().removeAttribute("f_currentLength");
		p.setComplete(true);
	}
%>
<%!/* 
	 * 执行客户端脚本 
	 *  
	 * @param response 
	 *            http response 
	 * @param script 
	 *            javscript string 
	 * @throws IOException  
	 *            IOException 
	
	 void execClientScript(HttpServletResponse resposne,  
	 String script) throws IOException {                
	 PrintWriter out = resposne.getWriter();                
	 out.println("<script type='text/javascript'>" + script + " </script>");  
	 out.flush();  
	 }   */%>
