/**
 * Created by qihaishi on 14-6-4.
 */

/**
 * 添加好友分组
 */
function doAddGroup(){
    var groupname=$("#groupname");
    if(groupname.val().Trim().length<1){
        alert('请输入分组名称!');
        return;
    }
    $.ajax({
        url: 'friendgroup?m=addGroup',
        type: 'post',
        data: {groupname:groupname.val()},
        dataType: 'json',
        cache: false,
        error: function () {
            alert('网络异常!')
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
        alert('未获取分组标识!');
        return;
    }
    if(groupname.val().Trim().length<1){
        alert('请输入分组名称!');
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
            alert('网络异常!')
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
        alert('未获取分组标识!');
        return;
    }

    if(!confirm('删除分组，不会删除好友，该组下的好友自动转到【我的好友】中!\n\n确定删除该组吗?'))return;
    $.ajax({
        url: 'userfriend?m=delGroup',
        type: 'post',
        data: {
            groupid:groupid
        },
        dataType: 'json',
        cache: false,
        error: function () {
            alert('网络异常!')
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
            alert('网络异常!')
        },
        success: function (rps) {
            var h='';
            if (rps.objList.length>0) {
                $.each(rps.objList,function(idx,itm){
                    h+='<input type="hidden" value="'+itm.groupid+'"/><li><a href="javascript:void(0);"  onclick="loadFriendById(\''+itm.groupid+'\')">'+itm.groupname+'</a></li>';
                });
            }
            h+='<input type="hidden" value="0"/><li><a href="javascript:void(0);"  onclick="loadFriendById(\'0\')">我的好友</a></li>';
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
 * 分组详情
 */
function load_group_detail(groupid){
    if(groupid=='0'){
        $("#p_operate").html('我的好友');
        return;
    }

    $.ajax({
        url: 'friendgroup?m=ajaxGroupList',
        type: 'post',
        data: {groupid:groupid},
        dataType: 'json',
        cache: false,
        error: function () {
            alert('网络异常!')
        },
        success: function (rps) {
            var h='';
            if (rps.objList.length>0) {
                $.each(rps.objList,function(idx,itm){
                    h=itm.groupname+"&nbsp;";
                    if(itm.groupid!=0){
                        h+='<a href="javascript:void(0);" onclick="showModel(\'dv_upd\');toUpdGroup(\''+itm.groupid+'\')">修改</a>&nbsp;';
                        h+='<a href="javascript:doDelGroup(\''+itm.groupid+'\')">删除</a>';
                    }
                });
            }
            $("#p_operate").html(h);
        }
    });
}


/**
 * 获取好友
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
            alert('网络异常!')
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
            alert('网络异常!')
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

