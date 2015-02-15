
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/util/common-jsp/common-jxpt.jsp"%>

<html>
<head>
<title>${sessionScope.CURRENT_TITLE}</title>
<script type="text/javascript" src="<%=basePath %>js/teachpaltform/group.js"></script>
<script type="text/javascript">
var courseid="${courseid}";
var subjectid="${subjectid}";
var termid="${termid}";
$(function(){
    getGroupByClass();
    $("#sel_cls").bind("change",function(){
        getGroupByClass();
    })
});


    function getGroupByClass(){
        var classId=$("#sel_cls").val();
        if(classId==null||classId.toString().length<1){
            alert('系统未获取班级标识!');
            return;
        }
        var url="group?m=getClassGroups";
        $.ajax({
            url:url,
            data:{classid:classId,subjectid:subjectid,termid:termid},
            type:'post',
            dataType:'json',
            error:function(){
                alert('异常错误,系统未响应！');
            },
            success:function(rps){
                $("#dv_content").html('');
                if(rps.type=="success"){
                    if(rps.objList!=null&&rps.objList.length>0){
                        $.each(rps.objList,function(idx,itm){
                            statByGroupId(classId,itm.groupid,itm.groupname,(idx==(rps.objList.length-1)));
                        });

                    }else{
                        statByClsId(classId);
                    }
                }else{
                    alert(rps.msg);
                }

            }});
    }

    function statByClsId(clsid){
        if(clsid==null||clsid.toString().length<1){
            alert('系统未获取班级标识!');
            return;
        }
        var url="task?loadStuStatByClass";
        $.ajax({
            url:url,
            data:{classid:clsid,subjectid:subjectid,courseid:courseid},
            type:'post',
            dataType:'json',
            error:function(){
                alert('系统未响应!');
            },
            success:function(rps){
                var column='',data='';
                if(rps.objList[0]!=null&&rps.objList[0].length>0){
                    column+='<div class="jxxt_zhuanti_rw_tongji_all">';
                    column+='<table border="0" cellpadding="0" cellspacing="0" class="public_tab2" >';
                    column+='<tr>';
                    $.each(rps.objList[0],function(idx,itm){
                        if(idx==0)
                            column+='<th>姓名</th>';
                        column+='<th>任务'+itm.orderidx+'<br>'+itm.taskTypeName+'</th>';
                    });
                    column+='</tr>';
                    column+='<tbody id="tb_data"></tbody>';
                    column+='</table>';
                    column+='</div>';

                }else{
                    column+='<div class="jxxt_zhuanti_rw_tongji_all">';
                    column+='<table border="0" cellpadding="0" cellspacing="0" class="public_tab2" >';
                    column+='<tr><td>暂无数据!</td></tr>';
                    column+='</table>';
                    column+='</div>';
                }
                $("#dv_content").html(column);
                if(rps.objList[1]!=null&&rps.objList[1].length>0){
                    $.each(rps.objList[1],function(idx,itm){
                        data+='<tr>';
                        $.each(itm,function(ix,im){
                           data+='<td>';
                            if(ix==0)
                                data+=im;
                            else{
                                if(typeof im!='undefined'&&im=='1')
                                    data+='√';
                                else
                                    data+='<span class="font-red">－</span>';
                            }
                           data+='</td>';
                        });
                        data+='</tr>';
                    });
                }
                $("#tb_data").html(data);
            }});
    }

function statByGroupId(classid,groupid,groupname,callback){
    if(groupid==null||groupid.toString().length<1){
        alert('系统未获取小组标识!');
        return;
    }
    var url="task?loadStuStatByGroup";
    $.ajax({
        url:url,
        data:{groupid:groupid,subjectid:subjectid,courseid:courseid,classid:classid},
        type:'post',
        dataType:'json',
        error:function(){
            alert('系统未响应!');
        },
        success:function(rps){
            var column='',data='';
            if(rps.objList[0]!=null&&rps.objList[0].length>0){
                column+='<div class="jxxt_zhuanti_rw_tongji_all">';
                column+='<table border="0" cellpadding="0" cellspacing="0" class="public_tab2" >';
                column+='<caption>'+groupname+'</caption>';
                column+='<tr>';
                $.each(rps.objList[0],function(idx,itm){
                    if(idx==0)
                        column+='<th>姓名</th>';
                    column+='<th>任务'+itm.orderidx+'<br>'+itm.taskTypeName+'</th>';
                });
                column+='</tr>';
                column+='<tbody id="tb_data_'+groupid+'"></tbody>';
                column+='</table>';
                column+='</div>';

            }else{
                column+='<div class="jxxt_zhuanti_rw_tongji_all">';
                column+='<table border="0" cellpadding="0" cellspacing="0" class="public_tab2" >';
                column+='<tr><td>暂无数据!</td></tr>';
                column+='</table>';
                column+='</div>';
            }
            $("#dv_content").html(column);
            if(rps.objList[1]!=null&&rps.objList[1].length>0){
                $.each(rps.objList[1],function(idx,itm){
                    data+='<tr>';
                    $.each(itm,function(ix,im){
                        data+='<td>';
                        if(ix==0)
                            data+=im;
                        else{
                            if(typeof im!='undefined'&&im=='1')
                                data+='√';
                            else
                                data+='<span class="font-red">－</span>';
                        }
                        data+='</td>';
                    });
                    data+='</tr>';
                });
            }
            $("#tb_data_"+groupid).html(data);

            if(callback)
                statNoGroup(classid);
        }


    });
}


function statNoGroup(classid){
    if(classid==null||classid.toString().length<1){
        alert('系统未获取班级标识!');
        return;
    }
    var url="task?loadNoGroupStuStat";
    $.ajax({
        url:url,
        data:{subjectid:subjectid,courseid:courseid,classid:classid},
        type:'post',
        dataType:'json',
        error:function(){
            alert('系统未响应!');
        },
        success:function(rps){
            var column='',data='';
            if(rps.objList[0]!=null&&rps.objList[0].length>0){
                column+='<div class="jxxt_zhuanti_rw_tongji_all">';
                column+='<table border="0" cellpadding="0" cellspacing="0" class="public_tab2" >';
                column+='<caption>未分配小组</caption>';
                column+='<tr>';
                $.each(rps.objList[0],function(idx,itm){
                    if(idx==0)
                        column+='<th>姓名</th>';
                    column+='<th>任务'+itm.orderidx+'<br>'+itm.taskTypeName+'</th>';
                });
                column+='</tr>';
                column+='<tbody id="tb_no_group"></tbody>';
                column+='</table>';
                column+='</div>';

            }else{
                column+='<div class="jxxt_zhuanti_rw_tongji_all">';
                column+='<table border="0" cellpadding="0" cellspacing="0" class="public_tab2" >';
                column+='<tr><td>暂无数据!</td></tr>';
                column+='</table>';
                column+='</div>';
            }
            $("#dv_content").append(column);
            if(rps.objList[1]!=null&&rps.objList[1].length>0){
                $.each(rps.objList[1],function(idx,itm){
                    data+='<tr>';
                    $.each(itm,function(ix,im){
                        data+='<td>';
                        if(ix==0)
                            data+=im;
                        else{
                            if(typeof im!='undefined'&&im=='1')
                                data+='√';
                            else
                                data+='<span class="font-red">－</span>';
                        }
                        data+='</td>';
                    });
                    data+='</tr>';
                });
            }
            $("#tb_no_group").html(data);
        }});
}

</script>
</head>
<body>
<div class="subpage_head">
    <span class="back"><a href="javascript:history.go(-1);" >返回</a></span>
    <span class="ico55"></span><strong>专题下全部任务统计</strong>
</div>

<div class="content1">
<div class="jxxt_zhuanti_rw_piyue">
    <h2 class="public_input">
        <select id="sel_cls" >
            <c:if test="${!empty classList}">
                <c:forEach items="${classList}" var="c">
                    <option value="${c.classid}">${c.classgrade}${c.classname}</option>
                </c:forEach>
            </c:if>
        </select>
      </h2>
</div>
    <div id="dv_content">

    </div>
</div>
<%@include file="/util/foot.jsp" %>
</body>
</html>
