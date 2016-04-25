package org.unicen.operation;

import java.util.Date;

import org.unicen.eventdriver.EventProvider;
import org.unicen.operation.event.OnFailEvent;
import org.unicen.operation.event.OnFinishEvent;
import org.unicen.operation.event.OnStartEvent;
import org.unicen.operation.event.StartFinishListener;

class StartFinishEventProvider implements SimpleOperation {

	@EventProvider
	private StartFinishListener provider;
	
	private final SimpleOperation operation;
	
	public StartFinishEventProvider(SimpleOperation operation) {
	
		this.operation = operation;
	}

	@Override
	public void execute(OperationContext context) {
		
		provider.onStart(onStartEvent(context));
		
		try {
			operation.execute(context);

			provider.onFinish(onFinishEvent(context));
			
		} catch(Throwable e){
			
			provider.onFail(onFailEvent(context, e));
			
			throw e;
		}
	}
	
	public OnStartEvent onStartEvent(OperationContext context) {
		
		Date startDate = new Date();
		context.setOperationData(this, "startDate", startDate);
		
		return new OnStartEvent(context, startDate);
	}

	
	public OnFinishEvent onFinishEvent(OperationContext context) {

		Date endDate = new Date();
		Date startDate = context.getOperationData(this, "startDate");
		
		long took = endDate.getTime() - startDate.getTime();
		
		context.setOperationData(this, "finishDate", endDate);
		context.setOperationData(this, "tookMillis", took);
		
		return new OnFinishEvent(context, endDate, took);
	}

	
	public OnFailEvent onFailEvent(OperationContext context, Throwable exception) {
		
		Date failDate = new Date();
		Date startDate = context.getOperationData(this, "startDate");

		long took = failDate.getTime() - startDate.getTime();

		context.setOperationData(this, "failDate", failDate);
		context.setOperationData(this, "tookMillis", took);
		
		return new OnFailEvent(context, exception);
	}
}
