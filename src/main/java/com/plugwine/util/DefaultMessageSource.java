package com.plugwine.util;

import java.util.Locale;

import org.apache.commons.lang.LocaleUtils;
import org.springframework.context.MessageSource;


/**
 * Default MessageSource implementation for the app
 * 
 */
public class DefaultMessageSource implements IMessageSource {

    /**
     * Bundle
     */
    private MessageSource bundle;
   
    //private Locale applicationLocale = null;

    @Override
    public MessageSource getBundle() {
        return bundle;
    }

    @Override
    public void setBundle(MessageSource bundle) {
        this.bundle = bundle;
    }

    @Override
    public Locale getApplicationLocale() {
    	Locale l = PlugwineLocaleContextHolder.getLocale();
    	if(l!=null && LocaleUtils.isAvailableLocale(l)) 
    		return l;

    	return Locale.getDefault();
    	//return ApplicationInfo.getLocale();
    }

    @Override
    public void setApplicationLocale(Locale applicationLocale) {
    	PlugwineLocaleContextHolder.setLocale(applicationLocale);
    }

    @Override
    public String getMessage(String key) {
        return getMessage(key, (Object[]) null);
    }
    
	@Override
	public String getMessageByLocale(String key, Locale locale) {
		return getMessageByLocale(key, (Object[]) null, locale);
	}

    @Override
    public String getMessage(String key, Object arg) {
        return getMessage(key, new Object[] { arg });
    }
    
	@Override
	public String getMessageByLocale(String key, Object arg, Locale locale) {
		return getMessageByLocale(key, new Object[] { arg }, locale);
	}
	
    @Override
    public String getMessage(String key, Object[] args) {
    	return bundle.getMessage(key, args, PREFIX_MESSAGE_NOT_FOUND+ " " + key + " " + PREFIX_MESSAGE_NOT_FOUND, getApplicationLocale());
    }

	@Override
	public String getMessageByLocale(String key, Object[] args, Locale locale) {
		return bundle.getMessage(key, args, PREFIX_MESSAGE_NOT_FOUND + " " + key + " " + PREFIX_MESSAGE_NOT_FOUND, locale);
	}
}
