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
 * Implémentation d'un DAO générique lié à une entité persistante. Cette implémentation peut être
 * utilisée soit directement comme un bean Spring en passant le type d'entité en paramètre du
 * constructeur, soit sous-classée pour y ajouter des méthodes spécifiques à cette entité.
 * 
 * @author fchopard
 * 
 * @param <T> le type d'objet traité par ce DAO
 * @param <ID> le type de la clé primaire des objets de type <T>
 */
@Repository
public class GenericDaoHibernate<T, ID extends Serializable> extends Hibernate4DaoSupport implements
    GenericDao<T, ID> {

    /**
     * Logger
     */
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    /**
     * Type de l'entité gérée par ce DAO
     */
    private final Class<T> entityType;

	private AppInfo appInfo;
    
    /**
     * Constructeur pour les sous-classes. Le type de la classe des entités est récupéré
     * automatiquement de la sous-classe.
     */
    protected GenericDaoHibernate() {
        ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
        @SuppressWarnings("unchecked")
        Class<T> entityClass = ((Class<T>) parameterizedType.getActualTypeArguments()[0]);
        entityType = entityClass;
    }

    /**
     * Constructeur pour instancier directement un DAO générique depuis Spring sans créer de
     * sous-classe.
     * 
     * @param entityClass la classe de l'entité
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
         * Il est nécessaire de réattacher une entité pour pouvoir la supprimer.
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
     * Retourne tous les objets disponible pour la propriété property dans le cadre d'une relation
     * One To One sur cette propriété. Les objets sont considérés comme disponible si et seulement
     * si ils ne sont pas déjà utilisé par une autre instance pour la même propriété.
     * 
     * Cela se traduit par l'appel de la requête nommée
     * "findAvailablesObjectsFor${EntityName}${PropertyName}" avec comme paramètre nommé l'id passé
     * en paramètre si la requête en a besoin.
     * 
     * @param property le nom de la propriété visée
     * @param entityId l'id de l'entité pour laquelle il faut récupérer les objets disponible.
     * 
     * @return Retourne tous les objets disponible pour la propriété property <br>
     *         TODO
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
    	// On essaie par le nom.
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
        // Si cela ne marche pas on utile la chaine
        if (query == null) {
            query = getCurrentSession().createQuery(queryNameOrString);
        }
        // S'il y a des parametres
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

       // On retourne une liste
       return (Long) query.uniqueResult();
    }
    
    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public T getUnique(String queryNameOrString, String[] properties, Object[] values) {
        //
        // On essaie par le nom.
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
        // Si cela ne marche pas on utile la chaine
        if (query == null) {
            query = getCurrentSession().createQuery(queryNameOrString);
        }

        // S'il y a des parametres
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
     * {@inheritDoc}
     */
    @Override
    public Object getUniqueFromSql(String queryNameOrString, String[] properties, Object[] values) {
        //
        // On essaie par le nom.
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
        // Si cela ne marche pas on utile la chaine
        if (query == null) {
            query = getCurrentSession().createQuery(queryNameOrString);
        }
        
        // S'il y a des parametres
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

        return query.uniqueResult();
    }

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
    @SuppressWarnings("unchecked")
    public T getUnique(String queryNameOrString, Object[] values) {
        //
        // On essaie par le nom.
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
        // Si cela ne marche pas on utile la chaine
        if (query == null) {
            query = getCurrentSession().createQuery(queryNameOrString);
        }

        // S'il y a des parametres
        if (query.getNamedParameters().length > 0) {
            String[] properties = query.getNamedParameters();
            for (int i = 0; i < properties.length && i < values.length; i++) {
                query.setParameter(properties[i], values[i]);
            }
        }

        return (T) query.uniqueResult();
    }

    /**
     * Retourne l'objet disponible dont la propriété unique spécifiée a la valeur indiquée.
     * 
     * Cela se traduit par l'appel de la requête nommée "get${EntityName}By${PropertyName}" avec la
     * valeur spécifiée pour la propriété.
     * 
     * @param property Nom d'une propriété unique
     * @param value Valeur de la propriété pour laquelle il faut récupérer un objet.
     * @param pageNumber Numéro de la page (-1 pour obtenir la liste complète des entités)
     * @return Retourne l'unique objet dont la propriété a la valeur spécifiée, ou <code>null</code>
     *         si aucun objet n'a cette propriété avec cette valeur.
     */
    @Override
    public List<?> listForQueryName(String queryNameOrString, String[] properties, Object[] values,
        int pageNumber) {

        // On essaie par le nom.
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
        // Si cela ne marche pas on utile la chaine
        if (query == null) {
            query = getCurrentSession().createQuery(queryNameOrString);
        }
        // S'il y a des parametres
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

        // On retourne une liste
        return query.list();
    }

    /**
     * @see listForQueryName
     * 
     */
    @Override
    public List<?> listForQueryNameWithMaxResults(String queryNameOrString, String[] properties, Object[] values,
        int maxResults) {

        // On essaie par le nom.
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
        // Si cela ne marche pas on utile la chaine
        if (query == null) {
            query = getCurrentSession().createQuery(queryNameOrString);
        }
        // S'il y a des parametres
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

        // On retourne une liste
        return query.list();
    }
    
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
    @SuppressWarnings("unchecked")
    public List<T> listByProperty(String property, Object value) {
        String queryName = "list" + entityType.getSimpleName()
            + "By"
            + WordUtils.capitalize(property);

        // On essaie par le nom.
        Query query = null;
        try {
            query = getCurrentSession().getNamedQuery(queryName);
        } catch (Exception ex) {
            LOGGER.debug("Unable to find Query :" + queryName + " - Exception :" + ex.getMessage());
            query = null;
        }

        // Si cela ne marche pas on utile la chaine
        if (query == null) {
            String queryString = "from " + entityType.getSimpleName()
                + " as entity where entity."
                + property
                + " = :"
                + property;
            query = getCurrentSession().createQuery(queryString);
        }

        // S'il y a des parametres
        if (query.getNamedParameters().length > 0) {
            if (value instanceof Collection) {
                query.setParameterList(property, (Collection<?>) value);
            } else {
                query.setParameter(property, value);
            }
        }

        // On retourne une liste
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

        // S'il y a des parametres
        if (query.getNamedParameters().length > 0) {
            for (int i = 0; i < property.length && i < value.length; i++) {
                query.setParameter(property[i], value[i]);
            }
        }

        // On retourne une liste
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
    public final List<T> findByExample(final T entiteExemple, final String... proprietesExclues) 
    {
        Criteria crit = getCurrentSession().createCriteria(entityType);
        Example example = Example.create(entiteExemple);
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
     * Ajoute des critères de pagination sur une requête Hibernate.
     * 
     * @param query Requête Hibernate
     * @param pageNumber Numéro de la page courante
     * @param searchPageSize Nombre maximum d'éléments dans la page courante
     * @return Requête Hibernate mise à jour
     */
    protected Query insertPagingCriteria(Query query, int pageNumber, int searchPageSize) {
        // Gestion de la pagination
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
        // On essaie par le nom.
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
        // Si cela ne marche pas on utile la chaine
        if (query == null) {
            query = getCurrentSession().createQuery(queryNameOrString);
        }
        // S'il y a des parametres
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

        // On retourne une liste
        return query.list();
    }
    
    public void setAppInfo(AppInfo appInfo) {
        this.appInfo = appInfo;
    }

}
