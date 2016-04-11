package org.unicen.eventdriver;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ProviderClass {

	Class<? extends EventProvider<?>> value() default DEFAULT.class;
	
	static final class DEFAULT implements EventProvider<DEFAULT_LISTENER> {}
	static final class DEFAULT_LISTENER implements Listener {}
}
