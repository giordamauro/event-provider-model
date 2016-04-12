package org.unicen.operation.event;

import org.unicen.operation.OperationContext;

public class OnFailEvent extends OperationEvent {

	private final Throwable exception;

	public OnFailEvent(OperationContext operationContext, Throwable exception) {
		super(operationContext);
		this.exception = exception;
	}

	@Override
	public String toString() {
		return "OnFailEvent [exception=" + exception + ", getOperationContext()=" + getOperationContext() + "]";
	}	
}
