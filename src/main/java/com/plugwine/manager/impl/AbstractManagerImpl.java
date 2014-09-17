package com.plugwine.manager.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.plugwine.manager.AbstractManager;
import com.plugwine.util.DefaultMessageSource;
import com.plugwine.util.IMessageSource;


/**
 * Implémentation d'un gestionnaire par défaut.
 * 
 * @author vboriesazeau
 * @version 1.0.0
 */
public abstract class AbstractManagerImpl implements AbstractManager {

    /** Logger */
    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * La source de messages
     */
    @Autowired
    @Qualifier(value = "businessMessageSource")
    private IMessageSource messageSource;

//    /**
//     * La factory de service.
//     */
//    private ServiceFactory serviceFactory;

    /**
     * Constructeur pour les sous-classes.
     */
    protected AbstractManagerImpl() {
        // rien à faire
    }

    protected final Logger getLogger() {
        return logger;
    }

    protected final IMessageSource getMessageSource() {
        if (messageSource == null) {
            logger.warn("Message messageSource est null");
            //
            // Parade moyenne et temporaire
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
