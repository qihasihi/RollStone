<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.school.entity.UserInfo"%>
<%@page import="javax.xml.parsers.DocumentBuilderFactory"%>
<%@page import="javax.xml.parsers.DocumentBuilder"%>
<%@page import="org.w3c.dom.Document"%>
<%@page import="java.io.File"%>
<%@page import="org.w3c.dom.Element"%>
<%@page import="org.w3c.dom.NodeList"%>
<%@page import="org.w3c.dom.Node"%>
<% UserInfo current_user = (UserInfo)request.getSession().getAttribute("CURRENT_USER");
//得到当前文件的完整路径及个别路径 
String parenturl=request.getRequestURI();
String url=parenturl.substring(0,parenturl.lastIndexOf("/"));
	   url=url.substring(url.lastIndexOf("/")).replaceAll("/","");
	String pageName="";
//读取XML
	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	DocumentBuilder db = factory.newDocumentBuilder();
	Document doc = db.parse(new File(request.getRealPath("/")+"util/xml/page-nav.xml"));
	Element elmtInfo = doc.getDocumentElement();
	NodeList nodes=elmtInfo.getChildNodes();
	if(nodes!=null&&nodes.getLength()>0){
		for (int i = 0; i < nodes.getLength(); i++) {
			Node result = nodes.item(i);
			//得到folder
			if (result.getNodeType() == Node.ELEMENT_NODE&& result.getNodeName().equals("folder")) {
				 String displayName=result.getAttributes().getNamedItem("displayName").getNodeValue();
				 String name=result.getAttributes().getNamedItem("name").getNodeValue();
				 if(name.indexOf("/")!=-1){
				 	if(parenturl.indexOf(name)!=-1)
				 		pageName=displayName;				 	
				 }else if(url.equals(name)){
				 		pageName=displayName;
				 }
			}  
		}	  
	}
%>
<script type="text/javascript">var current_parent_path="<%=url%>";</script>
<div class="head_crumb">
 <p><strong>
    <%=current_user.getRealname()!=null
    	&&current_user.getRealname().trim().length()>0?
    			current_user.getRealname():current_user.getUsername()%></strong>&nbsp;您好！&nbsp;&nbsp;当前位置：<%=pageName %></p>
</div>
	