/*
 * Copyrigth (c) 2009 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.presentation.filter;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;

public class TrimServletOutputStream extends ServletOutputStream {

	private DataOutputStream stream;

	public TrimServletOutputStream(OutputStream outputstream) {
		stream = new DataOutputStream(outputstream);
	}

	public void write(int i) throws IOException {
		stream.write(i);
	}

    @Override
	public void write(byte abyte0[]) throws IOException {
		stream.write(abyte0);
	}

    @Override
	public void write(byte abyte0[], int i, int j) throws IOException {
		stream.write(abyte0, i, j);
	}

	@Override
	public boolean isReady() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setWriteListener(WriteListener arg0) {
		// TODO Auto-generated method stub
		
	}

}