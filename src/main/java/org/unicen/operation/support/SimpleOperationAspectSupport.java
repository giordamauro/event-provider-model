package org.unicen.operation.support;

import org.unicen.aspect.Aspect;
import org.unicen.operation.OperationContext;
import org.unicen.operation.SimpleOperation;

public class SimpleOperationAspectSupport implements Aspect<SimpleOperation> {

    public void beforeExecute(SimpleOperation operation, OperationContext context) {}

    public void afterExecute(SimpleOperation operation, Object result, Exception exception, OperationContext context) {}
}
