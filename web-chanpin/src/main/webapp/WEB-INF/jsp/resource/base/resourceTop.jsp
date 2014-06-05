<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/util/common-jsp/common-zrxt.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script type="text/javascript" src="js/resource/new_resbase.js"></script>
	<script type="text/javascript">
	var p1;
    var audiosuffix="${audiosuffix}";
    var videosuffix="${videosuffix}";
    var imagesuffix="${imagesuffix}";
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
			page_size:5,				//当前页面显示的数量
			rectotal:0,				//一共多少
			pagetotal:1,
			page_order_by:"",
			operate_id:"resData"
		});
		pageGo('p1');
	});
	
	function beforeAjaxList(p){
        var param = {};
        p.setPageOrderBy("${topType}");
        param.timerange = "${timeType}";
        p.setPostParams(param);
	}
	
	function afterAjaxList(rps){
		var html = "";
		if(rps!=null&&rps.presult.list.length>0){
			$.each(rps.objList,function(idx,itm){
                html+="<tr>";
                html+="<td>";
                itm.filesuffixname=itm.filesuffixname.toLowerCase();
                if(itm.filesuffixname==".doc"||itm.filesuffixname==".docx")
                    html+="<b class='ico_doc1'></b>";
                else if(itm.filesuffixname==".xls"||itm.filesuffixname==".xlsx")
                    html+="<b class='ico_xls1'></b>";
                else if(itm.filesuffixname==".ppt"||itm.filesuffixname==".pptx")
                    html+="<b class='ico_ppt1'></b>";
                else if(itm.filesuffixname==".swf")
                    html+="<b class='ico_swf1'></b>";
                else if(itm.filesuffixname==".pdf")
                    html+="<b class='ico_pdf1'></b>";
                else if(itm.filesuffixname==".vsd")
                    html+="<b class='ico_vsd1'></b>";
                else if(itm.filesuffixname==".rtf")
                    html+="<b class='ico_rtf1'></b>";
                else if(itm.filesuffixname==".txt")
                    html+="<b class='ico_txt1'></b>";
                else if(audiosuffix.indexOf(itm.filesuffixname)!=-1)
                    html+="<b class='ico_mp31'></b>";
                else if(videosuffix.indexOf(itm.filesuffixname)!=-1)
                    html+="<b class='ico_mp41'></b>";
                else if(imagesuffix.indexOf(itm.filesuffixname)!=-1)
                    html+="<b class='ico_jpg1'></b>";
                else
                    html+="<b class='ico_other1'></b>";
                html+="</td>";
                html+="<td><p><a target='_blank' href='resource?m=todetail&resid="+itm.resid+"'>"+ itm.resname +"</a>&nbsp;&nbsp;&nbsp;&nbsp;"
                html+="知识点："+(itm.coursename==null?"无":"")+"&nbsp;&nbsp;&nbsp;<span class='ico70' title='北京四中网校标准资源库'></span></p>";
                html+="<p>"+(itm.resintroduce==null?"无":itm.resintroduce)+"</p>";
                html+="<p class='text_info'>"+itm.subjectname+","+itm.gradename+","+itm.restypename+","+itm.filetypename
                        +"<span class='ico41' title='赞'></span><b>"+itm.praisenum+"</b>"
                        +"<span class='ico73' title='推荐'></span><b>"+itm.recomendnum+"</b>"
                        +"<span class='ico58a' title='收藏'></span><b>"+itm.storenum+"</b>"
                        +"<span class='ico46' title='浏览'></span><b>"+itm.clicks+"</b>"
                        +"<span class='ico59' title='下载'></span><b>"+itm.downloadnum+"</b></p></td>";
                html+="</tr> ";
			});
		}else{
            html+="<tr><td colspan='2'>暂无数据！</td></tr> ";
		}
		
		p1.setPageSize(rps.presult.pageSize);
		p1.setPageNo(rps.presult.pageNo);
		p1.setRectotal(rps.presult.recTotal);
		p1.setPagetotal(rps.presult.pageTotal);
		p1.Refresh();
		$("#resData").html(html);
	}
	
    </script>
</head>
<body>
<div class="subpage_head"><span class="ico71"></span><strong>
    <c:if test="${topType=='RES_SCORE'}">
        资源评分排行
    </c:if>
    <c:if test="${topType=='CLICKS'}">
        最热浏览排行榜
    </c:if>
    <c:if test="${topType=='DOWNLOADNUM'}">
        最热下载排行榜
    </c:if>
    <c:if test="${topType=='C_TIME'}">
        最新上传资源
    </c:if>
    &mdash;&mdash;
    <c:if test="${timeType=='week'}">
        按周浏览量排行
    </c:if>
    <c:if test="${timeType=='month'}">
        按月浏览量排行
    </c:if>
    </strong></div>
<div class="content1">
    <table border="0" cellpadding="0" cellspacing="0" class="public_tab1 font-black">
        <col class="w30"/>
        <col class="w900"/>
        <tbody id="resData"></tbody>
    </table>

    <form action="/role/ajaxlist" id="page1form" name="page1form" method="post">
        <p class="nextpage" align="center" id="page1address"></p>
    </form>
</div>
<%@include file="/util/foot.jsp" %>
</body>
</html>
