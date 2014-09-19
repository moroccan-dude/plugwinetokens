package com.plugwine.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

public class Hbm2DDL {

	    public static void main(String[] args) throws IOException {
	        execute(args[0], args[1], args[2], Boolean.parseBoolean(args[3]), 
	        		Boolean.parseBoolean(args[4]), Boolean.parseBoolean(args[5]), Boolean.parseBoolean(args[6]));
	    }
	     
//	public static void main(String[] args)
//	{
//     String hbmCfgFilePath = "M:\\projects\\java-projects\\PlugwineTokens\\src\\main\\resources\\hibernate\\hibernate.cfg.xml";
//	   String propFilePath = "M:\\projects\\java-projects\\PlugwineTokens\\src\\main\\filters\\environment-dev_mssql.properties";
//	   String destinationPath = "M:\\projects\\java-projects\\PlugwineTokens\\src\\main\\resources\\hibernate\\a.ddl";
//		try {
//			execute(hbmCfgFile,propFileName,destination,true,true);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
	    public static void execute(String hbmCfgFilePath, String propFilePath, String destinationPath, boolean create, 
	    		boolean format, boolean export, boolean skipGeneration) throws FileNotFoundException, IOException 
	    {
	    	if(skipGeneration)
	    	{
	    		System.out.println("Skipping schema export");
	    		return;
	    	}
	        System.out.println("Starting schema export using: "+ hbmCfgFilePath + " and " + propFilePath);
	        if(export)
	        	System.out.println(">>>>>>>>>>>>>>>>>>   Exporting the SQL   <<<<<<<<<<<<<<<<<<");
	        Configuration config = new Configuration();
	        config.configure(new File(hbmCfgFilePath));

	        Properties properties = new Properties();
	        properties.load(new FileInputStream(new File(propFilePath)));
	        config.addProperties(properties);

	        SchemaExport schemaExport = new SchemaExport(config);
	        schemaExport.setOutputFile(destinationPath);
	        schemaExport.setFormat(format);
	        schemaExport.setDelimiter(";");

	        schemaExport.execute(true, export, false, create);
	        System.out.println("Schema exported to " + destinationPath);
	    }
}
