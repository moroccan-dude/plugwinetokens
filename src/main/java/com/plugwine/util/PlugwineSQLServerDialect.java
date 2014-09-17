package com.plugwine.util;

import org.hibernate.dialect.SQLServer2008Dialect;
//import org.hibernate.dialect.SQLServer2012Dialect;

import org.hibernate.type.StandardBasicTypes;

import java.sql.Types;

public class PlugwineSQLServerDialect extends SQLServer2008Dialect{
	
	 public PlugwineSQLServerDialect() {
		    super();
		    //registerHibernateType(Types.VARCHAR, name);
		    //registerColumnType(Types.VARCHAR, "lvarchar($l)");
		   
		    registerColumnType(Types.CHAR, "nchar(l)");
		    registerColumnType(Types.LONGVARCHAR, "nvarchar($l)");
		    registerColumnType(Types.CLOB, "ntext");
		    
		    registerHibernateType( Types.NVARCHAR, 255, StandardBasicTypes.STRING.getName() );
		    //registerColumnType( Types.VARCHAR, MAX_LENGTH, "varchar($l)" );
		    // Found: nvarchar, expected: varchar(255)  
		    // nvarchar in DB metadata, varchar(255) in the mapping?
		    // so I added the following line
		    registerColumnType(Types.VARCHAR, 255, "nvarchar($l)");
		    
		    registerHibernateType(MSSQLXMLUserType.XML_SQL_TYPE, "xmltype");
	        registerColumnType(MSSQLXMLUserType.XML_SQL_TYPE, "xmltype");
	        
	        registerHibernateType(MSSQLUUIDUserType.UUID_SQL_TYPE, "uniqueidentifier");
	        registerColumnType(MSSQLUUIDUserType.UUID_SQL_TYPE, "uniqueidentifier");
	 }
	
}
