package org.unicen.operation.input;

import org.unicen.eventdriver.EventProvider;
import org.unicen.operation.OperationContext;
import org.unicen.operation.event.OnFailEvent;
import org.unicen.operation.result.OnFinishResultEvent;

public interface InputOperationEventProvider<T, E> extends EventProvider<InputOperationListener<T, E>> {

	OnStartInputEvent<E> onStart(OperationContext context, E input);
	
	OnFinishResultEvent<T> onFinish(OperationContext context, T result);
	
	OnFailEvent onFail(OperationContext context, Throwable exception);
}
