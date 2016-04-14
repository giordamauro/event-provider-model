package org.unicen.operation.input;

import java.util.Date;

import org.unicen.eventdriver.ProviderClass;
import org.unicen.operation.OperationContext;
import org.unicen.operation.event.OnFailEvent;
import org.unicen.operation.result.OnFinishResultEvent;

public abstract class AbstractInputOperation<T, E> implements InputOperation<T, E>, InputOperationEventProvider<T, E> {

	@ProviderClass
	private InputOperationEventProvider<T, E> provider;
	
	@Override
	public T execute(E input, OperationContext context) throws Throwable {
		
		provider.onStart(context, input);
		
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
	public OnStartInputEvent<E> onStart(OperationContext context, E input) {
		
		Date startDate = new Date();
		context.setOperationData(this, "startDate", startDate);
		context.setOperationData(this, "input", input);
		
		return new OnStartInputEvent<>(context, startDate, input);
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
