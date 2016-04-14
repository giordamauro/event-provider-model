package org.unicen.operation.input;

import org.unicen.operation.Operation;
import org.unicen.operation.OperationContext;

public interface InputOperation<T, E> extends Operation {

	T execute(E input, OperationContext context) throws Throwable;

	Class<E> getInputType();
	
	Class<T> getOutputType();
}
