/**
 * 操作ett班级
 */
function sub_cls(clsid){
    var dvObj=typeof clsid=='undefined'?'dv_add':'dv_edit';

    var clsname=$("#"+dvObj+" input[id='cls_name']");
    var verifyTime=$("#"+dvObj+" input[id='verify_time']");
    var num=$("#"+dvObj+" input[id='num']");
    var type=$("#"+dvObj+" select[id='type']");
    var bzrJid=$("#"+dvObj+" select[id='bzr']");
    var grade=$("#"+dvObj+" select[id='grade']");
    var allowJoin=$("#"+dvObj+" input[name='rdo']");

    if(clsname.val().Trim().length<1){
        alert('请输入班级名称!');
        clsname.focus();return;
    }
    if(type.val().length<1){
        alert('请选择班级类型!');
        type.focus();return;
    }
    if(bzrJid.val().length<1){
        alert('请选择班主任!');
        bzrJid.focus();return;
    }
    if(grade.val().length<1){
        alert('请选择年级!');
        grade.focus();return;
    }

    var param={clsname:clsname,type:type,jid:bzrJid.val(),gradeid:grade.val()};
    if(verifyTime.val().length>0)
        param.verifyTime=verifyTime.val();
    if(allowJoin.val().length>0)
        param.allowJoin=allowJoin.val();

    var url='tpuser?doAddCls';
    if(typeof clsid!='undefined')
        url='tpuser?doUpdCls&clsid='+clsid;
    $.ajax({
        url:url,
        dataType:'json',
        type:'POST',
        data:param,
        cache: false,
        error:function(){
            //alert('异常错误!系统未响应!');
        },success:function(rps){

        }
    });
}