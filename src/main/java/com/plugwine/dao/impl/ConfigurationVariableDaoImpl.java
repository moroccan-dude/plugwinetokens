package com.plugwine.dao.impl;

import java.util.ArrayList;
import java.util.List;

import com.plugwine.dao.ConfigurationVariableDao;
import com.plugwine.domain.holder.TokenHolder;
import com.plugwine.domain.model.ConfigurationVariable;

public class ConfigurationVariableDaoImpl extends GenericDaoHibernate<ConfigurationVariable, Long> 
     implements ConfigurationVariableDao
{
	@Override
	public List<TokenHolder> findAllTokens(String componentId) {

		ArrayList<TokenHolder> tokens = new ArrayList<TokenHolder>(3);
		tokens.add(new TokenHolder("CONNECTIONSTRING","param1Value","Recette","Recette, PP, Prod Plugwine","WinID- CopyReleaseAndSetStatus (COMBINED)","R7_WINID"));
		tokens.add(new TokenHolder("CONNECTIONSTRING","param2Value","Pre-Prod Amazon","PP, Prod","WinID- CopyReleaseAndSetStatus (COMBINED)","PP_WINID"));
		tokens.add(new TokenHolder("CONNECTIONSTRING","param3Value","Pre-Prod Amazon","Recette, PP, Prod Plugwine","WinID- CopyReleaseAndSetStatus (COMBINED)","R7_WINID"));
		tokens.add(new TokenHolder("CONNECTIONSTRING","param4Value","Prod Amazon","Recette, PP, Prod Plugwine","WinID- CopyReleaseAndSetStatus (COMBINED)","R7_WINID"));
		tokens.add(new TokenHolder("CONNECTIONSTRING","param5Value","Prod Amazon","PP, Prod","WinID- CopyReleaseAndSetStatus (COMBINED)","PP_WINID"));
		tokens.add(new TokenHolder("CONNECTIONSTRING","param6Value","Dev","CI DEV plugwine","WinID- CopyReleaseAndSetStatus (COMBINED)","CI_WINID"));
		return tokens;
	}
}
