package com.school.util;

        import javax.xml.parsers.DocumentBuilder;
        import javax.xml.parsers.DocumentBuilderFactory;
        import org.w3c.dom.Document;
        import org.w3c.dom.Element;
        import org.w3c.dom.NodeList;

/**
 * ������ר�Ž���XML�ļ��ģ���Ҫ����Ϊϵͳ��ȡ�Լ��������ļ�ʱ�ṩ���Ľ�������
 * @author HX
 *
 */
public class XmlReader {

    /**
     * �õ�ĳ�ڵ���ĳ�����Ե�ֵ
     * @param element	Ҫ��ȡ���ԵĽڵ�
     * @param attributeName	Ҫȡֵ����������
     * @return	Ҫ��ȡ�����Ե�ֵ
     * @author HX_2010-01-12
     */
    public static String getAttribute( Element element, String attributeName ) {
        return element.getAttribute( attributeName );
    }

    /**
     * ��ȡָ���ڵ��µ��ı�
     * @param element	Ҫ��ȡ�ı��Ľڵ�
     * @return	ָ���ڵ��µ��ı�
     * @author HX_2010-01-12
     */
    public static String getText( Element element ) {
        return element.getFirstChild().getNodeValue();
    }

    /**
     * ����ĳ��xml�ļ��������ڴ��д���DOM��
     * @param xmlFile	Ҫ������XML�ļ�
     * @return	����ĳ�������ļ����Document
     * @throws Exception	xml�ļ�������
     */
    public static Document parse( String xmlFile ) throws Exception {
        // ��XML�ļ�������DOM��
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document domTree = db.parse( xmlFile );
        return domTree;
    }

    /**
     * ���ĳ�ڵ��µ�ĳ���ӽڵ㣨ָ���ӽڵ����ƣ���ĳ�����Ե�ֵ��<br>
     * ����ȡparentElement�����ֽ�childName����������attributeName��ֵΪattributeValue���ӽ��
     * @param parentElement	Ҫ��ȡ�ӽڵ���Ǹ����ڵ�
     * @param childName	Ҫ��ȡ���ӽڵ�����
     * @param attributeName	Ҫָ������������
     * @param attributeValue	Ҫָ�������Ե�ֵ
     * @return	�����������ӽڵ�
     * @throws Exception	�ӽ�㲻���ڻ��ж�������������ӽڵ�
     * @author HX_2008-12-01
     */
    public static Element getChildElement( Element parentElement, String childName, String attributeName, String attributeValue ) throws Exception {
        NodeList list = parentElement.getElementsByTagName( childName );
        int count = 0;
        Element curElement = null;
        for ( int i = 0 ; i < list.getLength() ; i ++ ) {
            Element child = ( Element )list.item( i );
            String value = child.getAttribute( attributeName );
            if ( true == value.equals( attributeValue ) ) {
                curElement = child;
                count ++;
            }
        }
        if ( 0 == count ) {
            throw new Exception( "�Ҳ����������������ӽڵ㣡" );
        } else if ( 1 < count ) {
            throw new Exception( "�ҵ���������������ӽڵ㣡" );
        }

        return curElement;
    }

    /**
     * �õ�ĳ�ڵ��µ�ĳ���ӽڵ㣨ͨ��ָ���ӽڵ����ƣ�<br>
     * ����ȡparentElement�����ֽ�childName���ӽڵ�
     * @param parentElement	Ҫ��ȡ�ӽڵ�ĸ��ڵ�
     * @param childName	Ҫ��ȡ���ӽڵ�����
     * @return	�����������ӽڵ�
     * @throws Exception	�Ҳ��������������ӽ����ҵ���������������ӽڵ�
     */
    public static Element getChildElement( Element parentElement, String childName ) throws Exception {
        NodeList list = parentElement.getElementsByTagName( childName );
        Element curElement = null;
        if ( 1 == list.getLength()  ) {
            curElement = ( Element )list.item( 0 );
        } else if ( 0 == list.getLength() ) {
            throw new Exception( "�Ҳ����������������ӽڵ㣡" );
        } else {
            throw new Exception( "�ҵ���������������ӽڵ㣡" );
        }
        return curElement;
    }
    /**
     * �õ�ĳ�ڵ��µ�ĳ���ӽڵ㣨ͨ��ָ���ӽڵ����ƣ�<br>
     * ����ȡparentElement�����ֽ�childName���ӽڵ�
     * @param parentElement	Ҫ��ȡ�ӽڵ�ĸ��ڵ�
     * @param childName	Ҫ��ȡ���ӽڵ�����
     * @return	�����������ӽڵ�
     * @throws Exception	�Ҳ��������������ӽ����ҵ���������������ӽڵ�
     */
    public static NodeList getChildNodeListElement( Element parentElement, String childName ) throws Exception {
        NodeList list = parentElement.getElementsByTagName( childName );
        if ( 0 == list.getLength() ) {
            throw new Exception( "�Ҳ����������������ӽڵ㣡" );
        } else if(list.getLength()<1) {
            throw new Exception( "�ҵ���������������ӽڵ㣡" );
        }
        return list;
    }
}

