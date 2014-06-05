<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/util/common-jsp/common-jxpt.jsp"%>
<%
	
%>
   
<html> 
	<head>
		<title>${sessionScope.CURRENT_TITLE}</title>
	<style>  
</style> 
		<script type="text/javascript"
			src="js/teachpaltform/resource.js"></script>  
		<script type="text/javascript">
		
		var courseid="${param.courseid}"; 
		$(function(){ 
			if(courseid.length>0)  
				$("#sel_course").val(courseid); 
		});  



		function operateStatus(divid){
			if(typeof(divid)=='undefined'){return;}
			var aobj=$("a[id='a_"+divid+"']");
			var divobj=$("#div_detail_"+divid);
			//隐藏课题
			$("div").filter(function(){return this.id.indexOf('div_detail_')!=-1&&$(this).css("display")!="none"&&this.id!="div_detail_"+divid;}).hide();
			$("a").filter(function(){return this.id.indexOf('a_')!=-1&&$(this).html()=="-";}).html("+");
			if(divobj.css("display")=="none"){            
				divobj.show();          
				aobj.html("-");    
			}else{      
				divobj.hide();
				aobj.html("+");
			}
			getCourseList(divid);
		}
		  
		/**
		*获取专题列表
		*/
		function getCourseList(courseid){
			if(courseid.length<1){
				alert('异常错误，参数错误!请刷新页面重试!');
				return;
			}
			
			$.ajax({   
				url:"tpres?m=ajaxCourseList",
				dataType:'json',
				type:"post",  
				cache: false,   
				data:{
					courseid:courseid
				},
				error:function(){  
					alert('系统未响应!');
				},success:function(rps){
					if(rps.type=="error"){
						alert(rps.msg);
					}else {	
						var htm='';
						if(rps.objList.length>0){
							htm+='<option value="">==请选择==</option>';
							$.each(rps.objList,function(idx,itm){
								htm+='<option value='+itm.courseid+'>'+itm.coursename+'</option>';	
							});
						}  
						$("#sel_course").html(htm);
						$("#res_name").val('');
						$("#res_keyword").val('');
						$("#res_introduce").val('');
						$("#res_remark").val('');
					}   
				}
			});	
		}
		
		/**
		*赋值
		*/
		function assignResValue(resid){
			if(typeof(resid)=="undefined"||resid.length<1){
				alert('异常错误，参数错误!请刷新页面重试!');
				return;
			}
			
			var selcourse=$("#sel_course").val();
			if(selcourse.Trim().length<1){
				alert('请选择课题!');
				return;
			}
			courseid=selcourse;
			
			$("#hd_resid_hd").val(resid);

			$.ajax({ 
				url:"tpres?loadResourceById",
				dataType:'json',
				type:"post",  
				cache: false, 
				data:{
					courseid:courseid,
					resid:resid 	
				},
				error:function(){  
					alert('系统未响应!');
				},success:function(rps){
					if(rps.type=="error"){
						alert(rps.msg);
					}else {			
						if(rps.objList.length>0){
							$.each(rps.objList,function(idx,itm){
								$("#res_name").val(itm.resname);
								$("#res_keyword").val(itm.reskeyword);
								$("#res_introduce").val(itm.resintroduce);
								$("#res_remark").val(itm.resremark);
							});
						}
					}
				}
			});	
		}

		function doTeacherResUpload(){
			var ref=$("#hd_resid_hd").val();
			var selcourse=$("#sel_course").val();
			if(selcourse.Trim().length<1){
				alert('请选择课题!');
				return;
			}
			courseid=selcourse;
			
			if(!ref){  
				alert('请先选择课题资源,再上传\n\n提示：展开我的资源列表，选择资源文件后点击上传!');
				return;   
			}       
			var resname=$("#res_name");
			var reskeyword=$("#res_keyword");
			var resintroduce=$("#res_introduce");
			var resremark=$("#res_remark");
			
			if(resname.val().Trim().length<1){
				alert('请填写资源名称!');
				resname.focus();
				return; 
			}
			
			if(reskeyword.val().Trim().length<1){
				alert('请填写关键字!');
				reskeyword.focus();
				return;
			}
			var param={
				resname:resname.val(),
				reskeyword:reskeyword.val(),
				resintroduce:resintroduce.val(),
				resremark:resremark.val(),
				courseid:courseid,
				usertype:1,
				resid:ref
			}; 
			
			if(!confirm("数据验证完毕!确认提交?")){return;}
			$.ajax({ 
				url:'tpres?doUploadTeacherRes',
				type:'post',  
				data:param,   
				dataType:'json',	    
				cache: false,
				error:function(){alert('网络异常!');}, 
				success:function(rps){
					if(rps.type=="error"){
						alert(rps.msg);
					}else{
						if(!confirm(rps.msg+"点击确认按钮跳转到我的资源页!")){return;}
						location.href='tpres?toTeacherResource&courseid=';//+courseid   
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
    <p class="p_t_10 font14"><strong>上传资源：</strong>
     	<a style="text-decoration: underline;color:blue;"  href="tpres?toLocalUpload&courseid=${param.courseid }">本地上传</a>&nbsp;&nbsp;&nbsp;&nbsp;
    	<a  href="tpres?toTeacherUpload&courseid=${param.courseid }">专题资源共享</a>
    </p>   
    
     <!--隐藏域资源、详细ID -->
				<input type="hidden" id="hd_resid_hd"/>
				 
				  
				 
    <table id="div_upload_control" border="0" cellpadding="0" cellspacing="0" class="public_tab1 m_a_10 zt14 f_right">
      <col class="w100"/>
      <col class="w600"/>
      <tr>
        <th>专题名称：</th> 
        <td>
        	<select id="sel_course">
				<!-- <option value="">==请选择==</option> 
				<c:if test="${!empty courseList}">
					<c:forEach items="${courseList}" var="c">
						<option value="${c.courseid }">${c.coursename }</option> 
					</c:forEach> 
				</c:if> --> 
			</select>   
        </td>
      </tr>
      <tr>
        <th>资源名称：</th>
        <td><input name="res_name" id="res_name" type="text" class="public_input w300" /></td>
      </tr>
      <!-- <tr>
        <th>资源类型：</th>
        <td><select name="select2">
          <option>视频</option>
        </select></td>
      </tr> -->
      <tr>
        <th>关&nbsp;键&nbsp;字：</th>
        <td><input name="res_keyword" maxlength="16" id="res_keyword" type="text" class="public_input w300" /></td>
      </tr>
      <tr>
        <th>资源简介：</th>
        <td><textarea id="res_introduce" class="public_input h90 w450"></textarea></td>
      </tr>
      <tr> 
        <th>备&nbsp;&nbsp;&nbsp;&nbsp;注：</th>
        <td><textarea id="res_remark" class="public_input h90 w450"></textarea></td>
      </tr> 
      <tr>
        <th>&nbsp;</th>
        <td><a href="javascript:void(0);" onclick="doTeacherResUpload()" class="an_blue">上&nbsp;传</a>
        <a href="tpres?toTeacherResource&courseid=${courseid }" class="an_blue">取&nbsp;消</a>
        </td> 
      </tr> 
      <tr>
        <td colspan="2"></td>
      </tr>
    </table>
    <div class="jxpt_zrgl_layoutL">
      <p class="font_strong p_b_10">专题资源库</p>
      <div class="jxpt_zrgl_content">
       <c:if test="${!empty courseList}">
			<c:forEach items="${courseList}" var="c"> 
				<div>
					<p>
						<span>
							<a id="a_${c.courseid }" href="javascript:operateStatus('${c.courseid }');">+</a>&nbsp;
						</span>
						<span>${c.coursename }</span>  
					</p>	
					<div id="div_detail_${c.courseid }" style="display:none;">   
	 						
					</div>			
				</div> 
			</c:forEach>
		</c:if> 
		<c:if test="${empty courseList}">
			<p>
				暂无数据!
			</p>
		</c:if>
      </div>
    </div>
  </div>  
</div>
		
		
		<script type="text/javascript"> 
			 <c:if test="${!empty resList}">
			 	<c:forEach items="${resList}" var="r">
			 		var divdetailobj=$("#div_detail_${r.courseid}");
			 		var htm='';
			 		if(divdetailobj.length>0){
				 		htm+="<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"; 
				 		htm+="<span><a href=\"javascript:assignResValue('${r.resourceid}')\">${r.filename}</a></span>";
				 		htm+='</p>';                            
			 		}         
			 		$(divdetailobj).append(htm);   
			 	</c:forEach>
			 </c:if>
		</script>
		  
		   <%@include file="/util/foot.jsp" %>
	</body>
</html>
