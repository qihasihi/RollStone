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
 * XML�ļ�������
 * @author zhengzhou
 */
public class OperateXMLUtil {
    /**
     * ��ѯxml��¼
     * @param fileName��xml�ļ��ľ���·��
     * @param expre��xPathɸѡ���ݵı��ʽ
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
            NodeList nodeList = (NodeList)exception.evaluate(document, XPathConstants.NODESET);//�õ�row����
            for (int i = 0; i < nodeList.getLength(); i++) {
                Map<String, Object> map = new HashMap<String, Object>();
                Node node = nodeList.item(i);//ѭ���õ�row
                NodeList nodeList2 = node.getChildNodes();//�õ�row�е��ӽڵ㼯��
                for (int j = 0; j < nodeList2.getLength(); j++) {
                    Node node2 = nodeList2.item(j);
                    if (node2 instanceof Element) {
                        String key = node2.getNodeName();
                        Object value=null;
                        if (node2.hasChildNodes()) { //���ش˽ڵ��Ƿ�����ӽڵ㡣 ������-Ϊtrue,����Ϊfalse
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
     * ���ã��м���Ȳ�
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
     * �õ���һ��Node
     * @param nd
     * @return
     */
    private static void getChildNode(Node nd,Map<String,Object> map){

        if(nd==null||nd.getFirstChild()==null)return;
        map.put(nd.getNodeName(),nd.getFirstChild().getNodeValue());
        if(nd.getChildNodes().getLength()>1){
            //ȥ����0��
            for (int i=1;i<nd.getChildNodes().getLength();i++) {
                getChildNode( nd.getChildNodes().item(i), map);
            }
        }
    }

    /**
     * ��ѯxml�ļ�������idֵ
     * @param fileName
     * @return
     */
    public static Integer getMaxId(String fileName){
        Integer maxId = 0;
        Document document = loadDocument(fileName);
        NodeList nodeList = document.getElementsByTagName("row");
        for (int i = 0; i < nodeList.getLength(); i++) {
            //ѭ���õ����е�row�ڵ�
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
     * ��xml�ļ�����Ӽ�¼
     * @param fileName:xml�ļ��ľ���·��
     * @param nameStrings���ڵ㼯
     * @param valueStrings���ڵ�ֵ��
     * @return
     */
    public static boolean addXml(String fileName,List nameStrings,Object[] valueStrings){
        boolean flag = false;
        Document document = loadDocument(fileName);
        Element element = document.getDocumentElement();
        NodeList nodeList = element.getElementsByTagName("table");
        Node node = nodeList.item(0);//��ø�Ԫ���µ�table�ڵ�
        Element table = (Element)node;//��table�ڵ�ǿ��ת��ΪElement
        Element row = document.createElement("row");//����row�ڵ�
        for (int i = 0; i < nameStrings.size(); i++) {
            Element name = document.createElement(nameStrings.get(i).toString()); //�����ڵ�
            // System.out.println(valueStrings.getClass());
            name.appendChild(document.createTextNode(valueStrings[i].toString())); //Ϊ�ڵ㴴���ڵ�ֵ
            row.appendChild(name);//�ѽڵ�׷�ӵ�row�ڵ���
            flag = true;
        }
        table.appendChild(row);//��row�ڵ�׷�ӵ�table�ڵ���
        writeXml(document, fileName);
        return flag;
    }

    /**
     * ����ʵ�嵽XML�ļ���
     * @param fileName  �ļ�·��
     * @param clsEntity ʵ�����
     * @param <T>
     * @return
     */
    public static <T> boolean updateEntityToXml(String fileName,String prikeyname,String prikeyvalue,List<T> clsEntity){
        if(clsEntity==null) return false;
        boolean flag = false;
        Document document = loadDocument(fileName);
        NodeList nodeList = document.getElementsByTagName("row");//�õ�row����
        String listName=clsEntity.get(0).getClass().getSimpleName()+"List";
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);//ѭ���õ����е�row
            Element element = (Element)node;//��rowǿ��ת��ΪElement
            String idString = element.getElementsByTagName(prikeyname).item(0).getFirstChild().getNodeValue();//ѭ���õ�row�ڵ��µ�id�ڵ�
            if (idString.equals(prikeyvalue)) { //���idƥ�䣬ִ���޸Ĳ���
                if (element instanceof Element) {
                    //�õ�
                    Node keyNode = element.getElementsByTagName(listName).item(0) ;
                    Element name =null;
                    Element nameParentChild=null;
                    //�����ڵ�
                    if(keyNode!=null)
                        name=(Element)keyNode;
                    else
                        name=document.createElement(listName); //�����ڵ�
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
                        element.appendChild(name);//�ѽڵ�׷�ӵ�row�ڵ���

                        flag = true;
                    }
                }
            }
        }
        writeXml(document, fileName);
        return flag;
    }

    /**
     * �õ����нڵ�
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
     * �޸�xml�ļ��е�һ������
     * @param fileName:xml�ļ��ľ���·��
     * @param nameStrings���ڵ㼯
     * @param valueStrings���ڵ�ֵ��
     * @return
     */
    public static boolean updateXml(String fileName,String updatecolumn,String columnvalue,String[] nameStrings,Object[] valueStrings){
        boolean flag = false;
        Document document = loadDocument(fileName);
        NodeList nodeList = document.getElementsByTagName("row");//�õ�row����
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);//ѭ���õ����е�row
            Element element = (Element)node;//��rowǿ��ת��ΪElement
            String idString = element.getElementsByTagName(updatecolumn).item(0).getFirstChild().getNodeValue();//ѭ���õ�row�ڵ��µ�id�ڵ�
            if (idString.equals(columnvalue)) { //���idƥ�䣬ִ���޸Ĳ���
                if (element instanceof Element) {
                    for (int j = 0; j < nameStrings.length; j++) {
                        Node keyNode = element.getElementsByTagName(nameStrings[j]).item(0) ;
                        if(keyNode==null){
                            Element name = document.createElement(nameStrings[j].toString()); //�����ڵ�
                            name.appendChild(document.createTextNode(valueStrings[j].toString())); //Ϊ�ڵ㴴���ڵ�ֵ
                            element.appendChild(name);//�ѽڵ�׷�ӵ�row�ڵ���
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
     * ����Idɾ��xml�ļ��е�ƥ������
     * @param fileName
     * @param rowId
     * @return
     */
    public static boolean deleteXml(String fileName,String updatecolumn,String rowId){
        boolean flag = false;
        Document document = loadDocument(fileName);
        NodeList nodeList = document.getElementsByTagName("row");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);//ѭ���õ����е�row
            Element element = (Element)node;//��rowǿ��ת��ΪElement
            String id = element.getElementsByTagName(updatecolumn).item(0).getFirstChild().getNodeValue();//ѭ���õ�row�ڵ��µ�id�ڵ�
            if (id.equals(rowId)) {//���idƥ�䣬ִ��ɾ������
                Node node2 = node.getParentNode();//�õ���row�ڵ�ĸ��ڵ�
                node2.removeChild(node);//�ø��ڵ��Ƴ��˽ڵ�
                flag = true;
            }
        }
        writeXml(document, fileName);
        return flag;
    }
    /**
     * ����һ���ļ�������ȡ���ļ�������Ϊorg.w3c.dom.Document���󷵻�
     * @param fileName:�������ļ����ļ���
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
     * ���ڴ������Բ�ͬԴ�� XML������ת�����д����ֽ�������
     * @param document
     * @param fileName
     */
    public static void writeXml(Document document,String fileName){
        TransformerFactory tff = TransformerFactory.newInstance();
        Transformer tf =null;
        try {
            tf = tff.newTransformer();
            tf.setOutputProperty(OutputKeys.INDENT, "yes");//����
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File(fileName));
            tf.transform(source, result);//��XML Source ת��Ϊ Result��
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
