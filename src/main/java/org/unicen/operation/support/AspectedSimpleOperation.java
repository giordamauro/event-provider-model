package org.unicen.operation.support;

import java.util.Objects;

import org.unicen.operation.OperationContext;
import org.unicen.operation.SimpleOperation;

public class AspectedSimpleOperation implements SimpleOperation {

    private final SimpleOperation operation;
    private final SimpleOperationAspectSupport aspect;
    
    public AspectedSimpleOperation(SimpleOperation operation, SimpleOperationAspectSupport aspect) {
    
        Objects.requireNonNull(operation, "Operation cannot be null");
        Objects.requireNonNull(aspect, "Aspect cannot be null");
        
        this.operation = operation;
        this.aspect = aspect;
    }

    @Override
    public void execute(OperationContext context) {

        aspect.beforeExecute(operation, context);

        Exception exception = null;
        
        try {
            operation.execute(context);
        }
        catch(Exception e){
            exception = e;
        }

        aspect.afterExecute(operation, null, exception, context);
        
        if(exception != null) {
            throw new IllegalStateException(exception);
        }
    }

}
