package com.plugwine.util;

import java.util.Locale;

import org.springframework.context.MessageSource;

/**
 * Interface générique décrivant les méthodes à implémenter pour toute source de message.
 * 
 * @author vboriesazeau
 * @version 1.0.0
 */
public interface IMessageSource {

	String PREFIX_MESSAGE_NOT_FOUND = "???";
    /**
     * Retourne le composant de résolution de cette source de messages.
     * 
     * @return Composant de résolution
     */
    public MessageSource getBundle();

    /**
     * Met à jour le composant de résolution de cette source de messages.
     * 
     * @param bundle Composant de résolution
     */
    public void setBundle(MessageSource bundle);

    /**
     * Retourne les paramètres régionaux de cette source de messages.
     * 
     * @return Paramètres régionaux
     */
    public Locale getApplicationLocale();

    /**
     * Met à jour les paramètres régionaux de cette source de messages.
     * 
     * @param applicationLocale Paramètres régionaux
     */
    public void setApplicationLocale(Locale applicationLocale);

    /**
     * Retourne le message résolu par cette source à partir de la clef spécifiée.
     * 
     * @param key Clef du message
     * @return Message associé à la clef, ou la clef elle-même si aucun message n'a pu être trouvé
     *         pour cette clef.
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
     * Retourne le message résolu par cette source à partir de la clef et du paramètre spécifié.
     * 
     * @param key Clef du message
     * @param arg Paramètre du message
     * @return Message associé à la clef, ou la clef elle-même si aucun message n'a pu être trouvé
     *         pour cette clef.
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
     * Retourne le message résolu par cette source à partir de la clef et des paramètres spécifiés.
     * 
     * @param key Clef du message
     * @param args Tableau statique de paramètres du messages
     * @return Message associé à la clef, ou la clef elle-même si aucun message n'a pu être trouvé
     *         pour cette clef.
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
