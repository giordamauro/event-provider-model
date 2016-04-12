package org.unicen.operation;

import java.util.Date;

import org.unicen.eventdriver.ProviderClass;
import org.unicen.operation.event.OnFailEvent;
import org.unicen.operation.event.OnFinishEvent;
import org.unicen.operation.event.OnStartEvent;

public abstract class AbstractStartFinishOperation implements Operation, StartFinishEventProvider {

	@ProviderClass
	private StartFinishEventProvider provider;
	
	private final OperationContext context = new OperationContext(this);
	
	protected abstract void doExecute() throws Throwable;
	
	protected OperationContext getContext() {
		return context;
	}
	
	@Override
	public void execute() throws Throwable {
		provider.onStart();
		
		try {
			doExecute();

			provider.onFinish();
			
		} catch(Throwable e){
			
			provider.onFail(e);
		}
	}

	@Override
	public OnStartEvent onStart() {
		
		Date startDate = new Date();
		context.setContextData("startDate", startDate);
		
		return new OnStartEvent(context, startDate);
	}

	@Override
	public OnFinishEvent onFinish() {

		Date endDate = new Date();
		Date startDate = context.getContextData("startDate");
		
		long took = endDate.getTime() - startDate.getTime();
		
		context.setContextData("finishDate", endDate);
		context.setContextData("tookMillis", took);
		
		return new OnFinishEvent(context, endDate, took);
	}

	@Override
	public OnFailEvent onFail(Throwable exception) {
		
		Date failDate = new Date();
		Date startDate = context.getContextData("startDate");

		long took = failDate.getTime() - startDate.getTime();

		context.setContextData("failDate", failDate);
		context.setContextData("tookMillis", took);
		
		return new OnFailEvent(context, exception);
	}
}
