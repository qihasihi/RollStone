package com.school.util;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.lang.reflect.Method;
import java.util.*;

/**
 * XML文件操作类
 * @author zhengzhou
 */
public class OperateXMLUtil {
    /**
     * 查询xml记录
     * @param fileName：xml文件的绝对路径
     * @param expre：xPath筛选数据的表达式
     * @return
     */
    @SuppressWarnings("unchecked")
    public static List findXml(String fileName,String expre,boolean isloadChild){
        List list = new ArrayList();
        Document document = loadDocument(fileName);
        try {
            XPathFactory xPathFactory = XPathFactory.newInstance();
            XPath xPath = xPathFactory.newXPath();
            XPathExpression exception = xPath.compile(expre);
            NodeList nodeList = (NodeList)exception.evaluate(document, XPathConstants.NODESET);//得到row集合
            for (int i = 0; i < nodeList.getLength(); i++) {
                Map<String, Object> map = new HashMap<String, Object>();
                Node node = nodeList.item(i);//循环得到row
                NodeList nodeList2 = node.getChildNodes();//得到row中的子节点集合
                for (int j = 0; j < nodeList2.getLength(); j++) {
                    Node node2 = nodeList2.item(j);
                    if (node2 instanceof Element) {
                        String key = node2.getNodeName();
                        Object value=null;
                        if (node2.hasChildNodes()) { //返回此节点是否具有子节点。 不包含-为true,包含为false
                                if(node2.getNodeType() == Node.ELEMENT_NODE&&node2.getChildNodes().getLength()>1){
                                    if(isloadChild){map.put(key,getParentChildNode(node2));};
                                }else{
                                    value=node2.getFirstChild().getNodeValue();
                                    map.put(key, value);
                                }
                        }
                    }
                }
                list.add(map);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 引用，中间过度层
     * @param nd
     * @return
     */
    private static List<Map<String,Object>> getParentChildNode(Node nd){
        List<Map<String,Object>> returnMapList=new ArrayList<Map<String, Object>>();
        if(nd.getChildNodes().getLength()>1){
            for(int i=0;i<nd.getChildNodes().getLength();i++){
                Node nd1=nd.getChildNodes().item(i);
                if(nd1.getNodeType() != Node.ELEMENT_NODE){
                    continue;
                }
                Map<String,Object> childMap=new HashMap<String,Object>();
                getChildNode(nd1,childMap);
                returnMapList.add(childMap);
            }
        }
        return returnMapList;
    }

    /**
     * 得到下一个Node
     * @param nd
     * @return
     */
    private static void getChildNode(Node nd,Map<String,Object> map){

        if(nd==null||nd.getFirstChild()==null)return;
        map.put(nd.getNodeName(),nd.getFirstChild().getNodeValue());
        if(nd.getChildNodes().getLength()>1){
            //去除第0个
            for (int i=1;i<nd.getChildNodes().getLength();i++) {
                getChildNode( nd.getChildNodes().item(i), map);
            }
        }
    }

    /**
     * 查询xml文件中最大的id值
     * @param fileName
     * @return
     */
    public static Integer getMaxId(String fileName){
        Integer maxId = 0;
        Document document = loadDocument(fileName);
        NodeList nodeList = document.getElementsByTagName("row");
        for (int i = 0; i < nodeList.getLength(); i++) {
            //循环得到所有的row节点
            Node node = nodeList.item(i);
            Element eleNode = (Element)node;
            String id = eleNode.getElementsByTagName("id").item(0).getFirstChild().getNodeValue();
            Integer firstId = Integer.valueOf(id);
            if (maxId<firstId) {
                maxId = firstId;
            }
        }
        return maxId;
    }
    /**
     * 往xml文件中添加记录
     * @param fileName:xml文件的绝对路径
     * @param nameStrings：节点集
     * @param valueStrings：节点值集
     * @return
     */
    public static boolean addXml(String fileName,List nameStrings,Object[] valueStrings){
        boolean flag = false;
        Document document = loadDocument(fileName);
        Element element = document.getDocumentElement();
        NodeList nodeList = element.getElementsByTagName("table");
        Node node = nodeList.item(0);//获得根元素下的table节点
        Element table = (Element)node;//将table节点强制转换为Element
        Element row = document.createElement("row");//创建row节点
        for (int i = 0; i < nameStrings.size(); i++) {
            Element name = document.createElement(nameStrings.get(i).toString()); //创建节点
            // System.out.println(valueStrings.getClass());
            name.appendChild(document.createTextNode(valueStrings[i].toString())); //为节点创建节点值
            row.appendChild(name);//把节点追加到row节点下
            flag = true;
        }
        table.appendChild(row);//把row节点追加到table节点下
        writeXml(document, fileName);
        return flag;
    }

    /**
     * 更新实体到XML文件中
     * @param fileName  文件路径
     * @param clsEntity 实体对象
     * @param <T>
     * @return
     */
    public static <T> boolean updateEntityToXml(String fileName,String prikeyname,String prikeyvalue,List<T> clsEntity){
        if(clsEntity==null) return false;
        boolean flag = false;
        Document document = loadDocument(fileName);
        NodeList nodeList = document.getElementsByTagName("row");//得到row集合
        String listName=clsEntity.get(0).getClass().getSimpleName()+"List";
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);//循环得到所有的row
            Element element = (Element)node;//将row强制转换为Element
            String idString = element.getElementsByTagName(prikeyname).item(0).getFirstChild().getNodeValue();//循环得到row节点下的id节点
            if (idString.equals(prikeyvalue)) { //如果id匹配，执行修改操作
                if (element instanceof Element) {
                    //得到
                    Node keyNode = element.getElementsByTagName(listName).item(0) ;
                    Element name =null;
                    Element nameParentChild=null;
                    //创建节点
                    if(keyNode!=null)
                        name=(Element)keyNode;
                    else
                        name=document.createElement(listName); //创建节点
                    for(T t:clsEntity){
                        Method[] med=t.getClass().getMethods();
                        nameParentChild=document.createElement(t.getClass().getSimpleName());
                        for(Method md:med){
                            if(md.getName().length()>3&&md.getName().indexOf("get")==0){
                                Object obj=null;
                                try{
                                    obj=md.invoke(t);;
                                    //System.out.println(md.getName()+":"+obj);
                                }catch(Exception e){
                                    System.out.println(md.getName());
                                 //   e.printStackTrace();
                                }
                                Element nameChild=document.createElement(md.getName().substring(3));
                                nameChild.appendChild(document.createTextNode(obj+""));
                                nameParentChild.appendChild(nameChild);
                            }
                        }
                        name.appendChild(nameParentChild);
                        element.appendChild(name);//把节点追加到row节点下

                        flag = true;
                    }
                }
            }
        }
        writeXml(document, fileName);
        return flag;
    }

    /**
     * 得到所有节点
     * @param fileName
     * @return
     */
    @SuppressWarnings("unchecked")
    public static List getNodeNames(String fileName){
        List list = new ArrayList();
        Document document = loadDocument(fileName);
        Node node = document.getElementsByTagName("row").item(0);
        NodeList subList = node.getChildNodes();
        for (int j = 0; j < subList.getLength(); j++) {
            Node node2 = subList.item(j);
            if (node2 instanceof Element) {
                String name = node2.getNodeName();
                list.add(name);
            }
        }
        return list;
    }
    /**
     * 修改xml文件中的一条数据
     * @param fileName:xml文件的绝对路径
     * @param nameStrings：节点集
     * @param valueStrings：节点值集
     * @return
     */
    public static boolean updateXml(String fileName,String updatecolumn,String columnvalue,String[] nameStrings,Object[] valueStrings){
        boolean flag = false;
        Document document = loadDocument(fileName);
        NodeList nodeList = document.getElementsByTagName("row");//得到row集合
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);//循环得到所有的row
            Element element = (Element)node;//将row强制转换为Element
            String idString = element.getElementsByTagName(updatecolumn).item(0).getFirstChild().getNodeValue();//循环得到row节点下的id节点
            if (idString.equals(columnvalue)) { //如果id匹配，执行修改操作
                if (element instanceof Element) {
                    for (int j = 0; j < nameStrings.length; j++) {
                        Node keyNode = element.getElementsByTagName(nameStrings[j]).item(0) ;
                        if(keyNode==null){
                            Element name = document.createElement(nameStrings[j].toString()); //创建节点
                            name.appendChild(document.createTextNode(valueStrings[j].toString())); //为节点创建节点值
                            element.appendChild(name);//把节点追加到row节点下
                        }else{
                            keyNode.getFirstChild().setNodeValue(valueStrings[j].toString());
                        }
                        flag = true;
                    }
                }
            }
        }
        writeXml(document, fileName);
        return flag;
    }
    /**
     * 根据Id删除xml文件中的匹配数据
     * @param fileName
     * @param rowId
     * @return
     */
    public static boolean deleteXml(String fileName,String updatecolumn,String rowId){
        boolean flag = false;
        Document document = loadDocument(fileName);
        NodeList nodeList = document.getElementsByTagName("row");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);//循环得到所有的row
            Element element = (Element)node;//将row强制转换为Element
            String id = element.getElementsByTagName(updatecolumn).item(0).getFirstChild().getNodeValue();//循环得到row节点下的id节点
            if (id.equals(rowId)) {//如果id匹配，执行删除操作
                Node node2 = node.getParentNode();//得到此row节点的父节点
                node2.removeChild(node);//用父节点移除此节点
                flag = true;
            }
        }
        writeXml(document, fileName);
        return flag;
    }
    /**
     * 给定一个文件名，获取该文件并解析为org.w3c.dom.Document对象返回
     * @param fileName:待解析文件的文件名
     * @return
     */
    public static Document loadDocument(String fileName){
        Document document = null;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setCoalescing(true);
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            document = db.parse(new File(fileName));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return document;
    }
    /**
     * 用于处理来自不同源的 XML，并将转换输出写入各种接收器。
     * @param document
     * @param fileName
     */
    public static void writeXml(Document document,String fileName){
        TransformerFactory tff = TransformerFactory.newInstance();
        Transformer tf =null;
        try {
            tf = tff.newTransformer();
            tf.setOutputProperty(OutputKeys.INDENT, "yes");//换行
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File(fileName));
            tf.transform(source, result);//将XML Source 转换为 Result。
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
