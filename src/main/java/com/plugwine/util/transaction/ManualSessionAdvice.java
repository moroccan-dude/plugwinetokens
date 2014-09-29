package com.plugwine.util.transaction;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



@Aspect
public class ManualSessionAdvice {

	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	@After(value = "@target(com.plugwine.util.transaction.ReadOnlyTransaction) && target(com.plugwine.manager.common.GenericManager)")
    public void cleanupManualSession(){
		LOGGER.debug("Running cleanupManualSession Advice.");
        Session session = SessionContextHolder.getSessionContext();
        if(session!=null)
        {
        	try
        	{
        		session.close();
        		LOGGER.debug("Manual Session closed successfully!!!");
        	}
        	catch(Exception exception)
        	{
        		exception.printStackTrace();
        	}
        	SessionContextHolder.resetSessionContext();
        }
    }
}
