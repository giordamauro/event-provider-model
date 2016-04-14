package org.unicen.operation.result;

import java.util.Date;

import org.unicen.eventdriver.ProviderClass;
import org.unicen.operation.OperationContext;
import org.unicen.operation.ResultOperation;
import org.unicen.operation.event.OnFailEvent;
import org.unicen.operation.event.OnStartEvent;

public abstract class AbstractResultOperation<T> implements ResultOperation<T>, ResultOperationEventProvider<T> {

	@ProviderClass
	private ResultOperationEventProvider<T> provider;
	
	@Override
	public T execute(OperationContext context) throws Throwable {
		
		provider.onStart(context);
		
		try {
			T result = doExecute(context);

			provider.onFinish(context, result);
			
			return result;
			
		} catch(Throwable e){
			
			provider.onFail(context, e);
			
			throw new IllegalStateException("Exception executing operation " + this.toString(), e);
		}
	}
	
	protected abstract T doExecute(OperationContext context) throws Throwable;

	@Override
	public OnStartEvent onStart(OperationContext context) {
		
		Date startDate = new Date();
		context.setOperationData(this, "startDate", startDate);
		
		return new OnStartEvent(context, startDate);
	}

	@Override
	public OnFinishResultEvent<T> onFinish(OperationContext context, T result) {

		Date endDate = new Date();
		Date startDate = context.getOperationData(this, "startDate");
		
		long took = endDate.getTime() - startDate.getTime();
		
		context.setOperationData(this, "finishDate", endDate);
		context.setOperationData(this, "tookMillis", took);
		context.setOperationData(this, "result", result);
		
		return new OnFinishResultEvent<>(context, endDate, took, result);
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
