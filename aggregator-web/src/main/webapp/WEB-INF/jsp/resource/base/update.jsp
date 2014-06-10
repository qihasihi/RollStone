<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/util/common-jsp/common-zrxt.jsp"%>
<html>
	<head>
		<title>资源上传</title>
		<script type="text/javascript" src="js/resource/resbase.js"></script>
		<script type="text/javascript" src="js/common/uploadControl.js"></script>
		<!-- 上传控件 -->
		<script type="text/javascript"
			src="util/uploadControl/js/jquery-1.9.1.js"></script>
		<script type="text/javascript"
			src="util/uploadControl/js/jquery-migrate-1.1.1.min.js"></script>
		<script type="text/javascript"
			src="util/uploadControl/js/jquery.json-2.4.js"></script>
		<script type="text/javascript"
			src="util/uploadControl/js/jquery-ui-1.10.2.custom.min.js"></script>
		<script type="text/javascript"
			src="util/uploadControl/js/knockout-2.2.1.js"></script>
		<script type="text/javascript">
    	var lb;
    	var exvaluecount="${extendcount}";
    	$(function(){
    		
    		//设置默认值
    		var hdposition=$("input[name='hd_posisions']");
    		var hdpostr="";
    		$.each(hdposition,function(idx,itm){
    			if(hdpostr.Trim().length>0)
    				hdpostr+="|";
    			hdpostr+=itm.value.Trim();
    		});
    		lb=new LeibieControl({controlid:'lb',addressid:'leibel_${param.resid}'},false,hdpostr);
    		    		
    		//var url="<%=fileSystemIpPort%>upload1.jsp?jsessionid=aaatCu3yQxMmN-Rru135t&res_id=${param.resid}";    		
    		//InitUpload(url,false);     		
    		
    		//加载文件
    		/*<c:if test="${!empty reFileInfoList}">
		    	<c:forEach items="${reFileInfoList}" var="rftmp">
		    		if(typeof(uploadControl.fileAttribute)!='undefined'){
		    			var fname="${rftmp.filename}";
		    			var fsize=${rftmp.filesize};
		    			uploadControl.fileAttribute[uploadControl.fileAttribute.length]={filename:fname,size:fsize}
		    		}
		    	</c:forEach>
		    	if(typeof(uploadControl.ismultiple)!="undefined"&&!uploadControl.ismultiple)//隐藏上传按钮。
					$("#btnUpload").hide();
				else
					$("#btnUpload").show();
    		</c:if>*/
    		//浏览权限
    		var rightviewroletype="${resinfo.rightviewroletype}";
    		if(rightviewroletype.Trim().length>0){
	    		document.getElementById('ck_right_view${param.resid}_all').checked=true;
	    		//ckChild(document.getElementById('ck_right_view_all'),'ck_right_view',0);
	    		$("#ck_right_view${param.resid}_${resinfo.rightviewroletype}")[0].checked=true;
	    		var h='';
	    		<c:if test="${!empty reviewright}">
	    			<c:forEach items="${reviewright}" var="review">
	    				if(rightviewroletype=="2"){
	    					h='${review.rightsubject}';
	    				}else{
							h+='<span id="sp_p_right_view${param.resid}_teacher_${review.rightuserref}"><font>${review.realname}</font>';
							h+='<input type="hidden" value="${review.rightuserid}">';
							h+='<a href="javascript:delAppoint(\'sp_p_right_view${param.resid}_teacher_${review.rightuserref}\');" style="color:gray">[X]</a></span>&nbsp;';
						}
	    			</c:forEach>
	    		</c:if>
	    		if(h.Trim().length>0){
	    			if(rightviewroletype=="2"){
	    				$("p[id='p_right_view${param.resid}_subject'] select option[value='"+h+"']").attr("selected",false);
	    				$("p[id='p_right_view${param.resid}_subject'] select option[value='"+h+"']").attr("selected",true);
						$("p[id='p_right_view${param.resid}_subject']").show();    				
	    			}else if(rightviewroletype=="3"){
	    				$("p[id='p_right_view${param.resid}_teacher']").html(h);
	    				$("p[id='p_right_view${param.resid}_teacher']").show();
	    			}
	    		}   		    		
    		}    		
    		h='';
    		//下载权限
    		var rightdownroletype="${resinfo.rightdownroletype}";
    		if(rightdownroletype.Trim().length>0){
	    		document.getElementById('ck_right_down${param.resid}_all').checked=true;
	    		//ckChild(document.getElementById('ck_right_down_all'),'ck_right_down',0);
	    		$("#ck_right_down${param.resid}_${resinfo.rightdownroletype}")[0].checked=true;
	    		var h='';
	    		<c:if test="${!empty redownright}">
	    			<c:forEach items="${redownright}" var="redown">
	    				if(rightdownroletype=="2"){
	    					h='${redown.rightsubject}';
	    				}else{
							h+='<span id="sp_p_right_down${param.resid}_teacher_${redown.rightuserref}"><font>${redown.realname}</font>';
							h+='<input type="hidden" value="${redown.rightuserid}">';
							h+='<a href="javascript:delAppoint(\'sp_p_right_down${param.resid}_teacher_${redown.rightuserref}\');" style="color:gray">[X]</a></span>&nbsp;';
						}
	    			</c:forEach>
	    		</c:if>
	    		if(h.Trim().length>0){
	    			if(rightdownroletype=="2"){
	    				$("p[id='p_right_down${param.resid}_subject'] select option[value='"+h+"']").attr("selected",false);
	    				$("p[id='p_right_down${param.resid}_subject'] select option[value='"+h+"']").attr("selected",true);
	    				$("p[id='p_right_down${param.resid}_subject']").show();
	    			}else if(rightdownroletype=="3"){
	    				$("p[id='p_right_down${param.resid}_teacher']").html(h);
	    				$("p[id='p_right_down${param.resid}_teacher']").show();
	    					  
	    			}
	    				
	    		}   	
    		}    		
    	})
    	function ziyuanHasCannel(fname,idx){
    		if(typeof(uploadControl.fileAttribute)!='undefined'&&uploadControl.fileAttribute.length>0){				
					$.each(uploadControl.fileAttribute,function(ixd,imd){
						if(imd.filename==fname){
							uploadControl.fileAttribute[ixd]=undefined;
							$("#divUploadInfo"+idx).remove(); 
							return;
						}
					})
				}
				if(typeof(uploadControl.ismultiple)!="undefined"&&!uploadControl.ismultiple)
					$("#btnUpload").show();
    	}
    	//权限设置 联动     
    	function ckChild(ck,childname,idx){
    		if(typeof(childname)!="undefined"&&$("input[name='"+childname+"']").length>0&&ck.checked)
    			$("input[name='"+childname+"']")[idx].checked=ck.checked;
    		else
	    		$("input[name='"+childname+"']").each(function(idx,itm){
	    			itm.checked=ck.checked;
	    		})
    		if(ck.id=="ck_right_down${param.resid}_all"&&ck.checked){
    			document.getElementById('ck_right_view${param.resid}_all').checked=ck.checked;
    			ckChild(document.getElementById('ck_right_view${param.resid}_all'),"ck_right_view",idx);
    		}
    	}
    	
    	function delAppoint(id){
    		$("#"+id).remove();
    	}
    	
    </script>
	</head>
	<body>
	<%@include file="/util/head.jsp"%>	
	<div class="jxpt_layout">
	  <%@include file="/util/nav.jsp" %>
		<div id="fade" class="black_overlay"
			style="background: black; filter: alpha(opacity = 50); opacity: 0.5; -moz-opacity: 0.5;"></div>
		  <!-- 指定教师 -->
		  <div class="public_windows_layout w730 h450" id="dv_appointright" style="display:none">
		  <p class="f_right"><a href="javascript:;" onclick="appointTeacherSub()" title="关闭"><span class="public_windows_close"></span></a></p>
		  <div class="jxpt_ziyuan_sczy_qxcz">
		  <p class="right">已选用户：</p>
		  <p class="left">备选用户：</p>
		  <input type="hidden" value="" id="valShowPath" name="valShowPath"/> 
		  <div class="one">
		    <script type="text/javascript"> 
		  					var d = new dTree('d');
	  					/***********权限操作************/
	  					d.add("0","-1","部门信息");			
	  					<c:if test="${!empty deptList}"> 
	  						<c:forEach items="${deptList}" var="dpt">  						
	  								d.add(${dpt.deptid},${dpt.parentdeptid},"${dpt.deptname}","javascript:clickDTreeLoadUser(${dpt.deptid});");    
	  						</c:forEach> 
	  					</c:if>
	  					document.write(d); 
		  				</script>	
		  </div>  
		  <div class="two">
		  <p><!-- <input  id="search_name" id="search_name" type="text" class="public_input w170" /><a href="1" target="_blank"><img src="images/an30_130705.png" width="22" height="20" align="middle" /></a> --></p>
		  <div>
		    <ul id="sel_noresult">      
		    </ul>
		  </div>
		</div>
		  <div class="two">
		    <p class="t_r"><a href="javascript:;" onclick="document.getElementById('sel_result').innerHTML='';">清空</a></p>
		  <div>
		    <ul id="sel_result">     
		    </ul>
		  </div>
		  </div>
		<p class="t_c clearit"><a class="an_gray" href="javascript:;" onclick="appointTeacherSub()">确定</a></p>
		</div>
		</div>


		<!-- 指定教师ssss -->
		<div id="dv_appointright"
			style="width: 735px; height: 490px; position: absolute; background-color: white; display: none; padding: 2px;">
			<p style="height: 18px">
				<span style="float: right"><a href="javascript:;"
					onclick="appointTeacherSub()">[X]</a>
				</span>
				<span id="sp_title" style="font-weight: bold; float: left">指定用户可见</span>
			</p>
			<div style="width: 735px; height: 380px;">
				<div style="width: 231px; float: right">
					<!-- 已选用户 -->
					<p>
						已选用户：
					</p>
					<p align="right">
						<a href="javascript:;">清空</a>
					</p>
					<p>
						<select id="sel_result" size="15"
							ondblclick="optsChange('sel_result','sel_noresult')"
							style="width: 200px; height: 300px;" name="sel_result"></select>
					</p>
				</div>
				<div style="width: 445px; height: 490px; float: left">
					<div style="float: right; width: 220px; height: 330px">
						<!--备选用户-->
						<p>
							备选用户：
						</p>
						<p>
							<input type="text" id="search_name" id="search_name" />
							<a href="javascript:;">搜索</a>
						</p>
						<p>
							<select id="sel_noresult" style="width: 200px; height: 300px;"
								ondblclick="optsChange('sel_noresult','sel_result')" size="15"
								name="sel_noresult"></select>
						</p>
					</div>
					<div style="float: left; width: 200px; height: 336px;">
						<script type="text/javascript"> 
  					var d = new dTree('d');
  					/***********权限操作************/
  					d.add("0","-1","部门信息");			
  					<c:if test="${!empty deptList}"> 
  						<c:forEach items="${deptList}" var="dpt">  						
  								d.add(${dpt.deptid},${dpt.parentdeptid},"${dpt.deptname}","javascript:clickDTreeLoadUser(${dpt.deptid});");    
  						</c:forEach> 
  					</c:if>
  					document.write(d); 
  				</script>
					</div>
				</div>
			</div>
			<input type="hidden" value="" id="valShowPath" name="valShowPath" />
			<p align="right">
				<a href="javascript:;" onclick="appointTeacherSub()">确定</a>
			</p>
		</div>
		  	<c:if test="${!empty erList}">
  		<c:forEach items="${erList}" var="er">
  			<input type="hidden" value="${er.posisions }" name="hd_posisions"/>
  		</c:forEach>  	
  	</c:if>
  
		<input type="hidden" value="${param.resid }" id="hd_resid"/>
		
    <c:if test="${!empty reFileInfoList}">
    	<c:forEach items="${reFileInfoList}" var="rftmp" varStatus="rfidx">
    	
		<div id="dv_edit_${param.resid }">
			<input type="hidden" value="${rftmp.filename }" name="fname">
			<input type="hidden" value="${rftmp.filesize }" name="fsize">
			<input type="hidden" value="${rftmp.path }" name="path">
			<input type="hidden" value="${param.resid }"
				name="uuid" id="uuid${param.resid }">
			<p class="jxpt_ziyuan_sczy_title"
				id="p_img_${param.resid }">
				<span><img src="images/an27_130705.png" width="27"
						height="27" 
						onclick="changeTableShow('${param.resid }',2)">
				</span>
				${rftmp.filename }
			</p>
			<table border="0" cellpadding="0" cellspacing="0"
				class="public_tab1 m_a_10 zt14"
				id="tbl_edit_body${param.resid }">
				<colgroup>
					<col class="w100">
					<col class="w720">
				</colgroup>
				<tbody>
					<tr>
						<th>
							资源名称：
						</th>
						<td>
							<input id="resource_name${param.resid }"
								name="resource_name${param.resid }"
								value="${resinfo.resname }"
								type="text" class="public_input w300"/>
						</td>
					</tr>
					<tr>
						<th>
							简&nbsp;&nbsp;&nbsp;&nbsp;介：
						</th>
						<td>
							<textarea id="introduce${param.resid }"
								name="introduce${param.resid }"
								class="public_input h90 w450">${resinfo.resintroduce}</textarea>
						</td>
					</tr>
					<tr>
						<th>
							分&nbsp;&nbsp;&nbsp;&nbsp;类：
						</th>
						<td id="leibel_${param.resid }"  class="jxpt_ziyuan_zrss_option" style="background-color:#f2f2f2">
						</td>
					</tr>
					<tr>
						<th>
							关&nbsp;键&nbsp;字：
						</th>
						<td>
							<p>
								<input name="key_word${param.resid }"
									id="key_word${param.resid }" type="text"
									value="${resinfo.reskeyword}"
									class="public_input w300"> 逗号分隔多个关键字
							</p>
							 <%
							 /*<c:if test="${!empty keyWordRecommend}">
								<ul class="jxpt_ziyuan_list02">
								  	<li class="one">推荐：</li>
								  	<li>
								 	<c:forEach items="${keyWordRecommend}" var="kwrecomed">
								 		 <b><a href="javascript:;" onclick="addKeyWord('${kwrecomed.dictionaryvalue}','key_word${param.resid }')">${kwrecomed.dictionaryname}</a></b>
								 	</c:forEach>
								 	</li>
								 </ul>		
							 </c:if>*/	%>								
						</td>
					</tr>
					<tr>
						<th>
							权限设置：
						</th>
						<td>
							<p>
								<input type="checkbox"
									onclick="ckChild(this,'ck_right_view${param.resid }',0);"
									name="ck_right_view${param.resid }_all"
									id="ck_right_view${param.resid }_all">				
							<label for="ck_right_view${param.resid }_all">浏览</label>
							</p>
							<p class="jxpt_ziyuan_checkbox">
							<c:if test="${!empty resourceViewRightType}">
								<c:forEach items="${resourceViewRightType}" var="vr">
									<input type="radio"  value="${vr.dictionaryvalue}" name="ck_right_view${param.resid }" onclick="appointTeacher(this,'dv_appointright','p_right_view${param.resid }_teacher','p_right_view${param.resid }_subject')" id="ck_right_view${param.resid }_${vr.dictionaryvalue}"/>
									<label for="ck_right_view${param.resid }_${vr.dictionaryvalue }">${vr.dictionaryname}</label>&nbsp;&nbsp;&nbsp;
								</c:forEach>					
							</c:if>								
							</p>
							<p class="jxpt_ziyuan_checkbox"
								id="p_right_view${param.resid }_teacher"
								style="display: none"></p>
							<p id="p_right_view${param.resid }_subject" style="display: none">
								<select id="right_view${param.resid }_subject">
									<c:if test="${!empty sublist}">
										<c:forEach items="${sublist}" var="sb">
											<option value="${sb.subjectinfo.subjectid}">${sb.subjectinfo.subjectname}</option>
										</c:forEach>
									</c:if>
								</select>
							</p>
							<p></p>
							<p>
								<input type="checkbox" onclick="ckChild(this,'ck_right_down${param.resid }',0);" name="ck_right_down${param.resid }_all" id="ck_right_down${param.resid }_all"/> 
								<label for="ck_right_down${param.resid }_all">下载</label>							
							</p>
							<p class="jxpt_ziyuan_checkbox">
								<c:if test="${!empty resourceViewRightType}">
									<c:forEach items="${resourceViewRightType}" var="vr">
										<input type="radio" value="${vr.dictionaryvalue}" onclick="appointTeacher(this,'dv_appointright','p_right_down${param.resid }_teacher','p_right_down${param.resid }_subject')" name="ck_right_down${param.resid }" id="ck_right_down${param.resid }_${vr.dictionaryvalue}"/>
										<label for="ck_right_down${param.resid }_${vr.dictionaryvalue }">${vr.dictionaryname}</label>
									</c:forEach>					
								</c:if>								
							</p>
							<p id="p_right_down${param.resid }_teacher"	style="display: none">
							<!-- 
							<span id="sp_p_right_viewC5D0236D-F8F0-0001-1C17-27941FD0132D_teacher_84"><font>王伟平<span></span><span></span></font><input type="hidden" value="84"><a style="color:gray" href="javascript:delAppoint('sp_p_right_viewC5D0236D-F8F0-0001-1C17-27941FD0132D_teacher_84');">[X]</a></span>
							 -->
							</p>
							<p id="p_right_down${param.resid }_subject"	style="display: none">
								<select	id="right_down${param.resid }_subject">
									<c:if test="${!empty sublist}">
										<c:forEach items="${sublist}" var="sb">
											<option value="${sb.subjectinfo.subjectid}">${sb.subjectinfo.subjectname}</option>
										</c:forEach>
									</c:if>
								</select>
							</p>
						</td>
					</tr>
					<tr>
						<th>
							&nbsp;
						</th>
						<td>
							<a href="javascript:;"
								onclick="validateUpdateDate('${param.resid }')"
								class="an_blue">提&nbsp;交</a>&nbsp;
							<!-- <input type="checkbox" name="ck_yingyong" id="ck_yingyong">
							<label for="ck_yingyong">
								批量应用下列文件
							</label> -->
						</td>
					</tr>
					<tr>
						<td colspan="2"></td>
					</tr>
				</tbody>
			</table>
		</div>
		</c:forEach>
		</c:if>


	<div id="result"></div>
			<%@include file="/util/foot.jsp"%>
	</div>



		<!-- 加载控件 -->
		<script type="text/javascript">	
    	 var url="<%=fileSystemIpPort%>upload1.jsp?jsessionid=aaatCu3yQxMmN-Rru135t&res_id=${UUID}";    		
	    if (navigator.userAgent.indexOf("MSIE") > 0) {		
			//$("#uploader").attr("classid","clsid:787D6738-39C4-458C-BE8B-0084503FC021");
			var h='<object classid="clsid:787D6738-39C4-458C-BE8B-0084503FC021" id="uploader"></object>';
			$("#span_obj_add").html(h);		
		} else if (isMozilla = navigator.userAgent.indexOf("Chrome") > 0) {
			var h=' <object id="uploader" type="application/x-itst-activex" clsid="{787D6738-39C4-458C-BE8B-0084503FC021}" style="width: 10px; height: 10px"></object>';
			$("#span_obj_add").html(h);
		}else {
			alert("异常，上传控件目前只支持Chrome,IE! \n\n您当前所用的是Safari");
		}
    </script>
	</body>
</html>
