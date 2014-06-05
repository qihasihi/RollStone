<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/util/common-jsp/common-thpj.jsp"%>

<html>
  <head>
    <script type="text/javascript" src="js/peer/peerlog.js"></script>
 	<c:if test="${!empty parentobj}">
    	  <script  type="text/javascript">
      			//处理子节点
      			var childobjContent="${childobj}";
      			var objJson={};
      			if(childobjContent.length>0){
      				var childobjArray=childobjContent.split("|");
      				if(childobjArray.length>0){
      					for(z=0;z<childobjArray.length;z++){
      						var childobj=childobjArray[z];
      						if(childobj.length>0){
      							var cobjArray=childobj.split(",");
      							if(cobjArray.length==3){      		
      								if(typeof(objJson["id"+cobjArray[0]])=="undefined")						
      									objJson["id"+cobjArray[0]]=[];
      								var tmp={"id":cobjArray[0],"name":cobjArray[1],"score":cobjArray[2]};
      								objJson["id"+cobjArray[0]][objJson["id"+cobjArray[0]].length]=tmp;
      							}
      						}
      					}
      				}
      			}
      			
      			
      			
      
        		//处理父节点
        		var obj=[];        		
        		var parentobjContent="${parentobj}";
        		if(parentobjContent.length>0){
        			var parentobjArray=parentobjContent.split("|");
        			var parentobjlength=parentobjArray.length;
        			if(parentobjlength>0){
        				for(i=0;i<parentobjlength;i++){
        					var parentobj=parentobjArray[i];
        					if(parentobj.length>0){
        						var parentChildArray=parentobj.split(',');
        						if(parentChildArray.length==3){
        							var tmp={"id":parentChildArray[0]
        									,"name":parentChildArray[1]
        									,"score":parentChildArray[2]};        				
        							tmp.child=objJson["id"+parentChildArray[0]];
        							obj[obj.length]=tmp;	
        						}
        					}
        				}
        			}
        		}
        	</script>
       </c:if> 	
        	
  	<script type="text/javascript">
  		var peerbaseref ="${peerbaseref}";
  		var ptype ="${pjtype}"
  		$(function(){
  			if(ptype.length<1){
  				ptype ="1";
  			}
  			$("#pj_"+ptype+"").addClass("F_blue");
  			
  			
	  		/* 
	  		*题目为空不显示确定按钮
	  		*题目不为空并且flag 为 null 为修改按钮
	  		*题目不为空并且flag 为 1    不显示按钮
	  		*/   
  			<c:if test="${!empty parentList}">
  				$("#p_for_upd").hide();
  			</c:if>
  			var bo = true;
  			<c:if test="${!empty logList  }">
  				<s:iterator value="#request.logList" var="ls">
  					<c:if test="${empty ls.FLAG }">
  						bo = false;  //可以修改
  					</c:if>
  				</s:iterator>
	  			if(!bo){
	  				$("#p_for_sub").hide();
	  				$("#p_for_upd").show();
	  			}else{
	  				$("#p_for_sub").hide();
	  				$("#p_for_upd").hide();
	  				//
	  				$("td").filter(function(){
	  					return this.id.indexOf("td_child_score_")!= -1 
	  				}).children("a").each(function(idx,itm){
	  					$(this).attr("href","javascript:void(0);");
	  				});
	  			}
  			</c:if>
  		});
  	
  		
  	</script>

  </head>
  
  <body>
    <div class="jxpt_layout">
  <p class="font14 font_strong t_c p_t_20">${pbase.year}&nbsp;&nbsp;${typename }</p>
  <p class="t_c">${deptnames}&nbsp;&nbsp;&nbsp;&nbsp;${pbase.btimestring }---${pbase.etimestring }</p>
  <table border="0" cellpadding="0" cellspacing="0" class="public_tab2 m_a_20">
      <col class="w60" />
      <col class="w220" />
      <col class="w60" />
      <col class="w370" />
      <col class="w60" />
<%--      <col class="w60" />--%>
<%--      <col class="w60" />--%>
        <tr>
          <th>序号</th>
          <th>内容</th>
          <th>分值</th>
          <th colspan="3">说明</th>    
<%--          <th>分数</th>    --%>
        </tr>
        <c:if test="${!empty parentobj}">
        	<script  type="text/javascript">
        	var ghtml='';
        		if(obj.length>0){        			
        			$.each(obj,function(idx,itm){
        				//大题
        				ghtml+='<tr id="tr_name'+itm.id+'_'+(idx+1)+'">';
        					ghtml+='<td rowspan="'+itm.child.length+'">'+(idx+1)+'</td>';
        					ghtml+='<td onmouseover="" onmouseup="" rowspan="'+itm.child.length+'"><p>'+itm.name+'</p></td>';
        					ghtml+='<td rowspan="'+itm.child.length+'">'+itm.score+'</td>';
        					if(itm.child.length>0){
        						ghtml+='<td id="td_child_name_'+itm.id+'_1">'+itm.child[0].name+'</td>';
        						ghtml+='<td id="td_child_score_${p.REF }_1">'+itm.child[0].score+'</td>';
        					}else{
        						ghtml+='<td id="td_child_name_'+itm.id+'_1"></td>';
        						ghtml+='<td id="td_child_score_'+itm.id+'_1"></td>';
        					}
        				ghtml+='</tr>';
        				//大题选项
        				$.each(itm.child,function(ix,im){
        					if(ix!=0){
        						ghtml+='<tr><td id="td_child_name_'+im.id+'_'+(ix+1)+'">'+im.name+'</td>';
         						ghtml+='<td id="td_child_score_'+im.id+'_'+(ix+1)+'">'+im.score+'</td></tr>';
        					}
        				});
        			});        		
        		}else
        			ghtml+='<tr><td>没有发现发预览的数据，请返回刷新重试！</td></tr>';
        			
        		document.write(ghtml);
        	</script> 
         </c:if> 
      </table>  
      <p class="p_l_60 p_b_20"><strong>　　注：</strong><br />
	      	<c:if test="${!empty remark}">	      
			 &nbsp;&nbsp;&nbsp;&nbsp;
			 ${remark } 
	       </c:if>
      </p> 
      </div>	
  </body>
</html>
