package com.indeval.portaldali.middleware.dto;

import java.io.Serializable;

public class ConciliacionModulosExcelDTO implements Serializable {

	private static final long serialVersionUID = 2602283977743085127L;

	private boolean reporteVacio;
	private byte[] bytesReporte;

	public boolean isReporteVacio() {
		return reporteVacio;
	}

	public void setReporteVacio(boolean reporteVacio) {
		this.reporteVacio = reporteVacio;
	}

	public byte[] getBytesReporte() {
		return bytesReporte;
	}

	public void setBytesReporte(byte[] bytesReporte) {
		this.bytesReporte = bytesReporte;
	}

}
