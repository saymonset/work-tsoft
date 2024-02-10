package com.indeval.portalinternacional.middleware.servicios.modelo.capitales;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("EnvioSic")
public class EnvioSicXML {

	@XStreamAlias("idHistorico")	
    private String idHistorico;
	
	@XStreamAlias("idCalendario")	
    private String idCalendario;
	
	@XStreamAlias("usuario")	
    private String usuario;
	
	@XStreamAlias("destinatarios")	
    private String destinatarios;

	public EnvioSicXML(String idHistorico, String idCalendario, String usuario, String destinatarios) {
		super();
		this.idHistorico = idHistorico;
		this.idCalendario = idCalendario;
		this.usuario = usuario;
		this.destinatarios = destinatarios;
	}

	public EnvioSicXML() {
		super();
	}

	public String getIdHistorico() {
		return idHistorico;
	}

	public void setIdHistorico(String idHistorico) {
		this.idHistorico = idHistorico;
	}

	public String getIdCalendario() {
		return idCalendario;
	}

	public void setIdCalendario(String idCalendario) {
		this.idCalendario = idCalendario;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getDestinatarios() {
		return destinatarios;
	}

	public void setDestinatarios(String destinatarios) {
		this.destinatarios = destinatarios;
	}
	
}
