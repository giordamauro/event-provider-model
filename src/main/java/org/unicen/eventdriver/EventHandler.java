package org.unicen.eventdriver;

public interface EventHandler {

	<T extends Listener> void subscribeListener(EventProvider<? extends T> provider, T listener);
	
	/**
	 * Creates a provider instance wired to this handler, 
	 * by wrapping the interface and delegating to the class.
	 * 
	 * @param implementation
	 * 
	 * @return T extends EventProvider<?>
	 */
	<T extends EventProvider<?>> T createProvider(T implementation);
}
