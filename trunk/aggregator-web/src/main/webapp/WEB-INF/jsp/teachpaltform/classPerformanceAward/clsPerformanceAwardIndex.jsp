<%--
  Created by IntelliJ IDEA.
  User: zhengzhou
  Date: 14-6-27
  Time: 上午10:23
  To change this template use File | Settings | File Templates.
--%>

<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@include file="/util/common-jsp/common-jxpt.jsp" %>
<html>
<head>
</head>
<body>
<div class="subpage_head"><span class="ico19"></span><strong>课堂表现</strong></div>
<div class="content1">
    <p class="t_c font-black"><strong>专题名称：${coursename}</strong><input type="hidden" name="subjectid" id="subjectid" value="${subjectid}"/></p>
    <table border="0" cellpadding="0" cellspacing="0" class="public_tab2 public_input">
        <colgroup class="w400"></colgroup>
        <colgroup span="5" class="w110"></colgroup>
        <tr>
            <th>小组名称</th>
            <th>学号</th>
            <th>学生姓名</th>
            <th>出勤</th>
            <th>笑脸</th>
            <th>违反纪律</th>
        </tr>
        <tbody id="d_body">

        </tbody>

    </table>

    <script type="text/javascript">
        var courseid=${courseid};
        $(function(){
            <c:if test="${empty subjectid}">
              alert('参数异常，请重试!');
                this.close();
            </c:if>
            <c:if test="${!empty dataListMap}">
            var h='';
                    <c:forEach items="${dataListMap}" var="dlm" varStatus="dlmidx">
                    h='<tr id="tr_${dlm.GROUP_ID}" >';
                    h+='<td rowspan=1>';
                    h+='<input type="hidden" name="group_id" id="hd_group_id" value="${dlm.GROUP_ID}"/>';
                    h+='<input type="hidden" name="user_id" id="hd_user_id" value="${dlm.USER_ID}"/>';
                    h+='${dlm.GROUP_NAME}<br><span class="ico78"></span> <a href="javascript:;" onclick="updateGroupAward(${dlm.GROUP_ID})" id="a_award_${dlm.GROUP_ID}"><span id="sp_grp_award${dlm.GROUP_ID}">${dlm.AWARD_NUMBER}</span></a>个</td>';
                    h+='<td>${dlm.STU_NO}</td>';
                    h+='<td>${dlm.STU_NAME}</td>';
                    h+='<td name="td_data">${dlm.ATTENDANCENUM}<input type="hidden" value="attendanceNum"/></td>';
                    h+='<td name="td_data">${dlm.SMILINGNUM}<input type="hidden" value="similingNum"/></td>';
                    h+='<td name="td_data">${dlm.VIOLATIONDISNUM}<input type="hidden" value="violationDisNum"/>';
                    h+='</tr>';
                    if($("tr[id='tr_${dlm.GROUP_ID}']").length<1){
                        $("#d_body").append(h);
                    }
                    </c:forEach>
                    <c:forEach items="${dataListMap}" var="dl1m">
                        h='<tr><td>${dl1m.STU_NO}';
                        h+='<input type="hidden" name="group_id" id="hd_group_id" value="${dl1m.GROUP_ID}"/>';
                        h+='<input type="hidden" name="user_id" id="hd_user_id" value="${dl1m.USER_ID}"/>';
                        h+='</td><td>${dl1m.STU_NAME}</td>';
                        h+='<td name="td_data">${dl1m.ATTENDANCENUM}<input type="hidden" value="attendanceNum"/></td>';
                        h+='<td name="td_data">${dl1m.SMILINGNUM}<input type="hidden" value="similingNum"/></td>';
                        h+='<td name="td_data">${dl1m.VIOLATIONDISNUM}<input type="hidden" value="violationDisNum"/></td></tr>';
                        var ulen=$("input[name='user_id']").filter(function(){return this.value==${dl1m.USER_ID}}).length;
                        if($("tr[id='tr_${dl1m.GROUP_ID}']").length>0&&ulen<1){
                            var rowspan=$("#tr_${dl1m.GROUP_ID} td:first").attr("rowspan");
                            //$("#tr_$ );
                            $("#tr_${dl1m.GROUP_ID} td:first").attr("rowspan",parseInt(rowspan)+1);
                            $("#tr_${dl1m.GROUP_ID}").after(h);
                        }
                    </c:forEach>
            </c:if>
            /**
            *
             */
            <%if(!isStudent){%>
            $("td[name='td_data']").bind("click",tdDataClick);
            <%}%>
        })
        /**
        *生成奖励框
         */
        function updateGroupAward(groupid){
            <%if(isStudent){%>
            return;
            <%}%>
            var cid=courseid;
            $("a[id='a_award_"+groupid+"']").attr("onclick","");
            $("a[id='a_award_"+groupid+"']").unbind("click");
            var h='<input name="txt_update_num" type="text"' +
                    ' id="txt_update_num" maxlength="4" class="w40" value="'+$("#sp_grp_award"+groupid).html().Trim()+'"/>';
            $("span[id='sp_grp_award"+groupid+"']").html(h);
            $("#txt_update_num").focus();

            $('input[name="txt_update_num"]').bind("keyup afterpaste",function(){
                this.value=this.value.replace(/\D/g,'');
            });


            $("#txt_update_num").bind("blur",function(){
                if(this.value.Trim().length<1||isNaN(this.value.Trim())){
                    $("#txt_update_num").focus();return;
                }
                var subid=$("#subjectid").val().Trim();
                var p={courseid:cid,groupid:groupid,awardNumber:this.value.Trim(),subjectid:subid};
                $.ajax({
                    url:'clsperformance?m=doAddOrUpdateGroup',
                    data:p,
                    type:'post',
                    dataType:'json',
                    error:function(){
                        alert('网络异常！');
                    },
                    success:function(rps){
                        if(rps.type!='success'){
                            alert(rps.msg);
                        }
                    }
                });
                //更新数据库
                $(this).parent().html(this.value.Trim());

                $("a[id='a_award_"+groupid+"']").bind("click",function(){updateGroupAward(groupid);});
            });
        }


        function tdDataClick(){
            $(this).unbind("click")
            var uid=$(this).parent().children().first().children("input[name='user_id']").val();
            var groupid=$(this).parent().children().first().children("input[name='group_id']").val();
            var type=$(this).children("input[type='hidden']").val();
            var cid=courseid;
            //alert(uid+"   "+groupid+"  "+this.innerText+" "+type);
            //生成数据
            var t=this.innerText.replace(/\-/g,'');
            if(type=="violationDisNum")
                t=this.innerText.replace(/\-/g,'');
            var h='<input name="txt_update_num" type="text" id="txt_update_num" maxlength="4" class="w40" value="'+t+'"/>';
            $(this).html(h);
            $("#txt_update_num").focus();

            $("#txt_update_num").bind("keyup afterpaste",function(){
                this.value=this.value.replace(/\D/g,'');
            });


            $("#txt_update_num").bind("blur",function(){
                if(this.value.Trim().length<1||isNaN(this.value.Trim())){
                    $("#txt_update_num").focus();return;
                }
                if(type=="violationDisNum")
                    this.value="-"+this.value;
                var subid=$("#subjectid").val().Trim();
                //更新数据库
                var p={userid:uid,groupid:groupid,courseid:cid,subjectid:subid};
                eval("(p."+type+"="+this.value.Trim()+")");
                $.ajax({
                    url:'clsperformance?m=doAddOrUpdate',
                    data:p,
                    type:'post',
                    dataType:'json',
                    error:function(){
                        alert('网络异常！');
                    },
                    success:function(rps){
                        if(rps.type!='success'){
                            alert(rps.msg);
                        }
                    }
                });
               // alert(uid+"  "+groupid+"   "+type+"   "+cid+"   "+this.value);
                $(this).parent().bind("click",tdDataClick);
                $(this).parent().html(this.value+'<input type="hidden" value="'+type+'"/>');
            });
        }
    </script>
</div>
<%@include file="/util/foot.jsp"%>
</body>
</html>
