package org.unicen.operation.test;

import org.unicen.operation.AbstractSimpleOperation;
import org.unicen.operation.OperationContext;

public class TestPlan extends AbstractSimpleOperation {

	@Override
	public void doExecute(OperationContext context) throws Throwable {

		System.out.println("Estoy corriendo un test");
	}
}
