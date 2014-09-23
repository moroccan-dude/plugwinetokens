package com.plugwine.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.WordUtils;
import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.plugwine.dao.GenericDao;
import com.plugwine.dao.Hibernate4DaoSupport;
import com.plugwine.util.AppInfo;
import com.plugwine.util.SortFilterPagingCriteria;

/**
 * Generic Hibernate implementation of the DAO interface for the entities of type T
 * 
 * 
 * @param <T> the entity Type handled by this DAO
 * @param <ID> the key type for the objects of type <T>
 */
@Repository
public class GenericDaoHibernate<T, ID extends Serializable> extends Hibernate4DaoSupport implements
    GenericDao<T, ID> {

    /**
     * Logger
     */
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    /**
     * The entity type handled by this DAO.
     */
    private final Class<T> entityType;

	private AppInfo appInfo;
    
    /**
     * Constructor. The entity type is automatically detected.
     */
    protected GenericDaoHibernate() {
        ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
        @SuppressWarnings("unchecked")
        Class<T> entityClass = ((Class<T>) parameterizedType.getActualTypeArguments()[0]);
        entityType = entityClass;
    }

    /**
     * Constructor.
     * 
     * @param entityClass
     */
    public GenericDaoHibernate(Class<T> entityClass) {
        entityType = entityClass;
    }

    protected final Logger getLogger() {
        return LOGGER;
    }

    /**
     * {@inheritDoc}
     */
    public final T persist(T entity) {
        entity = getHibernateTemplate().merge(entity);
        return entity;
    }

    /**
     * {@inheritDoc}
     */
    public final void delete(T entity) {
        /*
         * attaching an entity in order to delete it.
         */
        entity = persist(entity);
        getHibernateTemplate().deleteEntity(entity);
    }

	/**
     * {@inheritDoc}
     */
    @Override
    public final void flush() {
        getHibernateTemplate().flush();
    }


	/**
     * {@inheritDoc}
     */
    @Override
    public final void clear() {
        getHibernateTemplate().clear();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final T findById(ID id) {
        return findById(id, false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final T findById(ID id, boolean lock) {
        T entity;
        if (lock) {
            entity = get(entityType, id, LockMode.WRITE);
        } else {
            entity = get(entityType, id);
        }
        return entity;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final List<T> findAll() {
        List<T> entities = findByCriteria(
            DetachedCriteria.forClass(entityType));
        return entities;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<T> findAllByIds(List<ID> ids) {
        if (ids == null || ids.isEmpty()) {
            return new ArrayList<T>();
        }
        Query query = getCurrentSession().getNamedQuery("findAll" + entityType.getSimpleName() + "ByIds");
        query.setParameterList("ids", ids);
        return (List<T>) query.list();
    }

    /**
     * 
     * Invoking the named query:
     * "findAvailablesObjectsFor${EntityName}${PropertyName}"
     * 
     */
    protected List<?> findAvailablesObjectsForProperty(String property, ID entityId) {
        String queryName = "findAvailablesObjectsFor" + entityType.getSimpleName()
            + WordUtils.capitalize(property);
        Query query = getCurrentSession().getNamedQuery(queryName);
        if (query.getNamedParameters().length > 0) {
            query.setParameter(property, entityId);
        }
        return query.list();
    }

    /**
     * Invoking the named query:
     * "get${EntityName}By${PropertyName}"
     * 
     */
    @SuppressWarnings("unchecked")
    public T getByUniqueProperty(String property, Object value) {
        String queryName = "get" + entityType.getSimpleName()
            + "By"
            + WordUtils.capitalize(property);
        Query query = getCurrentSession().getNamedQuery(queryName);
        if (query.getNamedParameters().length > 0) {
            query.setParameter(property, value);
        }
        return (T) query.uniqueResult();
    }
    
    @SuppressWarnings("unchecked")
    public T getByUniqueProperties(String property1, Object value1, String property2, Object value2) {
        String queryName = "get" + entityType.getSimpleName()
            + "By"
            + WordUtils.capitalize(property1)
            + "And"
            + WordUtils.capitalize(property2);
        Query query = getCurrentSession().getNamedQuery(queryName);
        if (query.getNamedParameters().length > 0) {
            query.setParameter(property1, value1);
            query.setParameter(property2, value2);
        }
        return (T) query.uniqueResult();
    }

    
    @SuppressWarnings("unchecked")
    public T getByUniqueProperties(String property1, Object value1, String property2, Object value2,
    		String property3, String value3) 
    {
        String queryName = "get" + entityType.getSimpleName()
            + "By"
            + WordUtils.capitalize(property1)
            + "And"
            + WordUtils.capitalize(property2)
            + "And"
            + WordUtils.capitalize(property3);
        Query query = getCurrentSession().getNamedQuery(queryName);
        if (query.getNamedParameters().length > 0) {
            query.setParameter(property1, value1);
            query.setParameter(property2, value2);
            query.setParameter(property3, value3);
        }
        return (T) query.uniqueResult();
    }
    
    /**
     * Used for count queries
     * @param queryNameOrString
     * @param properties
     * @param values
     * @return
     */
    public Long getCount(String queryNameOrString, String[] properties, Object[] values)
    {
    	// let's try the query name
        Query query = null;
        try {
            if (!queryNameOrString.contains(" ")) {
                query = getCurrentSession().getNamedQuery(queryNameOrString);
            }
        } catch (Exception ex) {
            LOGGER.debug("Unable to find Query :" + queryNameOrString
                + " - Exception :"
                + ex.getMessage());
            query = null;
        }
        //
        // falling back to the full string
        if (query == null) {
            query = getCurrentSession().createQuery(queryNameOrString);
        }
        // if there are any parameters
        if (query.getNamedParameters().length > 0) {
            Object value;
            for (int i = 0; i < properties.length && i < values.length; i++) {
                value = values[i];
                if (value instanceof Collection) {
                    query.setParameterList(properties[i], (Collection<?>) value);
                } else {
                    query.setParameter(properties[i], value);
                }
            }
        }

       return (Long) query.uniqueResult();
    }
    
    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public T getUnique(String queryNameOrString, String[] properties, Object[] values) {
        //
        Query query = null;
        try {
            if (!queryNameOrString.contains(" ")) {
                query = getCurrentSession().getNamedQuery(queryNameOrString);
            }
        } catch (Exception ex) {
            LOGGER.debug("Unable to find Query :" + queryNameOrString
                + " - Exception :"
                + ex.getMessage());
            query = null;
        }
        //
        if (query == null) {
            query = getCurrentSession().createQuery(queryNameOrString);
        }

        if (query.getNamedParameters().length > 0) {
            Object value;
            for (int i = 0; i < properties.length && i < values.length; i++) {
                value = values[i];
                if (value instanceof Collection) {
                    query.setParameterList(properties[i], (Collection<?>) value);
                } else {
                    query.setParameter(properties[i], value);
                }
            }
        }

        return (T) query.uniqueResult();
    }

   
    /**
     * invoking the named query:
     * "get${EntityName}By${PropertyName}"
     * 
     */
    @SuppressWarnings("unchecked")
    public T getUnique(String queryNameOrString, Object[] values) {
        //
        Query query = null;
        try {
            if (!queryNameOrString.contains(" ")) {
                query = getCurrentSession().getNamedQuery(queryNameOrString);
            }
        } catch (Exception ex) {
            LOGGER.debug("Unable to find Query :" + queryNameOrString
                + " - Exception :"
                + ex.getMessage());
            query = null;
        }
        //
        if (query == null) {
            query = getCurrentSession().createQuery(queryNameOrString);
        }

        if (query.getNamedParameters().length > 0) {
            String[] properties = query.getNamedParameters();
            for (int i = 0; i < properties.length && i < values.length; i++) {
                query.setParameter(properties[i], values[i]);
            }
        }

        return (T) query.uniqueResult();
    }

    /**
     * invoking the named query:
     * "get${EntityName}By${PropertyName}"
     */
    @Override
    public List<?> listForQueryName(String queryNameOrString, String[] properties, Object[] values,
        int pageNumber) {

        Query query = null;
        try {
            if (!queryNameOrString.contains(" ")) {
                query = getCurrentSession().getNamedQuery(queryNameOrString);
            }
        } catch (Exception ex) {
            LOGGER.debug("Unable to find Query :" + queryNameOrString
                + " - Exception :"
                + ex.getMessage());
            query = null;
        }
        //
        if (query == null) {
            query = getCurrentSession().createQuery(queryNameOrString);
        }
        if (query.getNamedParameters().length > 0) {
            Object value;
            for (int i = 0; i < properties.length && i < values.length; i++) {
                value = values[i];
                if (value instanceof Collection) {
                    query.setParameterList(properties[i], (Collection<?>) value);
                } else {
                    query.setParameter(properties[i], value);
                }
            }
        }

        // Pagination
        insertPagingCriteria(query, pageNumber, appInfo.getMaxSearchPageSize());

        return query.list();
    }

    /**
     * @see listForQueryName
     * 
     */
    @Override
    public List<?> listForQueryNameWithMaxResults(String queryNameOrString, String[] properties, Object[] values,
        int maxResults) {

        Query query = null;
        try {
            if (!queryNameOrString.contains(" ")) {
                query = getCurrentSession().getNamedQuery(queryNameOrString);
            }
        } catch (Exception ex) {
            LOGGER.debug("Unable to find Query :" + queryNameOrString
                + " - Exception :"
                + ex.getMessage());
            query = null;
        }
        //
        if (query == null) {
            query = getCurrentSession().createQuery(queryNameOrString);
        }

        if (query.getNamedParameters().length > 0) {
            Object value;
            for (int i = 0; i < properties.length && i < values.length; i++) {
                value = values[i];
                if (value instanceof Collection) {
                    query.setParameterList(properties[i], (Collection<?>) value);
                } else {
                    query.setParameter(properties[i], value);
                }
            }
        }

        // Max results
        query.setMaxResults(maxResults);

        return query.list();
    }
    
    /**
     * 
     * Invoking the named query:
     * "list${EntityName}By${PropertyName}"
     * 
     */
    @SuppressWarnings("unchecked")
    public List<T> listByProperty(String property, Object value) {
        String queryName = "list" + entityType.getSimpleName()
            + "By"
            + WordUtils.capitalize(property);

        Query query = null;
        try {
            query = getCurrentSession().getNamedQuery(queryName);
        } catch (Exception ex) {
            LOGGER.debug("Unable to find Query :" + queryName + " - Exception :" + ex.getMessage());
            query = null;
        }

        if (query == null) {
            String queryString = "from " + entityType.getSimpleName()
                + " as entity where entity."
                + property
                + " = :"
                + property;
            query = getCurrentSession().createQuery(queryString);
        }

        if (query.getNamedParameters().length > 0) {
            if (value instanceof Collection) {
                query.setParameterList(property, (Collection<?>) value);
            } else {
                query.setParameter(property, value);
            }
        }

        return (List<T>) query.list();
    }

    /**
     * 
     * @param property
     * @param value
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<T> listByProperty(String[] property, Object[] value) {
        String queryString = "from " + entityType.getSimpleName() + " as entity where ";
        for (int i = 0; i < property.length && i < value.length; i++) {
            queryString += "entity." + property[i] + " = :" + property[i];
            if (i + 1 < property.length) {
                queryString += " and ";
            }

        }

        Query query = null;
        query = getCurrentSession().createQuery(queryString);

        if (query.getNamedParameters().length > 0) {
            for (int i = 0; i < property.length && i < value.length; i++) {
                query.setParameter(property[i], value[i]);
            }
        }

        return (List<T>) query.list();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final List<T> findByExample(T entiteExemple) {
        return findByExample(entiteExemple, (String[]) null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final List<T> findByExample(final T entiteExample, final String... proprietesExclues) 
    {
        Criteria crit = getCurrentSession().createCriteria(entityType);
        Example example = Example.create(entiteExample);
        if (proprietesExclues != null) {
            for (String exclue : proprietesExclues) {
                example.excludeProperty(exclue);
            }
        }
        crit.add(example);

        @SuppressWarnings("unchecked")
        List<T> entities = crit.list();
        return entities;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<?> findAllUniqueProperty(String property, String sortOrder) {
        Query query = getCurrentSession().createQuery(
            "select distinct " + property
                + " from "
                + entityType.getSimpleName()
                + " order by "
                + property
                + " "
                + sortOrder);

        return query.list();
    }

    /**
     * Amend pagination criteria on an hibernate query
     * 
     */
    protected Query insertPagingCriteria(Query query, int pageNumber, int searchPageSize) {

        if (pageNumber != SortFilterPagingCriteria.ALL_PAGES) {
            query.setFirstResult(pageNumber * searchPageSize);
            query.setMaxResults(searchPageSize);
        }
        return query;
    }
    
    /*
     * @see listForQueryName
     * 
     */
    @Override
    public List<?> listForQueryNameWithFirstResults(String queryNameOrString, String[] properties, 
    	Object[] values,int firstResults) 
    {
        Query query = null;
        try {
            if (!queryNameOrString.contains(" ")) {
                query = getCurrentSession().getNamedQuery(queryNameOrString);
            }
        } catch (Exception ex) {
            LOGGER.debug("Unable to find Query :" + queryNameOrString
                + " - Exception :"
                + ex.getMessage());
            query = null;
        }
        //
        if (query == null) {
            query = getCurrentSession().createQuery(queryNameOrString);
        }
        if (query.getNamedParameters().length > 0) {
            Object value;
            for (int i = 0; i < properties.length && i < values.length; i++) {
                value = values[i];
                if (value instanceof Collection) {
                    query.setParameterList(properties[i], (Collection<?>) value);
                } else {
                    query.setParameter(properties[i], value);
                }
            }
        }

        // First results
        query.setFirstResult(firstResults);
        query.setMaxResults(appInfo.getMaxSearchPageSize());

        return query.list();
    }
    
    public void setAppInfo(AppInfo appInfo) {
        this.appInfo = appInfo;
    }

}
