package org.unicen.operation.event;

import java.util.Date;

import org.unicen.operation.OperationContext;

public class OnStartEvent extends OperationEvent {

	private final Date date;
	
	public OnStartEvent(OperationContext context, Date date) {
		super(context);
		
		this.date = date;
	}

	public Date getDate() {
		return date;
	}

	@Override
	public String toString() {
		return "OnStartEvent [date=" + date + ", operationContext=" + getOperationContext() + "]";
	}
}
