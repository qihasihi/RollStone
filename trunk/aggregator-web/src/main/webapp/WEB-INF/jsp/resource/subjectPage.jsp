<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/util/common-jsp/common-zrxt.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml"><head>
<script type="text/javascript" src="js/resource/res_index.js"></script>
	<script type="text/javascript">
    var searchType=1;
	var subject=${sub};
	var restype="${type}";
	var grade=0;
	var orderBy="C_TIME";
	$(function(){
		getScoreTop();
		getLastUploads();
		getPublicUserTop();
		getDownloadTop();
		cutoverDiv('ulDownloadTop','ulLastUploads');
		cutoverDiv('ulScoreTop','ulViewClickTop');
		$("#li_sub_"+subject).attr("class","crumb");
		$("#li_rt_"+restype).attr("class","crumb");
		cutoverExcellentRes(1);

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
			page_size:20,				//当前页面显示的数量
			rectotal:0,				//一共多少
			pagetotal:1,
			operate_id:"resData"
		});

		p_exe_video=new PageControl({
			post_url:'resource?m=ajaxExcellentList',
			page_id:'page1',
			page_control_name:"p_exe_video",		//分页变量空间的对象名称
			post_form:document.pageVform,		//form
			gender_address_id:'pageVaddress',		//显示的区域
			http_free_operate_handler:beforeVideoAjaxList,		//执行查询前操作的内容
			http_operate_handler:afterVideoAjaxList,	//执行成功后返回方法
			new_page_html_mode:true,  //新分页html样式 ,默认为旧的
			return_type:'json',								//放回的值类型
			page_no:1,					//当前的页数
			page_size:8,				//当前页面显示的数量
			rectotal:0,				//一共多少
			pagetotal:1,
			operate_id:"excellentRes_Video"
		});

		p_exe_doc=new PageControl({
			post_url:'resource?m=ajaxExcellentList',
			page_id:'page2',
			page_control_name:"p_exe_doc",		//分页变量空间的对象名称
			post_form:document.pageDform,		//form
			gender_address_id:'pageDaddress',		//显示的区域
			http_free_operate_handler:beforeDocAjaxList,		//执行查询前操作的内容
			http_operate_handler:afterDocAjaxList,	//执行成功后返回方法
			new_page_html_mode:true,  //新分页html样式 ,默认为旧的
			return_type:'json',								//放回的值类型
			page_no:1,					//当前的页数
			page_size:24,				//当前页面显示的数量
			rectotal:0,				//一共多少
			pagetotal:1,
			operate_id:"excellentRes_Doc"
		});

		p_exe_other=new PageControl({
			post_url:'resource?m=ajaxExcellentList',
			page_id:'page2',
			page_control_name:"p_exe_other",		//分页变量空间的对象名称
			post_form:document.pageOform,		//form
			gender_address_id:'pageOaddress',		//显示的区域
			http_free_operate_handler:beforeOtherAjaxList,		//执行查询前操作的内容
			http_operate_handler:afterOtherAjaxList,	//执行成功后返回方法
			new_page_html_mode:true,  //新分页html样式 ,默认为旧的
			return_type:'json',								//放回的值类型
			page_no:1,					//当前的页数
			page_size:24,				//当前页面显示的数量
			rectotal:0,				//一共多少
			pagetotal:1,
			operate_id:"excellentRes_Other"
		});

		pageGo('p1');
		pageGo('p_exe_video');
		pageGo('p_exe_doc');
		pageGo('p_exe_other');
	});

	function beforeAjaxList(p){
		var param = {};
		p.setPageOrderBy(orderBy);
        if(subject!=0)
		    param.subject = subject;
        if(grade!=0)
            param.grade = grade;
        if(restype!='0')
            param.restype = restype;
		p.setPostParams(param);
	}

	function afterAjaxList(rps){
		var html = "";
		if(rps!=null&&rps.presult.list.length>0){
			$.each(rps.objList,function(idx,itm){
				html+="<li>";
				html+="<s>"+ itm.ctimeString.substring(0,10) +"</s>";
				html+="<b><a href='resource?m=toteacherreslist&userid="+itm.userref+"' target='_blank'>"+itm.username+"</a></b>";
				html+="<a target='_blank' href='resource?m=todetail&resid="+itm.resid+"'>"+ itm.resname +"</a>";
				html+="</li>";
			});
		}else{
			html+="<li>暂无数据！</li>";
		}
		p1.setPageSize(rps.presult.pageSize);
		p1.setPageNo(rps.presult.pageNo);
		p1.setRectotal(rps.presult.recTotal);
		p1.setPagetotal(rps.presult.pageTotal);
		p1.Refresh();
		$("#resData").html(html);
	}
	function getResourceListBySub(evid){
		subject=evid;
		$("#li_sub_"+evid).siblings().attr("class","");
		$("#li_sub_"+evid).attr("class","crumb");

		pageGo('p1');
		pageGo('p_exe_video');
		pageGo('p_exe_doc');
		pageGo('p_exe_other');
	}
    function getResourceListByType(evid){
        restype=evid;
        $("#li_rt_"+evid).siblings().attr("class","");
        $("#li_rt_"+evid).attr("class","crumb");
        pageGo('p1');
    }
    function getResourceListByGrade(evid){
        grade=evid;
        $("#li_grade_"+evid).siblings().attr("class","");
        $("#li_grade_"+evid).attr("class","crumb");
        pageGo('p1');
    }
	function showAddDiv(div){
		showModel(div,'fade',false);
		clearForm(document.addform);
		$("tr").filter(function(){
			return this.id.Trim().indexOf('atr_student')!=-1;
		}).hide('fast');
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
  
  <div class="public_lable_layout">
  
    <ul class="public_lable_list font-white">
    <li id="li_sub_0"><a href="javascript:getResourceListBySub(0);"><span id="sub_0">全部</span></a></li>
      <c:forEach items="${subjectEVList}" var="sev">
		<li id="li_sub_${sev.subjectid }"><a href="javascript:getResourceListBySub('${sev.subjectid }');"><span id="sub_${sev.subjectid }">${sev.subjectname }</span></a></li>
	</c:forEach>
    </ul>
  </div>
  <div class="public_lable_ej">
    <ul>
    <li id="li_rt_0"><a href="javascript:getResourceListByType('0');"><span id="rt_0">全部</span></a></li>
      <c:forEach items="${resTypeEVList}" var="rtev">
		<li id="li_rt_${rtev.dictionaryvalue }"><a href="javascript:getResourceListByType('${rtev.dictionaryvalue }');"><span id="rt_${rtev.dictionaryvalue }">${rtev.dictionaryname }</span></a></li>
	</c:forEach>
    </ul>
  </div>
  <div class="public_lable_sj">
    <ul>
    <li id="li_grade_0" class="crumb">&nbsp;<a href="javascript:getResourceListByGrade(0);"><span id="grade_0">全部</span></a></li>
     <c:forEach items="${gradeEVList}" var="gev">
		<li id="li_grade_${gev.gradeid }">&nbsp;<a href="javascript:getResourceListByGrade(${gev.gradeid });"><span id="grade_${gev.gradeid }">${gev.gradename }</span></a></li>
	</c:forEach>
    </ul>
  </div>

<div class="jxpt_ziyuan_layoutR">
    <p class="p_t_10">
    <a href="resource?m=toadd" target="_blank"><img src="images/an03_130423.png" title="上传资源" width="96" height="35" class="m_lr_5"/></a><a href="resource?m=tomyreslist" target="_blank"><img src="images/an24_130705.png" title="我的资源" width="96" height="35" class="m_lr_5"/></a></p>
    <p class="p_lr_15 m_t_10">资源总数：<span class="font-red">${resTotalNum}</span></p>
    <p class="p_lr_15 m_t_10">更新（日/周/月）：<span class="font-red">${dayResNum}/${weekResNum}/${monthResNum}</span></p>
    <p class="jxpt_ziyuan_gonggao m_t_15">资源公告
    	<span style="float:right;color: #333333;font-weight: normal;">
    	<c:if test="${!empty isadminFlag&&isadminFlag==0 }">
    	<!-- 管理员 -->
    		<a href="javascript:showAddDiv('addDiv')">添加</a>&nbsp;&nbsp;
    	</c:if>
    	<a href="notice?m=tomore&noticetype=3" target="_blank">更多</a>&nbsp;&nbsp;&nbsp;</span>
    </p>
      <div class="p_lr_15">
       <c:if test="${!empty resourceNotice}">
      	<c:forEach items="${resourceNotice}" var="inte">      		
	      		<li>
	      		<c:if test="${!empty inte.titlelink}">
	      			<a href="${inte.titlelink}" target="_blank">${inte.noticetitle15String}</a>1
	      		</c:if>
	      		<c:if test="${empty inte.titlelink}">
	      			<a href="notice?m=detail&ref=${inte.ref }" target="_blank">${inte.noticetitle15String}</a>
	      		</c:if>
      			</li>    		
      	</c:forEach>      
      </c:if>       
        <c:if test="${empty resourceNotice}">
        	<p class="p_t_10">暂无!</p>
        </c:if>      
      </div>
    <div class="jxpt_ziyuan_lable_layout">
        <ul>
          <li id="li_ulScoreTop" class="crumb"><a href="javascript:cutoverDiv('ulScoreTop','ulViewClickTop');getScoreTop();">评分排行榜</a></li>
          <li id="li_ulViewClickTop"><a href="javascript:cutoverDiv('ulViewClickTop','ulScoreTop');getViewClickTop();">浏览排行榜</a></li>
        </ul>
    </div>
      <ul id="ulScoreTop" class="p_lr_15">
      </ul>
      <ul id="ulViewClickTop" class="p_lr_15">
      </ul>
    <div class="jxpt_ziyuan_lable_layout">
        <ul>
          <li id="li_ulDownloadTop"><a href="javascript:cutoverDiv('ulDownloadTop','ulLastUploads');getDownloadTop();">最热下载</a></li>
          <li id="li_ulLastUploads" class="crumb"><a href="javascript:cutoverDiv('ulLastUploads','ulDownloadTop');getLastUploads();">最新上传</a></li>
        </ul>
    </div>
    <ul id="ulLastUploads" class="p_lr_15">
	</ul>
	<ul id="ulDownloadTop" class="p_lr_15">
	</ul>
      
      <ul class="p_lr_15">
      </ul>
    <div class="jxpt_ziyuan_lable_layout">
        <ul>
          <li class="crumb"><a href="javascript:getPublicUserTop();">上传最多用户</a></li>
        </ul>
    </div>
      <ul id="ulPublicUserTop"  class="p_lr_15">
      </ul>
  </div>

<div class="jxpt_ziyuan_layoutL">
  <div class="jxpt_ziyuan_xueke">
       <ul id="resData">
        </ul>
        <br/>
		<a href="javascript:void(0);" onclick="orderBy='CLICKS';pageGo('p1');" >
			<img style="float:right;" src="images/u67_normal.png" title="按浏览排行查看" ></a>
		<a href="javascript:void(0);" onclick="orderBy='C_TIME';pageGo('p1');" >
			<img  style="float:right;" src="images/u73_normal.png"  title="按更新时间查看" ></a>
		
  </div>
  <div class="t_r m_r_15 p_tb_10">
		<form action="/role/ajaxlist" id="page1form" name="page1form" method="post">
  			<p align="center" id="page1address"></p>
  		</form> 
 </div>

	<div class="jxpt_ziyuan_yxzy">
	   <p>优秀资源</p>
	   <ul class="jxpt_ziyuan_yxzy_lable">
	    <li id="exe_video" class="crumb"><a href="javascript:cutoverExcellentRes(1)">视频类</a></li>
	    <li id="exe_doc"><a href="javascript:cutoverExcellentRes(2)">文档类</a></li>
	    <li id="exe_other"><a href="javascript:cutoverExcellentRes(3)">其他</a></li>
	    </ul>
	    
	    <div id="excellentRes_Video_Div" class="jxpt_ziyuan_yxzy_sp">
	       <ul id="excellentRes_Video">
	       </ul>
	    </div>
	 
	    <div id="excellentRes_Doc_Div" class=" jxpt_ziyuan_yxzy_wb">
	       <ul id="excellentRes_Doc">
	       </ul>
	    </div>
	     
	    <div id="excellentRes_Other_Div" class=" jxpt_ziyuan_yxzy_wb">
	       <ul id="excellentRes_Other">
	       </ul>
	     </div>
	     
	    <form action="" id="pageVform" name="pageVform" method="post">
		<div id="pageVaddress">
	  	</div>   
	  	</form>
	 	<form action="" id="pageDform" name="pageDform" method="post">
		<div id="pageDaddress"  style="display:none;">
	 	</div> 
	 	</form>  
	 	<form action="" id="pageOform" name="pageOform" method="post">
		<div id="pageOaddress"  style="display:none;">
	 	</div> 
	 	</form> 
    </div>
    
</div>
</div>
<%@include file="/util/foot.jsp" %>


<div class="public_windows_layout w710 h490" style="display:none;" id="addDiv">
  <p class="f_right"><a href="javascript:closeModel('addDiv','hide');pageGo('p1')" title="关闭"><span class="public_windows_close"></span></a></p>
  <p class="t_c font14 font_strong p_t_10">添加通知公示</p>
  <form id="addform" name="addform">
  <table border="0" cellpadding="0" cellspacing="0"  class="public_tab1 m_a_10">
    <col class="w80"/>
    <col class="w560"/>
    <tr>
      <td>类&nbsp;&nbsp;&nbsp;&nbsp;型：</td>
      <td><select id="atype" name="atype"
								onchange="atypeSelect('atdNoticeUsefullife')">
								<option value="3">资源公告</option>								
								</select>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="checkbox" id="aisTop" name="aisTop" value="0" />
							是否置顶&nbsp;&nbsp;						
     </td>
    </tr>
    <tr id="atdNoticeUsefullife">
					</tr>
    <tr>
						<td>
							标 &nbsp;&nbsp;题:
						</td>
						<td>
							<input type="text" name="atitle"  id="atitle" class="public_input w200" />
						</td>
					</tr>
    <tr>
      <td>标题链接：</td>
      <td><input type="checkbox" onclick="aisTitleLinkEditor(this)" id="atitleLink" name="atitleLink" value="0"/></td>
    </tr>
    <tr>
      <td>内&nbsp;&nbsp;&nbsp;&nbsp;容：</td>
      <td id="atdContent"><textarea style="width:480px;height:100px" id="acontent" name="acontent"/></textarea>
    </tr>
    <tr>
      <td>发送对象：</td>
      <td><ul class="public_list2">
        <c:if test="${!empty role}">
								<c:forEach items="${role}" var="itm">
									<li><input onclick="arolechecked('${itm.roleid}','${itm.rolename}')" type="checkbox" value="${itm.roleid }" id="aroleId"
									name="aroleId" />${itm.rolename }</li>
								</c:forEach>
							</c:if>
      </ul></td>
    </tr>
    <tr id="atr_student_grade">
      <td>年&nbsp;&nbsp;&nbsp;&nbsp;级：</td>
      <td><ul class="public_list2">
        <c:if test="${empty gradeList }">
   							<li>没有发现年级信息!</li>
   						</c:if>
   						<c:if test="${!empty gradeList }">
   							<c:forEach var="g" items="${gradeList }" varStatus="status"> 
   								<li><input type="checkbox"  value="${g.gradevalue }" name="ack_grade"/>${g.gradename }</li>
   							</c:forEach>
   						</c:if>
      </ul></td>
    </tr>
  </table>
  </form>
  <p class="t_c p_t_10"><a class="an_gray"  href="javascript:onSubmitdoAdd()">发布</a><a class="an_gray"  href="javascript:clearForm(document.addform)">重置</a></p>
</div>
</body>
</html>
