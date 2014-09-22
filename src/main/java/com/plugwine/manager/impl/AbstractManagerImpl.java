package com.plugwine.manager.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.plugwine.manager.AbstractManager;
import com.plugwine.util.DefaultMessageSource;
import com.plugwine.util.IMessageSource;


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


    /**
     * Default constructor
     */
    protected AbstractManagerImpl() {
    }

    protected final Logger getLogger() {
        return logger;
    }

    protected final IMessageSource getMessageSource() {
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


}
