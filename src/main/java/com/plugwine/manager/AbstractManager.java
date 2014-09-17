package com.plugwine.manager;

import com.plugwine.util.IMessageSource;


/**
 * Interface définissant un gestionnaire générique.
 * 
 * @author vboriesazeau
 * @version 1.0.0
 */
public interface AbstractManager {

    /**
     * Injecte la factory de service.
     * 
     * @param factory La factory de service.
     */
    //void setServiceFactory(ServiceFactory factory);

    /**
     * 
     * @param messageSource
     */
    void setMessageSource(IMessageSource messageSource);
}
