package org.unicen.aspect;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

public class AspectFactory {

    public <T, E> E getWrapperInstance(T aspectableInstance, List<Aspect<T>> aspects) {

        Objects.requireNonNull(aspectableInstance);
        Objects.requireNonNull(aspects);

        Class<?> aspectableClass = aspectableInstance.getClass();
        Class<?>[] interfaces = aspectableClass.getInterfaces();

        InvocationHandler aspectInvocationHandler = new AspectInvocationHandler<T>(aspectableInstance, aspects);

        @SuppressWarnings("unchecked")
        E proxy = (E) Proxy.newProxyInstance(this.getClass().getClassLoader(), interfaces, aspectInvocationHandler);

        return proxy;
    }

    private static class AspectInvocationHandler<T> implements InvocationHandler {

        private final T aspectableInstance;
        private Map<String, List<Entry<Method, Aspect<T>>>> beforeAspects;
        private Map<String, List<Entry<Method, Aspect<T>>>> afterAspects;

        public AspectInvocationHandler(T aspectableInstance, List<Aspect<T>> aspects) {

            this.aspectableInstance = aspectableInstance;
            loadAspectableMethods(aspectableInstance.getClass(), aspects);
        }

        private void loadAspectableMethods(Class<?> aspectableClass, List<Aspect<T>> aspects) {
            
            this.beforeAspects = new HashMap<>();
            this.afterAspects = new HashMap<>();
           
            for(Aspect<T> aspect : aspects) {
                
                Method[] aspectMethods = aspect.getClass().getDeclaredMethods();
                for(Method aspectMethod : aspectMethods){
                    
                    String aspectMethodName = aspectMethod.getName();
                    if(aspectMethodName.startsWith("before")) {
                        String name = aspectMethodName.replaceFirst("before", "").toLowerCase();
                        
                        List<Entry<Method, Aspect<T>>> nameAspects = beforeAspects.get(name);
                        if(nameAspects == null) {
                            nameAspects = new ArrayList<>();
                            beforeAspects.put(name, nameAspects);
                        }
                        nameAspects.add(new SimpleEntry<Method, Aspect<T>>(aspectMethod, aspect));
                    }
                    else if(aspectMethodName.startsWith("after")) {
                        String name = aspectMethodName.replaceFirst("after", "").toLowerCase();
                        
                        List<Entry<Method, Aspect<T>>> nameAspects = afterAspects.get(name);
                        if(nameAspects == null) {
                            nameAspects = new ArrayList<>();
                            afterAspects.put(name, nameAspects);
                        }
                        nameAspects.add(new SimpleEntry<Method, Aspect<T>>(aspectMethod, aspect));
                    }
                }
            }
            
            for(Entry<String, List<Entry<Method, Aspect<T>>>> afterAspect : afterAspects.entrySet()) {
                Collections.reverse(afterAspect.getValue());
            }
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            
            executeBeforeAspects(method, args);

            Object result = null;
            Throwable exception = null;
            
            try {
                result = method.invoke(aspectableInstance, args);
            }
            catch(Throwable e){
                exception = e;
            }
            
            executeAfterAspects(method, args, result, exception);
            
            if(exception != null) {
                throw new IllegalStateException(String.format("Exception running aspected instance %s, method %s", aspectableInstance, method.getName()), exception);
            }
            return result;
        }
        
        private void executeBeforeAspects(Method method, Object[] args) throws Throwable {
            
            List<Entry<Method, Aspect<T>>> beforeList = beforeAspects.get(method.getName().toLowerCase());
            
            if(beforeList != null) {
	            for(Entry<Method, Aspect<T>> beforeEntry : beforeList) {
	                
	                List<Object> parameters = new ArrayList<>();
	                parameters.add(aspectableInstance);
	                parameters.addAll(Arrays.asList(args));
	                
	                Aspect<T> aspect = beforeEntry.getValue();
	                Method aspectMethod = beforeEntry.getKey();
	                
	                aspectMethod.setAccessible(true);
	                aspectMethod.invoke(aspect, parameters.toArray());
	            }
            }
        }
        
        private void executeAfterAspects(Method method, Object[] args, Object result, Throwable exception) throws Throwable {
            
            List<Entry<Method, Aspect<T>>> afterList = afterAspects.get(method.getName().toLowerCase());
            
            if(afterList != null) {
	            for(Entry<Method, Aspect<T>> afterEntry : afterList) {
	                
	                List<Object> parameters = new ArrayList<>();
	                parameters.add(aspectableInstance);
	                parameters.add(result);
	                parameters.add(exception);
	                parameters.addAll(Arrays.asList(args));
	                
	                Aspect<T> aspect = afterEntry.getValue();
	                Method aspectMethod = afterEntry.getKey();
                    
                    aspectMethod.setAccessible(true);
                    aspectMethod.invoke(aspect, parameters.toArray());
	            }
            }
        }

    }

}
