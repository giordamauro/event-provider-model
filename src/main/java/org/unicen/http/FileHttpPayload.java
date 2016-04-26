package org.unicen.http;

import java.io.File;
import java.util.Objects;

public class FileHttpPayload implements HttpPayload<File> {

    private static final String APPLICATION_OCTET_STREAM = "application/octet-stream";
    
	private final File payload;

	public FileHttpPayload(File file) {
	    
	    Objects.requireNonNull(file, "File cannot be null");
	    
		this.payload = file;
	}

	@Override
	public File getPayload() {
		return payload;
	}

    @Override
    public String getContentType() {
        return APPLICATION_OCTET_STREAM;
    }
}
