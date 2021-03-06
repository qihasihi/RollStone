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
   // Object fromType=session.getAttribute("fromType");
%>

<script type="text/javascript">var current_parent_path="<%=url%>";
href="user?m=toIndex";
<c:if test="${!empty sessionScope.fromType&&(sessionScope.fromType=='lzx'||sessionScope.fromType=='ett')}">
$(function(){
    //$(".head_crumb a[href='user?m=toIndex']").attr("href","<%=UtilTool.utilproperty.getProperty("LZX_WELCOME_PAGE_ADDRESS")%>");
    //直接去掉。删除
 $(".head_crumb a[href='user?m=toIndex']").parent().remove();
});

</c:if>
</script>
<div class="head_crumb">
 <p><strong>
    <%=current_user.getRealname()!=null
    	&&current_user.getRealname().trim().length()>0?
    			current_user.getRealname():current_user.getUsername()%></strong>&nbsp;您好！&nbsp;&nbsp;当前位置：<%=pageName %></p>
    <c:if test="${!empty termList}">
    <div class="jxxt_xueqi">
        <div class="menu"><span id="checkedTerm">${selTerm.year } ${selTerm.termname }</span><a class="ico13" href="javascript:void(0);" onclick="displayObj('termList');"></a></div>
        <ul id="termList" style="display:none;">
            <c:forEach var="tl" items="${termList }">
                <%if(isStudent){%>
                     <li><a href="javascript:void(0);" onclick="changeTerm('${tl.ref }','${tl.year } ${tl.termname }');">${tl.year } ${tl.termname }</a></li>
                <%}else{%>
                    <li><a href="javascript:void(0);" onclick="getTermCondition('${tl.ref }','${tl.year } ${tl.termname }');">${tl.year } ${tl.termname }</a></li>
                <%}%>
            </c:forEach>
        </ul>

        <%if(isTeacher){%>
            <c:if test="${!empty gradeSubjectList}">
                <div class="njxk"><span id="sp_subgrade">${subGradeInfo.gradevalue}${subGradeInfo.subjectname}</span>
                    <c:if test="${!empty gradeSubjectList and fn:length(gradeSubjectList)>1}">
                        <a  id="showMoreSubject" class="ico13" href="javascript:void(0);" onclick="displayObj('gradeSubjectList');"></a>
                    </c:if>
                    <ul id="gradeSubjectList" style="display: none;" class="hide">
                    <c:forEach items="${gradeSubjectList}" var="c" varStatus="idx">
                        <li id="li_${c.gradeid}_${c.subjectid}"><a href="javascript:;" onclick="changeGrade('${c.gradeid}','${c.subjectid}','${idx.index}','${c.gradevalue}${c.subjectname}')">${c.gradevalue}${c.subjectname}</a></li>
                    </c:forEach>
                    </ul>
                </div>
            </c:if>

        <%}%>

    </div>
    </c:if>
</div>

	