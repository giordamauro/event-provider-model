package org.unicen.operation.result;

import org.unicen.eventdriver.EventProvider;
import org.unicen.operation.OperationContext;
import org.unicen.operation.event.OnFailEvent;
import org.unicen.operation.event.OnStartEvent;

public interface ResultOperationEventProvider<T> extends EventProvider<ResultOperationListener<T>> {

	OnStartEvent onStart(OperationContext context);
	
	OnFinishResultEvent<T> onFinish(OperationContext context, T result);
	
	OnFailEvent onFail(OperationContext context, Throwable exception);
}
