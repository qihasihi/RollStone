/**
* Created by zhengzhou on 14-8-25.
*/
var subQuesId=",";
var TestPaperQues;
if (TestPaperQues == undefined) {
    TestPaperQues = function(settings) {
        this.initController(settings);
    }
};

TestPaperQues.prototype.initController=function(obj){
    this.currentQuesObj={idx:-1,quesid:0,parentQuesId:0};
    this.config=obj;

    this.nextNum(1);
};
TestPaperQues.prototype.nextNum=function(type){
    if(typeof(this.config.quesidstr)=="undefined"||this.config.quesidstr.length<1){
        alert('异常错误，参数异常!');return;
    }
    if(type==-2)
    return;

    if(typeof(this.config.quesidstr)!="undefined"&&this.config.quesidstr.Trim().length>0){
        var arrayObj=this.config.quesidstr.split(",");
        if(arrayObj.length>0){
            if(type==1){
                this.currentQuesObj.idx=this.currentQuesObj.idx+1;
                if(this.currentQuesObj.idx>=arrayObj.length-1){
                    this.currentQuesObj.idx=arrayObj.length-1;
                    if(confirm("最后一题了，是否交卷?")){
                        this.subPaper();
                    }else
                        return;
                }
            }else{
                this.currentQuesObj.idx=this.currentQuesObj.idx-1;
                if(this.currentQuesObj.idx<=0)
                    this.currentQuesObj.idx=0;
            }
            var cuQuesid=arrayObj[this.currentQuesObj.idx];
            var parentQuesId=0;
            if(cuQuesid.indexOf("|")!=-1){
                cuQuesid=cuQuesid.split("|")[1];
                parentQuesId=cuQuesid.split("|")[0];
            }
            this.currentQuesObj={idx:this.currentQuesObj.idx,quesid:cuQuesid,parentQuesId:parentQuesId};
            //下一体
            this.loadQues();
        }
    }
};

/**
 * 交卷
 */
TestPaperQues.prototype.subPaper=function(){
    var currentQuesId=this.currentQuesObj.parentQuesId;

    //提交
    var pid=this.config.paperid;
    if(typeof(pid)=="undefined"||pid.length<1){
        alert('异常错误，参数异常!');return;
    }
    var confirmMsg='是否确定交卷？ 提示：提交之后，你将无法再次作答。 ';

    var alaqLastQues="";
    var allquesArray=this.config.quesidstr.split(",");
    var curQuesType=$("#hd_questiontype_"+currentQuesId).val();
        var aqLastQuesArr=allquesArray[allquesArray.length-1];
        alaqLastQues=aqLastQuesArr;
        if(aqLastQuesArr.indexOf('|')!=-1){
            alaqLastQues=aqLastQuesArr.split("|")[1];
        }
//        if(currentQuesId==alaqLastQues&&subQuesId.indexOf(","+currentQuesId+",")==-1){
//
//        }
    subQuesId+=currentQuesId+",";
    this.freeSubQuesAnswer(-2);



    if(subQuesId.Trim()==","||subQuesId.Trim().length<1){
        confirmMsg="你尚未开始作答，确定交卷？\n\n提示：提交之后，你将无法再次作答。";
    }else{
        var subid=subQuesId;
        if(subid.indexOf(",")==0)
            subid=subid.substring(1);
        if(subid.lastIndexOf(",")==subid.length-1)
            subid=subid.substring(0,subid.length-1);
        var subQuesArray=subid.split(",");
        if(allquesArray.length>0){
            if(subQuesArray.length!=allquesArray.length)
                confirmMsg="你尚有试题没有作答，确定交卷？\n\n提示：提交之后，你将无法再次作答。";
        }
    }
    if(!confirm(confirmMsg))
        return;


    var cid=courseid;
    $.ajax({
        url:"paperques?m=doInPaper",
        dataType:'json',
        type:'post',
        data:{paperid:pid,courseid:cid,taskid:taskid},
        cache: false,
        error:function(){
            alert('异常错误!系统未响应!');
        },success:function(rps){
            if(rps.type=="success"){
                //alert(rps.msg);
                // 进入详情页面
                var lo="paperques?m=toTestPaper&paperid="+pid+"&courseid="+courseid+"&taskid="+taskid;
                if(typeof(papertype)!="undefined"&&papertype==5){
                    lo+="&isMidView=1";
                }
                location.href=lo;
            }else
                alert(rps.msg);
        }
    })
}
/**
 * 提交答案前
 */
TestPaperQues.prototype.freeSubQuesAnswer=function(direcType){
    var quesid=this.currentQuesObj.quesid;
    var questype=$("#dv_question div input[id='hd_questiontype_"+quesid+"']").val();
    var scoreObj=$("#dv_question div input[id='hs_score_"+quesid+"']");
    var score=scoreObj.val();
    var answer="";
    var isright=0;

    var issubmit=true;
    if(questype==7) //单选组 按单选入库
        questype=3;
    else if(questype==8)   //复选组，按复选入库
        questype=4;
    if(questype==3||questype==4){
        var ckLen=$("input[name='rdo_answer"+quesid+"']:checked");
        if(ckLen.length>0){
            if(questype==4){
                var len=$("input[name='rdo_answer"+quesid+"'][value*='1']").length;
                var iserror=false;
                $.each(ckLen,function(idx,itm){
                    answer+=itm.value.split("|")[0]+"|";
                    if(!iserror){
                        if(this.value.indexOf("|")!=-1){
                            if(this.value.split("|")[1]==0){
                                iserror=true;
                            }
                        }
                    }
                })
                answer=answer.substring(0,answer.length-1);
                if(!iserror){
                    if(ckLen.length!=len){//答对相关题，判正确，给半分
                        score=parseFloat(score/2).toFixed(2);
                        $("#hd_isright_"+quesid).val(2);
                    }else{
                        $("#hd_isright_"+quesid).val(1);
                    }
                }else{                 //答错，不给分
                    score=0;
                    $("#hd_isright_"+quesid).val(2);
                }
            }else{
                answer+=ckLen.val();
                var rtVal=$("#hd_rightVal"+quesid).val();
                if(rtVal.Trim()==answer.Trim()){
                    isright=1;
                }else
                    isright=0;

                if(isright==0){
                    score=0;
                    $("#hd_isright_"+quesid).val(2);
                }else if(isright==1){
                    $("#hd_isright_"+quesid).val(1);
                }
            }
        }else
            issubmit=false;
    }else if(questype==1){
        answer=$("#txt_answer_"+quesid).val().Trim();
        if(answer.length<1&&$("#txt_f2"+quesid).val().length<1)
            issubmit=false;
    }else if(questype==2){
        var tkLen=$("#dv_qs_"+quesid+" input[name='txt_tk']");
        if(tkLen.length>0){
            $.each(tkLen,function(ix,im){
                answer+=im.value.Trim()+"|";
            });
            answer=answer.substring(0,answer.length-1);
        }

        if(answer.replace(/\|/g,"").Trim().length<1&&$("#txt_f2"+quesid).val().length<1)
            issubmit=false;
    }
    $("input[id='hs_val_stu_"+quesid+"']").val(score);
    $("input[id='hs_val_"+quesid+"']").val(answer);

        if(issubmit){
            //提交数据
            this.subQues(direcType);
        }else{
            if(direcType!=-2)
                this.nextNum(direcType);
        }

}
/**
 * 提交答案
 */
TestPaperQues.prototype.subQues=function(direcType){
    var quesid=this.currentQuesObj.quesid;
    var taskid=this.config.taskid;
    var pid=this.config.paperid;

    var quesAnswerObj=$("#dv_question div input[id='hs_val_"+quesid+"']");
    var quesidObj=$("#dv_question div input[id='hd_quesid_"+quesid+"']");
    var scoreObj=$("#dv_question div input[id='hs_val_stu_"+quesid+"']");
    var questype=$("#dv_question div input[id='hd_questiontype_"+quesid+"']");
    var isright=$("#hd_isright_"+quesid).val();
    var data=new Array(),noanswer=new Array();
    if(quesAnswerObj.length>0&&quesAnswerObj.length==quesidObj.length&&scoreObj.length==quesidObj.length){
        for(var i=0;i<quesidObj.length;i++){
            if(quesAnswerObj[i].value.length<1){
                noanswer[noanswer.length]=i+1;
            }
            var tpanswer=quesAnswerObj[i].value;
            if(tpanswer.Trim().length>0){
                tpanswer=encodeURIComponent(tpanswer.replaceAll('"',"&quot;"));
            }
            data[i]={questionid:quesidObj[i].value,answer:tpanswer,score:scoreObj[i].value,questype:questype[i].value,isright:isright};

        }
    }
    var controlObj=this;
    //提交
    var pid=this.config.paperid;
    if(typeof(this.config.paperid)=="undefined"||pid.length<1){
        alert('异常错误，参数异常!');return;
    }
    //选择题
    var annexObj=$("#txt_f_"+quesid);
    //附件上传
    if(annexObj.length>0&&annexObj.val().Trim().length>0){
        //先附件上传
        if(!isTishiAnnex){
            alert("该题您选择了附件上传,将会先提交附件再提交数据，请耐心等待上传完毕!\n\n上传完毕后，会自动进行下一题!");
            isTishiAnnex=true;
        }
        //   var lastname = annexObj.val().substring(annexObj.val().lastIndex("/"));
        var url="paperques?m=doSaveTestPaper&taskid="+taskid;
        $.ajaxFileUpload({
            url: url + "&userid="+this.config.userid+"&paperid="+pid+"&hasannex=1&testQuesData="+ $.toJSON(data),
            fileElementId: 'txt_f_'+quesid,
            dataType: 'json',
            secureuri: false,
            type: 'POST',
            success: function (rps, status) {
                if(rps.type=="success"){
                    if(subQuesId.indexOf(","+quesid+",")<0)
                        subQuesId+=quesid+","
                    controlObj.nextNum(direcType);
                }else
                    alert(rps.msg);
            },
            error: function (data, status, e) {
                alert(e);
            }
        });
    }else{
        $.ajax({
            url:"paperques?m=doSaveTestPaper",
            dataType:'json',
            type:'post',
            data:{taskid:taskid,paperid:pid,userid:this.config.userid,testQuesData:$.toJSON(data)},
            cache: false,
            error:function(){
                alert('异常错误!系统未响应!');
            },success:function(rps){
                if(rps.type=="success"){
                    if(subQuesId.indexOf(","+quesid+",")<0)
                        subQuesId+=quesid+",";
                    controlObj.nextNum(direcType);
                }else
                    alert(rps.msg);
            }
        })
    }
}

/**
 * 加载答案
 */
TestPaperQues.prototype.loadQues=function(){
    $("#dv_question>div").hide();
    if($("#dv_q_"+this.currentQuesObj.questionid).length>0)
        $("#dv_q_"+this.currentQuesObj.questionid).show();

    var config=this.config;
    var cQuesObj=this.currentQuesObj;
    var ajxObj={
        url:'paperques?m=nextTestQues',
        dataType:'json',
        type:'post',
        data:{userid:this.config.userid,
            quesid:this.currentQuesObj.quesid,
            paperid:this.config.paperid,
            courseid:this.config.courseid},
        error:function(){alert('异常错误，原因：未知!');},
        success:function(rps){
            //失败
            if(rps.type=="error"){
                alert(rps.msg);return;
            }
            var h='';
            if(rps.type=="success"){
                var quesObj=rps.objList[0];
                var parentQuesObj=null;
                //加载题干
                if(rps.objList.length>1){ //如果是组试题，则 0:题干   1：题
                    quesObj=rps.objList[1];//
                    parentQuesObj=rps.objList[0];
                }
                var qtype=quesObj.questiontype;
                var sc=config.scoreArray[cQuesObj.idx];
                if(typeof(config.papertype)!="undefined"&&config.papertype==3)
                    sc=quesObj.score;
                var h='<div id="dv_q_'+quesObj.questionid+'">';
                h+='<input type="hidden" value="'+quesObj.questionid+'" id="hd_quesid_'+quesObj.questionid+'" name="hd_quesid"/>';
                h+='<input type="hidden" value="" name="hd_answer" id="hs_val_'+quesObj.questionid+'"/>';
                h+='<input type="hidden" value="0" name="hd_stu_score" id="hs_val_stu_'+quesObj.questionid+'"/>';
                h+='<input type="hidden" value="'+sc+'" name="hd_score" id="hs_score_'+quesObj.questionid+'"/>';
                h+='<input  type="hidden" value="'+quesObj.questiontype+'" name="hd_questiontype" id="hd_questiontype_'+quesObj.questionid+'"/>';
                h+='<input  type="hidden" value="2" name="hd_isright" id="hd_isright_'+quesObj.questionid+'"/>';

                h+=' <h1>';
                //h+='<span class="f_right">得分：0分</span>';
                if(quesObj.questiontype==3||quesObj.questiontype==7)
                    h+='单选题';
                else if(quesObj.questiontype==4||quesObj.questiontype==8)
                    h+='多选题';
                else if(quesObj.questiontype==1)
                    h+='问答题';
                else if(quesObj.questiontype==2)
                    h+='填空题';

                h+='（<span id="sp_score">'+sc+'</span>分）</h1>';
                if(parentQuesObj!=null){
                    var ex=parentQuesObj.extension;
                    h+='<div class="yuyin">'+parentQuesObj.content+'</div>';

                }
                if(typeof(quesObj.content)!="undefined")
                    h+=' <div class="title">'+quesObj.content+'</div>';
                if(quesObj.questiontype==3||quesObj.questiontype==4||quesObj.questiontype==7||quesObj.questiontype==8){
                    h+='<ul class="test" id="ul_answer'+quesObj.questionid+'">';
                    $.each(quesObj.questionOption,function(x,m){
                        h+='<li><span style="display:none">' ;
                        if(quesObj.questiontype==3||quesObj.questiontype==7){
                            h+='<input type="radio" name="rdo_answer'+quesObj.questionid+'" value="'+m.optiontype+'" id="rdo_answer'+m.questionid+m.optiontype+'"/>';
                            if(typeof(m.isright)!="undefined"&&m.isright==1){
                                h+='<input type="hidden" value="'+ m.optiontype+'"  name="hd_rightVal"  id="hd_rightVal'+quesObj.questionid+'"/>';
                            }
                        }else
                            h+='<input type="checkbox" name="rdo_answer'+quesObj.questionid+'"  value="'+m.optiontype+'|'+ m.isright+'" id="rdo_answer'+m.questionid+m.optiontype+'"/>';
                        h+='</span><label for="rdo_answer'+m.questionid+m.optiontype+'"><a href="javascript:;"><span class="blue">'+m.optiontype+'.</span>'+m.content+'</a></label></li>';
                    });
                    h+='</ul>';
                }
                h+='</div>';

                if($("#dv_q_"+quesObj.questionid).length>0)
                    $("#dv_q_"+quesObj.questionid).show();
                else{
                    $("#dv_question").append(h);

                    //空格出来
                    if(quesObj.questiontype==2){
                        $("span[name='fillbank']").each(function(idx,itm){
                            $(this).replaceWith('<input type="text" name="txt_tk" id="txt_tk_'+quesObj.questionid+'"/>');
                        });
                    }else if(quesObj.questiontype==3||quesObj.questiontype==4||quesObj.questiontype==7||quesObj.questiontype==8){
                        //如果是选择题则绑定选项事件
                        $("#ul_answer"+quesObj.questionid+" li").bind("click",function(){
                            var selObj=$(this).children().children("input[name*='rdo_answer']");
                            var selType=selObj.attr("type");
                            zm=this;
                            if(selType=='checkbox'){
                                if($(zm).attr("class").indexOf("crumb")>-1){
                                    $(zm).removeClass("crumb");
                                    selObj.attr("checked",false);
                                }else{
                                    $(zm).addClass("crumb");
                                    selObj.attr("checked",true);
                                }
                            }else{
                                $(zm).parent().children("li").each(function(z,zd){
                                    $(zd).removeClass("crumb");
                                });
                                $(zm).addClass("crumb");
                                selObj.attr("checked",true);
                            }

                        });
                    }
                    //如果学生已经做题，则自动填充
                    if(typeof(quesObj.spqLogs)!="undefined"){
                        var qtype=quesObj.questiontype;
                        if(qtype==1||qtype==2){
                            if(qtype==1){
                                //问答
                                $("#txt_answer_"+quesObj.questionid).val(quesObj.spqLogs.answer);
                            }else{
                                var anArray=quesObj.spqLogs.answer.split("|");
                                $("#dv_q_"+quesObj.questionid+" input[name='txt_tk']").each(function(i,m){
                                    if(anArray.length>=i+1)
                                        $(m).val(anArray[i])
                                    else
                                        return;
                                });
                            }
                            //附件
                            if(typeof(quesObj.spqLogs.annexName)!="undefined")
                                $("#txt_f2"+quesObj.questionid).val(quesObj.spqLogs.annexName);
                        }else if(qtype==3||qtype==4||qtype==7||qtype==8){
                            if(qtype==3||qtype==7){
                                $("#dv_q_"+quesObj.questionid+" input[value*='"+quesObj.spqLogs.answer+"']").attr("checked",true);
                                $("#dv_q_"+quesObj.questionid+" input[value*='"+quesObj.spqLogs.answer+"']").parent().parent().addClass("crumb");
                            }else{
                                var anArray=quesObj.spqLogs.answer.split("|");
                                $.each(anArray,function(i,m){
                                    $("#dv_q_"+quesObj.questionid+" input[value*='"+m+"']").attr("checked",true);
                                    $("#dv_q_"+quesObj.questionid+" input[value*='"+m+"']").parent().parent().addClass("crumb");
                                });
                            }
                        }
                    }


                }

            }
        }
    };
    //发送请求
    $.ajax(ajxObj);
};

