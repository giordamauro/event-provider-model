package org.unicen.eventdriver.test;

import java.util.Date;

import org.unicen.eventdriver.ProviderClass;

public class UserClass implements SimpleEventProvider {

	@ProviderClass
	private SimpleEventProvider provider;

	public void execute() {
		
		provider.onStart();
		
	}
	
	public OnStartEvent onStart() {
		
		System.out.println("Creating OnStart Event");
		
		return new OnStartEvent(new Date());
	}
}
