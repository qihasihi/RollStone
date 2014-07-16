<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/util/common-jsp/common-thpj.jsp"%>

<html>
  <head>
     <script type="text/javascript" src="<%=basePath %>js/peer/pj_peer_do.js"></script>
     <script type="text/javascript">
  	var pjitemCount=0;
  		var baseref="${peerbaseref}";
  		var pjtype="1";
  		$(function(){  	
  			<c:if test="${!empty p_type}">
  				pjtype="${p_type}";
  			</c:if>  
  			 //计算总分
	   		 calSumScore();	
  			//当前链接查看的信息
  			if(pjtype.length>0){
  				$("#pj_2").addClass("F_blue");
  				$("#pj_child_"+pjtype).addClass("F_blue");
  			}
  		    addDateLister();
  		    addColLister();	  		    
  		});  		 
  		
  	</script>
  </head>
  
  <body>
  	<%@include file="/util/head.jsp"  %>	
   <div id="zbdiv" style="display:none" class="public_windows_layout w450 h250">
  <p class="f_right"><a href="javascript:closeModel('zbdiv','hide')"  title="关闭"><span class="public_windows_close"></span></a></p>
  <p class="t_c font14 font_strong p_t_10">评价说明</p>
  <p class="p_20">　　${remark }　</p>
</div> 
     <div class="thpj_content">
     	 <%@include file="/util/nav.jsp" %>
    <div class="thpj_contentR">
      <p  class="p_b_10"><a href="javascript:showModel('zbdiv')" class="F_blue">评价说明</a><strong class="font14">${year }&nbsp;&nbsp; ${typename }</strong></p>
    
      <div class="f_left">
        <table border="0" cellpadding="0" cellspacing="0" id="tbl_col" class="public_tab2 td85 font_strong">
        <c:if test="${!empty parentitemList}">
	           <tr>
	            <th colspan="3">&nbsp;</th>
	          </tr>  
	          <c:forEach items="${parentitemList}" var="pi" varStatus="piIdx"> 
    					<tr> 
    					<td rowspan='${pi.childcount }'>${piIdx.index+1}、${pi.namestr } [${pi.score }分]
    						<input type="hidden" value='${pi.name }' id='hd_full_name_${pi.ref }'/> 
    					</td>
    					<td id='child_name_${pi.ref}_1'></td>
    					<td id='child_score_${pi.ref }_1' style="width:15px"></td>
    				</tr> 
    				<script type="text/javascript">
    					var rownum="${pi.childcount}";
    					pjitemCount+=parseInt(rownum);
    						var ref='${pi.ref }';
    					if(rownum.length>0&&rownum!=0&&ref.length>0){
    						rownum=parseInt(rownum)-1;
    						var h=''; 
    						for(i=1;i<=rownum;i++){
    							h+='<tr><td id="child_name_'+ref+'_'
    								+(i+1)+'"></td><td id="child_score_'+ref+'_'+(i+1)+'" style="width:15px">'
    								+'</td></tr>';    													
    						}
    						document.write(h);
    					}    		    							
    				</script>     				
    		  </c:forEach>
    		  <tr>
		  		<td colspan="3" align="right">总分</td>
		  	 </tr>         
         </c:if>
        </table>
      </div>
      <div class="thpj_jxpj" id="datadiv">
        <table border="0" cellpadding="0" cellspacing="0" id="tbl_data" class="public_tab2 td85" style="width:680px;">
       	 <!-- 加载被评价人列表 -->
       	  <c:if test="${!empty experimenterList}"> 
		  		<tr> 
		  			<c:forEach items="${experimenterList}" var="du" varStatus="idx">
		  			
		  				<th id="teacher_td">
		  					<script type="text/javascript">
		  						var tname="${du.teachername }";
		  						if(tname.Trim().length>0)
		  							tname=tname.substring(0,20);
		  						var thtml=tname+'<textarea style="display:none">${du.teachername}</textarea>';
		  						document.write(thtml);		  						
		  					</script>
		  				</th> 
		  				
		  			</c:forEach>
		  		</tr>
  			</c:if>  			
         <script type="text/javascript">
  			var t='';
  			var lastTrH='<tr>';
  			for(z=1;z<=pjitemCount;z++){
  			
  			  //普通行 
  			  t+= '<tr ';
  			  if(z%2==0){
  				t+='class="trbg2" ';
  			  }
  			  t+='>';  			
  				<c:forEach items="${experimenterList}" var="du" varStatus="idx">
  				<c:if test="${idx.index<5 }">
		  			t+='<td onclick="void(\'${du.userid }\','+z+')" id="tbl_data_td_'+z+'_${du.userid}">';
		  			t+='<span>未评</span><input type="hidden" value="${du.userid}"/></td>';
		  			if(z==1){
		  				lastTrH+='<td>0</td>';
		  			}
		  			</c:if>	
		  		</c:forEach>	
		  		  		
  			    t+='</tr>';
  			    //最后一行
  			   
  			 }
  			 lastTrH+='</tr>';
  			 t+=lastTrH;
  			  document.write(t);
  			</script> 
        </table>
      </div> 
      <p class="p_t_15 clearit">
       
      	<a href="javascript:doAddPJScore('${peerbaseref}','${p_type}')" class="an_blue">确定</a>&nbsp;&nbsp;&nbsp;&nbsp;
      	<a href="javascript:self.reload();" class="an_blue">
      	重置
      	</a>
      </p>
    </div>
    
    
   
  
  <script type="text/javascript">
  	<c:if test="${!empty childitemList}">
  		<c:forEach items="${childitemList}" var="ci">
  			var parentref="${ci.parentref}";
  			var ref="${ci.ref}";
  			if(parentref.length>0){
  			   var h='${ci.namestr}';
  			   //查询已经有多少道题归位
  			   var tdnameObjArray=$("td").filter(function(){
  			   						return this.id.indexOf('child_name_'+parentref+'_')!=-1
  			   								&&this.innerHTML.length>0;
  			   					 });
  			   //下一道题归位的位置
  			   var idx=tdnameObjArray.length+1;
  			 	
  			 	if(h.length>6)
  			 	   h=h.substring(0,6);
  			   //开始归位
  			   $("#child_name_"+parentref+'_'+idx).html(h+'<input type="hidden" value="'+ref+'"/><textarea style="display:none">${ci.name}</textarea>');
  			   $("#child_score_"+parentref+'_'+idx).html(${ci.score});  			   
  			}
  		</c:forEach>
  	</c:if>
  	//var teacherListLen=${fn:length(experimenterList)};
  //	$("#tbl_data").css("width",80*teacherListLen);
  	//var rowcount=pjitemCount+3; 
  	//$("#datadiv").css("height",26*rowcount);
  	
  	
  	
  	//编号
  	setTrNumber();
  	
  	//------------------------------------------------//
  	//历史评价分数情况。
  	<c:if test="${!empty peerlogList}">
  		<c:forEach items="${peerlogList}" var="pjs">
  			var pjitemid="${pjs.peeritemref}";
  			var pjscoreid="${pjs.score}";
  			var userid="${pjs.userid}";  			
  			//查找位置
  			var pjitemObj=$("#tbl_col td").filter(function(){
  							return this.id.indexOf('child_name_')!=-1
  									&&$(this).children('input[type="hidden"]').val().Trim()==pjitemid
  						});
  			if(pjitemObj.length>0){
  				var id=pjitemObj.parent().attr("id");
  				var rowid=id.substring(id.lastIndexOf('_')+1);
  				
  				//得到数据
  				var hiuserid=$("#tbl_data input[type='hidden']")
  					.filter(function(){return this.value.Trim()==userid});
  		
  				$.each(hiuserid,function(i,m){
  					//由于tbl_col的编号是从第一个开始，而tbl_data的数据索引是从0开始，而且第一列为姓名
  					if((i+1)==(rowid-1)){ 
  						//确定单元格 并赋予颜色
  						if(pjscoreid<1)
  							$(m).parent().css("background-color","yellow");
  						else
  							$(m).parent().css("background-color","#66FF99"); 
  						//组织数据添加
  						var h='<span>'+pjscoreid+'</span>';
  							h+='<input type="hidden" value="'+userid+'">';  						
  						$(m).parent().html(h);  						
  					}  				
  				});
  			}  				
  		</c:forEach> 
  			
  	</c:if>
  		 
  </script>
    <div class="thpj_contentL">
  <ul>
    <li class="crumb">
    	 <a href="peerlog?m=jslist&ptype=1" target="" >教师评价</a>
    	 
    </li>
    <li>
    	<a href="peerlog?m=jslist&ptype=2" target="" >职工评价</a>
    </li>
  </ul>
</div>
<div class="clear"></div>
</div>
<%@include file="/util/foot.jsp"  %>
  </body>
</html>
