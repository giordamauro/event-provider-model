package org.unicen.eventdriver.test;

import java.lang.reflect.Field;

import org.unicen.eventdriver.EventHandler;
import org.unicen.eventdriver.EventProvider;
import org.unicen.eventdriver.Listener;
import org.unicen.eventdriver.MapEventHandler;
import org.unicen.eventdriver.ProviderClass;

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
		injectProviders(handler, user);
		
		Listener listener = new SimpleListener(){

			public void onStart(OnStartEvent event) {
				
				System.out.println("On Start was called at: " + event.getDate());
			}};
		handler.subscribeListener(user, listener);
		
		user.execute();
	}
	
	private static void injectProviders(EventHandler handler, EventProvider<?> provider) throws Exception {
		
		Field[] fields = provider.getClass().getDeclaredFields();
		for(Field field : fields) {
		
			ProviderClass providerClassAnn = field.getAnnotation(ProviderClass.class);
			if(providerClassAnn != null) {
				
				EventProvider<?> wrappedProvider = handler.createProvider(provider);
				
				field.setAccessible(true);
				field.set(provider, wrappedProvider);
			}
		}
	}
}
