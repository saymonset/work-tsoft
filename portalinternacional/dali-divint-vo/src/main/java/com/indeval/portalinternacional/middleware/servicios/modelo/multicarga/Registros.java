package com.indeval.portalinternacional.middleware.servicios.modelo.multicarga;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("registrosCargados")
public class Registros {
	
	@XStreamAlias("listaRegistros") 
	@XStreamAsAttribute
	private List listaRegistros;

	public List getListaRegistros() {
		return listaRegistros;
	}

	public void setListaRegistros(List listaRegistros) {
		this.listaRegistros = listaRegistros;
	}

}
