package org.unicen.eventdriver;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class MapEventHandler implements EventHandler {

	private Map<EventProvider<?>, Set<Listener>> listeners = new HashMap<EventProvider<?>, Set<Listener>>();

	public <T extends Listener> void subscribeListener(EventProvider<? extends T> provider, T listener) {

		Objects.requireNonNull(provider);
		Objects.requireNonNull(listener);

		Set<Listener> subscribers = listeners.get(provider);
		if (subscribers == null) {
			subscribers = new HashSet<Listener>();
			listeners.put(provider, subscribers);
		}

		subscribers.add(listener);
	}

	/**
	 * Creates a provider instance wired to this handler, by wrapping the
	 * interface and delegating to the class.
	 * 
	 * @param implClass
	 * @return T extends EventProvider<?>
	 */
	public <T extends EventProvider<?>> T createProvider(T implementation) {

//		TODO: Check the interface & implementation -> listener + event provider
		
		Class<?>[] interfaces = implementation.getClass().getInterfaces();
		EventInvocationHandler eventInvocationHandler = new EventInvocationHandler(this, implementation);

		@SuppressWarnings("unchecked")
		T proxy = (T) Proxy.newProxyInstance(this.getClass().getClassLoader(), interfaces, eventInvocationHandler);

		return proxy;
	}
	
	private static class EventInvocationHandler implements InvocationHandler {

		private final MapEventHandler eventHandler;
		private final Object instance;
		
		public EventInvocationHandler(MapEventHandler eventHandler, Object instance) {
			this.eventHandler = eventHandler;
			this.instance = instance;
		}

		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			
			Object value = method.invoke(instance, args);
			
			Set<Listener> subscribers = eventHandler.listeners.get(instance);
			if(subscribers != null) {
				
				for(Listener listener : subscribers) {

					Method listenerMethod = findMethodByName(listener.getClass(), method.getName());
					listenerMethod.setAccessible(true);
					listenerMethod.invoke(listener, value);
				}
			}
			
			return value;
		}
		
		private Method findMethodByName(Class<?> aClass, String methodName) {
			
			Method[] methods = aClass.getDeclaredMethods();
			
			for(Method method : methods) {
				if(method.getName().equals(methodName)){
					return method;
				}
			}
			
			throw new IllegalStateException(String.format("Method name '%s' not found in class %s", methodName, aClass.getName()));
		}
	}
}
