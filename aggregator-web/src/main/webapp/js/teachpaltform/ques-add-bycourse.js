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
                var type=operate_type.length>0?"radio":"checkbox";
                shtml+='<input type="'+type+'" onclick="getQuestionIds(this,'+itm.questionid+')" value="'+itm.questionid+'" ';
                if(questionids.length>0){
                    $.each(questionids,function(x,m){
                        if(m==itm.questionid){
                            shtml+='checked="checked"';
                        }
                    });
                }
                if(itm.state!=null&&itm.state>0){
                    shtml+='checked="checked" disabled="disabled"';
                }else
                    shtml+='name="question"';

                var content=replaceAll(itm.content.toLowerCase(),'<span name="fillbank"></span>','______');
                shtml+='/>';
                shtml+='</td>';
                shtml+='<td>';
                shtml+='<p>'+content+'</p>';
                if(itm.extension=='4'){

                    shtml+='<br><div  class="p_t_10" id="sp_mp3_'+itm.questionid+'" ></div>'
                    shtml+='<script type="text/javascript">';
                    shtml+='playSound(\'play\',\''+ques_mp3_path+itm.questionid+'/001.mp3\',270,22,\'sp_mp3_'+itm.questionid+'\',false)';
                    shtml+='<\/script>';
                }
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

                if(typeof itm.questionTeam!='undefined'&&itm.questionTeam.length>0&&itm.extension!='5'){
                    $.each(itm.questionTeam,function(ix,m){
                        shtml+='<tr>';
                        shtml+='<td>&nbsp;</td>';
                        shtml+='<td>'+(ix+1)+'.'+m.content;
                        if(typeof m.questionOptionList!='undefined'&&m.questionOptionList.length>0){
                            shtml+='<table border="0" cellpadding="0" cellspacing="0" class="tab">';
                            shtml+='<col class="w30"/>';
                            shtml+='<col class="w850"/>';
                            $.each(m.questionOptionList,function(ix,im){
                                shtml+='<tr>';
                                var type=im.questiontype==3||im.questiontype==7?"radio":"checkbox";
                                shtml+='<th><input  type="'+type+'"/></th>';
                                shtml+='<td>'+im.optiontype+".&nbsp;";
                                shtml+=replaceAll(replaceAll(replaceAll(im.content.toLowerCase(),"<p>",""),"</p>",""),"<br>","");
                                if(im.isright==1)
                                    shtml+='<span class="ico12"></span>';
                                shtml+='</td>';
                                shtml+='</tr>';
                            });
                            shtml+='</table>';
                        }
                        shtml+='</td>';
                        shtml+='</tr>';
                    });
                }




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
    if(operate_type.length>0){
        questionids=new Array();
        questionids[0]=quesitonid;
    }else{
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
            $("#btn_addQues").attr("href","javascript:;");
            $.ajax({
                url:'question?m=addQuestionByCourse',
                dataType:'json',
                type:"post",
                cache: false,
                data:param,
                error:function(){
                    alert('异常错误!系统未响应!');
                    $("#btn_addQues").attr("href","javascript:questionSubmit();");
                },success:function(rps){
                    $("#btn_addQues").attr("href","javascript:questionSubmit();");
                    if(rps.type="success"){
                        alert(rps.msg);
                        if(operate_type.length>0){
                            if (window.opener != undefined) {
                                //for chrome
                                window.opener.returnValue =ids;
                            }
                            else {
                                window.returnValue =ids;
                            }
                            window.close();
                        }
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