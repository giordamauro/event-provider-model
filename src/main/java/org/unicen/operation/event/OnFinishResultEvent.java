package org.unicen.operation.event;

import java.util.Date;

import org.unicen.operation.OperationContext;

public class OnFinishResultEvent<T> extends OnFinishEvent {

	private final T result;
	
	public OnFinishResultEvent(OperationContext operationContext, Date date, Long tookMillis, T result) {
		super(operationContext, date, tookMillis);
		this.result = result;
	}

	public T getResult() {
		return result;
	}

	
}
