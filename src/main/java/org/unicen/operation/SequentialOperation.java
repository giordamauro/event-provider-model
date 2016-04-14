package org.unicen.operation;

import java.util.List;

public class SequentialOperation extends AbstractSimpleOperation {

	private final List<ResultOperation<?>> steps;

	public SequentialOperation(List<ResultOperation<?>> steps) {
		this.steps = steps;
	}
	
	@Override
	protected void doExecute(OperationContext context) throws Throwable {
		
		for(ResultOperation<?> operation : steps) {
			operation.execute(context);
		}
	}
}
