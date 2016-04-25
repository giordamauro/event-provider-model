package org.unicen.eventdriver;

public interface EventHandler {

	void subscribeListener(Object provider, EventListener listener);
	
	/**
	 * Creates a provider instance wired to this handler, 
	 * by wrapping the interface and delegating to the class.
	 * 
	 * @param implementation
	 * 
	 * @return T extends EventProvider<?>
	 */
	<T extends EventListener> T createProvider(Object provider, Class<T> listenerClass);
}
