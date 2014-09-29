package com.plugwine.dao.common;

import java.io.Serializable;
import java.util.List;

import com.plugwine.util.SortFilterPagingCriteria;

/**
 * Generic DAO interface for the entities of type T.
 * 
 * @param <T> the entity Type handled by this DAO
 * @param <ID> the key type for the objects of type <T>
 */
public interface GenericDao<T, ID extends Serializable> {

    /**
     * Returns an entity given its id.
     * 
     * @param id the entity id
     * @return the entity or <code>null</code> if not found
     */
    T findById(ID id);

    /**
     * Returns an entity given its id, with the possibility to issue a lock on this entity.
     * 
     * @param id the entity id
     * @param lock if <code>true</code>, the entity record will be locked
     * @return the entity or <code>null</code> if not found
     */
    T findById(ID id, boolean lock);

    /**
     * Return all entities of type T managed by this DAO.
     * 
     * @return the entity list
     */
    List<T> findAll();

    /**
     * @see #findByExample(Object, String...)
     */
    List<T> findByExample(T entiteExemple);

    /**
     * Returns all entities of type T managed by this DAO with an id in the given list. Invalid ids will be ignored.
     * 
     * @return the entity list with ids in the list.
     */
    List<T> findAllByIds(List<ID> ids);

    /**
     * Returns all matching entities using the example search. 
     * Note all reserved properties such as version, id as well as associations are ignored. 
     * Null values are also excluded.
     * 
     * @param entiteExample the example
     * @param proprietesExclues the properties to exclude from the search
     * @return the entity list
     */
    List<T> findByExample(T entiteExample, String... proprietesExclues);

    /**
     * Persist an entity.
     * 
     * @param entity
     * @return the persisted entity
     */
    T persist(T entity);

    /**
     * Delete an entity
     * 
     * @param entity the entity
     */
    void delete(T entity);

    /**
     * Force this DAO to synchronize with the persistence layer.
     */
    void flush();

    /**
     * Detach all entities
     */
    void clear();

    /**
     * 
     * @param property
     * @param value
     * @return
     */
    T getByUniqueProperty(String property, Object value);

    /**
     * Return the entity with the given properties and values
     * 
     * It will invoke a namedquery with the name as such "get${EntityName}By${PropertyName}" with the passed in property values
     * 
     * @param properties
     * @param values to used in the search.
     * 
     * @return Return the entity with the given properties and values, or <code>null</code> if there is no match.
     */
    T getUnique(String queryNameOrString, String[] properties, Object[] values);


    //Object getUniqueFromSql(String queryNameOrString, String[] properties, Object[] values);

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
     * @param pageNumber  (-1 to get all entities)
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
     * Return the list of all unique properties for an entity ordered according to sortOrder
     * 
     * @param property Name of the unique property
     * @param sortOrder sort order for this property ({@link SortFilterPagingCriteria}
     * @return
     */
    List<?> findAllUniqueProperty(String property, String sortOrder);
    
    /**
     * Return the total count of records
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
     * @param maxResults Maximum number of records to return
     * @return
     */
    List<?> listForQueryNameWithMaxResults(String queryNameOrString, String[] properties, Object[] values,
        int maxResults);
    
    /**
     * 
     * @param queryNameOrString
     * @param properties
     * @param values
     * @param firstResults first records to retrieve
     * @return
     */
    List<?> listForQueryNameWithFirstResults(String queryNameOrString, String[] properties, Object[] values,
        int firstResults);
}
