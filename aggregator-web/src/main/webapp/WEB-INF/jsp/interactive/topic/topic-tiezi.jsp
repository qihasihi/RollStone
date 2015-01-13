<%--
  Created by IntelliJ IDEA.
  User: zhengzhou
  Date: 15-1-11
  Time: 上午11:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/SchoolTabFunctions" prefix="fn"%>
<c:if test="${!empty ztTheme}">
    <c:forEach items="${ztTheme}" var="theme">
        <div class="jxxt_zhuanti_hdkj_zhutie">
            <div class="info">
                <p class="pic">
                    <c:if test="${theme.croleType eq '学生'}">
                        <img onmouseover="showTdSpan(${theme.themeid})" src="${theme.headimage}" onmouseout="hideTdSpan()" onerror="headError(this)" width="125" height="125" alt="头像">
                    </c:if>
                    <c:if test="${theme.croleType!='学生'}">
                        <img onmouseover="" src="${theme.headimage}" onerror="headError(this)" width="125" height="125" alt="头像">
                    </c:if>
                    <span class="ico96" style="display:${theme.isessence==1?'block':'none'}" id="sp_jhtx${theme.themeid}"></span>
                </p>
                <p>${theme.croleType}</p>
                <p>${theme.crealname}</p>
                <input type="hidden" name="cuser_grade" id="cg${theme.themeid}" value="${!empty theme.classgrade?theme.classgrade:''}"/>
                <input type="hidden" name="cuser_classname" id="cc${theme.themeid}" value="${!empty theme.classname?theme.classname:''}"/>
                <input type="hidden" name="cuser_groupname" id="cgn${theme.themeid}" value="${!empty theme.groupname?theme.groupname:''}"/>
            </div>
            <div class="text">
                <input type="hidden" name="zt_themeid" id="themeid${theme.themeid}" value="${theme.themeid}"/>
                <p class="t_r">${theme.autoCtimeString}</p>
                <p><span id="spti${theme.themeid}">${theme.themetitle}</span></p>
                <div id="pizhu_${theme.themeid }_1_updatecontent">
                    <c:if test="${!empty theme.commentbycontent}">
                        ${theme.commentbycontent }
                    </c:if>
                    <c:if test="${empty theme.commentbycontent}">
                        ${theme.themebycontent }
                    </c:if>
                </div>
                <c:if test="${!empty theme.imattachArray&&theme.sourceid==1}">
                    <p class="p_t_10">
                        <c:forEach items="${theme.imattachArray}" var="imItm" varStatus="viIdx">
                            <c:if test="${theme.imattachtype==1}">
                                <a href="${imItm}">图片${viIdx.index+1}</a>&nbsp;
                            </c:if>
                            <c:if test="${theme.imattachtype==2}">
                                <a href="${imItm}">语音${viIdx.index+1}</a>&nbsp;
                            </c:if>
                        </c:forEach>
                    </p>
                </c:if>
            </div>
            <p class="t_r">
                <c:if test="${!empty roleStr&&(roleStr=='TEACHER' || (culoginid==theme.cuserid))||(!empty param.quoteid&&param.quoteid!=0)}">
                    <c:if test="${(!empty param.quoteid&&param.quoteid!=0)||culoginid==theme.cuserid}">
                        <c:if test="${empty theme.commentbycontent}">
                            <a href="javascript:;" onclick="showUpdateDiv('div_update','${theme.themeid}')"><span class="ico11"></span>编辑</a>&nbsp;&nbsp;
                        </c:if>
                    </c:if>
                    <a href="javascript:;" onclick="doDelTheme(${theme.themeid });"><span class="ico04"></span>删除</a>
                </c:if>
                <c:if test="${empty param.quoteid||param.quoteid==0}">
                    <c:if test="${!empty roleStr&&roleStr=='TEACHER'}">
                        <c:if test="${theme.istop==0}">
                            <span id="span_top${theme.themeid}"><a href="javascript:isTopOrIsEssence('${theme.themeid}','1','1','span_top${theme.themeid}');"><span class="ico56"></span>置顶</a></span>
                        </c:if>
                        <c:if test="${theme.istop==1}">
                            <span id="span_top${theme.themeid}"><a href="javascript:isTopOrIsEssence('${theme.themeid}','1','0','span_top${theme.themeid}');"><span class="ico56"></span>取消置顶</a></span>
                        </c:if>
                    </c:if>
                    <%--只有教师才能加精华--%>
                    <c:if test="${!empty roleStr&&roleStr=='TEACHER'}">
                        <c:if  test="${theme.isessence==1}">
                            <span id="span_best${theme.themeid}"><a  href="javascript:isTopOrIsEssence('${theme.themeid}','2','0','span_best${theme.themeid}');"><span class="ico40"></span>取消精华</a></span>
                        </c:if>
                        <c:if  test="${theme.isessence==0}">
                            <span id="span_best${theme.themeid}"><a  href="javascript:isTopOrIsEssence('${theme.themeid}','2','1','span_best${theme.themeid}');"><span class="ico40"></span>加为精华</a></span>
                        </c:if>
                    </c:if>
                    <c:if test="${!empty roleStr&&roleStr=='TEACHER'}">
                        <c:if  test="${theme.ispraise==1}">
                            <span id="sp_praiseshu${theme.themeid}"><a  href="javascript:addOrCannelPariseTheme('${theme.themeid}',2);"><span class="ico41"></span>取消赞(${theme.praisecount})</a></span>
                        </c:if>
                        <c:if  test="${theme.ispraise==0}">
                            <span id="sp_praiseshu${theme.themeid}"><a  href="javascript:addOrCannelPariseTheme('${theme.themeid}',1);"><span class="ico41"></span>赞(${theme.praisecount})</a></span>
                        </c:if>
                        <a href="javascript:;" onclick="showPiZhuDiv('div_pizhu','${theme.themeid}')"><span class="ico42"></span>批注</a>&nbsp;&nbsp;
                    </c:if>
                    <a href="javascript:;" onclick="huitie(${theme.themeid})">
                </c:if>
                <span class="ico45"></span>回帖(<span style="margin:0 0 0 0;"  id="sp_ht${theme.themeid}">${theme.pinglunshu}</span>)
                <c:if test="${empty param.quoteid||param.quoteid==0}">
                    </a>
                </c:if>
            </p>
            <input type="hidden" value="0" id="hd_zttotalnum${theme.themeid}"/>
            <input type="hidden" value="1" id="hd_currentpage${theme.themeid}"/>
            <div class="jxxt_zhuanti_hdkj_zhutie_huifu" id="dv_ht_${theme.themeid}" style="display:none">
                  <div class="" style="display:none" id="dv_hl${theme.themeid}">
                    </div>
                 <p class="t_c font-darkblue" id="p_opzk_${theme.themeid}" style="display:block">
                     <a href="javascript:;" onclick="sqzk(${theme.themeid},1)" id="a_zk${theme.themeid}" style="display:none">展开回帖</a>
                     <a href="javascript:;" onclick="sqzk(${theme.themeid},2)" id="a_sq${theme.themeid}" style="display:none">收起回帖</a></p>
                <%--此处的data-bind是动态的，点击时动态绑定--%>
                <c:if test="${empty param.quoteid||param.quoteid==0}">
                    <div class="fabiao public_input" id="dv_doht${theme.themeid}" data-bind="" style="display:none">

                        <div id="dv_txt${theme.themeid}"><textarea name="txt_hf" id="txt_hf${theme.themeid}" class="" style="width:745px;height:30px;"></textarea></div>
                        <p class="" id="p_fb${theme.themeid}" style="display:none"><span class="t_r"><a href="javascript:;" onclick="quckRestore('${theme.themeid }',1)" class="an_small">发表</a></span></p>
                        <input type="hidden" value="1" name="hd_restoreType" id="hd_rt${theme.themeid}"/>
                    </div>
                </c:if>
            </div>
        </div>
    </c:forEach>
</c:if>
<div style="display:none">
    <input type="hidden" name="pagetotal" value="${presult.pageTotal}"/>
    <input type="hidden" name="pageSize" value="${presult.pageSize}"/>
    <input type="hidden" name="pageNo" value="${presult.pageNo}"/>
    <input type="hidden" name="rectotal" value="${presult.recTotal}"/>
</div>