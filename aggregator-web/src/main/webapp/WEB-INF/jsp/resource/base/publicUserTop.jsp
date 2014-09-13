<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/util/common-jsp/common-zrxt.jsp" %>
<%@page import="com.school.entity.resource.ExtendInfo"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>数字化校园</title>
<script type="text/javascript">
	var p1,p2; 
	var tchuserid;
	var adminFlag=${adminFlag};
	$(function(){
		p1=new PageControl({
			post_url:'resource?m=ajaxList',
			page_id:'page1',
			page_control_name:"p1",		//分页变量空间的对象名称
			post_form:document.page1form,		//form
			http_free_operate_handler:beforeAjaxList,
			gender_address_id:'page1address',		//显示的区域						
			http_operate_handler:afterAjaxList,	//执行成功后返回方法
			return_type:'json',								//放回的值类型
			page_no:1,					//当前的页数
			page_size:10,				//当前页面显示的数量
			rectotal:0,				//一共多少
			pagetotal:1,
			operate_id:"teacherList"
		});
		
		p2=new PageControl({
			post_url:'resource?m=ajaxList',
			page_id:'page2',
			page_control_name:"p2",		//分页变量空间的对象名称
			post_form:document.page2form,		//form
			http_free_operate_handler:beforeTchResAjaxList,
			gender_address_id:'page2address',		//显示的区域						
			http_operate_handler:afterTchResAjaxList,	//执行成功后返回方法
			return_type:'json',								//放回的值类型
			page_no:1,					//当前的页数
			page_size:5,				//当前页面显示的数量
			rectotal:0,				//一共多少
			pagetotal:1,
			operate_id:"teacherReses"
		});
		
		pageGo('p1');
	});
	
	function beforeAjaxList(p){
	}
	
	function afterAjaxList(rps){
		var html = "<li>上传量排行榜</li>";
		if(rps!=null&&rps.presult.list.length>0){
			$.each(rps.objList,function(idx,itm){
				html+="<li id='tch_li_"+itm.userid+"'><span>"+itm.resnum+"</span>";	
				html+="<a href="+"javascript:getTchReses("+itm.userid+");"+">"+ itm.realname +"</a>";  
				html+="</li>";
			});
			refreshTchResult(rps);
			$("#teacherList").html(html);
			getTchReses(rps.presult.list[0].userid);
		}else{
			html+="<li>没有找到！</li>";
			var reshtml="";
			reshtml+="<div class='content'>";	
			reshtml+="<p><strong>暂无数据！</strong></p>";
			reshtml+="</div> ";
			$("#teacherReses").html(reshtml);
			refreshTchResult(rps);
			$("#teacherList").html(html);
			$("#tchRealName").html("");
			$("#tchUserName").html("");
			$("#tchName").html("");
			$("#tchSubject").html("");
		}
	}
	
	function beforeTchResAjaxList(p){
		var param = {};
		param.userid = tchuserid;
		param.viewType = "false"; 
		p.setPostParams(param);
	}
	
	function afterTchResAjaxList(rps){
		var html = "";
		if(rps!=null&&rps.presult.list.length>0){
			$.each(rps.objList,function(idx,itm){
				html+="<div class='content'>";	
				html+="<p><strong>资源名称：</strong><a target='_blank' href='resource?m=todetail&resid="+itm.resid+"'>"+ itm.resname +"</a>&nbsp;&nbsp;";
				html+="<span id='rec_"+itm.resid+"'>";
				html+="</span></p>";
				html+="<p><strong>简&nbsp; &nbsp; 介：</strong><span class='width wrapline'>"+(itm.resintroduce==null?"无":itm.resintroduce)+"</span></p>";
				html+="<p class='info'>资源类型："+itm.subjectname+"、"+itm.gradename+"、"+itm.restypename+"、"+itm.filetypename
                        +"&nbsp;&nbsp;&nbsp;赞："+itm.praisenum+"&nbsp;&nbsp;&nbsp;收藏："+itm.storenum+"&nbsp;&nbsp;&nbsp;浏览："+itm.clicks+"&nbsp;&nbsp;&nbsp;下载："+itm.downloadnum+"</p>";
				html+="</div> ";
			});
		}else{
			html+="<div class='content'>";	
			html+="<p><strong>暂无数据！</strong></p>";
			html+="</div> ";
		}
		$("#tchResTotal").html(rps.presult.recTotal);
		p2.setPageSize(rps.presult.pageSize);
		p2.setPageNo(rps.presult.pageNo);
		p2.setRectotal(rps.presult.recTotal);
		p2.setPagetotal(rps.presult.pageTotal);
		p2.Refresh();
		$("#teacherReses").html(html);
	}
	function refreshTchResult(rps){
		p1.setPageSize(rps.presult.pageSize);
		p1.setPageNo(rps.presult.pageNo);
		p1.setRectotal(rps.presult.recTotal);
		p1.setPagetotal(rps.presult.pageTotal);
		p1.Refresh();
		var html="";
		html+='共 <span class="F_red">'+p1.getRectotal()+'</span>'+'条&nbsp;';
		if(p1.getPageNo()==1)
			html+= '<img src="'+p1.getRootProject()+'images/page02_a.gif" name="pagePre" id="pagePre" title="上一页" width="14" height="16" border="0" />&nbsp;';
		else
			html+= '<a href="javascript:pagePre(\'p1\');">'
				+'<img src="'+p1.getRootProject()+'images/page02_b.gif" name="pagePre" id="pagePre" title="上一页" width="14" height="16" border="0" /></a>&nbsp;';
		if(p1.getPageNo()==p1.getPagetotal())
			html+= '<img src="'+p1.getRootProject()+'images/page03_a.gif" name="pagePre" id="pagePre" title="上一页" width="14" height="16" border="0" />&nbsp;';
		else
			html+= '<a href="javascript:pageNext(\'p1\');">'
				+'<img src="'+p1.getRootProject()+'images/page03_b.gif" name="pageNext" id="pageNext" title="下一页" width="14" height="16" border="0" /></a>&nbsp;';
		$("#tchResult").html(html);
	}
	
	function getTchReses(userid){
		getTchInfo(userid);
		$("#tch_li_"+userid).siblings().attr("class","");
		$("#tch_li_"+userid).attr("class","crumb");
		tchuserid=userid;
		pageGo('p2');  		
	}
	
	function getTchInfo(userid){
		$.ajax({
			url:'teacher?m=ajaxlistByTnOrUn',
			type:'post',
			data:{tuserid:userid },
			dataType:'json',
			error:function(){alert("网络异常")},
			success:function(rps){
				if(rps.type=="success"&&rps.objList.length>0){
					$.each(rps.objList,function(idx,itm){
						var tch=rps.objList[0];
						$("#tchRealName").html(tch.teachername);
						$("#tchUserName").html(tch.userinfo.username);
						$("#tchName").html(tch.teachername);
						$("#tchSubject").html(tch.subjects!=null?tch.subjects:"无");
					});
				}else{
					$("#tchRealName").html("");
					$("#tchUserName").html("");
					$("#tchName").html("");
					$("#tchSubject").html("");
				}
			}
		});
	}
	
	function cutoverSrhType(n){
		if(n==1){
			$("#srhType").html("<a href='javascript:cutoverSrhType(1);' class='jxpt_ziyuan_search_an'>资源</a>"
					+"<a href='javascript:cutoverSrhType(2);'>教师</a></p>");
			searchType=1;
		}
		if(n==2){
			$("#srhType").html("<a href='javascript:cutoverSrhType(1);'>&nbsp;&nbsp;资源&nbsp;&nbsp;</a>"
					+"<a href='javascript:cutoverSrhType(2);' class='jxpt_ziyuan_search_an'>教师</a></p>");
			searchType=2;
		}
	}
	
	function doSearch(){
		if(searchType==1){
			var url="resource?m=list&srhValue="+$("#searchValue").val();
			url=encodeURI(encodeURI(url));
			window.location.href=url;
		}
		if(searchType==2){
			pageGo('p1');
		}
	}
	
</script>
</head>

<body>
<%@include file="/util/head.jsp" %>
<div class="jxpt_layout">
  <%@include file="/util/nav.jsp" %>
  
  <p class="jxpt_icon06">教师上传量排行</p>
  
<div class="jxpt_ziyuan_jsss">
<div class="jxpt_ziyuan_jsssR">
   <p class="p_l_10"><strong class="font-blue">
   <span id="tchRealName"></span></strong>
   &nbsp;&nbsp;用户名：<span id="tchUserName"></span>
   &nbsp;&nbsp;姓名：<span id="tchName"></span>
   &nbsp;&nbsp;教授科目：<span id="tchSubject"></span>
   &nbsp;&nbsp;发布资源数：<span id="tchResTotal"></span>
   </p>
   <h5></h5>
   <div id="teacherReses">
   </div>
   <div class="t_r p_t_10">
<form id="page2form" name="page2form" method="post">
  			<p align="center" id="page2address"></p>
	 </form> </div>
</div>

<div class="jxpt_ziyuan_jsssL">
  <ul id="teacherList">
  </ul>
<p class="p_l_20 p_20" id="tchResult"></p>
   <form id="page1form" name="page1form" method="post" style="display:none;">
  			<p align="center" id="page1address"></p>
	 </form>
</div>
<div class="clear"></div>

 
</div>
</div>

<%@include file="/util/foot.jsp" %>
</body>
</html>
