package org.unicen.http;

import java.io.InputStream;
import java.util.Objects;

public class InputStreamHttpPayload implements HttpPayload<InputStream> {

    private final InputStream payload;
	private final String contentType;

	public InputStreamHttpPayload(String contentType, InputStream payload) {
		
        Objects.requireNonNull(contentType, "ContentType cannot be null");
	    Objects.requireNonNull(payload, "Payload cannot be null");
	    
	    this.payload = payload;
		this.contentType = contentType;
	}
	
	public String getContentType() {
		return contentType;
	}

    @Override
    public InputStream getPayload() {
        return payload;
    }
}
