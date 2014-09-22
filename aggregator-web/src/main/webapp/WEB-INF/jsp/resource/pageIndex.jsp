<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/util/common-jsp/common-zrxt.jsp" %>

<html>
<head>
<script type="text/javascript" src="js/resource/res_index.js"></script>
<script type="text/javascript">
var p1,p2;
var subject="${def_sub}";
var rts=new Array('default'<c:forEach items="${resTypeEVList}" var="rtev">,'${rtev.dictionaryvalue }'</c:forEach>);
var audiosuffix="${audiosuffix}";
var videosuffix="${videosuffix}";
var imagesuffix="${imagesuffix}";
$(function(){
    <c:if test="${!empty isFirstInto&&isFirstInto==1}">
        showModel("dv_firstInto");
    </c:if>


	p_exe_video=new PageControl({
		post_url:'resource?m=ajaxExcellentList&isrecommend=1',
		page_id:'page1',
		page_control_name:"p_exe_video",		//分页变量空间的对象名称
		post_form:document.pageVform,		//form
		gender_address_id:'pageVaddress',		//显示的区域
		http_free_operate_handler:beforeVideoAjaxList,		//执行查询前操作的内容
		http_operate_handler:afterVideoAjaxList,	//执行成功后返回方法
		return_type:'json',								//放回的值类型
		page_no:1,					//当前的页数
		page_size:8,				//当前页面显示的数量
		rectotal:0,				//一共多少
		pagetotal:1,
		operate_id:"excellentRes_Video"
	});
	p_exe_doc=new PageControl({
		post_url:'resource?m=ajaxExcellentList&isrecommend=1',
		page_id:'page2',
		page_control_name:"p_exe_doc",		//分页变量空间的对象名称
		post_form:document.pageDform,		//form
		gender_address_id:'pageDaddress',		//显示的区域
		http_free_operate_handler:beforeDocAjaxList,		//执行查询前操作的内容
		http_operate_handler:afterDocAjaxList,	//执行成功后返回方法
		return_type:'json',								//放回的值类型
		page_no:1,					//当前的页数
		page_size:8,				//当前页面显示的数量
		rectotal:0,				//一共多少
		pagetotal:1,
		operate_id:"excellentRes_Doc"
	});

    p_myinfo=new PageControl({
        post_url:'resource?m=ajx_RsMyInfoCloud',
        page_id:'p_myinfo',
        page_control_name:"p_myinfo",		//分页变量空间的对象名称
        post_form:document.fm_dt_form,		//form
        gender_address_id:'dv_dt_pageress',		//显示的区域
      //  http_free_operate_handler:beforDtMethod,		//执行查询前操作的内容
        http_operate_handler:myInfoAjaxList,	//执行成功后返回方法
        return_type:'json',								//放回的值类型
        page_no:1,					//当前的页数
        page_size:8,				//当前页面显示的数量
        rectotal:0,				//一共多少
        pagetotal:1,
        operate_id:"ul_dt_data"
    });

    p_omyinfo=new PageControl({
        post_url:'resource?m=ajx_RsMyInfoCloudOther',
        page_id:'p_omyinfo',
        page_control_name:"p_omyinfo",		//分页变量空间的对象名称
        post_form:document.fm_dto_form,		//form
        gender_address_id:'dv_dto_pageress',		//显示的区域
        //  http_free_operate_handler:beforDtMethod,		//执行查询前操作的内容
        http_operate_handler:myInfoAjaxList,	//执行成功后返回方法
        return_type:'json',								//放回的值类型
        page_no:1,					//当前的页数
        page_size:8,				//当前页面显示的数量
        rectotal:0,				//一共多少
        pagetotal:1,
        operate_id:"ul_dt_data"
    });



    // getViewClickTop('week');
	//getSubjectResource(subject);
	//getScoreTop();
	//getLastUploads();
	//getPublicUserTop();
	//getDownloadTop();
	//cutoverDiv('ulDownloadTop','ulLastUploads');
	//cutoverDiv('ulScoreTop','ulViewClickTop');
	cutoverExcellentRes(1);
    pageGo("p_myinfo");
    pageGo('p_exe_video');
    pageGo('p_exe_doc');

	$('#acontent')
	.xheditor(
			{
				tool : "mfull",
				html5Upload:false,		//是否应用 html5上传，如果应用，则不允许上传大文件
				upLinkUrl : "{editorRoot}upload.jsp",
				upLinkExt : "zip,rar,txt,doc,ppt,pptx,docx,xls,xlsx,ZIP,RAR,TXT,DOC,PPT,PPTX,DOCX,XLS,XLSX",
				upImgUrl : "{editorRoot}upload.jsp",
				upImgExt : "jpg,jpeg,gif,png,JPG,JPEG,GIF,PNG",
				upFlashUrl : "{editorRoot}upload.jsp",
				upFlashExt : "swf,SWF"
			//	,upMediaUrl : "{editorRoot}upload.jsp",
			//	upMediaExt : "avi,mp3,wma,wmv,mp4,mov,mpeg,mpg,flv,AVI,MP4,MOV,FLV,MPEG,MPG,MP3,WMA,WMV"// ,
			// onUpload:insertUpload //指定回调涵数
			});
});

function myInfoAjaxList(rps){
    var h='';
    if(rps.type=="success"){
        if(rps.objList.length>0){
            $.each(rps.objList,function(idx,itm){
                var sMsg=itm.dataMsg;
                if(typeof(itm.realName)!="undefined"&&itm.realName.Trim().length>0)
                    sMsg=itm.otherDataMsg;
               h+='<li><b>'+itm.ctimeString+'</b>';
                if(itm.targetid>0)
                h+='<span class="ico64">';
                else
                    h+='<span class="ico63">';
               h+='</span>&nbsp;'+sMsg+'</li>';
            });
        }
        p_myinfo.setPageSize(rps.presult.pageSize);
        p_myinfo.setPageNo(rps.presult.pageNo);
        p_myinfo.setRectotal(rps.presult.recTotal);
        p_myinfo.setPagetotal(rps.presult.pageTotal);
        p_myinfo.Refresh();
    }else{
        h+='<li>'+rps.msg+'</li>';
    }
    $("#ul_dt_data").html(h);
}

</script>
</head>

<body>
<%@include file="/util/head.jsp"%>

<%@include file="/util/nav-base.jsp"%>
<div class="content2">
    <div class="zyxt_search public_input">
       <%/*
        <p class="f_right"><span class="ico61" title="资源更新"></span><span class="font-red">16339</span>&nbsp;<span class="ico65" title="校内上传"></span>83320&nbsp;<span class="ico62" title="云端上传"></span>22320&nbsp;<span class="ico66" title="资源积分"></span>63230</p>
       */%>
        <p><a href="resource?m=toadd" target="_blank" class="an_small">上传资源</a>&nbsp;&nbsp;<a href="resource?m=toMyResList" target="_blank" class="an_small">资源管理</a>&nbsp;&nbsp;
            <select id="searchType" name="searchType" class="w80">
                <option value="1">资源</option>
                <option value="2">教师</option>
                <option value="3">知识点</option>
            </select>
            <input id="searchValue" name="searchValue" type="text" class="w240"/>&nbsp;
            <a href="javascript:doSearch()" class="an_search" title="搜索"></a>&nbsp;&nbsp;&nbsp;&nbsp;
            <%--<a href="resource?m=toAllResPage" target="_blank" class="font-darkblue">查看全部资源</a>--%>
            <a href="resource?m=list" target="_blank" class="font-darkblue">高级搜索</a>
            </p>
    </div>

    <div class="zyxt_homeR font-black">

        <h1>我的资源排行榜</h1>
        <ul>
             <li><span class="f_right">浏览量</span>资源名称</li>
            <c:if test="${!empty myResRank}">
                <c:forEach items="${myResRank}" var="mrRk">
                <li>
                    <span class="f_right">${mrRk.clicks}</span>
                    <a  onmouseover="document.getElementById('p_num_${mrRk.resid}').style.display='block'"
                        onmouseout="document.getElementById('p_num_${mrRk.resid}').style.display='none'"
                      href="resource?m=todetail&resid=${mrRk.resid}&isfromrank=1" target="_blank">${mrRk.resname13word}</a>
                    <p onmouseover="document.getElementById('p_num_${mrRk.resid}').style.display='none'" id="p_num_${mrRk.resid}" class="number" style="display:none;position: absolute;">
                        浏览/赞/推荐/收藏/下载<br/>
                         ${mrRk.clicks} / ${mrRk.praisenum} / ${mrRk.recomendnum} / ${mrRk.storenum} / ${mrRk.downloadnum}</p>
                </li>
                </c:forEach>
                <li class="t_r"><a href="resource?m=toMyResRankMore" target="_blank" title="more" class="ico_more"></a></li>
            </c:if>
        </ul>

        <h1 style="display:none">共享排行榜</h1>
        <ul id="ul_sharerank  style="display:none">

        </ul>
        <div id="ul_share_rank_li_1" style="display:none"><!--个人校内排行榜-->
            <li><a href="javascript:;" onclick="document.getElementById('ul_sharerank').innerHTML=document.getElementById('ul_share_rank_li_1').innerHTML;" class="font-blue">个人校内</a>&nbsp;|&nbsp;
                <a a href="javascript:;" onclick="document.getElementById('ul_sharerank').innerHTML=document.getElementById('ul_share_rank_li_2').innerHTML;">个人云端</a>&nbsp;|&nbsp;
                <a  href="javascript:;" onclick="document.getElementById('ul_sharerank').innerHTML=document.getElementById('ul_share_rank_li_3').innerHTML;">学校</a></li>
            <c:if test="${!empty umtsList}">
                <c:forEach items="${umtsList}" var="umts">
                    <li><span class="f_right">${umts.totalScore}</span>${umts.realname}</li>
                </c:forEach>
                <li class="t_r"><a href="#" target="_blank" title="more" class="ico_more"></a></li>
            </c:if>
        </div>
        <div id="ul_share_rank_li_2"  style="display:none"><!--个人云端排行榜-->
            <li><a href="javascript:;" onclick="document.getElementById('ul_sharerank').innerHTML=document.getElementById('ul_share_rank_li_1').innerHTML;">个人校内</a>&nbsp;|&nbsp;
                <a a href="javascript:;" onclick="document.getElementById('ul_sharerank').innerHTML=document.getElementById('ul_share_rank_li_2').innerHTML;"  class="font-blue">个人云端</a>&nbsp;|&nbsp;
                <a  href="javascript:;" onclick="document.getElementById('ul_sharerank').innerHTML=document.getElementById('ul_share_rank_li_3').innerHTML;">学校</a></li>
            <!--数据-->
            <c:if test="${!empty usRankList}">
                <c:forEach items="${usRankList}" var="usRank">
                    <li><span class="f_right">${usRank.score}</span>${usRank.userrealname}</li>
                </c:forEach>
                <li class="t_r"><a href="#" target="_blank" title="more" class="ico_more"></a></li>
            </c:if>
        </div>
        <div id="ul_share_rank_li_3"  style="display:none"><!--学校排行榜-->
            <li><a href="javascript:;" onclick="document.getElementById('ul_sharerank').innerHTML=document.getElementById('ul_share_rank_li_1').innerHTML;">个人校内</a>&nbsp;|&nbsp;
                <a a href="javascript:;" onclick="document.getElementById('ul_sharerank').innerHTML=document.getElementById('ul_share_rank_li_2').innerHTML;">个人云端</a>&nbsp;|&nbsp;
                <a  href="javascript:;" onclick="document.getElementById('ul_sharerank').innerHTML=document.getElementById('ul_share_rank_li_3').innerHTML;" class="font-blue">学校</a></li>
            <!--数据-->
            <c:if test="${!empty ssRankList}">
                <c:forEach items="${ssRankList}" var="ssRank">
                    <li><span class="f_right">${ssRank.score}</span>${ssRank.schoolname}</li>
                </c:forEach>
                <li class="t_r"><a href="#" target="_blank" title="more" class="ico_more"></a></li>
            </c:if>
        </div>
              <h1  style="display:none">最热资源排行榜</h1>
        <ul  style="display:none" id="ulViewClickTop">





        </ul>
        <div id="dv_li_hot_view_1"  style="display:none"> <!--最热浏览（周）-->
            <li><a href="javascript:;" onclick="document.getElementById('ulViewClickTop').innerHTML=document.getElementById('dv_li_hot_view_1').innerHTML;"class="font-blue">周浏览</a>&nbsp;
                |&nbsp;<a  href="javascript:;" onclick="document.getElementById('ulViewClickTop').innerHTML=document.getElementById('dv_li_hot_view_2').innerHTML;">月浏览</a>&nbsp;|&nbsp;
                <a  href="javascript:;" onclick="document.getElementById('ulViewClickTop').innerHTML=document.getElementById('dv_li_hot_view_3').innerHTML;">最热下载</a></li>
            <!--数据-->
            <c:if test="${!empty weekRsList}">
                <c:forEach items="${weekRsList}" var="wkrk">
                    <li><span class="f_right">${wkrk.VIEWCOUNT}</span>
                        <a target="_blank" href="resource?m=todetail&resid=${wkrk.RES_ID}&isfromrank=1" >
                            ${wkrk.RES_NAME}
                    </a></li>
                </c:forEach>
                <li class="t_r"><a href="resource?m=toHotClicksResTypeToMore&type=week" target="_blank" title="more" class="ico_more"></a></li>
            </c:if>
        </div>
        <script type="text/javascript">
            $("#dv_li_hot_view_1 li:gt(0) a").each(function(idx,itm){
                var htm=this.innerHTML.Trim();
                if(htm.length>13)
                    htm=htm.substring(0,13)+"...";
                $(this).html(htm);
            });
        </script>
        <div id="dv_li_hot_view_2" style="display:none" > <!--最热浏览（月）-->
            <li><a href="javascript:;" onclick="document.getElementById('ulViewClickTop').innerHTML=document.getElementById('dv_li_hot_view_1').innerHTML;">周浏览</a>&nbsp;
                |&nbsp;<a  href="javascript:;" onclick="document.getElementById('ulViewClickTop').innerHTML=document.getElementById('dv_li_hot_view_2').innerHTML;" class="font-blue">月浏览</a>&nbsp;|&nbsp;
                <a  href="javascript:;" onclick="document.getElementById('ulViewClickTop').innerHTML=document.getElementById('dv_li_hot_view_3').innerHTML;">最热下载</a></li>
            </li>
            <!--数据-->
            <c:if test="${!empty monthRsList}">
                <c:forEach items="${monthRsList}" var="mrsrk">
                    <li><span class="f_right">${mrsrk.VIEWCOUNT}</span><a target="_blank"  href="resource?m=todetail&resid=${mrsrk.RES_ID}&isfromrank=1" >
                            ${mrsrk.RES_NAME}
                    </a></li>
                </c:forEach>
                <li class="t_r"><a href="resource?m=toHotClicksResTypeToMore&type=month" target="_blank" title="more" class="ico_more"></a></li>
            </c:if>
        </div>
        <script type="text/javascript">
            $("#dv_li_hot_view_2 li:gt(0) a").each(function(idx,itm){
                var htm=this.innerHTML.Trim();
                if(htm.length>13)
                    htm=htm.substring(0,13)+"...";
                $(this).html(htm);
            });
        </script>
        <div id="dv_li_hot_view_3" style="display:none"> <!--最热下载（月）-->
            <li><a href="javascript:;" onclick="document.getElementById('ulViewClickTop').innerHTML=document.getElementById('dv_li_hot_view_1').innerHTML;">周浏览</a>&nbsp;
                |&nbsp;<a  href="javascript:;" onclick="document.getElementById('ulViewClickTop').innerHTML=document.getElementById('dv_li_hot_view_2').innerHTML;">月浏览</a>&nbsp;|&nbsp;
                <a  href="javascript:;" onclick="document.getElementById('ulViewClickTop').innerHTML=document.getElementById('dv_li_hot_view_3').innerHTML;"  class="font-blue">最热下载</a></li>
            <!--数据-->
            <c:if test="${!empty downRsList}">
                <c:forEach items="${downRsList}" var="drs">
                    <li><span class="f_right">${drs.DOWNLOADNUM}</span><a target="_blank"  href="resource?m=todetail&resid=${drs.RES_ID}&isfromrank=1" >
                            ${drs.RES_NAME}
                    </a></li>
                </c:forEach>
                <li class="t_r"><a href="#" target="_blank" title="more" class="ico_more"></a></li>
            </c:if>
            </li>
        </div>
        <script type="text/javascript">
            $("#dv_li_hot_view_3 li:gt(0) a").each(function(idx,itm){
                var htm=this.innerHTML.Trim();
                if(htm.length>13)
                    htm=htm.substring(0,13)+"...";
                $(this).html(htm);
            });
        </script>
        <script type="text/javascript">
            $("#ul_sharerank").html($("#ul_share_rank_li_1").html());
            $("#ulViewClickTop").html($("#dv_li_hot_view_1").html());
        </script>
    </div>
    <div class="zyxt_homeL">
          <div class="zyxt_home_dongtaiT">
            <ul id="ul_dt">
                <li class="crumb" id="li_my"><a href="javascript:;" onclick="$('#ul_dt li').removeClass('crumb');$('#li_my').addClass('crumb');dv_dto_s_page.style.display='none';dv_dt_s_page.style.display='block';pageGo('p_myinfo');">我的动态</a></li>
                <li id="li_other"><a href="javascript:;" onclick="$('#ul_dt li').removeClass('crumb');$('#li_other').addClass('crumb');dv_dt_s_page.style.display='none';dv_dto_s_page.style.display='block';pageGo('p_omyinfo')" >他人动态</a></li>
            </ul>
        </div>
     <div class="zyxt_home_dongtaiB">
         <ul id="ul_dt_data">
             <%/* <li><b>2013年11月13日</b><span class="ico64"></span><a href="1" target="_blank" class="font-blue"><span class="ico_txt1"></span>雾里看花雾里看花雾里看花花雾里看花雾里看花</a>...&nbsp;&nbsp;审核通过&nbsp;+5分</li>
                <li><b>2013年11月13日</b><span class="ico63"></span><a href="1" target="_blank" class="font-blue"><span class="ico_rtf1"></span>雾里看花雾里看花雾里看花雾里看花雾里看花雾里看花雾里看花</a>...&nbsp;&nbsp;被选入标准资源库&nbsp;+5分</li>
                <li><b>2013年11月13日</b><span class="ico64"></span><a href="1" target="_blank" class="font-blue"><span class="ico_txt1"></span>雾里看花雾里看花雾里看里看花雾里看花雾里看花</a>...&nbsp;&nbsp;未通过审核！</li>
                <li><b>2013年11月13日</b><span class="ico63"></span><a href="1" target="_blank" class="font-blue"><span class="ico_ppt1"></span>雾里看花雾里看花雾里花雾里看花雾里看花雾里看花</a>...&nbsp;&nbsp;被管理员删除！ </li>
                <li><b>2013年11月13日</b><span class="ico67"></span><a href="1" target="_blank" class="font-blue">李静老师</a>&nbsp;评论了你的<a href="1" target="_blank" class="font-blue"><span class="ico_txt1"></span>雾里看花雾里看花雾里看花</a></li>
                <li><b>2013年11月13日</b><span class="ico68"></span>恭喜你！成功升级为<a href="1" target="_blank" class="font-blue">雾里看花雾里</a></li>
                <li><b>2013年11月13日</b><span class="ico63"></span><a href="1" target="_blank" class="font-blue"><span class="ico_pdf1"></span>雾里看花雾里看花雾里看花雾里看花雾里看花雾里看花雾里看花</a>...&nbsp;&nbsp;为恶意文件&nbsp;被删除&nbsp;+5分</li>
                <li><b>2013年11月13日</b><span class="ico63"></span><a href="1" target="_blank" class="font-blue"><span class="ico_doc1"></span>看花雾里看花雾里看花雾里看花雾里看花雾里看花</a>...&nbsp;&nbsp;审核通过&nbsp;+5分</li>
        */%>
                  </ul>
         <div class="nextpage" id="dv_dt_s_page">
             <span><a href="javascript:;" onclick="pageFirst('p_myinfo')"><b class="first"></b></a>
             </span><span><a href="javascript:;"  onclick="pagePre('p_myinfo')"><b class="before"></b></a></span>
             <span><a href="javascript:;" onclick="pageNext('p_myinfo')"><b class="after"></b></a></span>
             <span><a  href="javascript:;"  onclick="pageLast('p_myinfo')"><b class="last"></b></a></span>
         </div>
         <div class="nextpage" id="dv_dto_s_page" style="display:none">
             <span><a href="javascript:;" onclick="pageFirst('p_omyinfo')"><b class="first"></b></a>
             </span><span><a href="javascript:;"  onclick="pagePre('p_omyinfo')"><b class="before"></b></a></span>
             <span><a href="javascript:;" onclick="pageNext('p_omyinfo')"><b class="after"></b></a></span>
             <span><a  href="javascript:;"  onclick="pageLast('p_omyinfo')"><b class="last"></b></a></span>
         </div>
         <form name="fm_dt_form" id="fm_dt_form" method="post">
             <div class="nextpage" id="dv_dt_pageress" style="display:none"></div>
         </form>
         <form name="fm_dto_form" id="fm_dto_form" method="post">
             <div class="nextpage" id="dv_dto_pageress" style="display:none"></div>
         </form>
     </div>


        <p class="zyxt_home_tjzy"><span class="ico14"></span>推荐资源</p>
        <div class="subpage_lm">
            <ul>
                <li id="exe_video" class="crumb"><a href="javascript:cutoverExcellentRes(1)">视频类</a></li>
                <li id="exe_doc"><a href="javascript:cutoverExcellentRes(2)">文档类</a></li>
            </ul>
        </div>

        <ul id="excellentRes_Video" class="sp font-black"></ul>

        <ul id="excellentRes_Doc" class="wb font-black"  style="display:none;"></ul>

        <form action="" id="pageVform" name="pageVform" method="post">
            <div id="pageVaddress"  class="nextpage">
            </div>
        </form>

        <form action="" id="pageDform" name="pageDform" method="post">
            <div id="pageDaddress"  class="nextpage" style="display:none;">
            </div>
        </form>
    </div>
    </div>
</div>
 <%@include file="/util/foot.jsp" %>

<div class="public_windows" id="dv_firstInto"  style="display:none;>
<h3><a href="javascript:;" onclick="closeModel('dv_firstInto')" title="关闭"></a>欢迎进入资源系统！</h3>
<p class="t_c p_tb_10">你可以上传、管理、共享自我资源，<br>也可查看、使用他人共享或网校配送的资源，快来试试吧！</p>
<p class="t_c p_tb_10"><a  href="resource?m=toadd" onclick="closeModel('dv_firstInto');" target="_blank"  class="an_public1">上传资源</a>
    &nbsp;&nbsp;<a href="resource?m=toAllResPage" onclick="closeModel('dv_firstInto');" target="_blank" class="an_public1">查看资源</a></p>
</div>
</div>

</body>
</html>

