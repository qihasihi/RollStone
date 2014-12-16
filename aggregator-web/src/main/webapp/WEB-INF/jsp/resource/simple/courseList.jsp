<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/util/common-jsp/common-zrxt.jsp" %>
<html>
<head>
    <script type="text/javascript" src="js/resource/new_resbase.js"></script>
    <script type="text/javascript">
        var defaultSearchValue="${srhValue}";
        var lb;
        var adminFlag=${adminFlag};
        var selVersionId="${selVersionId}";
        var p1;
        $(function(){

//            $("#gradeid").change(function(){
//                getTchingMaterial();
//            });

            if(defaultSearchValue!=null
                    &&defaultSearchValue.length>0)
                $("#searchValue").val(defaultSearchValue);

            p1=new PageControl({
                post_url:'teachercourse?m=getCourseLibraryListAjax',
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
        });

        function beforeAjaxList(p){
            var gradeid=0;
            var materialid=0;
            var coursename="";
            gradeid = $("#gradeid").val();
//            materialid = $("#materialid").val();
            coursename = $("#searchValue").val().Trim();
            var param = {};
            if(gradeid !=null && gradeid!=0) {
                param.gradeid=gradeid;
            }
            if(materialid !=null && materialid!=0) {
                param.materialidvalues=materialid;
            }
            if(coursename!=null && coursename.Trim().length!=0) {
                param.coursename=coursename;
            }
            if($("#subjectid").val().Trim().length>0)
                param.subjectvalues=$("#subjectid").val().Trim();
            if(selVersionId.Trim().length>0)
                param.versionvalues=selVersionId;<%//精简版只允许查的教材信息%>
            param.courselevel=-4;  //精简版，只查云端的标准，共享，并且course_id>0
            p.setPostParams(param);
        }

        function afterAjaxList(rps){
            var html = "";
            if(rps!=null&&rps.presult.list.length>0){
                $.each(rps.objList,function(idx,itm){
                    html+="<tr>";
                    html+="<td><p><a href='simpleRes?m=courseResList&courseid="+ itm.courseid +"' target='_blank'>"+ itm.coursename +"</a></p></td>";
//                    html+="<td colspan='2'><p>"+ itm.materialnames +"</p></td>";
                    html+="<td>"+ itm.resnum +"</td>";
                    html+="<td colspan=2>"+ (itm.realname.length<1?"北京四中网校":itm.realname) +"</td>";
                    html+="</tr> ";
                });
            }else{
                html+="<tr>";
                html+="<td colspan='5'>没有数据！</td>";
                html+="</tr> ";
            }
            p1.setPageSize(rps.presult.pageSize);
            p1.setPageNo(rps.presult.pageNo);
            p1.setRectotal(rps.presult.recTotal);
            p1.setPagetotal(rps.presult.pageTotal);
            p1.Refresh();
            $("#courseData").html(html);
        }

        function getTchingMaterial(){
            var gradeid=$("#gradeid").val();
            $.post('teachingmaterial?m=getTchingMaterialList',{gradeid:gradeid},
                function(rps){
                    var html ="";
                    if(rps!=null && rps.objList!=null && rps.objList.length>0){
                       var  html1="<option value='0'>- 全部 -</option>";
                        $.each(rps.objList,function(idx,itm) {
                            var versionIdTmp="";
                            if(selVersionId.Trim().length>0)
                                versionIdTmp=(","+selVersionId+",");
                            var istrue=true;
                            if(versionIdTmp.Trim().length>0){
                                if(versionIdTmp.indexOf(","+itm.versionid+",")==-1)
                                  istrue=false;
                            }
                            if(istrue)
                                html+="<option value='"+itm.materialid+"'>"+itm.materialname+"("+itm.versionname+")</option>";
                        });
                        if(html.Trim().length>0)
                            html=html1+html;
                    }
                    if(html.Trim().length<1)
                        html="<option value='0'>- 无 -</option>";
                    $("#materialid").html(html);
                    pageGo('p1');
                },"json");
        }
    </script>
</head>

<body>
<div class="subpage_head"><span class="ico57"></span><strong>资源搜索</strong></div>
<div class="subpage_nav">
    <ul>
        <li><a href="simpleRes?m=resList">按资源搜索</a></li>
        <li class="crumb"><a href="simpleRes?m=courseList">按知识点搜索</a></li>
    </ul>
</div>

<div class="content1">
    <div class=" p_tb_10" id="dv_search_cond">
        <select id="subjectid" name="subjectid" onchange="pageGo('p1',1);" class="w100">
            <c:forEach var="sb" items="${ subList}">
                <option value="${sb.subjectid}">${sb.subjectname}</option>
            </c:forEach>
        </select>
        <select id="gradeid" name="gradeid"  onchange="pageGo('p1',1);" class="w100">
            <option value="0">-选择年级-</option>
            <c:forEach var="grade" items="${ gradeList}">
                <option value="${grade.gradeid}">${grade.gradename}</option>
            </c:forEach>
        </select>
        <%--<select id="materialid" name="materialid" onchange="pageGo('p1',1);" class="w220">--%>
            <%--<option value="0">选择版本</option>--%>
        <%--</select>--%>
        <input id="searchValue"  placeholder="输入信息按知识点名称搜索!" name="searchValue" type="text" class="w320"/>&nbsp;
        <a class="an_search" href="javascript:pageGo('p1',1);" title="搜索"></a></div>
    <table border="0" cellpadding="0" cellspacing="0" class="public_tab2">
        <colgroup class="w720"></colgroup>
        <%--<colgroup class="w240"></colgroup>--%>
        <colgroup span="2" class="w100"></colgroup>
        <tr>
            <th>知识点名称</th>
            <%--<th colspan="2">教材版本</th>--%>
            <th>资源数量</th>
            <th >作者</th>

        </tr>
        <tbody id="courseData" >
        </tbody>
    </table>
    <form action="" id="page1form" name="page1form" method="post">
        <p align="center"  class="nextpage" id="page1address"></p>
    </form>
    <div class="clear"></div>
</div>
<%@include file="/util/foot.jsp" %>
</body>
</html>
