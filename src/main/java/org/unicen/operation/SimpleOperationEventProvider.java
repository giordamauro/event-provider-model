package org.unicen.operation;

import java.util.Date;

import org.unicen.eventdriver.ProviderClass;
import org.unicen.operation.event.OnFailEvent;
import org.unicen.operation.event.OnFinishEvent;
import org.unicen.operation.event.OnStartEvent;
import org.unicen.operation.event.StartFinishEventProvider;

class SimpleOperationEventProvider implements SimpleOperation, StartFinishEventProvider {

	@ProviderClass
	private StartFinishEventProvider provider;
	
	private final SimpleOperation operation;
	
	public SimpleOperationEventProvider(SimpleOperation operation) {
	
		this.operation = operation;
	}

	@Override
	public void execute(OperationContext context) throws Throwable {
		
		provider.onStart(context);
		
		try {
			operation.execute(context);

			provider.onFinish(context);
			
		} catch(Throwable e){
			
			provider.onFail(context, e);
			
			throw e;
		}
	}
	
	@Override
	public OnStartEvent onStart(OperationContext context) {
		
		Date startDate = new Date();
		context.setOperationData(this, "startDate", startDate);
		
		return new OnStartEvent(context, startDate);
	}

	@Override
	public OnFinishEvent onFinish(OperationContext context) {

		Date endDate = new Date();
		Date startDate = context.getOperationData(this, "startDate");
		
		long took = endDate.getTime() - startDate.getTime();
		
		context.setOperationData(this, "finishDate", endDate);
		context.setOperationData(this, "tookMillis", took);
		
		return new OnFinishEvent(context, endDate, took);
	}

	@Override
	public OnFailEvent onFail(OperationContext context, Throwable exception) {
		
		Date failDate = new Date();
		Date startDate = context.getOperationData(this, "startDate");

		long took = failDate.getTime() - startDate.getTime();

		context.setOperationData(this, "failDate", failDate);
		context.setOperationData(this, "tookMillis", took);
		
		return new OnFailEvent(context, exception);
	}
}
