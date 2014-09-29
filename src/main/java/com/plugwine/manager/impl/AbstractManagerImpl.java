package com.plugwine.manager.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.plugwine.i18n.DefaultMessageSource;
import com.plugwine.i18n.IMessageSource;
import com.plugwine.manager.AbstractManager;
import com.plugwine.manager.ServiceFactory;


/**
 * Abstract Manager class
 * 
 */
public abstract class AbstractManagerImpl implements AbstractManager {

    /** Logger */
    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * the Message source
     */
    @Autowired
    @Qualifier(value = "businessMessageSource")
    private IMessageSource messageSource;

    private ServiceFactory serviceFactory;
    
    /**
     * Default constructor
     */
    protected AbstractManagerImpl() {
    }

    protected final Logger getLogger() {
        return logger;
    }

    public final IMessageSource getMessageSource() {
        if (messageSource == null) {
            logger.warn("Null messageSource found");
            //
            //
            messageSource = new DefaultMessageSource();
        }
        return messageSource;
    }

    /**
     * 
     * @param messageSource
     */
    public void setMessageSource(IMessageSource messageSource) {
        this.messageSource = messageSource;
    }

    protected final ServiceFactory getServiceFactory() {
        return serviceFactory;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setServiceFactory(ServiceFactory factory) {
        serviceFactory = factory;
    }
}
