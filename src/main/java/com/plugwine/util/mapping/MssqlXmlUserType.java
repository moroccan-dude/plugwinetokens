package com.plugwine.util.mapping;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.io.StringWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.UserType;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

 
public class MssqlXmlUserType implements UserType, Serializable {
    // all other methods are ommitted at present for brevity
 
    private static final long serialVersionUID = 2308230823023l;
    private static final Class<Document> returnedClass = Document.class;
    public static final int XML_SQL_TYPE = -16;
    private static final int[] SQL_TYPES = new int[] { XML_SQL_TYPE };
 
    public int[] sqlTypes() {
        return SQL_TYPES;
    }
 
    public Class<Document> returnedClass() {
        return returnedClass;
    }
 
    public int hashCode(Object _obj) {
        return _obj.hashCode();
    }
 
    public Object assemble(Serializable _cached, Object _owner)
            throws HibernateException {
        try {
            return stringToDom((String) _cached);
        } catch (Exception e) {
            throw new HibernateException(
                    "Could not assemble String to Document", e);
        }
    }
 
    public Serializable disassemble(Object _obj) throws HibernateException {
        try {
            return domToString((Document) _obj);
        } catch (Exception e) {
            throw new HibernateException(
                    "Could not disassemble Document to Serializable", e);
        }
    }
 
    public Object replace(Object _orig, Object _tar, Object _owner) {
        return deepCopy(_orig);
    }
 
    public boolean equals(Object arg0, Object arg1) throws HibernateException {
        if (arg0 == null && arg1 == null)
            return true;
        else if (arg0 == null && arg1 != null)
            return false;
        else
            return arg0.equals(arg1);
    }
 
	@Override
	public Object nullSafeGet(ResultSet rs, String[] names,
			SessionImplementor session, Object owner)
			throws HibernateException, SQLException {

        Document doc = null;
        try {
            Object op = rs.getObject(names[0]);
            doc = stringToDom((String)op);
        } catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
//            if (null != xmlType) {
//                xmlType.close();
//            }
        }
        return doc;
    }
 
	@Override
	public void nullSafeSet(PreparedStatement st, Object value, int index,
			SessionImplementor session) throws HibernateException, SQLException 
	{
        try {
            if(value==null)
            	st.setObject(index, domToString(stringToDom("")));
            else
				st.setObject(index, domToString((Document) value));
        } catch (Exception e) {
            throw new SQLException(
                    "Could not convert Document to String for storage");
        }
    }
 
    public Object deepCopy(Object value) throws HibernateException {
        if (value == null)
            return null;
 
        return (Document) ((Document) value).cloneNode(true);
    }
 
    public boolean isMutable() {
        return false;
    }
 
    protected static String domToString(Document _document)
            throws TransformerException {
        TransformerFactory tFactory = TransformerFactory.newInstance();
        Transformer transformer = tFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource source = new DOMSource(_document);
        StringWriter sw = new StringWriter();
        StreamResult result = new StreamResult(sw);
        transformer.transform(source, result);
        return sw.toString();
    }
 
    protected static Document stringToDom(String xmlSource)
            throws SAXException, ParserConfigurationException, IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(
                xmlSource.getBytes("UTF-8"));
        Document document = builder.parse(inputStream);
        return document;
    }

}
