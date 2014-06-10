<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/util/common-jsp/common-thpj.jsp"%>

<%
	int idxSort=0;
	int pjitemCount=0; 
%>
<html>
	<script type="text/javascript" src="<%=basePath %>js/peer/pj_peer_item.js"></script>
  <script type="text/javascript">
  		//分数上限
	  	var maxscore=35;	  	
  		var year="${year}";
  		var baseref="${peerbaseref}";
  		var pjtype="1";
  		if("${!empty ptype}"){
  			pjtype="${ptype}";
		}
  		var executeType="${executeType}";
  		var btime = "${btime}";
  		var etime = "${etime}";
  		var myDate = new Date();
  		var dateStr = myDate.getFullYear()+"-"+(parseInt(myDate.getMonth())+1)+"-"+myDate.getDate()+" "+myDate.getHours()+":"+myDate.getMinutes()+":"+myDate.getSeconds();
  						  						
  		$(function(){  	
  			
  			if(pjtype>1){
				$("input[name='rtype'][value='2']").attr("checked",true);
			}else{
				$("input[name='rtype'][value='1']").attr("checked",true);
			}
  			if(baseref.length>0)
  			 	$("#sel_base").val(baseref);
  			$("#pja_"+pjtype).attr("className","F_blue");
  			
  			//说明该任务正在执行  则不提供编辑
  			if(validateTwoDate(btime+" 00:00:00",dateStr)==true||validateTwoDate(etime+" 00:00:00",dateStr)==true){
  				//清楚所有在opquestbl div中的a标签
  				$("#opquestbl a").remove();
  				//删除opquestbl div中的button
  				$("#opquestbl input[type='button']").remove();
  					//删除opquestbl div中的button
  				$("#opquestbl script").remove();
  				//给所有的标题添加样式
  				/*$("#opquestbl label").css("font-weight","bold"); */
  				
  				//给每个input[type='text']  select  textarea 外添加<span>
  				/*var wrapHtml="<span></span>";
  				$("input[type='text']").wrap(wrapHtml);
  				$("select").filter(function(){return this.id.indexOf('_score')!=-1})
  					.wrap(wrapHtml);
  				$("textarea").filter(function(){return this.style.display!='none'}).wrap(wrapHtml);
  				*/
  				//将值填入Span中
  				$("input[type='text']").attr("disabled",true);
  				/*.each(function(idx,itm){
  					$(itm).parent().html(itm.value.Trim());
  				});*/
  				
  				$("select").filter(function(){return this.id.indexOf('_score')!=-1})
  				.attr("disabled",true);
  				/*each(function(idx,itm){
  					$(itm).parent().html(itm.value.Trim());
  				});*/
  				
  				$("textarea").filter(function(){return this.style.display!='none'}).attr("disabled",true);
  			/*	
  				each(function(idx,itm){
  					$(itm).parent().html(itm.value.Trim());
  				});*/
  			}	 
  		});
  		/**
  		执行查询
  		**/
  		function doSearch(){
  			if(pjtype.Trim().length<1)
  				pjtype=1;
  			var selbase=$("#sel_base").val().Trim();
  			if(selbase.length<1){
  				alert('请选择评价主题后点击查询!');
  				return;
  			}
  			var h="peeritem?m=list&ptype="+pjtype+"&peerbaseref="+selbase;
  			//var y=$("#sel_year").val().Trim();
  			//if(y.Trim().length>0)
  			 // h+='&year='+y;
  			location.href=h;
  		}
  		function ck(pt){
  	  		if(pt==null)
  	  	  		pt=1;
  			pjtype = pt;
  			doSearch();
		}
  	</script>
  <body>




	<%@include file="/util/head.jsp"  %>		 
				 
	<div class="thpj_content">
	 <%@include file="/util/nav.jsp" %>
  <div class="thpj_contentR">
    <p class="m_b_10 font14 font_strong"><input type="radio" onclick="ck('1')"  name="rtype" value="1" /> 教师互评&nbsp;&nbsp;&nbsp;&nbsp;
			    <input type="radio" onclick="ck('2')" name="rtype"  value="2" /> 职工互评</p>
    <table border="0" cellpadding="0" cellspacing="0" class="public_tab1 m_b_25 zt14">
      <col class="w100"/>
      <col class="w690"/>
      <tr>
        <th> 评价主题：</th>
        <td><select id="sel_base" name="sel_base"  onchange="doSearch()">
		      	<c:if test="${!empty peerBase}">
		      		<c:forEach items="${peerBase}" var="itm">
		      			<option value="${itm.ref}">${itm.year }&nbsp;${itm.name} </option>
		      		</c:forEach>
		      	</c:if>
		      </select></td>
      </tr>
      <tr id="opquestbl">
        <th>&nbsp;</th>
        <td>
        <c:if test="${!empty parentItemList}">       	
       			<c:forEach items="${parentItemList}" var="pi"  varStatus="pidx">
       				<%--<c:if test="${pi.type==1}">     
       					  --%><div class="thpj_bjpj"  id="div_ques_${pi.ref }">
					        <p id="tr${pi.ref }_first" ><span><label>评价<%=++idxSort %></label></span>：
					          <input id="investname${pi.ref }"  name="investname${pi.ref }" value="${pi.name }" type="text" class="public_input w450" />
					       <label>分数：</label><select id="sel_score_parent_${pi.ref }">
					       			<script type="text/javascript"> 
										var h='';
										for(z=0;z<=maxscore;z++)
											h+='<option value='+z+'>'+z+'</option>';
										document.write(h);
									</script>
								</select>
					     		 <script type="text/javascript">
									$("#sel_score_parent_${pi.ref }").val(${pi.score});
								</script>
					        &nbsp;<a href="javascript:void('')" onclick="delInputParent('div_ques_${pi.ref}')" class=" font-blue1">删除</a></p>
					      </div>
					       <%pjitemCount++; %>				       				  					
<%--       				</c:if>--%>
<%--       				 <c:if test="${pi.type==2}">       				 --%>
<%--       					<textarea style="display:none" id="h_remark" name="h_remark">${pi.remark }</textarea>--%>
<%--       				 </c:if>--%>
       			</c:forEach>       			
       	</c:if>
        <p   id="addQuesP"><a href="javascript:addInputParent()" class=" font-blue1">添加评价</a></p>
        </td>
      </tr>
      <tr>
        <th>评价说明：</th>
        <td>
        <textarea  id="remark" name="remark" class="public_input h90 w520 m_t_5">${remark }</textarea></td>
      </tr>
      <tr>
        <th>&nbsp;</th>
        <td>
        <a href="javascript:void('')" class="an_blue"  onclick="doOperateItem()">确&nbsp;定</a>
      &nbsp;&nbsp;<a href="javascript:void('')" class="an_blue" onclick="operateitemView()">预&nbsp;览</a>
        </td>
      </tr>
    </table>
  </div>

<div class="thpj_contentL">
  <ul>
     <li ><a href="peerbase?m=list">时间管理</a></li>
    <li class="crumb"><a href="peeritem?m=list">编辑评价</a></li>
    <li><a href="1">问卷设置</a></li>
    <li><a href="peerlog?m=todeptlogstat">评价结果</a></li>
  </ul>
</div>
<div class="clear"></div>
</div>





	<!-- 得到小题s -->
	<script type="text/javascript">
		var remark=$("#h_remark");
		if(remark.length>0)
			$("#remark").val(remark.val());
	
		<c:if test="${!empty childItemList}">
			<c:forEach items="${childItemList}" var="ci">
				var v='${ci.name}';
				var parentref='${ci.parentref}';
				var answerObjArray=$("p").filter(function(){return this.id.indexOf('tr'+parentref+'_answer_')!=-1});
				var len=answerObjArray.length;
				var h=' <p class="dj" id="tr'+parentref+'_answer_'+(len+1)+'"><label>等级'+(len+1)+'</label>&nbsp;<input type="text" class="public_input w410" id="answer${ci.parentref}_'+(len+1)+'" />';
				h+=' <label>分数：</label><select onchange="changescore(this,'+parentref+')" id="sel_score1_'+parentref+'_'+(len+1)+'" name="sel_score_'+parentref+'">';
				for(i=0;i<=maxscore;i++) 
					h+='<option value="'+i+'">'+i+'</option>';
				h+='</select>'; 
				h+='&nbsp;<a href="javascript:addinput_level('+parentref+')"><img width="16" height="16" border="0" title="添加" src="images/an38_130705.png"/></a>';
				h+='&nbsp;&nbsp;<a href="javascript:removeinput_level('+parentref+','+(len+1)+')"><img width="16" height="17" border="0" title="删除" src="images/an39_130705.png"/></a>';
				h+='</p>';
				//进行添加  逻辑：如果len==0 则：没有等级，  len>0则有等级
				if(len<1){
					$("#tr"+parentref+"_first").after(h);    
				}else{
					answerObjArray.last().after(h);  
				}
				//赋值 
				$("#answer"+parentref+"_"+(len+1)).val(v);
				$('#sel_score_'+parentref+'_'+(len+1)).val(${ci.score});
				$('#sel_score1_'+parentref+'_'+(len+1)).val(${ci.score});  
			</c:forEach>  
		</c:if>
		//如果是添加，则添加选框
		<%if(pjitemCount<1){%>
			addInputParent();
		<%}%>
	</script>
	<%@include file="/util/foot.jsp"  %> 
	<script type="text/javascript">
	</script>
  </body>
</html>
