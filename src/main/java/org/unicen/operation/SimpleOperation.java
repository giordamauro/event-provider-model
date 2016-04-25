package org.unicen.operation;

import org.unicen.eventdriver.EventWrapper;

@EventWrapper(StartFinishEventProvider.class)
public interface SimpleOperation extends Operation {

	void execute(OperationContext context) throws Throwable;
}
