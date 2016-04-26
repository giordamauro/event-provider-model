package org.unicen.http;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class UrlEncodedHttpPayload implements HttpPayload<List<HttpParameter>> {

    private static final String APPLICATION_FORM_URLENCODED = "application/x-www-form-urlencoded";
    
	private final List<HttpParameter> formParams;

	public UrlEncodedHttpPayload(List<HttpParameter> formParams) {
	    
	    Objects.requireNonNull(formParams, "Form params cannot be null");
	    
		this.formParams = Collections.unmodifiableList(formParams);
	}

	@Override
	public String getContentType() {
		return APPLICATION_FORM_URLENCODED;
	}

    @Override
    public List<HttpParameter> getPayload() {
        
        return formParams;
    }
}
