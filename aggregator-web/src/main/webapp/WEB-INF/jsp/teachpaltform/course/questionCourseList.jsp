<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="com.school.entity.UserInfo"%>
<%@ include file="/util/common-jsp/common-jxpt.jsp"%>
<%
    //UserInfo user=(UserInfo)request.getSession().getAttribute("CURRENT_USER");
//String userid=user.getRef();
%>
<html>
<script type="text/javascript" src="<%=basePath %>js/notice/notice.js"></script>
<script type="text/javascript">
    var subjectid="${subjectid}";
    var courseid="${addCourseId}";
    var operate_type="${param.operate_type}";
    var questype="${param.questype}";
    var p1;
    $(function(){


        //翻页控件
        p1=new PageControl({
            post_url:'teachercourse?m=getCourseQuestionList',
            page_id:'page1',
            page_control_name:"p1",		//分页变量空间的对象名称
            post_form:document.page1form,		//form
            http_free_operate_handler:validateParam,
            gender_address_id:'page1address',		//显示的区域
            http_operate_handler:getCourseQuestionList,	//执行成功后返回方法
            return_type:'json',								//放回的值类型
            page_no:1,					//当前的页数
            page_size:10,				//当前页面显示的数量
            rectotal:0,				//一共多少
            pagetotal:1,
            operate_id:"maintbl"
        });

       // pageGo('p1');

    });



    function validateParam(tObj){
        var param=new Object();
        var grade = $("#grade").val();
        if(grade.length>0&&grade!="0"){
            param.gradeid = grade;
        }
        var material = $("#material").val();
        if(material!=null&&material.length>0&&material!="0"){
            param.materialid=material;
        }
        var level = $("#level").val();
        if(level.length>0&&level!="0"){
            param.courselevel=level;
        }
        var name = $("#coursename").val();
        if(name.length>0){
            param.coursename = name;
        }
        if(questype.length>0)
            param.questiontype=questype;
        param.currentcourseid=courseid;
        param.subjectid=subjectid;
        tObj.setPostParams(param);
    }


    function getCourseQuestionList(rps){
        var addCourseId = $("#addCourseId").val();
        if(rps.type=='error'){
            alert(rps.msg);

                // 设置显示值
                var shtml = '<tr><td align="center">暂时没有专题!';
                shtml += '</td></tr>';
                $("#mainTbl").html(shtml);

        }else{
            var shtml = '';
            if(rps.objList.length<1){
                shtml+="<tr><th colspan='8' style='height:65px' align='center'>暂无信息!</th></tr>";
            }else{
                shtml+='<tr>';
                shtml+='<th>专题名称</th>';
                shtml+='<th>数量</th>';
                shtml+='<th>作者</th>';
                shtml+='<th>年级</th>';
                shtml+='<th>评价</th>';
                shtml+='</tr>';
                $.each(rps.objList,function(idx,itm){
                    if(itm.courseid=="${addCourseId}"){
                        return;
                    }
                    var tname = typeof itm.teachername == 'undefined'|| itm.teachername.length<1 ? "北京四中网校" : itm.teachername;
                    var versionname= typeof itm.versionname == 'undefined'||itm.versionname.length<1 ? "" :itm.versionname;
                    var materialname=typeof itm.materialname=='undefined'||itm.materialname.length<1?'':"("+itm.materialname+versionname+")";
                    shtml+='<tr>';
                    shtml+='<td><p><a href="question?m=toAddQuestionByCourse&selectCourseid='+itm.courseid+'&addCourseId='+addCourseId+'&operate_type='+operate_type+'&questype='+questype+'" >'+itm.coursename+'&nbsp;&nbsp;'+materialname+'</a></p></td>';
                    shtml+='<td>'+itm.questionnum+'</td>';
                    shtml+='<td>'+tname+'</td>';
                    shtml+='<td>'+itm.gradename+'</td>';
                    shtml+='<td>';
                    for(var i=1;i<6;i++)
                        if(itm.avgscore>=i)
                            shtml += "<span class='ico_star1'></span>";
                        else if(itm.avgscore<i&&itm.avgscore>i-1)
                            shtml += "<span class='ico_star2'></span>";
                        else
                            shtml += "<span class='ico_star3'></span>";
                    shtml+='</td>';
                    shtml+='</tr>';
                });
            }
            $("#mainTbl").hide();
            $("#mainTbl").html(shtml);
            $("#mainTbl").show('fast');
            //翻页信息
            if (typeof (p1) != "undefined" && typeof (p1) == "object") {
                p1.setPagetotal(rps.presult.pageTotal);
                p1.setRectotal(rps.presult.recTotal);
                p1.setPageSize(rps.presult.pageSize);
                p1.setPageNo(rps.presult.pageNo);
                p1.Refresh();
            }
        }
    }


    /**
     * 资源上传---查询教材
     */
    function load_material(materialid) {
        var gradeid = $("#grade").val();
        if (gradeid.length < 1)
            return;

        $.ajax({
            url: 'teachingmaterial?m=getTchingMaterialList',
            type: 'post',
            data: {
                gradeid: gradeid,
                subjectid:subjectid
            },
            dataType: 'json',
            cache: false,
            error: function () {
                alert('网络异常!')
            },
            success: function (rps) {
                if (rps.type == "error") {
                    alert(rps.msg);
                } else {
                    var htm = '';
                    if (rps.objList.length) {
                        htm='<option value="">==请选择教材版本==</option>'
                        $.each(rps.objList, function (idx, itm) {
                            var versionname=typeof itm.versionname !='undefined'&&itm.versionname.length>0?'('+itm.versionname+')':'';
                            htm += '<option value="' + itm.materialid + '">' + itm.subjectname + itm.materialname +versionname+ '</option>';
                        })
                    }
                    $("#material").html(htm);
                    if(typeof(materialid)!='undefined'&&materialid.length>0)
                        $("#material").val(materialid);
                }
            }
        });
    }

    function queryList(){
        var param={};

        $.ajax({
            url:'teachercourse?m=getCourseQuestionList',
            dataType:'json',
            type:'POST',
            data:param,
            cache: false,
            error:function(){
                alert('异常错误!系统未响应!');
            },success:function(rps){
                getCourseQuestionList(rps);
            }
        });
    }
</script>
<body>


    <div class="subpage_head"><span class="ico55"></span><strong>添加试题</strong></div>
    <div class="subpage_nav">
        <ul>
            <li><a href="question?m=toAddQuestion&courseid=${param.addCourseId}">新建试题</a></li>
            <li class="crumb"><a href="teachercourse?m=toCourseQuestionList&addCourseId=${param.addCourseId}">引用试题</a></li>
        </ul>
    </div>
    <div class="content1">
        <p class="public_input p_t_10">
            <select id="grade" name="grade" onchange="load_material()">
                <c:forEach var="grade" items="${gradeList}">
                    <option value="${grade.gradeid}">${grade.gradevalue}</option>
                </c:forEach>
            </select>
            <select id="material" name="material">
               <!-- <option value="0">全部</option>
                <option>人教版</option>
                <option>北师大版</option> -->
            </select>
            <select id="level" name="level">
                <option value="0">==请选择专题类型==</option>
                <option value="1">自建专题</option>
                <option  value="2">标准/共享专题</option>
                <option  value="3">关联专题</option>
            </select>
            <input  id="coursename" name="coursename" placeholder="输入专题名     匹配" type="text" class="w240" />
            <a href="javascript:pageGo('p1');" class="an_search" title="查询"></a></p>
        <input type="hidden" id="addCourseId" name="addCourseId" value="${addCourseId}"/>

        <table border="0" cellpadding="0" cellspacing="0" class="public_tab2">
            <col class="w500"/>
            <col class="w90"/>
            <col class="w100"/>
            <col class="w100"/>
            <col class="w150"/>
            <tbody id="mainTbl" name="mainTbl"></tbody>
        </table>
        <form id="page1form" name="page1form"  method="post" >
            <p align="center" id="page1address"></p>
        </form>
    </div>
    <%@include file="/util/foot.jsp" %>
</body>

<script type="text/javascript">
    <c:if test="${!empty materialInfo}">
         $("#grade").val("${materialInfo.gradeid}");
         load_material("${materialInfo.teachingmaterialid}");

    </c:if>

    <c:if test="${!empty relateCourse}">
        $("#level").val(3);
    </c:if>
</script>
</html>
