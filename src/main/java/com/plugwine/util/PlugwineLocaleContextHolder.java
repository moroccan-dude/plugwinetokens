package com.plugwine.util;

import java.util.Locale;

import org.springframework.context.i18n.LocaleContext;
import org.springframework.context.i18n.SimpleLocaleContext;

import org.springframework.core.NamedInheritableThreadLocal;
import org.springframework.core.NamedThreadLocal;


public class PlugwineLocaleContextHolder {

	private static final ThreadLocal<LocaleContext> localeContextHolder =
		new NamedThreadLocal<LocaleContext>("Locale context");

	private static final ThreadLocal<LocaleContext> inheritableLocaleContextHolder =
		new NamedInheritableThreadLocal<LocaleContext>("Locale context");


	/**
	 * Reset the LocaleContext for the current thread.
	 */
	public static void resetLocaleContext() {
		localeContextHolder.remove();
		inheritableLocaleContextHolder.remove();
	}
	
	/**
	 * Associate the given LocaleContext with the current thread,
	 * <i>not</i> exposing it as inheritable for child threads.
	 * @param localeContext the current LocaleContext
	 */
	public static void setLocaleContext(LocaleContext localeContext) {
		setLocaleContext(localeContext, false);
	}

	/**
	 * Associate the given LocaleContext with the current thread.
	 * @param localeContext the current LocaleContext,
	 * or <code>null</code> to reset the thread-bound context
	 * @param inheritable whether to expose the LocaleContext as inheritable
	 * for child threads (using an {@link java.lang.InheritableThreadLocal})
	 */
	public static void setLocaleContext(LocaleContext localeContext, boolean inheritable) {
		if (localeContext == null) {
			resetLocaleContext();
		}
		else {
			if (inheritable) {
				inheritableLocaleContextHolder.set(localeContext);
				localeContextHolder.remove();
			}
			else {
				localeContextHolder.set(localeContext);
				inheritableLocaleContextHolder.remove();
			}
		}
	}
	
	/**
	 * Return the LocaleContext associated with the current thread, if any.
	 * @return the current LocaleContext, or <code>null</code> if none
	 */
	public static LocaleContext getLocaleContext() {
		LocaleContext localeContext = localeContextHolder.get();
		if (localeContext == null) {
			localeContext = inheritableLocaleContextHolder.get();
		}
		return localeContext;
	}

	/**
	 * Associate the given Locale with the current thread.
	 * <p>Will implicitly create a LocaleContext for the given Locale,
	 * <i>not</i> exposing it as inheritable for child threads.
	 * @param locale the current Locale, or <code>null</code> to reset
	 * the thread-bound context
	 * @see SimpleLocaleContext#SimpleLocaleContext(java.util.Locale)
	 */
	public static void setLocale(Locale locale) {
		setLocale(locale, false);
	}

	/**
	 * Associate the given Locale with the current thread.
	 * <p>Will implicitly create a LocaleContext for the given Locale.
	 * @param locale the current Locale, or <code>null</code> to reset
	 * the thread-bound context
	 * @param inheritable whether to expose the LocaleContext as inheritable
	 * for child threads (using an {@link java.lang.InheritableThreadLocal})
	 * @see SimpleLocaleContext#SimpleLocaleContext(java.util.Locale)
	 */
	public static void setLocale(Locale locale, boolean inheritable) {
		LocaleContext localeContext = (locale != null ? new SimpleLocaleContext(locale) : null);
		setLocaleContext(localeContext, inheritable);
	}

	/**
	 * Return the Locale associated with the current thread, if any,
	 * or the system default Locale else.
	 * @return the current Locale, or the system default Locale if no
	 * specific Locale has been associated with the current thread
	 * @see LocaleContext#getLocale()
	 * @see java.util.Locale#getDefault()
	 */
	public static Locale getLocale() {
		LocaleContext localeContext = getLocaleContext();
		return (localeContext != null ? localeContext.getLocale() : Locale.getDefault());
	}

}

