<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@include file="/util/common-jsp/common-zrxt.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script type="text/javascript" src="js/resource/res_index.js"></script>
	<script type="text/javascript">
        var currtenTab=1; //当前学科导航栏页数
        var tabSize=14; //学科导航栏每页显示数量
        var audiosuffix="${audiosuffix}";
        var videosuffix="${videosuffix}";
        var imagesuffix="${imagesuffix}";
        var selVersionId="${selVersionId}";

        var subjectid="${param.subjectid}";
        $(function(){
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

            changeTab("back");
            changeTab("front");
            <%--eval($("li[id='li_sub_${!empty param.subjectid?param.subjectid:subjectEVList[0].subjectid}']").children("a").attr("onclick"));--%>
            getResourceListBySub('${!empty param.subjectid?param.subjectid:subjectEVList[0].subjectid}');
            //开始防ajax刷新
            allowRefresh();
        });

        function beforeAjaxList(p){
            var param = {};
            p.setPageOrderBy($("#orderby").val());
            if($("#subject").val()!=0)
                param.subjectvalues = $("#subject").val();
            if($("#restype").val()!=0)
                param.restypevalues = $("#restype").val();
            if($("#sharestatusvalue").val().length>0)
                param.sharestatusvalues=$("#sharestatusvalue").val();
            if($("#searchValue").val().length>0){
                param.resname =$("#searchValue").val().Trim();
            }
            if(selVersionId.Trim().length>0)
                param.versionvalues=selVersionId;<%//精简版只允许查的教材信息%>
            p.setPostParams(param);
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
        function afterAjaxList(rps){
            var html = "";
            if(rps!=null&&rps.presult.list.length>0){
                $.each(rps.objList,function(idx,itm){
                    html+="<tr "+(idx%2==1?"class='trbg1'":"")+"><td><p class=\"ico\">";
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
                    html+="<a target='_blank' href='simpleRes?m=todetail&resid="+itm.resid+"'>"+ itm.resname +"</a></p></td>";
                    html+="<td  onmouseover=\"getResCourse('"+itm.resid+"')\" onmouseout=\"dv_res_course.style.display='none';ul_course.innerHTML='';\"><p>查看</p></td>";
                    html+="<td>"+itm.username+"</td>";
                    html+="<td>"+itm.clicks+"</td>";
                    html+="<td>"+itm.recomendnum+"</td>";
                    html+="<td>"+itm.ctimeString.substring(0,10)+"</td>";
                    html+="</tr>";
                });
            }else{
                html+="<tr><td colspan='6'>暂无数据！</tr></td>";
            }
            p1.setPageSize(rps.presult.pageSize);
            p1.setPageNo(rps.presult.pageNo);
            p1.setRectotal(rps.presult.recTotal);
            p1.setPagetotal(rps.presult.pageTotal);
            p1.Refresh();
            $("#resData").html(html);
        }

        function getResourceListBySub(evid){
            $("#subject").val(evid);
            $("#li_sub_"+evid).siblings().attr("class","");
            $("#li_sub_"+evid).attr("class","crumb");
            pageGo('p1',1);
        }

        function getResourceListByType(evid){
            $("#restype").val(evid);
            $("#li_rt_"+evid).siblings().attr("class","");
            $("#li_rt_"+evid).attr("class","crumb");
            pageGo('p1',1);
        }

        function changeTab2(direct){
            if(direct=="back"){
                var crumbNextLen=$("ul[id='ul_sub'] .crumb").next().length;

                if(crumbNextLen<1)
                    changeTab('front');
                else if($("ul[id='ul_sub'] .crumb").next().filter(function(){return this.style.display!='none'}).length<1){
                    // eval($("ul[id='ul_sub'] li:first").children("a").attr("href").replace("javascript:",""));
                    changeTab('back');
                }
                if($("ul[id='ul_sub'] .crumb").next().length>0)
                    eval($("ul[id='ul_sub'] .crumb").next().children("a").attr("href").replace("javascript:",""));
                else
                    eval($("ul[id='ul_sub'] li:first").children("a").attr("href").replace("javascript:",""));
            }
            if(direct=="front"){
                var crumbNextLen=$("ul[id='ul_sub'] .crumb").prev().length;

                if(crumbNextLen<1)
                    changeTab('back');
                else if($("ul[id='ul_sub'] .crumb").prev().filter(function(){return this.style.display!='none'}).length<1){
                    // eval($("ul[id='ul_sub'] li:first").children("a").attr("href").replace("javascript:",""));
                    changeTab('front');
                }

                if($("ul[id='ul_sub'] .crumb").prev().length>0)
                    eval($("ul[id='ul_sub'] .crumb").prev().children("a").attr("href").replace("javascript:",""));
                else
                    eval($("ul[id='ul_sub'] li:last").children("a").attr("href").replace("javascript:",""));
            }
        }


        function changeTab(direct){
            tabTotal=$("#ul_sub").children().length; //标签总数
            var i=0,j=0;
            if(direct=="front"){
                if(currtenTab==1){
                    $("#ul_sub").children(":gt("+(tabTotal-1)+")").hide();
                    return;
                }else{
                    currtenTab-=1;
                    i=(currtenTab-1)*tabSize;
                    j=currtenTab*tabSize-1;
                    $("#ul_sub").children().show();
                    $("#ul_sub").children(":lt("+i+")").hide();
                    $("#ul_sub").children(":gt("+j+")").hide();
                    return;
                }
            }
            if(direct=="back"){
                if((currtenTab*tabSize)>=tabTotal){
                    $("#ul_sub").children(":gt("+(tabSize*currtenTab-1)+")").hide();
                    return;
                }else{
                    currtenTab+=1;
                    i=(currtenTab-1)*tabSize;
                    j=currtenTab*tabSize-1;
                    $("#ul_sub").children().show();
                    $("#ul_sub").children(":lt("+i+")").hide();
                    $("#ul_sub").children(":gt("+j+")").hide();
                    return;
                }
            }
        }

        function changeOrderBy(orderby,n){
            $("#orderby").val(orderby);
            $("#order_"+n).siblings().attr("class","");
            $("#order_"+n).attr("class","crumb");
            pageGo('p1',1);
        }
    </script>
</head>
<body>
<div class="subpage_head"><span class="lm_ico03"></span><strong>全部资源</strong>
<span style="float:right"><a href="simpleRes?m=resList" target="_blank"><strong>进入搜索页面</strong></a></span></div>
<input type="hidden" id="subject" value="${empty param.subjectid?subjectEVList[0].subjectid:param.subjectid}"/>
<input type="hidden" id="restype" value="${resTypeEVList[0].dictionaryvalue}"/>
<input type="hidden" id="orderby" value=" r.CLICKS DESC "/>
<div class="subpage_nav">
    <div class="arr"><a href="javascript:changeTab2('front');"><span class="up"></span></a><a href="javascript:changeTab2('back');"><span class="next"></span></a></div>
    <div style="width:940px;overflow-x:auto "><ul id="ul_sub">
        <c:forEach items="${subjectEVList}" var="sev" varStatus="status">
            <li id="li_sub_${sev.subjectid }" ${(status.index==0?"class='crumb'":"")}><a href="javascript:;" onclick="getResourceListBySub('${sev.subjectid }');searchValue.values='';"><span id="sub_${status.index}">${sev.subjectname }</span></a></li>
        </c:forEach>
    </ul></div>
</div>

<div class="content">
    <div class="contentR zyxt_qbzy">
        <div class="subpage_lm">
            <ul>
                <li id="order_1" class="crumb"><a href="javascript:changeOrderBy('r.CLICKS DESC',1);">按浏览量</a></li>
                <li id="order_2"><a href="javascript:changeOrderBy('r.C_TIME DESC',2);">按发布时间</a></li>
                <li id="order_3"><a href="javascript:changeOrderBy('r.RECOMENDNUM DESC',3);">按推荐次数</a></li>
            </ul>
            <span style="float:right">
                <select id="sharestatusvalue" onchange="pageGo('p1',1);" style="display:none">
                    <option value="1,2">全部</option>
                    <option value="2" selected>云端</option>
                    <option value="1">校内</option>
                </select>
                <input id="searchValue" placeholder="输入信息按资源名称搜索!" name="searchValue" type="text" class="w320"/>&nbsp;
            <a class="an_search" href="javascript:pageGo('p1',1);" title="搜索"></a></span>
        </div>
        <table border="0" cellpadding="0" cellspacing="0" class="public_tab2">
            <col class="w300" />
            <col class="w60" />
            <col class="w220" />
            <col class="w60" />
            <col class="w60" />
            <col class="w80" />
            <tr>
                <th>资源名称</th>
                <th>知识点</th>
                <th>作者</th>
                <th>浏览</th>
                <th>推荐</th>
                <th>发布时间</th>
            </tr>
            <tbody id="resData"></tbody>
        </table>
        <form action="" id="page1form" name="page1form" method="post">
            <p align="center" id="page1address"  class="nextpage"></p>
        </form>
    </div>
    <div class="subpage_contentL">
        <ul>
            <c:forEach items="${resTypeEVList}" var="rt" varStatus="status">
                <li id="li_rt_${rt.dictionaryvalue }" ${(status.index==0?"class='crumb'":"")}><a href="javascript:getResourceListByType('${rt.dictionaryvalue }');"><span id="rt_${status.index}">${rt.dictionaryname }</span></a></li>
            </c:forEach>
        </ul>
    </div>
    <div class="clear"></div>
</div>
<%@include file="/util/foot.jsp" %>

<!--上传文件类型-->
<div class="public_windows font-black"  id="dv_res_course" name="dv_res_course" style="display:none;position: absolute">
    <h3>知识点</h3>
    <ul id="ul_course">

    </ul>
</div>
</body>
</html>
