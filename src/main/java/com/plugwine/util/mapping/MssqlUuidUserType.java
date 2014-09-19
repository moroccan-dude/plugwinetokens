package com.plugwine.util.mapping;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.UserType;
import org.w3c.dom.Document;

 
public class MssqlUuidUserType implements UserType, Serializable {
    // all other methods are ommitted at present for brevity
 
    private static final long serialVersionUID = 2308230823023l;
    private static final Class<Document> returnedClass = Document.class;
    public static final int UUID_SQL_TYPE = -32;//12;//-32;
    private static final int[] SQL_TYPES = new int[] { UUID_SQL_TYPE };
 
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
            return (String) _cached;
        } catch (Exception e) {
            throw new HibernateException(
                    "Could not assemble String to UUID", e);
        }
    }
 
    public Serializable disassemble(Object _obj) throws HibernateException {
        try {
            return (String) _obj;
        } catch (Exception e) {
            throw new HibernateException(
                    "Could not disassemble UUID to Serializable", e);
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

        Object op = rs.getObject(names[0]);
        return op;
    }
 
	@Override
	public void nullSafeSet(PreparedStatement st, Object value, int index,
			SessionImplementor session) throws HibernateException, SQLException 
	{
        try {
				st.setObject(index, (String) value);
        } catch (Exception e) {
            throw new SQLException(
                    "Could not convert UUID to String for storage");
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

}
