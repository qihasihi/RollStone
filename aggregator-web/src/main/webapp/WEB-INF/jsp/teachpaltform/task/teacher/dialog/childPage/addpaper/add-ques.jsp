<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
    <title>${sessionScope.CURRENT_TITLE}</title>
    <style>
    </style>
    <script type="text/javascript"
            src="js/teachpaltform/ques.js"></script>
    <script type="text/javascript">
        var courseid = "${param.courseid}";
        var operate_type="${param.operate_type}";
        var pQuestype='${param.questype}';
        var addflag='${param.flag}';
        var paperid="${param.paperid}";
        var editor;
        var questionid="${questionid}";
        $(function () {
            if(addflag.length>0)
                $("input[name='rdo_ques_type_dialog']").attr("disabled",true);
            if(operate_type=="3")
                $("#li_quote").remove();
            //试题类型
            var questype =pQuestype.length>0?parseInt(pQuestype):$("input[name='rdo_ques_type_dialog']:checked").val();
            changeDialogQuesType(questype);
            $("input[name='rdo_ques_type_dialog']").filter(function(){return this.value==questype}).attr("checked",true);

            editor = new UE.ui.Editor({
                //这里可以选择自己需要的工具按钮名称,此处仅选择如下五个
                    toolbars: [
                    ['Source', 'Undo', 'Redo', 'Bold', 'fillbank', 'fullscreen', 'removeformat','insertimage']
                ],
               // initialFrameWidth: "580px",
               // initialFrameHeight: "500px",
                autoHeightEnabled: false
            });
            textarea:'editor'; //与textarea的name值保持一致
            editor.render('editor');
            editor.setDataId(questionid);
            BindText();
        });


        /**
         * 添加试题
         * @return
         */
        function doAddDialogPaperQues(){
            var questype=$("input[name='rdo_ques_type_dialog']:checked").val();
            var url='',iserror=0,param={},paramStr='?t='+new Date().getTime(),rflag=false,rightnum=0;
            var optionArry=new Array(),isrightArray=new Array();
            //试题
            if(questionid.length<1){
                alert('异常错误,系统未获取到试题标识!');
                return;
            }
            if(paperid.length<1){
                alert('异常错误,系统未获取到试卷标识!');
                return;
            }
            param.questionid=questionid;
            param.paperid=paperid;

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
                url:'question?m=doSubAddPaperQuestion',
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
                        loadPaperQues();
//                        setTimeout(function(){
                            loadDiv(3);
//                        },100);
                    }
                }
            });
        }




        function loadQuesDialog(){
            var url = 'task?m=toTaskElementDetial&tasktype=3&courseid=' + courseid;
            var questype = $("input[name='rdo_ques_type']:checked").val();
            url += '&questype=' + questype;
            $("#dv_content").load(url);
        }
    </script>


</head>
<body>

<div class="public_float public_float960" id="dv_edit_ques">
    <p id="p_create_ques" class="float_title"><a href="javascript:;" class="ico93" onclick="loadDiv(3)" title="返回"></a><span id="dv_model_mdname">新建试题</span></p>
    <p class="float_text public_input w820">
        <c:if test="${!empty quesTypeList}">
            <c:forEach items="${quesTypeList}" var="t" varStatus="idx">
                <c:if test="${t.dictionaryvalue==3}">
                    <input checked onclick="changeDialogQuesType(${t.dictionaryvalue})"
                           id="rdo_type_${idx.index+1}" type="radio" value="${t.dictionaryvalue}"
                           name="rdo_ques_type_dialog"/>
                </c:if>

                <c:if test="${t.dictionaryvalue ne 3}">
                    <input onclick="changeDialogQuesType(${t.dictionaryvalue})" id="rdo_type_${idx.index+1}"
                           type="radio" value="${t.dictionaryvalue}" name="rdo_ques_type_dialog"/>
                </c:if>

                <label for="rdo_type_${idx.index+1}">${t.dictionaryname}</label>
            </c:forEach>
        </c:if>
    </p>
    <div class="jxxt_float_h560">
        <div class="jxxt_zhuanti_shiti_add">
            <table border="0" cellpadding="0" cellspacing="0" class="public_tab1 public_input black">
                <col class="w80"/>
                <col class="w700"/>
                <tr  id="tr_ques_name">
                    <td><strong style="cursor: pointer" onclick="$('#ques_name').click();">题&nbsp;&nbsp;&nbsp;&nbsp;干：</strong></td>
                    <td>
                        <a href="javascript:void(0)" onclick="ed_option_dialog(this)" id="ques_name_dialog" name="ques_name_dialog" class="font-black">点击编辑题干&hellip;&hellip;</a>
                    </td>
                </tr>

                <!--
                <tr>
                    <td><strong>选&nbsp;&nbsp;&nbsp;&nbsp;项：</strong></td>
                    <td><input type="radio" name="radio" id="radio2" value="radio">
                        A. <a href="1" class="font-black">点击编辑选项&hellip;&hellip;</a><br>
                        <input type="radio" name="radio" id="radio" value="radio">
                        B. <a href="1" class="font-black">点击编辑选项&hellip;&hellip;</a><br>
                        <input type="radio" name="radio" id="radio4" value="radio">
                        C. <a href="1" class="font-black">点击编辑选项&hellip;&hellip;</a><br>
                        <input type="radio" name="radio" id="radio5" value="radio">
                        D. <a href="1" class="font-black">点击编辑选项&hellip;&hellip;</a>
                        <p class="p_t_10"><a href="1" title="添加选项" class="ico36"></a><a href="1" title="移出选项" class="ico34"></a></p>
                    </td>
                </tr>  -->



                <tr id="type_option">
                    <td><strong>选&nbsp;&nbsp;&nbsp;&nbsp;项：</strong></td>
                    <td>
                        <table id="ques_tbl">

                        </table>
                    </td>
                </tr>

                <tr>
                    <th colspan="2" id="td_ques_operate">

                    </th>
                </tr>

                <tr id="tr_right_answer">
                    <td><strong>正确答案：</strong></td>
                    <td>
                        <a class="font-black" id="correct_answer_dialog" name="correct_answer_dialog" href="javascript:void(0)" onclick="ed_option_dialog(this)">请编辑正确答案&hellip;&hellip;</a>
                        <span id="fill_answer" name="fill_answer" style="display: none"></span>
                        <!--请点击图标设置正确答案-->
                    </td>
                </tr>

                <!--<tr>
                    <td><strong>正确答案：</strong></td>
                    <td>编辑选项时勾选正确答案&nbsp;&nbsp;&nbsp;&nbsp;<a href="1" class="font-black">请点击图标设置正确答案</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="1" class="font-black">点击编辑正确答案&hellip;&hellip;</a></td>
                </tr>

                <tr>
                    <td><strong>答案解晰：</strong></td>
                    <td><a href="1" class="font-black">点击编辑答案解晰&hellip;&hellip;</a></td>
                </tr>
-->
                <tr>
                    <td><strong>答案解析：</strong></td>
                    <td>
                        <a class="font-black" id="ques_answer_dialog" name="ques_answer_dialog" href="javascript:void(0)" onclick="ed_option_dialog(this)">点击编辑答案解析&hellip;&hellip;</a>
                    </td>
                    <div id="dv_filter" style="display: none;"></div>
                </tr>


                <!--<tr>
                    <td colspan="2"></td>
                </tr>
                <tr class="border">
                    <td><strong>正在编辑：</strong></td>
                    <td>
                        <span id="sp_edit"></span>
                        <span id="sp_right"></span>
                        <div class="p_tb_10">
                            <textarea id="editor" name="editor" style="width:600px;height:120px;"></textarea>
                        </div>
                        <p class="t_c"><a href="javascript:void(0);" onclick="sub_option()" class="an_public3">保&nbsp;存</a></p>
                        <input type="hidden" id="hd_id"/>
                    </td>
                </tr> -->
            </table>
        </div>
        <p class="t_c p_t_20"><a  href="javascript:doAddDialogPaperQues();" id="btn_addQues"  class="an_public1">提&nbsp;交</a></p>
    </div>
</div>



<div class="public_float public_float960" id="dv_edit_option" style="display: none;">
    <p class="float_title" id="p_edit_ques"><a href="javascript:;" onclick="$('#dv_edit_ques').show();$('#dv_edit_option').hide();" class="ico93" title="返回"></a>编辑题干</p>
    <table border="0" cellpadding="0" cellspacing="0" class="public_tab1 public_input black w820">
       <!-- <tr>
            <td><span class="f_right font-red">点击图标插入填空占位符</span><input type="checkbox" name="checkbox" id="checkbox"> <span class="font-blue">设为正确答案 </span></td>
        </tr> -->
        <tr>
            <td>
                <span id="sp_edit"></span>
                <span id="sp_right"></span>
                <div class="p_tb_10">
                    <textarea id="editor" name="editor" style="width:600px;height:120px;"></textarea>
                </div>
                <input type="hidden" id="hd_id"/>
            </td>
        </tr>
        <tr>
            <td class="t_c"><a href="javascript:void(0);" onclick="sub_option_dialog()" class="an_public1">保&nbsp;存</a></td>
        </tr>
    </table>
</div>


</body>
</html>
