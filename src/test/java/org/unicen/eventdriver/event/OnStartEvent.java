package org.unicen.eventdriver.event;

import java.util.Date;

import org.unicen.eventdriver.Event;

public class OnStartEvent implements Event{

	private final Date date;

	public OnStartEvent(Date date) {
		this.date = date;
	}

	public Date getDate() {
		return date;
	}
}