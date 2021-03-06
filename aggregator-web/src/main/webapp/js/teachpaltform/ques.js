/**
 * 验证参数
 * @param tobj
 */

function validateParam(tObj){
    var param=new Object();
    param.courseid=courseid;
    //var quesType=$("input[name='rdo_ques_type']:checked").val();
    //if(quesType.length>0)
    param.questype=quesType;
    tObj.setPostParams(param);
}

function questionReturn(rps){
    if(rps.type=="error"){
        alert(rps.msg);return;
    }else{
        var htm='';
        $.each(rps.objList,function(idx,itm){
            $("#dv_filter").html(itm.content);
            var content=itm.content;
            var queslevel=itm.questionlevel==3?'':'<span class="ico44"></span>';
            var questype=itm.questiontypename;
            if(itm.questiontype==2){
                content=replaceAll(content.toLowerCase(),'<span name="fillbank"></span>','______');
            }
            htm+='<table class="public_tab1" border="0" cellspacing="0" cellpadding="0">';
            htm+='<col class="w30"/>';
            htm+='<col class="w910"/>';
            htm+='<caption>';
            htm+='<p class="t_r">';
            htm+='<a  href="question?m=todetail&id='+itm.questionid+'&courseid='+itm.courseid+'" title="浏览详情" class="ico46"></a>';
            if(itm.questiontype<6){
                if(parseInt(itm.flag)>0)
                    htm+='<a  title="已发任务" class="ico52"></a><a title="不可删除" class="ico03"></a>';
                else{
                    htm+='<a href="task?toAddTask&courseid='+courseid+'&tasktype=3&questype='+itm.questiontype+'&taskvalueid='+itm.questionid+'"" title="发任务" class="ico51"></a>';
                    htm+='<a href="javascript:void(0);" onclick="doDelQuestion(\''+itm.ref+'\')" title="删除" class="ico04"></a>';
                    htm+='<a href="question?m=toUpdQuestion&questionid='+itm.questionid+'&courseid='+itm.courseid+'" title="编辑" class="ico11"></a>';
                }

            }else
                htm+='<a href="javascript:void(0);" onclick="doDelQuestion(\''+itm.ref+'\')" title="删除" class="ico04"></a>';
            htm+='</p>';
            htm+='</caption>';

            htm+='<tr id="tr_'+itm.questionid+'"';
            if(idx%2==1)
                htm+=' class="trbg2"';
            htm+='><td';
            htm+='>'+queslevel+'</td>';

            htm+='<td><span class="bg">'+questype+'</span>';
            if(typeof(itm.showExamYearMsg)!="undefined"){
                htm+='<span>('+itm.showExamYearMsg+')</span>';
            }
            htm+="<p>"+content+"</p>";
            if(itm.extension=='4'){
                htm+='<div  class="p_t_10" id="sp_mp3_'+itm.questionid+'" ></div>'
                htm+='<script type="text/javascript">';
                htm+='playSound(\'play\',\''+ques_mp3_path+itm.questionid+'/001.mp3\',270,22,\'sp_mp3_'+itm.questionid+'\',false)';
                htm+='<\/script>';
            }
            /*if(itm.extension!=4)
                htm+=content; */
            if(typeof itm.questionOptionList!='undefined'&&itm.questionOptionList.length>0 && itm.extension !=4 && itm.questiontype!=1){
                htm+='<table border="0" cellpadding="0" cellspacing="0" class="tab">';
                htm+='<col class="w30"/>';
                htm+='<col class="w850"/>';
                $.each(itm.questionOptionList,function(ix,im){
                    htm+='<tr>';
                    var type=itm.questiontype==3?"radio":"checkbox";
                    htm+='<th><input  type="'+type+'"/></th>';
                    htm+='<td>'+im.optiontype+".&nbsp;";
                    htm+=replaceAll(replaceAll(replaceAll(im.content.toLowerCase(),"<p>",""),"</p>",""),"<br>","");
                    if(im.isright==1)
                        htm+='<span class="ico12"></span>';
                    htm+='</td>';
                    htm+='</tr>';
                });
                htm+='</table>';
            }

            if(typeof itm.questionTeam!='undefined'&&itm.questionTeam.length>0&&itm.extension!='5'){
                $.each(itm.questionTeam,function(ix,m){
                    htm+='<tr>';
                    htm+='<td>&nbsp;</td>';
                    htm+='<td>'+(ix+1)+'.'+m.content;
                    if(typeof m.questionOptionList!='undefined'&&m.questionOptionList.length>0){
                        htm+='<table border="0" cellpadding="0" cellspacing="0" class="tab">';
                        htm+='<col class="w30"/>';
                        htm+='<col class="w850"/>';
                        $.each(m.questionOptionList,function(ix,im){
                            htm+='<tr>';
                            var type=im.questiontype==3||im.questiontype==7?"radio":"checkbox";
                            htm+='<th><input  type="'+type+'"/></th>';
                            htm+='<td>'+im.optiontype+".&nbsp;";
                            htm+=replaceAll(replaceAll(replaceAll(im.content.toLowerCase(),"<p>",""),"</p>",""),"<br>","");
                            if(im.isright==1)
                                htm+='<span class="ico12"></span>';
                            htm+='</td>';
                            htm+='</tr>';
                        });
                        htm+='</table>';
                    }
                    htm+='</td>';
                    htm+='</tr>';
                });
            }
            htm+='</td>';
            htm+='</tr>';
            htm+='</table>';
        })
        $("#tbl_body_data").html(htm);
        $("#page1address").show();
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
 * @decription 查看试题详细
 * @param 试题对象
 * @author 岳春阳
 * */
function showDetail(obj){

}


/**
 * 添加选择题选项
 * @param questype 1：单选 2：双选  
 * @return
 *  <tr>
        <th>选&nbsp;&nbsp;&nbsp;&nbsp;项：</th>
        <td>
        <div class="p_b_14">
        <p><strong>选项一：</strong>
          <input type="checkbox" name="checkbox" id="checkbox" /> 
            正确答案&nbsp;&nbsp;<a href="1" class="font-lightblue">添加</a>&nbsp;&nbsp;<a href="1" class="font-lightblue">删除</a></p>
        <img src="images/pic01_130423.jpg" width="612" height="264" />
        </div>
        </td>
     </tr>
 */ 

function addQuesOption(quesobj,questype,isDialog){
    var optionArray=$("tr").filter(function(){return this.id.indexOf('tr_quesoption_')!=-1});
    var len=optionArray.length;
    var sysid=new Date().getTime()+parseInt(1000000*Math.random());
    var htm='';
    htm+='<tr id="tr_quesoption_'+sysid+'">';
    if(isDialog)
        htm+='<td>A：&nbsp;</td>';
    else
        htm+='<th>A：&nbsp;</th>';
    htm+='<td>';
    if(questype=="2"){
        htm+='<input style="display:none;" name="ck_right_option" type="checkbox" value="1" id="ck_right_'+sysid+'" />';
    }else if(questype=="4"){
        htm+='<input style="display:none;" name="ck_right_option" type="checkbox"  value="1" id="ck_right_'+sysid+'" />';
    }else{
        htm+='<input style="display:none;" name="rdo_right_option" type="radio"  value="1" id="rdo_right_'+sysid+'" />';
    }
    if(isDialog)
        htm+='<a style="color: #000" id="ques_option_'+sysid+'" name="ques_option_'+sysid+'" href="javascript:void(0)" onclick="ed_option_dialog(this)">请编辑选项..</a>';
    else
        htm+='<a style="color: #000" id="ques_option_'+sysid+'" name="ques_option_'+sysid+'" href="javascript:void(0)" onclick="ed_option(this)">请编辑选项..</a>';
    htm+='<span  id="sp_img_'+sysid+'"></span>';
    htm+='</td>';
    //htm+='<td></td>'
    htm+='</tr>';

    if(len<1)
        $("#ques_tbl").html(htm);
    else{
        $("tr").filter(function(){return this.id.indexOf("tr_quesoption_")!=-1}).last().after(htm);
    }
    updOptionNumber(isDialog);
    BindText(isDialog);
}


 
/**
 * 删除选项 
 * @param aobj
 * @return
 */
function delQuesOption(questype,isDialog){
    $("tr").filter(function(){return this.id.indexOf('tr_quesoption_')!=-1}).last().remove();
 	var optionArray=$("tr").filter(function(){return this.id.indexOf('tr_quesoption_')!=-1});
 	if(optionArray.length<1)
 		addQuesOption("",questype,isDialog);
 	else{
 		updOptionNumber(isDialog);
    }
}


     
/**
 * 修改选项编号  
 * @return
 */
function updOptionNumber(isDialog){
	var optionObj=$("tr").filter(function(){return this.id.indexOf('tr_quesoption_')!=-1});
	if(optionObj.length>0){
		$.each(optionObj,function(idx,itm){
            if(isDialog)
			    $(this).children("td").eq(0).html(AZarray[idx]+".");
            else
                $(this).children("th").eq(0).html(AZarray[idx]+".");
		});	 	
	}  
} 

function changeQuesType(type){
	if(isNaN(type)){
		alert('系统未获取到问题类型!');
		return;
	}
    $("#td_ques_operate").html('');
//    $("#ques_name").html('');
//    $("#fill_answer").html('');
//    $("#ques_answer").html('');
    $("#tr_right_answer").show();


	var tr_analysis=$("#tr_analysis");
    $(tr_analysis).show();
	var questionArray=$("tr").filter(function(){return this.id.indexOf("tr_quesoption_")!=-1});
	if(questionArray.length>0)
		questionArray.remove() ;
	
	if(type==1){ //问答
        $("#type_option").hide();
        $("#correct_answer").show();
        $("#fill_answer").hide();
	}else if(type==2){ //填空
        $("#type_option").hide();
       $("#correct_answer").hide();
        $("#fill_answer").show();
	}else if(type==3){ //单选
        $("#type_option").show();
        $("#tr_right_answer").hide();
		addQuesOption(undefined,1);
        addQuesOption(undefined,1);
        addQuesOption(undefined,1);
        addQuesOption(undefined,1);
        option_edit(type);
	}else if(type==4){ //多选
        $("#type_option").show();
        $("#tr_right_answer").hide();
		addQuesOption(undefined,2);
        addQuesOption(undefined,2);
        addQuesOption(undefined,2);
        addQuesOption(undefined,2);
        option_edit(type);
	}
    BindText();
    $("#sp_edit").html('');
    $("#sp_right").html('');
}



function changeDialogQuesType(type){
    if(isNaN(type)){
        alert('系统未获取到问题类型!');
        return;
    }
    $("#td_ques_operate").html('');
    $("#tr_right_answer").show();


    var tr_analysis=$("#tr_analysis");
    $(tr_analysis).show();
    var questionArray=$("tr").filter(function(){return this.id.indexOf("tr_quesoption_")!=-1});
    if(questionArray.length>0)
        questionArray.remove() ;

    if(type==1){ //问答
        $("#type_option").hide();
        $("#correct_answer").show();
        $("#tr_right_answer #correct_answer_dialog").show();
        $("#fill_answer").hide();
    }else if(type==2){ //填空
        $("#type_option").hide();
        $("#correct_answer").hide();
        $("#fill_answer").show();
        $("#tr_right_answer #correct_answer_dialog").hide();
    }else if(type==3){ //单选
        $("#type_option").show();
        $("#tr_right_answer").hide();
        addQuesOption(undefined,1,true);
        addQuesOption(undefined,1,true);
        addQuesOption(undefined,1,true);
        addQuesOption(undefined,1,true);
        option_edit_dialog(type);
    }else if(type==4){ //多选
        $("#type_option").show();
        $("#tr_right_answer").hide();
        addQuesOption(undefined,2,true);
        addQuesOption(undefined,2,true);
        addQuesOption(undefined,2,true);
        addQuesOption(undefined,2,true);
        option_edit_dialog(type);
    }
    BindTextDialog();
    $("#sp_edit").html('');
    $("#sp_right").html('');
}

/**
 * 保存选项
 */
function sub_option(){
  var hdobj=$("#hd_id").val();

    var n = hdobj.split("_");
    if(hdobj.length<1){
        alert('请选择编辑项!');
        return;
    }
    var optionObj=$("#"+hdobj);
    if(optionObj.length<1){
        alert("请选择编辑项!");
        return;
    }
    //optionObj.html(replaceAll(replaceAll(editor.getContent(),"<p>",""),"</p>",""));
    optionObj.html(editor.getContent());
    var questype=$("input[name='rdo_ques_type']:checked").val();
    if((questype==3||questype==4)&&$(optionObj).attr("id").indexOf("ques_option_")!=-1){
        var bo=$("input[id='ck_right']").attr("checked");
        $(optionObj).prev().attr("checked",bo);
        if(bo){
            if(questype==3){
                $("span").filter(function(){return this.id.indexOf('sp_img')!=-1}).removeClass('ico12');
                $("#sp_img_"+n[2]).addClass("ico12");
               // $(optionObj).next().html('<span class="ico12"></span>');
                //fillbank_answer+=$(optionObj).parent().siblings("th").children("th").html().replace(".","");

            }else if(questype==4){
                $(optionObj).next().addClass('ico12');
            }
        }else{
            $(optionObj).next().removeClass('ico12');
        }
    }
    if(questype==2&&hdobj=='ques_name'){
        $("#dv_filter").html(editor.getContent());
        var dvobj=$("#dv_filter span[name='span']");
        if(dvobj.length<1){
            alert('提示：填空题未发现占位符号!');
            return;
        }
        var fillbank_answer='';
        $(dvobj).each(function(idx,itm){
            if(fillbank_answer.length>0)
                fillbank_answer+="|";
            fillbank_answer+=isIE?itm.innerText:itm.textContent;
        });
        $("#fill_answer").html(fillbank_answer);
    }

}


/**
 * 保存选项
 */
function sub_option_dialog(){
    var hdobj=$("#hd_id").val();

    var n = hdobj.split("_");
    if(hdobj.length<1){
        alert('请选择编辑项!');
        return;
    }
    var optionObj=$("#"+hdobj);
    if(optionObj.length<1){
        alert("请选择编辑项!");
        return;
    }
    //optionObj.html(replaceAll(replaceAll(editor.getContent(),"<p>",""),"</p>",""));
    optionObj.html(editor.getContent());
    var questype=$("input[name='rdo_ques_type_dialog']:checked").val();
    if((questype==3||questype==4)&&$(optionObj).attr("id").indexOf("ques_option_")!=-1){
        var bo=$("input[id='ck_right']").attr("checked");
        $(optionObj).prev().attr("checked",bo);
        if(bo){
            if(questype==3){
                $("span").filter(function(){return this.id.indexOf('sp_img')!=-1}).removeClass('ico12');
                $("#sp_img_"+n[2]).addClass("ico12");
                // $(optionObj).next().html('<span class="ico12"></span>');
                //fillbank_answer+=$(optionObj).parent().siblings("th").children("th").html().replace(".","");

            }else if(questype==4){
                $(optionObj).next().addClass('ico12');
            }
        }else{
            $(optionObj).next().removeClass('ico12');
        }
    }
    if(questype==2&&hdobj=='ques_name_dialog'){
        $("#dv_filter").html(editor.getContent());
        var dvobj=$("#dv_filter span[name='span']");
        if(dvobj.length<1){
            alert('提示：填空题未发现占位符号!');
            return;
        }
        var fillbank_answer='';
        $(dvobj).each(function(idx,itm){
            if(fillbank_answer.length>0)
                fillbank_answer+="|";
            fillbank_answer+=isIE?itm.innerText:itm.textContent;
        });
        $("#fill_answer").html(fillbank_answer);

    }

    $('#dv_edit_ques').show();
    $('#dv_edit_option').hide();
}

/**
 * 增加选项操作
 */
function option_edit(type){
    //var h='<a href="javascript:addQuesOption(undefined,'+type+')">添加</a>';
   // h+='<a href="javascript:void(0);" onclick="delQuesOption()">删除</a>';
    var h='<p class="p_t_10"><a href="javascript:addQuesOption(\'\','+type+')" title="添加选项" class="ico36"></a><a href="javascript:void(0);" onclick="delQuesOption()" title="移出选项" class="ico34"></a></p>';
    $("#td_ques_operate").html(h);
}


function option_edit_dialog(type){
    var h='<p class="p_t_10"><a href="javascript:void(0);"  title="添加选项" class="ico36" onclick="addQuesOption(\'\','+type+',true)"></a>';
    h+='<a href="javascript:void(0);" onclick="delQuesOption(true)" title="移出选项" class="ico34"></a></p>';
    $("#td_ques_operate").html(h);
}

/**
 * 绑定TextArea事件
 * @constructor
 */
function BindText(){
    $(".jxpt_content_layout textarea").filter(function(){return this.id!='editor'}).each(function(idx,itm){
        $(itm).bind("focus",function(){
            editor.setContent($(itm).val());
            var option=$(itm).parentsUntil("tr").siblings("th").html().replace("：","");
            $("#sp_edit").html(option.Trim());
            $("#hd_id").val($(itm).attr("id"));

            //获取问题类型
            $("#sp_right").html('');
            var questype=$("input[name='rdo_ques_type']:checked").val();
            if((questype==3||questype==4)&&itm.id.indexOf("ques_option_")!=-1){
                $("#sp_right").html("<input type='checkbox'  id='ck_right' /><label for='ck_right'>设为正确答案</label>");
                $("input[id='ck_right']").attr("checked",$(itm).prev().attr("checked"));
            }else if(questype==2){
                //$("#dv_filter").html($(itm).val());
            }
        })

    });
}



/**
 * 绑定TextArea事件
 * @constructor
 */
function BindTextDialog(){
    $(".jxpt_content_layout textarea").filter(function(){return this.id!='editor'}).each(function(idx,itm){
        $(itm).bind("focus",function(){
            editor.setContent($(itm).val());
            var option=$(itm).parentsUntil("tr").siblings("td").html().replace("：","");
            $("#sp_edit").html(option.Trim());
            $("#hd_id").val($(itm).attr("id"));

            //获取问题类型
            $("#sp_right").html('');
            var questype=$("input[name='rdo_ques_type_dialog']:checked").val();
            if((questype==3||questype==4)&&itm.id.indexOf("ques_option_")!=-1){
                $("#sp_right").html("<input type='checkbox'  id='ck_right' /><label for='ck_right'>设为正确答案</label>");
                $("input[id='ck_right']").attr("checked",$(itm).prev().attr("checked"));
            }else if(questype==2){
                //$("#dv_filter").html($(itm).val());
            }
        })

    });
}

function ed_option(itm){
    var htm=$(itm).html();
    if(htm=='请编辑选项..'||htm=='点击编辑题干……'||htm=='点击编辑答案解析……'||htm=='请编辑正确答案……')
        htm='';
    editor.setContent(htm);
    var questype=$("input[name='rdo_ques_type']:checked").val();
    var option=$(itm).parentsUntil("tr").siblings("th").html().replace("：","");
    $("#sp_edit").html(option.Trim());
    if(questype==2&&option.indexOf("题")!=-1)
        $("#sp_edit").append("&nbsp;&nbsp;&nbsp;&nbsp;<span style='color:red'>点击图标<img src='images/f_icon.png' title='填空占位符'/>插入填空占位符。</span>");
    $("#hd_id").val($(itm).attr("id"));

    //获取问题类型
    $("#sp_right").html('');

    if((questype==3||questype==4)&&itm.id.indexOf("ques_option_")!=-1){
        $("#sp_right").html("<input type='checkbox'  id='ck_right' /><label for='ck_right'>设为正确答案</label>");
        $("input[id='ck_right']").attr("checked",$(itm).prev().attr("checked"));
    }else if(questype==2){
        //$("#dv_filter").html($(itm).val());
    }
}




function ed_option_dialog(itm){
    $('#dv_edit_ques').hide();
    $('#dv_edit_option').show();


    var htm=$(itm).html();
    if(htm=='请编辑选项..'||htm=='点击编辑题干……'||htm=='点击编辑答案解析……'||htm=='请编辑正确答案……')
        htm='';
    editor.setContent(htm);
    var questype=$("input[name='rdo_ques_type_dialog']:checked").val();
    var option=$(itm).parentsUntil("tr").siblings("td").html().replace("：","");
    $("#sp_edit").html(option.Trim());
    if(questype==2&&option.indexOf("题")!=-1)
        $("#sp_edit").append("&nbsp;&nbsp;&nbsp;&nbsp;<span style='color:red'>点击图标<img src='images/f_icon.png' title='填空占位符'/>插入填空占位符。</span>");
    $("#hd_id").val($(itm).attr("id"));

    //获取问题类型
    $("#sp_right").html('');

    if((questype==3||questype==4)&&itm.id.indexOf("ques_option_")!=-1){
        $("#sp_right").html("<input type='checkbox'  id='ck_right' /><label for='ck_right'>设为正确答案</label>");
        $("input[id='ck_right']").attr("checked",$(itm).prev().attr("checked"));
    }else if(questype==2){
        //$("#dv_filter").html($(itm).val());
    }
}

/**  
 * 添加试题
 * @return
 */  
function doAddQuestion(){
	var questype=$("input[name='rdo_ques_type']:checked").val();
	var url='',iserror=0,param={},paramStr='?t='+new Date().getTime(),rflag=false,rightnum=0;
    var optionArry=new Array(),isrightArray=new Array();
	//试题
    if(questionid.length<1){
        alert('异常错误,系统未获取到试题标识!');
        return;
    }
    param.questionid=questionid;
	var quesoptionArray=$("a").filter(function(){return this.id.indexOf("ques_option_")!=-1});
    var quesname=$("#ques_name").html();
    var quesanswer=$("#ques_answer").html();
    var correctanswer=$("#correct_answer").html();
    param.questype=questype;
    if(quesname.Trim().length<1||quesname.Trim()=='点击编辑题干……'){
        alert('提示：题干不能为空!');
        $("#ques_name").focus();
        return;
    }
    param.quesname=quesname;
    if(quesanswer.Trim().length>0&&quesanswer!='点击编辑答案解析……')
        param.quesanswer=quesanswer;

    if(questype=="1"){
        param.correctanswer =correctanswer;
    }else if(questype=="2"){
        var dvobj=$("#dv_filter span[name='span']");
        if(dvobj.length<1){
            alert('提示：填空题未发现占位符号!');
            return;
        }
//        var fillbank_answer='';
//         $(dvobj).each(function(idx,itm){
//             if(fillbank_answer.length>0)
//                 fillbank_answer+="|";
//             fillbank_answer+=isIE?itm.innerText:itm.textContent;
//         });
        param.correctanswer= $("#fill_answer").html();

        $("#dv_filter span[name='span']").after("<span name=fillbank></span>").andSelf().remove()
        param.quesname=$("#dv_filter").html();
    }else if(questype=="3"||questype=="4"){
        if(quesoptionArray.length>0){
            $.each(quesoptionArray,function(idx,itm){
                var data=$(itm).html();
                if(data.Trim().length>0){
                    var _id=$(itm).attr("id").substr($(itm).attr("id").lastIndexOf('_')+1);
                    var inptype=questype=="3"?'rdo_right':'ck_right';
                    //是否是正确选项
                    var is_right=$("input[id='"+inptype+"_"+_id+"']:checked");
                   // if(paramStr.Trim().length>0)
                    //    paramStr+='&';
                    //paramStr+='optionArray='+data.Trim();
                    optionArry.push(data);

                    if(is_right.length>0){
                       // paramStr+='&is_Right=1';
                        isrightArray.push(1);
                        rflag=true;
                        rightnum+=1;
                    }else{
                        //paramStr+='&is_Right=0';
                        isrightArray.push(0);
                    }
                }else{
                    iserror=1;
                    return;
                }
            });
            if(iserror==1){
                alert('请填写选项名称!');
                return;
            }
            if(!rflag){
                alert('您未设置正确答案!');
                return;
            }else if(questype=="4"&&rightnum<2){
                alert('多选题至少两个正确答案!');
                return;
            }
        }else{
            alert('提示：选择题缺少选项!');
            return;
        }
    }
	param.courseid=courseid;
    param.optionArray=optionArry.join("#sz#");
    param.rightArray=isrightArray.join(",");

	if(!confirm('数据验证完毕!确认提交?'))
		 return;
    resetBtnAttr("btn_addQues","an_small","an_gray_small","",2);
	//开始向后台添加数据
	$.ajax({
		url:'question?m=doSubAddQuestion',
		type:"post", 
		data:paramStr+'&'+$.param(param),    
		dataType:'json',     
		cache: false,     
		error:function(){
			alert('系统未响应，请稍候重试!');
            resetBtnAttr("btn_addQues","an_small","an_gray_small","doAddQuestion()",1);

		},success:function(rmsg){
            resetBtnAttr("btn_addQues","an_small","an_gray_small","doAddQuestion()",1);
			if(rmsg.type=="error"){
				alert(rmsg.msg);
			}else{
				alert(rmsg.msg);
                if(typeof operate_type!='undefined'&&operate_type.length){
                    if (window.opener != undefined) {
                        //for chrome
                        window.opener.returnValue =rmsg.objList[0];
                    }
                    else {
                        window.returnValue =rmsg.objList[0];
                    }
                    window.close();
                }
                location.href='question?m=toQuestionList&courseid='+courseid;
			}
		}
	});
}



/**
 * 添加试题
 * @return
 */
function doAddDialogQuestion(){
    var questype=$("input[name='rdo_ques_type_dialog']:checked").val();
    var url='',iserror=0,param={},paramStr='?t='+new Date().getTime(),rflag=false,rightnum=0;
    var optionArry=new Array(),isrightArray=new Array();
    //试题
    if(questionid.length<1){
        alert('异常错误,系统未获取到试题标识!');
        return;
    }
    param.questionid=questionid;
    var quesoptionArray=$("a").filter(function(){return this.id.indexOf("ques_option_")!=-1});
    var quesname=$("#ques_name_dialog").html();
    var quesanswer=$("#ques_answer_dialog").html();
    var correctanswer=$("#correct_answer_dialog").html();
    param.questype=questype;
    if(quesname.Trim().length<1||quesname.Trim()=='点击编辑题干……'){
        alert('提示：题干不能为空!');
        $("#ques_name_dia").focus();
        return;
    }
    param.quesname=quesname;
    if(quesanswer.Trim().length>0&&quesanswer!='点击编辑答案解析……')
        param.quesanswer=quesanswer;

    if(questype=="1"){
        param.correctanswer =correctanswer;
    }else if(questype=="2"){
        var dvobj=$("#dv_filter span[name='span']");
        if(dvobj.length<1){
            alert('提示：填空题未发现占位符号!');
            return;
        }
//        var fillbank_answer='';
//         $(dvobj).each(function(idx,itm){
//             if(fillbank_answer.length>0)
//                 fillbank_answer+="|";
//             fillbank_answer+=isIE?itm.innerText:itm.textContent;
//         });
        param.correctanswer= $("#fill_answer").html();

        $("#dv_filter span[name='span']").after("<span name=fillbank></span>").andSelf().remove()
        param.quesname=$("#dv_filter").html();
    }else if(questype=="3"||questype=="4"){
        if(quesoptionArray.length>0){
            $.each(quesoptionArray,function(idx,itm){
                var data=$(itm).html();
                if(data.Trim().length>0){
                    var _id=$(itm).attr("id").substr($(itm).attr("id").lastIndexOf('_')+1);
                    var inptype=questype=="3"?'rdo_right':'ck_right';
                    //是否是正确选项
                    var is_right=$("input[id='"+inptype+"_"+_id+"']:checked");
                    // if(paramStr.Trim().length>0)
                    //    paramStr+='&';
                    //paramStr+='optionArray='+data.Trim();
                    optionArry.push(data);

                    if(is_right.length>0){
                        // paramStr+='&is_Right=1';
                        isrightArray.push(1);
                        rflag=true;
                        rightnum+=1;
                    }else{
                        //paramStr+='&is_Right=0';
                        isrightArray.push(0);
                    }
                }else{
                    iserror=1;
                    return;
                }
            });
            if(iserror==1){
                alert('请填写选项名称!');
                return;
            }
            if(!rflag){
                alert('您未设置正确答案!');
                return;
            }else if(questype=="4"&&rightnum<2){
                alert('多选题至少两个正确答案!');
                return;
            }
        }else{
            alert('提示：选择题缺少选项!');
            return;
        }
    }
    param.courseid=courseid;
    param.optionArray=optionArry.join("#sz#");
    param.rightArray=isrightArray.join(",");

    if(!confirm('数据验证完毕!确认提交?'))
        return;
   // resetBtnAttr("btn_addQues","an_small","an_gray_small","",2);
    //开始向后台添加数据
    $.ajax({
        url:'question?m=doSubAddQuestion',
        type:"post",
        data:paramStr+'&'+$.param(param),
        dataType:'json',
        cache: false,
        error:function(){
            alert('系统未响应，请稍候重试!');
            //resetBtnAttr("btn_addQues","an_small","an_gray_small","doAddQuestion()",1);

        },success:function(rmsg){
            //resetBtnAttr("btn_addQues","an_small","an_gray_small","doAddQuestion()",1);
            if(rmsg.type=="error"){
                alert(rmsg.msg);
            }else{
                var questype = $("input[name='rdo_ques_type']:checked").val();
                var returnValue=rmsg.objList[0];
                $.ajax({
                    url: "question?m=questionAjaxList",
                    type: "post",
                    data: {questype: questype, courseid: courseid, questionid: returnValue},
                    dataType: 'json',
                    cache: false,
                    error: function () {
                        alert('系统未响应，请稍候重试!');
                    }, success: function (rmsg) {
                        if (rmsg.type == "error") {
                            alert(rmsg.msg);
                        } else {
                            var htm = '';
                            $("#ques_name").html('');
                            $("#ques_answer").html('');
                            $("#td_option").hide();
                            $("#td_option").html('');
                            if (rmsg.objList.length && typeof returnValue != 'undeinfed' && returnValue.toString().length > 0) {
                                $("#hd_questionid").val(rmsg.objList[0].questionid);
                                $("#sp_ques_id").html(rmsg.objList[0].questionid);
                                load_ques_detial(returnValue, questype);
                            }
                            $("#tr_ques_obj").show();
                            $.fancybox.close();
                        }
                    }
                });
            }
        }
    });
}




/**
 * 修改试题
 * @return
 */
function doUpdQuestion(questionid){
    if(typeof questionid=='undefined'){
        alert('系统未获取到试题标识!请刷新页面重试!');
        return;
    }
    var param={};
    var questype=$("input[name='rdo_ques_type']:checked").val();
    var url='',iserror=0,param={},paramStr='?t='+new Date().getTime(),rflag=false;
    var optionArry=new Array(),isrightArray=new Array();

    //试题
    var quesoptionArray=$("a").filter(function(){return this.id.indexOf("ques_option_")!=-1});
    var quesname=$("#ques_name").html();
    var quesanswer=$("#ques_answer").html();
    var correctanswer=$("#correct_answer").html();
    param.questype=questype;
    if(quesname.Trim().length<1){
        alert('提示：题干不能为空!');
        $("#ques_name").focus();
        return;
    }
    param.quesname=quesname;
    if((questype!=2) && (quesanswer.Trim().length<1)){
        alert('提示：答案解析不能为空!');
        return;
    }
    param.quesanswer=quesanswer;
    if(questype==1){
        param.correctanswer=correctanswer;
    }
   if(questype=="2"){
//        var dvobj=$("#dv_filter p > span[name='span']");
//        if(dvobj.length<1){
//            alert('提示：填空题未发现占位符号!');
//            return;
//        }
//        var fillbank_answer='';
//        $(dvobj).each(function(idx,itm){
//            if(fillbank_answer.length>0)
//                fillbank_answer+="|";
//            fillbank_answer+=isIE?itm.innerText:itm.textContent;
//        });
//        param.quesanswer=fillbank_answer;
//        $("#dv_filter p > span[name='span']").after("<span name=fillbank></span>").andSelf().remove()
//        param.quesname=$("#dv_filter").html();
       param.correctanswer= $("#fill_answer").html();

       $("#dv_filter span[name='span']").after("<span name=fillbank></span>").andSelf().remove()
       param.quesname=$("#dv_filter").html();
    }else if(questype=="3"||questype=="4"){
        if(quesoptionArray.length>0){
            $.each(quesoptionArray,function(idx,itm){
                var data=$(itm).html();
                if(data.Trim().length>0){
                    var _id=$(itm).attr("id").substr($(itm).attr("id").lastIndexOf('_')+1);
                    var inptype=questype=="3"?'rdo_right':'ck_right';
                    //是否是正确选项
                    var is_right=$("input[id='"+inptype+"_"+_id+"']:checked");
//                    if(paramStr.Trim().length>0)
//                        paramStr+='&';
//                    paramStr+='optionArray='+data;
                    optionArry.push(data);

                    if(is_right.length>0){
                        // paramStr+='&is_Right=1';
                        isrightArray.push(1);
                        rflag=true;
                    }else{
                        //paramStr+='&is_Right=0';
                        isrightArray.push(0);
                    }
                }else{
                    iserror=1;
                    return;
                }
            });
            if(iserror==1){
                alert('请填写选项名称!');
                return;
            }
            if(!rflag){
                alert('您未设置正确答案!');
                return;
            }
        }else{
            alert('提示：选择题缺少选项!');
            return;
        }
    }
    param.courseid=courseid;
    param.questionid=questionid;
    param.optionArray=optionArry.join("#sz#");
    param.rightArray=isrightArray.join(",");
    if(paperid.length>0)
        param.paperid=paperid;

    if(!confirm('数据验证完毕!确认提交?'))
        return;
    //开始向后台添加数据
    $.ajax({
        url:'question?m=doSubUpdQuestion',
        type:"post",
        data:paramStr+'&'+$.param(param),
        dataType:'json',
        cache: false,
        error:function(){
            alert('系统未响应，请稍候重试!');
        },success:function(rmsg){
            if(rmsg.type=="error"){
                alert(rmsg.msg);
            }else{
                alert(rmsg.msg);
                if(paperid.length>0){
                    window.returnValue='ok';
                    window.close();
                }
                location.href="question?m=toQuestionList&courseid="+courseid;
            }
        }
    });
}



/**
 * 修改试题
 * @return
 */
function doUpdDialogQues(questionid){
    if(typeof questionid=='undefined'){
        alert('系统未获取到试题标识!请刷新页面重试!');
        return;
    }
    var param={};
    var questype=$("input[name='rdo_ques_type_dialog']:checked").val();
    var url='',iserror=0,param={},paramStr='?t='+new Date().getTime(),rflag=false;
    var optionArry=new Array(),isrightArray=new Array();

    //试题
    var quesoptionArray=$("a").filter(function(){return this.id.indexOf("ques_option_")!=-1});
    var quesname=$("#ques_name_dialog").html();
    var quesanswer=$("#ques_answer_dialog").html();
    var correctanswer=$("#correct_answer_dialog").html();
    param.questype=questype;
    if(quesname.Trim().length<1){
        alert('提示：题干不能为空!');
        $("#ques_name").focus();
        return;
    }
    param.quesname=quesname;
    if((questype!=2) && (quesanswer.Trim().length<1)){
        alert('提示：答案解析不能为空!');
        return;
    }
    param.quesanswer=quesanswer;
    if(questype==1){
        param.correctanswer=correctanswer;
    }
    if(questype=="2"){
//        var dvobj=$("#dv_filter p > span[name='span']");
//        if(dvobj.length<1){
//            alert('提示：填空题未发现占位符号!');
//            return;
//        }
//        var fillbank_answer='';
//        $(dvobj).each(function(idx,itm){
//            if(fillbank_answer.length>0)
//                fillbank_answer+="|";
//            fillbank_answer+=isIE?itm.innerText:itm.textContent;
//        });
//        param.quesanswer=fillbank_answer;
//        $("#dv_filter p > span[name='span']").after("<span name=fillbank></span>").andSelf().remove()
//        param.quesname=$("#dv_filter").html();
        param.correctanswer= $("#fill_answer").html();

        $("#dv_filter span[name='span']").after("<span name=fillbank></span>").andSelf().remove()
        param.quesname=$("#dv_filter").html();
    }else if(questype=="3"||questype=="4"){
        if(quesoptionArray.length>0){
            $.each(quesoptionArray,function(idx,itm){
                var data=$(itm).html();
                if(data.Trim().length>0){
                    var _id=$(itm).attr("id").substr($(itm).attr("id").lastIndexOf('_')+1);
                    var inptype=questype=="3"?'rdo_right':'ck_right';
                    //是否是正确选项
                    var is_right=$("input[id='"+inptype+"_"+_id+"']:checked");
//                    if(paramStr.Trim().length>0)
//                        paramStr+='&';
//                    paramStr+='optionArray='+data;
                    optionArry.push(data);

                    if(is_right.length>0){
                        // paramStr+='&is_Right=1';
                        isrightArray.push(1);
                        rflag=true;
                    }else{
                        //paramStr+='&is_Right=0';
                        isrightArray.push(0);
                    }
                }else{
                    iserror=1;
                    return;
                }
            });
            if(iserror==1){
                alert('请填写选项名称!');
                return;
            }
            if(!rflag){
                alert('您未设置正确答案!');
                return;
            }
        }else{
            alert('提示：选择题缺少选项!');
            return;
        }
    }
    param.courseid=courseid;
    param.questionid=questionid;
    param.optionArray=optionArry.join("#sz#");
    param.rightArray=isrightArray.join(",");
    if(paperid.length>0)
        param.paperid=paperid;

    if(!confirm('数据验证完毕!确认提交?'))
        return;
    //开始向后台添加数据
    $.ajax({
        url:'question?m=doSubUpdQuestion',
        type:"post",
        data:paramStr+'&'+$.param(param),
        dataType:'json',
        cache: false,
        error:function(){
            alert('系统未响应，请稍候重试!');
        },success:function(rmsg){
            if(rmsg.type=="error"){
                alert(rmsg.msg);
            }else{
                //loadPaperQues(questionid);
                loadDiv(3,undefined,questionid);
            }
        }
    });
}


/**
 * 删除试题
 * @param quesid
 */
function doDelQuestion(ref){
    if(typeof(ref)=='undefined'||isNaN(ref)){
        alert('系统未获取到试题标识!');
        return;
    }
    if(typeof(courseid)=='undefined'||isNaN(courseid)){
        alert('系统未获取到专题标识!');
        return;
    }

    if(!confirm('确认删除?'))return;

    $.ajax({
        url:'question?m=del',
        type:"post",
        data:{
            ref:ref
        },
        dataType:'json',
        cache: false,
        error:function(){
            alert('系统未响应，请稍候重试!');
        },success:function(rmsg){
            if(rmsg.type=="error"){
                alert(rmsg.msg);
            }else{
                alert(rmsg.msg);
                pageGo('p1');
            }
        }
    });
}

function doStuSubmitQues(tasktype,taskid,quesid,groupid,questype){
	if(typeof(tasktype)=='undefined'||typeof(taskid)=='undefined'||
			typeof(quesid)=='undefined'||courseid.length<1){
		alert('未知的任务信息!请联系相关人员!');
		return;
	}
	//回答
	var txt_taskanswer=$("#txt_taskanswer_"+quesid);
	//填空
	var txt_fb_option=$("input[name='txt_fb_"+quesid+"']");
	var txt_fb_answer=$("input[name='txt_fb_"+quesid+"']").filter(function(){return this.value.Trim().length>0});
	//选择
	var optionArray=$("input[name='ques_option_"+quesid+"']:checked");
	 
	var paramStr='t='+new Date().getTime();
	var param={tasktype:tasktype,taskid:taskid,quesid:quesid,courseid:courseid,groupid:groupid};
	if(typeof(questype)!='undefined'&&!isNaN(questype))
		param.questype=questype; 
	 
	if(tasktype==3){ 
		if(questype==1){
			if(txt_taskanswer.val().Trim().length<1){
				alert('请输入问答题答案!');
				txt_taskanswer.focus();
				return;
			}
			param.quesanswer=txt_taskanswer.val();
			
		}else if(questype==2){
			if(txt_fb_option.length<1){
				alert('抱歉,未发现填空题回答输入框!');
				return;
			}
			if(txt_fb_option.length>txt_fb_answer.length){
				alert('请完成填空录入后提交!');
				return;
			}
			$.each(txt_fb_answer,function(idx,itm){
				if(paramStr.length>0)
					paramStr+="&";
				paramStr+="fbanswerArray="+$(itm).val();
			});
			
		}else if(questype==3||questype==4){
			if(optionArray.length<1){
				alert('请选择选项后提交!');
				return;
			}
			$.each(optionArray,function(idx,itm){
				if(paramStr.length>0)
					paramStr+="&";
				paramStr+="optionArray="+$(itm).val();
			});
		}
	}else if(tasktype==1){
       /* if ("ue"+taskid.getContent().Trim().length < 1) {
            alert('请输入学习心得后提交!');
            return;
        }
        param.quesanswer = "ue"+taskid..getContent().Trim(); */
	}
	
	//alert(paramStr);
	if(!confirm('数据验证完毕!确认提交?'))
		 return;
	//开始向后台添加数据  
	$.ajax({
		url:'task?doStuSubmitTask',
		type:"post", 
		data:paramStr+'&'+$.param(param),    
		dataType:'json',     
		cache: false,     
		error:function(){
			alert('系统未响应，请稍候重试!');
		},success:function(rmsg){
			if(rmsg.type=="error"){
				alert(rmsg.msg);
			}else{
				alert(rmsg.msg); 
				location.href='task?toStuTaskIndex&courseid='+courseid; 
			}
		}
	});
}
 

/**
 *  提交学生建议
 * @param taskid
 * @return
 */
function doSubSuggest(taskid){
	if(typeof(taskid)=='undefined'||taskid.length<1){
		alert('异常错误!参数异常,请刷新页面重试!');
		return;
	}
	if(typeof(courseid)=='undefined'||courseid.length<1){
		alert('异常错误!参数异常,请刷新页面重试!');
		return;
	}
	var msg=$("#txt_suggest_"+taskid);
	if(msg.val().Trim().length<1){
		alert('请输入建议内容后提交!');
		msg.focus();
		return;
	}
	var isanonmous=$("input[id='ck_anonmous_"+taskid+"']:checked");
	var param={
			courseid:courseid,
			taskid:taskid,
			suggestcontent:msg.val().Trim(),
			isanonmous:0 
	};
	if(isanonmous.length>0)
		param.isanonmous=isanonmous.val();  
	 
	//开始向后台添加数据   
	$.ajax({
		url:'task?doSubmitSuggest',
		type:"post", 
		data:param,
		dataType:'json',     
		cache: false,     
		error:function(){
			alert('系统未响应，请稍候重试!');
		},success:function(rmsg){
			if(rmsg.type=="error"){
				alert(rmsg.msg);
			}else{
				alert(rmsg.msg); 
				$("#txt_suggest_"+taskid).val('');
				closeModel('div_suggest_'+taskid+''); 
			}
		}
	});
	
}

function genderShowdiv(name,time){
	$("#div_tmp_show01").remove();
	//得到准确的 鼠标位置
	var y=mousePostion.y;
	var x=mousePostion.x;
	//判断是不是IE8以下的浏览器浏览
	if ($.browser.msie && (parseInt($.browser.version) <= 7)){
		y+=parseFloat(document.documentElement.scrollTop);
		x+=parseFloat(document.documentElement.scrollLeft); 
	}		
	y+=5;
	var h='<div id="div_tmp_show01" '
			+'style="position:absolute;padding:5px;border:1px solid green;left:'
			+x+'px;top:'+y+'px;background-color:white">'+name+'&nbsp;'+time+'</div>';
	$("body").append(h);
}

/**
 * 查询学生完成情况
 * @return

function loadStuPerformance(classid,tasktype){  
	$("#a_class_"+classid).parent("li").siblings().removeClass("crumb").end().addClass("crumb");   
	        
	if(typeof(classid)=='undefined'||isNaN(classid)){
		alert('异常错误，参数错误!');
		return; 
	}   
	if(typeof(taskid)=='undefined'||taskid.length<1){
		alert('异常错误，参数错误!');
		return;
	} 
	
	$.ajax({
		url:'task?loadStuPerformance',
		type:"post", 
		data:{
			taskid:taskid,
			classid:classid
		},
		dataType:'json',     
		cache: false,     
		error:function(){
			alert('系统未响应，请稍候重试!');
		},success:function(rmsg){
			var htm=''; 
			htm+='<colgroup><col class="w200" />'
				+'<col class="w110" />' 
				+'<col class="w160" />'
				+'<col class="w390" />'
				+'<col class="w110" /></colgroup>';
			htm+='<tr>';
			htm+='<th>学号</th>';
			htm+='<th>姓名</th>';
			htm+='<th>学习时间</th>';
			htm+='<th>数据统计</th>';
			htm+='<th>状态</th>';    
			htm+='</tr>'; 
			if(rmsg.objList.length>0){
				$.each(rmsg.objList,function(idx,itm){
					htm+='<tr>';  
					htm+='<td>'+itm.userinfo.stuNo+'</td>';
					htm+='<td>'+itm.userinfo.stuname+'</td>';   
					if(typeof(itm.ctimeString)!='undefined')
						htm+='<td>'+itm.ctimeString+'</td>';
					else
						htm+='<td></td>';
					if(itm.performanceList.length>0){
						htm+='<td><p>';
						$.each(itm.performanceList,function(ix,im){ 
							htm+='<a title="主题" target="_blank" href="activetopic/toTopicDetail?topicid='+im.topicid+'&themeid='+itm.answercontent+'">'+im.topictitle+'</a>&nbsp;&nbsp;';
						})       
						htm+='</p></td>';      
					}else if(itm.tasktype!=2){
						var content=typeof(itm.answercontent)=='undefined'?'':itm.answercontent;  
						htm+='<td><p>'+content+'</p></td>';  
					}else{
						htm+='<td></td>';
					}              
					if(itm.status==1)
						htm+='<td><span style="color:green;">已达标</span></td>';  
					else  
						htm+='<td>未达标</td>'; 
					htm+='</tr>';
				});  
			}else{ 
				htm+='<tr><td>暂无数据!</td></tr>';
			} 
			$("#initItemList").hide();
			$("#initItemList").html(htm); 
			$("#initItemList").show("fast");
		} 
	});
}
 */
 