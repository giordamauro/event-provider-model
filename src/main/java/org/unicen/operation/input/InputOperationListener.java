package org.unicen.operation.input;

import org.unicen.eventdriver.Listener;
import org.unicen.operation.event.OnFailEvent;
import org.unicen.operation.result.OnFinishResultEvent;

public interface InputOperationListener<T, E> extends Listener {

	void onStart(OnStartInputEvent<E> onStartEvent);
	
	void onFinish(OnFinishResultEvent<T> onFinishEvent);
	
	void onFail(OnFailEvent onFailEvent);
}
