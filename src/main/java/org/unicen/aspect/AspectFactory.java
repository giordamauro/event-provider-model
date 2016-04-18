package org.unicen.aspect;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.AbstractMap.SimpleEntry;

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
        private Map<Method, List<Entry<Method, Aspect<T>>>> beforeAspects;
        private Map<Method, List<Entry<Method, Aspect<T>>>> afterAspects;

        public AspectInvocationHandler(T aspectableInstance, List<Aspect<T>> aspects) {

            this.aspectableInstance = aspectableInstance;
            loadAspectableMethods(aspectableInstance.getClass(), aspects);
        }

        private void loadAspectableMethods(Class<?> aspectableClass, List<Aspect<T>> aspects) {
            
            this.beforeAspects = new HashMap<>();
            this.afterAspects = new HashMap<>();
            
            Map<String, List<Entry<Method, Aspect<T>>>> beforeMethods = new HashMap<>();
            Map<String, List<Entry<Method, Aspect<T>>>> afterMethods = new HashMap<>();
            for(Aspect<T> aspect : aspects) {
                
                Method[] aspectMethods = aspect.getClass().getDeclaredMethods();
                for(Method aspectMethod : aspectMethods){
                    
                    String aspectMethodName = aspectMethod.getName();
                    if(aspectMethodName.startsWith("before")) {
                        String name = aspectMethodName.replaceFirst("before", "");
                        
                        List<Entry<Method, Aspect<T>>> nameAspects = beforeMethods.get(name);
                        if(nameAspects == null) {
                            nameAspects = new ArrayList<>();
                            beforeMethods.put(name, nameAspects);
                        }
                        nameAspects.add(new SimpleEntry<Method, Aspect<T>>(aspectMethod, aspect));
                    }
                    else if(aspectMethodName.startsWith("after")) {
                        String name = aspectMethodName.replaceFirst("after", "");
                        
                        List<Entry<Method, Aspect<T>>> nameAspects = afterMethods.get(name);
                        if(nameAspects == null) {
                            nameAspects = new ArrayList<>();
                            afterMethods.put(name, nameAspects);
                        }
                        nameAspects.add(new SimpleEntry<Method, Aspect<T>>(aspectMethod, aspect));
                    }
                }
            }
            
            Method[] methods = aspectableClass.getDeclaredMethods();
            for(Method method : methods) {
                String name = method.getName();
                List<Entry<Method, Aspect<T>>> beforeAspectsList = beforeMethods.get(name);
                List<Entry<Method, Aspect<T>>> afterAspectsList = afterMethods.get(name);
                
                beforeAspects.put(method, beforeAspectsList);
                
                Collections.reverse(afterAspectsList);
                afterAspects.put(method, afterAspectsList);
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
                throw exception;
            }
            return result;
        }
        
        private void executeBeforeAspects(Method method, Object[] args) throws Throwable {
            
            List<Entry<Method, Aspect<T>>> beforeList = beforeAspects.get(method);
            for(Entry<Method, Aspect<T>> beforeEntry : beforeList) {
                
                List<Object> parameters = new ArrayList<>();
                parameters.add(aspectableInstance);
                parameters.addAll(Arrays.asList(args));
                
                Aspect<T> aspect = beforeEntry.getValue();
                beforeEntry.getKey().invoke(aspect, parameters.toArray());
                
            }
        }
        
        private void executeAfterAspects(Method method, Object[] args, Object result, Throwable exception) throws Throwable {
            
            List<Entry<Method, Aspect<T>>> afterList = afterAspects.get(method);
            for(Entry<Method, Aspect<T>> afterEntry : afterList) {
                
                List<Object> parameters = new ArrayList<>();
                parameters.add(aspectableInstance);
                parameters.add(result);
                parameters.add(exception);
                parameters.addAll(Arrays.asList(args));
                
                Aspect<T> aspect = afterEntry.getValue();
                afterEntry.getKey().invoke(aspect, parameters.toArray());
            }
        }

    }

}
