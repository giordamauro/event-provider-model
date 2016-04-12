package org.unicen.operation.test;

import org.unicen.operation.AbstractStartFinishOperation;

public class TestPlan extends AbstractStartFinishOperation {

	@Override
	public void doExecute() throws Throwable {

		System.out.println("Estoy corriendo un test");
		
	}

}
