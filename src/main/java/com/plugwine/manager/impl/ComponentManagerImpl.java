package com.plugwine.manager.impl;


import org.springframework.stereotype.Service;

import com.plugwine.dao.ComponentDao;
import com.plugwine.domain.model.Component;
import com.plugwine.manager.ComponentManager;



@Service
public class ComponentManagerImpl extends GenericManagerImpl<Component, Integer> 
implements ComponentManager {

	public ComponentManagerImpl()
	{
		super();
	}
	
	public ComponentManagerImpl(ComponentDao dao)
	{
		super(dao);
	}
	
	protected ComponentDao getDao() {
        return (ComponentDao) getGenericDao();
    }

	@Override
	protected void loadDependencies(Component entity) {
	}
}
