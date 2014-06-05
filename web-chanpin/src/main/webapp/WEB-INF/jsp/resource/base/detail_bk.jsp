<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.school.entity.resource.ResourceInfo"%>
<%@page import="com.school.entity.ScoreInfo"%>
<%@include file="/util/common-jsp/common-zrxt.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%String fname=null;//文件名称 %>   
<html>
  <head>
    <title>资源详情</title>    
    <script type="text/javascript" src="js/resource/resbase.js"></script>
    <link rel="stylesheet" type="text/css" href="css/resComment_star.css"/>
    <script type="text/javascript" src="js/comment/resCommentScorePlug.js"></script>   
    <script type="text/javascript" src="js/common/videoPlayer/new/jwplayer.js"></script>
	<script type="text/javascript" src="flexpaper/flexpaper.js"></script>
    <script type="text/javascript">
    	//文件地址:在msg.property中进行修改
   		var resourcepathHead="<%=fileSystemIpPort%>uploadfile/";		
   		var searchType=1;
    	var p1; 
  	
function resizeimg(ImgD, iwidth, iheight) {
	var image = new Image();
	image.src = ImgD.src;
	setTimeout(function(){  
	if (image.width > 0 && image.height > 0) {
		if (image.width / image.height >= iwidth / iheight) {
			if (image.width > iwidth) {
				ImgD.width = iwidth;
				ImgD.height = (image.height * iwidth) / image.width;
			} else {
				ImgD.width = image.width;
				ImgD.height = image.height;
			}
		} else {
			if (image.height > iheight) {
				ImgD.height = iheight;
				ImgD.width = (image.width * iheight) / image.height;
			} else {
				ImgD.width = image.width;
				ImgD.height = image.height;
			}
		}
	}},300);
}
    	$(function(){	 
    		$("[id='input_data']").each(function() {
    			$(this).hide();
    		});
    	    if($("#show_img_zhong_jpg").length>0){
    	   		resizeimg(document.getElementById('show_img_zhong_jpg'),433,300);	   	
    	   		$("#show_img_zhong_jpg").show();
			}
    	 	//if(isSelect){
    			//翻页控件
    		p1=new PageControl({
				post_url:'commoncomment?m=ajaxlist',
				page_id:'page1',
				page_control_name:"p1",		//分页变量空间的对象名称
				post_form:document.page1form,		//form
				gender_address_id:'page1address',		//显示的区域
				http_free_operate_handler:beforeAjaxList,		//执行查询前操作的内容
				http_operate_handler:afterAjaxList,	//执行成功后返回方法
				new_page_html_mode:true,  //新分页html样式 ,默认为旧的
				return_type:'json',								//放回的值类型
				page_no:1,					//当前的页数
				page_size:10,				//当前页面显示的数量
				rectotal:0,				//一共多少
				pagetotal:1,
				operate_id:"commentList" 
			});
			
			px=new PageControl({
				post_url:'resource?m=ajaxList',
				page_id:'pagex',
				page_control_name:"px",		//分页变量空间的对象名称
				page_abbreviated:true,
				post_form:document.pageXform,		//form
				http_free_operate_handler:beforeXAjaxList,
				gender_address_id:'pageXaddress',		//显示的区域						
				http_operate_handler:afterXAjaxList,	//执行成功后返回方法
				return_type:'json',								//放回的值类型
				page_no:1,					//当前的页数
				page_size:5,				//当前页面显示的数量
				rectotal:0,				//一共多少
				pagetotal:1,
				operate_id:"xResList"
			});
			pageGo('px');
			pageGo('p1');

            $("#subject").val(${resinfo.subject });
            $("#grade").val(${resinfo.grade });
            $("#restype").val(${resinfo.restype });
            $("#filetype").val(${resinfo.filetype });

    	});
    	
    	function beforeAjaxList(p){
    		var param = {};
    		param.commenttype = ${commenttype};
    		param.commentobjectid = "${param.resid }";
    		p.setPostParams(param);
    	}
    	
    	function afterAjaxList(rps){
    			var html = "";
    			if(rps!=null&&rps.presult.list.length>0){
    				$.each(rps.objList,function(idx,itm){
    					html+="<div class='jxpt_ziyuan_pl_content1'>";
    					html+="<div class='jxpt_ziyuan_pl_contentL'>"+((rps.presult.pageNo-1)*5+idx+1)+"楼</div>";
    					html+="<div class='jxpt_ziyuan_pl_contentR'>";
    					html+="<p>"+ itm.commentcontext+"</p>";
    					html+="<p class='f_right'><a href="+"javascript:doSupportOrOppose('"+itm.commentid+"',true);"+" title='顶'><s id='"+itm.commentid+"_support'>"+itm.support+"</s></a>";
    					html+="<a href="+"javascript:doSupportOrOppose('"+itm.commentid+"',false);"+" title='踩'><em id='"+itm.commentid+"_oppose'>"+itm.oppose+"</em></a></p>";
    					if(itm.anonymous==1)
    						itm.commentusername="匿名用户";
    					html+="<p class='jxpt_ziyuan_pl_time'>"+itm.ctimeString+" 来自："+itm.commentusername+"</p>";
    					html+=" </div><div class='clear'></div></div>";
    				});
    			}else{
    				html+="<div class='jxpt_ziyuan_pl_content1'>";
    				html+="<div class='jxpt_ziyuan_pl_contentL'></div>";
					html+="<div class='jxpt_ziyuan_pl_contentR'>";
					html+="暂无评论！！";
					html+=" </div><div class='clear'></div></div>";
    			}
    			p1.setPageSize(rps.presult.pageSize);
    			p1.setPageNo(rps.presult.pageNo);
    			p1.setRectotal(rps.presult.recTotal);
    			p1.setPagetotal(rps.presult.pageTotal);
    			p1.Refresh();
    			$("#commentList").html(html);
    			$("#cpn_0").html(rps.presult.recTotal);
    			$("#cpn_1").html(rps.presult.recTotal);
    	}    
    	
    	function beforeXAjaxList(p){
    		var param = {};
    		param.values = "${values}";
    		p.setPostParams(param);
    	}
    	
    	function afterXAjaxList(rps){
    			var html = "";
    			if(rps!=null&&rps.presult.list.length>0){
    				$.each(rps.objList,function(idx,itm){
    					html+="<p class='p_t_10'>";
    					html+="<a href='resource?m=todetail&resid="+itm.resid+"'>"+itm.resname+"</a></p>";
    					html+="<p class='t_r'>"+itm.howLongWithTaday+" "+itm.username+"</p>";
    					html+="<h5></h5>";
    				});
    			}else{
					html+="暂无";
    			}
    			px.setPageSize(rps.presult.pageSize);
    			px.setPageNo(rps.presult.pageNo);
    			px.setRectotal(rps.presult.recTotal);
    			px.setPagetotal(rps.presult.pageTotal);
    			px.Refresh();
    			$("#xResList").html(html);
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
    			var url="resource?m=toteacherreslist&srhValue="+$("#searchValue").val();
    			url=encodeURI(encodeURI(url));
    			window.location.href=url;
    		}
    	}
    	
    	function operateRecommend(resid){
    		if(typeof(resid)=="undefined"||resid==null||resid.Trim().length<1){
    			alert("异常错误，请刷新页面后重试! 原因：resid is empty!");return;
    		}
    		$.ajax({
    			url:'operaterecord?m=recomendres',
    			type:'POST',
    			data:{resid:resid},
    			dataType:'json',
    			error:function(){alert("网络异常")},
    			success:function(rps){
    				if(rps.type=="error"){
    					alert(rps.msg);
    					return;
    				}
    				$("#recomend_status").html("已推荐");
    			}
    		});    		
    	}
    	
    	function operatePraise(resid){
    		if(typeof(resid)=="undefined"||resid==null||resid.Trim().length<1){
    			alert("异常错误，请刷新页面后重试! 原因：resid is empty!");return;
    		}
    		$.ajax({
    			url:'operaterecord?m=praiseres',
    			type:'POST',
    			data:{resid:resid},
    			dataType:'json',
    			error:function(){alert("网络异常")},
    			success:function(rps){
    				if(rps.type=="error"){
    					alert(rps.msg);
    					return;
    				}
    				$("#praise_status").html("已点赞");
    			}
    		});    		
    	}
    	
    	function resourceReport(resid){
    		$("#addReportBtn").hide();
    		var content=$("#report_content").val();
    		if(typeof(content)=="undefined"||content==null||content.Trim().length<1){
    			alert("请填写举报原因！");return;
    		}
    		
    		if(typeof(resid)=="undefined"||resid==null||resid.Trim().length<1){
    			alert("异常错误，请刷新页面后重试! 原因：resid is empty!");return;
    		}
    		$.ajax({
    			url:'resourcereport?m=addresourcereport',
    			type:'POST',
    			data:{resid:resid,content:content},
    			dataType:'json',
    			error:function(){alert("网络异常");$("#addReportBtn").show();},
    			success:function(rps){
    				if(rps.type=="error"){
    					alert(rps.msg);
    					return;
    				}
    				alert("举报成功！");
    				closeModel('resource_report');
    			}
    		});    		
    	}
    	
    	function showInputData(type){
    		if(type){
    			$("[id='display_data']").each(function() {
        			$(this).hide();
        		});
        		$("[id='input_data']").each(function() {
        			$(this).show();
        		});
    		}else{
    			$("[id='display_data']").each(function() {
        			$(this).show();
        		});
        		$("[id='input_data']").each(function() {
        			$(this).hide();
        		});
    		}
    	}
    	
    </script>
  </head>  
  <body>  
  <%@include file="/util/head.jsp" %>
  	<div class="jxpt_layout">
  <%@include file="/util/nav.jsp" %>
  
  <div class="jxpt_ziyuan_search_layout">
  <div class="jxpt_ziyuan_search_content">
    <p class="jxpt_ziyuan_search_an_layout"  id="srhType">
    <a href="javascript:cutoverSrhType(1);" class="jxpt_ziyuan_search_an">资源</a>
    <a href="javascript:cutoverSrhType(2);">教师</a></p>
    <p>
      <input id="searchValue" name="searchValue" type="text" class="ziyuan_search" />
      <a href="javascript:doSearch()" class="an_darkblue">搜索</a>&nbsp;&nbsp;</p>
  </div>
  </div>
  
  <div class="jxpt_ziyuan_layoutR">
    <p class="jxpt_ziyuan_gonggao">资源统计情况</p>
      <ul class="p_lr_15 m_t_10">
        <li><span>（<b id="cpn_0">0</b>人评）</span>评分结果：</li>
        <li><span>${resinfo.storenum }</span><a href="javascript:;">收藏次数：</a></li>
        <li><span>${resinfo.clicks}</span><a href="javascript:;">浏览次数：</a></li>
        <li><span>${resinfo.downloadnum }</span><a href="javascript:;">下载次数：</a></li>
        <li><span>${resinfo.recomendnum }</span><a href="javascript:;">推荐次数：</a></li>
        <li><span>${resinfo.praisenum }</span><a href="javascript:;">点赞次数：</a></li>
        <!-- <li><span>4</span><a href="1">分享次数：</a></li> -->
      </ul>
    <p class="jxpt_ziyuan_gonggao m_t_15">您可能对以下资源有兴趣</p>
      <div id="xResList" class="p_lr_15">
      
      </div>
      <form action="" id="pageXform" name="pageXform" method="post">
		<p align="center" id="pageXaddress"></p>
	</form> 
  </div>
  
  <div class="jxpt_ziyuan_layoutL">
	
 <c:if test="${!empty resinfo.filename}">
			<c:set scope="request" var="filename" value="${resinfo.filename}"></c:set>
			<c:set scope="request" var="resid" value="${resinfo.resid}"></c:set>
			<c:set scope="request" var="respath" value=""></c:set>
			<% 
			fname = request.getAttribute("filename").toString();
			String filetype=UtilTool.getResourseType(fname); 
			String filepath=UtilTool.getResourceUrl(request.getAttribute("resid").toString(),request.getAttribute("filename").toString(),request.getAttribute("respath").toString());
			String viewFilepath=UtilTool.getConvertPath(request.getAttribute("resid").toString(),request.getAttribute("filename").toString());
			String viewResourceUrl=UtilTool.getResourceViewUrl(request,request.getAttribute("resid").toString(),request.getAttribute("filename").toString(),request.getAttribute("respath").toString());								
								
			if(filetype.trim().equals("video")||filetype.trim().equals("mp3")||filetype.equals("jpeg")){
			%>
				<input type="hidden" value="${resinfo.resid }" id="hd_id"/>
				<div class="jxpt_ziyuan_spR">
		  <p><span>(<i id="cpn_1">0</i>人评论)</span><strong>资源名称：</strong>
		  		<label id="display_data">${resinfo.resname }</label>
		  		<label id="input_data"><input id="resname" type="text" style="width:300px" value="${resinfo.resname }" /></label> </p>
		  <p><strong>学&nbsp;&nbsp;&nbsp;&nbsp;科：</strong><span  id="display_data" style="float:none">${resinfo.subjectname }</span>
              <span  id="input_data" style="float:none">
                  <select id="subject" name="subject">
                      <c:forEach items="${subjectEVList}" var="sev">
                              <option value="${sev.subjectid }" >${sev.subjectname }</option>
                      </c:forEach>
                  </select>
              </span></p>
          <p><strong>年&nbsp;&nbsp;&nbsp;&nbsp;级：</strong><span  id="display_data" style="float:none">${resinfo.gradename }</span>
              <span  id="input_data" style="float:none">
                  <select id="grade" name="grade">
                      <c:forEach items="${gradeEVList}" var="gev">
                          <option value="${gev.gradeid }" >${gev.gradename }</option>
                      </c:forEach>
                  </select>
              </span></p>
          <p><strong>资源类型：</strong><span  id="display_data" style="float:none">${resinfo.restypename}</span>
              <span  id="input_data" style="float:none">
                   <select name="restype" id="restype">
                       <c:forEach items="${resTypeEVList}" var="rtev">
                           <option value="${rtev.dictionaryvalue }" >${rtev.dictionaryname }</option>
                       </c:forEach>
                   </select>
              </span></p>
          <p><strong>文件类型：</strong><span  id="display_data" style="float:none">${resinfo.filetypename}</span>
              <span  id="input_data" style="float:none">
                    <select name="filetype" id="filetype">
                        <c:forEach items="${resFileTypeEVList}" var="ftev">
                            <option value="${ftev.dictionaryvalue }" >${ftev.dictionaryname }</option>
                        </c:forEach>
                    </select>
              </span></p>

		  <p><strong>关&nbsp;键&nbsp;字：</strong>
		  		<label id="display_data">${resinfo.reskeyword}</label>
		  		<label id="input_data"><input id="reskeyword" type="text" value="${resinfo.reskeyword}" /></label></p>
		  <p><strong>发布时间：</strong>${resinfo.ctimeString }</p>
		  <p><strong>修改时间：</strong>${resinfo.mtimeString }</p>
		  <p><strong>发&nbsp;布&nbsp;者：</strong>${resinfo.realname }</p>
		  <p><strong>简&nbsp;&nbsp;&nbsp;&nbsp;介：</strong>
		  		<label id="display_data">${resinfo.resintroduce}</label>
		  		<label id="input_data"><textarea id="resintroduce"  cols="60" rows="6" >${resinfo.resintroduce}</textarea></label></p>
		  <c:if test="${isreport==2}">
			<p style="float:right;" ><a href="javascript:void(0);" onclick="showModel('resource_report');">我要举报</a></p>
		</c:if>
		<c:if test="${isreport==1}">
		     <p style="float:right;" >已举报</p>
		</c:if>
		<c:if test="${ismine==1}">
		    <p style="float:right;" >
			<a href="javascript:void(0);" onclick="showInputData(true);">
				<img title="编辑" src="images/u27_normal.png"/></a>&nbsp;&nbsp;</p>
		</c:if>
		<span id="input_data">
		<p style="float:center;" ><input type="button" onclick="doUpdateRes('${resinfo.resid}');" value="保存" />&nbsp;&nbsp;
		<input type="button" onclick="showInputData(false);" value="取消" /></p></span>
		</div>
		<div class="jxpt_ziyuan_spL" style="background-color:#f2f2f2">
		  <% String lastname="";if(fname!=null&&fname.trim().length()>0)lastname=fname.substring(fname.lastIndexOf(".")); %>
		
		  <%if(filetype.trim().equals("video")){ %>
		    <div id="div_show" style="width:433;height:270;">
		  	<img src="images/video_gszh.jpg" id="img_video" height="270" width="433"/>
		  </div>
		  <p>		  
		  <span id="progress_"></span>大小：${resinfo.filesize }</p>
		  <script type="text/javascript">
		  		//加载视频进度，显示播放器
		  		videoConvertProgress("${resinfo.resid }","${resinfo.filename}"
		  					,"<%=UtilTool.gender_MD5(fname)%>" 
		  					,"","${resinfo.path}"
		  					,"<%=fileSystemIpPort%>"
		  					,"<%=fileSystemIpPort%>uploadfile/<%=UtilTool.gender_MD5(fname)+lastname.toLowerCase()%>.pre.jpg"
		  				);
		  	</script>
		  	<%}else if(filetype.trim().equals("mp3")){%>
            <div id="div_show" style="width:433;height:270;">
                <img src="images/video_gszh.jpg" id="img_video" height="270" width="433"/>
            </div>
            <p>
                <span id="progress_"></span>大小：${resinfo.filesize }</p>
            <script type="text/javascript">
                //加载音频，显示播放器
                loadSWFPlayer("<%=basePath %>uploadfile/<%=UtilTool.gender_MD5(fname)+lastname%>","div_show","");
            </script>
            <%}else{%>
		  	  <div id="div_show" style="width:433;height:300;">
		 		<img src="<%=fileSystemIpPort%>uploadfile/<%=UtilTool.gender_MD5(fname)+"_zhong.jpg"%>" id="show_img_zhong_jpg" style="display:none"/>
		 	 </div>
		  	<%}%>
		<%}else{%>	
		<p class="jxpt_ziyuan_text_title"><span> (<i id="cpn_1">0</i>人评)</span>
		<%if(fname.toLowerCase().indexOf(".doc")!=-1){%>
			<img src="images/ico02_130705.png" width="16" height="16" />
		<%}else if(fname.toLowerCase().indexOf(".ppt")!=-1){%>
			<img src="images/ico04_130705.png" width="16" height="16" />
		<%}else if(fname.toLowerCase().indexOf(".xls")!=-1){%>
			<img src="images/ico15_130705.png" width="16" height="16" />
		<%}else if(fname.toLowerCase().indexOf(".rar")!=-1||fname.toLowerCase().indexOf(".zip")!=-1){%>
			<img src="images/ico15_130705.png" width="16" height="16" />
		<%}else if(fname.toLowerCase().indexOf(".txt")!=-1){%>
		   <img src="images/ico08_130705.png" width="16" height="16" />
		<%}else if(fname.toLowerCase().indexOf(".mp3")!=-1){%>
            <img src="images/ico12_130705.png" width="16" height="16" />
		<%}else if(fname.toLowerCase().indexOf(".pdf")!=-1){%>		
		<img src="images/ico10_130705.png" width="16" height="16" />	
		<%}else{%>
		
		<%} %>${resinfo.filename}</p>
			<div class="jxpt_ziyuan_textL">
		  <p><strong>资源名称：</strong>
		  		<label id="display_data">${resinfo.resname }</label>
		  		<label id="input_data"><input id="resname" type="text" style="width:300px" value="${resinfo.resname }" /></label></p>
                <p><strong>学&nbsp;&nbsp;&nbsp;&nbsp;科：</strong><span  id="display_data" style="float:none">${resinfo.subjectname }</span>
              <span  id="input_data" style="float:none">
                  <select id="subject" name="subject">
                      <c:forEach items="${subjectEVList}" var="sev">
                          <option value="${sev.subjectid }" >${sev.subjectname }</option>
                      </c:forEach>
                  </select>
              </span></p>
                <p><strong>年&nbsp;&nbsp;&nbsp;&nbsp;级：</strong><span  id="display_data" style="float:none">${resinfo.gradename }</span>
              <span  id="input_data" style="float:none">
                  <select id="grade" name="grade">
                      <c:forEach items="${gradeEVList}" var="gev">
                          <option value="${gev.gradeid }" >${gev.gradename }</option>
                      </c:forEach>
                  </select>
              </span></p>
                <p><strong>资源类型：</strong><span  id="display_data" style="float:none">${resinfo.restypename}</span>
              <span  id="input_data" style="float:none">
                   <select name="restype" id="restype">
                       <c:forEach items="${resTypeEVList}" var="rtev">
                           <option value="${rtev.dictionaryvalue }" >${rtev.dictionaryname }</option>
                       </c:forEach>
                   </select>
              </span></p>
                <p><strong>文件类型：</strong><span  id="display_data" style="float:none">${resinfo.filetypename}</span>
              <span  id="input_data" style="float:none">
                    <select name="filetype" id="filetype">
                        <c:forEach items="${resFileTypeEVList}" var="ftev">
                            <option value="${ftev.dictionaryvalue }" >${ftev.dictionaryname }</option>
                        </c:forEach>
                    </select>
              </span></p>
		  <p><strong>关&nbsp;键&nbsp;字：</strong>
		  		<label id="display_data">${resinfo.reskeyword }</label>
		  		<label id="input_data"><input id="reskeyword" type="text" value="${resinfo.reskeyword }" /></label></p>
		  <p><strong>发布时间：</strong>${resinfo.ctimeString }</p>
		  <p><strong>修改时间：</strong>${resinfo.mtimeString }</p>
		  <p><strong>发&nbsp;布&nbsp;者：</strong>${resinfo.realname }</p>
		  <p><strong>简&nbsp;&nbsp;&nbsp;&nbsp;介：</strong>
		  		<label id="display_data">${resinfo.resintroduce }</label>
		  		<label id="input_data"><textarea id="resintroduce"  cols="60" rows="6" >${resinfo.resintroduce}</textarea></label></p>
		</div>
		<c:if test="${isreport==2}">
			<p style="float:right;" ><a href="javascript:void(0);" onclick="showModel('resource_report');">我要举报</a></p>
		</c:if>
		<c:if test="${isreport==1}">
		    <p style="float:right;" >已举报</p>
		</c:if>
		<c:if test="${ismine==1}">
		    <p style="float:right;" >
			<a href="javascript:void(0);" onclick="showInputData(true);">
				<img title="编辑" src="images/u27_normal.png"/></a>&nbsp;&nbsp;</p>
		</c:if>
		<span id="input_data">
		<p style="float:center;" ><input type="button" onclick="doUpdateRes('${resinfo.resid}');" value="保存" />&nbsp;&nbsp;
		<input type="button" onclick="showInputData(false);" value="取消" /></p></span>
		<%} %>
</c:if>
		<div class="jxpt_ziyuan_pl">
		<input type="hidden" id="commenttype" value="${commenttype }" />
		<input type="hidden" id="scoretype" value="${scoretype }" />
		<input type="hidden" id="objectid" value="${param.resid }" />
		  <table border="0" cellspacing="0" cellpadding="0">
		    <tr>
		    <th></th>
		    <td><div class="jxpt_ziyuan_pl_an">
		    <span id="dv_store">
		    <c:if test="${isstore==2}">
		    <a href="javascript:;" onClick="operateStore(1,'${param.resid }','dv_store')">收藏</a>
		    </c:if>
		     <c:if test="${isstore==1}">
		    <a href="javascript:;" onClick="operateStore(2,'${param.resid }','dv_store')">取消收藏</a>
		    </c:if>
		    </span>
		    <c:if test="${!empty downright&&downright==1}">
		     <a href="javascript:;" onclick="resourceDownLoadFile('${resinfo.resid }','<%=fname %>')">下载</a>
		    </c:if>
		    <c:if test="${isrecomend==2}">
		    <span id="recomend_status">
		     <a href="javascript:;" onclick="operateRecommend('${resinfo.resid }')">推荐</a></span>
		    </c:if>
		    <c:if test="${isrecomend==1}">
		     	已推荐
		    </c:if>
		    <c:if test="${ispraise==2}">
		    <span id="praise_status">
		     <a href="javascript:;" onclick="operatePraise('${resinfo.resid }')">赞一个</a></span>
		    </c:if>
		    <c:if test="${ispraise==1}">
		     	已点赞
		    </c:if>
		     </div></td>
		  </tr>
		  <div id="addComment">
		  </table>
		  <table border="0" cellspacing="0" cellpadding="0">
		  <tr>
		    <th valign="top">我来评论：</th>
		    <td><p><span class="f_right font-gray1">（共20条评论）</span><input type="checkbox" id="anonymous" name="anonymous" value="1">&nbsp;匿名</p>
		      <p><textarea id="commentcontext" name="commentcontext" class="public_input h90 w540"></textarea></p>
		      <p class="t_r p_t_10"><span class="font-gray1 p_r_20">还可以输入200个字</span><a href="javascript:addResourceComment();"  class="an_blue">发&nbsp;表</a></p></td>
		  </tr>
		  </table>
		  <h5></h5>
		  <div id="commentList">
		</div>
		<form action="/role/ajaxlist" id="page1form" name="page1form" method="post">
		<div id="page1address">
  		</div>
		</form>
		<div class="clear"></div>
		</div>
		</div>
		
		
	</div>
	</div>
		
   <%@include file="/util/foot.jsp" %>
   <script type="text/javascript">
  	<c:if test="${!empty erList}">
  		<c:forEach items="${erList}" var="ertmp">
  			$("span[id='sp_${ertmp.rootextendid}']").html('${ertmp.fullname}');
  		</c:forEach>
  	</c:if>
  </script>
  </body>
  </html> 
  
  <div style="display:none;background: #FFFFFF" id="resource_report">
  <p class="f_right"><a href="javascript:closeModel('resource_report');" title="关闭"><span class="public_windows_close"></span></a></p>
  <br/>
  <h3 align="center">举报资源</h3><br/>
  <table border="0" cellpadding="0" cellspacing="0" >
  <col class="w100" />
  <col class="w320" />
  <col class="w100" />
     <tr>
       <td align="right" valign="top">举报原因：</td>
       <td><textarea id="report_content" name="content" type="text" cols="60" rows="3"></textarea></td>
       <td><a id="addReportBtn" class="an_gray"  href="javascript:void(0);" onclick="resourceReport('${resinfo.resid }');">提交</a></td>
     </tr>
    </table>
    <br/>
</div>