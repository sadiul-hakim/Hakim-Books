package org.learn.book_management_system.security;

import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import org.springframework.util.StreamUtils;

import java.io.*;

class CachedBodyHttpServletRequest extends HttpServletRequestWrapper {

	private final byte[] cachedBody;

	public CachedBodyHttpServletRequest(HttpServletRequest request) throws IOException {
		super(request);
		InputStream requestInputStream = request.getInputStream();
		this.cachedBody = StreamUtils.copyToByteArray(requestInputStream);
	}

	@Override
	public ServletInputStream getInputStream() throws IOException {
		return new CachedBodyServletInputStream(this.cachedBody);
	}

	@Override
	public BufferedReader getReader() throws IOException {
		// Create a reader from cachedContent
		// and return it
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(this.cachedBody);
		return new BufferedReader(new InputStreamReader(byteArrayInputStream));
	}

}