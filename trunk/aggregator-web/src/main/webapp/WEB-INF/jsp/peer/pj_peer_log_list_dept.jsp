<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/util/common-jsp/common-thpj.jsp"%>
<%
UserInfo user=(UserInfo)request.getSession().getAttribute("CURRENT_USER");
%>
<html>
  <head>
   		<script type="text/javascript" src="<%=basePath %>js/peer/pj_peer_stat.js"></script>
   		<script type="text/javascript">
	   		var ptype = "${param.ptype}";
			if(ptype==null||ptype=="undefined"||ptype==""){
				ptype=1;
			}
			$(function(){
				//单选框赋值
				if(ptype>1){
					$("input[name='rtype'][value='2']").attr("checked",true);
				}else{
					$("input[name='rtype'][value='1']").attr("checked",true);
				}
				//下拉框赋值
				var baseref="${param.peerbaseref}";
				$("#selpeerbase").val(baseref);
				//复选框赋值
				if("${olddeptref}"!=null){
					var olddept = "${olddeptref}";
					var objdept = olddept.split("|");
					var deptArray = $("input[name='dept_ck']");
					if(deptArray.length<1){  
						return;
					}	   	   	   	   	
					if(objdept.length>1){
						for(var i = 0;i<objdept.length;i++){							
	   	   	   	   	   	   	for(var j = 0;j<deptArray.length;j++){
								if(deptArray[j].value==objdept[i]){
									deptArray[j].checked=true;
								}
	   	   	   	   	   	   	}	   	   	   	   	   
						}
					}else{
						$("input[name='dept_ck'][value='${olddeptref}']").attr("checked",true);
					}
				}
				
			});
		
			function ck(pt){
				ptype = pt;
			}
			
   	   		function change(){
   	   	   		var peerbaseref = $("#selpeerbase").val();
   	   	   		if(peerbaseref=="-1"){
   	   	   	   		return;
   	   	   	   	}else{
   	   	   	   	   	var deptArray = $("input[name='dept_ck']").filter(function(){
	   	 				return this.checked==true;
	   	 			});
   	   	   	   	   	var uri = 'peerlog?m=getdeptlogstat&peerbaseref='+peerbaseref+'&ptype='+ptype;
   	   	   	   	   	if(deptArray.length>0){
   	   	   	   	   	   	var deptref = '';
   	   	   	   	   	   	for(var i = 0;i<deptArray.length;i++){
							deptref+=deptArray[i].value;
							if(i<deptArray.length-1){
								deptref+='|';
							}
   	   	   	   	   	   	}
						uri+='&deptref='+deptref;
   	   	   	   	   	}
   	   	   	   	   	window.location.href = uri;
   	   	   	   	}
   	   	    }
   		</script>
  </head>
  
  <body>
    	
    	<%@include file="/util/head.jsp"  %>	
    	<div class="thpj_content">
    	 <%@include file="/util/nav.jsp" %>
			  <div class="thpj_contentR">
			    <p class="m_b_10 font14 font_strong"><input type="radio" onclick="ck(1)"  name="rtype" value="1" /> 教师互评&nbsp;&nbsp;&nbsp;&nbsp;
			    <input type="radio" onclick="ck(2)" name="rtype"  value="2" /> 职工互评</p>		     
		    	 <table border="0" cellpadding="0" cellspacing="0" class="public_tab1 zt14 m_b_25">
			      <col class="w100"/>
			      <col class="w560"/>
			      <tr>
			      	<th>评价主题：</th>
			     	 <td>
			      		<select id="selpeerbase">
				    		<option value="-1">请选择</option>
				    		<c:if test="${!empty baseList}">
					      		<c:forEach items="${baseList}" var="itm">
					      			<option value="${itm.ref}">${itm.year }&nbsp;${itm.name} </option>
					      		</c:forEach>
						    </c:if>
				    	</select>
		    		</td>
			      </tr>
			      <tr>
			        <th>参评部门：</th>
			        <td><ul class="public_list1">
			         	<c:if test="${!empty deptList}">
		    				<c:forEach items="${deptList}" var="itm">
		    					<li><input type="checkbox" id="dept_ck" name="dept_ck" value="${itm.deptid }"/>${itm.deptname }</li>
		    				</c:forEach>
	    				</c:if>
			        </ul>
			        </td>
			      </tr>
			      <tr>
			        <th>部门名称：</th>
			        <td><ul class="public_list1">
			        <c:if test="${!empty smalldeptList}">
		    				<c:forEach items="${smalldeptList}" var="itm">
		    					<li><input type="checkbox" id="dept_ck" name="dept_ck" value="${itm.deptid }"/>${itm.deptname }</li>
		    				</c:forEach>
	    				</c:if>
			      </ul></td>
			      </tr>
			      <tr>
			        <th>&nbsp;</th>
			        <td><a href="javascript:change()" class="an_blue">确&nbsp;定</a></td>
			      </tr>
			    </table>
			   
			   <p class="public_title">评价详情</p>
			    <p class="p_tb_10"><span class="font-red">说明：</span><br />
			    ${remark }</p>
			
			    <table border="0" cellpadding="0" cellspacing="0" class="public_tab2 td85">
			      <tr>
			        <th>姓名</th>
			       <c:if test="${!empty itemList}">
    				<c:forEach items="${itemList}" var="itm" varStatus="idx">
    					<th>第${idx.index+1 }项得分</th>
    				</c:forEach>
                </c:if>
			        <th>总分</th>
			        <th>排名</th>
			      </tr>
			      <tbody>
    		<c:if test="${!empty logList}">
    			 
	    			<c:forEach items="${logList}" var="logK" varStatus="idx">  
	    				<c:if test="${idx.index%2!=0}">
	    					<tr class="trbg2"> 
	    				</c:if>
	    				<c:if test="${idx.index%2==0}">
	    					<tr> 
	    				</c:if>  
		    				<c:forEach items="${logK}" var="logVal">   	    		     				
		        			<td>${logVal }</td>	
		        			</c:forEach>
	        			</tr>        				
	    			</c:forEach>    					
    		</c:if>    		
    		</tbody>
			    </table>
			  </div>
			
			<div class="thpj_contentL">
			  <ul>
			    <li ><a href="peerbase?m=list">时间管理</a></li>
    <li ><a href="peeritem?m=list">编辑评价</a></li>
    <li><a href="1">问卷设置</a></li>
    <li class="crumb"><a href="peerlog?m=todeptlogstat">评价结果</a></li>
			  </ul>
			</div>
			<div class="clear"></div>
			<%@include file="/util/foot.jsp"  %>
	</div>
  </body>
</html>
