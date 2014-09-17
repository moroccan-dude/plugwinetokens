package com.plugwine.dao;

import java.io.Serializable;
import java.util.List;



/**
 * Interface définisant un DAO générique pour l'accès aux données.
 * 
 * @author fchopard
 * 
 * @param <T> le type d'objet traité par ce DAO
 * @param <ID> le type de la clé primaire des objets de type <T>
 */
public interface GenericDao<T, ID extends Serializable> {

    /**
     * Recherche un objet par son identifiant sans effectuer de lock sur l'accès.
     * 
     * @param id l'identifiant de l'objet
     * @return l'entité ou <code>null</code> si non trouvée
     */
    T findById(ID id);

    /**
     * Recherche un objet par son identifiant avec la possibilité d'effectuer un lock.
     * 
     * @param id l'identifiant de l'objet
     * @param lock si <code>true</code>, l'entité sera lockée
     * @return l'entité ou <code>null</code> si non trouvée
     */
    T findById(ID id, boolean lock);

    /**
     * Recherche toutes les entités connu du type géré par ce DAO.
     * 
     * @return la liste des entités
     */
    List<T> findAll();

    /**
     * @see #findByExample(Object, String...)
     */
    List<T> findByExample(T entiteExemple);

    /**
     * Recherche toutes les entités connues du type géré par ce gestionnaire dont l'id est dans la
     * liste ids donnée en paramêtre. Si un id des id passé en paramêtre n'existe pas, il n'en sera
     * pas tenu compte
     * 
     * @return la liste des entités dont l'id est dans la liste ids
     */
    List<T> findAllByIds(List<ID> ids);

    /**
     * Recherche des entités en fonction d'une entité exemple. ATTENTION : Les propriétés de type
     * version, identifiant et les associations sont ignorées. Par défaut, les valeurs a null sont
     * exclues.
     * 
     * @param entiteExemple l'exemple
     * @param proprietesExclues les propriétés de l'exemple qu'il faut ignorer
     * @return la liste des entités
     */
    List<T> findByExample(T entiteExemple, String... proprietesExclues);

    /**
     * Rend une entité persistente. Si elle ne l'était pas encore, elle est créée, sinon elle est
     * mise à jour et rattaché au gestionnaire d'entités courant.
     * 
     * @param entity l'entité
     * @return l'entité persistente
     */
    T persist(T entity);

    /**
     * Supprime une entité persistente.
     * 
     * @param entity l'entité
     */
    void delete(T entity);

    /**
     * Force le gestionnaire d'entité à se synchroniser avec le système de persistence. Toutes les
     * modifications en court sur les entités sont sauvegardées.
     */
    void flush();

    /**
     * Détache toutes les entités du gestionnaire.
     */
    void clear();

    /**
     * Une serie de methodes generiques
     */
    /**
     * 
     * @param property
     * @param value
     * @return
     */
    T getByUniqueProperty(String property, Object value);

    /**
     * Retourne l'objet disponible dont la propriété unique spécifiée a la valeur indiquée.
     * 
     * Cela se traduit par l'appel de la requête nommée "get${EntityName}By${PropertyName}" avec la
     * valeur spécifiée pour la propriété.
     * 
     * @param property Nom d'une propriété unique
     * @param value Valeur de la propriété pour laquelle il faut récupérer un objet.
     * 
     * @return Retourne l'unique objet dont la propriété a la valeur spécifiée, ou <code>null</code>
     *         si aucun objet n'a cette propriété avec cette valeur.
     */
    T getUnique(String queryNameOrString, String[] properties, Object[] values);

    /**
     * Retourne l'objet disponible dont la propriété unique spécifiée a la valeur indiquée.
     * 
     * Cela se traduit par l'appel de la requête nommée "get${EntityName}By${PropertyName}" avec la
     * valeur spécifiée pour la propriété.
     * 
     * @param property Nom d'une propriété unique
     * @param value Valeur de la propriété pour laquelle il faut récupérer un objet.
     * 
     * @return Retourne l'unique objet dont la propriété a la valeur spécifiée, ou <code>null</code>
     *         si aucun objet n'a cette propriété avec cette valeur.
     */
    Object getUniqueFromSql(String queryNameOrString, String[] properties, Object[] values);

    /**
     * 
     * @param queryNameOrString
     * @param values
     * @return
     */
    T getUnique(String queryNameOrString, Object[] values);

    /**
     * 
     * @param queryNameOrString
     * @param properties
     * @param values
     * @param pageNumber Numéro de la page (-1 pour obtenir la liste complète des entités)
     * @return
     */
    List<?> listForQueryName(String queryNameOrString, String[] properties, Object[] values,
        int pageNumber);

    /**
     * 
     * @param property
     * @param value
     * @return
     */
    List<T> listByProperty(String property, Object value);

    /**
     * 
     * @param property
     * @param value
     * @return
     */
    List<T> listByProperty(String[] property, Object[] value);

    /**
     * Retourne la liste de toutes les propriétés uniques d'une entité, triées selon l'ordre
     * spécifié.
     * 
     * @param property Nom de la propriété unique
     * @param sortOrder Ordre de tri sur cette propriété ({@link SortFilterPagingCriteria}
     * @return Liste des valeurs uniques de cette propriété
     */
    List<?> findAllUniqueProperty(String property, String sortOrder);
    
    /**
     * Retourne le nombre total de records
     * 
     * @param queryNameOrString
     * @param properties
     * @param values
     * @return
     */
    Long getCount(String queryNameOrString, String[] properties, Object[] values);
    
    /**
     * 
     * @param queryNameOrString
     * @param properties
     * @param values
     * @param maxResults Nombre maximum de lignes à retourner
     * @return
     */
    List<?> listForQueryNameWithMaxResults(String queryNameOrString, String[] properties, Object[] values,
        int maxResults);
    
    /**
     * 
     * @param queryNameOrString
     * @param properties
     * @param values
     * @param firstResults Nombre de lignes a sauter
     * @return
     */
    List<?> listForQueryNameWithFirstResults(String queryNameOrString, String[] properties, Object[] values,
        int firstResults);
}
