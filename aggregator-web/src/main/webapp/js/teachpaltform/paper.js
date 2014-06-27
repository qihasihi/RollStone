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
                alert(rmsg.msg);
                location.href="paper?m=editPaperQuestion&courseid="+courseid+"&paperid="+rmsg.objList[0];
            }
        }
    });

}