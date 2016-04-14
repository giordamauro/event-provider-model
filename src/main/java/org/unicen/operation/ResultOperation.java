package org.unicen.operation;

public interface ResultOperation<T> extends Operation {

	T execute(OperationContext context) throws Throwable;
	
	Class<T> getOutputType();
}
