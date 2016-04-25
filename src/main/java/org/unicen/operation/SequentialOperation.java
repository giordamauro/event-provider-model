package org.unicen.operation;

import java.util.List;

public class SequentialOperation implements SimpleOperation {

	private final List<ResultOperation<?>> steps;

	public SequentialOperation(List<ResultOperation<?>> steps) {
		this.steps = steps;
	}
	
	@Override
	public void execute(OperationContext context) throws Throwable {
		
		for(ResultOperation<?> operation : steps) {
			operation.execute(context);
		}
	}
}
