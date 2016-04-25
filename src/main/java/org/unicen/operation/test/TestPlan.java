package org.unicen.operation.test;

import org.unicen.operation.OperationContext;
import org.unicen.operation.SimpleOperation;

public class TestPlan implements SimpleOperation {

	@Override
	public void execute(OperationContext context) {

//		System.out.println("Estoy corriendo un test");
		throw new IllegalStateException("exceptionn");
	}
}
