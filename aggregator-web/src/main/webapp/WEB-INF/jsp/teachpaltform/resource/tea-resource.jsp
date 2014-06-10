<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/util/common-jsp/common-jxpt.jsp"%>
      
<html> 
	<head> 
		<title>${sessionScope.CURRENT_TITLE}</title>
	<style>  
</style>  
		<script type="text/javascript"
			src="js/teachpaltform/resource.js"></script>   
		<script type="text/javascript">
		var courseid="${courseid}";     
		var pList;
		$(function(){
				pList = new PageControl( {
					post_url : 'tpres?getTeacherResourceList',
					page_id : 'page1',
					page_control_name : "pList",
					post_form : document.pListForm,
					gender_address_id : 'pListaddress',
					http_free_operate_handler : preeDoPageSub, //执行查询前操作的内容
					http_operate_handler : getInvestReturnMethod, //执行成功后返回方法
					return_type : 'json', //放回的值类型
					page_no : 1, //当前的页数
					page_size : 20, //当前页面显示的数量
					rectotal : 0, //一共多少 
					pagetotal : 1,
					operate_id : "initItemList"
				});  
				pageGo("pList");

			function preeDoPageSub(pObj){
				var param={}; 
				if(typeof(obj)!='object'){
					alert("异常错误，请刷新页面重试!");
					return;
				}
				if(typeof(courseid)!='undefined'&&courseid.length>0)
					param.courseid=courseid;
				pObj.setPostParams(param); 
			} 

			function getInvestReturnMethod(rps){
				var html='';
				html+='<tr>'; 
				html+='<th>专题名称</th>';
				html+='<th>资源名称</th>';
				html+='<th>媒体类型</th>';  
				html+='<th>发布时间</th>';
				html+='<th>点击量</th>';
				html+='<th>好评度</th>';		 
				html+='<th>操作</th>';
				html+='</tr>';
				if(rps.objList.length>0){  
					$.each(rps.objList,function(idx,itm){  
						html+='<tr>';
						html+='<td><p>'+itm.coursename+'</p></td>';
						html+='<td><p>';

                        var lastname=itm.filename.substring(itm.filename.indexOf("."));

						if(itm.resourseType=="jpeg"){
							html+='<a href="javascript:previewImg(\'div_show\',\''+itm.md5Id+'\',\''+itm.md5filename+'\');">'+itm.filename+'</a>';
						}else if(itm.resourseType=="video"){
							html+='<a href="javascript:loadSWFPlayer(\''+itm.md5Id+'\',\''+itm.md5filename+'\',\'div_show\',true,\''+lastname+'\');">'+itm.filename+'</a>';
						}else if(itm.resourseType=="other"){  
							html+='<a href="javascript:showModelOther(\'div_show\',\''+itm.filename+'\',\''+itm.resourceid+'\');">'+itm.filename+'</a>';
						}else if(itm.resourseType=="mp3"){ 
							html+='<a href="javascript:showModeloperateSound(\'div_show\',\''+itm.md5Id+'\',\''+itm.md5mp3file+'\',\'play\',\''+fileSystemIpPort+'\');">'+itm.filename+'</a>';  
						}else if(itm.resourseType=="doc"){ 
							html+='<a href="javascript:showModelDoc(\''+itm.swfpath+'\',\'div_show\');">'+itm.filename+'</a>';
						}else if(itm.resourseType=="swf"){
                            html+='<a href="javascript:swfobjPlayer(\''+itm.md5Id+'\',\''+itm.md5filename+lastname+'\',\'div_show\',true,\''+lastname+'\',\'swf\');">'+itm.filename+'</a>';
                        }
						html+='</p></td>';    
						html+='<td>';    
						if(itm.resourseType=="jpeg"){
							html+='图片';
						}else if(itm.resourseType=="video"){
							html+='视频';
						}else if(itm.resourseType=="doc"){
							html+='文档';
						}else if(itm.resourseType=="mp3"){ 
							html+='音频';  
						}else if(itm.resourseType=="swf"){
							html+='动画';
						}else if(itm.resourseType=="other"){
                            html+='其他';
                        }
                        html+='</td>';
						html+='<td>'+itm.ctimeString+'</td>'; 
						html+='<td>'+itm.viewcount+'</td>'; 
						html+='<td>'+itm.rankscore+'</td>';  
						html+='<td>';
						if(coursestate.length>0&&coursestate!="0")
							html+='<a href="javascript:void(0)" onclick="doDelOneResource(\''+itm.ref+'\')">删除</a>';  
						html+='</td>';      
						html+='</tr>';
					});          
				}else{    
					html+='<tr><td>暂无数据</td></tr>';       
				}   
				$("tbody[id='initItemList']").html(html); 
				 
				if(rps.objList.length>0){
					pList.setPagetotal(rps.presult.pageTotal);
					pList.setRectotal(rps.presult.recTotal);
					pList.setPageSize(rps.presult.pageSize);
					pList.setPageNo(rps.presult.pageNo);
				}else
				{
					pList.setPagetotal(0);   
					pList.setRectotal(0);	 	
					pList.setPageNo(1);   
				}
				pList.Refresh();
			}
		});
 
		
		/**
		*删除
		*/
		function doDelOneResource(ref){
			if(typeof(ref)=="undefined"||ref.length<1){
				alert('异常错误!未获取到资源详情标识!');
				return;  
			}
			if(!confirm("您确认删除该资源?\n\n提示：请谨慎操作,资源删除后与之关联的任务也会失效!")){return;}

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
						pageGo("pList");
					}  
				}
			});  
			
		}  

		</script>   
		 
	   
	</head> 
	<body> 
		<%@include file="/util/head.jsp" %>
		<div class="jxpt_layout">
		 <%@include file="/util/nav.jsp" %>
  <p class="jxpt_icon03">资源管理</p>
  <div class="public_ejlable_layout">
    <ul>
      <li class="crumb"><a href="tpres?toTeacherResource&courseid=${param.courseid }">我的资源</a></li>
      <li><a href="tpres?toResourceCheckList&courseid=${param.courseid }">学生资源</a></li>
    </ul>   
  </div> 
  <div class="jxpt_content_layout">
    <!--<p class="p_t_10 f_right"><strong>筛选课题：</strong>
<select name="select2" id="select2">
  <option>好好学习天天向上吧</option>   
  <option>第二小组</option>
  <option>第三小组</option>
</select>
    </p>-->
     <p class="p_t_10 font14" id="a_res_upload">  
     <strong>上传资源：</strong>  
      	<a style="text-decoration: underline;color:blue;" href="tpres?toLocalUpload&courseid=${courseid }">本地上传</a>&nbsp;&nbsp;&nbsp;&nbsp;
    	<a href="tpres?toTeacherUpload&courseid=${courseid }">专题资源共享</a>
    </p>     
    <table  border="0" cellpadding="0" cellspacing="0" class="public_tab2 m_t_10">
	     <colgroup span="2" class="w220"></colgroup>
	     <colgroup class="w120"></colgroup>
		 <colgroup class="w130"></colgroup>
		 <colgroup span="2" class="w90"></colgroup>
		 <colgroup class="w100"></colgroup>
		 <tbody id="initItemList">
		 	
		 </tbody>  
    </table>
    <form id="pListForm" name="pListForm">
			<p class="Mt20" id="pListaddress" align="center"></p>
	</form> 
  </div>
</div>
	   <%@include file="/util/foot.jsp" %>
	</body>
	
	<div style="position: absolute; width:660px; height: 510px; z-index: 1005; display: none;" id="swfplayer"> <!--  class="white_content1" -->
<div style="float:right"><a href="javascript:closeVideoPlayer();"><img height="15" border="0" width="15" alt="关闭" src="images/an14.gif"></a></div>
	<div id="div_show"></div>
</div> 
<!-------------------- 屏蔽层 --------------------------------------------------------->		
	<div id="fade" class="black_overlay" style="background:black; filter: alpha(opacity=50); opacity: 0.5; -moz-opacity:0.5;"></div>
</html>
