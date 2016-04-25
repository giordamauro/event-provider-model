package org.unicen.operation;

import java.util.Date;

import org.unicen.eventdriver.EventProvider;
import org.unicen.operation.event.InputOperationListener;
import org.unicen.operation.event.OnFailEvent;
import org.unicen.operation.event.OnFinishResultEvent;
import org.unicen.operation.event.OnStartInputEvent;

public class InputOperationEventProvider<T, E> implements InputOperation<T, E> {

	@EventProvider
	private InputOperationListener<T, E> provider;
	
	private final InputOperation<T, E> operation;
	
	public InputOperationEventProvider(InputOperation<T, E> operation) {
        this.operation = operation;
    }

    @Override
	public T execute(E input, OperationContext context) {
		
		provider.onStart(onStartEvent(context, input));
		
		try {
			T result = operation.execute(input, context);

			provider.onFinish(onFinishEvent(context, result));
			
			return result;
			
		} catch(Throwable e){
			
			provider.onFail(onFailEvent(context, e));
			
			throw new IllegalStateException("Exception executing operation " + this.toString(), e);
		}
	}

	
	public OnStartInputEvent<E> onStartEvent(OperationContext context, E input) {
		
		Date startDate = new Date();
		context.setOperationData(this, "startDate", startDate);
		context.setOperationData(this, "input", input);
		
		return new OnStartInputEvent<>(context, startDate, input);
	}

	
	public OnFinishResultEvent<T> onFinishEvent(OperationContext context, T result) {

		Date endDate = new Date();
		Date startDate = context.getOperationData(this, "startDate");
		
		long took = endDate.getTime() - startDate.getTime();
		
		context.setOperationData(this, "finishDate", endDate);
		context.setOperationData(this, "tookMillis", took);
		context.setOperationData(this, "result", result);
		
		return new OnFinishResultEvent<>(context, endDate, took, result);
	}

	
	public OnFailEvent onFailEvent(OperationContext context, Throwable exception) {
		
		Date failDate = new Date();
		Date startDate = context.getOperationData(this, "startDate");

		long took = failDate.getTime() - startDate.getTime();

		context.setOperationData(this, "failDate", failDate);
		context.setOperationData(this, "tookMillis", took);
		
		return new OnFailEvent(context, exception);
	}

    @Override
    public Class<E> getInputType() {
       return operation.getInputType();
    }

    @Override
    public Class<T> getOutputType() {
        return operation.getOutputType();
    }
}
