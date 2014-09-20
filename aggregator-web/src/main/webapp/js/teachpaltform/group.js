function addNewGroup(){
    var classId=$("#classId").val();
    var classType=$("#classType").val();
    var groupName=$("#groupName").val();


    if(groupName.Trim().length<1){
        alert('请输入小组名称!');
        return;
    }
    if(groupName.length>50){
        alert('小组名称限50字以内!');
        return;
    }
    if(subjectid.Trim().length<1){
        alert('系统未获取到学科标识!');
        return;
    }

    var dat={groupname:groupName,classid:classId,classtype:classType,subjectid:subjectid};
    $.ajax({
        url:'group?m=addNewGroup',
        data:dat,
        type:'post',
        dataType:'json',
        error:function(){
            alert('异常错误,系统未响应！');
        },success:function(rps){
            if(rps.type=="success"){
                alert("添加成功!");
                $("#groupName").val('');
                closeModel('addGroupDiv');
                getClassGroups(classId,classType);
            }else if(rps.type=="errorname"){
                alert("小组名已存在!");
            }else{
                alert(rps.msg+",无法添加!");
            }
        }
    });
}

function addStudentsToGroup(){
    var groupId=$("input[name=forGroupId]:checked").val();
    if(groupId==0||groupId==null){
        alert("请选择小组！");
        return;
    }
    var stusId =$("#no_gs").val();
    if(stusId==null || stusId.join(",").length==0){
        alert("请选择学生！");
        return;
    }
    var dat={groupid:groupId,stusId:stusId.join(",")};
    $.ajax({
        url:'group?m=addStudentsToGroup',
        data:dat,
        type:'post',
        dataType:'json',
        error:function(){
            alert('异常错误,系统未响应！');
        },success:function(rps){
            if(rps.type=="success"){
                alert(rps.msg);
                closeModel("selectStudent_Div");
                getNoGroupStudentsByClassId($("#classId").val(),1,$("#dcType").val());
                getClassGroups($("#classId").val(),1);
            }else{
                alert(rps.msg);
            }
        }
    });
}

function showNoGroupStudent(type){
    if(type){
        $("#studentSelectArea").show();
    }else{
        $("#studentSelectArea").hide();
    }
}

function getNoGroupStudentsByClassId(classId,classType,dctype){
    if(typeof classId=='undefined'|| typeof classType=='undefined')
        return;
    $("#noGroupStudents").html("");
    var url="group?m=getNoGroupStudents";
    $.post(url,{classId:classId,classType:classType,subjectid:subjectid},
        function(responseText){
            if(responseText==null||responseText.objList==null){
                //没有数
                $("#noGroupStudents").append("<li>无</li>");
                $("#no_gs").html();
                return;
            }
            $("#no_gs").html("");

            if(responseText.objList[0]!=null&&responseText.objList[0].length>0){
                $("#dv_addGroup").show();
                $.each(responseText.objList[0],function(idx,itm){
                    var h='<li>'+itm.STU_NAME;
                    if(typeof responseText.objList[1]!='undefined'&&responseText.objList[1]==3&&dctype>1)
                        h+='<a class="ico_delete" style="display: none;" title="删除"  href="javascript:delClassUser(\''+itm.REF+'\')"></a>';
                    if(dctype==1)
                        h+='<a style="display: none;" name="view_pass" class="ico92" title="查看密码" href="javascript:;"></a>';
                    h+='<span style="display: none;" class="password" name="pass">'+itm.PASSWORD+'</span>';
                    h+='</li>';
                    $("#noGroupStudents").append(h);
                    $("#no_gs").append("<option value='"+itm.USER_ID+"' >"+itm.STU_NAME+"</option>");
                });
            }else
                $("#dv_addGroup").hide();

           /* if(responseText.objList[0]!=null&&responseText.objList[0].length>0){
                $("#no_gs").append("<option id='special_option' value='0' style='display:none;' ></option>");
            }else{
                $("#no_gs").append("<option id='special_option' value='0' ></option>");
            } */
            if(responseText.objList[0]!=null||responseText.objList[0].length==0){
                $("#nogroup").hide();
            }

            $("#no_gs_bk").html($("#no_gs").html());


            //如果是网校班主任，删除学生
            if(typeof responseText.objList[1]!='undefined'){
                $("#noGroupStudents li").hover(function(){
                    $(this).children("a").show();
                },function(){
                    $(this).children().hide();
                    $(".password").hide();
                });
            }

            $("a[name='view_pass']").each(function(idx,itm){
                $(itm).click(function(){
                    var pass=$(this).next('span');
                    if($(pass).css("display")=='none'){
                        $(pass).css("display","");
                    }else{
                        $(pass).css("display","none");
                    }

                })
            });



        },"json");
}

function getClassGroups(classId,classType){
    if(typeof classId=='undefined'|| typeof classType=='undefined')
        return;
    var url="group?m=getClassGroups";
    $.ajax({
        url:url,
        data:{classid:classId,classtype:classType,subjectid:subjectid,termid:termid},
        type:'post',
        dataType:'json',
        error:function(){
            alert('异常错误,系统未响应！');
        },
        success:function(responseText){
            if(responseText.type=="success"){
                var forGroupHtml="";
                var changeGroupHtml="";
                // var groupHtml='<colgroup><col class="w280"><col class="w100"><col class="w150"><col class="w100"><col class="w120"></colgroup>';
                // groupHtml+='<tr><th>组名</th><th>学号</th><th>组员姓名</th><th>任务完成率</th><th>加入小组时间</th></tr>';
                var groupHtml='<colgroup><col class="w280"/><col class="w110"/><col class="w150"/><col class="w100"/><col class="w120"/></colgroup><tr><th>组名</th><th>学号</th><th>组员姓名</th><th>任务完成率</th><th>加入小组时间</th></tr>';
                if(responseText!=null&&responseText.objList!=null&&responseText.objList.length>0){
                    $.each(responseText.objList,function(idx,itm){
                        groupHtml+="<tbody id='group_"+itm.groupid+"'>";
                        groupHtml+="<tr>";
                        groupHtml+="<td class='v_c'>"+itm.groupname;
                        groupHtml+="<a href='javascript:delGroup("+itm.groupid+");' class='ico04' title='删除'></a>";
                        groupHtml+="</td>";
                        groupHtml+="<td>&nbsp;</td>";
                        groupHtml+="<td>&nbsp;</td>";
                        groupHtml+="</tr>";
                        groupHtml+="</tbody>";
                        forGroupHtml+="<li><input type='radio' name='forGroupId' value='"+itm.groupid+"'/>"+itm.groupname+"</li>";
                        changeGroupHtml+="<option value='"+itm.groupid+"' >"+itm.groupname+"</option>";
                    });
                    $.each(responseText.objList,function(idx,itm){
                        getGroupStudents(itm.groupid,itm.groupname,itm.completenum,itm.totalnum);
                    });
                }else{
                    groupHtml+="<tr><td colspan='5'>您还没有创建小组！</td></tr>";
                }
                $("#group_list").html(groupHtml);
                $("#forGroupList").html(forGroupHtml);
                $("#changeGroups").html(changeGroupHtml);
            }else{
                alert(responseText.msg);
            }

        }});
}

function editGroupName(groupId,groupName){
    var html="<input size='10' id='editGN_"+groupId+"' type='text' value='"+groupName+"'/>";
    html+="<a style='float:right;' href="+"javascript:updateGroup("+groupId+");"+">保存&nbsp;&nbsp;</a>";
    $("#gn_"+groupId).html(html)
}

function updateGroup(){
    var groupId = $("#uGroupID").val();
    var groupname=$("#editGN").val();
    if(groupId==null || groupId.length<1){
        alert("小组参数错误！！");
        return;
    }
    if(groupname.length<1){
        alert("清输入组名！！");
        return;
    }
    var url="group?m=editGroupName";
    $.post(url,{groupid:groupId,groupname:groupname},
        function(rps){
            if(rps.type=="success"){
                alert("修改成功!");
                closeModel('updateGroupName');
            }else if(rps.type=="errorname"){
                alert("小组名已存在！");
            }else{
                alert(rps.msg);
            }
        },"json");
}

function delGroup(groupId){

    if(!confirm("确认删除？"))
        return;
    var url="group?m=delGroup";
    $.post(url,{groupid:groupId},
        function(rps){
            if(rps.type=="success"){
                alert("删除成功!"+rps.msg);
                var clsid=$("#classId").val();
                getClassGroups(clsid,1);
                getNoGroupStudentsByClassId(clsid,1,$("#dcType").val());
            }else{
                alert(rps.msg);
            }
        },"json");
}

function delGroupStudent(ref){
    if(!confirm("确认删除？"))
        return;
    var url="group?m=delGroupStudent";
    $.post(url,{ref:ref},
        function(rps){
            if(rps.type=="success"){
                alert(rps.msg);
                $("#nogroup").show();
                getNoGroupStudentsByClassId($("#classId").val(),1,$("#dcType").val());
                getClassGroups($("#classId").val(),1);
            }else{
                alert("删除失败");
            }
        },"json");
}

function getGroupStudents(groupId,groupName,completenum,totalnum){
    var url="group?m=getGroupStudents";
    var gtHtml="";
    var percentHtml="";
    if(completenum>0){
        var cnum=parseInt(completenum);
        var tnum=parseInt(totalnum);
        var percent=parseFloat(cnum)/tnum*100;
        percentHtml="<br>" +percent.toFixed(2)+"%";
    }else{
        var percentHtml="<br>0%";
    }
    $.post(url,{groupid:groupId},
        function(responseText){
            if(responseText==null||responseText.objList==null||responseText.objList.length==0){
                gtHtml+="<tr><td class='v_c'>"+groupName+"<a href='javascript:delGroup("+groupId+");' class='ico04' title='删除'></td>";
                gtHtml+="<td colspan='4'>没有小组成员！</td></tr>";
                $("#group_"+groupId).html(gtHtml);
                return;
            }
            var dcType=$("#dcType").val();
            $.each(responseText.objList,function(idx,itm){
                gtHtml+="<tr>";
                if(idx==0){
                    gtHtml+="<td rowspan="+responseText.objList.length+" class='v_c'>"+groupName;
                    gtHtml+="<a href='javascript:delGroup("+groupId+");' class='ico04' title='删除'></a>";
                    gtHtml+=percentHtml;
                    gtHtml+="</td>";
                }
                var stuno=typeof itm.stuno=='undefined'?'--':itm.stuno;
                gtHtml+="<td>"+stuno+"</td>";
                gtHtml+="<td><span class='w60'>"+itm.stuname+"<b style='display: none;'>"+itm.password+"</b></span>";
                if(dcType==1)
                    gtHtml+='<a name="a_view" class="ico92" title="查看密码" href="javascript:void(0);"></a>';
                gtHtml+='<a href="javascript:delGroupStudent(\''+itm.ref+'\');" class="ico34" title="移出小组"></a>';
                if(itm.isleader==2)
                    gtHtml+='<a href="javascript:showGroupsPanel(\''+itm.ref+'\',\''+groupId+'\');" class="ico22" title="调组"></a></td>';
                else if(itm.isleader==1)
                    gtHtml+='<a href="javascript:showGroupsPanel(\''+itm.ref+'\',\''+groupId+'\');" class="ico23" title="调组"></a></td>';
                gtHtml+='<td>'+itm.completenum+'%</td>';
                gtHtml+='<td>'+itm.ctimestring+'</td>';
                gtHtml+='</tr>';
            });
            $("#group_"+groupId).html(gtHtml);


            var array= $.makeArray($("#group_"+groupId+" a[name='a_view']"));
            $.each(array,function(idx,itm){
                $(itm).click(function(){
                    var pass=$(this).siblings('span').children('b');
                    if($(pass).css("display")=='none'){
                        $(pass).css("display","");
                    }else{
                        $(pass).css("display","none");
                    }
                });
            });
        },"json");
}

function closeDiv(showId){
    showAndHidden(showId,'hide');
    showAndHidden('fade','hide');
}

function showGroupsPanel(ref,groupid){
    $("#gs_ref").val(ref);
    $("#changeGroups").val(groupid);
    showModel('changeGroupDiv');
}

function allSelected(flag){
    if(flag)
        $("input[name='studentsId']").each(function(){$(this).attr("checked",true);});
    else
        $("input[name='studentsId']").each(function(){$(this).attr("checked",!this.checked);});
}

function changeStuGroup(){
    var newGroupId = $("#changeGroups").val();
    var isleader = 2;
    if($("#isleader").attr("checked"))
        isleader=1;
    var ref = $("#gs_ref").val();
    if(ref==null||newGroupId==null){
        alert("无法调组！");
        return;
    }
    var url="group?m=changeGroupStudent";
    $.post(url,{ref:ref,groupid:newGroupId,isleader:isleader},
        function(rps){
            if(rps!=null&&rps.type=="success"){
                closeModel('changeGroupDiv');
                var clsid=$("#classId").val();
                getClassGroups(clsid,1);
            }else{
                alert("无法调组！");
            }
        },"json");
}

function editGroupNameDiv(){
    var groupId=$("#classGroups").val();
    if(groupId==0){
        alert("请先创建小组！");
        return;
    }
    showModel2('updateGroupName');
    $("#uGroupID").val(groupId);
    $("#editGN").val($("#classGroups").find("option:selected").text());
}

function showModel2(showId,isdrop){
    $("#fade").css("height",window.screen.availHeight+parseFloat(getScrollTop()));
    $("#fade").css("width",window.screen.availWidth);
    try{
        $("#"+showId).css("z-index",1005);
        $("#"+showId).css("position","absolute");
        $("#"+showId).css("top","350");
        $("#"+showId).css("left","500");
    }catch(e){}

    showAndHidden(showId,'show');
    showAndHidden('fade','show');

}

function filterResult(){
    var stuname=$("#stu_name").val();
    var n=0;
    var h='';
    $("#no_gs_bk option").each(function(idx,itm){
        if($(this).html().indexOf(stuname)!=-1){
            h+='<option value="'+itm.value+'">'+itm.text+'</option>';
            n++;
        }
    });
    $("#no_gs").html(h);





    //  显示预留条目，否则select框体会消失
    if(n==0)
        $("#special_option").show();
    else
        $("#special_option").hide();
}




/**
 * 获取学生列表
 * @param clsid
 */
function getStuList(clsid,dctype){
    if(typeof clsid=='undefined')
        return;

    $.ajax({
        url:'group?m=getClsStudent',
        data:{classid:clsid},
        type:'post',
        dataType:'json',
        error:function(){
            alert('异常错误,系统未响应！');
        },
        success:function(rps){
            var h='';
            if(rps.objList.length>0){
                $.each(rps.objList,function(idx,itm){
                    h+='<li>'+itm.realname;
                    if(dctype>1)
                        h+='<a style="display: none;" href="javascript:delClassUser(\''+itm.ref+'\')" class="ico_delete" title="删除"></a>';
                    else
                        h+='<a style="display: none;" name="a_view" class="ico92" title="查看密码" href="javascript:;"></a>';
                    h+='<span style="display: none;"  class="password">'+itm.password+'</span>'
                    h+='</li>';
                });
            }
            $("#stuList").html(h);


            $("#stuList li").hover(
                function(){
                    $(this).children("a").show();
                },
                function(){
                    $(this).children("a").hide();
                    $(".password").hide();
                }
            );

            $("#stuList a[name='a_view']").each(function(idx,itm){
                $(itm).click(function(){
                    var span=$(this).next();
                    if($(span).css("display")=='none'){
                        $(span).css("display","");
                    }else{
                        $(span).css("display","none");
                    }
                });
            });
        }
    });
}




/**
 * 添加班级学生
 * @param clsid
 */
function doAddClsStudent(){
    var clsid=$("#classId").val();
    if(typeof clsid=='undefined')
        return;

    var msg='数据验证完毕!确认操作?',param={clsid:clsid},jidArray=new Array(),nameArray=new Array();
    var liArray=$("#ul_cls_stu li");
    if(liArray.length<1){
        param.flag=1;
        msg='确认删除当前班级所有学生!?';
    }
    $.each(liArray,function(idx,itm){
        var jid=$(itm).data().bind.split("|")[0];
        var name=$(itm).data().bind.split("|")[1];
        jidArray.push(jid);
        nameArray.push(name);
    });
    if(jidArray.length>0&&nameArray.length>0){
        param.jidStr=jidArray.join(",");
        param.nameStr=nameArray.join(",");
    }


    if(!confirm(msg))return;
    $.ajax({
        url: 'tpuser?doAddClsStudent',
        type: 'post',
        data: param,
        dataType: 'json',
        cache: false,
        error: function () {
            alert('网络异常!')
        },
        success: function (rps) {
            if (rps.type == "error") {
                alert(rps.msg);
            } else {
                closeModel('dv_add_student');
                getStuList(clsid);
                getNoGroupStudentsByClassId(clsid,1,$("#dcType").val());
            }
        }
    });
}




function setAllStudent(){
    var wxArray=$("#ul_wx_stu li");
    if(wxArray.length>0){
        $.each(wxArray,function(ix,im){
            var id=$(im).attr('id');
            var parentId=$(im).parent('ul').attr('id');
            var descId=parentId=='ul_wx_stu'?'ul_cls_stu':'ul_wx_stu';
            var clssName=parentId=='ul_wx_stu'?'ico85b':'ico85a';
            var descObj=$('#'+descObj+' li[id="'+id+'"]');

            if(descObj.length<1){
                $(im).children('span').attr("class",clssName);
                $(im).appendTo( $("#"+descId));
            }
        });
    }
}

function reSetStudent(){
    var sxArray=$("#ul_cls_stu li");

    if(sxArray.length>0){
        $.each(sxArray,function(ix,im){
            var id=$(im).attr('id');
            var parentId=$(im).parent('ul').attr('id');
            var descId=parentId=='ul_cls_stu'?'ul_wx_stu':'ul_cls_stu';
            var clssName=parentId=='ul_wx_stu'?'ico85a':'ico85b';
            var descObj=$('#'+descObj+' li[id="'+id+'"]');

            if(descObj.length<1){
                $(im).children('span').attr("class",clssName);
                $(im).appendTo( $("#"+descId));
            }
        });
    }
}




/**
 * 获取网校学生
 */
function loadWXStudent(){
    var clsid=$("#classId").val();
    if(typeof clsid=='undefined')
        return;
    showModel('dv_add_student');
    $("#txt_stuName").val('');

    var param={clsid:clsid};
    $.ajax({
        url: 'tpuser?loadWXStudent',
        type: 'post',
        data: param,
        dataType: 'json',
        cache: false,
        error: function () {
            alert('网络异常!')
        },
        success: function (rps) {
            var wx='',sx='';
            if (rps.type == "error") {
                alert(rps.msg);
            } else {
                if(rps.objList[0]!=null&&rps.objList[0].length>0){
                    $.each(rps.objList[0],function(idx,itm){
                        wx+='<li id="'+itm.ettuserid+'" data-bind="'+itm.ettuserid+'|'+itm.realname+'"><span class="ico85a"></span>'+itm.realname+'</li>';
                    });

                }

                if(rps.objList[1]!=null&&rps.objList[1].length>0){
                    $.each(rps.objList[1],function(idx,itm){
                        sx+='<li id="'+itm.ettuserid+'" data-bind="'+itm.ettuserid+'|'+itm.realname+'"><span class="ico85b"></span>'+itm.realname+'</li>';
                    });
                }
            }
            $("#ul_wx_stu").html(wx);
            $("#ul_cls_stu").html(sx);


            $("#dv_add_student li").click(function(){
                var id=$(this).attr('id');
                var parentId=$(this).parent('ul').attr('id');
                var descId=parentId=='ul_wx_stu'?'ul_cls_stu':'ul_wx_stu';
                var clssName=parentId=='ul_wx_stu'?'ico85b':'ico85a';
                var descObj=$('#'+descObj+' li[id="'+id+'"]');

                if(descObj.length<1){
                    $(this).children('span').attr("class",clssName);
                    $(this).appendTo( $("#"+descId));
                }else
                    alert('当前学生已存在!');
            });
        }
    });
}



/**
 * 获取网校学生
 */
function loadWXStudentByName(){
    var clsid=$("#classId").val();
    if(typeof clsid=='undefined')
        return;
    var param={clsid:clsid};
    var stuName=$("#txt_stuName");
    if(stuName.val().Trim().length>0)
        param.stuName=stuName.val();

    $.ajax({
        url: 'tpuser?loadWXStudent',
        type: 'post',
        data: param,
        dataType: 'json',
        cache: false,
        error: function () {
            alert('网络异常!')
        },
        success: function (rps) {
            var wx='';
            if (rps.type == "error") {
                alert(rps.msg);
            } else {
                if(rps.objList[0]!=null&&rps.objList[0].length>0){
                    $.each(rps.objList[0],function(idx,itm){
                        wx+='<li id="'+itm.ettuserid+'" data-bind="'+itm.ettuserid+'|'+itm.realname+'"><span class="ico85a"></span>'+itm.realname+'</li>';
                    });

                }

            }
            $("#ul_wx_stu").html(wx);


            $("#dv_add_student li").click(function(){
                var id=$(this).attr('id');
                var parentId=$(this).parent('ul').attr('id');
                var descId=parentId=='ul_wx_stu'?'ul_cls_stu':'ul_wx_stu';
                var clssName=parentId=='ul_wx_stu'?'ico85b':'ico85a';
                var descObj=$('#'+descObj+' li[id="'+id+'"]');

                if(descObj.length<1){
                    $(this).children('span').attr("class",clssName);
                    $(this).appendTo( $("#"+descId));
                }else
                    alert('当前学生已存在!');

            });
        }
    });
}




/**
 * 删除班级用户
 * @param clsid
 */
function delClassUser(ref){
    if(typeof ref=='undefined')
        return;
    if(!confirm('确认操作?'))return;

    var param={ref:ref};
    $.ajax({
        url: 'tpuser?delClassUser',
        type: 'post',
        data: param,
        dataType: 'json',
        cache: false,
        error: function () {
            alert('网络异常!')
        },
        success: function (rps) {
            if (rps.type == "error") {
                alert(rps.msg);
            } else {
                //$("#li_"+ref).remove();
                var clsid=$("#classId").val();
                var dctype=$("dcType").val();
                if(isLession==2){
                    getStuList(clsid);
                }else if(isLession==3){
                    getNoGroupStudentsByClassId(clsid,1,dctype);
                }
            }
        }
    });
}

function getClassCourseList(){
    var classid=$("#classId").val();
    if(typeof classid=='undefined'||classid.toString().length<1)
        return;

    var param={classid:classid,classtype:1,subjectid:subjectid,gradeid:gradeid,termid:termid};
    $.ajax({
        url: 'teachercourse?m=toClassCourseList',
        type: 'post',
        data: param,
        dataType: 'json',
        cache: false,
        error: function () {
            alert('网络异常!')
        },
        success: function (rps) {
            var h='';
            if (rps.type == "error") {
                alert(rps.msg);
            } else {
                if(rps.objList.length>0){
                    $.each(rps.objList,function(idx,itm){
                        h+='<tr>';
                        h+='<td><p class="ico">';
                        if(typeof itm.quoteid=='undefined'|| itm.quoteid==0)
                            h+='<b class="ico16" title="自建"></b>';
                        else if(itm.quoteid!=0 && itm.courselevel==1)
                            h+='<b class="ico18" title="标准" ></b>';
                        else if(itm.quoteid!=0 && itm.courselevel==2)
                            h+='<b class="ico17" title="共享" ></b>';

                        h+='<a  href="task?toTaskList&courseid='+itm.courseid+'">'+itm.coursename+'</a></p></td>';
                        h+='<td>'+itm.ctimeString+'</td>';
                        h+='</tr>';
                    });
                }
                $("#clsCourseList").html(h);
            }
        }
    });
    //teachercourse?m=toClassCourseList&classid=189&classtype=1&subjectid=2&gradeid=1
}

var ClassContainer;
if(ClassContainer == undefined){
    ClassContainer=function(settings){
        this.initContainer(settings);
    }
}

ClassContainer.prototype.initContainer=function(obj){
    this.config=obj;
}
