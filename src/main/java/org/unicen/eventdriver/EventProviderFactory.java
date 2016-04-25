package org.unicen.eventdriver;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

public class EventProviderFactory {
	
	private final EventHandler handler;
	
	public EventProviderFactory(EventHandler handler) {
		this.handler = handler;
	}

	public <T> T resolveProviders(T instance) throws Exception {
		
	    T wrappedInstance = resolveEventWrappers(instance);
	    
	    injectEventProviders(wrappedInstance);
	    
	    return wrappedInstance;
	}
	
	private <T> T resolveEventWrappers(T instance) throws Exception {
	    
	    T latestWrapper = instance;
        
        Class<?>[] interfaces = instance.getClass().getInterfaces();
        for(Class<?> interfaceClass : interfaces) {
            
            EventWrapper eventWrapperAnn = interfaceClass.getAnnotation(EventWrapper.class);
            
            if(eventWrapperAnn != null) {
                Class<?>[] wrappers = eventWrapperAnn.value();
                
                for(Class<?> wrapperClass : wrappers) {
                
                    if(!interfaceClass.isAssignableFrom(wrapperClass)) {
                        throw new IllegalStateException(String.format("EventWrapper Class %s must implement %s", wrapperClass, interfaceClass));
                    }
               
                    Constructor<?> wrapperConstructor = wrapperClass.getDeclaredConstructor(interfaceClass);
                    wrapperConstructor.setAccessible(true);
                    
                    @SuppressWarnings("unchecked")
                    T wrapperInstance = (T) wrapperConstructor.newInstance(latestWrapper);
                    
                    latestWrapper = wrapperInstance;
                }
            }
        }
        
        return latestWrapper;
	}
	
	
	private void injectEventProviders(Object instance) throws Exception {
        
	    // TODO: inject providers to nested fields	    
	    
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
	}
}
