package org.unicen.eventdriver.test;

import java.lang.reflect.Field;

import org.unicen.eventdriver.EventHandler;
import org.unicen.eventdriver.EventListener;
import org.unicen.eventdriver.EventProvider;
import org.unicen.eventdriver.MapEventHandler;

/**
 * Spring Boot Main Class. Holds the default Spring Boot default configuration.  
 */
public class Application {
    
    /**
     * Main method used to start up the Spring Boot API.
     * @param args
     */
	public static void main(String[] args) throws Exception {
	    
		EventHandler handler = new MapEventHandler();
		
		UserClass user = new UserClass();
		
		EventListener listener = new SimpleListener(){

			public void onStart(OnStartEvent event) {
				
				System.out.println("On Start was called at: " + event.getDate());
			}};
		handler.subscribeListener(user, listener);
		
		injectProviders(handler, user);
		
		user.execute();
	}
	
	private static void injectProviders(EventHandler handler, Object provider) throws Exception {
		
		Field[] fields = provider.getClass().getDeclaredFields();
		for(Field field : fields) {
		
			EventProvider providerClassAnn = field.getAnnotation(EventProvider.class);
			if(providerClassAnn != null) {
			    
			    @SuppressWarnings("unchecked")
                Class<? extends EventListener> listenerClass =  (Class<? extends EventListener>) field.getType();
			    Object wrappedProvider = handler.createProvider(provider, listenerClass);
				
				field.setAccessible(true);
				field.set(provider, wrappedProvider);
			}
		}
	}
}
