/**
 * 添加试卷
 */
function addPaper(){
    var papername=$("#papername");
    if(papername.val().Trim().length<1){
        alert('请输入试卷名称!');
        papername.focus();
        return;
    }
    $.ajax({
        url:"paper?doSubAddPaper",
        type:"post",
        data:{courseid:courseid,papername:papername.val()},
        dataType:'json',
        cache: false,
        error:function(){
            alert('系统未响应，请稍候重试!');
        },success:function(rmsg){
            if(rmsg.type=="error"){
                alert(rmsg.msg);
            }else{
               // alert(rmsg.msg);
                // alert(rmsg.msg);
                loadEditPaper(courseid,rmsg.objList[0]);
            }
        }
    });

}
/**
 * 加载试卷的编辑页面
 * @param cid
 * @param pid
 */
function loadEditPaper(cid,pid){
    if(typeof(cid)=="undefined"||typeof(pid)=="undefined"||cid==null||pid==null){
        alert('异常错误，请刷新重试!');
        return;
    }
    var u="paper?m=editPaperQuestionModel&courseid="+cid+"&paperid="+pid;
    $("#dv_content #dv_content_child").load(u,function(){
        $("#dv_content .float_title").html("编辑试卷");
        //关闭相关层
        closeModel('dv_paper_name');
        $("#dv_content>div").hide();
        $("#dv_content").show();
        $("#dv_content #dv_content_child").fadeIn("fast");
        $("#a_click").click();
    });
}


    function importQuesList(){
        var url="question?m="
        var param = "dialogWidth:500px;dialogHeight:700px;status:no;location:no";
        var returnValue=window.showModalDialog(url,param);
        if(returnValue==null||returnValue.toString().length<1){
            alert("操作取消!");
            return;
        }
        $.ajax({
            url:"question?m=questionAjaxList",
            type:"post",
            data:{questype:questype,courseid:courseid,questionid:returnValue},
            dataType:'json',
            cache: false,
            error:function(){
                alert('系统未响应，请稍候重试!');
            },success:function(rmsg){
                if(rmsg.type=="error"){
                    alert(rmsg.msg);
                }else{
                    var htm='';
                    $("#ques_name").html('');
                    $("#ques_answer").html('');
                    $("#td_option").hide();
                    $("#td_option").html('');
                    if(rmsg.objList.length&&typeof returnValue!='undeinfed'){
                        $("#hd_questionid").val(rmsg.objList[0].questionid);
                        $("#sp_ques_id").html(rmsg.objList[0].questionid);
                        load_ques_detial(returnValue,questype);
                    }
                    $("#tr_ques_obj").show();
                }
            }
        });
    }
