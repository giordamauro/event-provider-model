package org.unicen.operation;

import java.util.HashMap;
import java.util.Map;

public class OperationContext {

	private final Operation currentOperation;
	private final Map<String, Object> data; 
	
	public OperationContext(Operation currentOperation) {
		this.currentOperation = currentOperation;
		this.data = new HashMap<>();
	}

	public Operation getCurrentOperation() {
		return currentOperation;
	}

	@SuppressWarnings("unchecked")
	public synchronized <T> T getContextData(String name) {
		return (T) data.get(name);
	}

	public synchronized void setContextData(String name, Object value) {
		data.put(name, value);
	}

	@Override
	public String toString() {
		return "OperationContext [currentOperation=" + currentOperation + ", data=" + data + "]";
	}
}
