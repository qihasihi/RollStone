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
    <script type="text/javascript">

        $(function(){
            //班级自动选中
            <c:if test="${!empty tccClsList}">
                if($("#sel_clsid option").length<=1)
                    $("#sel_clsid").hide();
                $("#sel_clsid option[value='${classid}']").attr("selected",true);
            </c:if>
            //处理个人积分
            <c:if test="${!empty stuMap}">
                <c:if test="${!empty clsDcType&&clsDcType==3}">
                    var wxdf=0;
                    $("td[data-bind='gr_wxdf']").each(function(idx,itm){
                        var h=$(itm).html();
                        if(h!="--"&& h.Trim().length>0){
                            wxdf+=parseInt(h.Trim());
                        }
                    });
                    $("#sp_wxdf").html(wxdf+"分");
                </c:if>
            var wsdf=0;
            $("td[data-bind='gr_wsdf']").each(function(idx,itm){
                var h=$(itm).html();
                if(h!="--"&& h.Trim().length>0){
                    wsdf+=parseInt(h.Trim());
                }
            });
            $("#sp_wsdf").html(wsdf+"分");
                 <c:if test="${!empty stuGroupId}">
                    var xzdf=0;
                    $("td[data-bind='gr_xzdf']").each(function(idx,itm){
                        var h=$(itm).html();
                        if(h!="--"&& h.Trim().length>0){
                            xzdf+=parseInt(h.Trim());
                        }else{
                            xzdf="--";
                            return;
                        }
                    });
                    $("#sp_xzdf").html((xzdf=='--'?xzdf:(xzdf+"分")));
                    </c:if>
          </c:if>
        })

    </script>
</head>
<body>
<div class="subpage_head"><span class="ico19"></span><strong>课堂积分</strong>
        <c:if test="${!empty tccClsList}">
            <strong style="float:right">
                    <select id="sel_clsid" onchange="clsChanged()">
                           <c:forEach items="${tccClsList}" var="tcc">
                               <option value="${tcc.classid}">${tcc.classname}</option>
                           </c:forEach>
                    </select>
                &nbsp;
            </strong>
        </c:if>

</div>
<div class="content1">
<%--<p class="t_c font-black"><strong>课堂总得分：${coursename}</strong><input type="hidden" name="subjectid" id="subjectid" value="${subjectid}"/></p>--%>
<input type="hidden" name="subjectid" id="subjectid" value="${subjectid}"/>
<c:if test="${!empty stuMap}">
<p class="t_c font-black"><strong>课堂总得分：${!empty tuMap.COURSE_TOTAL_SCORE?tuMap.COURSE_TOTAL_SCORE:"--"}</strong><input type="hidden" name="subjectid" id="subjectid" value="${subjectid}"/></p>
<table border="0" cellpadding="0" cellspacing="0" class="public_tab2 public_input">
    <col class="w310"/>
    <col class="w320"/>
    <col class="w310"/>
    <tr>
        <th>类别</th>
        <th>项目</th>
        <th>得分</th>
    </tr>
    <c:if test="${!empty clsDcType&&clsDcType==3}">
        <tr>
            <td rowspan="3" class="v_c">网下表现得分<br><span class="font-red" id="sp_wxdf">10分</span></td>
            <td><p>出勤次数</p></td>
            <td data-bind="gr_wxdf">${stuMap.ATTENDANCENUM}</td>
        </tr>
        <tr class="trbg1">
            <td><p>笑脸个数</p></td>
            <td data-bind="gr_wxdf">${stuMap.SMILINGNUM}</td>
        </tr>
        <tr class="trbg1">
            <td><p>违反纪律次数</p></td>
            <td data-bind="gr_wxdf">${stuMap.VIOLATIONDISNUM}</td>
        </tr>
    </c:if>
    <tr>
        <td rowspan="2" class="v_c">网上得分<br><span class="font-red"  id="sp_wsdf">100分</span></td>
        <td><p>完成任务数量</p></td>
        <td data-bind="gr_wsdf">${!empty stuMap.TASK_SCORE?stuMap.TASK_SCORE:0}</td>
    </tr>
    <tr class="trbg1">
        <td><p>完成专题评价</p></td>
        <td  data-bind="gr_wsdf">${!empty stuMap.COMMENT_SCORE?stuMap.COMMENT_SCORE:0}</td>
    </tr>
    <%--如果没有小组，则没有小组得分--%>
    <c:if test="${!empty stuGroupId}">
        <tr>
            <td rowspan="5" class="v_c">小组得分<br><span class="font-red" id="sp_xzdf">10分</span></td>
            <td><p>本组小红旗总数排全班第一</p></td>
            <td  data-bind="gr_xzdf">${!empty tuMap.GSCORE3?stuMap.GSCORE3:'--'}</td>
        </tr>
        <tr>
            <td><p>组内成员全部出勤且无迟到早退</p></td>
            <td data-bind="gr_xzdf">${!empty tuMap.GSCORE1?tuMap.GSCORE1:'--'}</td>
        </tr>
        <tr class="trbg1">
            <td><p>本组笑脸总数排全班第一</p></td>
            <td data-bind="gr_xzdf">${!empty tuMap.GSCORE2?tuMap.GSCORE2:'--'}</td>
        </tr>
        <tr>
            <td><p>本组违反纪律次数排全班第一</p></td>
            <td data-bind="gr_xzdf">${!empty tuMap.GSCORE4?tuMap.GSCORE4:'--'}</td>
        </tr>
        <tr class="trbg1">
            <td><p>本组完成网上任务平均数排全班第一</p></td>
            <td data-bind="gr_xzdf">${!empty tuMap.GSCORE5?tuMap.GSCORE5:'--'}</td>
        </tr>
    </c:if>
</table>
</c:if>

    <table border="0" cellpadding="0" cellspacing="0" class="public_tab2 public_input">
        <c:if test="${!empty clsDcType&&clsDcType==3}">
            <colgroup class="w350"></colgroup>
            <colgroup span="6" class="w110"></colgroup>
        </c:if>
        <c:if test="${empty clsDcType||clsDcType!=3}">
            <colgroup class="w350"></colgroup>
            <colgroup span="3" class="w110"></colgroup>
        </c:if>

        <tr>
            <%//只有是 clsDcType==3时，为爱课堂班级，显示小组名称，及出勤，笑脸，违反纪律%>
            <th>小组名称</th>
            <th>学号</th>
            <th>学生姓名</th>
            <th>网上得分</th>
             <c:if test="${!empty clsDcType&&clsDcType==3}">
                <th>出勤</th>
                <th>笑脸</th>
                <th>违反纪律</th>
            </c:if>
        </tr>
        <tbody id="d_body">

        </tbody>

    </table>
    <p class="t_r p_b_10" id="p_btnSub"><a href="javascript:;" onclick="doSub()" class="an_small">提交</a></p>
    <script type="text/javascript">
        var courseid=${courseid};
        var classid=${classid};
        var classtype=${classtype};
        var subjectid=${subjectid};
        var groupidstr="${leanderGrpid}";
        var ctumid="-1";//暂不启用//"${!empty ctUid?ctUid:''}";
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
                    h+='${empty dlm.GROUP_NAME?"未分组":dlm.GROUP_NAME}';
                    <c:if test="${!empty clsDcType&&clsDcType==3}">
                            <c:if test="${!empty dlm.GROUP_ID}">
                                h+='<br><span class="ico78"></span> <a href="javascript:;" onclick="updateGroupAward(${dlm.GROUP_ID},${dlm.GROUPSUBMIT_FLAG})" id="a_award_${dlm.GROUP_ID}"><span id="sp_grp_award${dlm.GROUP_ID}">${dlm.AWARD_NUMBER}</span></a>个';
                                h+='<br/>';
                                <c:if test="${!empty dlm.GSCORE1&&dlm.GSCORE1!=0}"><%//分数+1：组内成员全部出勤且无迟到早退%>
                                h+='<span class="ico86">出勤</span>';
                                </c:if>
                                <c:if test="${!empty dlm.GSCORE1&&dlm.GSCORE2!=0}"><%//分数+3：本组笑脸总数排全班第一%>
                                h+='<span class="ico86">笑脸</span>';
                                </c:if>
                                <c:if test="${!empty dlm.GSCORE1&&dlm.GSCORE3!=0}"><%//分数+3：本组小红旗总数排全班第一%>
                                h+='<span class="ico86">红旗</span>';
                                </c:if>
                                <c:if test="${!empty dlm.GSCORE1&&dlm.GSCORE4!=0}"><%//分数-1：本组违反纪律次数排全班第一%>
                                 h+='<span class="ico87">违纪</span>';
                                </c:if>
                                <c:if test="${!empty dlm.GSCORE1&&dlm.GSCORE5!=0}"><%//分数+3：本组完成网上任务完成率（小组任务完成率）排全班第一%>
                                h+='<span class="ico86">任务</span>';
                                </c:if>
                            </c:if>
                    </c:if>
                    h+='</td><td>${dlm.STU_NO}</td>';
                    h+='<td>${dlm.STU_NAME}</td>';
                    h+='<td  style="color:gray">${dlm.WSSCORE}</td>';

                    <c:if test="${!empty clsDcType&&clsDcType==3}">

                            var subFlag1=${dlm.SUBMIT_FLAG};
                            if((groupidstr.length<1||(groupidstr.indexOf(",${dlm.GROUP_ID},")!=-1&&ctumid!=${dlm.USER_ID}))&&subFlag1==0){
                                    h+='<td name="td_data">${dlm.ATTENDANCENUM}<input type="hidden" value="attendanceNum"/></td>';
                                    h+='<td name="td_data">${dlm.SMILINGNUM}<input type="hidden" value="similingNum"/></td>';
                                    h+='<td name="td_data">${dlm.VIOLATIONDISNUM}<input type="hidden" value="violationDisNum"/></td>';
                            }else{
                                h+='<td style="color:gray">${dlm.ATTENDANCENUM}<input type="hidden" value="attendanceNum"/></td>';
                                h+='<td style="color:gray">${dlm.SMILINGNUM}<input type="hidden" value="similingNum"/></td>';
                                h+='<td style="color:gray">${dlm.VIOLATIONDISNUM}<input type="hidden" value="violationDisNum"/></td>';
                            }
                    </c:if>
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
                        h+='<td  style="color:gray">${dl1m.WSSCORE}</td>';
                        <c:if test="${!empty clsDcType&&clsDcType==3}">
                            var subFlag=${dl1m.SUBMIT_FLAG};
                             if((groupidstr.length<1||(groupidstr.indexOf(",${dl1m.GROUP_ID},")!=-1&&ctumid!=${dl1m.USER_ID}))&&subFlag==0){
                                h+='<td name="td_data">${dl1m.ATTENDANCENUM}<input type="hidden" value="attendanceNum"/></td>';
                                h+='<td name="td_data">${dl1m.SMILINGNUM}<input type="hidden" value="similingNum"/></td>';
                                h+='<td name="td_data">${dl1m.VIOLATIONDISNUM}<input type="hidden" value="violationDisNum"/></td>';
                             }else{
                                h+='<td style="color:gray">${dl1m.ATTENDANCENUM}<input type="hidden" value="attendanceNum"/></td>';
                                h+='<td style="color:gray">${dl1m.SMILINGNUM}<input type="hidden" value="similingNum"/></td>';
                                h+='<td  style="color:gray">${dl1m.VIOLATIONDISNUM}<input type="hidden" value="violationDisNum"/></td>';
                             }
                        </c:if>
                        h+='</tr>';
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
            * 事件绑定
             */
            $("td[name='td_data']").bind("click",tdDataClick);
            if($("td[name='td_data']").length<1){
                $("#p_btnSub").remove();
            }

            var trJqObj=$("tr[id*='tr_']");
            if(trJqObj.length==1&&trJqObj.children("td:first").html().Trim().indexOf("未分组")!=-1){
                $("table:last colgroup:first").remove();
                $("table:last colgroup:first").attr("class","w350");
                $("table:last tr:first th:first").remove();
                trJqObj.each(function(idx,itm){
                   $(itm).children("td:first").hide();
                });
            }
        })
        /**
        *生成奖励框
         */
        function updateGroupAward(groupid,adminflag){
            <%--<%if(isStudent){%>--%>
            <%--return;--%>
            <%--<%}%>--%>
            if(typeof(adminflag)=="undefined"||adminflag==1){
                return;
            }
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
                var p={courseid:cid,groupid:groupid,awardNumber:this.value.Trim(),subjectid:subid,classid:${classid},clstype:${classtype}};
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

                $("a[id='a_award_"+groupid+"']").bind("click",function(){updateGroupAward(groupid,adminflag);});
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
                var p={userid:uid,groupid:groupid,courseid:cid,subjectid:subid,classid:${classid},clstype:${classtype}};
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
        /**
        *提交
         */
        function doSub(){
            var t_courseid=courseid;
            var t_classid=classid;
            var t_classtype=classtype;
            var t_subjectid=subjectid;
            //得到小组的JSON
            var jshtml='';
            $("tr[id*='tr_']").each(function(idx,itm){
                var trVal=itm.id.replace("tr_","");
                if(trVal!=null&&trVal.length>0){
                    var val=$("#sp_grp_award"+trVal).html().Trim();
                    if(val.length>0){
                        jshtml+=',"'+trVal+'":'+val;
                    }
                }
            });
            if(jshtml.length>0){
                jshtml=jshtml.substring(1);
                jshtml='{'+jshtml+'}';
            }

            if(!confirm("您确定编辑完学生在此课中的网下积分吗?\n\n提示：提交后将不能更新，小组组长提交后，教师也无法进行更改!"))
                return;
            var p={};
            if(jshtml.length>0){
                p.groupJson=jshtml;
            }
            //url
            var url="clsperformance/roleSub/"+t_courseid+"/"+t_classid+"/"+t_classtype+"/"+t_subjectid+"/sub";
            $.ajax({
                url:url,
                data:p,
                type:'post',
                dataType:'json',
                error:function(){
                    alert('网络异常！');
                },
                success:function(rps){
                    if(rps.type!='success'){
                        alert(rps.msg);
                    }else{
                        history.go(0);
                    }
                }
            });

        }

    function clsChanged(){
        var tclsid=$("#sel_clsid").val();
        if(tclsid.Trim().length>0){
            var url="clsperformance?m=toIndex&courseid=${params.courseid}&classtype=${params.classtype}&subjectid=${params.subjectid}&termid=${params.termid}&classid=${classid}";
            location.href=url;
        }
    }

    </script>
</div>
<%@include file="/util/foot.jsp"%>
</body>
</html>
