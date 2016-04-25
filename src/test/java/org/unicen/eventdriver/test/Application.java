package org.unicen.eventdriver.test;

import org.unicen.eventdriver.EventHandler;
import org.unicen.eventdriver.EventListener;
import org.unicen.eventdriver.EventProviderFactory;
import org.unicen.eventdriver.MapEventHandler;

/**
 * Spring Boot Main Class. Holds the default Spring Boot default configuration.
 */
public class Application {

	/**
	 * Main method used to start up the Spring Boot API.
	 * 
	 * @param args
	 */
	public static void main(String[] args) throws Exception {

		EventHandler handler = new MapEventHandler();
		EventProviderFactory providerFactory = new EventProviderFactory(handler);

		UserClass user = providerFactory.resolveProviders(new UserClass());

		EventListener listener = new SimpleListener() {

			public void onStart(OnStartEvent event) {

				System.out.println("On Start was called at: " + event.getDate());
			}
		};
		handler.subscribeListener(user, listener);

		user.execute();
	}

}
