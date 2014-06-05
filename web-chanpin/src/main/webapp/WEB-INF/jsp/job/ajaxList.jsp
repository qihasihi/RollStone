<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.school.entity.JobInfo"%>   
<%@page import="com.school.util.PageResult"%> 
 <%
      PageResult pageresul=(PageResult)request.getAttribute("pageresult");
      Collection<JobInfo> jobList=pageresul.getList();
    if(jobList==null){%>
      没有数据！！
      <%} else{%>
  <table width="200" border="2" cellspacing="0">
    <tr>
    	    <th>编号</td>
    	    <th>职务</td>
    </tr>
      <%
      Iterator  it  =  jobList.iterator();  //  获得一个迭代子   　　　　
      while(it.hasNext())  { 
    	  JobInfo  jobInfo  =  (JobInfo)it.next();  //  得到下一个元素   　　　　
    %>
     
  <tr>
    <td align="center"><%=jobInfo.getJobid() %></td>
    <td id="<%=jobInfo.getJobid() %>" align="center"><%=jobInfo.getJobname() %></td>
	<!-- <td align="center"><a href="javascript:deleteJob(<%=jobInfo.getJobid() %>)">删除</a></td> --> 
	<!--<td align="center"><a href="javascript:showUpdateDiv('<%=jobInfo.getJobid() %>');">修改</a></td> -->
	<td align="center"><a href="javascript:selUserjob('<%=jobInfo.getJobid() %>');">指定用户</a></td>
  </tr>
   <%}%>
</table>
<input type="hidden" id="pageSize" value="<%=pageresul.getPageSize() %>">
<input type="hidden" id="recTotal" value="<%=pageresul.getRecTotal() %>">
<input type="hidden" id="pageNo" value="<%=pageresul.getPageNo() %>">
<input type="hidden" id="pageTotal" value="<%=pageresul.getPageTotal() %>">
&nbsp;&nbsp;共&nbsp;<%=pageresul.getRecTotal() %>&nbsp; 条&nbsp;&nbsp;
<%if(pageresul.getPageNo()==1) {%>首页&nbsp;&nbsp;&nbsp;上一页
<%}else {%><a href="javascript:jumpToPage(1)">首页</a>&nbsp;&nbsp;&nbsp;<a href="javascript:jumpToPage(<%=pageresul.getPageNo()-1%>)">上一页</a><%}%>
&nbsp;&nbsp;&nbsp;
<%if(pageresul.getPageNo()==pageresul.getPageTotal()) {%>下一页&nbsp;&nbsp;&nbsp;末页
<%}else {%><a href="javascript:jumpToPage(<%=pageresul.getPageNo()+1%>)">下一页</a>&nbsp;&nbsp;&nbsp;<a href="javascript:jumpToPage(<%=pageresul.getPageTotal() %>)">末页</a><%}%>
&nbsp;&nbsp;&nbsp;当前&nbsp;<%= pageresul.getPageNo()%>/<%= pageresul.getPageTotal()%>&nbsp;页
      <%}%>