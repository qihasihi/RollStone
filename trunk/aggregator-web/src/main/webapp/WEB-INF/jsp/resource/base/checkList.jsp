<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/util/common-jsp/common-zrxt.jsp" %>
<html>
<head>
    <script type="text/javascript">
        var p3;
        var audiosuffix="${audiosuffix}";
        var videosuffix="${videosuffix}";
        var imagesuffix="${imagesuffix}";
        $(function(){
            //翻页控件
            p3=new PageControl({
                post_url:'resource?m=ajaxCheckList',
                page_id:'page3',
                page_control_name:"p3",		//分页变量空间的对象名称
                post_form:document.page3form,		//form
                http_free_operate_handler:beforeCheckAjaxList,
                http_operate_html:"<tr><td colspan='6'><img src='img/loading_smail.gif'/>正在操作中!请稍候……</td></tr>",
                gender_address_id:'page3address',		//显示的区域
                http_operate_handler:afterCheckAjaxList,	//执行成功后返回方法
                return_type:'json',								//放回的值类型
                page_no:1,					//当前的页数
                page_size:20,				//当前页面显示的数量
                rectotal:0,				//一共多少
                pagetotal:1,
                operate_id:"checkResData"
            });

            $("#ck_sub").change(function(){
                pageGo('p3');
            });
            $("#ck_grade").change(function(){
                pageGo('p3');
            });
            $("#ck_rt").change(function(){
                pageGo('p3');
            });
            $("#ck_ft").change(function(){
                pageGo('p3');
            });
            allowRefresh();
            <c:if test="${!empty subjectid}">
            $("#ck_sub option[value='${subjectid}']").attr("selected",true);
            </c:if>
            <c:if test="${!empty gradeid}">
            $("#ck_grade option[value='${gradeid}']").attr("selected",true);
            </c:if>
            pageGo('p3');
        });

        function beforeCheckAjaxList(p){
            var param = {};
            if($("#ck_sub").val()!=0){
                param.subjectvalues = $("#ck_sub").val();
            }
            if($("#ck_grade").val()!=0){
                param.gradevalues = $("#ck_grade").val();
            }
            if($("#ck_rt").val()!=0){
                param.restype = $("#ck_rt").val();
            }
            if($("#ck_ft").val()!=0){
                param.filetype = $("#ck_ft").val();
            }
            if($("#username").val().Trim().length>0){
                param.username = $("#username").val();
            }
            if($("#resname").val().Trim().length>0){
                param.resname = $("#resname").val();
            }
            p.setPostParams(param);
        }

        function afterCheckAjaxList(rps){
            var html = "";
            if(rps!=null&&rps.presult.list.length>0){
                $.each(rps.objList,function(idx,itm){
                    html+="<tr "+(idx%2==1?"class='trbg1'":"")+">";
                    html+="<td><p class='ico'><a target='_blank' href='resource?m=todetail&resid="+itm.resid+"'>";
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
                    html+=itm.resname+"</a></p></td>";
                    html+="<td>"+itm.subjectname+","+itm.gradename+","+itm.restypename+","+itm.filetypename+"</td>";
                    html+="<td>"+itm.username+"</td>";
                    html+="<td>"+itm.ctimeString+"</td>";
                    html+="<td><a class='font-red' href='javascript:void(0);' onclick="+"getReportList('"+itm.resid+"')"+">"+ itm.reportnum+"</a></td>";
                    if(itm.resstatus==3){
                        html+="<td id='res_"+itm.resid+"_handle'><a title='恢复' class='ico25' href='javascript:void(0);' onclick="+"changeResState('"+itm.resid+"',1);"+"></a></td>";
                    }else{
                        html+="<td id='res_"+itm.resid+"_handle'><a title='删除' class='ico04' href='javascript:void(0);' onclick="+"changeResState('"+itm.resid+"',3);"+"></a></td>";
                    }
                    html+="</tr>";
                });
            }else{
                html+="<tr><td colspan='6'>暂无数据！</td></tr>";
            }
            p3.setPageSize(rps.presult.pageSize);
            p3.setPageNo(rps.presult.pageNo);
            p3.setRectotal(rps.presult.recTotal);
            p3.setPagetotal(rps.presult.pageTotal);
            p3.Refresh();
            $("#checkResData").html(html);
        }

        function changeResState(resid,state){
            if(state==3){
                if(!confirm("确认删除？"))
                    return;
            }
            if(state==1){
                if(!confirm("确认恢复？"))
                    return;
            }

            if(resid==null||state==null){
                alert("参数不足，无法提交！");return;
            }
            $.ajax({
                url:'resource?m=changeresstate',
                type:'POST',
                data:{resid:resid,resstatus:state},
                dataType:'json',
                error:function(){alert("网络异常")},
                success:function(rps){
                    var  html="";
                    if(rps.type=="success"){
                        if(state==3){
                            html+="<a title='恢复' class='ico25' href='javascript:void(0);' onclick="+"changeResState('"+resid+"',1);"+"></a>";
                        }
                        if(state==1){
                            html+="<a title='删除' class='ico04' href='javascript:void(0);' onclick="+"changeResState('"+resid+"',3);"+"></a>";
                        }
                            $("#res_"+resid+"_handle").html(html);
                    }else{
                        alert(rps.msg);
                    }
                }
            });
        }

        function getReportList(resid){
            showModel("resource_report");
            $.ajax({
                url:'resourcereport?m=getreportlist',
                type:'POST',
                data:{resid:resid},
                dataType:'json',
                error:function(){alert("网络异常");$("#addReportBtn").show();},
                success:function(rps){
                    if(rps.type=="error"){
                        alert(rps.msg);
                        return;
                    }
                    if(rps!=null&&rps.presult.list.length>0){
                        var html="";
                        $.each(rps.objList,function(idx,itm){
                            html+="<tr>";
                            html+="<td>"+itm.content+"</td>";
                            html+="<td align='center'>"+itm.realname+"</td>";
                            html+="<td>"+itm.ctimeString+"</td>";
                            html+="</tr>";
                        });
                        $("#report_list").html(html);
                    }
                }
            });
        }
    </script>
</head>
<body>
<div class="subpage_head"><span class="lm_ico03"></span><strong>资源管理</strong></div>

<div class="subpage_nav">
    <ul>
        <li><a href="resource?m=toMyResList">我发布的</a></li>
        <li><a href="resource?m=toStoreList">我收藏的</a></li>
        <li class="crumb"><a href="resource?m=toCheckList">我管理的</a></li>
    </ul>
</div>

<div class="content1">
    <div class="zyxt_sczy public_input">
        <select name="ck_sub" id="ck_sub">
            <option selected="selected" value="0" >学科</option>
            <c:forEach items="${subjectEVList}" var="sev">
                <option value="${sev.subjectid }" >${sev.subjectname }</option>
            </c:forEach>
        </select>
        <select name="ck_grade" id="ck_grade">
            <option selected="selected" value="0" >年级</option>
            <c:forEach items="${gradeEVList}" var="gev">
                <option value="${gev.gradeid }" >${gev.gradename }</option>
            </c:forEach>
        </select>
        <select name="ck_rt" id="ck_rt">
            <option selected="selected" value="0" >资源类型</option>
            <c:forEach items="${resTypeEVList}" var="rtev">
                <option value="${rtev.dictionaryvalue }" >${rtev.dictionaryname }</option>
            </c:forEach>
        </select>
        <select name="ck_ft" id="ck_ft">
            <option selected="selected" value="0" >文件类型</option>
            <c:forEach items="${resFileTypeEVList}" var="ftev">
                <option value="${ftev.dictionaryvalue }" >${ftev.dictionaryname }</option>
            </c:forEach>
        </select>
        <br/><br/>
        <b>发布者：</b><input id="username" name="username" type="text" class="w80"/>
        <b>资源：</b><input id="resname" name="resname" type="text" class="w320"/>&nbsp;
        <a href="javascript:pageGo('p3');" class="an_search" title="搜索"></a>
    </div>
    <table border="0" cellpadding="0" cellspacing="0" class="public_tab2">
        <col class="w320" />
        <col class="w240" />
        <col class="w80" />
        <col class="w140" />
        <col class="w80" />
        <col class="w80" />
        <tr>
            <th>资源名称</th>
            <th>资源属性</th>
            <th>发布者</th>
            <th>发布时间</th>
            <th>举报人数</th>
            <th>操作</th>
        </tr>
        <tbody id="checkResData"></tbody>
    </table>

    <form id="page3form" name="page3form" method="post">
        <p class="nextpage" align="center" id="page3address"></p>
    </form>
</div>
<%@include file="/util/foot.jsp" %>
</body>
</html>

<div id="resource_report" class="zyxt_float_jubao" style="display:none;">
    <div class="public_windows">
        <h3><a href="javascript:closeModel('resource_report');" title="关闭"></a>举报信息</h3>
        <div class="height">
            <table border="0" cellpadding="0" cellspacing="0" class="public_tab3 black">
                <col class="w350" />
                <col class="w100" />
                <col class="w140" />
                <tr>
                    <th>举报内容</th>
                    <th>举报人</th>
                    <th>举报时间</th>
                </tr>
                <tbody id="report_list">
                </tbody>
            </table>
        </div>
    </div>
</div>