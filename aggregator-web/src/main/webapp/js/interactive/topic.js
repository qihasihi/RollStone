//////////////////////////////////////////////////////TOPIC-ADMIN.JSP

/**
			*组织参数
			*/
			function validateParam(tobj){
				var param=new Object();
				param.courseid=courseid;
				var topicnameObj=$("#sel_topicname"); 
				if(topicnameObj.val().Trim().length>0)
					param.topictitle=topicnameObj.val().Trim();
				tobj.setPostParams(param);	
			}			 
			 
			function topicPageReturn(rps){
				if(rps.type=="error"){
					alert(rps.msg);return;
				}else{
					var htm='';
					$.each(rps.objList,function(idx,itm){
						htm+='<tr';
						if(idx%2==1)
							htm+=' class="trbg2"';
						htm+='><td><p><a target="_blank" href="tptopic?m=toDetailTopic&topicid='+itm.topicid+'">';
						htm+=itm.topictitle25word+'</a></p></td>';
                        htm+='<td>'+itm.themecount+'</td>';
                        htm+='<td>'+itm.restorecount+'</td>';
                        htm+='<td>'+(itm.status==1?"开放":"关闭")+'</td>';
                        htm+='<td>'+itm.crealname+'</td>';
                        htm+='<td>'+itm.ctimeShortString+'</td>';
						htm+='<td>';
                        //云端数据，不允许修改
                        if(typeof(itm.cloudstatus)=="undefined"||itm.cloudstatus!=3&&itm.cloudstatus!=4){
                            htm+='<a href="javascript:toFillUpdate('+itm.topicid+')">编辑</a>&nbsp;';
                         }
                         htm+='<a href="javascript:deleteTopic('+itm.topicid+')">删除</a>&nbsp;';
						htm+='</td>';
						htm+='</tr>';
					}) 
					$("#tbl_body_data").html(htm);	
					//翻页信息
						if (typeof (p1) != "undefined" && typeof (p1) == "object") {
							p1.setPagetotal(rps.presult.pageTotal);
							p1.setRectotal(rps.presult.recTotal);
							p1.setPageSize(rps.presult.pageSize);
							p1.setPageNo(rps.presult.pageNo);	
							p1.Refresh(); 
						}				
				}	
			}

/**
 填充准备修改
 */
function toFillUpdate(id){
    if(typeof(id)=="undefined"||isNaN(id)){
        alert('异常错误，参数异常!原因：id is empty!');return;
    }
    $.ajax({
        url:'tptopic?m=getTopicById',
        type:"POST",
        cache:false,
        dataType:"json",
        data:{topicid:id},
        error:function(){
            alert('异常错误!系统未响应!');
        },success:function(rps){
            if(rps.type=="error"){
                alert(rps.msg);
            }else{
                $.each(rps.objList,function(idx,itm){
                    $("#u_topicid").val(itm.topicid);
                    $("#u_topictitle").val(itm.topictitle);
                    $("#u_topiccontent").val(itm.topiccontent);
                    $("input[name='rdo_update_tpc_state'][value='"+itm.status+"']").attr("checked",true);
                })
            }
        }

    });
}
/**
 执行修改
 */
function updateTopic(){

    //标题
    var topictitle=$("#topictitle");
    if(topictitle.val().Trim().length<1){
        alert('您尚未填写栏目名称，请输入!');
        topictitle.focus();
        return;
    }
    //说明
    var tcontent=$("#topiccontent");
    if(tcontent.val().Trim().length<1){
        alert('异常错误，参数异常!原因：topicid  is empty!');
        tcontent.focus();
        return;
    }
    var cont=tcontent.val().Trim();
    if(cont.length>500){
        if(!confirm("您输入的论题说明已经大于500字，是否取前500字后以‘省略号代替’?"))
            return;
        cont=cont.substring(0,500)+"……";
    }
    //状态
    var status=$("input[name='rdo_tpc_state']:checked").val();
    var topicid=$("#topicid").val();
    if(topicid.Trim().length<1){
        alert('异常错误，参数异常!原因：topicid  is empty!');
        return;
    }
    //参数
    var param={
        topicid:topicid,
        topictitle:topictitle.val().Trim(),
        topiccontent:cont,
        status:status
    };
    if(!confirm('您修改的论题如下：\n论题标题：'
        +topictitle.val().Trim()+'\n论题说明：'+tcontent.val().Trim()+'\n论题状态：'+(param.status==1?"开放":"关闭")+'\n\n您确认修改吗?'))
        return;
    $.ajax({
        url:"tptopic?m=doUpdateTopic",
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
            	alert("修改论题成功!");
                location.href="tptopic?m=index&courseid="+courseid;
            }
        }
    });

}
//添加修改页面
function operateTopic(){
	if(typeof(tpcid)!="undefined"){
		if(tpcid.length>0)
			updateTopic();
		else
			addTopic();
	}
	
}


/**
 *发论题
 */
function addTopic(){
    if(typeof(courseid)=="undefined"||courseid==null){
        alert('异常错误，参数异常!原因：courseid is empty!');
        return;
    }
    var title=$("#topictitle");
    if(title.val().Trim().length<1){
        alert('发贴失败,您尚未输入标题!请输入!');
        title.focus();return;
    }
    var content=$("#topiccontent");
    if(content.val().Trim().length<1){
        alert('发帖失败，您尚示输入内容!请输入!');
        content.focus();return;
    }

    var ctmp=content.val().Trim();
    if(ctmp.length>500){
        if(!confirm("您输入的论题说明已经大于500字，是否取前500字后以‘省略号代替’?"))
            return;
          ctmp=ctmp.substring(0,500)+"……";
    }
    var param={
        courseid:courseid,
        topictitle:title.val().Trim(),
        topiccontent:ctmp
    };
    param.status=$("input[name='rdo_tpc_state']:checked").val();    //状态

    if(!confirm('您确认添加吗?'))
        return;
    $.ajax({
        url:"tptopic?m=doSaveTopic",
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
                /**
                 * 发任务-建论题返回topicid
                 */
                if(operate_type.length>0){
                    if (window.opener != undefined) {
                        //for chrome
                        window.opener.returnValue =rps.objList[0];
                    }
                    else {
                        window.returnValue =rps.objList[0];
                    }
                    window.close();
                }
               if(confirm("新建论题已完成，是否退出该页面?\n\n提示：不退出，可继续创建!"))
            	  location.href='tptopic?m=index&courseid='+courseid;
               else{
            	   title.val('');
            	   content.val('');
               }
            }
        }
    });
}
/**
 删除论题
 */
function deleteTopic(id){
    if(typeof(id)=="undefined"||isNaN(id)){
        alert('异常错误，参数异常!请刷新页面后重试!');
        return;
    }
    if(!confirm('您确认删除该论题吗?'))
        return;
    $.ajax({
        url:"tptopic?m=doDeleteTopic",
        dataType:'json',
        type:"post",
        cache: false,
        data:{topicid:id},
        error:function(){
            alert('异常错误!系统未响应!');
        },success:function(rps){
            if(rps.type=="success")
                pageGo('p1');
            setTimeout(function(){alert(rps.msg)},200);
        }
    });
}










/**
			*论题详情页
			*/
			function validateParamDetail(tobj){
				var param=new Object();
				param.topicid=topicid;
//				var topicnameObj=$("#sel_topicname"); 
//				if(topicnameObj.val().Trim().length>0)
//					param.topicname=topicnameObj.val().Trim();
				if(typeof(selname)!="undefined"
					&&typeof(selvalue)!="undefined"&&selname.Trim().length>0&&selvalue.Trim().length>0)
					eval("(param."+selname+"='"+selvalue+"')");
				if(typeof(ordercol)!="undefined"&&ordercol!=null&&ordercol.Trim().length>0)
					param.ordercol=ordercol;
				if(typeof(ordertype)!="undefined"&&ordertype!=null&&ordertype.Trim().length>0)
					param.ordertype=ordertype;
                if(typeof(pclsid)!="undefined"&&pclsid.length>0&&typeof(pclstype)!="undefined"&&pclstype.length>0){
                    param.clsid=pclsid;
                    param.clstype=pclstype;
                }

				tobj.setPostParams(param);
			} 
			/*论题详情页，加载引用的THEMEID*/
			function validateParamDetail_quote(tobj){
				var param=new Object();
				param.topicid=topicid;
				//引用的COURSE_ID
				if(quotecourseid.length>0)
					param.courseid=quotecourseid;
				//引用的TOPIC_ID
				if(quotetopicid.length>0)
					param.topicid=quotetopicid;
				
				tobj.setPostParams(param);
				
			}
			
			
			
			/**
			 * 论题详情页
			 * @param rps
			 * @return
			 */
			function columnsPageReturn_Detail(rps){
				if(rps.type=="error"){
					alert(rps.msg);return;
				}else{
					var htm='';
					$.each(rps.objList,function(idx,itm){
						htm+='<tr ';
						if(idx%2!=0)						
							htm+='class="trbg1"';
						htm+='><td><p class="one">';
                        if(itm.istop==1)
                            htm+='<b class="ico39"></b>';
						else if(itm.isessence==1)
                            htm+='<b class="ico40"></b>';
						else 
							htm+='<b class="ico38"></b>';
							//if(itm.state==1)
						htm+='<a href="tptopictheme?m=toTopicThemeDetail&themeid='+itm.themeid+'">';
						if(itm.isread==1)
							htm+='<span style="color:gray">';
						if(itm.isread==1)
							htm+='<span style="color:gray">';
						var title=itm.commenttitle25word;
						if(title.Trim().length<1)
							title=itm.themetitle25word;
						htm+=title;
						if(itm.isread==1)
							htm+='</span>';
						
						
						htm+='</a></p></td>';								
						htm+='<td>'+itm.crealname+'</td>';
						htm+='<td>'+itm.ctimeString+'</td>';					
						htm+='<td>'+itm.viewcount+'</td>';  //查看
						htm+='<td>'+itm.pinglunshu+'</td>';   //回复
						var lastfabiao=itm.lastfabiao;
						if(typeof(lastfabiao)=="undefined")
							lastfabiao=''; 
						htm+='<td>'+lastfabiao+'</td>';  //最后发表
//						htm+='<td>';
//						if(itm.istop==1)
//							htm+='<a href="javascript:updateColumns(\''+itm.themeid+'\',\'istop\',\'0\');">取消置顶</a>&nbsp;';
//						else
//							htm+='<a href="javascript:updateColumns(\''+itm.themeid+'\',\'istop\',\'1\');">置顶</a>&nbsp;';
//							
//						if(itm.isessence==1)
//							htm+='<a href="javascript:updateColumns(\''+itm.themeid+'\',\'isessence\',\'0\');">取消精华</a>&nbsp;';
//						else
//						    htm+='<a href="javascript:updateColumns(\''+itm.themeid+'\',\'isessence\',\'1\');">精华</a>&nbsp;';
//						htm+='<a href="javascript:doDelTheme(\''+itm.themeid+'\')">删除</a>&nbsp;';
						htm+='</td>';
						htm+='</tr>';
					}) 
					$("#tbl_body_data").html(htm);	
					//翻页信息
						if (typeof (p1) != "undefined" && typeof (p1) == "object") {
							p1.setPagetotal(rps.presult.pageTotal);
							p1.setRectotal(rps.presult.recTotal);
							p1.setPageSize(rps.presult.pageSize);
							p1.setPageNo(rps.presult.pageNo);	
							p1.Refresh(); 							
						}
				}	
			}
			
			/**
			 * 论题详情页(共享主题)
			 * @param rps
			 * @return
			 */
			function theme2PageReturn_Detail(rps){
				if(rps.type=="error"){
					alert(rps.msg);return;
				}else{
					var htm='';
					$.each(rps.objList,function(idx,itm){						
						htm+='<tr ';
						if(idx%2!=0)						
							htm+='class="trbg1"';
						htm+='><td><p>';
					
							//if(itm.state==1)
						htm+='<a target="_blank" href="tptopictheme?m=toTopicThemeDetail&themeid='+itm.themeid+'">';
						
						var title=itm.commenttitle25word;
						if(title.Trim().length<1)
							title=itm.themetitle25word;
						htm+=title;
						htm+='</a></p></td>';								
						htm+='<td>'+itm.schoolname+'</td>';	
						if(typeof(roleStr)!="undefined"&&roleStr=="TEACHER"){
							if(itm.isquote<1){
								htm+='<td id="td_quote_'+itm.topicid+'">';
								htm+='<a href="javascript:;" onclick="addOrDelQuoteTheme('+topicid+','+itm.topicid+',\'td_quote_'+itm.topicid+'\',1)" title="可见" class="ico46"></a></td>';
							}else
								htm+='<td id="td_quote_'+itm.topicid+'"><a href="javascript:;" onclick="addOrDelQuoteTheme('+topicid+','+itm.topicid+',\'td_quote_'+itm.topicid+'\',2)" title="不可见"  class="ico47"></a></td>';
						}					
						htm+='</tr>';
					})
                    $("#tbl_body_share_data").html(htm);
                    if(htm.Trim().length<1){
                        $("#dv_other_topic").hide();
                    }else
                        $("#dv_other_topic").show();

					//翻页信息
						if (typeof (p2) != "undefined" && typeof (p2) == "object") {
							p2.setPagetotal(rps.presult.pageTotal);
							p2.setRectotal(rps.presult.recTotal);
							p2.setPageSize(rps.presult.pageSize);
							p2.setPageNo(rps.presult.pageNo);	
							p2.Refresh(); 							
						}
				}	
			}
		
			


			
					
//////////////////////////////////////////////////////TOPIC-DETAIL.JSP	
			
			var watchuid='';
			 function columnsFreeOperate(tobj){
					var param=new Object();
					if(watchuid.Trim().length>0)
						param.cuserid=watchuid;
					tobj.setPostParams(param);	
				}
			
				//只看该作者
				function watchByUid(uid,tadd){
					if(uid=="undefined"||uid=="")
						watchuid='';
					else
						watchuid=uid+""; 
					pageGo('p1');
					if(typeof(tadd)!="undefined"&&tadd!=null&&tadd.Trim().length>0){
						if(uid!=""){
							$("#"+tadd).html('<a class="font-lightblue" onclick="watchByUid(\'\',\''+tadd+'\')" href="javascript:;">查看全部作者</a>');
						}else{
							$("#"+tadd).html('<a class="font-lightblue" onclick="watchByUid(\''+authoruserid+'\',\''+tadd+'\')" href="javascript:;">只看该作者</a>');
						}
					}
				}

/**
 * 主题详情页
 * @return
 */
function columnsPageReturn_topicdetail(rps){
	if(rps.type=="error"){
		alert(rps.msg);return;
	}
	//回复
	var htm='';
	if(typeof(rps.objList[0])!="undefined"&&rps.objList[0].length>0){	
		$.each(rps.objList[0],function(idx,itm){
			htm+=' <div class="jxpt_kongjian_zhuti_layout"  id="dv_'+itm.ref+'">';
			htm+=' <div class="jxpt_kongjian_zhuti_layoutL">';
			htm+=' <p>';
			if(typeof(itm.cheadimage)=="undefined"||itm.cheadimage.Trim().length<1)   				
					htm+='<img onerror="headError(this);" onmouseover="getBoardInfo(\''+itm.cuserInfo.ref+'\',\'dv_boardinfo\')" src="adf" width="125" height="125"/>';
				else	   			
   			  htm+='<img  onerror="headError(this);" onmouseover="getBoardInfo(\''+itm.cuserInfo.ref+'\',\'dv_boardinfo\')" src="../'+itm.cheadimage+'" width="125" height="125"/>';	   
			
			
			//htm+='</p><p><a href="javascript:;" class="jxpt_kongjian_ico10 font-lightblue"><span id="realname_restore_'+itm.ref+'">'+itm.restoreuserinfo.realname+'</span></a></p>';
			htm+='</p><p><span id="realname_restore_'+itm.ref+'">'+itm.restoreuserinfo.realname+'</span></p>';
			htm+='</div>';
		    htm+='  <div class="jxpt_kongjian_zhuti_layoutR">';
			htm+='   <div class="jxpt_kongjian_zhuti_layoutRT">';
		   if(roletype&&roletype != 'STUDENT'){ 
				htm+=' <p class="f_right">';
				htm+='  <span><a href="javascript:;" onclick="showPiZhuDiv(\'div_pizhu\',\''+itm.ref+'\',2)" class="jxpt_kongjian_ico08">批注</a></span>';
				htm+='<span><a  href="javascript:;" onclick="deleteRestore(\''+itm.ref+'\',1)" class="jxpt_kongjian_ico09">删除</a></span>';
				htm+='<span class="w45">'+itm.flooridx+'楼</span>';
				htm+='</p>';						 
			}
			htm+='<p><a href="javascript:;" onclick="doYingyong(\''+itm.ref+'\')" class="jxpt_kongjian_ico01">引用</a>';
			htm+='<a href="javascript:;" onclick="showRestoreControl(\''+itm.ref+'\',undefined,\'div_childrestore'+itm.ref+'_1\')" class="jxpt_kongjian_ico05">评论</a>';
			htm+='<a onclick="setOperate(\''+itm.ref+'\',4,1)" href="javascript:;" class="jxpt_kongjian_ico06"><span  id="span_flower_'+itm.ref+'_4">鲜花('+itm.flowercount+')</span></a>';
			htm+='<a onclick="setOperate(\''+itm.ref+'\',4,2)" href="javascript:;" class="jxpt_kongjian_ico07"><span  id="span_jidan_'+itm.ref+'_4">鸡蛋('+itm.agecount+')</span></a>';
			htm+='</p>';
			htm+='<div id="pizhu_'+itm.ref+'_updatecontent" style="display:none" class="pizhu_a_div">';
			if(typeof(itm.updatecontent)!="undefined"&&itm.updatecontent.length>0)					   	 	
			   	 htm+=itm.updatecontent;	
			else
				 htm+=itm.restorecontent;	 
			htm+='</div> '; 
			htm+='</div> ';
			htm+='<div class="jxpt_kongjian_zhuti_layoutRC">';
			htm+='<p class="font-blue">['+itm.flooridx+'楼]发表于：'+itm.ctimeString+'</p>';
			htm+='<p> <div id="pizhu_'+itm.ref+'_updatecontent" class="pizhu_a_div">'; 
			if(typeof(itm.updatecontent)!="undefined"&&itm.updatecontent.length>0)	   	 	
			   	 htm+=itm.updatecontent;	
			else
				 htm+=itm.restorecontent;	
			htm+='</div>';
			htm+='</p>';
			htm+='</div>';
			htm+='<div class="jxpt_kongjian_pl_layout">';
			htm+='<p class="m_lr_10">';			
			htm+='<span class="f_right font-blue" id="sp_children_show_'+itm.ref+'"><a href="javascript:;" onclick="operateRestoreDiv(1,\'p_restore_'+itm.ref+'\',\'sp_children_show_'+itm.ref+'\')">收起</a></span><strong>评论</strong></p>';
			htm+='<div id="p_restore_'+itm.ref +'"></div>';
		    htm+='<div class="t_r p_tb_10" id="div_huifu_dv'+itm.ref+'_2"  style="display:none" >';
			htm+='<p>'; 
			htm+='<textarea name="textarea" class="public_input h80 w730"></textarea>';
			htm+='<input type="hidden" id="hd_chushi_id" value="" name="hd_chushi_id"/>';
			htm+='<input type="hidden" id="hd_chushi_id" value="" name="hd_huifu"/>';
			htm+=' </p>';
		    htm+='<p class="p_t_5"><a  href="javascript:;" onclick="doSubPingLun()"  class="an_darkblue">确&nbsp;定</a>';
			htm+='<a  href="javascript:;" onclick="showRestoreControl(-1,-1,\'div_huifu_dv'+itm.restoreid+'_2\')"  class="an_darkblue">取消</a>';
			htm+='</p>';
			htm+='</div>';
			htm+='<div class="clear"></div>';
			htm+='</div>';
			htm+='<div class="jxpt_kongjian_fbpl"  id="div_childrestore'+itm.ref+'_1" style="display:none" >';
			htm+='<p class="font_strong t_l">发表评论：</p>'; 
			htm+='<p>';
			htm+='<textarea name="textarea3" class="public_input h80 w760 "></textarea>';
			htm+='</p>';
			htm+='<p class="p_t_5 t_r"><a href="javascript:;" onclick="doSubPingLun()"  class="an_darkblue">发&nbsp;表</a></p>';
			htm+='<input type="hidden" id="hd_chushi_id" value="" name="hd_chushi_id"/>';
			htm+='<input type="hidden" id="hd_huifu" value="" name="hd_huifu"/>';
			htm+='</div>'; 
			htm+='<div class="jxpt_kongjian_zhuti_layoutRB">';
			if(typeof(watchuid)!="undefined"&&watchuid.Trim().length>0)
				htm+='<a href="javascript:;" onclick="watchByUid(\'\')" class="font-lightblue">查看全部作者</a>';
			else
				htm+='<a href="javascript:;" onclick="watchByUid(\''+itm.cuserid+'\')" class="font-lightblue">只看该作者</a></div>';
				
			htm+='</div>';
			htm+='<div class="clear"></div>'; 
			htm+='</div>';
		})
	}
		$("#div_restore").html(htm);		
		if(typeof(rps.objList[1])!="undefined"&&rps.objList[1].length>0){
			$.each(rps.objList[1],function(ix,itm){
			htm='<div id="dv_'+itm.ref+'" >';//class="jxpt_kongjian_pl_nr" 
   	 			htm+='<div class="jxpt_kongjian_pl_nrR">';
   	 			htm+=' <p><span class="font-blue">'+itm.restoreuserinfo.realname+'</span>';
   	 			htm+='<input type="hidden" value="'+itm.restoreuserid+'" name="hd_huifu_'+itm.ref+'_hd" name="hd_huifu_'+itm.ref+'_hd"/>';
   				var tousername=itm.tousername; 
   				if(typeof(tousername)!='undefined'&&tousername.Trim().length>0&&tousername.Trim()!="null"){
   					htm+='<span> 回复 </span><span class="font-blue">'+tousername+"</span>说："; 
   				}else
   					htm+="：";
   				htm+=itm.restorecontentString+'</p>';
   				htm+='<p><span class="font-gray1">'+itm.ctimeString+'</span>&nbsp;&nbsp;';
   				htm+='<a href="javascript:;" onclick="showRestoreControl(\''+itm.ref+'\',\''+itm.restoreuserid+'\',\'div_huifu_dv'+itm.restoreid+'_2\')" class="font-lightblue">回复</a>';
   				if(roletype.Trim().length>0&&roletype.Trim()!='STUDENT'){   				
   					htm+='&nbsp;<a href="javascript:;" onclick="javascript:deleteRestore(\''+itm.ref+'\',2)">删除</a>';
   				}
   				htm+='</p></div>';
   				htm+='<div class="jxpt_kongjian_pl_nrL">';
   				if(typeof(itm.cheadimage)=="undefined"||itm.cheadimage.Trim().length<1)   				
   					htm+='<img onerror="headError(this);" src="adf" width="38" height="38" />';
   				else	   			
	   			  htm+='<img  onerror="headError(this);" src="../'+itm.cheadimage+'" width="38" height="38"/>';	   			   			
   				htm+='<div class="clear"></div>';
   				htm+='</div>';   				
   				/*
   				htm+=' <span id="span_flower_${tmp.ref}_5"><a href="javascript:;" onclick="setOperate(${tmp.ref},5,1)">鲜花(${tmp.flowercount})</a></span>&nbsp;<span id="span_jidan_${tmp.ref}_5"><a href="javascript:;" onclick="setOperate(${tmp.ref},5,2)">鸡蛋(${tmp.flowercount})</a></span>';
   				htm+='</p>';*/ 
   				htm+='<div id="p_restore_'+itm.ref+'"></p></div>'; 
   				$("#p_restore_"+itm.restoreid).append(htm); 
			})
		}
	
	//翻页信息
	if (typeof (p1) != "undefined" && typeof (p1) == "object") {
		p1.setPagetotal(rps.presult.pageTotal);
		p1.setRectotal(rps.presult.recTotal);
		p1.setPageSize(rps.presult.pageSize);
		p1.setPageNo(rps.presult.pageNo);	
		p1.Refresh(); 							
	}	
	
	if (typeof (p2) != "undefined" && typeof (p2) == "object") {
		p2.setPagetotal(rps.presult.pageTotal);
		p2.setRectotal(rps.presult.recTotal);
		p2.setPageSize(rps.presult.pageSize);
		p2.setPageNo(rps.presult.pageNo);	
		p2.Refresh(); 
	}
}	
	
function doSubPingLun(strid){
	var id=$("#"+strid+" input[id='hd_chushi_id']").val();
	if(typeof(id)!="undefined"){
		doRestore(id,strid);
	}
}

/**
 * 显示隐藏评论框   (多层级评论框)
 * @param id
 * @return
 */
function showRestoreControl(id,touserid,strid){
	if(state==3){
		alert("该论题已经锁定，无法发布论题及其下的主题进行回复、评论等功能!");return;
	}
	if(typeof(strid)!="undefined"&&strid.Trim().length>0){
	    if(strid.indexOf('div_huifu_dv')!=-1&&$("#"+strid).length<1){
	    	var dvobj=$("#dv_"+id);
	    	if(dvobj.length<1)
	    		dvobj=$("#p_restore_"+id);
	        		
	    	while(true){
	    			var dvflag=false,reflag=false;
	    			if(dvobj.parent().attr("id").indexOf('dv_')!=-1){
	    				dvobj=dvobj.parent();
	    				dvflag=false;
	    			}else
	    				dvflag=true;
	    			
	    			while(true){
		        		if(dvobj.parent().attr("id").indexOf('p_restore_')!=-1){
		        			dvobj=dvobj.parent();
		        			reflag=false;
		        			dvflag=false;
		        		}else{
		        			reflag=true;
		        			break;
		        		}
	    			}
	    			if(dvflag&&reflag)
	    				break;
		        	
	    		}
    		
    		strid=dvobj.parent().children("div[id*='div_huifu_dv']").attr("id");
	    }
		
		var display=$("#"+strid).css("display"); 
		if(display!='block'){
			var h=' <p class="font_strong t_l">发表评论：</p><p>';
			if(typeof(id)!="undefined")
				 h+='<input type="hidden" id="hd_chushi_id" value="'+id+'" name="hd_chushi_id"/>';
			
			if(typeof(touserid)!="undefined") 
			     h+='<input type="hidden" value="'+touserid+'" id="hd_huifu_'+id+'_hd" />';
	        h+='<textarea name="textarea3" class="public_input h80 ';
	        if(strid.indexOf('div_childrestore')!=-1)
	        	h+=' w760 '
	        else
	        	h+=' w730 '
	        h+='"></textarea></p><p class="p_t_5 t_r"><a href="javascript:;" onclick="doSubPingLun(\''+strid+'\')" class="an_darkblue">发&nbsp;表</a>';
	        h+='&nbsp;<a href="javascript:;" onclick="showRestoreControl(-1,-1,\''+strid+'\')"  class="an_darkblue">取消</a></p>';
	    
	        
	        
	        $("#"+strid).html(h);
			$("#"+strid).show();
			//setTimeout(function(){
			$("#"+strid+" textarea").xheditor({tools:'Fontface,Italic,Underline,Strikethrough,Link,Unlink,Emot,List'});
			$("#"+strid+" textarea").focus();
			//},300);
			
			location.href="#"+strid;
		}else
			$("#"+strid).hide();
	}		
	/*
	var tmptouserid='';
	if(typeof(touserid)=='undefined'||isNaN(touserid)){
		if($("div[id='div_childrestore"+id+"']").css("display")=="block")   
			$("div[id='div_childrestore"+id+"']").hide('fast');
		else{	
			var htm='&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;评论：';
			htm+='<textarea style="height:70px" cols="50"';
			htm+=' id="children_restore'+id+'"></textarea>';
			htm+='<input type="button" onclick="doRestore('+id+')" value="确定"/>';
			htm+='<input type="hidden" value="'+touserid+'" id="hd_huifu_'+id+'_hd" />';		
			$("div[id='div_childrestore"+id+"']").html(htm);
			$("div[id='div_childrestore"+id+"']").show('fast');		
			$("div[id='div_childrestore"+id+"'] textarea").xheditor({tools:'Removeformat,|,Link,Unlink,|,Emot'});
			return;
		}
	}
	
	$("div").filter(function(){
		return this.id.indexOf('div_childrestore')==0;		
	}).each(function(idx,itm){
		var isflag=false;		
		$("#"+itm.id).parent().children("div").filter(
				function(){return this.id!=itm.id&&this.id.length>0} 
		).each(function(ix,im){
			
			var len=$("#"+im.id+" div[id='dv_"+id+"']").length;
			if(len>0){ 
				isflag=true;
				return;
			}
		});
		
		if(isflag){
			if($(this).css("display")=="block")   
				$(this).hide('fast');
			else{					
				var htm='&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;评论：';
				htm+='<textarea style="height:70px" cols="50"';
				htm+=' id="children_restore'+id+'"></textarea>';
				htm+='<input type="button" onclick="doRestore('+id+')" value="确定"/>';
				htm+='<input type="hidden" value="'+touserid+'" id="hd_huifu_'+id+'_hd" />';		
				$(this).html(htm);
				$(this).show('fast');		
				$(this).children("textarea").xheditor({tools:'Removeformat,|,Link,Unlink,|,Emot'});  
				return;			
			}			
		}		  
	});*/
}
/**
 * 回复评论
 * @param restoreid
 * @return
 */
function doRestore(restoreid,strid){
	if(state==3){
		alert("该论题已经锁定，无法发布论题及其下的主题进行回复、评论等功能!");return;
	}
	if(typeof(restoreid)=="undefined"||!isNaN(restoreid)){
		alert('异常错误，参数异常!请刷新页面后重试!');
		return;
	}	
	var restoreObj=$("#"+strid+" textarea");
	if(restoreObj.val().Trim().length<1){
		alert("回复内容您尚未输入，请输入后提交!");
		restoreObj.focus();return;
	}
	var touseridObj=$("#"+strid+" input[id='hd_huifu_"+restoreid+"_hd']");
	var touserid='';
	if(touseridObj.length>0)
		touserid=touseridObj.val();
	var param={restoreid:restoreid  
			,restorecontent:restoreObj.val().Trim()
			,type:2};
	if(touserid.length>0&&isNaN(touserid))
		param.touserid=touserid;
	$.ajax({
		url:"../activerestore/doRestoreTopic", 
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
				$.each(rps.objList,function(idx,itm){
					htm+='<div id="dv_'+itm.ref+'">';// class="jxpt_kongjian_pl_nr"
	   	 			htm+='<div class="jxpt_kongjian_pl_nrR">';
	   	 			htm+=' <p><span class="font-blue">'+itm.restoreuserinfo.realname+'</span>';
	   	 			htm+='<input type="hidden" value="'+itm.restoreuserid+'" name="hd_huifu_'+itm.ref+'_hd" name="hd_huifu_'+itm.ref+'_hd"/>';
	   				var tousername=itm.tousername; 
	   				if(typeof(tousername)!="undefined"
	   					&&tousername!=null
	   					&&tousername.Trim().length>0&&tousername.Trim()!="null"){
	   					htm+='<span>&nbsp;回复&nbsp;</span><span class="font-blue">'+tousername+"</span>说：";
	   				}else
	   					htm+="：";
	   				htm+=itm.restorecontent+'</p>';
	   				htm+='<p><span class="font-gray1">'+itm.ctimeString+'</span>&nbsp;&nbsp;';
	   				htm+='<a href="javascript:;" onclick="showRestoreControl(\''+itm.ref+'\',\''+itm.restoreuserid+'\',\'div_huifu_dv'+itm.restoreid+'_2\')" class="font-lightblue">回复</a>';
	   				if(roletype.Trim().length>0&&roletype.Trim()!='STUDENT'){   				
	   					htm+='&nbsp;<a href="javascript:void(\'\');" class="font-lightblue" onclick="javascript:deleteRestore(\''+itm.ref+'\',2)">删除</a>';
	   				} 
	   				htm+='</p></div>';
	   				htm+='<div class="jxpt_kongjian_pl_nrL">';
	   				if(typeof(itm.cheadimage)!="undefined"&&itm.cheadimage.length>0)
	   					htm+='<img src="../'+itm.cheadimage+'" onerror="headError(this)" width="38" height="38" />';
	   				else
	   					htm+='<img src="adsfasfd" onerror="headError(this)" width="38" height="38" />';
	   				
	   				htm+='</div><div class="clear"></div>';
	   				htm+='</div>';	   				
	   				/*
	   				htm+=' <span id="span_flower_${tmp.ref}_5"><a href="javascript:;" onclick="setOperate(${tmp.ref},5,1)">鲜花(${tmp.flowercount})</a></span>&nbsp;<span id="span_jidan_${tmp.ref}_5"><a href="javascript:;" onclick="setOperate(${tmp.ref},5,2)">鸡蛋(${tmp.flowercount})</a></span>';
	   				htm+='</p>';*/ 
	   				htm+='<div id="p_restore_'+itm.ref+'"></p></div>'; 
	   				htm+='<p id="p_restore_'+itm.ref +'"></p>';				
	   				htm+='</div>';			 
				}); 
				
				if($("#p_restore_"+restoreid+">div").length>0)
					$("#p_restore_"+restoreid+">div:last").after(htm);  
				else
					$("#p_restore_"+restoreid).append(htm); 
				showRestoreControl(restoreid,touserid,strid); 
			}
		}
	});	
}
/**
 * 评论删除 
 * @param id
 * @return
 */
function deleteRestore(id,type){
	if(state==3){
		alert("该论题已经锁定，无法发布论题及其下的主题进行回复、评论等功能!");return;
	}
	if(typeof(id)=="undefined"||!isNaN(id)){
		alert('异常错误，参数异常!请刷新页面后重试!');
		return;
	}
	if(typeof(type)=="undefined"||isNaN(type)){
		alert('异常错误，参数异常!请刷新页面后重试!');
		return;
	}
	if(!confirm('确认删除该回复吗?'))
		return;
	$.ajax({
		url:"../activerestore/doDeleteRestore",
		dataType:'json',
		type:"post",  
		cache: false, 
		data:{ref:id			
			,type:type},
		error:function(){
			alert('异常错误!系统未响应!');
		},success:function(rps){
			if(rps.type=="error"){
				alert(rps.msg);
			}else {			
				$("#dv_"+id).hide('fast');
				$("#dv_"+id).remove();
			}
		}
	});	
}
var restoreXheditor;
/**
 * 
 * @param areaid
 * @return
 */ 
function loadTextArea(areaid,pid){
	var htm='<textarea style="font-size:12px;width:600px;height:20px;font-style:italic;color:gray" id="'+areaid+'"';
		htm+=' onfocus="loadTextArea(\''+areaid+'\',\''+pid+'\')"></textarea>';
	$("#p_restoreadd").empty(); 
	$("#p_restoreadd").html(htm);	
	$("#"+pid).show();	
	restoreXheditor=$("#"+areaid).xheditor({tools:'simple'});
}  

function unloadTextAre(areid,pid){ 
	$("#"+pid).hide();
	$("#"+areid).css({'width':'600px','height':'20px','color':'gray','font-style':'italic','font-Color':'gray'});
	$("#"+areid).val('点击文本进行编辑回复!编辑完毕点击‘快速回复’按钮提交!');
	$("#"+areid).xheditor().remove();
}
/**
 * 操作( 鲜花，鸡蛋)
 * @param id  操作的ID
 * @param type	1:鲜花  2：鸡蛋
 * @return
 */
function setOperate(id,functiontype,typeref){
	if(state==3){
		alert("该论题已经锁定，无法发布论题及其下的主题进行回复、评论等功能!");return;
	}
	if(typeof(id)=="undefined"||!isNaN(id)){
		alert('异常错误，参数异常!请刷新页面后重试!');
		return;
	}
	if(typeof(functiontype)=="undefined"||isNaN(functiontype)){
		alert('异常错误，参数异常!请刷新页面后重试!');
		return;
	}
	if(typeof(typeref)=="undefined"||isNaN(typeref)){
		alert('异常错误，参数异常!请刷新页面后重试!');
		return;
	}
	var param={toid:id,functiontype:functiontype,typeref:typeref};
	
	$.ajax({
		url:"../activeoperate/doOperate",
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
				if(typeref==1)
					htm+='鲜花('+rps.objList[0]+')';
				else if(typeref==2)
					htm+='鸡蛋('+rps.objList[0]+')';
				htm+='';
				if(typeref==1)
					$("#span_flower_"+id+"_"+functiontype).html(htm);
				else if(typeref==2)
					$("#span_jidan_"+id+"_"+functiontype).html(htm);				
			}
		}
	});
}

//得到版权或回帖人相关信息
function getBoardInfo(usid,add){
	if(typeof(usid)=="undefined"||!isNaN(usid)){
		alert('异常错误!参数异常! 错误代码：us is empty!');
		return; 
	} 
	//确认进行修改
	$.ajax({ 
		url:'../activetopic/getTopicBoardInfo',
		dataType:'json',
		type:"post",
		cache: false,
		data:{userid:usid},
		error:function(){
			alert('异常错误!系统未响应!');
		},success:function(rps){
			if(rps.type=="error"){
				alert(rps.msg);
			}else {
				if(rps.objList.length>0){
					var h='';
					$.each(rps.objList,function(idx,itm){
						h+='<p style="padding-left:10px;padding-top:10px">姓&nbsp;&nbsp;名：'+itm.REALNAME+'</p>';
						h+='<p style="padding-left:10px;padding-top:10px">用户名：'+itm.USER_NAME+'</p>';
						h+='<p style="padding-left:10px;padding-top:10px">身&nbsp;&nbsp;份：'+itm.ROLENAME+'</p>';
						h+='<p style="padding-left:10px;padding-top:10px">主题数：'+itm.THEMECOUNT+'</p>';
						h+='<p style="padding-left:10px;padding-top:10px">回复数：'+itm.TOPICCOUNT+'</p>';
					})
					$("#"+add).html(h);					
					$("#"+add).css({left:(mousePostion.x-8)+"px",top:(mousePostion.y-8)+"px"});
					$("#"+add).show();
				} 
			}
		}
	});
}		

function operateRestoreDiv(type,operateid,childrenspanid){
 	var h='';
 	if(type==2){
 		$("#"+operateid).show('slow');
 		h+='<a  href="javascript:;" onclick="operateRestoreDiv(1,\''+operateid+'\',\''+childrenspanid+'\')">收起</a>';
 	}else{
 		$("#"+operateid).hide('slow');
 		h+='<a  href="javascript:;" onclick="operateRestoreDiv(2,\''+operateid+'\',\''+childrenspanid+'\')">展开</a>';
 	}
 	$("#"+childrenspanid).html(h);
 }

function listReturn(rps){
	if(rps.type=="error"){
		alert(rps.msg);			
	}else{
	 $("#mainUL").html('');
		 if(rps.objList.length>0){
		
		 var h='';
		 	$.each(rps.objList,function(idx,itm){
                if(itm.status!=2||(itm.status==2&&rt.length>0&&rt.Trim()=="TEACHER")){
                    h+='<div>'
                    h+='<div class="title">';
                    h+='<p class="f_right" style="width:160px">';
                    h+='<a href="tptopic?m=toTopicStatices&topicid='+itm.topicid+'" target="_blank" class="ico37" title="统计"></a>';
                    if(rt.length>0&&rt.Trim()=="TEACHER"){
                        //task?toAddTask&courseid='+courseid+'&tasktype=1&taskvalueid='+itm.resid+'"
                        if(itm.isPublishTask==0)
                            h+='<a href="task?toAddTask&courseid='+courseid+'&tasktype=2&taskvalueid='+itm.topicid+'" class="ico51" title="发任务"></a>';
                        else
                            h+='<span class="ico52" title="已发任务"></span>';
                        if(itm.status==2){
                                h+='<a href="javascript:;" onclick="updateTopicStatus('+itm.topicid+',1)" id="a_status_'+itm.topicid+'" class="ico53" title="开放论题"></a>';
                        }else if(itm.status==1){
                            h+='<a href="javascript:;" onclick="updateTopicStatus('+itm.topicid+',2)" id="a_status_'+itm.topicid+'" class="ico54" title="关闭论题"></a>';
                        }
                        if(itm.isPublishTask==0)
                            h+='<a href="tptopic?m=toAdmin&topicid='+itm.topicid+'&courseid='+itm.courseid+'" class="ico11" title="编辑"></a>';
                        if(itm.isPublishTask==0)
                        h+='<a href="javascript:;" onclick="if(confirm(\'您确定要删除此论题吗?\\n\\n提示：删除该论题后可在回收站里恢复!\')){updateTopicStatus('+itm.topicid+',3);}" class="ico04" title="删除"></a>';
                        else
                            h+='<span class="ico03"  title="不可删除"></a>';
                    }
                    h+='</p>';
                    h+='<p>';
                    if(rt.length>0&&rt.Trim()=="TEACHER"){
                        if(itm.coludstatus==3){
                            h+='<span class="ico17" title="共享"></span>';
                        }else if(itm.coludstatus==4){
                            h+='<span class="ico18" title="标准" ></span>';
                        }
                    }
                    h+='<a class="ico49b" id="a_op_'+itm.topicid+'" onclick="operateUI(\'dv_main_'+itm.topicid+'\',\'ico49\',\'a_op_'+itm.topicid+'\')" href="javascript:;"></a>';
                    h+='<a href="tptopic?m=toDetailTopic&topicid='+itm.topicid+'" target="_blank">'+itm.topictitle+'</a> </p><!-- ico49a -->';
                    h+='</div>';
                    h+='<div id="dv_main_'+itm.topicid+'" class="text" style="display:none">';
                    h+='<div class="f_right">';
                    h+='<p>主帖数：'+itm.themecount+'</p>';
                    h+='<p>评论数：'+itm.restorecount+'</p>';
                    h+='<p>创建人：'+itm.crealname+'</p>';
                    h+='<p>创建时间：'+itm.ctimeShortString+'</p>';
                    h+='</div>';
                    h+='<div class="f_left">';
                    h+='<div class="height">'+itm.topiccontent+'</div>';
                    h+='<p class="info">最后发表：'+(typeof(itm.lastFb)=="undefined"?"无":itm.lastFb)+'</p>';
                    h+='</div>';
                    h+='<div class="clear"></div>';
                    h+='</div>';
                    h+='</div>';
                    if(idx>0&&idx%3==0){
                        $("#mainUL").append(h);
                        h='';
                    }
                }
		 	})
		 	if(h.length>0)
		 		$("#mainUL").append(h);
             $("#mainUL a[id*='a_op_']:first").click();


		 	//翻页信息
		if (typeof (p1) != "undefined" && typeof (p1) == "object") {
			p1.setPagetotal(rps.presult.pageTotal);
			p1.setRectotal(rps.presult.recTotal);
			p1.setPageSize(rps.presult.pageSize);
			p1.setPageNo(rps.presult.pageNo);	
			p1.Refresh();
		}			 
		 }
	}		
}	
/**
* 专题选择
*/
function operateUI(ulid,cls,clsobj){
	var display=$("#"+ulid).css("display");
	if(display=='none')
		showAndHidden(ulid,'show');
	else
		showAndHidden(ulid,'hide');
	if(typeof(cls)!="undefined"&&cls.length>0&&typeof(clsobj)!="undefined"){
		if(display=='none')
			cls+='a';
		else
			cls+='b';
		$("#"+clsobj).attr("className",cls);
	}
}
/**
 * 开放关闭论题
 * @return
 */
function updateTopicStatus(id,val){
	if(typeof(id)=="undefined"||typeof(val)=="undefined"){
		alert('异常错误，参数异常!');return;
	}
	var param={topicid:id};
	param.status=val;
	$.ajax({ 
		url:'tptopic?m=doUpdateTopic',
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
				//修改原图标
				if(val!=3){
					if(val==1){
						$("#a_status_"+id).attr({"className":"ico54","alt":"开放论题"});
					}else if(val==2){
						$("#a_status_"+id).attr({"className":"ico53","alt":"关闭论题"});
					}
					$("#a_status_"+id).removeAttr("onclick");
					$("#a_status_"+id).unbind("click");
					$("#a_status_"+id).bind("click",function(){
						updateTopicStatus(id,(val==1?2:1));
					});
					
				}else{
					pageGo('p1');
					alert("删除成功!\n\n删除的论题会进入回收站，可进入回收站恢复!");
				}
				
			}
		}
	});	
}
/**
 * 将标准共享变为学生可用
 * @param crtpcid 当前的TOPIC_ID
 * @param quoteid 引用的topic_id
 * @param tdid 页面元素中按钮的父节点
 * @param type 1:代表可见操作  2：不可见操作
 * @return
 */
function addOrDelQuoteTheme(crtpcid,quoteid,tdid,type){
	if(typeof(crtpcid)=="undefined"||typeof(quoteid)=="undefined"){
		alert("异常错误，参数异常!");return;
	}
	var param={topicid:crtpcid,quoteid:quoteid,type:type};
	$.ajax({ 
		url:'tptopictheme?m=addOrDelQuoteTheme',
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
				//修改成功
				var h='';
				if(type==2){
					htm+='<a href="javascript:;" onclick="addOrDelQuoteTheme('+topicid+','+itm.topicid+',\'td_quote_'+itm.topicid+'\',1)" title="可见" class="ico46"></a>';
				}else
					htm+='<a href="javascript:;" onclick="addOrDelQuoteTheme('+topicid+','+itm.topicid+',\'td_quote_'+itm.topicid+'\',2)" title="不可见"  class="ico47"></a>';
				$("#"+tdid).html(htm);
			}
		}
	});	
	
}

