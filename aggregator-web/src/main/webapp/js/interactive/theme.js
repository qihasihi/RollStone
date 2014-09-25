////////////////////////////////////////theme-admin.jsp
	/**
	 * 执行翻页前
	 * @param tobj
	 * @return
	 */
	function validateParam(tobj){ 
		var clsid=$("#sel_search_clsid").val().Trim();
		if(clsid.length<1){					
			$("select[name='sel_search_clsid'] option").each(function(idx,itm){
				var val=itm.value.Trim();
				if(val.length>0){
					if(clsid.Trim().length>0)
						clsid+=',';
					clsid+=val;
				}
			})
		}
		var param={};
		if(clsid.Trim().length>0){
			param.clsid=clsid;
		}		
		var columnsnameObj=$("#sel_columns");
		if(columnsnameObj.val()!=null&&columnsnameObj.val().Trim().length>0)
			param.columnsid=columnsnameObj.val().Trim();
		var teachercourseid=$("#teachercourseid");
		if(teachercourseid.val()!=null&&teachercourseid.val().Trim().length>0)
			param.courseteacherid=teachercourseid.val().Trim();
		tobj.setPostParams(param);		 		
	}
	/**
	 * AJAX翻页查数据后操作 
	 * @param rps
	 * @return
	 */
	function themePageReturn(rps){
				if(rps.type=="error"){
					alert(rps.msg);return;
				}
				var htm='';
				$.each(rps.objList,function(idx,itm){
					htm+='<tr';
					if(idx%2==1)
						htm+=' class="trbg2"';
					htm+='><td><p><a href="../activetopic/toStuTopic?themeid='+itm.themeid+'">'+itm.themetitle+'</a></p></td>';
					htm+='<td>'+itm.topiccount+'</td>'; //主题数
					htm+='<td>'+itm.restorecount+'</td>'; //回复数
					htm+='<td>'+itm.cuserInfo.realname+'</td>';
					htm+='<td>'+itm.ctimeShortString+'</td>';
					var stateStr="";
					if(itm.state==1)
						stateStr="显示";
					else if(itm.state==2)
						stateStr="关闭";
					else if(itm.state==3)
						stateStr="锁定"; 
					else if(itm.state==4)
						stateStr="隐藏";
					htm+='<td>'+stateStr+'</td>';
					htm+='<td>';
					htm+='<a href="javascript:;" onclick="changeOrderIdx(\''+itm.themeid+'\',1)"><img src="../images/an22_130423.png" width="9" height="10" /></a>&nbsp;';
					htm+='<a href="javascript:;" onclick="changeOrderIdx(\''+itm.themeid+'\',2)"><img src="../images/an23_130423.png" width="9" height="10" /></a>&nbsp;';
					if(itm.istop==1)
						htm+='<a href="javascript:;"  class="font-lightblue" onclick="updateColumns(\''+itm.themeid+'\',\'istop\',2)">取消置顶</a>&nbsp;';
					else
						htm+='<a href="javascript:;" class="font-lightblue" onclick="updateColumns(\''+itm.themeid+'\',\'istop\',1)">置顶</a>&nbsp;';
					
					if(itm.isbest==1)
						htm+='<a href="javascript:;" class="font-lightblue" onclick="updateColumns(\''+itm.themeid+'\',\'isbest\',2)">取消精华</a>&nbsp;';
					else
						htm+='<a href="javascript:;" class="font-lightblue" onclick="updateColumns(\''+itm.themeid+'\',\'isbest\',1)">精华</a>&nbsp;'; 
	 				//htm+='<a href="javascript:;" class="font-lightblue" onclick="updateColumns('+itm.themeid+',\'istop\',1)">删除</a>&nbsp;';
					htm+='<a href="javascript:;" class="font-lightblue" onclick="loadThemeById(\''+itm.themeid+'\')">编辑</a>&nbsp;';
					if(itm.state!=1&&itm.state!=3) 
						htm+='<a href="javascript:;" class="font-lightblue" onclick="updateColumns(\''+itm.themeid+'\',\'state\',1)">显示</a>&nbsp;';
					if(itm.state!=4)
						htm+='<a href="javascript:;" class="font-lightblue" onclick="updateColumns(\''+itm.themeid+'\',\'state\',4)">隐藏</a>&nbsp;';
					if(itm.state!=1&&itm.state==3)
						htm+='<a href="javascript:;" class="font-lightblue" onclick="updateColumns(\''+itm.themeid+'\',\'state\',1)">开放</a>&nbsp;';
//					if(itm.state!=2)
//						htm+='<a href="javascript:;" class="font-lightblue" onclick="updateColumns(\''+itm.themeid+'\',\'state\',2)">关闭</a>&nbsp;';
					if(itm.state!=3)
						htm+='<a href="javascript:;" class="font-lightblue" onclick="updateColumns(\''+itm.themeid+'\',\'state\',3)">锁定</a>&nbsp;';					
					htm+='<a href="javascript:;" class="font-lightblue" onclick="doDelTheme(\''+itm.themeid+'\')">删除</a>';
					htm+='</td>'; 
					htm+='</tr>'; 
				});	
				$("#tbl_body_data").html(htm);
	}
	/**
	 * 删除论题操作
	 * @param themeid
	 * @return
	 */
	function doDelTheme(themeid){
		if(typeof(themeid)=="undefined"){
			alert('异常错误，参数异常! themeid is empty!');return;
		}
		
		if(!confirm("您确认删除该主题吗？\n\n提示：删除主题会连带删除该主题下的所有评论!"))
			return;
		$.ajax({
			url:"tptopictheme?m=doDelTheme",
			dataType:'json',
			type:"post", 
			cache: false,
			data:{themeid:themeid},
			error:function(){
				alert('异常错误!系统未响应!');
			},success:function(rps){
				if(rps.type=="error"){
					alert(rps.msg);
				}else{
                    alert('删除主题成功!页面即将退出!');
                   // window.close();
                    history.go(-1);
				}
			}
		})
	}
	
	
	function changeAddOperateType(){
		//改成添加
			//$("span[id='op_type']").html('添加论题');
			$("#u_theme_id").val('');				
			//$("#doadd").hide();
	}
	/**
	 * 验证提交后确定执行的方法
	 * @return
	 */
	function operateColumns(){
		var themeid=$("#theme_id").val();
		if(themeid.Trim().length>0)
			doUpdateTheme();
		else 
			doAddTheme();
	}	
	/**
	执行添加主题
	*/
	function doAddTheme(){
                if(typeof(topicid)=="undefined"||topicid.Trim().length<1){
                    alert("异常错误，原因：未知!");return;
                }
				var param={};
				var title=$("#themetitle"); 
				if(title.val().Trim().length<1){
					alert('您尚未输入论题标题，请输入!'); 
					title.focus();return;
				}
             var content=ueditor.getContent();
            if(content.Trim().length<1){
                alert('您尚未输入论题内容，请输入!');
                return;
            }
            if(typeof(newthemeid)!="undefined"&&newthemeid!=null&&newthemeid.length>0)
                param.themeid=newthemeid;
                //不显示提交按钮
                $('a[onclick="doAddTheme\\(\\)"]').hide();

				param.themetitle=title.val().Trim();

				param.themecontent=content.Trim();
                param.topicid=topicid;               
				$.ajax({
					url:"tptopictheme?m=doSaveTopicTheme",
					dataType:'json',
					type:"post", 
					cache: false,
					data:param,
					error:function(){
                        $('a[onclick="doAddTheme\\(\\)"]').show();
						alert('异常错误!系统未响应!');
					},success:function(rps){
						if(rps.type=="error"){
                            $('a[onclick="doAddTheme\\(\\)"]').show();
							alert(rps.msg);
						}else{
                            newthemeid=rps.objList[0];
                            //注销 ueditor
                            UE.getEditor('themecontent').setDataId(newthemeid);
							try{pageGo('p1');}catch(e){}
                            //清空
                            $("#themetitle").val('');
                            UE.getEditor('themecontent').setContent('');
                           // 弹出信息
                            $('a[onclick="doAddTheme\\(\\)"]').show();
                            alert(rps.msg);
                            closeModel("dv_create");

						}						
					}
				});				
			}
	
	function changeGroupTr(){
		var ckVal=$('input[name="a_display"]:checked').val();
		if(ckVal==3){
			$("tr[id='tr_group_display']").show();
		}else
			$("tr[id='tr_group_display']").hide();		
	}
			
	//根据ID得到实体
	function loadThemeById(id){
			 	if(typeof(id)=="undefined"||!isNaN(id)){
			 		alert('异常错误，参数异常！');return;
			 	}
			 	var param={themeid:id};
			 //	param.columnsid=columnsid;
			 	$.ajax({
					url:"getThemeBy",
					dataType:'json',
					type:"post", 
					cache: false,
					data:param,
					error:function(){
						alert('异常错误!系统未响应!');
					},success:function(rps){
						if(rps.type=="error"){
							alert(rps.msg);
						}else{															
							$.each(rps.objList,function(idx,itm){
								$("#u_theme_id").val(itm.themeid);
								$("#a_themetitle").val(itm.themetitle);
								$("#a_themecontent").val(itm.themecontent)
								$("#a_keyword").val(itm.themekeyword);
								if(typeof(itm.columnsInfo.columnsid)!="undefined"){
									//$("#a_sel_columnsid").val(itm.columnsInfo.columnsid);
									//栏目处理
									$("td[id='td_update_selCol'] select").val(itm.columnsid);
									$("td[id='td_add_selCol']").hide();
									$("td[id='td_update_selCol']").show();
								}
								$("input[name='a_state']").filter(function(){return this.value.Trim()==itm.state}).attr("checked",true);
								$("input[name='a_display']").filter(function(){return this.value.Trim()==itm.watchtype}).attr("checked",true);
								if(itm.watchtype==3){
									$("input[name='ckx_group']").attr("checked",false);
									var watchid=itm.watchid;
									if(typeof(watchid)!="undefined"){
										$.each(watchid.split('|'),function(x,m){
											if(m.Trim().length>0){
												$("#group_"+m.Trim()).attr("checked",true);
											}
										});
									}
									$("tr[id='tr_group_display']").show();
								}else
									$("tr[id='tr_group_display']").hide();
								//版主 
								if(typeof(itm.boarduserid)!="undefined"){
									$("input[name='ckx_stuname']").attr("checked",false);
									var borderuserobj=itm.boarduserid.split(',');
									$.each(borderuserobj,function(tx,tm){
										if(tm.Trim().length>0){
											$("input[name='ckx_stuname']").filter(function(){
												return this.value.split('|')[0].Trim()==tm.Trim();
											}).attr('checked',true);
										}
									});
									//防止报异常  1:添加   2:修改
									$("#stutype").val(2);
									btnStuClick();
								}
								//$("#doadd").show();
							})
							//$("span[id='op_type']").html('修改论题');
						}						
					}
				});	 			
			}

			

/**
 * 修改 置顶或精华
 * @param type  1:置顶  2:精华
 * @param val   0:取消  1:确定
 * @param spanid    操作的页面spanid
 */
    function isTopOrIsEssence(id,type,val,spanid){
        if(typeof(id)=="undefined"||id.Trim().length<1){
            alert('异常错误，原因：id 异常!');return;
        }
        if(typeof(type)=="undefined"||isNaN(type)){
            alert("异常错误，原因：type 异常!");return;
        }
        if(typeof(val)=="undefined"||isNaN(val)){
            alert("异常错误，原因：val 异常!");return;
        }
        var opcolumn="istop";
        if(type==2)
         opcolumn="isessence";
        updateColumns(id,opcolumn,val);
        if(typeof(spanid)!="undefined"){
            var h="<a href=\"javascript:isTopOrIsEssence('"+id+"','"+type+"','"+(val==0?1:0)+"','"+spanid+"')\">";
            if(type==2){
            	h+='<span class="ico40"></span>';
                if(val==0)
                    h+='精华';
                else
                    h+='取消精华';
            }else{
            	h+='<span class="ico56"></span>';
                if(val==0)
                    h+='置顶';
                else
                    h+='取消置顶';
            }
            h+='</a>';
            $("span[id='"+spanid+"']").html(h);
        }
    }

	/**
	 * 修改字段
	 * @param themeid
	 * @param columnName
	 * @param value
	 * @return
	 */
	function updateColumns(themeid,columnName,value){
		if(themeid==null||typeof(themeid)=="undefined"){
			alert('异常错误，参数异常! 错误代码：themeid is empty or error!');return;
		}
		if(typeof(columnName)==null||typeof(columnName)=="undefined"||columnName.Trim().length<1){
			alert('异常错误，参数异常! 错误代码：columnName is empty or error!');return;				
		}
		if(typeof(value)==null||typeof(value)=="undefined"||(typeof(value)=="string"&&value.Trim().length<1)){
			alert('异常错误，参数异常! 错误代码：value is empty or error!');return;				
		}
		//如果是String类型，则去两端空格及去掉单引号
		if(typeof(value)=="string")
			value=value.Trim().replace("'","‘");  
		var param={themeid:themeid}; 
		eval("(param."+columnName+"='"+value+"')");
		$.ajax({
			url:"tptopictheme?m=updateColumnByThemeId",
			dataType:'json',
			type:"post", 
			cache: false,
			data:param,
			error:function(){
				alert('异常错误!系统未响应!');
			},success:function(rps){ 
				if(rps.type=="error"){
					alert(rps.msg);
                    return false;
				}else{
                    //操作成功
                    return true;
				}						
			}
		});
	}
	/**
	 * 注册按钮事件
	 * @return
	 */
	function btnStuClick(){
		var stutype=$("#stutype").val().Trim(); 
		if(stutype.length<1){
			alert('异常错误，不正当弹出!');return; 
		}
		var showaddr="add_banzhu";
		var shtml='';
		$("input[name='ckx_stuname']:checked").each(function(idx,itm){
			var itmVal=itm.value.split('|');
			shtml+='<span id="stu_idx_'+itmVal[0]+'">'+itmVal[1];
			shtml+='<input type="hidden" value="'+itmVal[0]+'" name="stu_val"/>';
			shtml+='<input type="hidden" value="'+itmVal[1]+'" name="stu_relname"/>';
			shtml+='<a href="javascript:;" style="color:red" onclick="deleSpan(\'stu_idx_'+itmVal[0]+'\')">[X]</a>';
			shtml+='</span>&nbsp;';
		});
		if(shtml.Trim().length<1){
			alert('提示：您尚选择版主，将会默认为自己!');return;
		}
		$("#"+showaddr).html(shtml); 
		closeModel('studiv');
}
function deleSpan(id){
	$("span[id='"+id+"']").remove();
}
function showStuDiv(type){
	if(typeof(type)=="undefined")
		type=1;
	$("#stutype").val(type);
	$("#studiv").css("overflow-y","auto");  
	showModel('studiv',false);
} 
//资源论题管理页查询功能
function searchBeadCheck(txtid){
	var txtVal=$("#"+txtid).val().Trim();
	if(txtVal.length>0){		
		var msg="没有发现相似的学生!";
		if($("input[name='ckx_stuname'][value*='"+txtVal+"']").length>0){
			msg="";
			$("input[name='ckx_stuname'][value*='"+txtVal+"']").each(function(ix,im){
				if(msg.Trim().length>0)
					msg+=',';
				msg+=$(this).val().split("|")[1];
			})
			if(msg.length>0)
				msg="查到学生："+msg+"\n\n是否勾选?"
			if(confirm(msg)){
					$("input[name='ckx_stuname'][value*='"+txtVal+"']").attr("checked",true);
			}
		}else
		alert(msg);
		
	}
	
}



//显示修改层，修改TopicContent
function showUpdateDiv(divid,themetid){
    var tpccontent=$("#pizhu_"+themetid+"_1_updatecontent").html().Trim();
   
    $("#"+divid+" input[type='text']").val($("span[id='title_span']").html().Trim());
    $("#"+divid+" input[id='themeid']").val(themetid);
    showModel(divid,false);

    //加载富文本赋值
    UE.getEditor('update_txt').setContent(tpccontent);
}
//进行修改
function updateThemeContent(divid){

    var themecontent=$("#div_update textarea").val();
    var themeid=$("#"+divid+" input[id='themeid']").val();
    if(themeid.Trim().length<1){
        alert('异常错误，标识无效!');
        return;
    }
    var updatetitle=$("#div_update input[type='text']");
    if(updatetitle.val().Trim().length<1){
        alert('异常错误，修改后的标题不能为空!');
        updatetitle.focus();return;
    }
    var content=UE.getEditor('update_txt').getContent();

    if(!confirm("您确认要修改该主题吗?\n\n提示：修改后，修改前的主题数据不予保留!"))return;
    $.ajax({
        url:'tptopictheme?m=doUpdateTheme',
        dataType:'json',
        type:"post",
        cache: false,
        data:{themeid:themeid,themecontent:content,themetitle:updatetitle.val().Trim()},
        error:function(){
            alert('异常错误!系统未响应!');
        },success:function(rps){
            if(rps.type=="error"){
                alert(rps.msg);
            }else{
                if(rps.type=="success"){
                    $("#pizhu_"+themeid+"_1_updatecontent").html(content);
                    $("span[id='title_span']").html(updatetitle.val().Trim());
                    closeModel(divid);
                }
                alert(rps.msg);
            }
        }
    });
}


/**
 *显示批注
 */
function showPiZhuDiv(divid,id){
    if(typeof(id)=="undefined"||isNaN(id)){
        alert('异常错误，参数异常! id is empty!');return;
    }

    $("#"+divid+" span[id='to_span']").html($("span[id='realname_theme']").html());
    $("#pizhu_id").val(id);
	showModel(divid,'fade',false);
    var obj=$("#pizhu_txt").xheditor({tools:''});
    obj.setSource($("#pizhu_"+id+"_1_updatecontent").html());
    //将按钮加进工具栏
    //加载富文本
   // UE.getEditor("pizhu_txt").setContent($("#pizhu_"+id+"_1_updatecontent").html());
  $("#pizhu_txt").notes('mfull',obj);
  
  //$("#pizhu_txt").prev('#notes_control_tools').css("display","none")
  //将按钮加进工具栏
  $(".xhe_default table tbody tr").first().css("display","none");
  
//  $(".xhe_default table tbody tr:first")
//	.html("<td>"+$("#pizhu_txt").prev('#notes_control_tools').html()+"</td>");

}
/**
 * 关闭批注
 * @param divid
 */
function closePiZhuDiv(divid){
    $("#pizhu_txt").removeNotes();
    //UE.getEditor("pizhu_txt").destroy();
    closeModel(divid);
}


/**
 * 更新
 * @return
 */
function updateThemePiZhu(){

    var id=$("#pizhu_id").val();
    if(typeof(id)=="undefined"||id.Trim().length<1){
        alert('异常错误!参数异常! 错误代码：id is empty!');
        return;
    }
    var txtVal=$("#pizhu_txt").val();
    if(txtVal.Trim().length<1){
        alert('错误，批注不能为空!请填写完后进行确定!');
        return;
    }
    var url="tptopictheme?m=doUpdateTheme";

    //确认进行修改
    $.ajax({
        url:url,
        dataType:'json',
        type:"post",
        cache: false,
        data:{themeid:id.Trim(),commentcontent:txtVal.Trim()},
        error:function(){
            alert('异常错误!系统未响应!');
        },success:function(rps){
            if(rps.type=="error"){
                alert(rps.msg);
            }else {
                var idobj="div[id='pizhu_"+id+"_1_updatecontent']";
                $(idobj).html(txtVal.Trim());
                $("#pizhu_id").val('');
                closePiZhuDiv('div_pizhu');
            }
        }
    });
}

/**
 * 快速回贴
 * @param topicid
 * @param type 1:回复主贴， 2:回复评论
 */
function quckRestore(topicid,type,touserid){
	/*if(state==3){
		alert("该论题已经锁定，无法发布论题及其下的主题进行回复、评论等功能!");return;
	}*/
	if(typeof(topicid)=="undefined"){
		alert('异常错误，参数异常!请刷新页面后重试!');
		return;
	}
	if(typeof(type)=="undefined"||isNaN(type)){
		alert('异常错误，参数异常!请刷新页面后重试!');
		return;
	}
	
	 var content=UE.getEditor('returnVal').getContent();
	if(content.Trim().length<1){
		alert("回复内容您尚未输入，请输入后提交!");
		return;
	}
	var param={themeid:themeid
			   ,replycontent:content.Trim()
			};
	
	$.ajax({
		url:"tpthemereply?m=doAddReply",
		dataType:'json',
		type:"post",  
		cache: false, 
		data:param,
		error:function(){
			alert('异常错误!系统未响应!');
		},success:function(rps){
			if(rps.type=="error"){
				alert(rps.msg);
			}else { 
				var htm='';
				UE.getEditor("returnVal").setContent("");
				pageGo('p1');
                $("span[id='sp_pinglunshu']").html(parseInt($("span[id='sp_pinglunshu']:first").html())+1);
				//unloadTextAre('returnVal_'+topicid,'p_btn');
			}
		}
	});	
}

function tpThemeReplyListReturn(rps){
	if(rps.type=="error"){
		alert(rps.msg);return;
	}
	var h='';
	$("#div_restore").html(h);
	if(rps.objList.length>0){		
		$.each(rps.objList,function(idx,itm){
			h+='<div class="jxxt_zhuanti_hdkj_pl_nr">'
			h+='<div class="info">'
			if(typeof(itm.cheadimage)!="undefined"){
				h+='<p><img src="'+itm.cheadimage+'" alt="" width="125" height="125"></p>';
			}else{
				h+='<p><img src="images/defaultheadsrc_big.jpg" alt="" width="125" height="125"></p>';
            }
			h+='<p class="name">姓&nbsp;名：<span id="sp_author_'+itm.replyid+'">'+itm.crealname+'<span></p>'
			h+='</div>'
			h+='<div class="text">'
			h+='<p class="time"><span class="font-darkblue"><a href="javascript:;" onclick="doYingyong('+itm.replyid+')">引用</a>';
			h+='&nbsp;&nbsp;&nbsp;&nbsp;';
			if(loginuserid.length>0&&loginuserid==itm.userid||roleType.length>0&&roleType=="TEACHER"){
				h+='<a href="javascript:;" onclick="doDeleteReply('+itm.replyid+')">删除</a>';
			}
			h+='</span><strong>'+itm.rownum+'楼</strong>';
			h+='<span class="font-darkgray">评论时间：'+itm.ctimeString+'</span></p>';
			h+='<div style="width:100%;height:100%;overflow-x:auto" id="reply_content_'+itm.replyid+'">'+itm.replycontent+'</div>';
      		h+='</div></div><div>'
      		if(idx!=0&&idx%5==0){
      			$("#div_restore").append(h);
      			h='';
      		}	
		});
		
	}
	if(h.length>0)
		$("#div_restore").append(h);
	
	//翻页信息
	if (typeof (p1) != "undefined" && typeof (p1) == "object") {
		p1.setPagetotal(rps.presult.pageTotal);
		p1.setRectotal(rps.presult.recTotal);
		p1.setPageSize(rps.presult.pageSize);
		p1.setPageNo(rps.presult.pageNo);	
		p1.Refresh();
	}	
	
}

/**
 * 
 * @return
 */
function doYingyong(id){	
	//引用
	if(typeof(id)=="undefined"){
		alert('异常错误!参数异常! 错误代码：id is empty!');
		return; 
	} 	
	location.href="#div_resotre_queck"; 
	//loadTextArea("returnVal_"+topicid,'p_btn');
	//得到引用的数据
	var h='<fieldset class="fieldset">';
    	h+='<legend>引用</legend>';
    	h+='<div class="p_5_10">';
    	h+='<span class="font_blue">';
    	h+='作者: <strong>'; 
    	h+=$("span[id='sp_author_"+id+"']:first").html().Trim();
    	h+='</strong>';
    	//h+='<a rel="nofollow" href="showthread.php?p=65079#post65079"><img border="0" alt="查看帖子" src="images/buttons/viewpost.gif" class="inlineimg" title="查看帖子"></a>';
    	h+='</span>';
    	h+='<span class="wrapline p_tb_10"><br/><br/>'; 
    	h+=$('div[id="reply_content_'+id+'"]:first').html().Trim();
    	h+='</span>';
    	h+='</div>';
    	h+='</fieldset><br/>'; 
    UE.getEditor('returnVal').setContent(h);
		
}

/**
 * 删除评论
 * @param id
 * @return
 */
function doDeleteReply(id){
	//引用
	if(typeof(id)=="undefined"){
		alert('异常错误!参数异常! 错误代码：id is empty!');
		return; 
	} 	
	if(!confirm("您确认要删除该评论吗?"))
		return;
	var param={replyid:id};
	$.ajax({
		url:"tpthemereply?m=doDeleteReply",
		dataType:'json',
		type:"post",  
		cache: false, 
		data:param,
		error:function(){
			alert('异常错误!系统未响应!');
		},success:function(rps){
			if(rps.type=="error"){
				alert(rps.msg);
			}else {
				pageGo('p1');
				alert(rps.msg);
                //修改数量
                $("span[id='sp_pinglunshu']").each(function(idx,itm){
                    this.innerText=parseInt(this.innerText)-1;

                })
				//unloadTextAre('returnVal_'+topicid,'p_btn');
			}
		}
	});
}
/**
 * 赞
 * @return
 */
function addOrCannelPariseTheme(themeid,type){
	if(typeof(themeid)=="undefined"||typeof(type)=="undefined"){
		alert("异常错误,原因:参数异常!");return;
	}
	
	$.ajax({
		url:"tptopictheme?m=addOrCannelPariseTheme",
		dataType:'json',
		type:"post",  
		cache: false, 
		data:{themeid:themeid,type:type},
		error:function(){
			alert('异常错误!系统未响应!');
		},success:function(rps){
			if(rps.type=="error"){
				alert(rps.msg);
			}else {				
				//unloadTextAre('returnVal_'+topicid,'p_btn');
				var h='';
				if(type==1){//赞
					h+='<span id="sp_praiseshu"><a  href="javascript:addOrCannelPariseTheme('+themeid+',\'2\');"><span class="ico41"></span>取消赞('+rps.objList[0]+')</a>';
					
				}else{
					h+='<span id="sp_praiseshu"><a  href="javascript:addOrCannelPariseTheme('+themeid+',\'1\');"><span class="ico41"></span>赞('+rps.objList[0]+')</a>';
				}
				$("span[id='sp_praiseshu']").html(h);
				
			}
		}
	});
	
}

