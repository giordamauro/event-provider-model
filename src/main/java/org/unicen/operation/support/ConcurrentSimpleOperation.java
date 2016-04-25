package org.unicen.operation.support;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.unicen.operation.OperationContext;
import org.unicen.operation.SimpleOperation;

public class ConcurrentSimpleOperation implements SimpleOperation {

    private final ExecutorService executor;
	private final List<SimpleOperation> steps;

	public ConcurrentSimpleOperation(List<SimpleOperation> steps) {
		
        this.steps = steps;
		this.executor = Executors.newFixedThreadPool(steps.size());
	}
	
    @Override
    public void execute(final OperationContext context) {

        for (final SimpleOperation operation : steps) {

            executor.execute(new Runnable() {

                @Override
                public void run() {
                    operation.execute(context);
                }
            });
        }
	}
}
