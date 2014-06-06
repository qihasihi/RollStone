function addNewGroup(){
	var classId=$("#classId").val();
    var classType=$("#classType").val();
	var groupName=$("#groupName").val();

	if(groupName.Trim().length<1){
		alert('请输入小组名称!');
		return;
	}
    if(groupName.length>50){
        alert('小组名称限50字以内!');
        return;
    }

	var dat={groupname:groupName,classid:classId,classtype:classType};
	$.ajax({
		url:'group?m=addNewGroup',
		data:dat,
		type:'post',
		dataType:'json',
		error:function(){
			alert('异常错误,系统未响应！');
		},success:function(rps){
			if(rps.type=="success"){
				alert("添加成功!");
				closeModel('addGroupDiv');
                getClassGroups();
			}else if(rps.type=="errorname"){
				alert("小组名已存在！");
			}else{
				alert(rps.msg+",无法添加!");
			}		
		}
	});
}

function addStudentsToGroup(){ 
	var groupId=$("input[name=forGroupId]:checked").val();
	if(groupId==0||groupId==null){
		alert("请选择小组！");
		return;
	}
    var stusId =$("#no_gs").val();
	if(stusId==null || stusId.join(",").length==0){
		alert("请选择学生！");
		return;
	}
	var dat={groupid:groupId,stusId:stusId.join(",")};
	$.ajax({
		url:'group?m=addStudentsToGroup',
		data:dat,
		type:'post',
		dataType:'json',
		error:function(){
			alert('异常错误,系统未响应！');
		},success:function(rps){
			if(rps.type=="success"){
				alert(rps.msg);
                closeModel("selectStudent_Div");
                getNoGroupStudentsByClassId();
                getClassGroups();
			}else{
				alert(rps.msg);
			}		
		}
	});
}

function showNoGroupStudent(type){
	if(type){
		$("#studentSelectArea").show();
	}else{
		$("#studentSelectArea").hide();
	}
}

function getNoGroupStudentsByClassId(){
	var classId=$("#classId").val();
    var classType=$("#classType").val();
	if(classId==null||classId.length==0){
		alert("请选择班级！");
		return;
	}
	$("#noGroupStudents").html("");
	var url="group?m=getNoGroupStudents";
	$.post(url,{classId:classId,classType:classType},
			function(responseText){
		if(responseText==null||responseText.objList==null){
			//没有数		
			$("#noGroupStudents").append("<li>无</li>");
            $("#no_gs").html();
			return;
		}
		var ulId="";
        $("#no_gs").html("");
		$.each(responseText.objList,function(idx,itm){
			$("#noGroupStudents").append("<li>"+itm.STU_NAME+"</li>");
            $("#no_gs").append("<option value='"+itm.USER_ID+"' >"+itm.STU_NAME+"</option>");
		});
        if(responseText.objList.length>0){
            $("#no_gs").append("<option id='special_option' value='0' style='display:none;' ></option>");
        }else{
            $("#no_gs").append("<option id='special_option' value='0' ></option>");
        }
		if(responseText.objList.length==0){
			//$("#noGroupStudents").html("<li>全部添加到小组！</li>");
            $("#nogroup").hide();
		}

        $("#no_gs_bk").html($("#no_gs").html())

	},"json"); 
}

function getClassGroups(){
    var classId=$("#classId").val();
    var classType=$("#classType").val();
	var url="group?m=getClassGroups";
	$.ajax({
			url:url,
			data:{classid:classId,classtype:classType},
			type:'post',
			dataType:'json',
			error:function(){
				alert('异常错误,系统未响应！');
			},
			success:function(responseText){
                var forGroupHtml="";
                var changeGroupHtml="";
                var groupHtml="<colgroup><col class='w350'/><col class='w190'/><col class='w200'/></colgroup><tr><th>组名</th><th>学号</th><th>组员姓名</th></tr>";
                if(responseText!=null&&responseText.objList!=null&&responseText.objList.length>0){
                    $.each(responseText.objList,function(idx,itm){
                        groupHtml+="<tbody id='group_"+itm.groupid+"'>";
                        groupHtml+="<tr>";
                        groupHtml+="<td class='v_c'>"+itm.groupname;
                        if(isTrust.toString().length<1)
                            groupHtml+="<a href='javascript:delGroup("+itm.groupid+");' class='ico04' title='删除'>";
                        groupHtml+="</td>";
                        groupHtml+="<td>&nbsp;</td>";
                        groupHtml+="<td>&nbsp;</td>";
                        groupHtml+="</tr>";
                        groupHtml+="</tbody>";
                        forGroupHtml+="<li><input type='radio' name='forGroupId' value='"+itm.groupid+"'/>"+itm.groupname+"</li>";
                        changeGroupHtml+="<option value='"+itm.groupid+"' >"+itm.groupname+"</option>";
                    });
                    $("#group_list").html(groupHtml);
                    $("#forGroupList").html(forGroupHtml);
                    $("#changeGroups").html(changeGroupHtml);
                    $.each(responseText.objList,function(idx,itm){
                        getGroupStudents(itm.groupid,itm.groupname);
                    });
                }else{
                    $("#group_list").html(groupHtml+"<tr><td colspan='3'>您还没有创建小组！</td></tr>");
                }

	}}); 
}

function editGroupName(groupId,groupName){
	var html="<input size='10' id='editGN_"+groupId+"' type='text' value='"+groupName+"'/>";
	html+="<a style='float:right;' href="+"javascript:updateGroup("+groupId+");"+">保存&nbsp;&nbsp;</a>";
	$("#gn_"+groupId).html(html)
}

function updateGroup(){
	var groupId = $("#uGroupID").val();
	var groupname=$("#editGN").val();
    if(groupId==null || groupId.length<1){
        alert("小组参数错误！！");
        return;
    }
	if(groupname.length<1){
		alert("清输入组名！！");
		return;
	}
	var url="group?m=editGroupName";
	$.post(url,{groupid:groupId,groupname:groupname},
			function(rps){
		if(rps.type=="success"){
			alert("修改成功!");
			closeModel('updateGroupName');
		}else if(rps.type=="errorname"){
			alert("小组名已存在！");
		}else{
			alert(rps.msg);
		}
	},"json");
}

function delGroup(groupId){
	if(!confirm("确认删除？"))
		return;
	var url="group?m=delGroup";
	$.post(url,{groupid:groupId},
			function(rps){
		if(rps.type=="success"){
			alert("删除成功!"+rps.msg);
            getClassGroups();
            getNoGroupStudentsByClassId();
        }else{
			alert(rps.msg);
		}
	},"json"); 
}

function delGroupStudent(ref){
    if(!confirm("确认删除？"))
        return;
	var url="group?m=delGroupStudent";
	$.post(url,{ref:ref},
			function(rps){
		if(rps.type=="success"){
			alert(rps.msg);
            getNoGroupStudentsByClassId();
            getClassGroups();
		}else{
			alert("删除失败");
		}
	},"json");
}

function getGroupStudents(groupId,groupName){
	var url="group?m=getGroupStudents";
	var gtHtml="";
	$.post(url,{groupid:groupId},
			function(responseText){
		if(responseText==null||responseText.objList==null||responseText.objList.length==0){
            gtHtml+="<tr><td class='v_c'>"+groupName+"<a href='javascript:delGroup("+groupId+");' class='ico04' title='删除'></td>";
			gtHtml+="<td colspan='2'>没有小组成员！</td></tr>";
			$("#group_"+groupId).html(gtHtml);
			return;
		}                 ;
		$.each(responseText.objList,function(idx,itm){
			gtHtml+="<tr>";
            if(idx==0){
                gtHtml+="<td rowspan="+responseText.objList.length+" class='v_c'>"+groupName;
                if(isTrust.toString().length<1)
                    gtHtml+="<a href='javascript:delGroup("+groupId+");' class='ico04' title='删除'>";
                gtHtml+="</td>";
            }
			gtHtml+="<td>"+itm.stuno+"</td>";
			gtHtml+="<td><span class='w60'>"+itm.stuname+"</span>";
            if(isTrust.toString().length<1)
                gtHtml+="<a href='javascript:delGroupStudent("+itm.ref+");' class='ico34' title='移出小组'></a>";
            if(itm.isleader==1&&isTrust.toString().length<1)
                gtHtml+="<a href='javascript:showGroupsPanel("+itm.ref+");' class='ico23' title='调组'></a></td>";
            if(itm.isleader==2&&isTrust.toString().length<1)
                gtHtml+="<a href='javascript:showGroupsPanel("+itm.ref+");' class='ico22' title='调组'></a></td>";
			gtHtml+="</tr>";
		});
		$("#group_"+groupId).html(gtHtml);
	},"json");
}

function closeDiv(showId){				
	showAndHidden(showId,'hide');
	showAndHidden('fade','hide');
}

function showGroupsPanel(ref){
	$("#gs_ref").val(ref);
	showModel('changeGroupDiv');
}

function allSelected(flag){
	if(flag)
		$("input[name='studentsId']").each(function(){$(this).attr("checked",true);});
	else
		$("input[name='studentsId']").each(function(){$(this).attr("checked",!this.checked);});
}

function changeStuGroup(){
	var newGroupId = $("#changeGroups").val();
    var isleader = 2;
    if($("#isleader").attr("checked"))
        isleader=1;
    var ref = $("#gs_ref").val();
    if(ref==null||newGroupId==null){
    	alert("无法调组！");
    	return;
    }
	var url="group?m=changeGroupStudent";
	$.post(url,{ref:ref,groupid:newGroupId,isleader:isleader},
			function(rps){
		if(rps!=null&&rps.type=="success"){
			closeModel('changeGroupDiv');
            getClassGroups();
		}else{
			alert("无法调组！");			
		}
	},"json"); 
}

function editGroupNameDiv(){
	var groupId=$("#classGroups").val();
	if(groupId==0){
		alert("请先创建小组！");
		return;
	}
	showModel2('updateGroupName');
	$("#uGroupID").val(groupId);
	$("#editGN").val($("#classGroups").find("option:selected").text());
}

function showModel2(showId,isdrop){ 
	$("#fade").css("height",window.screen.availHeight+parseFloat(getScrollTop())); 
	$("#fade").css("width",window.screen.availWidth);
	try{
		$("#"+showId).css("z-index",1005);
		$("#"+showId).css("position","absolute");
		$("#"+showId).css("top","350");
		$("#"+showId).css("left","500");		
	}catch(e){}
	
	showAndHidden(showId,'show');			 	
	showAndHidden('fade','show');
		
}

function filterResult(){
    var stuname=$("#stu_name").val();
    var n=0;
    var h='';
    $("#no_gs_bk option").each(function(idx,itm){
        if($(this).html().indexOf(stuname)!=-1){
            h+='<option value="'+itm.value+'">'+itm.text+'</option>';
            n++;
        }
    });
    $("#no_gs").html(h);





    //  显示预留条目，否则select框体会消失
    if(n==0)
        $("#special_option").show();
    else
        $("#special_option").hide();
}