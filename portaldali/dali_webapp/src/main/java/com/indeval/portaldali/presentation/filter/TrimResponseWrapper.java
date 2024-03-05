package com.indeval.portaldali.presentation.filter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class TrimResponseWrapper extends HttpServletResponseWrapper {

	private ByteArrayOutputStream output;

	private PrintWriter writer;

	private HttpServletResponse origResponse;

	private int contentLength;

	private String contentType;

	public TrimResponseWrapper(HttpServletResponse httpservletresponse) {
		super(httpservletresponse);
		output = null;
		writer = null;
		output = new ByteArrayOutputStream();
		origResponse = httpservletresponse;
	}

	public byte[] getData() {
		try {
			finishResponse();
		} catch (Exception exception) {
		}
		return output.toByteArray();
	}

	public ServletOutputStream getOutputStream() {
		return new TrimServletOutputStream(output);
	}

	public ServletOutputStream createOutputStream() throws IOException {
		return new TrimServletOutputStream(output);
	}

	public PrintWriter getWriter() throws IOException {
		if (writer != null) {
			return writer;
		} else {
			ServletOutputStream servletoutputstream = getOutputStream();
			writer = new PrintWriter(new OutputStreamWriter(
					servletoutputstream, origResponse.getCharacterEncoding()));
			return writer;
		}
	}

	public void finishResponse() {
		try {
			if (writer != null)
				writer.close();
			else if (output != null) {
				output.flush();
				output.close();
			}
		} catch (IOException ioexception) {
		}
	}

	public void flushBuffer() throws IOException {
		output.flush();
	}

	public void setContentLength(int i) {
		contentLength = i;
		super.setContentLength(i);
	}

	public int getContentLength() {
		return contentLength;
	}

	public void setContentType(String s) {
		contentType = s;
		super.setContentType(s);
	}

	public String getContentType() {
		return contentType;
	}
}