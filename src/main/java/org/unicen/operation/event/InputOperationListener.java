package org.unicen.operation.event;

import org.unicen.eventdriver.EventListener;

public interface InputOperationListener<T, E> extends EventListener {

	void onStart(OnStartInputEvent<E> onStartEvent);
	
	void onFinish(OnFinishResultEvent<T> onFinishEvent);
	
	void onFail(OnFailEvent onFailEvent);
}
