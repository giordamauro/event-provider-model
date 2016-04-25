package org.unicen.operation.event;

import org.unicen.eventdriver.EventListener;

public interface ResultOperationListener<T> extends EventListener {

	void onStart(OnStartEvent onStartEvent);
	
	void onFinish(OnFinishResultEvent<T> onFinishEvent);
	
	void onFail(OnFailEvent onFailEvent);
}
