package org.unicen.http;

import java.util.List;
import java.util.Map;

public interface HttpRequest {

	HttpMethod getMethod();

	String getUrl();

	List<HttpParameter> getQueryParams();

	Map<String, String> getHeaders();

	String getHeader(String name);
	
	HttpPayload<?> getPayload();
}
