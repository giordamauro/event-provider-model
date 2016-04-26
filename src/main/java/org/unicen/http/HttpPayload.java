package org.unicen.http;

public interface HttpPayload<T> {

    String getContentType();
    
	T getPayload();
}
