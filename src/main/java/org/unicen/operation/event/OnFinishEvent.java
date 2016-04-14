package org.unicen.operation.event;

import java.util.Date;

import org.unicen.operation.OperationContext;

public class OnFinishEvent extends OperationEvent {

	private final Date date;
	private final Long tookMillis;
	
	public OnFinishEvent(OperationContext operationContext, Date date, Long tookMillis) {
		super(operationContext);
		this.date = date;
		this.tookMillis = tookMillis;
	}

	public Date getDate() {
		return date;
	}

	public Long getTookMillis() {
		return tookMillis;
	}

	@Override
	public String toString() {
		return "OnFinishEvent [date=" + date + ", tookMillis=" + tookMillis + "]";
	}
}
