<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
		<script type="text/javascript">
		var courseid="${param.courseid}";
		var pCheck;
		$(function(){
            pCheck = new PageControl( {
                post_url : 'tpres?m=ajaxCourseResList',
                page_id : 'pCheck',
                page_control_name : "pCheck",
                post_form : document.pCheckListForm,
                gender_address_id : 'pCheckaddress',
                http_free_operate_handler : preeDoCheckPageSub, //执行查询前操作的内容
                http_operate_handler : getCheckReturnMethod, //执行成功后返回方法
                return_type : 'json', //放回的值类型
                page_no : 1, //当前的页数
                page_size : 10, //当前页面显示的数量
                rectotal : 0, //一共多少
                pagetotal : 1,
                operate_id : "initItemList"
            });
				pageGo("pCheck");
		});



        /**
         * 学生资源审核
         * @param fileSize
         * @returns {*}
         */
        function preeDoCheckPageSub(pObj){
            var param={};
            if(typeof(courseid)!='undefined'&&courseid.length>0)
                param.courseid=courseid;
            param.usertype=1; //1:学生 2：老师
            pObj.setPostParams(param);
        }

        function getCheckReturnMethod(rps){
            var html='';
            if(rps.objList.length>0){
                $.each(rps.objList,function(idx,itm){
                    html+='<tr>';
                    var status='',filename=itm.resname+itm.filesuffixname,lastname=itm.filesuffixname;;
                    if(itm.resstatus=="0"){
                        status='待审核';
                    }else if(itm.resstatus=="1"){
                        status='已通过';
                    }else if(itm.resstatus=="2"){
                        status='未通过';
                    }

                    html+='<td><p>';//<input type="checkbox"  name="ck_resource" value="'+itm.ref+'"/>
                    if(itm.resourseType=="jpeg"){
                        html+='<a href="javascript:previewImg(\'div_show\',\''+itm.md5Id+'\',\'001\',\''+itm.resid+'\');">'+filename+'</a>';
                    }else if(itm.resourseType=="video"){
                        html+='<a href="javascript:loadSWFPlayer(\''+itm.md5Id+'\',\'001\',\'div_show\',true,\''+lastname+'\',\''+lastname+'\',\''+itm.resid+'\');">'+filename+'</a>';
                    }else if(itm.resourseType=="other"){
                        html+='<a href="javascript:showModelOther(\'div_show\',\''+"001"+itm.filesuffixname+'\',\''+itm.resid+'\');">'+filename+'</a>';
                    }else if(itm.resourseType=="mp3"){
                        html+='<a href="javascript:showModeloperateSound(\'div_show\',\''+itm.md5Id+'\',\''+itm.md5mp3file+'\',\'play\',\''+fileSystemIpPort+'\',\''+itm.resid+'\');">'+filename+'</a>';
                    }else if(itm.resourseType=="doc"){
                        html+='<a href="javascript:showModelDoc(\''+itm.swfpath+'\',\'div_show\');">'+filename+'</a>';
                    }else if(itm.resourseType=="swf"){
                        html+='<a href="javascript:swfobjPlayer(\''+itm.md5Id+'\',\'001\',\'div_show\',true,\''+lastname+'\',\'swf\',\'\',\''+itm.resid+'\');">'+filename+'</a>';
                    }
                    html+='</p></td>';
                    html+='<td>'+itm.realname+'</td>';
                    html+='<td>'+itm.ctimeString+'</td>';
                    html+='<td>'+status+'</td>';
                    html+='<td>';
                    if(itm.resstatus=="2"){
                        html+='<a href="javascript:void(0);" title="通过" class="ico02" onclick="doChecnkOneResource(1,\''+itm.resid+'\')"></a>';
                    }else if(itm.resstatus=="0"){
                        html+='<a href="javascript:void(0);" title="通过" class="ico02" onclick="doChecnkOneResource(1,\''+itm.resid+'\')"></a>';
                        html+='<a href="javascript:void(0);" title="不通过" class="ico01" onclick="doChecnkOneResource(2,\''+itm.resid+'\')"></a>';
                    }else if(itm.resstatus=="1"){
                        html+='<a href="javascript:void(0);" title="不通过" class="ico01" onclick="doChecnkOneResource(2,\''+itm.resid+'\')"></a>';
                    }
                    //html+='<a href="javascript:void(0)" onclick="doDelOneResource(\''+itm.ref+'\')">删除</a>';
                    html+='</td>';
                    html+='</tr>';
                });
            }else{
                html+='<tr><td>暂无数据</td></tr>';
            }
            $("#dv_check").show();
            $("tbody[id='initItemList']").html(html);

            if(rps.objList.length>0){
                pCheck.setPagetotal(rps.presult.pageTotal);
                pCheck.setRectotal(rps.presult.recTotal);
                pCheck.setPageSize(rps.presult.pageSize);
                pCheck.setPageNo(rps.presult.pageNo);
            }else
            {
                pCheck.setPagetotal(0);
                pCheck.setRectotal(0);
                pCheck.setPageNo(1);
            }
            $("#sp_totalcount").html(parseInt(rps.presult.recTotal));
            pCheck.Refresh();
        }

		
		function checkAll(obj){
			$("input[name='ck_resource']").attr("checked",$(obj).attr("checked"));
		}

		/**
		*删除
		*/
		function doDelOneResource(ref){
			if(typeof(ref)=="undefined"||ref.length<1){
				alert('异常错误!未获取到资源详情标识!');
				return;
			}
			if(!confirm("您确认删除该资源?")){return;}

			$.ajax({  
				url:'tpres?doDelOneResource',
				type:'post',
				data:{
					ref:ref
				},    
				dataType:'json',	      
				cache: false,
				error:function(){alert('网络异常!')}, 
				success:function(rps){
					if(rps.type=="error"){
						alert(rps.msg);
					}else{
						alert(rps.msg);
					}
				}
			});  
			
		}


		/**
		*删除
		*/
		function doDelAllResource(){
			var ckArray=$("input[name='ck_resource']:checked");
			if(ckArray.length<1){
				alert('请选择资源后进行操作!');
				return;
			}
			var baseidstr='';
			$.each(ckArray,function(idx,itm){
				var baseid=itm.value;
				if(typeof(baseid)!="undefined"&&baseid.length>0){
					if(baseidstr.length>0)
						baseidstr+=",";
					baseidstr+=baseid;
				}
			});
			if(baseidstr.length<1){
				alert('异常错误，未获取到资源标识!');
				return;
			} 
			if(!confirm('确认删除所选资源?')){return;} 
 
			$.ajax({  
				url:'tpres?doDelAllResource',
				type:'post',
				data:{ 
					baseidstr:baseidstr
				},   
				dataType:'json',	    
				cache: false,
				error:function(){alert('网络异常!')}, 
				success:function(rps){
					if(rps.type=="error"){
						alert(rps.msg);
					}else{
						alert(rps.msg);
						pageGo("pList");
					}
				}  
			});
		}

		/**
		*审核
		*/
		function doChecnkOneResource(resstate,ref){
			if(typeof(resstate)=="undefined"||isNaN(resstate)){
				alert('异常错误!未获取到状态标识!');
				return;
			}
			if(typeof(ref)=="undefined"||ref.length<1){
				alert('异常错误!未获取到资源标识!');
				return;
			}		
			if(typeof(courseid)=="undefined"||courseid.length<1){
				alert('异常错误!未获取到课题标识!');
				return;
			}	
			if(!confirm('确认操作?')){return;} 

			$.ajax({  
				url:'tpres?doCheckOneResource',
				type:'post',
				data:{ 
					courseid:courseid,
					ref:ref, 
					resstate:resstate
				},   
				dataType:'json',	    
				cache: false,
				error:function(){alert('网络异常!')}, 
				success:function(rps){
					if(rps.type=="error"){
						alert(rps.msg);
					}else{
						alert(rps.msg);
                        loadCheckPage();
					}
				}
			});  
			
		}
		
		/**
		*审核
		*/
		function doCheckAllResource(resstate){
			if(typeof(resstate)=="undefined"||isNaN(resstate)){
				alert('异常错误!未获取到状态标识!');
				return;
			}	
			if(typeof(courseid)=="undefined"||courseid.length<1){
				alert('异常错误!未获取到课题标识!');
				return;
			}	
			 
			var ckArray=$("input[name='ck_resource']:checked");
			if(ckArray.length<1){
				alert('请选择资源后进行操作!');
				return;
			}    
			var baseidstr='';
			$.each(ckArray,function(idx,itm){
				var baseid=itm.value;
				if(typeof(baseid)!="undefined"&&baseid.length>0){
					if(baseidstr.length>0)
						baseidstr+=",";
					baseidstr+=baseid;
				}
			});
			if(baseidstr.length<1){
				alert('异常错误，未获取到资源标识!');
				return;
			} 
			if(!confirm('确认操作?')){return;} 
 
			$.ajax({  
				url:'tpres?doCheckResource',
				type:'post',
				data:{ 
					courseid:courseid,
					baseidstr:baseidstr,
					resstate:resstate  
				},   
				dataType:'json',	    
				cache: false,
				error:function(){alert('网络异常!')}, 
				success:function(rps){
					if(rps.type=="error"){
						alert(rps.msg);
					}else{
						alert(rps.msg);
						pageGo("pList");
					}
				}  
			});
		}
		</script>
<div class="subpage_lm">
    <!--<p class="f_right w600">您有 <span class="font-blue" id="sp_totalcount"></span> 条资源有待审核。</p>-->
    <ul>
        <li class="crumb"><a >学生资源审核</a></li>
    </ul>
</div>
    <div id="dv_check" >
        <table border="0" cellpadding="0" cellspacing="0" class="public_tab2">
            <col class="w350"/>
            <col class="w80"/>
            <col class="w150"/>
            <col class="w80"/>
            <col class="w90"/>
            <tr>
                <th>资源名称</th>
                <th>上传人</th>
                <th>上传时间</th>
                <th>审核状态</th>
                <th>操作</th>
            </tr>
            <tbody id="initItemList">
            </tbody>
        </table>
        <form id="pCheckListForm" name="pCheckListForm">
            <p class="Mt20" id="pCheckaddress" align="center"></p>
        </form>
    </div>
		 

	<div style="position: absolute; width:660px; height: 510px; z-index: 1005; display: none;" id="swfplayer"> <!--  class="white_content1" -->
<div style="float:right"><a href="javascript:closeVideoPlayer();"><img height="15" border="0" width="15" alt="关闭" src="images/an14.gif"></a></div>
	<div id="div_show"></div>
</div> 
<!-------------------- 屏蔽层 --------------------------------------------------------->		
	<div id="fade" class="black_overlay" style="background:black; filter: alpha(opacity=50); opacity: 0.5; -moz-opacity:0.5;"></div>
