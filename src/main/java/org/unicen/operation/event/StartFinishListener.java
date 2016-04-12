package org.unicen.operation.event;

import org.unicen.eventdriver.Listener;

public interface StartFinishListener extends Listener{

	void onStart();
	
	void onFinish();
}
