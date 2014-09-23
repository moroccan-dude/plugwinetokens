package com.plugwine.manager.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import com.plugwine.dao.ConfigurationVariableDao;
import com.plugwine.domain.holder.VariableHolder;
import com.plugwine.domain.model.ConfigurationVariable;
import com.plugwine.domain.model.ConfigurationVariableValue;
import com.plugwine.manager.ConfigurationVariableManager;


// Note:
// For some unknown reason the transational annotation at the class level does not always work.
// Therefore, if a manager method requires a transaction execution, make sure to annotate it directly by the method.
// (i.e do not solely rely on the annotations defined in GenericManagerImpl.
@Service
public class ConfigurationVariableManagerImpl extends GenericManagerImpl<ConfigurationVariable, Long> 
implements ConfigurationVariableManager {

//	@Autowired
//    private ConfigurationVariableDao configurationVariableDao;
//	public void setConfigurationVariableDao(ConfigurationVariableDao configurationVariableDao) 
//	{
//		this.configurationVariableDao = configurationVariableDao;
//	}
	public ConfigurationVariableManagerImpl()
	{
		super();
	}
	
	public ConfigurationVariableManagerImpl(ConfigurationVariableDao dao)
	{
		super(dao);
	}
	
	protected ConfigurationVariableDao getDao() {
        return (ConfigurationVariableDao) getGenericDao();
    }
	
	@Override
	public VariableHolder findVariableByName(String name)
	{
		ConfigurationVariable configurationVariable = getDao().getVariableByName(name);
		if(configurationVariable==null)
			return null;
		
		return new VariableHolder((String)configurationVariable.getName(),getValues(configurationVariable));
	}
	
	private String getValues(ConfigurationVariable variable)
	{
		Set<ConfigurationVariableValue> values = variable.getConfigurationVariableValues();
		String stringVals = "";
		for(ConfigurationVariableValue val : values)
		{
			stringVals += (String)val.getId().getValue() + ","; 
		}
		if (stringVals.endsWith(",")) 
			stringVals = stringVals.substring(0,stringVals.length()-1);
		
		return stringVals;
	}
	
	@Override
	public List<VariableHolder> findAllVariables() 
	{
		List<ConfigurationVariable> variables =  getDao().findAllVariables();
		
		if(variables==null || variables.size()<=0)
			return new ArrayList<VariableHolder>();
		
		ArrayList<VariableHolder> tokens = new ArrayList<VariableHolder>(variables.size());
		for (ConfigurationVariable variable : variables)
		{	
			tokens.add(new VariableHolder((String)variable.getName(),getValues(variable)));
		}
		
//		tokens.add(new TokenHolder("CONNECTIONSTRING","param1Value","Recette","Recette, PP, Prod Plugwine","WinID- CopyReleaseAndSetStatus (COMBINED)","R7_WINID"));
//		tokens.add(new TokenHolder("CONNECTIONSTRING","param2Value","Pre-Prod Amazon","PP, Prod","WinID- CopyReleaseAndSetStatus (COMBINED)","PP_WINID"));
//		tokens.add(new TokenHolder("CONNECTIONSTRING","param3Value","Pre-Prod Amazon","Recette, PP, Prod Plugwine","WinID- CopyReleaseAndSetStatus (COMBINED)","R7_WINID"));
//		tokens.add(new TokenHolder("CONNECTIONSTRING","param4Value","Prod Amazon","Recette, PP, Prod Plugwine","WinID- CopyReleaseAndSetStatus (COMBINED)","R7_WINID"));
//		tokens.add(new TokenHolder("CONNECTIONSTRING","param5Value","Prod Amazon","PP, Prod","WinID- CopyReleaseAndSetStatus (COMBINED)","PP_WINID"));
//		tokens.add(new TokenHolder("CONNECTIONSTRING","param6Value","Dev","CI DEV plugwine","WinID- CopyReleaseAndSetStatus (COMBINED)","CI_WINID"));
		
		
		return tokens;
	}

	@Override
	protected void loadDependencies(ConfigurationVariable entity) {
		if (entity != null) {
			//System.out.println("is inited? " + ((PersistentCollection) entity.getConfigurationVariableValues()).wasInitialized());
            Hibernate.initialize(entity.getConfigurationVariableValues());
            //System.out.println("is inited? " + ((PersistentCollection) entity.getConfigurationVariableValues()).wasInitialized());
        }
		
	}

}
