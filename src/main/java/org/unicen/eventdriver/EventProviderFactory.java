package org.unicen.eventdriver;

import java.lang.reflect.Field;

public class EventProviderFactory {
	
	private final EventHandler handler;
	
	public EventProviderFactory(EventHandler handler) {
		this.handler = handler;
	}

	public <T> T resolveProviders(T instance) throws Exception {
		
		Field[] fields = instance.getClass().getDeclaredFields();
		for (Field field : fields) {

			EventProvider providerClassAnn = field.getAnnotation(EventProvider.class);
			if (providerClassAnn != null) {

				@SuppressWarnings("unchecked")
				Class<? extends EventListener> listenerClass = (Class<? extends EventListener>) field.getType();
				Object wrappedProvider = handler.createProvider(instance, listenerClass);

				field.setAccessible(true);
				field.set(instance, wrappedProvider);
			}
		}
		
		return instance;
	}
}
