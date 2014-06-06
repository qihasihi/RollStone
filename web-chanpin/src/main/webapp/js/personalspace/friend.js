/**
 * Created by qihaishi on 14-6-4.
 */

/**
 * ��Ӻ��ѷ���
 */
function doAddGroup(){
    var groupname=$("#groupname");
    if(groupname.val().Trim().length<1){
        alert('�������������!');
        return;
    }
    $.ajax({
        url: 'friendgroup?m=addGroup',
        type: 'post',
        data: {groupname:groupname.val()},
        dataType: 'json',
        cache: false,
        error: function () {
            alert('�����쳣!')
        },
        success: function (rps) {
            if (rps.type == 'error') {
                alert(rps.msg);
            } else {
                alert(rps.msg);
                loadGroup();
            }
        }
    });
}


function doUpdGroup(){
    var groupname=$("#dv_upd input[id='groupname']");
    var groupid=$("#hd_groupid").val();
    if(groupid.length<1){
        alert('δ��ȡ�����ʶ!');
        return;
    }
    if(groupname.val().Trim().length<1){
        alert('�������������!');
        return;
    }
    $.ajax({
        url: 'friendgroup?m=modify',
        type: 'post',
        data: {
            groupname:groupname.val(),
            groupid:groupid
        },
        dataType: 'json',
        cache: false,
        error: function () {
            alert('�����쳣!')
        },
        success: function (rps) {
            if (rps.type == 'error') {
                alert(rps.msg);
            } else {
                closeModel('dv_upd');
                alert(rps.msg);
                loadGroup();
            }
        }
    });
}


function doDelGroup(groupid){
    if(typeof  groupid=='undefined'||groupid.length<1){
        alert('δ��ȡ�����ʶ!');
        return;
    }

    if(!confirm('ɾ�����飬����ɾ�����ѣ������µĺ����Զ�ת�����ҵĺ��ѡ���!\n\nȷ��ɾ��������?'))return;
    $.ajax({
        url: 'userfriend?m=delGroup',
        type: 'post',
        data: {
            groupid:groupid
        },
        dataType: 'json',
        cache: false,
        error: function () {
            alert('�����쳣!')
        },
        success: function (rps) {
            if (rps.type == 'error') {
                alert(rps.msg);
            } else {
                alert(rps.msg);
                loadGroup();
            }
        }
    });
}

function loadGroup(){
    $.ajax({
        url: 'friendgroup?m=ajaxGroupList',
        type: 'post',
        data: {},
        dataType: 'json',
        cache: false,
        error: function () {
            alert('�����쳣!')
        },
        success: function (rps) {
            var h='';
            if (rps.objList.length>0) {
                $.each(rps.objList,function(idx,itm){
                    h+='<input type="hidden" value="'+itm.groupid+'"/><li><a href="javascript:void(0);"  onclick="loadFriendById(\''+itm.groupid+'\')">'+itm.groupname+'</a></li>';
                });
            }
            h+='<input type="hidden" value="0"/><li><a href="javascript:void(0);"  onclick="loadFriendById(\'0\')">�ҵĺ���</a></li>';
            $("#ul_group").html(h);

            var liObj=$("#ul_group li").eq(0);
            $.each(liObj,function(idx,itm){
                var groupid=$(itm).prev('input').val();
                load_group_detail(groupid);
                loadFriendById(groupid)
            });
        }
    });
}

/**
 * ��������
 */
function load_group_detail(groupid){
    if(groupid=='0'){
        $("#p_operate").html('�ҵĺ���');
        return;
    }

    $.ajax({
        url: 'friendgroup?m=ajaxGroupList',
        type: 'post',
        data: {groupid:groupid},
        dataType: 'json',
        cache: false,
        error: function () {
            alert('�����쳣!')
        },
        success: function (rps) {
            var h='';
            if (rps.objList.length>0) {
                $.each(rps.objList,function(idx,itm){
                    h=itm.groupname+"&nbsp;";
                    if(itm.groupid!=0){
                        h+='<a href="javascript:void(0);" onclick="showModel(\'dv_upd\');toUpdGroup(\''+itm.groupid+'\')">�޸�</a>&nbsp;';
                        h+='<a href="javascript:doDelGroup(\''+itm.groupid+'\')">ɾ��</a>';
                    }
                });
            }
            $("#p_operate").html(h);
        }
    });
}


/**
 * ��ȡ����
 */
function loadFriendById(groupid){
    if(typeof groupid=='undefined')
        return;

    load_group_detail(groupid);

    $.ajax({
        url: 'userfriend?m=loadFriendByGroupId',
        type: 'post',
        data: {groupid:groupid},
        dataType: 'json',
        cache: false,
        error: function () {
            alert('�����쳣!')
        },
        success: function (rps) {
            var h='';
            if (rps.objList.length>0) {
                $.each(rps.objList,function(idx,itm){
                    h+='<li>'+itm.friendname+'</li>';
                });
            }
            $("#ul_friend").html(h);
        }
    });
}



function toUpdGroup(groupid){
    if(typeof groupid=='undefined'){
        return;
    }
    $("#hd_groupid").val(groupid);

    $.ajax({
        url: 'friendgroup?m=ajaxGroupList',
        type: 'post',
        data: {groupid:groupid},
        dataType: 'json',
        cache: false,
        error: function () {
            alert('�����쳣!')
        },
        success: function (rps) {
            var h='';
            if (rps.objList.length>0) {
                $.each(rps.objList,function(idx,itm){
                    $('#dv_upd input[id="groupname"]').val(itm.groupname);
                });
            }
        }
    });
}

