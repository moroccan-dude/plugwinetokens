package com.plugwine.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

/**
 * Wrappers around new hibernate functionality.  Allows most spring 2 
 * style dao's to operate unchanged after being 
 * upgraded to Spring 3 and Hibernate 4.
 * @author Sam Thomas
 *
 */
@Transactional
public class Hibernate4DaoSupport {
	private SessionFactory sessionFactory;

	// I cheated!
	protected Hibernate4DaoSupport getHibernateTemplate(){
		return this;
	}
	
	public void deleteEntity(Object entity) 
	{
		getCurrentSession().delete(entity);
	}
	
	public void flush(){
		getCurrentSession().flush();
	}
	
	public void clear(){
		getCurrentSession().clear();
	}

	public <T> void deleteAll(Collection<T> items) {
		for(T item : items){
			deleteEntity(item);
		}
	}
	
	public void saveOrUpdate(Object obj){
		getCurrentSession().saveOrUpdate(obj);
	}
	
	public void save(Object obj){
		getCurrentSession().save(obj);
	}

	protected Session getCurrentSession(){
		return sessionFactory.getCurrentSession();
	}
	
	/**
	 * Simulate spring 2's "getHibernateTemplate().get()" method.
	 */
	protected <T> T get(Class<T> clazz, final Serializable id){
		@SuppressWarnings("unchecked")
		T result = (T) getCurrentSession().get(clazz, id);
		return result;
	}
	
	@SuppressWarnings("deprecation")
	protected <T> T get(Class<T> clazz, final Serializable id, LockMode lockMode){
		@SuppressWarnings("unchecked")
		T result = (T) getCurrentSession().get(clazz, id, lockMode);
		return result;
	}

	/**
	 * Simulate spring 2's "getHibernateTemplate().loadAll()" method.
	 */
	@SuppressWarnings("unchecked")
	protected <T> List<T> loadAll(Class<T> clazz){
		return getCurrentSession().createCriteria(clazz).list();
	}

	@SuppressWarnings("unchecked")
	protected <T> List<T> find(String query){
		return getCurrentSession().createQuery(query).list();
	}
	
	@SuppressWarnings("unchecked")
	protected <T> List<T> find(String query, List<Object> args){
		Query q = getCurrentSession().createQuery(query);

    	for(int i=0; i<args.size(); i++){
    		q.setParameter(i, args.get(i));
    	}
    	
    	return q.list();
	}
	
	@SuppressWarnings("unchecked")
	protected <T> List<T> find(String query, Object ...args){
		Query q = getCurrentSession().createQuery(query);

    	for(int i=0; i<args.length; i++){
    		q.setParameter(i, args[i]);
    	}
    	
    	return q.list();
	}

	@SuppressWarnings("unchecked")
	protected <T> List<T> findByCriteria(DetachedCriteria criteria){
		return criteria.getExecutableCriteria(getCurrentSession()) .list();
	}

	@SuppressWarnings("unchecked")
	protected <T> List<T> findByCriteria(DetachedCriteria criteria, int firstResult, int maxResults){
		return criteria.getExecutableCriteria(getCurrentSession())
				.setFirstResult(firstResult)
				.setMaxResults(maxResults)
				.list();
	}
	
	@SuppressWarnings("unchecked")
	public <T> T merge(final T entity) throws DataAccessException 
	{
		return (T) getCurrentSession().merge(entity);
	}
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
}