package org.unicen.operation.event;

import org.unicen.eventdriver.Event;
import org.unicen.operation.OperationContext;

public abstract class OperationEvent implements Event{

	private final OperationContext operationContext;
	
	public OperationEvent(OperationContext operationContext) {
		this.operationContext = operationContext;
	}

	public OperationContext getOperationContext() {
		return operationContext;
	}
}
