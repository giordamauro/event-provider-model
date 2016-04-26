package org.unicen.eventdriver.aspect;

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
import org.unicen.operation.support.AspectedSimpleOperation;
import org.unicen.operation.support.SimpleOperationAspectSupport;
import org.unicen.operation.test.TestPlan;

public class AspectableTest {

	public static void main(String[] args) throws Throwable {

		EventHandler handler = new MapEventHandler();
		EventProviderFactory providerFactory = new EventProviderFactory(handler);

		SimpleOperation testPlan = providerFactory.resolveProviders(new TestPlan());

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

		SimpleOperationAspectSupport aspect = new SimpleOperationAspectSupport() {
			
			@Override
			public void beforeExecute(SimpleOperation operation, OperationContext context) {
				
				System.out.println("Holaaa aspectoo");
			}
			
			@Override
            public void afterExecute(SimpleOperation operation, Object result, Exception exception, OperationContext context) {
                
                System.out.println("Chau aspectoo");
            }
			
		};
		
		SimpleOperation apectTestPlan  = new AspectedSimpleOperation(testPlan, aspect);
		
		apectTestPlan.execute(new OperationContext());
	}

}
