<%@ page language="java" import="java.util.*,org.w3c.dom.*,java.io.*,com.school.entity.*"
         pageEncoding="UTF-8"%>
<%@page
        import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@page import="javax.xml.parsers.DocumentBuilderFactory"%>
<%@page import="javax.xml.parsers.DocumentBuilder"%>
<%@page import="java.io.File"%>
<%@page import="com.school.util.DESPlus"%>
<%@page import="com.school.manager.inter.IColumnManager"%>
<%@page import="com.school.manager.ColumnManager"%>
<%
    //得到Spring配置信息
    ApplicationContext ac = WebApplicationContextUtils
            .getWebApplicationContext(application);
    //得到用户，判断角色
    UserInfo usr = (UserInfo) request.getSession().getAttribute("CURRENT_USER");
    if (usr == null) {
        response.getWriter().print("<script type='text/javascript'>alert('您未登陆，请登陆!');</script>");
        return;
    }
    //得到角色
    List<RoleUser> ru1List = usr.getCjJRoleUsers();
    if (ru1List == null || ru1List.size() < 1) {
        //response.getWriter().print("<script type='text/javascript'>alert('错误，您当前没有角色，请联系相关管理人员进行修正!');</script>");
        return;
    }

    //List<Map<String, Object>> mapListObj = new ArrayList<Map<String, Object>>();
	/*DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	DocumentBuilder db = factory.newDocumentBuilder();
	Document doc = db.parse(new File(request.getRealPath("/")+"util/xml/navigation.xml"));
	Element elmtInfo = doc.getDocumentElement();
	NodeList nodes = elmtInfo.getChildNodes();
	for (int i = 0; i < nodes.getLength(); i++) {
		Node result = nodes.item(i);
		if (result.getNodeType() == Node.ELEMENT_NODE&& result.getNodeName().equals("navs")) {
			NodeList ns = result.getChildNodes();
			if (ns.getLength() > 0) {
				for (int j = 0; j < ns.getLength(); j++) {
					Node cresult = ns.item(j);
					if (cresult.getNodeType() == Node.ELEMENT_NODE&& cresult.getNodeName().equals("nav")) {
						Map<String, Object> navMap = new HashMap<String, Object>();
						navMap.put("displayName", cresult.getAttributes().getNamedItem("displayName").getNodeValue());
						navMap.put("fnid", cresult.getAttributes().getNamedItem("fnid").getNodeValue());
						navMap.put("url", cresult.getAttributes().getNamedItem("url").getNodeValue());
						navMap.put("class", cresult.getAttributes().getNamedItem("class").getNodeValue());
						NodeList ns11 = cresult.getChildNodes();
						if (ns11.getLength() > 0) {
							List<Map<String, Object>> roleListMap = new ArrayList<Map<String, Object>>();
							for (int z = 0; z < ns11.getLength(); z++) {
								Node cresult1 = ns11.item(z);
								if (cresult1.getNodeType() == Node.ELEMENT_NODE
										&& cresult1.getNodeName()
												.equals("role")) {
									Map<String, Object> roleMap = new HashMap<String, Object>();
									roleMap.put("roleid", cresult1.getAttributes().getNamedItem("roleid").getNodeValue());
									roleMap.put("rolename", cresult1.getAttributes().getNamedItem("rolename").getNodeValue());
									roleListMap.add(roleMap);
								}
							}
							navMap.put("roles", roleListMap);
						}
						mapListObj.add(navMap);
					}
				}
			}
		}
	}*/
    IColumnManager columnManager=(ColumnManager)ac.getBean("columnManager");
    //得到当前用户有权限的栏目
    List<ColumnInfo> columnList=columnManager.getUserColumnList(usr.getRef());
    StringBuilder writeHtml = new StringBuilder("<div class='home_layoutL'><ul>"); //用于输出
    //读配置文件，查看已经购买的

    String fpath = request.getRealPath("/") + "school.txt";
    BufferedReader br = null;
    StringBuilder content = null;
    try {
        br = new BufferedReader(new FileReader(fpath.trim()));
        String lineContent = null;
        while ((lineContent = br.readLine()) != null) {
            if (content == null)
                content = new StringBuilder(lineContent);
            else
                content.append("\n" + lineContent);
        }
    } catch (FileNotFoundException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
        return;
    } finally {
        if (br != null)
            br.close();
    }
    DESPlus des = new DESPlus();
    String schoolkeyfn = des.decrypt(content.toString());
    //得到分校已经买的栏目
    String[] schoolArray = schoolkeyfn.split("\\*");
    //String[] schoolArray={"","","1,2,3,4,5,6,7,8,9,10,11,12"};

    if (schoolArray != null && schoolArray.length > 2&&columnList!=null&&columnList.size()>0){
        String fnStr = schoolArray[2];
        boolean ishasnotice=false;
        String[] fnStrArray = fnStr.split(",");
        if (fnStrArray != null && fnStrArray.length > 0) {
            for (String fnStrId : fnStrArray) {
                for (ColumnInfo colTmp : columnList) {
                    if (colTmp != null&& colTmp.getColumnid() != null) {
                        String fnid=null;
                        if(fnStrId.indexOf("-")!=-1)
                            fnid =fnStrId.trim().split("-")[0];
                        else
                            fnid =fnStrId.trim();
                        if (colTmp.getFnid().equals(fnid)) {
                            StringBuilder tmpWriteHtml=new StringBuilder();
                            tmpWriteHtml.append("<li><a ");		//target='_blank'
                            if(colTmp.getIsopen()==2)         //1：不打开新窗口   2：打开新窗口
                                tmpWriteHtml.append("target='_blank'");
                            tmpWriteHtml.append(" href=\"");
                            tmpWriteHtml.append(colTmp.getPath());
                            tmpWriteHtml.append("\">");
                            tmpWriteHtml.append("<span class='");
                            tmpWriteHtml.append(colTmp.getStyleclassid());
                            tmpWriteHtml.append("'></span>");
                            tmpWriteHtml.append(colTmp.getColumnname());
                            tmpWriteHtml.append("</a></li>");
                            if(fnid.equals("1")){
                                ColumnInfo tmpCol=new ColumnInfo();
                                tmpCol.setColumnname("通知公告");
                                tmpCol.setIsopen(1);
                                tmpCol.setStyleclassid("lm_ico05");
                                tmpCol.setPath("notice?m=list");

                                tmpWriteHtml.append("<li><a ");		//target='_blank'
                                if(tmpCol.getIsopen()==2)         //1：不打开新窗口   2：打开新窗口
                                    tmpWriteHtml.append("target='_blank'");
                                tmpWriteHtml.append(" href=\"");
                                tmpWriteHtml.append(tmpCol.getPath());
                                tmpWriteHtml.append("\">");
                                tmpWriteHtml.append("<span class='");
                                tmpWriteHtml.append(tmpCol.getStyleclassid());
                                tmpWriteHtml.append("'></span>");
                                tmpWriteHtml.append(tmpCol.getColumnname());
                                tmpWriteHtml.append("</a></li>");
                            }
                            if(writeHtml.indexOf(tmpWriteHtml.toString())==-1)
                                writeHtml.append(tmpWriteHtml);
                        }
                    }
                }
            }
        }
    }

    if(isStudent){
        writeHtml.append("<li><a target='_blank' href='user?m=toEttUrl&mid=11'><span class='lm_ico12'></span>天天单词</a></li>");
        writeHtml.append("<li><a target='_blank' href='user?m=toEttUrl&mid=9'><span class='lm_ico13'></span>互帮互学</a></li>");
        writeHtml.append("<li><a target='_blank' href='user?m=toEttUrl&mid=220'><span class='lm_ico14'></span>作文大赛</a></li>");
    }
    if(isTeacher){
        writeHtml.append("<li><a target='_blank' href='user?m=toEttUrl&modelName=ycjy'><span class='lm_ico09'></span>远程教研</a></li>");
        writeHtml.append("<li><a target='_blank' href='user?m=toEttUrl&modelName=mxst'><span class='lm_ico10'></span>名校试题</a></li>");
        writeHtml.append("<li><a target='_blank' href='user?m=toEttUrl&modelName=gkk'><span class='lm_ico11'></span>四中公开课</a></li>");
    }

    writeHtml.append("<li><a target='_blank' href='user?m=toEttUrl'><span class='lm_ico07'></span>四中网校</a></li>");
    writeHtml.append("</ul></div>");

%>
<%=writeHtml.toString()%>
<script type="text/javascript">
    $(function(){
        var ettCon=$(".home_layoutL li a[href='user?m=toEttUrl']");
        if(ettCon.length>1)
            ettCon.first().parent().remove();
    })
</script>
