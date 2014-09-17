<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%@include file="/util/common-jsp/common-jxpt.jsp"%>


<html>
	<head>
		<title>${sessionScope.CURRENT_TITLE}</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
	<script type="text/javascript" src="js/teachpaltform/group.js"></script>
        <script type="text/javascript" src="js/teachpaltform/tchcourselist.js"></script>
<script type="text/javascript">
    var currtenTab=1; //当前学科导航栏页数
    var tabSize=6; //学科导航栏每页显示数量
    var isTrust="${isTrust}";
    var subjectid="${subGradeInfo.subjectid}";
    var gradeid="${subGradeInfo.gradeid}";
    var subjectname='';
    var gradename='';

    var isLession="${userType}";
    var termid="${selTerm.ref}";
    var currtterm="${currtTerm.ref}";
    var teacherid="${teacherid}";
    var global_gradeid=0;
    var global_subjectid=0;

    var currentSubjectid=subjectid;
    var currentGradeid=gradeid;


    $(function(){

        timeTmp();//课程积分，记时器
        <c:if test="${! empty classes}">
            $("#classId").val('${classes[0].classid}');
            $("#classType").val(1);
            $("#dcType").val('${classes[0].dctype}');


        if(isLession==2){
            $("#a_addGroup").hide();
            $("#dv_addGroup").hide();
            $("#group_list").hide();
            $("#a_course").parent().hide();
            $("#h2_lession").hide();
        }else{
            $("#a_addGroup").show();
            $("#dv_addGroup").show();
            $("#group_list").show();
            $("#a_course").parent().show();
        }

        if($("#dcType").val()>1&&isLession>1){
            $("#a_addStudent").show();
            if(isLession==2){
                $("#dv_stu").show();
                getStuList('${classes[0].classid}');
            }
        }else{
            $("#a_addStudent").hide();
            $("#dv_stu").hide();
        }


        if(isLession!=2){
            getNoGroupStudentsByClassId('${classes[0].classid}',1);
            getClassGroups('${classes[0].classid}',1);
        }
        </c:if>
        loadClsCourse();
        if(isLession==2){
            $("#sp_subgrade").remove();
            $("#a_course").parent().hide();

        }

        $("a[name='h2a']").parent().each(function(idx,itm){
            if(idx==0){
                $(itm).click(function(){
                    $("#dv_coursescore").hide();
                    $("#dv_lession").hide();
                    $("#dv_manage_stu").show();
                    $(itm).siblings().removeClass('crumb').end().addClass('crumb');
                })

            }else if(idx==1){
                $(itm).click(function(){
                    $("#dv_coursescore").hide();
                    $("#dv_lession").show();
                    $("#dv_manage_stu").hide();
                    $(itm).siblings().removeClass('crumb').end().addClass('crumb');
                    getClassCourseList();
                })

            }else if(idx==2){       //课程积分
//                $(itm).click(function(){
//                    $("#dv_lession").hide();
//                    $("#dv_manage_stu").hide();
//                    $(itm).siblings().removeClass('crumb').end().addClass('crumb');
//
//                    //积分显示
//                    $(".jifen").show();
//                    if(courseScoreClsId==null||courseScoreClsId!=$("#classId").val().Trim()){
//                        $("#dv_coursescore").html('');
//                        $(".jifen li").removeClass("crumb");
//                    }
//                    $("#dv_coursescore").show();
////                    getClassCourseList();
//                })
            }
        });

        $("#a_course").attr("href",'teachercourse?toTeacherCourseList&termid='+termid+'&subjectid='+subjectid+'&gradeid='+gradeid+'');
        $("#a_calendar").attr("href",'teachercourse?toTeacherCalendarPage&termid='+termid+'&subjectid='+subjectid+'&gradeid='+gradeid+'');
    });
    function loadClsCourse(){
        //加载学科
            courseScoreClsId=$("#classId").val().Trim();
            $.ajax({
                url:'teachercourse?m=getCourseSubByClsId',
                data:{classid:courseScoreClsId,termid:termid},
                type:'post',
                dataType:'json',
                error:function(){
                    alert('异常错误,系统未响应！');
                },success:function(rps){
                    if(rps.type!="success"){
                        alert(rps.msg);return;
                    }
                    var firstSub=null;
                    $(".jifen").html('');
                    $.each(rps.objList,function(ix,im){
                        if(ix==0)
                            firstSub=im.subjectid;
                        if($("#jifen_"+im.subjectid).length<1){
                            var h='<li id="jifen_'+im.subjectid+'"><a href="javascript:;" data-bind="loadCourseScore('+im.subjectid+',classId.value,termid,1)"><span>'+im.subjectname+'</span></a></li>';
                            $(".jifen").append(h);
                        }
                    });
                    //绑定事件
                    $(".jifen li[id*=jifen_]").click(function(){
                        $("#dv_lession").hide();
                        $("#dv_manage_stu").hide();
//                        $("#a_calendar").siblings().removeClass('crumb').end().addClass('crumb');
                        //$("#a_calendar").parent().parent().children("li").removeClass('crumb');
                       // $("#a_calendar").addClass('crumb');
                        $("a[name='h2a']").parent().siblings("h2").removeClass('crumb').last().addClass('crumb');
                        eval($(this).children("a").attr("data-bind"));
                    });

//                    if(firstSub!=null)      //默认加载
//                        loadCourseScore(firstSub,classId.value,termid,1);

                }
            });
    }





    function setClsId(clsid,clstype,dctype,relationtype,clsname){
        $("#clsname").html(clsname);
        displayObj('ul_banji',false);
        $("#classId").val(clsid);
        $("#classType").val(clstype);
        $("#dcType").val(dctype);
        var clsid=$("#classId").val();
        checkRelationType(clsid);

        if(isLession==2){ //班主任
            $("#a_addGroup").hide();
            $("#dv_addGroup").hide();
            $("#group_list").hide();
            $("#a_course").parent().hide();
            $("#h2_lession").hide();
        }else{
            $("#a_addGroup").show();
            $("#dv_addGroup").show();
            $("#group_list").show();
            $("#a_course").parent().show();
            $("#h2_lession").show();
        }


        if(dctype>1&&isLession>1){
            $("#a_addStudent").show();
            if(isLession==2){
                $("#dv_stu").show();
                getStuList(clsid);
            }
        }else{
            $("#a_addStudent").hide();
            $("#dv_stu").hide();
        }
    }


    function checkRelationType(clsid){
        if(typeof clsid=='undefined')
            return;

        $.ajax({
            url:'group?checkRelationType',
            data:{classid:clsid},
            type:'post',
            dataType:'json',
            error:function(){
                alert('异常错误,系统未响应！');
            },success:function(rps){
                if(rps.type=="success"){
                    if(rps.objList[0]!=null)
                        isLession=rps.objList[0];

                    if(isLession==2){
                        $("#a_addGroup").hide();
                        $("#dv_addGroup").hide();
                        $("#group_list").hide();
                    }else{
                        $("#a_addGroup").show();
                        $("#dv_addGroup").show();
                        $("#group_list").show();
                    }

                    if($("#dcType").val()>1&&isLession>1){
                        $("#a_addStudent").show();
                        if(isLession==2){
                            $("#dv_stu").show();
                            getStuList(clsid);
                        }
                    }else{
                        $("#a_addStudent").hide();
                        $("#dv_stu").hide();
                    }
                }
            }
        });
    }





    //计时，防止无限刷新
    function timeTmp(){
        if(!allowLoadTeaCourseScore)
             allowLoadTeaCourseScore=true;
        setTimeout("timeTmp()",1500);
    }
    var courseScoreClsId=null;

    //课程积分
    var allowLoadTeaCourseScore=true;
    function loadCourseScore(subjectid,classid,termid,sort){
        if(!allowLoadTeaCourseScore){alert('请勿频繁刷新!');return;}
       // timeTmp();
        courseScoreClsId=classid;
        allowLoadTeaCourseScore=false;
        $(".content2 .jxxt_banji_layoutR").hide();
        $(".jifen li").removeClass("crumb");
        $(".jifen li[id='jifen_"+subjectid+"']").addClass("crumb");
        $("#dv_coursescore").show();
        if(typeof(sort)=="undefined")
            sort=1;
        $("#dv_coursescore").load("clsperformance?m=toTeaCourseScore&subjectid="+subjectid+"&classid="+classid+"&termid="+termid+"&sort="+sort+"");
    }
</script>
</head>
    <body>
    <%@include file="/util/head.jsp" %>
    <%@include file="/util/nav-base.jsp" %>


    <div id="nav">
        <ul>
             <li><a href="javascript:;" id="a_course">教学组织</a></li>
             <li class="crumb"><a href="javascript:;" id="a_clsid" >班级管理</a></li>
            <li><a href="javascript:;"  id="a_calendar">课程日历</a></li>
        </ul>
    </div>
    <div class="content2">
        <!--积分-->
        <div  class="jxxt_banji_layoutR" id="dv_coursescore" style="display:none">
        </div>
        <div  class="jxxt_banji_layoutR" id="dv_manage_stu">
            <h1>学员管理</h1>
            <div class="jxxt_banji_xzgl">
                <p class="font-darkblue">
                    <a id="a_addGroup" name="a_hide" href="javascript:showModel('addGroupDiv',false);"><span class="ico26"></span>新建小组</a>&nbsp;&nbsp;&nbsp;&nbsp;
                    <a id="a_addStudent" href="javascript:loadWXStudent()"><span class="ico26"></span>学员管理</a>
                </p>

                    <div class="jxxt_banji_xzgl_text" id="dv_addGroup">
                        <p class="font-black">未分配成员：</p>
                        <ul id="noGroupStudents">
                            <li>李小</li>
                            <li>王大天使<a href="1" class="ico_delete" title="删除"></a></li>
                            <li>周好人</li>
                            <li>李小</li>
                            <li>王大</li>
                            <li>周好人人</li>
                            <li>李小</li>
                            <li>王大</li>
                            <li>周好人</li>
                            <li>李小</li>
                            <li>王大</li>
                            <li>白仙仙人</li>
                        </ul>
                        <p class="p_t_10"><a name="a_hide" href="javascript:showModel('selectStudent_Div');"  class="an_public3">分配到组</a></p>
                    </div>
                    <table id="group_list" border="0" cellpadding="0" cellspacing="0" class="public_tab2 hover">

                    </table>


                    <div class="jxxt_banji_xzgl_text" id="dv_stu" style="display: none;">
                        <p class="font-black">班级成员：</p>
                        <ul id="stuList">

                        </ul>
                    </div>
            </div>
        </div>

        <div style="display: none;" class="jxxt_banji_layoutR" id="dv_lession">
            <h1 >课程表</h1>
            <table border="0" cellpadding="0" cellspacing="0" class="public_tab2">
                <col class="w550"/>
                <col class="w200"/>
                <tr>
                    <th>专题名称</th>
                    <th>开始时间</th>
                </tr>
                <tbody id="clsCourseList">

                </tbody>
                <!--
                <tr>
                    <td><p class="ico"><b class="ico16" title="自建"></b><a href="1">我读小说我读小说我读小说我读小说我读小说</a></p></td>
                    <td>2013-11-03 12:30</td>
                </tr>
                <tr class="trbg1">
                    <td><p class="ico"><b class="ico17" title="共享"></b><a href="1">我读小说我读小说我读小说我读小说我读小说是我喜欢的好好学习吧长了会怎么样呢，试试吧。好吧。吧长了会怎么样呢，试试吧。好吧。</a></p></td>
                    <td>2013-11-03 12:30</td>
                </tr>
                <tr>
                    <td><p class="ico"><b class="ico18" title="标准" ></b><a href="1">我读小说我读小说我读小说我读小说我读小说</a></p></td>
                    <td>2013-11-03 12:30</td>
                </tr>
                -->
            </table>
        </div>


        <div class="jxxt_banji_layoutL public_input">
            <c:if test="${!empty classes}">
            <h1><span id="clsname">${classes[0].classgrade}${classes[0].classname}</span><a href="javascript:displayObj('ul_banji');" class="ico49a"></a></h1>
                <ul class="banji" style="display: none;" id="ul_banji">
                    <c:forEach items="${classes}" var="c">
                        <li><a href="javascript:setClsId('${c.classid}',1,'${c.dctype}','${c.relationtype}','${c.classgrade}${c.classname}');getNoGroupStudentsByClassId('${c.classid}',1);getClassGroups('${c.classid}',1);getClassCourseList();loadClsCourse();">${c.classgrade}${c.classname}</a></li>
                    </c:forEach>
                </ul>
            </c:if>

            <input type="hidden" id="classId"/> <input type="hidden" id="classType"/><input type="hidden" id="dcType"/>

            <h2 class="crumb"><a name="h2a" href="javascript:;">学员管理</a></h2>
            <h2 id="h2_lession"><a name="h2a" href="javascript:;" >课程表</a></h2>
            <h2 ><a name="h2a" href="javascript:;" data-bind="course_score">课程积分</a></h2>
            <ul class="jifen">
            </ul>
        </div>
        <div class="clear"></div>
    </div>




    <span id="no_gs_bk" style="display:none">
    </span>
    <%@include file="/util/foot.jsp" %>
    </body>
</html>


<div class="public_windows public_input" id="dv_add_student" style="display: none;">
    <h3><a href="javascript:closeModel('dv_add_student')"  title="关闭"></a>学员管理</h3>
    <p class="p_tb_10">&nbsp;&nbsp;&nbsp;姓名：<input name="textfield5" id="txt_stuName" type="text" class="w140"/><a href="javascript:;" id="a_stuName" class="ico57" title="查询"></a></p>
    <div class="jxxt_float_bjgl">
        <p class="font-black"><a href="javascript:;" onclick="setAllStudent()" class="font-darkblue">全选</a>未添加成员：</p>
        <ul id="ul_wx_stu">
            <!--<li><span class="ico85a"></span>王小小</li>-->
        </ul>
    </div>
    <div class="jxxt_float_bjgl">
        <p class="font-black"><a href="javascript:;" onclick="reSetStudent()"  class="font-darkblue">清空</a>已添加成员：</p>
        <ul id="ul_cls_stu">
            <!--<li><span class="ico85b"></span>王小小</li>-->
        </ul>
    </div>
    <p class="t_c"><a href="javascript:doAddClsStudent();" id="a_sub_student"  class="an_public1">确&nbsp;定</a>&nbsp;&nbsp;<a href="javascript:closeModel('dv_add_student')"  class="an_public1">取&nbsp;消</a></p>
</div>




<div id="updateGroupName" style="margin: 5px;background-color: white" class="white_content"  >
    <input id="uGroupID" type="hidden" value="" />
    输入新组名：<input id="editGN" type="text" />
    <input type="button" value="修改" onclick="updateGroup();"/>
    <input type="button" value="返回" onclick="closeModel('updateGroupName');"/>
</div>

<div id="addGroupDiv" class="public_windows public_input" style="display:none;">
    <h3><a href="javascript:closeModel('addGroupDiv');" title="关闭"></a>新建小组</h3>
    <table border="0" cellpadding="0" cellspacing="0" class="public_tab1 ">
        <col class="w80"/>
        <col class="w320"/>
        <tr>
            <th>小组名称：</th>
            <td><input id="groupName" name="groupName" type="text" class="w300"/></td>
        </tr>
        <tr>
            <th>&nbsp;</th>
            <td><a href="javascript:addNewGroup();" class="an_public1">确&nbsp;定</a></td>
        </tr>
    </table>
</div>

<div id="changeGroupDiv" class="public_windows" style="display:none;">
    <input type="hidden" id="gs_ref" value=""/>
    <h3><a href="javascript:closeModel('changeGroupDiv');" title="关闭"></a>小组调整</h3>
    <table border="0" cellpadding="0" cellspacing="0" class="public_tab1 public_input">
        <col class="w80"/>
        <col class="w320"/>
        <tr>
            <th>调组到：</th>
            <td><select name="changeGroups" id="changeGroups">
            </select>
            </td>
        </tr>
        <tr>
            <td>&nbsp;</td>
            <td><input type="radio" id="isleader" name="isleader" value="1">
                指定为组长</td>
        </tr>
        <tr>
            <th>&nbsp;</th>
            <td><a href="javascript:changeStuGroup();" class="an_public1">提&nbsp;交</a></td>
        </tr>
    </table>
</div>

<div id="selectStudent_Div" class="public_windows public_input" style="display:none;">
    <h3><a href="javascript:closeModel('selectStudent_Div');" title="关闭"></a>分配到组</h3>
    <div class="jxxt_float_fpdzR">
        <p class="font-black">分配到</p>
        <ul id="forGroupList"></ul>
        <p><a href="javascript:void(0);" onclick="addStudentsToGroup();" class="an_public1">确&nbsp;定</a>&nbsp;&nbsp;<a href="javascript:closeModel('selectStudent_Div');" class="an_public1">取&nbsp;消</a></p>
    </div>
    <div class="jxxt_float_fpdzL">
        <p class="font-black">未分配人员</p>
        <p><input id="stu_name" type="text" value="" class="w120"/><a href="javascript:filterResult();" class="ico57" title="查询"></a></p><br/>
        <select id="no_gs" size="10" style="height: 200px;width: 150px;overflow-y:auto;"  multiple="multiple"></select>
    </div>

</div>
