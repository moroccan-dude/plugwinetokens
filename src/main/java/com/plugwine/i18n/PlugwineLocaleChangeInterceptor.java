package com.plugwine.i18n;

import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


public class PlugwineLocaleChangeInterceptor extends HandlerInterceptorAdapter {
	
	private static final Logger logger = LoggerFactory.getLogger(PlugwineLocaleChangeInterceptor.class);
	
	/**
	 * Default name of the locale specification parameter: "locale".
	 */
	public static final String DEFAULT_PARAM_NAME = "accept-language";

	private String paramName = DEFAULT_PARAM_NAME;


	/**
	 * Set the name of the parameter that contains a locale specification
	 * in a locale change request. Default is "locale".
	 */
	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	/**
	 * Return the name of the parameter that contains a locale specification
	 * in a locale change request.
	 */
	public String getParamName() {
		return this.paramName;
	}
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws ServletException {

		String newLocaleString = request.getHeader(getParamName());
		if (newLocaleString != null) 
		{
			Locale newLocale = null;
			try
			{
				newLocale = StringUtils.parseLocaleString(newLocaleString);
				
			}
			catch(Exception illegalLocale)
			{
				logger.warn("Illegal Locale requested " + newLocaleString,illegalLocale);
			}
			
			PlugwineLocaleContextHolder.setLocale(newLocale,true);
			logger.info("Setting Request locale to : " + newLocale.toString());
		}
		// Proceed in any case.
		return true;
	}
}
