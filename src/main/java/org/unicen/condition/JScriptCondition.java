package org.unicen.condition;

import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class JScriptCondition<T> implements Condition<T> {

    private static final ScriptEngine SCRIPT_ENGINE = new ScriptEngineManager().getEngineByName("js");
    
    @Override
    public boolean evaluate(T input) {

        SCRIPT_ENGINE.put("a", 1);
        SCRIPT_ENGINE.put("b", 5);

        Bindings bindings = SCRIPT_ENGINE.getBindings(ScriptContext.ENGINE_SCOPE);
        Object a = bindings.get("a");
        Object b = bindings.get("b");
        System.out.println("a = " + a);
        System.out.println("b = " + b);

        try {
            return (Boolean) SCRIPT_ENGINE.eval("c = a + b;");
        
        } catch (ScriptException e) {
           throw new IllegalStateException(e);
        }
    }
}
