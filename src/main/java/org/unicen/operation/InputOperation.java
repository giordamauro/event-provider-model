package org.unicen.operation;

public interface InputOperation<T, E> extends Operation {

	T execute(E input, OperationContext context) throws Throwable;

	Class<E> getInputType();
	
	Class<T> getOutputType();
}
