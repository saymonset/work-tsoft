package com.indeval.portalinternacional.middleware.servicios.modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.indeval.portaldali.persistence.modelo.CuentaNombrada;

@Entity
@Table(name="T_TITULOS_GRUPOS_RETENCION")
public class TitulosGrupoRetencion implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5145167750773055220L;
	
	private Long idTitulosGrupos;
	private GrupoRetencion grupoRetencion;
	private CuentaNombrada cuentaNombrada;
	
	@Id
	@Column(name="ID_TITULOS_GRUPOS")
	public Long getIdTitulosGrupos() {
		return idTitulosGrupos;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_GRUPO_RETENCION", updatable = false, insertable = false)
	public GrupoRetencion getGrupoRetencion() {
		return grupoRetencion;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_CUENTA", updatable = false, insertable = false)
	public CuentaNombrada getCuentaNombrada() {
		return cuentaNombrada;
	}
	public void setIdTitulosGrupos(Long idTitulosGrupos) {
		this.idTitulosGrupos = idTitulosGrupos;
	}
	public void setGrupoRetencion(GrupoRetencion grupoRetencion) {
		this.grupoRetencion = grupoRetencion;
	}
	public void setCuentaNombrada(CuentaNombrada cuentaNombrada) {
		this.cuentaNombrada = cuentaNombrada;
	}
	
}
