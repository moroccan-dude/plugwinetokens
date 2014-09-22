package com.plugwine.util.transaction;



import org.hibernate.Session;
import org.springframework.core.NamedInheritableThreadLocal;
import org.springframework.core.NamedThreadLocal;


public class SessionContextHolder {

	private static final ThreadLocal<Session> sessionContextHolder =
			new NamedThreadLocal<Session>("Session context");

		private static final ThreadLocal<Session> inheritableSessionContextHolder =
			new NamedInheritableThreadLocal<Session>("Session context");


		/**
		 * Reset the sessionContext for the current thread.
		 */
		public static void resetSessionContext() {
			sessionContextHolder.remove();
			inheritableSessionContextHolder.remove();
		}
		
		/**
		 * Associate the given SessionContext with the current thread,
		 * <i>not</i> exposing it as inheritable for child threads.
		 * @param SessionContext the current SessionContext
		 */
//		private static void setSessionContext(Session session) {
//			setSessionContext(session, false);
//		}

		/**
		 * Associate the given SessionContext with the current thread.
		 * @param SessionContext the current SessionContext,
		 * or <code>null</code> to reset the thread-bound context
		 * @param inheritable whether to expose the SessionContext as inheritable
		 * for child threads (using an {@link java.lang.InheritableThreadLocal})
		 */
		private static void setSessionContext(Session session, boolean inheritable) {
			if (session == null) {
				resetSessionContext();
			}
			else {
				if (inheritable) {
					inheritableSessionContextHolder.set(session);
					sessionContextHolder.remove();
				}
				else {
					sessionContextHolder.set(session);
					inheritableSessionContextHolder.remove();
				}
			}
		}
		
		/**
		 * Return the SessionContext associated with the current thread, if any.
		 * @return the current SessionContext, or <code>null</code> if none
		 */
		public static Session getSessionContext() {
			Session session = sessionContextHolder.get();
			if (session == null) {
				session = inheritableSessionContextHolder.get();
			}
			return session;
		}

		/**
		 * Associate the given Locale with the current thread.
		 * <p>Will implicitly create a SessionContext for the given Locale,
		 * <i>not</i> exposing it as inheritable for child threads.
		 * @param locale the current Locale, or <code>null</code> to reset
		 * the thread-bound context
		 * @see SimpleSessionContext#SimpleSessionContext(java.util.Locale)
		 */
		public static void setSession(Session session) {
			setSession(session, false);
		}

		/**
		 * Associate the given Locale with the current thread.
		 * <p>Will implicitly create a SessionContext for the given Locale.
		 * @param locale the current Locale, or <code>null</code> to reset
		 * the thread-bound context
		 * @param inheritable whether to expose the SessionContext as inheritable
		 * for child threads (using an {@link java.lang.InheritableThreadLocal})
		 * @see SimpleSessionContext#SimpleSessionContext(java.util.Locale)
		 */
		public static void setSession(Session session, boolean inheritable) {
			setSessionContext(session, inheritable);
		}

		/**
		 * Return the Locale associated with the current thread, if any,
		 * or the system default Locale else.
		 * @return the current Locale, or the system default Locale if no
		 * specific Locale has been associated with the current thread
		 * @see SessionContext#getLocale()
		 * @see java.util.Locale#getDefault()
		 */
//		public static Locale getLocale() {
//			SessionContext SessionContext = getSessionContext();
//			return (SessionContext != null ? SessionContext.getLocale() : Locale.getDefault());
//		}

}

