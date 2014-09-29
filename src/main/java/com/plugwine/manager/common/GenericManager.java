package com.plugwine.manager.common;

import java.io.Serializable;
import java.util.List;

/**
 * Generic Interface for the entity manager layer
 *
 * 
 * @param <T> the entity type handled by this manager
 * @param <ID> the key type for the objects of type <T>
 */
public interface GenericManager<T, ID extends Serializable> extends AbstractManager {

    /**
     * Returns an entity given its id
     * 
     * @param id entity id
     * @return the matching entity or <code>null</code> if not found
     */
    T get(ID id);

    /**
     * Returns an entity given its id including the dependents entities as defined per <code>loadDependencies()</code>
     * 
     * @param id the entity id
     * @return the matching entity or <code>null</code> if not found
     */
    T getWithDependencies(ID id);

    /**
     * Returns all the entities of type T handled by this manager.
     * 
     * @return the entity list
     */
    List<T> getAll();

    /**
     * Returns all entities of this type including the dependents entities as defined per <code>loadDependencies()</code>
     * 
     * @return the entity list
     */
    List<T> getAllWithDependencies();

    /**
     * Returns the list of entities defined in the given list. 
     * Invalid ids will be ignored.
     * 
     * @return the list
     */
    List<T> getAllByIds(List<ID> ids);

    /**
     * Save a detached entity. The entity is created (if not) and saved.
     * 
     * @param entity the entity
     * @return the persisted entity
     */
    T persist(T entity);

    /**
     * delete an entity
     * 
     * @param entity to delete
     */
    void delete(T entity);

    /**
     * fetch an entity given its ID
     * 
     * @param id
     * @return <code>true</code> if the entity exists, <code>false</code> otherwise
     */
    boolean exists(ID id);

    /**
     * 
     * @param property
     * @param value
     * @return
     */
    T getByUniqueProperty(String property, Object value);

    /**
     * 
     * @param queryNameOrString
     * @param properties
     * @param values
     * @return
     */
    T getUnique(String queryNameOrString, String[] properties, Object[] values);

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
     * @param pageNumber (-1 to get all entities)
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

}
