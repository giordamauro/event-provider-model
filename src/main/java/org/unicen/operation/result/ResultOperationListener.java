package org.unicen.operation.result;

import org.unicen.eventdriver.Listener;
import org.unicen.eventdriver.test.OnStartEvent;
import org.unicen.operation.event.OnFailEvent;

public interface ResultOperationListener<T> extends Listener {

	void onStart(OnStartEvent onStartEvent);
	
	void onFinish(OnFinishResultEvent<T> onFinishEvent);
	
	void onFail(OnFailEvent onFailEvent);
}
