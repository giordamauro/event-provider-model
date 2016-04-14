package org.unicen.operation.result;

import java.util.Date;

import org.unicen.operation.OperationContext;
import org.unicen.operation.event.OnFinishEvent;

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
