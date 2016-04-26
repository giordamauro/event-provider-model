package org.unicen.http;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class MultipartHttpPayload implements HttpPayload<List<HttpPayload<?>>> {

    private static final String MULTIPART_FORM_DATA = "multipart/form-data";
    
    private final List<HttpPayload<?>> multipartPayload;

    public MultipartHttpPayload(List<HttpPayload<?>> multipartPayload) {
        
        Objects.requireNonNull(multipartPayload, "MultipartPayload cannot be null");
        
        this.multipartPayload = Collections.unmodifiableList(multipartPayload);
    }

    @Override
    public String getContentType() {
        return MULTIPART_FORM_DATA;
    }

    @Override
    public List<HttpPayload<?>> getPayload() {
        return multipartPayload;
    }
}
