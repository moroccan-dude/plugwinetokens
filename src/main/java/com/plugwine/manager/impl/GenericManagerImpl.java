package com.plugwine.manager.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.plugwine.dao.GenericDao;
import com.plugwine.manager.GenericManager;


/**
 * Implémentation d'un gestionnaire d'entités générique.
 * 
 * @param <T> l'entité à gérer
 * @param <ID> le type de l'identifiant de l'entité
 * @author fchopard
 */
@Service
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public abstract class GenericManagerImpl<T, ID extends Serializable> extends AbstractManagerImpl implements
    GenericManager<T, ID> {

    /**
     * DAO générique correspondant à l'entité.
     */
    private GenericDao<T, ID> dao;

    /**
     * Constructeur pour les sous-classes.
     */
    protected GenericManagerImpl() {
        super();
    }

    /**
     * Constructeur pour instancier directement un manager depuis Spring sans créer de sous-classe.
     * 
     * @param dao le dao associé à ce manager
     */
    public GenericManagerImpl(GenericDao<T, ID> dao) {
        super();
        this.dao = dao;
    }

    protected final GenericDao<T, ID> getGenericDao() {
        return dao;
    }

    public final void setDao(GenericDao<T, ID> dao) {
        this.dao = dao;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean exists(ID id) {
        T entity = dao.findById(id);
        return entity != null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final T get(ID id) {
        T entity = dao.findById(id);
        return entity;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final T getWithDependencies(ID id) {
        T entity = get(id);
        loadDependencies(entity);
        return entity;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final List<T> getAll() {
        List<T> entities = dao.findAll();
        return entities;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<T> getAllWithDependencies() {
        List<T> entities = dao.findAll();//getAll();
        for (T entity : entities) {
            loadDependencies(entity);
        }
        return entities;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<T> getAllByIds(List<ID> ids) {
        return dao.findAllByIds(ids);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public final void delete(T entity) {
        dao.delete(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public final T persist(T entity) {
        return dao.persist(entity);
    }

    /**
     * Charge les dépendances via hibernate de l'entité.
     */
    protected abstract void loadDependencies(T entity);

    /**
     * Une serie de methodes generiques
     */
    /**
     * 
     * @param property
     * @param value
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public T getByUniqueProperty(String property, Object value) {
        return dao.getByUniqueProperty(property, value);
    }

    /**
     * 
     * @param queryNameOrString
     * @param properties
     * @param values
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public T getUnique(String queryNameOrString, String[] properties, Object[] values) {
        return dao.getUnique(queryNameOrString, properties, values);
    }

    /**
     * 
     * @param queryNameOrString
     * @param values
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public T getUnique(String queryNameOrString, Object[] values) {
        return dao.getUnique(queryNameOrString, values);
    }

    /**
     * 
     * @param queryNameOrString
     * @param properties
     * @param values
     * @param pageNumber Numéro de la page (-1 pour obtenir la liste complète des entités)
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<?> listForQueryName(String queryNameOrString, String[] properties, Object[] values,
        int pageNumber) {
        return dao.listForQueryName(queryNameOrString, properties, values, pageNumber);
    }

    /**
     * 
     * @param property
     * @param value
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<T> listByProperty(String property, Object value) {
        return dao.listByProperty(property, value);
    }

    /**
     * 
     * @param property
     * @param value
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<T> listByProperty(String[] property, Object[] value) {
        return dao.listByProperty(property, value);
    }
}
