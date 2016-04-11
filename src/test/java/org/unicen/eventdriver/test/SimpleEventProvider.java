package org.unicen.eventdriver.test;

import org.unicen.eventdriver.EventProvider;

public interface SimpleEventProvider extends EventProvider<SimpleListener>{

	OnStartEvent onStart();
}
