package org.unicen.operation;

import java.util.Date;

import org.unicen.eventdriver.EventProvider;
import org.unicen.operation.event.OnFailEvent;
import org.unicen.operation.event.OnFinishResultEvent;
import org.unicen.operation.event.OnStartEvent;
import org.unicen.operation.event.ResultOperationListener;

public class ResultOperationEventProvider<T> implements ResultOperation<T> {

	@EventProvider
	private ResultOperationListener<T> provider;
	
	private final ResultOperation<T> operation;
	
	public ResultOperationEventProvider(ResultOperation<T> operation) {
        this.operation = operation;
    }

    @Override
	public T execute(OperationContext context) throws Throwable {
		
		provider.onStart(onStartEvent(context));
		
		try {
			T result = operation.execute(context);

			provider.onFinish(onFinishEvent(context, result));
			
			return result;
			
		} catch(Throwable e){
			
			provider.onFail(onFailEvent(context, e));
			
			throw new IllegalStateException("Exception executing operation " + this.toString(), e);
		}
	}

	
	public OnStartEvent onStartEvent(OperationContext context) {
		
		Date startDate = new Date();
		context.setOperationData(this, "startDate", startDate);
		
		return new OnStartEvent(context, startDate);
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
    public Class<T> getOutputType() {
        return operation.getOutputType();
    }
}
