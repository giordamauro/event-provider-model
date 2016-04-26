package org.unicen.http;

import java.io.InputStream;
import java.util.Map;

public interface HttpResponse {

	int getStatusCode();

	Map<String, String> getHeaders();

	InputStream getResponseContent();
}
