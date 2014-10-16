<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/util/common-jsp/common-jxpt.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>${sessionScope.CURRENT_TITLE}</title>
		<script type="text/javascript" src="../js/interactive/columns.js"></script>
		<script type="text/javascript">
			var courseteacher="${courseteacher}";
			var year="${year}";		
			var p1;
			$(function(){
				$(".header_ico1 a").attr("href","../"+$(".header_ico1 a").attr("href"));
				if(courseteacher==null||year==null||year.Trim().length<1){
					alert('错误，非法访问!参数异常!');return;
				}
				p1=new PageControl({
						post_url:'columnsList',
						page_id:'page1',
						page_control_name:"p1",		//分页变量空间的对象名称
						post_form:document.page1form,		//form
						gender_address_id:'page1address',		//显示的区域
						http_free_operate_handler:validateParam,		//执行查询前操作的内容
						http_operate_handler:columnsPageReturn,	//执行成功后返回方法
						return_type:'json',								//放回的值类型
						page_no:1,					//当前的页数
						page_size:200000,				//当前页面显示的数量
						rectotal:0,				//一共多少
						pagetotal:1,
						operate_id:"tbl_body_data"  
					});
					pageGo('p1');
					
					$("input[name='a_rdo_cls']:first").attr("checked",true);
					$("input[name='a_rdo_type']:first").attr("checked",true);
			});
		</script>
	</head>  
  <body>
  <%@include file="/util/head.jsp" %>
  <div class="jxpt_layout">
  <p class="jxpt_icon01">栏目管理</p>
  <div class="jxpt_content_layout">
    <p class="font_strong p_l_10 font-blue font14"><span id="op_type">新建栏目</span></p>
    <table border="0" cellpadding="0" cellspacing="0" class="public_tab1 zt14">
      <col class="w100"/>
      <col class="w780"/>
      <tr>
        <th><span style="color:red">*</span>栏目名称：</th>
        <td><input name="a_columnsname" id="a_columnsname" maxlength="12" type="text" class="public_input w300" />
        <span class="font-gray1">(最多12个汉字)</span></td>
      </tr>
      <tr>
        <th><span style="color:red">*</span>所属班级：</th>
        <td><c:if test="${!empty courseclassList}">   				
   				<c:forEach items="${courseclassList}" var="coursecls">
   					<input type="checkbox" name="a_ck_cls" id="a_ck_${coursecls.classid}" value="${coursecls.classid}"/>
   					<label for="a_ck_${coursecls.classid}">${coursecls.classname}</label>
   				</c:forEach>
		</c:if>  </td> 
      </tr> 
      <tr>
        <th><span style="color:red">*</span>栏目状态：</th>
        <td><c:if test="${!empty columnstype}">
   			<c:forEach items="${columnstype}"  var="ctype">
		   		<input type="radio" name="a_rdo_type" id="a_rdo_${ctype.dictionaryvalue }" value="${ctype.dictionaryvalue }"/>
		   		<label for="a_rdo_${ctype.dictionaryvalue }">${ctype.dictionaryname }</label>
   			</c:forEach>
   		</c:if> </td>
      </tr>
      <tr>
        <th>&nbsp;</th>
        <td><a onclick="operateColumns()" href="javascript:;" class="an_blue">提&nbsp;交</a>
           <span id="doadd" style="display:none">
            <a href="javascript:;" onclick="changeAddOperateType()" class="an_blue">新建栏目</a>
           </span>
            <a href="javascript:;" onclick="history.go(-1);" class="an_blue">返回</a>
         <input type="hidden" value="" id="u_columnsid"/> 
        </td>
      </tr>
    </table>
    <p class="font_strong p_l_10 p_t_20 font-blue font14">栏目管理</p>
    <table border="0" cellpadding="0" cellspacing="0" class="public_tab2">
      <col class="w290" />
      <col class="w160" />
      <col class="w100" />
      <col class="w100" />
      <col class="w100" />
      <col class="w220" />
      <tr>
   		<th>栏目名称</th>
   		<th>所属班级</th>
   		<th>创建人</th>
   		<th>创建时间</th>
   		<th>状态</th>
   		<th>管理</th>
      </tr>
      <tbody  id="tbl_body_data">       
      </tbody>    
    </table>
     <form name="page1form" id="page1form" action="" method="post">
		  	<div id="page1address" style="display:none"></div>
    	</form>
  </div>
</div>
    <%@include file="/util/foot.jsp" %>
   	 
  </body>
</html>
