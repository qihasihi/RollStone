
function preeDoPageSub(pObj){
    var param={};
    param.termid=$("#termid").val();
    param.subjectid=$("#subjectid").val();
    pObj.setPostParams(param);
}

function getInvestReturnMethod(rps){
    var html='';
    var chtml='';
    var ghtml="";
    if(rps!=null&&rps.presult.list.length>1){
        if(rps.presult.list[0].length>0){
            $.each(rps.presult.list[0],function(idx,itm){
                html+="<tr "+(idx%2==1?"class='trbg2'":"")+">";
                html+="<td>"+itm.classEntity[0].CLASS_TIME.substring(0,16)+"</td>";
                html+="<td><p><a target='_blank' href='task?toStuTaskIndex&courseid="+itm.courseid+"'>"+itm.coursename+"</a></p></td>";
                html+="<td>"+itm.teachername+"</td>";
                html+="<td><p><a target='_blank' href='task?toStuTaskIndex&courseid="+itm.courseid+"'>"+itm.uncompletenum+"</a></p></td>";
                html+='<td><a target="_blank" href="clsperformance?m=toIndex&courseid='+itm.courseid+'&subjectid='+$("#subjectid").val()+'&termid='+$("#termid").val()+'">课堂表现</a></td>';
                if(itm.iscomment>0)
                    html+="<td><span class='font-darkgray'>已评价</span></td>";
                else
                    html+="<td><a href='commoncomment?m=toCourseComment&cid="+itm.courseid+"' target='_blank' class='font-blue'>评价</a></td>";
                html+="</tr>";
            });
        }else{
            html+="<tr><td colspan='6'>暂无数据</td></tr>";
        }
        var subjectid=$("#subjectid").val();
        if(rps.presult.list[1]!=null&&rps.presult.list[1].length>0){
            $.each(rps.presult.list[1],function(idx,itm){
                chtml+="<li><a target='_blank' href='teachercourse?m=toStudeClassList&classid="+itm.classid+"&classtype=1&subjectid="+subjectid+"'>"+itm.classgrade+itm.classname+"</a></li>";
            });
        }

        if(rps.presult.list[2]!=null&&rps.presult.list[2].length>0){
            $.each(rps.presult.list[2],function(idx,itm){
                chtml+="<li><a target='_blank' href='teachercourse?m=toStudeClassList&classid="+itm.virtualclassid+"&classtype=2&subjectid="+subjectid+"'>"+itm.virtualclassname+"</a></li>";
            });
        }
        $("#p_stuClasses").html('');
        if(rps.presult.list[3]!=null&&rps.presult.list[3].length>0){
            $.each(rps.presult.list[3],function(idx,itm){
                $('#p_stuClasses option[value='+itm.classid+']').remove();
                ghtml="<option value='"+itm.classid+"'>"+itm.classname+"</option>";
                $("#p_stuClasses").append(ghtml);
            });
            $("#my_term").show();
        }else{
            //ghtml+="<option value='0'>没有班级信息</option>";
            $("#my_term").hide();
        }
    }else{
        html+="<tr><td colspan='5'>暂无数据</td></tr>";
        ghtml+="<option value='0'>没有班级信息</option>";
        $("#my_term").hide();
    }
    $("#courseTable").html(html);
    $("#stuClasses").html(chtml);
    if(rps.presult.list[4]!=null&&rps.presult.list[4].length>0){
        var shtml='';
        $.each(rps.presult.list[4],function(idx,itm){
            shtml+='<li id="sub_'+itm.subjectid+'"><a href="javascript:searchBySubject('+itm.subjectid+')"><span id="sub_'+idx+'">'+itm.subjectname+'</span></a></li>';
        });
        $("#ul_grade").html(shtml);
        $("#sub_"+subjectid).siblings().attr("class","");
        $("#sub_"+subjectid).attr("class","crumb");
    }else{
        $("#ul_grade").html('');
    }
    $("#my_group").html("");
    if(rps.presult.list.length>2&&rps.presult.list[3]!=null&&rps.presult.list[3].length>0)
        getMyGroup();
    if(rps.presult.list.length>0){
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
}

function getMyGroup(){
    var values=$("#p_stuClasses").val();
    var subjectid=$("#subjectid").val();
    if(subjectid==null||subjectid.toString().Trim().length<1){
        alert('系统未获取到学科标识!');
        return;
    }
 //   var groupid=values.substring(0,values.indexOf("_"));
//    var groupname=values.substring(values.indexOf("_")+1,values.length);
    $.ajax({
        url:'group?m=getMyGroup',
        data:{
            //groupid:groupid
            classid:values,
            subjectid:subjectid
        },
        type:'post',
        dataType:'json',
        error:function(){
            alert('异常错误,系统未响应！');
        },
        success:function(rps){
            if(rps.objList!=null&&rps.objList.length>0){
                var html="";
                $.each(rps.objList,function(idx,itm){
                    html+='<ul>';
                    html+='<li>'+itm.teachername+'教师'+itm.groupname+'</li>';
                    if(itm.tpgroupstudent!=null&&itm.tpgroupstudent.length>0){
                        $.each(itm.tpgroupstudent,function(ix,im){
                            html+="<li>"+im.stuname+"</li>";
                        })
                    }
                    html+='</ul>';
                });
                $("#my_group").html(html);
            }else{
                $("#my_group").html("<ul><li>没有小组信息</li></ul>");
            }
        }
    });
}


function changeTerm(termid,termname){
    $("#termid").val(termid);
    $("#checkedTerm").html(termname);
    displayObj('termList',false);
    var uri="task?m=tostuSelfPerformance&termid="+$("#termid").val()+"&subjectid="+$("#subjectid").val();
    $("#task_performance").attr("href",uri);
    pageGo("pList");
}

function getTermSubject(){
    var termid=$("#termid").val();
    $.ajax({
        url:'teachercourse?m=getStuCourseSubject',
        data:{termid:termid},
        type:'post',
        dataType:'json',
        error:function(){
            alert('异常错误,系统未响应！');
        },
        success:function(rps){
            if(rps.type=="success"&&rps.objList!=null){
                var html="";
                $.each(rps.objList,function(idx,itm){
                    html+="<li id='sub_"+itm.SUBJECT_ID+"'><a href='javascript:searchBySubject("+itm.SUBJECT_ID+")'>"+itm.SUBJECT_NAME+"</a></li>";
                });
                $("#subjects").html(html);
                if(rps.objList.length>0)
                    searchBySubject(rps.objList[0].SUBJECT_ID);
                else{
                    html="<colgroup><col class='w80' /><col class='w130' /><col class='w270' /><col class='w80' /><col class='w80' /><col class='w80' /><col class='w80' /></colgroup>";
                    html+="<tr><th>状态</th><th>开课时间</th><th>专题名称</th><th>授课教师</th><th>任务</th><th>专题评价</th></tr>";
                    html+="<tr><td colspan='7'>暂无数据</td></tr>";
                    $("#courseTable").html(html);
                }
            }else{
                $("#subjects").html("");
            }
        }
    });
}

function searchBySubject(subjectid){
    $("#subjectid").val(subjectid);
    $("#sub_"+subjectid).siblings().attr("class","");
    $("#sub_"+subjectid).attr("class","crumb");
    var uri="task?m=tostuSelfPerformance&termid="+$("#termid").val()+"&subjectid="+$("#subjectid").val();
    $("#task_performance").attr("href",uri);
    pageGo("pList");
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