/*
 * Copyrigth (c) 2009 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.presentation.filter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TrimResponseWrapper extends HttpServletResponseWrapper {

    private ByteArrayOutputStream output;
    private PrintWriter writer;
    private HttpServletResponse origResponse;
    private int contentLength;
    private String contentType;
    private final Logger log = LoggerFactory.getLogger(this.getClass());

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
            log.debug("Error al escribir el buffer", exception);
        }
        return output.toByteArray();
    }

    @Override
    public ServletOutputStream getOutputStream() {
        return new TrimServletOutputStream(output);
    }

    public ServletOutputStream createOutputStream() throws IOException {
        return new TrimServletOutputStream(output);
    }

    @Override
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
            if (writer != null) {
                writer.close();
            } else if (output != null) {
                output.flush();
                output.close();
            }
        } catch (IOException exception) {
            log.debug("Error al escribir el buffer", exception);
        }
    }

    @Override
    public void flushBuffer() throws IOException {
        output.flush();
    }

    @Override
    public void setContentLength(int i) {
        contentLength = i;
        super.setContentLength(i);
    }

    public int getContentLength() {
        return contentLength;
    }

    @Override
    public void setContentType(String s) {
        contentType = s;
        super.setContentType(s);
    }

    @Override
    public String getContentType() {
        return contentType;
    }
}
