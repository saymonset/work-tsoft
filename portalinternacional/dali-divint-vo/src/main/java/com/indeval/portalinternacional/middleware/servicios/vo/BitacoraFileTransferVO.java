package com.indeval.portalinternacional.middleware.servicios.vo;

import java.io.Serializable;
import java.util.Date;

public class BitacoraFileTransferVO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String usRegistro;
	private String usAutoriza;
	private Date [] fechaProcesamiento;
	
	public String getUsRegistro() {
		return usRegistro;
	}
	
	public void setUsRegistro(String usRegistro) {
		this.usRegistro = usRegistro;
	}
	
	public String getUsAutoriza() {
		return usAutoriza;
	}
	
	public void setUsAutoriza(String usAutoriza) {
		this.usAutoriza = usAutoriza;
	}
	
	public Date[] getFechaProcesamiento() {
		return fechaProcesamiento;
	}
	
	public void setFechaProcesamiento(Date[] fechaProcesamiento) {
		this.fechaProcesamiento = fechaProcesamiento;
	}
	
}
