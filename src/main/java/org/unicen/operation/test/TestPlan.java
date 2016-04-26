package org.unicen.operation.test;

import org.unicen.operation.OperationContext;
import org.unicen.operation.SimpleOperation;

public class TestPlan implements SimpleOperation {

//	TODO: Make it stoppable: must run in a thread
	
	@Override
	public void execute(OperationContext context) {

		System.out.println("Estoy corriendo un test");
//		throw new IllegalStateException("exceptionn");
	}
}
