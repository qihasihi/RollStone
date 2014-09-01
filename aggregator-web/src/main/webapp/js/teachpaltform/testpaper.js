/**
 * Created by zhengzhou on 14-7-16.
 */
function loadQuesNumberTool(qsize){
    var h='';
    for(i=1;i<=qsize;i++){
        h+=' <li><a href="javascript:nextNum('+(i-1)+');">'+i+'</a></li>'
    }
    $("#dv_ques_number").html(h);
    //试题组，样式
    if(typeof(allquesidObj)=="undefined"||allquesidObj.length<1){
        alert('异常错误，参数异常!');return;
    }
    var obj=[],startIdx=-1,isbegin=false,freeId=null;
    var allarray=allquesidObj.split(",");
    if(allarray.length>0){
        for(z=0;z<allarray.length;z++){
            if(allarray[z].indexOf("|")!=-1){
                var tmpArray=allarray[z].split("|");
                if((freeId!=tmpArray[0]||z==allarray.length-1)&&isbegin){
                    isbegin=false;
                    freeid=null;
                    obj[obj.length]={beginIdx:startIdx,endIdx:z-1};
                    if(z==allarray.length-1)
                     obj[obj.length-1].endIdx=z;
                    startIdx=-1;
                }
                 if(!isbegin){
                    isbegin=true;
                    startIdx=z;
                    freeId=tmpArray[0];
                }
            }else if(isbegin){
                isbegin=false;
                freeId=null;
                obj[obj.length]={beginIdx:startIdx,endIdx:z-1};
                if(z==allarray.length-1)
                    obj[obj.length-1].endIdx=z-1;
                startIdx=-1;
            }
        }
    }
    if(obj.length>0){
        $.each(obj,function(ix,im){
            for(n=im.beginIdx;n<=im.endIdx;n++){
                var clasName='';
                if(n==im.beginIdx)
                    clasName='bgR';
                if(n==im.endIdx)
                     clasName='bgL';
                if(n!=im.beginIdx&&n!=im.endIdx)
                    clasName='bgC';
                $("#dv_ques_number>li").eq(n).addClass(clasName);
            }
        })
    }
}
var currentQuesId=undefined;

//下一题
function nextNum(i){
    var vi=(typeof(i)=="undefined"||i==null)?0:i;
    if(typeof(allquesidObj)=="undefined"||allquesidObj.length<1){
        alert('异常错误，参数异常!');return;
    }
    var type=1,quesid=undefined;
    vi--;
    if(vi<0){
        type=undefined;
    }
    if(type==undefined){
        next(type,undefined,paperid,allquesidObj,undefined,0);
    }else  if(allquesidObj.indexOf(",")!=-1){
        var arrayObj=allquesidObj.split(",");
        if(arrayObj.length>0){
             var cuQuesid=arrayObj[vi];
            if(cuQuesid.indexOf("|")!=-1)
            cuQuesid=cuQuesid.split("|")[1];
            next(type,cuQuesid,paperid,allquesidObj,undefined,0);
        }
    }
}

/**
 * 更新
 */
function updateNumState(idx){
    //allquesidObj;
   // subQuesId;
    if(subQuesId.length>0&&allquesidObj!=null&&allquesidObj.length>0){
        var allQuesArray=allquesidObj.split(",");
        var subQuesIdArray=subQuesId.split(",");

        if(subQuesIdArray.length>0&&allQuesArray.length>0){



            $("#dv_ques_number>li").removeClass("blue");
            $("#dv_ques_number>li").removeClass("gray_big");
            $("#dv_ques_number>li").removeClass("blue_big");
            //是否显示上一题，下一题

            for(z=0;z<subQuesIdArray.length;z++){
                //颜色改变
                for(b=0;b<allQuesArray.length;b++){
                    if(allQuesArray[b].indexOf("|")!=-1){
                        if(allQuesArray[b].split("|")[1]==subQuesIdArray[z]){
                            $("#dv_ques_number>li").eq(b).addClass("blue");
                            continue;
                        }
                    }
                    if(allQuesArray[b]==subQuesIdArray[z]){
                         $("#dv_ques_number>li").eq(b).addClass("blue");
                        continue;
                    }
                }
            }
        }
    }
    var clsName=$("#dv_ques_number>li").eq(idx).attr("className");
    if(typeof(clsName)!="undefined"){
        if(clsName.indexOf("blue")!=-1)
            $("#dv_ques_number>li").eq(idx).addClass("blue_big");
        else
            $("#dv_ques_number>li").eq(idx).addClass("gray_big");
    }

}

/**
 * 下(上)一题
 * @param type   1:下一题  0 OR NULL:上一题
 * @param quesid  当前的Quesid
 * @param allquesid 所有的QuesId
 * @param isright 是否正确
 * @param issub 是否提交前一位数据
 */
function next(type,quesid,paperid,allquesid,isright,issub){
    if(typeof(allquesid)=="undefined"||allquesid==null){
        alert('错误，没有题!参数异常');return;
    }
    if(typeof(type)=="undefined"||type==null)
        type=0;
    var nextIdx=-1;
    //下一题

        var arrayObj=allquesid.split(",");
        if(arrayObj.length>0){
            if(typeof(quesid)!="undefined"&&quesid!=null){
                for(var i=0;i<arrayObj.length;i++){
                    var a=arrayObj[i];
                    if(a.indexOf('|')!=-1){
                        if(a.split("|")[1]==quesid){
                            if(type==0)
                                nextIdx=i-1;
                            else
                                nextIdx=i+1;
                        }
                    }else{
                        if(a==quesid){
                            if(type==0)
                                nextIdx=i-1;
                            else
                                nextIdx=i+1;
                        }
                    }
                }
            }else
                 nextIdx=0;
            if(nextIdx==-1){
                return;
            }
            //存入数据库
            if(typeof(currentQuesId)!="undefined"&&currentQuesId!=null&&issub!=1)
                freeSubOnePaper(currentQuesId,isright,nextIdx);

            updateNumState(nextIdx);    //更新状态

            //nextIdx
            if(nextIdx!=arrayObj.length){
                //隐藏相关数据
                $("div[id*='dv_qs_']").hide();
                $("div[id*='dv_pqs_']").hide();
              var nextQuesId=arrayObj[nextIdx];
                if(nextQuesId.indexOf("|")!=-1){
                    var nextQuesIdArr=nextQuesId.split("|");
                    $("#dv_pqs_"+nextQuesIdArr[0]).show();
                    nextQuesId=nextQuesIdArr[1];
                }

               if($("div[id='dv_qs_"+nextQuesId+"']").length<1){
                    loadNextQues(arrayObj[nextIdx],paperid,nextIdx);
               }else{
                   $("div[id='dv_qs_"+nextQuesId+"']").show();
                   $("#p_operate>span").hide();
                   $("#p_operate>#sp_operate_"+nextQuesId).show();
                   currentQuesId=nextQuesId;

                   showQuesOperator(nextIdx,nextQuesId);
               }




            }
    }
}
/**
 * 加载下一题
 * @param quesid
 * @param paperid
 */
function loadNextQues(quesid,paperid,idx){
    if(typeof(quesid)=="undefined"||quesid==null||typeof(paperid)=="undefined"||paperid==null){
        alert('参数异常!');return;
    }
    //paperques?m=nextTestQues
    var ajxObj={
        url:'paperques?m=nextTestQues',
        dataType:'json',
        type:'post',
        data:{quesid:quesid,paperid:paperid,courseid:courseid},
        error:function(){alert('异常错误，原因：未知!');},
        success:function(rps){
            //失败
            if(rps.type=="error"){
                 alert(rps.msg);return;
            }
            var h='';
            //成功：
            if(rps.type=="success"){
                var quesObj=rps.objList[0];
                var parentQuesObj=null;
                //加载题干
                if(rps.objList.length>1){ //如果是组试题，则 0:题干   1：题
                    quesObj=rps.objList[1];//
                    parentQuesObj=rps.objList[0];
                    h+='<div class="jxxt_zhuanti_rw_ceshi_shiti font-black public_input" id="dv_pqs_'+parentQuesObj.questionid+'" style="width:850px;">';
                    var ex=parentQuesObj.extension;
                    h+='<input type="hidden" id="hd_p_extension'+parentQuesObj.questionid+'" id="hd_p_extension" value="'+ex+'"/>';
                    if(ex!=4&&ex!=5&&ex!=3&&ex!=2){
                        h+='<div class="p_b_20"><strong>试题组（<span id="sp_num'+parentQuesObj.questionid+'"></span>）</strong><br>';
                        h+=parentQuesObj.content;
                        h+='</div>';
                    }else if(ex==5){    //七选五
                        //显示题干
                        h+='<div class="p_b_20"><strong>七选五（<span id="sp_num'+parentQuesObj.questionid+'"></span>）</strong><br>';
                        h+=parentQuesObj.content;
                        //题选项
                        h+='<div style="display:none" id="p_option_'+parentQuesObj.questionid+'">';
                        //选项
                         if(typeof(parentQuesObj.questionOption)!="undefined"&&parentQuesObj.questionOption.length>0){
                             $.each(parentQuesObj.questionOption,function(x,m){
                                h+='<div id="p_optchild'+ m.optiontype+'">'+m.optiontype+'.'+m.content
                                    +'<input type="hidden" name="opt_quesid" value="'+ m.questionid+'"/>' +
                                    '<input type="hidden" name="opt_optiontype" value="'+ m.optiontype+'"/></div>';
                             });
                         }
                        h+='</div></div>';
                    }else if(ex==2){
                        h+='<div class="p_b_20"><strong>阅读理解（<span id="sp_num'+parentQuesObj.questionid+'"></span>）</strong><br>';
                        h+=parentQuesObj.content;
                        //题选项
                        h+='<div style="display:none" id="p_option_'+parentQuesObj.questionid+'">';
                        //选项
                        h+='</div></div>';
                    }else if(ex==3){
                        h+='<div class="p_b_20"><strong>完形填空（<span id="sp_num'+parentQuesObj.questionid+'"></span>）</strong><br>';
                        h+=parentQuesObj.content;
                        //题选项
                        h+='<div style="display:none" id="p_option_'+parentQuesObj.questionid+'">';
                        //选项
                        h+='</div></div>';
                    }else{

                        h+='<p><strong>英语听力（<span id="sp_num'+parentQuesObj.questionid+'"></span>）</strong><span id="p_mp3_'+parentQuesObj.questionid+'"></span></p>';
                    }
//                    h+='<table border="0" cellpadding="0" cellspacing="0" class="public_tab1">';
//                    h+='<col class="w30"/><col class="w860"/>';
//                    h+='<tr><th><input type="radio" name="radio" id="radio9" value="radio"></th>';
//                    h+=' <td>A. 民族性我国公安交通部门规定，从1993年7月1日起，在各种小轿车前排乘坐的人（包括司机）必须系好安全带。主要是防止汽车突然停止的时候，乘客会向前倾倒而发生事故。则汽车刹车时，乘客向前倾倒的原因是由于</td>';
//                    h+='</tr>';
//                    h+='</table>';
                    h+='<div id="dv_child_'+parentQuesObj.questionid+'"></div>';
                    h+='</div>';
//                    h+='<p class="jxxt_zhuanti_rw_ceshi_an"><a href="1" class="an_test1">上一题</a><a href="1" class="an_test1">下一题</a><a href="1" class="an_test2">交卷</a></p>';
//                    h+='</div>';
                    //加入当中
                    if($("div[id='dv_question'] #dv_pqs_"+parentQuesObj.questionid).length<1){
                        $("#dv_question").append(h);
                        if(ex==4){
                            var mp3url=_QUES_IMG_URL+"/"+parentQuesObj.questionid+"/001.mp3";
//                            loadSWFPlayer(mp3url,
//                                ,undefined
//                                ,undefined,45,45,true,undefined);
                            playSound('play',mp3url,1,1,'p_mp3_'+parentQuesObj.questionid);
                            $("#p_mp3_"+parentQuesObj.questionid+" object").hide();

                            $("#p_mp3_"+parentQuesObj.questionid).append(
                                '<div class="p_t_10"><a href="javascript:;" ' +
                                'onclick="$(\'#p_mp3_'+parentQuesObj.questionid+' object\').show()">'+
                                    '<img src="images/pic05_140722.png" width="39" height="27"></a></div>')
                        }
                        //检测组数量
                        var allArray=allquesidObj.split(",");
                        var isbegin=false,beignIdx=-1,endIdx=-1;
                        if(allArray.length>0){
                            for(i=0;i<allArray.length;i++){
                                if(allArray[i].indexOf("|")!=-1){
                                    var tmpArray=allArray[i].split("|");
                                    if(tmpArray[0]==parentQuesObj.questionid){
                                        if(!isbegin){
                                            isbegin=true;beginIdx=i+1;
                                        }
                                        endIdx=i+1;
                                    }
                                }
                            }
                        }
                        $("#sp_num"+parentQuesObj.questionid).html(beginIdx+"-"+endIdx);
                    }else
                        $("div[id='dv_question'] #dv_pqs_"+parentQuesObj.questionid).show();

                }

                var sc=scoreArray[idx];
                if(typeof(papertype)!="undefined"&&papertype==3)
                    sc=quesObj.score;

                //要答的题
                h='<div class="jxxt_zhuanti_rw_ceshi_shiti font-black public_input" style="width:850px" id="dv_qs_'+quesObj.questionid+'">';
                h+='<input type="hidden" value="'+quesObj.questionid+'" id="hd_quesid_'+quesObj.questionid+'" name="hd_quesid"/>';
                h+='<input type="hidden" value="" name="hd_answer" id="hs_val_'+quesObj.questionid+'"/>';
                h+='<input type="hidden" value="0" name="hd_stu_score" id="hs_val_stu_'+quesObj.questionid+'"/>';
                h+='<input type="hidden" value="'+sc+'" name="hd_score" id="hs_score_'+quesObj.questionid+'"/>';
                h+='<input  type="hidden" value="'+quesObj.questiontype+'" name="hd_questiontype" id="hd_questiontype_'+quesObj.questionid+'"/>';
                h+='<input  type="hidden" value="2" name="hd_isright" id="hd_isright_'+quesObj.questionid+'"/>';

                if(typeof(quesObj.content)!="undefined")
                    h+='<p><span id="p_num_'+quesObj.questionid+'"></span>.'+quesObj.content+'</p>';
                if(quesObj.questiontype==1||quesObj.questiontype==9){//问答题
                    h+='<div class="p_t_20"><textarea name="txt_answer" id="txt_answer_'+quesObj.questionid+'"  placeholder="输入你的答案"></textarea></div>';
                }
                if(quesObj.questiontype==1||quesObj.questiontype==2){
                    h+=' <div class="files"><strong>上传附件：</strong><input   id="txt_f2'+quesObj.questionid+'" readonly type="text" />';
                    h+=' <a href="javascript:;"   onclick="document.getElementById(\'txt_f_'+quesObj.questionid+'\').click();"  class="an_public3">选择</a></div>';
                    h+=' <input type="file" onchange="document.getElementById(\'txt_f2'+quesObj.questionid+'\').value=this.value.substring(this.value.lastIndexOf(\'\\\\\')+1);"' +
                         ' name="txt_f_'+quesObj.questionid+'2" style="display:none" id="txt_f_'+quesObj.questionid+'"/></p>';
                }
                //选项(如果是七选五。则直接提取选项进行输出)
                if(parentQuesObj!=null&&$("div[id='p_option_"+parentQuesObj.questionid+"']").length>0&&parentQuesObj.extension==5){
                    h+='<p><span id="p_num_'+quesObj.questionid+'"></span>.</p><table border="0" cellpadding="0" cellspacing="0" class="public_tab1">';
                    h+='<col class="w30"/><col class="w860"/>';
                    var optionPObj=$("#p_option_"+parentQuesObj.questionid+">div");
                    $.each(optionPObj,function(x,m){
                        var opttype=$("#"+ m.id+" input[name='opt_optiontype']").val();
                        var optid=$("#"+ m.id+" input[name='opt_quesid']").val();

                        var ishas=false;
                        if(typeof(quesObj.questionOption)!="undefined"&&quesObj.questionOption.length>0){
                            $.each(quesObj.questionOption,function(ix,im){
                                if(im.isright==1&&im.optiontype==opttype){
                                    ishas=true;return;
                                }
                            });
                        }
                        h+='<tr><th>' ;
                        if(quesObj.questiontype==3||quesObj.questiontype==7){
                            h+='<input type="radio" id="rdo_answer'+quesObj.questionid+opttype+'" onclick="next(1,'+quesObj.questionid+','+paperid+',\''+allquesidObj+'\','+ (ishas?1:0)+')" value="'+ opttype+'" name="rdo_answer'+quesObj.questionid+'">';
                            if(ishas)
                                h+='<input type="hidden" value="'+opttype+'" name="hd_rightVal" id="hd_rightVal'+quesObj.questionid+'"/>';
                        }
                        if(quesObj.questiontype==4||quesObj.questiontype==8)
                            h+='<input type="checkbox" value="'+opttype+'|'+(ishas?1:0)+'" id="rdo_answer'+quesObj.questionid+opttype+'" name="rdo_answer'+quesObj.questionid+'">';
                        h+='</th>';
                        h+=' <td><label for="rdo_answer'+quesObj.questionid+opttype+'">'+$(m).html().Trim()+'</lable></td>';
                        h+='</tr>';
                    });
                    h+='</table>';
                }else  if(quesObj.questiontype==3||quesObj.questiontype==7||quesObj.questiontype==8||quesObj.questiontype==4){
                    if(typeof(quesObj.questionOption)!="undefined"&&quesObj.questionOption.length>0){
                        h+='<table border="0" cellpadding="0" cellspacing="0" class="public_tab1">';
                        h+='<col class="w30"/><col class="w860"/>';
                        $.each(quesObj.questionOption,function(x,m){
                            h+='<tr><th>' ;
                            if(quesObj.questiontype==3||quesObj.questiontype==7){
                                h+='<input type="radio" id="rdo_answer'+m.questionid+m.optiontype+'" onclick="next(1,'+ m.questionid+','+paperid+',\''+allquesidObj+'\','+ m.isright+')" value="'+ m.optiontype+'" name="rdo_answer'+ m.questionid+'">';
                                if(m.isright==1)
                                    h+='<input type="hidden" value="'+ m.optiontype+'" name="hd_rightVal" id="hd_rightVal'+quesObj.questionid+'"/>';
                            }if(quesObj.questiontype==4||quesObj.questiontype==8)
                                h+='<input type="checkbox" value="'+m.optiontype+'|'+m.isright+'" id="rdo_answer'+m.questionid+m.optiontype+'" name="rdo_answer'+m.questionid+'">';
                             h+='</th>';
                            h+=' <td><label for="rdo_answer'+m.questionid+m.optiontype+'">'+m.optiontype+'.'+m.content+'</label></td>';
                            h+='</tr>';
                        });
                        h+='</table>';
                    }
                }

                if($("#dv_qs_"+quesObj.questionid).length<1){
                     if(parentQuesObj!=null&&$("div[id='dv_qs_"+parentQuesObj.questionid+"']").length>0){
                         $("#dv_child_"+parentQuesObj.questionid).append(h);
                      }else
                            $("#dv_question").append(h);


  //                  var idxObj=$("#dv_question div[id*='dv_qs_']");
//$("#p_num_"+quesObj.questionid).html(idxObj.length);
                    var allquesArray=allquesidObj.split(",");
                    if(allquesArray.length>0){
                        $.each(allquesArray,function(z,m){
                            if(m.indexOf('|')!=-1){
                                if(m.split('|')[1]==quesObj.questionid){
                                    $("#p_num_"+quesObj.questionid).html(z+1);
                                }
                            }else if(m==quesObj.questionid){
                                $("#p_num_"+quesObj.questionid).html(z+1);
                            }
                        })
                    }


                }else
                    $("#dv_qs_"+quesObj.questionid).show();


                var h3='<span id="sp_operate_'+quesObj.questionid+'"><a href="javascript:;" style="display:none" id="a_free_ques_'+quesObj.questionid+'" onclick="next(0,'+quesObj.questionid+','+paperid+',\''+allquesidObj+'\')" class="an_test1">上一题</a>' +
                    '<a  href="javascript:;" style="display:none"  id="a_next_ques_'+quesObj.questionid+'" onclick="next(1,'+quesObj.questionid+','+paperid+',\''+allquesidObj+'\')" class="an_test1">下一题</a>' +
                    '<a href="javascript:;" onclick="subPaper()" class="an_test2">交卷</a>';
                h3+='</span>';
                $("#p_operate>span").hide();
                if($("#sp_operate_"+quesObj.questionid).length>0){
                    $("#p_operate>#sp_operate_"+quesObj.questionid).show();
                }else{$("#p_operate").append(h3);
                }
                //空格出来
                $("span[name='fillbank']").each(function(idx,itm){
                    $(this).replaceWith('<input type="text" name="txt_tk" id="txt_tk_'+$(this).parent().parent().children("input[name='hd_quesid']").val()+'"/>');
                });

                //七选五   完型填空，则修改题号
                if(parentQuesObj!=null&&$("div[id='p_option_"+parentQuesObj.questionid+"']").length>0&&(parentQuesObj.extension==5||parentQuesObj.extension==3)){
                    var allQuesArray=allquesidObj.split(",");
                    var qNoidx=0;
                    var stzhasQues=false;
                    if(allQuesArray.length>0){
                        $.each(allQuesArray,function(z,mz){
                            if(mz.indexOf("|")!=-1){
                                var mzArray=mz.split("|");
                                if(mzArray[0]==parentQuesObj.questionid&&!stzhasQues){
                                    qNoidx++;
                                }
                                if(mzArray[1]==quesObj.questionid){
                                    stzhasQues=!stzhasQues;
                                    return;
                                }
                            }
                        });
                    }
                    if(stzhasQues){
                        $("#p_num_"+quesObj.questionid).html(qNoidx);
                    }
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
                            $("#dv_qs_"+quesObj.questionid+" input[name='txt_tk']").each(function(i,m){
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
                            if(qtype==3||qtype==7)
                                $("#dv_qs_"+quesObj.questionid+" input[value*='"+quesObj.spqLogs.answer+"']").attr("checked",true);
                            else{
                                var anArray=quesObj.spqLogs.answer.split("|");
                                $.each(anArray,function(i,m){
                                    $("#dv_qs_"+quesObj.questionid+" input[value*='"+m+"']").attr("checked",true);
                                });
                            }
                    }
                }

                showQuesOperator(idx,quesObj.questionid);

                currentQuesId=quesObj.questionid;
            }
        }
    };
    //发送请求
    $.ajax(ajxObj);
}
/**
 * 显示 上一题，下一题控制器
 * @param idx
 * @param quesid
 */
function showQuesOperator(idx,quesid){
    var allQuesArray=allquesidObj.split(",");
    var sFirst=false,sLast=false;
    if(((idx==0&&idx!=allQuesArray.length-1)||typeof(idx)=="undefined")||idx!=allQuesArray.length-1)
        sLast=true;
    if(typeof(idx)!="undefined"&&idx!=0)
        sFirst=true;

    //显示上一题，下一题
    if(sFirst&&sLast){
        $("#a_free_ques_"+quesid).show();
        $("#a_next_ques_"+quesid).show();
    }else{
        if(sFirst)
        if(sFirst&&!sLast)
            $("#a_free_ques_"+quesid).show();
        if(sLast&!sFirst)
            $("#a_next_ques_"+quesid).show();
    }

}

/**
 *提交测试
 */
function subPaper(){
    //提交
    var pid=$("#hd_paper_id").val();
    if(pid.length<1){
        alert('异常错误，参数异常!');return;
    }
    var confirmMsg='是否确定交卷？ 提示：提交之后，你将无法再次作答。 ';

    var alaqLastQues="";
    var allquesArray=allquesidObj.split(",");
    var curQuesType=$("#hd_questiontype_"+currentQuesId).val();
    if(curQuesType==1||curQuesType==2){
        var aqLastQuesArr=allquesArray[allquesArray.length-1];
        alaqLastQues=aqLastQuesArr;
        if(aqLastQuesArr.indexOf('|')!=-1){
            alaqLastQues=aqLastQuesArr.split("|")[1];
        }
        if(currentQuesId==alaqLastQues&&subQuesId.indexOf(","+currentQuesId+",")==-1){
            subQuesId+=currentQuesId+",";
            freeSubOnePaper(currentQuesId,0,currentQuesId);
        }
    }


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

function freeSubOnePaper(quesid,isright,nextid){
    var questype=$("#dv_question div input[id='hd_questiontype_"+quesid+"']").val();
    var scoreObj=$("#dv_question div input[id='hs_score_"+quesid+"']");
    var score=scoreObj.val();
    var answer="";

    if(typeof(isright)=="undefined")
      isright=0;

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
    if(issubmit)
        subOnePaper(quesid,nextid);
}


var isTishiAnnex=false;

/**
 *提交测试
 */
function subOnePaper(quesid,nextid){
    if(typeof(taskid)=="undefined"||taskid==null||taskid.Trim().length<1){
        alert('错误，参数异常!');
        return;
    }
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
//            if(noanswer.length>0){
//                var msg="您还有 "+noanswer.length+"道题没有做!是"
//                for(var i=0;i<noanswer.length;i++){
//                    msg+="第"+noanswer[i]+"题 "
//                }
//                msg+="是否继续提交?";
//                if(!confirm(msg)){
//                    next(noanswer[0]);
//                }
//            }
//    var isanswer=false;
//    $.each(data,function(idx,itm){
//        if(itm!=null&&itm.length>0){
//            isanswer=true;
//            return;
//        }
//    });
//    if(!isanswer)return;

    //提交
    var pid=$("#hd_paper_id").val();
    if(pid.length<1){
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
            url: url + "&paperid="+pid+"&hasannex=1&testQuesData="+ $.toJSON(data),
            fileElementId: 'txt_f_'+quesid,
            dataType: 'json',
            secureuri: false,
            type: 'POST',
            success: function (rps, status) {
                if(rps.type=="success"){
                    if(subQuesId.indexOf(","+quesid+",")<0)
                        subQuesId+=quesid+",";
                    updateNumState(nextid);
                   // next(1,quesid,paperid,allquesidObj);
                    //nextIdx
                    var arrayObj=allquesidObj.split(",");
                    if(nextid==arrayObj.length){
                        if(confirm("最后一题了,是否提交试卷?")){
                            subPaper();
                        }
                    }
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
            data:{taskid:taskid,paperid:pid,testQuesData:$.toJSON(data)},
            cache: false,
            error:function(){
                alert('异常错误!系统未响应!');
            },success:function(rps){
                if(rps.type=="success"){
                    if(subQuesId.indexOf(","+quesid+",")<0)
                        subQuesId+=quesid+","
                    updateNumState(nextid);
                  //  next(1,quesid,paperid,allquesidObj);

                    //nextIdx
                    var arrayObj=allquesidObj.split(",");
                    if(nextid==arrayObj.length){
                        if(confirm("最后一题了,是否提交试卷?")){
                            subPaper();
                        }
                    }
                }else
                    alert(rps.msg);
            }
        })
    }
}

/**
 * 计算任务结束时间
 * @param bt  开始时间
 * @param et  结束时间
 * @param showId  显示的位置
 */
function howLong(bt,et,showId){
    var tmp=0;
    if(new Date().getTime()>bt)
        tmp=parseInt(et)-new Date().getTime();
    else
        tmp=parseInt(et)-parseInt(bt);
    //计算出相差天数
    var days=Math.floor(tmp/(24*3600*1000))
    //计算出小时数
    var leave1=tmp%(24*3600*1000)    //计算天数后剩余的毫秒数
    var hours=Math.floor(leave1/(3600*1000))
    //计算相差分钟数
    var leave2=leave1%(3600*1000)        //计算小时数后剩余的毫秒数
    var minutes=Math.floor(leave2/(60*1000))
    //计算相差秒数
    var leave3=leave2%(60*1000)      //计算分钟数后剩余的毫秒数
    var seconds=Math.round(leave3/1000)

    $("span[id='"+showId+"']").html(days+"天"+hours+"时"+minutes+"分"+seconds+"秒");
    //动态
    setTimeout("howLong('"+bt+"','"+et+"','"+showId+"')",1000);
}