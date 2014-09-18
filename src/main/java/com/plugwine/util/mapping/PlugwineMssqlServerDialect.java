package com.plugwine.util.mapping;

import org.hibernate.dialect.SQLServer2008Dialect;
//import org.hibernate.dialect.SQLServer2012Dialect;

import org.hibernate.type.StandardBasicTypes;

import java.sql.Types;

public class PlugwineMssqlServerDialect extends SQLServer2008Dialect{
	
	 public PlugwineMssqlServerDialect() {
		    super();
		    registerColumnType(Types.CHAR, "nchar(l)");
		    registerColumnType(Types.LONGVARCHAR, "nvarchar($l)");
		    registerColumnType(Types.CLOB, "ntext");
		    
		    registerHibernateType( Types.NVARCHAR, 255, StandardBasicTypes.STRING.getName() );
		    //registerColumnType( Types.VARCHAR, MAX_LENGTH, "varchar($l)" );
		    // Adding the following line fixed the hibernate exception saying:
		    // Found: nvarchar, expected: varchar(255)
		    // Which really means:
		    // found nvarchar in DB metadata and found varchar(255) in the mapping?
		    // so I added the following line
		    registerColumnType(Types.VARCHAR, 255, "nvarchar($l)");
		    
		    // I also had to implement the XML user type
		    registerHibernateType(MssqlXmlUserType.XML_SQL_TYPE, "xml"); /* xml is the type as it is defined in MSSQL SERVER */
	        registerColumnType(MssqlXmlUserType.XML_SQL_TYPE, "xml");
//	        registerHibernateType(MssqlXmlUserType.XML_SQL_TYPE2, "xml"); /* xml is the type as it is defined in MSSQL SERVER */
////	        registerColumnType(MssqlXmlUserType.XML_SQL_TYPE2, "xml");
	        
	        // I also had to implement the Uniqueidentifier user type
	        registerHibernateType(MssqlUuidUserType.UUID_SQL_TYPE, "uniqueidentifier");
	        registerColumnType(MssqlUuidUserType.UUID_SQL_TYPE, "uniqueidentifier");
	 }
	
}
