var pList,pTsList; //分页控件变量

function delCourse(courseid){
    $.ajax({
        url:'teachercourse!delCourse.action',
        data:{courseid:courseid},
        type:'post',
        dataType:'json',
        error:function(){
            alert('异常错误,系统未响应！');
        },success:function(rps){
            if(rps.type=="success"){
                page_go();
            }else{
                alert('刪除失败！');
            }
        }
    });
}

function changeStatus(courseid,available){
    if(available!=0&&available!=1){
        alert("参数错误！");
        return;
    }
    $.ajax({
        url:'teachercourse?m=doChangeCourse',
        data:{courseid:courseid,coursestatus:available},
        type:'post',
        dataType:'json',
        error:function(){
            alert('异常错误,系统未响应！');
        },success:function(rps){
            if(rps.type=="success"){
                var strHtml="";
                if(available==1){
                    strHtml+="<span class='font-gray1'>开启</span>&nbsp;&nbsp;<span class='font-lightblue'><a href="+"javascript:changeStatus("+courseid+",0);"+">关闭</a></span>";
                }
                if(available==0){
                    strHtml+="<span class='font-lightblue'><a href="+"javascript:changeStatus("+courseid+",1);"+">开启</a></span>&nbsp;&nbsp;<span class='font-gray1'>关闭</span>";
                }
                $("#ava_"+courseid).html(strHtml);
            }else{
                alert("修改失败!");
            }
        }
    });
}

function deleteCourse(courseid){
    if(courseid==null){
        alert("参数错误！");
        return;
    }
    if(!confirm("确认删除？"))
        return;
    $.ajax({
        url:'teachercourse?m=doChangeCourse',
        data:{courseid:courseid,localstatus:2},
        type:'post',
        dataType:'json',
        error:function(){
            alert('异常错误,系统未响应！');
        },success:function(rps){
            if(rps.type=="success"){
                pageGo("pList");
            }else{
                alert("删除失败!");
            }
        }
    });
}

function changeGrade(gradeid,subjectid,idx){
    global_gradeid=gradeid;
    global_subjectid=subjectid;
    $("#li_"+gradeid+"_"+subjectid+"_"+idx).siblings().attr("class","");
    $("#li_"+gradeid+"_"+subjectid+"_"+idx).attr("class","crumb");
    $("#fGradeSub").html($("#t_grade_sub_"+idx).html());
   //$("#material_name").html("点击教材版本");
  // $("#material_id").val(0);
    pageGo("pList");
    if(pTsList!=null)
       pageGo("pTsList");
}

function fChangeGrade(){
    var val_sg=$("#fGradeSub").val();
    if(val_sg!=0){
        getFollowCourseList(val_sg.split("_")[0],val_sg.split("_")[1]);
    }else{
        $("#fCourseList").html("<p>没有搜索到专题！</p>");
    }
}

function preeDoPageSub(pObj){
    var material_id=$("#material_id").val();
    var param={};
    param.termid=termid;
    param.gradeid=global_gradeid;
    param.subjectid=global_subjectid;
    param.atermid=termid;
    if(material_id!=0){
        //param.materialidvalues=material_id;
    }
    pObj.setPostParams(param);
}

function getInvestReturnMethod(rps){
    var html="";
    var classhtml="";
    if(rps!=null
        &&rps.presult.list[0]!=null
        &&rps.presult.list[0].length>0){
        $.each(rps.presult.list[0],function(idx,itm){
            html+="<tr "+(idx%2==1?"class='trbg1'":"")+">";
            html+="<td><p class='one'>";
            if(itm.quoteid==0||typeof itm.quoteid=='undefined')
                html+="<span class='ico16' title='自建'></span>";
            else if(itm.quoteid!=0&&typeof itm.quoteid!='undefined'&&itm.qcuserid==userid)
                html+="<span class='ico16' title='自建'></span>";
            else if(itm.quoteid!=0&&typeof itm.quoteid!='undefined'&&itm.qcourselevel==1)
                html+="<span class='ico18' title='标准'></span>";
            else
                html+="<span class='ico17' title='共享'></span>";

            html+="<a target='_blank' href='task?toTaskList&courseid="+itm.courseid+"&subjectid="+global_subjectid+"'>"+itm.coursename+"</p></td>";
            html+="<td>";
            if(typeof(itm.classEntity)!='undefined'&&itm.classEntity.length>0){
                if(itm.classEntity[0].CLASS_NAME!="0"){
                    $.each(itm.classEntity,function(n,classTemp){
                        html+="<p><span class='f_right'>"+classTemp.CLASS_TIME.substring(0,16)+"</span>"+classTemp.CLASS_NAME+"</p>";
                    });
                }else{
                    html+="<p><a href='javascript:toUpdCoursePage("+itm.courseid+")' class='font-blue'>" +
                        "<img width='15' height='15' src='images/an02_131126.png'/>设置</a></p>";
                }
            }else{
                html+="<p><span class='f_right'>----</span>----</p>";
            }
            html+="</td>";
            html+="<td><a href='teachercourse?m=toClassCommentList&courseid="+itm.courseid+"&type=1' target='_blank'>"+itm.avgscore+"（"+itm.commentnum+"人）</a></td>";
            html+="<td>";
            if(termid==currtterm){
                var materialid=$("#material_id").val();
                html+="<a href='javascript:toUpdCoursePage("+itm.courseid+")' class='ico11' title='编辑'></a>";
                html+="<a href='javascript:deleteCourse("+itm.courseid+");' class='ico04' title='删除'></a>";
                $("a[name='a_hide']").show();
            }else
                $("a[name='a_hide']").hide();

            if(itm.sharetype==2){
                html+='<a class="ico21" title="已共享"></a>';
            }else if(typeof itm.quoteid=='undefined'||itm.quoteid==0){
                html+="<a href='javascript:shareCourse(\""+itm.courseid+"\","+itm.sharetype+")' class='ico20' title='共享'></a>";
            }

            html+="</td></tr>";
        });




        if(rps.presult.list[0].length>0){
            pList.setPagetotal(rps.presult.pageTotal);
            pList.setRectotal(rps.presult.recTotal);
            pList.setPageSize(rps.presult.pageSize);
            pList.setPageNo(rps.presult.pageNo);
        }else{
            pList.setPagetotal(0);
            pList.setRectotal(0);
            pList.setPageNo(1);
        }
        pList.Refresh();
    }else{
        html+="<tr><td colspan='4'>沒有数据！</td></tr>";

    }
    if(rps.presult.list[1]!=null&&rps.presult.list[1].length>0){
        $.each(rps.presult.list[1],function(idx,itm){
            classhtml+="<li><a target='_blank' href='teachercourse?m=toClassStudentList&classid="+itm.classid+"&classtype=1&subjectid="+global_subjectid+"&gradeid="+global_gradeid+"' >"+itm.classgrade+itm.classname+"</a></li>";
        });
    }
    if(rps.presult.list[2]!=null&&rps.presult.list[2].length>0){
        $.each(rps.presult.list[2],function(idx,itm){
            classhtml+="<li><a target='_blank' href='teachercourse?m=toClassStudentList&classid="+itm.virtualclassid+"&classtype=2&subjectid="+global_subjectid+"&gradeid="+global_gradeid+"' >"+itm.virtualclassname+"</a></li>";
        });
    }
    if(typeof(rps.objList[3])!="undefined"&&rps.objList[3]!=null){
        $("span[id='material_name']").html(rps.objList[3].materialname+"("+rps.objList[3].versionname+")");
        $("input[id='material_id']").val(rps.objList[3].materialid);
    }else{
        $("input[id='material_id']").val("");
        $("span[id='material_name']").html("点击教材版本");
        materialid="";
    }
    $("#courseTable").html(html);
    $("#claList").html(classhtml);
}


function toUpdCoursePage(courseid){
    var url= "teachercourse?m=toSaveOrUpdate&courseid="+courseid+"&gradeid="+global_gradeid+"&subjectid="+global_subjectid;
    if($("#material_id").val()!=0){
        url+="&materialid="+$("#material_id").val();
    }
    window.location.href=url;
}



//分享专题
function shareCourse(courseid,sharetype){
    if(sharetype==1){
        $("input:radio[value=1]").attr('checked','true');
    }
   $("#courseid").val(courseid);
    showModel('shareWindow');
}

function doShareCourse(){
    var courseid=$("#courseid").val();
    var sharetype=$("input[name='share']:checked").val();
    $.ajax({
        url:'teachercourse?m=shareCourse',//cls!??.action
        dataType:'json',
        type:'POST',
        data:{courseid:courseid,
            sharetype:sharetype
        },
        cache: false,
        error:function(){
            alert('异常错误!系统未响应!');
        },success:function(rps){
            pageGo('pList');
            alert(rps.msg);
            closeModel('shareWindow');
        }
    });
}

function getTsInvestReturnMethod(rps){
    var html="";
    var classid=0;
    var classtype=0;
    if(rps!=null
        &&rps.presult.list!=null
        &&rps.presult.list.length>0){
        $.each(rps.presult.list,function(idx,itm){
            var flag=true;
            html+="<tr>";
            html+="<td>"+itm.receiveteachername+"</td>";
            html+="<td><a href='teachercourse?m=toCourseDetial&courseid="+itm.courseid+"' target='_blank'>"+itm.coursename+"</td>";
            html+="<td>";
            if(itm.classEntity.length>0&&itm.classEntity[0].CLASS_NAME!="0"){
                $.each(itm.classEntity,function(n,classTemp){
                    classid=classTemp.CLASS_ID;
                    classtype=classTemp.CLASS_TYPE;
                    html+="<p><span class='f_right'>"+classTemp.CLASS_TIME.substring(0,16)+"</span>"+classTemp.CLASS_NAME+"</p>";
                });
            }else{
                html+="<p><span class='f_right'>----</span>----</p>";
            }
            html+="</td>";
            html+="<td><a href='teachercourse?m=toClassCommentList&tchid="+itm.cuserid+"&classid="+classid+"&classtype="+classtype+"&courseid="+itm.courseid+"&type=2'>"+itm.avgscore+"（"+itm.commentnum+"人）</a></td>";
            html+="</tr>";
        });

        if(rps.presult.list.length>0){
            pTsList.setPagetotal(rps.presult.pageTotal);
            pTsList.setRectotal(rps.presult.recTotal);
            pTsList.setPageSize(rps.presult.pageSize);
            pTsList.setPageNo(rps.presult.pageNo);
        }else{
            pTsList.setPagetotal(0);
            pTsList.setRectotal(0);
            pTsList.setPageNo(1);
        }
        pTsList.Refresh();
    }else{
        html+="<tr><td colspan='7'>沒有数据！</td></tr>";

    }
    $("#tsCourseTable").html(html);
}

function getTermCondition(tid,termname){
    displayObj('termList',false);
    $("#checkedTerm").html(termname);
    termid=tid;
    global_gradeid=0;
    global_subjectid=0;
    $.ajax({
        url:'teachercourse?m=tchTermCondition',
        data:{termid:termid},
        type:'post',
        dataType:'json',
        error:function(){
            alert('异常错误,系统未响应！');
        },
        success:function(rps){
            $("#teachClasses").html("");
            if(rps==null){
                alert("获取数据出错...");
                $("#ul_grade").html("");
                $("#courseTable").html("<tr><td colspan='4'>沒有数据！</td></tr>");
                $("#tsCourseTable").html("<tr><td colspan='4'>沒有数据！</td></tr>");
                $("#claList").html("");
                return ;
            }
            if(rps.objList[0]==null || rps.objList[0].length==0){
                alert("您还没有被分配学科，请联系管理员。");
                $("#ul_grade").html("");
                $("#courseTable").html("<tr><td colspan='4'>沒有数据！</td></tr>");
                $("#tsCourseTable").html("<tr><td colspan='4'>沒有数据！</td></tr>");
                $("#claList").html("");
                return ;
            }
            if(rps.objList[0]==null || rps.objList[0].length==0){
                alert("您在当前学年没有教授年级。");
                $("#ul_grade").html("");
                $("#courseTable").html("<tr><td colspan='4'>沒有数据！</td></tr>");
                $("#tsCourseTable").html("<tr><td colspan='4'>沒有数据！</td></tr>");
                $("#claList").html("");
                $("a[name='a_hide']").hide();
                return ;
            }
            //当前学期
            if(rps.objList[1]!=null && typeof rps.objList[1]!='undefined'){
                $("#hd_term_flag").val(rps.objList[1].flag);
                if(currtterm!=rps.objList[1].ref)
                    $("a[name='a_hide']").hide();
            }
            var html="";
            $.each(rps.objList[0],function(idx,gitm){
                html+="<li id='li_"+gitm.gradeid+"_"+gitm.subjectid+"_"+idx+"'>";
                html+="<a href="+"javascript:changeGrade("+gitm.gradeid+","+gitm.subjectid+","+idx+");"+">";
                html+="<span id='t_grade_sub_"+idx+"'>"+gitm.gradevalue+gitm.subjectname+"</span></a>";
            });
            $("#ul_grade").html(html);
            changeTab('back');
            changeTab('front');
            if(currentSubjectid!="undefind"&&currentSubjectid!=null&&currentSubjectid.length>0){
                var liid="li_"+currentGradeid+"_"+currentSubjectid+"_";
                var obj=$("li").filter(function(){return this.id.indexOf(liid)!=-1});
                eval(obj.children("a").attr("href").replace("javascript:",""));
            }else{
               $("#t_grade_sub_0").click();
            }
        }
    });
}

function getFTermCondition(){
    $.ajax({
        url:'teachercourse?m=tchTermCondition',
        data:{termid:$("#fTermid").val()},
        type:'post',
        dataType:'json',
        error:function(){
            alert('异常错误,系统未响应！');
        },
        success:function(rps){
            var html="";
            if(rps.objList.length>0
                &&rps.objList[0].length>0
                &&rps.objList[1].length>0){
                $.each(rps.objList[1],function(idx,gitm){
                    $.each(rps.objList[0],function(subIdx,subItm){
                        html+="<option value='"+gitm.gradeid+"_"+subItm.subjectinfo.subjectid+"'>"
                            +gitm.gradevalue+subItm.subjectinfo.subjectname+"</option>";
                    });
                });
            }else{
                html+="<option value='0'>-- 无 --</option>";
            }
            $("#fGradeSub").html(html);
            fChangeGrade();
        }
    });
}

function getFollowCourseList(gradeid,subjectid){
    $.ajax({
        url:'teachercourse?m=getTchCourListAjax',
        data:{termid:$("#fTermid").val(),
            subjectid:subjectid,
            gradeid:gradeid},
        type:'post',
        dataType:'json',
        error:function(){
            alert('异常错误,系统未响应！');
        },
        success:function(rps){
            var html="";
            if(rps!=null
                &&rps.presult.list[0]!=null
                &&rps.presult.list[0].length>0){
                html+="<li>&nbsp;</li>";
                $.each(rps.presult.list[0],function(idx,itm){
                    html+="<li><input type='radio' id='fcourseid' name='fcourseid' value='"+itm.courseid+"'/>&nbsp;";
                    html+=itm.coursename;
                    html+="</li>";
                });
            }else{
                html+="<li>没有搜索到专题！</li>";
            }
            $("#fCourseList").html(html);
        }
    });
}

function toSaveCoursePage(){
    if(global_gradeid<1){
        alert("没有获取到年级参数！");
        return;
    }
    if(global_subjectid<1){
        alert("没有获取到学科参数！");
        return;
    }

    var url= "teachercourse?m=toSaveOrUpdate"
        +"&gradeid="+global_gradeid
        +"&subjectid="+global_subjectid;
    if($("#material_id").val()!=0){
        url+="&materialid="+$("#material_id").val();
    }
    window.location.href=url;
}

function getTrusteeShipNotices(){
    $.post('trusteeship?m=getTrusteeShips',{},
        function(rps){
            if(rps!=null&&rps.objList!=null&&rps.objList.length>0){
                var html ="";
                $.each(rps.objList,function(idx,itm) {
                    html+="<tr id='tsn_"+itm.ref+"'><td>&nbsp;</td>";
                    if(itm.isaccept==0){
                        html+="<td>"+itm.trustteachername+" 老师向您托管 "+itm.classgrade+itm.classname+"</td>";
                        html+="<td align='center'><a href='javascript:updateTrusteeShip("+itm.ref+",1);'>同意</a>&nbsp;&nbsp;" +
                            "<a href='javascript:updateTrusteeShip("+itm.ref+",2);'>拒绝</a></td>";
                    }
                    if(itm.isaccept==2){
                        html+="<td>"+itm.receiveteachername+" 老师拒绝接管 "+itm.classgrade+itm.classname+"</td>";
                        html+="<td align='center'><a href='javascript:delTrusteeShip("+itm.ref+");'>确定</a></td>";
                    }
                    html+="</tr>";
                });
                $("#trusteeShip_notices").html(html);
                showModel("trusteeShip_div");
            }else{
                alert("没有托管提醒！");
            }
        },"json");
}

function updateTrusteeShip(ref,value){
    if(ref==null
        || value==null){
        alert('参数错误!');
        return;
    }
    $.ajax({
        url:'trusteeship?m=updateTrusteeShip',
        data:{
            ref:ref,
            isaccept:value
        },
        type:'POST',
        dataType:'json',
        error:function(){
            alert('异常错误,系统未响应！');
        },success:function(rps){
            if(rps.type=="success"){
                $("#tscNum").html($("#tscNum").html()-1);
                $("#tsn_"+ref).remove();
                if($("#tscNum").html()==0){
                    $("#a_tsc").remove();
                    closeModel("trusteeShip_div");
                }
            }else{
                 alert("操作失败，"+rps.msg);
            }
        }
    });
}

function delTrusteeShip(ref){
    if(ref==null){
        alert('参数错误!');
        return;
    }
    $.ajax({
        url:'trusteeship?m=delTrusteeShip',
        data:{
            ref:ref
        },
        type:'POST',
        dataType:'json',
        error:function(){
            alert('异常错误,系统未响应！');
        },success:function(rps){
            if(rps.type=="success"){
                $("#tsn_"+ref).remove();
                $("#tscNum").html($("#tscNum").html()-1);
                if($("#tscNum").html()==0){
                    $("#a_tsc").remove();
                    closeModel("trusteeShip_div");
                }
            }else{
                alert("操作失败，"+rps.msg);
            }
        }
    });
}

function addTeacherCourse(type){
    if(!confirm("确认添加？"))
        return;
    var courseids=new Array();
    var term=currtterm;
    var subjectid=$("#fGradeSub").val().split("_")[1];
    var gradeid=$("#fGradeSub").val().split("_")[0];
    $("input[name='fcourseid']:checked").each(function() {courseids.push($(this).val());});
    if(term.length<1){
        alert('学期参数错误!');
        window.close();
    }
    if(courseids.length<1 ){
        alert('请选择专题!');
        return;
    }
    if($("#fGradeSub").val()!=null && $("#fGradeSub").val().Trim().length<1){
        alert('年级学科参数错误!');
        return;
    }
    if($("#fGradeSub").val().split("_").length!=2){
        alert('年级学科参数错误!');
        return;
    }
    gradeid=$("#fGradeSub").val().split("_")[0];
    subjectid=$("#fGradeSub").val().split("_")[1];
    var url='teachercourse?m=doAddQuoteCourse';
    if(typeof type!='undeinfed'&&type==1)
        url='teachercourse?m=doContinueUseCourse';
    $.ajax({
        url:url,
        data:{
            termid:term,
            subjectid:subjectid,
            gradeid:gradeid,
            courseids:courseids.join(',')
        },
        type:'POST',
        dataType:'json',
        error:function(){
            alert('异常错误,系统未响应！');
        },success:function(rps){
            if(rps.type=="success"){
                alert("添加成功!");
                window.location.href="teachercourse?m=toTeacherCourseList&materialid="+materialid;
            }else{
                alert("无法添加!"+rps.msg);
            }
        }
    });
}

function getTchingMaterial(){
    if(global_gradeid<1){
        alert("没有获取到年级参数！");
        return;
    }
    if(global_subjectid<1){
        alert("没有获取到学科参数！");
        return;
    }
    $("#materia_button").hide();
    $.post('teachingmaterial?m=getTchingMaterialList',{gradeid:global_gradeid,subjectid:global_subjectid},
        function(rps){
            if(rps!=null
                && rps.objList!=null
                && rps.objList.length>0){
                var html ="";
                var material_id = $("#material_id").val();
                $.each(rps.objList,function(idx,itm) {
                    html+="<li style=\"width:450px;\"><input id='rdo_matid_"+itm.materialid+"' name='materialid' value='"+itm.materialid+"' type='radio' title='"+itm.materialname+"("+itm.versionname+")";
                    if(material_id!="undefind"&&material_id.length>0){
                        if(material_id==itm.materialid){
                            html+="' checked='checked";
                        }
                    }
                    html+="' /><label for=\"rdo_matid_"+itm.materialid+"\">"+itm.materialname+"("+itm.versionname+")</label></li>";
                });
                $("#teaching_materia").html(html);
                showModel("teaching_materia_div");
                $("#materia_button").show();
            }else{
                alert("无法找到学科和年级关联的教材，请联系管理添加。");
            }
        },"json");
}

function selectMaterial(){
    var material_id=$("input[name='materialid']:checked").val();
    if(material_id==null){
        alert("教材参数错误！");
        return ;
    }
    if(global_gradeid<1){
        alert("没有获取到年级参数！");
        return;
    }
    if(global_subjectid<1){
        alert("没有获取到学科参数！");
        return;
    }
    if(termid<1){
        alert("没有获取到学科参数！");
        return;
    }

    $("#material_name").html($("input[name='materialid']:checked").attr("title"));
    $("#material_id").val(material_id);

    //得到相关ID grade_id,subject_id,term_id
    //存入数据库，这一项的选项。
    var param={gradeid:global_gradeid,subjectid:global_subjectid,termid:termid,materialid:material_id};
    $.ajax({
        url:'tpteacherteamater?m=doSaveOrUpdate',
        data:param,
        type:'POST',
        dataType:'json',
        error:function(){
            alert('异常错误,系统未响应！');
        },success:function(rps){
            if(rps.type=="success"){
                closeModel("teaching_materia_div");
//                pageGo("pList");
//                if(pTsList!=null)
//                    pageGo("pTsList");
            }else{
                alert(rps.msg);
            }
        }
    });
}

function displayObj(id,type){
    if(type==undefined||type==null){
        if($("#"+id).is(":hidden"))
            $("#"+id).show();
        else
            $("#"+id).hide();
        return;
    }
    if(type){
        $("#"+id).show();
        return;
    }
    if(!type){
        $("#"+id).hide();
        return;
    }
}

function changeTab(direct){
    tabTotal=$("#ul_grade").children().length; //标签总数
    var i=0,j=0;
    if(direct=="front"){
        if(currtenTab==1){
            $("#ul_grade").children(":gt("+(tabTotal-1)+")").hide();
            return;
        }else{
            currtenTab-=1;
            i=(currtenTab-1)*tabSize;
            j=currtenTab*tabSize-1;
            $("#ul_grade").children().show();
            $("#ul_grade").children(":lt("+i+")").hide();
            $("#ul_grade").children(":gt("+j+")").hide();
            return;
        }
    }
    if(direct=="back"){
        if((currtenTab*tabSize)>=tabTotal){
            $("#ul_grade").children(":gt("+(tabSize*currtenTab-1)+")").hide();
            return;
        }else{
            currtenTab+=1;
            i=(currtenTab-1)*tabSize;
            j=currtenTab*tabSize-1;
            $("#ul_grade").children().show();
            $("#ul_grade").children(":lt("+i+")").hide();
            $("#ul_grade").children(":gt("+j+")").hide();
            return;
        }
    }
}

