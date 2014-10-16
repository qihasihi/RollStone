<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/util/common-jsp/common-zrxt.jsp" %>
<html  xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>My JSP 'check.jsp' starting page</title>
    <script type="text/javascript">
    	var byuser2selectedColor='#AE7373',rgbbyu2setedColor="rgb(174, 115, 115)";
    	$(function(){
    		var sbid="${param.subjectid}";
    		if(sbid.Trim().length>0){
    			shuDTreeClick(sbid,'sel_result');
    		}
    	})
    	function search_teacher(searchId,searchReId,type){
    		var tname=$("#"+searchId);
    		if(tname.val().Trim().length<1){
    			alert('请输入您要查询的教师名称，再点击查询!');
    			tname.focus();
    			return;
    		}
	    	$.ajax({
				url:'teacher?m=getTeacher',
				type:'post', 
				data:{realname:tname.val().Trim()},
				dataType:'json',
				error:function(){alert("网络异常!")},  
				success:function(rps){
					if(rps.type=='error'){
						alert(rps.msg);return;
					}
					if(rps.objList.length>0){
						var opts='';
						$.each(rps.objList,function(idx,itm){
							opts+='<li ';
							if(idx%2==0)
								opts+=' class="bg"';
							opts+='>';							
							var click="optsChange(\'sel_searchresult\',\'sel_result\',this.parentNode,1)";
							if(typeof(type)!="undefined"&&type==1)click="getShuTree(\'sel_searchresult_2\',this.parentNode)";
							opts+='<img style="cursor: pointer;" width="12" onclick="'+click+'" height="13" src="images/an31_130705.png">';
							opts+='<input type="hidden" value="'+itm.userid+'"/><span>'+itm.teachername+'</span></li>';					
						});				
						$("#"+searchReId).html(opts); 
					}
				}
			});
    	}
    	/**
    	* 更换Opts
    	*/
    	function optsChange(firstOptsId,lastOptsId,selthisObj,type){
    		var selimgLength=$("li[id='"+selthisObj+"'] img");
    		if(selimgLength.length>0){    			
    			return;
    		}
    		var suImg='<img style="cursor: pointer;" onclick="optsChange(\'sel_searchresult\',\'sel_result\',this.parentNode,1)" width="12" height="13" src="images/an31_130705.png">';
    		var erImg='<img style="cursor: pointer;" onclick="optsChange(\'sel_result\',\'sel_searchresult\',this.parentNode,2)" width="12" height="13" src="images/an32_130705.png">';
    	    if(typeof(selthisObj)=="undefined")
    	   		 return;	
    	    
    		var selOpts=$(selthisObj);
    		if(selOpts.length<1){
    			return;
    		}
    		var opts=$("#"+lastOptsId+" input[type='hidden']");
    		var h='';
    		selOpts.each(function(idx,itm){
    			var flag=true;
    			if(opts.length>0){
    				opts.each(function(ix,im){
    					if(im.value.Trim()==$(itm).children("input[type='hidden']").val().Trim())
    						flag=false;
    				})
    			}
    			if(flag){
	    			h+='<li'
	    			if(opts.length%2!=0)
	    			h+=' class="bg"'
	    			h+='>';
	    			if(type==1)
	    				h+=erImg;
	    			else 
	    				h+=suImg;    				
	    			h+='<input type="hidden" value="'+$(itm).children("input[type='hidden']").val()+'"/><span>'+$(itm).children("span").html()+'<span></li>';
    			}
    		});
    		//转移
    		$("#"+lastOptsId).append(h);
    		//清除
    		selOpts.remove();
    	}
    	/**
    	*以树形进行分配。
    	*/
    	function shuDTreeClick(ref,lastOptsId){
    		if(typeof(ref)=="undefined"||ref.Trim().length<1){
    			alert('异常错误，树形异常!');return;
    		}
    		//将树形的REF传入 
    		$("#hd_shu_id").val(ref);
    		$("#shu .dtree a").css("color","black");
    		$("#shu div[id='"+ref+"'] a").css("color","red");
    		//查询已有的教师验证
    		$.ajax({
				url:'check?m=getcheckteacher',
				type:'post', 
				data:{valueid:ref},
				dataType:'json',
				error:function(){alert("网络异常!")},  
				success:function(rps){
					if(rps.type=='error'){
						alert(rps.msg);return;
					}
					var opts='';
					$.each(rps.objList,function(idx,itm){
							opts+='<li >';
							opts+='<img style="cursor: pointer;" width="12" onclick="optsChange(\'sel_result\',\'sel_searchresult\',this.parentNode,2)" height="13" src="images/an32_130705.png">';
							opts+='<input type="hidden" value="'+itm.userid+'"/><span>'+itm.realname+'</span></li>';					
					});
					$("#"+lastOptsId).html(opts);					
					$("#div_shu_tname").show('fast'); 
				}
			});
    	}
    	/*提交以树形 Check */
    	function doSubShuChecked(){
    		var val=$("#hd_shu_id").val();
    		if(typeof(val)=="undefined"||val.Trim().length<1){
    			alert('异常错误，节点没有检测到您选择!请选择!');return;
    		}
    		var u='';
    		var opts=$("#sel_result li input[type='hidden']");
    		if(opts.length<1){
    			if(!confirm('您清空了该节点的审核权限，可能导致该节点的资源没无法进行审核!\n\n您确认此操作吗?'))
    			return;
    		}else{    		 
	    		opts.each(function(idx,itm){
	    			if(u.length>0)
	    				u+=','
	    			u+=itm.value;
	    		});
    		}
    		var param={valueid:val,userArrayId:u};
    		$.ajax({
    			url:'check?m=doAddCheck',
				type:'post', 
				data:param,
				dataType:'json',
				error:function(){alert("网络异常!")},  
				success:function(rps){
					if(rps.type=='error'){
						alert(rps.msg);return;
					}
					alert(rps.msg); 
				}
    		});
    	}
    	
    	
    	/**
    	*以教师进行分配。
    	*/
    	function shuDTreeClick_2(ref,lastOptsId){
    		if(typeof(ref)!="undefined"&&ref.Trim().length>0){    		
	    		//将树形的REF传入 
	  //  		$("#hd_shu_id").val(ref);
	//    		$("#shu .dtree a").css("color","black");
	    		var treeObj=$("#dv_shu_2 div[id='"+ref+"'] a:last");
	    		var color=treeObj.css("color");
	    		if(color=="rgb(255, 0, 0)")
	    			$("#dv_shu_2 div[id='"+ref+"'] a").css("color","#000000");
	    		else
	    			$("#dv_shu_2 div[id='"+ref+"'] a").css("color","#FF0000");
    		}
    		    			
    		var valueParentObj=$("#dv_shu_2 a").filter(function(){
    			return (this.style.color=='red'||this.style.color=='rgb(255, 0, 0)'||this.style.color=='#FF0000')
    		});
    		var h='';
    		valueParentObj.each(function(idx,itm){
    			h+="<li"
				if(idx%2==0)
					h+=' class="bg"';
				h+='>';				
				h+='<img style="cursor: pointer;" width="12" onclick="shuDTreeClick_2(\'';
				h+=$(itm).children("input[type='hidden']").val()+'\',\'sel_result_2\')" height="13" src="images/an32_130705.png">';				
				h+=$(itm).html()+"</li>";							
    		})	
    		$("#ul_result_1").html(h);
    	}	
    	/**
    	*得到树节点
    	*/
    	function getShuTree(selId,liObj){
    		if(typeof(selId)=="undefined"||selId.Trim().length<1){
    			alert('异常错误，树形异常!');return;
    		}
    		var sel=$(liObj).children("input[type='hidden']");
    		if(sel.length<1){
    			return;
    		}
    		$.ajax({
    			url:'check?m=getNodeByUserid',
				type:'post', 
				data:{userid:sel.val()},
				dataType:'json',
				error:function(){alert("网络异常!")},  
				success:function(rps){
					if(rps.type=='error'){
						alert(rps.msg);return;
					}
					var h='';
					$("#dv_shu_2 .dtree a").css("color","black");	
					if(rps.objList.length>0){											
						$.each(rps.objList,function(idx,itm){
							$("#dv_shu_2 input[value='"+itm.valueid+"']").parent().css("color","red");
							h+="<li";
							if(idx%2==0)
							h+=' class="bg"';
							h+='>';
							h+='<img style="cursor: pointer;" width="12" onclick="shuDTreeClick_2(\'';
							h+=itm.valueid+'\',\'sel_result_2\')" height="13" src="images/an32_130705.png">';
							h+=$("#dv_shu_2 input[value='"+itm.valueid+"']").parent().html()+"</li>";
						});
					}
					$("#ul_result_1").html(h);
					//用户
 					$("#sp_currentUser").html($(liObj).children("span").html()+"<input type='hidden' value='"+sel.val()+"'/>");
				}
    		});
    	}
    	
    	/**
    	*根据用户ID保存审核节点
    	*/
    	function doSubCheckByUser(){
    		var opts=$("span[id='sp_currentUser'] input[type='hidden']")
    		if(opts.length<1){
    			alert('请搜索教师后，在输入框下方的选择项中双击选择您要分配的教师!再点击右侧的树形，进行选择!');
    			return;
    		}
    		var valueid=$("#ul_result_1 li input[type='hidden']");	
    		/*if(valueid.length<1){
    			alert('错误，您尚未选择节点，请选择后再操作!');return;
    		}
    		if(valueid.val().Trim().length<1){
    			if(!confirm('您清空了该节点的审核权限，可能导致该节点的资源没无法进行审核!\n\n您确认此操作吗?'))
    			return;
    		}*/
    		var valArrId='';
    		$.each(valueid,function(idx,itm){
    			if(itm.value.Trim().length>0){
    				if(valArrId.Trim().length>0)
    					valArrId+=',';
    				valArrId+=itm.value.Trim();
    			}
    		});
    		if(valArrId.Trim().length<1){
    			if(!confirm('您清空了该节点的审核权限，可能导致该节点的资源没无法进行审核!\n\n您确认此操作吗?'))
    			 return;
    		}
    		var param={valueid:valArrId.Trim(),userid:opts.val().Trim()};
    		$.ajax({
    			url:'check?m=doAddCheck',
				type:'post', 
				data:param,
				dataType:'json',
				error:function(){alert("网络异常!")},  
				success:function(rps){
					if(rps.type=='error'){
						alert(rps.msg);return;
					}
					$("#dv_shu_2 .dtree a").css("color","black");
					alert(rps.msg);
				}
    		});    		
    	}    	
    	//选项卡进行切换
    	function changeCheckedModel(id){
    		$("#title_ul li").removeAttr("class");
    		if(id=="checkedbyuser"){
    			$("#"+id).show();
    			$("#checkedbyshu").hide();
    			$("#li_title_byuser").attr("class","crumb");
    			$("#dv_shu_2 .dtree a").css("color","black");
    			d1.openAll();
    		}else{
    			$("#"+id).show();
    			$("#checkedbyuser").hide();
    			$("#li_title_byshu").attr("class","crumb");    			
    		}
    	}    
    	/**
    	清除权限用户
    	*/
    	function clearResultUlChildren(clearUlId){
    		if(confirm("你确认清空当前指定的权限用户吗?"))
    			$("#"+clearUlId +" li").remove();
    	}	
    </script>
  </head>  
  <body>     
   <%@include file="/util/head.jsp" %>      
   <div class="jxpt_layout">
  <%@include file="/util/nav.jsp" %>
  <p class="jxpt_icon01">审核权限管理</p>
  <div class="public_ejlable_layout">
    <ul id="title_ul">
      <li class="crumb" id="li_title_byshu"><a href="javascript:;" onclick="changeCheckedModel('checkedbyshu')">按学科年级</a></li>
      <li  id="li_title_byuser"><a href="javascript:;" onclick="changeCheckedModel('checkedbyuser')">按用户</a></li>
    </ul>
  </div>
  <div id="checkedbyshu"> 
  <div class="jxpt_ziyuan_shqx">
    <div class="left">
      <p class="t_c p_tb_10"><a href="javascript:;" onclick="d.openAll()"><img src="images/an34_130705.png" title="展开树" width="76" height="27" /></a>&nbsp;&nbsp;&nbsp;&nbsp;<a  href="javascript:;" onclick="d.cll()"><img src="images/an35_130705.png" title="关闭树" width="76" height="27" /></a></p>
      <div class="auto" id="shu">
      <script type="text/javascript">			
				d = new dTree('d'); 
				d.add('0','-1','审核树','javascript:;');		
				<%/*<c:if test="${!empty extendList}">						
					<c:forEach items="${extendList}" var="cktmp">
						d.add('${cktmp.extendid}','${cktmp.dependextendid}' 
						,'${cktmp.extendname}','javascript:shuDTreeClick(\'${cktmp.extendid}\',\'sel_result\')'); 
					</c:forEach>
				</c:if>*/
				%>		
				<c:if test="${!empty extendValList}">						
					<c:forEach items="${extendValList}" var="etmp">
						d.add('${etmp["VALUE_ID"]}','${etmp["EXTEND_ID"]}' 
						,'${etmp["VALUE_NAME"]}','javascript:shuDTreeClick(\'${etmp["VALUE_ID"]}\',\'sel_result\')'); 
					</c:forEach>
				</c:if>
				document.write(d);		
			</script>      
    </div>
    </div>     
    <div class="right">
    <div class="two"  id="div_shu_tname">
    <p><strong>当前查询结果：</strong></p>
    <p>
        <input  name="search_tname" id="search_tname" type="text" class="public_input w160" />
      <a onclick="search_teacher('search_tname','sel_searchresult')" href="javascript:;"><img src="images/an30_130705.png" width="22" height="20" align="middle" /></a>
      </p>
      <div class="content">
        <ul id="sel_searchresult">         
        </ul>
      </div>
        <input type="hidden" value="" id="hd_shu_id"/>
    </div>
    <div class="two">
    <p><strong>已指定权限用户：</strong></p>
    <p class="t_r"><a href="javascript:clearResultUlChildren('sel_result')">清空</a></p>
      <div class="content">
        <ul id="sel_result">          
        </ul>
      </div>      		    
    </div>
    <p class="t_c clearit p_t_20"><a class="an_blue" onclick="doSubShuChecked()">确定</a><a class="an_blue" href="javascript:if(confirm('您确认重置吗?')){var sbid=document.getElementById('hd_shu_id').value;location.href='check?m=tocheckPage&subjectid='+sbid;}">重置</a></p>
    </div>
  </div>
</div>
<div id="checkedbyuser" style="display:none">
  <div class="jxpt_ziyuan_shqx">
    <div class="left">      
      <p><strong>教师姓名：</strong>
      <input  name="search_tname1" id="search_tname1" type="text" class="public_input w120" /></p>
      <p class="t_c p_t_10"><a class="an_blue_small" href="javascript:;" onclick="search_teacher('search_tname1','sel_searchresult_2',1)">确定</a></p>
      <p class="p_t_20"><strong>查询结果：</strong></p>
      <div class="content">
        <ul id="sel_searchresult_2">          
        </ul>
      </div>
    </div>
    
    <div class="right">
    <p><strong class="font14 font-purple p_l_10">当前用户：<span id="sp_currentUser"></span></strong></p>
    <h5></h5>
    <div class="two">
     <p><a href="javascript:;" onclick="d1.openAll()"><img src="images/an34_130705.png" title="展开树" width="76" height="27" /></a>&nbsp;&nbsp;&nbsp;&nbsp;<a  href="javascript:;" onclick="d1.closeAll()"><img src="images/an35_130705.png" title="关闭树" width="76" height="27" /></a></p>
      <div class="overflow" id="dv_shu_2">
      <script type="text/javascript">
				d1 = new dTree('d1');
				d1.add('0','-1','审核树','javascript:;');					
				<c:if test="${!empty extendValList}">					
					<c:forEach items="${extendValList}" var="etmp">
						d1.add('${etmp["VALUE_ID"]}','${etmp["EXTEND_ID"]}' 
						,'${etmp["VALUE_NAME"]}<input type="hidden" value="${etmp["VALUE_ID"]}"/>','javascript:shuDTreeClick_2(\'${etmp["VALUE_ID"]}\',\'sel_result_2\')'); 
					</c:forEach>
				</c:if>
				document.write(d1);		
			</script> 
      </div>
    </div>  
    <div class="two">
    <p><strong>已指定用户：</strong></p>
      <div class="content">
        <ul id="ul_result_1"> 
        </ul>
      </div>
    </div>
     <p class="t_c clearit"><a class="an_blue" href="javascript:;" onclick="doSubCheckByUser()">应用</a></p>
    </div>
  
  </div>
  </div>
    <div class="clear"></div></div>
        <%@include file="/util/foot.jsp" %>      	
  </body>
</html>
