package org.unicen.operation.event;

import org.unicen.eventdriver.EventProvider;
import org.unicen.operation.OperationContext;

public interface StartFinishEventProvider extends EventProvider<StartFinishListener> {

	OnStartEvent onStart(OperationContext context);
	
	OnFinishEvent onFinish(OperationContext context);
	
	OnFailEvent onFail(OperationContext context, Throwable exception);
}
