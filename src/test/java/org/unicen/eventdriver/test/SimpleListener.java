package org.unicen.eventdriver.test;

import org.unicen.eventdriver.Listener;

public interface SimpleListener extends Listener {

	void onStart(OnStartEvent event);
}
