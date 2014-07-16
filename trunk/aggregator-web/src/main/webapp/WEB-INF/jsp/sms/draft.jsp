<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/util/common.jsp" %> 
<%@page import="com.school.entity.SmsInfo"%>  
<html>
<head>
<title>数字化校园</title>
<link rel="stylesheet" type="text/css" href="css/dxzx.css"/>
<script type="text/javascript" src="js/draft.js"></script>
	<script type="text/javascript">
		var p1;
		$(function(){
			//翻页控件
					p1=new PageControl({
						post_url:'sms?m=ajaxdraftlist',
						new_page_html_mode:true,
						page_id:'page1',
						page_control_name:"p1",		//分页变量空间的对象名称
						post_form:document.pageform,		//form
						http_free_operate_handler:beforajaxList,
						gender_address_id:'pageaddress',		//显示的区域						
						http_operate_handler:afterajaxList,	//执行成功后返回方法
						return_type:'json',								//放回的值类型
						page_no:1,					//当前的页数
						page_size:20,				//当前页面显示的数量
						rectotal:0,				//一共多少
						pagetotal:1,
						operate_id:"maintbl"
					}); 
					pageGo('p1');   
		}); 

		function selComplete(){
			//获取权限标识
			var smsidstr =$("input[name='cksmsid']").filter(function(){
				return this.checked==true});
			
			var smsidstring='',returnValue='';
			var smsidarray = [];
			if(smsidstr.length>0){
				$.each(smsidstr,function(idx,itm){
					if(smsidstring.length>0)
						smsidstring+=",";
					smsidstring += itm.value.Trim(); 
				});
			}
			if(smsidstr.length<0){
				msg='未选中数据';
			}else{ 
				msg='删除'; 
			}   
			if(!confirm(msg)){return;}    
			
		      
			$.ajax({
				url:'sms?m=delBySmsid',
				type:'get', 
				data:{
					smsidstr  : smsidstring, 
					//prightidstr : returnValue,
				},
				dataType:'json',
				error:function(){alert("网络异常!")},  
				success:function(rps){ 
					alert(rps.msg);  
					pageGo('p1');  
				}
			});
		}	
	</script>
</head>

<body>
<%@include file="/util/head.jsp" %>
<%@include file="/util/nav-base.jsp" %>

<div class="jxpt_layout">
  <div class="public_lable_layout">
    <ul class="public_lable_list">
      <li><a href="sms?m=inbox">收件箱</a></li>
      <li><a href="sms?m=sent">发件箱</a></li>
      <li><a href="sms?m=write">写信息</a></li>
      <li class="crumb">草稿箱</li>
    </ul>
  </div>
  <p class="jxpt_search p_t_15 p_r_20">
    <input id="sel_keyword" type="text"/>
    <a href="javascript:pageGo('p1')" class="an_darkblue">搜索</a></p>

  <table border="0" cellspacing="0" class="public_tab2 m_a_20 clearit">
  <col class="w40" />
  <col class="w320" />
  <col class="w300" />
  <col class="w200" />
  <col class="w100" />
  <tbody id="maintbl"></tbody>
    </table>
  <p class="p_l_35">
      <input type="checkbox" name="checkbox3" onclick="selAllCkObj(this)" />全选&nbsp;&nbsp;&nbsp;
      <a href="javascript:pageGo('p1');" class="an_blue_small">刷新</a>
      <a href="javascript:selComplete();" class="an_blue_small">删除</a></p>
    <form action="" id="pageform" name="pageform" method="post">
		<div id="pageaddress"></div> 
   </form>   
</div>

<%@include file="/util/foot.jsp" %>
</body>
</html>

