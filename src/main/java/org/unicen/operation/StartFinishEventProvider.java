package org.unicen.operation;

import org.unicen.eventdriver.EventProvider;
import org.unicen.eventdriver.test.OnStartEvent;
import org.unicen.operation.event.OnFailEvent;
import org.unicen.operation.event.OnFinishEvent;
import org.unicen.operation.event.StartFinishListener;

public interface StartFinishEventProvider extends EventProvider<StartFinishListener>{

	OnStartEvent onStart();
	
	OnFinishEvent onFinish();
	
	OnFailEvent onFail();
}
