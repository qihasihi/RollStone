<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/util/common-jsp/common-zrxt.jsp" %>
<html>
<head>
<script type="text/javascript" src="js/resource/new_resbase.js"></script>
	<script type="text/javascript">
	var defaultSearchValue="${srhValue}";
	var lb;
	var p1;
    var audiosuffix="${audiosuffix}";
    var videosuffix="${videosuffix}";
    var imagesuffix="${imagesuffix}";
	$(function(){
		if(defaultSearchValue!=null
				&&defaultSearchValue.length>0)
			$("#searchValue").val(defaultSearchValue);
        if($("#subSel option").length<2){
            $("#subSel").hide();
        }else{
            <c:if test="${!empty subjectid}">
                $("#subSel option[value='${subjectid}']").attr("selected",true);
            </c:if>
        }
		lb=new LeibieControl({controlid:"lb"},true);
		p1=new PageControl({
			post_url:'resource?m=ajaxListByValues',
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
		pageGo('p1');

        allowRefresh();

	});



	function beforeAjaxList(p){
		var param = {};
        var resname="";
        resname = $("#searchValue").val();
        if(resname.Trim().length>0){
            param.resname = resname;
        }
        if($("#subSel").length>0){
            var sub=$("#subSel").val();
            if(sub.Trim().length>0){
                param.subjectvalues=sub;
            }
        }
        if($("#sharestatusvalue").val().length>0)
            param.sharestatusvalues=$("#sharestatusvalue").val();
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
                html+="<a href='javascript:;' onmouseover=\"getResCourse('"+itm.resid+"')\" onmouseout=\"dv_res_course.style.display='none';ul_course.innerHTML='';\"  class='font-darkblue'>知识点</a>&nbsp;&nbsp;&nbsp;";
                if(itm.resdegree==1||itm.resdegree==2)
                    html+="<span class='ico70' title='北京四中网校标准资源库'></span>";
                else
                    html+="<span>"+itm.username+"</span>";
				html+="</p><p><div class='wrapline'>"+(itm.resintroduce==null?"无":itm.resintroduce)+"</div></p>";
				html+="<p class='text_info'>"+itm.subjectname+","+itm.gradename+","+itm.restypename+","+itm.filetypename
                        +"<span class='ico41' title='赞'></span><b>"+itm.praisenum+"</b>"
                        +"<span class='ico73' title='推荐'></span><b>"+itm.recomendnum+"</b>"
                        +"<span class='ico58a' title='收藏'></span><b>"+itm.storenum+"</b>"
                        +"<span class='ico46' title='浏览'></span><b>"+itm.clicks+"</b>"
                        +"<span class='ico59' title='下载'></span><b>"+itm.downloadnum+"</b></p></td>";
				html+="</tr> ";
			});
            p1.setPageSize(rps.presult.pageSize);
            p1.setPageNo(rps.presult.pageNo);
            p1.setRectotal(rps.presult.recTotal);
            p1.setPagetotal(rps.presult.pageTotal);
            p1.Refresh();
            $("#resTotal").html(rps.presult.recTotal);
		}else{
			html+="<tr><td colspan='2'>暂无数据！</td></tr> ";
            $("#resTotal").html(0);
            p1.setPageSize(rps.presult.pageSize);
            p1.setPageNo(1);
            p1.setRectotal(0);
            p1.setPagetotal(1);
            p1.Refresh();
		}
		$("#resData").html(html);
	}

        function getResCourse(resid){
             var dvstyle=dv_res_course.style;
                dvstyle.left=(mousePostion.x+5)+'px';
                dvstyle.top=(mousePostion.y+5)+'px';
                dvstyle.display='block';
                // dv_res_course.style.display='none';
            if(typeof(resid)=="undefined"||resid==null||(resid+"").length<1){
                alert("异常错误，原因：参数异常!");return;
            }
            if($("#ul_course li").length<1)
            $.ajax({
                url:'resource?m=ajaxResCourse',
                type:'post',
                data:{resid:resid },
                dataType:'json',
                error:function(){alert("网络异常")},
                success:function(rps){
                     var h='';
                    $("#ul_course").html('');
                    if(rps.type=="success"){
                        if(rps.objList.length<1){
                            h+="<li>暂无知识点!</li>";
                            $("#ul_course").append(h);
                        }else{
                            $.each(rps.objList,function(idx,itm){
                                h+='<li value="'+itm.COURSE_ID+'">'+itm.COURSE_NAME+'</li>';
                                if(idx%30==0&&idx>0){
                                    $("#ul_course").append(h);
                                    h='';
                                }
                            });
                            if(h.length>0)
                                $("#ul_course").append(h);
                        }
                    }else{
                        h+="<li>异常错误!原因：未知!</li>";
                    }
                }
            });
        }
    </script>
</head>
<body>
<div class="subpage_head"><span class="ico57"></span><strong>资源搜索</strong></div>

<div class="subpage_nav">
    <ul>
        <li><a href="resource?m=toteacherreslist">按教师搜索</a></li>
        <li class="crumb"><a href="resource?m=resList">按资源搜索</a></li>
        <li><a href="resource?m=courseList">按知识点搜索</a></li>
    </ul>
</div>

<div class="content2">
    <div class="zyxt_searchT public_input">
        <select id="sharestatusvalue" onchange="pageGo('p1',1);">
            <option value="1,2">全部</option>
            <option value="2" selected>云端</option>
            <option value="1">校内</option>
        </select>
        <select id="subSel" onchange="pageGo('p1',1)">
        <c:if test="${!empty subList}">
            <c:forEach items="${subList}" var="sb">
                <option value="${sb.subjectid}">${sb.subjectname}</option>
            </c:forEach>
        </c:if>
        </select>
        <input id="searchValue"  placeholder="输入信息按资源名称搜索!" name="searchValue" type="text" class="w320"/>&nbsp;
        <a class="an_search" href="javascript:pageGo('p1',1);" title="搜索"></a>&nbsp;&nbsp;<a href="resource?m=list" class=" font-darkblue" >高级搜索</a>
        <p class="number">搜到资源 <span id="resTotal" class="font-red">0</span></p>
    </div>
    <table border="0" cellpadding="0" cellspacing="0" class="public_tab1 font-black">
        <col class="w30"/>
        <col class="w900"/>
        <tbody id="resData"></tbody>
    </table>
    <form action="" id="page1form" name="page1form" method="post">
        <p align="center"  class="nextpage"  id="page1address"></p>
    </form>
    <div class="clear"></div>
</div>
<!--上传文件类型-->
<div class="public_windows font-black"  id="dv_res_course" name="dv_res_course" style="display:none;position: absolute">
    <h3>知识点</h3>
    <ul id="ul_course">

    </ul>
</div>

<%@include file="/util/foot.jsp" %>
</body>
</html>
