package com.indeval.portalinternacional.middleware.servicios.multicarga.excell.vo;

import java.io.Serializable;
import java.util.List;

public class MulticargaResumenExcelVO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private List listaRegistrosError;
	
	private int totalRegistros = 0;
	
	private int totalRegistrosConError = 0;
	
	private int totalRegistrosCargados = 0;

		
	public List getListaRegistrosError() {
		return listaRegistrosError;
	}

	public void setListaRegistrosError(
			List listaRegistrosError) {
		this.listaRegistrosError = listaRegistrosError;
	}

	public int getTotalRegistros() {
		return totalRegistros;
	}

	public void setTotalRegistros(int totalRegistros) {
		this.totalRegistros = totalRegistros;
	}

	public int getTotalRegistrosConError() {
		return totalRegistrosConError;
	}

	public void setTotalRegistrosConError(int totalRegistrosConError) {
		this.totalRegistrosConError = totalRegistrosConError;
	}

	public int getTotalRegistrosCargados() {
		return totalRegistrosCargados;
	}

	public void setTotalRegistrosCargados(int totalRegistrosCargados) {
		this.totalRegistrosCargados = totalRegistrosCargados;
	}
	
	
	
	
	
	

}
