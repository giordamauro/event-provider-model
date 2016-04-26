package org.unicen.http;

import java.util.Objects;

public class StringHttpPayload implements HttpPayload<String> {

    private final String payload;
	private final String contentType;

	public StringHttpPayload(String contentType, String payload) {
		
        Objects.requireNonNull(contentType, "ContentType cannot be null");
	    Objects.requireNonNull(payload, "Payload cannot be null");
	    
	    this.payload = payload;
		this.contentType = contentType;
	}
	
	public String getContentType() {
		return contentType;
	}

    @Override
    public String getPayload() {
        return payload;
    }
}
