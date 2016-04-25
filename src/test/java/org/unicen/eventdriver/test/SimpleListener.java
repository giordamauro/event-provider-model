package org.unicen.eventdriver.test;

import org.unicen.eventdriver.EventListener;

public interface SimpleListener extends EventListener {

	void onStart(OnStartEvent event);
}
