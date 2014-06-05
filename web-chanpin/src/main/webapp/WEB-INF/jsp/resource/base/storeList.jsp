<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/util/common-jsp/common-zrxt.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
    <title>数字化校园</title>
    <script type="text/javascript">
        var p2;
        var audiosuffix="${audiosuffix}";
        var videosuffix="${videosuffix}";
        var imagesuffix="${imagesuffix}";
        $(function(){
            //翻页控件
            p2=new PageControl({
                post_url:'store?m=ajaxList',
                page_id:'page2',
                page_control_name:"p2",		//分页变量空间的对象名称
                post_form:document.page2form,		//form
                http_free_operate_handler:beforeStoreAjaxList,
                gender_address_id:'page2address',		//显示的区域
                http_operate_handler:afterStoreAjaxList,	//执行成功后返回方法
                return_type:'json',								//放回的值类型
                page_no:1,					//当前的页数
                page_size:20,				//当前页面显示的数量
                rectotal:0,				//一共多少
                pagetotal:1,
                operate_id:"storeResData"
            });

            $("#dv_search_condition select").change(function(){
                pageGo('p2');
            });
//            $("#st_grade").change(function(){
//                pageGo('p2');
//            });
//            $("#st_rt").change(function(){
//                pageGo('p2');
//            });
//            $("#st_ft").change(function(){
//                pageGo('p2');
//            });
            pageGo('p2');
        });

        function beforeStoreAjaxList(p){
            var param = {};
            param.userid = ${userid};
            if($("#st_sub").val()!=0){
                param.subject = $("#st_sub").val();
            }
            if($("#st_grade").val()!=0){
                param.grade = $("#st_grade").val();
            }
            if($("#st_rt").val()!=0){
                param.restype = $("#st_rt").val();
            }
            if($("#st_ft").val()!=0){
                param.filetype = $("#st_ft").val();
            }
            if($("#resname").val().Trim().length>0){
                param.resname = $("#resname").val();
            }
            if($("#sel_fromschool").val().Trim().length>0){
                param.schoolname = $("#sel_fromschool").val();
            }
            p.setPostParams(param);
        }

        function afterStoreAjaxList(rps){
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
                    html+="<td>"+(itm.schoolname==null?"":itm.schoolname)+"</td>";
                    html+="<td>"+itm.username+"</td>";
                    html+="<td><a href="+"javascript:operateStore('"+itm.resid+"')"+" title='取消收藏' class='ico58b'></a></td>";
                    html+="</tr>";
                });
            }else{
                html+="<tr><td colspan='4'>暂无数据！</td></tr>";
            }
            p2.setPageSize(rps.presult.pageSize);
            p2.setPageNo(rps.presult.pageNo);
            p2.setRectotal(rps.presult.recTotal);
            p2.setPagetotal(rps.presult.pageTotal);
            p2.Refresh();
            $("#storeResData").html(html);
        }

        function operateStore(resid){
            if(typeof(resid)=="undefined"||resid==null||resid.Trim().length<1){
                alert("异常错误，请刷新页面后重试! 原因：resid is empty!");return;
            }
            var u='store?m=dodelete';
            $.ajax({
                url:u,
                type:'POST',
                data:{resid:resid},
                dataType:'json',
                error:function(){alert("网络异常")},
                success:function(rps){
                    if(rps.type=="error"){
                        alert(rps.msg);
                    }else{
                        pageGo('p2');
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
        <li class="crumb"><a href="resource?m=toStoreList">我收藏的</a></li>
        <c:if test="${isadmin==1}">
            <li><a href="resource?m=toCheckList">我管理的</a></li>
        </c:if>
    </ul>
</div>

<div class="content1">
    <div class="zyxt_sczy public_input" id="dv_search_condition">
        <select name="st_sub" id="st_sub">
            <option selected="selected" value="0" >学科</option>
            <c:forEach items="${subjectEVList}" var="sev">
                <option value="${sev.subjectid }" >${sev.subjectname }</option>
            </c:forEach>
        </select>
        <select name="st_grade" id="st_grade">
            <option selected="selected" value="0" >年级</option>
            <c:forEach items="${gradeEVList}" var="gev">
                <option value="${gev.gradeid }" >${gev.gradename }</option>
            </c:forEach>
        </select>
        <select name="st_rt" id="st_rt">
            <option selected="selected" value="0" >资源类型</option>
            <c:forEach items="${resTypeEVList}" var="rtev">
                <option value="${rtev.dictionaryvalue }" >${rtev.dictionaryname }</option>
            </c:forEach>
        </select>
        <select name="st_ft" id="st_ft">
            <option selected="selected" value="0" >文件类型</option>
            <c:forEach items="${resFileTypeEVList}" var="ftev">
                <option value="${ftev.dictionaryvalue }" >${ftev.dictionaryname }</option>
            </c:forEach>
        </select>
        <select name="sel_fromschool" id="sel_fromschool" class="w220">
            <option selected="selected" value="">来源学校</option>
            <c:if test="${!empty scList}">
                <c:forEach items="${scList}" var="sh">
                    <option value="${sh.nameString}">${sh.nameString}</option>
                </c:forEach>
            </c:if>
        </select>
        <input id="resname" name="resname" type="text" class="w320"/>&nbsp;<a href="javascript:pageGo('p2');" class="an_search" title="搜索"></a>
    </div>
    <table border="0" cellpadding="0" cellspacing="0" class="public_tab2">
        <colgroup class="w440"></colgroup>
        <colgroup class="w300"></colgroup>
        <colgroup span="2" class="w100"></colgroup>
        <tr>
            <th>资源名称</th>
            <th>来源学校</th>
            <th>发布者</th>
            <th>操作</th>
        </tr>
        <tbody id="storeResData"></tbody>
    </table>

    <form id="page2form" name="page2form" method="post">
        <p class="nextpage"  id="page2address"></p>
    </form>
</div>
<%@include file="/util/foot.jsp" %>
</body>
</html>