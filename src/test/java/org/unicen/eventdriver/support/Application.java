package org.unicen.eventdriver.support;

import java.util.ArrayList;
import java.util.List;

import org.unicen.eventdriver.EventHandler;
import org.unicen.eventdriver.EventListener;
import org.unicen.eventdriver.EventProviderFactory;
import org.unicen.eventdriver.MapEventHandler;
import org.unicen.operation.OperationContext;
import org.unicen.operation.SimpleOperation;
import org.unicen.operation.event.OnFailEvent;
import org.unicen.operation.event.OnFinishEvent;
import org.unicen.operation.event.OnStartEvent;
import org.unicen.operation.event.StartFinishListener;
import org.unicen.operation.support.SequentialSimpleOperation;

public class Application {

	public static void main(String[] args) throws Throwable {

		EventHandler handler = new MapEventHandler();
		EventProviderFactory providerFactory = new EventProviderFactory(handler);

		List<SimpleOperation> steps = new ArrayList<>();
		steps.add(new SimpleOperation() {

            @Override
            public void execute(OperationContext context) {
                System.out.println("First step");
            }
		    
		});
	      steps.add(new SimpleOperation() {

	            @Override
	            public void execute(OperationContext context) {
	                System.out.println("Second step");
	            }
	            
	        });
        SequentialSimpleOperation sequentialOp = new SequentialSimpleOperation(steps);
		
		
		SimpleOperation testPlan = providerFactory.resolveProviders(sequentialOp);

		EventListener listener = new StartFinishListener() {
            
            @Override
            public void onStart(OnStartEvent onStartEvent) {
             
                System.out.println("starting");
            }
            
            @Override
            public void onFinish(OnFinishEvent onFinishEvent) {

                System.out.println("finishing");
            }
            
            @Override
            public void onFail(OnFailEvent onFailEvent) {
                System.out.println("failing");
            }
        };
		handler.subscribeListener(testPlan, listener);

		testPlan.execute(new OperationContext());
	}

}
