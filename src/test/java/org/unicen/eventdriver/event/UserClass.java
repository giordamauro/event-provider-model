package org.unicen.eventdriver.event;

import java.util.Date;

import org.unicen.eventdriver.EventProvider;

public class UserClass {

	@EventProvider
	private SimpleListener provider;

	public void execute() {
		
		provider.onStart(new OnStartEvent(new Date()));
		
	}	
}
