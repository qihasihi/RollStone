function questionListReturn(rps){
    if(rps.type=='error'){
        alert(rps.msg);
        if (typeof (p1) != "undefined" && typeof (p1) == "object") {
            // 设置空间不可用
            p1.setPagetotal(1);
            p1.setRectotal(0);
            p1.Refresh();
            // 设置显示值
            var shtml = '<tr><td align="center">暂时没有试题!';
            shtml += '</td></tr>';
            $("#mainTbl").html(shtml);
        }
    }else{
        var shtml = '';
        if(rps.objList.length<1){
            shtml+="<tr><th colspan='8' style='height:65px' align='center'>暂无信息!</th></tr>";
        }else{
            $.each(rps.objList,function(idx,itm){
                shtml+='<tr><td>';
                shtml+='<input type="checkbox" onclick="getQuestionIds(this,'+itm.questionid+')" value="'+itm.questionid+'" name="question"';
                if(questionids.length>0){
                    $.each(questionids,function(x,m){
                        if(m==itm.questionid){
                            shtml+='checked="checked"';
                        }
                    });
                }
                if(itm.state!=null&&itm.state>0){
                    shtml+='checked="checked" disabled="disabled"';
                }
                var content=replaceAll(itm.content.toLowerCase(),'<span name="fillbank"></span>','______');
                shtml+='/>';
                shtml+='</td>';
                shtml+='<td>';
                shtml+='<p>'+content+'</p>';
                shtml+='<p>';
                $.each(itm.questionOptionList,function(ix,im){
                    var type=itm.questiontype==3?"radio":"checkbox";
                    shtml+='<input disabled type="'+type+'"/>';
                    shtml+=im.optiontype+".&nbsp;";
                    shtml+=replaceAll(replaceAll(replaceAll(im.content.toLowerCase(),"<p>",""),"</p>",""),"<br>","");
                    if(im.isright==1)
                        shtml+='<span class="ico12"></span>';
                    shtml+='<br>';
                });
                shtml+='</p>';
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

function queryList(){
    var selectCourseid = $("#selectCourseid").val();
    var param = {};
    param.selectCourseid=selectCourseid;
    $.ajax({
        url:'question?m=getQuestionByCourse',
        dataType:'json',
        type:"post",
        cache: false,
        data:param,
        error:function(){
            alert('异常错误!系统未响应!');
        },success:function(rps){
            questionListReturn(rps);
        }
    });
}

function getQuestionIds(obj,quesitonid){
    if(obj.checked){
        questionids[questionids.length]=quesitonid;
    }else{
        if(questionids.length>0){
            for(var i =0;i<questionids.length;i++){
                if(questionids[i]==questionid){
                    questionids[i]=null;
                }
            }
        }
    }
}

function questionSubmit(){
    var param = {};
    var ids="";
    if(questionids.length>0){
        for(var i = 0;i<questionids.length;i++){
            if(questionids[i]!=null){
                ids+=questionids[i];
            }
            if(i<questionids.length-1){
                ids+="|";
            }
        }
        param.questionids=ids;
        if(addCourseId.length>0){
            param.addCourseId = addCourseId;
            $.ajax({
                url:'question?m=addQuestionByCourse',
                dataType:'json',
                type:"post",
                cache: false,
                data:param,
                error:function(){
                    alert('异常错误!系统未响应!');
                },success:function(rps){
                    if(rps.type="success"){
                        alert(rps.msg);
                       pageGo("p1");
                    }else{
                        alert(rps.msg);
                    }
                }
            });
        }else{
            alert("未找到您要添加的专题标示，请重试");
        }
    }else{
        alert("您尚未选择试题，请选择后在进行操作")
    }
}