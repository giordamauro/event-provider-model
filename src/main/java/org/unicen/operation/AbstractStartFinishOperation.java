package org.unicen.operation;

import org.unicen.eventdriver.ProviderClass;
import org.unicen.eventdriver.test.OnStartEvent;
import org.unicen.operation.event.OnFailEvent;
import org.unicen.operation.event.OnFinishEvent;

public abstract class AbstractStartFinishOperation implements Operation, StartFinishEventProvider {

	@ProviderClass
	private StartFinishEventProvider provider;
	
	public abstract void doExecute() throws Throwable;
	
	@Override
	public void execute() throws Throwable {
		provider.onStart();
		
		try {
			doExecute();

			provider.onFinish();
			
		} catch(Throwable e){
			
			provider.onFail();
		}
	}

	@Override
	public OnStartEvent onStart() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OnFinishEvent onFinish() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OnFailEvent onFail() {
		// TODO Auto-generated method stub
		return null;
	}
}
