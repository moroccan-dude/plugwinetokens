package com.plugwine.manager.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;



import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import com.plugwine.dao.ConfigurationVariableDao;
import com.plugwine.domain.holder.TokenHolder;
import com.plugwine.domain.model.ConfigurationVariable;
import com.plugwine.domain.model.ConfigurationVariableValue;
import com.plugwine.manager.ConfigurationVariableManager;

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
	
	@Override
	public List<TokenHolder> findAllVariables() 
	{
		List<ConfigurationVariable> variables = getAllWithDependencies();
//		List<ConfigurationVariable> variables =  configurationVariableDao.findAllVariables();
		
		if(variables==null || variables.size()<=0)
			return new ArrayList<TokenHolder>();
		
		ArrayList<TokenHolder> tokens = new ArrayList<TokenHolder>(variables.size());
		for (ConfigurationVariable variable : variables)
		{
			Set<ConfigurationVariableValue> values = variable.getConfigurationVariableValues();
			String stringVals = "";
			for(ConfigurationVariableValue val : values)
			{
				stringVals += (String)val.getId().getValue() + ","; 
			}
			if (stringVals.endsWith(",")) 
				stringVals = stringVals.substring(0,stringVals.length()-1);
			tokens.add(new TokenHolder((String)variable.getName(),stringVals,"Recette","Recette, PP, Prod Plugwine","WinID- CopyReleaseAndSetStatus (COMBINED)","R7_WINID"));
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
            Hibernate.initialize(entity.getConfigurationVariableValues());
        }
		
	}

}
