package org.unicen.operation.event;

import java.util.Date;

import org.unicen.operation.OperationContext;

public class OnStartInputEvent<E> extends OnStartEvent {

	private final E input;
	
	public OnStartInputEvent(OperationContext context, Date date, E input) {
		super(context, date);
		
		this.input = input;
	}

	public E getInput() {
		return input;
	}

	@Override
	public String toString() {
		return "OnStartInputEvent [input=" + input + ", date=" + getDate() + ",operationContext=" + getOperationContext() + "]";
	}
	
}
