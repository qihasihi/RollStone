<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/util/common-jsp/common-jxpt.jsp"%>
<html>
<head>
	<script type="text/javascript" src="js/interactive/topic.js"></script>
	<script type="text/javascript">
		var tpcid="${tpc.topicid}";
		 var operate_type="${param.operate_type}";
		 var courseid="${courseid}";
		$(function(){
			if(courseid==null){
					alert('异常错误，非法访问!参数异常!');return;
				}
			if(tpcid.length>0){
				$("input[id='rdo_tpc_state${tpc.status}']").attr("checked",true);
			}
		})
	</script>
</head>

<body>
<div class="subpage_head"><span class="ico55"></span>
<strong>
<c:if test="${!empty tpc }">
编辑论题
</c:if>
<c:if test="${empty tpc }">
新建论题
</c:if>
</strong></div>
<div class="content1">
  <table border="0" cellpadding="0" cellspacing="0" class="public_tab1 public_input">
    <col class="w120"/>
    <col class="w700"/>
    <tr>
      <th><span class="ico06"></span>论题标题：</th>
      <td><input value="${tpc.topictitle }" maxlength="30" name="topictitle" id="topictitle" type="text" class="w350" />
      （最多30字）</td>
    </tr>
    <tr>
      <th><span class="ico06"></span>论题说明：</th>
      <td><textarea id="topiccontent" name="topiccontent" class="h90 w600">${tpc.topiccontent }</textarea>
        <br>（最多500字）</td>
    </tr>
    <tr>
      <th><span class="ico06"></span>论题状态：
</th>
      <td class="font-black">
        <c:if test="${!empty topicState}">
                <c:forEach items="${topicState}" var="tstate" varStatus="ts_idx">
                    <input type="radio" value="${tstate.dictionaryvalue}" ${ts_idx.index==0?"checked=true":""} name="rdo_tpc_state" id="rdo_tpc_state${tstate.dictionaryvalue}"/>
                    <label for="rdo_add_tpc_state${tstate.dictionaryvalue}">${tstate.dictionaryname}</label>
                    &nbsp;&nbsp;&nbsp;&nbsp;
                </c:forEach>
            </c:if>
   	</td>
    </tr>
    <tr>
      <th>&nbsp;</th>
      <td>
      <a href="javascript:;" onclick="operateTopic()" class="an_small">提&nbsp;交</a><a href="tptopic?m=index&courseid=${courseid}" class="an_small">取&nbsp;消</a></td>
    </tr>
  </table>
  <br>
</div>
<input type="hidden" value="${tpc.topicid }" name="topicid" id="topicid"/>
<%@include file="/util/foot.jsp"%>
</body>
</html>
