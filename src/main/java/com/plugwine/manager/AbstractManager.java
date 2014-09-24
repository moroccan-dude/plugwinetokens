package com.plugwine.manager;

import com.plugwine.util.IMessageSource;


/**
 * Abstract Manager interface.
 * 
 * @version 1.0.0
 */
public interface AbstractManager {

    /**
     * 
     * @param messageSource
     */
    void setMessageSource(IMessageSource messageSource);
    
    /**
     * 
     * @return
     */
    public IMessageSource getMessageSource();
    
    /**
     * Inject the service factory
     * 
     * @param factory
     */
    void setServiceFactory(ServiceFactory factory);
}
