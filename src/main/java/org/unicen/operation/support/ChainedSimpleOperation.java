package org.unicen.operation.support;

import java.util.Objects;

import org.unicen.operation.OperationContext;
import org.unicen.operation.SimpleOperation;

public abstract class ChainedSimpleOperation implements SimpleOperation {

	private final SimpleOperation next;

	public ChainedSimpleOperation(SimpleOperation next) {
		
		Objects.requireNonNull(next, "Next cannot be null");

		this.next = next;
	}

	protected void next(OperationContext context) {
		next.execute(context);
	}
}
