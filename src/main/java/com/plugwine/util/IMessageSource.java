package com.plugwine.util;

import java.util.Locale;

import org.springframework.context.MessageSource;

/**
 * Generic Interface for message source handling
 * 
 */
public interface IMessageSource {

	String PREFIX_MESSAGE_NOT_FOUND = "???";
    /**
     * Return the bundle used to resolved the paramaeters.
     * 
     * @return
     */
    public MessageSource getBundle();

    /**
     * Updates the bundle used to resolve the parameters.
     * 
     * @param bundle Composant de résolution
     */
    public void setBundle(MessageSource bundle);

    /**
     * Return the current application locale
     * 
     * @return
     */
    public Locale getApplicationLocale();

    /**
     * update the application locale.
     * 
     * @param applicationLocale
     */
    public void setApplicationLocale(Locale applicationLocale);

    /**
     * Return the value for the given parameter key.
     * 
     * @param key
     * @return 
     */
    public String getMessage(String key);
    
    /**
     * 
     * @param key
     * @param locale
     * @return
     */
    public String getMessageByLocale(String key, Locale locale);

    /**
     *  Return the message for the given key using the passed in value
     * 
     * @param key
     * @param arg
     * @return 
     */
    public String getMessage(String key, Object arg);
    
    /**
     * 
     * @param key
     * @param arg
     * @param locale
     * @return
     */
    public String getMessageByLocale(String key, Object arg, Locale locale);

    /**
     * Return the message for the given key using the passed in values
     * 
     * @param key Clef du message
     * @param args
     * @return 
     */
    public String getMessage(String key, Object[] args);
    
    /**
     * 
     * @param key
     * @param args
     * @param locale
     * @return
     */
    public String getMessageByLocale(String key, Object[] args, Locale locale);
}
