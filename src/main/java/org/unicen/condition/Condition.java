package org.unicen.condition;

public interface Condition<T> {

    boolean evaluate(T input);
}
