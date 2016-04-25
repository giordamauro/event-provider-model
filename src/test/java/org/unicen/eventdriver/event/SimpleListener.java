package org.unicen.eventdriver.event;

import org.unicen.eventdriver.EventListener;

public interface SimpleListener extends EventListener {

	void onStart(OnStartEvent event);
}
