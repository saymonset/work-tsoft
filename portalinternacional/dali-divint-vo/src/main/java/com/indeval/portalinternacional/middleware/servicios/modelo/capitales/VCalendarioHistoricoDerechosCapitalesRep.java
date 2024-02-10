package com.indeval.portalinternacional.middleware.servicios.modelo.capitales;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Entidad de la vista V_HISTORICO_DERECHO_INT
 * @author javier.perez
 *
 */
@Entity
@Table(name = "V_HISTORICO_DERECHO_INT")
public class VCalendarioHistoricoDerechosCapitalesRep extends VCalendarioHistoricoDerechosCapitalesBase{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Transient
	public String getFechasAdicionlesFormatoReporte(){
		return this.fechasAdicionales != null ? this.fechasAdicionales.replaceAll("</br>", ", ").replace("|",": ") : null;
	}
	
	@Transient
	public String getRatioAdicionalFormatoReporte(){
		return this.ratioAdicional != null ? this.ratioAdicional.replaceAll("</br>", " ") : null;
	}
	
}
