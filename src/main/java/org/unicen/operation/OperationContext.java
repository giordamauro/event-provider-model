package org.unicen.operation;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class OperationContext {

	private final Map<String, Object> globalData = new ConcurrentHashMap<>();
	private final Map<Operation, Map<String, Object>> operationsData = new ConcurrentHashMap<>();
	
	@SuppressWarnings("unchecked")
	public <T> T getGlobal(String name) {
		return (T) globalData.get(name);
	}

	public void setGlobal(String name, Object value) {
		globalData.put(name, value);
	}

	@SuppressWarnings("unchecked")
	public <T> T getOperationData(Operation operation, String name) {

		Map<String, Object> operationData = operationsData.get(operation);
		
		if(operationData != null){
			return (T) operationData.get(name);
		}
		return null;
	}

	public Map<String, Object> getAllOperationData(Operation operation) {

		return operationsData.get(operation);
	}

	public void setOperationData(Operation operation, String name, Object value) {

		Map<String, Object> operationData = operationsData.get(operation);
		
		if(operationData == null){
			operationData = new ConcurrentHashMap<>();
			this.operationsData.put(operation, operationData);
		}
		
		operationData.put(name, value);
	}
}
